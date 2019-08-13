/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$hPhWWwdsY4xpBbhcwNVuU8dcJDw
implements BiConsumer {
    private final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ _$$Lambda$Collectors$hPhWWwdsY4xpBbhcwNVuU8dcJDw(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$reducing$34(this.f$0, (Object[])object, object2);
    }
}

