/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.DoubleConsumer;

public final class _$$Lambda$DoubleConsumer$HNSB3MjwB_DXE7Kpt1C_BT9h3T8
implements DoubleConsumer {
    private final /* synthetic */ DoubleConsumer f$0;
    private final /* synthetic */ DoubleConsumer f$1;

    public /* synthetic */ _$$Lambda$DoubleConsumer$HNSB3MjwB_DXE7Kpt1C_BT9h3T8(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        this.f$0 = doubleConsumer;
        this.f$1 = doubleConsumer2;
    }

    @Override
    public final void accept(double d) {
        DoubleConsumer.lambda$andThen$0(this.f$0, this.f$1, d);
    }
}

