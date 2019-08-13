/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$hNSw8Kk0nIafeklCUz0r3g25T08
implements BiFunction {
    private final /* synthetic */ Function f$0;

    public /* synthetic */ _$$Lambda$Collectors$hNSw8Kk0nIafeklCUz0r3g25T08(Function function) {
        this.f$0 = function;
    }

    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$groupingBy$46(this.f$0, object, object2);
    }
}

