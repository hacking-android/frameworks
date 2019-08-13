/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$8wofF-Li1V6a8rJQc-M2IGeJ26E
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$8wofF_Li1V6a8rJQc_M2IGeJ26E
implements EuiccCard.ApduRequestBuilder {
    public static final /* synthetic */ -$.Lambda.EuiccCard.8wofF-Li1V6a8rJQc-M2IGeJ26E INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$8wofF_Li1V6a8rJQc_M2IGeJ26E();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$8wofF_Li1V6a8rJQc_M2IGeJ26E() {
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$getEuiccChallenge$26(requestBuilder);
    }
}

