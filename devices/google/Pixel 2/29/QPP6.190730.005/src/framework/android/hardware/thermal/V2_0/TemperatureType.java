/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import java.util.ArrayList;

public final class TemperatureType {
    public static final int BATTERY = 2;
    public static final int BCL_CURRENT = 7;
    public static final int BCL_PERCENTAGE = 8;
    public static final int BCL_VOLTAGE = 6;
    public static final int CPU = 0;
    public static final int GPU = 1;
    public static final int NPU = 9;
    public static final int POWER_AMPLIFIER = 5;
    public static final int SKIN = 3;
    public static final int UNKNOWN = -1;
    public static final int USB_PORT = 4;

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
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("USB_PORT");
            n2 = n3 | 4;
        }
        int n4 = n2;
        if ((n & 5) == 5) {
            arrayList.add("POWER_AMPLIFIER");
            n4 = n2 | 5;
        }
        n3 = n4;
        if ((n & 6) == 6) {
            arrayList.add("BCL_VOLTAGE");
            n3 = n4 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("BCL_CURRENT");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("BCL_PERCENTAGE");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("NPU");
            n2 = n3 | 9;
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
        if (n == 4) {
            return "USB_PORT";
        }
        if (n == 5) {
            return "POWER_AMPLIFIER";
        }
        if (n == 6) {
            return "BCL_VOLTAGE";
        }
        if (n == 7) {
            return "BCL_CURRENT";
        }
        if (n == 8) {
            return "BCL_PERCENTAGE";
        }
        if (n == 9) {
            return "NPU";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

