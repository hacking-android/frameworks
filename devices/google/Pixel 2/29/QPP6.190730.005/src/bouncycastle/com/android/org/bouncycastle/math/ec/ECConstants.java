/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import java.math.BigInteger;

public interface ECConstants {
    public static final BigInteger EIGHT;
    public static final BigInteger FOUR;
    public static final BigInteger ONE;
    public static final BigInteger THREE;
    public static final BigInteger TWO;
    public static final BigInteger ZERO;

    static {
        ZERO = BigInteger.valueOf(0L);
        ONE = BigInteger.valueOf(1L);
        TWO = BigInteger.valueOf(2L);
        THREE = BigInteger.valueOf(3L);
        FOUR = BigInteger.valueOf(4L);
        EIGHT = BigInteger.valueOf(8L);
    }
}

