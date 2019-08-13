/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$okradEAowCk8rNBK1OaJIA6l6eA
implements EuiccCard.ApduResponseHandler {
    private final /* synthetic */ EuiccCard f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$okradEAowCk8rNBK1OaJIA6l6eA(EuiccCard euiccCard) {
        this.f$0 = euiccCard;
    }

    public final Object handleResult(byte[] arrby) {
        return this.f$0.lambda$getEid$11$EuiccCard(arrby);
    }
}

