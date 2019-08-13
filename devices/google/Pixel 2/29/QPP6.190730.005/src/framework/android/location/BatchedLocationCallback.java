/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.location.Location;
import java.util.List;

@SystemApi
public abstract class BatchedLocationCallback {
    public void onLocationBatch(List<Location> list) {
    }
}

