/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.AbstractPipeline;

public final class _$$Lambda$AbstractPipeline$i18ybop3uEZwLQyKh7zmnuTXiFw
implements Supplier {
    private final /* synthetic */ Spliterator f$0;

    public /* synthetic */ _$$Lambda$AbstractPipeline$i18ybop3uEZwLQyKh7zmnuTXiFw(Spliterator spliterator) {
        this.f$0 = spliterator;
    }

    public final Object get() {
        return AbstractPipeline.lambda$wrapSpliterator$1(this.f$0);
    }
}

