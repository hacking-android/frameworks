/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ImageFormat {
    public static final int DEPTH16 = 1144402265;
    public static final int DEPTH_JPEG = 1768253795;
    public static final int DEPTH_POINT_CLOUD = 257;
    public static final int FLEX_RGBA_8888 = 42;
    public static final int FLEX_RGB_888 = 41;
    public static final int HEIC = 1212500294;
    public static final int JPEG = 256;
    public static final int NV16 = 16;
    public static final int NV21 = 17;
    public static final int PRIVATE = 34;
    public static final int RAW10 = 37;
    public static final int RAW12 = 38;
    public static final int RAW_DEPTH = 4098;
    public static final int RAW_PRIVATE = 36;
    public static final int RAW_SENSOR = 32;
    public static final int RGB_565 = 4;
    public static final int UNKNOWN = 0;
    public static final int Y16 = 540422489;
    public static final int Y8 = 538982489;
    public static final int YUV_420_888 = 35;
    public static final int YUV_422_888 = 39;
    public static final int YUV_444_888 = 40;
    public static final int YUY2 = 20;
    public static final int YV12 = 842094169;

    public static int getBitsPerPixel(int n) {
        if (n != 4) {
            if (n != 20) {
                if (n != 32) {
                    if (n != 35) {
                        if (n != 4098) {
                            if (n != 538982489) {
                                if (n != 540422489) {
                                    if (n != 842094169) {
                                        if (n != 1144402265) {
                                            if (n != 16) {
                                                if (n != 17) {
                                                    switch (n) {
                                                        default: {
                                                            return -1;
                                                        }
                                                        case 42: {
                                                            return 32;
                                                        }
                                                        case 41: {
                                                            return 24;
                                                        }
                                                        case 40: {
                                                            return 24;
                                                        }
                                                        case 39: {
                                                            return 16;
                                                        }
                                                        case 38: {
                                                            return 12;
                                                        }
                                                        case 37: 
                                                    }
                                                    return 10;
                                                }
                                                return 12;
                                            }
                                            return 16;
                                        }
                                    } else {
                                        return 12;
                                    }
                                }
                                return 16;
                            }
                            return 8;
                        }
                    } else {
                        return 12;
                    }
                }
                return 16;
            }
            return 16;
        }
        return 16;
    }

    public static boolean isPublicFormat(int n) {
        if (n != 16 && n != 17 && n != 256 && n != 257) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            return false;
                        }
                        case 34: 
                        case 35: 
                        case 36: 
                        case 37: 
                        case 38: 
                        case 39: 
                        case 40: 
                        case 41: 
                        case 42: 
                    }
                }
                case 4: 
                case 20: 
                case 32: 
                case 4098: 
                case 538982489: 
                case 842094169: 
                case 1144402265: 
                case 1212500294: 
                case 1768253795: 
            }
        }
        return true;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Format {
    }

}

