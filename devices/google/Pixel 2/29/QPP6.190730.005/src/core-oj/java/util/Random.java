/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import sun.misc.Unsafe;

public class Random
implements Serializable {
    static final String BadBound = "bound must be positive";
    static final String BadRange = "bound must be greater than origin";
    static final String BadSize = "size must be non-negative";
    private static final double DOUBLE_UNIT = 1.1102230246251565E-16;
    private static final long addend = 11L;
    private static final long mask = 0xFFFFFFFFFFFFL;
    private static final long multiplier = 25214903917L;
    private static final long seedOffset;
    private static final AtomicLong seedUniquifier;
    private static final ObjectStreamField[] serialPersistentFields;
    static final long serialVersionUID = 3905348978240129619L;
    private static final Unsafe unsafe;
    private boolean haveNextNextGaussian = false;
    private double nextNextGaussian;
    private final AtomicLong seed;

    static {
        seedUniquifier = new AtomicLong(8682522807148012L);
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("seed", Long.TYPE), new ObjectStreamField("nextNextGaussian", Double.TYPE), new ObjectStreamField("haveNextNextGaussian", Boolean.TYPE)};
        unsafe = Unsafe.getUnsafe();
        try {
            seedOffset = unsafe.objectFieldOffset(Random.class.getDeclaredField("seed"));
            return;
        }
        catch (Exception exception) {
            throw new Error(exception);
        }
    }

    public Random() {
        this(Random.seedUniquifier() ^ System.nanoTime());
    }

    public Random(long l) {
        if (this.getClass() == Random.class) {
            this.seed = new AtomicLong(Random.initialScramble(l));
        } else {
            this.seed = new AtomicLong();
            this.setSeed(l);
        }
    }

    private static long initialScramble(long l) {
        return (25214903917L ^ l) & 0xFFFFFFFFFFFFL;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        long l = ((ObjectInputStream.GetField)(object = ((ObjectInputStream)object).readFields())).get("seed", -1L);
        if (l >= 0L) {
            this.resetSeed(l);
            this.nextNextGaussian = ((ObjectInputStream.GetField)object).get("nextNextGaussian", 0.0);
            this.haveNextNextGaussian = ((ObjectInputStream.GetField)object).get("haveNextNextGaussian", false);
            return;
        }
        throw new StreamCorruptedException("Random: invalid seed");
    }

    private void resetSeed(long l) {
        unsafe.putObjectVolatile(this, seedOffset, new AtomicLong(l));
    }

    private static long seedUniquifier() {
        long l;
        long l2;
        while (!seedUniquifier.compareAndSet(l2 = seedUniquifier.get(), l = 181783497276652981L * l2)) {
        }
        return l;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            ObjectOutputStream.PutField putField = objectOutputStream.putFields();
            putField.put("seed", this.seed.get());
            putField.put("nextNextGaussian", this.nextNextGaussian);
            putField.put("haveNextNextGaussian", this.haveNextNextGaussian);
            objectOutputStream.writeFields();
            return;
        }
    }

    public DoubleStream doubles() {
        return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0), false);
    }

    public DoubleStream doubles(double d, double d2) {
        if (d < d2) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, Long.MAX_VALUE, d, d2), false);
        }
        throw new IllegalArgumentException(BadRange);
    }

    public DoubleStream doubles(long l) {
        if (l >= 0L) {
            return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, l, Double.MAX_VALUE, 0.0), false);
        }
        throw new IllegalArgumentException(BadSize);
    }

    public DoubleStream doubles(long l, double d, double d2) {
        if (l >= 0L) {
            if (d < d2) {
                return StreamSupport.doubleStream(new RandomDoublesSpliterator(this, 0L, l, d, d2), false);
            }
            throw new IllegalArgumentException(BadRange);
        }
        throw new IllegalArgumentException(BadSize);
    }

    final double internalNextDouble(double d, double d2) {
        double d3;
        double d4 = d3 = this.nextDouble();
        if (d < d2) {
            d4 = d = (d2 - d) * d3 + d;
            if (d >= d2) {
                d4 = Double.longBitsToDouble(Double.doubleToLongBits(d2) - 1L);
            }
        }
        return d4;
    }

    final int internalNextInt(int n, int n2) {
        if (n < n2) {
            int n3 = n2 - n;
            if (n3 > 0) {
                return this.nextInt(n3) + n;
            }
            while ((n3 = this.nextInt()) < n || n3 >= n2) {
            }
            return n3;
        }
        return this.nextInt();
    }

    final long internalNextLong(long l, long l2) {
        long l3;
        long l4 = l3 = this.nextLong();
        if (l < l2) {
            long l5 = l2 - l;
            l4 = l5 - 1L;
            if ((l5 & l4) == 0L) {
                l4 = (l3 & l4) + l;
            } else {
                long l6 = l3;
                if (l5 > 0L) {
                    l2 = l3 >>> 1;
                    while (l2 + l4 - (l6 = l2 % l5) < 0L) {
                        l2 = this.nextLong() >>> 1;
                    }
                    l4 = l6 + l;
                } else {
                    do {
                        if (l6 >= l) {
                            l4 = l6;
                            if (l6 < l2) break;
                        }
                        l6 = this.nextLong();
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
        throw new IllegalArgumentException(BadRange);
    }

    public IntStream ints(long l) {
        if (l >= 0L) {
            return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, l, Integer.MAX_VALUE, 0), false);
        }
        throw new IllegalArgumentException(BadSize);
    }

    public IntStream ints(long l, int n, int n2) {
        if (l >= 0L) {
            if (n < n2) {
                return StreamSupport.intStream(new RandomIntsSpliterator(this, 0L, l, n, n2), false);
            }
            throw new IllegalArgumentException(BadRange);
        }
        throw new IllegalArgumentException(BadSize);
    }

    public LongStream longs() {
        return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L), false);
    }

    public LongStream longs(long l) {
        if (l >= 0L) {
            return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, l, Long.MAX_VALUE, 0L), false);
        }
        throw new IllegalArgumentException(BadSize);
    }

    public LongStream longs(long l, long l2) {
        if (l < l2) {
            return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, Long.MAX_VALUE, l, l2), false);
        }
        throw new IllegalArgumentException(BadRange);
    }

    public LongStream longs(long l, long l2, long l3) {
        if (l >= 0L) {
            if (l2 < l3) {
                return StreamSupport.longStream(new RandomLongsSpliterator(this, 0L, l, l2, l3), false);
            }
            throw new IllegalArgumentException(BadRange);
        }
        throw new IllegalArgumentException(BadSize);
    }

    protected int next(int n) {
        long l;
        long l2;
        AtomicLong atomicLong = this.seed;
        while (!atomicLong.compareAndSet(l = atomicLong.get(), l2 = 25214903917L * l + 11L & 0xFFFFFFFFFFFFL)) {
        }
        return (int)(l2 >>> 48 - n);
    }

    public boolean nextBoolean() {
        boolean bl = true;
        if (this.next(1) == 0) {
            bl = false;
        }
        return bl;
    }

    public void nextBytes(byte[] arrby) {
        int n = 0;
        int n2 = arrby.length;
        while (n < n2) {
            int n3 = this.nextInt();
            for (int i = java.lang.Math.min((int)(n2 - n), (int)4); i > 0; --i) {
                arrby[n] = (byte)n3;
                n3 >>= 8;
                ++n;
            }
        }
    }

    public double nextDouble() {
        return (double)(((long)this.next(26) << 27) + (long)this.next(27)) * 1.1102230246251565E-16;
    }

    public float nextFloat() {
        return (float)this.next(24) / 1.6777216E7f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public double nextGaussian() {
        synchronized (this) {
            double d;
            double d2;
            double d3;
            if (this.haveNextNextGaussian) {
                this.haveNextNextGaussian = false;
                return this.nextNextGaussian;
            }
            while ((d = (d2 = this.nextDouble() * 2.0 - 1.0) * d2 + (d3 = this.nextDouble() * 2.0 - 1.0) * d3) >= 1.0 || d == 0.0) {
            }
            d = StrictMath.sqrt(StrictMath.log(d) * -2.0 / d);
            this.nextNextGaussian = d3 * d;
            this.haveNextNextGaussian = true;
            return d2 * d;
        }
    }

    public int nextInt() {
        return this.next(32);
    }

    public int nextInt(int n) {
        if (n > 0) {
            int n2 = this.next(31);
            int n3 = n - 1;
            if ((n & n3) == 0) {
                n = (int)((long)n * (long)n2 >> 31);
            } else {
                int n4;
                while (n2 - (n4 = n2 % n) + n3 < 0) {
                    n2 = this.next(31);
                }
                n = n4;
            }
            return n;
        }
        throw new IllegalArgumentException(BadBound);
    }

    public long nextLong() {
        return ((long)this.next(32) << 32) + (long)this.next(32);
    }

    public void setSeed(long l) {
        synchronized (this) {
            this.seed.set(Random.initialScramble(l));
            this.haveNextNextGaussian = false;
            return;
        }
    }

    static final class RandomDoublesSpliterator
    implements Spliterator.OfDouble {
        final double bound;
        final long fence;
        long index;
        final double origin;
        final Random rng;

        RandomDoublesSpliterator(Random random, long l, long l2, double d, double d2) {
            this.rng = random;
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
                    Random random = this.rng;
                    double d = this.origin;
                    double d2 = this.bound;
                    do {
                        doubleConsumer.accept(random.internalNextDouble(d, d2));
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
                object = this.rng;
                this.index = l2;
                object = new RandomDoublesSpliterator((Random)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

    static final class RandomIntsSpliterator
    implements Spliterator.OfInt {
        final int bound;
        final long fence;
        long index;
        final int origin;
        final Random rng;

        RandomIntsSpliterator(Random random, long l, long l2, int n, int n2) {
            this.rng = random;
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
                    Random random = this.rng;
                    int n = this.origin;
                    int n2 = this.bound;
                    do {
                        intConsumer.accept(random.internalNextInt(n, n2));
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
                object = this.rng;
                this.index = l2;
                object = new RandomIntsSpliterator((Random)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

    static final class RandomLongsSpliterator
    implements Spliterator.OfLong {
        final long bound;
        final long fence;
        long index;
        final long origin;
        final Random rng;

        RandomLongsSpliterator(Random random, long l, long l2, long l3, long l4) {
            this.rng = random;
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
                    Random random = this.rng;
                    long l4 = this.origin;
                    long l5 = this.bound;
                    do {
                        longConsumer.accept(random.internalNextLong(l4, l5));
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
                object = this.rng;
                this.index = l2;
                object = new RandomLongsSpliterator((Random)object, l, l2, this.origin, this.bound);
            }
            return object;
        }
    }

}

