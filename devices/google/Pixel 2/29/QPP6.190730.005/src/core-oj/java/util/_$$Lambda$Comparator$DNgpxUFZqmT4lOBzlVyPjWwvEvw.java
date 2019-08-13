/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public final class _$$Lambda$Comparator$DNgpxUFZqmT4lOBzlVyPjWwvEvw
implements Comparator,
Serializable {
    private final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ _$$Lambda$Comparator$DNgpxUFZqmT4lOBzlVyPjWwvEvw(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$comparingInt$7b0bb60$1(this.f$0, object, object2);
    }
}

