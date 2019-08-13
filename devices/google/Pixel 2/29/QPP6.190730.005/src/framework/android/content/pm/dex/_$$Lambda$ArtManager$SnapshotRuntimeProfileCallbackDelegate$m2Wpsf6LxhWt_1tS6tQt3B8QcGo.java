/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.content.pm.dex.ArtManager;

public final class _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$m2Wpsf6LxhWt_1tS6tQt3B8QcGo
implements Runnable {
    private final /* synthetic */ ArtManager.SnapshotRuntimeProfileCallbackDelegate f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ArtManager$SnapshotRuntimeProfileCallbackDelegate$m2Wpsf6LxhWt_1tS6tQt3B8QcGo(ArtManager.SnapshotRuntimeProfileCallbackDelegate snapshotRuntimeProfileCallbackDelegate, int n) {
        this.f$0 = snapshotRuntimeProfileCallbackDelegate;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$1$ArtManager$SnapshotRuntimeProfileCallbackDelegate(this.f$1);
    }
}

