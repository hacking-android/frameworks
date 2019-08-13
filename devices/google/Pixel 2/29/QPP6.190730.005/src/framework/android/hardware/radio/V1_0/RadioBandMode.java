/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioBandMode {
    public static final int BAND_MODE_10_800M_2 = 15;
    public static final int BAND_MODE_5_450M = 10;
    public static final int BAND_MODE_7_700M_2 = 12;
    public static final int BAND_MODE_8_1800M = 13;
    public static final int BAND_MODE_9_900M = 14;
    public static final int BAND_MODE_AUS = 4;
    public static final int BAND_MODE_AUS_2 = 5;
    public static final int BAND_MODE_AWS = 17;
    public static final int BAND_MODE_CELL_800 = 6;
    public static final int BAND_MODE_EURO = 1;
    public static final int BAND_MODE_EURO_PAMR_400M = 16;
    public static final int BAND_MODE_IMT2000 = 11;
    public static final int BAND_MODE_JPN = 3;
    public static final int BAND_MODE_JTACS = 8;
    public static final int BAND_MODE_KOREA_PCS = 9;
    public static final int BAND_MODE_PCS = 7;
    public static final int BAND_MODE_UNSPECIFIED = 0;
    public static final int BAND_MODE_USA = 2;
    public static final int BAND_MODE_USA_2500M = 18;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("BAND_MODE_UNSPECIFIED");
        if ((n & 1) == 1) {
            arrayList.add("BAND_MODE_EURO");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAND_MODE_USA");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("BAND_MODE_JPN");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("BAND_MODE_AUS");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("BAND_MODE_AUS_2");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("BAND_MODE_CELL_800");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("BAND_MODE_PCS");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("BAND_MODE_JTACS");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("BAND_MODE_KOREA_PCS");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("BAND_MODE_5_450M");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("BAND_MODE_IMT2000");
            n2 = n3 | 11;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("BAND_MODE_7_700M_2");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("BAND_MODE_8_1800M");
            n2 = n3 | 13;
        }
        n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("BAND_MODE_9_900M");
            n3 = n2 | 14;
        }
        n2 = n3;
        if ((n & 15) == 15) {
            arrayList.add("BAND_MODE_10_800M_2");
            n2 = n3 | 15;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("BAND_MODE_EURO_PAMR_400M");
            n3 = n2 | 16;
        }
        n2 = n3;
        if ((n & 17) == 17) {
            arrayList.add("BAND_MODE_AWS");
            n2 = n3 | 17;
        }
        n3 = n2;
        if ((n & 18) == 18) {
            arrayList.add("BAND_MODE_USA_2500M");
            n3 = n2 | 18;
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
            return "BAND_MODE_UNSPECIFIED";
        }
        if (n == 1) {
            return "BAND_MODE_EURO";
        }
        if (n == 2) {
            return "BAND_MODE_USA";
        }
        if (n == 3) {
            return "BAND_MODE_JPN";
        }
        if (n == 4) {
            return "BAND_MODE_AUS";
        }
        if (n == 5) {
            return "BAND_MODE_AUS_2";
        }
        if (n == 6) {
            return "BAND_MODE_CELL_800";
        }
        if (n == 7) {
            return "BAND_MODE_PCS";
        }
        if (n == 8) {
            return "BAND_MODE_JTACS";
        }
        if (n == 9) {
            return "BAND_MODE_KOREA_PCS";
        }
        if (n == 10) {
            return "BAND_MODE_5_450M";
        }
        if (n == 11) {
            return "BAND_MODE_IMT2000";
        }
        if (n == 12) {
            return "BAND_MODE_7_700M_2";
        }
        if (n == 13) {
            return "BAND_MODE_8_1800M";
        }
        if (n == 14) {
            return "BAND_MODE_9_900M";
        }
        if (n == 15) {
            return "BAND_MODE_10_800M_2";
        }
        if (n == 16) {
            return "BAND_MODE_EURO_PAMR_400M";
        }
        if (n == 17) {
            return "BAND_MODE_AWS";
        }
        if (n == 18) {
            return "BAND_MODE_USA_2500M";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

