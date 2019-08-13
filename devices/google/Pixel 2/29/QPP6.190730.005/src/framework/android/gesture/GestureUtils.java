/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.Gesture;
import android.gesture.GesturePoint;
import android.gesture.GestureStroke;
import android.gesture.OrientedBoundingBox;
import android.graphics.RectF;
import android.util.Log;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class GestureUtils {
    private static final float NONUNIFORM_SCALE = (float)Math.sqrt(2.0);
    private static final float SCALING_THRESHOLD = 0.26f;

    private GestureUtils() {
    }

    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (IOException iOException) {
                Log.e("Gestures", "Could not close stream", iOException);
            }
        }
    }

    static float[] computeCentroid(float[] arrf) {
        float f = 0.0f;
        float f2 = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            f += arrf[i];
            f2 += arrf[++i];
        }
        return new float[]{f * 2.0f / (float)n, 2.0f * f2 / (float)n};
    }

    private static float[][] computeCoVariance(float[] arrf) {
        float[][] arrf2 = new float[2][2];
        arrf2[0][0] = 0.0f;
        arrf2[0][1] = 0.0f;
        arrf2[1][0] = 0.0f;
        arrf2[1][1] = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            float f = arrf[i];
            float f2 = arrf[++i];
            float[] arrf3 = arrf2[0];
            arrf3[0] = arrf3[0] + f * f;
            arrf3 = arrf2[0];
            arrf3[1] = arrf3[1] + f * f2;
            arrf2[1][0] = arrf2[0][1];
            arrf3 = arrf2[1];
            arrf3[1] = arrf3[1] + f2 * f2;
        }
        arrf = arrf2[0];
        arrf[0] = arrf[0] / (float)(n / 2);
        arrf = arrf2[0];
        arrf[1] = arrf[1] / (float)(n / 2);
        arrf = arrf2[1];
        arrf[0] = arrf[0] / (float)(n / 2);
        arrf = arrf2[1];
        arrf[1] = arrf[1] / (float)(n / 2);
        return arrf2;
    }

    private static float[] computeOrientation(float[][] arrf) {
        float[] arrf2 = new float[2];
        if (arrf[0][1] == 0.0f || arrf[1][0] == 0.0f) {
            arrf2[0] = 1.0f;
            arrf2[1] = 0.0f;
        }
        float f = -arrf[0][0];
        float f2 = arrf[1][1];
        float f3 = arrf[0][0];
        float f4 = arrf[1][1];
        float f5 = arrf[0][1];
        float f6 = arrf[1][0];
        f = (f - f2) / 2.0f;
        f6 = (float)Math.sqrt(Math.pow(f, 2.0) - (double)(f3 * f4 - f5 * f6));
        f3 = -f + f6;
        if (f3 == (f6 = -f - f6)) {
            arrf2[0] = 0.0f;
            arrf2[1] = 0.0f;
        } else {
            if (!(f3 > f6)) {
                f3 = f6;
            }
            arrf2[0] = 1.0f;
            arrf2[1] = (f3 - arrf[0][0]) / arrf[0][1];
        }
        return arrf2;
    }

    public static OrientedBoundingBox computeOrientedBoundingBox(ArrayList<GesturePoint> arrayList) {
        int n = arrayList.size();
        float[] arrf = new float[n * 2];
        for (int i = 0; i < n; ++i) {
            GesturePoint gesturePoint = arrayList.get(i);
            int n2 = i * 2;
            arrf[n2] = gesturePoint.x;
            arrf[n2 + 1] = gesturePoint.y;
        }
        return GestureUtils.computeOrientedBoundingBox(arrf, GestureUtils.computeCentroid(arrf));
    }

    public static OrientedBoundingBox computeOrientedBoundingBox(float[] arrf) {
        int n = arrf.length;
        float[] arrf2 = new float[n];
        for (int i = 0; i < n; ++i) {
            arrf2[i] = arrf[i];
        }
        return GestureUtils.computeOrientedBoundingBox(arrf2, GestureUtils.computeCentroid(arrf2));
    }

    private static OrientedBoundingBox computeOrientedBoundingBox(float[] arrf, float[] arrf2) {
        float f;
        GestureUtils.translate(arrf, -arrf2[0], -arrf2[1]);
        float[] arrf3 = GestureUtils.computeOrientation(GestureUtils.computeCoVariance(arrf));
        if (arrf3[0] == 0.0f && arrf3[1] == 0.0f) {
            f = -1.5707964f;
        } else {
            f = (float)Math.atan2(arrf3[1], arrf3[0]);
            GestureUtils.rotate(arrf, -f);
        }
        float f2 = Float.MAX_VALUE;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MIN_VALUE;
        float f5 = Float.MIN_VALUE;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            float f6 = f2;
            if (arrf[i] < f2) {
                f6 = arrf[i];
            }
            float f7 = f4;
            if (arrf[i] > f4) {
                f7 = arrf[i];
            }
            ++i;
            f4 = f3;
            if (arrf[i] < f3) {
                f4 = arrf[i];
            }
            float f8 = f5;
            if (arrf[i] > f5) {
                f8 = arrf[i];
            }
            f2 = f6;
            f3 = f4;
            f4 = f7;
            f5 = f8;
        }
        return new OrientedBoundingBox((float)((double)(180.0f * f) / 3.141592653589793), arrf2[0], arrf2[1], f4 - f2, f5 - f3);
    }

    static float computeStraightness(float[] arrf) {
        float f = GestureUtils.computeTotalLength(arrf);
        float f2 = arrf[2];
        float f3 = arrf[0];
        float f4 = arrf[3];
        float f5 = arrf[1];
        return (float)Math.hypot(f2 - f3, f4 - f5) / f;
    }

    static float computeStraightness(float[] arrf, float f) {
        float f2 = arrf[2];
        float f3 = arrf[0];
        float f4 = arrf[3];
        float f5 = arrf[1];
        return (float)Math.hypot(f2 - f3, f4 - f5) / f;
    }

    static float computeTotalLength(float[] arrf) {
        float f = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n - 4; i += 2) {
            float f2 = arrf[i + 2];
            float f3 = arrf[i];
            float f4 = arrf[i + 3];
            float f5 = arrf[i + 1];
            f = (float)((double)f + Math.hypot(f2 - f3, f4 - f5));
        }
        return f;
    }

    static float cosineDistance(float[] arrf, float[] arrf2) {
        float f = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            f += arrf[i] * arrf2[i];
        }
        return (float)Math.acos(f);
    }

    static float minimumCosineDistance(float[] arrf, float[] arrf2, int n) {
        int n2 = arrf.length;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < n2; i += 2) {
            f += arrf[i] * arrf2[i] + arrf[i + 1] * arrf2[i + 1];
            f2 += arrf[i] * arrf2[i + 1] - arrf[i + 1] * arrf2[i];
        }
        if (f != 0.0f) {
            float f3 = f2 / f;
            double d = Math.atan(f3);
            if (n > 2 && Math.abs(d) >= 3.141592653589793 / (double)n) {
                return (float)Math.acos(f);
            }
            d = Math.cos(d);
            double d2 = f3;
            return (float)Math.acos((double)f * d + (double)f2 * (d2 * d));
        }
        return 1.5707964f;
    }

    private static void plot(float f, float f2, float[] arrf, int n) {
        float f3 = 0.0f;
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = f3;
        }
        int n2 = (int)Math.floor(f);
        int n3 = (int)Math.ceil(f);
        int n4 = (int)Math.floor(f2);
        int n5 = (int)Math.ceil(f2);
        if (f == (float)n2 && f2 == (float)n4) {
            if (arrf[n = n5 * n + n3] < 1.0f) {
                arrf[n] = 1.0f;
            }
        } else {
            int n6;
            double d = Math.pow((float)n2 - f, 2.0);
            double d2 = Math.pow((float)n4 - f2, 2.0);
            double d3 = Math.pow((float)n3 - f, 2.0);
            double d4 = Math.pow((float)n5 - f2, 2.0);
            float f4 = (float)Math.sqrt(d + d2);
            float f5 = (float)Math.sqrt(d3 + d2);
            if ((f4 /= (f = f4 + f5 + (f3 = (float)Math.sqrt(d + d4)) + (f2 = (float)Math.sqrt(d3 + d4)))) > arrf[n6 = n4 * n + n2]) {
                arrf[n6] = f4;
            }
            if ((f5 /= f) > arrf[n4 = n4 * n + n3]) {
                arrf[n4] = f5;
            }
            if ((f3 /= f) > arrf[n2 = n5 * n + n2]) {
                arrf[n2] = f3;
            }
            if ((f = f2 / f) > arrf[n = n5 * n + n3]) {
                arrf[n] = f;
            }
        }
    }

    static float[] rotate(float[] arrf, float f) {
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        int n = arrf.length;
        for (int i = 0; i < n; i += 2) {
            float f4 = arrf[i];
            float f5 = arrf[i + 1];
            float f6 = arrf[i];
            f = arrf[i + 1];
            arrf[i] = f4 * f2 - f5 * f3;
            arrf[i + 1] = f6 * f3 + f * f2;
        }
        return arrf;
    }

    static float[] scale(float[] arrf, float f, float f2) {
        int n = arrf.length;
        for (int i = 0; i < n; i += 2) {
            arrf[i] = arrf[i] * f;
            int n2 = i + 1;
            arrf[n2] = arrf[n2] * f2;
        }
        return arrf;
    }

    public static float[] spatialSampling(Gesture gesture, int n) {
        return GestureUtils.spatialSampling(gesture, n, false);
    }

    public static float[] spatialSampling(Gesture arrf, int n, boolean bl) {
        float f;
        float f2;
        float f3;
        float f4 = n - 1;
        float[] arrf2 = new float[n * n];
        Arrays.fill(arrf2, 0.0f);
        RectF rectF = arrf.getBoundingBox();
        float f5 = rectF.width();
        float f6 = rectF.height();
        float f7 = f4 / f5;
        float f8 = f4 / f6;
        if (bl) {
            f = f7 < f8 ? f7 : f8;
            f7 = f;
            f2 = f;
            f = f7;
        } else {
            f = f2 = f5 / f6;
            if (f2 > 1.0f) {
                f = 1.0f / f2;
            }
            if (f < 0.26f) {
                f = f7 < f8 ? f7 : f8;
                f2 = f;
                f7 = f;
                f = f2;
                f2 = f7;
            } else if (f7 > f8) {
                f2 = NONUNIFORM_SCALE * f8;
                f = f7;
                if (f2 < f7) {
                    f = f2;
                }
                f2 = f8;
            } else {
                f3 = NONUNIFORM_SCALE * f7;
                f = f7;
                f2 = f8;
                if (f3 < f8) {
                    f2 = f3;
                    f = f7;
                }
            }
        }
        float f9 = -rectF.centerX();
        float f10 = -rectF.centerY();
        float f11 = f4 / 2.0f;
        float f12 = f4 / 2.0f;
        ArrayList<GestureStroke> arrayList = arrf.getStrokes();
        int n2 = arrayList.size();
        f3 = f;
        f = f4;
        for (int i = 0; i < n2; ++i) {
            int n3;
            float[] arrf3 = arrayList.get((int)i).points;
            int n4 = arrf3.length;
            arrf = new float[n4];
            for (n3 = 0; n3 < n4; n3 += 2) {
                arrf[n3] = (arrf3[n3] + f9) * f3 + f11;
                arrf[n3 + 1] = (arrf3[n3 + 1] + f10) * f2 + f12;
            }
            float f13 = -1.0f;
            f4 = -1.0f;
            for (n3 = 0; n3 < n4; n3 += 2) {
                f7 = arrf[n3] < 0.0f ? 0.0f : arrf[n3];
                f8 = arrf[n3 + 1] < 0.0f ? 0.0f : arrf[n3 + 1];
                if (f7 > f) {
                    f7 = f;
                }
                if (f8 > f) {
                    f8 = f;
                }
                GestureUtils.plot(f7, f8, arrf2, n);
                if (f13 != -1.0f) {
                    float f14;
                    float f15;
                    if (f13 > f7) {
                        f14 = (f4 - f8) / (f13 - f7);
                        for (f15 = (float)Math.ceil((double)((double)f7)); f15 < f13; f15 += 1.0f) {
                            GestureUtils.plot(f15, (f15 - f7) * f14 + f8, arrf2, n);
                        }
                    } else {
                        arrf3 = arrf;
                        arrf = arrf3;
                        if (f13 < f7) {
                            f15 = (float)Math.ceil(f13);
                            f14 = (f4 - f8) / (f13 - f7);
                            do {
                                arrf = arrf3;
                                if (!(f15 < f7)) break;
                                GestureUtils.plot(f15, (f15 - f7) * f14 + f8, arrf2, n);
                                f15 += 1.0f;
                            } while (true);
                        }
                    }
                    if (f4 > f8) {
                        f15 = (float)Math.ceil(f8);
                        f14 = (f13 - f7) / (f4 - f8);
                        for (f13 = f15; f13 < f4; f13 += 1.0f) {
                            GestureUtils.plot((f13 - f8) * f14 + f7, f13, arrf2, n);
                        }
                    } else if (f4 < f8) {
                        f15 = (float)Math.ceil(f4);
                        f13 = (f13 - f7) / (f4 - f8);
                        for (f4 = f15; f4 < f8; f4 += 1.0f) {
                            GestureUtils.plot((f4 - f8) * f13 + f7, f4, arrf2, n);
                        }
                    }
                }
                f13 = f7;
                f4 = f8;
            }
        }
        return arrf2;
    }

    static float squaredEuclideanDistance(float[] arrf, float[] arrf2) {
        float f = 0.0f;
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            float f2 = arrf[i] - arrf2[i];
            f += f2 * f2;
        }
        return f / (float)n;
    }

    public static float[] temporalSampling(GestureStroke arrf, int n) {
        float f = arrf.length / (float)(n - 1);
        int n2 = n * 2;
        float[] arrf2 = new float[n2];
        float f2 = 0.0f;
        arrf = arrf.points;
        float f3 = arrf[0];
        int n3 = 1;
        float f4 = arrf[1];
        float f5 = Float.MIN_VALUE;
        float f6 = Float.MIN_VALUE;
        arrf2[0] = f3;
        n = 0 + 1;
        arrf2[n] = f4;
        int n4 = n + 1;
        int n5 = 0;
        int n6 = arrf.length / 2;
        while (n5 < n6) {
            float f7;
            float f8;
            float f9 = f5;
            n = n5;
            if (f5 == Float.MIN_VALUE) {
                n = n5 + 1;
                if (n >= n6) break;
                f9 = arrf[n * 2];
                f6 = arrf[n * 2 + n3];
            }
            if (f2 + (f7 = (float)Math.hypot(f5 = f9 - f3, f8 = f6 - f4)) >= f) {
                f2 = (f - f2) / f7;
                f5 = f2 * f5 + f3;
                f3 = f4 + f2 * f8;
                arrf2[n4] = f5;
                arrf2[++n4] = f3;
                ++n4;
                f4 = f5;
                f2 = 0.0f;
                f5 = f3;
            } else {
                f4 = f9;
                f5 = f6;
                f9 = Float.MIN_VALUE;
                f6 = Float.MIN_VALUE;
                f2 += f7;
            }
            n3 = 1;
            f3 = f4;
            f4 = f5;
            f5 = f9;
            n5 = n;
        }
        for (n = n4; n < n2; n += 2) {
            arrf2[n] = f3;
            arrf2[n + 1] = f4;
        }
        return arrf2;
    }

    static float[] translate(float[] arrf, float f, float f2) {
        int n = arrf.length;
        for (int i = 0; i < n; i += 2) {
            arrf[i] = arrf[i] + f;
            int n2 = i + 1;
            arrf[n2] = arrf[n2] + f2;
        }
        return arrf;
    }
}

