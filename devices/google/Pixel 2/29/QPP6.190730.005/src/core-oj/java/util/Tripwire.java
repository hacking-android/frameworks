/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.-$
 *  java.util.-$$Lambda
 *  java.util.-$$Lambda$Tripwire
 *  java.util.-$$Lambda$Tripwire$03Zb3z-rd6SqpmwW72AFPa8slaw
 */
package java.util;

import java.security.AccessController;
import java.util.-$;
import java.util._$$Lambda$Tripwire$03Zb3z_rd6SqpmwW72AFPa8slaw;
import sun.util.logging.PlatformLogger;

final class Tripwire {
    static final boolean ENABLED = (Boolean)AccessController.doPrivileged(_$$Lambda$Tripwire$03Zb3z_rd6SqpmwW72AFPa8slaw.INSTANCE);
    private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";

    private Tripwire() {
    }

    static /* synthetic */ Boolean lambda$static$0() {
        return Boolean.getBoolean(TRIPWIRE_PROPERTY);
    }

    static void trip(Class<?> class_, String string) {
        PlatformLogger.getLogger(class_.getName()).warning(string, class_.getName());
    }
}

