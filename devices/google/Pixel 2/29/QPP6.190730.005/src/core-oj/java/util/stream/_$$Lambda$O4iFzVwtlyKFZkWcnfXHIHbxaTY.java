/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Node;
import java.util.stream.Nodes;

public final class _$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.O4iFzVwtlyKFZkWcnfXHIHbxaTY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY();
    }

    private /* synthetic */ _$$Lambda$O4iFzVwtlyKFZkWcnfXHIHbxaTY() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return new Nodes.ConcNode.OfInt((Node.OfInt)object, (Node.OfInt)object2);
    }
}

