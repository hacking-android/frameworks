/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public final class LargeBitmap {
    private long mNativeLargeBitmap;
    private boolean mRecycled;

    private LargeBitmap(long l) {
        this.mNativeLargeBitmap = l;
        this.mRecycled = false;
    }

    private void checkRecycled(String string2) {
        if (!this.mRecycled) {
            return;
        }
        throw new IllegalStateException(string2);
    }

    private static native void nativeClean(long var0);

    private static native Bitmap nativeDecodeRegion(long var0, int var2, int var3, int var4, int var5, BitmapFactory.Options var6);

    private static native int nativeGetHeight(long var0);

    private static native int nativeGetWidth(long var0);

    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        this.checkRecycled("decodeRegion called on recycled large bitmap");
        if (rect.left >= 0 && rect.top >= 0 && rect.right <= this.getWidth() && rect.bottom <= this.getHeight()) {
            return LargeBitmap.nativeDecodeRegion(this.mNativeLargeBitmap, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, options);
        }
        throw new IllegalArgumentException("rectangle is not inside the image");
    }

    protected void finalize() {
        this.recycle();
    }

    public int getHeight() {
        this.checkRecycled("getHeight called on recycled large bitmap");
        return LargeBitmap.nativeGetHeight(this.mNativeLargeBitmap);
    }

    public int getWidth() {
        this.checkRecycled("getWidth called on recycled large bitmap");
        return LargeBitmap.nativeGetWidth(this.mNativeLargeBitmap);
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    public void recycle() {
        if (!this.mRecycled) {
            LargeBitmap.nativeClean(this.mNativeLargeBitmap);
            this.mRecycled = true;
        }
    }
}

