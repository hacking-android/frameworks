/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public class CalendarCache {
    public static long EMPTY;
    private static final int[] primes;
    private int arraySize;
    private long[] keys;
    private int pIndex = 0;
    private int size = 0;
    private int threshold;
    private long[] values;

    static {
        primes = new int[]{61, 127, 509, 1021, 2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139};
        EMPTY = Long.MIN_VALUE;
    }

    public CalendarCache() {
        int n = this.arraySize = primes[this.pIndex];
        this.threshold = n * 3 / 4;
        this.keys = new long[n];
        this.values = new long[n];
        this.makeArrays(n);
    }

    private final int findIndex(long l) {
        int n = this.hash(l);
        int n2 = 0;
        while (this.values[n] != EMPTY && this.keys[n] != l) {
            int n3 = n2;
            if (n2 == 0) {
                n3 = this.hash2(l);
            }
            n = (n + n3) % this.arraySize;
            n2 = n3;
        }
        return n;
    }

    private final int hash(long l) {
        int n;
        int n2 = this.arraySize;
        int n3 = n = (int)((15821L * l + 1L) % (long)n2);
        if (n < 0) {
            n3 = n + n2;
        }
        return n3;
    }

    private final int hash2(long l) {
        int n = this.arraySize;
        return n - 2 - (int)(l % (long)(n - 2));
    }

    private void makeArrays(int n) {
        this.keys = new long[n];
        this.values = new long[n];
        for (int i = 0; i < n; ++i) {
            this.values[i] = EMPTY;
        }
        this.arraySize = n;
        this.threshold = (int)((double)this.arraySize * 0.75);
        this.size = 0;
    }

    private void rehash() {
        int n = this.arraySize;
        long[] arrl = this.keys;
        long[] arrl2 = this.values;
        int n2 = this.pIndex;
        int[] arrn = primes;
        if (n2 < arrn.length - 1) {
            this.pIndex = ++n2;
            this.arraySize = arrn[n2];
        } else {
            this.arraySize = this.arraySize * 2 + 1;
        }
        this.size = 0;
        this.makeArrays(this.arraySize);
        for (n2 = 0; n2 < n; ++n2) {
            if (arrl2[n2] == EMPTY) continue;
            this.put(arrl[n2], arrl2[n2]);
        }
    }

    public long get(long l) {
        synchronized (this) {
            l = this.values[this.findIndex(l)];
            return l;
        }
    }

    public void put(long l, long l2) {
        synchronized (this) {
            if (this.size >= this.threshold) {
                this.rehash();
            }
            int n = this.findIndex(l);
            this.keys[n] = l;
            this.values[n] = l2;
            ++this.size;
            return;
        }
    }
}

