/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$dplkPhACWDPIy18ogwdupEQaN40
 */
package java.util.stream;

import java.util.function.LongBinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$dplkPhACWDPIy18ogwdupEQaN40
implements LongBinaryOperator {
    public static final /* synthetic */ -$.Lambda.dplkPhACWDPIy18ogwdupEQaN40 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$dplkPhACWDPIy18ogwdupEQaN40();
    }

    private /* synthetic */ _$$Lambda$dplkPhACWDPIy18ogwdupEQaN40() {
    }

    @Override
    public final long applyAsLong(long l, long l2) {
        return Long.sum(l, l2);
    }
}

