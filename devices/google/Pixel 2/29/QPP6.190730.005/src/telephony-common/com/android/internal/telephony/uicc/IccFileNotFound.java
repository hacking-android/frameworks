/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc;

import com.android.internal.telephony.uicc.IccException;

public class IccFileNotFound
extends IccException {
    IccFileNotFound() {
    }

    IccFileNotFound(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ICC EF Not Found 0x");
        stringBuilder.append(Integer.toHexString(n));
        super(stringBuilder.toString());
    }

    IccFileNotFound(String string) {
        super(string);
    }
}

