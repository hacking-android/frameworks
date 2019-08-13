/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Keyframe;
import android.animation.Keyframes;
import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;

public class PathKeyframes
implements Keyframes {
    private static final ArrayList<Keyframe> EMPTY_KEYFRAMES = new ArrayList();
    private static final int FRACTION_OFFSET = 0;
    private static final int NUM_COMPONENTS = 3;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 2;
    private float[] mKeyframeData;
    private PointF mTempPointF = new PointF();

    public PathKeyframes(Path path) {
        this(path, 0.5f);
    }

    public PathKeyframes(Path path, float f) {
        if (path != null && !path.isEmpty()) {
            this.mKeyframeData = path.approximate(f);
            return;
        }
        throw new IllegalArgumentException("The path must not be null or empty");
    }

    private static float interpolate(float f, float f2, float f3) {
        return (f3 - f2) * f + f2;
    }

    private PointF interpolateInRange(float f, int n, int n2) {
        float[] arrf = this.mKeyframeData;
        float f2 = arrf[(n *= 3) + 0];
        float f3 = (f - f2) / (arrf[(n2 *= 3) + 0] - f2);
        float f4 = arrf[n + 1];
        float f5 = arrf[n2 + 1];
        f = arrf[n + 2];
        f2 = arrf[n2 + 2];
        f4 = PathKeyframes.interpolate(f3, f4, f5);
        f = PathKeyframes.interpolate(f3, f, f2);
        this.mTempPointF.set(f4, f);
        return this.mTempPointF;
    }

    private PointF pointForIndex(int n) {
        PointF pointF = this.mTempPointF;
        float[] arrf = this.mKeyframeData;
        pointF.set(arrf[(n *= 3) + 1], arrf[n + 2]);
        return this.mTempPointF;
    }

    @Override
    public Keyframes clone() {
        Keyframes keyframes = null;
        try {
            Keyframes keyframes2;
            keyframes = keyframes2 = (Keyframes)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return keyframes;
    }

    public Keyframes.FloatKeyframes createXFloatKeyframes() {
        return new FloatKeyframesBase(){

            @Override
            public float getFloatValue(float f) {
                return ((PointF)PathKeyframes.this.getValue((float)f)).x;
            }
        };
    }

    public Keyframes.IntKeyframes createXIntKeyframes() {
        return new IntKeyframesBase(){

            @Override
            public int getIntValue(float f) {
                return Math.round(((PointF)PathKeyframes.this.getValue((float)f)).x);
            }
        };
    }

    public Keyframes.FloatKeyframes createYFloatKeyframes() {
        return new FloatKeyframesBase(){

            @Override
            public float getFloatValue(float f) {
                return ((PointF)PathKeyframes.this.getValue((float)f)).y;
            }
        };
    }

    public Keyframes.IntKeyframes createYIntKeyframes() {
        return new IntKeyframesBase(){

            @Override
            public int getIntValue(float f) {
                return Math.round(((PointF)PathKeyframes.this.getValue((float)f)).y);
            }
        };
    }

    public ArrayList<Keyframe> getKeyframes() {
        return EMPTY_KEYFRAMES;
    }

    @Override
    public Class getType() {
        return PointF.class;
    }

    @Override
    public Object getValue(float f) {
        int n = this.mKeyframeData.length / 3;
        if (f < 0.0f) {
            return this.interpolateInRange(f, 0, 1);
        }
        if (f > 1.0f) {
            return this.interpolateInRange(f, n - 2, n - 1);
        }
        if (f == 0.0f) {
            return this.pointForIndex(0);
        }
        if (f == 1.0f) {
            return this.pointForIndex(n - 1);
        }
        int n2 = 0;
        --n;
        while (n2 <= n) {
            int n3 = (n2 + n) / 2;
            float f2 = this.mKeyframeData[n3 * 3 + 0];
            if (f < f2) {
                n = n3 - 1;
                continue;
            }
            if (f > f2) {
                n2 = n3 + 1;
                continue;
            }
            return this.pointForIndex(n3);
        }
        return this.interpolateInRange(f, n, n2);
    }

    @Override
    public void setEvaluator(TypeEvaluator typeEvaluator) {
    }

    static abstract class FloatKeyframesBase
    extends SimpleKeyframes
    implements Keyframes.FloatKeyframes {
        FloatKeyframesBase() {
        }

        @Override
        public Class getType() {
            return Float.class;
        }

        @Override
        public Object getValue(float f) {
            return Float.valueOf(this.getFloatValue(f));
        }
    }

    static abstract class IntKeyframesBase
    extends SimpleKeyframes
    implements Keyframes.IntKeyframes {
        IntKeyframesBase() {
        }

        @Override
        public Class getType() {
            return Integer.class;
        }

        @Override
        public Object getValue(float f) {
            return this.getIntValue(f);
        }
    }

    private static abstract class SimpleKeyframes
    implements Keyframes {
        private SimpleKeyframes() {
        }

        @Override
        public Keyframes clone() {
            Keyframes keyframes = null;
            try {
                Keyframes keyframes2;
                keyframes = keyframes2 = (Keyframes)super.clone();
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                // empty catch block
            }
            return keyframes;
        }

        public ArrayList<Keyframe> getKeyframes() {
            return EMPTY_KEYFRAMES;
        }

        @Override
        public void setEvaluator(TypeEvaluator typeEvaluator) {
        }
    }

}

