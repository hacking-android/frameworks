/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ValueObject;

class IconId
extends ValueObject {
    @UnsupportedAppUsage
    int recordNumber;
    boolean selfExplanatory;

    IconId() {
    }

    @Override
    ComprehensionTlvTag getTag() {
        return ComprehensionTlvTag.ICON_ID;
    }
}

