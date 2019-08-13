/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.location.ContextHubClient;
import android.hardware.location.NanoAppMessage;

@SystemApi
public class ContextHubClientCallback {
    public void onHubReset(ContextHubClient contextHubClient) {
    }

    public void onMessageFromNanoApp(ContextHubClient contextHubClient, NanoAppMessage nanoAppMessage) {
    }

    public void onNanoAppAborted(ContextHubClient contextHubClient, long l, int n) {
    }

    public void onNanoAppDisabled(ContextHubClient contextHubClient, long l) {
    }

    public void onNanoAppEnabled(ContextHubClient contextHubClient, long l) {
    }

    public void onNanoAppLoaded(ContextHubClient contextHubClient, long l) {
    }

    public void onNanoAppUnloaded(ContextHubClient contextHubClient, long l) {
    }
}

