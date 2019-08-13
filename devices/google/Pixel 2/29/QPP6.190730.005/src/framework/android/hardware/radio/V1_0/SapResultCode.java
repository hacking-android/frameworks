/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SapResultCode {
    public static final int CARD_ALREADY_POWERED_OFF = 3;
    public static final int CARD_ALREADY_POWERED_ON = 5;
    public static final int CARD_NOT_ACCESSSIBLE = 2;
    public static final int CARD_REMOVED = 4;
    public static final int DATA_NOT_AVAILABLE = 6;
    public static final int GENERIC_FAILURE = 1;
    public static final int NOT_SUPPORTED = 7;
    public static final int SUCCESS = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("SUCCESS");
        if ((n & 1) == 1) {
            arrayList.add("GENERIC_FAILURE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CARD_NOT_ACCESSSIBLE");
            n3 = n2 | 2;
        }
        int n4 = n3;
        if ((n & 3) == 3) {
            arrayList.add("CARD_ALREADY_POWERED_OFF");
            n4 = n3 | 3;
        }
        n2 = n4;
        if ((n & 4) == 4) {
            arrayList.add("CARD_REMOVED");
            n2 = n4 | 4;
        }
        n3 = n2;
        if ((n & 5) == 5) {
            arrayList.add("CARD_ALREADY_POWERED_ON");
            n3 = n2 | 5;
        }
        n2 = n3;
        if ((n & 6) == 6) {
            arrayList.add("DATA_NOT_AVAILABLE");
            n2 = n3 | 6;
        }
        n3 = n2;
        if ((n & 7) == 7) {
            arrayList.add("NOT_SUPPORTED");
            n3 = n2 | 7;
        }
        if (n != n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n3 & n));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(int n) {
        if (n == 0) {
            return "SUCCESS";
        }
        if (n == 1) {
            return "GENERIC_FAILURE";
        }
        if (n == 2) {
            return "CARD_NOT_ACCESSSIBLE";
        }
        if (n == 3) {
            return "CARD_ALREADY_POWERED_OFF";
        }
        if (n == 4) {
            return "CARD_REMOVED";
        }
        if (n == 5) {
            return "CARD_ALREADY_POWERED_ON";
        }
        if (n == 6) {
            return "DATA_NOT_AVAILABLE";
        }
        if (n == 7) {
            return "NOT_SUPPORTED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

