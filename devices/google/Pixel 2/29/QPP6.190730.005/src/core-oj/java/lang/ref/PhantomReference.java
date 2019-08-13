/*
 * Decompiled with CFR 0.145.
 */
package java.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReference<T>
extends Reference<T> {
    public PhantomReference(T t, ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }

    @Override
    public T get() {
        return null;
    }
}

