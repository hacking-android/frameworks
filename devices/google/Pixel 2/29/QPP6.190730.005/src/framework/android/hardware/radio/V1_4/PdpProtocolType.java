/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class PdpProtocolType {
    public static final int IP = 0;
    public static final int IPV4V6 = 2;
    public static final int IPV6 = 1;
    public static final int NON_IP = 4;
    public static final int PPP = 3;
    public static final int UNKNOWN = -1;
    public static final int UNSTRUCTURED = 5;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & -1) == -1) {
            arrayList.add("UNKNOWN");
            n2 = 0 | -1;
        }
        arrayList.add("IP");
        int n3 = n2;
        if ((n & 1) == 1) {
            arrayList.add("IPV6");
            n3 = n2 | 1;
        }
        n2 = n3;
        if ((n & 2) == 2) {
            arrayList.add("IPV4V6");
            n2 = n3 | 2;
        }
        n3 = n2;
        if ((n & 3) == 3) {
            arrayList.add("PPP");
            n3 = n2 | 3;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("NON_IP");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 5) == 5) {
            arrayList.add("UNSTRUCTURED");
            n3 = n2 | 5;
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
        if (n == -1) {
            return "UNKNOWN";
        }
        if (n == 0) {
            return "IP";
        }
        if (n == 1) {
            return "IPV6";
        }
        if (n == 2) {
            return "IPV4V6";
        }
        if (n == 3) {
            return "PPP";
        }
        if (n == 4) {
            return "NON_IP";
        }
        if (n == 5) {
            return "UNSTRUCTURED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

