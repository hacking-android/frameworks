/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.content.res.ConstantState;
import android.content.res.Resources;

public abstract class ComplexColor {
    private int mChangingConfigurations;

    public abstract boolean canApplyTheme();

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public abstract ConstantState<ComplexColor> getConstantState();

    public abstract int getDefaultColor();

    public boolean isStateful() {
        return false;
    }

    public abstract ComplexColor obtainForTheme(Resources.Theme var1);

    final void setBaseChangingConfigurations(int n) {
        this.mChangingConfigurations = n;
    }
}

