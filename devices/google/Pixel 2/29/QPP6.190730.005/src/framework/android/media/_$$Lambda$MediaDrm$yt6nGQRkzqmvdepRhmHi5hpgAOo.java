/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaDrm;

public final class _$$Lambda$MediaDrm$yt6nGQRkzqmvdepRhmHi5hpgAOo
implements Runnable {
    private final /* synthetic */ MediaDrm f$0;
    private final /* synthetic */ Object f$1;
    private final /* synthetic */ MediaDrm.ListenerWithExecutor f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ int f$4;

    public /* synthetic */ _$$Lambda$MediaDrm$yt6nGQRkzqmvdepRhmHi5hpgAOo(MediaDrm mediaDrm, Object object, MediaDrm.ListenerWithExecutor listenerWithExecutor, int n, int n2) {
        this.f$0 = mediaDrm;
        this.f$1 = object;
        this.f$2 = listenerWithExecutor;
        this.f$3 = n;
        this.f$4 = n2;
    }

    @Override
    public final void run() {
        MediaDrm.lambda$postEventFromNative$4(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

