/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.v0S5B6MBAksDVSST9c1nk2Movvk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$prepareDownload$35(arrby);
    }
}

