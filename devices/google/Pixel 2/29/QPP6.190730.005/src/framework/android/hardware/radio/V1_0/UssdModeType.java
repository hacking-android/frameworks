/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class UssdModeType {
    public static final int LOCAL_CLIENT = 3;
    public static final int NOTIFY = 0;
    public static final int NOT_SUPPORTED = 4;
    public static final int NW_RELEASE = 2;
    public static final int NW_TIMEOUT = 5;
    public static final int REQUEST = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NOTIFY");
        if ((n & 1) == 1) {
            arrayList.add("REQUEST");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("NW_RELEASE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("LOCAL_CLIENT");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("NOT_SUPPORTED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("NW_TIMEOUT");
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
            return "NOTIFY";
        }
        if (n == 1) {
            return "REQUEST";
        }
        if (n == 2) {
            return "NW_RELEASE";
        }
        if (n == 3) {
            return "LOCAL_CLIENT";
        }
        if (n == 4) {
            return "NOT_SUPPORTED";
        }
        if (n == 5) {
            return "NW_TIMEOUT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

