/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public abstract class Spline {
    public static Spline createLinearSpline(float[] arrf, float[] arrf2) {
        return new LinearSpline(arrf, arrf2);
    }

    public static Spline createMonotoneCubicSpline(float[] arrf, float[] arrf2) {
        return new MonotoneCubicSpline(arrf, arrf2);
    }

    public static Spline createSpline(float[] arrf, float[] arrf2) {
        if (Spline.isStrictlyIncreasing(arrf)) {
            if (Spline.isMonotonic(arrf2)) {
                return Spline.createMonotoneCubicSpline(arrf, arrf2);
            }
            return Spline.createLinearSpline(arrf, arrf2);
        }
        throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
    }

    private static boolean isMonotonic(float[] arrf) {
        if (arrf != null && arrf.length >= 2) {
            float f = arrf[0];
            for (int i = 1; i < arrf.length; ++i) {
                float f2 = arrf[i];
                if (f2 < f) {
                    return false;
                }
                f = f2;
            }
            return true;
        }
        throw new IllegalArgumentException("There must be at least two control points.");
    }

    private static boolean isStrictlyIncreasing(float[] arrf) {
        if (arrf != null && arrf.length >= 2) {
            float f = arrf[0];
            for (int i = 1; i < arrf.length; ++i) {
                float f2 = arrf[i];
                if (f2 <= f) {
                    return false;
                }
                f = f2;
            }
            return true;
        }
        throw new IllegalArgumentException("There must be at least two control points.");
    }

    public abstract float interpolate(float var1);

    public static class LinearSpline
    extends Spline {
        private final float[] mM;
        private final float[] mX;
        private final float[] mY;

        public LinearSpline(float[] arrf, float[] arrf2) {
            if (arrf != null && arrf2 != null && arrf.length == arrf2.length && arrf.length >= 2) {
                int n = arrf.length;
                this.mM = new float[n - 1];
                for (int i = 0; i < n - 1; ++i) {
                    this.mM[i] = (arrf2[i + 1] - arrf2[i]) / (arrf[i + 1] - arrf[i]);
                }
                this.mX = arrf;
                this.mY = arrf2;
                return;
            }
            throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
        }

        @Override
        public float interpolate(float f) {
            int n = this.mX.length;
            if (Float.isNaN(f)) {
                return f;
            }
            float[] arrf = this.mX;
            if (f <= arrf[0]) {
                return this.mY[0];
            }
            if (f >= arrf[n - 1]) {
                return this.mY[n - 1];
            }
            n = 0;
            while (f >= (arrf = this.mX)[n + 1]) {
                int n2;
                n = n2 = n + 1;
                if (f != arrf[n2]) continue;
                return this.mY[n2];
            }
            return this.mY[n] + this.mM[n] * (f - arrf[n]);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.mX.length;
            stringBuilder.append("LinearSpline{[");
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("(");
                stringBuilder.append(this.mX[i]);
                stringBuilder.append(", ");
                stringBuilder.append(this.mY[i]);
                if (i < n - 1) {
                    stringBuilder.append(": ");
                    stringBuilder.append(this.mM[i]);
                }
                stringBuilder.append(")");
            }
            stringBuilder.append("]}");
            return stringBuilder.toString();
        }
    }

    public static class MonotoneCubicSpline
    extends Spline {
        private float[] mM;
        private float[] mX;
        private float[] mY;

        public MonotoneCubicSpline(float[] arrf, float[] arrf2) {
            if (arrf != null && arrf2 != null && arrf.length == arrf2.length && arrf.length >= 2) {
                float f;
                int n;
                int n2 = arrf.length;
                float[] arrf3 = new float[n2 - 1];
                float[] arrf4 = new float[n2];
                for (n = 0; n < n2 - 1; ++n) {
                    f = arrf[n + 1] - arrf[n];
                    if (!(f <= 0.0f)) {
                        arrf3[n] = (arrf2[n + 1] - arrf2[n]) / f;
                        continue;
                    }
                    throw new IllegalArgumentException("The control points must all have strictly increasing X values.");
                }
                arrf4[0] = arrf3[0];
                for (n = 1; n < n2 - 1; ++n) {
                    arrf4[n] = (arrf3[n - 1] + arrf3[n]) * 0.5f;
                }
                arrf4[n2 - 1] = arrf3[n2 - 2];
                for (n = 0; n < n2 - 1; ++n) {
                    if (arrf3[n] == 0.0f) {
                        arrf4[n] = 0.0f;
                        arrf4[n + 1] = 0.0f;
                        continue;
                    }
                    float f2 = arrf4[n] / arrf3[n];
                    f = arrf4[n + 1] / arrf3[n];
                    if (!(f2 < 0.0f) && !(f < 0.0f)) {
                        if (!((f = (float)Math.hypot(f2, f)) > 3.0f)) continue;
                        f = 3.0f / f;
                        arrf4[n] = arrf4[n] * f;
                        int n3 = n + 1;
                        arrf4[n3] = arrf4[n3] * f;
                        continue;
                    }
                    throw new IllegalArgumentException("The control points must have monotonic Y values.");
                }
                this.mX = arrf;
                this.mY = arrf2;
                this.mM = arrf4;
                return;
            }
            throw new IllegalArgumentException("There must be at least two control points and the arrays must be of equal length.");
        }

        @Override
        public float interpolate(float f) {
            int n = this.mX.length;
            if (Float.isNaN(f)) {
                return f;
            }
            float[] arrf = this.mX;
            if (f <= arrf[0]) {
                return this.mY[0];
            }
            if (f >= arrf[n - 1]) {
                return this.mY[n - 1];
            }
            n = 0;
            while (f >= (arrf = this.mX)[n + 1]) {
                int n2;
                n = n2 = n + 1;
                if (f != arrf[n2]) continue;
                return this.mY[n2];
            }
            float f2 = arrf[n + 1] - arrf[n];
            float f3 = (f - arrf[n]) / f2;
            float[] arrf2 = this.mY;
            f = arrf2[n];
            arrf = this.mM;
            return (f * (f3 * 2.0f + 1.0f) + arrf[n] * f2 * f3) * (1.0f - f3) * (1.0f - f3) + (arrf2[n + 1] * (3.0f - 2.0f * f3) + arrf[n + 1] * f2 * (f3 - 1.0f)) * f3 * f3;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.mX.length;
            stringBuilder.append("MonotoneCubicSpline{[");
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("(");
                stringBuilder.append(this.mX[i]);
                stringBuilder.append(", ");
                stringBuilder.append(this.mY[i]);
                stringBuilder.append(": ");
                stringBuilder.append(this.mM[i]);
                stringBuilder.append(")");
            }
            stringBuilder.append("]}");
            return stringBuilder.toString();
        }
    }

}

