/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.AlgorithmConstraints;
import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXReason;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import sun.security.provider.certpath.PKIX;
import sun.security.util.AnchorCertificates;
import sun.security.util.CertConstraintParameters;
import sun.security.util.Debug;
import sun.security.util.DisabledAlgorithmConstraints;
import sun.security.util.KeyUtil;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;

public final class AlgorithmChecker
extends PKIXCertPathChecker {
    private static final Set<CryptoPrimitive> KU_PRIMITIVE_SET;
    private static final Set<CryptoPrimitive> SIGNATURE_PRIMITIVE_SET;
    private static final DisabledAlgorithmConstraints certPathDefaultConstraints;
    private static final Debug debug;
    private static final boolean publicCALimits;
    private final AlgorithmConstraints constraints;
    private PublicKey prevPubKey;
    private boolean trustedMatch = false;
    private final PublicKey trustedPubKey;

    static {
        debug = Debug.getInstance("certpath");
        SIGNATURE_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE));
        KU_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE, CryptoPrimitive.KEY_ENCAPSULATION, CryptoPrimitive.PUBLIC_KEY_ENCRYPTION, CryptoPrimitive.KEY_AGREEMENT));
        certPathDefaultConstraints = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        publicCALimits = certPathDefaultConstraints.checkProperty("jdkCA");
    }

    public AlgorithmChecker(AlgorithmConstraints algorithmConstraints) {
        this.prevPubKey = null;
        this.trustedPubKey = null;
        this.constraints = algorithmConstraints;
    }

    public AlgorithmChecker(TrustAnchor trustAnchor) {
        this(trustAnchor, certPathDefaultConstraints);
    }

    public AlgorithmChecker(TrustAnchor object, AlgorithmConstraints algorithmConstraints) {
        if (object != null) {
            if (((TrustAnchor)object).getTrustedCert() != null) {
                this.trustedPubKey = ((TrustAnchor)object).getTrustedCert().getPublicKey();
                this.trustedMatch = AlgorithmChecker.checkFingerprint(((TrustAnchor)object).getTrustedCert());
                if (this.trustedMatch && (object = debug) != null) {
                    ((Debug)object).println("trustedMatch = true");
                }
            } else {
                this.trustedPubKey = ((TrustAnchor)object).getCAPublicKey();
            }
            this.prevPubKey = this.trustedPubKey;
            this.constraints = algorithmConstraints;
            return;
        }
        throw new IllegalArgumentException("The trust anchor cannot be null");
    }

    static void check(PublicKey publicKey, X509CRL x509CRL) throws CertPathValidatorException {
        try {
            x509CRL = X509CRLImpl.toImpl(x509CRL);
        }
        catch (CRLException cRLException) {
            throw new CertPathValidatorException(cRLException);
        }
        AlgorithmChecker.check(publicKey, ((X509CRLImpl)x509CRL).getSigAlgId());
    }

    static void check(PublicKey serializable, AlgorithmId object) throws CertPathValidatorException {
        String string = ((AlgorithmId)object).getName();
        if (certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, string, (Key)serializable, (AlgorithmParameters)(object = ((AlgorithmId)object).getParameters()))) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Algorithm constraints check failed on signature algorithm: ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(" is disabled");
        throw new CertPathValidatorException(((StringBuilder)serializable).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
    }

    private static boolean checkFingerprint(X509Certificate x509Certificate) {
        if (!publicCALimits) {
            return false;
        }
        Debug debug = AlgorithmChecker.debug;
        if (debug != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AlgorithmChecker.contains: ");
            stringBuilder.append(x509Certificate.getSigAlgName());
            debug.println(stringBuilder.toString());
        }
        return AnchorCertificates.contains(x509Certificate);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void check(Certificate object, Collection<String> object2) throws CertPathValidatorException {
        AlgorithmParameters algorithmParameters;
        if (!(object instanceof X509Certificate) || this.constraints == null) return;
        boolean[] arrbl = ((X509Certificate)object).getKeyUsage();
        if (arrbl != null && arrbl.length < 9) {
            throw new CertPathValidatorException("incorrect KeyUsage extension", null, null, -1, PKIXReason.INVALID_KEY_USAGE);
        }
        object2 = KU_PRIMITIVE_SET;
        if (arrbl != null) {
            object2 = EnumSet.noneOf(CryptoPrimitive.class);
            if (arrbl[0] || arrbl[1] || arrbl[5] || arrbl[6]) {
                object2.add((CryptoPrimitive)CryptoPrimitive.SIGNATURE);
            }
            if (arrbl[2]) {
                object2.add((CryptoPrimitive)CryptoPrimitive.KEY_ENCAPSULATION);
            }
            if (arrbl[3]) {
                object2.add((CryptoPrimitive)CryptoPrimitive.PUBLIC_KEY_ENCRYPTION);
            }
            if (arrbl[4]) {
                object2.add((CryptoPrimitive)CryptoPrimitive.KEY_AGREEMENT);
            }
            if (object2.isEmpty()) {
                throw new CertPathValidatorException("incorrect KeyUsage extension bits", null, null, -1, PKIXReason.INVALID_KEY_USAGE);
            }
        }
        PublicKey publicKey = ((Certificate)object).getPublicKey();
        Object object3 = this.constraints;
        if (object3 instanceof DisabledAlgorithmConstraints) {
            ((DisabledAlgorithmConstraints)object3).permits((Set<CryptoPrimitive>)object2, new CertConstraintParameters((X509Certificate)object, this.trustedMatch));
            if (this.prevPubKey == null) {
                this.prevPubKey = publicKey;
                return;
            }
        }
        try {
            object = X509CertImpl.toImpl((X509Certificate)object);
            object3 = (AlgorithmId)((X509CertImpl)object).get("x509.algorithm");
            algorithmParameters = ((AlgorithmId)object3).getParameters();
            object3 = ((X509CertImpl)object).getSigAlgName();
            object = this.constraints;
        }
        catch (CertificateException certificateException) {
            throw new CertPathValidatorException(certificateException);
        }
        if (!(object instanceof DisabledAlgorithmConstraints)) {
            if (object.permits(SIGNATURE_PRIMITIVE_SET, (String)object3, algorithmParameters)) {
                if (!this.constraints.permits((Set<CryptoPrimitive>)object2, publicKey)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Algorithm constraints check failed on keysize: ");
                    ((StringBuilder)object).append(KeyUtil.getKeySize(publicKey));
                    throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Algorithm constraints check failed on signature algorithm: ");
                ((StringBuilder)object).append((String)object3);
                throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
            }
        }
        object2 = this.prevPubKey;
        object = publicKey;
        if (object2 != null) {
            if (this.constraints.permits(SIGNATURE_PRIMITIVE_SET, (String)object3, (Key)object2, algorithmParameters)) {
                object = publicKey;
                if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
                    object = this.prevPubKey;
                    if (!(object instanceof DSAPublicKey)) throw new CertPathValidatorException("Input key is not of a appropriate type for inheriting parameters");
                    if ((object = ((DSAPublicKey)object).getParams()) == null) throw new CertPathValidatorException("Key parameters missing from public key.");
                    try {
                        BigInteger bigInteger = ((DSAPublicKey)publicKey).getY();
                        object3 = KeyFactory.getInstance("DSA");
                        object2 = new DSAPublicKeySpec(bigInteger, object.getP(), object.getQ(), object.getG());
                        object = ((KeyFactory)object3).generatePublic((KeySpec)object2);
                    }
                    catch (GeneralSecurityException generalSecurityException) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unable to generate key with inherited parameters: ");
                        ((StringBuilder)object2).append(generalSecurityException.getMessage());
                        throw new CertPathValidatorException(((StringBuilder)object2).toString(), generalSecurityException);
                    }
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Algorithm constraints check failed on signature algorithm: ");
                ((StringBuilder)object).append((String)object3);
                throw new CertPathValidatorException(((StringBuilder)object).toString(), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
            }
        }
        this.prevPubKey = object;
    }

    @Override
    public Set<String> getSupportedExtensions() {
        return null;
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (!bl) {
            PublicKey publicKey = this.trustedPubKey;
            this.prevPubKey = publicKey != null ? publicKey : null;
            return;
        }
        throw new CertPathValidatorException("forward checking not supported");
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }

    void trySetTrustAnchor(TrustAnchor object) {
        if (this.prevPubKey == null) {
            if (object != null) {
                if (((TrustAnchor)object).getTrustedCert() != null) {
                    this.prevPubKey = ((TrustAnchor)object).getTrustedCert().getPublicKey();
                    this.trustedMatch = AlgorithmChecker.checkFingerprint(((TrustAnchor)object).getTrustedCert());
                    if (this.trustedMatch && (object = debug) != null) {
                        ((Debug)object).println("trustedMatch = true");
                    }
                } else {
                    this.prevPubKey = ((TrustAnchor)object).getCAPublicKey();
                }
            } else {
                throw new IllegalArgumentException("The trust anchor cannot be null");
            }
        }
    }
}

