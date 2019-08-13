/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.Matrix;
import javax.microedition.khronos.opengles.GL10;

public class GLU {
    private static final float[] sScratch = new float[32];

    public static String gluErrorString(int n) {
        if (n != 0) {
            switch (n) {
                default: {
                    return null;
                }
                case 1285: {
                    return "out of memory";
                }
                case 1284: {
                    return "stack underflow";
                }
                case 1283: {
                    return "stack overflow";
                }
                case 1282: {
                    return "invalid operation";
                }
                case 1281: {
                    return "invalid value";
                }
                case 1280: 
            }
            return "invalid enum";
        }
        return "no error";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void gluLookAt(GL10 gL10, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float[] arrf = sScratch;
        synchronized (arrf) {
            Matrix.setLookAtM(arrf, 0, f, f2, f3, f4, f5, f6, f7, f8, f9);
            gL10.glMultMatrixf(arrf, 0);
            return;
        }
    }

    public static void gluOrtho2D(GL10 gL10, float f, float f2, float f3, float f4) {
        gL10.glOrthof(f, f2, f3, f4, -1.0f, 1.0f);
    }

    public static void gluPerspective(GL10 gL10, float f, float f2, float f3, float f4) {
        float f5 = (float)Math.tan((double)f * 0.008726646259971648) * f3;
        f = -f5;
        gL10.glFrustumf(f * f2, f5 * f2, f, f5, f3, f4);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int gluProject(float f, float f2, float f3, float[] arrf, int n, float[] arrf2, int n2, int[] arrn, int n3, float[] arrf3, int n4) {
        float[] arrf4 = sScratch;
        synchronized (arrf4) {
            Matrix.multiplyMM(arrf4, 0, arrf2, n2, arrf, n);
            arrf4[16] = f;
            arrf4[17] = f2;
            arrf4[18] = f3;
            arrf4[19] = 1.0f;
            Matrix.multiplyMV(arrf4, 20, arrf4, 0, arrf4, 16);
            f = arrf4[23];
            if (f == 0.0f) {
                return 0;
            }
            f = 1.0f / f;
            arrf3[n4] = (float)arrn[n3] + (float)arrn[n3 + 2] * (arrf4[20] * f + 1.0f) * 0.5f;
            arrf3[n4 + 1] = (float)arrn[n3 + 1] + (float)arrn[n3 + 3] * (arrf4[21] * f + 1.0f) * 0.5f;
            arrf3[n4 + 2] = (arrf4[22] * f + 1.0f) * 0.5f;
            return 1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int gluUnProject(float f, float f2, float f3, float[] arrf, int n, float[] arrf2, int n2, int[] arrn, int n3, float[] arrf3, int n4) {
        float[] arrf4 = sScratch;
        synchronized (arrf4) {
            Matrix.multiplyMM(arrf4, 0, arrf2, n2, arrf, n);
            if (!Matrix.invertM(arrf4, 16, arrf4, 0)) {
                return 0;
            }
            arrf4[0] = (f - (float)arrn[n3 + 0]) * 2.0f / (float)arrn[n3 + 2] - 1.0f;
            arrf4[1] = (f2 - (float)arrn[n3 + 1]) * 2.0f / (float)arrn[n3 + 3] - 1.0f;
            arrf4[2] = f3 * 2.0f - 1.0f;
            arrf4[3] = 1.0f;
            Matrix.multiplyMV(arrf3, n4, arrf4, 16, arrf4, 0);
            return 1;
        }
    }
}

