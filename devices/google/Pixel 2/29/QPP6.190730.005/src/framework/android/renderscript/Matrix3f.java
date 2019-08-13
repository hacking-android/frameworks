/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Matrix3f {
    final float[] mMat;

    public Matrix3f() {
        this.mMat = new float[9];
        this.loadIdentity();
    }

    public Matrix3f(float[] arrf) {
        float[] arrf2 = this.mMat = new float[9];
        System.arraycopy(arrf, 0, arrf2, 0, arrf2.length);
    }

    public float get(int n, int n2) {
        return this.mMat[n * 3 + n2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public void load(Matrix3f arrf) {
        float[] arrf2 = arrf.getArray();
        arrf = this.mMat;
        System.arraycopy(arrf2, 0, arrf, 0, arrf.length);
    }

    public void loadIdentity() {
        float[] arrf = this.mMat;
        arrf[0] = 1.0f;
        arrf[1] = 0.0f;
        arrf[2] = 0.0f;
        arrf[3] = 0.0f;
        arrf[4] = 1.0f;
        arrf[5] = 0.0f;
        arrf[6] = 0.0f;
        arrf[7] = 0.0f;
        arrf[8] = 1.0f;
    }

    public void loadMultiply(Matrix3f matrix3f, Matrix3f matrix3f2) {
        for (int i = 0; i < 3; ++i) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (int j = 0; j < 3; ++j) {
                float f4 = matrix3f2.get(i, j);
                f += matrix3f.get(j, 0) * f4;
                f2 += matrix3f.get(j, 1) * f4;
                f3 += matrix3f.get(j, 2) * f4;
            }
            this.set(i, 0, f);
            this.set(i, 1, f2);
            this.set(i, 2, f3);
        }
    }

    public void loadRotate(float f) {
        this.loadIdentity();
        float f2 = f * 0.017453292f;
        f = (float)Math.cos(f2);
        f2 = (float)Math.sin(f2);
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[1] = -f2;
        arrf[3] = f2;
        arrf[4] = f;
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
        f = 0.017453292f * f;
        float f5 = (float)Math.cos(f);
        float f6 = (float)Math.sin(f);
        f = (float)Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
        if (f == 1.0f) {
            f = 1.0f / f;
            f2 *= f;
            f3 *= f;
            f = f4 * f;
        } else {
            f = f4;
        }
        float f7 = 1.0f - f5;
        f4 = f2 * f3;
        float f8 = f3 * f;
        float f9 = f * f2;
        float f10 = f2 * f6;
        float f11 = f3 * f6;
        f6 = f * f6;
        float[] arrf = this.mMat;
        arrf[0] = f2 * f2 * f7 + f5;
        arrf[3] = f4 * f7 - f6;
        arrf[6] = f9 * f7 + f11;
        arrf[1] = f4 * f7 + f6;
        arrf[4] = f3 * f3 * f7 + f5;
        arrf[7] = f8 * f7 - f10;
        arrf[2] = f9 * f7 - f11;
        arrf[5] = f8 * f7 + f10;
        arrf[8] = f * f * f7 + f5;
    }

    public void loadScale(float f, float f2) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[4] = f2;
    }

    public void loadScale(float f, float f2, float f3) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[4] = f2;
        arrf[8] = f3;
    }

    public void loadTranslate(float f, float f2) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[6] = f;
        arrf[7] = f2;
    }

    public void multiply(Matrix3f matrix3f) {
        Matrix3f matrix3f2 = new Matrix3f();
        matrix3f2.loadMultiply(this, matrix3f);
        this.load(matrix3f2);
    }

    public void rotate(float f) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadRotate(f);
        this.multiply(matrix3f);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadRotate(f, f2, f3, f4);
        this.multiply(matrix3f);
    }

    public void scale(float f, float f2) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadScale(f, f2);
        this.multiply(matrix3f);
    }

    public void scale(float f, float f2, float f3) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadScale(f, f2, f3);
        this.multiply(matrix3f);
    }

    public void set(int n, int n2, float f) {
        this.mMat[n * 3 + n2] = f;
    }

    public void translate(float f, float f2) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadTranslate(f, f2);
        this.multiply(matrix3f);
    }

    public void transpose() {
        for (int i = 0; i < 2; ++i) {
            for (int j = i + 1; j < 3; ++j) {
                float[] arrf = this.mMat;
                float f = arrf[i * 3 + j];
                arrf[i * 3 + j] = arrf[j * 3 + i];
                arrf[j * 3 + i] = f;
            }
        }
    }
}

