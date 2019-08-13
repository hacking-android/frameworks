/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$GYNZB08upNxJa3yuZVQ1065S1eU
implements BinaryOperator {
    private final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ _$$Lambda$Collectors$GYNZB08upNxJa3yuZVQ1065S1eU(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$partitioningBy$55(this.f$0, (Collectors.Partition)object, (Collectors.Partition)object2);
    }
}

