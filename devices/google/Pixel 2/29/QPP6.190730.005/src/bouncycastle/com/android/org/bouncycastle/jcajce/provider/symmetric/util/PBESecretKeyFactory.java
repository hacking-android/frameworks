/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;

public class PBESecretKeyFactory
extends BaseSecretKeyFactory
implements PBE {
    private int digest;
    private boolean forCipher;
    private int ivSize;
    private int keySize;
    private int scheme;

    public PBESecretKeyFactory(String string, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, int n, int n2, int n3, int n4) {
        super(string, aSN1ObjectIdentifier);
        this.forCipher = bl;
        this.scheme = n;
        this.digest = n2;
        this.keySize = n3;
        this.ivSize = n4;
    }

    @Override
    protected SecretKey engineGenerateSecret(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof PBEKeySpec) {
            PBEKeySpec pBEKeySpec = (PBEKeySpec)object;
            if (pBEKeySpec.getSalt() == null) {
                return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, null);
            }
            object = this.forCipher ? PBE.Util.makePBEParameters(pBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize) : PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, this.digest, this.keySize);
            return new BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, (CipherParameters)object);
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }
}

