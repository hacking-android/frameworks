/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Tripwire
 *  java.util.stream.-$$Lambda$Tripwire$h-WLrZCXxuA6HLdDg4eUp2SowfQ
 */
package java.util.stream;

import java.security.AccessController;
import java.util.stream.-$;
import java.util.stream._$$Lambda$Tripwire$h_WLrZCXxuA6HLdDg4eUp2SowfQ;
import sun.util.logging.PlatformLogger;

final class Tripwire {
    static final boolean ENABLED = (Boolean)AccessController.doPrivileged(_$$Lambda$Tripwire$h_WLrZCXxuA6HLdDg4eUp2SowfQ.INSTANCE);
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

