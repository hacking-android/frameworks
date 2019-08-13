/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telecom.ConferenceParticipant
 *  android.telephony.Rlog
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsStreamMediaProfile
 *  com.android.ims.ImsCall
 *  com.android.ims.ImsException
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.telecom.ConferenceParticipant;
import android.telephony.Rlog;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsStreamMediaProfile;
import com.android.ims.ImsCall;
import com.android.ims.ImsException;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.imsphone.ImsPhoneConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImsPhoneCall
extends Call {
    public static final String CONTEXT_BACKGROUND = "BG";
    public static final String CONTEXT_FOREGROUND = "FG";
    public static final String CONTEXT_HANDOVER = "HO";
    public static final String CONTEXT_RINGING = "RG";
    public static final String CONTEXT_UNKNOWN = "UK";
    private static final boolean DBG = Rlog.isLoggable((String)"ImsPhoneCall", (int)3);
    private static final boolean FORCE_DEBUG = false;
    private static final String LOG_TAG = "ImsPhoneCall";
    private static final boolean VDBG = Rlog.isLoggable((String)"ImsPhoneCall", (int)2);
    private final String mCallContext;
    private boolean mIsRingbackTonePlaying = false;
    ImsPhoneCallTracker mOwner;

    ImsPhoneCall() {
        this.mCallContext = CONTEXT_UNKNOWN;
    }

    public ImsPhoneCall(ImsPhoneCallTracker imsPhoneCallTracker, String string) {
        this.mOwner = imsPhoneCallTracker;
        this.mCallContext = string;
    }

    static boolean isLocalTone(ImsCall imsCall) {
        boolean bl = false;
        if (imsCall != null && imsCall.getCallProfile() != null && imsCall.getCallProfile().mMediaProfile != null) {
            if (imsCall.getCallProfile().mMediaProfile.mAudioDirection == 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void takeOver(ImsPhoneCall object) {
        this.mConnections = ((ImsPhoneCall)object).mConnections;
        this.mState = ((ImsPhoneCall)object).mState;
        object = this.mConnections.iterator();
        while (object.hasNext()) {
            ((ImsPhoneConnection)((Connection)object.next())).changeParent(this);
        }
    }

    public void attach(Connection connection) {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attach : ");
            stringBuilder.append(this.mCallContext);
            stringBuilder.append(" conn = ");
            stringBuilder.append(connection);
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        }
        this.clearDisconnected();
        this.mConnections.add(connection);
        this.mOwner.logState();
    }

    @UnsupportedAppUsage
    public void attach(Connection connection, Call.State state) {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attach : ");
            stringBuilder.append(this.mCallContext);
            stringBuilder.append(" state = ");
            stringBuilder.append(state.toString());
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        }
        this.attach(connection);
        this.mState = state;
    }

    @UnsupportedAppUsage
    public void attachFake(Connection connection, Call.State state) {
        this.attach(connection, state);
    }

    public boolean connectionDisconnected(ImsPhoneConnection object) {
        if (this.mState != Call.State.DISCONNECTED) {
            boolean bl;
            boolean bl2 = true;
            int n = 0;
            int n2 = this.mConnections.size();
            do {
                bl = bl2;
                if (n >= n2) break;
                if (((Connection)this.mConnections.get(n)).getState() != Call.State.DISCONNECTED) {
                    bl = false;
                    break;
                }
                ++n;
            } while (true);
            if (bl) {
                this.mState = Call.State.DISCONNECTED;
                if (VDBG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("connectionDisconnected : ");
                    ((StringBuilder)object).append(this.mCallContext);
                    ((StringBuilder)object).append(" state = ");
                    ((StringBuilder)object).append((Object)this.mState);
                    Rlog.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
                }
                return true;
            }
        }
        return false;
    }

    public void detach(ImsPhoneConnection imsPhoneConnection) {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("detach : ");
            stringBuilder.append(this.mCallContext);
            stringBuilder.append(" conn = ");
            stringBuilder.append(imsPhoneConnection);
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        }
        this.mConnections.remove(imsPhoneConnection);
        this.clearDisconnected();
        this.mOwner.logState();
    }

    public void dispose() {
        try {
            this.mOwner.hangup(this);
        }
        catch (CallStateException callStateException) {
            int n = this.mConnections.size();
            for (int i = 0; i < n; ++i) {
                ((ImsPhoneConnection)this.mConnections.get(i)).onDisconnect(14);
            }
        }
        finally {
            int n = this.mConnections.size();
            for (int i = 0; i < n; ++i) {
                ((ImsPhoneConnection)this.mConnections.get(i)).onDisconnect(14);
            }
        }
    }

    @Override
    public List<ConferenceParticipant> getConferenceParticipants() {
        ImsCall imsCall = this.getImsCall();
        if (imsCall == null) {
            return null;
        }
        return imsCall.getConferenceParticipants();
    }

    @UnsupportedAppUsage
    @Override
    public List<Connection> getConnections() {
        return this.mConnections;
    }

    @VisibleForTesting
    public ImsPhoneConnection getFirstConnection() {
        if (this.mConnections.size() == 0) {
            return null;
        }
        return (ImsPhoneConnection)this.mConnections.get(0);
    }

    ImsPhoneConnection getHandoverConnection() {
        return (ImsPhoneConnection)this.getEarliestConnection();
    }

    @VisibleForTesting
    public ImsCall getImsCall() {
        ImsCall imsCall = this.getFirstConnection() == null ? null : this.getFirstConnection().getImsCall();
        return imsCall;
    }

    @Override
    public Phone getPhone() {
        return this.mOwner.getPhone();
    }

    @UnsupportedAppUsage
    @Override
    public void hangup() throws CallStateException {
        this.mOwner.hangup(this);
    }

    boolean isFull() {
        boolean bl = this.mConnections.size() == 5;
        return bl;
    }

    @Override
    public boolean isMultiparty() {
        ImsCall imsCall = this.getImsCall();
        if (imsCall == null) {
            return false;
        }
        return imsCall.isMultiparty();
    }

    public void maybeStopRingback() {
        if (this.mIsRingbackTonePlaying) {
            this.getPhone().stopRingbackTone();
            this.mIsRingbackTonePlaying = false;
        }
    }

    @UnsupportedAppUsage
    void merge(ImsPhoneCall imsPhoneCall, Call.State state) {
        Object object = this.getFirstConnection();
        if (object != null) {
            long l = ((ImsPhoneConnection)object).getConferenceConnectTime();
            if (l > 0L) {
                ((Connection)object).setConnectTime(l);
                ((Connection)object).setConnectTimeReal(((Connection)object).getConnectTimeReal());
            } else if (DBG) {
                Rlog.d((String)LOG_TAG, (String)"merge: conference connect time is 0");
            }
        }
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("merge(");
            ((StringBuilder)object).append(this.mCallContext);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(imsPhoneCall);
            ((StringBuilder)object).append("state = ");
            ((StringBuilder)object).append((Object)state);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    @UnsupportedAppUsage
    void onHangupLocal() {
        int n = this.mConnections.size();
        for (int i = 0; i < n; ++i) {
            ((ImsPhoneConnection)this.mConnections.get(i)).onHangupLocal();
        }
        this.mState = Call.State.DISCONNECTING;
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onHangupLocal : ");
            stringBuilder.append(this.mCallContext);
            stringBuilder.append(" state = ");
            stringBuilder.append((Object)this.mState);
            Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    void setMute(boolean bl) {
        Object object = this.getFirstConnection() == null ? null : this.getFirstConnection().getImsCall();
        if (object != null) {
            try {
                object.setMute(bl);
            }
            catch (ImsException imsException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setMute failed : ");
                ((StringBuilder)object).append(imsException.getMessage());
                Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void switchWith(ImsPhoneCall imsPhoneCall) {
        Object object;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("switchWith : switchCall = ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" withCall = ");
            ((StringBuilder)object).append(imsPhoneCall);
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        synchronized (ImsPhoneCall.class) {
            object = new ImsPhoneCall();
            ImsPhoneCall.super.takeOver(this);
            this.takeOver(imsPhoneCall);
            imsPhoneCall.takeOver((ImsPhoneCall)object);
        }
        this.mOwner.logState();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsPhoneCall ");
        stringBuilder.append(this.mCallContext);
        stringBuilder.append(" state: ");
        stringBuilder.append(this.mState.toString());
        stringBuilder.append(" ");
        if (this.mConnections.size() > 1) {
            stringBuilder.append(" ERROR_MULTIPLE ");
        }
        Iterator iterator = this.mConnections.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append((Connection)iterator.next());
            stringBuilder.append(" ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean update(ImsPhoneConnection object, ImsCall object2, Call.State state) {
        boolean bl = false;
        object = this.mState;
        if (state == Call.State.ALERTING) {
            if (this.mIsRingbackTonePlaying && !ImsPhoneCall.isLocalTone((ImsCall)object2)) {
                this.getPhone().stopRingbackTone();
                this.mIsRingbackTonePlaying = false;
            } else if (!this.mIsRingbackTonePlaying && ImsPhoneCall.isLocalTone((ImsCall)object2)) {
                this.getPhone().startRingbackTone();
                this.mIsRingbackTonePlaying = true;
            }
        } else if (this.mIsRingbackTonePlaying) {
            this.getPhone().stopRingbackTone();
            this.mIsRingbackTonePlaying = false;
        }
        if (state != this.mState && state != Call.State.DISCONNECTED) {
            this.mState = state;
            bl = true;
        } else if (state == Call.State.DISCONNECTED) {
            bl = true;
        }
        if (VDBG) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("update : ");
            ((StringBuilder)object2).append(this.mCallContext);
            ((StringBuilder)object2).append(" state: ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" --> ");
            ((StringBuilder)object2).append((Object)this.mState);
            Rlog.v((String)LOG_TAG, (String)((StringBuilder)object2).toString());
        }
        return bl;
    }
}

