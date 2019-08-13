/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$Mo9-ryI3XUGyoHfpnRL3BoFhaqY
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.-$;
import java.util.stream.Node;
import java.util.stream.Nodes;

public final class _$$Lambda$Mo9_ryI3XUGyoHfpnRL3BoFhaqY
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.Mo9-ryI3XUGyoHfpnRL3BoFhaqY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Mo9_ryI3XUGyoHfpnRL3BoFhaqY();
    }

    private /* synthetic */ _$$Lambda$Mo9_ryI3XUGyoHfpnRL3BoFhaqY() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return new Nodes.ConcNode((Node)object, (Node)object2);
    }
}

