/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import java.util.ArrayList;

public final class CoolingType {
    public static final int BATTERY = 1;
    public static final int COMPONENT = 6;
    public static final int CPU = 2;
    public static final int FAN = 0;
    public static final int GPU = 3;
    public static final int MODEM = 4;
    public static final int NPU = 5;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("FAN");
        if ((n & 1) == 1) {
            arrayList.add("BATTERY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CPU");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("GPU");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("MODEM");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("NPU");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("COMPONENT");
            n3 = n2 | 6;
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
            return "FAN";
        }
        if (n == 1) {
            return "BATTERY";
        }
        if (n == 2) {
            return "CPU";
        }
        if (n == 3) {
            return "GPU";
        }
        if (n == 4) {
            return "MODEM";
        }
        if (n == 5) {
            return "NPU";
        }
        if (n == 6) {
            return "COMPONENT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

