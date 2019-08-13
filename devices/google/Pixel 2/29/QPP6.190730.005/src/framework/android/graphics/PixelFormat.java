/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PixelFormat {
    @Deprecated
    public static final int A_8 = 8;
    public static final int HSV_888 = 55;
    @Deprecated
    public static final int JPEG = 256;
    @Deprecated
    public static final int LA_88 = 10;
    @Deprecated
    public static final int L_8 = 9;
    public static final int OPAQUE = -1;
    public static final int RGBA_1010102 = 43;
    @Deprecated
    public static final int RGBA_4444 = 7;
    @Deprecated
    public static final int RGBA_5551 = 6;
    public static final int RGBA_8888 = 1;
    public static final int RGBA_F16 = 22;
    public static final int RGBX_8888 = 2;
    @Deprecated
    public static final int RGB_332 = 11;
    public static final int RGB_565 = 4;
    public static final int RGB_888 = 3;
    public static final int TRANSLUCENT = -3;
    public static final int TRANSPARENT = -2;
    public static final int UNKNOWN = 0;
    @Deprecated
    public static final int YCbCr_420_SP = 17;
    @Deprecated
    public static final int YCbCr_422_I = 20;
    @Deprecated
    public static final int YCbCr_422_SP = 16;
    public int bitsPerPixel;
    public int bytesPerPixel;

    public static boolean formatHasAlpha(int n) {
        return n == -3 || n == -2 || n == 1 || n == 10 || n == 22 || n == 43 || n == 6 || n == 7 || n == 8;
    }

    public static String formatToString(int n) {
        if (n != -3) {
            if (n != -2) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n != 4) {
                                    if (n != 16) {
                                        if (n != 17) {
                                            if (n != 20) {
                                                if (n != 22) {
                                                    if (n != 43) {
                                                        if (n != 55) {
                                                            if (n != 256) {
                                                                switch (n) {
                                                                    default: {
                                                                        return Integer.toString(n);
                                                                    }
                                                                    case 11: {
                                                                        return "RGB_332";
                                                                    }
                                                                    case 10: {
                                                                        return "LA_88";
                                                                    }
                                                                    case 9: {
                                                                        return "L_8";
                                                                    }
                                                                    case 8: {
                                                                        return "A_8";
                                                                    }
                                                                    case 7: {
                                                                        return "RGBA_4444";
                                                                    }
                                                                    case 6: 
                                                                }
                                                                return "RGBA_5551";
                                                            }
                                                            return "JPEG";
                                                        }
                                                        return "HSV_888";
                                                    }
                                                    return "RGBA_1010102";
                                                }
                                                return "RGBA_F16";
                                            }
                                            return "YCbCr_422_I";
                                        }
                                        return "YCbCr_420_SP";
                                    }
                                    return "YCbCr_422_SP";
                                }
                                return "RGB_565";
                            }
                            return "RGB_888";
                        }
                        return "RGBX_8888";
                    }
                    return "RGBA_8888";
                }
                return "UNKNOWN";
            }
            return "TRANSPARENT";
        }
        return "TRANSLUCENT";
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void getPixelFormatInfo(int var0, PixelFormat var1_1) {
        block5 : {
            block6 : {
                if (var0 == 1 || var0 == 2) break block5;
                if (var0 == 3) break block6;
                if (var0 == 4) ** GOTO lbl-1000
                if (var0 == 16) ** GOTO lbl29
                if (var0 == 17) ** GOTO lbl26
                if (var0 == 20) ** GOTO lbl29
                if (var0 == 22) ** GOTO lbl-1000
                if (var0 == 43) break block5;
                if (var0 != 55) {
                    switch (var0) {
                        default: {
                            var1_1 = new StringBuilder();
                            var1_1.append("unknown pixel format ");
                            var1_1.append(var0);
                            throw new IllegalArgumentException(var1_1.toString());
                        }
                        case 8: 
                        case 9: 
                        case 11: {
                            var1_1.bitsPerPixel = 8;
                            var1_1.bytesPerPixel = 1;
                            return;
                        }
                    }
                }
                break block6;
lbl-1000: // 1 sources:
                {
                    var1_1.bitsPerPixel = 64;
                    var1_1.bytesPerPixel = 8;
                    return;
lbl26: // 1 sources:
                    var1_1.bitsPerPixel = 12;
                    var1_1.bytesPerPixel = 1;
                    return;
lbl29: // 2 sources:
                    var1_1.bitsPerPixel = 16;
                    var1_1.bytesPerPixel = 1;
                    return;
                    case 6: 
                    case 7: 
                    case 10: lbl-1000: // 2 sources:
                    {
                        var1_1.bitsPerPixel = 16;
                        var1_1.bytesPerPixel = 2;
                        return;
                    }
                }
            }
            var1_1.bitsPerPixel = 24;
            var1_1.bytesPerPixel = 3;
            return;
        }
        var1_1.bitsPerPixel = 32;
        var1_1.bytesPerPixel = 4;
    }

    public static boolean isPublicFormat(int n) {
        return n == 1 || n == 2 || n == 3 || n == 4 || n == 22 || n == 43;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Format {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Opacity {
    }

}

