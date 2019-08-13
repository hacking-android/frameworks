/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSmsNumberType {
    public static final int ABBREVIATED = 6;
    public static final int ALPHANUMERIC = 5;
    public static final int INTERNATIONAL_OR_DATA_IP = 1;
    public static final int NATIONAL_OR_INTERNET_MAIL = 2;
    public static final int NETWORK = 3;
    public static final int RESERVED_7 = 7;
    public static final int SUBSCRIBER = 4;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("INTERNATIONAL_OR_DATA_IP");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("NATIONAL_OR_INTERNET_MAIL");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("NETWORK");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SUBSCRIBER");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("ALPHANUMERIC");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("ABBREVIATED");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("RESERVED_7");
            n2 = n3 | 7;
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
            return "INTERNATIONAL_OR_DATA_IP";
        }
        if (n == 2) {
            return "NATIONAL_OR_INTERNET_MAIL";
        }
        if (n == 3) {
            return "NETWORK";
        }
        if (n == 4) {
            return "SUBSCRIBER";
        }
        if (n == 5) {
            return "ALPHANUMERIC";
        }
        if (n == 6) {
            return "ABBREVIATED";
        }
        if (n == 7) {
            return "RESERVED_7";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

