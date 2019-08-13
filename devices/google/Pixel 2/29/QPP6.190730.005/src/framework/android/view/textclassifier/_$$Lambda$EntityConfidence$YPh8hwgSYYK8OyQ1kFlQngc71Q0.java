/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.view.textclassifier.EntityConfidence;
import java.util.Comparator;

public final class _$$Lambda$EntityConfidence$YPh8hwgSYYK8OyQ1kFlQngc71Q0
implements Comparator {
    private final /* synthetic */ EntityConfidence f$0;

    public /* synthetic */ _$$Lambda$EntityConfidence$YPh8hwgSYYK8OyQ1kFlQngc71Q0(EntityConfidence entityConfidence) {
        this.f$0 = entityConfidence;
    }

    public final int compare(Object object, Object object2) {
        return this.f$0.lambda$resetSortedEntitiesFromMap$0$EntityConfidence((String)object, (String)object2);
    }
}

