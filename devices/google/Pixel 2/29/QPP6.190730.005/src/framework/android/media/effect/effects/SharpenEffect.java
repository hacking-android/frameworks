/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.SharpenFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class SharpenEffect
extends SingleFilterEffect {
    public SharpenEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, SharpenFilter.class, "image", "image", new Object[0]);
    }
}

