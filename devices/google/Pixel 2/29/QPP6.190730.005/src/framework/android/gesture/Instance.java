/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.Gesture;
import android.gesture.GestureStroke;
import android.gesture.GestureUtils;
import java.util.ArrayList;

class Instance {
    private static final float[] ORIENTATIONS = new float[]{0.0f, 0.7853982f, 1.5707964f, 2.3561945f, 3.1415927f, 0.0f, -0.7853982f, -1.5707964f, -2.3561945f, -3.1415927f};
    private static final int PATCH_SAMPLE_SIZE = 16;
    private static final int SEQUENCE_SAMPLE_SIZE = 16;
    final long id;
    final String label;
    final float[] vector;

    private Instance(long l, float[] arrf, String string2) {
        this.id = l;
        this.vector = arrf;
        this.label = string2;
    }

    static Instance createInstance(int n, int n2, Gesture object, String string2) {
        if (n == 2) {
            float[] arrf = Instance.temporalSampler(n2, (Gesture)object);
            object = new Instance(((Gesture)object).getID(), arrf, string2);
            Instance.super.normalize();
        } else {
            float[] arrf = Instance.spatialSampler((Gesture)object);
            object = new Instance(((Gesture)object).getID(), arrf, string2);
        }
        return object;
    }

    private void normalize() {
        int n;
        float[] arrf = this.vector;
        float f = 0.0f;
        int n2 = arrf.length;
        for (n = 0; n < n2; ++n) {
            f += arrf[n] * arrf[n];
        }
        f = (float)Math.sqrt(f);
        for (n = 0; n < n2; ++n) {
            arrf[n] = arrf[n] / f;
        }
    }

    private static float[] spatialSampler(Gesture gesture) {
        return GestureUtils.spatialSampling(gesture, 16, false);
    }

    private static float[] temporalSampler(int n, Gesture arrf) {
        float f;
        float[] arrf2 = GestureUtils.temporalSampling(arrf.getStrokes().get(0), 16);
        arrf = GestureUtils.computeCentroid(arrf2);
        float f2 = (float)Math.atan2(arrf2[1] - arrf[1], arrf2[0] - arrf[0]);
        float f3 = f = -f2;
        if (n != 1) {
            int n2 = ORIENTATIONS.length;
            n = 0;
            do {
                f3 = f;
                if (n >= n2) break;
                float f4 = ORIENTATIONS[n] - f2;
                f3 = f;
                if (Math.abs(f4) < Math.abs(f)) {
                    f3 = f4;
                }
                ++n;
                f = f3;
            } while (true);
        }
        GestureUtils.translate(arrf2, -arrf[0], -arrf[1]);
        GestureUtils.rotate(arrf2, f3);
        return arrf2;
    }
}

