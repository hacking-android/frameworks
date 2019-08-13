/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.vibrator.V1_0;

import java.util.ArrayList;

public final class EffectStrength {
    public static final byte LIGHT = 0;
    public static final byte MEDIUM = 1;
    public static final byte STRONG = 2;

    public static final String dumpBitfield(byte by) {
        ArrayList<String> arrayList = new ArrayList<String>();
        byte by2 = 0;
        arrayList.add("LIGHT");
        if ((by & 1) == 1) {
            arrayList.add("MEDIUM");
            by2 = (byte)(false | true ? 1 : 0);
        }
        byte by3 = by2;
        if ((by & 2) == 2) {
            arrayList.add("STRONG");
            by3 = (byte)(by2 | 2);
        }
        if (by != by3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(Byte.toUnsignedInt((byte)(by3 & by))));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(byte by) {
        if (by == 0) {
            return "LIGHT";
        }
        if (by == 1) {
            return "MEDIUM";
        }
        if (by == 2) {
            return "STRONG";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(Byte.toUnsignedInt(by)));
        return stringBuilder.toString();
    }
}

