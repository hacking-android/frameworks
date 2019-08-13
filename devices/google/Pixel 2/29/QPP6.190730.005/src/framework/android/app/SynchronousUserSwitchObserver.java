/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.UserSwitchObserver;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;

public abstract class SynchronousUserSwitchObserver
extends UserSwitchObserver {
    public abstract void onUserSwitching(int var1) throws RemoteException;

    @Override
    public final void onUserSwitching(int n, IRemoteCallback iRemoteCallback) throws RemoteException {
        try {
            this.onUserSwitching(n);
            return;
        }
        finally {
            if (iRemoteCallback != null) {
                iRemoteCallback.sendResult(null);
            }
        }
    }
}

