/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$5wK_r0z9fLtA1ZRVlbk3WfOYXJI
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ byte[] f$0;
    private final /* synthetic */ byte[] f$1;
    private final /* synthetic */ byte[] f$2;
    private final /* synthetic */ byte[] f$3;

    public /* synthetic */ _$$Lambda$EuiccCard$5wK_r0z9fLtA1ZRVlbk3WfOYXJI(byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) {
        this.f$0 = arrby;
        this.f$1 = arrby2;
        this.f$2 = arrby3;
        this.f$3 = arrby4;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$prepareDownload$34(this.f$0, this.f$1, this.f$2, this.f$3, requestBuilder);
    }
}

