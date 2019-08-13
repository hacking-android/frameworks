/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.agreement;

import com.android.org.bouncycastle.crypto.BasicAgreement;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DHKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import java.math.BigInteger;

public class DHBasicAgreement
implements BasicAgreement {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private DHParameters dhParams;
    private DHPrivateKeyParameters key;

    @Override
    public BigInteger calculateAgreement(CipherParameters object) {
        Object object2 = (DHPublicKeyParameters)object;
        if (((DHKeyParameters)object2).getParameters().equals(this.dhParams)) {
            object = this.dhParams.getP();
            if ((object2 = ((DHPublicKeyParameters)object2).getY()) != null && ((BigInteger)object2).compareTo(ONE) > 0 && ((BigInteger)object2).compareTo(((BigInteger)object).subtract(ONE)) < 0) {
                object = ((BigInteger)object2).modPow(this.key.getX(), (BigInteger)object);
                if (!((BigInteger)object).equals(ONE)) {
                    return object;
                }
                throw new IllegalStateException("Shared key can't be 1");
            }
            throw new IllegalArgumentException("Diffie-Hellman public key is weak");
        }
        throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }

    @Override
    public int getFieldSize() {
        return (this.key.getParameters().getP().bitLength() + 7) / 8;
    }

    @Override
    public void init(CipherParameters cipherParameters) {
        if ((cipherParameters = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter)((ParametersWithRandom)cipherParameters).getParameters() : (AsymmetricKeyParameter)cipherParameters) instanceof DHPrivateKeyParameters) {
            this.key = (DHPrivateKeyParameters)cipherParameters;
            this.dhParams = this.key.getParameters();
            return;
        }
        throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
    }
}

