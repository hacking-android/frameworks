/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Node
 *  java.util.stream.-$$Lambda$Node$OfDouble
 *  java.util.stream.-$$Lambda$Node$OfDouble$5XMtiGLC0wkQzF2OIEVEnEBoYWM
 *  java.util.stream.-$$Lambda$Node$OfInt
 *  java.util.stream.-$$Lambda$Node$OfInt$SR5qcq7S0oCtehCDXAgbRdnvBbw
 *  java.util.stream.-$$Lambda$Node$OfLong
 *  java.util.stream.-$$Lambda$Node$OfLong$bPdsg_NFhPin-ja_QQPm0P0wq9s
 *  java.util.stream.-$$Lambda$Node$fa69PlTVbbnR3yr46T9Wo0_bIhg
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.stream.-$;
import java.util.stream.Nodes;
import java.util.stream.Sink;
import java.util.stream.StreamShape;
import java.util.stream.Tripwire;
import java.util.stream._$$Lambda$Node$OfDouble$5XMtiGLC0wkQzF2OIEVEnEBoYWM;
import java.util.stream._$$Lambda$Node$OfInt$SR5qcq7S0oCtehCDXAgbRdnvBbw;
import java.util.stream._$$Lambda$Node$OfLong$bPdsg_NFhPin_ja_QQPm0P0wq9s;
import java.util.stream._$$Lambda$Node$fa69PlTVbbnR3yr46T9Wo0_bIhg;

public interface Node<T> {
    public static /* synthetic */ void lambda$truncate$0(Object object) {
    }

    public T[] asArray(IntFunction<T[]> var1);

    public void copyInto(T[] var1, int var2);

    public long count();

    public void forEach(Consumer<? super T> var1);

    default public Node<T> getChild(int n) {
        throw new IndexOutOfBoundsException();
    }

    default public int getChildCount() {
        return 0;
    }

    default public StreamShape getShape() {
        return StreamShape.REFERENCE;
    }

    public Spliterator<T> spliterator();

    default public Node<T> truncate(long l, long l2, IntFunction<T[]> object) {
        if (l == 0L && l2 == this.count()) {
            return this;
        }
        Spliterator<T> spliterator = this.spliterator();
        object = Nodes.builder(l2 -= l, object);
        object.begin(l2);
        int n = 0;
        while ((long)n < l && spliterator.tryAdvance((Consumer<T>)_$$Lambda$Node$fa69PlTVbbnR3yr46T9Wo0_bIhg.INSTANCE)) {
            ++n;
        }
        n = 0;
        while ((long)n < l2 && spliterator.tryAdvance((Consumer<T>)object)) {
            ++n;
        }
        object.end();
        return object.build();
    }

    public static interface Builder<T>
    extends Sink<T> {
        public Node<T> build();

        public static interface OfDouble
        extends Builder<Double>,
        Sink.OfDouble {
            public java.util.stream.Node$OfDouble build();
        }

        public static interface OfInt
        extends Builder<Integer>,
        Sink.OfInt {
            public java.util.stream.Node$OfInt build();
        }

        public static interface OfLong
        extends Builder<Long>,
        Sink.OfLong {
            public java.util.stream.Node$OfLong build();
        }

    }

