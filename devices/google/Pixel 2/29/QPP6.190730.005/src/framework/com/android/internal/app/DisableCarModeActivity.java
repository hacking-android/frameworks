/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.IUiModeManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public class DisableCarModeActivity
extends Activity {
    private static final String TAG = "DisableCarModeActivity";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            IUiModeManager.Stub.asInterface(ServiceManager.getService("uimode")).disableCarMode(1);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to disable car mode", remoteException);
        }
        this.finish();
    }
}

