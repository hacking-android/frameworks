/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$0NUjmK32-r6146hGb0RCJUAfiOg
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$0NUjmK32_r6146hGb0RCJUAfiOg
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.0NUjmK32-r6146hGb0RCJUAfiOg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$0NUjmK32_r6146hGb0RCJUAfiOg();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$0NUjmK32_r6146hGb0RCJUAfiOg() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$resetMemory$17(arrby);
    }
}

