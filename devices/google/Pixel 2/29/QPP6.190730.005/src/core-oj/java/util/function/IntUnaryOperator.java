/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$IntUnaryOperator
 *  java.util.function.-$$Lambda$IntUnaryOperator$DKxG0-oyUAYjk17nXTQ5x-EXFgU
 */
package java.util.function;

import java.util.Objects;
import java.util.function.-$;
import java.util.function._$$Lambda$IntUnaryOperator$DKxG0_oyUAYjk17nXTQ5x_EXFgU;
import java.util.function._$$Lambda$IntUnaryOperator$F5l8VUrgGacAzg6VKzynJCfDBx4;
import java.util.function._$$Lambda$IntUnaryOperator$HBwqtJWwVkNFC818pCzqJ5KBLm0;

@FunctionalInterface
public interface IntUnaryOperator {
    public static IntUnaryOperator identity() {
        return _$$Lambda$IntUnaryOperator$DKxG0_oyUAYjk17nXTQ5x_EXFgU.INSTANCE;
    }

    public static /* synthetic */ int lambda$andThen$1(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2, int n) {
        return intUnaryOperator2.applyAsInt(intUnaryOperator.applyAsInt(n));
    }

    public static /* synthetic */ int lambda$compose$0(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2, int n) {
        return intUnaryOperator.applyAsInt(intUnaryOperator2.applyAsInt(n));
    }

    public static /* synthetic */ int lambda$identity$2(int n) {
        return n;
    }

    default public IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new _$$Lambda$IntUnaryOperator$F5l8VUrgGacAzg6VKzynJCfDBx4(this, intUnaryOperator);
    }

    public int applyAsInt(int var1);

    default public IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new _$$Lambda$IntUnaryOperator$HBwqtJWwVkNFC818pCzqJ5KBLm0(this, intUnaryOperator);
    }
}

