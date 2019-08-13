/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.VignetteFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class VignetteEffect
extends SingleFilterEffect {
    public VignetteEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, VignetteFilter.class, "image", "image", new Object[0]);
    }
}

