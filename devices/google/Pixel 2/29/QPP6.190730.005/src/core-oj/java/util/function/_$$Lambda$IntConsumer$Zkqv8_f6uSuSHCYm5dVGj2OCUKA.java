/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.IntConsumer;

public final class _$$Lambda$IntConsumer$Zkqv8_f6uSuSHCYm5dVGj2OCUKA
implements IntConsumer {
    private final /* synthetic */ IntConsumer f$0;
    private final /* synthetic */ IntConsumer f$1;

    public /* synthetic */ _$$Lambda$IntConsumer$Zkqv8_f6uSuSHCYm5dVGj2OCUKA(IntConsumer intConsumer, IntConsumer intConsumer2) {
        this.f$0 = intConsumer;
        this.f$1 = intConsumer2;
    }

    @Override
    public final void accept(int n) {
        IntConsumer.lambda$andThen$0(this.f$0, this.f$1, n);
    }
}

