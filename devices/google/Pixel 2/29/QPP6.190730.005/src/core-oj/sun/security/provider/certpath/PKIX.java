/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;

class PKIX {
    private static final Debug debug = Debug.getInstance("certpath");

    private PKIX() {
    }

    static BuilderParams checkBuilderParams(CertPathParameters certPathParameters) throws InvalidAlgorithmParameterException {
        if (certPathParameters instanceof PKIXBuilderParameters) {
            return new BuilderParams((PKIXBuilderParameters)certPathParameters);
        }
        throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXBuilderParameters");
    }

    static ValidatorParams checkParams(CertPath certPath, CertPathParameters certPathParameters) throws InvalidAlgorithmParameterException {
        if (certPathParameters instanceof PKIXParameters) {
            return new ValidatorParams(certPath, (PKIXParameters)certPathParameters);
        }
        throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXParameters");
    }

    static boolean isDSAPublicKeyWithoutParams(PublicKey publicKey) {
        boolean bl = publicKey instanceof DSAPublicKey && ((DSAPublicKey)publicKey).getParams() == null;
        return bl;
    }

    static class BuilderParams
    extends ValidatorParams {
        private PKIXBuilderParameters params;
        private List<CertStore> stores;
        private X500Principal targetSubject;

        BuilderParams(PKIXBuilderParameters pKIXBuilderParameters) throws InvalidAlgorithmParameterException {
            super(pKIXBuilderParameters);
            this.checkParams(pKIXBuilderParameters);
        }

        private void checkParams(PKIXBuilderParameters pKIXBuilderParameters) throws InvalidAlgorithmParameterException {
            if (this.targetCertConstraints() instanceof X509CertSelector) {
                this.params = pKIXBuilderParameters;
                this.targetSubject = BuilderParams.getTargetSubject(this.certStores(), (X509CertSelector)this.targetCertConstraints());
                return;
            }
            throw new InvalidAlgorithmParameterException("the targetCertConstraints parameter must be an X509CertSelector");
        }

        private static X500Principal getTargetSubject(List<CertStore> object, X509CertSelector x509CertSelector) throws InvalidAlgorithmParameterException {
            Object object2 = x509CertSelector.getSubject();
            if (object2 != null) {
                return object2;
            }
            Serializable serializable = x509CertSelector.getCertificate();
            if (serializable != null) {
                object2 = ((X509Certificate)serializable).getSubjectX500Principal();
            }
            if (object2 != null) {
                return object2;
            }
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (CertStore)object.next();
                try {
                    if ((object2 = ((CertStore)object2).getCertificates(x509CertSelector)).isEmpty()) continue;
                    object2 = ((X509Certificate)object2.iterator().next()).getSubjectX500Principal();
                    return object2;
                }
                catch (CertStoreException certStoreException) {
                    if (debug == null) continue;
                    object2 = debug;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("BuilderParams.getTargetSubjectDN: non-fatal exception retrieving certs: ");
                    ((StringBuilder)serializable).append(certStoreException);
                    ((Debug)object2).println(((StringBuilder)serializable).toString());
                    certStoreException.printStackTrace();
                }
            }
            throw new InvalidAlgorithmParameterException("Could not determine unique target subject");
        }

        @Override
        List<CertStore> certStores() {
            if (this.stores == null) {
                this.stores = new ArrayList<CertStore>(this.params.getCertStores());
                Collections.sort(this.stores, new CertStoreComparator());
            }
            return this.stores;
        }

        int maxPathLength() {
            return this.params.getMaxPathLength();
        }

        PKIXBuilderParameters params() {
            return this.params;
        }

