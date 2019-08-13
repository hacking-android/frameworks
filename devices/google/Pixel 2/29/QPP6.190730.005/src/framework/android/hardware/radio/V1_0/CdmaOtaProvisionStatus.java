/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaOtaProvisionStatus {
    public static final int A_KEY_EXCHANGED = 2;
    public static final int COMMITTED = 8;
    public static final int IMSI_DOWNLOADED = 6;
    public static final int MDN_DOWNLOADED = 5;
    public static final int NAM_DOWNLOADED = 4;
    public static final int OTAPA_ABORTED = 11;
    public static final int OTAPA_STARTED = 9;
    public static final int OTAPA_STOPPED = 10;
    public static final int PRL_DOWNLOADED = 7;
    public static final int SPC_RETRIES_EXCEEDED = 1;
    public static final int SPL_UNLOCKED = 0;
    public static final int SSD_UPDATED = 3;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("SPL_UNLOCKED");
        if ((n & 1) == 1) {
            arrayList.add("SPC_RETRIES_EXCEEDED");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("A_KEY_EXCHANGED");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("SSD_UPDATED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("NAM_DOWNLOADED");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("MDN_DOWNLOADED");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("IMSI_DOWNLOADED");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("PRL_DOWNLOADED");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("COMMITTED");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("OTAPA_STARTED");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("OTAPA_STOPPED");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("OTAPA_ABORTED");
            n2 = n3 | 11;
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
            return "SPL_UNLOCKED";
        }
        if (n == 1) {
            return "SPC_RETRIES_EXCEEDED";
        }
        if (n == 2) {
            return "A_KEY_EXCHANGED";
        }
        if (n == 3) {
            return "SSD_UPDATED";
        }
        if (n == 4) {
            return "NAM_DOWNLOADED";
        }
        if (n == 5) {
            return "MDN_DOWNLOADED";
        }
        if (n == 6) {
            return "IMSI_DOWNLOADED";
        }
        if (n == 7) {
            return "PRL_DOWNLOADED";
        }
        if (n == 8) {
            return "COMMITTED";
        }
        if (n == 9) {
            return "OTAPA_STARTED";
        }
        if (n == 10) {
            return "OTAPA_STOPPED";
        }
        if (n == 11) {
            return "OTAPA_ABORTED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

