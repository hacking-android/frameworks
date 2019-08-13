/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.NegativeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class NegativeEffect
extends SingleFilterEffect {
    public NegativeEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, NegativeFilter.class, "image", "image", new Object[0]);
    }
}

