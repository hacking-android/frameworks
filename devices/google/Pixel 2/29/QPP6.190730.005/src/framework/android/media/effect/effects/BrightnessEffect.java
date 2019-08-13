/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.BrightnessFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class BrightnessEffect
extends SingleFilterEffect {
    public BrightnessEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, BrightnessFilter.class, "image", "image", new Object[0]);
    }
}

