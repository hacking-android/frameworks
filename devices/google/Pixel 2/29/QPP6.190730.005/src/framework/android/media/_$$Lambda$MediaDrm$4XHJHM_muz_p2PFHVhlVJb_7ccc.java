/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaDrm;
import java.util.function.Consumer;

public final class _$$Lambda$MediaDrm$4XHJHM_muz_p2PFHVhlVJb_7ccc
implements Consumer {
    private final /* synthetic */ MediaDrm f$0;
    private final /* synthetic */ MediaDrm.OnSessionLostStateListener f$1;

    public /* synthetic */ _$$Lambda$MediaDrm$4XHJHM_muz_p2PFHVhlVJb_7ccc(MediaDrm mediaDrm, MediaDrm.OnSessionLostStateListener onSessionLostStateListener) {
        this.f$0 = mediaDrm;
        this.f$1 = onSessionLostStateListener;
    }

    public final void accept(Object object) {
        this.f$0.lambda$createOnSessionLostStateListener$3$MediaDrm(this.f$1, (MediaDrm.ListenerArgs)object);
    }
}

