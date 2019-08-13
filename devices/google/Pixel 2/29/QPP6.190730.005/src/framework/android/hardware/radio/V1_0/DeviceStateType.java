/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class DeviceStateType {
    public static final int CHARGING_STATE = 1;
    public static final int LOW_DATA_EXPECTED = 2;
    public static final int POWER_SAVE_MODE = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("POWER_SAVE_MODE");
        if ((n & 1) == 1) {
            arrayList.add("CHARGING_STATE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("LOW_DATA_EXPECTED");
            n3 = n2 | 2;
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
            return "POWER_SAVE_MODE";
        }
        if (n == 1) {
            return "CHARGING_STATE";
        }
        if (n == 2) {
            return "LOW_DATA_EXPECTED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

