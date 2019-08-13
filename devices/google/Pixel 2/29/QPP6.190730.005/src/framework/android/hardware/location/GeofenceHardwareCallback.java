/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.location.Location;

@SystemApi
public abstract class GeofenceHardwareCallback {
    public void onGeofenceAdd(int n, int n2) {
    }

    public void onGeofencePause(int n, int n2) {
    }

    public void onGeofenceRemove(int n, int n2) {
    }

    public void onGeofenceResume(int n, int n2) {
    }

    public void onGeofenceTransition(int n, int n2, Location location, long l, int n3) {
    }
}

