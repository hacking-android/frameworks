/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

public abstract class SSLEngine {
    private String peerHost = null;
    private int peerPort = -1;

    protected SSLEngine() {
    }

    protected SSLEngine(String string, int n) {
        this.peerHost = string;
        this.peerPort = n;
    }

    public abstract void beginHandshake() throws SSLException;

    public abstract void closeInbound() throws SSLException;

    public abstract void closeOutbound();

    public String getApplicationProtocol() {
        throw new UnsupportedOperationException();
    }

    public abstract Runnable getDelegatedTask();

    public abstract boolean getEnableSessionCreation();

    public abstract String[] getEnabledCipherSuites();

    public abstract String[] getEnabledProtocols();

    public String getHandshakeApplicationProtocol() {
        throw new UnsupportedOperationException();
    }

    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        throw new UnsupportedOperationException();
    }

    public SSLSession getHandshakeSession() {
        throw new UnsupportedOperationException();
    }

    public abstract SSLEngineResult.HandshakeStatus getHandshakeStatus();

    public abstract boolean getNeedClientAuth();

    public String getPeerHost() {
        return this.peerHost;
    }

    public int getPeerPort() {
        return this.peerPort;
    }

    public SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = new SSLParameters();
        sSLParameters.setCipherSuites(this.getEnabledCipherSuites());
        sSLParameters.setProtocols(this.getEnabledProtocols());
        if (this.getNeedClientAuth()) {
            sSLParameters.setNeedClientAuth(true);
        } else if (this.getWantClientAuth()) {
            sSLParameters.setWantClientAuth(true);
        }
        return sSLParameters;
    }

    public abstract SSLSession getSession();

    public abstract String[] getSupportedCipherSuites();

    public abstract String[] getSupportedProtocols();

    public abstract boolean getUseClientMode();

    public abstract boolean getWantClientAuth();

    public abstract boolean isInboundDone();

    public abstract boolean isOutboundDone();

    public abstract void setEnableSessionCreation(boolean var1);

    public abstract void setEnabledCipherSuites(String[] var1);

    public abstract void setEnabledProtocols(String[] var1);

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> biFunction) {
        throw new UnsupportedOperationException();
    }

    public abstract void setNeedClientAuth(boolean var1);

    public void setSSLParameters(SSLParameters sSLParameters) {
        String[] arrstring = sSLParameters.getCipherSuites();
        if (arrstring != null) {
            this.setEnabledCipherSuites(arrstring);
        }
        if ((arrstring = sSLParameters.getProtocols()) != null) {
            this.setEnabledProtocols(arrstring);
        }
        if (sSLParameters.getNeedClientAuth()) {
            this.setNeedClientAuth(true);
        } else if (sSLParameters.getWantClientAuth()) {
            this.setWantClientAuth(true);
        } else {
            this.setWantClientAuth(false);
        }
    }

    public abstract void setUseClientMode(boolean var1);

    public abstract void setWantClientAuth(boolean var1);

    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.unwrap(byteBuffer, new ByteBuffer[]{byteBuffer2}, 0, 1);
    }

    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] arrbyteBuffer) throws SSLException {
        if (arrbyteBuffer != null) {
            return this.unwrap(byteBuffer, arrbyteBuffer, 0, arrbyteBuffer.length);
        }
        throw new IllegalArgumentException("dsts == null");
    }

    public abstract SSLEngineResult unwrap(ByteBuffer var1, ByteBuffer[] var2, int var3, int var4) throws SSLException;

    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.wrap(new ByteBuffer[]{byteBuffer}, 0, 1, byteBuffer2);
    }

    public abstract SSLEngineResult wrap(ByteBuffer[] var1, int var2, int var3, ByteBuffer var4) throws SSLException;

    public SSLEngineResult wrap(ByteBuffer[] arrbyteBuffer, ByteBuffer byteBuffer) throws SSLException {
        if (arrbyteBuffer != null) {
            return this.wrap(arrbyteBuffer, 0, arrbyteBuffer.length, byteBuffer);
        }
        throw new IllegalArgumentException("src == null");
    }
}

