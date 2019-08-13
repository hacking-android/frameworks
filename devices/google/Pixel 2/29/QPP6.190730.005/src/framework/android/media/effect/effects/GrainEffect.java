/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.GrainFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class GrainEffect
extends SingleFilterEffect {
    public GrainEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, GrainFilter.class, "image", "image", new Object[0]);
    }
}

