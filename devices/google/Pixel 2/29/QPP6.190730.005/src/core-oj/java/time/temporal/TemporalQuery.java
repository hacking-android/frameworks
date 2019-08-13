/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.TemporalAccessor;

@FunctionalInterface
public interface TemporalQuery<R> {
    public R queryFrom(TemporalAccessor var1);
}

