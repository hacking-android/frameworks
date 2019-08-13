/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class InheritableThreadLocal<T>
extends ThreadLocal<T> {
    @Override
    protected T childValue(T t) {
        return t;
    }

    @Override
    void createMap(Thread thread, T t) {
        thread.inheritableThreadLocals = new ThreadLocal.ThreadLocalMap(this, t);
    }

    @Override
    ThreadLocal.ThreadLocalMap getMap(Thread thread) {
        return thread.inheritableThreadLocals;
    }
}

