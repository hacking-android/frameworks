/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp;

import com.android.okhttp.Address;
import com.android.okhttp.Route;
import com.android.okhttp.internal.RouteDatabase;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.io.RealConnection;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000L;
    @UnsupportedAppUsage
    private static final ConnectionPool systemDefault;
    private Runnable cleanupRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Throwable throwable22;
            do {
                long l;
                if ((l = ConnectionPool.this.cleanup(System.nanoTime())) == -1L) {
                    return;
                }
                if (l <= 0L) continue;
                long l2 = l / 1000000L;
                ConnectionPool connectionPool = ConnectionPool.this;
                synchronized (connectionPool) {
                    try {
                        try {
                            ConnectionPool.this.wait(l2, (int)(l - 1000000L * l2));
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                        continue;
                    }
                    catch (Throwable throwable22) {}
                    break;
                }
            } while (true);
            {
                throw throwable22;
            }
        }
    };
    @UnsupportedAppUsage
    private final Deque<RealConnection> connections = new ArrayDeque<RealConnection>();
    private final Executor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
    @UnsupportedAppUsage
    private final long keepAliveDurationNs;
    @UnsupportedAppUsage
    private final int maxIdleConnections;
    final RouteDatabase routeDatabase = new RouteDatabase();

    static {
        String string = System.getProperty("http.keepAlive");
        String string2 = System.getProperty("http.keepAliveDuration");
        String string3 = System.getProperty("http.maxConnections");
        long l = string2 != null ? Long.parseLong(string2) : 300000L;
        systemDefault = string != null && !Boolean.parseBoolean(string) ? new ConnectionPool(0, l) : (string3 != null ? new ConnectionPool(Integer.parseInt(string3), l) : new ConnectionPool(5, l));
    }

    public ConnectionPool(int n, long l) {
        this(n, l, TimeUnit.MILLISECONDS);
    }

    public ConnectionPool(int n, long l, TimeUnit object) {
        this.maxIdleConnections = n;
        this.keepAliveDurationNs = ((TimeUnit)((Object)object)).toNanos(l);
        if (l > 0L) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("keepAliveDuration <= 0: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    private int pruneAndGetAllocationCount(RealConnection realConnection, long l) {
        List<Reference<StreamAllocation>> list = realConnection.allocations;
        int n = 0;
        while (n < list.size()) {
            if (list.get(n).get() != null) {
                ++n;
                continue;
            }
            list.remove(n);
            realConnection.noNewStreams = true;
            if (!list.isEmpty()) continue;
            realConnection.idleAtNanos = l - this.keepAliveDurationNs;
            return 0;
        }
        return list.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    long cleanup(long l) {
        int n = 0;
        int n2 = 0;
        RealConnection realConnection = null;
        long l2 = Long.MIN_VALUE;
        synchronized (this) {
            for (RealConnection realConnection2 : this.connections) {
                if (this.pruneAndGetAllocationCount(realConnection2, l) > 0) {
                    ++n;
                    continue;
                }
                ++n2;
                long l3 = l - realConnection2.idleAtNanos;
                long l4 = l2;
                if (l3 > l2) {
                    l4 = l3;
                    realConnection = realConnection2;
                }
                l2 = l4;
            }
            if (l2 < this.keepAliveDurationNs && n2 <= this.maxIdleConnections) {
                if (n2 > 0) {
                    l = this.keepAliveDurationNs;
                    return l - l2;
                }
                if (n <= 0) return -1L;
                return this.keepAliveDurationNs;
            }
            this.connections.remove(realConnection);
        }
        Util.closeQuietly(realConnection.getSocket());
        return 0L;
    }

    boolean connectionBecameIdle(RealConnection realConnection) {
        if (!realConnection.noNewStreams && this.maxIdleConnections != 0) {
            this.notifyAll();
            return false;
        }
        this.connections.remove(realConnection);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void evictAll() {
        Iterator<RealConnection> iterator;
        ArrayList<RealConnection> arrayList = new ArrayList<RealConnection>();
        synchronized (this) {
            iterator = this.connections.iterator();
            while (iterator.hasNext()) {
                RealConnection realConnection = iterator.next();
                if (!realConnection.allocations.isEmpty()) continue;
                realConnection.noNewStreams = true;
                arrayList.add(realConnection);
                iterator.remove();
            }
        }
        iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Util.closeQuietly(iterator.next().getSocket());
        }
        return;
    }

    RealConnection get(Address address, StreamAllocation streamAllocation) {
        for (RealConnection realConnection : this.connections) {
            if (realConnection.allocations.size() >= realConnection.allocationLimit() || !address.equals(realConnection.getRoute().address) || realConnection.noNewStreams) continue;
            streamAllocation.acquire(realConnection);
            return realConnection;
        }
        return null;
    }

    public int getConnectionCount() {
        synchronized (this) {
            int n = this.connections.size();
            return n;
        }
    }

    public int getHttpConnectionCount() {
        synchronized (this) {
            int n = this.connections.size();
            int n2 = this.getMultiplexedConnectionCount();
            return n - n2;
        }
    }

    public int getIdleConnectionCount() {
        synchronized (this) {
            int n;
            int n2 = 0;
            Iterator<RealConnection> iterator = this.connections.iterator();
            while (iterator.hasNext()) {
                boolean bl = iterator.next().allocations.isEmpty();
                n = n2;
                if (!bl) break block4;
            }
            {
                block4 : {
                    n = n2 + 1;
                }
                n2 = n;
                continue;
            }
            return n2;
        }
    }

    public int getMultiplexedConnectionCount() {
        synchronized (this) {
            int n;
            int n2 = 0;
            Iterator<RealConnection> iterator = this.connections.iterator();
            while (iterator.hasNext()) {
                boolean bl = iterator.next().isMultiplexed();
                n = n2;
                if (!bl) break block4;
            }
            {
                block4 : {
                    n = n2 + 1;
                }
                n2 = n;
                continue;
            }
            return n2;
        }
    }

    @Deprecated
    public int getSpdyConnectionCount() {
        synchronized (this) {
            int n = this.getMultiplexedConnectionCount();
            return n;
        }
    }

    void put(RealConnection realConnection) {
        if (this.connections.isEmpty()) {
            this.executor.execute(this.cleanupRunnable);
        }
        this.connections.add(realConnection);
    }

    void setCleanupRunnableForTest(Runnable runnable) {
        this.cleanupRunnable = runnable;
    }

}

