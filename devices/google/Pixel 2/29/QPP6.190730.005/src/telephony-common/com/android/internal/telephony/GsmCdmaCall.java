/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaConnection;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.Phone;
import java.util.ArrayList;
import java.util.List;

public class GsmCdmaCall
extends Call {
    GsmCdmaCallTracker mOwner;

    public GsmCdmaCall(GsmCdmaCallTracker gsmCdmaCallTracker) {
        this.mOwner = gsmCdmaCallTracker;
    }

    public void attach(Connection connection, DriverCall driverCall) {
        this.mConnections.add(connection);
        this.mState = GsmCdmaCall.stateFromDCState(driverCall.state);
    }

    @UnsupportedAppUsage
    public void attachFake(Connection connection, Call.State state) {
        this.mConnections.add(connection);
        this.mState = state;
    }

    public boolean connectionDisconnected(GsmCdmaConnection gsmCdmaConnection) {
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
                return true;
            }
        }
        return false;
    }

    public void detach(GsmCdmaConnection gsmCdmaConnection) {
        this.mConnections.remove(gsmCdmaConnection);
        if (this.mConnections.size() == 0) {
            this.mState = Call.State.IDLE;
        }
    }

    @Override
    public List<Connection> getConnections() {
        return this.mConnections;
    }

    @Override
    public Phone getPhone() {
        return this.mOwner.getPhone();
    }

    @Override
    public void hangup() throws CallStateException {
        this.mOwner.hangup(this);
    }

    boolean isFull() {
        boolean bl = this.mConnections.size() == this.mOwner.getMaxConnectionsPerCall();
        return bl;
    }

    @Override
    public boolean isMultiparty() {
        int n = this.mConnections.size();
        boolean bl = true;
        if (n <= 1) {
            bl = false;
        }
        return bl;
    }

    void onHangupLocal() {
        int n = this.mConnections.size();
        for (int i = 0; i < n; ++i) {
            ((GsmCdmaConnection)this.mConnections.get(i)).onHangupLocal();
        }
        this.mState = Call.State.DISCONNECTING;
    }

    public String toString() {
        return this.mState.toString();
    }

    boolean update(GsmCdmaConnection object, DriverCall driverCall) {
        boolean bl = false;
        object = GsmCdmaCall.stateFromDCState(driverCall.state);
        if (object != this.mState) {
            this.mState = object;
            bl = true;
        }
        return bl;
    }
}

