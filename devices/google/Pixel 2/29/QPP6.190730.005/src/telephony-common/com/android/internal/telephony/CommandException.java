/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;

public class CommandException
extends RuntimeException {
    @UnsupportedAppUsage
    private Error mError;

    @UnsupportedAppUsage
    public CommandException(Error error) {
        super(error.toString());
        this.mError = error;
    }

    public CommandException(Error error, String string) {
        super(string);
        this.mError = error;
    }

    @UnsupportedAppUsage
    public static CommandException fromRilErrno(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Unrecognized RIL errno ");
                                        stringBuilder.append(n);
                                        Rlog.e((String)"GSM", (String)stringBuilder.toString());
                                        return new CommandException(Error.INVALID_RESPONSE);
                                    }
                                    case 525: {
                                        return new CommandException(Error.OEM_ERROR_25);
                                    }
                                    case 524: {
                                        return new CommandException(Error.OEM_ERROR_24);
                                    }
                                    case 523: {
                                        return new CommandException(Error.OEM_ERROR_23);
                                    }
                                    case 522: {
                                        return new CommandException(Error.OEM_ERROR_22);
                                    }
                                    case 521: {
                                        return new CommandException(Error.OEM_ERROR_21);
                                    }
                                    case 520: {
                                        return new CommandException(Error.OEM_ERROR_20);
                                    }
                                    case 519: {
                                        return new CommandException(Error.OEM_ERROR_19);
                                    }
                                    case 518: {
                                        return new CommandException(Error.OEM_ERROR_18);
                                    }
                                    case 517: {
                                        return new CommandException(Error.OEM_ERROR_17);
                                    }
                                    case 516: {
                                        return new CommandException(Error.OEM_ERROR_16);
                                    }
                                    case 515: {
                                        return new CommandException(Error.OEM_ERROR_15);
                                    }
                                    case 514: {
                                        return new CommandException(Error.OEM_ERROR_14);
                                    }
                                    case 513: {
                                        return new CommandException(Error.OEM_ERROR_13);
                                    }
                                    case 512: {
                                        return new CommandException(Error.OEM_ERROR_12);
                                    }
                                    case 511: {
                                        return new CommandException(Error.OEM_ERROR_11);
                                    }
                                    case 510: {
                                        return new CommandException(Error.OEM_ERROR_10);
                                    }
                                    case 509: {
                                        return new CommandException(Error.OEM_ERROR_9);
                                    }
                                    case 508: {
                                        return new CommandException(Error.OEM_ERROR_8);
                                    }
                                    case 507: {
                                        return new CommandException(Error.OEM_ERROR_7);
                                    }
                                    case 506: {
                                        return new CommandException(Error.OEM_ERROR_6);
                                    }
                                    case 505: {
                                        return new CommandException(Error.OEM_ERROR_5);
                                    }
                                    case 504: {
                                        return new CommandException(Error.OEM_ERROR_4);
                                    }
                                    case 503: {
                                        return new CommandException(Error.OEM_ERROR_3);
                                    }
                                    case 502: {
                                        return new CommandException(Error.OEM_ERROR_2);
                                    }
                                    case 501: 
                                }
                                return new CommandException(Error.OEM_ERROR_1);
                            }
                            case 66: {
                                return new CommandException(Error.INVALID_RESPONSE);
                            }
                            case 65: {
                                return new CommandException(Error.ABORTED);
                            }
                            case 64: {
                                return new CommandException(Error.DEVICE_IN_USE);
                            }
                            case 63: {
                                return new CommandException(Error.NO_NETWORK_FOUND);
                            }
                            case 62: {
                                return new CommandException(Error.NO_SUBSCRIPTION);
                            }
                            case 61: {
                                return new CommandException(Error.NOT_PROVISIONED);
                            }
                            case 60: {
                                return new CommandException(Error.NETWORK_NOT_READY);
                            }
                            case 59: {
                                return new CommandException(Error.NO_SUCH_ENTRY);
                            }
                            case 58: {
                                return new CommandException(Error.INVALID_SMSC_ADDRESS);
                            }
                            case 57: {
                                return new CommandException(Error.ENCODING_ERR);
                            }
                            case 56: {
                                return new CommandException(Error.INVALID_SMS_FORMAT);
                            }
                            case 55: {
                                return new CommandException(Error.EMPTY_RECORD);
                            }
                            case 54: {
                                return new CommandException(Error.OPERATION_NOT_ALLOWED);
                            }
                            case 53: {
                                return new CommandException(Error.NETWORK_REJECT);
                            }
                            case 52: {
                                return new CommandException(Error.SIM_FULL);
                            }
                            case 51: {
                                return new CommandException(Error.SIM_BUSY);
                            }
                            case 50: {
                                return new CommandException(Error.REQUEST_RATE_LIMITED);
                            }
                            case 49: {
                                return new CommandException(Error.NETWORK_ERR);
                            }
                            case 48: {
                                return new CommandException(Error.NO_SMS_TO_ACK);
                            }
                            case 47: {
                                return new CommandException(Error.INVALID_CALL_ID);
                            }
                            case 46: {
                                return new CommandException(Error.INVALID_MODEM_STATE);
                            }
                            case 45: {
                                return new CommandException(Error.INVALID_SIM_STATE);
                            }
                            case 44: {
                                return new CommandException(Error.INVALID_ARGUMENTS);
                            }
                            case 43: {
                                return new CommandException(Error.SIM_ERR);
                            }
                            case 42: {
                                return new CommandException(Error.NO_RESOURCES);
                            }
                            case 41: {
                                return new CommandException(Error.INVALID_STATE);
                            }
                            case 40: {
                                return new CommandException(Error.MODEM_ERR);
                            }
                            case 39: {
                                return new CommandException(Error.SYSTEM_ERR);
                            }
                            case 38: {
                                return new CommandException(Error.INTERNAL_ERR);
                            }
                            case 37: {
                                return new CommandException(Error.NO_MEMORY);
                            }
                            case 36: {
                                return new CommandException(Error.LCE_NOT_SUPPORTED);
                            }
                            case 35: {
                                return new CommandException(Error.SIM_SAP_CONNECT_OK_CALL_ONGOING);
                            }
                            case 34: {
                                return new CommandException(Error.SIM_SAP_MSG_SIZE_TOO_SMALL);
                            }
                            case 33: {
                                return new CommandException(Error.SIM_SAP_MSG_SIZE_TOO_LARGE);
                            }
                            case 32: {
                                return new CommandException(Error.SIM_SAP_CONNECT_FAILURE);
                            }
                            case 31: {
                                return new CommandException(Error.SIM_DATA_NOT_AVAILABLE);
                            }
                            case 30: {
                                return new CommandException(Error.SIM_ALREADY_POWERED_ON);
                            }
                            case 29: 
                        }
                        return new CommandException(Error.SIM_ALREADY_POWERED_OFF);
                    }
                    case 27: {
                        return new CommandException(Error.SS_MODIFIED_TO_SS);
                    }
                    case 26: {
                        return new CommandException(Error.SUBSCRIPTION_NOT_SUPPORTED);
                    }
                    case 25: {
                        return new CommandException(Error.SS_MODIFIED_TO_USSD);
                    }
                    case 24: {
                        return new CommandException(Error.SS_MODIFIED_TO_DIAL);
                    }
                    case 23: {
                        return new CommandException(Error.USSD_MODIFIED_TO_USSD);
                    }
                    case 22: {
                        return new CommandException(Error.USSD_MODIFIED_TO_SS);
                    }
                    case 21: {
                        return new CommandException(Error.USSD_MODIFIED_TO_DIAL);
                    }
                    case 20: {
                        return new CommandException(Error.DIAL_MODIFIED_TO_DIAL);
                    }
                    case 19: {
                        return new CommandException(Error.DIAL_MODIFIED_TO_SS);
                    }
                    case 18: {
                        return new CommandException(Error.DIAL_MODIFIED_TO_USSD);
                    }
                    case 17: {
                        return new CommandException(Error.NO_SUCH_ELEMENT);
                    }
                    case 16: {
                        return new CommandException(Error.MISSING_RESOURCE);
                    }
                    case 15: {
                        return new CommandException(Error.ILLEGAL_SIM_OR_ME);
                    }
                    case 14: {
                        return new CommandException(Error.FDN_CHECK_FAILURE);
                    }
                    case 13: {
                        return new CommandException(Error.MODE_NOT_SUPPORTED);
                    }
                    case 12: {
                        return new CommandException(Error.SUBSCRIPTION_NOT_AVAILABLE);
                    }
                    case 11: {
                        return new CommandException(Error.SIM_ABSENT);
                    }
                    case 10: {
                        return new CommandException(Error.SMS_FAIL_RETRY);
                    }
                    case 9: {
                        return new CommandException(Error.OP_NOT_ALLOWED_BEFORE_REG_NW);
                    }
                    case 8: 
                }
                return new CommandException(Error.OP_NOT_ALLOWED_DURING_VOICE_CALL);
            }
            case 6: {
                return new CommandException(Error.REQUEST_NOT_SUPPORTED);
            }
            case 5: {
                return new CommandException(Error.SIM_PUK2);
            }
            case 4: {
                return new CommandException(Error.SIM_PIN2);
            }
            case 3: {
                return new CommandException(Error.PASSWORD_INCORRECT);
            }
            case 2: {
                return new CommandException(Error.GENERIC_FAILURE);
            }
            case 1: {
                return new CommandException(Error.RADIO_NOT_AVAILABLE);
            }
            case 0: {
                return null;
            }
            case -1: 
        }
        return new CommandException(Error.INVALID_RESPONSE);
    }

    @UnsupportedAppUsage
    public Error getCommandError() {
        return this.mError;
    }

    public static enum Error {
        INVALID_RESPONSE,
        RADIO_NOT_AVAILABLE,
        GENERIC_FAILURE,
        PASSWORD_INCORRECT,
        SIM_PIN2,
        SIM_PUK2,
        REQUEST_NOT_SUPPORTED,
        OP_NOT_ALLOWED_DURING_VOICE_CALL,
        OP_NOT_ALLOWED_BEFORE_REG_NW,
        SMS_FAIL_RETRY,
        SIM_ABSENT,
        SUBSCRIPTION_NOT_AVAILABLE,
        MODE_NOT_SUPPORTED,
        FDN_CHECK_FAILURE,
        ILLEGAL_SIM_OR_ME,
        MISSING_RESOURCE,
        NO_SUCH_ELEMENT,
        SUBSCRIPTION_NOT_SUPPORTED,
        DIAL_MODIFIED_TO_USSD,
        DIAL_MODIFIED_TO_SS,
        DIAL_MODIFIED_TO_DIAL,
        USSD_MODIFIED_TO_DIAL,
        USSD_MODIFIED_TO_SS,
        USSD_MODIFIED_TO_USSD,
        SS_MODIFIED_TO_DIAL,
        SS_MODIFIED_TO_DIAL_VIDEO,
        SS_MODIFIED_TO_USSD,
        SS_MODIFIED_TO_SS,
        SIM_ALREADY_POWERED_OFF,
        SIM_ALREADY_POWERED_ON,
        SIM_DATA_NOT_AVAILABLE,
        SIM_SAP_CONNECT_FAILURE,
        SIM_SAP_MSG_SIZE_TOO_LARGE,
        SIM_SAP_MSG_SIZE_TOO_SMALL,
        SIM_SAP_CONNECT_OK_CALL_ONGOING,
        LCE_NOT_SUPPORTED,
        NO_MEMORY,
        INTERNAL_ERR,
        SYSTEM_ERR,
        MODEM_ERR,
        INVALID_STATE,
        NO_RESOURCES,
        SIM_ERR,
        INVALID_ARGUMENTS,
        INVALID_SIM_STATE,
        INVALID_MODEM_STATE,
        INVALID_CALL_ID,
        NO_SMS_TO_ACK,
        NETWORK_ERR,
        REQUEST_RATE_LIMITED,
        SIM_BUSY,
        SIM_FULL,
        NETWORK_REJECT,
        OPERATION_NOT_ALLOWED,
        EMPTY_RECORD,
        INVALID_SMS_FORMAT,
        ENCODING_ERR,
        INVALID_SMSC_ADDRESS,
        NO_SUCH_ENTRY,
        NETWORK_NOT_READY,
        NOT_PROVISIONED,
        NO_SUBSCRIPTION,
        NO_NETWORK_FOUND,
        DEVICE_IN_USE,
        ABORTED,
        OEM_ERROR_1,
        OEM_ERROR_2,
        OEM_ERROR_3,
        OEM_ERROR_4,
        OEM_ERROR_5,
        OEM_ERROR_6,
        OEM_ERROR_7,
        OEM_ERROR_8,
        OEM_ERROR_9,
        OEM_ERROR_10,
        OEM_ERROR_11,
        OEM_ERROR_12,
        OEM_ERROR_13,
        OEM_ERROR_14,
        OEM_ERROR_15,
        OEM_ERROR_16,
        OEM_ERROR_17,
        OEM_ERROR_18,
        OEM_ERROR_19,
        OEM_ERROR_20,
        OEM_ERROR_21,
        OEM_ERROR_22,
        OEM_ERROR_23,
        OEM_ERROR_24,
        OEM_ERROR_25;
        
    }

}

