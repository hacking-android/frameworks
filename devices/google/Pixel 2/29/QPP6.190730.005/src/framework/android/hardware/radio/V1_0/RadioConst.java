/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioConst {
    public static final int CARD_MAX_APPS = 8;
    public static final int CDMA_ALPHA_INFO_BUFFER_LENGTH = 64;
    public static final int CDMA_MAX_NUMBER_OF_INFO_RECS = 10;
    public static final int CDMA_NUMBER_INFO_BUFFER_LENGTH = 81;
    public static final int MAX_CLIENT_ID_LENGTH = 2;
    public static final int MAX_DEBUG_SOCKET_NAME_LENGTH = 12;
    public static final int MAX_QEMU_PIPE_NAME_LENGTH = 11;
    public static final int MAX_RILDS = 3;
    public static final int MAX_SOCKET_NAME_LENGTH = 6;
    public static final int MAX_UUID_LENGTH = 64;
    public static final int NUM_SERVICE_CLASSES = 7;
    public static final int NUM_TX_POWER_LEVELS = 5;
    public static final int SS_INFO_MAX = 4;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 64) == 64) {
            arrayList.add("CDMA_ALPHA_INFO_BUFFER_LENGTH");
            n2 = 0 | 64;
        }
        int n3 = n2;
        if ((n & 81) == 81) {
            arrayList.add("CDMA_NUMBER_INFO_BUFFER_LENGTH");
            n3 = n2 | 81;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("MAX_RILDS");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("MAX_SOCKET_NAME_LENGTH");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 2) == 2) {
            arrayList.add("MAX_CLIENT_ID_LENGTH");
            n2 = n3 | 2;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("MAX_DEBUG_SOCKET_NAME_LENGTH");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("MAX_QEMU_PIPE_NAME_LENGTH");
            n2 = n3 | 11;
        }
        int n4 = n2;
        if ((n & 64) == 64) {
            arrayList.add("MAX_UUID_LENGTH");
            n4 = n2 | 64;
        }
        n3 = n4;
        if ((n & 8) == 8) {
            arrayList.add("CARD_MAX_APPS");
            n3 = n4 | 8;
        }
        n2 = n3;
        if ((n & 10) == 10) {
            arrayList.add("CDMA_MAX_NUMBER_OF_INFO_RECS");
            n2 = n3 | 10;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SS_INFO_MAX");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("NUM_SERVICE_CLASSES");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 5) == 5) {
            arrayList.add("NUM_TX_POWER_LEVELS");
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
        if (n == 64) {
            return "CDMA_ALPHA_INFO_BUFFER_LENGTH";
        }
        if (n == 81) {
            return "CDMA_NUMBER_INFO_BUFFER_LENGTH";
        }
        if (n == 3) {
            return "MAX_RILDS";
        }
        if (n == 6) {
            return "MAX_SOCKET_NAME_LENGTH";
        }
        if (n == 2) {
            return "MAX_CLIENT_ID_LENGTH";
        }
        if (n == 12) {
            return "MAX_DEBUG_SOCKET_NAME_LENGTH";
        }
        if (n == 11) {
            return "MAX_QEMU_PIPE_NAME_LENGTH";
        }
        if (n == 64) {
            return "MAX_UUID_LENGTH";
        }
        if (n == 8) {
            return "CARD_MAX_APPS";
        }
        if (n == 10) {
            return "CDMA_MAX_NUMBER_OF_INFO_RECS";
        }
        if (n == 4) {
            return "SS_INFO_MAX";
        }
        if (n == 7) {
            return "NUM_SERVICE_CLASSES";
        }
        if (n == 5) {
            return "NUM_TX_POWER_LEVELS";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

