/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.AGpR_ArLREPF7xVOCf0sgHwbDtA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$getEuiccChallenge$27(arrby);
    }
}

