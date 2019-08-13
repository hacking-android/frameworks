/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ
 */
package java.util.stream;

import java.util.LongSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.stream.-$;

public final class _$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.JNjUhnscc8mcsjlQNaAi4qIfRDQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ();
    }

    private /* synthetic */ _$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ() {
    }

    public final void accept(Object object, Object object2) {
        ((LongSummaryStatistics)object).combine((LongSummaryStatistics)object2);
    }
}

