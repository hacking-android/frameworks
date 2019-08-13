/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;

public class NetworkPinner
extends ConnectivityManager.NetworkCallback {
    private static final String TAG = NetworkPinner.class.getSimpleName();
    @GuardedBy(value={"sLock"})
    private static ConnectivityManager sCM;
    @GuardedBy(value={"sLock"})
    private static Callback sCallback;
    @VisibleForTesting
    protected static final Object sLock;
    @GuardedBy(value={"sLock"})
    @VisibleForTesting
    protected static Network sNetwork;

    static {
        sLock = new Object();
    }

    private static void maybeInitConnectivityManager(Context context) {
        if (sCM == null && (sCM = (ConnectivityManager)context.getSystemService("connectivity")) == null) {
            throw new IllegalStateException("Bad luck, ConnectivityService not started.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void pin(Context object, NetworkRequest networkRequest) {
        Object object2 = sLock;
        synchronized (object2) {
            if (sCallback == null) {
                NetworkPinner.maybeInitConnectivityManager((Context)object);
                sCallback = object = new Callback();
                try {
                    sCM.registerNetworkCallback(networkRequest, sCallback);
                }
                catch (SecurityException securityException) {
                    Log.d(TAG, "Failed to register network callback", securityException);
                    sCallback = null;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void unpin() {
        Object object = sLock;
        synchronized (object) {
            Callback callback = sCallback;
            if (callback != null) {
                try {
                    sCM.bindProcessToNetwork(null);
                    sCM.unregisterNetworkCallback(sCallback);
                }
                catch (SecurityException securityException) {
                    Log.d(TAG, "Failed to unregister network callback", securityException);
                }
                sCallback = null;
                sNetwork = null;
            }
            return;
        }
    }

    private static class Callback
    extends ConnectivityManager.NetworkCallback {
        private Callback() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onAvailable(Network network) {
            Object object = sLock;
            synchronized (object) {
                if (this != sCallback) {
                    return;
                }
                if (sCM.getBoundNetworkForProcess() == null && sNetwork == null) {
                    sCM.bindProcessToNetwork(network);
                    sNetwork = network;
                    String string2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Wifi alternate reality enabled on network ");
                    stringBuilder.append(network);
                    Log.d(string2, stringBuilder.toString());
                }
                sLock.notify();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onLost(Network network) {
            Object object = sLock;
            synchronized (object) {
                if (this != sCallback) {
                    return;
                }
                if (network.equals(sNetwork) && network.equals(sCM.getBoundNetworkForProcess())) {
                    NetworkPinner.unpin();
                    String string2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Wifi alternate reality disabled on network ");
                    stringBuilder.append(network);
                    Log.d(string2, stringBuilder.toString());
                }
                sLock.notify();
                return;
            }
        }
    }

}

