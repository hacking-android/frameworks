/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc.euicc.async;

import android.telephony.Rlog;

public abstract class AsyncResultCallback<Result> {
    private static final String LOG_TAG = "AsyncResultCallback";

    public void onException(Throwable throwable) {
        Rlog.e((String)LOG_TAG, (String)"Error in onException", (Throwable)throwable);
    }

    public abstract void onResult(Result var1);
}

