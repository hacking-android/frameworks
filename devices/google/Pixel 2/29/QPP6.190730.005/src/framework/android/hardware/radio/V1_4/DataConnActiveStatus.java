/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class DataConnActiveStatus {
    public static final int ACTIVE = 2;
    public static final int DORMANT = 1;
    public static final int INACTIVE = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("INACTIVE");
        if ((n & 1) == 1) {
            arrayList.add("DORMANT");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("ACTIVE");
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
            return "INACTIVE";
        }
        if (n == 1) {
            return "DORMANT";
        }
        if (n == 2) {
            return "ACTIVE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

