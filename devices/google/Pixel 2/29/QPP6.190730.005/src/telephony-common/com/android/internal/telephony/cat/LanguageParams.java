/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;

class LanguageParams
extends CommandParams {
    String mLanguage;

    LanguageParams(CommandDetails commandDetails, String string) {
        super(commandDetails);
        this.mLanguage = string;
    }
}

