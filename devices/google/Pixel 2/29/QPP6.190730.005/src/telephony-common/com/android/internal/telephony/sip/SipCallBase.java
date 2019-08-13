/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.sip;

import com.android.internal.telephony.Call;
import com.android.internal.telephony.Connection;
import java.util.ArrayList;
import java.util.List;

abstract class SipCallBase
extends Call {
    SipCallBase() {
    }

    @Override
    public List<Connection> getConnections() {
        return this.mConnections;
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mState.toString());
        stringBuilder.append(":");
        stringBuilder.append(Object.super.toString());
        return stringBuilder.toString();
    }
}

