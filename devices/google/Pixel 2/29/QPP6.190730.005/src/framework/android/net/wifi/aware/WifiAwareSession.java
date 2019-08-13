/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.net.wifi.aware;

import android.annotation.SystemApi;
import android.net.NetworkSpecifier;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareUtils;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public class WifiAwareSession
implements AutoCloseable {
    private static final boolean DBG = false;
    private static final String TAG = "WifiAwareSession";
    private static final boolean VDBG = false;
    private final Binder mBinder;
    private final int mClientId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final WeakReference<WifiAwareManager> mMgr;
    private boolean mTerminated = true;

    public WifiAwareSession(WifiAwareManager wifiAwareManager, Binder binder, int n) {
        this.mMgr = new WeakReference<WifiAwareManager>(wifiAwareManager);
        this.mBinder = binder;
        this.mClientId = n;
        this.mTerminated = false;
        this.mCloseGuard.open("close");
    }

    @Override
    public void close() {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "destroy: called post GC on WifiAwareManager");
            return;
        }
        wifiAwareManager.disconnect(this.mClientId, this.mBinder);
        this.mTerminated = true;
        this.mMgr.clear();
        this.mCloseGuard.close();
    }

    public NetworkSpecifier createNetworkSpecifierOpen(int n, byte[] arrby) {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.e(TAG, "createNetworkSpecifierOpen: called post GC on WifiAwareManager");
            return null;
        }
        if (this.mTerminated) {
            Log.e(TAG, "createNetworkSpecifierOpen: called after termination");
            return null;
        }
        return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, arrby, null, null);
    }

    public NetworkSpecifier createNetworkSpecifierPassphrase(int n, byte[] arrby, String string2) {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.e(TAG, "createNetworkSpecifierPassphrase: called post GC on WifiAwareManager");
            return null;
        }
        if (this.mTerminated) {
            Log.e(TAG, "createNetworkSpecifierPassphrase: called after termination");
            return null;
        }
        if (WifiAwareUtils.validatePassphrase(string2)) {
            return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, arrby, null, string2);
        }
        throw new IllegalArgumentException("Passphrase must meet length requirements");
    }

    @SystemApi
    public NetworkSpecifier createNetworkSpecifierPmk(int n, byte[] arrby, byte[] arrby2) {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.e(TAG, "createNetworkSpecifierPmk: called post GC on WifiAwareManager");
            return null;
        }
        if (this.mTerminated) {
            Log.e(TAG, "createNetworkSpecifierPmk: called after termination");
            return null;
        }
        if (WifiAwareUtils.validatePmk(arrby2)) {
            return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, arrby, arrby2, null);
        }
        throw new IllegalArgumentException("PMK must 32 bytes");
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mTerminated) {
                this.close();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    @VisibleForTesting
    public int getClientId() {
        return this.mClientId;
    }

    public void publish(PublishConfig publishConfig, DiscoverySessionCallback discoverySessionCallback, Handler object) {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.e(TAG, "publish: called post GC on WifiAwareManager");
            return;
        }
        if (this.mTerminated) {
            Log.e(TAG, "publish: called after termination");
            return;
        }
        int n = this.mClientId;
        object = object == null ? Looper.getMainLooper() : ((Handler)object).getLooper();
        wifiAwareManager.publish(n, (Looper)object, publishConfig, discoverySessionCallback);
    }

    public void subscribe(SubscribeConfig subscribeConfig, DiscoverySessionCallback discoverySessionCallback, Handler object) {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.e(TAG, "publish: called post GC on WifiAwareManager");
            return;
        }
        if (this.mTerminated) {
            Log.e(TAG, "publish: called after termination");
            return;
        }
        int n = this.mClientId;
        object = object == null ? Looper.getMainLooper() : ((Handler)object).getLooper();
        wifiAwareManager.subscribe(n, (Looper)object, subscribeConfig, discoverySessionCallback);
    }
}

