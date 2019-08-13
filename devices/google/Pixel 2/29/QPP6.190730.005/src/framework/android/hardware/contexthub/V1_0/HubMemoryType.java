/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class HubMemoryType {
    public static final int MAIN = 0;
    public static final int SECONDARY = 1;
    public static final int TCM = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("MAIN");
        if ((n & 1) == 1) {
            arrayList.add("SECONDARY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("TCM");
            n3 = n2 | 2;
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
            return "MAIN";
        }
        if (n == 1) {
            return "SECONDARY";
        }
        if (n == 2) {
            return "TCM";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

