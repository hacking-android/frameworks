/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public final class SplittableRandom {
    static final String BAD_BOUND = "bound must be positive";
    static final String BAD_RANGE = "bound must be greater than origin";
    static final String BAD_SIZE = "size must be non-negative";
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;
    private static final long GOLDEN_GAMMA = -7046029254386353131L;
    private static final AtomicLong defaultGen = new AtomicLong(SplittableRandom.mix64(System.currentTimeMillis()) ^ SplittableRandom.mix64(System.nanoTime()));
    private final long gamma;
    private long seed;

    static {
        if (AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                return Boolean.getBoolean("java.util.secureRandomSeed");
            }
        }).booleanValue()) {
            byte[] arrby = SecureRandom.getSeed(8);
            long l = (long)arrby[0] & 255L;
            for (int i = 1; i < 8; ++i) {
                l = l << 8 | (long)arrby[i] & 255L;
            }
            defaultGen.set(l);
        }
    }

    public SplittableRandom() {
        long l = defaultGen.getAndAdd(4354685564936845354L);
        this.seed = SplittableRandom.mix64(l);
        this.gamma = SplittableRandom.mixGamma(-7046029254386353131L + l);
    }

    public SplittableRandom(long l) {
        this(l, -7046029254386353131L);
    }

    private SplittableRandom(long l, long l2) {
        this.seed = l;
        this.gamma = l2;
    }

    private static int mix32(long l) {
        l = (l >>> 33 ^ l) * 7109453100751455733L;
        return (int)((l >>> 28 ^ l) * -3808689974395783757L >>> 32);
    }

    private static long mix64(long l) {
        l = (l >>> 30 ^ l) * -4658895280553007687L;
        l = (l >>> 27 ^ l) * -7723592293110705685L;
        return l >>> 31 ^ l;
    }

    private static long mixGamma(long l) {
        block0 : {
            l = (l >>> 33 ^ l) * -49064778989728563L;
            l = (l >>> 33 ^ l) * -4265267296055464877L;
            if (Long.bitCount((l = l >>> 33 ^ l | 1L) >>> 1 ^ l) >= 24) break block0;
            l = -6148914691236517206L ^ l;
        }
        return l;
    }

    private long nextSeed() {
        long l;
        this.seed = l = this.seed + this.gamma;
        return l;
    }

    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0), false);
    }

    public DoubleStream doubles(double d, double d2) {
        if (d < d2) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, d, d2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public DoubleStream doubles(long l) {
        if (l >= 0L) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, l, Double.MAX_VALUE, 0.0), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    public DoubleStream doubles(long l, double d, double d2) {
        if (l >= 0L) {
            if (d < d2) {
                return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, l, d, d2), false);
            }
            throw new IllegalArgumentException(BAD_RANGE);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    final double internalNextDouble(double d, double d2) {
        double d3;
        double d4 = d3 = (double)(this.nextLong() >>> 11) * 1.1102230246251565E-16;
        if (d < d2) {
            d4 = d = (d2 - d) * d3 + d;
            if (d >= d2) {
                d4 = Double.longBitsToDouble(Double.doubleToLongBits(d2) - 1L);
            }
        }
        return d4;
    }

    final int internalNextInt(int n, int n2) {
        int n3;
        int n4 = n3 = SplittableRandom.mix32(this.nextSeed());
        if (n < n2) {
            n4 = n2 - n;
            int n5 = n4 - 1;
            if ((n4 & n5) == 0) {
                n4 = (n3 & n5) + n;
            } else {
                int n6 = n3;
                if (n4 > 0) {
                    n2 = n3 >>> 1;
                    while (n2 + n5 - (n6 = n2 % n4) < 0) {
                        n2 = SplittableRandom.mix32(this.nextSeed()) >>> 1;
                    }
                    n4 = n6 + n;
                } else {
                    do {
                        if (n6 >= n) {
                            n4 = n6;
                            if (n6 < n2) break;
                        }
                        n6 = SplittableRandom.mix32(this.nextSeed());
                    } while (true);
                }
            }
        }
        return n4;
    }

    final long internalNextLong(long l, long l2) {
        long l3;
        long l4 = l3 = SplittableRandom.mix64(this.nextSeed());
        if (l < l2) {
            l4 = l2 - l;
            long l5 = l4 - 1L;
            if ((l4 & l5) == 0L) {
                l4 = (l3 & l5) + l;
            } else {
                long l6 = l3;
                if (l4 > 0L) {
                    l2 = l3 >>> 1;
                    while (l2 + l5 - (l6 = l2 % l4) < 0L) {
                        l2 = SplittableRandom.mix64(this.nextSeed()) >>> 1;
                    }
                    l4 = l6 + l;
                } else {
                    do {
                        if (l6 >= l) {
                            l4 = l6;
                            if (l6 < l2) break;
                        }
                        l6 = SplittableRandom.mix64(this.nextSeed());
                    } while (true);
                }
            }
        }
        return l4;
    }

    public IntStream ints() {
        return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
    }

    public IntStream ints(int n, int n2) {
        if (n < n2) {
            return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, Long.MAX_VALUE, n, n2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public IntStream ints(long l) {
        if (l >= 0L) {
            return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, l, Integer.MAX_VALUE, 0), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    public IntStream ints(long l, int n, int n2) {
        if (l >= 0L) {
            if (n < n2) {
                return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, l, n, n2), false);
            }
            throw new IllegalArgumentException(BAD_RANGE);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    public LongStream longs() {
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }

    public LongStream longs(long l) {
        if (l >= 0L) {
            return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, l, Long.MAX_VALUE, 0L), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    public LongStream longs(long l, long l2) {
        if (l < l2) {
            return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, l, l2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public LongStream longs(long l, long l2, long l3) {
        if (l >= 0L) {
            if (l2 < l3) {
                return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, l, l2, l3), false);
            }
            throw new IllegalArgumentException(BAD_RANGE);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    public boolean nextBoolean() {
        boolean bl = SplittableRandom.mix32(this.nextSeed()) < 0;
        return bl;
    }

    public double nextDouble() {
        return (double)(SplittableRandom.mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16;
    }

    public double nextDouble(double d) {
        if (d > 0.0) {
            double d2 = (double)(SplittableRandom.mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16 * d;
            d = d2 < d ? d2 : Double.longBitsToDouble(Double.doubleToLongBits(d) - 1L);
            return d;
        }
        throw new IllegalArgumentException(BAD_BOUND);
    }

    public double nextDouble(double d, double d2) {
        if (d < d2) {
            return this.internalNextDouble(d, d2);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public int nextInt() {
        return SplittableRandom.mix32(this.nextSeed());
    }

    public int nextInt(int n) {
        if (n > 0) {
            int n2;
            int n3 = SplittableRandom.mix32(this.nextSeed());
            int n4 = n - 1;
            if ((n & n4) == 0) {
                n2 = n3 & n4;
            } else {
                n3 >>>= 1;
                do {
                    int n5;
                    n2 = n5 = n3 % n;
                    if (n3 + n4 - n5 >= 0) break;
                    n3 = SplittableRandom.mix32(this.nextSeed()) >>> 1;
                } while (true);
            }
            return n2;
        }
        throw new IllegalArgumentException(BAD_BOUND);
    }

    public int nextInt(int n, int n2) {
        if (n < n2) {
            return this.internalNextInt(n, n2);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public long nextLong() {
        return SplittableRandom.mix64(this.nextSeed());
    }

    public long nextLong(long l) {
        if (l > 0L) {
            long l2 = SplittableRandom.mix64(this.nextSeed());
            long l3 = l - 1L;
            if ((l & l3) == 0L) {
                l = l2 & l3;
            } else {
                long l4;
                l2 >>>= 1;
                while (l2 + l3 - (l4 = l2 % l) < 0L) {
                    l2 = SplittableRandom.mix64(this.nextSeed()) >>> 1;
                }
                l = l4;
            }
            return l;
        }
        throw new IllegalArgumentException(BAD_BOUND);
    }

    public long nextLong(long l, long l2) {
        if (l < l2) {
            return this.internalNextLong(l, l2);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    public SplittableRandom split() {
        return new SplittableRandom(this.nextLong(), SplittableRandom.mixGamma(this.nextSeed()));
    }

    private static final class RandomDoublesSpliterator
    implements Spliterator.OfDouble {
        final double bound;
        final long fence;
        long index;
        final double origin;
        final SplittableRandom rng;

        RandomDoublesSpliterator(SplittableRandom splittableRandom, long l, long l2, double d, double d2) {
            this.rng = splittableRandom;
            this.index = l;
            this.fence = l2;
            this.origin = d;
            this.bound = d2;
        }

        @Override
        public int characteristics() {
            return 17728;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                long l = this.index;
                long l2 = this.fence;
                if (l < l2) {
                    long l3;
                    this.index = l2;
                    SplittableRandom splittableRandom = this.rng;
                    double d = this.origin;
                    double d2 = this.bound;
                    do {
                        doubleConsumer.accept(splittableRandom.internalNextDouble(d, d2));
                        l = l3 = 1L + l;
                    } while (l3 < l2);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                long l = this.index;
                if (l < this.fence) {
                    doubleConsumer.accept(this.rng.internalNextDouble(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomDoublesSpliterator trySplit() {
            Object object;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                object = null;
            } else {
                object = this.rng.split();
                this.index = l2;
                object = new RandomDoublesSpliterator((SplittableRandom)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

    private static final class RandomIntsSpliterator
    implements Spliterator.OfInt {
        final int bound;
        final long fence;
        long index;
        final int origin;
        final SplittableRandom rng;

        RandomIntsSpliterator(SplittableRandom splittableRandom, long l, long l2, int n, int n2) {
            this.rng = splittableRandom;
            this.index = l;
            this.fence = l2;
            this.origin = n;
            this.bound = n2;
        }

        @Override
        public int characteristics() {
            return 17728;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            if (intConsumer != null) {
                long l = this.index;
                long l2 = this.fence;
                if (l < l2) {
                    long l3;
                    this.index = l2;
                    SplittableRandom splittableRandom = this.rng;
                    int n = this.origin;
                    int n2 = this.bound;
                    do {
                        intConsumer.accept(splittableRandom.internalNextInt(n, n2));
                        l = l3 = 1L + l;
                    } while (l3 < l2);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (intConsumer != null) {
                long l = this.index;
                if (l < this.fence) {
                    intConsumer.accept(this.rng.internalNextInt(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomIntsSpliterator trySplit() {
            Object object;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                object = null;
            } else {
                object = this.rng.split();
                this.index = l2;
                object = new RandomIntsSpliterator((SplittableRandom)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

    private static final class RandomLongsSpliterator
    implements Spliterator.OfLong {
        final long bound;
        final long fence;
        long index;
        final long origin;
        final SplittableRandom rng;

        RandomLongsSpliterator(SplittableRandom splittableRandom, long l, long l2, long l3, long l4) {
            this.rng = splittableRandom;
            this.index = l;
            this.fence = l2;
            this.origin = l3;
            this.bound = l4;
        }

        @Override
        public int characteristics() {
            return 17728;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            if (longConsumer != null) {
                long l = this.index;
                long l2 = this.fence;
                if (l < l2) {
                    long l3;
                    this.index = l2;
                    SplittableRandom splittableRandom = this.rng;
                    long l4 = this.origin;
                    long l5 = this.bound;
                    do {
                        longConsumer.accept(splittableRandom.internalNextLong(l4, l5));
                        l = l3 = 1L + l;
                    } while (l3 < l2);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            if (longConsumer != null) {
                long l = this.index;
                if (l < this.fence) {
                    longConsumer.accept(this.rng.internalNextLong(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomLongsSpliterator trySplit() {
            Object object;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                object = null;
            } else {
                object = this.rng.split();
                this.index = l2;
                object = new RandomLongsSpliterator((SplittableRandom)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

}

