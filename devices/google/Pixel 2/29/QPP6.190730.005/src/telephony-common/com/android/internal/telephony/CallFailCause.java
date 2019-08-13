/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

public interface CallFailCause {
    public static final int ACCESS_CLASS_BLOCKED = 260;
    public static final int ACCESS_INFORMATION_DISCARDED = 43;
    public static final int ACM_LIMIT_EXCEEDED = 68;
    public static final int BEARER_CAPABILITY_NOT_AUTHORISED = 57;
    public static final int BEARER_NOT_AVAIL = 58;
    public static final int BEARER_SERVICE_NOT_IMPLEMENTED = 65;
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
    public static final int CHANNEL_NOT_AVAIL = 44;
    public static final int CHANNEL_UNACCEPTABLE = 6;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int DESTINATION_OUT_OF_ORDER = 27;
    public static final int DIAL_MODIFIED_TO_DIAL = 246;
    public static final int DIAL_MODIFIED_TO_SS = 245;
    public static final int DIAL_MODIFIED_TO_USSD = 244;
    public static final int EMERGENCY_PERM_FAILURE = 326;
    public static final int EMERGENCY_TEMP_FAILURE = 325;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int FACILITY_REJECTED = 29;
    public static final int FDN_BLOCKED = 241;
    public static final int IE_NON_EXISTENT_OR_NOT_IMPLEMENTED = 99;
    public static final int IMEI_NOT_ACCEPTED = 243;
    public static final int INCOMING_CALL_BARRED_WITHIN_CUG = 55;
    public static final int INCOMPATIBLE_DESTINATION = 88;
    public static final int INTERWORKING_UNSPECIFIED = 127;
    public static final int INVALID_MANDATORY_INFORMATION = 96;
    public static final int INVALID_NUMBER_FORMAT = 28;
    public static final int INVALID_TRANSACTION_ID_VALUE = 81;
    public static final int INVALID_TRANSIT_NETWORK_SELECTION = 91;
    public static final int MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE = 101;
    public static final int MESSAGE_TYPE_NON_EXISTENT = 97;
    public static final int MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROT_STATE = 98;
    public static final int NETWORK_OUT_OF_ORDER = 38;
    public static final int NON_SELECTED_USER_CLEARING = 26;
    public static final int NORMAL_CLEARING = 16;
    public static final int NORMAL_UNSPECIFIED = 31;
    public static final int NOT_VALID = -1;
    public static final int NO_CIRCUIT_AVAIL = 34;
    public static final int NO_ROUTE_TO_DEST = 3;
    public static final int NO_USER_RESPONDING = 18;
    public static final int NUMBER_CHANGED = 22;
    public static final int ONLY_RESTRICTED_DIGITAL_INFO_BC_AVAILABLE = 70;
    public static final int OPERATOR_DETERMINED_BARRING = 8;
    public static final int PRE_EMPTION = 25;
    public static final int PROTOCOL_ERROR_UNSPECIFIED = 111;
    public static final int QOS_NOT_AVAIL = 49;
    public static final int RECOVERY_ON_TIMER_EXPIRY = 102;
    public static final int REQUESTED_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int REQUESTED_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int RESOURCES_UNAVAILABLE_UNSPECIFIED = 47;
    public static final int SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SERVICE_OR_OPTION_NOT_AVAILABLE = 63;
    public static final int SERVICE_OR_OPTION_NOT_IMPLEMENTED = 79;
    public static final int STATUS_ENQUIRY = 30;
    public static final int SWITCHING_CONGESTION = 42;
    public static final int TEMPORARY_FAILURE = 41;
    public static final int UNOBTAINABLE_NUMBER = 1;
    public static final int USER_ALERTING_NO_ANSWER = 19;
    public static final int USER_BUSY = 17;
    public static final int USER_NOT_MEMBER_OF_CUG = 87;
}

