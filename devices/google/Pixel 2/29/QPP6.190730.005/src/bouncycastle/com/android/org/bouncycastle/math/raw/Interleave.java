/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

public class Interleave {
    private static final long M32 = 0x55555555L;
    private static final long M64 = 0x5555555555555555L;
    private static final long M64R = -6148914691236517206L;

    public static int expand16to32(int n) {
        n &= 65535;
        n = (n << 8 | n) & 16711935;
        n = (n << 4 | n) & 252645135;
        n = (n << 2 | n) & 858993459;
        return (n << 1 | n) & 1431655765;
    }

    public static long expand32to64(int n) {
        int n2 = (n >>> 8 ^ n) & 65280;
        n2 = n ^ (n2 << 8 ^ n2);
        n = (n2 >>> 4 ^ n2) & 15728880;
        n2 ^= n << 4 ^ n;
        n = (n2 >>> 2 ^ n2) & 202116108;
        n = n2 ^ (n << 2 ^ n);
        n2 = (n >>> 1 ^ n) & 572662306;
        return ((long)((n ^= n2 << 1 ^ n2) >>> 1) & 0x55555555L) << 32 | 0x55555555L & (long)n;
    }

    public static void expand64To128(long l, long[] arrl, int n) {
        long l2 = (l >>> 16 ^ l) & 0xFFFF0000L;
        l ^= l2 << 16 ^ l2;
        l2 = (l >>> 8 ^ l) & 0xFF000000FF00L;
        l2 = l ^ (l2 << 8 ^ l2);
        l = (l2 >>> 4 ^ l2) & 0xF000F000F000F0L;
        l2 ^= l << 4 ^ l;
        l = (l2 >>> 2 ^ l2) & 0xC0C0C0C0C0C0C0CL;
        l = l2 ^ (l << 2 ^ l);
        l2 = (l >>> 1 ^ l) & 0x2222222222222222L;
        arrl[n] = (l ^= l2 << 1 ^ l2) & 0x5555555555555555L;
        arrl[n + 1] = 0x5555555555555555L & l >>> 1;
    }

    public static void expand64To128Rev(long l, long[] arrl, int n) {
        long l2 = (l >>> 16 ^ l) & 0xFFFF0000L;
        l ^= l2 << 16 ^ l2;
        l2 = (l >>> 8 ^ l) & 0xFF000000FF00L;
        l ^= l2 << 8 ^ l2;
        l2 = (l >>> 4 ^ l) & 0xF000F000F000F0L;
        l ^= l2 << 4 ^ l2;
        l2 = (l >>> 2 ^ l) & 0xC0C0C0C0C0C0C0CL;
        l2 = l ^ (l2 << 2 ^ l2);
        l = (l2 >>> 1 ^ l2) & 0x2222222222222222L;
        l = l2 ^ (l << 1 ^ l);
        arrl[n] = l & -6148914691236517206L;
        arrl[n + 1] = -6148914691236517206L & l << 1;
    }

    public static int expand8to16(int n) {
        n &= 255;
        n = (n << 4 | n) & 3855;
        n = (n << 2 | n) & 13107;
        return (n << 1 | n) & 21845;
    }

    public static int shuffle(int n) {
        int n2 = (n >>> 8 ^ n) & 65280;
        n ^= n2 << 8 ^ n2;
        n2 = (n >>> 4 ^ n) & 15728880;
        n ^= n2 << 4 ^ n2;
        n2 = (n >>> 2 ^ n) & 202116108;
        n ^= n2 << 2 ^ n2;
        n2 = (n >>> 1 ^ n) & 572662306;
        return n ^ (n2 << 1 ^ n2);
    }

    public static long shuffle(long l) {
        long l2 = (l >>> 16 ^ l) & 0xFFFF0000L;
        l ^= l2 << 16 ^ l2;
        l2 = (l >>> 8 ^ l) & 0xFF000000FF00L;
        l ^= l2 << 8 ^ l2;
        l2 = (l >>> 4 ^ l) & 0xF000F000F000F0L;
        l ^= l2 << 4 ^ l2;
        l2 = (l >>> 2 ^ l) & 0xC0C0C0C0C0C0C0CL;
        l ^= l2 << 2 ^ l2;
        l2 = (l >>> 1 ^ l) & 0x2222222222222222L;
        return l ^ (l2 << 1 ^ l2);
    }

    public static int shuffle2(int n) {
        int n2 = (n >>> 7 ^ n) & 11141290;
        n2 = n ^ (n2 << 7 ^ n2);
        n = (n2 >>> 14 ^ n2) & 52428;
        n2 ^= n << 14 ^ n;
        n = (n2 >>> 4 ^ n2) & 15728880;
        n2 ^= n << 4 ^ n;
        n = (n2 >>> 8 ^ n2) & 65280;
        return n2 ^ (n << 8 ^ n);
    }

    public static int unshuffle(int n) {
        int n2 = (n >>> 1 ^ n) & 572662306;
        n ^= n2 << 1 ^ n2;
        n2 = (n >>> 2 ^ n) & 202116108;
        n2 = n ^ (n2 << 2 ^ n2);
        n = (n2 >>> 4 ^ n2) & 15728880;
        n = n2 ^ (n << 4 ^ n);
        n2 = (n >>> 8 ^ n) & 65280;
        return n ^ (n2 << 8 ^ n2);
    }

    public static long unshuffle(long l) {
        long l2 = (l >>> 1 ^ l) & 0x2222222222222222L;
        l2 = l ^ (l2 << 1 ^ l2);
        l = (l2 >>> 2 ^ l2) & 0xC0C0C0C0C0C0C0CL;
        l = l2 ^ (l << 2 ^ l);
        l2 = (l >>> 4 ^ l) & 0xF000F000F000F0L;
        l ^= l2 << 4 ^ l2;
        l2 = (l >>> 8 ^ l) & 0xFF000000FF00L;
        l2 = l ^ (l2 << 8 ^ l2);
        l = (l2 >>> 16 ^ l2) & 0xFFFF0000L;
        return l2 ^ (l << 16 ^ l);
    }

    public static int unshuffle2(int n) {
        int n2 = (n >>> 8 ^ n) & 65280;
        n ^= n2 << 8 ^ n2;
        n2 = (n >>> 4 ^ n) & 15728880;
        n ^= n2 << 4 ^ n2;
        n2 = (n >>> 14 ^ n) & 52428;
        n2 = n ^ (n2 << 14 ^ n2);
        n = (n2 >>> 7 ^ n2) & 11141290;
        return n2 ^ (n << 7 ^ n);
    }
}

