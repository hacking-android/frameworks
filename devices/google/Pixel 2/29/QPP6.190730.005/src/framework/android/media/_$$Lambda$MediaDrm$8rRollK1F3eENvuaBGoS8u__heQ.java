/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaDrm;
import java.util.function.Consumer;

public final class _$$Lambda$MediaDrm$8rRollK1F3eENvuaBGoS8u__heQ
implements Consumer {
    private final /* synthetic */ MediaDrm f$0;
    private final /* synthetic */ MediaDrm.OnEventListener f$1;

    public /* synthetic */ _$$Lambda$MediaDrm$8rRollK1F3eENvuaBGoS8u__heQ(MediaDrm mediaDrm, MediaDrm.OnEventListener onEventListener) {
        this.f$0 = mediaDrm;
        this.f$1 = onEventListener;
    }

    public final void accept(Object object) {
        this.f$0.lambda$createOnEventListener$0$MediaDrm(this.f$1, (MediaDrm.ListenerArgs)object);
    }
}

