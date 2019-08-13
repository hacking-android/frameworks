/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.Controller2Link;
import android.media.MediaSession2;

public final class _$$Lambda$MediaSession2$53DgwUyPnHR49G3UlTPSmlD2b_I
implements Runnable {
    private final /* synthetic */ MediaSession2 f$0;
    private final /* synthetic */ MediaSession2.ControllerInfo f$1;
    private final /* synthetic */ Controller2Link f$2;

    public /* synthetic */ _$$Lambda$MediaSession2$53DgwUyPnHR49G3UlTPSmlD2b_I(MediaSession2 mediaSession2, MediaSession2.ControllerInfo controllerInfo, Controller2Link controller2Link) {
        this.f$0 = mediaSession2;
        this.f$1 = controllerInfo;
        this.f$2 = controller2Link;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onConnect$0$MediaSession2(this.f$1, this.f$2);
    }
}

