/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;

public final class AsynchronousCloseMonitor {
    private AsynchronousCloseMonitor() {
    }

    @UnsupportedAppUsage
    public static native void signalBlockedThreads(FileDescriptor var0);
}

