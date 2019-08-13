/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.endo;

import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPointMap;
import com.android.org.bouncycastle.math.ec.ScaleXPointMap;
import com.android.org.bouncycastle.math.ec.endo.GLVEndomorphism;
import com.android.org.bouncycastle.math.ec.endo.GLVTypeBParameters;
import java.math.BigInteger;

public class GLVTypeBEndomorphism
implements GLVEndomorphism {
    protected final ECCurve curve;
    protected final GLVTypeBParameters parameters;
    protected final ECPointMap pointMap;

    public GLVTypeBEndomorphism(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        this.curve = eCCurve;
        this.parameters = gLVTypeBParameters;
        this.pointMap = new ScaleXPointMap(eCCurve.fromBigInteger(gLVTypeBParameters.getBeta()));
    }

    protected BigInteger calculateB(BigInteger bigInteger, BigInteger bigInteger2, int n) {
        block1 : {
            boolean bl = bigInteger2.signum() < 0;
            bigInteger = bigInteger.multiply(bigInteger2.abs());
            boolean bl2 = bigInteger.testBit(n - 1);
            bigInteger = bigInteger2 = bigInteger.shiftRight(n);
            if (bl2) {
                bigInteger = bigInteger2.add(ECConstants.ONE);
            }
            if (!bl) break block1;
            bigInteger = bigInteger.negate();
        }
        return bigInteger;
    }

    @Override
    public BigInteger[] decomposeScalar(BigInteger bigInteger) {
        int n = this.parameters.getBits();
        BigInteger bigInteger2 = this.calculateB(bigInteger, this.parameters.getG1(), n);
        BigInteger bigInteger3 = this.calculateB(bigInteger, this.parameters.getG2(), n);
        GLVTypeBParameters gLVTypeBParameters = this.parameters;
        return new BigInteger[]{bigInteger.subtract(bigInteger2.multiply(gLVTypeBParameters.getV1A()).add(bigInteger3.multiply(gLVTypeBParameters.getV2A()))), bigInteger2.multiply(gLVTypeBParameters.getV1B()).add(bigInteger3.multiply(gLVTypeBParameters.getV2B())).negate()};
    }

    @Override
    public ECPointMap getPointMap() {
        return this.pointMap;
    }

    @Override
    public boolean hasEfficientPointMap() {
        return true;
    }
}

