/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.media;

import android.media.Controller2Link;
import android.media.MediaSession2Service;
import android.os.Bundle;

public final class _$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs
implements Runnable {
    private final /* synthetic */ MediaSession2Service.MediaSession2ServiceStub f$0;
    private final /* synthetic */ Bundle f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ Controller2Link f$4;
    private final /* synthetic */ int f$5;

    public /* synthetic */ _$$Lambda$MediaSession2Service$MediaSession2ServiceStub$lmqGgEmF_eznMgcMPXx8eX7uOKs(MediaSession2Service.MediaSession2ServiceStub mediaSession2ServiceStub, Bundle bundle, int n, int n2, Controller2Link controller2Link, int n3) {
        this.f$0 = mediaSession2ServiceStub;
        this.f$1 = bundle;
        this.f$2 = n;
        this.f$3 = n2;
        this.f$4 = controller2Link;
        this.f$5 = n3;
    }

    @Override
    public final void run() {
        this.f$0.lambda$connect$0$MediaSession2Service$MediaSession2ServiceStub(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
    }
}

