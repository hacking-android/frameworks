/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

public abstract class LRUCache<N, V> {
    private V[] oa = null;
    private final int size;

    public LRUCache(int n) {
        this.size = n;
    }

    public static void moveToFront(Object[] arrobject, int n) {
        Object object = arrobject[n];
        while (n > 0) {
            arrobject[n] = arrobject[n - 1];
            --n;
        }
        arrobject[0] = object;
    }

    protected abstract V create(N var1);

    public V forName(N object) {
        Object object2;
        if (this.oa == null) {
            this.oa = new Object[this.size];
        } else {
            for (int i = 0; i < ((V[])(object2 = this.oa)).length; ++i) {
                if ((object2 = object2[i]) == null || !this.hasName(object2, object)) continue;
                if (i > 0) {
                    LRUCache.moveToFront(this.oa, i);
                }
                return (V)object2;
            }
        }
        object2 = this.create(object);
        object = this.oa;
        object[((N)object).length - 1] = object2;
        LRUCache.moveToFront(object, ((N)object).length - 1);
        return (V)object2;
    }

    protected abstract boolean hasName(V var1, N var2);
}

