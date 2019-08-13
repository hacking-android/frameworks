/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.wifi.aware.DiscoverySession;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.WifiAwareManager;
import android.util.Log;
import java.lang.ref.WeakReference;

public class PublishDiscoverySession
extends DiscoverySession {
    private static final String TAG = "PublishDiscoverySession";

    public PublishDiscoverySession(WifiAwareManager wifiAwareManager, int n, int n2) {
        super(wifiAwareManager, n, n2);
    }

    public void updatePublish(PublishConfig publishConfig) {
        if (this.mTerminated) {
            Log.w(TAG, "updatePublish: called on terminated session");
            return;
        }
        WifiAwareManager wifiAwareManager = (WifiAwareManager)this.mMgr.get();
        if (wifiAwareManager == null) {
            Log.w(TAG, "updatePublish: called post GC on WifiAwareManager");
            return;
        }
        wifiAwareManager.updatePublish(this.mClientId, this.mSessionId, publishConfig);
    }
}

