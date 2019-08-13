/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.BufferAllocator;
import com.android.org.conscrypt.HandshakeListener;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

abstract class AbstractConscryptEngine
extends SSLEngine {
    AbstractConscryptEngine() {
    }

    abstract byte[] exportKeyingMaterial(String var1, byte[] var2, int var3) throws SSLException;

    public abstract String getApplicationProtocol();

    abstract String[] getApplicationProtocols();

    abstract byte[] getChannelId() throws SSLException;

    public abstract String getHandshakeApplicationProtocol();

    @Override
    public final SSLSession getHandshakeSession() {
        return this.handshakeSession();
    }

    abstract String getHostname();

    @Override
    public abstract String getPeerHost();

    @Override
    public abstract int getPeerPort();

    abstract byte[] getTlsUnique();

    abstract SSLSession handshakeSession();

    abstract int maxSealOverhead();

    abstract void setApplicationProtocolSelector(ApplicationProtocolSelector var1);

    abstract void setApplicationProtocols(String[] var1);

    abstract void setBufferAllocator(BufferAllocator var1);

    abstract void setChannelIdEnabled(boolean var1);

    abstract void setChannelIdPrivateKey(PrivateKey var1);

    abstract void setHandshakeListener(HandshakeListener var1);

    abstract void setHostname(String var1);

    abstract void setUseSessionTickets(boolean var1);

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer var1, ByteBuffer var2) throws SSLException;

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer var1, ByteBuffer[] var2) throws SSLException;

    @Override
    public abstract SSLEngineResult unwrap(ByteBuffer var1, ByteBuffer[] var2, int var3, int var4) throws SSLException;

    abstract SSLEngineResult unwrap(ByteBuffer[] var1, int var2, int var3, ByteBuffer[] var4, int var5, int var6) throws SSLException;

    abstract SSLEngineResult unwrap(ByteBuffer[] var1, ByteBuffer[] var2) throws SSLException;

    @Override
    public abstract SSLEngineResult wrap(ByteBuffer var1, ByteBuffer var2) throws SSLException;

    @Override
    public abstract SSLEngineResult wrap(ByteBuffer[] var1, int var2, int var3, ByteBuffer var4) throws SSLException;
}

