/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.event.NetworkEventDispatcher
 *  libcore.net.event.NetworkEventListener
 */
package com.android.okhttp;

import com.android.okhttp.ConnectionPool;
import libcore.net.event.NetworkEventDispatcher;
import libcore.net.event.NetworkEventListener;

public class ConfigAwareConnectionPool {
    private static final long CONNECTION_POOL_DEFAULT_KEEP_ALIVE_DURATION_MS = 300000L;
    private static final long CONNECTION_POOL_KEEP_ALIVE_DURATION_MS;
    private static final int CONNECTION_POOL_MAX_IDLE_CONNECTIONS;
    private static final ConfigAwareConnectionPool instance;
    private ConnectionPool connectionPool;
    private final NetworkEventDispatcher networkEventDispatcher;
    private boolean networkEventListenerRegistered;

    static {
        String string = System.getProperty("http.keepAlive");
        String string2 = System.getProperty("http.keepAliveDuration");
        String string3 = System.getProperty("http.maxConnections");
        long l = string2 != null ? Long.parseLong(string2) : 300000L;
        CONNECTION_POOL_KEEP_ALIVE_DURATION_MS = l;
        CONNECTION_POOL_MAX_IDLE_CONNECTIONS = string != null && !Boolean.parseBoolean(string) ? 0 : (string3 != null ? Integer.parseInt(string3) : 5);
        instance = new ConfigAwareConnectionPool();
    }

    private ConfigAwareConnectionPool() {
        this.networkEventDispatcher = NetworkEventDispatcher.getInstance();
    }

    protected ConfigAwareConnectionPool(NetworkEventDispatcher networkEventDispatcher) {
        this.networkEventDispatcher = networkEventDispatcher;
    }

    public static ConfigAwareConnectionPool getInstance() {
        return instance;
    }

    public ConnectionPool get() {
        synchronized (this) {
            Object object;
            if (this.connectionPool == null) {
                if (!this.networkEventListenerRegistered) {
                    NetworkEventDispatcher networkEventDispatcher = this.networkEventDispatcher;
                    object = new NetworkEventListener(){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        public void onNetworkConfigurationChanged() {
                            ConfigAwareConnectionPool configAwareConnectionPool = ConfigAwareConnectionPool.this;
                            synchronized (configAwareConnectionPool) {
                                ConfigAwareConnectionPool.this.connectionPool = null;
                                return;
                            }
                        }
                    };
                    networkEventDispatcher.addListener((NetworkEventListener)object);
                    this.networkEventListenerRegistered = true;
                }
                this.connectionPool = object = new ConnectionPool(CONNECTION_POOL_MAX_IDLE_CONNECTIONS, CONNECTION_POOL_KEEP_ALIVE_DURATION_MS);
            }
            object = this.connectionPool;
            return object;
        }
    }

}

