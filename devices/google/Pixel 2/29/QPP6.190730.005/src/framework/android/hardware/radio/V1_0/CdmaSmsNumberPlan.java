/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSmsNumberPlan {
    public static final int DATA = 3;
    public static final int PRIVATE = 9;
    public static final int RESERVED_10 = 10;
    public static final int RESERVED_11 = 11;
    public static final int RESERVED_12 = 12;
    public static final int RESERVED_13 = 13;
    public static final int RESERVED_14 = 14;
    public static final int RESERVED_15 = 15;
    public static final int RESERVED_2 = 2;
    public static final int RESERVED_5 = 5;
    public static final int RESERVED_6 = 6;
    public static final int RESERVED_7 = 7;
    public static final int RESERVED_8 = 8;
    public static final int TELEPHONY = 1;
    public static final int TELEX = 4;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("TELEPHONY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("RESERVED_2");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("DATA");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("TELEX");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("RESERVED_5");
            n2 = n3 | 5;
        }
        int n4 = n2;
        if ((n & 6) == 6) {
            arrayList.add("RESERVED_6");
            n4 = n2 | 6;
        }
        n3 = n4;
        if ((n & 7) == 7) {
            arrayList.add("RESERVED_7");
            n3 = n4 | 7;
        }
        n2 = n3;
        if ((n & 8) == 8) {
            arrayList.add("RESERVED_8");
            n2 = n3 | 8;
        }
        n4 = n2;
        if ((n & 9) == 9) {
            arrayList.add("PRIVATE");
            n4 = n2 | 9;
        }
        n3 = n4;
        if ((n & 10) == 10) {
            arrayList.add("RESERVED_10");
            n3 = n4 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("RESERVED_11");
            n2 = n3 | 11;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("RESERVED_12");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("RESERVED_13");
            n2 = n3 | 13;
        }
        n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("RESERVED_14");
            n3 = n2 | 14;
        }
        n2 = n3;
        if ((n & 15) == 15) {
            arrayList.add("RESERVED_15");
            n2 = n3 | 15;
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "TELEPHONY";
        }
        if (n == 2) {
            return "RESERVED_2";
        }
        if (n == 3) {
            return "DATA";
        }
        if (n == 4) {
            return "TELEX";
        }
        if (n == 5) {
            return "RESERVED_5";
        }
        if (n == 6) {
            return "RESERVED_6";
        }
        if (n == 7) {
            return "RESERVED_7";
        }
        if (n == 8) {
            return "RESERVED_8";
        }
        if (n == 9) {
            return "PRIVATE";
        }
        if (n == 10) {
            return "RESERVED_10";
        }
        if (n == 11) {
            return "RESERVED_11";
        }
        if (n == 12) {
            return "RESERVED_12";
        }
        if (n == 13) {
            return "RESERVED_13";
        }
        if (n == 14) {
            return "RESERVED_14";
        }
        if (n == 15) {
            return "RESERVED_15";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

