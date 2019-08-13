/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.ColorTemperatureFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class ColorTemperatureEffect
extends SingleFilterEffect {
    public ColorTemperatureEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, ColorTemperatureFilter.class, "image", "image", new Object[0]);
    }
}

