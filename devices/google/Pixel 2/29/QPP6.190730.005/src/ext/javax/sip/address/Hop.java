/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.address;

public interface Hop {
    public String getHost();

    public int getPort();

    public String getTransport();

    public boolean isURIRoute();

    public void setURIRouteFlag();

    public String toString();
}

