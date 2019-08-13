/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaRouter;

public final class _$$Lambda$MediaRouter$Static$Client$xd63sG7fVJYYu49J1xqN8JYQvo4
implements Runnable {
    private final /* synthetic */ MediaRouter.Static.Client f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$MediaRouter$Static$Client$xd63sG7fVJYYu49J1xqN8JYQvo4(MediaRouter.Static.Client client, String string2) {
        this.f$0 = client;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSelectedRouteChanged$0$MediaRouter$Static$Client(this.f$1);
    }
}

