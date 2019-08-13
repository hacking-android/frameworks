/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RestrictedState {
    public static final int CS_ALL = 4;
    public static final int CS_EMERGENCY = 1;
    public static final int CS_NORMAL = 2;
    public static final int NONE = 0;
    public static final int PS_ALL = 16;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 1) == 1) {
            arrayList.add("CS_EMERGENCY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CS_NORMAL");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("CS_ALL");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("PS_ALL");
            n3 = n2 | 16;
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
            return "NONE";
        }
        if (n == 1) {
            return "CS_EMERGENCY";
        }
        if (n == 2) {
            return "CS_NORMAL";
        }
        if (n == 4) {
            return "CS_ALL";
        }
        if (n == 16) {
            return "PS_ALL";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

