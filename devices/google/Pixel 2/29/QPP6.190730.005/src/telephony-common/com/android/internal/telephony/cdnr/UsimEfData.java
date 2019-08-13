/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.android.internal.telephony.cdnr;

import android.text.TextUtils;
import com.android.internal.telephony.cdnr.EfData;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.SIMRecords;
import java.util.Arrays;
import java.util.List;

public final class UsimEfData
implements EfData {
    private final SIMRecords mUsim;

    public UsimEfData(SIMRecords sIMRecords) {
        this.mUsim = sIMRecords;
    }

    @Override
    public List<String> getEhplmnList() {
        Object object = this.mUsim.getEhplmns();
        object = object != null ? Arrays.asList(object) : null;
        return object;
    }

    @Override
    public List<IccRecords.OperatorPlmnInfo> getOperatorPlmnList() {
        return null;
    }

    @Override
    public List<IccRecords.PlmnNetworkName> getPlmnNetworkNameList() {
        String string = this.mUsim.getPnnHomeName();
        if (!TextUtils.isEmpty((CharSequence)string)) {
            return Arrays.asList(new IccRecords.PlmnNetworkName(string, ""));
        }
        return null;
    }

    @Override
    public List<String> getServiceProviderDisplayInformation() {
        Object object = this.mUsim.getServiceProviderDisplayInformation();
        object = object != null ? Arrays.asList(object) : null;
        return object;
    }

    @Override
    public String getServiceProviderName() {
        String string;
        String string2 = string = this.mUsim.getServiceProviderName();
        if (TextUtils.isEmpty((CharSequence)string)) {
            string2 = null;
        }
        return string2;
    }

    @Override
    public int getServiceProviderNameDisplayCondition() {
        return this.mUsim.getCarrierNameDisplayCondition();
    }
}

