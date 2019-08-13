/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp;

import com.android.okhttp.Handshake;
import com.android.okhttp.Protocol;
import com.android.okhttp.Route;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.Socket;

public interface Connection {
    public Handshake getHandshake();

    public Protocol getProtocol();

    public Route getRoute();

    @UnsupportedAppUsage
    public Socket getSocket();
}

