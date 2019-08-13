/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;

public class RadioCapability {
    private static final int RADIO_CAPABILITY_VERSION = 1;
    public static final int RC_PHASE_APPLY = 2;
    public static final int RC_PHASE_CONFIGURED = 0;
    public static final int RC_PHASE_FINISH = 4;
    public static final int RC_PHASE_START = 1;
    public static final int RC_PHASE_UNSOL_RSP = 3;
    public static final int RC_STATUS_FAIL = 2;
    public static final int RC_STATUS_NONE = 0;
    public static final int RC_STATUS_SUCCESS = 1;
    private String mLogicalModemUuid;
    private int mPhase;
    private int mPhoneId;
    private int mRadioAccessFamily;
    private int mSession;
    private int mStatus;

    public RadioCapability(int n, int n2, int n3, int n4, String string, int n5) {
        this.mPhoneId = n;
        this.mSession = n2;
        this.mPhase = n3;
        this.mRadioAccessFamily = n4;
        this.mLogicalModemUuid = string;
        this.mStatus = n5;
    }

    public String getLogicalModemUuid() {
        return this.mLogicalModemUuid;
    }

    public int getPhase() {
        return this.mPhase;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    @UnsupportedAppUsage
    public int getRadioAccessFamily() {
        return this.mRadioAccessFamily;
    }

    public int getSession() {
        return this.mSession;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getVersion() {
        return 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mPhoneId = ");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append(" mVersion=");
        stringBuilder.append(this.getVersion());
        stringBuilder.append(" mSession=");
        stringBuilder.append(this.getSession());
        stringBuilder.append(" mPhase=");
        stringBuilder.append(this.getPhase());
        stringBuilder.append(" mRadioAccessFamily=");
        stringBuilder.append(this.getRadioAccessFamily());
        stringBuilder.append(" mLogicModemId=");
        stringBuilder.append(this.getLogicalModemUuid());
        stringBuilder.append(" mStatus=");
        stringBuilder.append(this.getStatus());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

