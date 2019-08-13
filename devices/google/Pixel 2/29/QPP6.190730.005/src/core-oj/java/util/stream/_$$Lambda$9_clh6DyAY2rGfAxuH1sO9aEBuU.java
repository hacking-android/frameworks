/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$9-clh6DyAY2rGfAxuH1sO9aEBuU
 */
package java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.ObjDoubleConsumer;
import java.util.stream.-$;

public final class _$$Lambda$9_clh6DyAY2rGfAxuH1sO9aEBuU
implements ObjDoubleConsumer {
    public static final /* synthetic */ -$.Lambda.9-clh6DyAY2rGfAxuH1sO9aEBuU INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$9_clh6DyAY2rGfAxuH1sO9aEBuU();
    }

    private /* synthetic */ _$$Lambda$9_clh6DyAY2rGfAxuH1sO9aEBuU() {
    }

    public final void accept(Object object, double d) {
        ((DoubleSummaryStatistics)object).accept(d);
    }
}

