/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

public class IntPair {
    private IntPair() {
    }

    public static int first(long l) {
        return (int)(l >> 32);
    }

    public static long of(int n, int n2) {
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    public static int second(long l) {
        return (int)l;
    }
}

