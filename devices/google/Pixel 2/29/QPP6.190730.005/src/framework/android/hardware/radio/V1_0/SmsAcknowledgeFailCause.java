/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SmsAcknowledgeFailCause {
    public static final int MEMORY_CAPACITY_EXCEEDED = 211;
    public static final int UNSPECIFIED_ERROR = 255;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 211) == 211) {
            arrayList.add("MEMORY_CAPACITY_EXCEEDED");
            n2 = 0 | 211;
        }
        int n3 = n2;
        if ((n & 255) == 255) {
            arrayList.add("UNSPECIFIED_ERROR");
            n3 = n2 | 255;
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
        if (n == 211) {
            return "MEMORY_CAPACITY_EXCEEDED";
        }
        if (n == 255) {
            return "UNSPECIFIED_ERROR";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

