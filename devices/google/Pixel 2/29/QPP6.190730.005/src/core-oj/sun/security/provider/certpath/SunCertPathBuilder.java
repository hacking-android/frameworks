/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.PolicyNode;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.AdjacencyList;
import sun.security.provider.certpath.AlgorithmChecker;
import sun.security.provider.certpath.BasicChecker;
import sun.security.provider.certpath.ForwardBuilder;
import sun.security.provider.certpath.ForwardState;
import sun.security.provider.certpath.PKIX;
import sun.security.provider.certpath.PolicyChecker;
import sun.security.provider.certpath.PolicyNodeImpl;
import sun.security.provider.certpath.RevocationChecker;
import sun.security.provider.certpath.State;
import sun.security.provider.certpath.SunCertPathBuilderException;
import sun.security.provider.certpath.SunCertPathBuilderResult;
import sun.security.provider.certpath.Vertex;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.PKIXExtensions;

public final class SunCertPathBuilder
extends CertPathBuilderSpi {
    private static final Debug debug = Debug.getInstance("certpath");
    private PKIX.BuilderParams buildParams;
    private CertificateFactory cf;
    private PublicKey finalPublicKey;
    private boolean pathCompleted = false;
    private PolicyNode policyTreeResult;
    private TrustAnchor trustAnchor;

    public SunCertPathBuilder() throws CertPathBuilderException {
        try {
            this.cf = CertificateFactory.getInstance("X.509");
            return;
        }
        catch (CertificateException certificateException) {
            throw new CertPathBuilderException(certificateException);
        }
    }

    private static List<Vertex> addVertices(Collection<X509Certificate> object, List<List<Vertex>> list) {
        list = list.get(list.size() - 1);
        object = object.iterator();
        while (object.hasNext()) {
            list.add((List<Vertex>)((Object)new Vertex((X509Certificate)object.next())));
        }
        return list;
    }

    private static boolean anchorIsTarget(TrustAnchor object, CertSelector certSelector) {
        if ((object = ((TrustAnchor)object).getTrustedCert()) != null) {
            return certSelector.match((Certificate)object);
        }
        return false;
    }

    private PKIXCertPathBuilderResult build() throws CertPathBuilderException {
        ArrayList<List<Vertex>> arrayList = new ArrayList<List<Vertex>>();
        PKIXCertPathBuilderResult pKIXCertPathBuilderResult = this.buildCertPath(false, arrayList);
        Object object = pKIXCertPathBuilderResult;
        if (pKIXCertPathBuilderResult == null) {
            object = debug;
            if (object != null) {
                ((Debug)object).println("SunCertPathBuilder.engineBuild: 2nd pass; try building again searching all certstores");
            }
            arrayList.clear();
            object = this.buildCertPath(true, arrayList);
            if (object == null) {
                throw new SunCertPathBuilderException("unable to find valid certification path to requested target", new AdjacencyList(arrayList));
            }
        }
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private PKIXCertPathBuilderResult buildCertPath(boolean bl, List<List<Vertex>> list) throws CertPathBuilderException {
        this.pathCompleted = false;
        this.trustAnchor = null;
        this.finalPublicKey = null;
        this.policyTreeResult = null;
        Object object = new LinkedList<X509Certificate>();
        this.buildForward(list, (LinkedList<X509Certificate>)object, bl);
        try {
            if (!this.pathCompleted) return null;
            if (debug != null) {
                debug.println("SunCertPathBuilder.engineBuild() pathCompleted");
            }
            Collections.reverse(object);
            CertPath certPath = this.cf.generateCertPath((List<? extends Certificate>)object);
            TrustAnchor trustAnchor = this.trustAnchor;
            PolicyNode policyNode = this.policyTreeResult;
            PublicKey publicKey = this.finalPublicKey;
            object = new AdjacencyList(list);
            return new SunCertPathBuilderResult(certPath, trustAnchor, policyNode, publicKey, (AdjacencyList)object);
        }
        catch (CertificateException certificateException) {
            object = debug;
            if (object == null) throw new SunCertPathBuilderException("unable to find valid certification path to requested target", certificateException, new AdjacencyList(list));
            ((Debug)object).println("SunCertPathBuilder.engineBuild() exception in wrap-up");
            certificateException.printStackTrace();
            throw new SunCertPathBuilderException("unable to find valid certification path to requested target", certificateException, new AdjacencyList(list));
        }
        catch (IOException | GeneralSecurityException exception) {
            object = debug;
            if (object == null) throw new SunCertPathBuilderException("unable to find valid certification path to requested target", exception, new AdjacencyList(list));
            ((Debug)object).println("SunCertPathBuilder.engineBuild() exception in build");
            exception.printStackTrace();
            throw new SunCertPathBuilderException("unable to find valid certification path to requested target", exception, new AdjacencyList(list));
        }
    }

    private void buildForward(List<List<Vertex>> list, LinkedList<X509Certificate> linkedList, boolean bl) throws GeneralSecurityException, IOException {
        Object object = debug;
        if (object != null) {
            ((Debug)object).println("SunCertPathBuilder.buildForward()...");
        }
        object = new ForwardState();
        ((ForwardState)object).initState(this.buildParams.certPathCheckers());
        list.clear();
        list.add(new LinkedList());
        this.depthFirstSearchForward(this.buildParams.targetSubject(), (ForwardState)object, new ForwardBuilder(this.buildParams, bl), list, linkedList);
    }

    private void depthFirstSearchForward(X500Principal object, ForwardState forwardState, ForwardBuilder forwardBuilder, List<List<Vertex>> list, LinkedList<X509Certificate> linkedList) throws GeneralSecurityException, IOException {
        Object object3;
        Object object2;
        Object object4 = debug;
        if (object4 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("SunCertPathBuilder.depthFirstSearchForward(");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(", ");
            ((StringBuilder)object2).append(forwardState.toString());
            ((StringBuilder)object2).append(")");
            ((Debug)object4).println(((StringBuilder)object2).toString());
        }
        object4 = forwardBuilder.getMatchingCerts(forwardState, this.buildParams.certStores());
        object2 = SunCertPathBuilder.addVertices((Collection<X509Certificate>)object4, list);
        object = debug;
        if (object != null) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("SunCertPathBuilder.depthFirstSearchForward(): certs.size=");
            ((StringBuilder)object3).append(object2.size());
            ((Debug)object).println(((StringBuilder)object3).toString());
        }
        object = object2.iterator();
        block4 : while (object.hasNext()) {
            Vertex vertex;
            Object object5;
            X509Certificate x509Certificate;
            block32 : {
                Object object6;
                Object object7;
                Object object8;
                ArrayList<X509Certificate> arrayList;
                PolicyChecker policyChecker;
                vertex = (Vertex)object.next();
                object5 = (ForwardState)forwardState.clone();
                x509Certificate = vertex.getCertificate();
                try {
                    forwardBuilder.verifyCert(x509Certificate, (State)object5, linkedList);
                    if (!forwardBuilder.isPathCompleted(x509Certificate)) break block32;
                    object3 = debug;
                    if (object3 != null) {
                        ((Debug)object3).println("SunCertPathBuilder.depthFirstSearchForward(): commencing final verification");
                    }
                    arrayList = new ArrayList<X509Certificate>(linkedList);
                    if (forwardBuilder.trustAnchor.getTrustedCert() == null) {
                        arrayList.add(0, x509Certificate);
                    }
                    object7 = new PolicyNodeImpl(null, "2.5.29.32.0", null, false, Collections.singleton("2.5.29.32.0"), false);
                    object3 = new ArrayList();
                    policyChecker = new PolicyChecker(this.buildParams.initialPolicies(), arrayList.size(), this.buildParams.explicitPolicyRequired(), this.buildParams.policyMappingInhibited(), this.buildParams.anyPolicyInhibited(), this.buildParams.policyQualifiersRejected(), (PolicyNodeImpl)object7);
                    object3.add(policyChecker);
                }
                catch (GeneralSecurityException generalSecurityException) {
                    object3 = debug;
                    if (object3 != null) {
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("SunCertPathBuilder.depthFirstSearchForward(): validation failed: ");
                        ((StringBuilder)object5).append(generalSecurityException);
                        ((Debug)object3).println(((StringBuilder)object5).toString());
                        generalSecurityException.printStackTrace();
                    }
                    vertex.setThrowable(generalSecurityException);
                    continue;
                }
                object3.add(new AlgorithmChecker(forwardBuilder.trustAnchor));
                object7 = null;
                if (((ForwardState)object5).keyParamsNeeded()) {
                    object7 = x509Certificate.getPublicKey();
                    if (forwardBuilder.trustAnchor.getTrustedCert() == null) {
                        object7 = forwardBuilder.trustAnchor.getCAPublicKey();
                        object8 = debug;
                        if (object8 != null) {
                            object5 = new StringBuilder();
                            ((StringBuilder)object5).append("SunCertPathBuilder.depthFirstSearchForward using buildParams public key: ");
                            ((StringBuilder)object5).append(object7.toString());
                            ((Debug)object8).println(((StringBuilder)object5).toString());
                        }
                    }
                    object7 = new BasicChecker(new TrustAnchor(x509Certificate.getSubjectX500Principal(), (PublicKey)object7, null), this.buildParams.date(), this.buildParams.sigProvider(), true);
                    object3.add(object7);
                }
                this.buildParams.setCertPath(this.cf.generateCertPath(arrayList));
                int n = 0;
                object8 = this.buildParams.certPathCheckers();
                object5 = object8.iterator();
                while (object5.hasNext()) {
                    object6 = (PKIXCertPathChecker)object5.next();
                    if (!(object6 instanceof PKIXRevocationChecker)) continue;
                    if (n == 0) {
                        if (object6 instanceof RevocationChecker) {
                            ((RevocationChecker)object6).init(forwardBuilder.trustAnchor, this.buildParams);
                        }
                        n = 1;
                        continue;
                    }
                    throw new CertPathValidatorException("Only one PKIXRevocationChecker can be specified");
                }
                if (this.buildParams.revocationEnabled() && n == 0) {
                    object3.add(new RevocationChecker(forwardBuilder.trustAnchor, this.buildParams));
                }
                object3.addAll(object8);
                object5 = object8;
                for (n = 0; n < arrayList.size(); ++n) {
                    X509Certificate object92 = (X509Certificate)arrayList.get(n);
                    object6 = debug;
                    if (object6 != null) {
                        object8 = new StringBuilder();
                        ((StringBuilder)object8).append("current subject = ");
                        ((StringBuilder)object8).append(object92.getSubjectX500Principal());
                        ((Debug)object6).println(((StringBuilder)object8).toString());
                    }
                    if ((object8 = object92.getCriticalExtensionOIDs()) == null) {
                        object8 = Collections.emptySet();
                    }
                    object6 = object3.iterator();
                    while (object6.hasNext()) {
                        PKIXCertPathChecker pKIXCertPathChecker = (PKIXCertPathChecker)object6.next();
                        if (pKIXCertPathChecker.isForwardCheckingSupported()) continue;
                        if (n == 0) {
                            pKIXCertPathChecker.init(false);
                            if (pKIXCertPathChecker instanceof AlgorithmChecker) {
                                ((AlgorithmChecker)pKIXCertPathChecker).trySetTrustAnchor(forwardBuilder.trustAnchor);
                            }
                        }
                        try {
                            pKIXCertPathChecker.check(object92, (Collection<String>)object8);
                        }
                        catch (CertPathValidatorException certPathValidatorException) {
                            object7 = debug;
                            if (object7 != null) {
                                object5 = new StringBuilder();
                                ((StringBuilder)object5).append("SunCertPathBuilder.depthFirstSearchForward(): final verification failed: ");
                                ((StringBuilder)object5).append(certPathValidatorException);
                                ((Debug)object7).println(((StringBuilder)object5).toString());
                            }
                            if (this.buildParams.targetCertConstraints().match(object92) && certPathValidatorException.getReason() == CertPathValidatorException.BasicReason.REVOKED) {
                                throw certPathValidatorException;
                            }
                            vertex.setThrowable(certPathValidatorException);
                            continue block4;
                        }
                    }
                    for (PKIXCertPathChecker pKIXCertPathChecker : this.buildParams.certPathCheckers()) {
                        Set<String> set;
                        if (!pKIXCertPathChecker.isForwardCheckingSupported() || (set = pKIXCertPathChecker.getSupportedExtensions()) == null) continue;
                        object8.removeAll(set);
                    }
                    if (object8.isEmpty()) continue;
                    object8.remove(PKIXExtensions.BasicConstraints_Id.toString());
                    object8.remove(PKIXExtensions.NameConstraints_Id.toString());
                    object8.remove(PKIXExtensions.CertificatePolicies_Id.toString());
                    object8.remove(PKIXExtensions.PolicyMappings_Id.toString());
                    object8.remove(PKIXExtensions.PolicyConstraints_Id.toString());
                    object8.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
                    object8.remove(PKIXExtensions.SubjectAlternativeName_Id.toString());
                    object8.remove(PKIXExtensions.KeyUsage_Id.toString());
                    object8.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
                    if (object8.isEmpty()) continue;
                    throw new CertPathValidatorException("unrecognized critical extension(s)", null, null, -1, PKIXReason.UNRECOGNIZED_CRIT_EXT);
                }
                object = debug;
                if (object != null) {
                    ((Debug)object).println("SunCertPathBuilder.depthFirstSearchForward(): final verification succeeded - path completed!");
                }
                this.pathCompleted = true;
                if (forwardBuilder.trustAnchor.getTrustedCert() == null) {
                    forwardBuilder.addCertToPath(x509Certificate, linkedList);
                }
                this.trustAnchor = forwardBuilder.trustAnchor;
                if (object7 != null) {
                    this.finalPublicKey = ((BasicChecker)object7).getPublicKey();
                } else {
                    object = linkedList.isEmpty() ? forwardBuilder.trustAnchor.getTrustedCert() : (Certificate)linkedList.getLast();
                    this.finalPublicKey = ((Certificate)object).getPublicKey();
                }
                this.policyTreeResult = policyChecker.getPolicyTree();
                return;
            }
            forwardBuilder.addCertToPath(x509Certificate, linkedList);
            ((ForwardState)object5).updateState(x509Certificate);
            list.add(new LinkedList());
            vertex.setIndex(list.size() - 1);
            this.depthFirstSearchForward(x509Certificate.getIssuerX500Principal(), (ForwardState)object5, forwardBuilder, list, linkedList);
            if (this.pathCompleted) {
                return;
            }
            object3 = debug;
            if (object3 != null) {
                ((Debug)object3).println("SunCertPathBuilder.depthFirstSearchForward(): backtracking");
            }
            forwardBuilder.removeFinalCertFromPath(linkedList);
        }
    }

    @Override
    public CertPathBuilderResult engineBuild(CertPathParameters certPathParameters) throws CertPathBuilderException, InvalidAlgorithmParameterException {
        Debug debug = SunCertPathBuilder.debug;
        if (debug != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SunCertPathBuilder.engineBuild(");
            stringBuilder.append(certPathParameters);
            stringBuilder.append(")");
            debug.println(stringBuilder.toString());
        }
        this.buildParams = PKIX.checkBuilderParams(certPathParameters);
        return this.build();
    }

    @Override
    public CertPathChecker engineGetRevocationChecker() {
        return new RevocationChecker();
    }
}

