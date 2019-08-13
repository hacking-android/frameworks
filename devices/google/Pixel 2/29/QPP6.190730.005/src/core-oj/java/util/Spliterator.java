/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Comparator;
import java.util.Objects;
import java.util.Tripwire;
import java.util._$$Lambda$2CyTD4Tuo1NS84gjxzFA3u1LWl0;
import java.util._$$Lambda$9llQTmDvC2fDr_Gds5d6BexJH00;
import java.util._$$Lambda$E08DiBhfezKzcLFK_72WvmuOUJs;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public interface Spliterator<T> {
    public static final int CONCURRENT = 4096;
    public static final int DISTINCT = 1;
    public static final int IMMUTABLE = 1024;
    public static final int NONNULL = 256;
    public static final int ORDERED = 16;
    public static final int SIZED = 64;
    public static final int SORTED = 4;
    public static final int SUBSIZED = 16384;

    public int characteristics();

    public long estimateSize();

    default public void forEachRemaining(Consumer<? super T> consumer) {
        while (this.tryAdvance(consumer)) {
        }
    }

    default public Comparator<? super T> getComparator() {
        throw new IllegalStateException();
    }

    default public long getExactSizeIfKnown() {
        long l = (this.characteristics() & 64) == 0 ? -1L : this.estimateSize();
        return l;
    }

    default public boolean hasCharacteristics(int n) {
        boolean bl = (this.characteristics() & n) == n;
        return bl;
    }

    public boolean tryAdvance(Consumer<? super T> var1);

    public Spliterator<T> trySplit();

    public static interface OfDouble
    extends OfPrimitive<Double, DoubleConsumer, OfDouble> {
        @Override
        default public void forEachRemaining(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                this.forEachRemaining((DoubleConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$2CyTD4Tuo1NS84gjxzFA3u1LWl0(consumer));
            }
        }

        @Override
        default public void forEachRemaining(DoubleConsumer doubleConsumer) {
            while (this.tryAdvance(doubleConsumer)) {
            }
        }

        @Override
        default public boolean tryAdvance(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                return this.tryAdvance((DoubleConsumer)((Object)consumer));
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
            }
            Objects.requireNonNull(consumer);
            return this.tryAdvance(new _$$Lambda$2CyTD4Tuo1NS84gjxzFA3u1LWl0(consumer));
        }

        @Override
        public boolean tryAdvance(DoubleConsumer var1);

        @Override
        public OfDouble trySplit();
    }

    public static interface OfInt
    extends OfPrimitive<Integer, IntConsumer, OfInt> {
        @Override
        default public void forEachRemaining(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                this.forEachRemaining((IntConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$E08DiBhfezKzcLFK_72WvmuOUJs(consumer));
            }
        }

        @Override
        default public void forEachRemaining(IntConsumer intConsumer) {
            while (this.tryAdvance(intConsumer)) {
            }
        }

        @Override
        default public boolean tryAdvance(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                return this.tryAdvance((IntConsumer)((Object)consumer));
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
            }
            Objects.requireNonNull(consumer);
            return this.tryAdvance(new _$$Lambda$E08DiBhfezKzcLFK_72WvmuOUJs(consumer));
        }

        @Override
        public boolean tryAdvance(IntConsumer var1);

        @Override
        public OfInt trySplit();
    }

    public static interface OfLong
    extends OfPrimitive<Long, LongConsumer, OfLong> {
        @Override
        default public void forEachRemaining(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                this.forEachRemaining((LongConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$9llQTmDvC2fDr_Gds5d6BexJH00(consumer));
            }
        }

        @Override
        default public void forEachRemaining(LongConsumer longConsumer) {
            while (this.tryAdvance(longConsumer)) {
            }
        }

        @Override
        default public boolean tryAdvance(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                return this.tryAdvance((LongConsumer)((Object)consumer));
            }
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
            }
            Objects.requireNonNull(consumer);
            return this.tryAdvance(new _$$Lambda$9llQTmDvC2fDr_Gds5d6BexJH00(consumer));
        }

        @Override
        public boolean tryAdvance(LongConsumer var1);

        @Override
        public OfLong trySplit();
    }

    public static interface OfPrimitive<T, T_CONS, T_SPLITR extends OfPrimitive<T, T_CONS, T_SPLITR>>
    extends Spliterator<T> {
        default public void forEachRemaining(T_CONS T_CONS) {
            while (this.tryAdvance(T_CONS)) {
            }
        }

        public boolean tryAdvance(T_CONS var1);

        public T_SPLITR trySplit();
    }

}

