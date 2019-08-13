/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaRoamingType {
    public static final int AFFILIATED_ROAM = 1;
    public static final int ANY_ROAM = 2;
    public static final int HOME_NETWORK = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("HOME_NETWORK");
        if ((n & 1) == 1) {
            arrayList.add("AFFILIATED_ROAM");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("ANY_ROAM");
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
            return "HOME_NETWORK";
        }
        if (n == 1) {
            return "AFFILIATED_ROAM";
        }
        if (n == 2) {
            return "ANY_ROAM";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

