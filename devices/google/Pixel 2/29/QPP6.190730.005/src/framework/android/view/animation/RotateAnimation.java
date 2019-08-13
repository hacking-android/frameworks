/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.R;

public class RotateAnimation
extends Animation {
    private float mFromDegrees;
    private float mPivotX;
    private int mPivotXType = 0;
    private float mPivotXValue = 0.0f;
    private float mPivotY;
    private int mPivotYType = 0;
    private float mPivotYValue = 0.0f;
    private float mToDegrees;

    public RotateAnimation(float f, float f2) {
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public RotateAnimation(float f, float f2, float f3, float f4) {
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = f3;
        this.mPivotYValue = f4;
        this.initializePivotPoint();
    }

    public RotateAnimation(float f, float f2, int n, float f3, int n2, float f4) {
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotXValue = f3;
        this.mPivotXType = n;
        this.mPivotYValue = f4;
        this.mPivotYType = n2;
        this.initializePivotPoint();
    }

    public RotateAnimation(Context object, AttributeSet object2) {
        super((Context)object, (AttributeSet)object2);
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.RotateAnimation);
        this.mFromDegrees = ((TypedArray)object).getFloat(0, 0.0f);
        this.mToDegrees = ((TypedArray)object).getFloat(1, 0.0f);
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(2));
        this.mPivotXType = ((Animation.Description)object2).type;
        this.mPivotXValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(3));
        this.mPivotYType = ((Animation.Description)object2).type;
        this.mPivotYValue = ((Animation.Description)object2).value;
        ((TypedArray)object).recycle();
        this.initializePivotPoint();
    }

    private void initializePivotPoint() {
        if (this.mPivotXType == 0) {
            this.mPivotX = this.mPivotXValue;
        }
        if (this.mPivotYType == 0) {
            this.mPivotY = this.mPivotYValue;
        }
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        float f2 = this.mFromDegrees;
        f = f2 + (this.mToDegrees - f2) * f;
        f2 = this.getScaleFactor();
        if (this.mPivotX == 0.0f && this.mPivotY == 0.0f) {
            transformation.getMatrix().setRotate(f);
        } else {
            transformation.getMatrix().setRotate(f, this.mPivotX * f2, this.mPivotY * f2);
        }
    }

    @Override
    public void initialize(int n, int n2, int n3, int n4) {
        super.initialize(n, n2, n3, n4);
        this.mPivotX = this.resolveSize(this.mPivotXType, this.mPivotXValue, n, n3);
        this.mPivotY = this.resolveSize(this.mPivotYType, this.mPivotYValue, n2, n4);
    }
}

