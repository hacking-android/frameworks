/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$PTd6jsJ7t0481HRFfH8tnGifDqw
implements BiConsumer {
    private final /* synthetic */ BinaryOperator f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$Collectors$PTd6jsJ7t0481HRFfH8tnGifDqw(BinaryOperator binaryOperator, Function function) {
        this.f$0 = binaryOperator;
        this.f$1 = function;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$reducing$41(this.f$0, this.f$1, (Object[])object, object2);
    }
}

