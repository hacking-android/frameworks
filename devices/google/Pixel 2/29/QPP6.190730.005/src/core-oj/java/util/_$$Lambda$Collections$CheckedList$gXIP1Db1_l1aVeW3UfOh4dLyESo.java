/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collections;
import java.util.function.UnaryOperator;

public final class _$$Lambda$Collections$CheckedList$gXIP1Db1_l1aVeW3UfOh4dLyESo
implements UnaryOperator {
    private final /* synthetic */ Collections.CheckedList f$0;
    private final /* synthetic */ UnaryOperator f$1;

    public /* synthetic */ _$$Lambda$Collections$CheckedList$gXIP1Db1_l1aVeW3UfOh4dLyESo(Collections.CheckedList checkedList, UnaryOperator unaryOperator) {
        this.f$0 = checkedList;
        this.f$1 = unaryOperator;
    }

    @Override
    public final Object apply(Object object) {
        return this.f$0.lambda$replaceAll$0$Collections$CheckedList(this.f$1, object);
    }
}

