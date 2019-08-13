/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class RadioTechnology {
    public static final int EDGE = 2;
    public static final int EHRPD = 13;
    public static final int EVDO_0 = 7;
    public static final int EVDO_A = 8;
    public static final int EVDO_B = 12;
    public static final int GPRS = 1;
    public static final int GSM = 16;
    public static final int HSDPA = 9;
    public static final int HSPA = 11;
    public static final int HSPAP = 15;
    public static final int HSUPA = 10;
    public static final int IS95A = 4;
    public static final int IS95B = 5;
    public static final int IWLAN = 18;
    public static final int LTE = 14;
    public static final int LTE_CA = 19;
    public static final int NR = 20;
    public static final int ONE_X_RTT = 6;
    public static final int TD_SCDMA = 17;
    public static final int UMTS = 3;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("GPRS");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("EDGE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("UMTS");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("IS95A");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("IS95B");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("ONE_X_RTT");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("EVDO_0");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("EVDO_A");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("HSDPA");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("HSUPA");
            n3 = n2 | 10;
        }
        int n4 = n3;
        if ((n & 11) == 11) {
            arrayList.add("HSPA");
            n4 = n3 | 11;
        }
        n2 = n4;
        if ((n & 12) == 12) {
            arrayList.add("EVDO_B");
            n2 = n4 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("EHRPD");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("LTE");
            n2 = n3 | 14;
        }
        n3 = n2;
        if ((n & 15) == 15) {
            arrayList.add("HSPAP");
            n3 = n2 | 15;
        }
        n4 = n3;
        if ((n & 16) == 16) {
            arrayList.add("GSM");
            n4 = n3 | 16;
        }
        n2 = n4;
        if ((n & 17) == 17) {
            arrayList.add("TD_SCDMA");
            n2 = n4 | 17;
        }
        n3 = n2;
        if ((n & 18) == 18) {
            arrayList.add("IWLAN");
            n3 = n2 | 18;
        }
        n2 = n3;
        if ((n & 19) == 19) {
            arrayList.add("LTE_CA");
            n2 = n3 | 19;
        }
        n3 = n2;
        if ((n & 20) == 20) {
            arrayList.add("NR");
            n3 = n2 | 20;
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "GPRS";
        }
        if (n == 2) {
            return "EDGE";
        }
        if (n == 3) {
            return "UMTS";
        }
        if (n == 4) {
            return "IS95A";
        }
        if (n == 5) {
            return "IS95B";
        }
        if (n == 6) {
            return "ONE_X_RTT";
        }
        if (n == 7) {
            return "EVDO_0";
        }
        if (n == 8) {
            return "EVDO_A";
        }
        if (n == 9) {
            return "HSDPA";
        }
        if (n == 10) {
            return "HSUPA";
        }
        if (n == 11) {
            return "HSPA";
        }
        if (n == 12) {
            return "EVDO_B";
        }
        if (n == 13) {
            return "EHRPD";
        }
        if (n == 14) {
            return "LTE";
        }
        if (n == 15) {
            return "HSPAP";
        }
        if (n == 16) {
            return "GSM";
        }
        if (n == 17) {
            return "TD_SCDMA";
        }
        if (n == 18) {
            return "IWLAN";
        }
        if (n == 19) {
            return "LTE_CA";
        }
        if (n == 20) {
            return "NR";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

