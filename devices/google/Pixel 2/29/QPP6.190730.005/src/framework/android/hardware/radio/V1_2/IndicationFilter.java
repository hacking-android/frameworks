/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import java.util.ArrayList;

public final class IndicationFilter {
    public static final int ALL = -1;
    public static final int DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int FULL_NETWORK_STATE = 2;
    public static final int LINK_CAPACITY_ESTIMATE = 8;
    public static final int NONE = 0;
    public static final int PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int SIGNAL_STRENGTH = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & -1) == -1) {
            arrayList.add("ALL");
            n2 = 0 | -1;
        }
        int n3 = n2;
        if ((n & 1) == 1) {
            arrayList.add("SIGNAL_STRENGTH");
            n3 = n2 | 1;
        }
        n2 = n3;
        if ((n & 2) == 2) {
            arrayList.add("FULL_NETWORK_STATE");
            n2 = n3 | 2;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("DATA_CALL_DORMANCY_CHANGED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 8) == 8) {
            arrayList.add("LINK_CAPACITY_ESTIMATE");
            n2 = n3 | 8;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("PHYSICAL_CHANNEL_CONFIG");
            n3 = n2 | 16;
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
            return "NONE";
        }
        if (n == -1) {
            return "ALL";
        }
        if (n == 1) {
            return "SIGNAL_STRENGTH";
        }
        if (n == 2) {
            return "FULL_NETWORK_STATE";
        }
        if (n == 4) {
            return "DATA_CALL_DORMANCY_CHANGED";
        }
        if (n == 8) {
            return "LINK_CAPACITY_ESTIMATE";
        }
        if (n == 16) {
            return "PHYSICAL_CHANNEL_CONFIG";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

