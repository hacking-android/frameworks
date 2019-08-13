/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.view.contentcapture.MainContentCaptureSession;

public final class _$$Lambda$MainContentCaptureSession$49zT7C2BXrEdkyggyGk1Qs4d46k
implements Runnable {
    private final /* synthetic */ MainContentCaptureSession f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$MainContentCaptureSession$49zT7C2BXrEdkyggyGk1Qs4d46k(MainContentCaptureSession mainContentCaptureSession, int n) {
        this.f$0 = mainContentCaptureSession;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$scheduleFlush$2$MainContentCaptureSession(this.f$1);
    }
}

