/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$VQnU3Jki1-RCSS5B-Yg_Kf6hQAY
 *  java.util.stream.-$$Lambda$r6LgDiay3Ow5w51ifJiV4dn8S84
 *  java.util.stream.-$$Lambda$zcFI7bYCRDtB1UMy72aPExbc6R4
 */
package java.util.stream;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.ForEachOps;
import java.util.stream.Node;
import java.util.stream.Nodes;
import java.util.stream.PipelineHelper;
import java.util.stream.ReduceOps;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.StreamSpliterators;
import java.util.stream._$$Lambda$DistinctOps$1$y8chmSlaKpIKb_VPPBaXdCNT51c;
import java.util.stream._$$Lambda$VQnU3Jki1_RCSS5B_Yg_Kf6hQAY;
import java.util.stream._$$Lambda$r6LgDiay3Ow5w51ifJiV4dn8S84;
import java.util.stream._$$Lambda$zcFI7bYCRDtB1UMy72aPExbc6R4;

final class DistinctOps {
    private DistinctOps() {
    }

    static <T> ReferencePipeline<T, T> makeRef(AbstractPipeline<?, T, ?> abstractPipeline) {
        return new ReferencePipeline.StatefulOp<T, T>(abstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_DISTINCT | StreamOpFlag.NOT_SIZED){

            static /* synthetic */ void lambda$opEvaluateParallel$0(AtomicBoolean atomicBoolean, ConcurrentHashMap concurrentHashMap, Object object) {
                if (object == null) {
                    atomicBoolean.set(true);
                } else {
                    concurrentHashMap.putIfAbsent(object, Boolean.TRUE);
                }
            }

            @Override
            public <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> hashSet, Spliterator<P_IN> object, IntFunction<T[]> object2) {
                if (StreamOpFlag.DISTINCT.isKnown(((PipelineHelper)((Object)hashSet)).getStreamAndOpFlags())) {
                    return ((PipelineHelper)((Object)hashSet)).evaluate(object, false, (IntFunction<P_OUT[]>)object2);
                }
                if (StreamOpFlag.ORDERED.isKnown(((PipelineHelper)((Object)hashSet)).getStreamAndOpFlags())) {
                    return this.reduce((PipelineHelper<T>)((Object)hashSet), (Spliterator<P_IN>)object);
                }
                object2 = new AtomicBoolean(false);
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                ForEachOps.makeRef(new _$$Lambda$DistinctOps$1$y8chmSlaKpIKb_VPPBaXdCNT51c((AtomicBoolean)object2, concurrentHashMap), false).evaluateParallel(hashSet, object);
                object = concurrentHashMap.keySet();
                hashSet = object;
                if (((AtomicBoolean)object2).get()) {
                    hashSet = new HashSet<Object>((Collection<Object>)object);
                    hashSet.add(null);
                }
                return Nodes.node(hashSet);
            }

            @Override
            public <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator) {
                if (StreamOpFlag.DISTINCT.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                    return pipelineHelper.wrapSpliterator(spliterator);
                }
                if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                    return this.reduce(pipelineHelper, spliterator).spliterator();
                }
                return new StreamSpliterators.DistinctSpliterator<T>(pipelineHelper.wrapSpliterator(spliterator));
            }

            @Override
            public Sink<T> opWrapSink(int n, Sink<T> sink) {
                Objects.requireNonNull(sink);
                if (StreamOpFlag.DISTINCT.isKnown(n)) {
                    return sink;
                }
                if (StreamOpFlag.SORTED.isKnown(n)) {
                    return new Sink.ChainedReference<T, T>(sink){
                        T lastSeen;
                        boolean seenNull;

                        @Override
                        public void accept(T object) {
                            if (object == null) {
                                if (!this.seenNull) {
                                    this.seenNull = true;
                                    object = this.downstream;
                                    this.lastSeen = null;
                                    object.accept(null);
                                }
                            } else {
                                Object object2 = this.lastSeen;
                                if (object2 == null || !object.equals(object2)) {
                                    object2 = this.downstream;
                                    this.lastSeen = object;
                                    object2.accept(object);
                                }
                            }
                        }

                        @Override
                        public void begin(long l) {
                            this.seenNull = false;
                            this.lastSeen = null;
                            this.downstream.begin(-1L);
                        }

                        @Override
                        public void end() {
                            this.seenNull = false;
                            this.lastSeen = null;
                            this.downstream.end();
                        }
                    };
                }
                return new Sink.ChainedReference<T, T>(sink){
                    Set<T> seen;

                    @Override
                    public void accept(T t) {
                        if (!this.seen.contains(t)) {
                            this.seen.add(t);
                            this.downstream.accept(t);
                        }
                    }

                    @Override
                    public void begin(long l) {
                        this.seen = new HashSet<T>();
                        this.downstream.begin(-1L);
                    }

                    @Override
                    public void end() {
                        this.seen = null;
                        this.downstream.end();
                    }
                };
            }

            @Override
            <P_IN> Node<T> reduce(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator) {
                return Nodes.node((Collection)ReduceOps.makeRef(_$$Lambda$VQnU3Jki1_RCSS5B_Yg_Kf6hQAY.INSTANCE, _$$Lambda$zcFI7bYCRDtB1UMy72aPExbc6R4.INSTANCE, _$$Lambda$r6LgDiay3Ow5w51ifJiV4dn8S84.INSTANCE).evaluateParallel(pipelineHelper, spliterator));
            }

        };
    }

}

