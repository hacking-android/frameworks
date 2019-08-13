/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.DisplayMetrics;

public class TypedValue {
    public static final int COMPLEX_MANTISSA_MASK = 16777215;
    public static final int COMPLEX_MANTISSA_SHIFT = 8;
    public static final int COMPLEX_RADIX_0p23 = 3;
    public static final int COMPLEX_RADIX_16p7 = 1;
    public static final int COMPLEX_RADIX_23p0 = 0;
    public static final int COMPLEX_RADIX_8p15 = 2;
    public static final int COMPLEX_RADIX_MASK = 3;
    public static final int COMPLEX_RADIX_SHIFT = 4;
    public static final int COMPLEX_UNIT_DIP = 1;
    public static final int COMPLEX_UNIT_FRACTION = 0;
    public static final int COMPLEX_UNIT_FRACTION_PARENT = 1;
    public static final int COMPLEX_UNIT_IN = 4;
    public static final int COMPLEX_UNIT_MASK = 15;
    public static final int COMPLEX_UNIT_MM = 5;
    public static final int COMPLEX_UNIT_PT = 3;
    public static final int COMPLEX_UNIT_PX = 0;
    public static final int COMPLEX_UNIT_SHIFT = 0;
    public static final int COMPLEX_UNIT_SP = 2;
    public static final int DATA_NULL_EMPTY = 1;
    public static final int DATA_NULL_UNDEFINED = 0;
    public static final int DENSITY_DEFAULT = 0;
    public static final int DENSITY_NONE = 65535;
    private static final String[] DIMENSION_UNIT_STRS;
    private static final String[] FRACTION_UNIT_STRS;
    private static final float MANTISSA_MULT = 0.00390625f;
    private static final float[] RADIX_MULTS;
    public static final int TYPE_ATTRIBUTE = 2;
    public static final int TYPE_DIMENSION = 5;
    public static final int TYPE_FIRST_COLOR_INT = 28;
    public static final int TYPE_FIRST_INT = 16;
    public static final int TYPE_FLOAT = 4;
    public static final int TYPE_FRACTION = 6;
    public static final int TYPE_INT_BOOLEAN = 18;
    public static final int TYPE_INT_COLOR_ARGB4 = 30;
    public static final int TYPE_INT_COLOR_ARGB8 = 28;
    public static final int TYPE_INT_COLOR_RGB4 = 31;
    public static final int TYPE_INT_COLOR_RGB8 = 29;
    public static final int TYPE_INT_DEC = 16;
    public static final int TYPE_INT_HEX = 17;
    public static final int TYPE_LAST_COLOR_INT = 31;
    public static final int TYPE_LAST_INT = 31;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_REFERENCE = 1;
    public static final int TYPE_STRING = 3;
    public int assetCookie;
    public int changingConfigurations = -1;
    public int data;
    public int density;
    public int resourceId;
    public int sourceResourceId;
    public CharSequence string;
    public int type;

    static {
        RADIX_MULTS = new float[]{0.00390625f, 3.0517578E-5f, 1.1920929E-7f, 4.656613E-10f};
        DIMENSION_UNIT_STRS = new String[]{"px", "dip", "sp", "pt", "in", "mm"};
        FRACTION_UNIT_STRS = new String[]{"%", "%p"};
    }

