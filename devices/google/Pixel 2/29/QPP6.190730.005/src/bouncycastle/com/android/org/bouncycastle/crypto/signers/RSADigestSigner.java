/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoException;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.Signer;
import com.android.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.org.bouncycastle.crypto.engines.RSABlindedEngine;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.util.Hashtable;

public class RSADigestSigner
implements Signer {
    private static final Hashtable oidMap = new Hashtable();
    private final AlgorithmIdentifier algId;
    private final Digest digest;
    private boolean forSigning;
    private final AsymmetricBlockCipher rsaEngine = new PKCS1Encoding(new RSABlindedEngine());

    static {
        oidMap.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
        oidMap.put("SHA-224", NISTObjectIdentifiers.id_sha224);
        oidMap.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        oidMap.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        oidMap.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        oidMap.put("SHA-512/224", NISTObjectIdentifiers.id_sha512_224);
        oidMap.put("SHA-512/256", NISTObjectIdentifiers.id_sha512_256);
        oidMap.put("MD5", PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(Digest digest) {
        this(digest, (ASN1ObjectIdentifier)oidMap.get(digest.getAlgorithmName()));
    }

    public RSADigestSigner(Digest digest, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.digest = digest;
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    private byte[] derEncode(byte[] arrby) throws IOException {
        return new DigestInfo(this.algId, arrby).getEncoded("DER");
    }

    @Override
    public byte[] generateSignature() throws CryptoException, DataLengthException {
        if (this.forSigning) {
            byte[] arrby = new byte[this.digest.getDigestSize()];
            this.digest.doFinal(arrby, 0);
            try {
                arrby = this.derEncode(arrby);
                arrby = this.rsaEngine.processBlock(arrby, 0, arrby.length);
                return arrby;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unable to encode signature: ");
                stringBuilder.append(iOException.getMessage());
                throw new CryptoException(stringBuilder.toString(), iOException);
            }
        }
        throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
    }

    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.digest.getAlgorithmName());
        stringBuilder.append("withRSA");
        return stringBuilder.toString();
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        this.forSigning = bl;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter)((ParametersWithRandom)cipherParameters).getParameters() : (AsymmetricKeyParameter)cipherParameters;
        if (bl && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        }
        if (!bl && asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("verification requires public key");
        }
        this.reset();
        this.rsaEngine.init(bl, cipherParameters);
    }

    @Override
    public void reset() {
        this.digest.reset();
    }

    @Override
    public void update(byte by) {
        this.digest.update(by);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        this.digest.update(arrby, n, n2);
    }

    @Override
    public boolean verifySignature(byte[] arrby) {
        if (!this.forSigning) {
            byte[] arrby2 = new byte[this.digest.getDigestSize()];
            byte[] arrby3 = this.digest;
            boolean bl = false;
            arrby3.doFinal(arrby2, 0);
            try {
                arrby3 = this.rsaEngine.processBlock(arrby, 0, arrby.length);
                arrby = this.derEncode(arrby2);
            }
            catch (Exception exception) {
                return false;
            }
            if (arrby3.length == arrby.length) {
                return Arrays.constantTimeAreEqual(arrby3, arrby);
            }
            if (arrby3.length == arrby.length - 2) {
                int n;
                int n2 = arrby3.length - arrby2.length - 2;
                int n3 = arrby.length;
                int n4 = arrby2.length;
                arrby[1] = (byte)(arrby[1] - 2);
                arrby[3] = (byte)(arrby[3] - 2);
                int n5 = 0;
                for (n = 0; n < arrby2.length; ++n) {
                    n5 |= arrby3[n2 + n] ^ arrby[n3 - n4 - 2 + n];
                }
                for (n = 0; n < n2; ++n) {
                    n5 |= arrby3[n] ^ arrby[n];
                }
                if (n5 == 0) {
                    bl = true;
                }
                return bl;
            }
            Arrays.constantTimeAreEqual(arrby, arrby);
            return false;
        }
        throw new IllegalStateException("RSADigestSigner not initialised for verification");
    }
}

