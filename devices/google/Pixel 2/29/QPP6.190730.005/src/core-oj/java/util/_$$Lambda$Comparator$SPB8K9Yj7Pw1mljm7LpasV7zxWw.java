/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;

public final class _$$Lambda$Comparator$SPB8K9Yj7Pw1mljm7LpasV7zxWw
implements Comparator,
Serializable {
    private final /* synthetic */ Function f$0;

    public /* synthetic */ _$$Lambda$Comparator$SPB8K9Yj7Pw1mljm7LpasV7zxWw(Function function) {
        this.f$0 = function;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$comparing$77a9974f$1(this.f$0, object, object2);
    }
}

