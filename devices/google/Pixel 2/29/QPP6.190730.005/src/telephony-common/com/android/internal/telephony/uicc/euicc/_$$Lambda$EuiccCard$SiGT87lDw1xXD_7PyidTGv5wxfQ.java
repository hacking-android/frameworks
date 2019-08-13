/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;
import com.android.internal.telephony.uicc.euicc.apdu.RequestProvider;

public final class _$$Lambda$EuiccCard$SiGT87lDw1xXD_7PyidTGv5wxfQ
implements RequestProvider {
    private final /* synthetic */ EuiccCard f$0;
    private final /* synthetic */ EuiccCard.ApduRequestBuilder f$1;

    public /* synthetic */ _$$Lambda$EuiccCard$SiGT87lDw1xXD_7PyidTGv5wxfQ(EuiccCard euiccCard, EuiccCard.ApduRequestBuilder apduRequestBuilder) {
        this.f$0 = euiccCard;
        this.f$1 = apduRequestBuilder;
    }

    @Override
    public final void buildRequest(byte[] arrby, RequestBuilder requestBuilder) {
        this.f$0.lambda$newRequestProvider$49$EuiccCard(this.f$1, arrby, requestBuilder);
    }
}

