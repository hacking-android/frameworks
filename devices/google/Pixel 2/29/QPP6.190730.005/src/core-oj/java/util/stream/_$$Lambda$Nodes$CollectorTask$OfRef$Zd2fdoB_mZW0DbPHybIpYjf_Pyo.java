/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.stream.Nodes;

public final class _$$Lambda$Nodes$CollectorTask$OfRef$Zd2fdoB_mZW0DbPHybIpYjf_Pyo
implements LongFunction {
    private final /* synthetic */ IntFunction f$0;

    public /* synthetic */ _$$Lambda$Nodes$CollectorTask$OfRef$Zd2fdoB_mZW0DbPHybIpYjf_Pyo(IntFunction intFunction) {
        this.f$0 = intFunction;
    }

    public final Object apply(long l) {
        return Nodes.CollectorTask.OfRef.lambda$new$0(this.f$0, l);
    }
}

