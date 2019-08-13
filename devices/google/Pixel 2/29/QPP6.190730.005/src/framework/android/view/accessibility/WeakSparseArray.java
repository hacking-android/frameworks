/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.util.SparseArray;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class WeakSparseArray<E> {
    private final ReferenceQueue<E> mRefQueue = new ReferenceQueue();
    private final SparseArray<WeakReferenceWithId<E>> mSparseArray = new SparseArray();

    WeakSparseArray() {
    }

    private void removeUnreachableValues() {
        Reference<E> reference = this.mRefQueue.poll();
        while (reference != null) {
            this.mSparseArray.remove(((WeakReferenceWithId)reference).mId);
            reference = this.mRefQueue.poll();
        }
    }

    public void append(int n, E e) {
        this.removeUnreachableValues();
        this.mSparseArray.append(n, new WeakReferenceWithId<E>(e, this.mRefQueue, n));
    }

    public E get(int n) {
        this.removeUnreachableValues();
        WeakReferenceWithId<Object> weakReferenceWithId = this.mSparseArray.get(n);
        weakReferenceWithId = weakReferenceWithId != null ? weakReferenceWithId.get() : null;
        return (E)weakReferenceWithId;
    }

    public void remove(int n) {
        this.removeUnreachableValues();
        this.mSparseArray.remove(n);
    }

    private static class WeakReferenceWithId<E>
    extends WeakReference<E> {
        final int mId;

        WeakReferenceWithId(E e, ReferenceQueue<? super E> referenceQueue, int n) {
            super(e, referenceQueue);
            this.mId = n;
        }
    }

}

