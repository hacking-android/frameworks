/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

interface InetAddressImpl {
    public InetAddress anyLocalAddress();

    public void clearAddressCache();

    public String getHostByAddr(byte[] var1) throws UnknownHostException;

    public boolean isReachable(InetAddress var1, int var2, NetworkInterface var3, int var4) throws IOException;

    public InetAddress[] lookupAllHostAddr(String var1, int var2) throws UnknownHostException;

    public InetAddress[] loopbackAddresses();
}

