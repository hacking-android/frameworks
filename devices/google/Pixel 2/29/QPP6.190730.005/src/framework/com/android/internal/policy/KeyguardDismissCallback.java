/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.os.RemoteException;
import com.android.internal.policy.IKeyguardDismissCallback;

public class KeyguardDismissCallback
extends IKeyguardDismissCallback.Stub {
    @Override
    public void onDismissCancelled() throws RemoteException {
    }

    @Override
    public void onDismissError() throws RemoteException {
    }

    @Override
    public void onDismissSucceeded() throws RemoteException {
    }
}

