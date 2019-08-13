/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class PersoSubstate {
    public static final int IN_PROGRESS = 1;
    public static final int READY = 2;
    public static final int RUIM_CORPORATE = 16;
    public static final int RUIM_CORPORATE_PUK = 22;
    public static final int RUIM_HRPD = 15;
    public static final int RUIM_HRPD_PUK = 21;
    public static final int RUIM_NETWORK1 = 13;
    public static final int RUIM_NETWORK1_PUK = 19;
    public static final int RUIM_NETWORK2 = 14;
    public static final int RUIM_NETWORK2_PUK = 20;
    public static final int RUIM_RUIM = 18;
    public static final int RUIM_RUIM_PUK = 24;
    public static final int RUIM_SERVICE_PROVIDER = 17;
    public static final int RUIM_SERVICE_PROVIDER_PUK = 23;
    public static final int SIM_CORPORATE = 5;
    public static final int SIM_CORPORATE_PUK = 10;
    public static final int SIM_NETWORK = 3;
    public static final int SIM_NETWORK_PUK = 8;
    public static final int SIM_NETWORK_SUBSET = 4;
    public static final int SIM_NETWORK_SUBSET_PUK = 9;
    public static final int SIM_SERVICE_PROVIDER = 6;
    public static final int SIM_SERVICE_PROVIDER_PUK = 11;
    public static final int SIM_SIM = 7;
    public static final int SIM_SIM_PUK = 12;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("IN_PROGRESS");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("READY");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("SIM_NETWORK");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SIM_NETWORK_SUBSET");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("SIM_CORPORATE");
            n2 = n3 | 5;
        }
        int n4 = n2;
        if ((n & 6) == 6) {
            arrayList.add("SIM_SERVICE_PROVIDER");
            n4 = n2 | 6;
        }
        n3 = n4;
        if ((n & 7) == 7) {
            arrayList.add("SIM_SIM");
            n3 = n4 | 7;
        }
        n2 = n3;
        if ((n & 8) == 8) {
            arrayList.add("SIM_NETWORK_PUK");
            n2 = n3 | 8;
        }
        n3 = n2;
        if ((n & 9) == 9) {
            arrayList.add("SIM_NETWORK_SUBSET_PUK");
            n3 = n2 | 9;
        }
        n2 = n3;
        if ((n & 10) == 10) {
            arrayList.add("SIM_CORPORATE_PUK");
            n2 = n3 | 10;
        }
        n3 = n2;
        if ((n & 11) == 11) {
            arrayList.add("SIM_SERVICE_PROVIDER_PUK");
            n3 = n2 | 11;
        }
        n2 = n3;
        if ((n & 12) == 12) {
            arrayList.add("SIM_SIM_PUK");
            n2 = n3 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("RUIM_NETWORK1");
            n3 = n2 | 13;
        }
        n4 = n3;
        if ((n & 14) == 14) {
            arrayList.add("RUIM_NETWORK2");
            n4 = n3 | 14;
        }
        n2 = n4;
        if ((n & 15) == 15) {
            arrayList.add("RUIM_HRPD");
            n2 = n4 | 15;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("RUIM_CORPORATE");
            n3 = n2 | 16;
        }
        n2 = n3;
        if ((n & 17) == 17) {
            arrayList.add("RUIM_SERVICE_PROVIDER");
            n2 = n3 | 17;
        }
        n3 = n2;
        if ((n & 18) == 18) {
            arrayList.add("RUIM_RUIM");
            n3 = n2 | 18;
        }
        n2 = n3;
        if ((n & 19) == 19) {
            arrayList.add("RUIM_NETWORK1_PUK");
            n2 = n3 | 19;
        }
        n3 = n2;
        if ((n & 20) == 20) {
            arrayList.add("RUIM_NETWORK2_PUK");
            n3 = n2 | 20;
        }
        n2 = n3;
        if ((n & 21) == 21) {
            arrayList.add("RUIM_HRPD_PUK");
            n2 = n3 | 21;
        }
        n3 = n2;
        if ((n & 22) == 22) {
            arrayList.add("RUIM_CORPORATE_PUK");
            n3 = n2 | 22;
        }
        n2 = n3;
        if ((n & 23) == 23) {
            arrayList.add("RUIM_SERVICE_PROVIDER_PUK");
            n2 = n3 | 23;
        }
        n3 = n2;
        if ((n & 24) == 24) {
            arrayList.add("RUIM_RUIM_PUK");
            n3 = n2 | 24;
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
            return "IN_PROGRESS";
        }
        if (n == 2) {
            return "READY";
        }
        if (n == 3) {
            return "SIM_NETWORK";
        }
        if (n == 4) {
            return "SIM_NETWORK_SUBSET";
        }
        if (n == 5) {
            return "SIM_CORPORATE";
        }
        if (n == 6) {
            return "SIM_SERVICE_PROVIDER";
        }
        if (n == 7) {
            return "SIM_SIM";
        }
        if (n == 8) {
            return "SIM_NETWORK_PUK";
        }
        if (n == 9) {
            return "SIM_NETWORK_SUBSET_PUK";
        }
        if (n == 10) {
            return "SIM_CORPORATE_PUK";
        }
        if (n == 11) {
            return "SIM_SERVICE_PROVIDER_PUK";
        }
        if (n == 12) {
            return "SIM_SIM_PUK";
        }
        if (n == 13) {
            return "RUIM_NETWORK1";
        }
        if (n == 14) {
            return "RUIM_NETWORK2";
        }
        if (n == 15) {
            return "RUIM_HRPD";
        }
        if (n == 16) {
            return "RUIM_CORPORATE";
        }
        if (n == 17) {
            return "RUIM_SERVICE_PROVIDER";
        }
        if (n == 18) {
            return "RUIM_RUIM";
        }
        if (n == 19) {
            return "RUIM_NETWORK1_PUK";
        }
        if (n == 20) {
            return "RUIM_NETWORK2_PUK";
        }
        if (n == 21) {
            return "RUIM_HRPD_PUK";
        }
        if (n == 22) {
            return "RUIM_CORPORATE_PUK";
        }
        if (n == 23) {
            return "RUIM_SERVICE_PROVIDER_PUK";
        }
        if (n == 24) {
            return "RUIM_RUIM_PUK";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

