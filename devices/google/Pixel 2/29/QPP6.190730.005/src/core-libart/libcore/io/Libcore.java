/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Objects;
import libcore.io.BlockGuardOs;
import libcore.io.Linux;
import libcore.io.Os;

public final class Libcore {
    @UnsupportedAppUsage
    public static volatile Os os;
    public static final Os rawOs;

    static {
        rawOs = new Linux();
        os = new BlockGuardOs(rawOs);
    }

    private Libcore() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean compareAndSetOs(Os os, Os os2) {
        Objects.requireNonNull(os2);
        Os os3 = Libcore.os;
        boolean bl = false;
        if (os3 != os) {
            return false;
        }
        synchronized (Libcore.class) {
            if (Libcore.os == os) {
                bl = true;
            }
            if (bl) {
                Libcore.os = os2;
            }
            return bl;
        }
    }

    public static Os getOs() {
        return os;
    }
}

