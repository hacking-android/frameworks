/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.action.GetBooleanAction;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.State;
import sun.security.util.Debug;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralSubtree;
import sun.security.x509.GeneralSubtrees;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;

public abstract class Builder {
    static final boolean USE_AIA;
    private static final Debug debug;
    final PKIX.BuilderParams buildParams;
    private Set<String> matchingPolicies;
    final X509CertSelector targetCertConstraints;

    static {
        debug = Debug.getInstance("certpath");
        USE_AIA = AccessController.doPrivileged(new GetBooleanAction("com.sun.security.enableAIAcaIssuers"));
    }

    Builder(PKIX.BuilderParams builderParams) {
        this.buildParams = builderParams;
        this.targetCertConstraints = (X509CertSelector)builderParams.targetCertConstraints();
    }

    static int distance(GeneralNameInterface object, GeneralNameInterface generalNameInterface, int n) {
        int n2 = object.constrains(generalNameInterface);
        if (n2 != -1) {
            if (n2 != 0) {
                if (n2 != 1 && n2 != 2) {
                    if (n2 != 3) {
                        return n;
                    }
                    object = debug;
                    if (object != null) {
                        ((Debug)object).println("Builder.distance(): Names are same type but in different subtrees");
                    }
                    return n;
                }
                return generalNameInterface.subtreeDepth() - object.subtreeDepth();
            }
            return 0;
        }
        object = debug;
        if (object != null) {
            ((Debug)object).println("Builder.distance(): Names are different types");
        }
        return n;
    }

