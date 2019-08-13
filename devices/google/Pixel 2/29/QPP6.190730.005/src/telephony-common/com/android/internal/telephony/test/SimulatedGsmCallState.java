/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.test;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.test.CallInfo;
import com.android.internal.telephony.test.InvalidStateEx;
import java.util.ArrayList;
import java.util.List;

class SimulatedGsmCallState
extends Handler {
    static final int CONNECTING_PAUSE_MSEC = 500;
    static final int EVENT_PROGRESS_CALL_STATE = 1;
    static final int MAX_CALLS = 7;
    private boolean mAutoProgressConnecting = true;
    CallInfo[] mCalls = new CallInfo[7];
    private boolean mNextDialFailImmediately;

    public SimulatedGsmCallState(Looper looper) {
        super(looper);
    }

    private int countActiveLines() throws InvalidStateEx {
        int n;
        Object object;
        boolean bl = false;
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = false;
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < ((CallInfo[])(object = this.mCalls)).length; ++i) {
            object = object[i];
            boolean bl3 = bl;
            int n6 = n2;
            int n7 = n3;
            boolean bl4 = bl2;
            int n8 = n4;
            n = n5;
            if (object != null) {
                n8 = 0;
                if (!bl && ((CallInfo)object).mIsMpty) {
                    n5 = ((CallInfo)object).mState == CallInfo.State.HOLDING ? 1 : 0;
                    n = n5;
                } else {
                    if (((CallInfo)object).mIsMpty && n5 != 0 && ((CallInfo)object).mState == CallInfo.State.ACTIVE) {
                        Rlog.e((String)"ModelInterpreter", (String)"Invalid state");
                        throw new InvalidStateEx();
                    }
                    n = n5;
                    if (!((CallInfo)object).mIsMpty) {
                        n = n5;
                        if (bl) {
                            n = n5;
                            if (n5 != 0) {
                                if (((CallInfo)object).mState != CallInfo.State.HOLDING) {
                                    n = n5;
                                } else {
                                    Rlog.e((String)"ModelInterpreter", (String)"Invalid state");
                                    throw new InvalidStateEx();
                                }
                            }
                        }
                    }
                }
                bl3 = bl | ((CallInfo)object).mIsMpty;
                n5 = ((CallInfo)object).mState == CallInfo.State.HOLDING ? 1 : 0;
                n6 = n2 | n5;
                n5 = n8;
                if (((CallInfo)object).mState == CallInfo.State.ACTIVE) {
                    n5 = 1;
                }
                n7 = n3 | n5;
                bl4 = bl2 | ((CallInfo)object).isConnecting();
                n8 = n4 | ((CallInfo)object).isRinging();
            }
            bl = bl3;
            n2 = n6;
            n3 = n7;
            bl2 = bl4;
            n4 = n8;
            n5 = n;
        }
        n = 0;
        if (n2 != 0) {
            n = 0 + 1;
        }
        n5 = n;
        if (n3 != 0) {
            n5 = n + 1;
        }
        n = n5;
        if (bl2) {
            n = n5 + 1;
        }
        n5 = n;
        if (n4 != 0) {
            n5 = n + 1;
        }
        return n5;
    }

    public boolean conference() {
        int n;
        Object object;
        int n2 = 0;
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            object = object[n];
            int n3 = n2;
            if (object != null) {
                n3 = n2 + 1;
                if (object.isConnecting()) {
                    return false;
                }
            }
            n2 = n3;
        }
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            if ((object = object[n]) == null) continue;
            object.mState = CallInfo.State.ACTIVE;
            if (n2 <= 0) continue;
            object.mIsMpty = true;
        }
        return true;
    }

    public boolean explicitCallTransfer() {
        Object object;
        int n = 0;
        for (int i = 0; i < ((CallInfo[])(object = this.mCalls)).length; ++i) {
            object = object[i];
            int n2 = n;
            if (object != null) {
                n2 = n + 1;
                if (((CallInfo)object).isConnecting()) {
                    return false;
                }
            }
            n = n2;
        }
        return this.triggerHangupAll();
    }

    public List<String> getClccLines() {
        Object object;
        ArrayList<String> arrayList = new ArrayList<String>(this.mCalls.length);
        for (int i = 0; i < ((CallInfo[])(object = this.mCalls)).length; ++i) {
            if ((object = object[i]) == null) continue;
            arrayList.add(((CallInfo)object).toCLCCLine(i + 1));
        }
        return arrayList;
    }

    public List<DriverCall> getDriverCalls() {
        Object object;
        ArrayList<DriverCall> arrayList = new ArrayList<DriverCall>(this.mCalls.length);
        for (int i = 0; i < ((CallInfo[])(object = this.mCalls)).length; ++i) {
            if ((object = object[i]) == null) continue;
            arrayList.add(((CallInfo)object).toDriverCall(i + 1));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("SC< getDriverCalls ");
        ((StringBuilder)object).append(arrayList);
        Rlog.d((String)"GSM", (String)((StringBuilder)object).toString());
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message message) {
        synchronized (this) {
            if (message.what == 1) {
                this.progressConnectingCallState();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean onAnswer() {
        synchronized (this) {
            int n = 0;
            while (n < this.mCalls.length) {
                CallInfo callInfo = this.mCalls[n];
                if (callInfo != null) {
                    if (callInfo.mState == CallInfo.State.INCOMING) return this.switchActiveAndHeldOrWaiting();
                    if (callInfo.mState == CallInfo.State.WAITING) {
                        return this.switchActiveAndHeldOrWaiting();
                    }
                }
                ++n;
            }
            return false;
        }
    }

    public boolean onChld(char c, char c2) {
        int n;
        boolean bl;
        block14 : {
            block15 : {
                n = 0;
                if (c2 == '\u0000') break block14;
                int n2 = c2 - 49;
                if (n2 < 0) break block15;
                n = n2;
                if (n2 < this.mCalls.length) break block14;
            }
            return false;
        }
        switch (c) {
            default: {
                bl = false;
                break;
            }
            case '5': {
                bl = false;
                break;
            }
            case '4': {
                bl = this.explicitCallTransfer();
                break;
            }
            case '3': {
                bl = this.conference();
                break;
            }
            case '2': {
                if (c2 <= '\u0000') {
                    bl = this.switchActiveAndHeldOrWaiting();
                    break;
                }
                bl = this.separateCall(n);
                break;
            }
            case '1': {
                if (c2 <= '\u0000') {
                    bl = this.releaseActiveAcceptHeldOrWaiting();
                    break;
                }
                CallInfo[] arrcallInfo = this.mCalls;
                if (arrcallInfo[n] == null) {
                    bl = false;
                    break;
                }
                arrcallInfo[n] = null;
                bl = true;
                break;
            }
            case '0': {
                bl = this.releaseHeldOrUDUB();
            }
        }
        return bl;
    }

    public boolean onDial(String charSequence) {
        CallInfo[] arrcallInfo;
        int n;
        block12 : {
            n = -1;
            arrcallInfo = new StringBuilder();
            arrcallInfo.append("SC> dial '");
            arrcallInfo.append((String)charSequence);
            arrcallInfo.append("'");
            Rlog.d((String)"GSM", (String)arrcallInfo.toString());
            if (this.mNextDialFailImmediately) {
                this.mNextDialFailImmediately = false;
                Rlog.d((String)"GSM", (String)"SC< dial fail (per request)");
                return false;
            }
            if (((String)(charSequence = PhoneNumberUtils.extractNetworkPortion((String)charSequence))).length() == 0) {
                Rlog.d((String)"GSM", (String)"SC< dial fail (invalid ph num)");
                return false;
            }
            if (((String)charSequence).startsWith("*99") && ((String)charSequence).endsWith("#")) {
                Rlog.d((String)"GSM", (String)"SC< dial ignored (gprs)");
                return true;
            }
            try {
                if (this.countActiveLines() <= 1) break block12;
                Rlog.d((String)"GSM", (String)"SC< dial fail (invalid call state)");
                return false;
            }
            catch (InvalidStateEx invalidStateEx) {
                Rlog.d((String)"GSM", (String)"SC< dial fail (invalid call state)");
                return false;
            }
        }
        for (int i = 0; i < (arrcallInfo = this.mCalls).length; ++i) {
            int n2 = n;
            if (n < 0) {
                n2 = n;
                if (arrcallInfo[i] == null) {
                    n2 = i;
                }
            }
            if ((arrcallInfo = this.mCalls)[i] != null && !arrcallInfo[i].isActiveOrHeld()) {
                Rlog.d((String)"GSM", (String)"SC< dial fail (invalid call state)");
                return false;
            }
            arrcallInfo = this.mCalls;
            if (arrcallInfo[i] != null && arrcallInfo[i].mState == CallInfo.State.ACTIVE) {
                this.mCalls[i].mState = CallInfo.State.HOLDING;
            }
            n = n2;
        }
        if (n < 0) {
            Rlog.d((String)"GSM", (String)"SC< dial fail (invalid call state)");
            return false;
        }
        arrcallInfo[n] = CallInfo.createOutgoingCall((String)charSequence);
        if (this.mAutoProgressConnecting) {
            this.sendMessageDelayed(this.obtainMessage(1, (Object)this.mCalls[n]), 500L);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("SC< dial (slot = ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(")");
        Rlog.d((String)"GSM", (String)((StringBuilder)charSequence).toString());
        return true;
    }

    public boolean onHangup() {
        Object object;
        boolean bl = false;
        for (int i = 0; i < ((CallInfo[])(object = this.mCalls)).length; ++i) {
            object = object[i];
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (object.mState != CallInfo.State.WAITING) {
                    this.mCalls[i] = null;
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void progressConnectingCallState() {
        synchronized (this) {
            for (int i = 0; i < this.mCalls.length; ++i) {
                CallInfo callInfo = this.mCalls[i];
                if (callInfo != null && callInfo.mState == CallInfo.State.DIALING) {
                    callInfo.mState = CallInfo.State.ALERTING;
                    if (!this.mAutoProgressConnecting) break;
                    this.sendMessageDelayed(this.obtainMessage(1, (Object)callInfo), 500L);
                    break;
                }
                if (callInfo == null || callInfo.mState != CallInfo.State.ALERTING) continue;
                callInfo.mState = CallInfo.State.ACTIVE;
                break;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void progressConnectingToActive() {
        synchronized (this) {
            for (int i = 0; i < this.mCalls.length; ++i) {
                CallInfo callInfo = this.mCalls[i];
                if (callInfo == null || callInfo.mState != CallInfo.State.DIALING && callInfo.mState != CallInfo.State.ALERTING) continue;
                callInfo.mState = CallInfo.State.ACTIVE;
                break;
            }
            return;
        }
    }

    public boolean releaseActiveAcceptHeldOrWaiting() {
        Object object;
        int n;
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = false;
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            object = object[n];
            bl = bl3;
            if (object != null) {
                bl = bl3;
                if (((CallInfo)object).mState == CallInfo.State.ACTIVE) {
                    this.mCalls[n] = null;
                    bl = true;
                }
            }
            bl3 = bl;
        }
        if (!bl3) {
            for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
                if ((object = object[n]) == null || ((CallInfo)object).mState != CallInfo.State.DIALING && ((CallInfo)object).mState != CallInfo.State.ALERTING) continue;
                this.mCalls[n] = null;
            }
        }
        bl = bl2;
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            object = object[n];
            bl3 = bl;
            if (object != null) {
                bl3 = bl;
                if (((CallInfo)object).mState == CallInfo.State.HOLDING) {
                    ((CallInfo)object).mState = CallInfo.State.ACTIVE;
                    bl3 = true;
                }
            }
            bl = bl3;
        }
        if (bl) {
            return true;
        }
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            if ((object = object[n]) == null || !((CallInfo)object).isRinging()) continue;
            ((CallInfo)object).mState = CallInfo.State.ACTIVE;
            return true;
        }
        return true;
    }

    public boolean releaseHeldOrUDUB() {
        Object object;
        boolean bl;
        boolean bl2 = false;
        int n = 0;
        do {
            object = this.mCalls;
            bl = bl2;
            if (n >= ((CallInfo[])object).length) break;
            if ((object = object[n]) != null && ((CallInfo)object).isRinging()) {
                bl = true;
                this.mCalls[n] = null;
                break;
            }
            ++n;
        } while (true);
        if (!bl) {
            for (n = 0; n < ((Object)(object = this.mCalls)).length; ++n) {
                if ((object = object[n]) == null || ((CallInfo)object).mState != CallInfo.State.HOLDING) continue;
                this.mCalls[n] = null;
            }
        }
        return true;
    }

    public boolean separateCall(int n) {
        block9 : {
            int n2;
            CallInfo callInfo = this.mCalls[n];
            if (callInfo == null) break block9;
            try {
                if (callInfo.isConnecting() || this.countActiveLines() != 1) break block9;
                callInfo.mState = CallInfo.State.ACTIVE;
                callInfo.mIsMpty = false;
                n2 = 0;
            }
            catch (InvalidStateEx invalidStateEx) {
                return false;
            }
            do {
                int n3;
                block10 : {
                    if (n2 >= this.mCalls.length) break;
                    int n4 = 0;
                    int n5 = 0;
                    n3 = n4;
                    int n6 = n5;
                    if (n2 == n) break block10;
                    callInfo = this.mCalls[n2];
                    n3 = n4;
                    n6 = n5;
                    if (callInfo == null) break block10;
                    n3 = n4;
                    n6 = n5;
                    if (callInfo.mState != CallInfo.State.ACTIVE) break block10;
                    callInfo.mState = CallInfo.State.HOLDING;
                    n3 = 0 + 1;
                    n6 = n2;
                }
                if (n3 == 1) {
                    this.mCalls[n6].mIsMpty = false;
                }
                ++n2;
            } while (true);
            return true;
        }
        return false;
    }

    public void setAutoProgressConnectingCall(boolean bl) {
        this.mAutoProgressConnecting = bl;
    }

    public void setNextDialFailImmediately(boolean bl) {
        this.mNextDialFailImmediately = bl;
    }

    public boolean switchActiveAndHeldOrWaiting() {
        Object object;
        boolean bl;
        boolean bl2 = false;
        int n = 0;
        do {
            object = this.mCalls;
            bl = bl2;
            if (n >= ((CallInfo[])object).length) break;
            if ((object = object[n]) != null && ((CallInfo)object).mState == CallInfo.State.HOLDING) {
                bl = true;
                break;
            }
            ++n;
        } while (true);
        for (n = 0; n < ((CallInfo[])(object = this.mCalls)).length; ++n) {
            if ((object = object[n]) == null) continue;
            if (((CallInfo)object).mState == CallInfo.State.ACTIVE) {
                ((CallInfo)object).mState = CallInfo.State.HOLDING;
                continue;
            }
            if (((CallInfo)object).mState == CallInfo.State.HOLDING) {
                ((CallInfo)object).mState = CallInfo.State.ACTIVE;
                continue;
            }
            if (bl || !((CallInfo)object).isRinging()) continue;
            ((CallInfo)object).mState = CallInfo.State.ACTIVE;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean triggerHangupAll() {
        synchronized (this) {
            boolean bl = false;
            int n = 0;
            while (n < this.mCalls.length) {
                CallInfo callInfo = this.mCalls[n];
                if (this.mCalls[n] != null) {
                    bl = true;
                }
                this.mCalls[n] = null;
                ++n;
            }
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean triggerHangupBackground() {
        synchronized (this) {
            boolean bl = false;
            int n = 0;
            while (n < this.mCalls.length) {
                CallInfo callInfo = this.mCalls[n];
                boolean bl2 = bl;
                if (callInfo != null) {
                    bl2 = bl;
                    if (callInfo.mState == CallInfo.State.HOLDING) {
                        this.mCalls[n] = null;
                        bl2 = true;
                    }
                }
                ++n;
                bl = bl2;
            }
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean triggerHangupForeground() {
        synchronized (this) {
            int n;
            CallInfo callInfo;
            boolean bl;
            boolean bl2 = false;
            int n2 = 0;
            for (n = 0; n < this.mCalls.length; ++n) {
                block13 : {
                    block14 : {
                        callInfo = this.mCalls[n];
                        bl = bl2;
                        if (callInfo == null) break block13;
                        if (callInfo.mState == CallInfo.State.INCOMING) break block14;
                        bl = bl2;
                        if (callInfo.mState != CallInfo.State.WAITING) break block13;
                    }
                    this.mCalls[n] = null;
                    bl = true;
                }
                bl2 = bl;
            }
            n = n2;
            bl = bl2;
            do {
                block15 : {
                    block16 : {
                        if (n >= this.mCalls.length) {
                            return bl;
                        }
                        callInfo = this.mCalls[n];
                        bl2 = bl;
                        if (callInfo == null) break block15;
                        if (callInfo.mState == CallInfo.State.DIALING || callInfo.mState == CallInfo.State.ACTIVE) break block16;
                        bl2 = bl;
                        if (callInfo.mState != CallInfo.State.ALERTING) break block15;
                    }
                    this.mCalls[n] = null;
                    bl2 = true;
                }
                ++n;
                bl = bl2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean triggerRing(String string) {
        synchronized (this) {
            int n = -1;
            boolean bl = false;
            for (int i = 0; i < this.mCalls.length; ++i) {
                int n2;
                CallInfo callInfo = this.mCalls[i];
                if (callInfo == null && n < 0) {
                    n2 = i;
                } else {
                    if (callInfo != null && (callInfo.mState == CallInfo.State.INCOMING || callInfo.mState == CallInfo.State.WAITING)) {
                        Rlog.w((String)"ModelInterpreter", (String)"triggerRing failed; phone already ringing");
                        return false;
                    }
                    n2 = n;
                    if (callInfo != null) {
                        bl = true;
                        n2 = n;
                    }
                }
                n = n2;
            }
            if (n < 0) {
                Rlog.w((String)"ModelInterpreter", (String)"triggerRing failed; all full");
                return false;
            }
            this.mCalls[n] = CallInfo.createIncomingCall(PhoneNumberUtils.extractNetworkPortion((String)string));
            if (bl) {
                this.mCalls[n].mState = CallInfo.State.WAITING;
            }
            return true;
        }
    }
}

