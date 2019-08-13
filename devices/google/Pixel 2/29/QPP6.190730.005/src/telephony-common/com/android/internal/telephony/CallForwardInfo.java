/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telecom.Log
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telecom.Log;

public class CallForwardInfo {
    private static final String TAG = "CallForwardInfo";
    @UnsupportedAppUsage
    public String number;
    @UnsupportedAppUsage
    public int reason;
    @UnsupportedAppUsage
    public int serviceClass;
    @UnsupportedAppUsage
    public int status;
    @UnsupportedAppUsage
    public int timeSeconds;
    @UnsupportedAppUsage
    public int toa;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[CallForwardInfo: status=");
        String string = this.status == 0 ? " not active " : " active ";
        stringBuilder.append(string);
        stringBuilder.append(", reason= ");
        stringBuilder.append(this.reason);
        stringBuilder.append(", serviceClass= ");
        stringBuilder.append(this.serviceClass);
        stringBuilder.append(", timeSec= ");
        stringBuilder.append(this.timeSeconds);
        stringBuilder.append(" seconds, number=");
        stringBuilder.append(Log.pii((Object)this.number));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

