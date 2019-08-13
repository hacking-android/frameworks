/*
 * Decompiled with CFR 0.145.
 */
package sun.net.spi.nameservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface NameService {
    public String getHostByAddr(byte[] var1) throws UnknownHostException;

    public InetAddress[] lookupAllHostAddr(String var1, int var2) throws UnknownHostException;
}

