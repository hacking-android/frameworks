/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.provider.BlockedNumberContract
 *  android.provider.BlockedNumberContract$SystemContract
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.BlockedNumberContract;
import android.telephony.Rlog;

public class AsyncEmergencyContactNotifier
extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "AsyncEmergencyContactNotifier";
    private final Context mContext;

    public AsyncEmergencyContactNotifier(Context context) {
        this.mContext = context;
    }

    protected Void doInBackground(Void ... arrvoid) {
        try {
            BlockedNumberContract.SystemContract.notifyEmergencyContact((Context)this.mContext);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception notifying emergency contact: ");
            stringBuilder.append(exception);
            Rlog.e((String)TAG, (String)stringBuilder.toString());
        }
        return null;
    }
}

