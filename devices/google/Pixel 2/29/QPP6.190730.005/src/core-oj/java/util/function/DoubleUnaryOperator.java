/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$DoubleUnaryOperator
 *  java.util.function.-$$Lambda$DoubleUnaryOperator$i7wtM_8Ous-CB32HCfZ4usZ4zaQ
 */
package java.util.function;

import java.util.Objects;
import java.util.function.-$;
import java.util.function._$$Lambda$DoubleUnaryOperator$EzzlhUGRoL66wVBCG__euZgC_CA;
import java.util.function._$$Lambda$DoubleUnaryOperator$TV17Df571GWp0dWUym3y8OK6ZbM;
import java.util.function._$$Lambda$DoubleUnaryOperator$i7wtM_8Ous_CB32HCfZ4usZ4zaQ;

@FunctionalInterface
public interface DoubleUnaryOperator {
    public static DoubleUnaryOperator identity() {
        return _$$Lambda$DoubleUnaryOperator$i7wtM_8Ous_CB32HCfZ4usZ4zaQ.INSTANCE;
    }

    public static /* synthetic */ double lambda$andThen$1(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, double d) {
        return doubleUnaryOperator2.applyAsDouble(doubleUnaryOperator.applyAsDouble(d));
    }

    public static /* synthetic */ double lambda$compose$0(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, double d) {
        return doubleUnaryOperator.applyAsDouble(doubleUnaryOperator2.applyAsDouble(d));
    }

    public static /* synthetic */ double lambda$identity$2(double d) {
        return d;
    }

    default public DoubleUnaryOperator andThen(DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return new _$$Lambda$DoubleUnaryOperator$EzzlhUGRoL66wVBCG__euZgC_CA(this, doubleUnaryOperator);
    }

    public double applyAsDouble(double var1);

    default public DoubleUnaryOperator compose(DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return new _$$Lambda$DoubleUnaryOperator$TV17Df571GWp0dWUym3y8OK6ZbM(this, doubleUnaryOperator);
    }
}

