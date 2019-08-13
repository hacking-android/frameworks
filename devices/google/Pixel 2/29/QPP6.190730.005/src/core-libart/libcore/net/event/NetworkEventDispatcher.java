/*
 * Decompiled with CFR 0.145.
 */
package libcore.net.event;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.net.event.NetworkEventListener;

public class NetworkEventDispatcher {
    private static final NetworkEventDispatcher instance = new NetworkEventDispatcher();
    private final List<NetworkEventListener> listeners = new CopyOnWriteArrayList<NetworkEventListener>();

    protected NetworkEventDispatcher() {
    }

    @UnsupportedAppUsage
    public static NetworkEventDispatcher getInstance() {
        return instance;
    }

    @UnsupportedAppUsage
    public void addListener(NetworkEventListener networkEventListener) {
        if (networkEventListener != null) {
            this.listeners.add(networkEventListener);
            return;
        }
        throw new NullPointerException("toAdd == null");
    }

    public void onNetworkConfigurationChanged() {
        for (NetworkEventListener networkEventListener : this.listeners) {
            try {
                networkEventListener.onNetworkConfigurationChanged();
            }
            catch (RuntimeException runtimeException) {
                System.logI((String)"Exception thrown during network event propagation", (Throwable)runtimeException);
            }
        }
    }

    public void removeListener(NetworkEventListener networkEventListener) {
        for (NetworkEventListener networkEventListener2 : this.listeners) {
            if (networkEventListener2 != networkEventListener) continue;
            this.listeners.remove(networkEventListener2);
            return;
        }
    }
}

