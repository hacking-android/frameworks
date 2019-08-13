/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telecom.ConferenceParticipant
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telecom.ConferenceParticipant;
import android.telephony.Rlog;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.Phone;
import java.util.ArrayList;
import java.util.List;

public abstract class Call {
    protected final String LOG_TAG;
    @UnsupportedAppUsage
    public ArrayList<Connection> mConnections = new ArrayList();
    @UnsupportedAppUsage
    public State mState = State.IDLE;

    public Call() {
        this.LOG_TAG = "Call";
    }

    public static State stateFromDCState(DriverCall.State state) {
        switch (state) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("illegal call state:");
                stringBuilder.append((Object)state);
                throw new RuntimeException(stringBuilder.toString());
            }
            case WAITING: {
                return State.WAITING;
            }
            case INCOMING: {
                return State.INCOMING;
            }
            case ALERTING: {
                return State.ALERTING;
            }
            case DIALING: {
                return State.DIALING;
            }
            case HOLDING: {
                return State.HOLDING;
            }
            case ACTIVE: 
        }
        return State.ACTIVE;
    }

    public void clearDisconnected() {
        for (int i = this.mConnections.size() - 1; i >= 0; --i) {
            if (this.mConnections.get(i).getState() != State.DISCONNECTED) continue;
            this.mConnections.remove(i);
        }
        if (this.mConnections.size() == 0) {
            this.setState(State.IDLE);
        }
    }

    public List<ConferenceParticipant> getConferenceParticipants() {
        return null;
    }

    @UnsupportedAppUsage
    public abstract List<Connection> getConnections();

    public long getEarliestConnectTime() {
        long l = Long.MAX_VALUE;
        List<Connection> list = this.getConnections();
        if (list.size() == 0) {
            return 0L;
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            long l2 = list.get(i).getConnectTime();
            if (l2 >= l) continue;
            l = l2;
        }
        return l;
    }

    @UnsupportedAppUsage
    public Connection getEarliestConnection() {
        long l = Long.MAX_VALUE;
        Connection connection = null;
        List<Connection> list = this.getConnections();
        if (list.size() == 0) {
            return null;
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Connection connection2 = list.get(i);
            long l2 = connection2.getCreateTime();
            long l3 = l;
            if (l2 < l) {
                connection = connection2;
                l3 = l2;
            }
            l = l3;
        }
        return connection;
    }

    public long getEarliestCreateTime() {
        long l = Long.MAX_VALUE;
        List<Connection> list = this.getConnections();
        if (list.size() == 0) {
            return 0L;
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            long l2 = list.get(i).getCreateTime();
            if (l2 >= l) continue;
            l = l2;
        }
        return l;
    }

    @UnsupportedAppUsage
    public Connection getLatestConnection() {
        List<Connection> list = this.getConnections();
        if (list.size() == 0) {
            return null;
        }
        long l = 0L;
        Connection connection = null;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Connection connection2 = list.get(i);
            long l2 = connection2.getCreateTime();
            long l3 = l;
            if (l2 > l) {
                connection = connection2;
                l3 = l2;
            }
            l = l3;
        }
        return connection;
    }

    @UnsupportedAppUsage
    public abstract Phone getPhone();

    @UnsupportedAppUsage
    public State getState() {
        return this.mState;
    }

    @UnsupportedAppUsage
    public abstract void hangup() throws CallStateException;

    public void hangupIfAlive() {
        if (this.getState().isAlive()) {
            try {
                this.hangup();
            }
            catch (CallStateException callStateException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" hangupIfActive: caught ");
                stringBuilder.append(callStateException);
                Rlog.w((String)"Call", (String)stringBuilder.toString());
            }
        }
    }

    public boolean hasConnection(Connection connection) {
        boolean bl = connection.getCall() == this;
        return bl;
    }

    public boolean hasConnections() {
        List<Connection> list = this.getConnections();
        boolean bl = false;
        if (list == null) {
            return false;
        }
        if (list.size() > 0) {
            bl = true;
        }
        return bl;
    }

    public boolean isDialingOrAlerting() {
        return this.getState().isDialing();
    }

    @UnsupportedAppUsage
    public boolean isIdle() {
        return this.getState().isAlive() ^ true;
    }

    @UnsupportedAppUsage
    public abstract boolean isMultiparty();

    public boolean isRinging() {
        return this.getState().isRinging();
    }

    protected void setState(State state) {
        this.mState = state;
    }

    public static enum SrvccState {
        NONE,
        STARTED,
        COMPLETED,
        FAILED,
        CANCELED;
        
    }

    public static enum State {
        IDLE,
        ACTIVE,
        HOLDING,
        DIALING,
        ALERTING,
        INCOMING,
        WAITING,
        DISCONNECTED,
        DISCONNECTING;
        

        @UnsupportedAppUsage
        public boolean isAlive() {
            boolean bl = this != IDLE && this != DISCONNECTED && this != DISCONNECTING;
            return bl;
        }

        public boolean isDialing() {
            boolean bl = this == DIALING || this == ALERTING;
            return bl;
        }

        @UnsupportedAppUsage
        public boolean isRinging() {
            boolean bl = this == INCOMING || this == WAITING;
            return bl;
        }
    }

}

