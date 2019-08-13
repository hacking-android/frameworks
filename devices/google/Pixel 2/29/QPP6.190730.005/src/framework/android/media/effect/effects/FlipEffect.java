/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.FlipFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class FlipEffect
extends SingleFilterEffect {
    public FlipEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, FlipFilter.class, "image", "image", new Object[0]);
    }
}

