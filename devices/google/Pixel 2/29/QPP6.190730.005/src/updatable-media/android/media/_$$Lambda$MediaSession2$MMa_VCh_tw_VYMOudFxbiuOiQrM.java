/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaSession2;

public final class _$$Lambda$MediaSession2$MMa_VCh_tw_VYMOudFxbiuOiQrM
implements Runnable {
    private final /* synthetic */ MediaSession2 f$0;
    private final /* synthetic */ MediaSession2.ControllerInfo f$1;

    public /* synthetic */ _$$Lambda$MediaSession2$MMa_VCh_tw_VYMOudFxbiuOiQrM(MediaSession2 mediaSession2, MediaSession2.ControllerInfo controllerInfo) {
        this.f$0 = mediaSession2;
        this.f$1 = controllerInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onDisconnect$1$MediaSession2(this.f$1);
    }
}

