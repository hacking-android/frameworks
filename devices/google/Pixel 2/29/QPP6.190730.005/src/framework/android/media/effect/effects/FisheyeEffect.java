/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.FisheyeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class FisheyeEffect
extends SingleFilterEffect {
    public FisheyeEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, FisheyeFilter.class, "image", "image", new Object[0]);
    }
}