    public static float applyDimension(int n, float f, DisplayMetrics displayMetrics) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return 0.0f;
                            }
                            return displayMetrics.xdpi * f * 0.03937008f;
                        }
                        return displayMetrics.xdpi * f;
                    }
                    return displayMetrics.xdpi * f * 0.013888889f;
                }
                return displayMetrics.scaledDensity * f;
            }
            return displayMetrics.density * f;
        }
        return f;
    }

    public static final String coerceToString(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 6) {
                                if (n != 17) {
                                    if (n != 18) {
                                        if (n >= 28 && n <= 31) {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("#");
                                            stringBuilder.append(Integer.toHexString(n2));
                                            return stringBuilder.toString();
                                        }
                                        if (n >= 16 && n <= 31) {
                                            return Integer.toString(n2);
                                        }
                                        return null;
                                    }
                                    String string2 = n2 != 0 ? "true" : "false";
                                    return string2;
                                }
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("0x");
                                stringBuilder.append(Integer.toHexString(n2));
                                return stringBuilder.toString();
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(Float.toString(TypedValue.complexToFloat(n2) * 100.0f));
                            stringBuilder.append(FRACTION_UNIT_STRS[n2 >> 0 & 15]);
                            return stringBuilder.toString();
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(Float.toString(TypedValue.complexToFloat(n2)));
                        stringBuilder.append(DIMENSION_UNIT_STRS[n2 >> 0 & 15]);
                        return stringBuilder.toString();
                    }
                    return Float.toString(Float.intBitsToFloat(n2));
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?");
                stringBuilder.append(n2);
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@");
            stringBuilder.append(n2);
            return stringBuilder.toString();
        }
        return null;
    }

    public static float complexToDimension(int n, DisplayMetrics displayMetrics) {
        return TypedValue.applyDimension(n >> 0 & 15, TypedValue.complexToFloat(n), displayMetrics);
    }

    @Deprecated
    public static float complexToDimensionNoisy(int n, DisplayMetrics displayMetrics) {
        return TypedValue.complexToDimension(n, displayMetrics);
    }

    public static int complexToDimensionPixelOffset(int n, DisplayMetrics displayMetrics) {
        return (int)TypedValue.applyDimension(n >> 0 & 15, TypedValue.complexToFloat(n), displayMetrics);
    }

    public static int complexToDimensionPixelSize(int n, DisplayMetrics displayMetrics) {
        float f = TypedValue.complexToFloat(n);
        float f2 = TypedValue.applyDimension(n >> 0 & 15, f, displayMetrics);
        f2 = f2 >= 0.0f ? 0.5f + f2 : (f2 -= 0.5f);
        n = (int)f2;
        if (n != 0) {
            return n;
        }
        if (f == 0.0f) {
            return 0;
        }
        if (f > 0.0f) {
            return 1;
        }
        return -1;
    }

    public static float complexToFloat(int n) {
        return (float)(n & -256) * RADIX_MULTS[n >> 4 & 3];
    }

    public static float complexToFraction(int n, float f, float f2) {
        int n2 = n >> 0 & 15;
        if (n2 != 0) {
            if (n2 != 1) {
                return 0.0f;
            }
            return TypedValue.complexToFloat(n) * f2;
        }
        return TypedValue.complexToFloat(n) * f;
    }

    public final CharSequence coerceToString() {
        int n = this.type;
        if (n == 3) {
            return this.string;
        }
        return TypedValue.coerceToString(n, this.data);
    }

    public int getComplexUnit() {
        return this.data >> 0 & 15;
    }

    public float getDimension(DisplayMetrics displayMetrics) {
        return TypedValue.complexToDimension(this.data, displayMetrics);
    }

    public final float getFloat() {
        return Float.intBitsToFloat(this.data);
    }

    public float getFraction(float f, float f2) {
        return TypedValue.complexToFraction(this.data, f, f2);
    }

    public boolean isColorType() {
        int n = this.type;
        boolean bl = n >= 28 && n <= 31;
        return bl;
    }

    public void setTo(TypedValue typedValue) {
        this.type = typedValue.type;
        this.string = typedValue.string;
        this.data = typedValue.data;
        this.assetCookie = typedValue.assetCookie;
        this.resourceId = typedValue.resourceId;
        this.density = typedValue.density;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TypedValue{t=0x");
        stringBuilder.append(Integer.toHexString(this.type));
        stringBuilder.append("/d=0x");
        stringBuilder.append(Integer.toHexString(this.data));
        if (this.type == 3) {
            stringBuilder.append(" \"");
            CharSequence charSequence = this.string;
            if (charSequence == null) {
                charSequence = "<null>";
            }
            stringBuilder.append(charSequence);
            stringBuilder.append("\"");
        }
        if (this.assetCookie != 0) {
            stringBuilder.append(" a=");
            stringBuilder.append(this.assetCookie);
        }
        if (this.resourceId != 0) {
            stringBuilder.append(" r=0x");
            stringBuilder.append(Integer.toHexString(this.resourceId));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

