/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.ResultReceiver
 */
package android.media;

import android.media.MediaSession2;
import android.media.Session2Command;
import android.os.Bundle;
import android.os.ResultReceiver;

public final class _$$Lambda$MediaSession2$4mb9hAWB7fa2IwV_RWmJ7c7__v4
implements Runnable {
    private final /* synthetic */ MediaSession2 f$0;
    private final /* synthetic */ MediaSession2.ControllerInfo f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ ResultReceiver f$3;
    private final /* synthetic */ Session2Command f$4;
    private final /* synthetic */ Bundle f$5;

    public /* synthetic */ _$$Lambda$MediaSession2$4mb9hAWB7fa2IwV_RWmJ7c7__v4(MediaSession2 mediaSession2, MediaSession2.ControllerInfo controllerInfo, int n, ResultReceiver resultReceiver, Session2Command session2Command, Bundle bundle) {
        this.f$0 = mediaSession2;
        this.f$1 = controllerInfo;
        this.f$2 = n;
        this.f$3 = resultReceiver;
        this.f$4 = session2Command;
        this.f$5 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSessionCommand$2$MediaSession2(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
    }
}

