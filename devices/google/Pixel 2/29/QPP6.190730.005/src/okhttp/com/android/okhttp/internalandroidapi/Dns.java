/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internalandroidapi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public interface Dns {
    public List<InetAddress> lookup(String var1) throws UnknownHostException;
}

