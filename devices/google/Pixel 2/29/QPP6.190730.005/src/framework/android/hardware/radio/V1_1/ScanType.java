/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class ScanType {
    public static final int ONE_SHOT = 0;
    public static final int PERIODIC = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("ONE_SHOT");
        if ((n & 1) == 1) {
            arrayList.add("PERIODIC");
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
            return "ONE_SHOT";
        }
        if (n == 1) {
            return "PERIODIC";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

