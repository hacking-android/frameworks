/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.RemoteConnection;

public final class _$$Lambda$RemoteConnection$mmHouQhUco_u9PRJ9qkMqlkKzAs
implements Runnable {
    private final /* synthetic */ RemoteConnection.Callback f$0;
    private final /* synthetic */ RemoteConnection f$1;

    public /* synthetic */ _$$Lambda$RemoteConnection$mmHouQhUco_u9PRJ9qkMqlkKzAs(RemoteConnection.Callback callback, RemoteConnection remoteConnection) {
        this.f$0 = callback;
        this.f$1 = remoteConnection;
    }

    @Override
    public final void run() {
        RemoteConnection.lambda$onRttSessionRemotelyTerminated$2(this.f$0, this.f$1);
    }
}

