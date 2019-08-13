/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.URL;
import sun.net.ProgressMeteringPolicy;

class DefaultProgressMeteringPolicy
implements ProgressMeteringPolicy {
    DefaultProgressMeteringPolicy() {
    }

    @Override
    public int getProgressUpdateThreshold() {
        return 8192;
    }

    @Override
    public boolean shouldMeterInput(URL uRL, String string) {
        return false;
    }
}

