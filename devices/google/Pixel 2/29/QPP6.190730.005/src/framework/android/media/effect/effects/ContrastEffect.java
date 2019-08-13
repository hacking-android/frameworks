/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.ContrastFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class ContrastEffect
extends SingleFilterEffect {
    public ContrastEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, ContrastFilter.class, "image", "image", new Object[0]);
    }
}

