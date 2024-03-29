/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.PhoneNumberUtils
 */
package com.android.internal.telephony.gsm;

import android.telephony.PhoneNumberUtils;

public class SuppServiceNotification {
    public static final int CODE_1_CALL_DEFLECTED = 8;
    public static final int CODE_1_CALL_FORWARDED = 2;
    public static final int CODE_1_CALL_IS_WAITING = 3;
    public static final int CODE_1_CLIR_SUPPRESSION_REJECTED = 7;
    public static final int CODE_1_CUG_CALL = 4;
    public static final int CODE_1_INCOMING_CALLS_BARRED = 6;
    public static final int CODE_1_OUTGOING_CALLS_BARRED = 5;
    public static final int CODE_1_SOME_CF_ACTIVE = 1;
    public static final int CODE_1_UNCONDITIONAL_CF_ACTIVE = 0;
    public static final int CODE_2_ADDITIONAL_CALL_FORWARDED = 10;
    public static final int CODE_2_CALL_CONNECTED_ECT = 8;
    public static final int CODE_2_CALL_CONNECTING_ECT = 7;
    public static final int CODE_2_CALL_ON_HOLD = 2;
    public static final int CODE_2_CALL_RETRIEVED = 3;
    public static final int CODE_2_CUG_CALL = 1;
    public static final int CODE_2_DEFLECTED_CALL = 9;
    public static final int CODE_2_FORWARDED_CALL = 0;
    public static final int CODE_2_FORWARD_CHECK_RECEIVED = 6;
    public static final int CODE_2_MULTI_PARTY_CALL = 4;
    public static final int CODE_2_ON_HOLD_CALL_RELEASED = 5;
    public static final int NOTIFICATION_TYPE_CODE_1 = 0;
    public static final int NOTIFICATION_TYPE_CODE_2 = 1;
    public int code;
    public String[] history;
    public int index;
    public int notificationType;
    public String number;
    public int type;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" mobile");
        String string = this.notificationType == 0 ? " originated " : " terminated ";
        stringBuilder.append(string);
        stringBuilder.append(" code: ");
        stringBuilder.append(this.code);
        stringBuilder.append(" index: ");
        stringBuilder.append(this.index);
        stringBuilder.append(" history: ");
        stringBuilder.append(this.history);
        stringBuilder.append(" \"");
        stringBuilder.append(PhoneNumberUtils.stringFromStringAndTOA((String)this.number, (int)this.type));
        stringBuilder.append("\" ");
        return stringBuilder.toString();
    }
}

