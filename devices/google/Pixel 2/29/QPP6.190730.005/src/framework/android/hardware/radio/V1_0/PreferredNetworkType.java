/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class PreferredNetworkType {
    public static final int CDMA_EVDO_AUTO = 4;
    public static final int CDMA_ONLY = 5;
    public static final int EVDO_ONLY = 6;
    public static final int GSM_ONLY = 1;
    public static final int GSM_WCDMA = 0;
    public static final int GSM_WCDMA_AUTO = 3;
    public static final int GSM_WCDMA_CDMA_EVDO_AUTO = 7;
    public static final int LTE_CDMA_EVDO = 8;
    public static final int LTE_CMDA_EVDO_GSM_WCDMA = 10;
    public static final int LTE_GSM_WCDMA = 9;
    public static final int LTE_ONLY = 11;
    public static final int LTE_WCDMA = 12;
    public static final int TD_SCDMA_GSM = 16;
    public static final int TD_SCDMA_GSM_LTE = 17;
    public static final int TD_SCDMA_GSM_WCDMA = 18;
    public static final int TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO = 21;
    public static final int TD_SCDMA_GSM_WCDMA_LTE = 20;
    public static final int TD_SCDMA_LTE = 15;
    public static final int TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int TD_SCDMA_ONLY = 13;
    public static final int TD_SCDMA_WCDMA = 14;
    public static final int TD_SCDMA_WCDMA_LTE = 19;
    public static final int WCDMA = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("GSM_WCDMA");
        if ((n & 1) == 1) {
            arrayList.add("GSM_ONLY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("WCDMA");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("GSM_WCDMA_AUTO");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CDMA_EVDO_AUTO");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("CDMA_ONLY");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("EVDO_ONLY");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("GSM_WCDMA_CDMA_EVDO_AUTO");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("LTE_CDMA_EVDO");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("LTE_GSM_WCDMA");
            n2 = n3 | 9;
        }
        int n4 = n2;
        if ((n & 10) == 10) {
            arrayList.add("LTE_CMDA_EVDO_GSM_WCDMA");
            n4 = n2 | 10;
        }
        n3 = n4;
        if ((n & 11) == 11) {
            arrayList.add("LTE_ONLY");
            n3 = n4 | 11;
        }
        n2 = n3;
        if ((n & 12) == 12) {
            arrayList.add("LTE_WCDMA");
            n2 = n3 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("TD_SCDMA_ONLY");
            n3 = n2 | 13;
        }
        n4 = n3;
        if ((n & 14) == 14) {
            arrayList.add("TD_SCDMA_WCDMA");
            n4 = n3 | 14;
        }
        n2 = n4;
        if ((n & 15) == 15) {
            arrayList.add("TD_SCDMA_LTE");
            n2 = n4 | 15;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("TD_SCDMA_GSM");
            n3 = n2 | 16;
        }
        n2 = n3;
        if ((n & 17) == 17) {
            arrayList.add("TD_SCDMA_GSM_LTE");
            n2 = n3 | 17;
        }
        n3 = n2;
        if ((n & 18) == 18) {
            arrayList.add("TD_SCDMA_GSM_WCDMA");
            n3 = n2 | 18;
        }
        n2 = n3;
        if ((n & 19) == 19) {
            arrayList.add("TD_SCDMA_WCDMA_LTE");
            n2 = n3 | 19;
        }
        n3 = n2;
        if ((n & 20) == 20) {
            arrayList.add("TD_SCDMA_GSM_WCDMA_LTE");
            n3 = n2 | 20;
        }
        n2 = n3;
        if ((n & 21) == 21) {
            arrayList.add("TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO");
            n2 = n3 | 21;
        }
        n3 = n2;
        if ((n & 22) == 22) {
            arrayList.add("TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA");
            n3 = n2 | 22;
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
            return "GSM_WCDMA";
        }
        if (n == 1) {
            return "GSM_ONLY";
        }
        if (n == 2) {
            return "WCDMA";
        }
        if (n == 3) {
            return "GSM_WCDMA_AUTO";
        }
        if (n == 4) {
            return "CDMA_EVDO_AUTO";
        }
        if (n == 5) {
            return "CDMA_ONLY";
        }
        if (n == 6) {
            return "EVDO_ONLY";
        }
        if (n == 7) {
            return "GSM_WCDMA_CDMA_EVDO_AUTO";
        }
        if (n == 8) {
            return "LTE_CDMA_EVDO";
        }
        if (n == 9) {
            return "LTE_GSM_WCDMA";
        }
        if (n == 10) {
            return "LTE_CMDA_EVDO_GSM_WCDMA";
        }
        if (n == 11) {
            return "LTE_ONLY";
        }
        if (n == 12) {
            return "LTE_WCDMA";
        }
        if (n == 13) {
            return "TD_SCDMA_ONLY";
        }
        if (n == 14) {
            return "TD_SCDMA_WCDMA";
        }
        if (n == 15) {
            return "TD_SCDMA_LTE";
        }
        if (n == 16) {
            return "TD_SCDMA_GSM";
        }
        if (n == 17) {
            return "TD_SCDMA_GSM_LTE";
        }
        if (n == 18) {
            return "TD_SCDMA_GSM_WCDMA";
        }
        if (n == 19) {
            return "TD_SCDMA_WCDMA_LTE";
        }
        if (n == 20) {
            return "TD_SCDMA_GSM_WCDMA_LTE";
        }
        if (n == 21) {
            return "TD_SCDMA_GSM_WCDMA_CDMA_EVDO_AUTO";
        }
        if (n == 22) {
            return "TD_SCDMA_LTE_CDMA_EVDO_GSM_WCDMA";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

