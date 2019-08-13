/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractConscryptEngine;
import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.BufferAllocator;
import com.android.org.conscrypt.ConscryptEngine;
import com.android.org.conscrypt.HandshakeListener;
import com.android.org.conscrypt.Preconditions;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

final class Java8EngineWrapper
extends AbstractConscryptEngine {
    private final ConscryptEngine delegate;
    private BiFunction<SSLEngine, List<String>, String> selector;

    Java8EngineWrapper(ConscryptEngine conscryptEngine) {
        this.delegate = Preconditions.checkNotNull(conscryptEngine, "delegate");
    }

    static SSLEngine getDelegate(SSLEngine sSLEngine) {
        if (sSLEngine instanceof Java8EngineWrapper) {
            return ((Java8EngineWrapper)sSLEngine).delegate;
        }
        return sSLEngine;
    }

    private static ApplicationProtocolSelector toApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> object) {
        object = object == null ? null : new ApplicationProtocolSelector((BiFunction)object){
            final /* synthetic */ BiFunction val$selector;
            {
                this.val$selector = biFunction;
            }

            @Override
            public String selectApplicationProtocol(SSLEngine sSLEngine, List<String> list) {
                return (String)this.val$selector.apply(sSLEngine, list);
            }

            @Override
            public String selectApplicationProtocol(SSLSocket sSLSocket, List<String> list) {
                throw new UnsupportedOperationException();
            }
        };
        return object;
    }

    @Override
    public void beginHandshake() throws SSLException {
        this.delegate.beginHandshake();
    }

    @Override
    public void closeInbound() throws SSLException {
        this.delegate.closeInbound();
    }

    @Override
    public void closeOutbound() {
        this.delegate.closeOutbound();
    }

    @Override
    byte[] exportKeyingMaterial(String string, byte[] arrby, int n) throws SSLException {
        return this.delegate.exportKeyingMaterial(string, arrby, n);
    }

    @Override
    public String getApplicationProtocol() {
        return this.delegate.getApplicationProtocol();
    }

    @Override
    String[] getApplicationProtocols() {
        return this.delegate.getApplicationProtocols();
    }

    @Override
    byte[] getChannelId() throws SSLException {
        return this.delegate.getChannelId();
    }

    @Override
    public Runnable getDelegatedTask() {
        return this.delegate.getDelegatedTask();
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.delegate.getEnableSessionCreation();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.delegate.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.delegate.getEnabledProtocols();
    }

    @Override
    public String getHandshakeApplicationProtocol() {
        return this.delegate.getHandshakeApplicationProtocol();
    }

    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return this.selector;
    }

    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.delegate.getHandshakeStatus();
    }

    @Override
    String getHostname() {
        return this.delegate.getHostname();
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.delegate.getNeedClientAuth();
    }

    @Override
    public String getPeerHost() {
        return this.delegate.getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.delegate.getPeerPort();
    }

    @Override
    public SSLParameters getSSLParameters() {
        return this.delegate.getSSLParameters();
    }

    @Override
    public SSLSession getSession() {
        return this.delegate.getSession();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return this.delegate.getSupportedProtocols();
    }

    @Override
    byte[] getTlsUnique() {
        return this.delegate.getTlsUnique();
    }

    @Override
    public boolean getUseClientMode() {
        return this.delegate.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.delegate.getWantClientAuth();
    }

    @Override
    SSLSession handshakeSession() {
        return this.delegate.handshakeSession();
    }

    @Override
    public boolean isInboundDone() {
        return this.delegate.isInboundDone();
    }

    @Override
    public boolean isOutboundDone() {
        return this.delegate.isOutboundDone();
    }

    @Override
    int maxSealOverhead() {
        return this.delegate.maxSealOverhead();
    }

    @Override
    void setApplicationProtocolSelector(ApplicationProtocolSelector object) {
        ConscryptEngine conscryptEngine = this.delegate;
        object = object == null ? null : new ApplicationProtocolSelectorAdapter(this, (ApplicationProtocolSelector)object);
        conscryptEngine.setApplicationProtocolSelector((ApplicationProtocolSelectorAdapter)object);
    }

    @Override
    void setApplicationProtocols(String[] arrstring) {
        this.delegate.setApplicationProtocols(arrstring);
    }

    @Override
    void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.delegate.setBufferAllocator(bufferAllocator);
    }

    @Override
    void setChannelIdEnabled(boolean bl) {
        this.delegate.setChannelIdEnabled(bl);
    }

    @Override
    void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.delegate.setChannelIdPrivateKey(privateKey);
    }

    @Override
    public void setEnableSessionCreation(boolean bl) {
        this.delegate.setEnableSessionCreation(bl);
    }

    @Override
    public void setEnabledCipherSuites(String[] arrstring) {
        this.delegate.setEnabledCipherSuites(arrstring);
    }

    @Override
    public void setEnabledProtocols(String[] arrstring) {
        this.delegate.setEnabledProtocols(arrstring);
    }

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> biFunction) {
        this.selector = biFunction;
        this.setApplicationProtocolSelector(Java8EngineWrapper.toApplicationProtocolSelector(biFunction));
    }

    @Override
    void setHandshakeListener(HandshakeListener handshakeListener) {
        this.delegate.setHandshakeListener(handshakeListener);
    }

    @Override
    void setHostname(String string) {
        this.delegate.setHostname(string);
    }

    @Override
    public void setNeedClientAuth(boolean bl) {
        this.delegate.setNeedClientAuth(bl);
    }

    @Override
    public void setSSLParameters(SSLParameters sSLParameters) {
        this.delegate.setSSLParameters(sSLParameters);
    }

    @Override
    public void setUseClientMode(boolean bl) {
        this.delegate.setUseClientMode(bl);
    }

    @Override
    void setUseSessionTickets(boolean bl) {
        this.delegate.setUseSessionTickets(bl);
    }

    @Override
    public void setWantClientAuth(boolean bl) {
        this.delegate.setWantClientAuth(bl);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.delegate.unwrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] arrbyteBuffer) throws SSLException {
        return this.delegate.unwrap(byteBuffer, arrbyteBuffer);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] arrbyteBuffer, int n, int n2) throws SSLException {
        return this.delegate.unwrap(byteBuffer, arrbyteBuffer, n, n2);
    }

    @Override
    SSLEngineResult unwrap(ByteBuffer[] arrbyteBuffer, int n, int n2, ByteBuffer[] arrbyteBuffer2, int n3, int n4) throws SSLException {
        return this.delegate.unwrap(arrbyteBuffer, n, n2, arrbyteBuffer2, n3, n4);
    }

    @Override
    SSLEngineResult unwrap(ByteBuffer[] arrbyteBuffer, ByteBuffer[] arrbyteBuffer2) throws SSLException {
        return this.delegate.unwrap(arrbyteBuffer, arrbyteBuffer2);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.delegate.wrap(byteBuffer, byteBuffer2);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] arrbyteBuffer, int n, int n2, ByteBuffer byteBuffer) throws SSLException {
        return this.delegate.wrap(arrbyteBuffer, n, n2, byteBuffer);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] arrbyteBuffer, ByteBuffer byteBuffer) throws SSLException {
        return this.delegate.wrap(arrbyteBuffer, byteBuffer);
    }

}

