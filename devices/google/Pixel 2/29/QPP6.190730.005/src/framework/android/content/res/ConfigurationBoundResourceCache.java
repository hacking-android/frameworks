/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Configuration;
import android.content.res.ConstantState;
import android.content.res.Resources;
import android.content.res.ThemedResourceCache;

public class ConfigurationBoundResourceCache<T>
extends ThemedResourceCache<ConstantState<T>> {
    public T getInstance(long l, Resources resources, Resources.Theme theme) {
        ConstantState constantState = (ConstantState)this.get(l, theme);
        if (constantState != null) {
            return constantState.newInstance(resources, theme);
        }
        return null;
    }

    @Override
    public boolean shouldInvalidateEntry(ConstantState<T> constantState, int n) {
        return Configuration.needNewResources(n, constantState.getChangingConfigurations());
    }
}

