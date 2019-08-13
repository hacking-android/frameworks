/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$8ABiL5PN53c8rr14_yI2_4o5Zlo
 *  java.util.stream.-$$Lambda$B6rBjxAejI5kqKK9J3AHwY_L9ag
 *  java.util.stream.-$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go
 *  java.util.stream.-$$Lambda$LfPL0444L8HcP6gPtdKqQiCTSfM
 *  java.util.stream.-$$Lambda$Mo9-ryI3XUGyoHfpnRL3BoFhaqY
 *  java.util.stream.-$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY
 *  java.util.stream.-$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo
 */
package java.util.stream;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.stream.-$;
import java.util.stream.AbstractTask;
import java.util.stream.Node;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;
import java.util.stream.Stream;
import java.util.stream.StreamShape;
import java.util.stream._$$Lambda$8ABiL5PN53c8rr14_yI2_4o5Zlo;
import java.util.stream._$$Lambda$B6rBjxAejI5kqKK9J3AHwY_L9ag;
import java.util.stream._$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go;
import java.util.stream._$$Lambda$LfPL0444L8HcP6gPtdKqQiCTSfM;
import java.util.stream._$$Lambda$Mo9_ryI3XUGyoHfpnRL3BoFhaqY;
import java.util.stream._$$Lambda$Nodes$CollectorTask$OfRef$Zd2fdoB_mZW0DbPHybIpYjf_Pyo;
import java.util.stream._$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY;
import java.util.stream._$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo;

final class Nodes {
    static final String BAD_SIZE = "Stream size exceeds max array size";
    private static final double[] EMPTY_DOUBLE_ARRAY;
    private static final Node.OfDouble EMPTY_DOUBLE_NODE;
    private static final int[] EMPTY_INT_ARRAY;
    private static final Node.OfInt EMPTY_INT_NODE;
    private static final long[] EMPTY_LONG_ARRAY;
    private static final Node.OfLong EMPTY_LONG_NODE;
    private static final Node EMPTY_NODE;
    static final long MAX_ARRAY_SIZE = 0x7FFFFFF7L;

    static {
        EMPTY_NODE = new EmptyNode.OfRef();
        EMPTY_INT_NODE = new EmptyNode.OfInt();
        EMPTY_LONG_NODE = new EmptyNode.OfLong();
        EMPTY_DOUBLE_NODE = new EmptyNode.OfDouble();
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_DOUBLE_ARRAY = new double[0];
    }

    private Nodes() {
        throw new Error("no instances");
    }

    static <T> Node.Builder<T> builder() {
        return new SpinedNodeBuilder();
    }

    static <T> Node.Builder<T> builder(long l, IntFunction<T[]> builder) {
        builder = l >= 0L && l < 0x7FFFFFF7L ? new FixedNodeBuilder(l, (IntFunction<T[]>)((Object)builder)) : Nodes.builder();
        return builder;
    }

    public static <P_IN, P_OUT> Node<P_OUT> collect(PipelineHelper<P_OUT> node, Spliterator<P_IN> spliterator, boolean bl, IntFunction<P_OUT[]> arrP_OUT) {
        block2 : {
            long l = ((PipelineHelper)((Object)node)).exactOutputSizeIfKnown(spliterator);
            if (l >= 0L && spliterator.hasCharacteristics(16384)) {
                if (l < 0x7FFFFFF7L) {
                    arrP_OUT = arrP_OUT.apply((int)l);
                    new SizedCollectorTask.OfRef<P_IN, T>(spliterator, (PipelineHelper<T>)((Object)node), arrP_OUT).invoke();
                    return Nodes.node(arrP_OUT);
                }
                throw new IllegalArgumentException(BAD_SIZE);
            }
            node = (Node)new CollectorTask.OfRef<P_IN, P_OUT>((PipelineHelper<P_OUT>)((Object)node), (IntFunction<P_OUT[]>)arrP_OUT, spliterator).invoke();
            if (!bl) break block2;
            node = Nodes.flatten(node, arrP_OUT);
        }
        return node;
    }

    public static <P_IN> Node.OfDouble collectDouble(PipelineHelper<Double> object, Spliterator<P_IN> spliterator, boolean bl) {
        block2 : {
            long l = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
            if (l >= 0L && spliterator.hasCharacteristics(16384)) {
                if (l < 0x7FFFFFF7L) {
                    double[] arrd = new double[(int)l];
                    new SizedCollectorTask.OfDouble<P_IN>(spliterator, (PipelineHelper<Double>)object, arrd).invoke();
                    return Nodes.node(arrd);
                }
                throw new IllegalArgumentException(BAD_SIZE);
            }
            object = (Node.OfDouble)new CollectorTask.OfDouble<P_IN>((PipelineHelper<Double>)object, spliterator).invoke();
            if (!bl) break block2;
            object = Nodes.flattenDouble((Node.OfDouble)object);
        }
        return object;
    }

    public static <P_IN> Node.OfInt collectInt(PipelineHelper<Integer> object, Spliterator<P_IN> spliterator, boolean bl) {
        block2 : {
            long l = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
            if (l >= 0L && spliterator.hasCharacteristics(16384)) {
                if (l < 0x7FFFFFF7L) {
                    int[] arrn = new int[(int)l];
                    new SizedCollectorTask.OfInt<P_IN>(spliterator, (PipelineHelper<Integer>)object, arrn).invoke();
                    return Nodes.node(arrn);
                }
                throw new IllegalArgumentException(BAD_SIZE);
            }
            object = (Node.OfInt)new CollectorTask.OfInt<P_IN>((PipelineHelper<Integer>)object, spliterator).invoke();
            if (!bl) break block2;
            object = Nodes.flattenInt((Node.OfInt)object);
        }
        return object;
    }

    public static <P_IN> Node.OfLong collectLong(PipelineHelper<Long> object, Spliterator<P_IN> spliterator, boolean bl) {
        block2 : {
            long l = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
            if (l >= 0L && spliterator.hasCharacteristics(16384)) {
                if (l < 0x7FFFFFF7L) {
                    long[] arrl = new long[(int)l];
                    new SizedCollectorTask.OfLong<P_IN>(spliterator, (PipelineHelper<Long>)object, arrl).invoke();
                    return Nodes.node(arrl);
                }
                throw new IllegalArgumentException(BAD_SIZE);
            }
            object = (Node.OfLong)new CollectorTask.OfLong<P_IN>((PipelineHelper<Long>)object, spliterator).invoke();
            if (!bl) break block2;
            object = Nodes.flattenLong((Node.OfLong)object);
        }
        return object;
    }

