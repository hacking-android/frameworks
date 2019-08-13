/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.SaturateFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class SaturateEffect
extends SingleFilterEffect {
    public SaturateEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, SaturateFilter.class, "image", "image", new Object[0]);
    }
}

