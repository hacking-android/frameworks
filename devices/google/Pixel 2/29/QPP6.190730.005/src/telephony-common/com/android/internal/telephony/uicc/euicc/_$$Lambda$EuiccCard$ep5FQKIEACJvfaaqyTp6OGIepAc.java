/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$ep5FQKIEACJvfaaqyTp6OGIepAc
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ byte[] f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$EuiccCard$ep5FQKIEACJvfaaqyTp6OGIepAc(byte[] arrby, int n) {
        this.f$0 = arrby;
        this.f$1 = n;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$cancelSession$39(this.f$0, this.f$1, requestBuilder);
    }
}

