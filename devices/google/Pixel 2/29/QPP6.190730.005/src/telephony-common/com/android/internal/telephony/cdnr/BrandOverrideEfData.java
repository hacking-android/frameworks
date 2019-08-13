/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdnr;

import com.android.internal.telephony.cdnr.EfData;
import java.util.Arrays;
import java.util.List;

public final class BrandOverrideEfData
implements EfData {
    private final String mRegisteredPlmn;
    private final String mSpn;

    public BrandOverrideEfData(String string, String string2) {
        this.mSpn = string;
        this.mRegisteredPlmn = string2;
    }

    @Override
    public List<String> getServiceProviderDisplayInformation() {
        return Arrays.asList(this.mRegisteredPlmn);
    }

    @Override
    public String getServiceProviderName() {
        return this.mSpn;
    }

    @Override
    public int getServiceProviderNameDisplayCondition() {
        return 0;
    }
}

