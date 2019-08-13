/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import java.util.ArrayList;

public final class MaxSearchTimeRange {
    public static final int MAX = 3600;
    public static final int MIN = 60;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 60) == 60) {
            arrayList.add("MIN");
            n2 = 0 | 60;
        }
        int n3 = n2;
        if ((n & 3600) == 3600) {
            arrayList.add("MAX");
            n3 = n2 | 3600;
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
        if (n == 60) {
            return "MIN";
        }
        if (n == 3600) {
            return "MAX";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

