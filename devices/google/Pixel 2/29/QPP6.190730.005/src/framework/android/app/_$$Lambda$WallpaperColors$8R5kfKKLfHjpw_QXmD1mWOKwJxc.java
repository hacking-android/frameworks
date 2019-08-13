/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.WallpaperColors;
import com.android.internal.graphics.palette.Palette;
import java.util.function.Predicate;

public final class _$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc
implements Predicate {
    private final /* synthetic */ float f$0;

    public /* synthetic */ _$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc(float f) {
        this.f$0 = f;
    }

    public final boolean test(Object object) {
        return WallpaperColors.lambda$fromBitmap$0(this.f$0, (Palette.Swatch)object);
    }
}

