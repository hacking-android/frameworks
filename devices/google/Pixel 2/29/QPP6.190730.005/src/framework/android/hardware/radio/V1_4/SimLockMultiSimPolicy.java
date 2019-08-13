/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class SimLockMultiSimPolicy {
    public static final int NO_MULTISIM_POLICY = 0;
    public static final int ONE_VALID_SIM_MUST_BE_PRESENT = 1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NO_MULTISIM_POLICY");
        if ((n & 1) == 1) {
            arrayList.add("ONE_VALID_SIM_MUST_BE_PRESENT");
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
            return "NO_MULTISIM_POLICY";
        }
        if (n == 1) {
            return "ONE_VALID_SIM_MUST_BE_PRESENT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

