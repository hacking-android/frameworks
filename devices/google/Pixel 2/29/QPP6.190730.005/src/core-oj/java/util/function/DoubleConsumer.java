/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$DoubleConsumer$HNSB3MjwB_DXE7Kpt1C_BT9h3T8;

@FunctionalInterface
public interface DoubleConsumer {
    public static /* synthetic */ void lambda$andThen$0(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2, double d) {
        doubleConsumer.accept(d);
        doubleConsumer2.accept(d);
    }

    public void accept(double var1);

    default public DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new _$$Lambda$DoubleConsumer$HNSB3MjwB_DXE7Kpt1C_BT9h3T8(this, doubleConsumer);
    }
}

