/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.stream.PipelineHelper;
import java.util.stream.StreamShape;
import java.util.stream.Tripwire;

interface TerminalOp<E_IN, R> {
    default public <P_IN> R evaluateParallel(PipelineHelper<E_IN> pipelineHelper, Spliterator<P_IN> spliterator) {
        if (Tripwire.ENABLED) {
            Tripwire.trip(this.getClass(), "{0} triggering TerminalOp.evaluateParallel serial default");
        }
        return this.evaluateSequential(pipelineHelper, spliterator);
    }

    public <P_IN> R evaluateSequential(PipelineHelper<E_IN> var1, Spliterator<P_IN> var2);

    default public int getOpFlags() {
        return 0;
    }

    default public StreamShape inputShape() {
        return StreamShape.REFERENCE;
    }
}

