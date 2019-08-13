/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;

public final class MathUtils {
    private static final float DEG_TO_RAD = 0.017453292f;
    private static final float RAD_TO_DEG = 57.295784f;

    private MathUtils() {
    }

    @UnsupportedAppUsage
    public static float abs(float f) {
        if (!(f > 0.0f)) {
            f = -f;
        }
        return f;
    }

    public static float acos(float f) {
        return (float)Math.acos(f);
    }

    public static int addOrThrow(int n, int n2) throws IllegalArgumentException {
        if (n2 == 0) {
            return n;
        }
        if (n2 > 0 && n <= Integer.MAX_VALUE - n2) {
            return n + n2;
        }
        if (n2 < 0 && n >= Integer.MIN_VALUE - n2) {
            return n + n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Addition overflow: ");
        stringBuilder.append(n);
        stringBuilder.append(" + ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static float asin(float f) {
        return (float)Math.asin(f);
    }

    public static float atan(float f) {
        return (float)Math.atan(f);
    }

    public static float atan2(float f, float f2) {
        return (float)Math.atan2(f, f2);
    }

    @UnsupportedAppUsage
    public static float constrain(float f, float f2, float f3) {
        block1 : {
            block0 : {
                if (!(f < f2)) break block0;
                f = f2;
                break block1;
            }
            if (!(f > f3)) break block1;
            f = f3;
        }
        return f;
    }

    @UnsupportedAppUsage
    public static int constrain(int n, int n2, int n3) {
        block1 : {
            block0 : {
                if (n >= n2) break block0;
                n = n2;
                break block1;
            }
            if (n <= n3) break block1;
            n = n3;
        }
        return n;
    }

    public static long constrain(long l, long l2, long l3) {
        block1 : {
            block0 : {
                if (l >= l2) break block0;
                l = l2;
                break block1;
            }
            if (l <= l3) break block1;
            l = l3;
        }
        return l;
    }

    public static float constrainedMap(float f, float f2, float f3, float f4, float f5) {
        return MathUtils.lerp(f, f2, MathUtils.lerpInvSat(f3, f4, f5));
    }

    public static float cross(float f, float f2, float f3, float f4) {
        return f * f4 - f2 * f3;
    }

    public static float degrees(float f) {
        return 57.295784f * f;
    }

    public static float dist(float f, float f2, float f3, float f4) {
        return (float)Math.hypot(f3 - f, f4 - f2);
    }

    public static float dist(float f, float f2, float f3, float f4, float f5, float f6) {
        f = f4 - f;
        f2 = f5 - f2;
        f3 = f6 - f3;
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public static float dot(float f, float f2, float f3, float f4) {
        return f * f3 + f2 * f4;
    }

    public static float exp(float f) {
        return (float)Math.exp(f);
    }

    public static void fitRect(Rect rect, int n) {
        if (rect.isEmpty()) {
            return;
        }
        float f = Math.max(rect.width(), rect.height());
        rect.scale((float)n / f);
    }

    @UnsupportedAppUsage
    public static float lerp(float f, float f2, float f3) {
        return (f2 - f) * f3 + f;
    }

    public static float lerpDeg(float f, float f2, float f3) {
        return ((f2 - f + 180.0f) % 360.0f - 180.0f) * f3 + f;
    }

    public static float lerpInv(float f, float f2, float f3) {
        f = f != f2 ? (f3 - f) / (f2 - f) : 0.0f;
        return f;
    }

    public static float lerpInvSat(float f, float f2, float f3) {
        return MathUtils.saturate(MathUtils.lerpInv(f, f2, f3));
    }

    public static float log(float f) {
        return (float)Math.log(f);
    }

    public static float mag(float f, float f2) {
        return (float)Math.hypot(f, f2);
    }

    public static float mag(float f, float f2, float f3) {
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }

    public static float map(float f, float f2, float f3, float f4, float f5) {
        return (f4 - f3) * ((f5 - f) / (f2 - f)) + f3;
    }

    public static float max(float f, float f2) {
        if (!(f > f2)) {
            f = f2;
        }
        return f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float max(float f, float f2, float f3) {
        if (!(f > f2)) {
            if (!(f2 > f3)) return f3;
            return f2;
        }
        if (!(f > f3)) return f3;
        return f;
    }

    @UnsupportedAppUsage
    public static float max(int n, int n2) {
        float f = n > n2 ? (float)n : (float)n2;
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static float max(int n, int n2, int n3) {
        if (n > n2) {
            if (n > n3) {
                return n;
            }
        } else if (n2 > n3) {
            n = n2;
            return n;
        }
        n = n3;
        return n;
    }

    public static float min(float f, float f2) {
        if (!(f < f2)) {
            f = f2;
        }
        return f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float min(float f, float f2, float f3) {
        if (!(f < f2)) {
            if (!(f2 < f3)) return f3;
            return f2;
        }
        if (!(f < f3)) return f3;
        return f;
    }

    public static float min(int n, int n2) {
        float f = n < n2 ? (float)n : (float)n2;
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static float min(int n, int n2, int n3) {
        if (n < n2) {
            if (n < n3) {
                return n;
            }
        } else if (n2 < n3) {
            n = n2;
            return n;
        }
        n = n3;
        return n;
    }

    public static float norm(float f, float f2, float f3) {
        return (f3 - f) / (f2 - f);
    }

    public static float pow(float f, float f2) {
        return (float)Math.pow(f, f2);
    }

    public static float radians(float f) {
        return 0.017453292f * f;
    }

    public static float saturate(float f) {
        return MathUtils.constrain(f, 0.0f, 1.0f);
    }

    public static float smoothStep(float f, float f2, float f3) {
        return MathUtils.constrain((f3 - f) / (f2 - f), 0.0f, 1.0f);
    }

    public static float sq(float f) {
        return f * f;
    }

    public static float sqrt(float f) {
        return (float)Math.sqrt(f);
    }

    public static float tan(float f) {
        return (float)Math.tan(f);
    }
}

