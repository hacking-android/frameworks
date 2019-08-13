/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V1_0;

import java.util.ArrayList;

public final class TemperatureType {
    public static final int BATTERY = 2;
    public static final int CPU = 0;
    public static final int GPU = 1;
    public static final int SKIN = 3;
    public static final int UNKNOWN = -1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & -1) == -1) {
            arrayList.add("UNKNOWN");
            n2 = 0 | -1;
        }
        arrayList.add("CPU");
        int n3 = n2;
        if ((n & 1) == 1) {
            arrayList.add("GPU");
            n3 = n2 | 1;
        }
        n2 = n3;
        if ((n & 2) == 2) {
            arrayList.add("BATTERY");
            n2 = n3 | 2;
        }
        n3 = n2;
        if ((n & 3) == 3) {
            arrayList.add("SKIN");
            n3 = n2 | 3;
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
        if (n == -1) {
            return "UNKNOWN";
        }
        if (n == 0) {
            return "CPU";
        }
        if (n == 1) {
            return "GPU";
        }
        if (n == 2) {
            return "BATTERY";
        }
        if (n == 3) {
            return "SKIN";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

