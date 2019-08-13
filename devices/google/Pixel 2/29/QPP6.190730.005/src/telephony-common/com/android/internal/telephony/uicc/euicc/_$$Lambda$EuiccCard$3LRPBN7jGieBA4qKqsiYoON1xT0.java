/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0
implements EuiccCard.ApduRequestBuilder {
    public static final /* synthetic */ -$.Lambda.EuiccCard.3LRPBN7jGieBA4qKqsiYoON1xT0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0() {
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$getDefaultSmdpAddress$18(requestBuilder);
    }
}

