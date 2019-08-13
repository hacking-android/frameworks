/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.util.logging.LogRecord;

@FunctionalInterface
public interface Filter {
    public boolean isLoggable(LogRecord var1);
}

