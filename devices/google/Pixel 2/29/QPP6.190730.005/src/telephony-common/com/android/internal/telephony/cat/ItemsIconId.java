/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ValueObject;

class ItemsIconId
extends ValueObject {
    int[] recordNumbers;
    boolean selfExplanatory;

    ItemsIconId() {
    }

    @Override
    ComprehensionTlvTag getTag() {
        return ComprehensionTlvTag.ITEM_ICON_ID_LIST;
    }
}

