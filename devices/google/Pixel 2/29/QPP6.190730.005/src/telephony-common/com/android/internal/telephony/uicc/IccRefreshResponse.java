/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;

public class IccRefreshResponse {
    public static final int REFRESH_RESULT_FILE_UPDATE = 0;
    public static final int REFRESH_RESULT_INIT = 1;
    public static final int REFRESH_RESULT_RESET = 2;
    @UnsupportedAppUsage
    public String aid;
    @UnsupportedAppUsage
    public int efId;
    @UnsupportedAppUsage
    public int refreshResult;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.refreshResult);
        stringBuilder.append(", ");
        stringBuilder.append(this.aid);
        stringBuilder.append(", ");
        stringBuilder.append(this.efId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

