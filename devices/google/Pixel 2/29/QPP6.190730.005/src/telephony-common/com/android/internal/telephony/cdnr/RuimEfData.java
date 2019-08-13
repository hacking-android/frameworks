/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.android.internal.telephony.cdnr;

import android.text.TextUtils;
import com.android.internal.telephony.cdnr.EfData;
import com.android.internal.telephony.uicc.RuimRecords;

public final class RuimEfData
implements EfData {
    private static final int DEFAULT_CARRIER_NAME_DISPLAY_CONDITION_BITMASK = 0;
    private final RuimRecords mRuim;

    public RuimEfData(RuimRecords ruimRecords) {
        this.mRuim = ruimRecords;
    }

    @Override
    public String getServiceProviderName() {
        String string;
        block0 : {
            string = this.mRuim.getServiceProviderName();
            if (!TextUtils.isEmpty((CharSequence)string)) break block0;
            string = null;
        }
        return string;
    }

    @Override
    public int getServiceProviderNameDisplayCondition() {
        int n = this.mRuim.getCsimSpnDisplayCondition() ? 2 : 0;
        return n;
    }
}

