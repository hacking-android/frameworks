/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class HubMemoryFlag {
    public static final int EXEC = 4;
    public static final int READ = 1;
    public static final int WRITE = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("READ");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("WRITE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("EXEC");
            n2 = n3 | 4;
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
            return "READ";
        }
        if (n == 2) {
            return "WRITE";
        }
        if (n == 4) {
            return "EXEC";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

