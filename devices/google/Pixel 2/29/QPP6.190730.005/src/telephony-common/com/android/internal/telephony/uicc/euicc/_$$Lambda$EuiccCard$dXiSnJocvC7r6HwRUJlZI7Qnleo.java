/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$dXiSnJocvC7r6HwRUJlZI7Qnleo
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ EuiccCard f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ byte[] f$2;
    private final /* synthetic */ byte[] f$3;
    private final /* synthetic */ byte[] f$4;
    private final /* synthetic */ byte[] f$5;

    public /* synthetic */ _$$Lambda$EuiccCard$dXiSnJocvC7r6HwRUJlZI7Qnleo(EuiccCard euiccCard, String string, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) {
        this.f$0 = euiccCard;
        this.f$1 = string;
        this.f$2 = arrby;
        this.f$3 = arrby2;
        this.f$4 = arrby3;
        this.f$5 = arrby4;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        this.f$0.lambda$authenticateServer$32$EuiccCard(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, requestBuilder);
    }
}

