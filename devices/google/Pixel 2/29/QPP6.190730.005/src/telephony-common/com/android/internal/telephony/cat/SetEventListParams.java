/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;

class SetEventListParams
extends CommandParams {
    int[] mEventInfo;

    SetEventListParams(CommandDetails commandDetails, int[] arrn) {
        super(commandDetails);
        this.mEventInfo = arrn;
    }
}

