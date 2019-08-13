/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.ToGrayFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class GrayscaleEffect
extends SingleFilterEffect {
    public GrayscaleEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, ToGrayFilter.class, "image", "image", new Object[0]);
    }
}

