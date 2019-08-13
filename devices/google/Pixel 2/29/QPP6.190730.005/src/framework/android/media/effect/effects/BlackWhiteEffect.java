/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.BlackWhiteFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class BlackWhiteEffect
extends SingleFilterEffect {
    public BlackWhiteEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, BlackWhiteFilter.class, "image", "image", new Object[0]);
    }
}

