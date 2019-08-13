/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$IntPipeline
 *  java.util.stream.-$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo
 */
package java.util.stream;

import java.util.function.IntToLongFunction;
import java.util.stream.-$;
import java.util.stream.IntPipeline;

public final class _$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo
implements IntToLongFunction {
    public static final /* synthetic */ -$.Lambda.IntPipeline.Q_Wb7uDnZZMCasMbsGNAwSlprMo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo();
    }

    private /* synthetic */ _$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo() {
    }

    @Override
    public final long applyAsLong(int n) {
        return IntPipeline.lambda$count$1(n);
    }
}

