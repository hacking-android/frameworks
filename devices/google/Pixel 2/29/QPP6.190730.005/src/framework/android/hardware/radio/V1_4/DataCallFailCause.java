/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import java.util.ArrayList;

public final class DataCallFailCause {
    public static final int ACCESS_ATTEMPT_ALREADY_IN_PROGRESS = 2219;
    public static final int ACCESS_BLOCK = 2087;
    public static final int ACCESS_BLOCK_ALL = 2088;
    public static final int ACCESS_CLASS_DSAC_REJECTION = 2108;
    public static final int ACCESS_CONTROL_LIST_CHECK_FAILURE = 2128;
    public static final int ACTIVATION_REJECTED_BCM_VIOLATION = 48;
    public static final int ACTIVATION_REJECT_GGSN = 30;
    public static final int ACTIVATION_REJECT_UNSPECIFIED = 31;
    public static final int APN_DISABLED = 2045;
    public static final int APN_DISALLOWED_ON_ROAMING = 2059;
    public static final int APN_MISMATCH = 2054;
    public static final int APN_PARAMETERS_CHANGED = 2060;
    public static final int APN_PENDING_HANDOVER = 2041;
    public static final int APN_TYPE_CONFLICT = 112;
    public static final int AUTH_FAILURE_ON_EMERGENCY_CALL = 122;
    public static final int BEARER_HANDLING_NOT_SUPPORTED = 60;
    public static final int CALL_DISALLOWED_IN_ROAMING = 2068;
    public static final int CALL_PREEMPT_BY_EMERGENCY_APN = 127;
    public static final int CANNOT_ENCODE_OTA_MESSAGE = 2159;
    public static final int CDMA_ALERT_STOP = 2077;
    public static final int CDMA_INCOMING_CALL = 2076;
    public static final int CDMA_INTERCEPT = 2073;
    public static final int CDMA_LOCK = 2072;
    public static final int CDMA_RELEASE_DUE_TO_SO_REJECTION = 2075;
    public static final int CDMA_REORDER = 2074;
    public static final int CDMA_RETRY_ORDER = 2086;
    public static final int CHANNEL_ACQUISITION_FAILURE = 2078;
    public static final int CLOSE_IN_PROGRESS = 2030;
    public static final int COLLISION_WITH_NETWORK_INITIATED_REQUEST = 56;
    public static final int COMPANION_IFACE_IN_USE = 118;
    public static final int CONCURRENT_SERVICES_INCOMPATIBLE = 2083;
    public static final int CONCURRENT_SERVICES_NOT_ALLOWED = 2091;
    public static final int CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION = 2080;
    public static final int CONDITIONAL_IE_ERROR = 100;
    public static final int CONGESTION = 2106;
    public static final int CONNECTION_RELEASED = 2113;
    public static final int CS_DOMAIN_NOT_AVAILABLE = 2181;
    public static final int CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED = 2188;
    public static final int DATA_PLAN_EXPIRED = 2198;
    public static final int DATA_REGISTRATION_FAIL = -2;
    public static final int DATA_ROAMING_SETTINGS_DISABLED = 2064;
    public static final int DATA_SETTINGS_DISABLED = 2063;
    public static final int DBM_OR_SMS_IN_PROGRESS = 2211;
    public static final int DDS_SWITCHED = 2065;
    public static final int DDS_SWITCH_IN_PROGRESS = 2067;
    public static final int DRB_RELEASED_BY_RRC = 2112;
    public static final int DS_EXPLICIT_DEACTIVATION = 2125;
    public static final int DUAL_SWITCH = 2227;
    public static final int DUN_CALL_DISALLOWED = 2056;
    public static final int DUPLICATE_BEARER_ID = 2118;
    public static final int EHRPD_TO_HRPD_FALLBACK = 2049;
    public static final int EMBMS_NOT_ENABLED = 2193;
    public static final int EMBMS_REGULAR_DEACTIVATION = 2195;
    public static final int EMERGENCY_IFACE_ONLY = 116;
    public static final int EMERGENCY_MODE = 2221;
    public static final int EMM_ACCESS_BARRED = 115;
    public static final int EMM_ACCESS_BARRED_INFINITE_RETRY = 121;
    public static final int EMM_ATTACH_FAILED = 2115;
    public static final int EMM_ATTACH_STARTED = 2116;
    public static final int EMM_DETACHED = 2114;
    public static final int EMM_T3417_EXPIRED = 2130;
    public static final int EMM_T3417_EXT_EXPIRED = 2131;
    public static final int EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED = 2178;
    public static final int EPS_SERVICES_NOT_ALLOWED_IN_PLMN = 2179;
    public static final int ERROR_UNSPECIFIED = 65535;
    public static final int ESM_BAD_OTA_MESSAGE = 2122;
    public static final int ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK = 2120;
    public static final int ESM_COLLISION_SCENARIOS = 2119;
    public static final int ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT = 2124;
    public static final int ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL = 2123;
    public static final int ESM_FAILURE = 2182;
    public static final int ESM_INFO_NOT_RECEIVED = 53;
    public static final int ESM_LOCAL_CAUSE_NONE = 2126;
    public static final int ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER = 2121;
    public static final int ESM_PROCEDURE_TIME_OUT = 2155;
    public static final int ESM_UNKNOWN_EPS_BEARER_CONTEXT = 2111;
    public static final int EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE = 2201;
    public static final int EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY = 2200;
    public static final int EVDO_HDR_CHANGED = 2202;
    public static final int EVDO_HDR_CONNECTION_SETUP_TIMEOUT = 2206;
    public static final int EVDO_HDR_EXITED = 2203;
    public static final int EVDO_HDR_NO_SESSION = 2204;
    public static final int EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL = 2205;
    public static final int FADE = 2217;
    public static final int FAILED_TO_ACQUIRE_COLOCATED_HDR = 2207;
    public static final int FEATURE_NOT_SUPP = 40;
    public static final int FILTER_SEMANTIC_ERROR = 44;
    public static final int FILTER_SYTAX_ERROR = 45;
    public static final int FORBIDDEN_APN_NAME = 2066;
    public static final int GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED = 2097;
    public static final int GPRS_SERVICES_NOT_ALLOWED = 2098;
    public static final int GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN = 2103;
    public static final int HANDOFF_PREFERENCE_CHANGED = 2251;
    public static final int HDR_ACCESS_FAILURE = 2213;
    public static final int HDR_FADE = 2212;
    public static final int HDR_NO_LOCK_GRANTED = 2210;
    public static final int IFACE_AND_POL_FAMILY_MISMATCH = 120;
    public static final int IFACE_MISMATCH = 117;
    public static final int ILLEGAL_ME = 2096;
    public static final int ILLEGAL_MS = 2095;
    public static final int IMEI_NOT_ACCEPTED = 2177;
    public static final int IMPLICITLY_DETACHED = 2100;
    public static final int IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER = 2176;
    public static final int INCOMING_CALL_REJECTED = 2092;
    public static final int INSUFFICIENT_RESOURCES = 26;
    public static final int INTERFACE_IN_USE = 2058;
    public static final int INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN = 114;
    public static final int INTERNAL_EPC_NONEPC_TRANSITION = 2057;
    public static final int INVALID_CONNECTION_ID = 2156;
    public static final int INVALID_DNS_ADDR = 123;
    public static final int INVALID_EMM_STATE = 2190;
    public static final int INVALID_MANDATORY_INFO = 96;
    public static final int INVALID_MODE = 2223;
    public static final int INVALID_PCSCF_ADDR = 113;
    public static final int INVALID_PCSCF_OR_DNS_ADDRESS = 124;
    public static final int INVALID_PRIMARY_NSAPI = 2158;
    public static final int INVALID_SIM_STATE = 2224;
    public static final int INVALID_TRANSACTION_ID = 81;
    public static final int IPV6_ADDRESS_TRANSFER_FAILED = 2047;
    public static final int IPV6_PREFIX_UNAVAILABLE = 2250;
    public static final int IP_ADDRESS_MISMATCH = 119;
    public static final int IP_VERSION_MISMATCH = 2055;
    public static final int IRAT_HANDOVER_FAILED = 2194;
    public static final int IS707B_MAX_ACCESS_PROBES = 2089;
    public static final int LIMITED_TO_IPV4 = 2234;
    public static final int LIMITED_TO_IPV6 = 2235;
    public static final int LLC_SNDCP = 25;
    public static final int LOCAL_END = 2215;
    public static final int LOCATION_AREA_NOT_ALLOWED = 2102;
    public static final int LOWER_LAYER_REGISTRATION_FAILURE = 2197;
    public static final int LOW_POWER_MODE_OR_POWERING_DOWN = 2044;
    public static final int LTE_NAS_SERVICE_REQUEST_FAILED = 2117;
    public static final int LTE_THROTTLING_NOT_REQUIRED = 2127;
    public static final int MAC_FAILURE = 2183;
    public static final int MAXIMIUM_NSAPIS_EXCEEDED = 2157;
    public static final int MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED = 2166;
    public static final int MAX_ACCESS_PROBE = 2079;
    public static final int MAX_ACTIVE_PDP_CONTEXT_REACHED = 65;
    public static final int MAX_IPV4_CONNECTIONS = 2052;
    public static final int MAX_IPV6_CONNECTIONS = 2053;
    public static final int MAX_PPP_INACTIVITY_TIMER_EXPIRED = 2046;
    public static final int MESSAGE_INCORRECT_SEMANTIC = 95;
    public static final int MESSAGE_TYPE_UNSUPPORTED = 97;
    public static final int MIP_CONFIG_FAILURE = 2050;
    public static final int MIP_FA_ADMIN_PROHIBITED = 2001;
    public static final int MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED = 2012;
    public static final int MIP_FA_ENCAPSULATION_UNAVAILABLE = 2008;
    public static final int MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE = 2004;
    public static final int MIP_FA_INSUFFICIENT_RESOURCES = 2002;
    public static final int MIP_FA_MALFORMED_REPLY = 2007;
    public static final int MIP_FA_MALFORMED_REQUEST = 2006;
    public static final int MIP_FA_MISSING_CHALLENGE = 2017;
    public static final int MIP_FA_MISSING_HOME_ADDRESS = 2015;
    public static final int MIP_FA_MISSING_HOME_AGENT = 2014;
    public static final int MIP_FA_MISSING_NAI = 2013;
    public static final int MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2003;
    public static final int MIP_FA_REASON_UNSPECIFIED = 2000;
    public static final int MIP_FA_REQUESTED_LIFETIME_TOO_LONG = 2005;
    public static final int MIP_FA_REVERSE_TUNNEL_IS_MANDATORY = 2011;
    public static final int MIP_FA_REVERSE_TUNNEL_UNAVAILABLE = 2010;
    public static final int MIP_FA_STALE_CHALLENGE = 2018;
    public static final int MIP_FA_UNKNOWN_CHALLENGE = 2016;
    public static final int MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE = 2009;
    public static final int MIP_HA_ADMIN_PROHIBITED = 2020;
    public static final int MIP_HA_ENCAPSULATION_UNAVAILABLE = 2029;
    public static final int MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE = 2023;
    public static final int MIP_HA_INSUFFICIENT_RESOURCES = 2021;
    public static final int MIP_HA_MALFORMED_REQUEST = 2025;
    public static final int MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2022;
    public static final int MIP_HA_REASON_UNSPECIFIED = 2019;
    public static final int MIP_HA_REGISTRATION_ID_MISMATCH = 2024;
    public static final int MIP_HA_REVERSE_TUNNEL_IS_MANDATORY = 2028;
    public static final int MIP_HA_REVERSE_TUNNEL_UNAVAILABLE = 2027;
    public static final int MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS = 2026;
    public static final int MISSING_UKNOWN_APN = 27;
    public static final int MODEM_APP_PREEMPTED = 2032;
    public static final int MODEM_RESTART = 2037;
    public static final int MSC_TEMPORARILY_NOT_REACHABLE = 2180;
    public static final int MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE = 101;
    public static final int MSG_TYPE_NONCOMPATIBLE_STATE = 98;
    public static final int MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK = 2099;
    public static final int MULTIPLE_PDP_CALL_NOT_ALLOWED = 2192;
    public static final int MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED = 55;
    public static final int NAS_LAYER_FAILURE = 2191;
    public static final int NAS_REQUEST_REJECTED_BY_NETWORK = 2167;
    public static final int NAS_SIGNALLING = 14;
    public static final int NETWORK_FAILURE = 38;
    public static final int NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH = 2154;
    public static final int NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH = 2153;
    public static final int NETWORK_INITIATED_TERMINATION = 2031;
    public static final int NONE = 0;
    public static final int NON_IP_NOT_SUPPORTED = 2069;
    public static final int NORMAL_RELEASE = 2218;
    public static final int NO_CDMA_SERVICE = 2084;
    public static final int NO_COLLOCATED_HDR = 2225;
    public static final int NO_EPS_BEARER_CONTEXT_ACTIVATED = 2189;
    public static final int NO_GPRS_CONTEXT = 2094;
    public static final int NO_HYBRID_HDR_SERVICE = 2209;
    public static final int NO_PDP_CONTEXT_ACTIVATED = 2107;
    public static final int NO_RESPONSE_FROM_BASE_STATION = 2081;
    public static final int NO_SERVICE = 2216;
    public static final int NO_SERVICE_ON_GATEWAY = 2093;
    public static final int NSAPI_IN_USE = 35;
    public static final int NULL_APN_DISALLOWED = 2061;
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
    public static final int ONLY_IPV4V6_ALLOWED = 57;
    public static final int ONLY_IPV4_ALLOWED = 50;
    public static final int ONLY_IPV6_ALLOWED = 51;
    public static final int ONLY_NON_IP_ALLOWED = 58;
    public static final int ONLY_SINGLE_BEARER_ALLOWED = 52;
    public static final int OPERATOR_BARRED = 8;
    public static final int OTASP_COMMIT_IN_PROGRESS = 2208;
    public static final int PDN_CONN_DOES_NOT_EXIST = 54;
    public static final int PDN_INACTIVITY_TIMER_EXPIRED = 2051;
    public static final int PDN_IPV4_CALL_DISALLOWED = 2033;
    public static final int PDN_IPV4_CALL_THROTTLED = 2034;
    public static final int PDN_IPV6_CALL_DISALLOWED = 2035;
    public static final int PDN_IPV6_CALL_THROTTLED = 2036;
    public static final int PDN_NON_IP_CALL_DISALLOWED = 2071;
    public static final int PDN_NON_IP_CALL_THROTTLED = 2070;
    public static final int PDP_ACTIVATE_MAX_RETRY_FAILED = 2109;
    public static final int PDP_DUPLICATE = 2104;
    public static final int PDP_ESTABLISH_TIMEOUT_EXPIRED = 2161;
    public static final int PDP_INACTIVE_TIMEOUT_EXPIRED = 2163;
    public static final int PDP_LOWERLAYER_ERROR = 2164;
    public static final int PDP_MODIFY_COLLISION = 2165;
    public static final int PDP_MODIFY_TIMEOUT_EXPIRED = 2162;
    public static final int PDP_PPP_NOT_SUPPORTED = 2038;
    public static final int PDP_WITHOUT_ACTIVE_TFT = 46;
    public static final int PHONE_IN_USE = 2222;
    public static final int PHYSICAL_LINK_CLOSE_IN_PROGRESS = 2040;
    public static final int PLMN_NOT_ALLOWED = 2101;
    public static final int PPP_AUTH_FAILURE = 2229;
    public static final int PPP_CHAP_FAILURE = 2232;
    public static final int PPP_CLOSE_IN_PROGRESS = 2233;
    public static final int PPP_OPTION_MISMATCH = 2230;
    public static final int PPP_PAP_FAILURE = 2231;
    public static final int PPP_TIMEOUT = 2228;
    public static final int PREF_RADIO_TECH_CHANGED = -4;
    public static final int PROFILE_BEARER_INCOMPATIBLE = 2042;
    public static final int PROTOCOL_ERRORS = 111;
    public static final int QOS_NOT_ACCEPTED = 37;
    public static final int RADIO_ACCESS_BEARER_FAILURE = 2110;
    public static final int RADIO_ACCESS_BEARER_SETUP_FAILURE = 2160;
    public static final int RADIO_POWER_OFF = -5;
    public static final int REDIRECTION_OR_HANDOFF_IN_PROGRESS = 2220;
    public static final int REGULAR_DEACTIVATION = 36;
    public static final int REJECTED_BY_BASE_STATION = 2082;
    public static final int RRC_CONNECTION_ABORTED_AFTER_HANDOVER = 2173;
    public static final int RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE = 2174;
    public static final int RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE = 2171;
    public static final int RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE = 2175;
    public static final int RRC_CONNECTION_ABORT_REQUEST = 2151;
    public static final int RRC_CONNECTION_ACCESS_BARRED = 2139;
    public static final int RRC_CONNECTION_ACCESS_STRATUM_FAILURE = 2137;
    public static final int RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS = 2138;
    public static final int RRC_CONNECTION_CELL_NOT_CAMPED = 2144;
    public static final int RRC_CONNECTION_CELL_RESELECTION = 2140;
    public static final int RRC_CONNECTION_CONFIG_FAILURE = 2141;
    public static final int RRC_CONNECTION_INVALID_REQUEST = 2168;
    public static final int RRC_CONNECTION_LINK_FAILURE = 2143;
    public static final int RRC_CONNECTION_NORMAL_RELEASE = 2147;
    public static final int RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER = 2150;
    public static final int RRC_CONNECTION_RADIO_LINK_FAILURE = 2148;
    public static final int RRC_CONNECTION_REESTABLISHMENT_FAILURE = 2149;
    public static final int RRC_CONNECTION_REJECT_BY_NETWORK = 2146;
    public static final int RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE = 2172;
    public static final int RRC_CONNECTION_RF_UNAVAILABLE = 2170;
    public static final int RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR = 2152;
    public static final int RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE = 2145;
    public static final int RRC_CONNECTION_TIMER_EXPIRED = 2142;
    public static final int RRC_CONNECTION_TRACKING_AREA_ID_CHANGED = 2169;
    public static final int RRC_UPLINK_CONNECTION_RELEASE = 2134;
    public static final int RRC_UPLINK_DATA_TRANSMISSION_FAILURE = 2132;
    public static final int RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER = 2133;
    public static final int RRC_UPLINK_ERROR_REQUEST_FROM_NAS = 2136;
    public static final int RRC_UPLINK_RADIO_LINK_FAILURE = 2135;
    public static final int RUIM_NOT_PRESENT = 2085;
    public static final int SECURITY_MODE_REJECTED = 2186;
    public static final int SERVICE_NOT_ALLOWED_ON_PLMN = 2129;
    public static final int SERVICE_OPTION_NOT_SUBSCRIBED = 33;
    public static final int SERVICE_OPTION_NOT_SUPPORTED = 32;
    public static final int SERVICE_OPTION_OUT_OF_ORDER = 34;
    public static final int SIGNAL_LOST = -3;
    public static final int SIM_CARD_CHANGED = 2043;
    public static final int SYNCHRONIZATION_FAILURE = 2184;
    public static final int TEST_LOOPBACK_REGULAR_DEACTIVATION = 2196;
    public static final int TETHERED_CALL_ACTIVE = -6;
    public static final int TFT_SEMANTIC_ERROR = 41;
    public static final int TFT_SYTAX_ERROR = 42;
    public static final int THERMAL_EMERGENCY = 2090;
    public static final int THERMAL_MITIGATION = 2062;
    public static final int TRAT_SWAP_FAILED = 2048;
    public static final int UE_INITIATED_DETACH_OR_DISCONNECT = 128;
    public static final int UE_IS_ENTERING_POWERSAVE_MODE = 2226;
    public static final int UE_RAT_CHANGE = 2105;
    public static final int UE_SECURITY_CAPABILITIES_MISMATCH = 2185;
    public static final int UMTS_HANDOVER_TO_IWLAN = 2199;
    public static final int UMTS_REACTIVATION_REQ = 39;
    public static final int UNACCEPTABLE_NON_EPS_AUTHENTICATION = 2187;
    public static final int UNKNOWN_INFO_ELEMENT = 99;
    public static final int UNKNOWN_PDP_ADDRESS_TYPE = 28;
    public static final int UNKNOWN_PDP_CONTEXT = 43;
    public static final int UNPREFERRED_RAT = 2039;
    public static final int UNSUPPORTED_1X_PREV = 2214;
    public static final int UNSUPPORTED_APN_IN_CURRENT_PLMN = 66;
    public static final int UNSUPPORTED_QCI_VALUE = 59;
    public static final int USER_AUTHENTICATION = 29;
    public static final int VOICE_REGISTRATION_FAIL = -1;
    public static final int VSNCP_ADMINISTRATIVELY_PROHIBITED = 2245;
    public static final int VSNCP_APN_UNATHORIZED = 2238;
    public static final int VSNCP_GEN_ERROR = 2237;
    public static final int VSNCP_INSUFFICIENT_PARAMETERS = 2243;
    public static final int VSNCP_NO_PDN_GATEWAY_ADDRESS = 2240;
    public static final int VSNCP_PDN_EXISTS_FOR_THIS_APN = 2248;
    public static final int VSNCP_PDN_GATEWAY_REJECT = 2242;
    public static final int VSNCP_PDN_GATEWAY_UNREACHABLE = 2241;
    public static final int VSNCP_PDN_ID_IN_USE = 2246;
    public static final int VSNCP_PDN_LIMIT_EXCEEDED = 2239;
    public static final int VSNCP_RECONNECT_NOT_ALLOWED = 2249;
    public static final int VSNCP_RESOURCE_UNAVAILABLE = 2244;
    public static final int VSNCP_SUBSCRIBER_LIMITATION = 2247;
    public static final int VSNCP_TIMEOUT = 2236;

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
        n2 = n3;
        if ((n & 28) == 28) {
            arrayList.add("UNKNOWN_PDP_ADDRESS_TYPE");
            n2 = n3 | 28;
        }
        n3 = n2;
        if ((n & 29) == 29) {
            arrayList.add("USER_AUTHENTICATION");
            n3 = n2 | 29;
        }
        n2 = n3;
        if ((n & 30) == 30) {
            arrayList.add("ACTIVATION_REJECT_GGSN");
            n2 = n3 | 30;
        }
        n3 = n2;
        if ((n & 31) == 31) {
            arrayList.add("ACTIVATION_REJECT_UNSPECIFIED");
            n3 = n2 | 31;
        }
        int n4 = n3;
        if ((n & 32) == 32) {
            arrayList.add("SERVICE_OPTION_NOT_SUPPORTED");
            n4 = n3 | 32;
        }
        n2 = n4;
        if ((n & 33) == 33) {
            arrayList.add("SERVICE_OPTION_NOT_SUBSCRIBED");
            n2 = n4 | 33;
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
        n2 = n3;
        if ((n & 40) == 40) {
            arrayList.add("FEATURE_NOT_SUPP");
            n2 = n3 | 40;
        }
        n3 = n2;
        if ((n & 41) == 41) {
            arrayList.add("TFT_SEMANTIC_ERROR");
            n3 = n2 | 41;
        }
        n2 = n3;
        if ((n & 42) == 42) {
            arrayList.add("TFT_SYTAX_ERROR");
            n2 = n3 | 42;
        }
        n3 = n2;
        if ((n & 43) == 43) {
            arrayList.add("UNKNOWN_PDP_CONTEXT");
            n3 = n2 | 43;
        }
        n2 = n3;
        if ((n & 44) == 44) {
            arrayList.add("FILTER_SEMANTIC_ERROR");
            n2 = n3 | 44;
        }
        n4 = n2;
        if ((n & 45) == 45) {
            arrayList.add("FILTER_SYTAX_ERROR");
            n4 = n2 | 45;
        }
        n3 = n4;
        if ((n & 46) == 46) {
            arrayList.add("PDP_WITHOUT_ACTIVE_TFT");
            n3 = n4 | 46;
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
        n2 = n3;
        if ((n & 54) == 54) {
            arrayList.add("PDN_CONN_DOES_NOT_EXIST");
            n2 = n3 | 54;
        }
        n3 = n2;
        if ((n & 55) == 55) {
            arrayList.add("MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED");
            n3 = n2 | 55;
        }
        n2 = n3;
        if ((n & 65) == 65) {
            arrayList.add("MAX_ACTIVE_PDP_CONTEXT_REACHED");
            n2 = n3 | 65;
        }
        n3 = n2;
        if ((n & 66) == 66) {
            arrayList.add("UNSUPPORTED_APN_IN_CURRENT_PLMN");
            n3 = n2 | 66;
        }
        n2 = n3;
        if ((n & 81) == 81) {
            arrayList.add("INVALID_TRANSACTION_ID");
            n2 = n3 | 81;
        }
        n3 = n2;
        if ((n & 95) == 95) {
            arrayList.add("MESSAGE_INCORRECT_SEMANTIC");
            n3 = n2 | 95;
        }
        n2 = n3;
        if ((n & 96) == 96) {
            arrayList.add("INVALID_MANDATORY_INFO");
            n2 = n3 | 96;
        }
        n3 = n2;
        if ((n & 97) == 97) {
            arrayList.add("MESSAGE_TYPE_UNSUPPORTED");
            n3 = n2 | 97;
        }
        n2 = n3;
        if ((n & 98) == 98) {
            arrayList.add("MSG_TYPE_NONCOMPATIBLE_STATE");
            n2 = n3 | 98;
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
        n4 = n2;
        if ((n & 101) == 101) {
            arrayList.add("MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE");
            n4 = n2 | 101;
        }
        n3 = n4;
        if ((n & 111) == 111) {
            arrayList.add("PROTOCOL_ERRORS");
            n3 = n4 | 111;
        }
        n2 = n3;
        if ((n & 112) == 112) {
            arrayList.add("APN_TYPE_CONFLICT");
            n2 = n3 | 112;
        }
        n3 = n2;
        if ((n & 113) == 113) {
            arrayList.add("INVALID_PCSCF_ADDR");
            n3 = n2 | 113;
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
        n4 = n3;
        if ((n & 118) == 118) {
            arrayList.add("COMPANION_IFACE_IN_USE");
            n4 = n3 | 118;
        }
        n2 = n4;
        if ((n & 119) == 119) {
            arrayList.add("IP_ADDRESS_MISMATCH");
            n2 = n4 | 119;
        }
        n3 = n2;
        if ((n & 120) == 120) {
            arrayList.add("IFACE_AND_POL_FAMILY_MISMATCH");
            n3 = n2 | 120;
        }
        n4 = n3;
        if ((n & 121) == 121) {
            arrayList.add("EMM_ACCESS_BARRED_INFINITE_RETRY");
            n4 = n3 | 121;
        }
        n2 = n4;
        if ((n & 122) == 122) {
            arrayList.add("AUTH_FAILURE_ON_EMERGENCY_CALL");
            n2 = n4 | 122;
        }
        n3 = n2;
        if ((n & 4097) == 4097) {
            arrayList.add("OEM_DCFAILCAUSE_1");
            n3 = n2 | 4097;
        }
        n2 = n3;
        if ((n & 4098) == 4098) {
            arrayList.add("OEM_DCFAILCAUSE_2");
            n2 = n3 | 4098;
        }
        n3 = n2;
        if ((n & 4099) == 4099) {
            arrayList.add("OEM_DCFAILCAUSE_3");
            n3 = n2 | 4099;
        }
        n2 = n3;
        if ((n & 4100) == 4100) {
            arrayList.add("OEM_DCFAILCAUSE_4");
            n2 = n3 | 4100;
        }
        n3 = n2;
        if ((n & 4101) == 4101) {
            arrayList.add("OEM_DCFAILCAUSE_5");
            n3 = n2 | 4101;
        }
        n2 = n3;
        if ((n & 4102) == 4102) {
            arrayList.add("OEM_DCFAILCAUSE_6");
            n2 = n3 | 4102;
        }
        n3 = n2;
        if ((n & 4103) == 4103) {
            arrayList.add("OEM_DCFAILCAUSE_7");
            n3 = n2 | 4103;
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
        n2 = n3;
        if ((n & 4108) == 4108) {
            arrayList.add("OEM_DCFAILCAUSE_12");
            n2 = n3 | 4108;
        }
        n3 = n2;
        if ((n & 4109) == 4109) {
            arrayList.add("OEM_DCFAILCAUSE_13");
            n3 = n2 | 4109;
        }
        n2 = n3;
        if ((n & 4110) == 4110) {
            arrayList.add("OEM_DCFAILCAUSE_14");
            n2 = n3 | 4110;
        }
        n3 = n2;
        if ((n & 4111) == 4111) {
            arrayList.add("OEM_DCFAILCAUSE_15");
            n3 = n2 | 4111;
        }
        n4 = n3;
        if ((n & -1) == -1) {
            arrayList.add("VOICE_REGISTRATION_FAIL");
            n4 = n3 | -1;
        }
        n2 = n4;
        if ((n & -2) == -2) {
            arrayList.add("DATA_REGISTRATION_FAIL");
            n2 = n4 | -2;
        }
        n3 = n2;
        if ((n & -3) == -3) {
            arrayList.add("SIGNAL_LOST");
            n3 = n2 | -3;
        }
        n2 = n3;
        if ((n & -4) == -4) {
            arrayList.add("PREF_RADIO_TECH_CHANGED");
            n2 = n3 | -4;
        }
        n3 = n2;
        if ((n & -5) == -5) {
            arrayList.add("RADIO_POWER_OFF");
            n3 = n2 | -5;
        }
        n2 = n3;
        if ((n & -6) == -6) {
            arrayList.add("TETHERED_CALL_ACTIVE");
            n2 = n3 | -6;
        }
        n3 = n2;
        if ((65535 & n) == 65535) {
            arrayList.add("ERROR_UNSPECIFIED");
            n3 = n2 | 65535;
        }
        n4 = n3;
        if ((n & 25) == 25) {
            arrayList.add("LLC_SNDCP");
            n4 = n3 | 25;
        }
        n2 = n4;
        if ((n & 48) == 48) {
            arrayList.add("ACTIVATION_REJECTED_BCM_VIOLATION");
            n2 = n4 | 48;
        }
        n3 = n2;
        if ((n & 56) == 56) {
            arrayList.add("COLLISION_WITH_NETWORK_INITIATED_REQUEST");
            n3 = n2 | 56;
        }
        n2 = n3;
        if ((n & 57) == 57) {
            arrayList.add("ONLY_IPV4V6_ALLOWED");
            n2 = n3 | 57;
        }
        n3 = n2;
        if ((n & 58) == 58) {
            arrayList.add("ONLY_NON_IP_ALLOWED");
            n3 = n2 | 58;
        }
        n2 = n3;
        if ((n & 59) == 59) {
            arrayList.add("UNSUPPORTED_QCI_VALUE");
            n2 = n3 | 59;
        }
        n4 = n2;
        if ((n & 60) == 60) {
            arrayList.add("BEARER_HANDLING_NOT_SUPPORTED");
            n4 = n2 | 60;
        }
        n3 = n4;
        if ((n & 123) == 123) {
            arrayList.add("INVALID_DNS_ADDR");
            n3 = n4 | 123;
        }
        n2 = n3;
        if ((n & 124) == 124) {
            arrayList.add("INVALID_PCSCF_OR_DNS_ADDRESS");
            n2 = n3 | 124;
        }
        n3 = n2;
        if ((n & 127) == 127) {
            arrayList.add("CALL_PREEMPT_BY_EMERGENCY_APN");
            n3 = n2 | 127;
        }
        n4 = n3;
        if ((n & 128) == 128) {
            arrayList.add("UE_INITIATED_DETACH_OR_DISCONNECT");
            n4 = n3 | 128;
        }
        n2 = n4;
        if ((n & 2000) == 2000) {
            arrayList.add("MIP_FA_REASON_UNSPECIFIED");
            n2 = n4 | 2000;
        }
        n4 = n2;
        if ((n & 2001) == 2001) {
            arrayList.add("MIP_FA_ADMIN_PROHIBITED");
            n4 = n2 | 2001;
        }
        n3 = n4;
        if ((n & 2002) == 2002) {
            arrayList.add("MIP_FA_INSUFFICIENT_RESOURCES");
            n3 = n4 | 2002;
        }
        n2 = n3;
        if ((n & 2003) == 2003) {
            arrayList.add("MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE");
            n2 = n3 | 2003;
        }
        n3 = n2;
        if ((n & 2004) == 2004) {
            arrayList.add("MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE");
            n3 = n2 | 2004;
        }
        n2 = n3;
        if ((n & 2005) == 2005) {
            arrayList.add("MIP_FA_REQUESTED_LIFETIME_TOO_LONG");
            n2 = n3 | 2005;
        }
        n3 = n2;
        if ((n & 2006) == 2006) {
            arrayList.add("MIP_FA_MALFORMED_REQUEST");
            n3 = n2 | 2006;
        }
        n4 = n3;
        if ((n & 2007) == 2007) {
            arrayList.add("MIP_FA_MALFORMED_REPLY");
            n4 = n3 | 2007;
        }
        n2 = n4;
        if ((n & 2008) == 2008) {
            arrayList.add("MIP_FA_ENCAPSULATION_UNAVAILABLE");
            n2 = n4 | 2008;
        }
        n3 = n2;
        if ((n & 2009) == 2009) {
            arrayList.add("MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE");
            n3 = n2 | 2009;
        }
        n4 = n3;
        if ((n & 2010) == 2010) {
            arrayList.add("MIP_FA_REVERSE_TUNNEL_UNAVAILABLE");
            n4 = n3 | 2010;
        }
        n2 = n4;
        if ((n & 2011) == 2011) {
            arrayList.add("MIP_FA_REVERSE_TUNNEL_IS_MANDATORY");
            n2 = n4 | 2011;
        }
        n3 = n2;
        if ((n & 2012) == 2012) {
            arrayList.add("MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED");
            n3 = n2 | 2012;
        }
        n4 = n3;
        if ((n & 2013) == 2013) {
            arrayList.add("MIP_FA_MISSING_NAI");
            n4 = n3 | 2013;
        }
        n2 = n4;
        if ((n & 2014) == 2014) {
            arrayList.add("MIP_FA_MISSING_HOME_AGENT");
            n2 = n4 | 2014;
        }
        n3 = n2;
        if ((n & 2015) == 2015) {
            arrayList.add("MIP_FA_MISSING_HOME_ADDRESS");
            n3 = n2 | 2015;
        }
        n2 = n3;
        if ((n & 2016) == 2016) {
            arrayList.add("MIP_FA_UNKNOWN_CHALLENGE");
            n2 = n3 | 2016;
        }
        n3 = n2;
        if ((n & 2017) == 2017) {
            arrayList.add("MIP_FA_MISSING_CHALLENGE");
            n3 = n2 | 2017;
        }
        n4 = n3;
        if ((n & 2018) == 2018) {
            arrayList.add("MIP_FA_STALE_CHALLENGE");
            n4 = n3 | 2018;
        }
        n2 = n4;
        if ((n & 2019) == 2019) {
            arrayList.add("MIP_HA_REASON_UNSPECIFIED");
            n2 = n4 | 2019;
        }
        n3 = n2;
        if ((n & 2020) == 2020) {
            arrayList.add("MIP_HA_ADMIN_PROHIBITED");
            n3 = n2 | 2020;
        }
        n2 = n3;
        if ((n & 2021) == 2021) {
            arrayList.add("MIP_HA_INSUFFICIENT_RESOURCES");
            n2 = n3 | 2021;
        }
        n3 = n2;
        if ((n & 2022) == 2022) {
            arrayList.add("MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE");
            n3 = n2 | 2022;
        }
        n4 = n3;
        if ((n & 2023) == 2023) {
            arrayList.add("MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE");
            n4 = n3 | 2023;
        }
        n2 = n4;
        if ((n & 2024) == 2024) {
            arrayList.add("MIP_HA_REGISTRATION_ID_MISMATCH");
            n2 = n4 | 2024;
        }
        n3 = n2;
        if ((n & 2025) == 2025) {
            arrayList.add("MIP_HA_MALFORMED_REQUEST");
            n3 = n2 | 2025;
        }
        n2 = n3;
        if ((n & 2026) == 2026) {
            arrayList.add("MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS");
            n2 = n3 | 2026;
        }
        n3 = n2;
        if ((n & 2027) == 2027) {
            arrayList.add("MIP_HA_REVERSE_TUNNEL_UNAVAILABLE");
            n3 = n2 | 2027;
        }
        n4 = n3;
        if ((n & 2028) == 2028) {
            arrayList.add("MIP_HA_REVERSE_TUNNEL_IS_MANDATORY");
            n4 = n3 | 2028;
        }
        n2 = n4;
        if ((n & 2029) == 2029) {
            arrayList.add("MIP_HA_ENCAPSULATION_UNAVAILABLE");
            n2 = n4 | 2029;
        }
        n3 = n2;
        if ((n & 2030) == 2030) {
            arrayList.add("CLOSE_IN_PROGRESS");
            n3 = n2 | 2030;
        }
        n2 = n3;
        if ((n & 2031) == 2031) {
            arrayList.add("NETWORK_INITIATED_TERMINATION");
            n2 = n3 | 2031;
        }
        n3 = n2;
        if ((n & 2032) == 2032) {
            arrayList.add("MODEM_APP_PREEMPTED");
            n3 = n2 | 2032;
        }
        n2 = n3;
        if ((n & 2033) == 2033) {
            arrayList.add("PDN_IPV4_CALL_DISALLOWED");
            n2 = n3 | 2033;
        }
        n3 = n2;
        if ((n & 2034) == 2034) {
            arrayList.add("PDN_IPV4_CALL_THROTTLED");
            n3 = n2 | 2034;
        }
        n2 = n3;
        if ((n & 2035) == 2035) {
            arrayList.add("PDN_IPV6_CALL_DISALLOWED");
            n2 = n3 | 2035;
        }
        n3 = n2;
        if ((n & 2036) == 2036) {
            arrayList.add("PDN_IPV6_CALL_THROTTLED");
            n3 = n2 | 2036;
        }
        n2 = n3;
        if ((n & 2037) == 2037) {
            arrayList.add("MODEM_RESTART");
            n2 = n3 | 2037;
        }
        n3 = n2;
        if ((n & 2038) == 2038) {
            arrayList.add("PDP_PPP_NOT_SUPPORTED");
            n3 = n2 | 2038;
        }
        n2 = n3;
        if ((n & 2039) == 2039) {
            arrayList.add("UNPREFERRED_RAT");
            n2 = n3 | 2039;
        }
        n3 = n2;
        if ((n & 2040) == 2040) {
            arrayList.add("PHYSICAL_LINK_CLOSE_IN_PROGRESS");
            n3 = n2 | 2040;
        }
        n2 = n3;
        if ((n & 2041) == 2041) {
            arrayList.add("APN_PENDING_HANDOVER");
            n2 = n3 | 2041;
        }
        n3 = n2;
        if ((n & 2042) == 2042) {
            arrayList.add("PROFILE_BEARER_INCOMPATIBLE");
            n3 = n2 | 2042;
        }
        n2 = n3;
        if ((n & 2043) == 2043) {
            arrayList.add("SIM_CARD_CHANGED");
            n2 = n3 | 2043;
        }
        n3 = n2;
        if ((n & 2044) == 2044) {
            arrayList.add("LOW_POWER_MODE_OR_POWERING_DOWN");
            n3 = n2 | 2044;
        }
        n2 = n3;
        if ((n & 2045) == 2045) {
            arrayList.add("APN_DISABLED");
            n2 = n3 | 2045;
        }
        n3 = n2;
        if ((n & 2046) == 2046) {
            arrayList.add("MAX_PPP_INACTIVITY_TIMER_EXPIRED");
            n3 = n2 | 2046;
        }
        n2 = n3;
        if ((n & 2047) == 2047) {
            arrayList.add("IPV6_ADDRESS_TRANSFER_FAILED");
            n2 = n3 | 2047;
        }
        n3 = n2;
        if ((n & 2048) == 2048) {
            arrayList.add("TRAT_SWAP_FAILED");
            n3 = n2 | 2048;
        }
        n2 = n3;
        if ((n & 2049) == 2049) {
            arrayList.add("EHRPD_TO_HRPD_FALLBACK");
            n2 = n3 | 2049;
        }
        n4 = n2;
        if ((n & 2050) == 2050) {
            arrayList.add("MIP_CONFIG_FAILURE");
            n4 = n2 | 2050;
        }
        n3 = n4;
        if ((n & 2051) == 2051) {
            arrayList.add("PDN_INACTIVITY_TIMER_EXPIRED");
            n3 = n4 | 2051;
        }
        n2 = n3;
        if ((n & 2052) == 2052) {
            arrayList.add("MAX_IPV4_CONNECTIONS");
            n2 = n3 | 2052;
        }
        n4 = n2;
        if ((n & 2053) == 2053) {
            arrayList.add("MAX_IPV6_CONNECTIONS");
            n4 = n2 | 2053;
        }
        n3 = n4;
        if ((n & 2054) == 2054) {
            arrayList.add("APN_MISMATCH");
            n3 = n4 | 2054;
        }
        n2 = n3;
        if ((n & 2055) == 2055) {
            arrayList.add("IP_VERSION_MISMATCH");
            n2 = n3 | 2055;
        }
        n3 = n2;
        if ((n & 2056) == 2056) {
            arrayList.add("DUN_CALL_DISALLOWED");
            n3 = n2 | 2056;
        }
        n4 = n3;
        if ((n & 2057) == 2057) {
            arrayList.add("INTERNAL_EPC_NONEPC_TRANSITION");
            n4 = n3 | 2057;
        }
        n2 = n4;
        if ((n & 2058) == 2058) {
            arrayList.add("INTERFACE_IN_USE");
            n2 = n4 | 2058;
        }
        n4 = n2;
        if ((n & 2059) == 2059) {
            arrayList.add("APN_DISALLOWED_ON_ROAMING");
            n4 = n2 | 2059;
        }
        n3 = n4;
        if ((n & 2060) == 2060) {
            arrayList.add("APN_PARAMETERS_CHANGED");
            n3 = n4 | 2060;
        }
        n2 = n3;
        if ((n & 2061) == 2061) {
            arrayList.add("NULL_APN_DISALLOWED");
            n2 = n3 | 2061;
        }
        n3 = n2;
        if ((n & 2062) == 2062) {
            arrayList.add("THERMAL_MITIGATION");
            n3 = n2 | 2062;
        }
        n2 = n3;
        if ((n & 2063) == 2063) {
            arrayList.add("DATA_SETTINGS_DISABLED");
            n2 = n3 | 2063;
        }
        n3 = n2;
        if ((n & 2064) == 2064) {
            arrayList.add("DATA_ROAMING_SETTINGS_DISABLED");
            n3 = n2 | 2064;
        }
        n2 = n3;
        if ((n & 2065) == 2065) {
            arrayList.add("DDS_SWITCHED");
            n2 = n3 | 2065;
        }
        n3 = n2;
        if ((n & 2066) == 2066) {
            arrayList.add("FORBIDDEN_APN_NAME");
            n3 = n2 | 2066;
        }
        n2 = n3;
        if ((n & 2067) == 2067) {
            arrayList.add("DDS_SWITCH_IN_PROGRESS");
            n2 = n3 | 2067;
        }
        n3 = n2;
        if ((n & 2068) == 2068) {
            arrayList.add("CALL_DISALLOWED_IN_ROAMING");
            n3 = n2 | 2068;
        }
        n2 = n3;
        if ((n & 2069) == 2069) {
            arrayList.add("NON_IP_NOT_SUPPORTED");
            n2 = n3 | 2069;
        }
        n3 = n2;
        if ((n & 2070) == 2070) {
            arrayList.add("PDN_NON_IP_CALL_THROTTLED");
            n3 = n2 | 2070;
        }
        n2 = n3;
        if ((n & 2071) == 2071) {
            arrayList.add("PDN_NON_IP_CALL_DISALLOWED");
            n2 = n3 | 2071;
        }
        n3 = n2;
        if ((n & 2072) == 2072) {
            arrayList.add("CDMA_LOCK");
            n3 = n2 | 2072;
        }
        n2 = n3;
        if ((n & 2073) == 2073) {
            arrayList.add("CDMA_INTERCEPT");
            n2 = n3 | 2073;
        }
        n3 = n2;
        if ((n & 2074) == 2074) {
            arrayList.add("CDMA_REORDER");
            n3 = n2 | 2074;
        }
        n4 = n3;
        if ((n & 2075) == 2075) {
            arrayList.add("CDMA_RELEASE_DUE_TO_SO_REJECTION");
            n4 = n3 | 2075;
        }
        n2 = n4;
        if ((n & 2076) == 2076) {
            arrayList.add("CDMA_INCOMING_CALL");
            n2 = n4 | 2076;
        }
        n3 = n2;
        if ((n & 2077) == 2077) {
            arrayList.add("CDMA_ALERT_STOP");
            n3 = n2 | 2077;
        }
        n2 = n3;
        if ((n & 2078) == 2078) {
            arrayList.add("CHANNEL_ACQUISITION_FAILURE");
            n2 = n3 | 2078;
        }
        n3 = n2;
        if ((n & 2079) == 2079) {
            arrayList.add("MAX_ACCESS_PROBE");
            n3 = n2 | 2079;
        }
        n2 = n3;
        if ((n & 2080) == 2080) {
            arrayList.add("CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION");
            n2 = n3 | 2080;
        }
        n3 = n2;
        if ((n & 2081) == 2081) {
            arrayList.add("NO_RESPONSE_FROM_BASE_STATION");
            n3 = n2 | 2081;
        }
        n4 = n3;
        if ((n & 2082) == 2082) {
            arrayList.add("REJECTED_BY_BASE_STATION");
            n4 = n3 | 2082;
        }
        n2 = n4;
        if ((n & 2083) == 2083) {
            arrayList.add("CONCURRENT_SERVICES_INCOMPATIBLE");
            n2 = n4 | 2083;
        }
        n4 = n2;
        if ((n & 2084) == 2084) {
            arrayList.add("NO_CDMA_SERVICE");
            n4 = n2 | 2084;
        }
        n3 = n4;
        if ((n & 2085) == 2085) {
            arrayList.add("RUIM_NOT_PRESENT");
            n3 = n4 | 2085;
        }
        n2 = n3;
        if ((n & 2086) == 2086) {
            arrayList.add("CDMA_RETRY_ORDER");
            n2 = n3 | 2086;
        }
        n3 = n2;
        if ((n & 2087) == 2087) {
            arrayList.add("ACCESS_BLOCK");
            n3 = n2 | 2087;
        }
        n4 = n3;
        if ((n & 2088) == 2088) {
            arrayList.add("ACCESS_BLOCK_ALL");
            n4 = n3 | 2088;
        }
        n2 = n4;
        if ((n & 2089) == 2089) {
            arrayList.add("IS707B_MAX_ACCESS_PROBES");
            n2 = n4 | 2089;
        }
        n3 = n2;
        if ((n & 2090) == 2090) {
            arrayList.add("THERMAL_EMERGENCY");
            n3 = n2 | 2090;
        }
        n2 = n3;
        if ((n & 2091) == 2091) {
            arrayList.add("CONCURRENT_SERVICES_NOT_ALLOWED");
            n2 = n3 | 2091;
        }
        n3 = n2;
        if ((n & 2092) == 2092) {
            arrayList.add("INCOMING_CALL_REJECTED");
            n3 = n2 | 2092;
        }
        n2 = n3;
        if ((n & 2093) == 2093) {
            arrayList.add("NO_SERVICE_ON_GATEWAY");
            n2 = n3 | 2093;
        }
        n3 = n2;
        if ((n & 2094) == 2094) {
            arrayList.add("NO_GPRS_CONTEXT");
            n3 = n2 | 2094;
        }
        n2 = n3;
        if ((n & 2095) == 2095) {
            arrayList.add("ILLEGAL_MS");
            n2 = n3 | 2095;
        }
        n3 = n2;
        if ((n & 2096) == 2096) {
            arrayList.add("ILLEGAL_ME");
            n3 = n2 | 2096;
        }
        n2 = n3;
        if ((n & 2097) == 2097) {
            arrayList.add("GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED");
            n2 = n3 | 2097;
        }
        n4 = n2;
        if ((n & 2098) == 2098) {
            arrayList.add("GPRS_SERVICES_NOT_ALLOWED");
            n4 = n2 | 2098;
        }
        n3 = n4;
        if ((n & 2099) == 2099) {
            arrayList.add("MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK");
            n3 = n4 | 2099;
        }
        n2 = n3;
        if ((n & 2100) == 2100) {
            arrayList.add("IMPLICITLY_DETACHED");
            n2 = n3 | 2100;
        }
        n3 = n2;
        if ((n & 2101) == 2101) {
            arrayList.add("PLMN_NOT_ALLOWED");
            n3 = n2 | 2101;
        }
        n4 = n3;
        if ((n & 2102) == 2102) {
            arrayList.add("LOCATION_AREA_NOT_ALLOWED");
            n4 = n3 | 2102;
        }
        n2 = n4;
        if ((n & 2103) == 2103) {
            arrayList.add("GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN");
            n2 = n4 | 2103;
        }
        n3 = n2;
        if ((n & 2104) == 2104) {
            arrayList.add("PDP_DUPLICATE");
            n3 = n2 | 2104;
        }
        n2 = n3;
        if ((n & 2105) == 2105) {
            arrayList.add("UE_RAT_CHANGE");
            n2 = n3 | 2105;
        }
        n3 = n2;
        if ((n & 2106) == 2106) {
            arrayList.add("CONGESTION");
            n3 = n2 | 2106;
        }
        n2 = n3;
        if ((n & 2107) == 2107) {
            arrayList.add("NO_PDP_CONTEXT_ACTIVATED");
            n2 = n3 | 2107;
        }
        n3 = n2;
        if ((n & 2108) == 2108) {
            arrayList.add("ACCESS_CLASS_DSAC_REJECTION");
            n3 = n2 | 2108;
        }
        n2 = n3;
        if ((n & 2109) == 2109) {
            arrayList.add("PDP_ACTIVATE_MAX_RETRY_FAILED");
            n2 = n3 | 2109;
        }
        n3 = n2;
        if ((n & 2110) == 2110) {
            arrayList.add("RADIO_ACCESS_BEARER_FAILURE");
            n3 = n2 | 2110;
        }
        n4 = n3;
        if ((n & 2111) == 2111) {
            arrayList.add("ESM_UNKNOWN_EPS_BEARER_CONTEXT");
            n4 = n3 | 2111;
        }
        n2 = n4;
        if ((n & 2112) == 2112) {
            arrayList.add("DRB_RELEASED_BY_RRC");
            n2 = n4 | 2112;
        }
        n3 = n2;
        if ((n & 2113) == 2113) {
            arrayList.add("CONNECTION_RELEASED");
            n3 = n2 | 2113;
        }
        n2 = n3;
        if ((n & 2114) == 2114) {
            arrayList.add("EMM_DETACHED");
            n2 = n3 | 2114;
        }
        n3 = n2;
        if ((n & 2115) == 2115) {
            arrayList.add("EMM_ATTACH_FAILED");
            n3 = n2 | 2115;
        }
        n2 = n3;
        if ((n & 2116) == 2116) {
            arrayList.add("EMM_ATTACH_STARTED");
            n2 = n3 | 2116;
        }
        n3 = n2;
        if ((n & 2117) == 2117) {
            arrayList.add("LTE_NAS_SERVICE_REQUEST_FAILED");
            n3 = n2 | 2117;
        }
        n2 = n3;
        if ((n & 2118) == 2118) {
            arrayList.add("DUPLICATE_BEARER_ID");
            n2 = n3 | 2118;
        }
        n4 = n2;
        if ((n & 2119) == 2119) {
            arrayList.add("ESM_COLLISION_SCENARIOS");
            n4 = n2 | 2119;
        }
        n3 = n4;
        if ((n & 2120) == 2120) {
            arrayList.add("ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK");
            n3 = n4 | 2120;
        }
        n2 = n3;
        if ((n & 2121) == 2121) {
            arrayList.add("ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER");
            n2 = n3 | 2121;
        }
        n3 = n2;
        if ((n & 2122) == 2122) {
            arrayList.add("ESM_BAD_OTA_MESSAGE");
            n3 = n2 | 2122;
        }
        n2 = n3;
        if ((n & 2123) == 2123) {
            arrayList.add("ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL");
            n2 = n3 | 2123;
        }
        n3 = n2;
        if ((n & 2124) == 2124) {
            arrayList.add("ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT");
            n3 = n2 | 2124;
        }
        n2 = n3;
        if ((n & 2125) == 2125) {
            arrayList.add("DS_EXPLICIT_DEACTIVATION");
            n2 = n3 | 2125;
        }
        n3 = n2;
        if ((n & 2126) == 2126) {
            arrayList.add("ESM_LOCAL_CAUSE_NONE");
            n3 = n2 | 2126;
        }
        n4 = n3;
        if ((n & 2127) == 2127) {
            arrayList.add("LTE_THROTTLING_NOT_REQUIRED");
            n4 = n3 | 2127;
        }
        n2 = n4;
        if ((n & 2128) == 2128) {
            arrayList.add("ACCESS_CONTROL_LIST_CHECK_FAILURE");
            n2 = n4 | 2128;
        }
        n3 = n2;
        if ((n & 2129) == 2129) {
            arrayList.add("SERVICE_NOT_ALLOWED_ON_PLMN");
            n3 = n2 | 2129;
        }
        n2 = n3;
        if ((n & 2130) == 2130) {
            arrayList.add("EMM_T3417_EXPIRED");
            n2 = n3 | 2130;
        }
        n3 = n2;
        if ((n & 2131) == 2131) {
            arrayList.add("EMM_T3417_EXT_EXPIRED");
            n3 = n2 | 2131;
        }
        n2 = n3;
        if ((n & 2132) == 2132) {
            arrayList.add("RRC_UPLINK_DATA_TRANSMISSION_FAILURE");
            n2 = n3 | 2132;
        }
        n3 = n2;
        if ((n & 2133) == 2133) {
            arrayList.add("RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER");
            n3 = n2 | 2133;
        }
        n2 = n3;
        if ((n & 2134) == 2134) {
            arrayList.add("RRC_UPLINK_CONNECTION_RELEASE");
            n2 = n3 | 2134;
        }
        n3 = n2;
        if ((n & 2135) == 2135) {
            arrayList.add("RRC_UPLINK_RADIO_LINK_FAILURE");
            n3 = n2 | 2135;
        }
        n2 = n3;
        if ((n & 2136) == 2136) {
            arrayList.add("RRC_UPLINK_ERROR_REQUEST_FROM_NAS");
            n2 = n3 | 2136;
        }
        n4 = n2;
        if ((n & 2137) == 2137) {
            arrayList.add("RRC_CONNECTION_ACCESS_STRATUM_FAILURE");
            n4 = n2 | 2137;
        }
        n3 = n4;
        if ((n & 2138) == 2138) {
            arrayList.add("RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS");
            n3 = n4 | 2138;
        }
        n2 = n3;
        if ((n & 2139) == 2139) {
            arrayList.add("RRC_CONNECTION_ACCESS_BARRED");
            n2 = n3 | 2139;
        }
        n3 = n2;
        if ((n & 2140) == 2140) {
            arrayList.add("RRC_CONNECTION_CELL_RESELECTION");
            n3 = n2 | 2140;
        }
        n4 = n3;
        if ((n & 2141) == 2141) {
            arrayList.add("RRC_CONNECTION_CONFIG_FAILURE");
            n4 = n3 | 2141;
        }
        n2 = n4;
        if ((n & 2142) == 2142) {
            arrayList.add("RRC_CONNECTION_TIMER_EXPIRED");
            n2 = n4 | 2142;
        }
        n3 = n2;
        if ((n & 2143) == 2143) {
            arrayList.add("RRC_CONNECTION_LINK_FAILURE");
            n3 = n2 | 2143;
        }
        n2 = n3;
        if ((n & 2144) == 2144) {
            arrayList.add("RRC_CONNECTION_CELL_NOT_CAMPED");
            n2 = n3 | 2144;
        }
        n3 = n2;
        if ((n & 2145) == 2145) {
            arrayList.add("RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE");
            n3 = n2 | 2145;
        }
        n2 = n3;
        if ((n & 2146) == 2146) {
            arrayList.add("RRC_CONNECTION_REJECT_BY_NETWORK");
            n2 = n3 | 2146;
        }
        n4 = n2;
        if ((n & 2147) == 2147) {
            arrayList.add("RRC_CONNECTION_NORMAL_RELEASE");
            n4 = n2 | 2147;
        }
        n3 = n4;
        if ((n & 2148) == 2148) {
            arrayList.add("RRC_CONNECTION_RADIO_LINK_FAILURE");
            n3 = n4 | 2148;
        }
        n2 = n3;
        if ((n & 2149) == 2149) {
            arrayList.add("RRC_CONNECTION_REESTABLISHMENT_FAILURE");
            n2 = n3 | 2149;
        }
        n3 = n2;
        if ((n & 2150) == 2150) {
            arrayList.add("RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER");
            n3 = n2 | 2150;
        }
        n2 = n3;
        if ((n & 2151) == 2151) {
            arrayList.add("RRC_CONNECTION_ABORT_REQUEST");
            n2 = n3 | 2151;
        }
        n3 = n2;
        if ((n & 2152) == 2152) {
            arrayList.add("RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR");
            n3 = n2 | 2152;
        }
        n4 = n3;
        if ((n & 2153) == 2153) {
            arrayList.add("NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH");
            n4 = n3 | 2153;
        }
        n2 = n4;
        if ((n & 2154) == 2154) {
            arrayList.add("NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH");
            n2 = n4 | 2154;
        }
        n3 = n2;
        if ((n & 2155) == 2155) {
            arrayList.add("ESM_PROCEDURE_TIME_OUT");
            n3 = n2 | 2155;
        }
        n2 = n3;
        if ((n & 2156) == 2156) {
            arrayList.add("INVALID_CONNECTION_ID");
            n2 = n3 | 2156;
        }
        n3 = n2;
        if ((n & 2157) == 2157) {
            arrayList.add("MAXIMIUM_NSAPIS_EXCEEDED");
            n3 = n2 | 2157;
        }
        n2 = n3;
        if ((n & 2158) == 2158) {
            arrayList.add("INVALID_PRIMARY_NSAPI");
            n2 = n3 | 2158;
        }
        n3 = n2;
        if ((n & 2159) == 2159) {
            arrayList.add("CANNOT_ENCODE_OTA_MESSAGE");
            n3 = n2 | 2159;
        }
        n2 = n3;
        if ((n & 2160) == 2160) {
            arrayList.add("RADIO_ACCESS_BEARER_SETUP_FAILURE");
            n2 = n3 | 2160;
        }
        n3 = n2;
        if ((n & 2161) == 2161) {
            arrayList.add("PDP_ESTABLISH_TIMEOUT_EXPIRED");
            n3 = n2 | 2161;
        }
        n2 = n3;
        if ((n & 2162) == 2162) {
            arrayList.add("PDP_MODIFY_TIMEOUT_EXPIRED");
            n2 = n3 | 2162;
        }
        n3 = n2;
        if ((n & 2163) == 2163) {
            arrayList.add("PDP_INACTIVE_TIMEOUT_EXPIRED");
            n3 = n2 | 2163;
        }
        n4 = n3;
        if ((n & 2164) == 2164) {
            arrayList.add("PDP_LOWERLAYER_ERROR");
            n4 = n3 | 2164;
        }
        n2 = n4;
        if ((n & 2165) == 2165) {
            arrayList.add("PDP_MODIFY_COLLISION");
            n2 = n4 | 2165;
        }
        n4 = n2;
        if ((n & 2166) == 2166) {
            arrayList.add("MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED");
            n4 = n2 | 2166;
        }
        n3 = n4;
        if ((n & 2167) == 2167) {
            arrayList.add("NAS_REQUEST_REJECTED_BY_NETWORK");
            n3 = n4 | 2167;
        }
        n2 = n3;
        if ((n & 2168) == 2168) {
            arrayList.add("RRC_CONNECTION_INVALID_REQUEST");
            n2 = n3 | 2168;
        }
        n3 = n2;
        if ((n & 2169) == 2169) {
            arrayList.add("RRC_CONNECTION_TRACKING_AREA_ID_CHANGED");
            n3 = n2 | 2169;
        }
        n2 = n3;
        if ((n & 2170) == 2170) {
            arrayList.add("RRC_CONNECTION_RF_UNAVAILABLE");
            n2 = n3 | 2170;
        }
        n3 = n2;
        if ((n & 2171) == 2171) {
            arrayList.add("RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE");
            n3 = n2 | 2171;
        }
        n2 = n3;
        if ((n & 2172) == 2172) {
            arrayList.add("RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE");
            n2 = n3 | 2172;
        }
        n3 = n2;
        if ((n & 2173) == 2173) {
            arrayList.add("RRC_CONNECTION_ABORTED_AFTER_HANDOVER");
            n3 = n2 | 2173;
        }
        n2 = n3;
        if ((n & 2174) == 2174) {
            arrayList.add("RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE");
            n2 = n3 | 2174;
        }
        n3 = n2;
        if ((n & 2175) == 2175) {
            arrayList.add("RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE");
            n3 = n2 | 2175;
        }
        n4 = n3;
        if ((n & 2176) == 2176) {
            arrayList.add("IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER");
            n4 = n3 | 2176;
        }
        n2 = n4;
        if ((n & 2177) == 2177) {
            arrayList.add("IMEI_NOT_ACCEPTED");
            n2 = n4 | 2177;
        }
        n3 = n2;
        if ((n & 2178) == 2178) {
            arrayList.add("EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED");
            n3 = n2 | 2178;
        }
        n2 = n3;
        if ((n & 2179) == 2179) {
            arrayList.add("EPS_SERVICES_NOT_ALLOWED_IN_PLMN");
            n2 = n3 | 2179;
        }
        n3 = n2;
        if ((n & 2180) == 2180) {
            arrayList.add("MSC_TEMPORARILY_NOT_REACHABLE");
            n3 = n2 | 2180;
        }
        n2 = n3;
        if ((n & 2181) == 2181) {
            arrayList.add("CS_DOMAIN_NOT_AVAILABLE");
            n2 = n3 | 2181;
        }
        n3 = n2;
        if ((n & 2182) == 2182) {
            arrayList.add("ESM_FAILURE");
            n3 = n2 | 2182;
        }
        n2 = n3;
        if ((n & 2183) == 2183) {
            arrayList.add("MAC_FAILURE");
            n2 = n3 | 2183;
        }
        n3 = n2;
        if ((n & 2184) == 2184) {
            arrayList.add("SYNCHRONIZATION_FAILURE");
            n3 = n2 | 2184;
        }
        n2 = n3;
        if ((n & 2185) == 2185) {
            arrayList.add("UE_SECURITY_CAPABILITIES_MISMATCH");
            n2 = n3 | 2185;
        }
        n3 = n2;
        if ((n & 2186) == 2186) {
            arrayList.add("SECURITY_MODE_REJECTED");
            n3 = n2 | 2186;
        }
        n4 = n3;
        if ((n & 2187) == 2187) {
            arrayList.add("UNACCEPTABLE_NON_EPS_AUTHENTICATION");
            n4 = n3 | 2187;
        }
        n2 = n4;
        if ((n & 2188) == 2188) {
            arrayList.add("CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED");
            n2 = n4 | 2188;
        }
        n3 = n2;
        if ((n & 2189) == 2189) {
            arrayList.add("NO_EPS_BEARER_CONTEXT_ACTIVATED");
            n3 = n2 | 2189;
        }
        n2 = n3;
        if ((n & 2190) == 2190) {
            arrayList.add("INVALID_EMM_STATE");
            n2 = n3 | 2190;
        }
        n4 = n2;
        if ((n & 2191) == 2191) {
            arrayList.add("NAS_LAYER_FAILURE");
            n4 = n2 | 2191;
        }
        n3 = n4;
        if ((n & 2192) == 2192) {
            arrayList.add("MULTIPLE_PDP_CALL_NOT_ALLOWED");
            n3 = n4 | 2192;
        }
        n2 = n3;
        if ((n & 2193) == 2193) {
            arrayList.add("EMBMS_NOT_ENABLED");
            n2 = n3 | 2193;
        }
        n3 = n2;
        if ((n & 2194) == 2194) {
            arrayList.add("IRAT_HANDOVER_FAILED");
            n3 = n2 | 2194;
        }
        n4 = n3;
        if ((n & 2195) == 2195) {
            arrayList.add("EMBMS_REGULAR_DEACTIVATION");
            n4 = n3 | 2195;
        }
        n2 = n4;
        if ((n & 2196) == 2196) {
            arrayList.add("TEST_LOOPBACK_REGULAR_DEACTIVATION");
            n2 = n4 | 2196;
        }
        n3 = n2;
        if ((n & 2197) == 2197) {
            arrayList.add("LOWER_LAYER_REGISTRATION_FAILURE");
            n3 = n2 | 2197;
        }
        n4 = n3;
        if ((n & 2198) == 2198) {
            arrayList.add("DATA_PLAN_EXPIRED");
            n4 = n3 | 2198;
        }
        n2 = n4;
        if ((n & 2199) == 2199) {
            arrayList.add("UMTS_HANDOVER_TO_IWLAN");
            n2 = n4 | 2199;
        }
        n3 = n2;
        if ((n & 2200) == 2200) {
            arrayList.add("EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY");
            n3 = n2 | 2200;
        }
        n2 = n3;
        if ((n & 2201) == 2201) {
            arrayList.add("EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE");
            n2 = n3 | 2201;
        }
        n3 = n2;
        if ((n & 2202) == 2202) {
            arrayList.add("EVDO_HDR_CHANGED");
            n3 = n2 | 2202;
        }
        n2 = n3;
        if ((n & 2203) == 2203) {
            arrayList.add("EVDO_HDR_EXITED");
            n2 = n3 | 2203;
        }
        n3 = n2;
        if ((n & 2204) == 2204) {
            arrayList.add("EVDO_HDR_NO_SESSION");
            n3 = n2 | 2204;
        }
        n2 = n3;
        if ((n & 2205) == 2205) {
            arrayList.add("EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL");
            n2 = n3 | 2205;
        }
        n3 = n2;
        if ((n & 2206) == 2206) {
            arrayList.add("EVDO_HDR_CONNECTION_SETUP_TIMEOUT");
            n3 = n2 | 2206;
        }
        n2 = n3;
        if ((n & 2207) == 2207) {
            arrayList.add("FAILED_TO_ACQUIRE_COLOCATED_HDR");
            n2 = n3 | 2207;
        }
        n3 = n2;
        if ((n & 2208) == 2208) {
            arrayList.add("OTASP_COMMIT_IN_PROGRESS");
            n3 = n2 | 2208;
        }
        n2 = n3;
        if ((n & 2209) == 2209) {
            arrayList.add("NO_HYBRID_HDR_SERVICE");
            n2 = n3 | 2209;
        }
        n3 = n2;
        if ((n & 2210) == 2210) {
            arrayList.add("HDR_NO_LOCK_GRANTED");
            n3 = n2 | 2210;
        }
        n2 = n3;
        if ((n & 2211) == 2211) {
            arrayList.add("DBM_OR_SMS_IN_PROGRESS");
            n2 = n3 | 2211;
        }
        n3 = n2;
        if ((n & 2212) == 2212) {
            arrayList.add("HDR_FADE");
            n3 = n2 | 2212;
        }
        n2 = n3;
        if ((n & 2213) == 2213) {
            arrayList.add("HDR_ACCESS_FAILURE");
            n2 = n3 | 2213;
        }
        n3 = n2;
        if ((n & 2214) == 2214) {
            arrayList.add("UNSUPPORTED_1X_PREV");
            n3 = n2 | 2214;
        }
        n4 = n3;
        if ((n & 2215) == 2215) {
            arrayList.add("LOCAL_END");
            n4 = n3 | 2215;
        }
        n2 = n4;
        if ((n & 2216) == 2216) {
            arrayList.add("NO_SERVICE");
            n2 = n4 | 2216;
        }
        n3 = n2;
        if ((n & 2217) == 2217) {
            arrayList.add("FADE");
            n3 = n2 | 2217;
        }
        n2 = n3;
        if ((n & 2218) == 2218) {
            arrayList.add("NORMAL_RELEASE");
            n2 = n3 | 2218;
        }
        n4 = n2;
        if ((n & 2219) == 2219) {
            arrayList.add("ACCESS_ATTEMPT_ALREADY_IN_PROGRESS");
            n4 = n2 | 2219;
        }
        n3 = n4;
        if ((n & 2220) == 2220) {
            arrayList.add("REDIRECTION_OR_HANDOFF_IN_PROGRESS");
            n3 = n4 | 2220;
        }
        n2 = n3;
        if ((n & 2221) == 2221) {
            arrayList.add("EMERGENCY_MODE");
            n2 = n3 | 2221;
        }
        n3 = n2;
        if ((n & 2222) == 2222) {
            arrayList.add("PHONE_IN_USE");
            n3 = n2 | 2222;
        }
        n2 = n3;
        if ((n & 2223) == 2223) {
            arrayList.add("INVALID_MODE");
            n2 = n3 | 2223;
        }
        n4 = n2;
        if ((n & 2224) == 2224) {
            arrayList.add("INVALID_SIM_STATE");
            n4 = n2 | 2224;
        }
        n3 = n4;
        if ((n & 2225) == 2225) {
            arrayList.add("NO_COLLOCATED_HDR");
            n3 = n4 | 2225;
        }
        n2 = n3;
        if ((n & 2226) == 2226) {
            arrayList.add("UE_IS_ENTERING_POWERSAVE_MODE");
            n2 = n3 | 2226;
        }
        n4 = n2;
        if ((n & 2227) == 2227) {
            arrayList.add("DUAL_SWITCH");
            n4 = n2 | 2227;
        }
        n3 = n4;
        if ((n & 2228) == 2228) {
            arrayList.add("PPP_TIMEOUT");
            n3 = n4 | 2228;
        }
        n2 = n3;
        if ((n & 2229) == 2229) {
            arrayList.add("PPP_AUTH_FAILURE");
            n2 = n3 | 2229;
        }
        n3 = n2;
        if ((n & 2230) == 2230) {
            arrayList.add("PPP_OPTION_MISMATCH");
            n3 = n2 | 2230;
        }
        n2 = n3;
        if ((n & 2231) == 2231) {
            arrayList.add("PPP_PAP_FAILURE");
            n2 = n3 | 2231;
        }
        n3 = n2;
        if ((n & 2232) == 2232) {
            arrayList.add("PPP_CHAP_FAILURE");
            n3 = n2 | 2232;
        }
        n2 = n3;
        if ((n & 2233) == 2233) {
            arrayList.add("PPP_CLOSE_IN_PROGRESS");
            n2 = n3 | 2233;
        }
        n3 = n2;
        if ((n & 2234) == 2234) {
            arrayList.add("LIMITED_TO_IPV4");
            n3 = n2 | 2234;
        }
        n2 = n3;
        if ((n & 2235) == 2235) {
            arrayList.add("LIMITED_TO_IPV6");
            n2 = n3 | 2235;
        }
        n3 = n2;
        if ((n & 2236) == 2236) {
            arrayList.add("VSNCP_TIMEOUT");
            n3 = n2 | 2236;
        }
        n2 = n3;
        if ((n & 2237) == 2237) {
            arrayList.add("VSNCP_GEN_ERROR");
            n2 = n3 | 2237;
        }
        n3 = n2;
        if ((n & 2238) == 2238) {
            arrayList.add("VSNCP_APN_UNATHORIZED");
            n3 = n2 | 2238;
        }
        n4 = n3;
        if ((n & 2239) == 2239) {
            arrayList.add("VSNCP_PDN_LIMIT_EXCEEDED");
            n4 = n3 | 2239;
        }
        n2 = n4;
        if ((n & 2240) == 2240) {
            arrayList.add("VSNCP_NO_PDN_GATEWAY_ADDRESS");
            n2 = n4 | 2240;
        }
        n3 = n2;
        if ((n & 2241) == 2241) {
            arrayList.add("VSNCP_PDN_GATEWAY_UNREACHABLE");
            n3 = n2 | 2241;
        }
        n2 = n3;
        if ((n & 2242) == 2242) {
            arrayList.add("VSNCP_PDN_GATEWAY_REJECT");
            n2 = n3 | 2242;
        }
        n3 = n2;
        if ((n & 2243) == 2243) {
            arrayList.add("VSNCP_INSUFFICIENT_PARAMETERS");
            n3 = n2 | 2243;
        }
        n2 = n3;
        if ((n & 2244) == 2244) {
            arrayList.add("VSNCP_RESOURCE_UNAVAILABLE");
            n2 = n3 | 2244;
        }
        n3 = n2;
        if ((n & 2245) == 2245) {
            arrayList.add("VSNCP_ADMINISTRATIVELY_PROHIBITED");
            n3 = n2 | 2245;
        }
        n4 = n3;
        if ((n & 2246) == 2246) {
            arrayList.add("VSNCP_PDN_ID_IN_USE");
            n4 = n3 | 2246;
        }
        n2 = n4;
        if ((n & 2247) == 2247) {
            arrayList.add("VSNCP_SUBSCRIBER_LIMITATION");
            n2 = n4 | 2247;
        }
        n3 = n2;
        if ((n & 2248) == 2248) {
            arrayList.add("VSNCP_PDN_EXISTS_FOR_THIS_APN");
            n3 = n2 | 2248;
        }
        n2 = n3;
        if ((n & 2249) == 2249) {
            arrayList.add("VSNCP_RECONNECT_NOT_ALLOWED");
            n2 = n3 | 2249;
        }
        n3 = n2;
        if ((n & 2250) == 2250) {
            arrayList.add("IPV6_PREFIX_UNAVAILABLE");
            n3 = n2 | 2250;
        }
        n2 = n3;
        if ((n & 2251) == 2251) {
            arrayList.add("HANDOFF_PREFERENCE_CHANGED");
            n2 = n3 | 2251;
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
        if (n == 25) {
            return "LLC_SNDCP";
        }
        if (n == 48) {
            return "ACTIVATION_REJECTED_BCM_VIOLATION";
        }
        if (n == 56) {
            return "COLLISION_WITH_NETWORK_INITIATED_REQUEST";
        }
        if (n == 57) {
            return "ONLY_IPV4V6_ALLOWED";
        }
        if (n == 58) {
            return "ONLY_NON_IP_ALLOWED";
        }
        if (n == 59) {
            return "UNSUPPORTED_QCI_VALUE";
        }
        if (n == 60) {
            return "BEARER_HANDLING_NOT_SUPPORTED";
        }
        if (n == 123) {
            return "INVALID_DNS_ADDR";
        }
        if (n == 124) {
            return "INVALID_PCSCF_OR_DNS_ADDRESS";
        }
        if (n == 127) {
            return "CALL_PREEMPT_BY_EMERGENCY_APN";
        }
        if (n == 128) {
            return "UE_INITIATED_DETACH_OR_DISCONNECT";
        }
        if (n == 2000) {
            return "MIP_FA_REASON_UNSPECIFIED";
        }
        if (n == 2001) {
            return "MIP_FA_ADMIN_PROHIBITED";
        }
        if (n == 2002) {
            return "MIP_FA_INSUFFICIENT_RESOURCES";
        }
        if (n == 2003) {
            return "MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE";
        }
        if (n == 2004) {
            return "MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE";
        }
        if (n == 2005) {
            return "MIP_FA_REQUESTED_LIFETIME_TOO_LONG";
        }
        if (n == 2006) {
            return "MIP_FA_MALFORMED_REQUEST";
        }
        if (n == 2007) {
            return "MIP_FA_MALFORMED_REPLY";
        }
        if (n == 2008) {
            return "MIP_FA_ENCAPSULATION_UNAVAILABLE";
        }
        if (n == 2009) {
            return "MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE";
        }
        if (n == 2010) {
            return "MIP_FA_REVERSE_TUNNEL_UNAVAILABLE";
        }
        if (n == 2011) {
            return "MIP_FA_REVERSE_TUNNEL_IS_MANDATORY";
        }
        if (n == 2012) {
            return "MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED";
        }
        if (n == 2013) {
            return "MIP_FA_MISSING_NAI";
        }
        if (n == 2014) {
            return "MIP_FA_MISSING_HOME_AGENT";
        }
        if (n == 2015) {
            return "MIP_FA_MISSING_HOME_ADDRESS";
        }
        if (n == 2016) {
            return "MIP_FA_UNKNOWN_CHALLENGE";
        }
        if (n == 2017) {
            return "MIP_FA_MISSING_CHALLENGE";
        }
        if (n == 2018) {
            return "MIP_FA_STALE_CHALLENGE";
        }
        if (n == 2019) {
            return "MIP_HA_REASON_UNSPECIFIED";
        }
        if (n == 2020) {
            return "MIP_HA_ADMIN_PROHIBITED";
        }
        if (n == 2021) {
            return "MIP_HA_INSUFFICIENT_RESOURCES";
        }
        if (n == 2022) {
            return "MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE";
        }
        if (n == 2023) {
            return "MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE";
        }
        if (n == 2024) {
            return "MIP_HA_REGISTRATION_ID_MISMATCH";
        }
        if (n == 2025) {
            return "MIP_HA_MALFORMED_REQUEST";
        }
        if (n == 2026) {
            return "MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS";
        }
        if (n == 2027) {
            return "MIP_HA_REVERSE_TUNNEL_UNAVAILABLE";
        }
        if (n == 2028) {
            return "MIP_HA_REVERSE_TUNNEL_IS_MANDATORY";
        }
        if (n == 2029) {
            return "MIP_HA_ENCAPSULATION_UNAVAILABLE";
        }
        if (n == 2030) {
            return "CLOSE_IN_PROGRESS";
        }
        if (n == 2031) {
            return "NETWORK_INITIATED_TERMINATION";
        }
        if (n == 2032) {
            return "MODEM_APP_PREEMPTED";
        }
        if (n == 2033) {
            return "PDN_IPV4_CALL_DISALLOWED";
        }
        if (n == 2034) {
            return "PDN_IPV4_CALL_THROTTLED";
        }
        if (n == 2035) {
            return "PDN_IPV6_CALL_DISALLOWED";
        }
        if (n == 2036) {
            return "PDN_IPV6_CALL_THROTTLED";
        }
        if (n == 2037) {
            return "MODEM_RESTART";
        }
        if (n == 2038) {
            return "PDP_PPP_NOT_SUPPORTED";
        }
        if (n == 2039) {
            return "UNPREFERRED_RAT";
        }
        if (n == 2040) {
            return "PHYSICAL_LINK_CLOSE_IN_PROGRESS";
        }
        if (n == 2041) {
            return "APN_PENDING_HANDOVER";
        }
        if (n == 2042) {
            return "PROFILE_BEARER_INCOMPATIBLE";
        }
        if (n == 2043) {
            return "SIM_CARD_CHANGED";
        }
        if (n == 2044) {
            return "LOW_POWER_MODE_OR_POWERING_DOWN";
        }
        if (n == 2045) {
            return "APN_DISABLED";
        }
        if (n == 2046) {
            return "MAX_PPP_INACTIVITY_TIMER_EXPIRED";
        }
        if (n == 2047) {
            return "IPV6_ADDRESS_TRANSFER_FAILED";
        }
        if (n == 2048) {
            return "TRAT_SWAP_FAILED";
        }
        if (n == 2049) {
            return "EHRPD_TO_HRPD_FALLBACK";
        }
        if (n == 2050) {
            return "MIP_CONFIG_FAILURE";
        }
        if (n == 2051) {
            return "PDN_INACTIVITY_TIMER_EXPIRED";
        }
        if (n == 2052) {
            return "MAX_IPV4_CONNECTIONS";
        }
        if (n == 2053) {
            return "MAX_IPV6_CONNECTIONS";
        }
        if (n == 2054) {
            return "APN_MISMATCH";
        }
        if (n == 2055) {
            return "IP_VERSION_MISMATCH";
        }
        if (n == 2056) {
            return "DUN_CALL_DISALLOWED";
        }
        if (n == 2057) {
            return "INTERNAL_EPC_NONEPC_TRANSITION";
        }
        if (n == 2058) {
            return "INTERFACE_IN_USE";
        }
        if (n == 2059) {
            return "APN_DISALLOWED_ON_ROAMING";
        }
        if (n == 2060) {
            return "APN_PARAMETERS_CHANGED";
        }
        if (n == 2061) {
            return "NULL_APN_DISALLOWED";
        }
        if (n == 2062) {
            return "THERMAL_MITIGATION";
        }
        if (n == 2063) {
            return "DATA_SETTINGS_DISABLED";
        }
        if (n == 2064) {
            return "DATA_ROAMING_SETTINGS_DISABLED";
        }
        if (n == 2065) {
            return "DDS_SWITCHED";
        }
        if (n == 2066) {
            return "FORBIDDEN_APN_NAME";
        }
        if (n == 2067) {
            return "DDS_SWITCH_IN_PROGRESS";
        }
        if (n == 2068) {
            return "CALL_DISALLOWED_IN_ROAMING";
        }
        if (n == 2069) {
            return "NON_IP_NOT_SUPPORTED";
        }
        if (n == 2070) {
            return "PDN_NON_IP_CALL_THROTTLED";
        }
        if (n == 2071) {
            return "PDN_NON_IP_CALL_DISALLOWED";
        }
        if (n == 2072) {
            return "CDMA_LOCK";
        }
        if (n == 2073) {
            return "CDMA_INTERCEPT";
        }
        if (n == 2074) {
            return "CDMA_REORDER";
        }
        if (n == 2075) {
            return "CDMA_RELEASE_DUE_TO_SO_REJECTION";
        }
        if (n == 2076) {
            return "CDMA_INCOMING_CALL";
        }
        if (n == 2077) {
            return "CDMA_ALERT_STOP";
        }
        if (n == 2078) {
            return "CHANNEL_ACQUISITION_FAILURE";
        }
        if (n == 2079) {
            return "MAX_ACCESS_PROBE";
        }
        if (n == 2080) {
            return "CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION";
        }
        if (n == 2081) {
            return "NO_RESPONSE_FROM_BASE_STATION";
        }
        if (n == 2082) {
            return "REJECTED_BY_BASE_STATION";
        }
        if (n == 2083) {
            return "CONCURRENT_SERVICES_INCOMPATIBLE";
        }
        if (n == 2084) {
            return "NO_CDMA_SERVICE";
        }
        if (n == 2085) {
            return "RUIM_NOT_PRESENT";
        }
        if (n == 2086) {
            return "CDMA_RETRY_ORDER";
        }
        if (n == 2087) {
            return "ACCESS_BLOCK";
        }
        if (n == 2088) {
            return "ACCESS_BLOCK_ALL";
        }
        if (n == 2089) {
            return "IS707B_MAX_ACCESS_PROBES";
        }
        if (n == 2090) {
            return "THERMAL_EMERGENCY";
        }
        if (n == 2091) {
            return "CONCURRENT_SERVICES_NOT_ALLOWED";
        }
        if (n == 2092) {
            return "INCOMING_CALL_REJECTED";
        }
        if (n == 2093) {
            return "NO_SERVICE_ON_GATEWAY";
        }
        if (n == 2094) {
            return "NO_GPRS_CONTEXT";
        }
        if (n == 2095) {
            return "ILLEGAL_MS";
        }
        if (n == 2096) {
            return "ILLEGAL_ME";
        }
        if (n == 2097) {
            return "GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED";
        }
        if (n == 2098) {
            return "GPRS_SERVICES_NOT_ALLOWED";
        }
        if (n == 2099) {
            return "MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK";
        }
        if (n == 2100) {
            return "IMPLICITLY_DETACHED";
        }
        if (n == 2101) {
            return "PLMN_NOT_ALLOWED";
        }
        if (n == 2102) {
            return "LOCATION_AREA_NOT_ALLOWED";
        }
        if (n == 2103) {
            return "GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN";
        }
        if (n == 2104) {
            return "PDP_DUPLICATE";
        }
        if (n == 2105) {
            return "UE_RAT_CHANGE";
        }
        if (n == 2106) {
            return "CONGESTION";
        }
        if (n == 2107) {
            return "NO_PDP_CONTEXT_ACTIVATED";
        }
        if (n == 2108) {
            return "ACCESS_CLASS_DSAC_REJECTION";
        }
        if (n == 2109) {
            return "PDP_ACTIVATE_MAX_RETRY_FAILED";
        }
        if (n == 2110) {
            return "RADIO_ACCESS_BEARER_FAILURE";
        }
        if (n == 2111) {
            return "ESM_UNKNOWN_EPS_BEARER_CONTEXT";
        }
        if (n == 2112) {
            return "DRB_RELEASED_BY_RRC";
        }
        if (n == 2113) {
            return "CONNECTION_RELEASED";
        }
        if (n == 2114) {
            return "EMM_DETACHED";
        }
        if (n == 2115) {
            return "EMM_ATTACH_FAILED";
        }
        if (n == 2116) {
            return "EMM_ATTACH_STARTED";
        }
        if (n == 2117) {
            return "LTE_NAS_SERVICE_REQUEST_FAILED";
        }
        if (n == 2118) {
            return "DUPLICATE_BEARER_ID";
        }
        if (n == 2119) {
            return "ESM_COLLISION_SCENARIOS";
        }
        if (n == 2120) {
            return "ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK";
        }
        if (n == 2121) {
            return "ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER";
        }
        if (n == 2122) {
            return "ESM_BAD_OTA_MESSAGE";
        }
        if (n == 2123) {
            return "ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL";
        }
        if (n == 2124) {
            return "ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT";
        }
        if (n == 2125) {
            return "DS_EXPLICIT_DEACTIVATION";
        }
        if (n == 2126) {
            return "ESM_LOCAL_CAUSE_NONE";
        }
        if (n == 2127) {
            return "LTE_THROTTLING_NOT_REQUIRED";
        }
        if (n == 2128) {
            return "ACCESS_CONTROL_LIST_CHECK_FAILURE";
        }
        if (n == 2129) {
            return "SERVICE_NOT_ALLOWED_ON_PLMN";
        }
        if (n == 2130) {
            return "EMM_T3417_EXPIRED";
        }
        if (n == 2131) {
            return "EMM_T3417_EXT_EXPIRED";
        }
        if (n == 2132) {
            return "RRC_UPLINK_DATA_TRANSMISSION_FAILURE";
        }
        if (n == 2133) {
            return "RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER";
        }
        if (n == 2134) {
            return "RRC_UPLINK_CONNECTION_RELEASE";
        }
        if (n == 2135) {
            return "RRC_UPLINK_RADIO_LINK_FAILURE";
        }
        if (n == 2136) {
            return "RRC_UPLINK_ERROR_REQUEST_FROM_NAS";
        }
        if (n == 2137) {
            return "RRC_CONNECTION_ACCESS_STRATUM_FAILURE";
        }
        if (n == 2138) {
            return "RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS";
        }
        if (n == 2139) {
            return "RRC_CONNECTION_ACCESS_BARRED";
        }
        if (n == 2140) {
            return "RRC_CONNECTION_CELL_RESELECTION";
        }
        if (n == 2141) {
            return "RRC_CONNECTION_CONFIG_FAILURE";
        }
        if (n == 2142) {
            return "RRC_CONNECTION_TIMER_EXPIRED";
        }
        if (n == 2143) {
            return "RRC_CONNECTION_LINK_FAILURE";
        }
        if (n == 2144) {
            return "RRC_CONNECTION_CELL_NOT_CAMPED";
        }
        if (n == 2145) {
            return "RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE";
        }
        if (n == 2146) {
            return "RRC_CONNECTION_REJECT_BY_NETWORK";
        }
        if (n == 2147) {
            return "RRC_CONNECTION_NORMAL_RELEASE";
        }
        if (n == 2148) {
            return "RRC_CONNECTION_RADIO_LINK_FAILURE";
        }
        if (n == 2149) {
            return "RRC_CONNECTION_REESTABLISHMENT_FAILURE";
        }
        if (n == 2150) {
            return "RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER";
        }
        if (n == 2151) {
            return "RRC_CONNECTION_ABORT_REQUEST";
        }
        if (n == 2152) {
            return "RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR";
        }
        if (n == 2153) {
            return "NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH";
        }
        if (n == 2154) {
            return "NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH";
        }
        if (n == 2155) {
            return "ESM_PROCEDURE_TIME_OUT";
        }
        if (n == 2156) {
            return "INVALID_CONNECTION_ID";
        }
        if (n == 2157) {
            return "MAXIMIUM_NSAPIS_EXCEEDED";
        }
        if (n == 2158) {
            return "INVALID_PRIMARY_NSAPI";
        }
        if (n == 2159) {
            return "CANNOT_ENCODE_OTA_MESSAGE";
        }
        if (n == 2160) {
            return "RADIO_ACCESS_BEARER_SETUP_FAILURE";
        }
        if (n == 2161) {
            return "PDP_ESTABLISH_TIMEOUT_EXPIRED";
        }
        if (n == 2162) {
            return "PDP_MODIFY_TIMEOUT_EXPIRED";
        }
        if (n == 2163) {
            return "PDP_INACTIVE_TIMEOUT_EXPIRED";
        }
        if (n == 2164) {
            return "PDP_LOWERLAYER_ERROR";
        }
        if (n == 2165) {
            return "PDP_MODIFY_COLLISION";
        }
        if (n == 2166) {
            return "MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED";
        }
        if (n == 2167) {
            return "NAS_REQUEST_REJECTED_BY_NETWORK";
        }
        if (n == 2168) {
            return "RRC_CONNECTION_INVALID_REQUEST";
        }
        if (n == 2169) {
            return "RRC_CONNECTION_TRACKING_AREA_ID_CHANGED";
        }
        if (n == 2170) {
            return "RRC_CONNECTION_RF_UNAVAILABLE";
        }
        if (n == 2171) {
            return "RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE";
        }
        if (n == 2172) {
            return "RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE";
        }
        if (n == 2173) {
            return "RRC_CONNECTION_ABORTED_AFTER_HANDOVER";
        }
        if (n == 2174) {
            return "RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE";
        }
        if (n == 2175) {
            return "RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE";
        }
        if (n == 2176) {
            return "IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER";
        }
        if (n == 2177) {
            return "IMEI_NOT_ACCEPTED";
        }
        if (n == 2178) {
            return "EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED";
        }
        if (n == 2179) {
            return "EPS_SERVICES_NOT_ALLOWED_IN_PLMN";
        }
        if (n == 2180) {
            return "MSC_TEMPORARILY_NOT_REACHABLE";
        }
        if (n == 2181) {
            return "CS_DOMAIN_NOT_AVAILABLE";
        }
        if (n == 2182) {
            return "ESM_FAILURE";
        }
        if (n == 2183) {
            return "MAC_FAILURE";
        }
        if (n == 2184) {
            return "SYNCHRONIZATION_FAILURE";
        }
        if (n == 2185) {
            return "UE_SECURITY_CAPABILITIES_MISMATCH";
        }
        if (n == 2186) {
            return "SECURITY_MODE_REJECTED";
        }
        if (n == 2187) {
            return "UNACCEPTABLE_NON_EPS_AUTHENTICATION";
        }
        if (n == 2188) {
            return "CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED";
        }
        if (n == 2189) {
            return "NO_EPS_BEARER_CONTEXT_ACTIVATED";
        }
        if (n == 2190) {
            return "INVALID_EMM_STATE";
        }
        if (n == 2191) {
            return "NAS_LAYER_FAILURE";
        }
        if (n == 2192) {
            return "MULTIPLE_PDP_CALL_NOT_ALLOWED";
        }
        if (n == 2193) {
            return "EMBMS_NOT_ENABLED";
        }
        if (n == 2194) {
            return "IRAT_HANDOVER_FAILED";
        }
        if (n == 2195) {
            return "EMBMS_REGULAR_DEACTIVATION";
        }
        if (n == 2196) {
            return "TEST_LOOPBACK_REGULAR_DEACTIVATION";
        }
        if (n == 2197) {
            return "LOWER_LAYER_REGISTRATION_FAILURE";
        }
        if (n == 2198) {
            return "DATA_PLAN_EXPIRED";
        }
        if (n == 2199) {
            return "UMTS_HANDOVER_TO_IWLAN";
        }
        if (n == 2200) {
            return "EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY";
        }
        if (n == 2201) {
            return "EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE";
        }
        if (n == 2202) {
            return "EVDO_HDR_CHANGED";
        }
        if (n == 2203) {
            return "EVDO_HDR_EXITED";
        }
        if (n == 2204) {
            return "EVDO_HDR_NO_SESSION";
        }
        if (n == 2205) {
            return "EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL";
        }
        if (n == 2206) {
            return "EVDO_HDR_CONNECTION_SETUP_TIMEOUT";
        }
        if (n == 2207) {
            return "FAILED_TO_ACQUIRE_COLOCATED_HDR";
        }
        if (n == 2208) {
            return "OTASP_COMMIT_IN_PROGRESS";
        }
        if (n == 2209) {
            return "NO_HYBRID_HDR_SERVICE";
        }
        if (n == 2210) {
            return "HDR_NO_LOCK_GRANTED";
        }
        if (n == 2211) {
            return "DBM_OR_SMS_IN_PROGRESS";
        }
        if (n == 2212) {
            return "HDR_FADE";
        }
        if (n == 2213) {
            return "HDR_ACCESS_FAILURE";
        }
        if (n == 2214) {
            return "UNSUPPORTED_1X_PREV";
        }
        if (n == 2215) {
            return "LOCAL_END";
        }
        if (n == 2216) {
            return "NO_SERVICE";
        }
        if (n == 2217) {
            return "FADE";
        }
        if (n == 2218) {
            return "NORMAL_RELEASE";
        }
        if (n == 2219) {
            return "ACCESS_ATTEMPT_ALREADY_IN_PROGRESS";
        }
        if (n == 2220) {
            return "REDIRECTION_OR_HANDOFF_IN_PROGRESS";
        }
        if (n == 2221) {
            return "EMERGENCY_MODE";
        }
        if (n == 2222) {
            return "PHONE_IN_USE";
        }
        if (n == 2223) {
            return "INVALID_MODE";
        }
        if (n == 2224) {
            return "INVALID_SIM_STATE";
        }
        if (n == 2225) {
            return "NO_COLLOCATED_HDR";
        }
        if (n == 2226) {
            return "UE_IS_ENTERING_POWERSAVE_MODE";
        }
        if (n == 2227) {
            return "DUAL_SWITCH";
        }
        if (n == 2228) {
            return "PPP_TIMEOUT";
        }
        if (n == 2229) {
            return "PPP_AUTH_FAILURE";
        }
        if (n == 2230) {
            return "PPP_OPTION_MISMATCH";
        }
        if (n == 2231) {
            return "PPP_PAP_FAILURE";
        }
        if (n == 2232) {
            return "PPP_CHAP_FAILURE";
        }
        if (n == 2233) {
            return "PPP_CLOSE_IN_PROGRESS";
        }
        if (n == 2234) {
            return "LIMITED_TO_IPV4";
        }
        if (n == 2235) {
            return "LIMITED_TO_IPV6";
        }
        if (n == 2236) {
            return "VSNCP_TIMEOUT";
        }
        if (n == 2237) {
            return "VSNCP_GEN_ERROR";
        }
        if (n == 2238) {
            return "VSNCP_APN_UNATHORIZED";
        }
        if (n == 2239) {
            return "VSNCP_PDN_LIMIT_EXCEEDED";
        }
        if (n == 2240) {
            return "VSNCP_NO_PDN_GATEWAY_ADDRESS";
        }
        if (n == 2241) {
            return "VSNCP_PDN_GATEWAY_UNREACHABLE";
        }
        if (n == 2242) {
            return "VSNCP_PDN_GATEWAY_REJECT";
        }
        if (n == 2243) {
            return "VSNCP_INSUFFICIENT_PARAMETERS";
        }
        if (n == 2244) {
            return "VSNCP_RESOURCE_UNAVAILABLE";
        }
        if (n == 2245) {
            return "VSNCP_ADMINISTRATIVELY_PROHIBITED";
        }
        if (n == 2246) {
            return "VSNCP_PDN_ID_IN_USE";
        }
        if (n == 2247) {
            return "VSNCP_SUBSCRIBER_LIMITATION";
        }
        if (n == 2248) {
            return "VSNCP_PDN_EXISTS_FOR_THIS_APN";
        }
        if (n == 2249) {
            return "VSNCP_RECONNECT_NOT_ALLOWED";
        }
        if (n == 2250) {
            return "IPV6_PREFIX_UNAVAILABLE";
        }
        if (n == 2251) {
            return "HANDOFF_PREFERENCE_CHANGED";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

