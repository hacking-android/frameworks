/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

public class DctConstants {
    public static final String APN_TYPE_KEY = "apnType";
    public static final int BASE = 270336;
    public static final int CMD_CLEAR_PROVISIONING_SPINNER = 270378;
    public static final int CMD_ENABLE_MOBILE_PROVISIONING = 270373;
    public static final int CMD_IS_PROVISIONING_APN = 270374;
    public static final int CMD_NET_STAT_POLL = 270376;
    public static final int CMD_SET_ENABLE_FAIL_FAST_MOBILE_DATA = 270372;
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;
    public static final int EVENT_APN_CHANGED = 270355;
    public static final int EVENT_CLEAN_UP_ALL_CONNECTIONS = 270365;
    public static final int EVENT_CLEAN_UP_CONNECTION = 270360;
    public static final int EVENT_DATA_CONNECTION_ATTACHED = 270352;
    public static final int EVENT_DATA_CONNECTION_DETACHED = 270345;
    public static final int EVENT_DATA_ENABLED_CHANGED = 270382;
    public static final int EVENT_DATA_ENABLED_OVERRIDE_RULES_CHANGED = 270387;
    public static final int EVENT_DATA_RAT_CHANGED = 270377;
    public static final int EVENT_DATA_RECONNECT = 270383;
    public static final int EVENT_DATA_SERVICE_BINDING_CHANGED = 270385;
    public static final int EVENT_DATA_SETUP_COMPLETE = 270336;
    public static final int EVENT_DATA_SETUP_COMPLETE_ERROR = 270371;
    public static final int EVENT_DATA_STALL_ALARM = 270353;
    public static final int EVENT_DEVICE_PROVISIONED_CHANGE = 270386;
    public static final int EVENT_DISABLE_APN = 270350;
    public static final int EVENT_DISCONNECT_DONE = 270351;
    public static final int EVENT_DO_RECOVERY = 270354;
    public static final int EVENT_ENABLE_APN = 270349;
    public static final int EVENT_ICC_CHANGED = 270369;
    public static final int EVENT_NETWORK_STATUS_CHANGED = 270380;
    public static final int EVENT_PCO_DATA_RECEIVED = 270381;
    public static final int EVENT_PROVISIONING_APN_ALARM = 270375;
    public static final int EVENT_PS_RESTRICT_DISABLED = 270359;
    public static final int EVENT_PS_RESTRICT_ENABLED = 270358;
    public static final int EVENT_RADIO_AVAILABLE = 270337;
    public static final int EVENT_RADIO_OFF_OR_NOT_AVAILABLE = 270342;
    public static final int EVENT_RECORDS_LOADED = 270338;
    public static final int EVENT_RESTART_RADIO = 270362;
    public static final int EVENT_ROAMING_OFF = 270348;
    public static final int EVENT_ROAMING_ON = 270347;
    public static final int EVENT_ROAMING_SETTING_CHANGE = 270384;
    public static final int EVENT_TRY_SETUP_DATA = 270339;
    public static final int EVENT_VOICE_CALL_ENDED = 270344;
    public static final int EVENT_VOICE_CALL_STARTED = 270343;
    public static final int INVALID = -1;
    public static final String PROVISIONING_URL_KEY = "provisioningUrl";

    public static enum Activity {
        NONE,
        DATAIN,
        DATAOUT,
        DATAINANDOUT,
        DORMANT;
        
    }

    public static enum State {
        IDLE,
        CONNECTING,
        RETRYING,
        CONNECTED,
        DISCONNECTING,
        FAILED;
        
    }

}

