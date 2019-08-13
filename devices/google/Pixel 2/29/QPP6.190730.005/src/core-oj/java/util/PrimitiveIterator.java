/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Tripwire;
import java.util._$$Lambda$2CyTD4Tuo1NS84gjxzFA3u1LWl0;
import java.util._$$Lambda$9llQTmDvC2fDr_Gds5d6BexJH00;
import java.util._$$Lambda$E08DiBhfezKzcLFK_72WvmuOUJs;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public interface PrimitiveIterator<T, T_CONS>
extends Iterator<T> {
    public void forEachRemaining(T_CONS var1);

    public static interface OfDouble
    extends PrimitiveIterator<Double, DoubleConsumer> {
        @Override
        default public void forEachRemaining(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                this.forEachRemaining((DoubleConsumer)((Object)consumer));
            } else {
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$2CyTD4Tuo1NS84gjxzFA3u1LWl0(consumer));
            }
        }

        @Override
        default public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            while (this.hasNext()) {
                doubleConsumer.accept(this.nextDouble());
            }
        }

        @Override
        default public Double next() {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()");
            }
            return this.nextDouble();
        }

        public double nextDouble();
    }

    public static interface OfInt
    extends PrimitiveIterator<Integer, IntConsumer> {
        @Override
        default public void forEachRemaining(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                this.forEachRemaining((IntConsumer)((Object)consumer));
            } else {
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$E08DiBhfezKzcLFK_72WvmuOUJs(consumer));
            }
        }

        @Override
        default public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (this.hasNext()) {
                intConsumer.accept(this.nextInt());
            }
        }

        @Override
        default public Integer next() {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()");
            }
            return this.nextInt();
        }

        public int nextInt();
    }

    public static interface OfLong
    extends PrimitiveIterator<Long, LongConsumer> {
        @Override
        default public void forEachRemaining(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                this.forEachRemaining((LongConsumer)((Object)consumer));
            } else {
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
                }
                Objects.requireNonNull(consumer);
                this.forEachRemaining(new _$$Lambda$9llQTmDvC2fDr_Gds5d6BexJH00(consumer));
            }
        }

        @Override
        default public void forEachRemaining(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            while (this.hasNext()) {
                longConsumer.accept(this.nextLong());
            }
        }

        @Override
        default public Long next() {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()");
            }
            return this.nextLong();
        }

        public long nextLong();
    }

}

