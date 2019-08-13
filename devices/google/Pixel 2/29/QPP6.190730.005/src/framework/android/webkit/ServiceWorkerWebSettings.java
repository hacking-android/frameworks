/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

public abstract class ServiceWorkerWebSettings {
    public abstract boolean getAllowContentAccess();

    public abstract boolean getAllowFileAccess();

    public abstract boolean getBlockNetworkLoads();

    public abstract int getCacheMode();

    public abstract void setAllowContentAccess(boolean var1);

    public abstract void setAllowFileAccess(boolean var1);

    public abstract void setBlockNetworkLoads(boolean var1);

    public abstract void setCacheMode(int var1);
}

