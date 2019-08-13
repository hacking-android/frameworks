/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Build
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.os.Build;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccFileNotFound;
import com.android.internal.telephony.uicc.IccFileTypeMismatch;
import com.android.internal.telephony.uicc.IccUtils;

public class IccIoResult {
    private static final String UNKNOWN_ERROR = "unknown";
    @UnsupportedAppUsage
    public byte[] payload;
    @UnsupportedAppUsage
    public int sw1;
    @UnsupportedAppUsage
    public int sw2;

    @UnsupportedAppUsage
    public IccIoResult(int n, int n2, String string) {
        this(n, n2, IccUtils.hexStringToBytes((String)string));
    }

    @UnsupportedAppUsage
    public IccIoResult(int n, int n2, byte[] arrby) {
        this.sw1 = n;
        this.sw2 = n2;
        this.payload = arrby;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getErrorString() {
        int n = this.sw1;
        if (n != 152) {
            if (n == 158) return null;
            if (n == 159) return null;
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    switch (n) {
                                        default: {
                                            return UNKNOWN_ERROR;
                                        }
                                        case 148: {
                                            n = this.sw2;
                                            if (n == 0) return "no EF selected";
                                            if (n == 2) return "out f range (invalid address)";
                                            if (n == 4) return "file ID not found/pattern not found";
                                            if (n != 8) return UNKNOWN_ERROR;
                                            return "file is inconsistent with the command";
                                        }
                                        case 147: {
                                            if (this.sw2 != 0) return UNKNOWN_ERROR;
                                            return "SIM Application Toolkit is busy. Command cannot be executed at present, further normal commands are allowed.";
                                        }
                                        case 146: {
                                            n = this.sw2;
                                            if (n >> 4 == 0) {
                                                return "command successful but after using an internal update retry routine";
                                            }
                                            if (n != 64) return UNKNOWN_ERROR;
                                            return "memory problem";
                                        }
                                        case 145: {
                                            return null;
                                        }
                                        case 144: {
                                            return null;
                                        }
                                    }
                                }
                                case 111: {
                                    if (this.sw2 == 0) return "technical problem with no diagnostic given";
                                    return "The interpretation of this status word is command dependent";
                                }
                                case 110: {
                                    return "wrong instruction class given in the command";
                                }
                                case 109: {
                                    return "unknown instruction code given in the command";
                                }
                            }
                        }
                        case 107: {
                            return "incorrect parameter P1 or P2";
                        }
                        case 106: {
                            switch (this.sw2) {
                                default: {
                                    return UNKNOWN_ERROR;
                                }
                                case 136: {
                                    return "Referenced data not found";
                                }
                                case 135: {
                                    return "Lc inconsistent with P1 to P2";
                                }
                                case 134: {
                                    return "Incorrect parameters P1 to P2";
                                }
                                case 132: {
                                    return "Not enough memory space";
                                }
                                case 131: {
                                    return "Record not found";
                                }
                                case 130: {
                                    return "File not found";
                                }
                                case 129: {
                                    return "Function not supported";
                                }
                                case 128: {
                                    return "Incorrect parameters in the data field";
                                }
                            }
                        }
                        case 105: {
                            n = this.sw2;
                            if (n == 0) return "No information given";
                            if (n == 137) return "Command not allowed - secure channel - security not satisfied";
                            switch (n) {
                                default: {
                                    return UNKNOWN_ERROR;
                                }
                                case 134: {
                                    return "Command not allowed (no EF selected)";
                                }
                                case 133: {
                                    return "Conditions of use not satisfied";
                                }
                                case 132: {
                                    return "Referenced data invalidated";
                                }
                                case 131: {
                                    return "Authentication/PIN method blocked";
                                }
                                case 130: {
                                    return "Security status not satisfied";
                                }
                                case 129: {
                                    return "Command incompatible with file structure";
                                }
                            }
                        }
                        case 104: {
                            n = this.sw2;
                            if (n == 0) return "No information given";
                            if (n == 129) return "Logical channel not supported";
                            if (n != 130) return UNKNOWN_ERROR;
                            return "Secure messaging not supported";
                        }
                        case 103: {
                            if (this.sw2 == 0) return "incorrect parameter P3";
                            return "The interpretation of this status word is command dependent";
                        }
                    }
                }
                case 101: {
                    n = this.sw2;
                    if (n == 0) return "No information given, state of non-volatile memory changed";
                    if (n != 129) return UNKNOWN_ERROR;
                    return "Memory problem";
                }
                case 100: {
                    if (this.sw2 != 0) return UNKNOWN_ERROR;
                    return "No information given, state of non-volatile memory unchanged";
                }
                case 99: {
                    n = this.sw2;
                    if (n >> 4 == 12) {
                        return "Command successful but after using an internalupdate retry routine but Verification failed";
                    }
                    if (n == 241) return "More data expected";
                    if (n != 242) return UNKNOWN_ERROR;
                    return "More data expected and proactive command pending";
                }
                case 98: {
                    n = this.sw2;
                    if (n == 0) return "No information given, state of non volatile memory unchanged";
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    return UNKNOWN_ERROR;
                                }
                                case 243: {
                                    return "Response data available";
                                }
                                case 242: {
                                    return "More data available and proactive command pending";
                                }
                                case 241: {
                                    return "More data available";
                                }
                            }
                        }
                        case 132: {
                            return "Selected file in termination state";
                        }
                        case 131: {
                            return "Selected file invalidated";
                        }
                        case 130: {
                            return "End of file/record reached before reading Le bytes";
                        }
                        case 129: {
                            return "Part of returned data may be corrupted";
                        }
                    }
                }
            }
        }
        n = this.sw2;
        if (n == 2) return "no CHV initialized";
        if (n == 4) return "access condition not fulfilled/unsuccessful CHV verification, at least one attempt left/unsuccessful UNBLOCK CHV verification, at least one attempt left/authentication failed";
        if (n == 8) return "in contradiction with CHV status";
        if (n == 16) return "in contradiction with invalidation status";
        if (n == 64) return "unsuccessful CHV verification, no attempt left/unsuccessful UNBLOCK CHV verification, no attempt left/CHV blockedUNBLOCK CHV blocked";
        if (n == 80) return "increase cannot be performed, Max value reached";
        if (n == 98) return "authentication error, application specific";
        switch (n) {
            default: {
                return UNKNOWN_ERROR;
            }
            case 103: {
                return "authentication error, no memory space available in EF_MUK";
            }
            case 102: {
                return "authentication error, no memory space available";
            }
            case 101: {
                return "key freshness failure";
            }
            case 100: 
        }
        return "authentication error, security context not supported";
    }

    public IccException getException() {
        if (this.success()) {
            return null;
        }
        if (this.sw1 != 148) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sw1:");
            stringBuilder.append(this.sw1);
            stringBuilder.append(" sw2:");
            stringBuilder.append(this.sw2);
            return new IccException(stringBuilder.toString());
        }
        if (this.sw2 == 8) {
            return new IccFileTypeMismatch();
        }
        return new IccFileNotFound();
    }

    @UnsupportedAppUsage
    public boolean success() {
        int n = this.sw1;
        boolean bl = n == 144 || n == 145 || n == 158 || n == 159;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IccIoResult sw1:0x");
        stringBuilder.append(Integer.toHexString(this.sw1));
        stringBuilder.append(" sw2:0x");
        stringBuilder.append(Integer.toHexString(this.sw2));
        stringBuilder.append(" Payload: ");
        CharSequence charSequence = Build.IS_DEBUGGABLE ? IccUtils.bytesToHexString((byte[])this.payload) : "*******";
        stringBuilder.append((String)charSequence);
        if (!this.success()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" Error: ");
            ((StringBuilder)charSequence).append(this.getErrorString());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }
}

