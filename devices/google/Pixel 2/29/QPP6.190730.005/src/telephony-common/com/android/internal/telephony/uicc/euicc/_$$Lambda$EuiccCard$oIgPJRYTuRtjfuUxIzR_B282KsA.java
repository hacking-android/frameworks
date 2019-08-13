/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.oIgPJRYTuRtjfuUxIzR_B282KsA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$cancelSession$40(arrby);
    }
}

