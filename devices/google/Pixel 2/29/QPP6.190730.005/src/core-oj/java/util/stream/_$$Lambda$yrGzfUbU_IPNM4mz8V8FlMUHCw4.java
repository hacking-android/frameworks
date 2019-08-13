/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4
 */
package java.util.stream;

import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.stream.-$;

public final class _$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4
implements Predicate {
    public static final /* synthetic */ -$.Lambda.yrGzfUbU_IPNM4mz8V8FlMUHCw4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4();
    }

    private /* synthetic */ _$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4() {
    }

    public final boolean test(Object object) {
        return ((OptionalDouble)object).isPresent();
    }
}

