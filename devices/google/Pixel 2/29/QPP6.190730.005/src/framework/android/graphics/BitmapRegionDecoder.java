/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BitmapRegionDecoder {
    private long mNativeBitmapRegionDecoder;
    private final Object mNativeLock = new Object();
    private boolean mRecycled;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private BitmapRegionDecoder(long l) {
        this.mNativeBitmapRegionDecoder = l;
        this.mRecycled = false;
    }

    private void checkRecycled(String string2) {
        if (!this.mRecycled) {
            return;
        }
        throw new IllegalStateException(string2);
    }

    private static native void nativeClean(long var0);

    private static native Bitmap nativeDecodeRegion(long var0, int var2, int var3, int var4, int var5, BitmapFactory.Options var6, long var7, long var9);

    private static native int nativeGetHeight(long var0);

    private static native int nativeGetWidth(long var0);

    private static native BitmapRegionDecoder nativeNewInstance(long var0, boolean var2);

    private static native BitmapRegionDecoder nativeNewInstance(FileDescriptor var0, boolean var1);

    private static native BitmapRegionDecoder nativeNewInstance(InputStream var0, byte[] var1, boolean var2);

    @UnsupportedAppUsage
    private static native BitmapRegionDecoder nativeNewInstance(byte[] var0, int var1, int var2, boolean var3);

    public static BitmapRegionDecoder newInstance(FileDescriptor fileDescriptor, boolean bl) throws IOException {
        return BitmapRegionDecoder.nativeNewInstance(fileDescriptor, bl);
    }

    public static BitmapRegionDecoder newInstance(InputStream inputStream, boolean bl) throws IOException {
        if (inputStream instanceof AssetManager.AssetInputStream) {
            return BitmapRegionDecoder.nativeNewInstance(((AssetManager.AssetInputStream)inputStream).getNativeAsset(), bl);
        }
        return BitmapRegionDecoder.nativeNewInstance(inputStream, new byte[16384], bl);
    }

    public static BitmapRegionDecoder newInstance(String object, boolean bl) throws IOException {
        Object object2;
        InputStream inputStream = null;
        Object object3 = inputStream;
        object3 = inputStream;
        try {
            object3 = object = (object2 = new FileInputStream((String)object));
        }
        catch (Throwable throwable) {
            if (object3 != null) {
                try {
                    ((InputStream)object3).close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            throw throwable;
        }
        object2 = BitmapRegionDecoder.newInstance((InputStream)object, bl);
        try {
            ((InputStream)object).close();
        }
        catch (IOException iOException) {}
        return object2;
    }

    public static BitmapRegionDecoder newInstance(byte[] arrby, int n, int n2, boolean bl) throws IOException {
        if ((n | n2) >= 0 && arrby.length >= n + n2) {
            return BitmapRegionDecoder.nativeNewInstance(arrby, n, n2, bl);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap decodeRegion(Rect object, BitmapFactory.Options options) {
        BitmapFactory.Options.validate(options);
        Object object2 = this.mNativeLock;
        synchronized (object2) {
            this.checkRecycled("decodeRegion called on recycled region decoder");
            if (((Rect)object).right > 0 && ((Rect)object).bottom > 0 && ((Rect)object).left < this.getWidth() && ((Rect)object).top < this.getHeight()) {
                return BitmapRegionDecoder.nativeDecodeRegion(this.mNativeBitmapRegionDecoder, ((Rect)object).left, ((Rect)object).top, ((Rect)object).right - ((Rect)object).left, ((Rect)object).bottom - ((Rect)object).top, options, BitmapFactory.Options.nativeInBitmap(options), BitmapFactory.Options.nativeColorSpace(options));
            }
            object = new IllegalArgumentException("rectangle is outside the image");
            throw object;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.recycle();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHeight() {
        Object object = this.mNativeLock;
        synchronized (object) {
            this.checkRecycled("getHeight called on recycled region decoder");
            return BitmapRegionDecoder.nativeGetHeight(this.mNativeBitmapRegionDecoder);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getWidth() {
        Object object = this.mNativeLock;
        synchronized (object) {
            this.checkRecycled("getWidth called on recycled region decoder");
            return BitmapRegionDecoder.nativeGetWidth(this.mNativeBitmapRegionDecoder);
        }
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recycle() {
        Object object = this.mNativeLock;
        synchronized (object) {
            if (!this.mRecycled) {
                BitmapRegionDecoder.nativeClean(this.mNativeBitmapRegionDecoder);
                this.mRecycled = true;
            }
            return;
        }
    }
}

