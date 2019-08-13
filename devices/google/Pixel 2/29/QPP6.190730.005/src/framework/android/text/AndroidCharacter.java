/*
 * Decompiled with CFR 0.145.
 */
package android.text;

@Deprecated
public class AndroidCharacter {
    public static final int EAST_ASIAN_WIDTH_AMBIGUOUS = 1;
    public static final int EAST_ASIAN_WIDTH_FULL_WIDTH = 3;
    public static final int EAST_ASIAN_WIDTH_HALF_WIDTH = 2;
    public static final int EAST_ASIAN_WIDTH_NARROW = 4;
    public static final int EAST_ASIAN_WIDTH_NEUTRAL = 0;
    public static final int EAST_ASIAN_WIDTH_WIDE = 5;

    public static native void getDirectionalities(char[] var0, byte[] var1, int var2);

    public static native int getEastAsianWidth(char var0);

    public static native void getEastAsianWidths(char[] var0, int var1, int var2, byte[] var3);

    public static native char getMirror(char var0);

    public static native boolean mirror(char[] var0, int var1, int var2);
}

