/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.DuotoneFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class DuotoneEffect
extends SingleFilterEffect {
    public DuotoneEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, DuotoneFilter.class, "image", "image", new Object[0]);
    }
}

