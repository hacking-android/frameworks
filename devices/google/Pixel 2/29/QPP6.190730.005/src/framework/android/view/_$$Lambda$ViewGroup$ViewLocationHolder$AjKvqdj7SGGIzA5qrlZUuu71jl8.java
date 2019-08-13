/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.function.Predicate;

public final class _$$Lambda$ViewGroup$ViewLocationHolder$AjKvqdj7SGGIzA5qrlZUuu71jl8
implements Predicate {
    private final /* synthetic */ Rect f$0;
    private final /* synthetic */ Rect f$1;

    public /* synthetic */ _$$Lambda$ViewGroup$ViewLocationHolder$AjKvqdj7SGGIzA5qrlZUuu71jl8(Rect rect, Rect rect2) {
        this.f$0 = rect;
        this.f$1 = rect2;
    }

    public final boolean test(Object object) {
        return ViewGroup.ViewLocationHolder.lambda$compareBoundsOfTree$1(this.f$0, this.f$1, (View)object);
    }
}

