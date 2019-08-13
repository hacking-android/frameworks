/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.cat.ResultCode;

class RilMessage {
    @UnsupportedAppUsage
    Object mData;
    @UnsupportedAppUsage
    int mId;
    ResultCode mResCode;

    @UnsupportedAppUsage
    RilMessage(int n, String string) {
        this.mId = n;
        this.mData = string;
    }

    RilMessage(RilMessage rilMessage) {
        this.mId = rilMessage.mId;
        this.mData = rilMessage.mData;
        this.mResCode = rilMessage.mResCode;
    }
}

