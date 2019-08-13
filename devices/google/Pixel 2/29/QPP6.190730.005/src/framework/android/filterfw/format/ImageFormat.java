/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.format;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.MutableFrameFormat;
import android.graphics.Bitmap;

public class ImageFormat {
    public static final int COLORSPACE_GRAY = 1;
    public static final String COLORSPACE_KEY = "colorspace";
    public static final int COLORSPACE_RGB = 2;
    public static final int COLORSPACE_RGBA = 3;
    public static final int COLORSPACE_YUV = 4;

    public static int bytesPerSampleForColorspace(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return 3;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown colorspace id ");
                    stringBuilder.append(n);
                    stringBuilder.append("!");
                    throw new RuntimeException(stringBuilder.toString());
                }
                return 4;
            }
            return 3;
        }
        return 1;
    }

    @UnsupportedAppUsage
    public static MutableFrameFormat create(int n) {
        return ImageFormat.create(0, 0, n, ImageFormat.bytesPerSampleForColorspace(n), 0);
    }

    @UnsupportedAppUsage
    public static MutableFrameFormat create(int n, int n2) {
        return ImageFormat.create(0, 0, n, ImageFormat.bytesPerSampleForColorspace(n), n2);
    }

    @UnsupportedAppUsage
    public static MutableFrameFormat create(int n, int n2, int n3, int n4) {
        return ImageFormat.create(n, n2, n3, ImageFormat.bytesPerSampleForColorspace(n3), n4);
    }

    public static MutableFrameFormat create(int n, int n2, int n3, int n4, int n5) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(2, n5);
        mutableFrameFormat.setDimensions(n, n2);
        mutableFrameFormat.setBytesPerSample(n4);
        mutableFrameFormat.setMetaValue(COLORSPACE_KEY, n3);
        if (n5 == 1) {
            mutableFrameFormat.setObjectClass(Bitmap.class);
        }
        return mutableFrameFormat;
    }
}

