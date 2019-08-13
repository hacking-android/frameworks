/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class UtranBands {
    public static final int BAND_1 = 1;
    public static final int BAND_10 = 10;
    public static final int BAND_11 = 11;
    public static final int BAND_12 = 12;
    public static final int BAND_13 = 13;
    public static final int BAND_14 = 14;
    public static final int BAND_19 = 19;
    public static final int BAND_2 = 2;
    public static final int BAND_20 = 20;
    public static final int BAND_21 = 21;
    public static final int BAND_22 = 22;
    public static final int BAND_25 = 25;
    public static final int BAND_26 = 26;
    public static final int BAND_3 = 3;
    public static final int BAND_4 = 4;
    public static final int BAND_5 = 5;
    public static final int BAND_6 = 6;
    public static final int BAND_7 = 7;
    public static final int BAND_8 = 8;
    public static final int BAND_9 = 9;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("BAND_1");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAND_2");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("BAND_3");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("BAND_4");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("BAND_5");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("BAND_6");
            n3 = n2 | 6;
        }
        int n4 = n3;
        if ((n & 7) == 7) {
            arrayList.add("BAND_7");
            n4 = n3 | 7;
        }
        n2 = n4;
        if ((n & 8) == 8) {
            arrayList.add("BAND_8");
            n2 = n4 | 8;
        }
        n3 = n2;
        if ((n & 9) == 9) {
            arrayList.add("BAND_9");
            n3 = n2 | 9;
        }
        n2 = n3;
        if ((n & 10) == 10) {
            arrayList.add("BAND_10");
            n2 = n3 | 10;
        }
        n3 = n2;
        if ((n & 11) == 11) {
            arrayList.add("BAND_11");
            n3 = n2 | 11;
        }
        n2 = n3;
        if ((n & 12) == 12) {
            arrayList.add("BAND_12");
            n2 = n3 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("BAND_13");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("BAND_14");
            n2 = n3 | 14;
        }
        n3 = n2;
        if ((n & 19) == 19) {
            arrayList.add("BAND_19");
            n3 = n2 | 19;
        }
        n2 = n3;
        if ((n & 20) == 20) {
            arrayList.add("BAND_20");
            n2 = n3 | 20;
        }
        n3 = n2;
        if ((n & 21) == 21) {
            arrayList.add("BAND_21");
            n3 = n2 | 21;
        }
        n2 = n3;
        if ((n & 22) == 22) {
            arrayList.add("BAND_22");
            n2 = n3 | 22;
        }
        n3 = n2;
        if ((n & 25) == 25) {
            arrayList.add("BAND_25");
            n3 = n2 | 25;
        }
        n2 = n3;
        if ((n & 26) == 26) {
            arrayList.add("BAND_26");
            n2 = n3 | 26;
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
        if (n == 1) {
            return "BAND_1";
        }
        if (n == 2) {
            return "BAND_2";
        }
        if (n == 3) {
            return "BAND_3";
        }
        if (n == 4) {
            return "BAND_4";
        }
        if (n == 5) {
            return "BAND_5";
        }
        if (n == 6) {
            return "BAND_6";
        }
        if (n == 7) {
            return "BAND_7";
        }
        if (n == 8) {
            return "BAND_8";
        }
        if (n == 9) {
            return "BAND_9";
        }
        if (n == 10) {
            return "BAND_10";
        }
        if (n == 11) {
            return "BAND_11";
        }
        if (n == 12) {
            return "BAND_12";
        }
        if (n == 13) {
            return "BAND_13";
        }
        if (n == 14) {
            return "BAND_14";
        }
        if (n == 19) {
            return "BAND_19";
        }
        if (n == 20) {
            return "BAND_20";
        }
        if (n == 21) {
            return "BAND_21";
        }
        if (n == 22) {
            return "BAND_22";
        }
        if (n == 25) {
            return "BAND_25";
        }
        if (n == 26) {
            return "BAND_26";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

