/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.CropRectFilter;
import android.media.effect.EffectContext;
import android.media.effect.SizeChangeEffect;

public class CropEffect
extends SizeChangeEffect {
    public CropEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, CropRectFilter.class, "image", "image", new Object[0]);
    }
}

