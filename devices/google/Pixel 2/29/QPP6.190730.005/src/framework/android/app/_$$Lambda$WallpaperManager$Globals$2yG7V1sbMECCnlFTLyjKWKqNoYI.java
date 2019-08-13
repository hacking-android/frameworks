/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.WallpaperManager;
import android.util.Pair;
import java.util.function.Predicate;

public final class _$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI
implements Predicate {
    private final /* synthetic */ WallpaperManager.OnColorsChangedListener f$0;

    public /* synthetic */ _$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI(WallpaperManager.OnColorsChangedListener onColorsChangedListener) {
        this.f$0 = onColorsChangedListener;
    }

    public final boolean test(Object object) {
        return WallpaperManager.Globals.lambda$removeOnColorsChangedListener$0(this.f$0, (Pair)object);
    }
}

