/*
 * Decompiled with CFR 0.145.
 */
package java.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class WeakReference<T>
extends Reference<T> {
    public WeakReference(T t) {
        super(t);
    }

    public WeakReference(T t, ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }
}

