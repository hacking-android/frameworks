/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.Tripwire;

public interface Sink<T>
extends Consumer<T> {
    @Override
    default public void accept(double d) {
        throw new IllegalStateException("called wrong accept method");
    }

    @Override
    default public void accept(int n) {
        throw new IllegalStateException("called wrong accept method");
    }

    @Override
    default public void accept(long l) {
        throw new IllegalStateException("called wrong accept method");
    }

    default public void begin(long l) {
    }

    default public boolean cancellationRequested() {
        return false;
    }

    default public void end() {
    }

    public static abstract class ChainedDouble<E_OUT>
    implements OfDouble {
        protected final Sink<? super E_OUT> downstream;

        public ChainedDouble(Sink<? super E_OUT> sink) {
            this.downstream = Objects.requireNonNull(sink);
        }

        @Override
        public void begin(long l) {
            this.downstream.begin(l);
        }

        @Override
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override
        public void end() {
            this.downstream.end();
        }
    }

    public static abstract class ChainedInt<E_OUT>
    implements OfInt {
        protected final Sink<? super E_OUT> downstream;

        public ChainedInt(Sink<? super E_OUT> sink) {
            this.downstream = Objects.requireNonNull(sink);
        }

        @Override
        public void begin(long l) {
            this.downstream.begin(l);
        }

        @Override
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override
        public void end() {
            this.downstream.end();
        }
    }

    public static abstract class ChainedLong<E_OUT>
    implements OfLong {
        protected final Sink<? super E_OUT> downstream;

        public ChainedLong(Sink<? super E_OUT> sink) {
            this.downstream = Objects.requireNonNull(sink);
        }

        @Override
        public void begin(long l) {
            this.downstream.begin(l);
        }

        @Override
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override
        public void end() {
            this.downstream.end();
        }
    }

    public static abstract class ChainedReference<T, E_OUT>
    implements Sink<T> {
        protected final Sink<? super E_OUT> downstream;

        public ChainedReference(Sink<? super E_OUT> sink) {
            this.downstream = Objects.requireNonNull(sink);
        }

        @Override
        public void begin(long l) {
            this.downstream.begin(l);
        }

        @Override
        public boolean cancellationRequested() {
            return this.downstream.cancellationRequested();
        }

        @Override
        public void end() {
            this.downstream.end();
        }
    }

    public static interface OfDouble
    extends Sink<Double>,
    DoubleConsumer {
        @Override
        public void accept(double var1);

        @Override
        default public void accept(Double d) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            }
            this.accept((double)d);
        }
    }

    public static interface OfInt
    extends Sink<Integer>,
    IntConsumer {
        @Override
        public void accept(int var1);

        @Override
        default public void accept(Integer n) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            }
            this.accept((int)n);
        }
    }

    public static interface OfLong
    extends Sink<Long>,
    LongConsumer {
        @Override
        public void accept(long var1);

        @Override
        default public void accept(Long l) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Sink.OfLong.accept(Long)");
            }
            this.accept((long)l);
        }
    }

}

