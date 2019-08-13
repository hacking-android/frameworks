/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaDrm;
import java.util.function.Consumer;

public final class _$$Lambda$MediaDrm$btxNighXxrJ0k5ooHZIA_tMesRA
implements Consumer {
    private final /* synthetic */ MediaDrm f$0;
    private final /* synthetic */ MediaDrm.OnExpirationUpdateListener f$1;

    public /* synthetic */ _$$Lambda$MediaDrm$btxNighXxrJ0k5ooHZIA_tMesRA(MediaDrm mediaDrm, MediaDrm.OnExpirationUpdateListener onExpirationUpdateListener) {
        this.f$0 = mediaDrm;
        this.f$1 = onExpirationUpdateListener;
    }

    public final void accept(Object object) {
        this.f$0.lambda$createOnExpirationUpdateListener$2$MediaDrm(this.f$1, (MediaDrm.ListenerArgs)object);
    }
}

