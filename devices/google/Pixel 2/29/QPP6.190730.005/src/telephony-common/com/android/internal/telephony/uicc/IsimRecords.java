/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;

public interface IsimRecords {
    @UnsupportedAppUsage
    public String getIsimDomain();

    @UnsupportedAppUsage
    public String getIsimImpi();

    @UnsupportedAppUsage
    public String[] getIsimImpu();

    public String getIsimIst();

    public String[] getIsimPcscf();
}

