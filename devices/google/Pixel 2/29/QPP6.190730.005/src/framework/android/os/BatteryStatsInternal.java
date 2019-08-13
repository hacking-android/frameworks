/*
 * Decompiled with CFR 0.145.
 */
package android.os;

public abstract class BatteryStatsInternal {
    public abstract String[] getMobileIfaces();

    public abstract String[] getWifiIfaces();

    public abstract void noteJobsDeferred(int var1, int var2, long var3);
}

