/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.IMmMA3gSh1g8aaHsYtCih61EKmo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$getRulesAuthTable$25(arrby);
    }
}

