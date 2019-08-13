/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.AdaptableX509CertSelector;
import sun.security.provider.certpath.Builder;
import sun.security.provider.certpath.CertPathHelper;
import sun.security.provider.certpath.ForwardState;
import sun.security.provider.certpath.KeyChecker;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.State;
import sun.security.provider.certpath.URICertStore;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;

class ForwardBuilder
extends Builder {
    private static final Debug debug = Debug.getInstance("certpath");
    private AdaptableX509CertSelector caSelector;
    private X509CertSelector caTargetSelector;
    private X509CertSelector eeSelector;
    private boolean searchAllCertStores = true;
    TrustAnchor trustAnchor;
    private final Set<TrustAnchor> trustAnchors;
    private final Set<X509Certificate> trustedCerts;
    private final Set<X500Principal> trustedSubjectDNs;

    /*
     * WARNING - void declaration
     */
    ForwardBuilder(PKIX.BuilderParams object2, boolean bl) {
        super((PKIX.BuilderParams)object2);
        void var2_4;
        this.trustAnchors = ((PKIX.ValidatorParams)object2).trustAnchors();
        this.trustedCerts = new HashSet<X509Certificate>(this.trustAnchors.size());
        this.trustedSubjectDNs = new HashSet<X500Principal>(this.trustAnchors.size());
        for (TrustAnchor trustAnchor : this.trustAnchors) {
            X509Certificate x509Certificate = trustAnchor.getTrustedCert();
            if (x509Certificate != null) {
                this.trustedCerts.add(x509Certificate);
                this.trustedSubjectDNs.add(x509Certificate.getSubjectX500Principal());
                continue;
            }
            this.trustedSubjectDNs.add(trustAnchor.getCA());
        }
        this.searchAllCertStores = var2_4;
    }

    private boolean getCerts(AuthorityInfoAccessExtension iterator, Collection<X509Certificate> collection) {
        if (!Builder.USE_AIA) {
            return false;
        }
        if ((iterator = ((AuthorityInfoAccessExtension)((Object)iterator)).getAccessDescriptions()) != null && !iterator.isEmpty()) {
            boolean bl = false;
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                boolean bl2;
                block8 : {
                    CertStore certStore = URICertStore.getInstance((AccessDescription)iterator.next());
                    bl2 = bl;
                    if (certStore != null) {
                        bl2 = bl;
                        if (!collection.addAll(certStore.getCertificates(this.caSelector))) break block8;
                        bl = true;
                        bl2 = true;
                        try {
                            boolean bl3 = this.searchAllCertStores;
                            if (!bl3) {
                                return true;
                            }
                        }
                        catch (CertStoreException certStoreException) {
                            Debug debug = ForwardBuilder.debug;
                            bl2 = bl;
                            if (debug == null) break block8;
                            debug.println("exception getting certs from CertStore:");
                            certStoreException.printStackTrace();
                            bl2 = bl;
                        }
                    }
                }
                bl = bl2;
            }
            return bl;
        }
        return false;
    }

    private void getMatchingCACerts(ForwardState object, List<CertStore> object2, Collection<X509Certificate> collection) throws IOException {
        Object object3 = debug;
        if (object3 != null) {
            ((Debug)object3).println("ForwardBuilder.getMatchingCACerts()...");
        }
        int n = collection.size();
        if (((ForwardState)object).isInitial()) {
            if (this.targetCertConstraints.getBasicConstraints() == -2) {
                return;
            }
            object3 = debug;
            if (object3 != null) {
                ((Debug)object3).println("ForwardBuilder.getMatchingCACerts(): the target is a CA");
            }
            if (this.caTargetSelector == null) {
                this.caTargetSelector = (X509CertSelector)this.targetCertConstraints.clone();
                if (this.buildParams.explicitPolicyRequired()) {
                    this.caTargetSelector.setPolicy(this.getMatchingPolicies());
                }
            }
            object3 = this.caTargetSelector;
        } else {
            if (this.caSelector == null) {
                this.caSelector = new AdaptableX509CertSelector();
                if (this.buildParams.explicitPolicyRequired()) {
                    this.caSelector.setPolicy(this.getMatchingPolicies());
                }
            }
            this.caSelector.setSubject(((ForwardState)object).issuerDN);
            CertPathHelper.setPathToNames(this.caSelector, ((ForwardState)object).subjectNamesTraversed);
            this.caSelector.setValidityPeriod(((ForwardState)object).cert.getNotBefore(), ((ForwardState)object).cert.getNotAfter());
            object3 = this.caSelector;
        }
        ((X509CertSelector)object3).setBasicConstraints(-1);
        for (X509Certificate x509Certificate : this.trustedCerts) {
            if (!((X509CertSelector)object3).match(x509Certificate)) continue;
            Debug debug = ForwardBuilder.debug;
            if (debug != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ForwardBuilder.getMatchingCACerts: found matching trust anchor.\n  SN: ");
                stringBuilder.append(Debug.toHexString(x509Certificate.getSerialNumber()));
                stringBuilder.append("\n  Subject: ");
                stringBuilder.append(x509Certificate.getSubjectX500Principal());
                stringBuilder.append("\n  Issuer: ");
                stringBuilder.append(x509Certificate.getIssuerX500Principal());
                debug.println(stringBuilder.toString());
            }
            if (!collection.add(x509Certificate) || this.searchAllCertStores) continue;
            return;
        }
        ((X509CertSelector)object3).setCertificateValid(this.buildParams.date());
        ((X509CertSelector)object3).setBasicConstraints(((ForwardState)object).traversedCACerts);
        if ((((ForwardState)object).isInitial() || this.buildParams.maxPathLength() == -1 || this.buildParams.maxPathLength() > ((ForwardState)object).traversedCACerts) && this.addMatchingCerts((X509CertSelector)object3, (Collection<CertStore>)object2, collection, this.searchAllCertStores) && !this.searchAllCertStores) {
            return;
        }
        if (!((ForwardState)object).isInitial() && Builder.USE_AIA && (object = ((ForwardState)object).cert.getAuthorityInfoAccessExtension()) != null) {
            this.getCerts((AuthorityInfoAccessExtension)object, collection);
        }
        if (debug != null) {
            int n2 = collection.size();
            object = debug;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ForwardBuilder.getMatchingCACerts: found ");
            ((StringBuilder)object2).append(n2 - n);
            ((StringBuilder)object2).append(" CA certs");
            ((Debug)object).println(((StringBuilder)object2).toString());
        }
    }

    private void getMatchingEECerts(ForwardState object, List<CertStore> list, Collection<X509Certificate> collection) throws IOException {
        object = debug;
        if (object != null) {
            ((Debug)object).println("ForwardBuilder.getMatchingEECerts()...");
        }
        if (this.eeSelector == null) {
            this.eeSelector = (X509CertSelector)this.targetCertConstraints.clone();
            this.eeSelector.setCertificateValid(this.buildParams.date());
            if (this.buildParams.explicitPolicyRequired()) {
                this.eeSelector.setPolicy(this.getMatchingPolicies());
            }
            this.eeSelector.setBasicConstraints(-2);
        }
        this.addMatchingCerts(this.eeSelector, list, collection, this.searchAllCertStores);
    }

    @Override
    void addCertToPath(X509Certificate x509Certificate, LinkedList<X509Certificate> linkedList) {
        linkedList.addFirst(x509Certificate);
    }

    @Override
    Collection<X509Certificate> getMatchingCerts(State state, List<CertStore> list) throws CertStoreException, CertificateException, IOException {
        Object object = debug;
        if (object != null) {
            ((Debug)object).println("ForwardBuilder.getMatchingCerts()...");
        }
        state = (ForwardState)state;
        object = new TreeSet<X509Certificate>(new PKIXCertComparator(this.trustedSubjectDNs, ((ForwardState)state).cert));
        if (((ForwardState)state).isInitial()) {
            this.getMatchingEECerts((ForwardState)state, list, (Collection<X509Certificate>)object);
        }
        this.getMatchingCACerts((ForwardState)state, list, (Collection<X509Certificate>)object);
        return object;
    }

    @Override
    boolean isPathCompleted(X509Certificate x509Certificate) {
        Object invalidKeyException;
        Serializable serializable = new ArrayList();
        for (TrustAnchor object3 : this.trustAnchors) {
            if (object3.getTrustedCert() != null) {
                if (!x509Certificate.equals(object3.getTrustedCert())) continue;
                this.trustAnchor = object3;
                return true;
            }
            invalidKeyException = object3.getCA();
            PublicKey publicKey = object3.getCAPublicKey();
            if (invalidKeyException != null && publicKey != null && ((X500Principal)invalidKeyException).equals(x509Certificate.getSubjectX500Principal()) && publicKey.equals(x509Certificate.getPublicKey())) {
                this.trustAnchor = object3;
                return true;
            }
            serializable.add(object3);
        }
        Iterator iterator = serializable.iterator();
        while (iterator.hasNext()) {
            invalidKeyException = (TrustAnchor)iterator.next();
            serializable = ((TrustAnchor)invalidKeyException).getCA();
            PublicKey publicKey = ((TrustAnchor)invalidKeyException).getCAPublicKey();
            if (serializable == null || !((X500Principal)serializable).equals(x509Certificate.getIssuerX500Principal()) || PKIX.isDSAPublicKeyWithoutParams(publicKey)) continue;
            try {
                if (this.buildParams.sigProvider() != null) {
                    x509Certificate.verify(publicKey, this.buildParams.sigProvider());
                } else {
                    x509Certificate.verify(publicKey);
                }
                this.trustAnchor = invalidKeyException;
                return true;
            }
            catch (GeneralSecurityException generalSecurityException) {
                invalidKeyException = debug;
                if (invalidKeyException == null) continue;
                ((Debug)invalidKeyException).println("ForwardBuilder.isPathCompleted() unexpected exception");
                generalSecurityException.printStackTrace();
            }
            catch (InvalidKeyException invalidKeyException2) {
                invalidKeyException = debug;
                if (invalidKeyException == null) continue;
                ((Debug)invalidKeyException).println("ForwardBuilder.isPathCompleted() invalid DSA key found");
            }
        }
        return false;
    }

    @Override
    void removeFinalCertFromPath(LinkedList<X509Certificate> linkedList) {
        linkedList.removeFirst();
    }

    @Override
    void verifyCert(X509Certificate object, State collection, List<X509Certificate> iterator) throws GeneralSecurityException {
        boolean bl;
        Object object2;
        Debug object32 = debug;
        if (object32 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ForwardBuilder.verifyCert(SN: ");
            ((StringBuilder)object2).append(Debug.toHexString(((X509Certificate)object).getSerialNumber()));
            ((StringBuilder)object2).append("\n  Issuer: ");
            ((StringBuilder)object2).append(((X509Certificate)object).getIssuerX500Principal());
            ((StringBuilder)object2).append(")\n  Subject: ");
            ((StringBuilder)object2).append(((X509Certificate)object).getSubjectX500Principal());
            ((StringBuilder)object2).append(")");
            object32.println(((StringBuilder)object2).toString());
        }
        object2 = (ForwardState)((Object)collection);
        if (iterator != null) {
            collection = iterator.iterator();
            while (collection.hasNext()) {
                if (!((Certificate)object).equals((X509Certificate)collection.next())) continue;
                object = debug;
                if (object != null) {
                    ((Debug)object).println("loop detected!!");
                }
                throw new CertPathValidatorException("loop detected");
            }
        }
        if (!(bl = this.trustedCerts.contains(object))) {
            iterator = object.getCriticalExtensionOIDs();
            collection = iterator;
            if (iterator == null) {
                collection = Collections.emptySet();
            }
            iterator = ((ForwardState)object2).forwardCheckers.iterator();
            while (iterator.hasNext()) {
                iterator.next().check((Certificate)object, collection);
            }
            for (PKIXCertPathChecker pKIXCertPathChecker : this.buildParams.certPathCheckers()) {
                Set<String> set;
                if (pKIXCertPathChecker.isForwardCheckingSupported() || (set = pKIXCertPathChecker.getSupportedExtensions()) == null) continue;
                collection.removeAll(set);
            }
            if (!collection.isEmpty()) {
                collection.remove(PKIXExtensions.BasicConstraints_Id.toString());
                collection.remove(PKIXExtensions.NameConstraints_Id.toString());
                collection.remove(PKIXExtensions.CertificatePolicies_Id.toString());
                collection.remove(PKIXExtensions.PolicyMappings_Id.toString());
                collection.remove(PKIXExtensions.PolicyConstraints_Id.toString());
                collection.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
                collection.remove(PKIXExtensions.SubjectAlternativeName_Id.toString());
                collection.remove(PKIXExtensions.KeyUsage_Id.toString());
                collection.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
                if (!collection.isEmpty()) {
                    throw new CertPathValidatorException("Unrecognized critical extension(s)", null, null, -1, PKIXReason.UNRECOGNIZED_CRIT_EXT);
                }
            }
        }
        if (((ForwardState)object2).isInitial()) {
            return;
        }
        if (!bl) {
            if (((X509Certificate)object).getBasicConstraints() != -1) {
                KeyChecker.verifyCAKeyUsage((X509Certificate)object);
            } else {
                throw new CertificateException("cert is NOT a CA cert");
            }
        }
        if (!((ForwardState)object2).keyParamsNeeded()) {
            if (this.buildParams.sigProvider() != null) {
                ((ForwardState)object2).cert.verify(((Certificate)object).getPublicKey(), this.buildParams.sigProvider());
            } else {
                ((ForwardState)object2).cert.verify(((Certificate)object).getPublicKey());
            }
        }
    }

    static class PKIXCertComparator
    implements Comparator<X509Certificate> {
        static final String METHOD_NME = "PKIXCertComparator.compare()";
        private final X509CertSelector certSkidSelector;
        private final Set<X500Principal> trustedSubjectDNs;

        PKIXCertComparator(Set<X500Principal> set, X509CertImpl x509CertImpl) throws IOException {
            this.trustedSubjectDNs = set;
            this.certSkidSelector = this.getSelector(x509CertImpl);
        }

        private X509CertSelector getSelector(X509CertImpl object) throws IOException {
            byte[] arrby;
            if (object != null && (object = ((X509CertImpl)object).getAuthorityKeyIdentifierExtension()) != null && (arrby = ((AuthorityKeyIdentifierExtension)object).getEncodedKeyIdentifier()) != null) {
                object = new X509CertSelector();
                ((X509CertSelector)object).setSubjectKeyIdentifier(arrby);
                return object;
            }
            return null;
        }

        @Override
        public int compare(X509Certificate object, X509Certificate object2) {
            Object object3;
            int n;
            int n2;
            Object object4;
            if (((Certificate)object).equals(object2)) {
                return 0;
            }
            Object object5 = this.certSkidSelector;
            if (object5 != null) {
                if (((X509CertSelector)object5).match((Certificate)object)) {
                    return -1;
                }
                if (this.certSkidSelector.match((Certificate)object2)) {
                    return 1;
                }
            }
            object5 = ((X509Certificate)object).getIssuerX500Principal();
            Object object6 = ((X509Certificate)object2).getIssuerX500Principal();
            X500Name x500Name = X500Name.asX500Name((X500Principal)object5);
            X500Name x500Name2 = X500Name.asX500Name((X500Principal)object6);
            if (debug != null) {
                object3 = debug;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("PKIXCertComparator.compare() o1 Issuer:  ");
                ((StringBuilder)object4).append(object5);
                ((Debug)object3).println(((StringBuilder)object4).toString());
                object4 = debug;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("PKIXCertComparator.compare() o2 Issuer:  ");
                ((StringBuilder)object3).append(object6);
                ((Debug)object4).println(((StringBuilder)object3).toString());
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() MATCH TRUSTED SUBJECT TEST...");
            }
            boolean bl = this.trustedSubjectDNs.contains(object5);
            boolean bl2 = this.trustedSubjectDNs.contains(object6);
            if (debug != null) {
                object6 = debug;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("PKIXCertComparator.compare() m1: ");
                ((StringBuilder)object4).append(bl);
                ((Debug)object6).println(((StringBuilder)object4).toString());
                object4 = debug;
                object6 = new StringBuilder();
                ((StringBuilder)object6).append("PKIXCertComparator.compare() m2: ");
                ((StringBuilder)object6).append(bl2);
                ((Debug)object4).println(((StringBuilder)object6).toString());
            }
            if (bl && bl2) {
                return -1;
            }
            if (bl) {
                return -1;
            }
            if (bl2) {
                return 1;
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() NAMING DESCENDANT TEST...");
            }
            object6 = this.trustedSubjectDNs.iterator();
            while (object6.hasNext()) {
                object4 = X500Name.asX500Name(object6.next());
                n2 = Builder.distance((GeneralNameInterface)object4, x500Name, -1);
                n = Builder.distance((GeneralNameInterface)object4, x500Name2, -1);
                if (debug != null) {
                    object4 = debug;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("PKIXCertComparator.compare() distanceTto1: ");
                    ((StringBuilder)object3).append(n2);
                    ((Debug)object4).println(((StringBuilder)object3).toString());
                    object4 = debug;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("PKIXCertComparator.compare() distanceTto2: ");
                    ((StringBuilder)object3).append(n);
                    ((Debug)object4).println(((StringBuilder)object3).toString());
                }
                if (n2 <= 0 && n <= 0) continue;
                if (n2 == n) {
                    return -1;
                }
                if (n2 > 0 && n <= 0) {
                    return -1;
                }
                if (n2 <= 0 && n > 0) {
                    return 1;
                }
                if (n2 < n) {
                    return -1;
                }
                return 1;
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() NAMING ANCESTOR TEST...");
            }
            object5 = this.trustedSubjectDNs.iterator();
            while (object5.hasNext()) {
                object6 = X500Name.asX500Name(object5.next());
                n = Builder.distance((GeneralNameInterface)object6, x500Name, Integer.MAX_VALUE);
                n2 = Builder.distance((GeneralNameInterface)object6, x500Name2, Integer.MAX_VALUE);
                if (debug != null) {
                    object6 = debug;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("PKIXCertComparator.compare() distanceTto1: ");
                    ((StringBuilder)object4).append(n);
                    ((Debug)object6).println(((StringBuilder)object4).toString());
                    object4 = debug;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("PKIXCertComparator.compare() distanceTto2: ");
                    ((StringBuilder)object6).append(n2);
                    ((Debug)object4).println(((StringBuilder)object6).toString());
                }
                if (n >= 0 && n2 >= 0) continue;
                if (n == n2) {
                    return -1;
                }
                if (n < 0 && n2 >= 0) {
                    return -1;
                }
                if (n >= 0 && n2 < 0) {
                    return 1;
                }
                if (n > n2) {
                    return -1;
                }
                return 1;
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() SAME NAMESPACE AS TRUSTED TEST...");
            }
            object5 = this.trustedSubjectDNs.iterator();
            while (object5.hasNext()) {
                object4 = X500Name.asX500Name(object5.next());
                object6 = ((X500Name)object4).commonAncestor(x500Name);
                object3 = ((X500Name)object4).commonAncestor(x500Name2);
                if (debug != null) {
                    Object object7 = debug;
                    Object object8 = new StringBuilder();
                    ((StringBuilder)object8).append("PKIXCertComparator.compare() tAo1: ");
                    ((StringBuilder)object8).append(String.valueOf(object6));
                    ((Debug)object7).println(((StringBuilder)object8).toString());
                    object8 = debug;
                    object7 = new StringBuilder();
                    ((StringBuilder)object7).append("PKIXCertComparator.compare() tAo2: ");
                    ((StringBuilder)object7).append(String.valueOf(object3));
                    ((Debug)object8).println(((StringBuilder)object7).toString());
                }
                if (object6 == null && object3 == null) continue;
                if (object6 != null && object3 != null) {
                    n = Builder.hops((GeneralNameInterface)object4, x500Name, Integer.MAX_VALUE);
                    n2 = Builder.hops((GeneralNameInterface)object4, x500Name2, Integer.MAX_VALUE);
                    if (debug != null) {
                        object4 = debug;
                        object6 = new StringBuilder();
                        ((StringBuilder)object6).append("PKIXCertComparator.compare() hopsTto1: ");
                        ((StringBuilder)object6).append(n);
                        ((Debug)object4).println(((StringBuilder)object6).toString());
                        object6 = debug;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("PKIXCertComparator.compare() hopsTto2: ");
                        ((StringBuilder)object4).append(n2);
                        ((Debug)object6).println(((StringBuilder)object4).toString());
                    }
                    if (n == n2) continue;
                    if (n > n2) {
                        return 1;
                    }
                    return -1;
                }
                if (object6 == null) {
                    return 1;
                }
                return -1;
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() CERT ISSUER/SUBJECT COMPARISON TEST...");
            }
            object6 = ((X509Certificate)object).getSubjectX500Principal();
            object2 = ((X509Certificate)object2).getSubjectX500Principal();
            object = X500Name.asX500Name((X500Principal)object6);
            object5 = X500Name.asX500Name((X500Principal)object2);
            if (debug != null) {
                object3 = debug;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("PKIXCertComparator.compare() o1 Subject: ");
                ((StringBuilder)object4).append(object6);
                ((Debug)object3).println(((StringBuilder)object4).toString());
                object4 = debug;
                object6 = new StringBuilder();
                ((StringBuilder)object6).append("PKIXCertComparator.compare() o2 Subject: ");
                ((StringBuilder)object6).append(object2);
                ((Debug)object4).println(((StringBuilder)object6).toString());
            }
            n2 = Builder.distance((GeneralNameInterface)object, x500Name, Integer.MAX_VALUE);
            n = Builder.distance((GeneralNameInterface)object5, x500Name2, Integer.MAX_VALUE);
            if (debug != null) {
                object2 = debug;
                object = new StringBuilder();
                ((StringBuilder)object).append("PKIXCertComparator.compare() distanceStoI1: ");
                ((StringBuilder)object).append(n2);
                ((Debug)object2).println(((StringBuilder)object).toString());
                object = debug;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("PKIXCertComparator.compare() distanceStoI2: ");
                ((StringBuilder)object2).append(n);
                ((Debug)object).println(((StringBuilder)object2).toString());
            }
            if (n > n2) {
                return -1;
            }
            if (n < n2) {
                return 1;
            }
            if (debug != null) {
                debug.println("PKIXCertComparator.compare() no tests matched; RETURN 0");
            }
            return -1;
        }
    }

}

