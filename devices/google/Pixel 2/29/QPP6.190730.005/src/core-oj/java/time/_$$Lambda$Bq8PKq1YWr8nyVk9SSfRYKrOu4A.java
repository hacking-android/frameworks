/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A
 */
package java.time;

import java.time.-$;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.Bq8PKq1YWr8nyVk9SSfRYKrOu4A INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A();
    }

    private /* synthetic */ _$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return LocalDate.from(temporalAccessor);
    }
}

