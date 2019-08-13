/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.org.bouncycastle.crypto.engines.RSABlindedEngine;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;

public class DigestSignatureSpi
extends SignatureSpi {
    private AlgorithmIdentifier algId;
    private AsymmetricBlockCipher cipher;
    private Digest digest;

    protected DigestSignatureSpi(ASN1ObjectIdentifier aSN1ObjectIdentifier, Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    protected DigestSignatureSpi(Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = null;
    }

    private byte[] derEncode(byte[] arrby) throws IOException {
        AlgorithmIdentifier algorithmIdentifier = this.algId;
        if (algorithmIdentifier == null) {
            return arrby;
        }
        return new DigestInfo(algorithmIdentifier, arrby).getEncoded("DER");
    }

    private String getType(Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName();
    }

    @Override
    protected Object engineGetParameter(String string) {
        return null;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override
    protected void engineInitSign(PrivateKey object) throws InvalidKeyException {
        if (object instanceof RSAPrivateKey) {
            object = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)object);
            this.digest.reset();
            this.cipher.init(true, (CipherParameters)object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Supplied key (");
        stringBuilder.append(this.getType(object));
        stringBuilder.append(") is not a RSAPrivateKey instance");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    protected void engineInitVerify(PublicKey object) throws InvalidKeyException {
        if (object instanceof RSAPublicKey) {
            object = RSAUtil.generatePublicKeyParameter((RSAPublicKey)object);
            this.digest.reset();
            this.cipher.init(false, (CipherParameters)object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Supplied key (");
        stringBuilder.append(this.getType(object));
        stringBuilder.append(") is not a RSAPublicKey instance");
        throw new InvalidKeyException(stringBuilder.toString());
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
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(arrby, 0);
        try {
            arrby = this.derEncode(arrby);
            arrby = this.cipher.processBlock(arrby, 0, arrby.length);
            return arrby;
        }
        catch (Exception exception) {
            throw new SignatureException(exception.toString());
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new SignatureException("key too small for signature type");
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
    protected boolean engineVerify(byte[] arrby) throws SignatureException {
        byte[] arrby2 = new byte[this.digest.getDigestSize()];
        Digest digest = this.digest;
        boolean bl = false;
        digest.doFinal(arrby2, 0);
        try {
            arrby = this.cipher.processBlock(arrby, 0, arrby.length);
            arrby2 = this.derEncode(arrby2);
        }
        catch (Exception exception) {
            return false;
        }
        if (arrby.length == arrby2.length) {
            return Arrays.constantTimeAreEqual(arrby, arrby2);
        }
        if (arrby.length == arrby2.length - 2) {
            int n;
            arrby2[1] = (byte)(arrby2[1] - 2);
            arrby2[3] = (byte)(arrby2[3] - 2);
            int n2 = arrby2[3] + 4;
            int n3 = n2 + 2;
            int n4 = 0;
            for (n = 0; n < arrby2.length - n3; ++n) {
                n4 |= arrby[n2 + n] ^ arrby2[n3 + n];
            }
            for (n = 0; n < n2; ++n) {
                n4 |= arrby[n] ^ arrby2[n];
            }
            if (n4 == 0) {
                bl = true;
            }
            return bl;
        }
        Arrays.constantTimeAreEqual(arrby2, arrby2);
        return false;
    }

    public static class MD5
    extends DigestSignatureSpi {
        public MD5() {
            super(PKCSObjectIdentifiers.md5, AndroidDigestFactory.getMD5(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA1
    extends DigestSignatureSpi {
        public SHA1() {
            super(OIWObjectIdentifiers.idSHA1, AndroidDigestFactory.getSHA1(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA224
    extends DigestSignatureSpi {
        public SHA224() {
            super(NISTObjectIdentifiers.id_sha224, AndroidDigestFactory.getSHA224(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA256
    extends DigestSignatureSpi {
        public SHA256() {
            super(NISTObjectIdentifiers.id_sha256, AndroidDigestFactory.getSHA256(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA384
    extends DigestSignatureSpi {
        public SHA384() {
            super(NISTObjectIdentifiers.id_sha384, AndroidDigestFactory.getSHA384(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512
    extends DigestSignatureSpi {
        public SHA512() {
            super(NISTObjectIdentifiers.id_sha512, AndroidDigestFactory.getSHA512(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

}

