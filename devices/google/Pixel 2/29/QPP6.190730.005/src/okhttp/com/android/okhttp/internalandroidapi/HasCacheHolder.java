/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internalandroidapi;

import com.android.okhttp.Cache;
import java.io.File;

public interface HasCacheHolder {
    public CacheHolder getCacheHolder();

    public static final class CacheHolder {
        private final Cache okHttpCache;

        private CacheHolder() {
            throw new UnsupportedOperationException();
        }

        private CacheHolder(Cache cache) {
            this.okHttpCache = cache;
        }

        public static CacheHolder create(File file, long l) {
            return new CacheHolder(new Cache(file, l));
        }

        public Cache getCache() {
            return this.okHttpCache;
        }

        public boolean isEquivalent(File file, long l) {
            boolean bl = this.okHttpCache.getDirectory().equals(file) && this.okHttpCache.getMaxSize() == l && !this.okHttpCache.isClosed();
            return bl;
        }
    }

}

