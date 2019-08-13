/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.Consumer;

public final class _$$Lambda$Consumer$fZIgy_f2Fa5seBa8ztxXTExq2p4
implements Consumer {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$Consumer$fZIgy_f2Fa5seBa8ztxXTExq2p4(Consumer consumer, Consumer consumer2) {
        this.f$0 = consumer;
        this.f$1 = consumer2;
    }

    public final void accept(Object object) {
        Consumer.lambda$andThen$0(this.f$0, this.f$1, object);
    }
}

