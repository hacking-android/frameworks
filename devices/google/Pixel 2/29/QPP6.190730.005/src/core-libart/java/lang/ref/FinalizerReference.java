/*
 * Decompiled with CFR 0.145.
 */
package java.lang.ref;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public final class FinalizerReference<T>
extends Reference<T> {
    private static final Object LIST_LOCK;
    @UnsupportedAppUsage
    private static FinalizerReference<?> head;
    @UnsupportedAppUsage
    public static final ReferenceQueue<Object> queue;
    @UnsupportedAppUsage
    private FinalizerReference<?> next;
    private FinalizerReference<?> prev;
    private T zombie;

    static {
        queue = new ReferenceQueue();
        LIST_LOCK = new Object();
        head = null;
    }

    public FinalizerReference(T t, ReferenceQueue<? super T> referenceQueue) {
        super(t, referenceQueue);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void add(Object object) {
        FinalizerReference<Object> finalizerReference = new FinalizerReference<Object>(object, queue);
        object = LIST_LOCK;
        synchronized (object) {
            finalizerReference.prev = null;
            finalizerReference.next = head;
            if (head != null) {
                FinalizerReference.head.prev = finalizerReference;
            }
            head = finalizerReference;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean enqueueSentinelReference(Sentinel sentinel) {
        Object object = LIST_LOCK;
        synchronized (object) {
            FinalizerReference<?> finalizerReference = head;
            do {
                if (finalizerReference == null) {
                    throw new AssertionError((Object)"newly-created live Sentinel not on list!");
                }
                if (FinalizerReference.super.getReferent() == sentinel) {
                    finalizerReference.clearReferent();
                    finalizerReference.zombie = sentinel;
                    if (!FinalizerReference.super.makeCircularListIfUnenqueued()) {
                        return false;
                    }
                    ReferenceQueue.add(finalizerReference);
                    return true;
                }
                finalizerReference = finalizerReference.next;
            } while (true);
        }
    }

    public static void finalizeAllEnqueued(long l) throws InterruptedException {
        Sentinel sentinel;
        while (!FinalizerReference.enqueueSentinelReference(sentinel = new Sentinel())) {
        }
        sentinel.awaitFinalization(l);
    }

    @FastNative
    private final native T getReferent();

    @FastNative
    private native boolean makeCircularListIfUnenqueued();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void remove(FinalizerReference<?> finalizerReference) {
        Object object = LIST_LOCK;
        synchronized (object) {
            FinalizerReference<?> finalizerReference2 = finalizerReference.next;
            FinalizerReference<?> finalizerReference3 = finalizerReference.prev;
            finalizerReference.next = null;
            finalizerReference.prev = null;
            if (finalizerReference3 != null) {
                finalizerReference3.next = finalizerReference2;
            } else {
                head = finalizerReference2;
            }
            if (finalizerReference2 != null) {
                finalizerReference2.prev = finalizerReference3;
            }
            return;
        }
    }

    @Override
    public void clear() {
        this.zombie = null;
    }

    @Override
    public T get() {
        return this.zombie;
    }

    private static class Sentinel {
        boolean finalized = false;

        private Sentinel() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void awaitFinalization(long l) throws InterruptedException {
            synchronized (this) {
                long l2 = System.nanoTime() + l;
                while (!this.finalized) {
                    if (l != 0L) {
                        long l3 = System.nanoTime();
                        if (l3 >= l2) break;
                        l3 = l2 - l3;
                        this.wait(l3 / 1000000L, (int)(l3 % 1000000L));
                        continue;
                    }
                    this.wait();
                }
                return;
            }
        }

        protected void finalize() throws Throwable {
            synchronized (this) {
                if (!this.finalized) {
                    this.finalized = true;
                    this.notifyAll();
                    return;
                }
                AssertionError assertionError = new AssertionError();
                throw assertionError;
            }
        }
    }

}

