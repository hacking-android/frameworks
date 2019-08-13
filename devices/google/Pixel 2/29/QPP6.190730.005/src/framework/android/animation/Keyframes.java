/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Keyframe;
import android.animation.TypeEvaluator;
import java.util.List;

public interface Keyframes
extends Cloneable {
    public Keyframes clone();

    public List<Keyframe> getKeyframes();

    public Class getType();

    public Object getValue(float var1);

    public void setEvaluator(TypeEvaluator var1);

    public static interface FloatKeyframes
    extends Keyframes {
        public float getFloatValue(float var1);
    }

    public static interface IntKeyframes
    extends Keyframes {
        public int getIntValue(float var1);
    }

}

