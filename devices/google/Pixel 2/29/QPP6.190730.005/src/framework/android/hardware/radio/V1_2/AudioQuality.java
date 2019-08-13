/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import java.util.ArrayList;

public final class AudioQuality {
    public static final int AMR = 1;
    public static final int AMR_WB = 2;
    public static final int EVRC = 6;
    public static final int EVRC_B = 7;
    public static final int EVRC_NW = 9;
    public static final int EVRC_WB = 8;
    public static final int GSM_EFR = 3;
    public static final int GSM_FR = 4;
    public static final int GSM_HR = 5;
    public static final int UNSPECIFIED = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNSPECIFIED");
        if ((n & 1) == 1) {
            arrayList.add("AMR");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("AMR_WB");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("GSM_EFR");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("GSM_FR");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("GSM_HR");
            n2 = n3 | 5;
        }
        int n4 = n2;
        if ((n & 6) == 6) {
            arrayList.add("EVRC");
            n4 = n2 | 6;
        }
        n3 = n4;
        if ((n & 7) == 7) {
            arrayList.add("EVRC_B");
            n3 = n4 | 7;
        }
        n2 = n3;
        if ((n & 8) == 8) {
            arrayList.add("EVRC_WB");
            n2 = n3 | 8;
        }
        n3 = n2;
        if ((n & 9) == 9) {
            arrayList.add("EVRC_NW");
            n3 = n2 | 9;
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
            return "UNSPECIFIED";
        }
        if (n == 1) {
            return "AMR";
        }
        if (n == 2) {
            return "AMR_WB";
        }
        if (n == 3) {
            return "GSM_EFR";
        }
        if (n == 4) {
            return "GSM_FR";
        }
        if (n == 5) {
            return "GSM_HR";
        }
        if (n == 6) {
            return "EVRC";
        }
        if (n == 7) {
            return "EVRC_B";
        }
        if (n == 8) {
            return "EVRC_WB";
        }
        if (n == 9) {
            return "EVRC_NW";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

