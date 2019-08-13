/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Matrix3f;

public class Matrix4f {
    @UnsupportedAppUsage
    final float[] mMat;

    public Matrix4f() {
        this.mMat = new float[16];
        this.loadIdentity();
    }

    public Matrix4f(float[] arrf) {
        float[] arrf2 = this.mMat = new float[16];
        System.arraycopy(arrf, 0, arrf2, 0, arrf2.length);
    }

    private float computeCofactor(int n, int n2) {
        float f;
        block0 : {
            int n3 = (n + 1) % 4;
            int n4 = (n + 2) % 4;
            int n5 = (n + 3) % 4;
            int n6 = (n2 + 1) % 4;
            int n7 = (n2 + 2) % 4;
            int n8 = (n2 + 3) % 4;
            float[] arrf = this.mMat;
            f = arrf[n6 * 4 + n3] * (arrf[n7 * 4 + n4] * arrf[n8 * 4 + n5] - arrf[n8 * 4 + n4] * arrf[n7 * 4 + n5]) - arrf[n7 * 4 + n3] * (arrf[n6 * 4 + n4] * arrf[n8 * 4 + n5] - arrf[n8 * 4 + n4] * arrf[n6 * 4 + n5]) + arrf[n8 * 4 + n3] * (arrf[n6 * 4 + n4] * arrf[n7 * 4 + n5] - arrf[n7 * 4 + n4] * arrf[n6 * 4 + n5]);
            if ((n + n2 & 1) == 0) break block0;
            f = -f;
        }
        return f;
    }

    public float get(int n, int n2) {
        return this.mMat[n * 4 + n2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public boolean inverse() {
        int n;
        Matrix4f matrix4f = new Matrix4f();
        for (n = 0; n < 4; ++n) {
            for (int i = 0; i < 4; ++i) {
                matrix4f.mMat[n * 4 + i] = this.computeCofactor(n, i);
            }
        }
        float[] arrf = this.mMat;
        float f = arrf[0];
        float[] arrf2 = matrix4f.mMat;
        if ((double)Math.abs(f = f * arrf2[0] + arrf[4] * arrf2[1] + arrf[8] * arrf2[2] + arrf[12] * arrf2[3]) < 1.0E-6) {
            return false;
        }
        f = 1.0f / f;
        for (n = 0; n < 16; ++n) {
            this.mMat[n] = matrix4f.mMat[n] * f;
        }
        return true;
    }

    public boolean inverseTranspose() {
        int n;
        Matrix4f matrix4f = new Matrix4f();
        for (n = 0; n < 4; ++n) {
            for (int i = 0; i < 4; ++i) {
                matrix4f.mMat[i * 4 + n] = this.computeCofactor(n, i);
            }
        }
        float[] arrf = this.mMat;
        float f = arrf[0];
        float[] arrf2 = matrix4f.mMat;
        if ((double)Math.abs(f = f * arrf2[0] + arrf[4] * arrf2[4] + arrf[8] * arrf2[8] + arrf[12] * arrf2[12]) < 1.0E-6) {
            return false;
        }
        f = 1.0f / f;
        for (n = 0; n < 16; ++n) {
            this.mMat[n] = matrix4f.mMat[n] * f;
        }
        return true;
    }

    public void load(Matrix3f arrf) {
        this.mMat[0] = arrf.mMat[0];
        this.mMat[1] = arrf.mMat[1];
        this.mMat[2] = arrf.mMat[2];
        float[] arrf2 = this.mMat;
        arrf2[3] = 0.0f;
        arrf2[4] = arrf.mMat[3];
        this.mMat[5] = arrf.mMat[4];
        this.mMat[6] = arrf.mMat[5];
        arrf2 = this.mMat;
        arrf2[7] = 0.0f;
        arrf2[8] = arrf.mMat[6];
        this.mMat[9] = arrf.mMat[7];
        this.mMat[10] = arrf.mMat[8];
        arrf = this.mMat;
        arrf[11] = 0.0f;
        arrf[12] = 0.0f;
        arrf[13] = 0.0f;
        arrf[14] = 0.0f;
        arrf[15] = 1.0f;
    }

    public void load(Matrix4f arrf) {
        float[] arrf2 = arrf.getArray();
        arrf = this.mMat;
        System.arraycopy(arrf2, 0, arrf, 0, arrf.length);
    }

    public void loadFrustum(float f, float f2, float f3, float f4, float f5, float f6) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = f5 * 2.0f / (f2 - f);
        arrf[5] = 2.0f * f5 / (f4 - f3);
        arrf[8] = (f2 + f) / (f2 - f);
        arrf[9] = (f4 + f3) / (f4 - f3);
        arrf[10] = -(f6 + f5) / (f6 - f5);
        arrf[11] = -1.0f;
        arrf[14] = -2.0f * f6 * f5 / (f6 - f5);
        arrf[15] = 0.0f;
    }

    public void loadIdentity() {
        float[] arrf = this.mMat;
        arrf[0] = 1.0f;
        arrf[1] = 0.0f;
        arrf[2] = 0.0f;
        arrf[3] = 0.0f;
        arrf[4] = 0.0f;
        arrf[5] = 1.0f;
        arrf[6] = 0.0f;
        arrf[7] = 0.0f;
        arrf[8] = 0.0f;
        arrf[9] = 0.0f;
        arrf[10] = 1.0f;
        arrf[11] = 0.0f;
        arrf[12] = 0.0f;
        arrf[13] = 0.0f;
        arrf[14] = 0.0f;
        arrf[15] = 1.0f;
    }

    public void loadMultiply(Matrix4f matrix4f, Matrix4f matrix4f2) {
        for (int i = 0; i < 4; ++i) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            for (int j = 0; j < 4; ++j) {
                float f5 = matrix4f2.get(i, j);
                f += matrix4f.get(j, 0) * f5;
                f2 += matrix4f.get(j, 1) * f5;
                f3 += matrix4f.get(j, 2) * f5;
                f4 += matrix4f.get(j, 3) * f5;
            }
            this.set(i, 0, f);
            this.set(i, 1, f2);
            this.set(i, 2, f3);
            this.set(i, 3, f4);
        }
    }

