/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DSAExt;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.digests.NullDigest;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.signers.DSAEncoding;
import com.android.org.bouncycastle.crypto.signers.StandardDSAEncoding;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;

public class DSASigner
extends SignatureSpi
implements PKCSObjectIdentifiers,
X509ObjectIdentifiers {
    private Digest digest;
    private DSAEncoding encoding = StandardDSAEncoding.INSTANCE;
    private SecureRandom random;
    private DSAExt signer;

    protected DSASigner(Digest digest, DSAExt dSAExt) {
        this.digest = digest;
        this.signer = dSAExt;
    }

    protected void checkKey(DSAParameters dSAParameters) throws InvalidKeyException {
        int n = dSAParameters.getP().bitLength();
        int n2 = dSAParameters.getQ().bitLength();
        int n3 = this.digest.getDigestSize();
        if (n >= 1024 && n <= 3072 && n % 1024 == 0) {
            if (n == 1024 && n2 != 160) {
                throw new InvalidKeyException("valueN must be 160 for valueL = 1024");
            }
            if (n == 2048 && n2 != 224 && n2 != 256) {
                throw new InvalidKeyException("valueN must be 224 or 256 for valueL = 2048");
            }
            if (n == 3072 && n2 != 256) {
                throw new InvalidKeyException("valueN must be 256 for valueL = 3072");
            }
            if (!(this.digest instanceof NullDigest) && n2 > n3 * 8) {
                throw new InvalidKeyException("Key is too strong for this signature algorithm");
            }
            return;
        }
        throw new InvalidKeyException("valueL values must be between 1024 and 3072 and a multiple of 1024");
    }

    @Override
    protected Object engineGetParameter(String string) {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    @Override
    protected void engineInitSign(PrivateKey object) throws InvalidKeyException {
        AsymmetricKeyParameter asymmetricKeyParameter = DSAUtil.generatePrivateKeyParameter((PrivateKey)object);
        this.checkKey(((DSAKeyParameters)asymmetricKeyParameter).getParameters());
        SecureRandom secureRandom = this.random;
        object = asymmetricKeyParameter;
        if (secureRandom != null) {
            object = new ParametersWithRandom(asymmetricKeyParameter, secureRandom);
        }
        this.digest.reset();
        this.signer.init(true, (CipherParameters)object);
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.random = secureRandom;
        this.engineInitSign(privateKey);
    }

    @Override
    protected void engineInitVerify(PublicKey object) throws InvalidKeyException {
        object = DSAUtil.generatePublicKeyParameter((PublicKey)object);
        this.digest.reset();
        this.signer.init(false, (CipherParameters)object);
    }

    @Override
    protected void engineSetParameter(String string, Object object) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        Object[] arrobject = new byte[this.digest.getDigestSize()];
        this.digest.doFinal((byte[])arrobject, 0);
        try {
            arrobject = this.signer.generateSignature((byte[])arrobject);
            arrobject = this.encoding.encode(this.signer.getOrder(), (BigInteger)arrobject[0], (BigInteger)arrobject[1]);
            return arrobject;
        }
        catch (Exception exception) {
            throw new SignatureException(exception.toString());
        }
    }

    @Override
    protected void engineUpdate(byte by) throws SignatureException {
        this.digest.update(by);
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) throws SignatureException {
        this.digest.update(arrby, n, n2);
    }

    @Override
    protected boolean engineVerify(byte[] arrobject) throws SignatureException {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(arrby, 0);
        try {
            arrobject = this.encoding.decode(this.signer.getOrder(), (byte[])arrobject);
        }
        catch (Exception exception) {
            throw new SignatureException("error decoding signature bytes.");
        }
        return this.signer.verifySignature(arrby, (BigInteger)arrobject[0], (BigInteger)arrobject[1]);
    }

    public static class dsa224
    extends DSASigner {
        public dsa224() {
            super(AndroidDigestFactory.getSHA224(), new com.android.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class dsa256
    extends DSASigner {
        public dsa256() {
            super(AndroidDigestFactory.getSHA256(), new com.android.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class noneDSA
    extends DSASigner {
        public noneDSA() {
            super(new NullDigest(), new com.android.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class stdDSA
    extends DSASigner {
        public stdDSA() {
            super(AndroidDigestFactory.getSHA1(), new com.android.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

}

