/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.cas.V1_0;

import java.util.ArrayList;

public final class Status {
    public static final int BAD_VALUE = 6;
    public static final int ERROR_CAS_CANNOT_HANDLE = 4;
    public static final int ERROR_CAS_DECRYPT = 13;
    public static final int ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED = 12;
    public static final int ERROR_CAS_DEVICE_REVOKED = 11;
    public static final int ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION = 9;
    public static final int ERROR_CAS_INVALID_STATE = 5;
    public static final int ERROR_CAS_LICENSE_EXPIRED = 2;
    public static final int ERROR_CAS_NOT_PROVISIONED = 7;
    public static final int ERROR_CAS_NO_LICENSE = 1;
    public static final int ERROR_CAS_RESOURCE_BUSY = 8;
    public static final int ERROR_CAS_SESSION_NOT_OPENED = 3;
    public static final int ERROR_CAS_TAMPER_DETECTED = 10;
    public static final int ERROR_CAS_UNKNOWN = 14;
    public static final int OK = 0;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("OK");
        if ((n & 1) == 1) {
            arrayList.add("ERROR_CAS_NO_LICENSE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("ERROR_CAS_LICENSE_EXPIRED");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("ERROR_CAS_SESSION_NOT_OPENED");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("ERROR_CAS_CANNOT_HANDLE");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("ERROR_CAS_INVALID_STATE");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("BAD_VALUE");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("ERROR_CAS_NOT_PROVISIONED");
            n2 = n3 | 7;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("ERROR_CAS_RESOURCE_BUSY");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 9) == 9) {
            arrayList.add("ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION");
            n2 = n3 | 9;
        }
        n3 = n2;
        if ((n & 10) == 10) {
            arrayList.add("ERROR_CAS_TAMPER_DETECTED");
            n3 = n2 | 10;
        }
        n2 = n3;
        if ((n & 11) == 11) {
            arrayList.add("ERROR_CAS_DEVICE_REVOKED");
            n2 = n3 | 11;
        }
        n3 = n2;
        if ((n & 12) == 12) {
            arrayList.add("ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED");
            n3 = n2 | 12;
        }
        n2 = n3;
        if ((n & 13) == 13) {
            arrayList.add("ERROR_CAS_DECRYPT");
            n2 = n3 | 13;
        }
        n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("ERROR_CAS_UNKNOWN");
            n3 = n2 | 14;
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
            return "OK";
        }
        if (n == 1) {
            return "ERROR_CAS_NO_LICENSE";
        }
        if (n == 2) {
            return "ERROR_CAS_LICENSE_EXPIRED";
        }
        if (n == 3) {
            return "ERROR_CAS_SESSION_NOT_OPENED";
        }
        if (n == 4) {
            return "ERROR_CAS_CANNOT_HANDLE";
        }
        if (n == 5) {
            return "ERROR_CAS_INVALID_STATE";
        }
        if (n == 6) {
            return "BAD_VALUE";
        }
        if (n == 7) {
            return "ERROR_CAS_NOT_PROVISIONED";
        }
        if (n == 8) {
            return "ERROR_CAS_RESOURCE_BUSY";
        }
        if (n == 9) {
            return "ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION";
        }
        if (n == 10) {
            return "ERROR_CAS_TAMPER_DETECTED";
        }
        if (n == 11) {
            return "ERROR_CAS_DEVICE_REVOKED";
        }
        if (n == 12) {
            return "ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED";
        }
        if (n == 13) {
            return "ERROR_CAS_DECRYPT";
        }
        if (n == 14) {
            return "ERROR_CAS_UNKNOWN";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

