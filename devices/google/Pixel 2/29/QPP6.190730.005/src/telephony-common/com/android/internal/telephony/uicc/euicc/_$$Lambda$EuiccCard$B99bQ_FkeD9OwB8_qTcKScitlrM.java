/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$B99bQ-FkeD9OwB8_qTcKScitlrM
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$B99bQ_FkeD9OwB8_qTcKScitlrM
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.B99bQ-FkeD9OwB8_qTcKScitlrM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$B99bQ_FkeD9OwB8_qTcKScitlrM();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$B99bQ_FkeD9OwB8_qTcKScitlrM() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$getAllProfiles$3(arrby);
    }
}

