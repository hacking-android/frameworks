/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Rect;
import java.io.OutputStream;

public class YuvImage {
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private byte[] mData;
    private int mFormat;
    private int mHeight;
    private int[] mStrides;
    private int mWidth;

    public YuvImage(byte[] arrby, int n, int n2, int n3, int[] arrn) {
        if (n != 17 && n != 20) {
            throw new IllegalArgumentException("only support ImageFormat.NV21 and ImageFormat.YUY2 for now");
        }
        if (n2 > 0 && n3 > 0) {
            if (arrby != null) {
                this.mStrides = arrn == null ? this.calculateStrides(n2, n) : arrn;
                this.mData = arrby;
                this.mFormat = n;
                this.mWidth = n2;
                this.mHeight = n3;
                return;
            }
            throw new IllegalArgumentException("yuv cannot be null");
        }
        throw new IllegalArgumentException("width and height must large than 0");
    }

    private void adjustRectangle(Rect rect) {
        int n = rect.width();
        int n2 = rect.height();
        int n3 = n;
        if (this.mFormat == 17) {
            n3 = n & -2;
            rect.left &= -2;
            rect.top &= -2;
            rect.right = rect.left + n3;
            rect.bottom = rect.top + (n2 & -2);
        }
        if (this.mFormat == 20) {
            rect.left &= -2;
            rect.right = rect.left + (n3 & -2);
        }
    }

    private int[] calculateStrides(int n, int n2) {
        if (n2 == 17) {
            return new int[]{n, n};
        }
        if (n2 == 20) {
            return new int[]{n * 2};
        }
        return null;
    }

    private static native boolean nativeCompressToJpeg(byte[] var0, int var1, int var2, int var3, int[] var4, int[] var5, int var6, OutputStream var7, byte[] var8);

    int[] calculateOffsets(int n, int n2) {
        int n3 = this.mFormat;
        if (n3 == 17) {
            int[] arrn = this.mStrides;
            return new int[]{arrn[0] * n2 + n, this.mHeight * arrn[0] + n2 / 2 * arrn[1] + n / 2 * 2};
        }
        if (n3 == 20) {
            return new int[]{this.mStrides[0] * n2 + n / 2 * 4};
        }
        return null;
    }

    public boolean compressToJpeg(Rect rect, int n, OutputStream outputStream) {
        if (new Rect(0, 0, this.mWidth, this.mHeight).contains(rect)) {
            if (n >= 0 && n <= 100) {
                if (outputStream != null) {
                    this.adjustRectangle(rect);
                    int[] arrn = this.calculateOffsets(rect.left, rect.top);
                    return YuvImage.nativeCompressToJpeg(this.mData, this.mFormat, rect.width(), rect.height(), arrn, this.mStrides, n, outputStream, new byte[4096]);
                }
                throw new IllegalArgumentException("stream cannot be null");
            }
            throw new IllegalArgumentException("quality must be 0..100");
        }
        throw new IllegalArgumentException("rectangle is not inside the image");
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int[] getStrides() {
        return this.mStrides;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public byte[] getYuvData() {
        return this.mData;
    }

    public int getYuvFormat() {
        return this.mFormat;
    }
}

