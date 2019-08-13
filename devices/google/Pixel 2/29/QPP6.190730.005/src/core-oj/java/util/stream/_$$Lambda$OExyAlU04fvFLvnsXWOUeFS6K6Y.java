/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y
 */
package java.util.stream;

import java.util.function.LongBinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y
implements LongBinaryOperator {
    public static final /* synthetic */ -$.Lambda.OExyAlU04fvFLvnsXWOUeFS6K6Y INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y();
    }

    private /* synthetic */ _$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y() {
    }

    @Override
    public final long applyAsLong(long l, long l2) {
        return Math.min(l, l2);
    }
}

