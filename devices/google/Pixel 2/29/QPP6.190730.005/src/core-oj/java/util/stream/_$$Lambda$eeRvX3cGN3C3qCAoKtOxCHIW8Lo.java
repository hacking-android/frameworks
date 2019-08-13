/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Node;
import java.util.stream.Nodes;

public final class _$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.eeRvX3cGN3C3qCAoKtOxCHIW8Lo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo();
    }

    private /* synthetic */ _$$Lambda$eeRvX3cGN3C3qCAoKtOxCHIW8Lo() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return new Nodes.ConcNode.OfLong((Node.OfLong)object, (Node.OfLong)object2);
    }
}

