/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg
 */
package java.util.stream;

import java.util.function.LongBinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg
implements LongBinaryOperator {
    public static final /* synthetic */ -$.Lambda.6eeAyFpmvaed9kw3uuEs0ErN7sg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg();
    }

    private /* synthetic */ _$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg() {
    }

    @Override
    public final long applyAsLong(long l, long l2) {
        return Math.max(l, l2);
    }
}

