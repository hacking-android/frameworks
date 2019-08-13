/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

@FunctionalInterface
public interface Callable<V> {
    public V call() throws Exception;
}

