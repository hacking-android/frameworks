/*
 * Decompiled with CFR 0.145.
 */
package android.os.image;

import android.os.RemoteException;
import android.os.image.DynamicSystemClient;

public final class _$$Lambda$DynamicSystemClient$DynSystemServiceConnection$Q_VWaYUew87mkpsE47b33p5XLa8
implements Runnable {
    private final /* synthetic */ DynamicSystemClient.DynSystemServiceConnection f$0;
    private final /* synthetic */ RemoteException f$1;

    public /* synthetic */ _$$Lambda$DynamicSystemClient$DynSystemServiceConnection$Q_VWaYUew87mkpsE47b33p5XLa8(DynamicSystemClient.DynSystemServiceConnection dynSystemServiceConnection, RemoteException remoteException) {
        this.f$0 = dynSystemServiceConnection;
        this.f$1 = remoteException;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onServiceConnected$0$DynamicSystemClient$DynSystemServiceConnection(this.f$1);
    }
}

