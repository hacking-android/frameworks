/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Keyframe;
import android.animation.KeyframeSet;
import android.animation.Keyframes;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import java.util.List;

class FloatKeyframeSet
extends KeyframeSet
implements Keyframes.FloatKeyframes {
    public FloatKeyframeSet(Keyframe.FloatKeyframe ... arrfloatKeyframe) {
        super(arrfloatKeyframe);
    }

    @Override
    public FloatKeyframeSet clone() {
        List list = this.mKeyframes;
        int n = this.mKeyframes.size();
        Keyframe.FloatKeyframe[] arrfloatKeyframe = new Keyframe.FloatKeyframe[n];
        for (int i = 0; i < n; ++i) {
            arrfloatKeyframe[i] = (Keyframe.FloatKeyframe)((Keyframe)list.get(i)).clone();
        }
        return new FloatKeyframeSet(arrfloatKeyframe);
    }

    @Override
    public float getFloatValue(float f) {
        if (f <= 0.0f) {
            Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe)this.mKeyframes.get(0);
            Object object = (Keyframe.FloatKeyframe)this.mKeyframes.get(1);
            float f2 = floatKeyframe.getFloatValue();
            float f3 = ((Keyframe.FloatKeyframe)object).getFloatValue();
            float f4 = floatKeyframe.getFraction();
            float f5 = ((Keyframe)object).getFraction();
            object = ((Keyframe)object).getInterpolator();
            float f6 = f;
            if (object != null) {
                f6 = object.getInterpolation(f);
            }
            f = (f6 - f4) / (f5 - f4);
            f = this.mEvaluator == null ? (f3 - f2) * f + f2 : ((Number)this.mEvaluator.evaluate(f, Float.valueOf(f2), Float.valueOf(f3))).floatValue();
            return f;
        }
        if (f >= 1.0f) {
            Object object = (Keyframe.FloatKeyframe)this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe)this.mKeyframes.get(this.mNumKeyframes - 1);
            float f7 = ((Keyframe.FloatKeyframe)object).getFloatValue();
            float f8 = floatKeyframe.getFloatValue();
            float f9 = ((Keyframe)object).getFraction();
            float f10 = floatKeyframe.getFraction();
            object = floatKeyframe.getInterpolator();
            float f11 = f;
            if (object != null) {
                f11 = object.getInterpolation(f);
            }
            f = (f11 - f9) / (f10 - f9);
            f = this.mEvaluator == null ? (f8 - f7) * f + f7 : ((Number)this.mEvaluator.evaluate(f, Float.valueOf(f7), Float.valueOf(f8))).floatValue();
            return f;
        }
        Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe)this.mKeyframes.get(0);
        for (int i = 1; i < this.mNumKeyframes; ++i) {
            Keyframe.FloatKeyframe floatKeyframe2 = (Keyframe.FloatKeyframe)this.mKeyframes.get(i);
            if (f < floatKeyframe2.getFraction()) {
                TimeInterpolator timeInterpolator = floatKeyframe2.getInterpolator();
                float f12 = (f - floatKeyframe.getFraction()) / (floatKeyframe2.getFraction() - floatKeyframe.getFraction());
                float f13 = floatKeyframe.getFloatValue();
                float f14 = floatKeyframe2.getFloatValue();
                f = f12;
                if (timeInterpolator != null) {
                    f = timeInterpolator.getInterpolation(f12);
                }
                f = this.mEvaluator == null ? (f14 - f13) * f + f13 : ((Number)this.mEvaluator.evaluate(f, Float.valueOf(f13), Float.valueOf(f14))).floatValue();
                return f;
            }
            floatKeyframe = floatKeyframe2;
        }
        return ((Number)((Keyframe)this.mKeyframes.get(this.mNumKeyframes - 1)).getValue()).floatValue();
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

