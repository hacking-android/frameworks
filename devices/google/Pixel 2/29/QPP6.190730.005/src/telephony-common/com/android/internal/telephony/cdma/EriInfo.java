/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma;

public final class EriInfo {
    public static final int ROAMING_ICON_MODE_FLASH = 1;
    public static final int ROAMING_ICON_MODE_NORMAL = 0;
    public static final int ROAMING_INDICATOR_FLASH = 2;
    public static final int ROAMING_INDICATOR_OFF = 1;
    public static final int ROAMING_INDICATOR_ON = 0;
    public int alertId;
    public int callPromptId;
    public String eriText;
    public int iconIndex;
    public int iconMode;
    public int roamingIndicator;

    public EriInfo(int n, int n2, int n3, String string, int n4, int n5) {
        this.roamingIndicator = n;
        this.iconIndex = n2;
        this.iconMode = n3;
        this.eriText = string;
        this.callPromptId = n4;
        this.alertId = n5;
    }
}

