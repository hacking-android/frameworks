/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.imsphone.ImsExternalConnection;
import java.util.ArrayList;
import java.util.List;

public class ImsExternalCall
extends Call {
    private Phone mPhone;

    @UnsupportedAppUsage
    public ImsExternalCall(Phone phone, ImsExternalConnection imsExternalConnection) {
        this.mPhone = phone;
        this.mConnections.add(imsExternalConnection);
    }

    @Override
    public List<Connection> getConnections() {
        return this.mConnections;
    }

    @Override
    public Phone getPhone() {
        return this.mPhone;
    }

    @Override
    public void hangup() throws CallStateException {
    }

    @Override
    public boolean isMultiparty() {
        return false;
    }

    public void setActive() {
        this.setState(Call.State.ACTIVE);
    }

    public void setTerminated() {
        this.setState(Call.State.DISCONNECTED);
    }
}

