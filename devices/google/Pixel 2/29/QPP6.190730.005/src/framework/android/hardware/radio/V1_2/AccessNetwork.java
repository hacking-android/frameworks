/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import java.util.ArrayList;

public final class AccessNetwork {
    public static final int CDMA2000 = 4;
    public static final int EUTRAN = 3;
    public static final int GERAN = 1;
    public static final int IWLAN = 5;
    public static final int UTRAN = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("GERAN");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("UTRAN");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("EUTRAN");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CDMA2000");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("IWLAN");
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
        if (n == 1) {
            return "GERAN";
        }
        if (n == 2) {
            return "UTRAN";
        }
        if (n == 3) {
            return "EUTRAN";
        }
        if (n == 4) {
            return "CDMA2000";
        }
        if (n == 5) {
            return "IWLAN";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

