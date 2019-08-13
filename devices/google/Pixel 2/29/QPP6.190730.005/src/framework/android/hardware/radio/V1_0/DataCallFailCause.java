/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class DataCallFailCause {
    public static final int ACTIVATION_REJECT_GGSN = 30;
    public static final int ACTIVATION_REJECT_UNSPECIFIED = 31;
    public static final int APN_TYPE_CONFLICT = 112;
    public static final int AUTH_FAILURE_ON_EMERGENCY_CALL = 122;
    public static final int COMPANION_IFACE_IN_USE = 118;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int DATA_REGISTRATION_FAIL = -2;
    public static final int EMERGENCY_IFACE_ONLY = 116;
    public static final int EMM_ACCESS_BARRED = 115;
    public static final int EMM_ACCESS_BARRED_INFINITE_RETRY = 121;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int ESM_INFO_NOT_RECEIVED = 53;
    public static final int FEATURE_NOT_SUPP = 40;
    public static final int FILTER_SEMANTIC_ERROR = 44;
    public static final int FILTER_SYTAX_ERROR = 45;
    public static final int IFACE_AND_POL_FAMILY_MISMATCH = 120;
    public static final int IFACE_MISMATCH = 117;
    public static final int INSUFFICIENT_RESOURCES = 26;
    public static final int INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN = 114;
    public static final int INVALID_MANDATORY_INFO = 96;
    public static final int INVALID_PCSCF_ADDR = 113;
    public static final int INVALID_TRANSACTION_ID = 81;
    public static final int IP_ADDRESS_MISMATCH = 119;
    public static final int MAX_ACTIVE_PDP_CONTEXT_REACHED = 65;
    public static final int MESSAGE_INCORRECT_SEMANTIC = 95;
    public static final int MESSAGE_TYPE_UNSUPPORTED = 97;
    public static final int MISSING_UKNOWN_APN = 27;
    public static final int MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE = 101;
    public static final int MSG_TYPE_NONCOMPATIBLE_STATE = 98;
    public static final int MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED = 55;
    public static final int NAS_SIGNALLING = 14;
    public static final int NETWORK_FAILURE = 38;
    public static final int NONE = 0;
    public static final int NSAPI_IN_USE = 35;
    public static final int OEM_DCFAILCAUSE_1 = 4097;
    public static final int OEM_DCFAILCAUSE_10 = 4106;
    public static final int OEM_DCFAILCAUSE_11 = 4107;
    public static final int OEM_DCFAILCAUSE_12 = 4108;
    public static final int OEM_DCFAILCAUSE_13 = 4109;
    public static final int OEM_DCFAILCAUSE_14 = 4110;
    public static final int OEM_DCFAILCAUSE_15 = 4111;
    public static final int OEM_DCFAILCAUSE_2 = 4098;
    public static final int OEM_DCFAILCAUSE_3 = 4099;
    public static final int OEM_DCFAILCAUSE_4 = 4100;
    public static final int OEM_DCFAILCAUSE_5 = 4101;
    public static final int OEM_DCFAILCAUSE_6 = 4102;
    public static final int OEM_DCFAILCAUSE_7 = 4103;
    public static final int OEM_DCFAILCAUSE_8 = 4104;
    public static final int OEM_DCFAILCAUSE_9 = 4105;
    public static final int ONLY_IPV4_ALLOWED = 50;
    public static final int ONLY_IPV6_ALLOWED = 51;
    public static final int ONLY_SINGLE_BEARER_ALLOWED = 52;
    public static final int OPERATOR_BARRED = 8;
    public static final int PDN_CONN_DOES_NOT_EXIST = 54;
    public static final int PDP_WITHOUT_ACTIVE_TFT = 46;
    public static final int PREF_RADIO_TECH_CHANGED = -4;
    public static final int PROTOCOL_ERRORS = 111;
    public static final int QOS_NOT_ACCEPTED = 37;
    public static final int RADIO_POWER_OFF = -5;
    public static final int REGULAR_DEACTIVATION = 36;
    public static final int SERVICE_OPTION_NOT_SUBSCRIBED = 33;
    public static final int SERVICE_OPTION_NOT_SUPPORTED = 32;
    public static final int SERVICE_OPTION_OUT_OF_ORDER = 34;
    public static final int SIGNAL_LOST = -3;
    public static final int TETHERED_CALL_ACTIVE = -6;
    public static final int TFT_SEMANTIC_ERROR = 41;
    public static final int TFT_SYTAX_ERROR = 42;
    public static final int UMTS_REACTIVATION_REQ = 39;
    public static final int UNKNOWN_INFO_ELEMENT = 99;
    public static final int UNKNOWN_PDP_ADDRESS_TYPE = 28;
    public static final int UNKNOWN_PDP_CONTEXT = 43;
    public static final int UNSUPPORTED_APN_IN_CURRENT_PLMN = 66;
    public static final int USER_AUTHENTICATION = 29;
    public static final int VOICE_REGISTRATION_FAIL = -1;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("NONE");
        if ((n & 8) == 8) {
            arrayList.add("OPERATOR_BARRED");
            n2 = 0 | 8;
        }
        int n3 = n2;
        if ((n & 14) == 14) {
            arrayList.add("NAS_SIGNALLING");
            n3 = n2 | 14;
        }
        n2 = n3;
        if ((n & 26) == 26) {
            arrayList.add("INSUFFICIENT_RESOURCES");
            n2 = n3 | 26;
        }
        n3 = n2;
        if ((n & 27) == 27) {
            arrayList.add("MISSING_UKNOWN_APN");
            n3 = n2 | 27;
        }
        int n4 = n3;
        if ((n & 28) == 28) {
            arrayList.add("UNKNOWN_PDP_ADDRESS_TYPE");
            n4 = n3 | 28;
        }
        n2 = n4;
        if ((n & 29) == 29) {
            arrayList.add("USER_AUTHENTICATION");
            n2 = n4 | 29;
        }
        n3 = n2;
        if ((n & 30) == 30) {
            arrayList.add("ACTIVATION_REJECT_GGSN");
            n3 = n2 | 30;
        }
        n2 = n3;
        if ((n & 31) == 31) {
            arrayList.add("ACTIVATION_REJECT_UNSPECIFIED");
            n2 = n3 | 31;
        }
        n3 = n2;
        if ((n & 32) == 32) {
            arrayList.add("SERVICE_OPTION_NOT_SUPPORTED");
            n3 = n2 | 32;
        }
        n2 = n3;
        if ((n & 33) == 33) {
            arrayList.add("SERVICE_OPTION_NOT_SUBSCRIBED");
            n2 = n3 | 33;
        }
        n4 = n2;
        if ((n & 34) == 34) {
            arrayList.add("SERVICE_OPTION_OUT_OF_ORDER");
            n4 = n2 | 34;
        }
        n3 = n4;
        if ((n & 35) == 35) {
            arrayList.add("NSAPI_IN_USE");
            n3 = n4 | 35;
        }
        n2 = n3;
        if ((n & 36) == 36) {
            arrayList.add("REGULAR_DEACTIVATION");
            n2 = n3 | 36;
        }
        n3 = n2;
        if ((n & 37) == 37) {
            arrayList.add("QOS_NOT_ACCEPTED");
            n3 = n2 | 37;
        }
        n2 = n3;
        if ((n & 38) == 38) {
            arrayList.add("NETWORK_FAILURE");
            n2 = n3 | 38;
        }
        n3 = n2;
        if ((n & 39) == 39) {
            arrayList.add("UMTS_REACTIVATION_REQ");
            n3 = n2 | 39;
        }
        n4 = n3;
        if ((n & 40) == 40) {
            arrayList.add("FEATURE_NOT_SUPP");
            n4 = n3 | 40;
        }
        n2 = n4;
        if ((n & 41) == 41) {
            arrayList.add("TFT_SEMANTIC_ERROR");
            n2 = n4 | 41;
        }
        n3 = n2;
        if ((n & 42) == 42) {
            arrayList.add("TFT_SYTAX_ERROR");
            n3 = n2 | 42;
        }
        n2 = n3;
        if ((n & 43) == 43) {
            arrayList.add("UNKNOWN_PDP_CONTEXT");
            n2 = n3 | 43;
        }
        n3 = n2;
        if ((n & 44) == 44) {
            arrayList.add("FILTER_SEMANTIC_ERROR");
            n3 = n2 | 44;
        }
        n2 = n3;
        if ((n & 45) == 45) {
            arrayList.add("FILTER_SYTAX_ERROR");
            n2 = n3 | 45;
        }
        n3 = n2;
        if ((n & 46) == 46) {
            arrayList.add("PDP_WITHOUT_ACTIVE_TFT");
            n3 = n2 | 46;
        }
        n2 = n3;
        if ((n & 50) == 50) {
            arrayList.add("ONLY_IPV4_ALLOWED");
            n2 = n3 | 50;
        }
        n3 = n2;
        if ((n & 51) == 51) {
            arrayList.add("ONLY_IPV6_ALLOWED");
            n3 = n2 | 51;
        }
        n2 = n3;
        if ((n & 52) == 52) {
            arrayList.add("ONLY_SINGLE_BEARER_ALLOWED");
            n2 = n3 | 52;
        }
        n3 = n2;
        if ((n & 53) == 53) {
            arrayList.add("ESM_INFO_NOT_RECEIVED");
            n3 = n2 | 53;
        }
        n4 = n3;
        if ((n & 54) == 54) {
            arrayList.add("PDN_CONN_DOES_NOT_EXIST");
            n4 = n3 | 54;
        }
        n2 = n4;
        if ((n & 55) == 55) {
            arrayList.add("MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED");
            n2 = n4 | 55;
        }
        n3 = n2;
        if ((n & 65) == 65) {
            arrayList.add("MAX_ACTIVE_PDP_CONTEXT_REACHED");
            n3 = n2 | 65;
        }
        n2 = n3;
        if ((n & 66) == 66) {
            arrayList.add("UNSUPPORTED_APN_IN_CURRENT_PLMN");
            n2 = n3 | 66;
        }
        n3 = n2;
        if ((n & 81) == 81) {
            arrayList.add("INVALID_TRANSACTION_ID");
            n3 = n2 | 81;
        }
        n2 = n3;
        if ((n & 95) == 95) {
            arrayList.add("MESSAGE_INCORRECT_SEMANTIC");
            n2 = n3 | 95;
        }
        n3 = n2;
        if ((n & 96) == 96) {
            arrayList.add("INVALID_MANDATORY_INFO");
            n3 = n2 | 96;
        }
        n4 = n3;
        if ((n & 97) == 97) {
            arrayList.add("MESSAGE_TYPE_UNSUPPORTED");
            n4 = n3 | 97;
        }
        n2 = n4;
        if ((n & 98) == 98) {
            arrayList.add("MSG_TYPE_NONCOMPATIBLE_STATE");
            n2 = n4 | 98;
        }
        n3 = n2;
        if ((n & 99) == 99) {
            arrayList.add("UNKNOWN_INFO_ELEMENT");
            n3 = n2 | 99;
        }
        n2 = n3;
        if ((n & 100) == 100) {
            arrayList.add("CONDITIONAL_IE_ERROR");
            n2 = n3 | 100;
        }
        n3 = n2;
        if ((n & 101) == 101) {
            arrayList.add("MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE");
            n3 = n2 | 101;
        }
        n2 = n3;
        if ((n & 111) == 111) {
            arrayList.add("PROTOCOL_ERRORS");
            n2 = n3 | 111;
        }
        n4 = n2;
        if ((n & 112) == 112) {
            arrayList.add("APN_TYPE_CONFLICT");
            n4 = n2 | 112;
        }
        n3 = n4;
        if ((n & 113) == 113) {
            arrayList.add("INVALID_PCSCF_ADDR");
            n3 = n4 | 113;
        }
        n2 = n3;
        if ((n & 114) == 114) {
            arrayList.add("INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN");
            n2 = n3 | 114;
        }
        n3 = n2;
        if ((n & 115) == 115) {
            arrayList.add("EMM_ACCESS_BARRED");
            n3 = n2 | 115;
        }
        n2 = n3;
        if ((n & 116) == 116) {
            arrayList.add("EMERGENCY_IFACE_ONLY");
            n2 = n3 | 116;
        }
        n3 = n2;
        if ((n & 117) == 117) {
            arrayList.add("IFACE_MISMATCH");
            n3 = n2 | 117;
        }
        n2 = n3;
        if ((n & 118) == 118) {
            arrayList.add("COMPANION_IFACE_IN_USE");
            n2 = n3 | 118;
        }
        n3 = n2;
        if ((n & 119) == 119) {
            arrayList.add("IP_ADDRESS_MISMATCH");
            n3 = n2 | 119;
        }
        n2 = n3;
        if ((n & 120) == 120) {
            arrayList.add("IFACE_AND_POL_FAMILY_MISMATCH");
            n2 = n3 | 120;
        }
        n4 = n2;
        if ((n & 121) == 121) {
            arrayList.add("EMM_ACCESS_BARRED_INFINITE_RETRY");
            n4 = n2 | 121;
        }
        n3 = n4;
        if ((n & 122) == 122) {
            arrayList.add("AUTH_FAILURE_ON_EMERGENCY_CALL");
            n3 = n4 | 122;
        }
        n2 = n3;
        if ((n & 4097) == 4097) {
            arrayList.add("OEM_DCFAILCAUSE_1");
            n2 = n3 | 4097;
        }
        n3 = n2;
        if ((n & 4098) == 4098) {
            arrayList.add("OEM_DCFAILCAUSE_2");
            n3 = n2 | 4098;
        }
        n2 = n3;
        if ((n & 4099) == 4099) {
            arrayList.add("OEM_DCFAILCAUSE_3");
            n2 = n3 | 4099;
        }
        n3 = n2;
        if ((n & 4100) == 4100) {
            arrayList.add("OEM_DCFAILCAUSE_4");
            n3 = n2 | 4100;
        }
        n2 = n3;
        if ((n & 4101) == 4101) {
            arrayList.add("OEM_DCFAILCAUSE_5");
            n2 = n3 | 4101;
        }
        n4 = n2;
        if ((n & 4102) == 4102) {
            arrayList.add("OEM_DCFAILCAUSE_6");
            n4 = n2 | 4102;
        }
        n3 = n4;
        if ((n & 4103) == 4103) {
            arrayList.add("OEM_DCFAILCAUSE_7");
            n3 = n4 | 4103;
        }
        n2 = n3;
        if ((n & 4104) == 4104) {
            arrayList.add("OEM_DCFAILCAUSE_8");
            n2 = n3 | 4104;
        }
        n3 = n2;
        if ((n & 4105) == 4105) {
            arrayList.add("OEM_DCFAILCAUSE_9");
            n3 = n2 | 4105;
        }
        n2 = n3;
        if ((n & 4106) == 4106) {
            arrayList.add("OEM_DCFAILCAUSE_10");
            n2 = n3 | 4106;
        }
        n3 = n2;
        if ((n & 4107) == 4107) {
            arrayList.add("OEM_DCFAILCAUSE_11");
            n3 = n2 | 4107;
        }
        n4 = n3;
        if ((n & 4108) == 4108) {
            arrayList.add("OEM_DCFAILCAUSE_12");
            n4 = n3 | 4108;
        }
        n2 = n4;
        if ((n & 4109) == 4109) {
            arrayList.add("OEM_DCFAILCAUSE_13");
            n2 = n4 | 4109;
        }
        n3 = n2;
        if ((n & 4110) == 4110) {
            arrayList.add("OEM_DCFAILCAUSE_14");
            n3 = n2 | 4110;
        }
        n2 = n3;
        if ((n & 4111) == 4111) {
            arrayList.add("OEM_DCFAILCAUSE_15");
            n2 = n3 | 4111;
        }
        n4 = n2;
        if ((n & -1) == -1) {
            arrayList.add("VOICE_REGISTRATION_FAIL");
            n4 = n2 | -1;
        }
        n3 = n4;
        if ((n & -2) == -2) {
            arrayList.add("DATA_REGISTRATION_FAIL");
            n3 = n4 | -2;
        }
        n2 = n3;
        if ((n & -3) == -3) {
            arrayList.add("SIGNAL_LOST");
            n2 = n3 | -3;
        }
        n3 = n2;
        if ((n & -4) == -4) {
            arrayList.add("PREF_RADIO_TECH_CHANGED");
            n3 = n2 | -4;
        }
        n2 = n3;
        if ((n & -5) == -5) {
            arrayList.add("RADIO_POWER_OFF");
            n2 = n3 | -5;
        }
        n3 = n2;
        if ((n & -6) == -6) {
            arrayList.add("TETHERED_CALL_ACTIVE");
            n3 = n2 | -6;
        }
        n2 = n3;
        if ((65535 & n) == 65535) {
            arrayList.add("ERROR_UNSPECIFIED");
            n2 = n3 | 65535;
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
        if (n == 8) {
            return "OPERATOR_BARRED";
        }
        if (n == 14) {
            return "NAS_SIGNALLING";
        }
        if (n == 26) {
            return "INSUFFICIENT_RESOURCES";
        }
        if (n == 27) {
            return "MISSING_UKNOWN_APN";
        }
        if (n == 28) {
            return "UNKNOWN_PDP_ADDRESS_TYPE";
        }
        if (n == 29) {
            return "USER_AUTHENTICATION";
        }
        if (n == 30) {
            return "ACTIVATION_REJECT_GGSN";
        }
        if (n == 31) {
            return "ACTIVATION_REJECT_UNSPECIFIED";
        }
        if (n == 32) {
            return "SERVICE_OPTION_NOT_SUPPORTED";
        }
        if (n == 33) {
            return "SERVICE_OPTION_NOT_SUBSCRIBED";
        }
        if (n == 34) {
            return "SERVICE_OPTION_OUT_OF_ORDER";
        }
        if (n == 35) {
            return "NSAPI_IN_USE";
        }
        if (n == 36) {
            return "REGULAR_DEACTIVATION";
        }
        if (n == 37) {
            return "QOS_NOT_ACCEPTED";
        }
        if (n == 38) {
            return "NETWORK_FAILURE";
        }
        if (n == 39) {
            return "UMTS_REACTIVATION_REQ";
        }
        if (n == 40) {
            return "FEATURE_NOT_SUPP";
        }
        if (n == 41) {
            return "TFT_SEMANTIC_ERROR";
        }
        if (n == 42) {
            return "TFT_SYTAX_ERROR";
        }
        if (n == 43) {
            return "UNKNOWN_PDP_CONTEXT";
        }
        if (n == 44) {
            return "FILTER_SEMANTIC_ERROR";
        }
        if (n == 45) {
            return "FILTER_SYTAX_ERROR";
        }
        if (n == 46) {
            return "PDP_WITHOUT_ACTIVE_TFT";
        }
        if (n == 50) {
            return "ONLY_IPV4_ALLOWED";
        }
        if (n == 51) {
            return "ONLY_IPV6_ALLOWED";
        }
        if (n == 52) {
            return "ONLY_SINGLE_BEARER_ALLOWED";
        }
        if (n == 53) {
            return "ESM_INFO_NOT_RECEIVED";
        }
        if (n == 54) {
            return "PDN_CONN_DOES_NOT_EXIST";
        }
        if (n == 55) {
            return "MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED";
        }
        if (n == 65) {
            return "MAX_ACTIVE_PDP_CONTEXT_REACHED";
        }
        if (n == 66) {
            return "UNSUPPORTED_APN_IN_CURRENT_PLMN";
        }
        if (n == 81) {
            return "INVALID_TRANSACTION_ID";
        }
        if (n == 95) {
            return "MESSAGE_INCORRECT_SEMANTIC";
        }
        if (n == 96) {
            return "INVALID_MANDATORY_INFO";
        }
        if (n == 97) {
            return "MESSAGE_TYPE_UNSUPPORTED";
        }
        if (n == 98) {
            return "MSG_TYPE_NONCOMPATIBLE_STATE";
        }
        if (n == 99) {
            return "UNKNOWN_INFO_ELEMENT";
        }
        if (n == 100) {
            return "CONDITIONAL_IE_ERROR";
        }
        if (n == 101) {
            return "MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE";
        }
        if (n == 111) {
            return "PROTOCOL_ERRORS";
        }
        if (n == 112) {
            return "APN_TYPE_CONFLICT";
        }
        if (n == 113) {
            return "INVALID_PCSCF_ADDR";
        }
        if (n == 114) {
            return "INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN";
        }
        if (n == 115) {
            return "EMM_ACCESS_BARRED";
        }
        if (n == 116) {
            return "EMERGENCY_IFACE_ONLY";
        }
        if (n == 117) {
            return "IFACE_MISMATCH";
        }
        if (n == 118) {
            return "COMPANION_IFACE_IN_USE";
        }
        if (n == 119) {
            return "IP_ADDRESS_MISMATCH";
        }
        if (n == 120) {
            return "IFACE_AND_POL_FAMILY_MISMATCH";
        }
        if (n == 121) {
            return "EMM_ACCESS_BARRED_INFINITE_RETRY";
        }
        if (n == 122) {
            return "AUTH_FAILURE_ON_EMERGENCY_CALL";
        }
        if (n == 4097) {
            return "OEM_DCFAILCAUSE_1";
        }
        if (n == 4098) {
            return "OEM_DCFAILCAUSE_2";
        }
        if (n == 4099) {
            return "OEM_DCFAILCAUSE_3";
        }
        if (n == 4100) {
            return "OEM_DCFAILCAUSE_4";
        }
        if (n == 4101) {
            return "OEM_DCFAILCAUSE_5";
        }
        if (n == 4102) {
            return "OEM_DCFAILCAUSE_6";
        }
        if (n == 4103) {
            return "OEM_DCFAILCAUSE_7";
        }
        if (n == 4104) {
            return "OEM_DCFAILCAUSE_8";
        }
        if (n == 4105) {
            return "OEM_DCFAILCAUSE_9";
        }
        if (n == 4106) {
            return "OEM_DCFAILCAUSE_10";
        }
        if (n == 4107) {
            return "OEM_DCFAILCAUSE_11";
        }
        if (n == 4108) {
            return "OEM_DCFAILCAUSE_12";
        }
        if (n == 4109) {
            return "OEM_DCFAILCAUSE_13";
        }
        if (n == 4110) {
            return "OEM_DCFAILCAUSE_14";
        }
        if (n == 4111) {
            return "OEM_DCFAILCAUSE_15";
        }
        if (n == -1) {
            return "VOICE_REGISTRATION_FAIL";
        }
        if (n == -2) {
            return "DATA_REGISTRATION_FAIL";
        }
        if (n == -3) {
            return "SIGNAL_LOST";
        }
        if (n == -4) {
            return "PREF_RADIO_TECH_CHANGED";
        }
        if (n == -5) {
            return "RADIO_POWER_OFF";
        }
        if (n == -6) {
            return "TETHERED_CALL_ACTIVE";
        }
        if (n == 65535) {
            return "ERROR_UNSPECIFIED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

