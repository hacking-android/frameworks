/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.RotateFilter;
import android.media.effect.EffectContext;
import android.media.effect.SizeChangeEffect;

public class RotateEffect
extends SizeChangeEffect {
    public RotateEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, RotateFilter.class, "image", "image", new Object[0]);
    }
}

