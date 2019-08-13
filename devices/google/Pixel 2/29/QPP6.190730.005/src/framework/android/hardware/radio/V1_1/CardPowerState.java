/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import java.util.ArrayList;

public final class CardPowerState {
    public static final int POWER_DOWN = 0;
    public static final int POWER_UP = 1;
    public static final int POWER_UP_PASS_THROUGH = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("POWER_DOWN");
        if ((n & 1) == 1) {
            arrayList.add("POWER_UP");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("POWER_UP_PASS_THROUGH");
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
            return "POWER_DOWN";
        }
        if (n == 1) {
            return "POWER_UP";
        }
        if (n == 2) {
            return "POWER_UP_PASS_THROUGH";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

