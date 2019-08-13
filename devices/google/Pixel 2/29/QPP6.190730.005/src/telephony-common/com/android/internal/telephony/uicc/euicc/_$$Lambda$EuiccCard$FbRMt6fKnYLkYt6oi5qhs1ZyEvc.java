/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$FbRMt6fKnYLkYt6oi5qhs1ZyEvc
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$FbRMt6fKnYLkYt6oi5qhs1ZyEvc(String string) {
        this.f$0 = string;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$setDefaultSmdpAddress$22(this.f$0, requestBuilder);
    }
}

