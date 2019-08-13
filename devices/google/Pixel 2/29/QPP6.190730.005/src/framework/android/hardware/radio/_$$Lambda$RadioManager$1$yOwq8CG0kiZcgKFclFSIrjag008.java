/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.Announcement;
import android.hardware.radio.RadioManager;
import java.util.List;

public final class _$$Lambda$RadioManager$1$yOwq8CG0kiZcgKFclFSIrjag008
implements Runnable {
    private final /* synthetic */ Announcement.OnListUpdatedListener f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$RadioManager$1$yOwq8CG0kiZcgKFclFSIrjag008(Announcement.OnListUpdatedListener onListUpdatedListener, List list) {
        this.f$0 = onListUpdatedListener;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        RadioManager.1.lambda$onListUpdated$0(this.f$0, this.f$1);
    }
}

