/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Xfermode;

@Deprecated
public class AvoidXfermode
extends Xfermode {
    public AvoidXfermode(int n, int n2, Mode mode) {
        if (n2 >= 0 && n2 <= 255) {
            return;
        }
        throw new IllegalArgumentException("tolerance must be 0..255");
    }

    public static enum Mode {
        AVOID(0),
        TARGET(1);
        
        final int nativeInt;

        private Mode(int n2) {
            this.nativeInt = n2;
        }
    }

}

