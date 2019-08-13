/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.EventListener;

public abstract class EventListenerProxy<T extends EventListener>
implements EventListener {
    private final T listener;

    public EventListenerProxy(T t) {
        this.listener = t;
    }

    public T getListener() {
        return this.listener;
    }
}

