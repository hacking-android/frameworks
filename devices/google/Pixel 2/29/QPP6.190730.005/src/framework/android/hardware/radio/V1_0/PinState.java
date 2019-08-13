/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class PinState {
    public static final int DISABLED = 3;
    public static final int ENABLED_BLOCKED = 4;
    public static final int ENABLED_NOT_VERIFIED = 1;
    public static final int ENABLED_PERM_BLOCKED = 5;
    public static final int ENABLED_VERIFIED = 2;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("ENABLED_NOT_VERIFIED");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("ENABLED_VERIFIED");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("DISABLED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("ENABLED_BLOCKED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("ENABLED_PERM_BLOCKED");
            n2 = n3 | 5;
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "ENABLED_NOT_VERIFIED";
        }
        if (n == 2) {
            return "ENABLED_VERIFIED";
        }
        if (n == 3) {
            return "DISABLED";
        }
        if (n == 4) {
            return "ENABLED_BLOCKED";
        }
        if (n == 5) {
            return "ENABLED_PERM_BLOCKED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

