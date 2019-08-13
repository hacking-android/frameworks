/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

public class Visibility {
    public static native void computeBoundingSphere(float[] var0, int var1, int var2, float[] var3, int var4);

    public static native int frustumCullSpheres(float[] var0, int var1, float[] var2, int var3, int var4, int[] var5, int var6, int var7);

    public static native int visibilityTest(float[] var0, int var1, float[] var2, int var3, char[] var4, int var5, int var6);
}

