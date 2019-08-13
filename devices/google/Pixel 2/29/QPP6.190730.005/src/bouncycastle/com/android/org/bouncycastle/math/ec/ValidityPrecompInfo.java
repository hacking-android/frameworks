/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.PreCompInfo;

class ValidityPrecompInfo
implements PreCompInfo {
    static final String PRECOMP_NAME = "bc_validity";
    private boolean curveEquationPassed = false;
    private boolean failed = false;
    private boolean orderPassed = false;

    ValidityPrecompInfo() {
    }

    boolean hasCurveEquationPassed() {
        return this.curveEquationPassed;
    }

    boolean hasFailed() {
        return this.failed;
    }

    boolean hasOrderPassed() {
        return this.orderPassed;
    }

    void reportCurveEquationPassed() {
        this.curveEquationPassed = true;
    }

    void reportFailed() {
        this.failed = true;
    }

    void reportOrderPassed() {
        this.orderPassed = true;
    }
}

