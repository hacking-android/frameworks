/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$i0Jl5dMkfWphZviqg6QdkkWPWRI
 */
package java.util.stream;

import java.util.StringJoiner;
import java.util.function.BinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$i0Jl5dMkfWphZviqg6QdkkWPWRI
implements BinaryOperator {
    public static final /* synthetic */ -$.Lambda.i0Jl5dMkfWphZviqg6QdkkWPWRI INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$i0Jl5dMkfWphZviqg6QdkkWPWRI();
    }

    private /* synthetic */ _$$Lambda$i0Jl5dMkfWphZviqg6QdkkWPWRI() {
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return ((StringJoiner)object).merge((StringJoiner)object2);
    }
}