    public static interface OfDouble
    extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, OfDouble> {
        public static /* synthetic */ void lambda$truncate$0(double d) {
        }

        @Override
        default public void copyInto(Double[] arrdouble, int n) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
            }
            double[] arrd = (double[])this.asPrimitiveArray();
            for (int i = 0; i < arrd.length; ++i) {
                arrdouble[n + i] = arrd[i];
            }
        }

        @Override
        default public void forEach(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                this.forEach((DoubleConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfDouble)this.spliterator()).forEachRemaining(consumer);
            }
        }

        @Override
        default public StreamShape getShape() {
            return StreamShape.DOUBLE_VALUE;
        }

        @Override
        default public double[] newArray(int n) {
            return new double[n];
        }

        @Override
        default public OfDouble truncate(long l, long l2, IntFunction<Double[]> object) {
            if (l == 0L && l2 == this.count()) {
                return this;
            }
            object = (Spliterator.OfDouble)this.spliterator();
            Builder.OfDouble ofDouble = Nodes.doubleBuilder(l2 -= l);
            ofDouble.begin(l2);
            int n = 0;
            while ((long)n < l && object.tryAdvance((DoubleConsumer)_$$Lambda$Node$OfDouble$5XMtiGLC0wkQzF2OIEVEnEBoYWM.INSTANCE)) {
                ++n;
            }
            n = 0;
            while ((long)n < l2 && object.tryAdvance(ofDouble)) {
                ++n;
            }
            ofDouble.end();
            return ofDouble.build();
        }
    }

    public static interface OfInt
    extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, OfInt> {
        public static /* synthetic */ void lambda$truncate$0(int n) {
        }

        @Override
        default public void copyInto(Integer[] arrinteger, int n) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
            }
            int[] arrn = (int[])this.asPrimitiveArray();
            for (int i = 0; i < arrn.length; ++i) {
                arrinteger[n + i] = arrn[i];
            }
        }

        @Override
        default public void forEach(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                this.forEach((IntConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfInt)this.spliterator()).forEachRemaining(consumer);
            }
        }

        @Override
        default public StreamShape getShape() {
            return StreamShape.INT_VALUE;
        }

        @Override
        default public int[] newArray(int n) {
            return new int[n];
        }

        @Override
        default public OfInt truncate(long l, long l2, IntFunction<Integer[]> object) {
            if (l == 0L && l2 == this.count()) {
                return this;
            }
            object = (Spliterator.OfInt)this.spliterator();
            Builder.OfInt ofInt = Nodes.intBuilder(l2 -= l);
            ofInt.begin(l2);
            int n = 0;
            while ((long)n < l && object.tryAdvance((IntConsumer)_$$Lambda$Node$OfInt$SR5qcq7S0oCtehCDXAgbRdnvBbw.INSTANCE)) {
                ++n;
            }
            n = 0;
            while ((long)n < l2 && object.tryAdvance(ofInt)) {
                ++n;
            }
            ofInt.end();
            return ofInt.build();
        }
    }

    public static interface OfLong
    extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, OfLong> {
        public static /* synthetic */ void lambda$truncate$0(long l) {
        }

        @Override
        default public void copyInto(Long[] arrlong, int n) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
            }
            long[] arrl = (long[])this.asPrimitiveArray();
            for (int i = 0; i < arrl.length; ++i) {
                arrlong[n + i] = arrl[i];
            }
        }

        @Override
        default public void forEach(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                this.forEach((LongConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                }
                ((Spliterator.OfLong)this.spliterator()).forEachRemaining(consumer);
            }
        }

        @Override
        default public StreamShape getShape() {
            return StreamShape.LONG_VALUE;
        }

        @Override
        default public long[] newArray(int n) {
            return new long[n];
        }

        @Override
        default public OfLong truncate(long l, long l2, IntFunction<Long[]> object) {
            if (l == 0L && l2 == this.count()) {
                return this;
            }
            object = (Spliterator.OfLong)this.spliterator();
            Builder.OfLong ofLong = Nodes.longBuilder(l2 -= l);
            ofLong.begin(l2);
            int n = 0;
            while ((long)n < l && object.tryAdvance((LongConsumer)_$$Lambda$Node$OfLong$bPdsg_NFhPin_ja_QQPm0P0wq9s.INSTANCE)) {
                ++n;
            }
            n = 0;
            while ((long)n < l2 && object.tryAdvance(ofLong)) {
                ++n;
            }
            ofLong.end();
            return ofLong.build();
        }
    }

    public static interface OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
    extends Node<T> {
        @Override
        default public T[] asArray(IntFunction<T[]> arrT) {
            if (Tripwire.ENABLED) {
                Tripwire.trip(this.getClass(), "{0} calling Node.OfPrimitive.asArray");
            }
            if (this.count() < 0x7FFFFFF7L) {
                arrT = arrT.apply((int)this.count());
                this.copyInto(arrT, 0);
                return arrT;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        public T_ARR asPrimitiveArray();

        public void copyInto(T_ARR var1, int var2);

        public void forEach(T_CONS var1);

        default public T_NODE getChild(int n) {
            throw new IndexOutOfBoundsException();
        }

        public T_ARR newArray(int var1);

        public T_SPLITR spliterator();

        public T_NODE truncate(long var1, long var3, IntFunction<T[]> var5);
    }

}

