/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$xODDB9V1Y7uzjD3f8OY1Ck4IN4k
implements Function {
    private final /* synthetic */ Function f$0;

    public /* synthetic */ _$$Lambda$Collectors$xODDB9V1Y7uzjD3f8OY1Ck4IN4k(Function function) {
        this.f$0 = function;
    }

    public final Object apply(Object object) {
        return Collectors.lambda$groupingBy$47(this.f$0, (Map)object);
    }
}

