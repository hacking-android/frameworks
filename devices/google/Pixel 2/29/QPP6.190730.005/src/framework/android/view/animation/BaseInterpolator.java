/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.view.animation.Interpolator;

public abstract class BaseInterpolator
implements Interpolator {
    private int mChangingConfiguration;

    public int getChangingConfiguration() {
        return this.mChangingConfiguration;
    }

    void setChangingConfiguration(int n) {
        this.mChangingConfiguration = n;
    }
}

