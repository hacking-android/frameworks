/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SapConnectRsp {
    public static final int CONNECT_FAILURE = 1;
    public static final int CONNECT_OK_CALL_ONGOING = 4;
    public static final int MSG_SIZE_TOO_LARGE = 2;
    public static final int MSG_SIZE_TOO_SMALL = 3;
    public static final int SUCCESS = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("SUCCESS");
        if ((n & 1) == 1) {
            arrayList.add("CONNECT_FAILURE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("MSG_SIZE_TOO_LARGE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("MSG_SIZE_TOO_SMALL");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("CONNECT_OK_CALL_ONGOING");
            n3 = n2 | 4;
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
            return "SUCCESS";
        }
        if (n == 1) {
            return "CONNECT_FAILURE";
        }
        if (n == 2) {
            return "MSG_SIZE_TOO_LARGE";
        }
        if (n == 3) {
            return "MSG_SIZE_TOO_SMALL";
        }
        if (n == 4) {
            return "CONNECT_OK_CALL_ONGOING";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

