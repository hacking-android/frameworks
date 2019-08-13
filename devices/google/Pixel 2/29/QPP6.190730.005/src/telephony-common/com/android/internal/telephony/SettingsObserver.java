/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SettingsObserver
extends ContentObserver {
    private static final String TAG = "SettingsObserver";
    private final Context mContext;
    private final Handler mHandler;
    private final Map<Uri, Integer> mUriEventMap = new HashMap<Uri, Integer>();

    public SettingsObserver(Context context, Handler handler) {
        super(null);
        this.mContext = context;
        this.mHandler = handler;
    }

    public void observe(Uri uri, int n) {
        this.mUriEventMap.put(uri, n);
        this.mContext.getContentResolver().registerContentObserver(uri, false, (ContentObserver)this);
    }

    public void onChange(boolean bl) {
        Rlog.e((String)TAG, (String)"Should never be reached.");
    }

    public void onChange(boolean bl, Uri uri) {
        Serializable serializable = this.mUriEventMap.get((Object)uri);
        if (serializable != null) {
            this.mHandler.obtainMessage(((Integer)serializable).intValue()).sendToTarget();
        } else {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No matching event to send for URI=");
            ((StringBuilder)serializable).append((Object)uri);
            Rlog.e((String)TAG, (String)((StringBuilder)serializable).toString());
        }
    }

    public void unobserve() {
        this.mContext.getContentResolver().unregisterContentObserver((ContentObserver)this);
    }
}

