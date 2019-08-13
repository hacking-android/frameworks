/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdnr;

import com.android.internal.telephony.cdnr.EfData;

public final class EriEfData
implements EfData {
    private final String mEriText;

    public EriEfData(String string) {
        this.mEriText = string;
    }

    @Override
    public String getServiceProviderName() {
        return this.mEriText;
    }
}

