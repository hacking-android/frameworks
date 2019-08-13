/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.InetAddress;
import libcore.util.BasicLruCache;

class AddressCache {
    private static final int MAX_ENTRIES = 16;
    private static final long TTL_NANOS = 2000000000L;
    @UnsupportedAppUsage
    private final BasicLruCache<AddressCacheKey, AddressCacheEntry> cache = new BasicLruCache(16);

    AddressCache() {
    }

    public void clear() {
        this.cache.evictAll();
    }

    public Object get(String object, int n) {
        if ((object = this.cache.get(new AddressCacheKey((String)object, n))) != null && ((AddressCacheEntry)object).expiryNanos >= System.nanoTime()) {
            return ((AddressCacheEntry)object).value;
        }
        return null;
    }

    public void put(String string, int n, InetAddress[] arrinetAddress) {
        this.cache.put(new AddressCacheKey(string, n), new AddressCacheEntry(arrinetAddress));
    }

    public void putUnknownHost(String string, int n, String string2) {
        this.cache.put(new AddressCacheKey(string, n), new AddressCacheEntry(string2));
    }

    static class AddressCacheEntry {
        @UnsupportedAppUsage
        final long expiryNanos;
        @UnsupportedAppUsage
        final Object value;

        @UnsupportedAppUsage
        AddressCacheEntry(Object object) {
            this.value = object;
            this.expiryNanos = System.nanoTime() + 2000000000L;
        }
    }

    static class AddressCacheKey {
        @UnsupportedAppUsage
        private final String mHostname;
        private final int mNetId;

        AddressCacheKey(String string, int n) {
            this.mHostname = string;
            this.mNetId = n;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof AddressCacheKey)) {
                return false;
            }
            object = (AddressCacheKey)object;
            if (!this.mHostname.equals(((AddressCacheKey)object).mHostname) || this.mNetId != ((AddressCacheKey)object).mNetId) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return (17 * 31 + this.mNetId) * 31 + this.mHostname.hashCode();
        }
    }

}

