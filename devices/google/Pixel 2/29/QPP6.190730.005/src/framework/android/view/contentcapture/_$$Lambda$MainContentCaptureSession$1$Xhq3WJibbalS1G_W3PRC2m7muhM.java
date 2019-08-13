/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.os.IBinder;
import android.view.contentcapture.MainContentCaptureSession;

public final class _$$Lambda$MainContentCaptureSession$1$Xhq3WJibbalS1G_W3PRC2m7muhM
implements Runnable {
    private final /* synthetic */ MainContentCaptureSession.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ IBinder f$2;

    public /* synthetic */ _$$Lambda$MainContentCaptureSession$1$Xhq3WJibbalS1G_W3PRC2m7muhM(MainContentCaptureSession.1 var1_1, int n, IBinder iBinder) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = iBinder;
    }

    @Override
    public final void run() {
        this.f$0.lambda$send$1$MainContentCaptureSession$1(this.f$1, this.f$2);
    }
}

