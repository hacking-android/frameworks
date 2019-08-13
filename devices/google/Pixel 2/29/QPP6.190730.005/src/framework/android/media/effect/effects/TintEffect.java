/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.TintFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class TintEffect
extends SingleFilterEffect {
    public TintEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, TintFilter.class, "image", "image", new Object[0]);
    }
}

