/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP-URUuDP17U
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP_URUuDP17U
implements EuiccCard.ApduIntermediateResultHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.dwMNgp0nb8jQ75klP-URUuDP17U INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP_URUuDP17U();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP_URUuDP17U() {
    }

    @Override
    public final boolean shouldContinue(IccIoResult iccIoResult) {
        return EuiccCard.lambda$loadBoundProfilePackage$38(iccIoResult);
    }
}

