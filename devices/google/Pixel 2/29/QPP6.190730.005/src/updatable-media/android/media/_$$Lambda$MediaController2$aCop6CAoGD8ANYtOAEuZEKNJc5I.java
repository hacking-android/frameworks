/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaController2;

public final class _$$Lambda$MediaController2$aCop6CAoGD8ANYtOAEuZEKNJc5I
implements Runnable {
    private final /* synthetic */ MediaController2 f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$MediaController2$aCop6CAoGD8ANYtOAEuZEKNJc5I(MediaController2 mediaController2, boolean bl) {
        this.f$0 = mediaController2;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onPlaybackActiveChanged$3$MediaController2(this.f$1);
    }
}

