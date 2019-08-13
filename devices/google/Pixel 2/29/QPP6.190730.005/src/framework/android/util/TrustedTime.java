/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;

public interface TrustedTime {
    @UnsupportedAppUsage
    public long currentTimeMillis();

    @UnsupportedAppUsage
    public boolean forceRefresh();

    @UnsupportedAppUsage
    public long getCacheAge();

    public long getCacheCertainty();

    @UnsupportedAppUsage
    public boolean hasCache();
}

