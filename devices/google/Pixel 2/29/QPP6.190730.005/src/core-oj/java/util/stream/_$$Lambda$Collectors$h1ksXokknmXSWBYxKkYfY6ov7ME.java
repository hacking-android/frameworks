/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$h1ksXokknmXSWBYxKkYfY6ov7ME
implements BiFunction {
    private final /* synthetic */ Function f$0;

    public /* synthetic */ _$$Lambda$Collectors$h1ksXokknmXSWBYxKkYfY6ov7ME(Function function) {
        this.f$0 = function;
    }

    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$groupingByConcurrent$52(this.f$0, object, object2);
    }
}

