/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package android.media;

import android.media.MediaController2;
import android.os.IBinder;

public final class _$$Lambda$MediaController2$PSnpjlMlhb1Gdn0LBWml9HGT5NI
implements IBinder.DeathRecipient {
    private final /* synthetic */ MediaController2 f$0;

    public /* synthetic */ _$$Lambda$MediaController2$PSnpjlMlhb1Gdn0LBWml9HGT5NI(MediaController2 mediaController2) {
        this.f$0 = mediaController2;
    }

    public final void binderDied() {
        this.f$0.lambda$new$0$MediaController2();
    }
}

