/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TimeInterpolator;

public abstract class Keyframe
implements Cloneable {
    float mFraction;
    boolean mHasValue;
    private TimeInterpolator mInterpolator = null;
    Class mValueType;
    boolean mValueWasSetOnStart;

    public static Keyframe ofFloat(float f) {
        return new FloatKeyframe(f);
    }

    public static Keyframe ofFloat(float f, float f2) {
        return new FloatKeyframe(f, f2);
    }

    public static Keyframe ofInt(float f) {
        return new IntKeyframe(f);
    }

    public static Keyframe ofInt(float f, int n) {
        return new IntKeyframe(f, n);
    }

    public static Keyframe ofObject(float f) {
        return new ObjectKeyframe(f, null);
    }

    public static Keyframe ofObject(float f, Object object) {
        return new ObjectKeyframe(f, object);
    }

    public abstract Keyframe clone();

    public float getFraction() {
        return this.mFraction;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public Class getType() {
        return this.mValueType;
    }

    public abstract Object getValue();

    public boolean hasValue() {
        return this.mHasValue;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    public abstract void setValue(Object var1);

    void setValueWasSetOnStart(boolean bl) {
        this.mValueWasSetOnStart = bl;
    }

    boolean valueWasSetOnStart() {
        return this.mValueWasSetOnStart;
    }

    static class FloatKeyframe
    extends Keyframe {
        float mValue;

        FloatKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = Float.TYPE;
        }

        FloatKeyframe(float f, float f2) {
            this.mFraction = f;
            this.mValue = f2;
            this.mValueType = Float.TYPE;
            this.mHasValue = true;
        }

        @Override
        public FloatKeyframe clone() {
            FloatKeyframe floatKeyframe = this.mHasValue ? new FloatKeyframe(this.getFraction(), this.mValue) : new FloatKeyframe(this.getFraction());
            floatKeyframe.setInterpolator(this.getInterpolator());
            floatKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return floatKeyframe;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        @Override
        public Object getValue() {
            return Float.valueOf(this.mValue);
        }

        @Override
        public void setValue(Object object) {
            if (object != null && object.getClass() == Float.class) {
                this.mValue = ((Float)object).floatValue();
                this.mHasValue = true;
            }
        }
    }

    static class IntKeyframe
    extends Keyframe {
        int mValue;

        IntKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = Integer.TYPE;
        }

        IntKeyframe(float f, int n) {
            this.mFraction = f;
            this.mValue = n;
            this.mValueType = Integer.TYPE;
            this.mHasValue = true;
        }

        @Override
        public IntKeyframe clone() {
            IntKeyframe intKeyframe = this.mHasValue ? new IntKeyframe(this.getFraction(), this.mValue) : new IntKeyframe(this.getFraction());
            intKeyframe.setInterpolator(this.getInterpolator());
            intKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return intKeyframe;
        }

        public int getIntValue() {
            return this.mValue;
        }

        @Override
        public Object getValue() {
            return this.mValue;
        }

        @Override
        public void setValue(Object object) {
            if (object != null && object.getClass() == Integer.class) {
                this.mValue = (Integer)object;
                this.mHasValue = true;
            }
        }
    }

    static class ObjectKeyframe
    extends Keyframe {
        Object mValue;

        ObjectKeyframe(float f, Object class_) {
            this.mFraction = f;
            this.mValue = class_;
            boolean bl = class_ != null;
            this.mHasValue = bl;
            class_ = this.mHasValue ? class_.getClass() : Object.class;
            this.mValueType = class_;
        }

        @Override
        public ObjectKeyframe clone() {
            float f = this.getFraction();
            Object object = this.hasValue() ? this.mValue : null;
            object = new ObjectKeyframe(f, object);
            ((ObjectKeyframe)object).mValueWasSetOnStart = this.mValueWasSetOnStart;
            ((Keyframe)object).setInterpolator(this.getInterpolator());
            return object;
        }

        @Override
        public Object getValue() {
            return this.mValue;
        }

        @Override
        public void setValue(Object object) {
            this.mValue = object;
            boolean bl = object != null;
            this.mHasValue = bl;
        }
    }

}

