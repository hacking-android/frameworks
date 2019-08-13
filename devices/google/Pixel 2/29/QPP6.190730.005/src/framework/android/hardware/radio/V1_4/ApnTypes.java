/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class ApnTypes {
    public static final int ALL = 1023;
    public static final int CBS = 128;
    public static final int DEFAULT = 1;
    public static final int DUN = 8;
    public static final int EMERGENCY = 512;
    public static final int FOTA = 32;
    public static final int HIPRI = 16;
    public static final int IA = 256;
    public static final int IMS = 64;
    public static final int MCX = 1024;
    public static final int MMS = 2;
    public static final int NONE = 0;
    public static final int SUPL = 4;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 1) == 1) {
            arrayList.add("DEFAULT");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("MMS");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("SUPL");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("DUN");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("HIPRI");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("FOTA");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 64) == 64) {
            arrayList.add("IMS");
            n2 = n3 | 64;
        }
        n3 = n2;
        if ((n & 128) == 128) {
            arrayList.add("CBS");
            n3 = n2 | 128;
        }
        n2 = n3;
        if ((n & 256) == 256) {
            arrayList.add("IA");
            n2 = n3 | 256;
        }
        n3 = n2;
        if ((n & 512) == 512) {
            arrayList.add("EMERGENCY");
            n3 = n2 | 512;
        }
        n2 = n3;
        if ((n & 1023) == 1023) {
            arrayList.add("ALL");
            n2 = n3 | 1023;
        }
        n3 = n2;
        if ((n & 1024) == 1024) {
            arrayList.add("MCX");
            n3 = n2 | 1024;
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
            return "DEFAULT";
        }
        if (n == 2) {
            return "MMS";
        }
        if (n == 4) {
            return "SUPL";
        }
        if (n == 8) {
            return "DUN";
        }
        if (n == 16) {
            return "HIPRI";
        }
        if (n == 32) {
            return "FOTA";
        }
        if (n == 64) {
            return "IMS";
        }
        if (n == 128) {
            return "CBS";
        }
        if (n == 256) {
            return "IA";
        }
        if (n == 512) {
            return "EMERGENCY";
        }
        if (n == 1023) {
            return "ALL";
        }
        if (n == 1024) {
            return "MCX";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

