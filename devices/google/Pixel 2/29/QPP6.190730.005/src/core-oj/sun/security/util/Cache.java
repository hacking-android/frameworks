/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.util.Arrays;
import java.util.Map;
import sun.security.util.MemoryCache;
import sun.security.util.NullCache;

public abstract class Cache<K, V> {
    protected Cache() {
    }

    public static <K, V> Cache<K, V> newHardMemoryCache(int n) {
        return new MemoryCache(false, n);
    }

    public static <K, V> Cache<K, V> newHardMemoryCache(int n, int n2) {
        return new MemoryCache(false, n, n2);
    }

    public static <K, V> Cache<K, V> newNullCache() {
        return NullCache.INSTANCE;
    }

    public static <K, V> Cache<K, V> newSoftMemoryCache(int n) {
        return new MemoryCache(true, n);
    }

    public static <K, V> Cache<K, V> newSoftMemoryCache(int n, int n2) {
        return new MemoryCache(true, n, n2);
    }

    public abstract void accept(CacheVisitor<K, V> var1);

    public abstract void clear();

    public abstract V get(Object var1);

    public abstract void put(K var1, V var2);

    public abstract void remove(Object var1);

    public abstract void setCapacity(int var1);

    public abstract void setTimeout(int var1);

    public abstract int size();

    public static interface CacheVisitor<K, V> {
        public void visit(Map<K, V> var1);
    }

    public static class EqualByteArray {
        private final byte[] b;
        private volatile int hash;

        public EqualByteArray(byte[] arrby) {
            this.b = arrby;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof EqualByteArray)) {
                return false;
            }
            object = (EqualByteArray)object;
            return Arrays.equals(this.b, ((EqualByteArray)object).b);
        }

        public int hashCode() {
            int n;
            int n2 = n = this.hash;
            if (n == 0) {
                byte[] arrby;
                n2 = this.b.length + 1;
                for (n = 0; n < (arrby = this.b).length; ++n) {
                    n2 += (arrby[n] & 255) * 37;
                }
                this.hash = n2;
            }
            return n2;
        }
    }

}

