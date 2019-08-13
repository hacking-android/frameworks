/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaCallWaitingNumberPlan {
    public static final int DATA = 3;
    public static final int ISDN = 1;
    public static final int NATIONAL = 8;
    public static final int PRIVATE = 9;
    public static final int TELEX = 4;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("ISDN");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 3) == 3) {
            arrayList.add("DATA");
            n3 = n2 | 3;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("TELEX");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("NATIONAL");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("PRIVATE");
            n2 = n3 | 9;
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
            return "ISDN";
        }
        if (n == 3) {
            return "DATA";
        }
        if (n == 4) {
            return "TELEX";
        }
        if (n == 8) {
            return "NATIONAL";
        }
        if (n == 9) {
            return "PRIVATE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

