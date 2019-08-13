/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.TTvsStUIyUFrPpvGTlsjBCy3NyM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$getProfile$5(arrby);
    }
}

