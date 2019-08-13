/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.ReferencePipeline;

public final class _$$Lambda$ReferencePipeline$i_WMOWw_bzoH_v_eRIvtIHIFvko
implements Consumer {
    private final /* synthetic */ BiConsumer f$0;
    private final /* synthetic */ Object f$1;

    public /* synthetic */ _$$Lambda$ReferencePipeline$i_WMOWw_bzoH_v_eRIvtIHIFvko(BiConsumer biConsumer, Object object) {
        this.f$0 = biConsumer;
        this.f$1 = object;
    }

    public final void accept(Object object) {
        ReferencePipeline.lambda$collect$1(this.f$0, this.f$1, object);
    }
}

