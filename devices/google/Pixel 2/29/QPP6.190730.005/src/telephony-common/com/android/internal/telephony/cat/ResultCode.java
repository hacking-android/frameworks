/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;

public enum ResultCode {
    OK(0),
    PRFRMD_WITH_PARTIAL_COMPREHENSION(1),
    PRFRMD_WITH_MISSING_INFO(2),
    PRFRMD_WITH_ADDITIONAL_EFS_READ(3),
    PRFRMD_ICON_NOT_DISPLAYED(4),
    PRFRMD_MODIFIED_BY_NAA(5),
    PRFRMD_LIMITED_SERVICE(6),
    PRFRMD_WITH_MODIFICATION(7),
    PRFRMD_NAA_NOT_ACTIVE(8),
    PRFRMD_TONE_NOT_PLAYED(9),
    UICC_SESSION_TERM_BY_USER(16),
    BACKWARD_MOVE_BY_USER(17),
    NO_RESPONSE_FROM_USER(18),
    HELP_INFO_REQUIRED(19),
    USSD_SS_SESSION_TERM_BY_USER(20),
    TERMINAL_CRNTLY_UNABLE_TO_PROCESS(32),
    NETWORK_CRNTLY_UNABLE_TO_PROCESS(33),
    USER_NOT_ACCEPT(34),
    USER_CLEAR_DOWN_CALL(35),
    CONTRADICTION_WITH_TIMER(36),
    NAA_CALL_CONTROL_TEMPORARY(37),
    LAUNCH_BROWSER_ERROR(38),
    MMS_TEMPORARY(39),
    BEYOND_TERMINAL_CAPABILITY(48),
    CMD_TYPE_NOT_UNDERSTOOD(49),
    CMD_DATA_NOT_UNDERSTOOD(50),
    CMD_NUM_NOT_KNOWN(51),
    SS_RETURN_ERROR(52),
    SMS_RP_ERROR(53),
    REQUIRED_VALUES_MISSING(54),
    USSD_RETURN_ERROR(55),
    MULTI_CARDS_CMD_ERROR(56),
    USIM_CALL_CONTROL_PERMANENT(57),
    BIP_ERROR(58),
    ACCESS_TECH_UNABLE_TO_PROCESS(59),
    FRAMES_ERROR(60),
    MMS_ERROR(61);
    
    private int mCode;

    private ResultCode(int n2) {
        this.mCode = n2;
    }

    public static ResultCode fromInt(int n) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.mCode != n) continue;
            return resultCode;
        }
        return null;
    }

    @UnsupportedAppUsage
    public int value() {
        return this.mCode;
    }
}

