/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.MaskFilter;

public class TableMaskFilter
extends MaskFilter {
    private TableMaskFilter(long l) {
        this.native_instance = l;
    }

    public TableMaskFilter(byte[] arrby) {
        if (arrby.length >= 256) {
            this.native_instance = TableMaskFilter.nativeNewTable(arrby);
            return;
        }
        throw new RuntimeException("table.length must be >= 256");
    }

    @UnsupportedAppUsage
    public static TableMaskFilter CreateClipTable(int n, int n2) {
        return new TableMaskFilter(TableMaskFilter.nativeNewClip(n, n2));
    }

    public static TableMaskFilter CreateGammaTable(float f) {
        return new TableMaskFilter(TableMaskFilter.nativeNewGamma(f));
    }

    private static native long nativeNewClip(int var0, int var1);

    private static native long nativeNewGamma(float var0);

    private static native long nativeNewTable(byte[] var0);
}

