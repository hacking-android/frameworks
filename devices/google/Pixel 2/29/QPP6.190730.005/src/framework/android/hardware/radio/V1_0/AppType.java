/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class AppType {
    public static final int CSIM = 4;
    public static final int ISIM = 5;
    public static final int RUIM = 3;
    public static final int SIM = 1;
    public static final int UNKNOWN = 0;
    public static final int USIM = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("SIM");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("USIM");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("RUIM");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CSIM");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("ISIM");
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "SIM";
        }
        if (n == 2) {
            return "USIM";
        }
        if (n == 3) {
            return "RUIM";
        }
        if (n == 4) {
            return "CSIM";
        }
        if (n == 5) {
            return "ISIM";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

