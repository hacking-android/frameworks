/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$00j_sPLzMkCJBnrpRWJA8rfmUIY
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$00j_sPLzMkCJBnrpRWJA8rfmUIY(int n) {
        this.f$0 = n;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$listNotifications$41(this.f$0, requestBuilder);
    }
}

