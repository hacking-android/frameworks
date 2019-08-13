/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.URL;

public interface ProgressMeteringPolicy {
    public int getProgressUpdateThreshold();

    public boolean shouldMeterInput(URL var1, String var2);
}

