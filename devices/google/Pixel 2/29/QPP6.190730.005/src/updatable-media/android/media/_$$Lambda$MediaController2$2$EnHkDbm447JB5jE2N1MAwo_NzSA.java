/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.media;

import android.media.MediaController2;
import android.media.Session2Command;
import android.os.Bundle;

public final class _$$Lambda$MediaController2$2$EnHkDbm447JB5jE2N1MAwo_NzSA
implements Runnable {
    private final /* synthetic */ MediaController2.2 f$0;
    private final /* synthetic */ Session2Command f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ Bundle f$3;

    public /* synthetic */ _$$Lambda$MediaController2$2$EnHkDbm447JB5jE2N1MAwo_NzSA(MediaController2.2 var1_1, Session2Command session2Command, int n, Bundle bundle) {
        this.f$0 = var1_1;
        this.f$1 = session2Command;
        this.f$2 = n;
        this.f$3 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onReceiveResult$0$MediaController2$2(this.f$1, this.f$2, this.f$3);
    }
}

