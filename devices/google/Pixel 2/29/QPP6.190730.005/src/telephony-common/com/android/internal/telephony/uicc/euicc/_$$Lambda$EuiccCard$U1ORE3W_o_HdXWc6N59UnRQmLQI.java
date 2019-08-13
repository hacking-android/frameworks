/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.EuiccSpecVersion;

public final class _$$Lambda$EuiccCard$U1ORE3W_o_HdXWc6N59UnRQmLQI
implements EuiccCard.ApduResponseHandler {
    private final /* synthetic */ EuiccCard f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$U1ORE3W_o_HdXWc6N59UnRQmLQI(EuiccCard euiccCard) {
        this.f$0 = euiccCard;
    }

    public final Object handleResult(byte[] arrby) {
        return this.f$0.lambda$getSpecVersion$1$EuiccCard(arrby);
    }
}

