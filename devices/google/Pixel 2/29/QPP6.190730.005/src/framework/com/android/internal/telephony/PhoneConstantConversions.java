/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.PhoneConstants;

public class PhoneConstantConversions {
    public static int convertCallState(PhoneConstants.State state) {
        int n = 1.$SwitchMap$com$android$internal$telephony$PhoneConstants$State[state.ordinal()];
        if (n != 1) {
            if (n != 2) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    public static PhoneConstants.State convertCallState(int n) {
        if (n != 1) {
            if (n != 2) {
                return PhoneConstants.State.IDLE;
            }
            return PhoneConstants.State.OFFHOOK;
        }
        return PhoneConstants.State.RINGING;
    }

    public static int convertDataState(PhoneConstants.DataState dataState) {
        int n = 1.$SwitchMap$com$android$internal$telephony$PhoneConstants$DataState[dataState.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return 0;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static PhoneConstants.DataState convertDataState(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return PhoneConstants.DataState.DISCONNECTED;
                }
                return PhoneConstants.DataState.SUSPENDED;
            }
            return PhoneConstants.DataState.CONNECTED;
        }
        return PhoneConstants.DataState.CONNECTING;
    }

}

