/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractConscryptEngine;
import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.ActiveSession;
import com.android.org.conscrypt.AllocatedBuffer;
import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.BufferAllocator;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.ConscryptSession;
import com.android.org.conscrypt.ExternalSession;
import com.android.org.conscrypt.HandshakeListener;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.NativeSsl;
import com.android.org.conscrypt.NativeSslSession;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.PSKKeyManager;
import com.android.org.conscrypt.PeerInfoProvider;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.Preconditions;
import com.android.org.conscrypt.SSLNullSession;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import com.android.org.conscrypt.SessionSnapshot;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
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
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

final class ConscryptEngine
extends AbstractConscryptEngine
implements NativeCrypto.SSLHandshakeCallbacks,
SSLParametersImpl.AliasChooser,
SSLParametersImpl.PSKCallbacks {
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING;
    private static final SSLEngineResult NEED_UNWRAP_CLOSED;
    private static final SSLEngineResult NEED_UNWRAP_OK;
    private static final SSLEngineResult NEED_WRAP_CLOSED;
    private static final SSLEngineResult NEED_WRAP_OK;
    private static BufferAllocator defaultBufferAllocator;
    private ActiveSession activeSession;
    private BufferAllocator bufferAllocator = defaultBufferAllocator;
    private OpenSSLKey channelIdPrivateKey;
    private SessionSnapshot closedSession;
    private final SSLSession externalSession = Platform.wrapSSLSession(new ExternalSession(new ExternalSession.Provider(){

        @Override
        public ConscryptSession provideSession() {
            return ConscryptEngine.this.provideSession();
        }
    }));
    private SSLException handshakeException;
    private boolean handshakeFinished;
    private HandshakeListener handshakeListener;
    private ByteBuffer lazyDirectBuffer;
    private int maxSealOverhead;
    private final NativeSsl.BioWrapper networkBio;
    private String peerHostname;
    private final PeerInfoProvider peerInfoProvider;
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private final NativeSsl ssl;
    private final SSLParametersImpl sslParameters;
    private int state = 0;

    static {
        NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
        defaultBufferAllocator = null;
    }

    ConscryptEngine(SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = PeerInfoProvider.nullProvider();
        this.ssl = ConscryptEngine.newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    ConscryptEngine(SSLParametersImpl sSLParametersImpl, PeerInfoProvider peerInfoProvider) {
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = Preconditions.checkNotNull(peerInfoProvider, "peerInfoProvider");
        this.ssl = ConscryptEngine.newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    ConscryptEngine(String string, int n, SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = sSLParametersImpl;
        this.peerInfoProvider = PeerInfoProvider.forHostAndPort(string, n);
        this.ssl = ConscryptEngine.newSsl(sSLParametersImpl, this);
        this.networkBio = this.ssl.newBio();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void beginHandshakeInternal() throws SSLException {
        Throwable throwable2222;
        NativeSslSession nativeSslSession;
        int n = this.state;
        if (n == 0) throw new IllegalStateException("Client/server mode must be set before handshake");
        if (n != 1) {
            if (n == 6) throw new IllegalStateException("Engine has already been closed");
            if (n == 7) throw new IllegalStateException("Engine has already been closed");
            if (n == 8) throw new IllegalStateException("Engine has already been closed");
            return;
        }
        this.transitionTo(2);
        this.ssl.initialize(this.getHostname(), this.channelIdPrivateKey);
        if (this.getUseClientMode() && (nativeSslSession = this.clientSessionContext().getCachedSession(this.getHostname(), this.getPeerPort(), this.sslParameters)) != null) {
            nativeSslSession.offerToResume(this.ssl);
        }
        this.maxSealOverhead = this.ssl.getMaxSealOverhead();
        this.handshake();
        if (!false) return;
        this.closeAndFreeResources();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                if (!iOException.getMessage().contains("unexpected CCS")) throw SSLUtils.toSSLHandshakeException(iOException);
                Platform.logEvent(String.format("ssl_unexpected_ccs: host=%s", this.getPeerHost()));
                throw SSLUtils.toSSLHandshakeException(iOException);
            }
        }
        if (!true) throw throwable2222;
        this.closeAndFreeResources();
        throw throwable2222;
    }

    private static int calcDstsLength(ByteBuffer[] arrbyteBuffer, int n, int n2) {
        int n3 = 0;
        for (int i = 0; i < arrbyteBuffer.length; ++i) {
            ByteBuffer byteBuffer = arrbyteBuffer[i];
            boolean bl = byteBuffer != null;
            Preconditions.checkArgument(bl, "dsts[%d] is null", i);
            if (!byteBuffer.isReadOnly()) {
                int n4 = n3;
                if (i >= n) {
                    n4 = n3;
                    if (i < n + n2) {
                        n4 = n3 + byteBuffer.remaining();
                    }
                }
                n3 = n4;
                continue;
            }
            throw new ReadOnlyBufferException();
        }
        return n3;
    }

    private static long calcSrcsLength(ByteBuffer[] object, int n, int n2) {
        long l = 0L;
        while (n < n2) {
            ByteBuffer byteBuffer = object[n];
            if (byteBuffer != null) {
                l += (long)byteBuffer.remaining();
                ++n;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("srcs[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("] is null");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return l;
    }

    private ClientSessionContext clientSessionContext() {
        return this.sslParameters.getClientSessionContext();
    }

    private void closeAll() throws SSLException {
        this.closeOutbound();
        this.closeInbound();
    }

    private void closeAndFreeResources() {
        this.transitionTo(8);
        if (!this.ssl.isClosed()) {
            this.ssl.close();
            this.networkBio.close();
        }
    }

    private SSLException convertException(Throwable throwable) {
        if (!(throwable instanceof SSLHandshakeException) && this.handshakeFinished) {
            return SSLUtils.toSSLException(throwable);
        }
        return SSLUtils.toSSLHandshakeException(throwable);
    }

    private long directByteBufferAddress(ByteBuffer byteBuffer, int n) {
        return NativeCrypto.getDirectBufferAddress(byteBuffer) + (long)n;
    }

    private void finishHandshake() throws SSLException {
        this.handshakeFinished = true;
        HandshakeListener handshakeListener = this.handshakeListener;
        if (handshakeListener != null) {
            handshakeListener.onHandshakeFinished();
        }
    }

    static BufferAllocator getDefaultBufferAllocator() {
        return defaultBufferAllocator;
    }

    private SSLEngineResult.Status getEngineStatus() {
        int n = this.state;
        if (n != 6 && n != 7 && n != 8) {
            return SSLEngineResult.Status.OK;
        }
        return SSLEngineResult.Status.CLOSED;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int n) {
        SSLEngineResult.HandshakeStatus handshakeStatus = !this.handshakeFinished ? ConscryptEngine.pendingStatus(n) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        return handshakeStatus;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatusInternal() {
        if (this.handshakeFinished) {
            return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        }
        switch (this.state) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected engine state: ");
                stringBuilder.append(this.state);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 3: {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            case 2: {
                return ConscryptEngine.pendingStatus(this.pendingOutboundEncryptedBytes());
            }
            case 0: 
            case 1: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
        }
        return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private ByteBuffer getOrCreateLazyDirectBuffer() {
        if (this.lazyDirectBuffer == null) {
            this.lazyDirectBuffer = ByteBuffer.allocateDirect(Math.max(16384, 16709));
        }
        this.lazyDirectBuffer.clear();
        return this.lazyDirectBuffer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        try {
            block9 : {
                if (this.handshakeException != null) {
                    if (this.pendingOutboundEncryptedBytes() > 0) {
                        return SSLEngineResult.HandshakeStatus.NEED_WRAP;
                    }
                    SSLException sSLException = this.handshakeException;
                    this.handshakeException = null;
                    throw sSLException;
                }
                int n = this.ssl.doHandshake();
                if (n == 2) return ConscryptEngine.pendingStatus(this.pendingOutboundEncryptedBytes());
                if (n == 3) break block9;
                this.activeSession.onPeerCertificateAvailable(this.getPeerHost(), this.getPeerPort());
                this.finishHandshake();
                return SSLEngineResult.HandshakeStatus.FINISHED;
            }
            try {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            catch (IOException iOException) {
                this.sendSSLShutdown();
                throw iOException;
            }
            catch (SSLException sSLException) {
                if (this.pendingOutboundEncryptedBytes() > 0) {
                    this.handshakeException = sSLException;
                    return SSLEngineResult.HandshakeStatus.NEED_WRAP;
                }
                this.sendSSLShutdown();
                throw sSLException;
            }
        }
        catch (Exception exception) {
            throw SSLUtils.toSSLHandshakeException(exception);
        }
    }

    private boolean isHandshakeStarted() {
        int n = this.state;
        return n != 0 && n != 1;
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        if (!this.handshakeFinished && handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            return this.handshake();
        }
        return handshakeStatus;
    }

    private SSLEngineResult newResult(int n, int n2, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        SSLEngineResult.Status status = this.getEngineStatus();
        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
            handshakeStatus = this.getHandshakeStatusInternal();
        }
        return new SSLEngineResult(status, this.mayFinishHandshake(handshakeStatus), n, n2);
    }

    private static NativeSsl newSsl(SSLParametersImpl object, ConscryptEngine conscryptEngine) {
        try {
            object = NativeSsl.newInstance((SSLParametersImpl)object, conscryptEngine, conscryptEngine, conscryptEngine);
            return object;
        }
        catch (SSLException sSLException) {
            throw new RuntimeException(sSLException);
        }
    }

    private SSLException newSslExceptionWithMessage(String string) {
        if (!this.handshakeFinished) {
            return new SSLException(string);
        }
        return new SSLHandshakeException(string);
    }

    private int pendingInboundCleartextBytes() {
        return this.ssl.getPendingReadableBytes();
    }

    private int pendingOutboundEncryptedBytes() {
        return this.networkBio.getPendingWrittenBytes();
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int n) {
        SSLEngineResult.HandshakeStatus handshakeStatus = n > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
        return handshakeStatus;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ConscryptSession provideHandshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 2) return SSLNullSession.getNullSession();
            return this.activeSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ConscryptSession provideSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) {
                if (this.closedSession == null) return SSLNullSession.getNullSession();
                return this.closedSession;
            }
            if (this.state >= 3) return this.activeSession;
            return SSLNullSession.getNullSession();
        }
    }

    private int readEncryptedData(ByteBuffer byteBuffer, int n) throws SSLException {
        int n2;
        block4 : {
            block5 : {
                int n3;
                n2 = 0;
                try {
                    n3 = byteBuffer.position();
                    if (byteBuffer.remaining() < n) break block4;
                    n = Math.min(n, byteBuffer.limit() - n3);
                    if (!byteBuffer.isDirect()) break block5;
                    n2 = n = this.readEncryptedDataDirect(byteBuffer, n3, n);
                    if (n <= 0) break block4;
                }
                catch (Exception exception) {
                    throw this.convertException(exception);
                }
                byteBuffer.position(n3 + n);
                n2 = n;
                break block4;
            }
            n2 = this.readEncryptedDataHeap(byteBuffer, n);
        }
        return n2;
    }

    private int readEncryptedDataDirect(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        return this.networkBio.readDirectByteBuffer(this.directByteBufferAddress(byteBuffer, n), n2);
    }

    private int readEncryptedDataHeap(ByteBuffer byteBuffer, int n) throws IOException {
        AllocatedBuffer allocatedBuffer;
        block13 : {
            AllocatedBuffer allocatedBuffer2;
            Object object;
            block12 : {
                block11 : {
                    object = null;
                    allocatedBuffer = null;
                    allocatedBuffer2 = object;
                    try {
                        if (this.bufferAllocator == null) break block11;
                        allocatedBuffer2 = object;
                    }
                    catch (Throwable throwable) {
                        if (allocatedBuffer2 != null) {
                            allocatedBuffer2.release();
                        }
                        throw throwable;
                    }
                    allocatedBuffer2 = allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(n);
                    object = allocatedBuffer.nioBuffer();
                    break block12;
                }
                allocatedBuffer2 = object;
                object = this.getOrCreateLazyDirectBuffer();
            }
            allocatedBuffer2 = allocatedBuffer;
            n = this.readEncryptedDataDirect((ByteBuffer)object, 0, Math.min(n, ((Buffer)object).remaining()));
            if (n <= 0) break block13;
            allocatedBuffer2 = allocatedBuffer;
            ((Buffer)object).position(n);
            allocatedBuffer2 = allocatedBuffer;
            ((Buffer)object).flip();
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.put((ByteBuffer)object);
        }
        if (allocatedBuffer != null) {
            allocatedBuffer.release();
        }
        return n;
    }

    private SSLEngineResult readPendingBytesFromBIO(ByteBuffer object, int n, int n2, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        block8 : {
            int n3;
            block10 : {
                int n4;
                block9 : {
                    try {
                        n3 = this.pendingOutboundEncryptedBytes();
                        if (n3 <= 0) break block8;
                    }
                    catch (Exception exception) {
                        throw this.convertException(exception);
                    }
                    if (object.remaining() < n3) {
                        object = SSLEngineResult.Status.BUFFER_OVERFLOW;
                        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                            handshakeStatus = this.getHandshakeStatus(n3);
                        }
                        return new SSLEngineResult((SSLEngineResult.Status)((Object)object), this.mayFinishHandshake(handshakeStatus), n, n2);
                    }
                    n4 = this.readEncryptedData((ByteBuffer)object, n3);
                    if (n4 > 0) break block9;
                    NativeCrypto.SSL_clear_error();
                    break block10;
                }
                n2 += n4;
                n3 -= n4;
            }
            object = this.getEngineStatus();
            if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                handshakeStatus = this.getHandshakeStatus(n3);
            }
            object = new SSLEngineResult((SSLEngineResult.Status)((Object)object), this.mayFinishHandshake(handshakeStatus), n, n2);
            return object;
        }
        return null;
    }

    private int readPlaintextData(ByteBuffer byteBuffer) throws IOException {
        int n;
        int n2;
        block4 : {
            block5 : {
                try {
                    n2 = byteBuffer.position();
                    n = Math.min(16709, byteBuffer.limit() - n2);
                    if (!byteBuffer.isDirect()) break block4;
                    if ((n = this.readPlaintextDataDirect(byteBuffer, n2, n)) <= 0) break block5;
                }
                catch (CertificateException certificateException) {
                    throw this.convertException(certificateException);
                }
                byteBuffer.position(n2 + n);
            }
            return n;
        }
        n2 = this.readPlaintextDataHeap(byteBuffer, n);
        return n2;
    }

    private int readPlaintextDataDirect(ByteBuffer byteBuffer, int n, int n2) throws IOException, CertificateException {
        return this.ssl.readDirectByteBuffer(this.directByteBufferAddress(byteBuffer, n), n2);
    }

    private int readPlaintextDataHeap(ByteBuffer byteBuffer, int n) throws IOException, CertificateException {
        AllocatedBuffer allocatedBuffer;
        block13 : {
            AllocatedBuffer allocatedBuffer2;
            Object object;
            block12 : {
                block11 : {
                    object = null;
                    allocatedBuffer = null;
                    allocatedBuffer2 = object;
                    try {
                        if (this.bufferAllocator == null) break block11;
                        allocatedBuffer2 = object;
                    }
                    catch (Throwable throwable) {
                        if (allocatedBuffer2 != null) {
                            allocatedBuffer2.release();
                        }
                        throw throwable;
                    }
                    allocatedBuffer2 = allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(n);
                    object = allocatedBuffer.nioBuffer();
                    break block12;
                }
                allocatedBuffer2 = object;
                object = this.getOrCreateLazyDirectBuffer();
            }
            allocatedBuffer2 = allocatedBuffer;
            n = this.readPlaintextDataDirect((ByteBuffer)object, 0, Math.min(n, ((Buffer)object).remaining()));
            if (n <= 0) break block13;
            allocatedBuffer2 = allocatedBuffer;
            ((Buffer)object).position(n);
            allocatedBuffer2 = allocatedBuffer;
            ((Buffer)object).flip();
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.put((ByteBuffer)object);
        }
        if (allocatedBuffer != null) {
            allocatedBuffer.release();
        }
        return n;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private void sendSSLShutdown() {
        try {
            this.ssl.shutdown();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private AbstractSessionContext sessionContext() {
        return this.sslParameters.getSessionContext();
    }

    static void setDefaultBufferAllocator(BufferAllocator bufferAllocator) {
        defaultBufferAllocator = bufferAllocator;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] arrbyteBuffer = this.singleDstBuffer;
        arrbyteBuffer[0] = byteBuffer;
        return arrbyteBuffer;
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] arrbyteBuffer = this.singleSrcBuffer;
        arrbyteBuffer[0] = byteBuffer;
        return arrbyteBuffer;
    }

    private void transitionTo(int n) {
        if (n != 2) {
            int n2;
            if (n == 8 && !this.ssl.isClosed() && (n2 = this.state) >= 2 && n2 < 8) {
                this.closedSession = new SessionSnapshot(this.activeSession);
            }
        } else {
            this.handshakeFinished = false;
            this.activeSession = new ActiveSession(this.ssl, this.sslParameters.getSessionContext());
        }
        this.state = n;
    }

    private int writeEncryptedData(ByteBuffer byteBuffer, int n) throws SSLException {
        block3 : {
            int n2;
            try {
                n2 = byteBuffer.position();
                n = byteBuffer.isDirect() ? this.writeEncryptedDataDirect(byteBuffer, n2, n) : this.writeEncryptedDataHeap(byteBuffer, n2, n);
                if (n <= 0) break block3;
            }
            catch (IOException iOException) {
                throw new SSLException(iOException);
            }
            byteBuffer.position(n2 + n);
        }
        return n;
    }

    private int writeEncryptedDataDirect(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        return this.networkBio.writeDirectByteBuffer(this.directByteBufferAddress(byteBuffer, n), n2);
    }

    private int writeEncryptedDataHeap(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        block16 : {
            Object object;
            AllocatedBuffer allocatedBuffer;
            AllocatedBuffer allocatedBuffer2;
            block15 : {
                block14 : {
                    object = null;
                    allocatedBuffer = null;
                    allocatedBuffer2 = object;
                    try {
                        if (this.bufferAllocator == null) break block14;
                        allocatedBuffer2 = object;
                    }
                    catch (Throwable throwable) {
                        if (allocatedBuffer2 != null) {
                            allocatedBuffer2.release();
                        }
                        throw throwable;
                    }
                    allocatedBuffer2 = allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(n2);
                    object = allocatedBuffer.nioBuffer();
                    break block15;
                }
                allocatedBuffer2 = object;
                object = this.getOrCreateLazyDirectBuffer();
            }
            allocatedBuffer2 = allocatedBuffer;
            int n3 = byteBuffer.limit();
            allocatedBuffer2 = allocatedBuffer;
            n2 = Math.min(Math.min(n3 - n, n2), ((Buffer)object).remaining());
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.limit(n + n2);
            allocatedBuffer2 = allocatedBuffer;
            ((ByteBuffer)object).put(byteBuffer);
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.limit(n3);
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.position(n);
            allocatedBuffer2 = allocatedBuffer;
            n2 = this.writeEncryptedDataDirect((ByteBuffer)object, 0, n2);
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.position(n);
            if (allocatedBuffer == null) break block16;
            allocatedBuffer.release();
        }
        return n2;
    }

    private int writePlaintextData(ByteBuffer byteBuffer, int n) throws SSLException {
        block3 : {
            int n2;
            try {
                n2 = byteBuffer.position();
                n = byteBuffer.isDirect() ? this.writePlaintextDataDirect(byteBuffer, n2, n) : this.writePlaintextDataHeap(byteBuffer, n2, n);
                if (n <= 0) break block3;
            }
            catch (Exception exception) {
                throw this.convertException(exception);
            }
            byteBuffer.position(n2 + n);
        }
        return n;
    }

    private int writePlaintextDataDirect(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        return this.ssl.writeDirectByteBuffer(this.directByteBufferAddress(byteBuffer, n), n2);
    }

    private int writePlaintextDataHeap(ByteBuffer byteBuffer, int n, int n2) throws IOException {
        block16 : {
            Object object;
            AllocatedBuffer allocatedBuffer;
            AllocatedBuffer allocatedBuffer2;
            block15 : {
                block14 : {
                    object = null;
                    allocatedBuffer = null;
                    allocatedBuffer2 = object;
                    try {
                        if (this.bufferAllocator == null) break block14;
                        allocatedBuffer2 = object;
                    }
                    catch (Throwable throwable) {
                        if (allocatedBuffer2 != null) {
                            allocatedBuffer2.release();
                        }
                        throw throwable;
                    }
                    allocatedBuffer2 = allocatedBuffer = this.bufferAllocator.allocateDirectBuffer(n2);
                    object = allocatedBuffer.nioBuffer();
                    break block15;
                }
                allocatedBuffer2 = object;
                object = this.getOrCreateLazyDirectBuffer();
            }
            allocatedBuffer2 = allocatedBuffer;
            int n3 = byteBuffer.limit();
            allocatedBuffer2 = allocatedBuffer;
            n2 = Math.min(n2, ((Buffer)object).remaining());
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.limit(n + n2);
            allocatedBuffer2 = allocatedBuffer;
            ((ByteBuffer)object).put(byteBuffer);
            allocatedBuffer2 = allocatedBuffer;
            ((Buffer)object).flip();
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.limit(n3);
            allocatedBuffer2 = allocatedBuffer;
            byteBuffer.position(n);
            allocatedBuffer2 = allocatedBuffer;
            n = this.writePlaintextDataDirect((ByteBuffer)object, 0, n2);
            if (allocatedBuffer == null) break block16;
            allocatedBuffer.release();
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void beginHandshake() throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.beginHandshakeInternal();
            return;
        }
    }

    @Override
    public String chooseClientAlias(X509KeyManager x509KeyManager, X500Principal[] arrx500Principal, String[] arrstring) {
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            return ((X509ExtendedKeyManager)x509KeyManager).chooseEngineClientAlias(arrstring, arrx500Principal, this);
        }
        return x509KeyManager.chooseClientAlias(arrstring, arrx500Principal, null);
    }

    @Override
    public String chooseClientPSKIdentity(PSKKeyManager pSKKeyManager, String string) {
        return pSKKeyManager.chooseClientKeyIdentity(string, this);
    }

    @Override
    public String chooseServerAlias(X509KeyManager x509KeyManager, String string) {
        if (x509KeyManager instanceof X509ExtendedKeyManager) {
            return ((X509ExtendedKeyManager)x509KeyManager).chooseEngineServerAlias(string, null, this);
        }
        return x509KeyManager.chooseServerAlias(string, null, null);
    }

    @Override
    public String chooseServerPSKIdentityHint(PSKKeyManager pSKKeyManager) {
        return pSKKeyManager.chooseServerKeyIdentityHint(this);
    }

    @Override
    public void clientCertificateRequested(byte[] arrby, int[] arrn, byte[][] arrby2) throws CertificateEncodingException, SSLException {
        this.ssl.chooseClientCertificate(arrby, arrn, arrby2);
    }

    @Override
    public int clientPSKKeyRequested(String string, byte[] arrby, byte[] arrby2) {
        return this.ssl.clientPSKKeyRequested(string, arrby, arrby2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void closeInbound() throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 8 && this.state != 6) {
                if (this.isHandshakeStarted()) {
                    if (this.isOutboundDone()) {
                        this.closeAndFreeResources();
                    } else {
                        this.transitionTo(6);
                    }
                } else {
                    this.closeAndFreeResources();
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void closeOutbound() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 8 && this.state != 7) {
                if (this.isHandshakeStarted()) {
                    this.sendSSLShutdown();
                    if (this.isInboundDone()) {
                        this.closeAndFreeResources();
                    } else {
                        this.transitionTo(7);
                    }
                } else {
                    this.closeAndFreeResources();
                }
                return;
            }
            return;
        }
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

    protected void finalize() throws Throwable {
        try {
            this.transitionTo(8);
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public String getApplicationProtocol() {
        return SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
    }

    @Override
    String[] getApplicationProtocols() {
        return this.sslParameters.getApplicationProtocols();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    byte[] getChannelId() throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.getUseClientMode()) {
                IllegalStateException illegalStateException = new IllegalStateException("Not allowed in client mode");
                throw illegalStateException;
            }
            if (!this.isHandshakeStarted()) {
                return this.ssl.getTlsChannelId();
            }
            IllegalStateException illegalStateException = new IllegalStateException("Channel ID is only available after handshake completes");
            throw illegalStateException;
        }
    }

    @Override
    public Runnable getDelegatedTask() {
        return null;
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getHandshakeApplicationProtocol() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 2) return null;
            return this.getApplicationProtocol();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.getHandshakeStatusInternal();
        }
    }

    @Override
    String getHostname() {
        String string = this.peerHostname;
        if (string == null) {
            string = this.peerInfoProvider.getHostname();
        }
        return string;
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public SecretKey getPSKKey(PSKKeyManager pSKKeyManager, String string, String string2) {
        return pSKKeyManager.getKey(string, string2, this);
    }

    @Override
    public String getPeerHost() {
        String string = this.peerHostname;
        if (string == null) {
            string = this.peerInfoProvider.getHostnameOrIP();
        }
        return string;
    }

    @Override
    public int getPeerPort() {
        return this.peerInfoProvider.getPort();
    }

    @Override
    public SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        Platform.getSSLParameters(sSLParameters, this.sslParameters, this);
        return sSLParameters;
    }

    @Override
    public SSLSession getSession() {
        return this.externalSession;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    byte[] getTlsUnique() {
        return this.ssl.getTlsUnique();
    }

    @Override
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    SSLSession handshakeSession() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state != 2) return null;
            ExternalSession.Provider provider = new ExternalSession.Provider(){

                @Override
                public ConscryptSession provideSession() {
                    return ConscryptEngine.this.provideHandshakeSession();
                }
            };
            ExternalSession externalSession = new ExternalSession(provider);
            return Platform.wrapSSLSession(externalSession);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isInboundDone() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) return true;
            if (this.state == 6) return true;
            if (!this.ssl.wasShutdownReceived()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isOutboundDone() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.state == 8) return true;
            if (this.state == 7) return true;
            if (!this.ssl.wasShutdownSent()) return false;
            return true;
        }
    }

    @Override
    int maxSealOverhead() {
        return this.maxSealOverhead;
    }

    @Override
    public void onNewSessionEstablished(long l) {
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
    public void onSSLStateChange(int n, int n2) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (n != 16) {
                if (n == 32) {
                    if (this.state != 2 && this.state != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Completed handshake while in mode ");
                        stringBuilder.append(this.state);
                        IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                        throw illegalStateException;
                    }
                    this.transitionTo(3);
                }
            } else {
                this.transitionTo(2);
            }
            return;
        }
    }

    @Override
    public int serverPSKKeyRequested(String string, String string2, byte[] arrby) {
        return this.ssl.serverPSKKeyRequested(string, string2, arrby);
    }

    @Override
    public long serverSessionRequested(byte[] arrby) {
        return 0L;
    }

    @Override
    void setApplicationProtocolSelector(ApplicationProtocolSelector object) {
        object = object == null ? null : new ApplicationProtocolSelectorAdapter(this, (ApplicationProtocolSelector)object);
        this.setApplicationProtocolSelector((ApplicationProtocolSelectorAdapter)object);
    }

    void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.sslParameters.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override
    void setApplicationProtocols(String[] arrstring) {
        this.sslParameters.setApplicationProtocols(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void setBufferAllocator(BufferAllocator object) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (!this.isHandshakeStarted()) {
                this.bufferAllocator = object;
                return;
            }
            object = new IllegalStateException("Could not set buffer allocator after the initial handshake has begun.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void setChannelIdEnabled(boolean bl) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.getUseClientMode()) {
                IllegalStateException illegalStateException = new IllegalStateException("Not allowed in client mode");
                throw illegalStateException;
            }
            if (!this.isHandshakeStarted()) {
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
     */
    @Override
    void setChannelIdPrivateKey(PrivateKey serializable) {
        if (!this.getUseClientMode()) {
            throw new IllegalStateException("Not allowed in server mode");
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (this.isHandshakeStarted()) {
                serializable = new IllegalStateException("Could not change Channel ID private key after the initial handshake has begun.");
                throw serializable;
            }
            if (serializable == null) {
                this.sslParameters.channelIdEnabled = false;
                this.channelIdPrivateKey = null;
                return;
            }
            this.sslParameters.channelIdEnabled = true;
            ECParameterSpec eCParameterSpec = null;
            try {
                if (serializable instanceof ECKey) {
                    eCParameterSpec = ((ECKey)((Object)serializable)).getParams();
                }
                ECParameterSpec eCParameterSpec2 = eCParameterSpec;
                if (eCParameterSpec == null) {
                    eCParameterSpec2 = OpenSSLECGroupContext.getCurveByName("prime256v1").getECParameterSpec();
                }
                this.channelIdPrivateKey = OpenSSLKey.fromECPrivateKeyForTLSStackOnly(serializable, eCParameterSpec2);
            }
            catch (InvalidKeyException invalidKeyException) {
                // empty catch block
            }
            return;
        }
    }

    @Override
    public void setEnableSessionCreation(boolean bl) {
        this.sslParameters.setEnableSessionCreation(bl);
    }

    @Override
    public void setEnabledCipherSuites(String[] arrstring) {
        this.sslParameters.setEnabledCipherSuites(arrstring);
    }

    @Override
    public void setEnabledProtocols(String[] arrstring) {
        this.sslParameters.setEnabledProtocols(arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void setHandshakeListener(HandshakeListener object) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (!this.isHandshakeStarted()) {
                this.handshakeListener = object;
                return;
            }
            object = new IllegalStateException("Handshake listener must be set before starting the handshake.");
            throw object;
        }
    }

    @Override
    void setHostname(String string) {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        boolean bl = string != null;
        sSLParametersImpl.setUseSni(bl);
        this.peerHostname = string;
    }

    @Override
    public void setNeedClientAuth(boolean bl) {
        this.sslParameters.setNeedClientAuth(bl);
    }

    @Override
    public void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        Platform.setSSLParameters(sSLParameters, this.sslParameters, this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setUseClientMode(boolean bl) {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            if (!this.isHandshakeStarted()) {
                this.transitionTo(1);
                this.sslParameters.setUseClientMode(bl);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can not change mode after handshake: state == ");
            stringBuilder.append(this.state);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    @Override
    void setUseSessionTickets(boolean bl) {
        this.sslParameters.setUseSessionTickets(bl);
    }

    @Override
    public void setWantClientAuth(boolean bl) {
        this.sslParameters.setWantClientAuth(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer object, ByteBuffer byteBuffer) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            try {
                object = this.unwrap(this.singleSrcBuffer((ByteBuffer)object), this.singleDstBuffer(byteBuffer));
                return object;
            }
            finally {
                this.resetSingleSrcBuffer();
                this.resetSingleDstBuffer();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer object, ByteBuffer[] arrbyteBuffer) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            try {
                object = this.unwrap(this.singleSrcBuffer((ByteBuffer)object), arrbyteBuffer);
                return object;
            }
            finally {
                this.resetSingleSrcBuffer();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult unwrap(ByteBuffer object, ByteBuffer[] arrbyteBuffer, int n, int n2) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            try {
                object = this.unwrap(this.singleSrcBuffer((ByteBuffer)object), 0, 1, arrbyteBuffer, n, n2);
                return object;
            }
            finally {
                this.resetSingleSrcBuffer();
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    SSLEngineResult unwrap(ByteBuffer[] var1_1, int var2_23, int var3_24, ByteBuffer[] var4_25, int var5_26, int var6_27) throws SSLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    @Override
    SSLEngineResult unwrap(ByteBuffer[] arrbyteBuffer, ByteBuffer[] arrbyteBuffer2) throws SSLException {
        boolean bl = true;
        boolean bl2 = arrbyteBuffer != null;
        Preconditions.checkArgument(bl2, "srcs is null");
        bl2 = arrbyteBuffer2 != null ? bl : false;
        Preconditions.checkArgument(bl2, "dsts is null");
        return this.unwrap(arrbyteBuffer, 0, arrbyteBuffer.length, arrbyteBuffer2, 0, arrbyteBuffer2.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void verifyCertificateChain(byte[][] var1_1, String var2_4) throws CertificateException {
        if (var1_1 /* !! */  == null) ** GOTO lbl15
        try {
            if (var1_1 /* !! */ .length != 0) {
                var1_2 = SSLUtils.decodeX509CertificateChain(var1_1 /* !! */ );
                var3_8 = this.sslParameters.getX509TrustManager();
                if (var3_8 == null) {
                    var1_3 = new CertificateException("No X.509 TrustManager");
                    throw var1_3;
                }
                this.activeSession.onPeerCertificatesReceived(this.getPeerHost(), this.getPeerPort(), var1_2);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult wrap(ByteBuffer object, ByteBuffer byteBuffer) throws SSLException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            try {
                object = this.wrap(this.singleSrcBuffer((ByteBuffer)object), byteBuffer);
                return object;
            }
            finally {
                this.resetSingleSrcBuffer();
            }
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SSLEngineResult wrap(ByteBuffer[] object, int n, int n2, ByteBuffer object2) throws SSLException {
        Object object3 = object;
        boolean bl = object3 != null;
        Preconditions.checkArgument(bl, "srcs is null");
        bl = object2 != null;
        Preconditions.checkArgument(bl, "dst is null");
        Preconditions.checkPositionIndexes(n, n + n2, ((ByteBuffer[])object3).length);
        if (((Buffer)object2).isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            ByteBuffer byteBuffer;
            int n3;
            int n4 = this.state;
            if (n4 == 0) {
                object = new IllegalStateException("Client/server mode must be set before calling wrap");
                throw object;
            }
            if (n4 != 1) {
                if (n4 == 7 || n4 == 8) {
                    object = this.readPendingBytesFromBIO((ByteBuffer)object2, 0, 0, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
                    if (object == null) return new SSLEngineResult(SSLEngineResult.Status.CLOSED, this.getHandshakeStatusInternal(), 0, 0);
                    return object;
                }
            } else {
                this.beginHandshakeInternal();
            }
            SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (!this.handshakeFinished) {
                handshakeStatus = this.handshake();
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    return NEED_UNWRAP_OK;
                }
                if (this.state == 8) {
                    return NEED_UNWRAP_CLOSED;
                }
            }
            int n5 = 0;
            int n6 = n + n2;
            n2 = n5;
            for (n4 = n; n4 < n6; ++n4) {
                byteBuffer = object3[n4];
                if (byteBuffer == null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("srcs[");
                    ((StringBuilder)object2).append(n4);
                    ((StringBuilder)object2).append("] is null");
                    object = new IllegalArgumentException(((StringBuilder)object2).toString());
                    throw object;
                }
                if (n2 == 16384) continue;
                n5 = n2 + byteBuffer.remaining();
                if (n5 <= 16384) {
                    n2 = n5;
                    if (n5 >= 0) continue;
                }
                n2 = 16384;
            }
            if (((Buffer)object2).remaining() < SSLUtils.calculateOutNetBufSize(n2)) {
                return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatusInternal(), 0, 0);
            }
            n2 = 0;
            n5 = 0;
            n4 = n;
            n = n5;
            block5 : do {
                n3 = n2;
                n5 = n;
                if (n4 >= n6) break;
                byteBuffer = object[n4];
                bl = byteBuffer != null;
                Preconditions.checkArgument(bl, "srcs[%d] is null", n4);
                n5 = n;
                n = n2;
                while (byteBuffer.hasRemaining()) {
                    n2 = this.writePlaintextData(byteBuffer, Math.min(byteBuffer.remaining(), 16384 - n5));
                    if (n2 > 0) {
                        object3 = this.readPendingBytesFromBIO((ByteBuffer)object2, n5 += n2, n, handshakeStatus);
                        if (object3 != null) {
                            if (((SSLEngineResult)object3).getStatus() != SSLEngineResult.Status.OK) {
                                return object3;
                            }
                            n = ((SSLEngineResult)object3).bytesProduced();
                        }
                        if (n5 != 16384) continue;
                        n3 = n;
                        break block5;
                    }
                    if ((n2 = this.ssl.getError(n2)) != 2) {
                        if (n2 != 3) {
                            if (n2 != 6) {
                                this.sendSSLShutdown();
                                throw this.newSslExceptionWithMessage("SSL_write");
                            }
                            this.closeAll();
                            object = this.readPendingBytesFromBIO((ByteBuffer)object2, n5, n, handshakeStatus);
                            if (object == null) return CLOSED_NOT_HANDSHAKING;
                            return object;
                        }
                        object = this.readPendingBytesFromBIO((ByteBuffer)object2, n5, n, handshakeStatus);
                        if (object == null) return NEED_WRAP_CLOSED;
                        return object;
                    }
                    object = this.readPendingBytesFromBIO((ByteBuffer)object2, n5, n, handshakeStatus);
                    if (object == null) return new SSLEngineResult(this.getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, n5, n);
                    return object;
                }
                ++n4;
                n2 = n;
                n = n5;
            } while (true);
            if (n5 != 0) return this.newResult(n5, n3, handshakeStatus);
            object = this.readPendingBytesFromBIO((ByteBuffer)object2, 0, n3, handshakeStatus);
            if (object == null) return this.newResult(n5, n3, handshakeStatus);
            return object;
        }
    }

}

