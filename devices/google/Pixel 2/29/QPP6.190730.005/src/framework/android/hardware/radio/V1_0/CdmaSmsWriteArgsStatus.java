/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSmsWriteArgsStatus {
    public static final int REC_READ = 1;
    public static final int REC_UNREAD = 0;
    public static final int STO_SENT = 3;
    public static final int STO_UNSENT = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("REC_UNREAD");
        if ((n & 1) == 1) {
            arrayList.add("REC_READ");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("STO_UNSENT");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("STO_SENT");
            n2 = n3 | 3;
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
            return "REC_UNREAD";
        }
        if (n == 1) {
            return "REC_READ";
        }
        if (n == 2) {
            return "STO_UNSENT";
        }
        if (n == 3) {
            return "STO_SENT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

