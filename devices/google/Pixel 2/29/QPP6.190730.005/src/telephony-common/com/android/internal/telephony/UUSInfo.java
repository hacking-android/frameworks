/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;

public class UUSInfo {
    public static final int UUS_DCS_IA5c = 4;
    public static final int UUS_DCS_OSIHLP = 1;
    public static final int UUS_DCS_RMCF = 3;
    public static final int UUS_DCS_USP = 0;
    public static final int UUS_DCS_X244 = 2;
    public static final int UUS_TYPE1_IMPLICIT = 0;
    public static final int UUS_TYPE1_NOT_REQUIRED = 2;
    public static final int UUS_TYPE1_REQUIRED = 1;
    public static final int UUS_TYPE2_NOT_REQUIRED = 4;
    public static final int UUS_TYPE2_REQUIRED = 3;
    public static final int UUS_TYPE3_NOT_REQUIRED = 6;
    public static final int UUS_TYPE3_REQUIRED = 5;
    private byte[] mUusData;
    private int mUusDcs;
    private int mUusType;

    public UUSInfo() {
        this.mUusType = 0;
        this.mUusDcs = 4;
        this.mUusData = null;
    }

    public UUSInfo(int n, int n2, byte[] arrby) {
        this.mUusType = n;
        this.mUusDcs = n2;
        this.mUusData = arrby;
    }

    @UnsupportedAppUsage
    public int getDcs() {
        return this.mUusDcs;
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.mUusType;
    }

    @UnsupportedAppUsage
    public byte[] getUserData() {
        return this.mUusData;
    }

    public void setDcs(int n) {
        this.mUusDcs = n;
    }

    public void setType(int n) {
        this.mUusType = n;
    }

    public void setUserData(byte[] arrby) {
        this.mUusData = arrby;
    }
}

