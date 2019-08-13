/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CallForwardInfoStatus {
    public static final int DISABLE = 0;
    public static final int ENABLE = 1;
    public static final int ERASURE = 4;
    public static final int INTERROGATE = 2;
    public static final int REGISTRATION = 3;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("DISABLE");
        if ((n & 1) == 1) {
            arrayList.add("ENABLE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("INTERROGATE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("REGISTRATION");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("ERASURE");
            n3 = n2 | 4;
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
            return "DISABLE";
        }
        if (n == 1) {
            return "ENABLE";
        }
        if (n == 2) {
            return "INTERROGATE";
        }
        if (n == 3) {
            return "REGISTRATION";
        }
        if (n == 4) {
            return "ERASURE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

