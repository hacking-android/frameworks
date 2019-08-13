/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.transition.PathMotion;
import android.util.AttributeSet;
import com.android.internal.R;

public class ArcMotion
extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float)Math.tan(Math.toRadians(35.0));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle = 70.0f;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion() {
    }

    public ArcMotion(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ArcMotion);
        this.setMinimumVerticalAngle(((TypedArray)object).getFloat(1, 0.0f));
        this.setMinimumHorizontalAngle(((TypedArray)object).getFloat(0, 0.0f));
        this.setMaximumAngle(((TypedArray)object).getFloat(2, 70.0f));
        ((TypedArray)object).recycle();
    }

    private static float toTangent(float f) {
        if (!(f < 0.0f) && !(f > 90.0f)) {
            return (float)Math.tan(Math.toRadians(f / 2.0f));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        float f5;
        Path path = new Path();
        path.moveTo(f, f2);
        float f6 = f3 - f;
        float f7 = f4 - f2;
        float f8 = f6 * f6 + f7 * f7;
        float f9 = (f + f3) / 2.0f;
        float f10 = (f2 + f4) / 2.0f;
        float f11 = f8 * 0.25f;
        boolean bl = f2 > f4;
        if (f7 == 0.0f) {
            f8 = f9;
            f6 = Math.abs(f6) * 0.5f * this.mMinimumHorizontalTangent + f10;
            f7 = 0.0f;
        } else if (f6 == 0.0f) {
            f8 = Math.abs(f7) * 0.5f * this.mMinimumVerticalTangent + f9;
            f6 = f10;
            f7 = 0.0f;
        } else if (Math.abs(f6) < Math.abs(f7)) {
            f8 = Math.abs(f8 / (f7 * 2.0f));
            if (bl) {
                f8 = f4 + f8;
                f6 = f3;
            } else {
                f8 = f2 + f8;
                f6 = f;
            }
            f7 = this.mMinimumVerticalTangent;
            f5 = f11 * f7 * f7;
            f7 = f8;
            f8 = f6;
            f6 = f7;
            f7 = f5;
        } else {
            f8 /= f6 * 2.0f;
            if (bl) {
                f8 = f + f8;
                f6 = f2;
            } else {
                f8 = f3 - f8;
                f6 = f4;
            }
            f7 = this.mMinimumHorizontalTangent;
            f7 = f11 * f7 * f7;
        }
        f5 = f9 - f8;
        float f12 = f10 - f6;
        f12 = f5 * f5 + f12 * f12;
        f5 = this.mMaximumTangent;
        f5 = f11 * f5 * f5;
        if (f12 == 0.0f || !(f12 < f7)) {
            f7 = f12 > f5 ? f5 : 0.0f;
        }
        if (f7 != 0.0f) {
            f7 = (float)Math.sqrt(f7 / f12);
            f8 = f9 + (f8 - f9) * f7;
            f6 = f10 + (f6 - f10) * f7;
        }
        path.cubicTo((f + f8) / 2.0f, (f2 + f6) / 2.0f, (f8 + f3) / 2.0f, (f6 + f4) / 2.0f, f3, f4);
        return path;
    }

    public void setMaximumAngle(float f) {
        this.mMaximumAngle = f;
        this.mMaximumTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumHorizontalAngle(float f) {
        this.mMinimumHorizontalAngle = f;
        this.mMinimumHorizontalTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumVerticalAngle(float f) {
        this.mMinimumVerticalAngle = f;
        this.mMinimumVerticalTangent = ArcMotion.toTangent(f);
    }
}

