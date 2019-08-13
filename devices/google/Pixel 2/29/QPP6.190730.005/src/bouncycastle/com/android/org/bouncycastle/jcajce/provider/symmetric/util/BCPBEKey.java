/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import java.security.spec.KeySpec;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;

public class BCPBEKey
implements PBEKey {
    String algorithm;
    int digest;
    int ivSize;
    int keySize;
    ASN1ObjectIdentifier oid;
    CipherParameters param;
    PBEKeySpec pbeKeySpec;
    boolean tryWrong = false;
    int type;

    public BCPBEKey(String string, ASN1ObjectIdentifier aSN1ObjectIdentifier, int n, int n2, int n3, int n4, PBEKeySpec pBEKeySpec, CipherParameters cipherParameters) {
        this.algorithm = string;
        this.oid = aSN1ObjectIdentifier;
        this.type = n;
        this.digest = n2;
        this.keySize = n3;
        this.ivSize = n4;
        this.pbeKeySpec = pBEKeySpec;
        this.param = cipherParameters;
    }

    public BCPBEKey(String string, KeySpec keySpec, CipherParameters cipherParameters) {
        this.algorithm = string;
        this.param = cipherParameters;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    int getDigest() {
        return this.digest;
    }

    @Override
    public byte[] getEncoded() {
        CipherParameters cipherParameters = this.param;
        if (cipherParameters != null) {
            cipherParameters = cipherParameters instanceof ParametersWithIV ? (KeyParameter)((ParametersWithIV)cipherParameters).getParameters() : (KeyParameter)cipherParameters;
            return ((KeyParameter)cipherParameters).getKey();
        }
        int n = this.type;
        if (n == 2) {
            return PBEParametersGenerator.PKCS12PasswordToBytes(this.pbeKeySpec.getPassword());
        }
        if (n == 5) {
            return PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.pbeKeySpec.getPassword());
        }
        return PBEParametersGenerator.PKCS5PasswordToBytes(this.pbeKeySpec.getPassword());
    }

    @Override
    public String getFormat() {
        return "RAW";
    }

    @Override
    public int getIterationCount() {
        return this.pbeKeySpec.getIterationCount();
    }

    public int getIvSize() {
        return this.ivSize;
    }

    int getKeySize() {
        return this.keySize;
    }

    public ASN1ObjectIdentifier getOID() {
        return this.oid;
    }

    public CipherParameters getParam() {
        return this.param;
    }

    @Override
    public char[] getPassword() {
        return this.pbeKeySpec.getPassword();
    }

    @Override
    public byte[] getSalt() {
        return this.pbeKeySpec.getSalt();
    }

    int getType() {
        return this.type;
    }

    public void setTryWrongPKCS12Zero(boolean bl) {
        this.tryWrong = bl;
    }

    boolean shouldTryWrongPKCS12() {
        return this.tryWrong;
    }
}

