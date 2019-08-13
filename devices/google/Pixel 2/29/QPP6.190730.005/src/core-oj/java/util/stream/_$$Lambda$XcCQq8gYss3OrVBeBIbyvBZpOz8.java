/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8
 */
package java.util.stream;

import java.util.OptionalLong;
import java.util.function.Predicate;
import java.util.stream.-$;

public final class _$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8
implements Predicate {
    public static final /* synthetic */ -$.Lambda.XcCQq8gYss3OrVBeBIbyvBZpOz8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8();
    }

    private /* synthetic */ _$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8() {
    }

    public final boolean test(Object object) {
        return ((OptionalLong)object).isPresent();
    }
}

