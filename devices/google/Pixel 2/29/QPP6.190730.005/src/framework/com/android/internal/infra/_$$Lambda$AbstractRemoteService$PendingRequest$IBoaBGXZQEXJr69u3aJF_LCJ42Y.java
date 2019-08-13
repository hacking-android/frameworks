/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.infra;

import com.android.internal.infra.AbstractRemoteService;

public final class _$$Lambda$AbstractRemoteService$PendingRequest$IBoaBGXZQEXJr69u3aJF_LCJ42Y
implements Runnable {
    private final /* synthetic */ AbstractRemoteService.PendingRequest f$0;
    private final /* synthetic */ AbstractRemoteService f$1;

    public /* synthetic */ _$$Lambda$AbstractRemoteService$PendingRequest$IBoaBGXZQEXJr69u3aJF_LCJ42Y(AbstractRemoteService.PendingRequest pendingRequest, AbstractRemoteService abstractRemoteService) {
        this.f$0 = pendingRequest;
        this.f$1 = abstractRemoteService;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$AbstractRemoteService$PendingRequest(this.f$1);
    }
}

