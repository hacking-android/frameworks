/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$LongUnaryOperator
 *  java.util.function.-$$Lambda$LongUnaryOperator$kI3lBaNH3h6ldTmGeiEUd61CYJI
 */
package java.util.function;

import java.util.Objects;
import java.util.function.-$;
import java.util.function._$$Lambda$LongUnaryOperator$MxQtG8PPTeFzxiwgpkR3tXC_HLU;
import java.util.function._$$Lambda$LongUnaryOperator$e52YMvir03pwSw7KvpRuqEbSDRg;
import java.util.function._$$Lambda$LongUnaryOperator$kI3lBaNH3h6ldTmGeiEUd61CYJI;

@FunctionalInterface
public interface LongUnaryOperator {
    public static LongUnaryOperator identity() {
        return _$$Lambda$LongUnaryOperator$kI3lBaNH3h6ldTmGeiEUd61CYJI.INSTANCE;
    }

    public static /* synthetic */ long lambda$andThen$1(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2, long l) {
        return longUnaryOperator2.applyAsLong(longUnaryOperator.applyAsLong(l));
    }

    public static /* synthetic */ long lambda$compose$0(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2, long l) {
        return longUnaryOperator.applyAsLong(longUnaryOperator2.applyAsLong(l));
    }

    public static /* synthetic */ long lambda$identity$2(long l) {
        return l;
    }

    default public LongUnaryOperator andThen(LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return new _$$Lambda$LongUnaryOperator$MxQtG8PPTeFzxiwgpkR3tXC_HLU(this, longUnaryOperator);
    }

    public long applyAsLong(long var1);

    default public LongUnaryOperator compose(LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return new _$$Lambda$LongUnaryOperator$e52YMvir03pwSw7KvpRuqEbSDRg(this, longUnaryOperator);
    }
}

