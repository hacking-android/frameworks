/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.PosterizeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class PosterizeEffect
extends SingleFilterEffect {
    public PosterizeEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, PosterizeFilter.class, "image", "image", new Object[0]);
    }
}

