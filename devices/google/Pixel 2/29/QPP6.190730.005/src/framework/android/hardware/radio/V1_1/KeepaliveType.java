/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class KeepaliveType {
    public static final int NATT_IPV4 = 0;
    public static final int NATT_IPV6 = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NATT_IPV4");
        if ((n & 1) == 1) {
            arrayList.add("NATT_IPV6");
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
            return "NATT_IPV4";
        }
        if (n == 1) {
            return "NATT_IPV6";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

