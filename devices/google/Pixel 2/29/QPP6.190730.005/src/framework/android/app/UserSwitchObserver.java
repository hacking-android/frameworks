/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.IUserSwitchObserver;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;

public class UserSwitchObserver
extends IUserSwitchObserver.Stub {
    @Override
    public void onForegroundProfileSwitch(int n) throws RemoteException {
    }

    @Override
    public void onLockedBootComplete(int n) throws RemoteException {
    }

    @Override
    public void onUserSwitchComplete(int n) throws RemoteException {
    }

    @Override
    public void onUserSwitching(int n, IRemoteCallback iRemoteCallback) throws RemoteException {
        if (iRemoteCallback != null) {
            iRemoteCallback.sendResult(null);
        }
    }
}

