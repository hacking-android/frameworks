/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaCallWaitingNumberType {
    public static final int INTERNATIONAL = 1;
    public static final int NATIONAL = 2;
    public static final int NETWORK_SPECIFIC = 3;
    public static final int SUBSCRIBER = 4;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("INTERNATIONAL");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("NATIONAL");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("NETWORK_SPECIFIC");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SUBSCRIBER");
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "INTERNATIONAL";
        }
        if (n == 2) {
            return "NATIONAL";
        }
        if (n == 3) {
            return "NETWORK_SPECIFIC";
        }
        if (n == 4) {
            return "SUBSCRIBER";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

