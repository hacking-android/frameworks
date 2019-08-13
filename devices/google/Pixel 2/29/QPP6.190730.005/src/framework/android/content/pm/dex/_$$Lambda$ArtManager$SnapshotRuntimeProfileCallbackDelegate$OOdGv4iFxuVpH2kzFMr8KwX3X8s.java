/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.content.pm.dex.ArtManager;
import android.os.ParcelFileDescriptor;

public final class _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$OOdGv4iFxuVpH2kzFMr8KwX3X8s
implements Runnable {
    private final /* synthetic */ ArtManager.SnapshotRuntimeProfileCallbackDelegate f$0;
    private final /* synthetic */ ParcelFileDescriptor f$1;

    public /* synthetic */ _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$OOdGv4iFxuVpH2kzFMr8KwX3X8s(ArtManager.SnapshotRuntimeProfileCallbackDelegate snapshotRuntimeProfileCallbackDelegate, ParcelFileDescriptor parcelFileDescriptor) {
        this.f$0 = snapshotRuntimeProfileCallbackDelegate;
        this.f$1 = parcelFileDescriptor;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuccess$0$ArtManager$SnapshotRuntimeProfileCallbackDelegate(this.f$1);
    }
}

