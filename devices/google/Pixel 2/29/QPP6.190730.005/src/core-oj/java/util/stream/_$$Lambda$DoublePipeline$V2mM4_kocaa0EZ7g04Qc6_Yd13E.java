/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$DoublePipeline
 *  java.util.stream.-$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E
 */
package java.util.stream;

import java.util.function.DoubleToLongFunction;
import java.util.stream.-$;
import java.util.stream.DoublePipeline;

public final class _$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E
implements DoubleToLongFunction {
    public static final /* synthetic */ -$.Lambda.DoublePipeline.V2mM4_kocaa0EZ7g04Qc6_Yd13E INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E();
    }

    private /* synthetic */ _$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E() {
    }

    @Override
    public final long applyAsLong(double d) {
        return DoublePipeline.lambda$count$7(d);
    }
}

