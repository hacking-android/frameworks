/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ThemedResourceCache;
import android.graphics.drawable.Drawable;

class DrawableCache
extends ThemedResourceCache<Drawable.ConstantState> {
    DrawableCache() {
    }

    @UnsupportedAppUsage
    public Drawable getInstance(long l, Resources resources, Resources.Theme theme) {
        Drawable.ConstantState constantState = (Drawable.ConstantState)this.get(l, theme);
        if (constantState != null) {
            return constantState.newDrawable(resources, theme);
        }
        return null;
    }

    @Override
    public boolean shouldInvalidateEntry(Drawable.ConstantState constantState, int n) {
        return Configuration.needNewResources(n, constantState.getChangingConfigurations());
    }
}

