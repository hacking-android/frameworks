/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.BitmapOverlayFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class BitmapOverlayEffect
extends SingleFilterEffect {
    public BitmapOverlayEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, BitmapOverlayFilter.class, "image", "image", new Object[0]);
    }
}

