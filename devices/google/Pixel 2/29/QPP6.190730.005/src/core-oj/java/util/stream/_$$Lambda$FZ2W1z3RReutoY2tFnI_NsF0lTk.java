/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk
 */
package java.util.stream;

import java.util.function.IntBinaryOperator;
import java.util.stream.-$;

public final class _$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk
implements IntBinaryOperator {
    public static final /* synthetic */ -$.Lambda.FZ2W1z3RReutoY2tFnI_NsF0lTk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk();
    }

    private /* synthetic */ _$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk() {
    }

    @Override
    public final int applyAsInt(int n, int n2) {
        return Math.min(n, n2);
    }
}

