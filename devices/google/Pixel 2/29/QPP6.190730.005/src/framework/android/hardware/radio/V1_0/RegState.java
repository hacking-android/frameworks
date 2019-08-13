/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RegState {
    public static final int NOT_REG_MT_NOT_SEARCHING_OP = 0;
    public static final int NOT_REG_MT_NOT_SEARCHING_OP_EM = 10;
    public static final int NOT_REG_MT_SEARCHING_OP = 2;
    public static final int NOT_REG_MT_SEARCHING_OP_EM = 12;
    public static final int REG_DENIED = 3;
    public static final int REG_DENIED_EM = 13;
    public static final int REG_HOME = 1;
    public static final int REG_ROAMING = 5;
    public static final int UNKNOWN = 4;
    public static final int UNKNOWN_EM = 14;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NOT_REG_MT_NOT_SEARCHING_OP");
        if ((n & 1) == 1) {
            arrayList.add("REG_HOME");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("NOT_REG_MT_SEARCHING_OP");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("REG_DENIED");
            n2 = n3 | 3;
        }
        int n4 = n2;
        if ((n & 4) == 4) {
            arrayList.add("UNKNOWN");
            n4 = n2 | 4;
        }
        n3 = n4;
        if ((n & 5) == 5) {
            arrayList.add("REG_ROAMING");
            n3 = n4 | 5;
        }
        n2 = n3;
        if ((n & 10) == 10) {
            arrayList.add("NOT_REG_MT_NOT_SEARCHING_OP_EM");
            n2 = n3 | 10;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("NOT_REG_MT_SEARCHING_OP_EM");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("REG_DENIED_EM");
            n2 = n3 | 13;
        }
        n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("UNKNOWN_EM");
            n3 = n2 | 14;
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
            return "NOT_REG_MT_NOT_SEARCHING_OP";
        }
        if (n == 1) {
            return "REG_HOME";
        }
        if (n == 2) {
            return "NOT_REG_MT_SEARCHING_OP";
        }
        if (n == 3) {
            return "REG_DENIED";
        }
        if (n == 4) {
            return "UNKNOWN";
        }
        if (n == 5) {
            return "REG_ROAMING";
        }
        if (n == 10) {
            return "NOT_REG_MT_NOT_SEARCHING_OP_EM";
        }
        if (n == 12) {
            return "NOT_REG_MT_SEARCHING_OP_EM";
        }
        if (n == 13) {
            return "REG_DENIED_EM";
        }
        if (n == 14) {
            return "UNKNOWN_EM";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

