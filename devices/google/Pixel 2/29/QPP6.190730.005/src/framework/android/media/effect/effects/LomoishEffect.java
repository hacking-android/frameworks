/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.LomoishFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class LomoishEffect
extends SingleFilterEffect {
    public LomoishEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, LomoishFilter.class, "image", "image", new Object[0]);
    }
}

