/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma.sms;

import android.util.SparseIntArray;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.util.HexDump;

public class UserData {
    public static final int ASCII_CR_INDEX = 13;
    public static final char[] ASCII_MAP;
    public static final int ASCII_MAP_BASE_INDEX = 32;
    public static final int ASCII_MAP_MAX_INDEX;
    public static final int ASCII_NL_INDEX = 10;
    public static final int ENCODING_7BIT_ASCII = 2;
    public static final int ENCODING_GSM_7BIT_ALPHABET = 9;
    public static final int ENCODING_GSM_DCS = 10;
    public static final int ENCODING_GSM_DCS_16BIT = 2;
    public static final int ENCODING_GSM_DCS_7BIT = 0;
    public static final int ENCODING_GSM_DCS_8BIT = 1;
    public static final int ENCODING_IA5 = 3;
    public static final int ENCODING_IS91_EXTENDED_PROTOCOL = 1;
    public static final int ENCODING_KOREAN = 6;
    public static final int ENCODING_LATIN = 8;
    public static final int ENCODING_LATIN_HEBREW = 7;
    public static final int ENCODING_OCTET = 0;
    public static final int ENCODING_SHIFT_JIS = 5;
    public static final int ENCODING_UNICODE_16 = 4;
    public static final int IS91_MSG_TYPE_CLI = 132;
    public static final int IS91_MSG_TYPE_SHORT_MESSAGE = 133;
    public static final int IS91_MSG_TYPE_SHORT_MESSAGE_FULL = 131;
    public static final int IS91_MSG_TYPE_VOICEMAIL_STATUS = 130;
    public static final int PRINTABLE_ASCII_MIN_INDEX = 32;
    static final byte UNENCODABLE_7_BIT_CHAR = 32;
    public static final SparseIntArray charToAscii;
    public int msgEncoding;
    public boolean msgEncodingSet = false;
    public int msgType;
    public int numFields;
    public int paddingBits;
    public byte[] payload;
    public String payloadStr;
    public SmsHeader userDataHeader;

    static {
        char[] arrc;
        ASCII_MAP = new char[]{' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~'};
        charToAscii = new SparseIntArray();
        for (int i = 0; i < (arrc = ASCII_MAP).length; ++i) {
            charToAscii.put(arrc[i], i + 32);
        }
        charToAscii.put(10, 10);
        charToAscii.put(13, 13);
        ASCII_MAP_MAX_INDEX = ASCII_MAP.length + 32 - 1;
    }

    public static byte[] stringToAscii(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            int n2 = charToAscii.get(string2.charAt(i), -1);
            if (n2 == -1) {
                return null;
            }
            arrby[i] = (byte)n2;
        }
        return arrby;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserData ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("{ msgEncoding=");
        Object object = this.msgEncodingSet ? Integer.valueOf(this.msgEncoding) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", msgType=");
        ((StringBuilder)object).append(this.msgType);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", paddingBits=");
        ((StringBuilder)object).append(this.paddingBits);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", numFields=");
        ((StringBuilder)object).append(this.numFields);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", userDataHeader=");
        ((StringBuilder)object).append(this.userDataHeader);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", payload='");
        ((StringBuilder)object).append(HexDump.toHexString(this.payload));
        ((StringBuilder)object).append("'");
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", payloadStr='");
        ((StringBuilder)object).append(this.payloadStr);
        ((StringBuilder)object).append("'");
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}

