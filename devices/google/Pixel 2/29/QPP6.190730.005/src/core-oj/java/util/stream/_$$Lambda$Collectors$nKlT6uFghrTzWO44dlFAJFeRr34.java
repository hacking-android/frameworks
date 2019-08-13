/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$nKlT6uFghrTzWO44dlFAJFeRr34
implements BiConsumer {
    private final /* synthetic */ Function f$0;
    private final /* synthetic */ Function f$1;
    private final /* synthetic */ BinaryOperator f$2;

    public /* synthetic */ _$$Lambda$Collectors$nKlT6uFghrTzWO44dlFAJFeRr34(Function function, Function function2, BinaryOperator binaryOperator) {
        this.f$0 = function;
        this.f$1 = function2;
        this.f$2 = binaryOperator;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$toMap$58(this.f$0, this.f$1, this.f$2, (Map)object, object2);
    }
}