        X500Principal targetSubject() {
            return this.targetSubject;
        }
    }

    private static class CertStoreComparator
    implements Comparator<CertStore> {
        private CertStoreComparator() {
        }

        @Override
        public int compare(CertStore certStore, CertStore certStore2) {
            if (!certStore.getType().equals("Collection") && !(certStore.getCertStoreParameters() instanceof CollectionCertStoreParameters)) {
                return 1;
            }
            return -1;
        }
    }

    static class CertStoreTypeException
    extends CertStoreException {
        private static final long serialVersionUID = 7463352639238322556L;
        private final String type;

        CertStoreTypeException(String string, CertStoreException certStoreException) {
            super(certStoreException.getMessage(), certStoreException.getCause());
            this.type = string;
        }

        String getType() {
            return this.type;
        }
    }

    static class ValidatorParams {
        private Set<TrustAnchor> anchors;
        private CertPath certPath;
        private List<X509Certificate> certs;
        private List<PKIXCertPathChecker> checkers;
        private CertSelector constraints;
        private Date date;
        private boolean gotConstraints;
        private boolean gotDate;
        private final PKIXParameters params;
        private Set<String> policies;
        private List<CertStore> stores;

        ValidatorParams(CertPath certPath, PKIXParameters pKIXParameters) throws InvalidAlgorithmParameterException {
            this(pKIXParameters);
            if (!certPath.getType().equals("X.509") && !certPath.getType().equals("X509")) {
                throw new InvalidAlgorithmParameterException("inappropriate CertPath type specified, must be X.509 or X509");
            }
            this.certPath = certPath;
        }

        ValidatorParams(PKIXParameters pKIXParameters) throws InvalidAlgorithmParameterException {
            this.anchors = pKIXParameters.getTrustAnchors();
            Iterator<TrustAnchor> iterator = this.anchors.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getNameConstraints() == null) continue;
                throw new InvalidAlgorithmParameterException("name constraints in trust anchor not supported");
            }
            this.params = pKIXParameters;
        }

        boolean anyPolicyInhibited() {
            return this.params.isAnyPolicyInhibited();
        }

        CertPath certPath() {
            return this.certPath;
        }

        List<PKIXCertPathChecker> certPathCheckers() {
            if (this.checkers == null) {
                this.checkers = this.params.getCertPathCheckers();
            }
            return this.checkers;
        }

        List<CertStore> certStores() {
            if (this.stores == null) {
                this.stores = this.params.getCertStores();
            }
            return this.stores;
        }

        List<X509Certificate> certificates() {
            if (this.certs == null) {
                Serializable serializable = this.certPath;
                if (serializable == null) {
                    this.certs = Collections.emptyList();
                } else {
                    serializable = new ArrayList<Certificate>(serializable.getCertificates());
                    Collections.reverse(serializable);
                    this.certs = serializable;
                }
            }
            return this.certs;
        }

        Date date() {
            if (!this.gotDate) {
                this.date = this.params.getDate();
                if (this.date == null) {
                    this.date = new Date();
                }
                this.gotDate = true;
            }
            return this.date;
        }

        boolean explicitPolicyRequired() {
            return this.params.isExplicitPolicyRequired();
        }

        PKIXParameters getPKIXParameters() {
            return this.params;
        }

        Set<String> initialPolicies() {
            if (this.policies == null) {
                this.policies = this.params.getInitialPolicies();
            }
            return this.policies;
        }

        boolean policyMappingInhibited() {
            return this.params.isPolicyMappingInhibited();
        }

        boolean policyQualifiersRejected() {
            return this.params.getPolicyQualifiersRejected();
        }

        boolean revocationEnabled() {
            return this.params.isRevocationEnabled();
        }

        void setCertPath(CertPath certPath) {
            this.certPath = certPath;
        }

        String sigProvider() {
            return this.params.getSigProvider();
        }

        CertSelector targetCertConstraints() {
            if (!this.gotConstraints) {
                this.constraints = this.params.getTargetCertConstraints();
                this.gotConstraints = true;
            }
            return this.constraints;
        }

        Set<TrustAnchor> trustAnchors() {
            return this.anchors;
        }
    }

}

