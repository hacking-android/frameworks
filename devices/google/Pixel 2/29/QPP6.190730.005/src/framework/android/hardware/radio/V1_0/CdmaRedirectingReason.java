/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaRedirectingReason {
    public static final int CALLED_DTE_OUT_OF_ORDER = 9;
    public static final int CALL_FORWARDING_BUSY = 1;
    public static final int CALL_FORWARDING_BY_THE_CALLED_DTE = 10;
    public static final int CALL_FORWARDING_NO_REPLY = 2;
    public static final int CALL_FORWARDING_UNCONDITIONAL = 15;
    public static final int RESERVED = 16;
    public static final int UNKNOWN = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("UNKNOWN");
        if ((n & 1) == 1) {
            arrayList.add("CALL_FORWARDING_BUSY");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("CALL_FORWARDING_NO_REPLY");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("CALLED_DTE_OUT_OF_ORDER");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("CALL_FORWARDING_BY_THE_CALLED_DTE");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 15) == 15) {
            arrayList.add("CALL_FORWARDING_UNCONDITIONAL");
            n2 = n3 | 15;
        }
        n3 = n2;
        if ((n & 16) == 16) {
            arrayList.add("RESERVED");
            n3 = n2 | 16;
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
            return "UNKNOWN";
        }
        if (n == 1) {
            return "CALL_FORWARDING_BUSY";
        }
        if (n == 2) {
            return "CALL_FORWARDING_NO_REPLY";
        }
        if (n == 9) {
            return "CALLED_DTE_OUT_OF_ORDER";
        }
        if (n == 10) {
            return "CALL_FORWARDING_BY_THE_CALLED_DTE";
        }
        if (n == 15) {
            return "CALL_FORWARDING_UNCONDITIONAL";
        }
        if (n == 16) {
            return "RESERVED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

