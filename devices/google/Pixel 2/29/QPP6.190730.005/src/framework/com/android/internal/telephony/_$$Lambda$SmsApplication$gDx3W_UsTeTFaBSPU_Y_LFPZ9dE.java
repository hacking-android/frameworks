/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.SmsApplication;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class _$$Lambda$SmsApplication$gDx3W_UsTeTFaBSPU_Y_LFPZ9dE
implements Consumer {
    private final /* synthetic */ CompletableFuture f$0;

    public /* synthetic */ _$$Lambda$SmsApplication$gDx3W_UsTeTFaBSPU_Y_LFPZ9dE(CompletableFuture completableFuture) {
        this.f$0 = completableFuture;
    }

    public final void accept(Object object) {
        SmsApplication.lambda$setDefaultApplicationInternal$0(this.f$0, (Boolean)object);
    }
}

