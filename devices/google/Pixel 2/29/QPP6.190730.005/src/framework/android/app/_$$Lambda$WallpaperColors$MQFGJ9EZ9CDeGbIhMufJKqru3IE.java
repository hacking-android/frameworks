/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$WallpaperColors
 *  android.app.-$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE
 */
package android.app;

import android.app.-$;
import android.app.WallpaperColors;
import com.android.internal.graphics.palette.Palette;
import java.util.Comparator;

public final class _$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE
implements Comparator {
    public static final /* synthetic */ -$.Lambda.WallpaperColors.MQFGJ9EZ9CDeGbIhMufJKqru3IE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE();
    }

    private /* synthetic */ _$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE() {
    }

    public final int compare(Object object, Object object2) {
        return WallpaperColors.lambda$fromBitmap$1((Palette.Swatch)object, (Palette.Swatch)object2);
    }
}

