/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.view.contentcapture.ContentCaptureManager;
import com.android.internal.util.SyncResultReceiver;

public final class _$$Lambda$ContentCaptureManager$F5a5O5ubPHwlndmmnmOInl75_sQ
implements ContentCaptureManager.MyRunnable {
    private final /* synthetic */ ContentCaptureManager f$0;

    public /* synthetic */ _$$Lambda$ContentCaptureManager$F5a5O5ubPHwlndmmnmOInl75_sQ(ContentCaptureManager contentCaptureManager) {
        this.f$0 = contentCaptureManager;
    }

    @Override
    public final void run(SyncResultReceiver syncResultReceiver) {
        this.f$0.lambda$getContentCaptureConditions$0$ContentCaptureManager(syncResultReceiver);
    }
}

