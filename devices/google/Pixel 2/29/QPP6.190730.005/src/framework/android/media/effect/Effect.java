/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.media.effect.EffectUpdateListener;

public abstract class Effect {
    public abstract void apply(int var1, int var2, int var3, int var4);

    public abstract String getName();

    public abstract void release();

    public abstract void setParameter(String var1, Object var2);

    public void setUpdateListener(EffectUpdateListener effectUpdateListener) {
    }
}

