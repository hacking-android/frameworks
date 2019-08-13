/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$ad8ZNOGiSpsXDY_HtABmtE9E1UA
implements BinaryOperator {
    private final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ _$$Lambda$Collectors$ad8ZNOGiSpsXDY_HtABmtE9E1UA(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$reducing$35(this.f$0, (Object[])object, (Object[])object2);
    }
}

