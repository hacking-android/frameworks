/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.FloatKeyframeSet;
import android.animation.IntKeyframeSet;
import android.animation.Keyframe;
import android.animation.Keyframes;
import android.animation.PathKeyframes;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.util.Log;
import java.util.Arrays;
import java.util.List;

public class KeyframeSet
implements Keyframes {
    TypeEvaluator mEvaluator;
    Keyframe mFirstKeyframe;
    TimeInterpolator mInterpolator;
    List<Keyframe> mKeyframes;
    Keyframe mLastKeyframe;
    int mNumKeyframes;

    public KeyframeSet(Keyframe ... arrkeyframe) {
        this.mNumKeyframes = arrkeyframe.length;
        this.mKeyframes = Arrays.asList(arrkeyframe);
        this.mFirstKeyframe = arrkeyframe[0];
        this.mLastKeyframe = arrkeyframe[this.mNumKeyframes - 1];
        this.mInterpolator = this.mLastKeyframe.getInterpolator();
    }

    public static KeyframeSet ofFloat(float ... arrf) {
        boolean bl = false;
        boolean bl2 = false;
        int n = arrf.length;
        Keyframe.FloatKeyframe[] arrfloatKeyframe = new Keyframe.FloatKeyframe[java.lang.Math.max(n, 2)];
        if (n == 1) {
            arrfloatKeyframe[0] = (Keyframe.FloatKeyframe)Keyframe.ofFloat(0.0f);
            arrfloatKeyframe[1] = (Keyframe.FloatKeyframe)Keyframe.ofFloat(1.0f, arrf[0]);
            if (Float.isNaN(arrf[0])) {
                bl = true;
            }
        } else {
            arrfloatKeyframe[0] = (Keyframe.FloatKeyframe)Keyframe.ofFloat(0.0f, arrf[0]);
            int n2 = 1;
            do {
                bl = bl2;
                if (n2 >= n) break;
                arrfloatKeyframe[n2] = (Keyframe.FloatKeyframe)Keyframe.ofFloat((float)n2 / (float)(n - 1), arrf[n2]);
                if (Float.isNaN(arrf[n2])) {
                    bl2 = true;
                }
                ++n2;
            } while (true);
        }
        if (bl) {
            Log.w("Animator", "Bad value (NaN) in float animator");
        }
        return new FloatKeyframeSet(arrfloatKeyframe);
    }

    public static KeyframeSet ofInt(int ... arrn) {
        int n = arrn.length;
        Keyframe.IntKeyframe[] arrintKeyframe = new Keyframe.IntKeyframe[java.lang.Math.max(n, 2)];
        if (n == 1) {
            arrintKeyframe[0] = (Keyframe.IntKeyframe)Keyframe.ofInt(0.0f);
            arrintKeyframe[1] = (Keyframe.IntKeyframe)Keyframe.ofInt(1.0f, arrn[0]);
        } else {
            arrintKeyframe[0] = (Keyframe.IntKeyframe)Keyframe.ofInt(0.0f, arrn[0]);
            for (int i = 1; i < n; ++i) {
                arrintKeyframe[i] = (Keyframe.IntKeyframe)Keyframe.ofInt((float)i / (float)(n - 1), arrn[i]);
            }
        }
        return new IntKeyframeSet(arrintKeyframe);
    }

    public static KeyframeSet ofKeyframe(Keyframe ... arrkeyframe) {
        int n;
        int n2 = arrkeyframe.length;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        for (n = 0; n < n2; ++n) {
            if (arrkeyframe[n] instanceof Keyframe.FloatKeyframe) {
                bl = true;
                continue;
            }
            if (arrkeyframe[n] instanceof Keyframe.IntKeyframe) {
                bl2 = true;
                continue;
            }
            bl3 = true;
        }
        if (bl && !bl2 && !bl3) {
            Keyframe.FloatKeyframe[] arrfloatKeyframe = new Keyframe.FloatKeyframe[n2];
            for (n = 0; n < n2; ++n) {
                arrfloatKeyframe[n] = (Keyframe.FloatKeyframe)arrkeyframe[n];
            }
            return new FloatKeyframeSet(arrfloatKeyframe);
        }
        if (bl2 && !bl && !bl3) {
            Keyframe.IntKeyframe[] arrintKeyframe = new Keyframe.IntKeyframe[n2];
            for (n = 0; n < n2; ++n) {
                arrintKeyframe[n] = (Keyframe.IntKeyframe)arrkeyframe[n];
            }
            return new IntKeyframeSet(arrintKeyframe);
        }
        return new KeyframeSet(arrkeyframe);
    }

    public static KeyframeSet ofObject(Object ... arrobject) {
        int n = arrobject.length;
        Keyframe[] arrkeyframe = new Keyframe.ObjectKeyframe[java.lang.Math.max(n, 2)];
        if (n == 1) {
            arrkeyframe[0] = (Keyframe.ObjectKeyframe)Keyframe.ofObject(0.0f);
            arrkeyframe[1] = (Keyframe.ObjectKeyframe)Keyframe.ofObject(1.0f, arrobject[0]);
        } else {
            arrkeyframe[0] = (Keyframe.ObjectKeyframe)Keyframe.ofObject(0.0f, arrobject[0]);
            for (int i = 1; i < n; ++i) {
                arrkeyframe[i] = (Keyframe.ObjectKeyframe)Keyframe.ofObject((float)i / (float)(n - 1), arrobject[i]);
            }
        }
        return new KeyframeSet(arrkeyframe);
    }

    public static PathKeyframes ofPath(Path path) {
        return new PathKeyframes(path);
    }

    public static PathKeyframes ofPath(Path path, float f) {
        return new PathKeyframes(path, f);
    }

    @Override
    public KeyframeSet clone() {
        List<Keyframe> list = this.mKeyframes;
        int n = this.mKeyframes.size();
        Keyframe[] arrkeyframe = new Keyframe[n];
        for (int i = 0; i < n; ++i) {
            arrkeyframe[i] = list.get(i).clone();
        }
        return new KeyframeSet(arrkeyframe);
    }

    @Override
    public List<Keyframe> getKeyframes() {
        return this.mKeyframes;
    }

    @Override
    public Class getType() {
        return this.mFirstKeyframe.getType();
    }

    @Override
    public Object getValue(float f) {
        int n = this.mNumKeyframes;
        if (n == 2) {
            TimeInterpolator timeInterpolator = this.mInterpolator;
            float f2 = f;
            if (timeInterpolator != null) {
                f2 = timeInterpolator.getInterpolation(f);
            }
            return this.mEvaluator.evaluate(f2, this.mFirstKeyframe.getValue(), this.mLastKeyframe.getValue());
        }
        if (f <= 0.0f) {
            Keyframe keyframe = this.mKeyframes.get(1);
            TimeInterpolator timeInterpolator = keyframe.getInterpolator();
            float f3 = f;
            if (timeInterpolator != null) {
                f3 = timeInterpolator.getInterpolation(f);
            }
            f = this.mFirstKeyframe.getFraction();
            f = (f3 - f) / (keyframe.getFraction() - f);
            return this.mEvaluator.evaluate(f, this.mFirstKeyframe.getValue(), keyframe.getValue());
        }
        if (f >= 1.0f) {
            Keyframe keyframe = this.mKeyframes.get(n - 2);
            TimeInterpolator timeInterpolator = this.mLastKeyframe.getInterpolator();
            float f4 = f;
            if (timeInterpolator != null) {
                f4 = timeInterpolator.getInterpolation(f);
            }
            f = keyframe.getFraction();
            f = (f4 - f) / (this.mLastKeyframe.getFraction() - f);
            return this.mEvaluator.evaluate(f, keyframe.getValue(), this.mLastKeyframe.getValue());
        }
        Keyframe keyframe = this.mFirstKeyframe;
        for (n = 1; n < this.mNumKeyframes; ++n) {
            Keyframe keyframe2 = this.mKeyframes.get(n);
            if (f < keyframe2.getFraction()) {
                TimeInterpolator timeInterpolator = keyframe2.getInterpolator();
                float f5 = keyframe.getFraction();
                f = f5 = (f - f5) / (keyframe2.getFraction() - f5);
                if (timeInterpolator != null) {
                    f = timeInterpolator.getInterpolation(f5);
                }
                return this.mEvaluator.evaluate(f, keyframe.getValue(), keyframe2.getValue());
            }
            keyframe = keyframe2;
        }
        return this.mLastKeyframe.getValue();
    }

    @Override
    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
    }

    public String toString() {
        String string2 = " ";
        for (int i = 0; i < this.mNumKeyframes; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(this.mKeyframes.get(i).getValue());
            stringBuilder.append("  ");
            string2 = stringBuilder.toString();
        }
        return string2;
    }
}

