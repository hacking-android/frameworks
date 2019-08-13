/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$Rc41c7zRLip3RrHuKqZ_Sv7h8wI
implements EuiccCard.ApduResponseHandler {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$Rc41c7zRLip3RrHuKqZ_Sv7h8wI(String string) {
        this.f$0 = string;
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$disableProfile$7(this.f$0, arrby);
    }
}

