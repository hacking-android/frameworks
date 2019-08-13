/*
 * Decompiled with CFR 0.145.
 */
package android.debug;

import android.debug.IAdbTransport;
import java.io.File;

public abstract class AdbManagerInternal {
    public abstract File getAdbKeysFile();

    public abstract File getAdbTempKeysFile();

    public abstract boolean isAdbEnabled();

    public abstract void registerTransport(IAdbTransport var1);

    public abstract void unregisterTransport(IAdbTransport var1);
}

