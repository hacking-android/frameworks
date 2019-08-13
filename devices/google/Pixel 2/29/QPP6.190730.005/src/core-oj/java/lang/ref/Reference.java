/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package java.lang.ref;

import dalvik.annotation.optimization.FastNative;
import java.lang.ref.ReferenceQueue;

public abstract class Reference<T> {
    private static boolean disableIntrinsic = false;
    private static boolean slowPathEnabled = false;
    Reference<?> pendingNext;
    final ReferenceQueue<? super T> queue;
    Reference queueNext;
    volatile T referent;

    Reference(T t) {
        this(t, null);
    }

    Reference(T t, ReferenceQueue<? super T> referenceQueue) {
        this.referent = t;
        this.queue = referenceQueue;
    }

    @FastNative
    private final native T getReferent();

    public static void reachabilityFence(Object object) {
        SinkHolder.sink = object;
        if (SinkHolder.finalize_count == 0) {
            SinkHolder.sink = null;
        }
    }

    public void clear() {
        this.clearReferent();
    }

    @FastNative
    native void clearReferent();

    public boolean enqueue() {
        ReferenceQueue<T> referenceQueue = this.queue;
        boolean bl = referenceQueue != null && referenceQueue.enqueue(this);
        return bl;
    }

    public T get() {
        return this.getReferent();
    }

    public boolean isEnqueued() {
        ReferenceQueue<T> referenceQueue = this.queue;
        boolean bl = referenceQueue != null && referenceQueue.isEnqueued(this);
        return bl;
    }

    private static class SinkHolder {
        private static volatile int finalize_count = 0;
        static volatile Object sink;
        private static Object sinkUser;

        static {
            sinkUser = new Object(){

                protected void finalize() {
                    if (sink == null && finalize_count > 0) {
                        throw new AssertionError((Object)"Can't get here");
                    }
                    SinkHolder.access$008();
                }
            };
        }

        private SinkHolder() {
        }

        static /* synthetic */ int access$008() {
            int n = finalize_count;
            finalize_count = n + 1;
            return n;
        }

    }

}

