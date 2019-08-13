/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class ApnAuthType {
    public static final int NO_PAP_CHAP = 2;
    public static final int NO_PAP_NO_CHAP = 0;
    public static final int PAP_CHAP = 3;
    public static final int PAP_NO_CHAP = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NO_PAP_NO_CHAP");
        if ((n & 1) == 1) {
            arrayList.add("PAP_NO_CHAP");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("NO_PAP_CHAP");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("PAP_CHAP");
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
            return "NO_PAP_NO_CHAP";
        }
        if (n == 1) {
            return "PAP_NO_CHAP";
        }
        if (n == 2) {
            return "NO_PAP_CHAP";
        }
        if (n == 3) {
            return "PAP_CHAP";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

