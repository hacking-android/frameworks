/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.graphics.Bitmap;

public final class GLUtils {
    private GLUtils() {
    }

    public static String getEGLErrorString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0x");
                stringBuilder.append(Integer.toHexString(n));
                return stringBuilder.toString();
            }
            case 12302: {
                return "EGL_CONTEXT_LOST";
            }
            case 12301: {
                return "EGL_BAD_SURFACE";
            }
            case 12300: {
                return "EGL_BAD_PARAMETER";
            }
            case 12299: {
                return "EGL_BAD_NATIVE_WINDOW";
            }
            case 12298: {
                return "EGL_BAD_NATIVE_PIXMAP";
            }
            case 12297: {
                return "EGL_BAD_MATCH";
            }
            case 12296: {
                return "EGL_BAD_DISPLAY";
            }
            case 12295: {
                return "EGL_BAD_CURRENT_SURFACE";
            }
            case 12294: {
                return "EGL_BAD_CONTEXT";
            }
            case 12293: {
                return "EGL_BAD_CONFIG";
            }
            case 12292: {
                return "EGL_BAD_ATTRIBUTE";
            }
            case 12291: {
                return "EGL_BAD_ALLOC";
            }
            case 12290: {
                return "EGL_BAD_ACCESS";
            }
            case 12289: {
                return "EGL_NOT_INITIALIZED";
            }
            case 12288: 
        }
        return "EGL_SUCCESS";
    }

    public static int getInternalFormat(Bitmap bitmap) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                int n = GLUtils.native_getInternalFormat(bitmap.getNativeInstance());
                if (n >= 0) {
                    return n;
                }
                throw new IllegalArgumentException("Unknown internalformat");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("getInternalFormat can't be used with a null Bitmap");
    }

    public static int getType(Bitmap bitmap) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                int n = GLUtils.native_getType(bitmap.getNativeInstance());
                if (n >= 0) {
                    return n;
                }
                throw new IllegalArgumentException("Unknown type");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("getType can't be used with a null Bitmap");
    }

    private static native int native_getInternalFormat(long var0);

    private static native int native_getType(long var0);

    private static native int native_texImage2D(int var0, int var1, int var2, long var3, int var5, int var6);

    private static native int native_texSubImage2D(int var0, int var1, int var2, int var3, long var4, int var6, int var7);

    public static void texImage2D(int n, int n2, int n3, Bitmap bitmap, int n4) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                if (GLUtils.native_texImage2D(n, n2, n3, bitmap.getNativeInstance(), -1, n4) == 0) {
                    return;
                }
                throw new IllegalArgumentException("invalid Bitmap format");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("texImage2D can't be used with a null Bitmap");
    }

    public static void texImage2D(int n, int n2, int n3, Bitmap bitmap, int n4, int n5) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                if (GLUtils.native_texImage2D(n, n2, n3, bitmap.getNativeInstance(), n4, n5) == 0) {
                    return;
                }
                throw new IllegalArgumentException("invalid Bitmap format");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("texImage2D can't be used with a null Bitmap");
    }

    public static void texImage2D(int n, int n2, Bitmap bitmap, int n3) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                if (GLUtils.native_texImage2D(n, n2, -1, bitmap.getNativeInstance(), -1, n3) == 0) {
                    return;
                }
                throw new IllegalArgumentException("invalid Bitmap format");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("texImage2D can't be used with a null Bitmap");
    }

    public static void texSubImage2D(int n, int n2, int n3, int n4, Bitmap bitmap) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                int n5 = GLUtils.getType(bitmap);
                if (GLUtils.native_texSubImage2D(n, n2, n3, n4, bitmap.getNativeInstance(), -1, n5) == 0) {
                    return;
                }
                throw new IllegalArgumentException("invalid Bitmap format");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("texSubImage2D can't be used with a null Bitmap");
    }

    public static void texSubImage2D(int n, int n2, int n3, int n4, Bitmap bitmap, int n5, int n6) {
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                if (GLUtils.native_texSubImage2D(n, n2, n3, n4, bitmap.getNativeInstance(), n5, n6) == 0) {
                    return;
                }
                throw new IllegalArgumentException("invalid Bitmap format");
            }
            throw new IllegalArgumentException("bitmap is recycled");
        }
        throw new NullPointerException("texSubImage2D can't be used with a null Bitmap");
    }
}

