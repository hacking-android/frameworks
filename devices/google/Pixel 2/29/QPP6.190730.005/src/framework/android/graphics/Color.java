/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.ColorSpace;
import android.util.Half;
import com.android.internal.util.XmlUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public class Color {
    public static final int BLACK = -16777216;
    public static final int BLUE = -16776961;
    public static final int CYAN = -16711681;
    public static final int DKGRAY = -12303292;
    public static final int GRAY = -7829368;
    public static final int GREEN = -16711936;
    public static final int LTGRAY = -3355444;
    public static final int MAGENTA = -65281;
    public static final int RED = -65536;
    public static final int TRANSPARENT = 0;
    public static final int WHITE = -1;
    public static final int YELLOW = -256;
    private static final HashMap<String, Integer> sColorNameMap = new HashMap();
    private final ColorSpace mColorSpace;
    private final float[] mComponents;

    static {
        sColorNameMap.put("black", -16777216);
        Serializable serializable = sColorNameMap;
        Integer n = -12303292;
        serializable.put("darkgray", n);
        Serializable serializable2 = sColorNameMap;
        serializable = -7829368;
        serializable2.put("gray", (Integer)serializable);
        Serializable serializable3 = sColorNameMap;
        serializable2 = -3355444;
        serializable3.put("lightgray", (Integer)serializable2);
        sColorNameMap.put("white", -1);
        sColorNameMap.put("red", -65536);
        Serializable serializable4 = sColorNameMap;
        serializable3 = -16711936;
        serializable4.put("green", (Integer)serializable3);
        sColorNameMap.put("blue", -16776961);
        sColorNameMap.put("yellow", -256);
        Serializable serializable5 = sColorNameMap;
        serializable4 = -16711681;
        serializable5.put("cyan", (Integer)serializable4);
        HashMap<String, Integer> hashMap = sColorNameMap;
        serializable5 = -65281;
        hashMap.put("magenta", (Integer)serializable5);
        sColorNameMap.put("aqua", (Integer)serializable4);
        sColorNameMap.put("fuchsia", (Integer)serializable5);
        sColorNameMap.put("darkgrey", n);
        sColorNameMap.put("grey", (Integer)serializable);
        sColorNameMap.put("lightgrey", (Integer)serializable2);
        sColorNameMap.put("lime", (Integer)serializable3);
        sColorNameMap.put("maroon", -8388608);
        sColorNameMap.put("navy", -16777088);
        sColorNameMap.put("olive", -8355840);
        sColorNameMap.put("purple", -8388480);
        sColorNameMap.put("silver", -4144960);
        sColorNameMap.put("teal", -16744320);
    }

    public Color() {
        this.mComponents = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        this.mColorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
    }

    private Color(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    private Color(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        this.mComponents = new float[]{f, f2, f3, f4};
        this.mColorSpace = colorSpace;
    }

    private Color(float[] arrf, ColorSpace colorSpace) {
        this.mComponents = arrf;
        this.mColorSpace = colorSpace;
    }

    public static int HSVToColor(int n, float[] arrf) {
        if (arrf.length >= 3) {
            return Color.nativeHSVToColor(n, arrf);
        }
        throw new RuntimeException("3 components required for hsv");
    }

    public static int HSVToColor(float[] arrf) {
        return Color.HSVToColor(255, arrf);
    }

    public static void RGBToHSV(int n, int n2, int n3, float[] arrf) {
        if (arrf.length >= 3) {
            Color.nativeRGBToHSV(n, n2, n3, arrf);
            return;
        }
        throw new RuntimeException("3 components required for hsv");
    }

    public static float alpha(long l) {
        if ((63L & l) == 0L) {
            return (float)(l >> 56 & 255L) / 255.0f;
        }
        return (float)(l >> 6 & 1023L) / 1023.0f;
    }

    public static int alpha(int n) {
        return n >>> 24;
    }

    public static int argb(float f, float f2, float f3, float f4) {
        int n = (int)(f * 255.0f + 0.5f);
        int n2 = (int)(f2 * 255.0f + 0.5f);
        int n3 = (int)(f3 * 255.0f + 0.5f);
        return (int)(255.0f * f4 + 0.5f) | (n << 24 | n2 << 16 | n3 << 8);
    }

    public static int argb(int n, int n2, int n3, int n4) {
        return n << 24 | n2 << 16 | n3 << 8 | n4;
    }

    public static float blue(long l) {
        if ((63L & l) == 0L) {
            return (float)(l >> 32 & 255L) / 255.0f;
        }
        return Half.toFloat((short)(l >> 16 & 65535L));
    }

    public static int blue(int n) {
        return n & 255;
    }

    public static ColorSpace colorSpace(long l) {
        return ColorSpace.get((int)(63L & l));
    }

    public static void colorToHSV(int n, float[] arrf) {
        Color.RGBToHSV(n >> 16 & 255, n >> 8 & 255, n & 255, arrf);
    }

    public static long convert(float f, float f2, float f3, float f4, ColorSpace.Connector connector) {
        float[] arrf = connector.transform(f, f2, f3);
        return Color.pack(arrf[0], arrf[1], arrf[2], f4, connector.getDestination());
    }

    public static long convert(float f, float f2, float f3, float f4, ColorSpace arrf, ColorSpace colorSpace) {
        arrf = ColorSpace.connect((ColorSpace)arrf, colorSpace).transform(f, f2, f3);
        return Color.pack(arrf[0], arrf[1], arrf[2], f4, colorSpace);
    }

    public static long convert(int n, ColorSpace colorSpace) {
        return Color.convert((float)(n >> 16 & 255) / 255.0f, (float)(n >> 8 & 255) / 255.0f, (float)(n & 255) / 255.0f, (float)(n >> 24 & 255) / 255.0f, ColorSpace.get(ColorSpace.Named.SRGB), colorSpace);
    }

    public static long convert(long l, ColorSpace.Connector connector) {
        return Color.convert(Color.red(l), Color.green(l), Color.blue(l), Color.alpha(l), connector);
    }

    public static long convert(long l, ColorSpace colorSpace) {
        return Color.convert(Color.red(l), Color.green(l), Color.blue(l), Color.alpha(l), Color.colorSpace(l), colorSpace);
    }

    public static int getHtmlColor(String string2) {
        Integer n = sColorNameMap.get(string2.toLowerCase(Locale.ROOT));
        if (n != null) {
            return n;
        }
        try {
            int n2 = XmlUtils.convertValueToInt(string2, -1);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    public static float green(long l) {
        if ((63L & l) == 0L) {
            return (float)(l >> 40 & 255L) / 255.0f;
        }
        return Half.toFloat((short)(l >> 32 & 65535L));
    }

    public static int green(int n) {
        return n >> 8 & 255;
    }

    public static boolean isInColorSpace(long l, ColorSpace colorSpace) {
        boolean bl = (int)(63L & l) == colorSpace.getId();
        return bl;
    }

    public static boolean isSrgb(long l) {
        return Color.colorSpace(l).isSrgb();
    }

    public static boolean isWideGamut(long l) {
        return Color.colorSpace(l).isWideGamut();
    }

    public static float luminance(int n) {
        DoubleUnaryOperator doubleUnaryOperator = ((ColorSpace.Rgb)ColorSpace.get(ColorSpace.Named.SRGB)).getEotf();
        return (float)(0.2126 * doubleUnaryOperator.applyAsDouble((double)Color.red(n) / 255.0) + 0.7152 * doubleUnaryOperator.applyAsDouble((double)Color.green(n) / 255.0) + 0.0722 * doubleUnaryOperator.applyAsDouble((double)Color.blue(n) / 255.0));
    }

    public static float luminance(long l) {
        Object object = Color.colorSpace(l);
        if (((ColorSpace)object).getModel() == ColorSpace.Model.RGB) {
            object = ((ColorSpace.Rgb)object).getEotf();
            return Color.saturate((float)(0.2126 * object.applyAsDouble(Color.red(l)) + 0.7152 * object.applyAsDouble(Color.green(l)) + 0.0722 * object.applyAsDouble(Color.blue(l))));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The specified color must be encoded in an RGB color space. The supplied color space is ");
        stringBuilder.append((Object)((ColorSpace)object).getModel());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static native int nativeHSVToColor(int var0, float[] var1);

    private static native void nativeRGBToHSV(int var0, int var1, int var2, float[] var3);

    public static long pack(float f, float f2, float f3) {
        return Color.pack(f, f2, f3, 1.0f, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4) {
        return Color.pack(f, f2, f3, f4, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static long pack(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        if (colorSpace.isSrgb()) {
            int n = (int)(f4 * 255.0f + 0.5f);
            int n2 = (int)(f * 255.0f + 0.5f);
            int n3 = (int)(f2 * 255.0f + 0.5f);
            return ((long)((int)(255.0f * f3 + 0.5f) | (n2 << 16 | n << 24 | n3 << 8)) & 0xFFFFFFFFL) << 32;
        }
        int n = colorSpace.getId();
        if (n != -1) {
            if (colorSpace.getComponentCount() <= 3) {
                short s = Half.toHalf(f);
                short s2 = Half.toHalf(f2);
                short s3 = Half.toHalf(f3);
                int n4 = (int)(Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f + 0.5f);
                long l = s;
                long l2 = s2;
                return (65535L & (long)s3) << 16 | ((l & 65535L) << 48 | (l2 & 65535L) << 32) | ((long)n4 & 1023L) << 6 | (long)n & 63L;
            }
            throw new IllegalArgumentException("The color space must use a color model with at most 3 components");
        }
        throw new IllegalArgumentException("Unknown color space, please use a color space returned by ColorSpace.get()");
    }

    public static long pack(int n) {
        return ((long)n & 0xFFFFFFFFL) << 32;
    }

    public static int parseColor(String object) {
        block4 : {
            block7 : {
                long l;
                block6 : {
                    block5 : {
                        if (((String)object).charAt(0) != '#') break block4;
                        l = Long.parseLong(((String)object).substring(1), 16);
                        if (((String)object).length() != 7) break block5;
                        l |= -16777216L;
                        break block6;
                    }
                    if (((String)object).length() != 9) break block7;
                }
                return (int)l;
            }
            throw new IllegalArgumentException("Unknown color");
        }
        if ((object = sColorNameMap.get(((String)object).toLowerCase(Locale.ROOT))) != null) {
            return (Integer)object;
        }
        throw new IllegalArgumentException("Unknown color");
    }

    public static float red(long l) {
        if ((63L & l) == 0L) {
            return (float)(l >> 48 & 255L) / 255.0f;
        }
        return Half.toFloat((short)(l >> 48 & 65535L));
    }

    public static int red(int n) {
        return n >> 16 & 255;
    }

    public static int rgb(float f, float f2, float f3) {
        int n = (int)(f * 255.0f + 0.5f);
        int n2 = (int)(f2 * 255.0f + 0.5f);
        return (int)(255.0f * f3 + 0.5f) | (n << 16 | -16777216 | n2 << 8);
    }

    public static int rgb(int n, int n2, int n3) {
        return n << 16 | -16777216 | n2 << 8 | n3;
    }

    private static float saturate(float f) {
        block1 : {
            block0 : {
                float f2 = 0.0f;
                if (!(f <= 0.0f)) break block0;
                f = f2;
                break block1;
            }
            if (!(f >= 1.0f)) break block1;
            f = 1.0f;
        }
        return f;
    }

    public static int toArgb(long l) {
        if ((63L & l) == 0L) {
            return (int)(l >> 32);
        }
        float f = Color.red(l);
        float f2 = Color.green(l);
        float f3 = Color.blue(l);
        float f4 = Color.alpha(l);
        float[] arrf = ColorSpace.connect(Color.colorSpace(l)).transform(f, f2, f3);
        int n = (int)(f4 * 255.0f + 0.5f);
        int n2 = (int)(arrf[0] * 255.0f + 0.5f);
        int n3 = (int)(arrf[1] * 255.0f + 0.5f);
        return (int)(arrf[2] * 255.0f + 0.5f) | (n << 24 | n2 << 16 | n3 << 8);
    }

    public static Color valueOf(float f, float f2, float f3) {
        return new Color(f, f2, f3, 1.0f);
    }

    public static Color valueOf(float f, float f2, float f3, float f4) {
        return new Color(Color.saturate(f), Color.saturate(f2), Color.saturate(f3), Color.saturate(f4));
    }

    public static Color valueOf(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        if (colorSpace.getComponentCount() <= 3) {
            return new Color(f, f2, f3, f4, colorSpace);
        }
        throw new IllegalArgumentException("The specified color space must use a color model with at most 3 color components");
    }

    public static Color valueOf(int n) {
        return new Color((float)(n >> 16 & 255) / 255.0f, (float)(n >> 8 & 255) / 255.0f, (float)(n & 255) / 255.0f, (float)(n >> 24 & 255) / 255.0f, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Color valueOf(long l) {
        return new Color(Color.red(l), Color.green(l), Color.blue(l), Color.alpha(l), Color.colorSpace(l));
    }

    public static Color valueOf(float[] arrf, ColorSpace colorSpace) {
        if (arrf.length >= colorSpace.getComponentCount() + 1) {
            return new Color(Arrays.copyOf(arrf, colorSpace.getComponentCount() + 1), colorSpace);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Received a component array of length ");
        stringBuilder.append(arrf.length);
        stringBuilder.append(" but the color model requires ");
        stringBuilder.append(colorSpace.getComponentCount() + 1);
        stringBuilder.append(" (including alpha)");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public float alpha() {
        float[] arrf = this.mComponents;
        return arrf[arrf.length - 1];
    }

    public float blue() {
        return this.mComponents[2];
    }

    public Color convert(ColorSpace colorSpace) {
        ColorSpace.Connector connector = ColorSpace.connect(this.mColorSpace, colorSpace);
        float[] arrf = new float[4];
        float[] arrf2 = this.mComponents;
        arrf[0] = arrf2[0];
        arrf[1] = arrf2[1];
        arrf[2] = arrf2[2];
        arrf[3] = arrf2[3];
        connector.transform(arrf);
        return new Color(arrf, colorSpace);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Color)object;
            if (!Arrays.equals(this.mComponents, ((Color)object).mComponents)) {
                return false;
            }
            return this.mColorSpace.equals(((Color)object).mColorSpace);
        }
        return false;
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public float getComponent(int n) {
        return this.mComponents[n];
    }

    public int getComponentCount() {
        return this.mColorSpace.getComponentCount() + 1;
    }

    public float[] getComponents() {
        float[] arrf = this.mComponents;
        return Arrays.copyOf(arrf, arrf.length);
    }

    public float[] getComponents(float[] object) {
        if (object == null) {
            object = this.mComponents;
            return Arrays.copyOf((float[])object, ((float[])object).length);
        }
        int n = ((float[])object).length;
        float[] arrf = this.mComponents;
        if (n >= arrf.length) {
            System.arraycopy(arrf, 0, object, 0, arrf.length);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The specified array's length must be at least ");
        ((StringBuilder)object).append(this.mComponents.length);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public ColorSpace.Model getModel() {
        return this.mColorSpace.getModel();
    }

    public float green() {
        return this.mComponents[1];
    }

    public int hashCode() {
        return Arrays.hashCode(this.mComponents) * 31 + this.mColorSpace.hashCode();
    }

    public boolean isSrgb() {
        return this.getColorSpace().isSrgb();
    }

    public boolean isWideGamut() {
        return this.getColorSpace().isWideGamut();
    }

    public float luminance() {
        if (this.mColorSpace.getModel() == ColorSpace.Model.RGB) {
            DoubleUnaryOperator doubleUnaryOperator = ((ColorSpace.Rgb)this.mColorSpace).getEotf();
            return Color.saturate((float)(0.2126 * doubleUnaryOperator.applyAsDouble(this.mComponents[0]) + 0.7152 * doubleUnaryOperator.applyAsDouble(this.mComponents[1]) + 0.0722 * doubleUnaryOperator.applyAsDouble(this.mComponents[2])));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The specified color must be encoded in an RGB color space. The supplied color space is ");
        stringBuilder.append((Object)((Object)this.mColorSpace.getModel()));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public long pack() {
        float[] arrf = this.mComponents;
        return Color.pack(arrf[0], arrf[1], arrf[2], arrf[3], this.mColorSpace);
    }

    public float red() {
        return this.mComponents[0];
    }

    public int toArgb() {
        if (this.mColorSpace.isSrgb()) {
            float[] arrf = this.mComponents;
            int n = (int)(arrf[3] * 255.0f + 0.5f);
            int n2 = (int)(arrf[0] * 255.0f + 0.5f);
            int n3 = (int)(arrf[1] * 255.0f + 0.5f);
            return (int)(arrf[2] * 255.0f + 0.5f) | (n << 24 | n2 << 16 | n3 << 8);
        }
        float[] arrf = new float[4];
        float[] arrf2 = this.mComponents;
        arrf[0] = arrf2[0];
        arrf[1] = arrf2[1];
        arrf[2] = arrf2[2];
        arrf[3] = arrf2[3];
        ColorSpace.connect(this.mColorSpace).transform(arrf);
        return (int)(arrf[3] * 255.0f + 0.5f) << 24 | (int)(arrf[0] * 255.0f + 0.5f) << 16 | (int)(arrf[1] * 255.0f + 0.5f) << 8 | (int)(arrf[2] * 255.0f + 0.5f);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Color(");
        float[] arrf = this.mComponents;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrf[i]);
            stringBuilder.append(", ");
        }
        stringBuilder.append(this.mColorSpace.getName());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

