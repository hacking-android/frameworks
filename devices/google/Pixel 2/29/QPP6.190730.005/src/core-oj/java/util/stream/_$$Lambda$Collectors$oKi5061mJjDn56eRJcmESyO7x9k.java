/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$oKi5061mJjDn56eRJcmESyO7x9k
implements Function {
    private final /* synthetic */ Function f$0;

    public /* synthetic */ _$$Lambda$Collectors$oKi5061mJjDn56eRJcmESyO7x9k(Function function) {
        this.f$0 = function;
    }

    public final Object apply(Object object) {
        return Collectors.lambda$groupingByConcurrent$53(this.f$0, (ConcurrentMap)object);
    }
}

