/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class AsyncEventType {
    public static final int RESTARTED = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("RESTARTED");
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
        if (n == 1) {
            return "RESTARTED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

