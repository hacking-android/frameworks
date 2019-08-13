/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.Shader;

public class BitmapShader
extends Shader {
    @UnsupportedAppUsage
    public Bitmap mBitmap;
    @UnsupportedAppUsage
    private int mTileX;
    @UnsupportedAppUsage
    private int mTileY;

    private BitmapShader(Bitmap bitmap, int n, int n2) {
        if (bitmap != null) {
            if (bitmap == this.mBitmap && n == this.mTileX && n2 == this.mTileY) {
                return;
            }
            this.mBitmap = bitmap;
            this.mTileX = n;
            this.mTileY = n2;
            return;
        }
        throw new IllegalArgumentException("Bitmap must be non-null");
    }

    public BitmapShader(Bitmap bitmap, Shader.TileMode tileMode, Shader.TileMode tileMode2) {
        this(bitmap, tileMode.nativeInt, tileMode2.nativeInt);
    }

    private static native long nativeCreate(long var0, long var2, int var4, int var5);

    @Override
    long createNativeInstance(long l) {
        return BitmapShader.nativeCreate(l, this.mBitmap.getNativeInstance(), this.mTileX, this.mTileY);
    }
}

