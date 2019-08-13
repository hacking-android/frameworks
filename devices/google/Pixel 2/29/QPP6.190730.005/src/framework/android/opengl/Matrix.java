/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

public class Matrix {
    private static final float[] sTemp = new float[32];

    public static void frustumM(float[] arrf, int n, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f != f2) {
            if (f4 != f3) {
                if (f5 != f6) {
                    if (!(f5 <= 0.0f)) {
                        if (!(f6 <= 0.0f)) {
                            float f7 = 1.0f / (f2 - f);
                            float f8 = 1.0f / (f4 - f3);
                            float f9 = 1.0f / (f5 - f6);
                            arrf[n + 0] = f5 * f7 * 2.0f;
                            arrf[n + 5] = f5 * f8 * 2.0f;
                            arrf[n + 8] = (f2 + f) * f7;
                            arrf[n + 9] = (f4 + f3) * f8;
                            arrf[n + 10] = (f6 + f5) * f9;
                            arrf[n + 14] = f6 * f5 * f9 * 2.0f;
                            arrf[n + 11] = -1.0f;
                            arrf[n + 1] = 0.0f;
                            arrf[n + 2] = 0.0f;
                            arrf[n + 3] = 0.0f;
                            arrf[n + 4] = 0.0f;
                            arrf[n + 6] = 0.0f;
                            arrf[n + 7] = 0.0f;
                            arrf[n + 12] = 0.0f;
                            arrf[n + 13] = 0.0f;
                            arrf[n + 15] = 0.0f;
                            return;
                        }
                        throw new IllegalArgumentException("far <= 0.0f");
                    }
                    throw new IllegalArgumentException("near <= 0.0f");
                }
                throw new IllegalArgumentException("near == far");
            }
            throw new IllegalArgumentException("top == bottom");
        }
        throw new IllegalArgumentException("left == right");
    }

    public static boolean invertM(float[] arrf, int n, float[] arrf2, int n2) {
        float f = arrf2[n2 + 0];
        float f2 = arrf2[n2 + 1];
        float f3 = arrf2[n2 + 2];
        float f4 = arrf2[n2 + 3];
        float f5 = arrf2[n2 + 4];
        float f6 = arrf2[n2 + 5];
        float f7 = arrf2[n2 + 6];
        float f8 = arrf2[n2 + 7];
        float f9 = arrf2[n2 + 8];
        float f10 = arrf2[n2 + 9];
        float f11 = arrf2[n2 + 10];
        float f12 = arrf2[n2 + 11];
        float f13 = arrf2[n2 + 12];
        float f14 = arrf2[n2 + 13];
        float f15 = arrf2[n2 + 14];
        float f16 = arrf2[n2 + 15];
        float f17 = f11 * f16;
        float f18 = f15 * f12;
        float f19 = f7 * f16;
        float f20 = f15 * f8;
        float f21 = f7 * f12;
        float f22 = f11 * f8;
        float f23 = f3 * f16;
        float f24 = f15 * f4;
        float f25 = f3 * f12;
        float f26 = f11 * f4;
        float f27 = f3 * f8;
        float f28 = f7 * f4;
        float f29 = f17 * f6 + f20 * f10 + f21 * f14 - (f18 * f6 + f19 * f10 + f22 * f14);
        float f30 = f18 * f2 + f23 * f10 + f26 * f14 - (f17 * f2 + f24 * f10 + f25 * f14);
        float f31 = f19 * f2 + f24 * f6 + f27 * f14 - (f20 * f2 + f23 * f6 + f28 * f14);
        float f32 = f22 * f2 + f25 * f6 + f28 * f10 - (f21 * f2 + f26 * f6 + f27 * f10);
        float f33 = f9 * f14;
        float f34 = f13 * f10;
        float f35 = f5 * f14;
        float f36 = f13 * f6;
        float f37 = f5 * f10;
        float f38 = f9 * f6;
        f14 = f * f14;
        float f39 = f13 * f2;
        float f40 = f * f10;
        f10 = f9 * f2;
        f6 = f * f6;
        f2 = f5 * f2;
        float f41 = f * f29 + f5 * f30 + f9 * f31 + f13 * f32;
        if (f41 == 0.0f) {
            return false;
        }
        f41 = 1.0f / f41;
        arrf[n] = f29 * f41;
        arrf[n + 1] = f30 * f41;
        arrf[n + 2] = f31 * f41;
        arrf[n + 3] = f32 * f41;
        arrf[n + 4] = (f18 * f5 + f19 * f9 + f22 * f13 - (f17 * f5 + f20 * f9 + f21 * f13)) * f41;
        arrf[n + 5] = (f17 * f + f24 * f9 + f25 * f13 - (f18 * f + f23 * f9 + f26 * f13)) * f41;
        arrf[n + 6] = (f20 * f + f23 * f5 + f28 * f13 - (f19 * f + f24 * f5 + f27 * f13)) * f41;
        arrf[n + 7] = (f21 * f + f26 * f5 + f27 * f9 - (f22 * f + f25 * f5 + f28 * f9)) * f41;
        arrf[n + 8] = (f33 * f8 + f36 * f12 + f37 * f16 - (f34 * f8 + f35 * f12 + f38 * f16)) * f41;
        arrf[n + 9] = (f34 * f4 + f14 * f12 + f10 * f16 - (f33 * f4 + f39 * f12 + f40 * f16)) * f41;
        arrf[n + 10] = (f35 * f4 + f39 * f8 + f6 * f16 - (f36 * f4 + f14 * f8 + f2 * f16)) * f41;
        arrf[n + 11] = (f38 * f4 + f40 * f8 + f2 * f12 - (f37 * f4 + f10 * f8 + f6 * f12)) * f41;
        arrf[n + 12] = (f35 * f11 + f38 * f15 + f34 * f7 - (f37 * f15 + f33 * f7 + f36 * f11)) * f41;
        arrf[n + 13] = (f40 * f15 + f33 * f3 + f39 * f11 - (f14 * f11 + f10 * f15 + f34 * f3)) * f41;
        arrf[n + 14] = (f14 * f7 + f2 * f15 + f36 * f3 - (f6 * f15 + f35 * f3 + f39 * f7)) * f41;
        arrf[n + 15] = (f6 * f11 + f37 * f3 + f10 * f7 - (f40 * f7 + f2 * f11 + f38 * f3)) * f41;
        return true;
    }

    public static float length(float f, float f2, float f3) {
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public static native void multiplyMM(float[] var0, int var1, float[] var2, int var3, float[] var4, int var5);

    public static native void multiplyMV(float[] var0, int var1, float[] var2, int var3, float[] var4, int var5);

    public static void orthoM(float[] arrf, int n, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f != f2) {
            if (f3 != f4) {
                if (f5 != f6) {
                    float f7 = 1.0f / (f2 - f);
                    float f8 = 1.0f / (f4 - f3);
                    float f9 = 1.0f / (f6 - f5);
                    f = -(f2 + f);
                    f2 = -(f4 + f3);
                    f3 = -(f6 + f5);
                    arrf[n + 0] = f7 * 2.0f;
                    arrf[n + 5] = 2.0f * f8;
                    arrf[n + 10] = -2.0f * f9;
                    arrf[n + 12] = f * f7;
                    arrf[n + 13] = f2 * f8;
                    arrf[n + 14] = f3 * f9;
                    arrf[n + 15] = 1.0f;
                    arrf[n + 1] = 0.0f;
                    arrf[n + 2] = 0.0f;
                    arrf[n + 3] = 0.0f;
                    arrf[n + 4] = 0.0f;
                    arrf[n + 6] = 0.0f;
                    arrf[n + 7] = 0.0f;
                    arrf[n + 8] = 0.0f;
                    arrf[n + 9] = 0.0f;
                    arrf[n + 11] = 0.0f;
                    return;
                }
                throw new IllegalArgumentException("near == far");
            }
            throw new IllegalArgumentException("bottom == top");
        }
        throw new IllegalArgumentException("left == right");
    }

    public static void perspectiveM(float[] arrf, int n, float f, float f2, float f3, float f4) {
        float f5 = 1.0f / (float)Math.tan((double)f * 0.008726646259971648);
        f = 1.0f / (f3 - f4);
        arrf[n + 0] = f5 / f2;
        arrf[n + 1] = 0.0f;
        arrf[n + 2] = 0.0f;
        arrf[n + 3] = 0.0f;
        arrf[n + 4] = 0.0f;
        arrf[n + 5] = f5;
        arrf[n + 6] = 0.0f;
        arrf[n + 7] = 0.0f;
        arrf[n + 8] = 0.0f;
        arrf[n + 9] = 0.0f;
        arrf[n + 10] = (f4 + f3) * f;
        arrf[n + 11] = -1.0f;
        arrf[n + 12] = 0.0f;
        arrf[n + 13] = 0.0f;
        arrf[n + 14] = 2.0f * f4 * f3 * f;
        arrf[n + 15] = 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void rotateM(float[] arrf, int n, float f, float f2, float f3, float f4) {
        float[] arrf2 = sTemp;
        synchronized (arrf2) {
            Matrix.setRotateM(sTemp, 0, f, f2, f3, f4);
            Matrix.multiplyMM(sTemp, 16, arrf, n, sTemp, 0);
            System.arraycopy(sTemp, 16, arrf, n, 16);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void rotateM(float[] arrf, int n, float[] arrf2, int n2, float f, float f2, float f3, float f4) {
        float[] arrf3 = sTemp;
        synchronized (arrf3) {
            Matrix.setRotateM(sTemp, 0, f, f2, f3, f4);
            Matrix.multiplyMM(arrf, n, arrf2, n2, sTemp, 0);
            return;
        }
    }

    public static void scaleM(float[] arrf, int n, float f, float f2, float f3) {
        for (int i = 0; i < 4; ++i) {
            int n2 = n + i;
            arrf[n2] = arrf[n2] * f;
            int n3 = n2 + 4;
            arrf[n3] = arrf[n3] * f2;
            n3 = n2 + 8;
            arrf[n3] = arrf[n3] * f3;
        }
    }

    public static void scaleM(float[] arrf, int n, float[] arrf2, int n2, float f, float f2, float f3) {
        for (int i = 0; i < 4; ++i) {
            int n3 = n + i;
            int n4 = n2 + i;
            arrf[n3] = arrf2[n4] * f;
            arrf[n3 + 4] = arrf2[n4 + 4] * f2;
            arrf[n3 + 8] = arrf2[n4 + 8] * f3;
            arrf[n3 + 12] = arrf2[n4 + 12];
        }
    }

    public static void setIdentityM(float[] arrf, int n) {
        int n2;
        for (n2 = 0; n2 < 16; ++n2) {
            arrf[n + n2] = 0.0f;
        }
        for (n2 = 0; n2 < 16; n2 += 5) {
            arrf[n + n2] = 1.0f;
        }
    }

    public static void setLookAtM(float[] arrf, int n, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = 1.0f / Matrix.length(f4 -= f, f5 -= f2, f6 -= f3);
        f4 *= f10;
        f5 *= f10;
        f6 *= f10;
        f10 = f5 * f9 - f6 * f8;
        f9 = f6 * f7 - f4 * f9;
        float f11 = f4 * f8 - f5 * f7;
        f8 = 1.0f / Matrix.length(f10, f9, f11);
        f7 = f10 * f8;
        f9 *= f8;
        f8 = f11 * f8;
        arrf[n + 0] = f7;
        arrf[n + 1] = f9 * f6 - f8 * f5;
        arrf[n + 2] = -f4;
        arrf[n + 3] = 0.0f;
        arrf[n + 4] = f9;
        arrf[n + 5] = f8 * f4 - f7 * f6;
        arrf[n + 6] = -f5;
        arrf[n + 7] = 0.0f;
        arrf[n + 8] = f8;
        arrf[n + 9] = f7 * f5 - f9 * f4;
        arrf[n + 10] = -f6;
        arrf[n + 11] = 0.0f;
        arrf[n + 12] = 0.0f;
        arrf[n + 13] = 0.0f;
        arrf[n + 14] = 0.0f;
        arrf[n + 15] = 1.0f;
        Matrix.translateM(arrf, n, -f, -f2, -f3);
    }

    public static void setRotateEulerM(float[] arrf, int n, float f, float f2, float f3) {
        float f4 = f * 0.017453292f;
        float f5 = f2 * 0.017453292f;
        float f6 = 0.017453292f * f3;
        f = (float)Math.cos(f4);
        f3 = (float)Math.sin(f4);
        f2 = (float)Math.cos(f5);
        f4 = (float)Math.sin(f5);
        f5 = (float)Math.cos(f6);
        float f7 = (float)Math.sin(f6);
        float f8 = f * f4;
        f6 = f3 * f4;
        arrf[n + 0] = f2 * f5;
        arrf[n + 1] = -f2 * f7;
        arrf[n + 2] = f4;
        arrf[n + 3] = 0.0f;
        arrf[n + 4] = f8 * f5 + f * f7;
        arrf[n + 5] = -f8 * f7 + f * f5;
        arrf[n + 6] = -f3 * f2;
        arrf[n + 7] = 0.0f;
        arrf[n + 8] = -f6 * f5 + f3 * f7;
        arrf[n + 9] = f6 * f7 + f3 * f5;
        arrf[n + 10] = f * f2;
        arrf[n + 11] = 0.0f;
        arrf[n + 12] = 0.0f;
        arrf[n + 13] = 0.0f;
        arrf[n + 14] = 0.0f;
        arrf[n + 15] = 1.0f;
    }

    public static void setRotateM(float[] arrf, int n, float f, float f2, float f3, float f4) {
        arrf[n + 3] = 0.0f;
        arrf[n + 7] = 0.0f;
        arrf[n + 11] = 0.0f;
        arrf[n + 12] = 0.0f;
        arrf[n + 13] = 0.0f;
        arrf[n + 14] = 0.0f;
        arrf[n + 15] = 1.0f;
        f = 0.017453292f * f;
        float f5 = (float)Math.sin(f);
        float f6 = (float)Math.cos(f);
        if (1.0f == f2 && 0.0f == f3 && 0.0f == f4) {
            arrf[n + 5] = f6;
            arrf[n + 10] = f6;
            arrf[n + 6] = f5;
            arrf[n + 9] = -f5;
            arrf[n + 1] = 0.0f;
            arrf[n + 2] = 0.0f;
            arrf[n + 4] = 0.0f;
            arrf[n + 8] = 0.0f;
            arrf[n + 0] = 1.0f;
        } else if (0.0f == f2 && 1.0f == f3 && 0.0f == f4) {
            arrf[n + 0] = f6;
            arrf[n + 10] = f6;
            arrf[n + 8] = f5;
            arrf[n + 2] = -f5;
            arrf[n + 1] = 0.0f;
            arrf[n + 4] = 0.0f;
            arrf[n + 6] = 0.0f;
            arrf[n + 9] = 0.0f;
            arrf[n + 5] = 1.0f;
        } else if (0.0f == f2 && 0.0f == f3 && 1.0f == f4) {
            arrf[n + 0] = f6;
            arrf[n + 5] = f6;
            arrf[n + 1] = f5;
            arrf[n + 4] = -f5;
            arrf[n + 2] = 0.0f;
            arrf[n + 6] = 0.0f;
            arrf[n + 8] = 0.0f;
            arrf[n + 9] = 0.0f;
            arrf[n + 10] = 1.0f;
        } else {
            f = Matrix.length(f2, f3, f4);
            if (1.0f != f) {
                f = 1.0f / f;
                f2 *= f;
                f3 *= f;
                f = f4 * f;
            } else {
                f = f4;
            }
            float f7 = 1.0f - f6;
            float f8 = f2 * f3;
            f4 = f3 * f;
            float f9 = f * f2;
            float f10 = f2 * f5;
            float f11 = f3 * f5;
            f5 = f * f5;
            arrf[n + 0] = f2 * f2 * f7 + f6;
            arrf[n + 4] = f8 * f7 - f5;
            arrf[n + 8] = f9 * f7 + f11;
            arrf[n + 1] = f8 * f7 + f5;
            arrf[n + 5] = f3 * f3 * f7 + f6;
            arrf[n + 9] = f4 * f7 - f10;
            arrf[n + 2] = f9 * f7 - f11;
            arrf[n + 6] = f4 * f7 + f10;
            arrf[n + 10] = f * f * f7 + f6;
        }
    }

    public static void translateM(float[] arrf, int n, float f, float f2, float f3) {
        for (int i = 0; i < 4; ++i) {
            int n2 = n + i;
            int n3 = n2 + 12;
            arrf[n3] = arrf[n3] + (arrf[n2] * f + arrf[n2 + 4] * f2 + arrf[n2 + 8] * f3);
        }
    }

    public static void translateM(float[] arrf, int n, float[] arrf2, int n2, float f, float f2, float f3) {
        int n3;
        for (n3 = 0; n3 < 12; ++n3) {
            arrf[n + n3] = arrf2[n2 + n3];
        }
        for (n3 = 0; n3 < 4; ++n3) {
            int n4 = n2 + n3;
            arrf[n + n3 + 12] = arrf2[n4] * f + arrf2[n4 + 4] * f2 + arrf2[n4 + 8] * f3 + arrf2[n4 + 12];
        }
    }

    public static void transposeM(float[] arrf, int n, float[] arrf2, int n2) {
        for (int i = 0; i < 4; ++i) {
            int n3 = i * 4 + n2;
            arrf[i + n] = arrf2[n3];
            arrf[i + 4 + n] = arrf2[n3 + 1];
            arrf[i + 8 + n] = arrf2[n3 + 2];
            arrf[i + 12 + n] = arrf2[n3 + 3];
        }
    }
}

