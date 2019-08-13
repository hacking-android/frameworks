/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class Result {
    public static final int BAD_PARAMS = 2;
    public static final int NOT_INIT = 3;
    public static final int OK = 0;
    public static final int TRANSACTION_FAILED = 4;
    public static final int TRANSACTION_PENDING = 5;
    public static final int UNKNOWN_FAILURE = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("OK");
        if ((n & 1) == 1) {
            arrayList.add("UNKNOWN_FAILURE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("BAD_PARAMS");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("NOT_INIT");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("TRANSACTION_FAILED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("TRANSACTION_PENDING");
            n2 = n3 | 5;
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
            return "UNKNOWN_FAILURE";
        }
        if (n == 2) {
            return "BAD_PARAMS";
        }
        if (n == 3) {
            return "NOT_INIT";
        }
        if (n == 4) {
            return "TRANSACTION_FAILED";
        }
        if (n == 5) {
            return "TRANSACTION_PENDING";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

