/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.telephony.DataFailCause
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.dataconnection;

import android.content.Intent;
import android.telephony.DataFailCause;
import android.telephony.Rlog;
import com.android.internal.telephony.dataconnection.DataConnection;

public class DcFailBringUp {
    static final String ACTION_FAIL_BRINGUP = "action_fail_bringup";
    static final String COUNTER = "counter";
    private static final boolean DBG = true;
    static final int DEFAULT_COUNTER = 2;
    static final int DEFAULT_FAIL_CAUSE = 65535;
    static final int DEFAULT_SUGGESTED_RETRY_TIME = -1;
    static final String FAIL_CAUSE = "fail_cause";
    static final String INTENT_BASE = DataConnection.class.getPackage().getName();
    private static final String LOG_TAG = "DcFailBringUp";
    static final String SUGGESTED_RETRY_TIME = "suggested_retry_time";
    int mCounter;
    int mFailCause;
    int mSuggestedRetryTime;

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    public void saveParameters(int n, int n2, int n3) {
        this.mCounter = n;
        this.mFailCause = DataFailCause.getFailCause((int)n2);
        this.mSuggestedRetryTime = n3;
    }

    void saveParameters(Intent object, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".saveParameters: action=");
        stringBuilder.append(object.getAction());
        DcFailBringUp.log(stringBuilder.toString());
        this.mCounter = object.getIntExtra(COUNTER, 2);
        this.mFailCause = DataFailCause.getFailCause((int)object.getIntExtra(FAIL_CAUSE, 65535));
        this.mSuggestedRetryTime = object.getIntExtra(SUGGESTED_RETRY_TIME, -1);
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(".saveParameters: ");
        ((StringBuilder)object).append(this);
        DcFailBringUp.log(((StringBuilder)object).toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mCounter=");
        stringBuilder.append(this.mCounter);
        stringBuilder.append(" mFailCause=");
        stringBuilder.append(this.mFailCause);
        stringBuilder.append(" mSuggestedRetryTime=");
        stringBuilder.append(this.mSuggestedRetryTime);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