    static <T> Node<T> conc(StreamShape streamShape, Node<T> object, Node<T> node) {
        int n = 1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return new ConcNode.OfDouble((Node.OfDouble)object, (Node.OfDouble)node);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown shape ");
                    ((StringBuilder)object).append((Object)streamShape);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                return new ConcNode.OfLong((Node.OfLong)object, (Node.OfLong)node);
            }
            return new ConcNode.OfInt((Node.OfInt)object, (Node.OfInt)node);
        }
        return new ConcNode<T>((Node<T>)object, node);
    }

    static Node.Builder.OfDouble doubleBuilder() {
        return new DoubleSpinedNodeBuilder();
    }

    static Node.Builder.OfDouble doubleBuilder(long l) {
        Node.Builder.OfDouble ofDouble = l >= 0L && l < 0x7FFFFFF7L ? new DoubleFixedNodeBuilder(l) : Nodes.doubleBuilder();
        return ofDouble;
    }

    static <T> Node<T> emptyNode(StreamShape streamShape) {
        int n = 1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return EMPTY_DOUBLE_NODE;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown shape ");
                    stringBuilder.append((Object)streamShape);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                return EMPTY_LONG_NODE;
            }
            return EMPTY_INT_NODE;
        }
        return EMPTY_NODE;
    }

    public static <T> Node<T> flatten(Node<T> node, IntFunction<T[]> arrobject) {
        if (node.getChildCount() > 0) {
            long l = node.count();
            if (l < 0x7FFFFFF7L) {
                arrobject = arrobject.apply((int)l);
                new ToArrayTask.OfRef(node, arrobject, 0).invoke();
                return Nodes.node(arrobject);
            }
            throw new IllegalArgumentException(BAD_SIZE);
        }
        return node;
    }

    public static Node.OfDouble flattenDouble(Node.OfDouble ofDouble) {
        if (ofDouble.getChildCount() > 0) {
            long l = ofDouble.count();
            if (l < 0x7FFFFFF7L) {
                double[] arrd = new double[(int)l];
                new ToArrayTask.OfDouble(ofDouble, arrd, 0).invoke();
                return Nodes.node(arrd);
            }
            throw new IllegalArgumentException(BAD_SIZE);
        }
        return ofDouble;
    }

    public static Node.OfInt flattenInt(Node.OfInt ofInt) {
        if (ofInt.getChildCount() > 0) {
            long l = ofInt.count();
            if (l < 0x7FFFFFF7L) {
                int[] arrn = new int[(int)l];
                new ToArrayTask.OfInt(ofInt, arrn, 0).invoke();
                return Nodes.node(arrn);
            }
            throw new IllegalArgumentException(BAD_SIZE);
        }
        return ofInt;
    }

    public static Node.OfLong flattenLong(Node.OfLong ofLong) {
        if (ofLong.getChildCount() > 0) {
            long l = ofLong.count();
            if (l < 0x7FFFFFF7L) {
                long[] arrl = new long[(int)l];
                new ToArrayTask.OfLong(ofLong, arrl, 0).invoke();
                return Nodes.node(arrl);
            }
            throw new IllegalArgumentException(BAD_SIZE);
        }
        return ofLong;
    }

    static Node.Builder.OfInt intBuilder() {
        return new IntSpinedNodeBuilder();
    }

    static Node.Builder.OfInt intBuilder(long l) {
        Node.Builder.OfInt ofInt = l >= 0L && l < 0x7FFFFFF7L ? new IntFixedNodeBuilder(l) : Nodes.intBuilder();
        return ofInt;
    }

    static Node.Builder.OfLong longBuilder() {
        return new LongSpinedNodeBuilder();
    }

    static Node.Builder.OfLong longBuilder(long l) {
        Node.Builder.OfLong ofLong = l >= 0L && l < 0x7FFFFFF7L ? new LongFixedNodeBuilder(l) : Nodes.longBuilder();
        return ofLong;
    }

    static Node.OfDouble node(double[] arrd) {
        return new DoubleArrayNode(arrd);
    }

    static Node.OfInt node(int[] arrn) {
        return new IntArrayNode(arrn);
    }

    static Node.OfLong node(long[] arrl) {
        return new LongArrayNode(arrl);
    }

    static <T> Node<T> node(Collection<T> collection) {
        return new CollectionNode<T>(collection);
    }

    static <T> Node<T> node(T[] arrT) {
        return new ArrayNode<T>(arrT);
    }

    private static abstract class AbstractConcNode<T, T_NODE extends Node<T>>
    implements Node<T> {
        protected final T_NODE left;
        protected final T_NODE right;
        private final long size;

        AbstractConcNode(T_NODE T_NODE, T_NODE T_NODE2) {
            this.left = T_NODE;
            this.right = T_NODE2;
            this.size = T_NODE.count() + T_NODE2.count();
        }

        @Override
        public long count() {
            return this.size;
        }

        public T_NODE getChild(int n) {
            if (n == 0) {
                return this.left;
            }
            if (n == 1) {
                return this.right;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int getChildCount() {
            return 2;
        }
    }

    private static class ArrayNode<T>
    implements Node<T> {
        final T[] array;
        int curSize;

        ArrayNode(long l, IntFunction<T[]> intFunction) {
            if (l < 0x7FFFFFF7L) {
                this.array = intFunction.apply((int)l);
                this.curSize = 0;
                return;
            }
            throw new IllegalArgumentException(Nodes.BAD_SIZE);
        }

        ArrayNode(T[] arrT) {
            this.array = arrT;
            this.curSize = arrT.length;
        }

        @Override
        public T[] asArray(IntFunction<T[]> arrT) {
            arrT = this.array;
            if (arrT.length == this.curSize) {
                return arrT;
            }
            throw new IllegalStateException();
        }

        @Override
        public void copyInto(T[] arrT, int n) {
            System.arraycopy(this.array, 0, arrT, n, this.curSize);
        }

        @Override
        public long count() {
            return this.curSize;
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            for (int i = 0; i < this.curSize; ++i) {
                consumer.accept(this.array[i]);
            }
        }

        @Override
        public Spliterator<T> spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("ArrayNode[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class CollectionNode<T>
    implements Node<T> {
        private final Collection<T> c;

        CollectionNode(Collection<T> collection) {
            this.c = collection;
        }

        @Override
        public T[] asArray(IntFunction<T[]> intFunction) {
            Collection<T> collection = this.c;
            return collection.toArray(intFunction.apply(collection.size()));
        }

        @Override
        public void copyInto(T[] arrT, int n) {
            for (T arrT[n] : this.c) {
                ++n;
            }
        }

        @Override
        public long count() {
            return this.c.size();
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            this.c.forEach(consumer);
        }

        @Override
        public Spliterator<T> spliterator() {
            return this.c.stream().spliterator();
        }

        public String toString() {
            return String.format("CollectionNode[%d][%s]", this.c.size(), this.c);
        }
    }

    private static class CollectorTask<P_IN, P_OUT, T_NODE extends Node<P_OUT>, T_BUILDER extends Node.Builder<P_OUT>>
    extends AbstractTask<P_IN, P_OUT, T_NODE, CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER>> {
        protected final LongFunction<T_BUILDER> builderFactory;
        protected final BinaryOperator<T_NODE> concFactory;
        protected final PipelineHelper<P_OUT> helper;

        CollectorTask(CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER> collectorTask, Spliterator<P_IN> spliterator) {
            super(collectorTask, spliterator);
            this.helper = collectorTask.helper;
            this.builderFactory = collectorTask.builderFactory;
            this.concFactory = collectorTask.concFactory;
        }

        CollectorTask(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator, LongFunction<T_BUILDER> longFunction, BinaryOperator<T_NODE> binaryOperator) {
            super(pipelineHelper, spliterator);
            this.helper = pipelineHelper;
            this.builderFactory = longFunction;
            this.concFactory = binaryOperator;
        }

        @Override
        protected T_NODE doLeaf() {
            Node.Builder builder = (Node.Builder)this.builderFactory.apply(this.helper.exactOutputSizeIfKnown(this.spliterator));
            return (T_NODE)this.helper.wrapAndCopyInto(builder, this.spliterator).build();
        }

        @Override
        protected CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER> makeChild(Spliterator<P_IN> spliterator) {
            return new CollectorTask<P_IN, P_OUT, T_NODE, T_BUILDER>(this, spliterator);
        }

        @Override
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!this.isLeaf()) {
                this.setLocalResult((Node)this.concFactory.apply((Node)((CollectorTask)this.leftChild).getLocalResult(), (Node)((CollectorTask)this.rightChild).getLocalResult()));
            }
            super.onCompletion(countedCompleter);
        }

        private static final class OfDouble<P_IN>
        extends CollectorTask<P_IN, Double, Node.OfDouble, Node.Builder.OfDouble> {
            OfDouble(PipelineHelper<Double> pipelineHelper, Spliterator<P_IN> spliterator) {
                super(pipelineHelper, spliterator, _$$Lambda$LfPL0444L8HcP6gPtdKqQiCTSfM.INSTANCE, _$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go.INSTANCE);
            }
        }

        private static final class OfInt<P_IN>
        extends CollectorTask<P_IN, Integer, Node.OfInt, Node.Builder.OfInt> {
            OfInt(PipelineHelper<Integer> pipelineHelper, Spliterator<P_IN> spliterator) {
                super(pipelineHelper, spliterator, _$$Lambda$B6rBjxAejI5kqKK9J3AHwY_L9ag.INSTANCE, _$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY.INSTANCE);
            }
        }

        private static final class OfLong<P_IN>
        extends CollectorTask<P_IN, Long, Node.OfLong, Node.Builder.OfLong> {
            OfLong(PipelineHelper<Long> pipelineHelper, Spliterator<P_IN> spliterator) {
                super(pipelineHelper, spliterator, _$$Lambda$8ABiL5PN53c8rr14_yI2_4o5Zlo.INSTANCE, _$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo.INSTANCE);
            }
        }

        private static final class OfRef<P_IN, P_OUT>
        extends CollectorTask<P_IN, P_OUT, Node<P_OUT>, Node.Builder<P_OUT>> {
            OfRef(PipelineHelper<P_OUT> pipelineHelper, IntFunction<P_OUT[]> intFunction, Spliterator<P_IN> spliterator) {
                super(pipelineHelper, spliterator, new _$$Lambda$Nodes$CollectorTask$OfRef$Zd2fdoB_mZW0DbPHybIpYjf_Pyo(intFunction), _$$Lambda$Mo9_ryI3XUGyoHfpnRL3BoFhaqY.INSTANCE);
            }

            static /* synthetic */ Node.Builder lambda$new$0(IntFunction intFunction, long l) {
                return Nodes.builder(l, intFunction);
            }
        }

    }

    static final class ConcNode<T>
    extends AbstractConcNode<T, Node<T>>
    implements Node<T> {
        ConcNode(Node<T> node, Node<T> node2) {
            super(node, node2);
        }

        @Override
        public T[] asArray(IntFunction<T[]> arrT) {
            long l = this.count();
            if (l < 0x7FFFFFF7L) {
                arrT = arrT.apply((int)l);
                this.copyInto(arrT, 0);
                return arrT;
            }
            throw new IllegalArgumentException(Nodes.BAD_SIZE);
        }

        @Override
        public void copyInto(T[] arrT, int n) {
            Objects.requireNonNull(arrT);
            this.left.copyInto(arrT, n);
            this.right.copyInto(arrT, (int)this.left.count() + n);
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            this.left.forEach(consumer);
            this.right.forEach(consumer);
        }

        @Override
        public Spliterator<T> spliterator() {
            return new InternalNodeSpliterator.OfRef(this);
        }

        public String toString() {
            if (this.count() < 32L) {
                return String.format("ConcNode[%s.%s]", this.left, this.right);
            }
            return String.format("ConcNode[size=%d]", this.count());
        }

        @Override
        public Node<T> truncate(long l, long l2, IntFunction<T[]> intFunction) {
            if (l == 0L && l2 == this.count()) {
                return this;
            }
            long l3 = this.left.count();
            if (l >= l3) {
                return this.right.truncate(l - l3, l2 - l3, intFunction);
            }
            if (l2 <= l3) {
                return this.left.truncate(l, l2, intFunction);
            }
            return Nodes.conc(this.getShape(), this.left.truncate(l, l3, intFunction), this.right.truncate(0L, l2 - l3, intFunction));
        }

        static final class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble>
        implements Node.OfDouble {
            OfDouble(Node.OfDouble ofDouble, Node.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            @Override
            public Spliterator.OfDouble spliterator() {
                return new InternalNodeSpliterator.OfDouble(this);
            }
        }

        static final class OfInt
        extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt>
        implements Node.OfInt {
            OfInt(Node.OfInt ofInt, Node.OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            @Override
            public Spliterator.OfInt spliterator() {
                return new InternalNodeSpliterator.OfInt(this);
            }
        }

        static final class OfLong
        extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong>
        implements Node.OfLong {
            OfLong(Node.OfLong ofLong, Node.OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            @Override
            public Spliterator.OfLong spliterator() {
                return new InternalNodeSpliterator.OfLong(this);
            }
        }

        private static abstract class OfPrimitive<E, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<E, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE>>
        extends AbstractConcNode<E, T_NODE>
        implements Node.OfPrimitive<E, T_CONS, T_ARR, T_SPLITR, T_NODE> {
            OfPrimitive(T_NODE T_NODE, T_NODE T_NODE2) {
                super(T_NODE, T_NODE2);
            }

            @Override
            public T_ARR asPrimitiveArray() {
                long l = this.count();
                if (l < 0x7FFFFFF7L) {
                    Object T_ARR = this.newArray((int)l);
                    this.copyInto(T_ARR, 0);
                    return T_ARR;
                }
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            }

            @Override
            public void copyInto(T_ARR T_ARR, int n) {
                ((Node.OfPrimitive)this.left).copyInto(T_ARR, n);
                ((Node.OfPrimitive)this.right).copyInto(T_ARR, (int)((Node.OfPrimitive)this.left).count() + n);
            }

            @Override
            public void forEach(T_CONS T_CONS) {
                ((Node.OfPrimitive)this.left).forEach(T_CONS);
                ((Node.OfPrimitive)this.right).forEach(T_CONS);
            }

            public String toString() {
                if (this.count() < 32L) {
                    return String.format("%s[%s.%s]", this.getClass().getName(), this.left, this.right);
                }
                return String.format("%s[size=%d]", this.getClass().getName(), this.count());
            }
        }

    }

    private static class DoubleArrayNode
    implements Node.OfDouble {
        final double[] array;
        int curSize;

        DoubleArrayNode(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new double[(int)l];
                this.curSize = 0;
                return;
            }
            throw new IllegalArgumentException(Nodes.BAD_SIZE);
        }

        DoubleArrayNode(double[] arrd) {
            this.array = arrd;
            this.curSize = arrd.length;
        }

        @Override
        public double[] asPrimitiveArray() {
            double[] arrd = this.array;
            int n = arrd.length;
            int n2 = this.curSize;
            if (n == n2) {
                return arrd;
            }
            return Arrays.copyOf(arrd, n2);
        }

        @Override
        public void copyInto(double[] arrd, int n) {
            System.arraycopy((Object)this.array, 0, (Object)arrd, n, this.curSize);
        }

        @Override
        public long count() {
            return this.curSize;
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            for (int i = 0; i < this.curSize; ++i) {
                doubleConsumer.accept(this.array[i]);
            }
        }

        @Override
        public Spliterator.OfDouble spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("DoubleArrayNode[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class DoubleFixedNodeBuilder
    extends DoubleArrayNode
    implements Node.Builder.OfDouble {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        DoubleFixedNodeBuilder(long l) {
            super(l);
        }

        @Override
        public void accept(double d) {
            if (this.curSize < this.array.length) {
                double[] arrd = this.array;
                int n = this.curSize;
                this.curSize = n + 1;
                arrd[n] = d;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", this.array.length));
        }

        @Override
        public void begin(long l) {
            if (l == (long)this.array.length) {
                this.curSize = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", l, this.array.length));
        }

        @Override
        public Node.OfDouble build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public void end() {
            if (this.curSize >= this.array.length) {
                return;
            }
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public String toString() {
            return String.format("DoubleFixedNodeBuilder[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class DoubleSpinedNodeBuilder
    extends SpinedBuffer.OfDouble
    implements Node.OfDouble,
    Node.Builder.OfDouble {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        DoubleSpinedNodeBuilder() {
        }

        @Override
        public void accept(double d) {
            super.accept(d);
        }

        @Override
        public double[] asPrimitiveArray() {
            return (double[])super.asPrimitiveArray();
        }

        @Override
        public void begin(long l) {
            this.building = true;
            this.clear();
            this.ensureCapacity(l);
        }

        @Override
        public Node.OfDouble build() {
            return this;
        }

        @Override
        public void copyInto(double[] arrd, int n) {
            super.copyInto(arrd, n);
        }

        @Override
        public void end() {
            this.building = false;
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            super.forEach(doubleConsumer);
        }

        @Override
        public Spliterator.OfDouble spliterator() {
            return super.spliterator();
        }
    }

    private static abstract class EmptyNode<T, T_ARR, T_CONS>
    implements Node<T> {
        EmptyNode() {
        }

        @Override
        public T[] asArray(IntFunction<T[]> intFunction) {
            return intFunction.apply(0);
        }

        public void copyInto(T_ARR T_ARR, int n) {
        }

        @Override
        public long count() {
            return 0L;
        }

        public void forEach(T_CONS T_CONS) {
        }

        private static final class OfDouble
        extends EmptyNode<Double, double[], DoubleConsumer>
        implements Node.OfDouble {
            OfDouble() {
            }

            @Override
            public double[] asPrimitiveArray() {
                return EMPTY_DOUBLE_ARRAY;
            }

            @Override
            public Spliterator.OfDouble spliterator() {
                return Spliterators.emptyDoubleSpliterator();
            }
        }

        private static final class OfInt
        extends EmptyNode<Integer, int[], IntConsumer>
        implements Node.OfInt {
            OfInt() {
            }

            @Override
            public int[] asPrimitiveArray() {
                return EMPTY_INT_ARRAY;
            }

            @Override
            public Spliterator.OfInt spliterator() {
                return Spliterators.emptyIntSpliterator();
            }
        }

        private static final class OfLong
        extends EmptyNode<Long, long[], LongConsumer>
        implements Node.OfLong {
            OfLong() {
            }

            @Override
            public long[] asPrimitiveArray() {
                return EMPTY_LONG_ARRAY;
            }

            @Override
            public Spliterator.OfLong spliterator() {
                return Spliterators.emptyLongSpliterator();
            }
        }

        private static class OfRef<T>
        extends EmptyNode<T, T[], Consumer<? super T>> {
            private OfRef() {
            }

            @Override
            public Spliterator<T> spliterator() {
                return Spliterators.emptySpliterator();
            }
        }

    }

    private static final class FixedNodeBuilder<T>
    extends ArrayNode<T>
    implements Node.Builder<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        FixedNodeBuilder(long l, IntFunction<T[]> intFunction) {
            super(l, intFunction);
        }

        @Override
        public void accept(T t) {
            if (this.curSize < this.array.length) {
                Object[] arrobject = this.array;
                int n = this.curSize;
                this.curSize = n + 1;
                arrobject[n] = t;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", this.array.length));
        }

        @Override
        public void begin(long l) {
            if (l == (long)this.array.length) {
                this.curSize = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", l, this.array.length));
        }

        @Override
        public Node<T> build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public void end() {
            if (this.curSize >= this.array.length) {
                return;
            }
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public String toString() {
            return String.format("FixedNodeBuilder[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static class IntArrayNode
    implements Node.OfInt {
        final int[] array;
        int curSize;

        IntArrayNode(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new int[(int)l];
                this.curSize = 0;
                return;
            }
            throw new IllegalArgumentException(Nodes.BAD_SIZE);
        }

        IntArrayNode(int[] arrn) {
            this.array = arrn;
            this.curSize = arrn.length;
        }

        @Override
        public int[] asPrimitiveArray() {
            int[] arrn = this.array;
            int n = arrn.length;
            int n2 = this.curSize;
            if (n == n2) {
                return arrn;
            }
            return Arrays.copyOf(arrn, n2);
        }

        @Override
        public void copyInto(int[] arrn, int n) {
            System.arraycopy((Object)this.array, 0, (Object)arrn, n, this.curSize);
        }

        @Override
        public long count() {
            return this.curSize;
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            for (int i = 0; i < this.curSize; ++i) {
                intConsumer.accept(this.array[i]);
            }
        }

        @Override
        public Spliterator.OfInt spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("IntArrayNode[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class IntFixedNodeBuilder
    extends IntArrayNode
    implements Node.Builder.OfInt {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        IntFixedNodeBuilder(long l) {
            super(l);
        }

        @Override
        public void accept(int n) {
            if (this.curSize < this.array.length) {
                int[] arrn = this.array;
                int n2 = this.curSize;
                this.curSize = n2 + 1;
                arrn[n2] = n;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", this.array.length));
        }

        @Override
        public void begin(long l) {
            if (l == (long)this.array.length) {
                this.curSize = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", l, this.array.length));
        }

        @Override
        public Node.OfInt build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public void end() {
            if (this.curSize >= this.array.length) {
                return;
            }
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public String toString() {
            return String.format("IntFixedNodeBuilder[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class IntSpinedNodeBuilder
    extends SpinedBuffer.OfInt
    implements Node.OfInt,
    Node.Builder.OfInt {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        IntSpinedNodeBuilder() {
        }

        @Override
        public void accept(int n) {
            super.accept(n);
        }

        @Override
        public int[] asPrimitiveArray() {
            return (int[])super.asPrimitiveArray();
        }

        @Override
        public void begin(long l) {
            this.building = true;
            this.clear();
            this.ensureCapacity(l);
        }

        @Override
        public Node.OfInt build() {
            return this;
        }

        @Override
        public void copyInto(int[] arrn, int n) throws IndexOutOfBoundsException {
            super.copyInto(arrn, n);
        }

        @Override
        public void end() {
            this.building = false;
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            super.forEach(intConsumer);
        }

        @Override
        public Spliterator.OfInt spliterator() {
            return super.spliterator();
        }
    }

    private static abstract class InternalNodeSpliterator<T, S extends Spliterator<T>, N extends Node<T>>
    implements Spliterator<T> {
        int curChildIndex;
        N curNode;
        S lastNodeSpliterator;
        S tryAdvanceSpliterator;
        Deque<N> tryAdvanceStack;

        InternalNodeSpliterator(N n) {
            this.curNode = n;
        }

        @Override
        public final int characteristics() {
            return 64;
        }

        @Override
        public final long estimateSize() {
            if (this.curNode == null) {
                return 0L;
            }
            S s = this.lastNodeSpliterator;
            if (s != null) {
                return s.estimateSize();
            }
            long l = 0L;
            for (int i = this.curChildIndex; i < this.curNode.getChildCount(); ++i) {
                l += this.curNode.getChild(i).count();
            }
            return l;
        }

        protected final N findNextLeafNode(Deque<N> deque) {
            Node node;
            while ((node = (Node)deque.pollFirst()) != null) {
                if (node.getChildCount() == 0) {
                    if (node.count() <= 0L) continue;
                    return (N)node;
                }
                for (int i = node.getChildCount() - 1; i >= 0; --i) {
                    deque.addFirst(node.getChild(i));
                }
            }
            return null;
        }

        protected final Deque<N> initStack() {
            ArrayDeque arrayDeque = new ArrayDeque(8);
            for (int i = this.curNode.getChildCount() - 1; i >= this.curChildIndex; --i) {
                arrayDeque.addFirst(this.curNode.getChild(i));
            }
            return arrayDeque;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected final boolean initTryAdvance() {
            if (this.curNode == null) {
                return false;
            }
            if (this.tryAdvanceSpliterator != null) return true;
            S s = this.lastNodeSpliterator;
            if (s != null) {
                this.tryAdvanceSpliterator = s;
                return true;
            }
            this.tryAdvanceStack = this.initStack();
            N n = this.findNextLeafNode(this.tryAdvanceStack);
            if (n != null) {
                this.tryAdvanceSpliterator = n.spliterator();
                return true;
            }
            this.curNode = null;
            return false;
        }

        public final S trySplit() {
            N n = this.curNode;
            if (n != null && this.tryAdvanceSpliterator == null) {
                Object object = this.lastNodeSpliterator;
                if (object != null) {
                    return (S)object.trySplit();
                }
                if (this.curChildIndex < n.getChildCount() - 1) {
                    object = this.curNode;
                    int n2 = this.curChildIndex;
                    this.curChildIndex = n2 + 1;
                    return (S)object.getChild(n2).spliterator();
                }
                this.curNode = this.curNode.getChild(this.curChildIndex);
                if (this.curNode.getChildCount() == 0) {
                    this.lastNodeSpliterator = this.curNode.spliterator();
                    return (S)this.lastNodeSpliterator.trySplit();
                }
                this.curChildIndex = 0;
                object = this.curNode;
                int n3 = this.curChildIndex;
                this.curChildIndex = n3 + 1;
                return (S)object.getChild(n3).spliterator();
            }
            return null;
        }

        private static final class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble>
        implements Spliterator.OfDouble {
            OfDouble(Node.OfDouble ofDouble) {
                super(ofDouble);
            }
        }

        private static final class OfInt
        extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt>
        implements Spliterator.OfInt {
            OfInt(Node.OfInt ofInt) {
                super(ofInt);
            }
        }

        private static final class OfLong
        extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong>
        implements Spliterator.OfLong {
            OfLong(Node.OfLong ofLong) {
                super(ofLong);
            }
        }

        private static abstract class OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, N extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, N>>
        extends InternalNodeSpliterator<T, T_SPLITR, N>
        implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            OfPrimitive(N n) {
                super(n);
            }

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator == null) {
                    if (this.lastNodeSpliterator == null) {
                        Node.OfPrimitive ofPrimitive;
                        Deque deque = this.initStack();
                        while ((ofPrimitive = (Node.OfPrimitive)this.findNextLeafNode(deque)) != null) {
                            ofPrimitive.forEach(T_CONS);
                        }
                        this.curNode = null;
                    } else {
                        ((Spliterator.OfPrimitive)this.lastNodeSpliterator).forEachRemaining(T_CONS);
                    }
                } else {
                    while (this.tryAdvance(T_CONS)) {
                    }
                }
            }

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                if (!this.initTryAdvance()) {
                    return false;
                }
                boolean bl = ((Spliterator.OfPrimitive)this.tryAdvanceSpliterator).tryAdvance(T_CONS);
                if (!bl) {
                    Node.OfPrimitive ofPrimitive;
                    if (this.lastNodeSpliterator == null && (ofPrimitive = (Node.OfPrimitive)this.findNextLeafNode(this.tryAdvanceStack)) != null) {
                        this.tryAdvanceSpliterator = ofPrimitive.spliterator();
                        return ((Spliterator.OfPrimitive)this.tryAdvanceSpliterator).tryAdvance(T_CONS);
                    }
                    this.curNode = null;
                }
                return bl;
            }
        }

        private static final class OfRef<T>
        extends InternalNodeSpliterator<T, Spliterator<T>, Node<T>> {
            OfRef(Node<T> node) {
                super(node);
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator == null) {
                    if (this.lastNodeSpliterator == null) {
                        Object n;
                        Deque deque = this.initStack();
                        while ((n = this.findNextLeafNode(deque)) != null) {
                            n.forEach(consumer);
                        }
                        this.curNode = null;
                    } else {
                        this.lastNodeSpliterator.forEachRemaining(consumer);
                    }
                } else {
                    while (this.tryAdvance(consumer)) {
                    }
                }
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                if (!this.initTryAdvance()) {
                    return false;
                }
                boolean bl = this.tryAdvanceSpliterator.tryAdvance(consumer);
                if (!bl) {
                    Object n;
                    if (this.lastNodeSpliterator == null && (n = this.findNextLeafNode(this.tryAdvanceStack)) != null) {
                        this.tryAdvanceSpliterator = n.spliterator();
                        return this.tryAdvanceSpliterator.tryAdvance(consumer);
                    }
                    this.curNode = null;
                }
                return bl;
            }
        }

    }

    private static class LongArrayNode
    implements Node.OfLong {
        final long[] array;
        int curSize;

        LongArrayNode(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new long[(int)l];
                this.curSize = 0;
                return;
            }
            throw new IllegalArgumentException(Nodes.BAD_SIZE);
        }

        LongArrayNode(long[] arrl) {
            this.array = arrl;
            this.curSize = arrl.length;
        }

        @Override
        public long[] asPrimitiveArray() {
            long[] arrl = this.array;
            int n = arrl.length;
            int n2 = this.curSize;
            if (n == n2) {
                return arrl;
            }
            return Arrays.copyOf(arrl, n2);
        }

        @Override
        public void copyInto(long[] arrl, int n) {
            System.arraycopy((Object)this.array, 0, (Object)arrl, n, this.curSize);
        }

        @Override
        public long count() {
            return this.curSize;
        }

        @Override
        public void forEach(LongConsumer longConsumer) {
            for (int i = 0; i < this.curSize; ++i) {
                longConsumer.accept(this.array[i]);
            }
        }

        @Override
        public Spliterator.OfLong spliterator() {
            return Arrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("LongArrayNode[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class LongFixedNodeBuilder
    extends LongArrayNode
    implements Node.Builder.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        LongFixedNodeBuilder(long l) {
            super(l);
        }

        @Override
        public void accept(long l) {
            if (this.curSize < this.array.length) {
                long[] arrl = this.array;
                int n = this.curSize;
                this.curSize = n + 1;
                arrl[n] = l;
                return;
            }
            throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", this.array.length));
        }

        @Override
        public void begin(long l) {
            if (l == (long)this.array.length) {
                this.curSize = 0;
                return;
            }
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", l, this.array.length));
        }

        @Override
        public Node.OfLong build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public void end() {
            if (this.curSize >= this.array.length) {
                return;
            }
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", this.curSize, this.array.length));
        }

        @Override
        public String toString() {
            return String.format("LongFixedNodeBuilder[%d][%s]", this.array.length - this.curSize, Arrays.toString(this.array));
        }
    }

    private static final class LongSpinedNodeBuilder
    extends SpinedBuffer.OfLong
    implements Node.OfLong,
    Node.Builder.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        LongSpinedNodeBuilder() {
        }

        @Override
        public void accept(long l) {
            super.accept(l);
        }

        @Override
        public long[] asPrimitiveArray() {
            return (long[])super.asPrimitiveArray();
        }

        @Override
        public void begin(long l) {
            this.building = true;
            this.clear();
            this.ensureCapacity(l);
        }

        @Override
        public Node.OfLong build() {
            return this;
        }

        @Override
        public void copyInto(long[] arrl, int n) {
            super.copyInto(arrl, n);
        }

        @Override
        public void end() {
            this.building = false;
        }

        @Override
        public void forEach(LongConsumer longConsumer) {
            super.forEach(longConsumer);
        }

        @Override
        public Spliterator.OfLong spliterator() {
            return super.spliterator();
        }
    }

    private static abstract class SizedCollectorTask<P_IN, P_OUT, T_SINK extends Sink<P_OUT>, K extends SizedCollectorTask<P_IN, P_OUT, T_SINK, K>>
    extends CountedCompleter<Void>
    implements Sink<P_OUT> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected int fence;
        protected final PipelineHelper<P_OUT> helper;
        protected int index;
        protected long length;
        protected long offset;
        protected final Spliterator<P_IN> spliterator;
        protected final long targetSize;

        SizedCollectorTask(Spliterator<P_IN> spliterator, PipelineHelper<P_OUT> pipelineHelper, int n) {
            this.spliterator = spliterator;
            this.helper = pipelineHelper;
            this.targetSize = AbstractTask.suggestTargetSize(spliterator.estimateSize());
            this.offset = 0L;
            this.length = n;
        }

        SizedCollectorTask(K k, Spliterator<P_IN> spliterator, long l, long l2, int n) {
            super((CountedCompleter<?>)k);
            this.spliterator = spliterator;
            this.helper = ((SizedCollectorTask)k).helper;
            this.targetSize = ((SizedCollectorTask)k).targetSize;
            this.offset = l;
            this.length = l2;
            if (l >= 0L && l2 >= 0L && l + l2 - 1L < (long)n) {
                return;
            }
            throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", l, l, l2, n));
        }

        @Override
        public void begin(long l) {
            long l2 = this.length;
            if (l <= l2) {
                this.index = (int)this.offset;
                this.fence = this.index + (int)l2;
                return;
            }
            throw new IllegalStateException("size passed to Sink.begin exceeds array length");
        }

        @Override
        public void compute() {
            Spliterator<P_IN> spliterator;
            SizedCollectorTask<P_IN, P_OUT, T_SINK, K> sizedCollectorTask = this;
            Spliterator<P_IN> spliterator2 = this.spliterator;
            while (spliterator2.estimateSize() > sizedCollectorTask.targetSize && (spliterator = spliterator2.trySplit()) != null) {
                sizedCollectorTask.setPendingCount(1);
                long l = spliterator.estimateSize();
                ((ForkJoinTask)sizedCollectorTask.makeChild(spliterator, sizedCollectorTask.offset, l)).fork();
                sizedCollectorTask = sizedCollectorTask.makeChild(spliterator2, sizedCollectorTask.offset + l, sizedCollectorTask.length - l);
            }
            sizedCollectorTask.helper.wrapAndCopyInto(sizedCollectorTask, spliterator2);
            sizedCollectorTask.propagateCompletion();
        }

        abstract K makeChild(Spliterator<P_IN> var1, long var2, long var4);

        static final class OfDouble<P_IN>
        extends SizedCollectorTask<P_IN, Double, Sink.OfDouble, OfDouble<P_IN>>
        implements Sink.OfDouble {
            private final double[] array;

            OfDouble(Spliterator<P_IN> spliterator, PipelineHelper<Double> pipelineHelper, double[] arrd) {
                super(spliterator, pipelineHelper, arrd.length);
                this.array = arrd;
            }

            OfDouble(OfDouble<P_IN> ofDouble, Spliterator<P_IN> spliterator, long l, long l2) {
                super(ofDouble, spliterator, l, l2, ofDouble.array.length);
                this.array = ofDouble.array;
            }

            @Override
            public void accept(double d) {
                if (this.index < this.fence) {
                    double[] arrd = this.array;
                    int n = this.index;
                    this.index = n + 1;
                    arrd[n] = d;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override
            OfDouble<P_IN> makeChild(Spliterator<P_IN> spliterator, long l, long l2) {
                return new OfDouble<P_IN>(this, spliterator, l, l2);
            }
        }

        static final class OfInt<P_IN>
        extends SizedCollectorTask<P_IN, Integer, Sink.OfInt, OfInt<P_IN>>
        implements Sink.OfInt {
            private final int[] array;

            OfInt(Spliterator<P_IN> spliterator, PipelineHelper<Integer> pipelineHelper, int[] arrn) {
                super(spliterator, pipelineHelper, arrn.length);
                this.array = arrn;
            }

            OfInt(OfInt<P_IN> ofInt, Spliterator<P_IN> spliterator, long l, long l2) {
                super(ofInt, spliterator, l, l2, ofInt.array.length);
                this.array = ofInt.array;
            }

            @Override
            public void accept(int n) {
                if (this.index < this.fence) {
                    int[] arrn = this.array;
                    int n2 = this.index;
                    this.index = n2 + 1;
                    arrn[n2] = n;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override
            OfInt<P_IN> makeChild(Spliterator<P_IN> spliterator, long l, long l2) {
                return new OfInt<P_IN>(this, spliterator, l, l2);
            }
        }

        static final class OfLong<P_IN>
        extends SizedCollectorTask<P_IN, Long, Sink.OfLong, OfLong<P_IN>>
        implements Sink.OfLong {
            private final long[] array;

            OfLong(Spliterator<P_IN> spliterator, PipelineHelper<Long> pipelineHelper, long[] arrl) {
                super(spliterator, pipelineHelper, arrl.length);
                this.array = arrl;
            }

            OfLong(OfLong<P_IN> ofLong, Spliterator<P_IN> spliterator, long l, long l2) {
                super(ofLong, spliterator, l, l2, ofLong.array.length);
                this.array = ofLong.array;
            }

            @Override
            public void accept(long l) {
                if (this.index < this.fence) {
                    long[] arrl = this.array;
                    int n = this.index;
                    this.index = n + 1;
                    arrl[n] = l;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override
            OfLong<P_IN> makeChild(Spliterator<P_IN> spliterator, long l, long l2) {
                return new OfLong<P_IN>(this, spliterator, l, l2);
            }
        }

        static final class OfRef<P_IN, P_OUT>
        extends SizedCollectorTask<P_IN, P_OUT, Sink<P_OUT>, OfRef<P_IN, P_OUT>>
        implements Sink<P_OUT> {
            private final P_OUT[] array;

            OfRef(Spliterator<P_IN> spliterator, PipelineHelper<P_OUT> pipelineHelper, P_OUT[] arrP_OUT) {
                super(spliterator, pipelineHelper, arrP_OUT.length);
                this.array = arrP_OUT;
            }

            OfRef(OfRef<P_IN, P_OUT> ofRef, Spliterator<P_IN> spliterator, long l, long l2) {
                super(ofRef, spliterator, l, l2, ofRef.array.length);
                this.array = ofRef.array;
            }

            @Override
            public void accept(P_OUT P_OUT) {
                if (this.index < this.fence) {
                    P_OUT[] arrP_OUT = this.array;
                    int n = this.index;
                    this.index = n + 1;
                    arrP_OUT[n] = P_OUT;
                    return;
                }
                throw new IndexOutOfBoundsException(Integer.toString(this.index));
            }

            @Override
            OfRef<P_IN, P_OUT> makeChild(Spliterator<P_IN> spliterator, long l, long l2) {
                return new OfRef<P_IN, P_OUT>(this, spliterator, l, l2);
            }
        }

    }

    private static final class SpinedNodeBuilder<T>
    extends SpinedBuffer<T>
    implements Node<T>,
    Node.Builder<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean building = false;

        SpinedNodeBuilder() {
        }

        @Override
        public void accept(T t) {
            super.accept(t);
        }

        @Override
        public T[] asArray(IntFunction<T[]> intFunction) {
            return super.asArray(intFunction);
        }

        @Override
        public void begin(long l) {
            this.building = true;
            this.clear();
            this.ensureCapacity(l);
        }

        @Override
        public Node<T> build() {
            return this;
        }

        @Override
        public void copyInto(T[] arrT, int n) {
            super.copyInto(arrT, n);
        }

        @Override
        public void end() {
            this.building = false;
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            super.forEach(consumer);
        }

        @Override
        public Spliterator<T> spliterator() {
            return super.spliterator();
        }
    }

    private static abstract class ToArrayTask<T, T_NODE extends Node<T>, K extends ToArrayTask<T, T_NODE, K>>
    extends CountedCompleter<Void> {
        protected final T_NODE node;
        protected final int offset;

        ToArrayTask(T_NODE T_NODE, int n) {
            this.node = T_NODE;
            this.offset = n;
        }

        ToArrayTask(K k, T_NODE T_NODE, int n) {
            super((CountedCompleter<?>)k);
            this.node = T_NODE;
            this.offset = n;
        }

        @Override
        public void compute() {
            ToArrayTask<T, T_NODE, K> toArrayTask = this;
            do {
                int n;
                if (toArrayTask.node.getChildCount() == 0) {
                    toArrayTask.copyNodeToArray();
                    toArrayTask.propagateCompletion();
                    return;
                }
                toArrayTask.setPendingCount(toArrayTask.node.getChildCount() - 1);
                int n2 = 0;
                for (n = 0; n < toArrayTask.node.getChildCount() - 1; ++n) {
                    K k = toArrayTask.makeChild(n, toArrayTask.offset + n2);
                    n2 = (int)((long)n2 + ((ToArrayTask)k).node.count());
                    ((ForkJoinTask)k).fork();
                }
                toArrayTask = toArrayTask.makeChild(n, toArrayTask.offset + n2);
            } while (true);
        }

        abstract void copyNodeToArray();

        abstract K makeChild(int var1, int var2);

        private static final class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, Node.OfDouble> {
            private OfDouble(Node.OfDouble ofDouble, double[] arrd, int n) {
                super(ofDouble, arrd, n, null);
            }
        }

        private static final class OfInt
        extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, Node.OfInt> {
            private OfInt(Node.OfInt ofInt, int[] arrn, int n) {
                super(ofInt, arrn, n, null);
            }
        }

        private static final class OfLong
        extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, Node.OfLong> {
            private OfLong(Node.OfLong ofLong, long[] arrl, int n) {
                super(ofLong, arrl, n, null);
            }
        }

        private static class OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends Node.OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
        extends ToArrayTask<T, T_NODE, OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>> {
            private final T_ARR array;

            private OfPrimitive(T_NODE T_NODE, T_ARR T_ARR, int n) {
                super(T_NODE, n);
                this.array = T_ARR;
            }

            /* synthetic */ OfPrimitive(Node.OfPrimitive ofPrimitive, Object object, int n, 1 var4_4) {
                this(ofPrimitive, object, n);
            }

            private OfPrimitive(OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE> ofPrimitive, T_NODE T_NODE, int n) {
                super(ofPrimitive, T_NODE, n);
                this.array = ofPrimitive.array;
            }

            @Override
            void copyNodeToArray() {
                ((Node.OfPrimitive)this.node).copyInto(this.array, this.offset);
            }

            @Override
            OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE> makeChild(int n, int n2) {
                return new OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, Node>(this, ((Node.OfPrimitive)this.node).getChild(n), n2);
            }
        }

        private static final class OfRef<T>
        extends ToArrayTask<T, Node<T>, OfRef<T>> {
            private final T[] array;

            private OfRef(Node<T> node, T[] arrT, int n) {
                super(node, n);
                this.array = arrT;
            }

            private OfRef(OfRef<T> ofRef, Node<T> node, int n) {
                super(ofRef, node, n);
                this.array = ofRef.array;
            }

            @Override
            void copyNodeToArray() {
                this.node.copyInto(this.array, this.offset);
            }

            @Override
            OfRef<T> makeChild(int n, int n2) {
                return new OfRef(this, this.node.getChild(n), n2);
            }
        }

    }

}

