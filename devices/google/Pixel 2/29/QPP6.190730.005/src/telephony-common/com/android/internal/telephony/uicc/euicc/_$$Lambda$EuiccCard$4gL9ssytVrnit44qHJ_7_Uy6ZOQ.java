/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ-7-Uy6ZOQ
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ_7_Uy6ZOQ
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.4gL9ssytVrnit44qHJ-7-Uy6ZOQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ_7_Uy6ZOQ();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ_7_Uy6ZOQ() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$setNickname$13(arrby);
    }
}

