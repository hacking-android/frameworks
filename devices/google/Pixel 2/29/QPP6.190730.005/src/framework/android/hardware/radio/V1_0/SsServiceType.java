/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SsServiceType {
    public static final int ALL_BARRING = 16;
    public static final int BAIC = 14;
    public static final int BAIC_ROAMING = 15;
    public static final int BAOC = 11;
    public static final int BAOIC = 12;
    public static final int BAOIC_EXC_HOME = 13;
    public static final int CFU = 0;
    public static final int CF_ALL = 4;
    public static final int CF_ALL_CONDITIONAL = 5;
    public static final int CF_BUSY = 1;
    public static final int CF_NOT_REACHABLE = 3;
    public static final int CF_NO_REPLY = 2;
    public static final int CLIP = 6;
    public static final int CLIR = 7;
    public static final int COLP = 8;
    public static final int COLR = 9;
    public static final int INCOMING_BARRING = 18;
    public static final int OUTGOING_BARRING = 17;
    public static final int WAIT = 10;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("CFU");
        if ((n & 1) == 1) {
            arrayList.add("CF_BUSY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CF_NO_REPLY");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("CF_NOT_REACHABLE");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CF_ALL");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("CF_ALL_CONDITIONAL");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("CLIP");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("CLIR");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("COLP");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("COLR");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("WAIT");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("BAOC");
            n2 = n3 | 11;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("BAOIC");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("BAOIC_EXC_HOME");
            n2 = n3 | 13;
        }
        int n4 = n2;
        if ((n & 14) == 14) {
            arrayList.add("BAIC");
            n4 = n2 | 14;
        }
        n3 = n4;
        if ((n & 15) == 15) {
            arrayList.add("BAIC_ROAMING");
            n3 = n4 | 15;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("ALL_BARRING");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 17) == 17) {
            arrayList.add("OUTGOING_BARRING");
            n3 = n2 | 17;
        }
        n2 = n3;
        if ((n & 18) == 18) {
            arrayList.add("INCOMING_BARRING");
            n2 = n3 | 18;
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
            return "CFU";
        }
        if (n == 1) {
            return "CF_BUSY";
        }
        if (n == 2) {
            return "CF_NO_REPLY";
        }
        if (n == 3) {
            return "CF_NOT_REACHABLE";
        }
        if (n == 4) {
            return "CF_ALL";
        }
        if (n == 5) {
            return "CF_ALL_CONDITIONAL";
        }
        if (n == 6) {
            return "CLIP";
        }
        if (n == 7) {
            return "CLIR";
        }
        if (n == 8) {
            return "COLP";
        }
        if (n == 9) {
            return "COLR";
        }
        if (n == 10) {
            return "WAIT";
        }
        if (n == 11) {
            return "BAOC";
        }
        if (n == 12) {
            return "BAOIC";
        }
        if (n == 13) {
            return "BAOIC_EXC_HOME";
        }
        if (n == 14) {
            return "BAIC";
        }
        if (n == 15) {
            return "BAIC_ROAMING";
        }
        if (n == 16) {
            return "ALL_BARRING";
        }
        if (n == 17) {
            return "OUTGOING_BARRING";
        }
        if (n == 18) {
            return "INCOMING_BARRING";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

