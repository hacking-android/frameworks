/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.ResultReceiver
 */
package android.media;

import android.media.MediaController2;
import android.media.Session2Command;
import android.os.Bundle;
import android.os.ResultReceiver;

public final class _$$Lambda$MediaController2$o4uceEhANIC4PwNDbuKRY_Ai7Hc
implements Runnable {
    private final /* synthetic */ MediaController2 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ResultReceiver f$2;
    private final /* synthetic */ Session2Command f$3;
    private final /* synthetic */ Bundle f$4;

    public /* synthetic */ _$$Lambda$MediaController2$o4uceEhANIC4PwNDbuKRY_Ai7Hc(MediaController2 mediaController2, int n, ResultReceiver resultReceiver, Session2Command session2Command, Bundle bundle) {
        this.f$0 = mediaController2;
        this.f$1 = n;
        this.f$2 = resultReceiver;
        this.f$3 = session2Command;
        this.f$4 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSessionCommand$4$MediaController2(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

