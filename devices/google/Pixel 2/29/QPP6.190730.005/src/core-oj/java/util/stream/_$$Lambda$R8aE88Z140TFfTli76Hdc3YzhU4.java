/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$R8aE88Z140TFfTli76Hdc3YzhU4
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$R8aE88Z140TFfTli76Hdc3YzhU4
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.R8aE88Z140TFfTli76Hdc3YzhU4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$R8aE88Z140TFfTli76Hdc3YzhU4();
    }

    private /* synthetic */ _$$Lambda$R8aE88Z140TFfTli76Hdc3YzhU4() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return Long.sum((Long)object, (Long)object2);
    }
}

