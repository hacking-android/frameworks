/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SapStatus {
    public static final int CARD_INSERTED = 4;
    public static final int CARD_NOT_ACCESSIBLE = 2;
    public static final int CARD_REMOVED = 3;
    public static final int CARD_RESET = 1;
    public static final int RECOVERED = 5;
    public static final int UNKNOWN_ERROR = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN_ERROR");
        if ((n & 1) == 1) {
            arrayList.add("CARD_RESET");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CARD_NOT_ACCESSIBLE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("CARD_REMOVED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CARD_INSERTED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("RECOVERED");
            n2 = n3 | 5;
        }
        if (n != n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n2 & n));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(int n) {
        if (n == 0) {
            return "UNKNOWN_ERROR";
        }
        if (n == 1) {
            return "CARD_RESET";
        }
        if (n == 2) {
            return "CARD_NOT_ACCESSIBLE";
        }
        if (n == 3) {
            return "CARD_REMOVED";
        }
        if (n == 4) {
            return "CARD_INSERTED";
        }
        if (n == 5) {
            return "RECOVERED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

