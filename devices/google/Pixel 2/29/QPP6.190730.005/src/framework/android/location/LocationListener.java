/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.Location;
import android.os.Bundle;

public interface LocationListener {
    public void onLocationChanged(Location var1);

    public void onProviderDisabled(String var1);

    public void onProviderEnabled(String var1);

    @Deprecated
    public void onStatusChanged(String var1, int var2, Bundle var3);
}

