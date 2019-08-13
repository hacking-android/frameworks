/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics.palette;

import com.android.internal.graphics.palette.Palette;
import java.util.List;

public interface Quantizer {
    public List<Palette.Swatch> getQuantizedColors();

    public void quantize(int[] var1, int var2, Palette.Filter[] var3);
}

