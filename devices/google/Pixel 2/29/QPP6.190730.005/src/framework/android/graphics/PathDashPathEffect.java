/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Path;
import android.graphics.PathEffect;

public class PathDashPathEffect
extends PathEffect {
    public PathDashPathEffect(Path path, float f, float f2, Style style2) {
        this.native_instance = PathDashPathEffect.nativeCreate(path.readOnlyNI(), f, f2, style2.native_style);
    }

    private static native long nativeCreate(long var0, float var2, float var3, int var4);

    public static enum Style {
        TRANSLATE(0),
        ROTATE(1),
        MORPH(2);
        
        int native_style;

        private Style(int n2) {
            this.native_style = n2;
        }
    }

}

