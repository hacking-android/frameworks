/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$UnaryOperator
 *  java.util.function.-$$Lambda$UnaryOperator$p7kKvUH5OpW1KFw_KNJNdNw8HUE
 */
package java.util.function;

import java.util.function.-$;
import java.util.function.Function;
import java.util.function._$$Lambda$UnaryOperator$p7kKvUH5OpW1KFw_KNJNdNw8HUE;

@FunctionalInterface
public interface UnaryOperator<T>
extends Function<T, T> {
    public static <T> UnaryOperator<T> identity() {
        return _$$Lambda$UnaryOperator$p7kKvUH5OpW1KFw_KNJNdNw8HUE.INSTANCE;
    }

    public static /* synthetic */ Object lambda$identity$0(Object object) {
        return object;
    }
}

