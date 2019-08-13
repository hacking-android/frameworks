/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCard;

public final class _$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U
implements EuiccCard.ApduResponseHandler {
    public static final /* synthetic */ -$.Lambda.EuiccCard.NcqG_zW56i_tsv86TpwlBqIvg4U INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U();
    }

    private /* synthetic */ _$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U() {
    }

    public final Object handleResult(byte[] arrby) {
        return EuiccCard.lambda$retrieveNotificationList$44(arrby);
    }
}

