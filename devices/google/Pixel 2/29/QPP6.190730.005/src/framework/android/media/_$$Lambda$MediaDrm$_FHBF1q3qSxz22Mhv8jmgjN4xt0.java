/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaDrm;
import java.util.function.Consumer;

public final class _$$Lambda$MediaDrm$_FHBF1q3qSxz22Mhv8jmgjN4xt0
implements Consumer {
    private final /* synthetic */ MediaDrm f$0;
    private final /* synthetic */ MediaDrm.OnKeyStatusChangeListener f$1;

    public /* synthetic */ _$$Lambda$MediaDrm$_FHBF1q3qSxz22Mhv8jmgjN4xt0(MediaDrm mediaDrm, MediaDrm.OnKeyStatusChangeListener onKeyStatusChangeListener) {
        this.f$0 = mediaDrm;
        this.f$1 = onKeyStatusChangeListener;
    }

    public final void accept(Object object) {
        this.f$0.lambda$createOnKeyStatusChangeListener$1$MediaDrm(this.f$1, (MediaDrm.ListenerArgs)object);
    }
}

