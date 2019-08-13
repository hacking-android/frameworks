/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.vibrator.V1_0;

import java.util.ArrayList;

public final class Status {
    public static final int BAD_VALUE = 2;
    public static final int OK = 0;
    public static final int UNKNOWN_ERROR = 1;
    public static final int UNSUPPORTED_OPERATION = 3;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("OK");
        if ((n & 1) == 1) {
            arrayList.add("UNKNOWN_ERROR");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAD_VALUE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("UNSUPPORTED_OPERATION");
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
        if (n == 0) {
            return "OK";
        }
        if (n == 1) {
            return "UNKNOWN_ERROR";
        }
        if (n == 2) {
            return "BAD_VALUE";
        }
        if (n == 3) {
            return "UNSUPPORTED_OPERATION";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

