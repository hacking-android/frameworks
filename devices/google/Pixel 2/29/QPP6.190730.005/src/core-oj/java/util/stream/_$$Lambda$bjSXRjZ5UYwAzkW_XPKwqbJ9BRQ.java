/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$bjSXRjZ5UYwAzkW-XPKwqbJ9BRQ
 */
package java.util.stream;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.-$;

public final class _$$Lambda$bjSXRjZ5UYwAzkW_XPKwqbJ9BRQ
implements Predicate {
    public static final /* synthetic */ -$.Lambda.bjSXRjZ5UYwAzkW-XPKwqbJ9BRQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$bjSXRjZ5UYwAzkW_XPKwqbJ9BRQ();
    }

    private /* synthetic */ _$$Lambda$bjSXRjZ5UYwAzkW_XPKwqbJ9BRQ() {
    }

    public final boolean test(Object object) {
        return ((Optional)object).isPresent();
    }
}

