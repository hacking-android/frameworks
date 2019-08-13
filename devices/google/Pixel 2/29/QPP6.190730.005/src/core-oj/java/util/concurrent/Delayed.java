/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.TimeUnit;

public interface Delayed
extends Comparable<Delayed> {
    public long getDelay(TimeUnit var1);
}

