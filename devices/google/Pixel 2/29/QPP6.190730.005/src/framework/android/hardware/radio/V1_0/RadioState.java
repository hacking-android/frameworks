/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioState {
    public static final int OFF = 0;
    public static final int ON = 10;
    public static final int UNAVAILABLE = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("OFF");
        if ((n & 1) == 1) {
            arrayList.add("UNAVAILABLE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("ON");
            n3 = n2 | 10;
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
            return "OFF";
        }
        if (n == 1) {
            return "UNAVAILABLE";
        }
        if (n == 10) {
            return "ON";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

