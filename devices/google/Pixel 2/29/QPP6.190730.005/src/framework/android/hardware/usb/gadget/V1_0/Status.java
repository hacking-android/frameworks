/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb.gadget.V1_0;

import java.util.ArrayList;

public final class Status {
    public static final int CONFIGURATION_NOT_SUPPORTED = 4;
    public static final int ERROR = 1;
    public static final int FUNCTIONS_APPLIED = 2;
    public static final int FUNCTIONS_NOT_APPLIED = 3;
    public static final int SUCCESS = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("SUCCESS");
        if ((n & 1) == 1) {
            arrayList.add("ERROR");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("FUNCTIONS_APPLIED");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("FUNCTIONS_NOT_APPLIED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CONFIGURATION_NOT_SUPPORTED");
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
            return "SUCCESS";
        }
        if (n == 1) {
            return "ERROR";
        }
        if (n == 2) {
            return "FUNCTIONS_APPLIED";
        }
        if (n == 3) {
            return "FUNCTIONS_NOT_APPLIED";
        }
        if (n == 4) {
            return "CONFIGURATION_NOT_SUPPORTED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

