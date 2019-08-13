/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$_VOB5FQfE7RUMgpmr8bK_j3CsUA
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$EuiccCard$_VOB5FQfE7RUMgpmr8bK_j3CsUA(String string, String string2) {
        this.f$0 = string;
        this.f$1 = string2;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$setNickname$12(this.f$0, this.f$1, requestBuilder);
    }
}

