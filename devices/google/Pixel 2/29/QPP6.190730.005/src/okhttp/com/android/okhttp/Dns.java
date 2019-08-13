/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public interface Dns {
    public static final Dns SYSTEM = new Dns(){

        @Override
        public List<InetAddress> lookup(String string) throws UnknownHostException {
            if (string != null) {
                return Arrays.asList(InetAddress.getAllByName(string));
            }
            throw new UnknownHostException("hostname == null");
        }
    };

    public List<InetAddress> lookup(String var1) throws UnknownHostException;

}

