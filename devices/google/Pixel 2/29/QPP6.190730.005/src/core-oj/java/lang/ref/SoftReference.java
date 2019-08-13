/*
 * Decompiled with CFR 0.145.
 */
package java.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class SoftReference<T>
extends Reference<T> {
    private static long clock;
    private long timestamp = clock;

    public SoftReference(T t) {
        super(t);
    }

    public SoftReference(T t, ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }

    @Override
    public T get() {
        long l;
        long l2;
        Object t = super.get();
        if (t != null && (l = this.timestamp) != (l2 = clock)) {
            this.timestamp = l2;
        }
        return t;
    }
}

