/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.Future;

public interface RunnableFuture<V>
extends Runnable,
Future<V> {
    @Override
    public void run();
}

