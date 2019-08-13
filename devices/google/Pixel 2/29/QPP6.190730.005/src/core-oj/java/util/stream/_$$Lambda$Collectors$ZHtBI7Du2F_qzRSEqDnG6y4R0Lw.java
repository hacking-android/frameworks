/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$ZHtBI7Du2F_qzRSEqDnG6y4R0Lw
implements BiConsumer {
    private final /* synthetic */ Function f$0;
    private final /* synthetic */ Function f$1;
    private final /* synthetic */ BinaryOperator f$2;

    public /* synthetic */ _$$Lambda$Collectors$ZHtBI7Du2F_qzRSEqDnG6y4R0Lw(Function function, Function function2, BinaryOperator binaryOperator) {
        this.f$0 = function;
        this.f$1 = function2;
        this.f$2 = binaryOperator;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$toConcurrentMap$59(this.f$0, this.f$1, this.f$2, (ConcurrentMap)object, object2);
    }
}

