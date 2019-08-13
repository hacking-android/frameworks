/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class UusType {
    public static final int TYPE1_IMPLICIT = 0;
    public static final int TYPE1_NOT_REQUIRED = 2;
    public static final int TYPE1_REQUIRED = 1;
    public static final int TYPE2_NOT_REQUIRED = 4;
    public static final int TYPE2_REQUIRED = 3;
    public static final int TYPE3_NOT_REQUIRED = 6;
    public static final int TYPE3_REQUIRED = 5;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("TYPE1_IMPLICIT");
        if ((n & 1) == 1) {
            arrayList.add("TYPE1_REQUIRED");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("TYPE1_NOT_REQUIRED");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("TYPE2_REQUIRED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("TYPE2_NOT_REQUIRED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("TYPE3_REQUIRED");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("TYPE3_NOT_REQUIRED");
            n3 = n2 | 6;
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
            return "TYPE1_IMPLICIT";
        }
        if (n == 1) {
            return "TYPE1_REQUIRED";
        }
        if (n == 2) {
            return "TYPE1_NOT_REQUIRED";
        }
        if (n == 3) {
            return "TYPE2_REQUIRED";
        }
        if (n == 4) {
            return "TYPE2_NOT_REQUIRED";
        }
        if (n == 5) {
            return "TYPE3_REQUIRED";
        }
        if (n == 6) {
            return "TYPE3_NOT_REQUIRED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

