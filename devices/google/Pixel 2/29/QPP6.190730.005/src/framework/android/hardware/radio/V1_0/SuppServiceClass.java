/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SuppServiceClass {
    public static final int DATA = 2;
    public static final int DATA_ASYNC = 32;
    public static final int DATA_SYNC = 16;
    public static final int FAX = 4;
    public static final int MAX = 128;
    public static final int NONE = 0;
    public static final int PACKET = 64;
    public static final int PAD = 128;
    public static final int SMS = 8;
    public static final int VOICE = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 1) == 1) {
            arrayList.add("VOICE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("DATA");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("FAX");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("SMS");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("DATA_SYNC");
            n2 = n3 | 16;
        }
        int n4 = n2;
        if ((n & 32) == 32) {
            arrayList.add("DATA_ASYNC");
            n4 = n2 | 32;
        }
        n3 = n4;
        if ((n & 64) == 64) {
            arrayList.add("PACKET");
            n3 = n4 | 64;
        }
        n2 = n3;
        if ((n & 128) == 128) {
            arrayList.add("PAD");
            n2 = n3 | 128;
        }
        n3 = n2;
        if ((n & 128) == 128) {
            arrayList.add("MAX");
            n3 = n2 | 128;
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
            return "NONE";
        }
        if (n == 1) {
            return "VOICE";
        }
        if (n == 2) {
            return "DATA";
        }
        if (n == 4) {
            return "FAX";
        }
        if (n == 8) {
            return "SMS";
        }
        if (n == 16) {
            return "DATA_SYNC";
        }
        if (n == 32) {
            return "DATA_ASYNC";
        }
        if (n == 64) {
            return "PACKET";
        }
        if (n == 128) {
            return "PAD";
        }
        if (n == 128) {
            return "MAX";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

