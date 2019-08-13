/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.wifi.aware.DiscoverySession;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.WifiAwareManager;
import android.util.Log;
import java.lang.ref.WeakReference;

public class SubscribeDiscoverySession
extends DiscoverySession {
    private static final String TAG = "SubscribeDiscSession";

    public SubscribeDiscoverySession(WifiAwareManager wifiAwareManager, int n, int n2) {
        super(wifiAwareManager, n, n2);
    }

    public void updateSubscribe(SubscribeConfig subscribeConfig) {
        if (this.mTerminated) {
            Log.w(TAG, "updateSubscribe: called on terminated session");
            return;
        }
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "updateSubscribe: called post GC on WifiAwareManager");
            return;
        }
        wifiAwareManager.updateSubscribe(this.mClientId, this.mSessionId, subscribeConfig);
    }
}

