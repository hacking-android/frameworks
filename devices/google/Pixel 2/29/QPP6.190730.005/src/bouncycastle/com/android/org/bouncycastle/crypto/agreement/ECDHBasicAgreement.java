/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.agreement;

import com.android.org.bouncycastle.crypto.BasicAgreement;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

public class ECDHBasicAgreement
implements BasicAgreement {
    private ECPrivateKeyParameters key;

    @Override
    public BigInteger calculateAgreement(CipherParameters object) {
        object = (ECPublicKeyParameters)object;
        ECDomainParameters eCDomainParameters = this.key.getParameters();
        if (eCDomainParameters.equals(((ECKeyParameters)object).getParameters())) {
            BigInteger bigInteger = this.key.getD();
            ECPoint eCPoint = ECAlgorithms.cleanPoint(eCDomainParameters.getCurve(), ((ECPublicKeyParameters)object).getQ());
            if (!eCPoint.isInfinity()) {
                BigInteger bigInteger2 = eCDomainParameters.getH();
                BigInteger bigInteger3 = bigInteger;
                object = eCPoint;
                if (!bigInteger2.equals(ECConstants.ONE)) {
                    bigInteger3 = eCDomainParameters.getHInv().multiply(bigInteger).mod(eCDomainParameters.getN());
                    object = ECAlgorithms.referenceMultiply(eCPoint, bigInteger2);
                }
                if (!((ECPoint)(object = ((ECPoint)object).multiply(bigInteger3).normalize())).isInfinity()) {
                    return ((ECPoint)object).getAffineXCoord().toBigInteger();
                }
                throw new IllegalStateException("Infinity is not a valid agreement value for ECDH");
            }
            throw new IllegalStateException("Infinity is not a valid public key for ECDH");
        }
        throw new IllegalStateException("ECDH public key has wrong domain parameters");
    }

    @Override
    public int getFieldSize() {
        return (this.key.getParameters().getCurve().getFieldSize() + 7) / 8;
    }

    @Override
    public void init(CipherParameters cipherParameters) {
        this.key = (ECPrivateKeyParameters)cipherParameters;
    }
}

