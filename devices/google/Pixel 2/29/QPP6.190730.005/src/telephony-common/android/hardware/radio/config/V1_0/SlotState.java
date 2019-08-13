/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.config.V1_0;

import java.util.ArrayList;

public final class SlotState {
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("INACTIVE");
        if ((n & 1) == 1) {
            arrayList.add("ACTIVE");
            n2 = false | true;
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
            return "INACTIVE";
        }
        if (n == 1) {
            return "ACTIVE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

