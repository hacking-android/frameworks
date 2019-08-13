/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NattSocketKeepalive;

public final class _$$Lambda$NattSocketKeepalive$7nsE_7bVdhw33oN4gmk8WVi_r9U
implements Runnable {
    private final /* synthetic */ NattSocketKeepalive f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$NattSocketKeepalive$7nsE_7bVdhw33oN4gmk8WVi_r9U(NattSocketKeepalive nattSocketKeepalive, int n) {
        this.f$0 = nattSocketKeepalive;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$startImpl$0$NattSocketKeepalive(this.f$1);
    }
}

