/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.io;

import com.android.okhttp.Address;
import com.android.okhttp.Authenticator;
import com.android.okhttp.CertificatePinner;
import com.android.okhttp.Connection;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Handshake;
import com.android.okhttp.Headers;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.Route;
import com.android.okhttp.internal.ConnectionSpecSelector;
import com.android.okhttp.internal.Platform;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.Version;
import com.android.okhttp.internal.framed.FramedConnection;
import com.android.okhttp.internal.http.Http1xStream;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.RouteException;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.tls.CertificateChainCleaner;
import com.android.okhttp.internal.tls.OkHostnameVerifier;
import com.android.okhttp.internal.tls.TrustRootIndex;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public final class RealConnection
implements Connection {
    private static SSLSocketFactory lastSslSocketFactory;
    private static TrustRootIndex lastTrustRootIndex;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList<Reference<StreamAllocation>>();
    public volatile FramedConnection framedConnection;
    private Handshake handshake;
    public long idleAtNanos = Long.MAX_VALUE;
    public boolean noNewStreams;
    private Protocol protocol;
    private Socket rawSocket;
    private final Route route;
    public BufferedSink sink;
    public Socket socket;
    public BufferedSource source;
    public int streamCount;

    public RealConnection(Route route) {
        this.route = route;
    }

    private void connectSocket(int n, int n2, int n3, ConnectionSpecSelector object) throws IOException {
        this.rawSocket.setSoTimeout(n2);
        try {
            Platform.get().connectSocket(this.rawSocket, this.route.getSocketAddress(), n);
        }
        catch (ConnectException connectException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to connect to ");
            stringBuilder.append(this.route.getSocketAddress());
            throw new ConnectException(stringBuilder.toString());
        }
        this.source = Okio.buffer(Okio.source(this.rawSocket));
        this.sink = Okio.buffer(Okio.sink(this.rawSocket));
        if (this.route.getAddress().getSslSocketFactory() != null) {
            this.connectTls(n2, n3, (ConnectionSpecSelector)object);
        } else {
            this.protocol = Protocol.HTTP_1_1;
            this.socket = this.rawSocket;
        }
        if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
            this.socket.setSoTimeout(0);
            object = new FramedConnection.Builder(true).socket(this.socket, this.route.getAddress().url().host(), this.source, this.sink).protocol(this.protocol).build();
            ((FramedConnection)object).sendConnectionPreface();
            this.framedConnection = object;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void connectTls(int n, int n2, ConnectionSpecSelector object) throws IOException {
        Object object2;
        Throwable throwable22222;
        block15 : {
            Object object3;
            Address address;
            Object object5;
            Object object4;
            block14 : {
                if (this.route.requiresTunnel()) {
                    this.createTunnel(n, n2);
                }
                address = this.route.getAddress();
                object3 = address.getSslSocketFactory();
                object4 = null;
                object2 = null;
                object2 = object3 = (SSLSocket)((SSLSocketFactory)object3).createSocket(this.rawSocket, address.getUriHost(), address.getUriPort(), true);
                object4 = object3;
                object = ((ConnectionSpecSelector)object).configureSecureSocket((SSLSocket)object3);
                object2 = object3;
                object4 = object3;
                if (((ConnectionSpec)object).supportsTlsExtensions()) {
                    object2 = object3;
                    object4 = object3;
                    Platform.get().configureTlsExtensions((SSLSocket)object3, address.getUriHost(), address.getProtocols());
                }
                object2 = object3;
                object4 = object3;
                ((SSLSocket)object3).startHandshake();
                object2 = object3;
                object4 = object3;
                object5 = Handshake.get(((SSLSocket)object3).getSession());
                object2 = object3;
                object4 = object3;
                if (!address.getHostnameVerifier().verify(address.getUriHost(), ((SSLSocket)object3).getSession())) break block14;
                object2 = object3;
                object4 = object3;
                if (address.getCertificatePinner() != CertificatePinner.DEFAULT) {
                    object2 = object3;
                    object4 = object3;
                    TrustRootIndex trustRootIndex = RealConnection.trustRootIndex(address.getSslSocketFactory());
                    object2 = object3;
                    object4 = object3;
                    object2 = object3;
                    object4 = object3;
                    Object object6 = new CertificateChainCleaner(trustRootIndex);
                    object2 = object3;
                    object4 = object3;
                    object6 = ((CertificateChainCleaner)object6).clean(((Handshake)object5).peerCertificates());
                    object2 = object3;
                    object4 = object3;
                    address.getCertificatePinner().check(address.getUriHost(), (List<Certificate>)object6);
                }
                object2 = object3;
                object4 = object3;
                if (((ConnectionSpec)object).supportsTlsExtensions()) {
                    object2 = object3;
                    object4 = object3;
                    object = Platform.get().getSelectedProtocol((SSLSocket)object3);
                } else {
                    object = null;
                }
                object2 = object3;
                object4 = object3;
                this.socket = object3;
                object2 = object3;
                object4 = object3;
                this.source = Okio.buffer(Okio.source(this.socket));
                object2 = object3;
                object4 = object3;
                this.sink = Okio.buffer(Okio.sink(this.socket));
                object2 = object3;
                object4 = object3;
                this.handshake = object5;
                if (object != null) {
                    object2 = object3;
                    object4 = object3;
                    object = Protocol.get((String)object);
                } else {
                    object2 = object3;
                    object4 = object3;
                    object = Protocol.HTTP_1_1;
                }
                object2 = object3;
                object4 = object3;
                this.protocol = object;
                Platform.get().afterHandshake((SSLSocket)object3);
                if (true) return;
                Util.closeQuietly((Socket)object3);
                return;
            }
            object2 = object3;
            object4 = object3;
            try {
                object5 = (X509Certificate)((Handshake)object5).peerCertificates().get(0);
                object2 = object3;
                object4 = object3;
                object2 = object3;
                object4 = object3;
                object2 = object3;
                object4 = object3;
                object = new StringBuilder();
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append("Hostname ");
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append(address.getUriHost());
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append(" not verified:\n    certificate: ");
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append(CertificatePinner.pin((Certificate)object5));
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append("\n    DN: ");
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append(((X509Certificate)object5).getSubjectDN().getName());
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append("\n    subjectAltNames: ");
                object2 = object3;
                object4 = object3;
                ((StringBuilder)object).append(OkHostnameVerifier.allSubjectAltNames((X509Certificate)object5));
                object2 = object3;
                object4 = object3;
                SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(((StringBuilder)object).toString());
                object2 = object3;
                object4 = object3;
                throw sSLPeerUnverifiedException;
            }
            catch (Throwable throwable22222) {
                break block15;
            }
            catch (AssertionError assertionError) {
                object2 = object4;
                if (Util.isAndroidGetsocknameError(assertionError)) {
                    object2 = object4;
                    object2 = object4;
                    object3 = new IOException((Throwable)((Object)assertionError));
                    object2 = object4;
                    throw object3;
                }
                object2 = object4;
                throw assertionError;
            }
        }
        if (object2 != null) {
            Platform.get().afterHandshake((SSLSocket)object2);
        }
        if (false) throw throwable22222;
        Util.closeQuietly(object2);
        throw throwable22222;
    }

    private void createTunnel(int n, int n2) throws IOException {
        block3 : {
            Object object;
            Object object2;
            block4 : {
                object2 = this.createTunnelRequest();
                object = ((Request)object2).httpUrl();
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append("CONNECT ");
                ((StringBuilder)object3).append(Util.hostHeader((HttpUrl)object, true));
                ((StringBuilder)object3).append(" HTTP/1.1");
                object = ((StringBuilder)object3).toString();
                do {
                    long l;
                    object3 = new Http1xStream(null, this.source, this.sink);
                    this.source.timeout().timeout(n, TimeUnit.MILLISECONDS);
                    this.sink.timeout().timeout(n2, TimeUnit.MILLISECONDS);
                    ((Http1xStream)object3).writeRequest(((Request)object2).headers(), (String)object);
                    ((Http1xStream)object3).finishRequest();
                    object2 = ((Http1xStream)object3).readResponse().request((Request)object2).build();
                    long l2 = l = OkHeaders.contentLength((Response)object2);
                    if (l == -1L) {
                        l2 = 0L;
                    }
                    object3 = ((Http1xStream)object3).newFixedLengthSource(l2);
                    Util.skipAll((Source)object3, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
                    object3.close();
                    int n3 = ((Response)object2).code();
                    if (n3 == 200) break block3;
                    if (n3 != 407) break block4;
                } while ((object2 = OkHeaders.processAuthHeader(this.route.getAddress().getAuthenticator(), (Response)object2, this.route.getProxy())) != null);
                throw new IOException("Failed to authenticate with proxy");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected response code for CONNECT: ");
            ((StringBuilder)object).append(((Response)object2).code());
            throw new IOException(((StringBuilder)object).toString());
        }
        if (this.source.buffer().exhausted() && this.sink.buffer().exhausted()) {
            return;
        }
        throw new IOException("TLS tunnel buffered too many bytes!");
    }

    private Request createTunnelRequest() throws IOException {
        return new Request.Builder().url(this.route.getAddress().url()).header("Host", Util.hostHeader(this.route.getAddress().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
    }

    private static TrustRootIndex trustRootIndex(SSLSocketFactory object) {
        synchronized (RealConnection.class) {
            if (object != lastSslSocketFactory) {
                X509TrustManager x509TrustManager = Platform.get().trustManager((SSLSocketFactory)object);
                lastTrustRootIndex = Platform.get().trustRootIndex(x509TrustManager);
                lastSslSocketFactory = object;
            }
            object = lastTrustRootIndex;
            return object;
        }
    }

    public int allocationLimit() {
        FramedConnection framedConnection = this.framedConnection;
        int n = framedConnection != null ? framedConnection.maxConcurrentStreams() : 1;
        return n;
    }

    public void cancel() {
        Util.closeQuietly(this.rawSocket);
    }

    public void connect(int n, int n2, int n3, List<ConnectionSpec> object, boolean bl) throws RouteException {
        if (this.protocol == null) {
            StringBuilder stringBuilder = null;
            ConnectionSpecSelector connectionSpecSelector = new ConnectionSpecSelector((List<ConnectionSpec>)object);
            Proxy proxy = this.route.getProxy();
            Address address = this.route.getAddress();
            Serializable serializable = stringBuilder;
            if (this.route.getAddress().getSslSocketFactory() == null) {
                if (object.contains(ConnectionSpec.CLEARTEXT)) {
                    serializable = stringBuilder;
                } else {
                    serializable = new StringBuilder();
                    serializable.append("CLEARTEXT communication not supported: ");
                    serializable.append(object);
                    throw new RouteException(new UnknownServiceException(serializable.toString()));
                }
            }
            while (this.protocol == null) {
                try {
                    object = proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.HTTP ? new Socket(proxy) : address.getSocketFactory().createSocket();
                    this.rawSocket = object;
                    this.connectSocket(n, n2, n3, connectionSpecSelector);
                }
                catch (IOException iOException) {
                    Util.closeQuietly(this.socket);
                    Util.closeQuietly(this.rawSocket);
                    this.socket = null;
                    this.rawSocket = null;
                    this.source = null;
                    this.sink = null;
                    this.handshake = null;
                    this.protocol = null;
                    if (serializable == null) {
                        serializable = new RouteException(iOException);
                    } else {
                        ((RouteException)serializable).addConnectException(iOException);
                    }
                    if (bl && connectionSpecSelector.connectionFailed(iOException)) continue;
                    throw serializable;
                }
            }
            return;
        }
        throw new IllegalStateException("already connected");
    }

    @Override
    public Handshake getHandshake() {
        return this.handshake;
    }

    @Override
    public Protocol getProtocol() {
        Protocol protocol = this.protocol;
        if (protocol == null) {
            protocol = Protocol.HTTP_1_1;
        }
        return protocol;
    }

    @Override
    public Route getRoute() {
        return this.route;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    boolean isConnected() {
        boolean bl = this.protocol != null;
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isHealthy(boolean bl) {
        if (this.socket.isClosed() != false) return false;
        if (this.socket.isInputShutdown() != false) return false;
        if (this.socket.isOutputShutdown()) {
            return false;
        }
        if (this.framedConnection != null) {
            return true;
        }
        if (bl == false) return true;
        n = this.socket.getSoTimeout();
        this.socket.setSoTimeout(1);
        bl = this.source.exhausted();
        if (!bl) ** GOTO lbl20
        {
            catch (Throwable throwable) {
                this.socket.setSoTimeout(n);
                throw throwable;
            }
        }
        try {
            this.socket.setSoTimeout(n);
            return false;
lbl20: // 1 sources:
            this.socket.setSoTimeout(n);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
        catch (SocketTimeoutException socketTimeoutException) {
            // empty catch block
        }
        return true;
    }

    public boolean isMultiplexed() {
        boolean bl = this.framedConnection != null;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection{");
        stringBuilder.append(this.route.getAddress().url().host());
        stringBuilder.append(":");
        stringBuilder.append(this.route.getAddress().url().port());
        stringBuilder.append(", proxy=");
        stringBuilder.append(this.route.getProxy());
        stringBuilder.append(" hostAddress=");
        stringBuilder.append(this.route.getSocketAddress());
        stringBuilder.append(" cipherSuite=");
        Object object = this.handshake;
        object = object != null ? ((Handshake)object).cipherSuite() : "none";
        stringBuilder.append((String)object);
        stringBuilder.append(" protocol=");
        stringBuilder.append((Object)this.protocol);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

