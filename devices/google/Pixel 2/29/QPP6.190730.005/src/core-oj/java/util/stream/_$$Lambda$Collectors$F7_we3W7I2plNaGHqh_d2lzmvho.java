/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$F7_we3W7I2plNaGHqh_d2lzmvho
implements BiConsumer {
    private final /* synthetic */ Function f$0;
    private final /* synthetic */ Supplier f$1;
    private final /* synthetic */ BiConsumer f$2;

    public /* synthetic */ _$$Lambda$Collectors$F7_we3W7I2plNaGHqh_d2lzmvho(Function function, Supplier supplier, BiConsumer biConsumer) {
        this.f$0 = function;
        this.f$1 = supplier;
        this.f$2 = biConsumer;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$groupingBy$45(this.f$0, this.f$1, this.f$2, (Map)object, object2);
    }
}

