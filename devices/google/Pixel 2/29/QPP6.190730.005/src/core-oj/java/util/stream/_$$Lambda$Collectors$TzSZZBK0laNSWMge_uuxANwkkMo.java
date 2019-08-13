/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$TzSZZBK0laNSWMge_uuxANwkkMo
implements BinaryOperator {
    private final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ _$$Lambda$Collectors$TzSZZBK0laNSWMge_uuxANwkkMo(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$mapMerger$7(this.f$0, (Map)object, (Map)object2);
    }
}

