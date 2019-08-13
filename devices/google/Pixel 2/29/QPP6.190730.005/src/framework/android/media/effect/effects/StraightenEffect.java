/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.StraightenFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class StraightenEffect
extends SingleFilterEffect {
    public StraightenEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, StraightenFilter.class, "image", "image", new Object[0]);
    }
}

