/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class DataProfileId {
    public static final int CBS = 4;
    public static final int DEFAULT = 0;
    public static final int FOTA = 3;
    public static final int IMS = 2;
    public static final int INVALID = -1;
    public static final int OEM_BASE = 1000;
    public static final int TETHERED = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("DEFAULT");
        if ((n & 1) == 1) {
            arrayList.add("TETHERED");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("IMS");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("FOTA");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CBS");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 1000) == 1000) {
            arrayList.add("OEM_BASE");
            n2 = n3 | 1000;
        }
        n3 = n2;
        if ((n & -1) == -1) {
            arrayList.add("INVALID");
            n3 = n2 | -1;
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
            return "DEFAULT";
        }
        if (n == 1) {
            return "TETHERED";
        }
        if (n == 2) {
            return "IMS";
        }
        if (n == 3) {
            return "FOTA";
        }
        if (n == 4) {
            return "CBS";
        }
        if (n == 1000) {
            return "OEM_BASE";
        }
        if (n == -1) {
            return "INVALID";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

