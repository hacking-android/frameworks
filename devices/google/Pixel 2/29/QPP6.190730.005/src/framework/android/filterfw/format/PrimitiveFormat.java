/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.format;

import android.filterfw.core.MutableFrameFormat;

public class PrimitiveFormat {
    public static MutableFrameFormat createByteFormat(int n) {
        return PrimitiveFormat.createFormat(2, n);
    }

    public static MutableFrameFormat createByteFormat(int n, int n2) {
        return PrimitiveFormat.createFormat(2, n, n2);
    }

    public static MutableFrameFormat createDoubleFormat(int n) {
        return PrimitiveFormat.createFormat(6, n);
    }

    public static MutableFrameFormat createDoubleFormat(int n, int n2) {
        return PrimitiveFormat.createFormat(6, n, n2);
    }

    public static MutableFrameFormat createFloatFormat(int n) {
        return PrimitiveFormat.createFormat(5, n);
    }

    public static MutableFrameFormat createFloatFormat(int n, int n2) {
        return PrimitiveFormat.createFormat(5, n, n2);
    }

    private static MutableFrameFormat createFormat(int n, int n2) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(n, n2);
        mutableFrameFormat.setDimensionCount(1);
        return mutableFrameFormat;
    }

    private static MutableFrameFormat createFormat(int n, int n2, int n3) {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(n, n3);
        mutableFrameFormat.setDimensions(n2);
        return mutableFrameFormat;
    }

    public static MutableFrameFormat createInt16Format(int n) {
        return PrimitiveFormat.createFormat(3, n);
    }

    public static MutableFrameFormat createInt16Format(int n, int n2) {
        return PrimitiveFormat.createFormat(3, n, n2);
    }

    public static MutableFrameFormat createInt32Format(int n) {
        return PrimitiveFormat.createFormat(4, n);
    }

    public static MutableFrameFormat createInt32Format(int n, int n2) {
        return PrimitiveFormat.createFormat(4, n, n2);
    }
}

