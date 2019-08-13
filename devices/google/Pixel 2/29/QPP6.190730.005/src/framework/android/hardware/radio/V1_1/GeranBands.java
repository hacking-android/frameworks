/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class GeranBands {
    public static final int BAND_450 = 3;
    public static final int BAND_480 = 4;
    public static final int BAND_710 = 5;
    public static final int BAND_750 = 6;
    public static final int BAND_850 = 8;
    public static final int BAND_DCS1800 = 12;
    public static final int BAND_E900 = 10;
    public static final int BAND_ER900 = 14;
    public static final int BAND_P900 = 9;
    public static final int BAND_PCS1900 = 13;
    public static final int BAND_R900 = 11;
    public static final int BAND_T380 = 1;
    public static final int BAND_T410 = 2;
    public static final int BAND_T810 = 7;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("BAND_T380");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAND_T410");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("BAND_450");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("BAND_480");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("BAND_710");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("BAND_750");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("BAND_T810");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("BAND_850");
            n3 = n2 | 8;
        }
        int n4 = n3;
        if ((n & 9) == 9) {
            arrayList.add("BAND_P900");
            n4 = n3 | 9;
        }
        n2 = n4;
        if ((n & 10) == 10) {
            arrayList.add("BAND_E900");
            n2 = n4 | 10;
        }
        n3 = n2;
        if ((n & 11) == 11) {
            arrayList.add("BAND_R900");
            n3 = n2 | 11;
        }
        n2 = n3;
        if ((n & 12) == 12) {
            arrayList.add("BAND_DCS1800");
            n2 = n3 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("BAND_PCS1900");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("BAND_ER900");
            n2 = n3 | 14;
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
            return "BAND_T380";
        }
        if (n == 2) {
            return "BAND_T410";
        }
        if (n == 3) {
            return "BAND_450";
        }
        if (n == 4) {
            return "BAND_480";
        }
        if (n == 5) {
            return "BAND_710";
        }
        if (n == 6) {
            return "BAND_750";
        }
        if (n == 7) {
            return "BAND_T810";
        }
        if (n == 8) {
            return "BAND_850";
        }
        if (n == 9) {
            return "BAND_P900";
        }
        if (n == 10) {
            return "BAND_E900";
        }
        if (n == 11) {
            return "BAND_R900";
        }
        if (n == 12) {
            return "BAND_DCS1800";
        }
        if (n == 13) {
            return "BAND_PCS1900";
        }
        if (n == 14) {
            return "BAND_ER900";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

