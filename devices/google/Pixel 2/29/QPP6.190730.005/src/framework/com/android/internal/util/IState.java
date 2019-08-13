/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Message;

public interface IState {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;

    public void enter();

    public void exit();

    @UnsupportedAppUsage
    public String getName();

    public boolean processMessage(Message var1);
}

