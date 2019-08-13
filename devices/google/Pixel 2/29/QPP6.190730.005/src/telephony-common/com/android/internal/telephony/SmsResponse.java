/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;

public class SmsResponse {
    @UnsupportedAppUsage
    String mAckPdu;
    @UnsupportedAppUsage
    public int mErrorCode;
    @UnsupportedAppUsage
    int mMessageRef;

    @UnsupportedAppUsage
    public SmsResponse(int n, String string, int n2) {
        this.mMessageRef = n;
        this.mAckPdu = string;
        this.mErrorCode = n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ mMessageRef = ");
        stringBuilder.append(this.mMessageRef);
        stringBuilder.append(", mErrorCode = ");
        stringBuilder.append(this.mErrorCode);
        stringBuilder.append(", mAckPdu = ");
        stringBuilder.append(this.mAckPdu);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

