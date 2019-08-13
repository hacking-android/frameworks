/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.view.contentcapture.ContentCaptureManager;
import com.android.internal.util.SyncResultReceiver;

public final class _$$Lambda$ContentCaptureManager$uvjEvSXcmP7_uA6i89N3m1TrKCk
implements ContentCaptureManager.MyRunnable {
    private final /* synthetic */ ContentCaptureManager f$0;

    public /* synthetic */ _$$Lambda$ContentCaptureManager$uvjEvSXcmP7_uA6i89N3m1TrKCk(ContentCaptureManager contentCaptureManager) {
        this.f$0 = contentCaptureManager;
    }

    @Override
    public final void run(SyncResultReceiver syncResultReceiver) {
        this.f$0.lambda$isContentCaptureFeatureEnabled$1$ContentCaptureManager(syncResultReceiver);
    }
}

