/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ-YvOM
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ_YvOM
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.6M0Cvkh43ith8i9YF2YZNZ-YvOM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ_YvOM();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ_YvOM() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$deleteProfile$15(arrby);
    }
}

