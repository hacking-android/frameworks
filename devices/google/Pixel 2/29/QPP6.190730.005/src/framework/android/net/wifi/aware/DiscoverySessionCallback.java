/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeDiscoverySession;
import java.util.List;

public class DiscoverySessionCallback {
    public void onMessageReceived(PeerHandle peerHandle, byte[] arrby) {
    }

    public void onMessageSendFailed(int n) {
    }

    public void onMessageSendSucceeded(int n) {
    }

    public void onPublishStarted(PublishDiscoverySession publishDiscoverySession) {
    }

    public void onServiceDiscovered(PeerHandle peerHandle, byte[] arrby, List<byte[]> list) {
    }

    public void onServiceDiscoveredWithinRange(PeerHandle peerHandle, byte[] arrby, List<byte[]> list, int n) {
    }

    public void onSessionConfigFailed() {
    }

    public void onSessionConfigUpdated() {
    }

    public void onSessionTerminated() {
    }

    public void onSubscribeStarted(SubscribeDiscoverySession subscribeDiscoverySession) {
    }
}

