/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.HidlSupport;
import java.util.Iterator;
import java.util.function.Predicate;

public final class _$$Lambda$HidlSupport$oV2DlGQSAfcavBj7TK20nYhwS0U
implements Predicate {
    private final /* synthetic */ Iterator f$0;

    public /* synthetic */ _$$Lambda$HidlSupport$oV2DlGQSAfcavBj7TK20nYhwS0U(Iterator iterator) {
        this.f$0 = iterator;
    }

    public final boolean test(Object object) {
        return HidlSupport.lambda$deepEquals$1(this.f$0, object);
    }
}

