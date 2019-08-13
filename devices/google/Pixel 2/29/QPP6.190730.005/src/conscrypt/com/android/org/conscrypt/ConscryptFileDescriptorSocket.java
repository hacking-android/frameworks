/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.ActiveSession;
import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.ArrayUtils;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.ConscryptSession;
import com.android.org.conscrypt.ExternalSession;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.NativeSsl;
import com.android.org.conscrypt.NativeSslSession;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.PSKKeyManager;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLNullSession;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import com.android.org.conscrypt.SessionSnapshot;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

class ConscryptFileDescriptorSocket
extends OpenSSLSocketImpl
implements NativeCrypto.SSLHandshakeCallbacks,
SSLParametersImpl.AliasChooser,
SSLParametersImpl.PSKCallbacks {
    private static final boolean DBG_STATE = false;
    private final ActiveSession activeSession;
    private OpenSSLKey channelIdPrivateKey;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

        @Override
        public ConscryptSession provideSession() {
            return ConscryptFileDescriptorSocket.this.provideSession();
        }
    }));
    private final Object guard = Platform.closeGuardGet();
    private int handshakeTimeoutMilliseconds = -1;
    private SSLInputStream is;
    private SSLOutputStream os;
    private final NativeSsl ssl;
    private final SSLParametersImpl sslParameters;
    private int state = 0;
    private int writeTimeoutMilliseconds = 0;

    ConscryptFileDescriptorSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    ConscryptFileDescriptorSocket(String string, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n);
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    ConscryptFileDescriptorSocket(String string, int n, InetAddress inetAddress, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n, inetAddress, n2);
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    ConscryptFileDescriptorSocket(InetAddress inetAddress, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n);
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    ConscryptFileDescriptorSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    ConscryptFileDescriptorSocket(Socket socket, String string, int n, boolean bl, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, string, n, bl);
        this.sslParameters = sSLParametersImpl;
        this.ssl = ConscryptFileDescriptorSocket.newSsl(sSLParametersImpl, this);
        this.activeSession = new ActiveSession(this.ssl, sSLParametersImpl.getSessionContext());
    }

    private void assertReadableOrWriteableState() {
        int n = this.state;
        if (n != 5 && n != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid state: ");
            stringBuilder.append(this.state);
            throw new AssertionError((Object)stringBuilder.toString());
        }
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private void closeUnderlyingSocket() throws IOException {
        super.close();
    }

    private void free() {
        if (!this.ssl.isClosed()) {
            this.ssl.close();
            Platform.closeGuardClose(this.guard);
        }
    }

    private static NativeSsl newSsl(SSLParametersImpl sSLParametersImpl, ConscryptFileDescriptorSocket conscryptFileDescriptorSocket) throws SSLException {
        return NativeSsl.newInstance(sSLParametersImpl, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket, conscryptFileDescriptorSocket);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ConscryptSession provideHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state < 2) return SSLNullSession.getNullSession();
            if (this.state >= 5) return SSLNullSession.getNullSession();
            return this.activeSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ConscryptSession provideSession() {
        boolean bl;
        boolean bl2 = false;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                if (this.closedSession == null) return SSLNullSession.getNullSession();
                return this.closedSession;
            }
            try {
                boolean bl3 = this.state >= 5;
                bl = bl3;
                if (!bl3) {
                    bl = bl3;
                    bl2 = bl3;
                    if (this.isConnected()) {
                        bl2 = bl3;
                        this.waitForHandshake();
                        bl = true;
                    }
                }
            }
            catch (IOException iOException) {
                bl = bl2;
            }
        }
        if (bl) return this.activeSession;
        return SSLNullSession.getNullSession();
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    private void shutdownAndFreeSslNative() throws IOException {
        try {
            Platform.blockGuardOnNetwork();
            this.ssl.shutdown(Platform.getFileDescriptor(this.socket));
        }
        catch (Throwable throwable) {
            this.free();
            this.closeUnderlyingSocket();
            throw throwable;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.free();
        this.closeUnderlyingSocket();
    }

    private void transitionTo(int n) {
        int n2;
        if (n == 8 && !this.ssl.isClosed() && (n2 = this.state) >= 2 && n2 < 8) {
            this.closedSession = new SessionSnapshot(this.activeSession);
        }
        this.state = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitForHandshake() throws IOException {
        this.startHandshake();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            int n;
            while (this.state != 5 && this.state != 4 && (n = this.state) != 8) {
                try {
                    this.ssl.wait();
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    IOException iOException = new IOException("Interrupted waiting for handshake", interruptedException);
                    throw iOException;
                }
            }
            if (this.state != 8) {
                return;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    @Override
    public final String chooseClientAlias(X509KeyManager x509KeyManager, X500Principal[] arrx500Principal, String[] arrstring) {
        return x509KeyManager.chooseClientAlias(arrstring, arrx500Principal, this);
    }

    @Override
    public final String chooseClientPSKIdentity(PSKKeyManager pSKKeyManager, String string) {
        return pSKKeyManager.chooseClientKeyIdentity(string, this);
    }

    @Override
    public final String chooseServerAlias(X509KeyManager x509KeyManager, String string) {
        return x509KeyManager.chooseServerAlias(string, null, this);
    }

    @Override
    public final String chooseServerPSKIdentityHint(PSKKeyManager pSKKeyManager) {
        return pSKKeyManager.chooseServerKeyIdentityHint(this);
    }

    @Override
    public final void clientCertificateRequested(byte[] arrby, int[] arrn, byte[][] arrby2) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(arrby, arrn, arrby2);
    }

    @Override
    public final int clientPSKKeyRequested(String string, byte[] arrby, byte[] arrby2) {
        return this.ssl.clientPSKKeyRequested(string, arrby, arrby2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void close() throws IOException {
        NativeSsl nativeSsl = this.ssl;
        if (nativeSsl == null) {
            return;
        }
        // MONITORENTER : nativeSsl
        if (this.state == 8) {
            // MONITOREXIT : nativeSsl
            return;
        }
        int n = this.state;
        this.transitionTo(8);
        if (n == 0) {
            this.free();
            this.closeUnderlyingSocket();
            this.ssl.notifyAll();
            // MONITOREXIT : nativeSsl
            return;
        }
        if (n != 5 && n != 4) {
            this.ssl.interrupt();
            this.ssl.notifyAll();
            // MONITOREXIT : nativeSsl
            return;
        }
        this.ssl.notifyAll();
        SSLInputStream sSLInputStream = this.is;
        SSLOutputStream sSLOutputStream = this.os;
        // MONITOREXIT : nativeSsl
        if (sSLInputStream != null || sSLOutputStream != null) {
            this.ssl.interrupt();
        }
        if (sSLInputStream != null) {
            sSLInputStream.awaitPendingOps();
        }
        if (sSLOutputStream != null) {
            sSLOutputStream.awaitPendingOps();
        }
        this.shutdownAndFreeSslNative();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    byte[] exportKeyingMaterial(String string, byte[] arrby, int n) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state >= 3 && this.state != 8) {
                return this.ssl.exportKeyingMaterial(string, arrby, n);
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void finalize() throws Throwable {
        block6 : {
            try {
                if (this.guard != null) {
                    Platform.closeGuardWarnIfOpen(this.guard);
                }
                if (this.ssl == null) break block6;
                NativeSsl nativeSsl = this.ssl;
                synchronized (nativeSsl) {
                    this.transitionTo(8);
                }
            }
            catch (Throwable throwable) {
                Object.super.finalize();
                throw throwable;
            }
        }
        Object.super.finalize();
    }

    @Override
    final SSLSession getActiveSession() {
        return this.activeSession;
    }

    @Override
    public final String getApplicationProtocol() {
        return SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
    }

    @Override
    final String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final byte[] getChannelId() throws SSLException {
        if (this.getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 5) {
                return this.ssl.getTlsChannelId();
            }
            IllegalStateException illegalStateException = new IllegalStateException("Channel ID is only available after handshake completes");
            throw illegalStateException;
        }
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final String getHandshakeApplicationProtocol() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state < 2) return null;
            if (this.state >= 5) return null;
            return this.getApplicationProtocol();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final SSLSession getHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state < 2) return null;
            if (this.state >= 5) return null;
            ExternalSession.Provider provider = new ExternalSession.Provider(){

                @Override
                public ConscryptSession provideSession() {
                    return ConscryptFileDescriptorSocket.this.provideHandshakeSession();
                }
            };
            SSLSession sSLSession = new ExternalSession(provider);
            return Platform.wrapSSLSession(sSLSession);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final InputStream getInputStream() throws IOException {
        SSLInputStream sSLInputStream;
        this.checkOpen();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                SocketException socketException = new SocketException("Socket is closed.");
                throw socketException;
            }
            if (this.is == null) {
                this.is = sSLInputStream = new SSLInputStream();
            }
            sSLInputStream = this.is;
        }
        this.waitForHandshake();
        return sSLInputStream;
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final OutputStream getOutputStream() throws IOException {
        SSLOutputStream sSLOutputStream;
        this.checkOpen();
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                SocketException socketException = new SocketException("Socket is closed.");
                throw socketException;
            }
            if (this.os == null) {
                this.os = sSLOutputStream = new SSLOutputStream();
            }
            sSLOutputStream = this.os;
        }
        this.waitForHandshake();
        return sSLOutputStream;
    }

    @Override
    public final SecretKey getPSKKey(PSKKeyManager pSKKeyManager, String string, String string2) {
        return pSKKeyManager.getKey(string, string2, this);
    }

    @Override
    public final SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        Platform.getSSLParameters(sSLParameters, this.sslParameters, this);
        return sSLParameters;
    }

    @Override
    public final SSLSession getSession() {
        return this.externalSession;
    }

    @Override
    public final int getSoWriteTimeout() throws SocketException {
        return this.writeTimeoutMilliseconds;
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public final String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    @Override
    public final boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override
    public final void onNewSessionEstablished(long l) {
        try {
            NativeCrypto.SSL_SESSION_up_ref(l);
            Object object = new NativeRef.SSL_SESSION(l);
            object = NativeSslSession.newInstance((NativeRef.SSL_SESSION)object, this.activeSession);
            this.sessionContext().cacheSession((NativeSslSession)object);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onSSLStateChange(int n, int n2) {
        if (n != 32) {
            return;
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                return;
            }
            this.transitionTo(5);
        }
        this.notifyHandshakeCompletedListeners();
        nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.notifyAll();
            return;
        }
    }

    @Override
    public final int serverPSKKeyRequested(String string, String string2, byte[] arrby) {
        return this.ssl.serverPSKKeyRequested(string, string2, arrby);
    }

    @Override
    public final long serverSessionRequested(byte[] arrby) {
        return 0L;
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector object) {
        object = object == null ? null : new ApplicationProtocolSelectorAdapter(this, (ApplicationProtocolSelector)object);
        this.setApplicationProtocolSelector((ApplicationProtocolSelectorAdapter)object);
    }

    @Override
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.sslParameters.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override
    final void setApplicationProtocols(String[] arrstring) {
        this.sslParameters.setApplicationProtocols(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setChannelIdEnabled(boolean bl) {
        if (this.getUseClientMode()) {
            throw new IllegalStateException("Client mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 0) {
                // MONITOREXIT [2, 3] lbl6 : MonitorExitStatement: MONITOREXIT : var2_2
                this.sslParameters.channelIdEnabled = bl;
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Could not enable/disable Channel ID after the initial handshake has begun.");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void setChannelIdPrivateKey(PrivateKey serializable) {
        if (!this.getUseClientMode()) throw new IllegalStateException("Server mode");
        Object object = this.ssl;
        // MONITORENTER : object
        if (this.state != 0) {
            serializable = new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
            throw serializable;
        }
        // MONITOREXIT : object
        if (serializable == null) {
            this.sslParameters.channelIdEnabled = false;
            this.channelIdPrivateKey = null;
            return;
        }
        this.sslParameters.channelIdEnabled = true;
        object = null;
        try {
            if (serializable instanceof ECKey) {
                object = ((ECKey)((Object)serializable)).getParams();
            }
            Object object2 = object;
            if (object == null) {
                object2 = OpenSSLECGroupContext.getCurveByName("prime256v1").getECParameterSpec();
            }
            this.channelIdPrivateKey = OpenSSLKey.fromECPrivateKeyForTLSStackOnly(serializable, (ECParameterSpec)object2);
            return;
        }
        catch (InvalidKeyException invalidKeyException) {
            // empty catch block
        }
    }

    @Override
    public final void setEnableSessionCreation(boolean bl) {
        this.sslParameters.setEnableSessionCreation(bl);
    }

    @Override
    public final void setEnabledCipherSuites(String[] arrstring) {
        this.sslParameters.setEnabledCipherSuites(arrstring);
    }

    @Override
    public final void setEnabledProtocols(String[] arrstring) {
        this.sslParameters.setEnabledProtocols(arrstring);
    }

    @Override
    public final void setHandshakeTimeout(int n) throws SocketException {
        this.handshakeTimeoutMilliseconds = n;
    }

    @UnsupportedAppUsage
    @Override
    public final void setHostname(String string) {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        boolean bl = string != null;
        sSLParametersImpl.setUseSni(bl);
        super.setHostname(string);
    }

    @Override
    public final void setNeedClientAuth(boolean bl) {
        this.sslParameters.setNeedClientAuth(bl);
    }

    @Override
    public final void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        Platform.setSSLParameters(sSLParameters, this.sslParameters, this);
    }

    @Override
    public final void setSoWriteTimeout(int n) throws SocketException {
        this.writeTimeoutMilliseconds = n;
        Platform.setSocketWriteTimeout(this, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setUseClientMode(boolean bl) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 0) {
                // MONITOREXIT [2, 3] lbl4 : MonitorExitStatement: MONITOREXIT : var2_2
                this.sslParameters.setUseClientMode(bl);
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Could not change the mode after the initial handshake has begun.");
            throw illegalArgumentException;
        }
    }

    @UnsupportedAppUsage
    @Override
    public final void setUseSessionTickets(boolean bl) {
        this.sslParameters.setUseSessionTickets(bl);
    }

    @Override
    public final void setWantClientAuth(boolean bl) {
        this.sslParameters.setWantClientAuth(bl);
    }

    /*
     * Exception decompiling
     */
    @Override
    public final void startHandshake() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [22[TRYBLOCK]], but top level block is 53[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void verifyCertificateChain(byte[][] var1_1, String var2_4) throws CertificateException {
        if (var1_1 /* !! */  == null) ** GOTO lbl15
        try {
            if (var1_1 /* !! */ .length != 0) {
                var1_2 = SSLUtils.decodeX509CertificateChain(var1_1 /* !! */ );
                var3_8 = this.sslParameters.getX509TrustManager();
                if (var3_8 == null) {
                    var1_3 = new CertificateException("No X.509 TrustManager");
                    throw var1_3;
                }
                this.activeSession.onPeerCertificatesReceived(this.getHostnameOrIP(), this.getPort(), var1_2);
                if (this.getUseClientMode()) {
                    Platform.checkServerTrusted(var3_8, var1_2, (String)var2_7, this);
                    return;
                }
                Platform.checkClientTrusted(var3_8, var1_2, var1_2[0].getPublicKey().getAlgorithm(), this);
                return;
            }
lbl15: // 3 sources:
            var1_4 = new CertificateException("Peer sent no certificate");
            throw var1_4;
        }
        catch (Exception var1_5) {
            throw new CertificateException(var1_5);
        }
        catch (CertificateException var1_6) {
            throw var1_6;
        }
    }

    private class SSLInputStream
    extends InputStream {
        private final Object readLock = new Object();

        SSLInputStream() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void awaitPendingOps() {
            Object object = this.readLock;
            // MONITORENTER : object
            // MONITOREXIT : object
        }

        @Override
        public int read() throws IOException {
            byte[] arrby = new byte[1];
            int n = this.read(arrby, 0, 1);
            int n2 = -1;
            if (n != -1) {
                n2 = arrby[0] & 255;
            }
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read(byte[] object, int n, int n2) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(((byte[])object).length, n, n2);
            if (n2 == 0) {
                return 0;
            }
            Object object2 = this.readLock;
            synchronized (object2) {
                Object object3 = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (object3) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        object = new SocketException("socket is closed");
                        throw object;
                    }
                }
                n = ConscryptFileDescriptorSocket.this.ssl.read(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), (byte[])object, n, n2, ConscryptFileDescriptorSocket.this.getSoTimeout());
                if (n == -1) {
                    object = ConscryptFileDescriptorSocket.this.ssl;
                    synchronized (object) {
                        if (ConscryptFileDescriptorSocket.this.state == 8) {
                            object3 = new SocketException("socket is closed");
                            throw object3;
                        }
                    }
                }
                return n;
            }
        }
    }

    private class SSLOutputStream
    extends OutputStream {
        private final Object writeLock = new Object();

        SSLOutputStream() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void awaitPendingOps() {
            Object object = this.writeLock;
            // MONITORENTER : object
            // MONITOREXIT : object
        }

        @Override
        public void write(int n) throws IOException {
            this.write(new byte[]{(byte)(n & 255)});
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(byte[] object, int n, int n2) throws IOException {
            Platform.blockGuardOnNetwork();
            ConscryptFileDescriptorSocket.this.checkOpen();
            ArrayUtils.checkOffsetAndCount(((byte[])object).length, n, n2);
            if (n2 == 0) {
                return;
            }
            Object object2 = this.writeLock;
            synchronized (object2) {
                Object object3 = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (object3) {
                    if (ConscryptFileDescriptorSocket.this.state == 8) {
                        object = new SocketException("socket is closed");
                        throw object;
                    }
                }
                ConscryptFileDescriptorSocket.this.ssl.write(Platform.getFileDescriptor(ConscryptFileDescriptorSocket.this.socket), (byte[])object, n, n2, ConscryptFileDescriptorSocket.this.writeTimeoutMilliseconds);
                object = ConscryptFileDescriptorSocket.this.ssl;
                synchronized (object) {
                    if (ConscryptFileDescriptorSocket.this.state != 8) {
                        return;
                    }
                    object3 = new SocketException("socket is closed");
                    throw object3;
                }
            }
        }
    }

}

