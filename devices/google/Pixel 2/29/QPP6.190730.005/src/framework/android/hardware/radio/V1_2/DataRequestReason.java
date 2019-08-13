/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import java.util.ArrayList;

public final class DataRequestReason {
    public static final int HANDOVER = 3;
    public static final int NORMAL = 1;
    public static final int SHUTDOWN = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("NORMAL");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("SHUTDOWN");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("HANDOVER");
            n2 = n3 | 3;
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
        if (n == 1) {
            return "NORMAL";
        }
        if (n == 2) {
            return "SHUTDOWN";
        }
        if (n == 3) {
            return "HANDOVER";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

