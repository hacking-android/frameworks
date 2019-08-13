/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.certpath.PKIX;
import sun.security.util.Debug;
import sun.security.x509.X500Name;

class BasicChecker
extends PKIXCertPathChecker {
    private static final Debug debug = Debug.getInstance("certpath");
    private final X500Principal caName;
    private final Date date;
    private PublicKey prevPubKey;
    private X500Principal prevSubject;
    private final boolean sigOnly;
    private final String sigProvider;
    private final PublicKey trustedPubKey;

    BasicChecker(TrustAnchor trustAnchor, Date date, String string, boolean bl) {
        if (trustAnchor.getTrustedCert() != null) {
            this.trustedPubKey = trustAnchor.getTrustedCert().getPublicKey();
            this.caName = trustAnchor.getTrustedCert().getSubjectX500Principal();
        } else {
            this.trustedPubKey = trustAnchor.getCAPublicKey();
            this.caName = trustAnchor.getCA();
        }
        this.date = date;
        this.sigProvider = string;
        this.sigOnly = bl;
        this.prevPubKey = this.trustedPubKey;
    }

    static PublicKey makeInheritedParamsKey(PublicKey object, PublicKey object2) throws CertPathValidatorException {
        if (object instanceof DSAPublicKey && object2 instanceof DSAPublicKey) {
            if ((object2 = ((DSAPublicKey)object2).getParams()) != null) {
                try {
                    BigInteger bigInteger = ((DSAPublicKey)object).getY();
                    KeyFactory keyFactory = KeyFactory.getInstance("DSA");
                    object = new DSAPublicKeySpec(bigInteger, object2.getP(), object2.getQ(), object2.getG());
                    object = keyFactory.generatePublic((KeySpec)object);
                    return object;
                }
                catch (GeneralSecurityException generalSecurityException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unable to generate key with inherited parameters: ");
                    ((StringBuilder)object).append(generalSecurityException.getMessage());
                    throw new CertPathValidatorException(((StringBuilder)object).toString(), generalSecurityException);
                }
            }
            throw new CertPathValidatorException("Key parameters missing");
        }
        throw new CertPathValidatorException("Input key is not appropriate type for inheriting parameters");
    }

    private void updateState(X509Certificate x509Certificate) throws CertPathValidatorException {
        Serializable serializable;
        PublicKey publicKey = x509Certificate.getPublicKey();
        Debug debug = BasicChecker.debug;
        if (debug != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("BasicChecker.updateState issuer: ");
            ((StringBuilder)serializable).append(x509Certificate.getIssuerX500Principal().toString());
            ((StringBuilder)serializable).append("; subject: ");
            ((StringBuilder)serializable).append(x509Certificate.getSubjectX500Principal());
            ((StringBuilder)serializable).append("; serial#: ");
            ((StringBuilder)serializable).append(x509Certificate.getSerialNumber().toString());
            debug.println(((StringBuilder)serializable).toString());
        }
        serializable = publicKey;
        if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
            publicKey = BasicChecker.makeInheritedParamsKey(publicKey, this.prevPubKey);
            debug = BasicChecker.debug;
            serializable = publicKey;
            if (debug != null) {
                debug.println("BasicChecker.updateState Made key with inherited params");
                serializable = publicKey;
            }
        }
        this.prevPubKey = serializable;
        this.prevSubject = x509Certificate.getSubjectX500Principal();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyNameChaining(X509Certificate serializable) throws CertPathValidatorException {
        Object object;
        if (this.prevSubject == null) return;
        Debug debug = BasicChecker.debug;
        if (debug != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("---checking ");
            ((StringBuilder)object).append("subject/issuer name chaining");
            ((StringBuilder)object).append("...");
            debug.println(((StringBuilder)object).toString());
        }
        if (X500Name.asX500Name((X500Principal)(serializable = ((X509Certificate)serializable).getIssuerX500Principal())).isEmpty()) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("subject/issuer name chaining");
            ((StringBuilder)serializable).append(" check failed: empty/null issuer DN in certificate is invalid");
            throw new CertPathValidatorException(((StringBuilder)serializable).toString(), null, null, -1, PKIXReason.NAME_CHAINING);
        }
        if (((X500Principal)serializable).equals(this.prevSubject)) {
            object = BasicChecker.debug;
            if (object == null) return;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("subject/issuer name chaining");
            ((StringBuilder)serializable).append(" verified.");
            ((Debug)object).println(((StringBuilder)serializable).toString());
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("subject/issuer name chaining");
        ((StringBuilder)serializable).append(" check failed");
        throw new CertPathValidatorException(((StringBuilder)serializable).toString(), null, null, -1, PKIXReason.NAME_CHAINING);
    }

    private void verifySignature(X509Certificate object) throws CertPathValidatorException {
        block6 : {
            Object object2 = debug;
            if (object2 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("---checking ");
                stringBuilder.append("signature");
                stringBuilder.append("...");
                ((Debug)object2).println(stringBuilder.toString());
            }
            try {
                if (this.sigProvider != null) {
                    ((Certificate)object).verify(this.prevPubKey, this.sigProvider);
                } else {
                    ((Certificate)object).verify(this.prevPubKey);
                }
                object = debug;
                if (object == null) break block6;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("signature");
            }
            catch (GeneralSecurityException generalSecurityException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("signature");
                ((StringBuilder)object2).append(" check failed");
                throw new CertPathValidatorException(((StringBuilder)object2).toString(), generalSecurityException);
            }
            catch (SignatureException signatureException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("signature");
                ((StringBuilder)object).append(" check failed");
                throw new CertPathValidatorException(((StringBuilder)object).toString(), signatureException, null, -1, CertPathValidatorException.BasicReason.INVALID_SIGNATURE);
            }
            ((StringBuilder)object2).append(" verified.");
            ((Debug)object).println(((StringBuilder)object2).toString());
        }
        return;
    }

    private void verifyTimestamp(X509Certificate serializable) throws CertPathValidatorException {
        block4 : {
            Object object = debug;
            if (object != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("---checking ");
                stringBuilder.append("timestamp");
                stringBuilder.append(":");
                stringBuilder.append(this.date.toString());
                stringBuilder.append("...");
                ((Debug)object).println(stringBuilder.toString());
            }
            try {
                ((X509Certificate)serializable).checkValidity(this.date);
                object = debug;
                if (object == null) break block4;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("timestamp");
            }
            catch (CertificateNotYetValidException certificateNotYetValidException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("timestamp");
                ((StringBuilder)serializable).append(" check failed");
                throw new CertPathValidatorException(((StringBuilder)serializable).toString(), certificateNotYetValidException, null, -1, CertPathValidatorException.BasicReason.NOT_YET_VALID);
            }
            catch (CertificateExpiredException certificateExpiredException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("timestamp");
                ((StringBuilder)object).append(" check failed");
                throw new CertPathValidatorException(((StringBuilder)object).toString(), certificateExpiredException, null, -1, CertPathValidatorException.BasicReason.EXPIRED);
            }
            ((StringBuilder)serializable).append(" verified.");
            ((Debug)object).println(((StringBuilder)serializable).toString());
        }
        return;
    }

    @Override
    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        certificate = (X509Certificate)certificate;
        if (!this.sigOnly) {
            this.verifyTimestamp((X509Certificate)certificate);
            this.verifyNameChaining((X509Certificate)certificate);
        }
        this.verifySignature((X509Certificate)certificate);
        this.updateState((X509Certificate)certificate);
    }

    PublicKey getPublicKey() {
        return this.prevPubKey;
    }

    @Override
    public Set<String> getSupportedExtensions() {
        return null;
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            this.prevPubKey = this.trustedPubKey;
            if (!PKIX.isDSAPublicKeyWithoutParams(this.prevPubKey)) {
                this.prevSubject = this.caName;
                return;
            }
            throw new CertPathValidatorException("Key parameters missing");
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }
}

