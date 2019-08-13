/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$fcz5l0a6JlSxs8MXCst7wXG4bUc
implements EuiccCard.ApduResponseHandler {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$fcz5l0a6JlSxs8MXCst7wXG4bUc(String string) {
        this.f$0 = string;
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$switchToProfile$9(this.f$0, arrby);
    }
}

