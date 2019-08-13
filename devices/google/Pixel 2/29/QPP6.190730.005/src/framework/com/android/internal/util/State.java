/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Message;
import com.android.internal.util.IState;

public class State
implements IState {
    @UnsupportedAppUsage
    protected State() {
    }

    @UnsupportedAppUsage
    @Override
    public void enter() {
    }

    @UnsupportedAppUsage
    @Override
    public void exit() {
    }

    @UnsupportedAppUsage
    @Override
    public String getName() {
        String string2 = this.getClass().getName();
        return string2.substring(string2.lastIndexOf(36) + 1);
    }

    @UnsupportedAppUsage
    @Override
    public boolean processMessage(Message message) {
        return false;
    }
}

