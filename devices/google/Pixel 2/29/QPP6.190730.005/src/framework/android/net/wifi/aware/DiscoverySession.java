/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.net.wifi.aware;

import android.annotation.SystemApi;
import android.net.NetworkSpecifier;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

public class DiscoverySession
implements AutoCloseable {
    private static final boolean DBG = false;
    private static final int MAX_SEND_RETRY_COUNT = 5;
    private static final String TAG = "DiscoverySession";
    private static final boolean VDBG = false;
    protected final int mClientId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    protected WeakReference<WifiAwareManager> mMgr;
    protected final int mSessionId;
    protected boolean mTerminated = false;

    public DiscoverySession(WifiAwareManager wifiAwareManager, int n, int n2) {
        this.mMgr = new WeakReference<WifiAwareManager>(wifiAwareManager);
        this.mClientId = n;
        this.mSessionId = n2;
        this.mCloseGuard.open("close");
    }

    public static int getMaxSendRetryCount() {
        return 5;
    }

    @Override
    public void close() {
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "destroy: called post GC on WifiAwareManager");
            return;
        }
        wifiAwareManager.terminateSession(this.mClientId, this.mSessionId);
        this.mTerminated = true;
        this.mMgr.clear();
        this.mCloseGuard.close();
    }

    @Deprecated
    public NetworkSpecifier createNetworkSpecifierOpen(PeerHandle peerHandle) {
        if (this.mTerminated) {
            Log.w(TAG, "createNetworkSpecifierOpen: called on terminated session");
            return null;
        }
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "createNetworkSpecifierOpen: called post GC on WifiAwareManager");
            return null;
        }
        int n = this instanceof SubscribeDiscoverySession ? 0 : 1;
        return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, this.mSessionId, peerHandle, null, null);
    }

    @Deprecated
    public NetworkSpecifier createNetworkSpecifierPassphrase(PeerHandle peerHandle, String string2) {
        if (WifiAwareUtils.validatePassphrase(string2)) {
            if (this.mTerminated) {
                Log.w(TAG, "createNetworkSpecifierPassphrase: called on terminated session");
                return null;
            }
            WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
            if (wifiAwareManager == null) {
                Log.w(TAG, "createNetworkSpecifierPassphrase: called post GC on WifiAwareManager");
                return null;
            }
            int n = this instanceof SubscribeDiscoverySession ? 0 : 1;
            return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, this.mSessionId, peerHandle, null, string2);
        }
        throw new IllegalArgumentException("Passphrase must meet length requirements");
    }

    @SystemApi
    @Deprecated
    public NetworkSpecifier createNetworkSpecifierPmk(PeerHandle peerHandle, byte[] arrby) {
        if (WifiAwareUtils.validatePmk(arrby)) {
            if (this.mTerminated) {
                Log.w(TAG, "createNetworkSpecifierPmk: called on terminated session");
                return null;
            }
            WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
            if (wifiAwareManager == null) {
                Log.w(TAG, "createNetworkSpecifierPmk: called post GC on WifiAwareManager");
                return null;
            }
            int n = this instanceof SubscribeDiscoverySession ? 0 : 1;
            return wifiAwareManager.createNetworkSpecifier(this.mClientId, n, this.mSessionId, peerHandle, arrby, null);
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

    @VisibleForTesting
    public int getSessionId() {
        return this.mSessionId;
    }

    public void sendMessage(PeerHandle peerHandle, int n, byte[] arrby) {
        this.sendMessage(peerHandle, n, arrby, 0);
    }

    public void sendMessage(PeerHandle peerHandle, int n, byte[] arrby, int n2) {
        if (this.mTerminated) {
            Log.w(TAG, "sendMessage: called on terminated session");
            return;
        }
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "sendMessage: called post GC on WifiAwareManager");
            return;
        }
        wifiAwareManager.sendMessage(this.mClientId, this.mSessionId, peerHandle, arrby, n, n2);
    }

    public void setTerminated() {
        if (this.mTerminated) {
            Log.w(TAG, "terminate: already terminated.");
            return;
        }
        this.mTerminated = true;
        this.mMgr.clear();
        this.mCloseGuard.close();
    }
}

