/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CaptureRequest;
import android.os.Parcelable;
import android.util.Rational;
import android.util.Size;
import com.android.internal.util.Preconditions;

public class ParamsUtils {
    private static final int RATIONAL_DENOMINATOR = 1000000;

    private ParamsUtils() {
        throw new AssertionError();
    }

    public static void convertRectF(Rect rect, RectF rectF) {
        Preconditions.checkNotNull(rect, "source must not be null");
        Preconditions.checkNotNull(rectF, "destination must not be null");
        rectF.left = rect.left;
        rectF.right = rect.right;
        rectF.bottom = rect.bottom;
        rectF.top = rect.top;
    }

    public static Rational createRational(float f) {
        if (Float.isNaN(f)) {
            return Rational.NaN;
        }
        if (f == Float.POSITIVE_INFINITY) {
            return Rational.POSITIVE_INFINITY;
        }
        if (f == Float.NEGATIVE_INFINITY) {
            return Rational.NEGATIVE_INFINITY;
        }
        if (f == 0.0f) {
            return Rational.ZERO;
        }
        int n = 1000000;
        float f2;
        while (!((f2 = (float)n * f) > -2.14748365E9f && f2 < 2.14748365E9f || n == 1)) {
            n /= 10;
        }
        return new Rational((int)f2, n);
    }

    public static Rect createRect(RectF rectF) {
        Preconditions.checkNotNull(rectF, "rect must not be null");
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return rect;
    }

    public static Rect createRect(Size size) {
        Preconditions.checkNotNull(size, "size must not be null");
        return new Rect(0, 0, size.getWidth(), size.getHeight());
    }

    public static Size createSize(Rect rect) {
        Preconditions.checkNotNull(rect, "rect must not be null");
        return new Size(rect.width(), rect.height());
    }

    public static <T> T getOrDefault(CaptureRequest captureRequest, CaptureRequest.Key<T> key, T t) {
        Preconditions.checkNotNull(captureRequest, "r must not be null");
        Preconditions.checkNotNull(key, "key must not be null");
        Preconditions.checkNotNull(t, "defaultValue must not be null");
        captureRequest = captureRequest.get(key);
        if (captureRequest == null) {
            return t;
        }
        return (T)captureRequest;
    }

    public static Rect mapRect(Matrix matrix, Rect parcelable) {
        Preconditions.checkNotNull(matrix, "transform must not be null");
        Preconditions.checkNotNull(parcelable, "rect must not be null");
        parcelable = new RectF((Rect)parcelable);
        matrix.mapRect((RectF)parcelable);
        return ParamsUtils.createRect((RectF)parcelable);
    }
}

