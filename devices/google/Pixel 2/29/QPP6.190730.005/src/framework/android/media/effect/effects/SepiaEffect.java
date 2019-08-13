/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.SepiaFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class SepiaEffect
extends SingleFilterEffect {
    public SepiaEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, SepiaFilter.class, "image", "image", new Object[0]);
    }
}

