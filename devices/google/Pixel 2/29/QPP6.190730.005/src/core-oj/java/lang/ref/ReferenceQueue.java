/*
 * Decompiled with CFR 0.145.
 */
package java.lang.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import sun.misc.Cleaner;

public class ReferenceQueue<T> {
    private static final Reference sQueueNextUnenqueued = new PhantomReference<Object>(null, null);
    public static Reference<?> unenqueued = null;
    private Reference<? extends T> head = null;
    private final Object lock = new Object();
    private Reference<? extends T> tail = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void add(Reference<?> reference) {
        synchronized (ReferenceQueue.class) {
            if (unenqueued == null) {
                unenqueued = reference;
            } else {
                Reference<?> reference2 = unenqueued;
                while (reference2.pendingNext != unenqueued) {
                    reference2 = reference2.pendingNext;
                }
                reference2.pendingNext = reference;
                reference2 = reference;
                while (reference2.pendingNext != reference) {
                    reference2 = reference2.pendingNext;
                }
                reference2.pendingNext = unenqueued;
            }
            ReferenceQueue.class.notifyAll();
            return;
        }
    }

    private boolean enqueueLocked(Reference<? extends T> reference) {
        if (reference.queueNext != null) {
            return false;
        }
        if (reference instanceof Cleaner) {
            ((Cleaner)reference).clean();
            reference.queueNext = sQueueNextUnenqueued;
            return true;
        }
        Reference<? extends T> reference2 = this.tail;
        if (reference2 == null) {
            this.head = reference;
        } else {
            reference2.queueNext = reference;
        }
        this.tail = reference;
        this.tail.queueNext = reference;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void enqueuePending(Reference<?> reference) {
        Reference<?> reference2;
        Reference<?> reference3 = reference;
        do {
            ReferenceQueue referenceQueue;
            if ((referenceQueue = reference3.queue) == null) {
                reference2 = reference3.pendingNext;
                reference3.pendingNext = reference3;
            } else {
                Object object = referenceQueue.lock;
                synchronized (object) {
                    do {
                        reference2 = reference3.pendingNext;
                        reference3.pendingNext = reference3;
                        ReferenceQueue.super.enqueueLocked(reference3);
                        if (reference2 == reference) break;
                        reference3 = reference2;
                    } while (reference2.queue == referenceQueue);
                    referenceQueue.lock.notifyAll();
                }
            }
            reference3 = reference2;
        } while (reference2 != reference);
    }

    private Reference<? extends T> reallyPollLocked() {
        Reference<? extends T> reference = this.head;
        if (reference != null) {
            Reference<? extends T> reference2 = this.head;
            if (reference == this.tail) {
                this.tail = null;
                this.head = null;
            } else {
                this.head = reference.queueNext;
            }
            reference2.queueNext = sQueueNextUnenqueued;
            return reference2;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean enqueue(Reference<? extends T> reference) {
        Object object = this.lock;
        synchronized (object) {
            if (this.enqueueLocked(reference)) {
                this.lock.notifyAll();
                return true;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isEnqueued(Reference<? extends T> reference) {
        Object object = this.lock;
        synchronized (object) {
            if (reference.queueNext == null) return false;
            if (reference.queueNext == sQueueNextUnenqueued) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Reference<? extends T> poll() {
        Object object = this.lock;
        synchronized (object) {
            if (this.head != null) return this.reallyPollLocked();
            return null;
        }
    }

    public Reference<? extends T> remove() throws InterruptedException {
        return this.remove(0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Reference<? extends T> remove(long l) throws IllegalArgumentException, InterruptedException {
        if (l < 0L) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        Object object = this.lock;
        synchronized (object) {
            Reference<T> reference = this.reallyPollLocked();
            if (reference != null) {
                return reference;
            }
            long l2 = l == 0L ? 0L : System.nanoTime();
            do {
                this.lock.wait(l);
                reference = this.reallyPollLocked();
                if (reference != null) {
                    return reference;
                }
                if (l == 0L) continue;
                long l3 = System.nanoTime();
                if ((l -= (l3 - l2) / 1000000L) <= 0L) {
                    return null;
                }
                l2 = l3;
            } while (true);
        }
    }
}

