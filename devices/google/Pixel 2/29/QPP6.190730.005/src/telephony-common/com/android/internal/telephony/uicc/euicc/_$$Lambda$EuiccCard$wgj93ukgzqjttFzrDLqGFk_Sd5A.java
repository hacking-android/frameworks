/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.wgj93ukgzqjttFzrDLqGFk_Sd5A INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$setDefaultSmdpAddress$23(arrby);
    }
}

