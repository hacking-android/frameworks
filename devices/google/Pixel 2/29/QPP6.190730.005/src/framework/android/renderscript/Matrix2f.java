/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Matrix2f {
    final float[] mMat;

    public Matrix2f() {
        this.mMat = new float[4];
        this.loadIdentity();
    }

    public Matrix2f(float[] arrf) {
        float[] arrf2 = this.mMat = new float[4];
        System.arraycopy(arrf, 0, arrf2, 0, arrf2.length);
    }

    public float get(int n, int n2) {
        return this.mMat[n * 2 + n2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public void load(Matrix2f arrf) {
        float[] arrf2 = arrf.getArray();
        arrf = this.mMat;
        System.arraycopy(arrf2, 0, arrf, 0, arrf.length);
    }

    public void loadIdentity() {
        float[] arrf = this.mMat;
        arrf[0] = 1.0f;
        arrf[1] = 0.0f;
        arrf[2] = 0.0f;
        arrf[3] = 1.0f;
    }

    public void loadMultiply(Matrix2f matrix2f, Matrix2f matrix2f2) {
        for (int i = 0; i < 2; ++i) {
            float f = 0.0f;
            float f2 = 0.0f;
            for (int j = 0; j < 2; ++j) {
                float f3 = matrix2f2.get(i, j);
                f += matrix2f.get(j, 0) * f3;
                f2 += matrix2f.get(j, 1) * f3;
            }
            this.set(i, 0, f);
            this.set(i, 1, f2);
        }
    }

    public void loadRotate(float f) {
        float f2 = f * 0.017453292f;
        f = (float)Math.cos(f2);
        f2 = (float)Math.sin(f2);
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[1] = -f2;
        arrf[2] = f2;
        arrf[3] = f;
    }

    public void loadScale(float f, float f2) {
        this.loadIdentity();
        float[] arrf = this.mMat;
        arrf[0] = f;
        arrf[3] = f2;
    }

    public void multiply(Matrix2f matrix2f) {
        Matrix2f matrix2f2 = new Matrix2f();
        matrix2f2.loadMultiply(this, matrix2f);
        this.load(matrix2f2);
    }

    public void rotate(float f) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadRotate(f);
        this.multiply(matrix2f);
    }

    public void scale(float f, float f2) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadScale(f, f2);
        this.multiply(matrix2f);
    }

    public void set(int n, int n2, float f) {
        this.mMat[n * 2 + n2] = f;
    }

    public void transpose() {
        float[] arrf = this.mMat;
        float f = arrf[1];
        arrf[1] = arrf[2];
        arrf[2] = f;
    }
}

