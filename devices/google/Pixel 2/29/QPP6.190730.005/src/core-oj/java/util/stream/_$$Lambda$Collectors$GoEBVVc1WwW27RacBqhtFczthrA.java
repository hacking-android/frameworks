/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Collectors
 *  java.util.stream.-$$Lambda$Collectors$GoEBVVc1WwW27RacBqhtFczthrA
 */
package java.util.stream;

import java.util.LongSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$GoEBVVc1WwW27RacBqhtFczthrA
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.Collectors.GoEBVVc1WwW27RacBqhtFczthrA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Collectors$GoEBVVc1WwW27RacBqhtFczthrA();
    }

    private /* synthetic */ _$$Lambda$Collectors$GoEBVVc1WwW27RacBqhtFczthrA() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Collectors.lambda$summarizingLong$63((LongSummaryStatistics)object, (LongSummaryStatistics)object2);
    }
}

