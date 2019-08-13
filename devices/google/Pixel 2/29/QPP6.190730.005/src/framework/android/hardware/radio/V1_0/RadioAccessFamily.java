/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioAccessFamily {
    public static final int EDGE = 4;
    public static final int EHRPD = 8192;
    public static final int EVDO_0 = 128;
    public static final int EVDO_A = 256;
    public static final int EVDO_B = 4096;
    public static final int GPRS = 2;
    public static final int GSM = 65536;
    public static final int HSDPA = 512;
    public static final int HSPA = 2048;
    public static final int HSPAP = 32768;
    public static final int HSUPA = 1024;
    public static final int IS95A = 16;
    public static final int IS95B = 32;
    public static final int LTE = 16384;
    public static final int LTE_CA = 524288;
    public static final int ONE_X_RTT = 64;
    public static final int TD_SCDMA = 131072;
    public static final int UMTS = 8;
    public static final int UNKNOWN = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("UNKNOWN");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("GPRS");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("EDGE");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("UMTS");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("IS95A");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("IS95B");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 64) == 64) {
            arrayList.add("ONE_X_RTT");
            n2 = n3 | 64;
        }
        n3 = n2;
        if ((n & 128) == 128) {
            arrayList.add("EVDO_0");
            n3 = n2 | 128;
        }
        int n4 = n3;
        if ((n & 256) == 256) {
            arrayList.add("EVDO_A");
            n4 = n3 | 256;
        }
        n2 = n4;
        if ((n & 512) == 512) {
            arrayList.add("HSDPA");
            n2 = n4 | 512;
        }
        n3 = n2;
        if ((n & 1024) == 1024) {
            arrayList.add("HSUPA");
            n3 = n2 | 1024;
        }
        n2 = n3;
        if ((n & 2048) == 2048) {
            arrayList.add("HSPA");
            n2 = n3 | 2048;
        }
        n3 = n2;
        if ((n & 4096) == 4096) {
            arrayList.add("EVDO_B");
            n3 = n2 | 4096;
        }
        n2 = n3;
        if ((n & 8192) == 8192) {
            arrayList.add("EHRPD");
            n2 = n3 | 8192;
        }
        n3 = n2;
        if ((n & 16384) == 16384) {
            arrayList.add("LTE");
            n3 = n2 | 16384;
        }
        n2 = n3;
        if ((32768 & n) == 32768) {
            arrayList.add("HSPAP");
            n2 = n3 | 32768;
        }
        n3 = n2;
        if ((65536 & n) == 65536) {
            arrayList.add("GSM");
            n3 = n2 | 65536;
        }
        n2 = n3;
        if ((131072 & n) == 131072) {
            arrayList.add("TD_SCDMA");
            n2 = n3 | 131072;
        }
        n3 = n2;
        if ((524288 & n) == 524288) {
            arrayList.add("LTE_CA");
            n3 = n2 | 524288;
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
        if (n == 1) {
            return "UNKNOWN";
        }
        if (n == 2) {
            return "GPRS";
        }
        if (n == 4) {
            return "EDGE";
        }
        if (n == 8) {
            return "UMTS";
        }
        if (n == 16) {
            return "IS95A";
        }
        if (n == 32) {
            return "IS95B";
        }
        if (n == 64) {
            return "ONE_X_RTT";
        }
        if (n == 128) {
            return "EVDO_0";
        }
        if (n == 256) {
            return "EVDO_A";
        }
        if (n == 512) {
            return "HSDPA";
        }
        if (n == 1024) {
            return "HSUPA";
        }
        if (n == 2048) {
            return "HSPA";
        }
        if (n == 4096) {
            return "EVDO_B";
        }
        if (n == 8192) {
            return "EHRPD";
        }
        if (n == 16384) {
            return "LTE";
        }
        if (n == 32768) {
            return "HSPAP";
        }
        if (n == 65536) {
            return "GSM";
        }
        if (n == 131072) {
            return "TD_SCDMA";
        }
        if (n == 524288) {
            return "LTE_CA";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

