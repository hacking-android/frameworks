/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.widget.RemoteViewsAdapter;

public final class _$$Lambda$RemoteViewsAdapter$_xHEGE7CkOWJ8u7GAjsH_hc_iiA
implements Runnable {
    private final /* synthetic */ RemoteViewsAdapter.RemoteViewsCacheKey f$0;

    public /* synthetic */ _$$Lambda$RemoteViewsAdapter$_xHEGE7CkOWJ8u7GAjsH_hc_iiA(RemoteViewsAdapter.RemoteViewsCacheKey remoteViewsCacheKey) {
        this.f$0 = remoteViewsCacheKey;
    }

    @Override
    public final void run() {
        RemoteViewsAdapter.lambda$saveRemoteViewsCache$0(this.f$0);
    }
}

