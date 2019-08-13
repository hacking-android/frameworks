/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class TransactionResult {
    public static final int FAILURE = 1;
    public static final int SUCCESS = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("SUCCESS");
        if ((n & 1) == 1) {
            arrayList.add("FAILURE");
            n2 = false | true;
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
            return "SUCCESS";
        }
        if (n == 1) {
            return "FAILURE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

