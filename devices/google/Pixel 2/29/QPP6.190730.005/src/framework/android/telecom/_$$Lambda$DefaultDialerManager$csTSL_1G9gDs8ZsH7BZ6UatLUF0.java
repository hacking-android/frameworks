/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.DefaultDialerManager;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0
implements Consumer {
    private final /* synthetic */ CompletableFuture f$0;

    public /* synthetic */ _$$Lambda$DefaultDialerManager$csTSL_1G9gDs8ZsH7BZ6UatLUF0(CompletableFuture completableFuture) {
        this.f$0 = completableFuture;
    }

    public final void accept(Object object) {
        DefaultDialerManager.lambda$setDefaultDialerApplication$0(this.f$0, (Boolean)object);
    }
}

