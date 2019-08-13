/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Node;
import java.util.stream.Nodes;

public final class _$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.KTexUmxMdHIv08L4oU8j9HXK_go INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go();
    }

    private /* synthetic */ _$$Lambda$KTexUmxMdHIv08L4oU8j9HXK_go() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return new Nodes.ConcNode.OfDouble((Node.OfDouble)object, (Node.OfDouble)object2);
    }
}

