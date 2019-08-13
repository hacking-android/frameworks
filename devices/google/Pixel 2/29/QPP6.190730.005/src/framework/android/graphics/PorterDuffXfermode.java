/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.PorterDuff;
import android.graphics.Xfermode;

public class PorterDuffXfermode
extends Xfermode {
    public PorterDuffXfermode(PorterDuff.Mode mode) {
        this.porterDuffMode = mode.nativeInt;
    }
}

