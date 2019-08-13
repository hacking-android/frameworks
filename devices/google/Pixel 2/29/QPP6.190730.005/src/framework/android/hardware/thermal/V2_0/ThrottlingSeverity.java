/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import java.util.ArrayList;

public final class ThrottlingSeverity {
    public static final int CRITICAL = 4;
    public static final int EMERGENCY = 5;
    public static final int LIGHT = 1;
    public static final int MODERATE = 2;
    public static final int NONE = 0;
    public static final int SEVERE = 3;
    public static final int SHUTDOWN = 6;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 1) == 1) {
            arrayList.add("LIGHT");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("MODERATE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("SEVERE");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CRITICAL");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("EMERGENCY");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("SHUTDOWN");
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
            return "NONE";
        }
        if (n == 1) {
            return "LIGHT";
        }
        if (n == 2) {
            return "MODERATE";
        }
        if (n == 3) {
            return "SEVERE";
        }
        if (n == 4) {
            return "CRITICAL";
        }
        if (n == 5) {
            return "EMERGENCY";
        }
        if (n == 6) {
            return "SHUTDOWN";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

