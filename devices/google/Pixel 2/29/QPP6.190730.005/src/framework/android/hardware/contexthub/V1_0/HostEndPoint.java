/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class HostEndPoint {
    public static final short BROADCAST = -1;
    public static final short UNSPECIFIED = -2;

    public static final String dumpBitfield(short s) {
        ArrayList<String> arrayList = new ArrayList<String>();
        short s2 = 0;
        if ((s & -1) == -1) {
            arrayList.add("BROADCAST");
            s2 = (short)(0 | -1);
        }
        short s3 = s2;
        if ((s & -2) == -2) {
            arrayList.add("UNSPECIFIED");
            s3 = (short)(s2 | -2);
        }
        if (s != s3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(Short.toUnsignedInt((short)(s3 & s))));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(short s) {
        if (s == -1) {
            return "BROADCAST";
        }
        if (s == -2) {
            return "UNSPECIFIED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(Short.toUnsignedInt(s)));
        return stringBuilder.toString();
    }
}

