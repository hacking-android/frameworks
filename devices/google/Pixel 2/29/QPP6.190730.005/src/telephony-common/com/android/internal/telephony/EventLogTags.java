/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.EventLog
 */
package com.android.internal.telephony;

import android.util.EventLog;

public class EventLogTags {
    public static final int BAD_IP_ADDRESS = 50117;
    public static final int CALL_DROP = 50106;
    public static final int CDMA_DATA_DROP = 50111;
    public static final int CDMA_DATA_SETUP_FAILED = 50110;
    public static final int CDMA_DATA_STATE_CHANGE = 50115;
    public static final int CDMA_SERVICE_STATE_CHANGE = 50116;
    public static final int DATA_NETWORK_REGISTRATION_FAIL = 50107;
    public static final int DATA_NETWORK_STATUS_ON_RADIO_OFF = 50108;
    public static final int DATA_STALL_RECOVERY_CLEANUP = 50119;
    public static final int DATA_STALL_RECOVERY_GET_DATA_CALL_LIST = 50118;
    public static final int DATA_STALL_RECOVERY_RADIO_RESTART = 50121;
    public static final int DATA_STALL_RECOVERY_RADIO_RESTART_WITH_PROP = 50122;
    public static final int DATA_STALL_RECOVERY_REREGISTER = 50120;
    public static final int EXP_DET_SMS_DENIED_BY_USER = 50125;
    public static final int EXP_DET_SMS_SENT_BY_USER = 50128;
    public static final int GSM_DATA_STATE_CHANGE = 50113;
    public static final int GSM_RAT_SWITCHED = 50112;
    public static final int GSM_RAT_SWITCHED_NEW = 50123;
    public static final int GSM_SERVICE_STATE_CHANGE = 50114;
    public static final int PDP_BAD_DNS_ADDRESS = 50100;
    public static final int PDP_CONTEXT_RESET = 50103;
    public static final int PDP_NETWORK_DROP = 50109;
    public static final int PDP_RADIO_RESET = 50102;
    public static final int PDP_RADIO_RESET_COUNTDOWN_TRIGGERED = 50101;
    public static final int PDP_REREGISTER_NETWORK = 50104;
    public static final int PDP_SETUP_FAIL = 50105;

    private EventLogTags() {
    }

    public static void writeBadIpAddress(String string) {
        EventLog.writeEvent((int)50117, (String)string);
    }

    public static void writeCallDrop(int n, int n2, int n3) {
        EventLog.writeEvent((int)50106, (Object[])new Object[]{n, n2, n3});
    }

    public static void writeCdmaDataDrop(int n, int n2) {
        EventLog.writeEvent((int)50111, (Object[])new Object[]{n, n2});
    }

    public static void writeCdmaDataSetupFailed(int n, int n2, int n3) {
        EventLog.writeEvent((int)50110, (Object[])new Object[]{n, n2, n3});
    }

    public static void writeCdmaDataStateChange(String string, String string2) {
        EventLog.writeEvent((int)50115, (Object[])new Object[]{string, string2});
    }

    public static void writeCdmaServiceStateChange(int n, int n2, int n3, int n4) {
        EventLog.writeEvent((int)50116, (Object[])new Object[]{n, n2, n3, n4});
    }

    public static void writeDataNetworkRegistrationFail(int n, int n2) {
        EventLog.writeEvent((int)50107, (Object[])new Object[]{n, n2});
    }

    public static void writeDataNetworkStatusOnRadioOff(String string, int n) {
        EventLog.writeEvent((int)50108, (Object[])new Object[]{string, n});
    }

    public static void writeDataStallRecoveryCleanup(int n) {
        EventLog.writeEvent((int)50119, (int)n);
    }

    public static void writeDataStallRecoveryGetDataCallList(int n) {
        EventLog.writeEvent((int)50118, (int)n);
    }

    public static void writeDataStallRecoveryRadioRestart(int n) {
        EventLog.writeEvent((int)50121, (int)n);
    }

    public static void writeDataStallRecoveryRadioRestartWithProp(int n) {
        EventLog.writeEvent((int)50122, (int)n);
    }

    public static void writeDataStallRecoveryReregister(int n) {
        EventLog.writeEvent((int)50120, (int)n);
    }

    public static void writeExpDetSmsDeniedByUser(String string) {
        EventLog.writeEvent((int)50125, (String)string);
    }

    public static void writeExpDetSmsSentByUser(String string) {
        EventLog.writeEvent((int)50128, (String)string);
    }

    public static void writeGsmDataStateChange(String string, String string2) {
        EventLog.writeEvent((int)50113, (Object[])new Object[]{string, string2});
    }

    public static void writeGsmRatSwitched(int n, int n2, int n3) {
        EventLog.writeEvent((int)50112, (Object[])new Object[]{n, n2, n3});
    }

    public static void writeGsmRatSwitchedNew(int n, int n2, int n3) {
        EventLog.writeEvent((int)50123, (Object[])new Object[]{n, n2, n3});
    }

    public static void writeGsmServiceStateChange(int n, int n2, int n3, int n4) {
        EventLog.writeEvent((int)50114, (Object[])new Object[]{n, n2, n3, n4});
    }

    public static void writePdpBadDnsAddress(String string) {
        EventLog.writeEvent((int)50100, (String)string);
    }

    public static void writePdpContextReset(int n) {
        EventLog.writeEvent((int)50103, (int)n);
    }

    public static void writePdpNetworkDrop(int n, int n2) {
        EventLog.writeEvent((int)50109, (Object[])new Object[]{n, n2});
    }

    public static void writePdpRadioReset(int n) {
        EventLog.writeEvent((int)50102, (int)n);
    }

    public static void writePdpRadioResetCountdownTriggered(int n) {
        EventLog.writeEvent((int)50101, (int)n);
    }

    public static void writePdpReregisterNetwork(int n) {
        EventLog.writeEvent((int)50104, (int)n);
    }

    public static void writePdpSetupFail(int n, int n2, int n3) {
        EventLog.writeEvent((int)50105, (Object[])new Object[]{n, n2, n3});
    }
}

