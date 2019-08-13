/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;

public enum ComprehensionTlvTag {
    COMMAND_DETAILS(1),
    DEVICE_IDENTITIES(2),
    RESULT(3),
    DURATION(4),
    ALPHA_ID(5),
    ADDRESS(6),
    USSD_STRING(10),
    SMS_TPDU(11),
    TEXT_STRING(13),
    TONE(14),
    ITEM(15),
    ITEM_ID(16),
    RESPONSE_LENGTH(17),
    FILE_LIST(18),
    HELP_REQUEST(21),
    DEFAULT_TEXT(23),
    EVENT_LIST(25),
    ICON_ID(30),
    ITEM_ICON_ID_LIST(31),
    IMMEDIATE_RESPONSE(43),
    LANGUAGE(45),
    URL(49),
    BROWSER_TERMINATION_CAUSE(52),
    TEXT_ATTRIBUTE(80);
    
    private int mValue;

    private ComprehensionTlvTag(int n2) {
        this.mValue = n2;
    }

    public static ComprehensionTlvTag fromInt(int n) {
        for (ComprehensionTlvTag comprehensionTlvTag : ComprehensionTlvTag.values()) {
            if (comprehensionTlvTag.mValue != n) continue;
            return comprehensionTlvTag;
        }
        return null;
    }

    @UnsupportedAppUsage
    public int value() {
        return this.mValue;
    }
}

