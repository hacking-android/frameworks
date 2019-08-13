/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioError {
    public static final int ABORTED = 65;
    public static final int CANCELLED = 7;
    public static final int DEVICE_IN_USE = 64;
    public static final int DIAL_MODIFIED_TO_DIAL = 20;
    public static final int DIAL_MODIFIED_TO_SS = 19;
    public static final int DIAL_MODIFIED_TO_USSD = 18;
    public static final int EMPTY_RECORD = 55;
    public static final int ENCODING_ERR = 57;
    public static final int FDN_CHECK_FAILURE = 14;
    public static final int GENERIC_FAILURE = 2;
    public static final int ILLEGAL_SIM_OR_ME = 15;
    public static final int INTERNAL_ERR = 38;
    public static final int INVALID_ARGUMENTS = 44;
    public static final int INVALID_CALL_ID = 47;
    public static final int INVALID_MODEM_STATE = 46;
    public static final int INVALID_RESPONSE = 66;
    public static final int INVALID_SIM_STATE = 45;
    public static final int INVALID_SMSC_ADDRESS = 58;
    public static final int INVALID_SMS_FORMAT = 56;
    public static final int INVALID_STATE = 41;
    public static final int LCE_NOT_SUPPORTED = 36;
    public static final int MISSING_RESOURCE = 16;
    public static final int MODEM_ERR = 40;
    public static final int MODE_NOT_SUPPORTED = 13;
    public static final int NETWORK_ERR = 49;
    public static final int NETWORK_NOT_READY = 60;
    public static final int NETWORK_REJECT = 53;
    public static final int NONE = 0;
    public static final int NOT_PROVISIONED = 61;
    public static final int NO_MEMORY = 37;
    public static final int NO_NETWORK_FOUND = 63;
    public static final int NO_RESOURCES = 42;
    public static final int NO_SMS_TO_ACK = 48;
    public static final int NO_SUBSCRIPTION = 62;
    public static final int NO_SUCH_ELEMENT = 17;
    public static final int NO_SUCH_ENTRY = 59;
    public static final int OEM_ERROR_1 = 501;
    public static final int OEM_ERROR_10 = 510;
    public static final int OEM_ERROR_11 = 511;
    public static final int OEM_ERROR_12 = 512;
    public static final int OEM_ERROR_13 = 513;
    public static final int OEM_ERROR_14 = 514;
    public static final int OEM_ERROR_15 = 515;
    public static final int OEM_ERROR_16 = 516;
    public static final int OEM_ERROR_17 = 517;
    public static final int OEM_ERROR_18 = 518;
    public static final int OEM_ERROR_19 = 519;
    public static final int OEM_ERROR_2 = 502;
    public static final int OEM_ERROR_20 = 520;
    public static final int OEM_ERROR_21 = 521;
    public static final int OEM_ERROR_22 = 522;
    public static final int OEM_ERROR_23 = 523;
    public static final int OEM_ERROR_24 = 524;
    public static final int OEM_ERROR_25 = 525;
    public static final int OEM_ERROR_3 = 503;
    public static final int OEM_ERROR_4 = 504;
    public static final int OEM_ERROR_5 = 505;
    public static final int OEM_ERROR_6 = 506;
    public static final int OEM_ERROR_7 = 507;
    public static final int OEM_ERROR_8 = 508;
    public static final int OEM_ERROR_9 = 509;
    public static final int OPERATION_NOT_ALLOWED = 54;
    public static final int OP_NOT_ALLOWED_BEFORE_REG_TO_NW = 9;
    public static final int OP_NOT_ALLOWED_DURING_VOICE_CALL = 8;
    public static final int PASSWORD_INCORRECT = 3;
    public static final int RADIO_NOT_AVAILABLE = 1;
    public static final int REQUEST_NOT_SUPPORTED = 6;
    public static final int REQUEST_RATE_LIMITED = 50;
    public static final int SIM_ABSENT = 11;
    public static final int SIM_BUSY = 51;
    public static final int SIM_ERR = 43;
    public static final int SIM_FULL = 52;
    public static final int SIM_PIN2 = 4;
    public static final int SIM_PUK2 = 5;
    public static final int SMS_SEND_FAIL_RETRY = 10;
    public static final int SS_MODIFIED_TO_DIAL = 24;
    public static final int SS_MODIFIED_TO_SS = 27;
    public static final int SS_MODIFIED_TO_USSD = 25;
    public static final int SUBSCRIPTION_NOT_AVAILABLE = 12;
    public static final int SUBSCRIPTION_NOT_SUPPORTED = 26;
    public static final int SYSTEM_ERR = 39;
    public static final int USSD_MODIFIED_TO_DIAL = 21;
    public static final int USSD_MODIFIED_TO_SS = 22;
    public static final int USSD_MODIFIED_TO_USSD = 23;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 1) == 1) {
            arrayList.add("RADIO_NOT_AVAILABLE");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("GENERIC_FAILURE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("PASSWORD_INCORRECT");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("SIM_PIN2");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("SIM_PUK2");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("REQUEST_NOT_SUPPORTED");
            n3 = n2 | 6;
        }
        int n4 = n3;
        if ((n & 7) == 7) {
            arrayList.add("CANCELLED");
            n4 = n3 | 7;
        }
        n2 = n4;
        if ((n & 8) == 8) {
            arrayList.add("OP_NOT_ALLOWED_DURING_VOICE_CALL");
            n2 = n4 | 8;
        }
        n3 = n2;
        if ((n & 9) == 9) {
            arrayList.add("OP_NOT_ALLOWED_BEFORE_REG_TO_NW");
            n3 = n2 | 9;
        }
        n2 = n3;
        if ((n & 10) == 10) {
            arrayList.add("SMS_SEND_FAIL_RETRY");
            n2 = n3 | 10;
        }
        n3 = n2;
        if ((n & 11) == 11) {
            arrayList.add("SIM_ABSENT");
            n3 = n2 | 11;
        }
        n2 = n3;
        if ((n & 12) == 12) {
            arrayList.add("SUBSCRIPTION_NOT_AVAILABLE");
            n2 = n3 | 12;
        }
        n3 = n2;
        if ((n & 13) == 13) {
            arrayList.add("MODE_NOT_SUPPORTED");
            n3 = n2 | 13;
        }
        n2 = n3;
        if ((n & 14) == 14) {
            arrayList.add("FDN_CHECK_FAILURE");
            n2 = n3 | 14;
        }
        n3 = n2;
        if ((n & 15) == 15) {
            arrayList.add("ILLEGAL_SIM_OR_ME");
            n3 = n2 | 15;
        }
        n4 = n3;
        if ((n & 16) == 16) {
            arrayList.add("MISSING_RESOURCE");
            n4 = n3 | 16;
        }
        n2 = n4;
        if ((n & 17) == 17) {
            arrayList.add("NO_SUCH_ELEMENT");
            n2 = n4 | 17;
        }
        n3 = n2;
        if ((n & 18) == 18) {
            arrayList.add("DIAL_MODIFIED_TO_USSD");
            n3 = n2 | 18;
        }
        n2 = n3;
        if ((n & 19) == 19) {
            arrayList.add("DIAL_MODIFIED_TO_SS");
            n2 = n3 | 19;
        }
        n3 = n2;
        if ((n & 20) == 20) {
            arrayList.add("DIAL_MODIFIED_TO_DIAL");
            n3 = n2 | 20;
        }
        n2 = n3;
        if ((n & 21) == 21) {
            arrayList.add("USSD_MODIFIED_TO_DIAL");
            n2 = n3 | 21;
        }
        n3 = n2;
        if ((n & 22) == 22) {
            arrayList.add("USSD_MODIFIED_TO_SS");
            n3 = n2 | 22;
        }
        n2 = n3;
        if ((n & 23) == 23) {
            arrayList.add("USSD_MODIFIED_TO_USSD");
            n2 = n3 | 23;
        }
        n3 = n2;
        if ((n & 24) == 24) {
            arrayList.add("SS_MODIFIED_TO_DIAL");
            n3 = n2 | 24;
        }
        n2 = n3;
        if ((n & 25) == 25) {
            arrayList.add("SS_MODIFIED_TO_USSD");
            n2 = n3 | 25;
        }
        n3 = n2;
        if ((n & 26) == 26) {
            arrayList.add("SUBSCRIPTION_NOT_SUPPORTED");
            n3 = n2 | 26;
        }
        n2 = n3;
        if ((n & 27) == 27) {
            arrayList.add("SS_MODIFIED_TO_SS");
            n2 = n3 | 27;
        }
        n4 = n2;
        if ((n & 36) == 36) {
            arrayList.add("LCE_NOT_SUPPORTED");
            n4 = n2 | 36;
        }
        n3 = n4;
        if ((n & 37) == 37) {
            arrayList.add("NO_MEMORY");
            n3 = n4 | 37;
        }
        n2 = n3;
        if ((n & 38) == 38) {
            arrayList.add("INTERNAL_ERR");
            n2 = n3 | 38;
        }
        n4 = n2;
        if ((n & 39) == 39) {
            arrayList.add("SYSTEM_ERR");
            n4 = n2 | 39;
        }
        n3 = n4;
        if ((n & 40) == 40) {
            arrayList.add("MODEM_ERR");
            n3 = n4 | 40;
        }
        n2 = n3;
        if ((n & 41) == 41) {
            arrayList.add("INVALID_STATE");
            n2 = n3 | 41;
        }
        n3 = n2;
        if ((n & 42) == 42) {
            arrayList.add("NO_RESOURCES");
            n3 = n2 | 42;
        }
        n2 = n3;
        if ((n & 43) == 43) {
            arrayList.add("SIM_ERR");
            n2 = n3 | 43;
        }
        n3 = n2;
        if ((n & 44) == 44) {
            arrayList.add("INVALID_ARGUMENTS");
            n3 = n2 | 44;
        }
        n2 = n3;
        if ((n & 45) == 45) {
            arrayList.add("INVALID_SIM_STATE");
            n2 = n3 | 45;
        }
        n3 = n2;
        if ((n & 46) == 46) {
            arrayList.add("INVALID_MODEM_STATE");
            n3 = n2 | 46;
        }
        n2 = n3;
        if ((n & 47) == 47) {
            arrayList.add("INVALID_CALL_ID");
            n2 = n3 | 47;
        }
        n3 = n2;
        if ((n & 48) == 48) {
            arrayList.add("NO_SMS_TO_ACK");
            n3 = n2 | 48;
        }
        n2 = n3;
        if ((n & 49) == 49) {
            arrayList.add("NETWORK_ERR");
            n2 = n3 | 49;
        }
        n3 = n2;
        if ((n & 50) == 50) {
            arrayList.add("REQUEST_RATE_LIMITED");
            n3 = n2 | 50;
        }
        n2 = n3;
        if ((n & 51) == 51) {
            arrayList.add("SIM_BUSY");
            n2 = n3 | 51;
        }
        n3 = n2;
        if ((n & 52) == 52) {
            arrayList.add("SIM_FULL");
            n3 = n2 | 52;
        }
        n4 = n3;
        if ((n & 53) == 53) {
            arrayList.add("NETWORK_REJECT");
            n4 = n3 | 53;
        }
        n2 = n4;
        if ((n & 54) == 54) {
            arrayList.add("OPERATION_NOT_ALLOWED");
            n2 = n4 | 54;
        }
        n3 = n2;
        if ((n & 55) == 55) {
            arrayList.add("EMPTY_RECORD");
            n3 = n2 | 55;
        }
        n2 = n3;
        if ((n & 56) == 56) {
            arrayList.add("INVALID_SMS_FORMAT");
            n2 = n3 | 56;
        }
        n3 = n2;
        if ((n & 57) == 57) {
            arrayList.add("ENCODING_ERR");
            n3 = n2 | 57;
        }
        n4 = n3;
        if ((n & 58) == 58) {
            arrayList.add("INVALID_SMSC_ADDRESS");
            n4 = n3 | 58;
        }
        n2 = n4;
        if ((n & 59) == 59) {
            arrayList.add("NO_SUCH_ENTRY");
            n2 = n4 | 59;
        }
        n3 = n2;
        if ((n & 60) == 60) {
            arrayList.add("NETWORK_NOT_READY");
            n3 = n2 | 60;
        }
        n4 = n3;
        if ((n & 61) == 61) {
            arrayList.add("NOT_PROVISIONED");
            n4 = n3 | 61;
        }
        n2 = n4;
        if ((n & 62) == 62) {
            arrayList.add("NO_SUBSCRIPTION");
            n2 = n4 | 62;
        }
        n3 = n2;
        if ((n & 63) == 63) {
            arrayList.add("NO_NETWORK_FOUND");
            n3 = n2 | 63;
        }
        n2 = n3;
        if ((n & 64) == 64) {
            arrayList.add("DEVICE_IN_USE");
            n2 = n3 | 64;
        }
        n3 = n2;
        if ((n & 65) == 65) {
            arrayList.add("ABORTED");
            n3 = n2 | 65;
        }
        n2 = n3;
        if ((n & 66) == 66) {
            arrayList.add("INVALID_RESPONSE");
            n2 = n3 | 66;
        }
        n3 = n2;
        if ((n & 501) == 501) {
            arrayList.add("OEM_ERROR_1");
            n3 = n2 | 501;
        }
        n2 = n3;
        if ((n & 502) == 502) {
            arrayList.add("OEM_ERROR_2");
            n2 = n3 | 502;
        }
        n3 = n2;
        if ((n & 503) == 503) {
            arrayList.add("OEM_ERROR_3");
            n3 = n2 | 503;
        }
        n2 = n3;
        if ((n & 504) == 504) {
            arrayList.add("OEM_ERROR_4");
            n2 = n3 | 504;
        }
        n3 = n2;
        if ((n & 505) == 505) {
            arrayList.add("OEM_ERROR_5");
            n3 = n2 | 505;
        }
        n2 = n3;
        if ((n & 506) == 506) {
            arrayList.add("OEM_ERROR_6");
            n2 = n3 | 506;
        }
        n3 = n2;
        if ((n & 507) == 507) {
            arrayList.add("OEM_ERROR_7");
            n3 = n2 | 507;
        }
        n2 = n3;
        if ((n & 508) == 508) {
            arrayList.add("OEM_ERROR_8");
            n2 = n3 | 508;
        }
        n3 = n2;
        if ((n & 509) == 509) {
            arrayList.add("OEM_ERROR_9");
            n3 = n2 | 509;
        }
        n2 = n3;
        if ((n & 510) == 510) {
            arrayList.add("OEM_ERROR_10");
            n2 = n3 | 510;
        }
        n3 = n2;
        if ((n & 511) == 511) {
            arrayList.add("OEM_ERROR_11");
            n3 = n2 | 511;
        }
        n2 = n3;
        if ((n & 512) == 512) {
            arrayList.add("OEM_ERROR_12");
            n2 = n3 | 512;
        }
        n3 = n2;
        if ((n & 513) == 513) {
            arrayList.add("OEM_ERROR_13");
            n3 = n2 | 513;
        }
        n2 = n3;
        if ((n & 514) == 514) {
            arrayList.add("OEM_ERROR_14");
            n2 = n3 | 514;
        }
        n3 = n2;
        if ((n & 515) == 515) {
            arrayList.add("OEM_ERROR_15");
            n3 = n2 | 515;
        }
        n2 = n3;
        if ((n & 516) == 516) {
            arrayList.add("OEM_ERROR_16");
            n2 = n3 | 516;
        }
        n3 = n2;
        if ((n & 517) == 517) {
            arrayList.add("OEM_ERROR_17");
            n3 = n2 | 517;
        }
        n2 = n3;
        if ((n & 518) == 518) {
            arrayList.add("OEM_ERROR_18");
            n2 = n3 | 518;
        }
        n3 = n2;
        if ((n & 519) == 519) {
            arrayList.add("OEM_ERROR_19");
            n3 = n2 | 519;
        }
        n4 = n3;
        if ((n & 520) == 520) {
            arrayList.add("OEM_ERROR_20");
            n4 = n3 | 520;
        }
        n2 = n4;
        if ((n & 521) == 521) {
            arrayList.add("OEM_ERROR_21");
            n2 = n4 | 521;
        }
        n3 = n2;
        if ((n & 522) == 522) {
            arrayList.add("OEM_ERROR_22");
            n3 = n2 | 522;
        }
        n2 = n3;
        if ((n & 523) == 523) {
            arrayList.add("OEM_ERROR_23");
            n2 = n3 | 523;
        }
        n3 = n2;
        if ((n & 524) == 524) {
            arrayList.add("OEM_ERROR_24");
            n3 = n2 | 524;
        }
        n2 = n3;
        if ((n & 525) == 525) {
            arrayList.add("OEM_ERROR_25");
            n2 = n3 | 525;
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
            return "NONE";
        }
        if (n == 1) {
            return "RADIO_NOT_AVAILABLE";
        }
        if (n == 2) {
            return "GENERIC_FAILURE";
        }
        if (n == 3) {
            return "PASSWORD_INCORRECT";
        }
        if (n == 4) {
            return "SIM_PIN2";
        }
        if (n == 5) {
            return "SIM_PUK2";
        }
        if (n == 6) {
            return "REQUEST_NOT_SUPPORTED";
        }
        if (n == 7) {
            return "CANCELLED";
        }
        if (n == 8) {
            return "OP_NOT_ALLOWED_DURING_VOICE_CALL";
        }
        if (n == 9) {
            return "OP_NOT_ALLOWED_BEFORE_REG_TO_NW";
        }
        if (n == 10) {
            return "SMS_SEND_FAIL_RETRY";
        }
        if (n == 11) {
            return "SIM_ABSENT";
        }
        if (n == 12) {
            return "SUBSCRIPTION_NOT_AVAILABLE";
        }
        if (n == 13) {
            return "MODE_NOT_SUPPORTED";
        }
        if (n == 14) {
            return "FDN_CHECK_FAILURE";
        }
        if (n == 15) {
            return "ILLEGAL_SIM_OR_ME";
        }
        if (n == 16) {
            return "MISSING_RESOURCE";
        }
        if (n == 17) {
            return "NO_SUCH_ELEMENT";
        }
        if (n == 18) {
            return "DIAL_MODIFIED_TO_USSD";
        }
        if (n == 19) {
            return "DIAL_MODIFIED_TO_SS";
        }
        if (n == 20) {
            return "DIAL_MODIFIED_TO_DIAL";
        }
        if (n == 21) {
            return "USSD_MODIFIED_TO_DIAL";
        }
        if (n == 22) {
            return "USSD_MODIFIED_TO_SS";
        }
        if (n == 23) {
            return "USSD_MODIFIED_TO_USSD";
        }
        if (n == 24) {
            return "SS_MODIFIED_TO_DIAL";
        }
        if (n == 25) {
            return "SS_MODIFIED_TO_USSD";
        }
        if (n == 26) {
            return "SUBSCRIPTION_NOT_SUPPORTED";
        }
        if (n == 27) {
            return "SS_MODIFIED_TO_SS";
        }
        if (n == 36) {
            return "LCE_NOT_SUPPORTED";
        }
        if (n == 37) {
            return "NO_MEMORY";
        }
        if (n == 38) {
            return "INTERNAL_ERR";
        }
        if (n == 39) {
            return "SYSTEM_ERR";
        }
        if (n == 40) {
            return "MODEM_ERR";
        }
        if (n == 41) {
            return "INVALID_STATE";
        }
        if (n == 42) {
            return "NO_RESOURCES";
        }
        if (n == 43) {
            return "SIM_ERR";
        }
        if (n == 44) {
            return "INVALID_ARGUMENTS";
        }
        if (n == 45) {
            return "INVALID_SIM_STATE";
        }
        if (n == 46) {
            return "INVALID_MODEM_STATE";
        }
        if (n == 47) {
            return "INVALID_CALL_ID";
        }
        if (n == 48) {
            return "NO_SMS_TO_ACK";
        }
        if (n == 49) {
            return "NETWORK_ERR";
        }
        if (n == 50) {
            return "REQUEST_RATE_LIMITED";
        }
        if (n == 51) {
            return "SIM_BUSY";
        }
        if (n == 52) {
            return "SIM_FULL";
        }
        if (n == 53) {
            return "NETWORK_REJECT";
        }
        if (n == 54) {
            return "OPERATION_NOT_ALLOWED";
        }
        if (n == 55) {
            return "EMPTY_RECORD";
        }
        if (n == 56) {
            return "INVALID_SMS_FORMAT";
        }
        if (n == 57) {
            return "ENCODING_ERR";
        }
        if (n == 58) {
            return "INVALID_SMSC_ADDRESS";
        }
        if (n == 59) {
            return "NO_SUCH_ENTRY";
        }
        if (n == 60) {
            return "NETWORK_NOT_READY";
        }
        if (n == 61) {
            return "NOT_PROVISIONED";
        }
        if (n == 62) {
            return "NO_SUBSCRIPTION";
        }
        if (n == 63) {
            return "NO_NETWORK_FOUND";
        }
        if (n == 64) {
            return "DEVICE_IN_USE";
        }
        if (n == 65) {
            return "ABORTED";
        }
        if (n == 66) {
            return "INVALID_RESPONSE";
        }
        if (n == 501) {
            return "OEM_ERROR_1";
        }
        if (n == 502) {
            return "OEM_ERROR_2";
        }
        if (n == 503) {
            return "OEM_ERROR_3";
        }
        if (n == 504) {
            return "OEM_ERROR_4";
        }
        if (n == 505) {
            return "OEM_ERROR_5";
        }
        if (n == 506) {
            return "OEM_ERROR_6";
        }
        if (n == 507) {
            return "OEM_ERROR_7";
        }
        if (n == 508) {
            return "OEM_ERROR_8";
        }
        if (n == 509) {
            return "OEM_ERROR_9";
        }
        if (n == 510) {
            return "OEM_ERROR_10";
        }
        if (n == 511) {
            return "OEM_ERROR_11";
        }
        if (n == 512) {
            return "OEM_ERROR_12";
        }
        if (n == 513) {
            return "OEM_ERROR_13";
        }
        if (n == 514) {
            return "OEM_ERROR_14";
        }
        if (n == 515) {
            return "OEM_ERROR_15";
        }
        if (n == 516) {
            return "OEM_ERROR_16";
        }
        if (n == 517) {
            return "OEM_ERROR_17";
        }
        if (n == 518) {
            return "OEM_ERROR_18";
        }
        if (n == 519) {
            return "OEM_ERROR_19";
        }
        if (n == 520) {
            return "OEM_ERROR_20";
        }
        if (n == 521) {
            return "OEM_ERROR_21";
        }
        if (n == 522) {
            return "OEM_ERROR_22";
        }
        if (n == 523) {
            return "OEM_ERROR_23";
        }
        if (n == 524) {
            return "OEM_ERROR_24";
        }
        if (n == 525) {
            return "OEM_ERROR_25";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