    static int hops(GeneralNameInterface object, GeneralNameInterface generalNameInterface, int n) {
        int n2 = object.constrains(generalNameInterface);
        if (n2 != -1) {
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            return n;
                        }
                        if (object.getType() != 4) {
                            object = debug;
                            if (object != null) {
                                ((Debug)object).println("Builder.hops(): hopDistance not implemented for this name type");
                            }
                            return n;
                        }
                        object = (X500Name)object;
                        X500Name x500Name = (X500Name)generalNameInterface;
                        generalNameInterface = ((X500Name)object).commonAncestor(x500Name);
                        if (generalNameInterface == null) {
                            object = debug;
                            if (object != null) {
                                ((Debug)object).println("Builder.hops(): Names are in different namespaces");
                            }
                            return n;
                        }
                        n = ((X500Name)generalNameInterface).subtreeDepth();
                        return ((X500Name)object).subtreeDepth() + x500Name.subtreeDepth() - n * 2;
                    }
                    return generalNameInterface.subtreeDepth() - object.subtreeDepth();
                }
                return generalNameInterface.subtreeDepth() - object.subtreeDepth();
            }
            return 0;
        }
        object = debug;
        if (object != null) {
            ((Debug)object).println("Builder.hops(): Names are different types");
        }
        return n;
    }

    static int targetDistance(NameConstraintsExtension nameConstraintsExtension, X509Certificate object, GeneralNameInterface generalNameInterface) throws IOException {
        int n;
        int n2;
        block14 : {
            if (nameConstraintsExtension != null && !nameConstraintsExtension.verify((X509Certificate)object)) {
                throw new IOException("certificate does not satisfy existing name constraints");
            }
            try {
                object = X509CertImpl.toImpl((X509Certificate)object);
                if (!X500Name.asX500Name(((X509CertImpl)object).getSubjectX500Principal()).equals(generalNameInterface)) break block14;
                return 0;
            }
            catch (CertificateException certificateException) {
                throw new IOException("Invalid certificate", certificateException);
            }
        }
        Object object2 = ((X509CertImpl)object).getSubjectAlternativeNameExtension();
        if (object2 != null && (object2 = ((SubjectAlternativeNameExtension)object2).get("subject_name")) != null) {
            n = ((GeneralNames)object2).size();
            for (n2 = 0; n2 < n; ++n2) {
                if (!((GeneralNames)object2).get(n2).getName().equals(generalNameInterface)) continue;
                return 0;
            }
        }
        if ((object = ((X509CertImpl)object).getNameConstraintsExtension()) == null) {
            return -1;
        }
        if (nameConstraintsExtension != null) {
            nameConstraintsExtension.merge((NameConstraintsExtension)object);
        } else {
            nameConstraintsExtension = (NameConstraintsExtension)((NameConstraintsExtension)object).clone();
        }
        object = debug;
        if (object != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Builder.targetDistance() merged constraints: ");
            ((StringBuilder)object2).append(String.valueOf(nameConstraintsExtension));
            ((Debug)object).println(((StringBuilder)object2).toString());
        }
        object = nameConstraintsExtension.get("permitted_subtrees");
        object2 = nameConstraintsExtension.get("excluded_subtrees");
        if (object != null) {
            ((GeneralSubtrees)object).reduce((GeneralSubtrees)object2);
        }
        if ((object2 = debug) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Builder.targetDistance() reduced constraints: ");
            stringBuilder.append(object);
            ((Debug)object2).println(stringBuilder.toString());
        }
        if (nameConstraintsExtension.verify(generalNameInterface)) {
            if (object == null) {
                return -1;
            }
            n = ((GeneralSubtrees)object).size();
            for (n2 = 0; n2 < n; ++n2) {
                int n3 = Builder.distance(((GeneralSubtrees)object).get(n2).getName().getName(), generalNameInterface, -1);
                if (n3 < 0) continue;
                return n3 + 1;
            }
            return -1;
        }
        throw new IOException("New certificate not allowed to sign certificate for target");
    }

    abstract void addCertToPath(X509Certificate var1, LinkedList<X509Certificate> var2);

    boolean addMatchingCerts(X509CertSelector object, Collection<CertStore> object2, Collection<X509Certificate> collection, boolean bl) {
        Object object3 = ((X509CertSelector)object).getCertificate();
        if (object3 != null) {
            if (((X509CertSelector)object).match((Certificate)object3) && !X509CertImpl.isSelfSigned((X509Certificate)object3, this.buildParams.sigProvider())) {
                object = debug;
                if (object != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Builder.addMatchingCerts: adding target cert\n  SN: ");
                    ((StringBuilder)object2).append(Debug.toHexString(((X509Certificate)object3).getSerialNumber()));
                    ((StringBuilder)object2).append("\n  Subject: ");
                    ((StringBuilder)object2).append(((X509Certificate)object3).getSubjectX500Principal());
                    ((StringBuilder)object2).append("\n  Issuer: ");
                    ((StringBuilder)object2).append(((X509Certificate)object3).getIssuerX500Principal());
                    ((Debug)object).println(((StringBuilder)object2).toString());
                }
                return collection.add((X509Certificate)object3);
            }
            return false;
        }
        boolean bl2 = false;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Serializable serializable;
            object3 = (CertStore)object2.next();
            boolean bl3 = bl2;
            try {
                object3 = ((CertStore)object3).getCertificates((CertSelector)object).iterator();
            }
            catch (CertStoreException certStoreException) {
                Debug debug = Builder.debug;
                bl2 = bl3;
                if (debug == null) continue;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Builder.addMatchingCerts, non-fatal exception retrieving certs: ");
                ((StringBuilder)serializable).append(certStoreException);
                debug.println(((StringBuilder)serializable).toString());
                certStoreException.printStackTrace();
                bl2 = bl3;
                continue;
            }
            do {
                boolean bl4;
                block11 : {
                    bl3 = bl2;
                    if (!object3.hasNext()) break;
                    bl3 = bl2;
                    serializable = object3.next();
                    bl4 = bl2;
                    bl3 = bl2;
                    if (X509CertImpl.isSelfSigned((X509Certificate)serializable, this.buildParams.sigProvider())) break block11;
                    bl3 = bl2;
                    boolean bl5 = collection.add((X509Certificate)serializable);
                    bl4 = bl2;
                    if (!bl5) break block11;
                    bl4 = true;
                }
                bl2 = bl4;
            } while (true);
            if (bl || !bl2) continue;
            return true;
        }
        return bl2;
    }

    abstract Collection<X509Certificate> getMatchingCerts(State var1, List<CertStore> var2) throws CertStoreException, CertificateException, IOException;

    Set<String> getMatchingPolicies() {
        if (this.matchingPolicies != null) {
            Set<String> set = this.buildParams.initialPolicies();
            if (!set.isEmpty() && !set.contains("2.5.29.32.0") && this.buildParams.policyMappingInhibited()) {
                this.matchingPolicies = new HashSet<String>(set);
                this.matchingPolicies.add("2.5.29.32.0");
            } else {
                this.matchingPolicies = Collections.emptySet();
            }
        }
        return this.matchingPolicies;
    }

    abstract boolean isPathCompleted(X509Certificate var1);

    abstract void removeFinalCertFromPath(LinkedList<X509Certificate> var1);

    abstract void verifyCert(X509Certificate var1, State var2, List<X509Certificate> var3) throws GeneralSecurityException;
}

