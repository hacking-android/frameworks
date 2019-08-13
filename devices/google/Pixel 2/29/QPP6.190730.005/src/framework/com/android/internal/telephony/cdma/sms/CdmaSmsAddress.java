/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma.sms;

import android.util.SparseBooleanArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.util.HexDump;

public class CdmaSmsAddress
extends SmsAddress {
    public static final int DIGIT_MODE_4BIT_DTMF = 0;
    public static final int DIGIT_MODE_8BIT_CHAR = 1;
    public static final int NUMBERING_PLAN_ISDN_TELEPHONY = 1;
    public static final int NUMBERING_PLAN_UNKNOWN = 0;
    public static final int NUMBER_MODE_DATA_NETWORK = 1;
    public static final int NUMBER_MODE_NOT_DATA_NETWORK = 0;
    public static final int SMS_ADDRESS_MAX = 36;
    public static final int SMS_SUBADDRESS_MAX = 36;
    public static final int TON_ABBREVIATED = 6;
    public static final int TON_ALPHANUMERIC = 5;
    public static final int TON_INTERNATIONAL_OR_IP = 1;
    public static final int TON_NATIONAL_OR_EMAIL = 2;
    public static final int TON_NETWORK = 3;
    public static final int TON_RESERVED = 7;
    public static final int TON_SUBSCRIBER = 4;
    public static final int TON_UNKNOWN = 0;
    private static final SparseBooleanArray numericCharDialableMap;
    private static final char[] numericCharsDialable;
    private static final char[] numericCharsSugar;
    public int digitMode;
    public int numberMode;
    public int numberOfDigits;
    public int numberPlan;

    static {
        int n;
        char[] arrc;
        numericCharsDialable = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '*', '#'};
        numericCharsSugar = new char[]{'(', ')', ' ', '-', '+', '.', '/', '\\'};
        numericCharDialableMap = new SparseBooleanArray(numericCharsDialable.length + numericCharsSugar.length);
        for (n = 0; n < (arrc = numericCharsDialable).length; ++n) {
            numericCharDialableMap.put(arrc[n], true);
        }
        for (n = 0; n < (arrc = numericCharsSugar).length; ++n) {
            numericCharDialableMap.put(arrc[n], false);
        }
    }

    private static String filterNumericSugar(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            int n2 = numericCharDialableMap.indexOfKey(c);
            if (n2 < 0) {
                return null;
            }
            if (!numericCharDialableMap.valueAt(n2)) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static String filterWhitespace(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static CdmaSmsAddress parse(String arrby) {
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        cdmaSmsAddress.address = arrby;
        cdmaSmsAddress.ton = 0;
        cdmaSmsAddress.digitMode = 0;
        cdmaSmsAddress.numberPlan = 0;
        cdmaSmsAddress.numberMode = 0;
        String string2 = CdmaSmsAddress.filterNumericSugar((String)arrby);
        if (!arrby.contains("+") && string2 != null) {
            arrby = CdmaSmsAddress.parseToDtmf(string2);
        } else {
            cdmaSmsAddress.digitMode = 1;
            cdmaSmsAddress.numberMode = 1;
            String string3 = CdmaSmsAddress.filterWhitespace((String)arrby);
            if (arrby.contains("@")) {
                cdmaSmsAddress.ton = 2;
                string2 = string3;
            } else {
                string2 = string3;
                if (arrby.contains("+")) {
                    string2 = string3;
                    if (CdmaSmsAddress.filterNumericSugar((String)arrby) != null) {
                        cdmaSmsAddress.ton = 1;
                        cdmaSmsAddress.numberPlan = 1;
                        cdmaSmsAddress.numberMode = 0;
                        string2 = CdmaSmsAddress.filterNumericSugar((String)arrby);
                    }
                }
            }
            arrby = UserData.stringToAscii(string2);
        }
        if (arrby == null) {
            return null;
        }
        cdmaSmsAddress.origBytes = arrby;
        cdmaSmsAddress.numberOfDigits = arrby.length;
        return cdmaSmsAddress;
    }

    @VisibleForTesting
    public static byte[] parseToDtmf(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            block7 : {
                int n2;
                block4 : {
                    block6 : {
                        block5 : {
                            block3 : {
                                n2 = string2.charAt(i);
                                if (n2 < 49 || n2 > 57) break block3;
                                n2 -= 48;
                                break block4;
                            }
                            if (n2 != 48) break block5;
                            n2 = 10;
                            break block4;
                        }
                        if (n2 != 42) break block6;
                        n2 = 11;
                        break block4;
                    }
                    if (n2 != 35) break block7;
                    n2 = 12;
                }
                arrby[i] = (byte)n2;
                continue;
            }
            return null;
        }
        return arrby;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CdmaSmsAddress ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("{ digitMode=");
        stringBuilder2.append(this.digitMode);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", numberMode=");
        stringBuilder2.append(this.numberMode);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", numberPlan=");
        stringBuilder2.append(this.numberPlan);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", numberOfDigits=");
        stringBuilder2.append(this.numberOfDigits);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", ton=");
        stringBuilder2.append(this.ton);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", address=\"");
        stringBuilder2.append(this.address);
        stringBuilder2.append("\"");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", origBytes=");
        stringBuilder2.append(HexDump.toHexString(this.origBytes));
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}

