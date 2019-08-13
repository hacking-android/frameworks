/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;

public interface RunnableScheduledFuture<V>
extends RunnableFuture<V>,
ScheduledFuture<V> {
    public boolean isPeriodic();
}

