/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Supplier;

public final class RuntimeHooks {
    private static Supplier<String> zoneIdSupplier;

    private RuntimeHooks() {
    }

    public static Supplier<String> getTimeZoneIdSupplier() {
        return zoneIdSupplier;
    }

    public static void setTimeZoneIdSupplier(Supplier<String> supplier) {
        if (zoneIdSupplier == null) {
            zoneIdSupplier = Objects.requireNonNull(supplier);
            TimeZone.setDefault(null);
            return;
        }
        throw new UnsupportedOperationException("zoneIdSupplier instance already set");
    }

    public static void setUncaughtExceptionPreHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Thread.setUncaughtExceptionPreHandler((Thread.UncaughtExceptionHandler)uncaughtExceptionHandler);
    }
}

