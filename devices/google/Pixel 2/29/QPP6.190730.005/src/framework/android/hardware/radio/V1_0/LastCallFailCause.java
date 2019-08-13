/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class LastCallFailCause {
    public static final int ACCESS_CLASS_BLOCKED = 260;
    public static final int ACCESS_INFORMATION_DISCARDED = 43;
    public static final int ACM_LIMIT_EXCEEDED = 68;
    public static final int BEARER_CAPABILITY_NOT_AUTHORIZED = 57;
    public static final int BEARER_CAPABILITY_UNAVAILABLE = 58;
    public static final int BEARER_SERVICE_NOT_IMPLEMENTED = 65;
    public static final int BUSY = 17;
    public static final int CALL_BARRED = 240;
    public static final int CALL_REJECTED = 21;
    public static final int CDMA_ACCESS_BLOCKED = 1009;
    public static final int CDMA_ACCESS_FAILURE = 1006;
    public static final int CDMA_DROP = 1001;
    public static final int CDMA_INTERCEPT = 1002;
    public static final int CDMA_LOCKED_UNTIL_POWER_CYCLE = 1000;
    public static final int CDMA_NOT_EMERGENCY = 1008;
    public static final int CDMA_PREEMPTED = 1007;
    public static final int CDMA_REORDER = 1003;
    public static final int CDMA_RETRY_ORDER = 1005;
    public static final int CDMA_SO_REJECT = 1004;
    public static final int CHANNEL_UNACCEPTABLE = 6;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int CONGESTION = 34;
    public static final int DESTINATION_OUT_OF_ORDER = 27;
    public static final int DIAL_MODIFIED_TO_DIAL = 246;
    public static final int DIAL_MODIFIED_TO_SS = 245;
    public static final int DIAL_MODIFIED_TO_USSD = 244;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int FACILITY_REJECTED = 29;
    public static final int FDN_BLOCKED = 241;
    public static final int IMEI_NOT_ACCEPTED = 243;
    public static final int IMSI_UNKNOWN_IN_VLR = 242;
    public static final int INCOMING_CALLS_BARRED_WITHIN_CUG = 55;
    public static final int INCOMPATIBLE_DESTINATION = 88;
    public static final int INFORMATION_ELEMENT_NON_EXISTENT = 99;
    public static final int INTERWORKING_UNSPECIFIED = 127;
    public static final int INVALID_MANDATORY_INFORMATION = 96;
    public static final int INVALID_NUMBER_FORMAT = 28;
    public static final int INVALID_TRANSACTION_IDENTIFIER = 81;
    public static final int INVALID_TRANSIT_NW_SELECTION = 91;
    public static final int MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE = 101;
    public static final int MESSAGE_TYPE_NON_IMPLEMENTED = 97;
    public static final int MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE = 98;
    public static final int NETWORK_DETACH = 261;
    public static final int NETWORK_OUT_OF_ORDER = 38;
    public static final int NETWORK_REJECT = 252;
    public static final int NETWORK_RESP_TIMEOUT = 251;
    public static final int NORMAL = 16;
    public static final int NORMAL_UNSPECIFIED = 31;
    public static final int NO_ANSWER_FROM_USER = 19;
    public static final int NO_ROUTE_TO_DESTINATION = 3;
    public static final int NO_USER_RESPONDING = 18;
    public static final int NO_VALID_SIM = 249;
    public static final int NUMBER_CHANGED = 22;
    public static final int OEM_CAUSE_1 = 61441;
    public static final int OEM_CAUSE_10 = 61450;
    public static final int OEM_CAUSE_11 = 61451;
    public static final int OEM_CAUSE_12 = 61452;
    public static final int OEM_CAUSE_13 = 61453;
    public static final int OEM_CAUSE_14 = 61454;
    public static final int OEM_CAUSE_15 = 61455;
    public static final int OEM_CAUSE_2 = 61442;
    public static final int OEM_CAUSE_3 = 61443;
    public static final int OEM_CAUSE_4 = 61444;
    public static final int OEM_CAUSE_5 = 61445;
    public static final int OEM_CAUSE_6 = 61446;
    public static final int OEM_CAUSE_7 = 61447;
    public static final int OEM_CAUSE_8 = 61448;
    public static final int OEM_CAUSE_9 = 61449;
    public static final int ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE = 70;
    public static final int OPERATOR_DETERMINED_BARRING = 8;
    public static final int OUT_OF_SERVICE = 248;
    public static final int PREEMPTION = 25;
    public static final int PROTOCOL_ERROR_UNSPECIFIED = 111;
    public static final int QOS_UNAVAILABLE = 49;
    public static final int RADIO_ACCESS_FAILURE = 253;
    public static final int RADIO_INTERNAL_ERROR = 250;
    public static final int RADIO_LINK_FAILURE = 254;
    public static final int RADIO_LINK_LOST = 255;
    public static final int RADIO_OFF = 247;
    public static final int RADIO_RELEASE_ABNORMAL = 259;
    public static final int RADIO_RELEASE_NORMAL = 258;
    public static final int RADIO_SETUP_FAILURE = 257;
    public static final int RADIO_UPLINK_FAILURE = 256;
    public static final int RECOVERY_ON_TIMER_EXPIRED = 102;
    public static final int REQUESTED_CIRCUIT_OR_CHANNEL_NOT_AVAILABLE = 44;
    public static final int REQUESTED_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int REQUESTED_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int RESOURCES_UNAVAILABLE_OR_UNSPECIFIED = 47;
    public static final int RESP_TO_STATUS_ENQUIRY = 30;
    public static final int SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SERVICE_OPTION_NOT_AVAILABLE = 63;
    public static final int SERVICE_OR_OPTION_NOT_IMPLEMENTED = 79;
    public static final int SWITCHING_EQUIPMENT_CONGESTION = 42;
    public static final int TEMPORARY_FAILURE = 41;
    public static final int UNOBTAINABLE_NUMBER = 1;
    public static final int USER_NOT_MEMBER_OF_CUG = 87;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        if ((n & 1) == 1) {
            arrayList.add("UNOBTAINABLE_NUMBER");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 3) == 3) {
            arrayList.add("NO_ROUTE_TO_DESTINATION");
            n3 = n2 | 3;
        }
        n2 = n3;
        if ((n & 6) == 6) {
            arrayList.add("CHANNEL_UNACCEPTABLE");
            n2 = n3 | 6;
        }
        n3 = n2;
        if ((n & 8) == 8) {
            arrayList.add("OPERATOR_DETERMINED_BARRING");
            n3 = n2 | 8;
        }
        n2 = n3;
        if ((n & 16) == 16) {
            arrayList.add("NORMAL");
            n2 = n3 | 16;
        }
        n3 = n2;
        if ((n & 17) == 17) {
            arrayList.add("BUSY");
            n3 = n2 | 17;
        }
        n2 = n3;
        if ((n & 18) == 18) {
            arrayList.add("NO_USER_RESPONDING");
            n2 = n3 | 18;
        }
        n3 = n2;
        if ((n & 19) == 19) {
            arrayList.add("NO_ANSWER_FROM_USER");
            n3 = n2 | 19;
        }
        n2 = n3;
        if ((n & 21) == 21) {
            arrayList.add("CALL_REJECTED");
            n2 = n3 | 21;
        }
        n3 = n2;
        if ((n & 22) == 22) {
            arrayList.add("NUMBER_CHANGED");
            n3 = n2 | 22;
        }
        n2 = n3;
        if ((n & 25) == 25) {
            arrayList.add("PREEMPTION");
            n2 = n3 | 25;
        }
        n3 = n2;
        if ((n & 27) == 27) {
            arrayList.add("DESTINATION_OUT_OF_ORDER");
            n3 = n2 | 27;
        }
        n2 = n3;
        if ((n & 28) == 28) {
            arrayList.add("INVALID_NUMBER_FORMAT");
            n2 = n3 | 28;
        }
        n3 = n2;
        if ((n & 29) == 29) {
            arrayList.add("FACILITY_REJECTED");
            n3 = n2 | 29;
        }
        n2 = n3;
        if ((n & 30) == 30) {
            arrayList.add("RESP_TO_STATUS_ENQUIRY");
            n2 = n3 | 30;
        }
        n3 = n2;
        if ((n & 31) == 31) {
            arrayList.add("NORMAL_UNSPECIFIED");
            n3 = n2 | 31;
        }
        n2 = n3;
        if ((n & 34) == 34) {
            arrayList.add("CONGESTION");
            n2 = n3 | 34;
        }
        n3 = n2;
        if ((n & 38) == 38) {
            arrayList.add("NETWORK_OUT_OF_ORDER");
            n3 = n2 | 38;
        }
        n2 = n3;
        if ((n & 41) == 41) {
            arrayList.add("TEMPORARY_FAILURE");
            n2 = n3 | 41;
        }
        n3 = n2;
        if ((n & 42) == 42) {
            arrayList.add("SWITCHING_EQUIPMENT_CONGESTION");
            n3 = n2 | 42;
        }
        n2 = n3;
        if ((n & 43) == 43) {
            arrayList.add("ACCESS_INFORMATION_DISCARDED");
            n2 = n3 | 43;
        }
        n3 = n2;
        if ((n & 44) == 44) {
            arrayList.add("REQUESTED_CIRCUIT_OR_CHANNEL_NOT_AVAILABLE");
            n3 = n2 | 44;
        }
        n2 = n3;
        if ((n & 47) == 47) {
            arrayList.add("RESOURCES_UNAVAILABLE_OR_UNSPECIFIED");
            n2 = n3 | 47;
        }
        n3 = n2;
        if ((n & 49) == 49) {
            arrayList.add("QOS_UNAVAILABLE");
            n3 = n2 | 49;
        }
        int n4 = n3;
        if ((n & 50) == 50) {
            arrayList.add("REQUESTED_FACILITY_NOT_SUBSCRIBED");
            n4 = n3 | 50;
        }
        n2 = n4;
        if ((n & 55) == 55) {
            arrayList.add("INCOMING_CALLS_BARRED_WITHIN_CUG");
            n2 = n4 | 55;
        }
        n3 = n2;
        if ((n & 57) == 57) {
            arrayList.add("BEARER_CAPABILITY_NOT_AUTHORIZED");
            n3 = n2 | 57;
        }
        n2 = n3;
        if ((n & 58) == 58) {
            arrayList.add("BEARER_CAPABILITY_UNAVAILABLE");
            n2 = n3 | 58;
        }
        n3 = n2;
        if ((n & 63) == 63) {
            arrayList.add("SERVICE_OPTION_NOT_AVAILABLE");
            n3 = n2 | 63;
        }
        n2 = n3;
        if ((n & 65) == 65) {
            arrayList.add("BEARER_SERVICE_NOT_IMPLEMENTED");
            n2 = n3 | 65;
        }
        n4 = n2;
        if ((n & 68) == 68) {
            arrayList.add("ACM_LIMIT_EXCEEDED");
            n4 = n2 | 68;
        }
        n3 = n4;
        if ((n & 69) == 69) {
            arrayList.add("REQUESTED_FACILITY_NOT_IMPLEMENTED");
            n3 = n4 | 69;
        }
        n2 = n3;
        if ((n & 70) == 70) {
            arrayList.add("ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE");
            n2 = n3 | 70;
        }
        n4 = n2;
        if ((n & 79) == 79) {
            arrayList.add("SERVICE_OR_OPTION_NOT_IMPLEMENTED");
            n4 = n2 | 79;
        }
        n3 = n4;
        if ((n & 81) == 81) {
            arrayList.add("INVALID_TRANSACTION_IDENTIFIER");
            n3 = n4 | 81;
        }
        n2 = n3;
        if ((n & 87) == 87) {
            arrayList.add("USER_NOT_MEMBER_OF_CUG");
            n2 = n3 | 87;
        }
        n3 = n2;
        if ((n & 88) == 88) {
            arrayList.add("INCOMPATIBLE_DESTINATION");
            n3 = n2 | 88;
        }
        n2 = n3;
        if ((n & 91) == 91) {
            arrayList.add("INVALID_TRANSIT_NW_SELECTION");
            n2 = n3 | 91;
        }
        n3 = n2;
        if ((n & 95) == 95) {
            arrayList.add("SEMANTICALLY_INCORRECT_MESSAGE");
            n3 = n2 | 95;
        }
        n2 = n3;
        if ((n & 96) == 96) {
            arrayList.add("INVALID_MANDATORY_INFORMATION");
            n2 = n3 | 96;
        }
        n3 = n2;
        if ((n & 97) == 97) {
            arrayList.add("MESSAGE_TYPE_NON_IMPLEMENTED");
            n3 = n2 | 97;
        }
        n4 = n3;
        if ((n & 98) == 98) {
            arrayList.add("MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE");
            n4 = n3 | 98;
        }
        n2 = n4;
        if ((n & 99) == 99) {
            arrayList.add("INFORMATION_ELEMENT_NON_EXISTENT");
            n2 = n4 | 99;
        }
        n3 = n2;
        if ((n & 100) == 100) {
            arrayList.add("CONDITIONAL_IE_ERROR");
            n3 = n2 | 100;
        }
        n2 = n3;
        if ((n & 101) == 101) {
            arrayList.add("MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE");
            n2 = n3 | 101;
        }
        n3 = n2;
        if ((n & 102) == 102) {
            arrayList.add("RECOVERY_ON_TIMER_EXPIRED");
            n3 = n2 | 102;
        }
        n2 = n3;
        if ((n & 111) == 111) {
            arrayList.add("PROTOCOL_ERROR_UNSPECIFIED");
            n2 = n3 | 111;
        }
        n3 = n2;
        if ((n & 127) == 127) {
            arrayList.add("INTERWORKING_UNSPECIFIED");
            n3 = n2 | 127;
        }
        n2 = n3;
        if ((n & 240) == 240) {
            arrayList.add("CALL_BARRED");
            n2 = n3 | 240;
        }
        n3 = n2;
        if ((n & 241) == 241) {
            arrayList.add("FDN_BLOCKED");
            n3 = n2 | 241;
        }
        n2 = n3;
        if ((n & 242) == 242) {
            arrayList.add("IMSI_UNKNOWN_IN_VLR");
            n2 = n3 | 242;
        }
        n3 = n2;
        if ((n & 243) == 243) {
            arrayList.add("IMEI_NOT_ACCEPTED");
            n3 = n2 | 243;
        }
        n2 = n3;
        if ((n & 244) == 244) {
            arrayList.add("DIAL_MODIFIED_TO_USSD");
            n2 = n3 | 244;
        }
        n4 = n2;
        if ((n & 245) == 245) {
            arrayList.add("DIAL_MODIFIED_TO_SS");
            n4 = n2 | 245;
        }
        n3 = n4;
        if ((n & 246) == 246) {
            arrayList.add("DIAL_MODIFIED_TO_DIAL");
            n3 = n4 | 246;
        }
        n2 = n3;
        if ((n & 247) == 247) {
            arrayList.add("RADIO_OFF");
            n2 = n3 | 247;
        }
        n3 = n2;
        if ((n & 248) == 248) {
            arrayList.add("OUT_OF_SERVICE");
            n3 = n2 | 248;
        }
        n2 = n3;
        if ((n & 249) == 249) {
            arrayList.add("NO_VALID_SIM");
            n2 = n3 | 249;
        }
        n3 = n2;
        if ((n & 250) == 250) {
            arrayList.add("RADIO_INTERNAL_ERROR");
            n3 = n2 | 250;
        }
        n2 = n3;
        if ((n & 251) == 251) {
            arrayList.add("NETWORK_RESP_TIMEOUT");
            n2 = n3 | 251;
        }
        n3 = n2;
        if ((n & 252) == 252) {
            arrayList.add("NETWORK_REJECT");
            n3 = n2 | 252;
        }
        n2 = n3;
        if ((n & 253) == 253) {
            arrayList.add("RADIO_ACCESS_FAILURE");
            n2 = n3 | 253;
        }
        n3 = n2;
        if ((n & 254) == 254) {
            arrayList.add("RADIO_LINK_FAILURE");
            n3 = n2 | 254;
        }
        n2 = n3;
        if ((n & 255) == 255) {
            arrayList.add("RADIO_LINK_LOST");
            n2 = n3 | 255;
        }
        n4 = n2;
        if ((n & 256) == 256) {
            arrayList.add("RADIO_UPLINK_FAILURE");
            n4 = n2 | 256;
        }
        n3 = n4;
        if ((n & 257) == 257) {
            arrayList.add("RADIO_SETUP_FAILURE");
            n3 = n4 | 257;
        }
        n2 = n3;
        if ((n & 258) == 258) {
            arrayList.add("RADIO_RELEASE_NORMAL");
            n2 = n3 | 258;
        }
        n3 = n2;
        if ((n & 259) == 259) {
            arrayList.add("RADIO_RELEASE_ABNORMAL");
            n3 = n2 | 259;
        }
        n2 = n3;
        if ((n & 260) == 260) {
            arrayList.add("ACCESS_CLASS_BLOCKED");
            n2 = n3 | 260;
        }
        n4 = n2;
        if ((n & 261) == 261) {
            arrayList.add("NETWORK_DETACH");
            n4 = n2 | 261;
        }
        n3 = n4;
        if ((n & 1000) == 1000) {
            arrayList.add("CDMA_LOCKED_UNTIL_POWER_CYCLE");
            n3 = n4 | 1000;
        }
        n4 = n3;
        if ((n & 1001) == 1001) {
            arrayList.add("CDMA_DROP");
            n4 = n3 | 1001;
        }
        n2 = n4;
        if ((n & 1002) == 1002) {
            arrayList.add("CDMA_INTERCEPT");
            n2 = n4 | 1002;
        }
        n3 = n2;
        if ((n & 1003) == 1003) {
            arrayList.add("CDMA_REORDER");
            n3 = n2 | 1003;
        }
        n2 = n3;
        if ((n & 1004) == 1004) {
            arrayList.add("CDMA_SO_REJECT");
            n2 = n3 | 1004;
        }
        n3 = n2;
        if ((n & 1005) == 1005) {
            arrayList.add("CDMA_RETRY_ORDER");
            n3 = n2 | 1005;
        }
        n2 = n3;
        if ((n & 1006) == 1006) {
            arrayList.add("CDMA_ACCESS_FAILURE");
            n2 = n3 | 1006;
        }
        n3 = n2;
        if ((n & 1007) == 1007) {
            arrayList.add("CDMA_PREEMPTED");
            n3 = n2 | 1007;
        }
        n2 = n3;
        if ((n & 1008) == 1008) {
            arrayList.add("CDMA_NOT_EMERGENCY");
            n2 = n3 | 1008;
        }
        n3 = n2;
        if ((n & 1009) == 1009) {
            arrayList.add("CDMA_ACCESS_BLOCKED");
            n3 = n2 | 1009;
        }
        n2 = n3;
        if ((61441 & n) == 61441) {
            arrayList.add("OEM_CAUSE_1");
            n2 = n3 | 61441;
        }
        n3 = n2;
        if ((61442 & n) == 61442) {
            arrayList.add("OEM_CAUSE_2");
            n3 = n2 | 61442;
        }
        n2 = n3;
        if ((61443 & n) == 61443) {
            arrayList.add("OEM_CAUSE_3");
            n2 = n3 | 61443;
        }
        n3 = n2;
        if ((61444 & n) == 61444) {
            arrayList.add("OEM_CAUSE_4");
            n3 = n2 | 61444;
        }
        n2 = n3;
        if ((61445 & n) == 61445) {
            arrayList.add("OEM_CAUSE_5");
            n2 = n3 | 61445;
        }
        n3 = n2;
        if ((61446 & n) == 61446) {
            arrayList.add("OEM_CAUSE_6");
            n3 = n2 | 61446;
        }
        n2 = n3;
        if ((61447 & n) == 61447) {
            arrayList.add("OEM_CAUSE_7");
            n2 = n3 | 61447;
        }
        n4 = n2;
        if ((61448 & n) == 61448) {
            arrayList.add("OEM_CAUSE_8");
            n4 = n2 | 61448;
        }
        n3 = n4;
        if ((61449 & n) == 61449) {
            arrayList.add("OEM_CAUSE_9");
            n3 = n4 | 61449;
        }
        n2 = n3;
        if ((61450 & n) == 61450) {
            arrayList.add("OEM_CAUSE_10");
            n2 = n3 | 61450;
        }
        n3 = n2;
        if ((61451 & n) == 61451) {
            arrayList.add("OEM_CAUSE_11");
            n3 = n2 | 61451;
        }
        n2 = n3;
        if ((61452 & n) == 61452) {
            arrayList.add("OEM_CAUSE_12");
            n2 = n3 | 61452;
        }
        n3 = n2;
        if ((61453 & n) == 61453) {
            arrayList.add("OEM_CAUSE_13");
            n3 = n2 | 61453;
        }
        n2 = n3;
        if ((61454 & n) == 61454) {
            arrayList.add("OEM_CAUSE_14");
            n2 = n3 | 61454;
        }
        n3 = n2;
        if ((61455 & n) == 61455) {
            arrayList.add("OEM_CAUSE_15");
            n3 = n2 | 61455;
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
        if (n == 1) {
            return "UNOBTAINABLE_NUMBER";
        }
        if (n == 3) {
            return "NO_ROUTE_TO_DESTINATION";
        }
        if (n == 6) {
            return "CHANNEL_UNACCEPTABLE";
        }
        if (n == 8) {
            return "OPERATOR_DETERMINED_BARRING";
        }
        if (n == 16) {
            return "NORMAL";
        }
        if (n == 17) {
            return "BUSY";
        }
        if (n == 18) {
            return "NO_USER_RESPONDING";
        }
        if (n == 19) {
            return "NO_ANSWER_FROM_USER";
        }
        if (n == 21) {
            return "CALL_REJECTED";
        }
        if (n == 22) {
            return "NUMBER_CHANGED";
        }
        if (n == 25) {
            return "PREEMPTION";
        }
        if (n == 27) {
            return "DESTINATION_OUT_OF_ORDER";
        }
        if (n == 28) {
            return "INVALID_NUMBER_FORMAT";
        }
        if (n == 29) {
            return "FACILITY_REJECTED";
        }
        if (n == 30) {
            return "RESP_TO_STATUS_ENQUIRY";
        }
        if (n == 31) {
            return "NORMAL_UNSPECIFIED";
        }
        if (n == 34) {
            return "CONGESTION";
        }
        if (n == 38) {
            return "NETWORK_OUT_OF_ORDER";
        }
        if (n == 41) {
            return "TEMPORARY_FAILURE";
        }
        if (n == 42) {
            return "SWITCHING_EQUIPMENT_CONGESTION";
        }
        if (n == 43) {
            return "ACCESS_INFORMATION_DISCARDED";
        }
        if (n == 44) {
            return "REQUESTED_CIRCUIT_OR_CHANNEL_NOT_AVAILABLE";
        }
        if (n == 47) {
            return "RESOURCES_UNAVAILABLE_OR_UNSPECIFIED";
        }
        if (n == 49) {
            return "QOS_UNAVAILABLE";
        }
        if (n == 50) {
            return "REQUESTED_FACILITY_NOT_SUBSCRIBED";
        }
        if (n == 55) {
            return "INCOMING_CALLS_BARRED_WITHIN_CUG";
        }
        if (n == 57) {
            return "BEARER_CAPABILITY_NOT_AUTHORIZED";
        }
        if (n == 58) {
            return "BEARER_CAPABILITY_UNAVAILABLE";
        }
        if (n == 63) {
            return "SERVICE_OPTION_NOT_AVAILABLE";
        }
        if (n == 65) {
            return "BEARER_SERVICE_NOT_IMPLEMENTED";
        }
        if (n == 68) {
            return "ACM_LIMIT_EXCEEDED";
        }
        if (n == 69) {
            return "REQUESTED_FACILITY_NOT_IMPLEMENTED";
        }
        if (n == 70) {
            return "ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE";
        }
        if (n == 79) {
            return "SERVICE_OR_OPTION_NOT_IMPLEMENTED";
        }
        if (n == 81) {
            return "INVALID_TRANSACTION_IDENTIFIER";
        }
        if (n == 87) {
            return "USER_NOT_MEMBER_OF_CUG";
        }
        if (n == 88) {
            return "INCOMPATIBLE_DESTINATION";
        }
        if (n == 91) {
            return "INVALID_TRANSIT_NW_SELECTION";
        }
        if (n == 95) {
            return "SEMANTICALLY_INCORRECT_MESSAGE";
        }
        if (n == 96) {
            return "INVALID_MANDATORY_INFORMATION";
        }
        if (n == 97) {
            return "MESSAGE_TYPE_NON_IMPLEMENTED";
        }
        if (n == 98) {
            return "MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (n == 99) {
            return "INFORMATION_ELEMENT_NON_EXISTENT";
        }
        if (n == 100) {
            return "CONDITIONAL_IE_ERROR";
        }
        if (n == 101) {
            return "MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE";
        }
        if (n == 102) {
            return "RECOVERY_ON_TIMER_EXPIRED";
        }
        if (n == 111) {
            return "PROTOCOL_ERROR_UNSPECIFIED";
        }
        if (n == 127) {
            return "INTERWORKING_UNSPECIFIED";
        }
        if (n == 240) {
            return "CALL_BARRED";
        }
        if (n == 241) {
            return "FDN_BLOCKED";
        }
        if (n == 242) {
            return "IMSI_UNKNOWN_IN_VLR";
        }
        if (n == 243) {
            return "IMEI_NOT_ACCEPTED";
        }
        if (n == 244) {
            return "DIAL_MODIFIED_TO_USSD";
        }
        if (n == 245) {
            return "DIAL_MODIFIED_TO_SS";
        }
        if (n == 246) {
            return "DIAL_MODIFIED_TO_DIAL";
        }
        if (n == 247) {
            return "RADIO_OFF";
        }
        if (n == 248) {
            return "OUT_OF_SERVICE";
        }
        if (n == 249) {
            return "NO_VALID_SIM";
        }
        if (n == 250) {
            return "RADIO_INTERNAL_ERROR";
        }
        if (n == 251) {
            return "NETWORK_RESP_TIMEOUT";
        }
        if (n == 252) {
            return "NETWORK_REJECT";
        }
        if (n == 253) {
            return "RADIO_ACCESS_FAILURE";
        }
        if (n == 254) {
            return "RADIO_LINK_FAILURE";
        }
        if (n == 255) {
            return "RADIO_LINK_LOST";
        }
        if (n == 256) {
            return "RADIO_UPLINK_FAILURE";
        }
        if (n == 257) {
            return "RADIO_SETUP_FAILURE";
        }
        if (n == 258) {
            return "RADIO_RELEASE_NORMAL";
        }
        if (n == 259) {
            return "RADIO_RELEASE_ABNORMAL";
        }
        if (n == 260) {
            return "ACCESS_CLASS_BLOCKED";
        }
        if (n == 261) {
            return "NETWORK_DETACH";
        }
        if (n == 1000) {
            return "CDMA_LOCKED_UNTIL_POWER_CYCLE";
        }
        if (n == 1001) {
            return "CDMA_DROP";
        }
        if (n == 1002) {
            return "CDMA_INTERCEPT";
        }
        if (n == 1003) {
            return "CDMA_REORDER";
        }
        if (n == 1004) {
            return "CDMA_SO_REJECT";
        }
        if (n == 1005) {
            return "CDMA_RETRY_ORDER";
        }
        if (n == 1006) {
            return "CDMA_ACCESS_FAILURE";
        }
        if (n == 1007) {
            return "CDMA_PREEMPTED";
        }
        if (n == 1008) {
            return "CDMA_NOT_EMERGENCY";
        }
        if (n == 1009) {
            return "CDMA_ACCESS_BLOCKED";
        }
        if (n == 61441) {
            return "OEM_CAUSE_1";
        }
        if (n == 61442) {
            return "OEM_CAUSE_2";
        }
        if (n == 61443) {
            return "OEM_CAUSE_3";
        }
        if (n == 61444) {
            return "OEM_CAUSE_4";
        }
        if (n == 61445) {
            return "OEM_CAUSE_5";
        }
        if (n == 61446) {
            return "OEM_CAUSE_6";
        }
        if (n == 61447) {
            return "OEM_CAUSE_7";
        }
        if (n == 61448) {
            return "OEM_CAUSE_8";
        }
        if (n == 61449) {
            return "OEM_CAUSE_9";
        }
        if (n == 61450) {
            return "OEM_CAUSE_10";
        }
        if (n == 61451) {
            return "OEM_CAUSE_11";
        }
        if (n == 61452) {
            return "OEM_CAUSE_12";
        }
        if (n == 61453) {
            return "OEM_CAUSE_13";
        }
        if (n == 61454) {
            return "OEM_CAUSE_14";
        }
        if (n == 61455) {
            return "OEM_CAUSE_15";
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

