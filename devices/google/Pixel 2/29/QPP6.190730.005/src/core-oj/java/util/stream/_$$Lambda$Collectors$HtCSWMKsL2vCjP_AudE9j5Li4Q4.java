/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Collectors
 *  java.util.stream.-$$Lambda$Collectors$HtCSWMKsL2vCjP_AudE9j5Li4Q4
 */
package java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$HtCSWMKsL2vCjP_AudE9j5Li4Q4
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.Collectors.HtCSWMKsL2vCjP_AudE9j5Li4Q4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Collectors$HtCSWMKsL2vCjP_AudE9j5Li4Q4();
    }

    private /* synthetic */ _$$Lambda$Collectors$HtCSWMKsL2vCjP_AudE9j5Li4Q4() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$summarizingInt$61((IntSummaryStatistics)object, (IntSummaryStatistics)object2);
    }
}

