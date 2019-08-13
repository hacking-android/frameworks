/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.media;

import android.media.MediaSession2;
import android.media.Session2Command;
import android.os.Bundle;

public final class _$$Lambda$MediaSession2$1$Ty9__5M_U_w4LH0UhCT90vhjsHE
implements Runnable {
    private final /* synthetic */ MediaSession2.1 f$0;
    private final /* synthetic */ MediaSession2.ControllerInfo f$1;
    private final /* synthetic */ Session2Command f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ Bundle f$4;

    public /* synthetic */ _$$Lambda$MediaSession2$1$Ty9__5M_U_w4LH0UhCT90vhjsHE(MediaSession2.1 var1_1, MediaSession2.ControllerInfo controllerInfo, Session2Command session2Command, int n, Bundle bundle) {
        this.f$0 = var1_1;
        this.f$1 = controllerInfo;
        this.f$2 = session2Command;
        this.f$3 = n;
        this.f$4 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onReceiveResult$0$MediaSession2$1(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

