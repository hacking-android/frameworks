/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import sun.misc.Unsafe;

public class ThreadLocalRandom
extends Random {
    static final String BAD_BOUND = "bound must be positive";
    static final String BAD_RANGE = "bound must be greater than origin";
    static final String BAD_SIZE = "size must be non-negative";
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;
    private static final float FLOAT_UNIT = 5.9604645E-8f;
    private static final long GAMMA = -7046029254386353131L;
    private static final long PROBE;
    private static final int PROBE_INCREMENT = -1640531527;
    private static final long SECONDARY;
    private static final long SEED;
    private static final long SEEDER_INCREMENT = -4942790177534073029L;
    private static final Unsafe U;
    static final ThreadLocalRandom instance;
    private static final ThreadLocal<Double> nextLocalGaussian;
    private static final AtomicInteger probeGenerator;
    private static final AtomicLong seeder;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = -5851777807851030925L;
    boolean initialized = true;

    static {
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("rnd", Long.TYPE), new ObjectStreamField("initialized", Boolean.TYPE)};
        U = Unsafe.getUnsafe();
        try {
            SEED = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomSeed"));
            PROBE = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomSecondarySeed"));
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
        nextLocalGaussian = new ThreadLocal();
        probeGenerator = new AtomicInteger();
        instance = new ThreadLocalRandom();
        seeder = new AtomicLong(ThreadLocalRandom.mix64(System.currentTimeMillis()) ^ ThreadLocalRandom.mix64(System.nanoTime()));
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
            seeder.set(l);
        }
        return;
    }

    private ThreadLocalRandom() {
    }

    static final int advanceProbe(int n) {
        n ^= n << 13;
        n ^= n >>> 17;
        n ^= n << 5;
        U.putInt(Thread.currentThread(), PROBE, n);
        return n;
    }

    public static ThreadLocalRandom current() {
        if (U.getInt(Thread.currentThread(), PROBE) == 0) {
            ThreadLocalRandom.localInit();
        }
        return instance;
    }

    static final int getProbe() {
        return U.getInt(Thread.currentThread(), PROBE);
    }

    static final void localInit() {
        int n = probeGenerator.addAndGet(-1640531527);
        if (n == 0) {
            n = 1;
        }
        long l = ThreadLocalRandom.mix64(seeder.getAndAdd(-4942790177534073029L));
        Thread thread = Thread.currentThread();
        U.putLong(thread, SEED, l);
        U.putInt(thread, PROBE, n);
    }

    private static int mix32(long l) {
        l = (l >>> 33 ^ l) * -49064778989728563L;
        return (int)((l >>> 33 ^ l) * -4265267296055464877L >>> 32);
    }

    private static long mix64(long l) {
        l = (l >>> 33 ^ l) * -49064778989728563L;
        l = (l >>> 33 ^ l) * -4265267296055464877L;
        return l >>> 33 ^ l;
    }

    static final int nextSecondarySeed() {
        Thread thread = Thread.currentThread();
        int n = U.getInt(thread, SECONDARY);
        if (n != 0) {
            n = n << 13 ^ n;
            n ^= n >>> 17;
            n ^= n << 5;
        } else {
            n = ThreadLocalRandom.mix32(seeder.getAndAdd(-4942790177534073029L));
            if (n == 0) {
                n = 1;
            }
        }
        U.putInt(thread, SECONDARY, n);
        return n;
    }

    private Object readResolve() {
        return ThreadLocalRandom.current();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("rnd", U.getLong(Thread.currentThread(), SEED));
        putField.put("initialized", true);
        objectOutputStream.writeFields();
    }

    @Override
    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0), false);
    }

    @Override
    public DoubleStream doubles(double d, double d2) {
        if (d < d2) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, Long.MAX_VALUE, d, d2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    @Override
    public DoubleStream doubles(long l) {
        if (l >= 0L) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, l, Double.MAX_VALUE, 0.0), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    @Override
    public DoubleStream doubles(long l, double d, double d2) {
        if (l >= 0L) {
            if (d < d2) {
                return StreamSupport.doubleStream(new RandomDoublesSpliterator(0L, l, d, d2), false);
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
        int n4 = n3 = ThreadLocalRandom.mix32(this.nextSeed());
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
                        n2 = ThreadLocalRandom.mix32(this.nextSeed()) >>> 1;
                    }
                    n4 = n6 + n;
                } else {
                    do {
                        if (n6 >= n) {
                            n4 = n6;
                            if (n6 < n2) break;
                        }
                        n6 = ThreadLocalRandom.mix32(this.nextSeed());
                    } while (true);
                }
            }
        }
        return n4;
    }

    final long internalNextLong(long l, long l2) {
        long l3;
        long l4 = l3 = ThreadLocalRandom.mix64(this.nextSeed());
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
                        l2 = ThreadLocalRandom.mix64(this.nextSeed()) >>> 1;
                    }
                    l4 = l6 + l;
                } else {
                    do {
                        if (l6 >= l) {
                            l4 = l6;
                            if (l6 < l2) break;
                        }
                        l6 = ThreadLocalRandom.mix64(this.nextSeed());
                    } while (true);
                }
            }
        }
        return l4;
    }

    @Override
    public IntStream ints() {
        return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0), false);
    }

    @Override
    public IntStream ints(int n, int n2) {
        if (n < n2) {
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, Long.MAX_VALUE, n, n2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    @Override
    public IntStream ints(long l) {
        if (l >= 0L) {
            return StreamSupport.intStream(new RandomIntsSpliterator(0L, l, Integer.MAX_VALUE, 0), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    @Override
    public IntStream ints(long l, int n, int n2) {
        if (l >= 0L) {
            if (n < n2) {
                return StreamSupport.intStream(new RandomIntsSpliterator(0L, l, n, n2), false);
            }
            throw new IllegalArgumentException(BAD_RANGE);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    @Override
    public LongStream longs() {
        return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }

    @Override
    public LongStream longs(long l) {
        if (l >= 0L) {
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, l, Long.MAX_VALUE, 0L), false);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    @Override
    public LongStream longs(long l, long l2) {
        if (l < l2) {
            return StreamSupport.longStream(new RandomLongsSpliterator(0L, Long.MAX_VALUE, l, l2), false);
        }
        throw new IllegalArgumentException(BAD_RANGE);
    }

    @Override
    public LongStream longs(long l, long l2, long l3) {
        if (l >= 0L) {
            if (l2 < l3) {
                return StreamSupport.longStream(new RandomLongsSpliterator(0L, l, l2, l3), false);
            }
            throw new IllegalArgumentException(BAD_RANGE);
        }
        throw new IllegalArgumentException(BAD_SIZE);
    }

    @Override
    protected int next(int n) {
        return (int)(ThreadLocalRandom.mix64(this.nextSeed()) >>> 64 - n);
    }

    @Override
    public boolean nextBoolean() {
        boolean bl = ThreadLocalRandom.mix32(this.nextSeed()) < 0;
        return bl;
    }

    @Override
    public double nextDouble() {
        return (double)(ThreadLocalRandom.mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16;
    }

    public double nextDouble(double d) {
        if (d > 0.0) {
            double d2 = (double)(ThreadLocalRandom.mix64(this.nextSeed()) >>> 11) * 1.1102230246251565E-16 * d;
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

    @Override
    public float nextFloat() {
        return (float)(ThreadLocalRandom.mix32(this.nextSeed()) >>> 8) * 5.9604645E-8f;
    }

    @Override
    public double nextGaussian() {
        double d;
        double d2;
        double d3;
        Double d4 = nextLocalGaussian.get();
        if (d4 != null) {
            nextLocalGaussian.set(null);
            return d4;
        }
        while ((d3 = (d = this.nextDouble() * 2.0 - 1.0) * d + (d2 = this.nextDouble() * 2.0 - 1.0) * d2) >= 1.0 || d3 == 0.0) {
        }
        d3 = StrictMath.sqrt(StrictMath.log(d3) * -2.0 / d3);
        nextLocalGaussian.set(new Double(d2 * d3));
        return d * d3;
    }

    @Override
    public int nextInt() {
        return ThreadLocalRandom.mix32(this.nextSeed());
    }

    @Override
    public int nextInt(int n) {
        if (n > 0) {
            int n2;
            int n3 = ThreadLocalRandom.mix32(this.nextSeed());
            int n4 = n - 1;
            if ((n & n4) == 0) {
                n2 = n3 & n4;
            } else {
                n3 >>>= 1;
                do {
                    int n5;
                    n2 = n5 = n3 % n;
                    if (n3 + n4 - n5 >= 0) break;
                    n3 = ThreadLocalRandom.mix32(this.nextSeed()) >>> 1;
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

    @Override
    public long nextLong() {
        return ThreadLocalRandom.mix64(this.nextSeed());
    }

    public long nextLong(long l) {
        if (l > 0L) {
            long l2 = ThreadLocalRandom.mix64(this.nextSeed());
            long l3 = l - 1L;
            if ((l & l3) == 0L) {
                l = l2 & l3;
            } else {
                long l4;
                l2 >>>= 1;
                while (l2 + l3 - (l4 = l2 % l) < 0L) {
                    l2 = ThreadLocalRandom.mix64(this.nextSeed()) >>> 1;
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

    final long nextSeed() {
        Unsafe unsafe = U;
        Thread thread = Thread.currentThread();
        long l = SEED;
        long l2 = U.getLong(thread, l) - 7046029254386353131L;
        unsafe.putLong(thread, l, l2);
        return l2;
    }

    @Override
    public void setSeed(long l) {
        if (!this.initialized) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    private static final class RandomDoublesSpliterator
    implements Spliterator.OfDouble {
        final double bound;
        final long fence;
        long index;
        final double origin;

        RandomDoublesSpliterator(long l, long l2, double d, double d2) {
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
                    double d = this.origin;
                    double d2 = this.bound;
                    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                    do {
                        doubleConsumer.accept(threadLocalRandom.internalNextDouble(d, d2));
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
                    doubleConsumer.accept(ThreadLocalRandom.current().internalNextDouble(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomDoublesSpliterator trySplit() {
            RandomDoublesSpliterator randomDoublesSpliterator;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                randomDoublesSpliterator = null;
            } else {
                this.index = l2;
                randomDoublesSpliterator = new RandomDoublesSpliterator(l, l2, this.origin, this.bound);
            }
            return randomDoublesSpliterator;
        }
    }

    private static final class RandomIntsSpliterator
    implements Spliterator.OfInt {
        final int bound;
        final long fence;
        long index;
        final int origin;

        RandomIntsSpliterator(long l, long l2, int n, int n2) {
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
                    int n = this.origin;
                    int n2 = this.bound;
                    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                    do {
                        intConsumer.accept(threadLocalRandom.internalNextInt(n, n2));
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
                    intConsumer.accept(ThreadLocalRandom.current().internalNextInt(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomIntsSpliterator trySplit() {
            RandomIntsSpliterator randomIntsSpliterator;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                randomIntsSpliterator = null;
            } else {
                this.index = l2;
                randomIntsSpliterator = new RandomIntsSpliterator(l, l2, this.origin, this.bound);
            }
            return randomIntsSpliterator;
        }
    }

    private static final class RandomLongsSpliterator
    implements Spliterator.OfLong {
        final long bound;
        final long fence;
        long index;
        final long origin;

        RandomLongsSpliterator(long l, long l2, long l3, long l4) {
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
                    long l4 = this.origin;
                    long l5 = this.bound;
                    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                    do {
                        longConsumer.accept(threadLocalRandom.internalNextLong(l4, l5));
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
                    longConsumer.accept(ThreadLocalRandom.current().internalNextLong(this.origin, this.bound));
                    this.index = 1L + l;
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public RandomLongsSpliterator trySplit() {
            RandomLongsSpliterator randomLongsSpliterator;
            long l = this.index;
            long l2 = this.fence + l >>> 1;
            if (l2 <= l) {
                randomLongsSpliterator = null;
            } else {
                this.index = l2;
                randomLongsSpliterator = new RandomLongsSpliterator(l, l2, this.origin, this.bound);
            }
            return randomLongsSpliterator;
        }
    }

}

