/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECKeyParameters;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;

public class ECPublicKeyParameters
extends ECKeyParameters {
    private final ECPoint Q;

    public ECPublicKeyParameters(ECPoint eCPoint, ECDomainParameters eCDomainParameters) {
        super(false, eCDomainParameters);
        this.Q = ECDomainParameters.validate(eCDomainParameters.getCurve(), eCPoint);
    }

    public ECPoint getQ() {
        return this.Q;
    }
}

