/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.vibrator.V1_1;

import java.util.ArrayList;

public final class Effect_1_1 {
    public static final int CLICK = 0;
    public static final int DOUBLE_CLICK = 1;
    public static final int TICK = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("CLICK");
        if ((n & 1) == 1) {
            arrayList.add("DOUBLE_CLICK");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("TICK");
            n3 = n2 | 2;
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
            return "CLICK";
        }
        if (n == 1) {
            return "DOUBLE_CLICK";
        }
        if (n == 2) {
            return "TICK";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

