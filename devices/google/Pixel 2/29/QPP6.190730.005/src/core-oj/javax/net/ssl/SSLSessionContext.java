/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.util.Enumeration;
import javax.net.ssl.SSLSession;

public interface SSLSessionContext {
    public Enumeration<byte[]> getIds();

    public SSLSession getSession(byte[] var1);

    public int getSessionCacheSize();

    public int getSessionTimeout();

    public void setSessionCacheSize(int var1) throws IllegalArgumentException;

    public void setSessionTimeout(int var1) throws IllegalArgumentException;
}

