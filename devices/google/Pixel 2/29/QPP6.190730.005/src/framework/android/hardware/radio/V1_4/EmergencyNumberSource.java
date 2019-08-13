/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class EmergencyNumberSource {
    public static final int DEFAULT = 8;
    public static final int MODEM_CONFIG = 4;
    public static final int NETWORK_SIGNALING = 1;
    public static final int SIM = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("NETWORK_SIGNALING");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("SIM");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 4) == 4) {
            arrayList.add("MODEM_CONFIG");
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("DEFAULT");
            n3 = n2 | 8;
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
        if (n == 1) {
            return "NETWORK_SIGNALING";
        }
        if (n == 2) {
            return "SIM";
        }
        if (n == 4) {
            return "MODEM_CONFIG";
        }
        if (n == 8) {
            return "DEFAULT";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

