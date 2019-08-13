/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40
 */
package java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.stream.-$;

public final class _$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.YcgMAuDDScc4HC6CSMDq1R0qa40 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40();
    }

    private /* synthetic */ _$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40() {
    }

    public final void accept(Object object, Object object2) {
        ((IntSummaryStatistics)object).combine((IntSummaryStatistics)object2);
    }
}

