/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$AYHfF2w_VlO00s9p_djcPJl_1no
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$EuiccCard$AYHfF2w_VlO00s9p_djcPJl_1no(String string, boolean bl) {
        this.f$0 = string;
        this.f$1 = bl;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$switchToProfile$8(this.f$0, this.f$1, requestBuilder);
    }
}

