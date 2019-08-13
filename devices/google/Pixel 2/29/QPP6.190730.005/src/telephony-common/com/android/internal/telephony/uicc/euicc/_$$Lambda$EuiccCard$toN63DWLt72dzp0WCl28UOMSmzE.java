/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE
implements EuiccCard.ApduRequestBuilder {
    public static final /* synthetic */ -$.Lambda.EuiccCard.toN63DWLt72dzp0WCl28UOMSmzE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE() {
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$getAllProfiles$2(requestBuilder);
    }
}

