/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Address;
import com.android.okhttp.ConnectionPool;
import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.Route;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.RouteDatabase;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.framed.FramedConnection;
import com.android.okhttp.internal.http.Http1xStream;
import com.android.okhttp.internal.http.Http2xStream;
import com.android.okhttp.internal.http.HttpStream;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.internal.http.RouteException;
import com.android.okhttp.internal.http.RouteSelector;
import com.android.okhttp.internal.io.RealConnection;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class StreamAllocation {
    public final Address address;
    private boolean canceled;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private boolean released;
    private RouteSelector routeSelector;
    private HttpStream stream;

    public StreamAllocation(ConnectionPool connectionPool, Address address) {
        this.connectionPool = connectionPool;
        this.address = address;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void connectionFailed(IOException iOException) {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            if (this.routeSelector != null) {
                if (this.connection.streamCount == 0) {
                    Route route = this.connection.getRoute();
                    this.routeSelector.connectFailed(route, iOException);
                } else {
                    this.routeSelector = null;
                }
            }
        }
        this.connectionFailed();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void deallocate(boolean bl, boolean bl2, boolean bl3) {
        RealConnection realConnection;
        block13 : {
            Object var5_5;
            block14 : {
                RealConnection realConnection2 = null;
                var5_5 = null;
                ConnectionPool connectionPool = this.connectionPool;
                // MONITORENTER : connectionPool
                if (bl3) {
                    this.stream = null;
                }
                if (bl2) {
                    this.released = true;
                }
                realConnection = realConnection2;
                if (this.connection == null) break block13;
                if (bl) {
                    this.connection.noNewStreams = true;
                }
                realConnection = realConnection2;
                if (this.stream != null) break block13;
                if (this.released) break block14;
                realConnection = realConnection2;
                if (!this.connection.noNewStreams) break block13;
            }
            this.release(this.connection);
            if (this.connection.streamCount > 0) {
                this.routeSelector = null;
            }
            realConnection = var5_5;
            if (this.connection.allocations.isEmpty()) {
                this.connection.idleAtNanos = System.nanoTime();
                realConnection = var5_5;
                if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                    realConnection = this.connection;
                }
            }
            this.connection = null;
        }
        // MONITOREXIT : connectionPool
        if (realConnection == null) return;
        Util.closeQuietly(realConnection.getSocket());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RealConnection findConnection(int n, int n2, int n3, boolean bl) throws IOException, RouteException {
        Object object;
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            if (this.released) {
                IllegalStateException illegalStateException = new IllegalStateException("released");
                throw illegalStateException;
            }
            if (this.stream != null) {
                IllegalStateException illegalStateException = new IllegalStateException("stream != null");
                throw illegalStateException;
            }
            if (this.canceled) {
                IOException iOException = new IOException("Canceled");
                throw iOException;
            }
            object = this.connection;
            if (object != null && !((RealConnection)object).noNewStreams) {
                return object;
            }
            object = Internal.instance.get(this.connectionPool, this.address, this);
            if (object != null) {
                this.connection = object;
                return object;
            }
            if (this.routeSelector == null) {
                this.routeSelector = object = new RouteSelector(this.address, this.routeDatabase());
            }
        }
        object = new RealConnection(this.routeSelector.next());
        this.acquire((RealConnection)object);
        connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            Internal.instance.put(this.connectionPool, (RealConnection)object);
            this.connection = object;
            if (!this.canceled) {
                // MONITOREXIT [5, 6] lbl31 : MonitorExitStatement: MONITOREXIT : var5_5
                ((RealConnection)object).connect(n, n2, n3, this.address.getConnectionSpecs(), bl);
                this.routeDatabase().connected(((RealConnection)object).getRoute());
                return object;
            }
            object = new IOException("Canceled");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private RealConnection findHealthyConnection(int n, int n2, int n3, boolean bl, boolean bl2) throws IOException, RouteException {
        do {
            RealConnection realConnection = this.findConnection(n, n2, n3, bl);
            ConnectionPool connectionPool = this.connectionPool;
            // MONITORENTER : connectionPool
            if (realConnection.streamCount == 0) {
                // MONITOREXIT : connectionPool
                return realConnection;
            }
            // MONITOREXIT : connectionPool
            if (realConnection.isHealthy(bl2)) {
                return realConnection;
            }
            this.connectionFailed();
        } while (true);
    }

    private boolean isRecoverable(RouteException exception) {
        if ((exception = ((RouteException)exception).getLastConnectException()) instanceof ProtocolException) {
            return false;
        }
        if (exception instanceof InterruptedIOException) {
            return exception instanceof SocketTimeoutException;
        }
        if (exception instanceof SSLHandshakeException && exception.getCause() instanceof CertificateException) {
            return false;
        }
        return !(exception instanceof SSLPeerUnverifiedException);
    }

    private boolean isRecoverable(IOException iOException) {
        if (iOException instanceof ProtocolException) {
            return false;
        }
        return !(iOException instanceof InterruptedIOException);
    }

    private void release(RealConnection realConnection) {
        int n = realConnection.allocations.size();
        for (int i = 0; i < n; ++i) {
            if (realConnection.allocations.get(i).get() != this) continue;
            realConnection.allocations.remove(i);
            return;
        }
        throw new IllegalStateException();
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public void acquire(RealConnection realConnection) {
        realConnection.allocations.add(new WeakReference<StreamAllocation>(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void cancel() {
        ConnectionPool connectionPool = this.connectionPool;
        // MONITORENTER : connectionPool
        this.canceled = true;
        HttpStream httpStream = this.stream;
        RealConnection realConnection = this.connection;
        // MONITOREXIT : connectionPool
        if (httpStream != null) {
            httpStream.cancel();
            return;
        }
        if (realConnection == null) return;
        realConnection.cancel();
    }

    public RealConnection connection() {
        synchronized (this) {
            RealConnection realConnection = this.connection;
            return realConnection;
        }
    }

    public void connectionFailed() {
        this.deallocate(true, false, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HttpStream newStream(int n, int n2, int n3, boolean bl, boolean bl2) throws RouteException, IOException {
        HttpStream httpStream;
        try {
            RealConnection realConnection = this.findHealthyConnection(n, n2, n3, bl, bl2);
            if (realConnection.framedConnection != null) {
                httpStream = new Http2xStream(this, realConnection.framedConnection);
            } else {
                realConnection.getSocket().setSoTimeout(n2);
                realConnection.source.timeout().timeout(n2, TimeUnit.MILLISECONDS);
                realConnection.sink.timeout().timeout(n3, TimeUnit.MILLISECONDS);
                httpStream = new Http1xStream(this, realConnection.source, realConnection.sink);
            }
            ConnectionPool connectionPool = this.connectionPool;
            synchronized (connectionPool) {
                ++realConnection.streamCount;
                this.stream = httpStream;
            }
        }
        catch (IOException iOException) {
            throw new RouteException(iOException);
        }
        return httpStream;
    }

    public void noNewStreams() {
        this.deallocate(true, false, false);
    }

    public boolean recover(RouteException routeException) {
        RouteSelector routeSelector;
        if (this.canceled) {
            return false;
        }
        if (this.connection != null) {
            this.connectionFailed(routeException.getLastConnectException());
        }
        return ((routeSelector = this.routeSelector) == null || routeSelector.hasNext()) && this.isRecoverable(routeException);
    }

    public boolean recover(IOException iOException, Sink object) {
        int n;
        RealConnection realConnection = this.connection;
        if (realConnection != null) {
            n = realConnection.streamCount;
            this.connectionFailed(iOException);
            if (n == 1) {
                return false;
            }
        }
        n = object != null && !(object instanceof RetryableSink) ? 0 : 1;
        object = this.routeSelector;
        return (object == null || ((RouteSelector)object).hasNext()) && this.isRecoverable(iOException) && n != 0;
        {
        }
    }

    public void release() {
        this.deallocate(false, true, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HttpStream stream() {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            return this.stream;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void streamFinished(HttpStream httpStream) {
        ConnectionPool connectionPool = this.connectionPool;
        synchronized (connectionPool) {
            if (httpStream != null && httpStream == this.stream) {
                // MONITOREXIT [2, 3] lbl4 : MonitorExitStatement: MONITOREXIT : var2_2
                this.deallocate(false, false, true);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected ");
            stringBuilder.append(this.stream);
            stringBuilder.append(" but was ");
            stringBuilder.append(httpStream);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    public String toString() {
        return this.address.toString();
    }
}

