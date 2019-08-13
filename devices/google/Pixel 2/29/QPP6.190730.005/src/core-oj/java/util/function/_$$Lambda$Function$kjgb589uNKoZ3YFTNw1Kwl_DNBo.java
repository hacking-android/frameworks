/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.Function;

public final class _$$Lambda$Function$kjgb589uNKoZ3YFTNw1Kwl_DNBo
implements Function {
    private final /* synthetic */ Function f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$Function$kjgb589uNKoZ3YFTNw1Kwl_DNBo(Function function, Function function2) {
        this.f$0 = function;
        this.f$1 = function2;
    }

    public final Object apply(Object object) {
        return Function.lambda$compose$0(this.f$0, this.f$1, object);
    }
}

