/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$EcGEDb4lqNEz5YyXQfIgXTUpv_c
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$EcGEDb4lqNEz5YyXQfIgXTUpv_c(int n) {
        this.f$0 = n;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$retrieveNotification$45(this.f$0, requestBuilder);
    }
}

