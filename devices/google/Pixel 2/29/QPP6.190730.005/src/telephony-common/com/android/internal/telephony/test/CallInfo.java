/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.test;

import com.android.internal.telephony.ATParseEx;
import com.android.internal.telephony.DriverCall;

class CallInfo {
    boolean mIsMT;
    boolean mIsMpty;
    String mNumber;
    State mState;
    int mTOA;

    CallInfo(boolean bl, State state, boolean bl2, String string) {
        this.mIsMT = bl;
        this.mState = state;
        this.mIsMpty = bl2;
        this.mNumber = string;
        this.mTOA = string.length() > 0 && string.charAt(0) == '+' ? 145 : 129;
    }

    static CallInfo createIncomingCall(String string) {
        return new CallInfo(true, State.INCOMING, false, string);
    }

    static CallInfo createOutgoingCall(String string) {
        return new CallInfo(false, State.DIALING, false, string);
    }

    boolean isActiveOrHeld() {
        boolean bl = this.mState == State.ACTIVE || this.mState == State.HOLDING;
        return bl;
    }

    boolean isConnecting() {
        boolean bl = this.mState == State.DIALING || this.mState == State.ALERTING;
        return bl;
    }

    boolean isRinging() {
        boolean bl = this.mState == State.INCOMING || this.mState == State.WAITING;
        return bl;
    }

    String toCLCCLine(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+CLCC: ");
        stringBuilder.append(n);
        stringBuilder.append(",");
        boolean bl = this.mIsMT;
        String string = "1";
        String string2 = bl ? "1" : "0";
        stringBuilder.append(string2);
        stringBuilder.append(",");
        stringBuilder.append(this.mState.value());
        stringBuilder.append(",0,");
        string2 = this.mIsMpty ? string : "0";
        stringBuilder.append(string2);
        stringBuilder.append(",\"");
        stringBuilder.append(this.mNumber);
        stringBuilder.append("\",");
        stringBuilder.append(this.mTOA);
        return stringBuilder.toString();
    }

    DriverCall toDriverCall(int n) {
        DriverCall driverCall = new DriverCall();
        driverCall.index = n;
        driverCall.isMT = this.mIsMT;
        try {
            driverCall.state = DriverCall.stateFromCLCC(this.mState.value());
        }
        catch (ATParseEx aTParseEx) {
            throw new RuntimeException("should never happen", aTParseEx);
        }
        driverCall.isMpty = this.mIsMpty;
        driverCall.number = this.mNumber;
        driverCall.TOA = this.mTOA;
        driverCall.isVoice = true;
        driverCall.als = 0;
        return driverCall;
    }

    static enum State {
        ACTIVE(0),
        HOLDING(1),
        DIALING(2),
        ALERTING(3),
        INCOMING(4),
        WAITING(5);
        
        private final int mValue;

        private State(int n2) {
            this.mValue = n2;
        }

        public int value() {
            return this.mValue;
        }
    }

}

