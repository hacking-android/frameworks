/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AllocatedBuffer;
import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.BufferAllocator;
import com.android.org.conscrypt.ConscryptEngine;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.HandshakeListener;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.PeerInfoProvider;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

class ConscryptEngineSocket
extends OpenSSLSocketImpl {
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
    private BufferAllocator bufferAllocator = ConscryptEngine.getDefaultBufferAllocator();
    private final ConscryptEngine engine;
    private final Object handshakeLock = new Object();
    private SSLInputStream in;
    private SSLOutputStream out;
    private int state = 0;
    private final Object stateLock = new Object();

    ConscryptEngineSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    ConscryptEngineSocket(String string, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n);
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    ConscryptEngineSocket(String string, int n, InetAddress inetAddress, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n, inetAddress, n2);
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    ConscryptEngineSocket(InetAddress inetAddress, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n);
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    ConscryptEngineSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    ConscryptEngineSocket(Socket socket, String string, int n, boolean bl, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, string, n, bl);
        this.engine = ConscryptEngineSocket.newEngine(sSLParametersImpl, this);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private void doHandshake() throws IOException {
        boolean bl = false;
        while (!bl) {
            try {
                int n = 3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[this.engine.getHandshakeStatus().ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4 && n != 5) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown handshake status: ");
                                stringBuilder.append((Object)this.engine.getHandshakeStatus());
                                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                                throw illegalStateException;
                            }
                            bl = true;
                            continue;
                        }
                        IllegalStateException illegalStateException = new IllegalStateException("Engine tasks are unsupported");
                        throw illegalStateException;
                    }
                    this.out.writeInternal(ConscryptEngineSocket.EMPTY_BUFFER);
                    this.out.flushInternal();
                    continue;
                }
                if (this.in.processDataFromSocket(EmptyArray.BYTE, 0, 0) >= 0) continue;
                EOFException eOFException = new EOFException();
                throw SSLUtils.toSSLHandshakeException(eOFException);
            }
            catch (Exception exception) {
                this.close();
                throw SSLUtils.toSSLHandshakeException(exception);
            }
            catch (IOException iOException) {
                this.close();
                throw iOException;
            }
            catch (SSLException sSLException) {
                this.close();
                throw sSLException;
            }
        }
    }

    private static X509TrustManager getDelegatingTrustManager(X509TrustManager x509TrustManager, ConscryptEngineSocket conscryptEngineSocket) {
        if (x509TrustManager instanceof X509ExtendedTrustManager) {
            return new X509ExtendedTrustManager((X509ExtendedTrustManager)x509TrustManager, conscryptEngineSocket){
                final /* synthetic */ X509ExtendedTrustManager val$extendedDelegate;
                final /* synthetic */ ConscryptEngineSocket val$socket;
                {
                    this.val$extendedDelegate = x509ExtendedTrustManager;
                    this.val$socket = conscryptEngineSocket;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
                    this.val$extendedDelegate.checkClientTrusted(arrx509Certificate, string);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string, Socket socket) throws CertificateException {
                    throw new AssertionError((Object)"Should not be called");
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string, SSLEngine sSLEngine) throws CertificateException {
                    this.val$extendedDelegate.checkClientTrusted(arrx509Certificate, string, this.val$socket);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
                    this.val$extendedDelegate.checkServerTrusted(arrx509Certificate, string);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string, Socket socket) throws CertificateException {
                    throw new AssertionError((Object)"Should not be called");
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string, SSLEngine sSLEngine) throws CertificateException {
                    this.val$extendedDelegate.checkServerTrusted(arrx509Certificate, string, this.val$socket);
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return this.val$extendedDelegate.getAcceptedIssuers();
                }
            };
        }
        return x509TrustManager;
    }

    private InputStream getUnderlyingInputStream() throws IOException {
        return super.getInputStream();
    }

    private OutputStream getUnderlyingOutputStream() throws IOException {
        return super.getOutputStream();
    }

    private static ConscryptEngine newEngine(SSLParametersImpl sSLParametersImpl, final ConscryptEngineSocket conscryptEngineSocket) {
        Object object = Platform.supportsX509ExtendedTrustManager() ? sSLParametersImpl.cloneWithTrustManager(ConscryptEngineSocket.getDelegatingTrustManager(sSLParametersImpl.getX509TrustManager(), conscryptEngineSocket)) : sSLParametersImpl;
        object = new ConscryptEngine((SSLParametersImpl)object, conscryptEngineSocket.peerInfoProvider());
        ((ConscryptEngine)object).setHandshakeListener(new HandshakeListener(){

            @Override
            public void onHandshakeFinished() {
                conscryptEngineSocket.onHandshakeFinished();
            }
        });
        ((ConscryptEngine)object).setUseClientMode(sSLParametersImpl.getUseClientMode());
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onHandshakeFinished() {
        boolean bl = false;
        Object object = this.stateLock;
        // MONITORENTER : object
        if (this.state != 8) {
            if (this.state == 2) {
                this.state = 4;
            } else if (this.state == 3) {
                this.state = 5;
            }
            this.stateLock.notifyAll();
            bl = true;
        }
        // MONITOREXIT : object
        if (!bl) return;
        this.notifyHandshakeCompletedListeners();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitForHandshake() throws IOException {
        this.startHandshake();
        Object object = this.stateLock;
        synchronized (object) {
            int n;
            while (this.state != 5 && this.state != 4 && (n = this.state) != 8) {
                try {
                    this.stateLock.wait();
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void close() throws IOException {
        Object object = this.stateLock;
        if (object == null) {
            return;
        }
        synchronized (object) {
            if (this.state == 8) {
                return;
            }
            this.state = 8;
            this.stateLock.notifyAll();
        }
        try {
            super.close();
            return;
        }
        finally {
            this.engine.closeInbound();
            this.engine.closeOutbound();
            object = this.in;
            if (object != null) {
                ((SSLInputStream)object).release();
            }
        }
    }

    @Override
    byte[] exportKeyingMaterial(String string, byte[] arrby, int n) throws SSLException {
        return this.engine.exportKeyingMaterial(string, arrby, n);
    }

    @Override
    final SSLSession getActiveSession() {
        return this.engine.getSession();
    }

    @Override
    public final String getApplicationProtocol() {
        return this.engine.getApplicationProtocol();
    }

    @Override
    final String[] getApplicationProtocols() {
        return this.engine.getApplicationProtocols();
    }

    @Override
    public final byte[] getChannelId() throws SSLException {
        return this.engine.getChannelId();
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    @Override
    public final String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    @Override
    public final String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    @Override
    public final String getHandshakeApplicationProtocol() {
        return this.engine.getHandshakeApplicationProtocol();
    }

    @Override
    public final SSLSession getHandshakeSession() {
        return this.engine.handshakeSession();
    }

    @Override
    public final InputStream getInputStream() throws IOException {
        this.checkOpen();
        this.waitForHandshake();
        return this.in;
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    @Override
    public final OutputStream getOutputStream() throws IOException {
        this.checkOpen();
        this.waitForHandshake();
        return this.out;
    }

    @Override
    public final SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    @Override
    public final SSLSession getSession() {
        if (this.isConnected()) {
            try {
                this.waitForHandshake();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.engine.getSession();
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    @Override
    public final String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    @Override
    byte[] getTlsUnique() {
        return this.engine.getTlsUnique();
    }

    @Override
    public final boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    @Override
    public final void setApplicationProtocolSelector(ApplicationProtocolSelector object) {
        object = object == null ? null : new ApplicationProtocolSelectorAdapter(this, (ApplicationProtocolSelector)object);
        this.setApplicationProtocolSelector((ApplicationProtocolSelectorAdapter)object);
    }

    @Override
    final void setApplicationProtocolSelector(ApplicationProtocolSelectorAdapter applicationProtocolSelectorAdapter) {
        this.engine.setApplicationProtocolSelector(applicationProtocolSelectorAdapter);
    }

    @Override
    final void setApplicationProtocols(String[] arrstring) {
        this.engine.setApplicationProtocols(arrstring);
    }

    void setBufferAllocator(BufferAllocator bufferAllocator) {
        this.engine.setBufferAllocator(bufferAllocator);
        this.bufferAllocator = bufferAllocator;
    }

    @Override
    public final void setChannelIdEnabled(boolean bl) {
        this.engine.setChannelIdEnabled(bl);
    }

    @Override
    public final void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.engine.setChannelIdPrivateKey(privateKey);
    }

    @Override
    public final void setEnableSessionCreation(boolean bl) {
        this.engine.setEnableSessionCreation(bl);
    }

    @Override
    public final void setEnabledCipherSuites(String[] arrstring) {
        this.engine.setEnabledCipherSuites(arrstring);
    }

    @Override
    public final void setEnabledProtocols(String[] arrstring) {
        this.engine.setEnabledProtocols(arrstring);
    }

    @Override
    public final void setHostname(String string) {
        this.engine.setHostname(string);
        super.setHostname(string);
    }

    @Override
    public final void setNeedClientAuth(boolean bl) {
        this.engine.setNeedClientAuth(bl);
    }

    @Override
    public final void setSSLParameters(SSLParameters sSLParameters) {
        this.engine.setSSLParameters(sSLParameters);
    }

    @Override
    public final void setUseClientMode(boolean bl) {
        this.engine.setUseClientMode(bl);
    }

    @Override
    public final void setUseSessionTickets(boolean bl) {
        this.engine.setUseSessionTickets(bl);
    }

    @Override
    public final void setWantClientAuth(boolean bl) {
        this.engine.setWantClientAuth(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void startHandshake() throws IOException {
        this.checkOpen();
        try {
            Object object = this.handshakeLock;
            // MONITORENTER : object
            Object object2 = this.stateLock;
        }
        catch (Exception exception) {
            this.close();
            throw SSLUtils.toSSLHandshakeException(exception);
        }
        catch (IOException iOException) {
            this.close();
            throw iOException;
        }
        catch (SSLException sSLException) {
            this.close();
            throw sSLException;
        }
        // MONITORENTER : object2
        if (this.state == 0) {
            Closeable closeable;
            this.state = 2;
            this.engine.beginHandshake();
            this.in = closeable = new SSLInputStream();
            closeable = new SSLOutputStream();
            this.out = closeable;
            // MONITOREXIT : object2
            this.doHandshake();
            // MONITOREXIT : object
            return;
        }
        // MONITOREXIT : object2
        // MONITOREXIT : object
        return;
    }

    private final class SSLInputStream
    extends InputStream {
        private final AllocatedBuffer allocatedBuffer;
        private final ByteBuffer fromEngine;
        private final ByteBuffer fromSocket;
        private final int fromSocketArrayOffset;
        private final Object readLock = new Object();
        private final byte[] singleByte = new byte[1];
        private InputStream socketInputStream;

        SSLInputStream() {
            if (ConscryptEngineSocket.this.bufferAllocator != null) {
                this.allocatedBuffer = ConscryptEngineSocket.this.bufferAllocator.allocateDirectBuffer(ConscryptEngineSocket.this.engine.getSession().getApplicationBufferSize());
                this.fromEngine = this.allocatedBuffer.nioBuffer();
            } else {
                this.allocatedBuffer = null;
                this.fromEngine = ByteBuffer.allocateDirect(ConscryptEngineSocket.this.engine.getSession().getApplicationBufferSize());
            }
            this.fromEngine.flip();
            this.fromSocket = ByteBuffer.allocate(ConscryptEngineSocket.this.engine.getSession().getPacketBufferSize());
            this.fromSocketArrayOffset = this.fromSocket.arrayOffset();
        }

        private void init() throws IOException {
            if (this.socketInputStream == null) {
                this.socketInputStream = ConscryptEngineSocket.this.getUnderlyingInputStream();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean isHandshakeFinished() {
            Object object = ConscryptEngineSocket.this.stateLock;
            synchronized (object) {
                if (ConscryptEngineSocket.this.state < 4) return false;
                return true;
            }
        }

        private boolean isHandshaking(SSLEngineResult.HandshakeStatus handshakeStatus) {
            int n = 3.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()];
            return n == 1 || n == 2 || n == 3;
        }

        private int processDataFromSocket(byte[] object, int n, int n2) throws IOException {
            boolean bl;
            Platform.blockGuardOnNetwork();
            ConscryptEngineSocket.this.checkOpen();
            this.init();
            do {
                if (this.fromEngine.remaining() > 0) {
                    n2 = Math.min(this.fromEngine.remaining(), n2);
                    this.fromEngine.get((byte[])object, n, n2);
                    return n2;
                }
                bl = true;
                this.fromSocket.flip();
                this.fromEngine.clear();
                boolean bl2 = this.isHandshaking(ConscryptEngineSocket.this.engine.getHandshakeStatus());
                SSLEngineResult sSLEngineResult = ConscryptEngineSocket.this.engine.unwrap(this.fromSocket, this.fromEngine);
                this.fromSocket.compact();
                this.fromEngine.flip();
                int n3 = 3.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[sSLEngineResult.getStatus().ordinal()];
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 == 3) {
                            return -1;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unexpected engine result ");
                        ((StringBuilder)object).append((Object)sSLEngineResult.getStatus());
                        throw new SSLException(((StringBuilder)object).toString());
                    }
                    if (!bl2 && this.isHandshaking(sSLEngineResult.getHandshakeStatus()) && this.isHandshakeFinished()) {
                        this.renegotiate();
                        return 0;
                    }
                    bl = false;
                } else if (sSLEngineResult.bytesProduced() != 0) {
                    bl = false;
                }
                if (bl || sSLEngineResult.bytesProduced() != 0) continue;
                return 0;
            } while (!bl || this.readFromSocket() != -1);
            return -1;
        }

        private int readFromSocket() throws IOException {
            int n;
            block3 : {
                int n2;
                try {
                    n2 = this.fromSocket.position();
                    n = this.fromSocket.limit();
                    n = this.socketInputStream.read(this.fromSocket.array(), this.fromSocketArrayOffset + n2, n - n2);
                    if (n <= 0) break block3;
                }
                catch (EOFException eOFException) {
                    return -1;
                }
                this.fromSocket.position(n2 + n);
            }
            return n;
        }

        private int readUntilDataAvailable(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            while ((n3 = this.processDataFromSocket(arrby, n, n2)) == 0) {
            }
            return n3;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void renegotiate() throws IOException {
            Object object = ConscryptEngineSocket.this.handshakeLock;
            synchronized (object) {
                ConscryptEngineSocket.this.doHandshake();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int available() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                this.init();
                int n = this.fromEngine.remaining();
                int n2 = !this.fromSocket.hasRemaining() && this.socketInputStream.available() <= 0 ? 0 : 1;
                return n + n2;
            }
        }

        @Override
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                int n = this.read(this.singleByte, 0, 1);
                if (n == -1) {
                    return -1;
                }
                if (n == 1) {
                    return this.singleByte[0];
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("read incorrect number of bytes ");
                stringBuilder.append(n);
                SSLException sSLException = new SSLException(stringBuilder.toString());
                throw sSLException;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read(byte[] arrby) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                return this.read(arrby, 0, arrby.length);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.readLock;
            synchronized (object) {
                return this.readUntilDataAvailable(arrby, n, n2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void release() {
            Object object = this.readLock;
            synchronized (object) {
                if (this.allocatedBuffer != null) {
                    this.allocatedBuffer.release();
                }
                return;
            }
        }
    }

    private final class SSLOutputStream
    extends OutputStream {
        private OutputStream socketOutputStream;
        private final ByteBuffer target;
        private final int targetArrayOffset;
        private final Object writeLock = new Object();

        SSLOutputStream() {
            this.target = ByteBuffer.allocate(ConscryptEngineSocket.this.engine.getSession().getPacketBufferSize());
            this.targetArrayOffset = this.target.arrayOffset();
        }

        private void flushInternal() throws IOException {
            ConscryptEngineSocket.this.checkOpen();
            this.init();
            this.socketOutputStream.flush();
        }

        private void init() throws IOException {
            if (this.socketOutputStream == null) {
                this.socketOutputStream = ConscryptEngineSocket.this.getUnderlyingOutputStream();
            }
        }

        private void writeInternal(ByteBuffer object) throws IOException {
            SSLEngineResult sSLEngineResult;
            block1 : {
                block2 : {
                    block3 : {
                        int n;
                        Platform.blockGuardOnNetwork();
                        ConscryptEngineSocket.this.checkOpen();
                        this.init();
                        int n2 = ((Buffer)object).remaining();
                        do {
                            this.target.clear();
                            sSLEngineResult = ConscryptEngineSocket.this.engine.wrap((ByteBuffer)object, this.target);
                            if (sSLEngineResult.getStatus() != SSLEngineResult.Status.OK) break block1;
                            if (this.target.position() != sSLEngineResult.bytesProduced()) break block2;
                            n = n2 - sSLEngineResult.bytesConsumed();
                            if (n != ((Buffer)object).remaining()) break block3;
                            this.target.flip();
                            this.writeToSocket();
                            n2 = n;
                        } while (n > 0);
                        return;
                    }
                    throw new SSLException("Engine did not read the correct number of bytes");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Engine bytesProduced ");
                ((StringBuilder)object).append(sSLEngineResult.bytesProduced());
                ((StringBuilder)object).append(" does not match bytes written ");
                ((StringBuilder)object).append(this.target.position());
                throw new SSLException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected engine result ");
            ((StringBuilder)object).append((Object)sSLEngineResult.getStatus());
            throw new SSLException(((StringBuilder)object).toString());
        }

        private void writeToSocket() throws IOException {
            this.socketOutputStream.write(this.target.array(), this.targetArrayOffset, this.target.limit());
        }

        @Override
        public void close() throws IOException {
            ConscryptEngineSocket.this.close();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void flush() throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.flushInternal();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(int n) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.write(new byte[]{(byte)n});
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(byte[] arrby) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.writeInternal(ByteBuffer.wrap(arrby));
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            ConscryptEngineSocket.this.startHandshake();
            Object object = this.writeLock;
            synchronized (object) {
                this.writeInternal(ByteBuffer.wrap(arrby, n, n2));
                return;
            }
        }
    }

}

