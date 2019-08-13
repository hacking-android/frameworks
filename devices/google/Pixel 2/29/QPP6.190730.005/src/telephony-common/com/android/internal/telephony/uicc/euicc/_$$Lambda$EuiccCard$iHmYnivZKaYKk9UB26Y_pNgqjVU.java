/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$iHmYnivZKaYKk9UB26Y_pNgqjVU
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$iHmYnivZKaYKk9UB26Y_pNgqjVU(int n) {
        this.f$0 = n;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$removeNotificationFromList$47(this.f$0, requestBuilder);
    }
}

