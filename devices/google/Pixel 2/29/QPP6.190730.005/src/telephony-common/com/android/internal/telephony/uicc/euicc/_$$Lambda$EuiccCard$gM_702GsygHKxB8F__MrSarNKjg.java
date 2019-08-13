/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$gM-702GsygHKxB8F-_MrSarNKjg
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$gM_702GsygHKxB8F__MrSarNKjg
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.gM-702GsygHKxB8F-_MrSarNKjg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$gM_702GsygHKxB8F__MrSarNKjg();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$gM_702GsygHKxB8F__MrSarNKjg() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$listNotifications$42(arrby);
    }
}

