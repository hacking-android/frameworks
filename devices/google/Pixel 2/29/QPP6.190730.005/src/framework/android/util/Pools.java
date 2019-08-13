/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;

public final class Pools {
    private Pools() {
    }

    public static interface Pool<T> {
        @UnsupportedAppUsage
        public T acquire();

        @UnsupportedAppUsage
        public boolean release(T var1);
    }

    public static class SimplePool<T>
    implements Pool<T> {
        @UnsupportedAppUsage
        private final Object[] mPool;
        private int mPoolSize;

        @UnsupportedAppUsage
        public SimplePool(int n) {
            if (n > 0) {
                this.mPool = new Object[n];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        private boolean isInPool(T t) {
            for (int i = 0; i < this.mPoolSize; ++i) {
                if (this.mPool[i] != t) continue;
                return true;
            }
            return false;
        }

        @UnsupportedAppUsage
        @Override
        public T acquire() {
            int n = this.mPoolSize;
            if (n > 0) {
                int n2 = n - 1;
                Object[] arrobject = this.mPool;
                Object object = arrobject[n2];
                arrobject[n2] = null;
                this.mPoolSize = n - 1;
                return (T)object;
            }
            return null;
        }

        @UnsupportedAppUsage
        @Override
        public boolean release(T t) {
            if (!this.isInPool(t)) {
                int n = this.mPoolSize;
                Object[] arrobject = this.mPool;
                if (n < arrobject.length) {
                    arrobject[n] = t;
                    this.mPoolSize = n + 1;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("Already in the pool!");
        }
    }

    public static class SynchronizedPool<T>
    extends SimplePool<T> {
        private final Object mLock;

        @UnsupportedAppUsage
        public SynchronizedPool(int n) {
            this(n, new Object());
        }

        public SynchronizedPool(int n, Object object) {
            super(n);
            this.mLock = object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        @Override
        public T acquire() {
            Object object = this.mLock;
            synchronized (object) {
                Object t = super.acquire();
                return t;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        @Override
        public boolean release(T t) {
            Object object = this.mLock;
            synchronized (object) {
                return super.release(t);
            }
        }
    }

}

