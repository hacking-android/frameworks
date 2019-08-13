/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SsTeleserviceType {
    public static final int ALL_DATA_TELESERVICES = 3;
    public static final int ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int ALL_TELESEVICES = 1;
    public static final int ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int SMS_SERVICES = 4;
    public static final int TELEPHONY = 2;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("ALL_TELE_AND_BEARER_SERVICES");
        if ((n & 1) == 1) {
            arrayList.add("ALL_TELESEVICES");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("TELEPHONY");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("ALL_DATA_TELESERVICES");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SMS_SERVICES");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("ALL_TELESERVICES_EXCEPT_SMS");
            n2 = n3 | 5;
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
            return "ALL_TELE_AND_BEARER_SERVICES";
        }
        if (n == 1) {
            return "ALL_TELESEVICES";
        }
        if (n == 2) {
            return "TELEPHONY";
        }
        if (n == 3) {
            return "ALL_DATA_TELESERVICES";
        }
        if (n == 4) {
            return "SMS_SERVICES";
        }
        if (n == 5) {
            return "ALL_TELESERVICES_EXCEPT_SMS";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

