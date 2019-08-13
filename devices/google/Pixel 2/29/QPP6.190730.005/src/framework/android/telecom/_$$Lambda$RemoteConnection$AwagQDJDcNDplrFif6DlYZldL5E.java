/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.RemoteConnection;

public final class _$$Lambda$RemoteConnection$AwagQDJDcNDplrFif6DlYZldL5E
implements Runnable {
    private final /* synthetic */ RemoteConnection.Callback f$0;
    private final /* synthetic */ RemoteConnection f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$RemoteConnection$AwagQDJDcNDplrFif6DlYZldL5E(RemoteConnection.Callback callback, RemoteConnection remoteConnection, int n) {
        this.f$0 = callback;
        this.f$1 = remoteConnection;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        RemoteConnection.lambda$onRttInitiationFailure$1(this.f$0, this.f$1, this.f$2);
    }
}

