/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.os.IBinder;
import android.view.contentcapture.MainContentCaptureSession;

public final class _$$Lambda$MainContentCaptureSession$UWslDbWedtPhv49PtRsvG4TlYWw
implements IBinder.DeathRecipient {
    private final /* synthetic */ MainContentCaptureSession f$0;

    public /* synthetic */ _$$Lambda$MainContentCaptureSession$UWslDbWedtPhv49PtRsvG4TlYWw(MainContentCaptureSession mainContentCaptureSession) {
        this.f$0 = mainContentCaptureSession;
    }

    @Override
    public final void binderDied() {
        this.f$0.lambda$onSessionStarted$1$MainContentCaptureSession();
    }
}

