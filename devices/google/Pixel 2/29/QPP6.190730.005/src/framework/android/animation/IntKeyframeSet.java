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

class IntKeyframeSet
extends KeyframeSet
implements Keyframes.IntKeyframes {
    public IntKeyframeSet(Keyframe.IntKeyframe ... arrintKeyframe) {
        super(arrintKeyframe);
    }

    @Override
    public IntKeyframeSet clone() {
        List list = this.mKeyframes;
        int n = this.mKeyframes.size();
        Keyframe.IntKeyframe[] arrintKeyframe = new Keyframe.IntKeyframe[n];
        for (int i = 0; i < n; ++i) {
            arrintKeyframe[i] = (Keyframe.IntKeyframe)((Keyframe)list.get(i)).clone();
        }
        return new IntKeyframeSet(arrintKeyframe);
    }

    @Override
    public int getIntValue(float f) {
        if (f <= 0.0f) {
            Object object = (Keyframe.IntKeyframe)this.mKeyframes.get(0);
            Keyframe.IntKeyframe intKeyframe = (Keyframe.IntKeyframe)this.mKeyframes.get(1);
            int n = ((Keyframe.IntKeyframe)object).getIntValue();
            int n2 = intKeyframe.getIntValue();
            float f2 = ((Keyframe)object).getFraction();
            float f3 = intKeyframe.getFraction();
            object = intKeyframe.getInterpolator();
            float f4 = f;
            if (object != null) {
                f4 = object.getInterpolation(f);
            }
            f = (f4 - f2) / (f3 - f2);
            n2 = this.mEvaluator == null ? (int)((float)(n2 - n) * f) + n : ((Number)this.mEvaluator.evaluate(f, n, n2)).intValue();
            return n2;
        }
        if (f >= 1.0f) {
            Keyframe.IntKeyframe intKeyframe = (Keyframe.IntKeyframe)this.mKeyframes.get(this.mNumKeyframes - 2);
            Object object = (Keyframe.IntKeyframe)this.mKeyframes.get(this.mNumKeyframes - 1);
            int n = intKeyframe.getIntValue();
            int n3 = ((Keyframe.IntKeyframe)object).getIntValue();
            float f5 = intKeyframe.getFraction();
            float f6 = ((Keyframe)object).getFraction();
            object = ((Keyframe)object).getInterpolator();
            float f7 = f;
            if (object != null) {
                f7 = object.getInterpolation(f);
            }
            f = (f7 - f5) / (f6 - f5);
            n = this.mEvaluator == null ? (int)((float)(n3 - n) * f) + n : ((Number)this.mEvaluator.evaluate(f, n, n3)).intValue();
            return n;
        }
        Keyframe.IntKeyframe intKeyframe = (Keyframe.IntKeyframe)this.mKeyframes.get(0);
        for (int i = 1; i < this.mNumKeyframes; ++i) {
            Keyframe.IntKeyframe intKeyframe2 = (Keyframe.IntKeyframe)this.mKeyframes.get(i);
            if (f < intKeyframe2.getFraction()) {
                TimeInterpolator timeInterpolator = intKeyframe2.getInterpolator();
                float f8 = (f - intKeyframe.getFraction()) / (intKeyframe2.getFraction() - intKeyframe.getFraction());
                i = intKeyframe.getIntValue();
                int n = intKeyframe2.getIntValue();
                f = f8;
                if (timeInterpolator != null) {
                    f = timeInterpolator.getInterpolation(f8);
                }
                i = this.mEvaluator == null ? (int)((float)(n - i) * f) + i : ((Number)this.mEvaluator.evaluate(f, i, n)).intValue();
                return i;
            }
            intKeyframe = intKeyframe2;
        }
        return ((Number)((Keyframe)this.mKeyframes.get(this.mNumKeyframes - 1)).getValue()).intValue();
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

