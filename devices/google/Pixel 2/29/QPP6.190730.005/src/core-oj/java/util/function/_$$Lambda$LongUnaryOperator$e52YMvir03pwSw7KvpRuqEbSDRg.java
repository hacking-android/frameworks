/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongUnaryOperator;

public final class _$$Lambda$LongUnaryOperator$e52YMvir03pwSw7KvpRuqEbSDRg
implements LongUnaryOperator {
    private final /* synthetic */ LongUnaryOperator f$0;
    private final /* synthetic */ LongUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$LongUnaryOperator$e52YMvir03pwSw7KvpRuqEbSDRg(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        this.f$0 = longUnaryOperator;
        this.f$1 = longUnaryOperator2;
    }

    @Override
    public final long applyAsLong(long l) {
        return LongUnaryOperator.lambda$compose$0(this.f$0, this.f$1, l);
    }
}

