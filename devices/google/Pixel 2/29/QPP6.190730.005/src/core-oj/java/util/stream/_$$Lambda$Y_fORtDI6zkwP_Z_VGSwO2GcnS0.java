/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0
 */
package java.util.stream;

import java.util.LongSummaryStatistics;
import java.util.function.ObjLongConsumer;
import java.util.stream.-$;

public final class _$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0
implements ObjLongConsumer {
    public static final /* synthetic */ -$.Lambda.Y_fORtDI6zkwP_Z_VGSwO2GcnS0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0();
    }

    private /* synthetic */ _$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0() {
    }

    public final void accept(Object object, long l) {
        ((LongSummaryStatistics)object).accept(l);
    }
}