    public void loadOrtho(float f, float f2, float f3, float f4, float f5, float f6) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = 2.0f / (f2 - f);
        arrf[5] = 2.0f / (f4 - f3);
        arrf[10] = -2.0f / (f6 - f5);
        arrf[12] = -(f2 + f) / (f2 - f);
        arrf[13] = -(f4 + f3) / (f4 - f3);
        arrf[14] = -(f6 + f5) / (f6 - f5);
    }

    public void loadOrthoWindow(int n, int n2) {
        this.loadOrtho(0.0f, n, n2, 0.0f, -1.0f, 1.0f);
    }

    public void loadPerspective(float f, float f2, float f3, float f4) {
        f = (float)Math.tan((float)((double)f * 3.141592653589793 / 360.0)) * f3;
        float f5 = -f;
        this.loadFrustum(f5 * f2, f * f2, f5, f, f3, f4);
    }

    public void loadProjectionNormalized(int n, int n2) {
        Matrix4f matrix4f = new Matrix4f();
        Matrix4f matrix4f2 = new Matrix4f();
        if (n > n2) {
            float f = (float)n / (float)n2;
            matrix4f.loadFrustum(-f, f, -1.0f, 1.0f, 1.0f, 100.0f);
        } else {
            float f = (float)n2 / (float)n;
            matrix4f.loadFrustum(-1.0f, 1.0f, -f, f, 1.0f, 100.0f);
        }
        matrix4f2.loadRotate(180.0f, 0.0f, 1.0f, 0.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadScale(-2.0f, 2.0f, 1.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadTranslate(0.0f, 0.0f, 2.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        this.load(matrix4f);
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
        float f5;
        float[] arrf = this.mMat;
        arrf[3] = 0.0f;
        arrf[7] = 0.0f;
        arrf[11] = 0.0f;
        arrf[12] = 0.0f;
        arrf[13] = 0.0f;
        arrf[14] = 0.0f;
        arrf[15] = 1.0f;
        f = 0.017453292f * f;
        float f6 = (float)Math.cos(f);
        float f7 = (float)Math.sin(f);
        f = (float)Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
        if (f == 1.0f) {
            f5 = 1.0f / f;
            f = f2 * f5;
            f2 = f3 * f5;
            f3 = f;
            f = f2;
            f2 = f4 *= f5;
        } else {
            f5 = f2;
            f = f3;
            f2 = f4;
            f3 = f5;
        }
        float f8 = 1.0f - f6;
        f5 = f3 * f;
        float f9 = f * f2;
        float f10 = f2 * f3;
        float f11 = f3 * f7;
        f4 = f * f7;
        f7 = f2 * f7;
        arrf = this.mMat;
        arrf[0] = f3 * f3 * f8 + f6;
        arrf[4] = f5 * f8 - f7;
        arrf[8] = f10 * f8 + f4;
        arrf[1] = f5 * f8 + f7;
        arrf[5] = f * f * f8 + f6;
        arrf[9] = f9 * f8 - f11;
        arrf[2] = f10 * f8 - f4;
        arrf[6] = f9 * f8 + f11;
        arrf[10] = f2 * f2 * f8 + f6;
    }

    public void loadScale(float f, float f2, float f3) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[5] = f2;
        arrf[10] = f3;
    }

    public void loadTranslate(float f, float f2, float f3) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[12] = f;
        arrf[13] = f2;
        arrf[14] = f3;
    }

    public void multiply(Matrix4f matrix4f) {
        Matrix4f matrix4f2 = new Matrix4f();
        matrix4f2.loadMultiply(this, matrix4f);
        this.load(matrix4f2);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadRotate(f, f2, f3, f4);
        this.multiply(matrix4f);
    }

    public void scale(float f, float f2, float f3) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadScale(f, f2, f3);
        this.multiply(matrix4f);
    }

    public void set(int n, int n2, float f) {
        this.mMat[n * 4 + n2] = f;
    }

    public void translate(float f, float f2, float f3) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadTranslate(f, f2, f3);
        this.multiply(matrix4f);
    }

    public void transpose() {
        for (int i = 0; i < 3; ++i) {
            for (int j = i + 1; j < 4; ++j) {
                float[] arrf = this.mMat;
                float f = arrf[i * 4 + j];
                arrf[i * 4 + j] = arrf[j * 4 + i];
                arrf[j * 4 + i] = f;
            }
        }
    }
}

