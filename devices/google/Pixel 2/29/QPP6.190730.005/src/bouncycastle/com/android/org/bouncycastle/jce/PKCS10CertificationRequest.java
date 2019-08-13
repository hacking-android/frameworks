/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.CertificationRequest;
import com.android.org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PSSParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class PKCS10CertificationRequest
extends CertificationRequest {
    private static Hashtable algorithms = new Hashtable();
    private static Hashtable keyAlgorithms;
    private static Set noParams;
    private static Hashtable oids;
    private static Hashtable params;

    static {
        params = new Hashtable();
        keyAlgorithms = new Hashtable();
        oids = new Hashtable();
        noParams = new HashSet();
        algorithms.put("MD5WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("MD5WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("RSAWITHMD5", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("SHA1WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA1WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("RSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA1WITHDSA", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        algorithms.put("DSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
        algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
        algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
        algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
        algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
        algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
        algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
        oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        oids.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
        oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        keyAlgorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
        params.put("SHA1WITHRSAANDMGF1", PKCS10CertificationRequest.creatPSSParams(algorithmIdentifier, 20));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE);
        params.put("SHA224WITHRSAANDMGF1", PKCS10CertificationRequest.creatPSSParams(algorithmIdentifier, 28));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE);
        params.put("SHA256WITHRSAANDMGF1", PKCS10CertificationRequest.creatPSSParams(algorithmIdentifier, 32));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE);
        params.put("SHA384WITHRSAANDMGF1", PKCS10CertificationRequest.creatPSSParams(algorithmIdentifier, 48));
        algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE);
        params.put("SHA512WITHRSAANDMGF1", PKCS10CertificationRequest.creatPSSParams(algorithmIdentifier, 64));
    }

    public PKCS10CertificationRequest(ASN1Sequence aSN1Sequence) {
        super(aSN1Sequence);
    }

    public PKCS10CertificationRequest(String string, X509Name x509Name, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        this(string, x509Name, publicKey, aSN1Set, privateKey, "BC");
    }

    public PKCS10CertificationRequest(String object, X509Name x509Name, PublicKey object2, ASN1Set aSN1Set, PrivateKey privateKey, String string) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        String string2 = Strings.toUpperCase((String)object);
        ASN1Primitive aSN1Primitive = aSN1ObjectIdentifier = (ASN1ObjectIdentifier)algorithms.get(string2);
        if (aSN1ObjectIdentifier == null) {
            try {
                aSN1Primitive = new ASN1ObjectIdentifier(string2);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Unknown signature type requested");
            }
        }
        if (x509Name != null) {
            if (object2 != null) {
                this.sigAlgId = noParams.contains(aSN1Primitive) ? new AlgorithmIdentifier((ASN1ObjectIdentifier)aSN1Primitive) : (params.containsKey(string2) ? new AlgorithmIdentifier((ASN1ObjectIdentifier)aSN1Primitive, (ASN1Encodable)params.get(string2)) : new AlgorithmIdentifier((ASN1ObjectIdentifier)aSN1Primitive, DERNull.INSTANCE));
                try {
                    aSN1Primitive = (ASN1Sequence)ASN1Primitive.fromByteArray(object2.getEncoded());
                    this.reqInfo = object2 = new CertificationRequestInfo(x509Name, SubjectPublicKeyInfo.getInstance(aSN1Primitive), aSN1Set);
                    object = string == null ? Signature.getInstance((String)object) : Signature.getInstance((String)object, string);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("can't encode public key");
                }
                ((Signature)object).initSign(privateKey);
                try {
                    ((Signature)object).update(this.reqInfo.getEncoded("DER"));
                    this.sigBits = new DERBitString(((Signature)object).sign());
                    return;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("exception encoding TBS cert request - ");
                    ((StringBuilder)object).append(exception);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            throw new IllegalArgumentException("public key must not be null");
        }
        throw new IllegalArgumentException("subject must not be null");
    }

    public PKCS10CertificationRequest(String string, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        this(string, PKCS10CertificationRequest.convertName(x500Principal), publicKey, aSN1Set, privateKey, "BC");
    }

    public PKCS10CertificationRequest(String string, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey, String string2) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        this(string, PKCS10CertificationRequest.convertName(x500Principal), publicKey, aSN1Set, privateKey, string2);
    }

    public PKCS10CertificationRequest(byte[] arrby) {
        super(PKCS10CertificationRequest.toDERSequence(arrby));
    }

    private static X509Name convertName(X500Principal principal) {
        try {
            principal = new X509Principal(principal.getEncoded());
            return principal;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("can't convert name");
        }
    }

    private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier algorithmIdentifier, int n) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer(n), new ASN1Integer(1L));
    }

    private static String getDigestAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (PKCSObjectIdentifiers.md5.equals(aSN1ObjectIdentifier)) {
            return "MD5";
        }
        if (OIWObjectIdentifiers.idSHA1.equals(aSN1ObjectIdentifier)) {
            return "SHA1";
        }
        if (NISTObjectIdentifiers.id_sha224.equals(aSN1ObjectIdentifier)) {
            return "SHA224";
        }
        if (NISTObjectIdentifiers.id_sha256.equals(aSN1ObjectIdentifier)) {
            return "SHA256";
        }
        if (NISTObjectIdentifiers.id_sha384.equals(aSN1ObjectIdentifier)) {
            return "SHA384";
        }
        if (NISTObjectIdentifiers.id_sha512.equals(aSN1ObjectIdentifier)) {
            return "SHA512";
        }
        return aSN1ObjectIdentifier.getId();
    }

    static String getSignatureName(AlgorithmIdentifier object) {
        ASN1Encodable aSN1Encodable = ((AlgorithmIdentifier)object).getParameters();
        if (aSN1Encodable != null && !DERNull.INSTANCE.equals(aSN1Encodable) && ((AlgorithmIdentifier)object).getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            aSN1Encodable = RSASSAPSSparams.getInstance(aSN1Encodable);
            object = new StringBuilder();
            ((StringBuilder)object).append(PKCS10CertificationRequest.getDigestAlgName(((RSASSAPSSparams)aSN1Encodable).getHashAlgorithm().getAlgorithm()));
            ((StringBuilder)object).append("withRSAandMGF1");
            return ((StringBuilder)object).toString();
        }
        return ((AlgorithmIdentifier)object).getAlgorithm().getId();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setSignatureParameters(Signature object, ASN1Encodable aSN1Encodable) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (aSN1Encodable == null || DERNull.INSTANCE.equals(aSN1Encodable)) return;
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(((Signature)object).getAlgorithm(), ((Signature)object).getProvider());
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded("DER"));
            if (!((Signature)object).getAlgorithm().endsWith("MGF1")) return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("IOException decoding parameters: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new SignatureException(((StringBuilder)object).toString());
        }
        try {
            ((Signature)object).setParameter(algorithmParameters.getParameterSpec(PSSParameterSpec.class));
            return;
        }
        catch (GeneralSecurityException generalSecurityException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception extracting parameters: ");
            ((StringBuilder)object).append(generalSecurityException.getMessage());
            throw new SignatureException(((StringBuilder)object).toString());
        }
    }

    private static ASN1Sequence toDERSequence(byte[] object) {
        try {
            ASN1InputStream aSN1InputStream = new ASN1InputStream((byte[])object);
            object = (ASN1Sequence)aSN1InputStream.readObject();
            return object;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("badly encoded request");
        }
    }

    @Override
    public byte[] getEncoded() {
        try {
            byte[] arrby = this.getEncoded("DER");
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException.toString());
        }
    }

    public PublicKey getPublicKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        return this.getPublicKey("BC");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public PublicKey getPublicKey(String string) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Object object = this.reqInfo.getSubjectPublicKeyInfo();
        Object object2 = new DERBitString((ASN1Encodable)object);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(((ASN1BitString)object2).getOctets());
        object = ((SubjectPublicKeyInfo)object).getAlgorithm();
        if (string != null) return KeyFactory.getInstance(((AlgorithmIdentifier)object).getAlgorithm().getId(), string).generatePublic(x509EncodedKeySpec);
        try {
            return KeyFactory.getInstance(((AlgorithmIdentifier)object).getAlgorithm().getId()).generatePublic(x509EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            if (keyAlgorithms.get(((AlgorithmIdentifier)object).getAlgorithm()) == null) throw noSuchAlgorithmException;
            object = (String)keyAlgorithms.get(((AlgorithmIdentifier)object).getAlgorithm());
            if (string != null) return KeyFactory.getInstance((String)object, string).generatePublic(x509EncodedKeySpec);
            try {
                return KeyFactory.getInstance((String)object).generatePublic(x509EncodedKeySpec);
            }
            catch (IOException iOException) {
                throw new InvalidKeyException("error decoding public key");
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
                throw new InvalidKeyException("error decoding public key");
            }
        }
    }

    public boolean verify() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        return this.verify("BC");
    }

    public boolean verify(String string) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        return this.verify(this.getPublicKey(string), string);
    }

    public boolean verify(PublicKey publicKey, String object) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        NoSuchAlgorithmException noSuchAlgorithmException2;
        block6 : {
            if (object == null) {
                Signature signature = Signature.getInstance(PKCS10CertificationRequest.getSignatureName(this.sigAlgId));
                object = signature;
            }
            try {
                Signature signature = Signature.getInstance(PKCS10CertificationRequest.getSignatureName(this.sigAlgId), (String)object);
                object = signature;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
                if (oids.get(this.sigAlgId.getAlgorithm()) == null) break block6;
                String string = (String)oids.get(this.sigAlgId.getAlgorithm());
                object = object == null ? Signature.getInstance(string) : Signature.getInstance(string, (String)object);
            }
            this.setSignatureParameters((Signature)object, this.sigAlgId.getParameters());
            ((Signature)object).initVerify(publicKey);
            try {
                ((Signature)object).update(this.reqInfo.getEncoded("DER"));
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("exception encoding TBS cert request - ");
                ((StringBuilder)object).append(exception);
                throw new SignatureException(((StringBuilder)object).toString());
            }
            return ((Signature)object).verify(this.sigBits.getOctets());
        }
        throw noSuchAlgorithmException2;
    }
}

