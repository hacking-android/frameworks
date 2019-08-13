/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

public class ShutdownActivity
extends Activity {
    private static final String TAG = "ShutdownActivity";
    private boolean mConfirm;
    private boolean mReboot;
    private boolean mUserRequested;

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getIntent();
        this.mReboot = "android.intent.action.REBOOT".equals(((Intent)object).getAction());
        this.mConfirm = ((Intent)object).getBooleanExtra("android.intent.extra.KEY_CONFIRM", false);
        this.mUserRequested = ((Intent)object).getBooleanExtra("android.intent.extra.USER_REQUESTED_SHUTDOWN", false);
        object = this.mUserRequested ? "userrequested" : ((Intent)object).getStringExtra("android.intent.extra.REASON");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onCreate(): confirm=");
        stringBuilder.append(this.mConfirm);
        Slog.i(TAG, stringBuilder.toString());
        object = new Thread(TAG, (String)object){
            final /* synthetic */ String val$reason;
            {
                this.val$reason = string3;
                super(string2);
            }

            @Override
            public void run() {
                IPowerManager iPowerManager = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
                try {
                    if (ShutdownActivity.this.mReboot) {
                        iPowerManager.reboot(ShutdownActivity.this.mConfirm, null, false);
                    } else {
                        iPowerManager.shutdown(ShutdownActivity.this.mConfirm, this.val$reason, false);
                    }
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        };
        ((Thread)object).start();
        this.finish();
        try {
            ((Thread)object).join();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

}

