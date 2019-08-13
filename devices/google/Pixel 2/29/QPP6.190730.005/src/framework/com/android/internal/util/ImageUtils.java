/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ContentInterface;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.util.Size;
import com.android.internal.util._$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY;
import com.android.internal.util._$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ImageUtils {
    private static final int ALPHA_TOLERANCE = 50;
    private static final int COMPACT_BITMAP_SIZE = 64;
    private static final int TOLERANCE = 20;
    private int[] mTempBuffer;
    private Bitmap mTempCompactBitmap;
    private Canvas mTempCompactBitmapCanvas;
    private Paint mTempCompactBitmapPaint;
    private final Matrix mTempMatrix = new Matrix();

    public static Bitmap buildScaledBitmap(Drawable drawable2, int n, int n2) {
        if (drawable2 == null) {
            return null;
        }
        int n3 = drawable2.getIntrinsicWidth();
        int n4 = drawable2.getIntrinsicHeight();
        if (n3 <= n && n4 <= n2 && drawable2 instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable2).getBitmap();
        }
        if (n4 > 0 && n3 > 0) {
            float f = Math.min(1.0f, Math.min((float)n / (float)n3, (float)n2 / (float)n4));
            n = (int)((float)n3 * f);
            n2 = (int)((float)n4 * f);
            Bitmap bitmap = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable2.setBounds(0, 0, n, n2);
            drawable2.draw(canvas);
            return bitmap;
        }
        return null;
    }

    public static int calculateSampleSize(Size size, Size size2) {
        int n = 1;
        int n2 = 1;
        if (size.getHeight() > size2.getHeight() || size.getWidth() > size2.getWidth()) {
            int n3 = size.getHeight() / 2;
            int n4 = size.getWidth() / 2;
            do {
                n = n2;
                if (n3 / n2 < size2.getHeight()) break;
                n = n2;
                if (n4 / n2 < size2.getWidth()) break;
                n2 *= 2;
            } while (true);
        }
        return n;
    }

    private void ensureBufferSize(int n) {
        int[] arrn = this.mTempBuffer;
        if (arrn == null || arrn.length < n) {
            this.mTempBuffer = new int[n];
        }
    }

    public static boolean isGrayscale(int n) {
        boolean bl = true;
        if ((n >> 24 & 255) < 50) {
            return true;
        }
        int n2 = n >> 16 & 255;
        int n3 = n >> 8 & 255;
        if (Math.abs(n2 - n3) >= 20 || Math.abs(n2 - (n &= 255)) >= 20 || Math.abs(n3 - n) >= 20) {
            bl = false;
        }
        return bl;
    }

    static /* synthetic */ AssetFileDescriptor lambda$loadThumbnail$0(ContentProviderClient contentProviderClient, Uri uri, Bundle bundle) throws Exception {
        return contentProviderClient.openTypedAssetFile(uri, "image/*", bundle, null);
    }

    static /* synthetic */ void lambda$loadThumbnail$1(Size size, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        int n = ImageUtils.calculateSampleSize(imageInfo.getSize(), size);
        if (n > 1) {
            imageDecoder.setTargetSampleSize(n);
        }
    }

    public static Bitmap loadThumbnail(ContentResolver contentInterface, Uri object, Size size) throws IOException {
        contentInterface = ((ContentResolver)contentInterface).acquireContentProviderClient((Uri)object);
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.content.extra.SIZE", Point.convert(size));
            Object object2 = new _$$Lambda$ImageUtils$UJyN8OeHYbkY_xJzm1U3D7W4PNY((ContentProviderClient)contentInterface, (Uri)object, bundle);
            object = ImageDecoder.createSource((Callable<AssetFileDescriptor>)object2);
            object2 = new _$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE(size);
            object = ImageDecoder.decodeBitmap((ImageDecoder.Source)object, (ImageDecoder.OnHeaderDecodedListener)object2);
            if (contentInterface != null) {
                ((ContentProviderClient)contentInterface).close();
            }
            return object;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (contentInterface != null) {
                    try {
                        ((ContentProviderClient)contentInterface).close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                }
                throw throwable2;
            }
        }
    }

    public boolean isGrayscale(Bitmap bitmap) {
        int n;
        int n2;
        Bitmap bitmap2;
        int n3;
        block6 : {
            int n4;
            block5 : {
                n4 = bitmap.getHeight();
                n2 = bitmap.getWidth();
                if (n4 > 64) break block5;
                n3 = n4;
                n = n2;
                bitmap2 = bitmap;
                if (n2 <= 64) break block6;
            }
            if (this.mTempCompactBitmap == null) {
                this.mTempCompactBitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888);
                this.mTempCompactBitmapCanvas = new Canvas(this.mTempCompactBitmap);
                this.mTempCompactBitmapPaint = new Paint(1);
                this.mTempCompactBitmapPaint.setFilterBitmap(true);
            }
            this.mTempMatrix.reset();
            this.mTempMatrix.setScale(64.0f / (float)n2, 64.0f / (float)n4, 0.0f, 0.0f);
            this.mTempCompactBitmapCanvas.drawColor(0, PorterDuff.Mode.SRC);
            this.mTempCompactBitmapCanvas.drawBitmap(bitmap, this.mTempMatrix, this.mTempCompactBitmapPaint);
            bitmap2 = this.mTempCompactBitmap;
            n3 = 64;
            n = 64;
        }
        n2 = n3 * n;
        this.ensureBufferSize(n2);
        bitmap2.getPixels(this.mTempBuffer, 0, n, 0, 0, n, n3);
        for (n = 0; n < n2; ++n) {
            if (ImageUtils.isGrayscale(this.mTempBuffer[n])) continue;
            return false;
        }
        return true;
    }
}

