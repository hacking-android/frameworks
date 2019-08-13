/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.R;

public class ScaleAnimation
extends Animation {
    private float mFromX;
    private int mFromXData = 0;
    private int mFromXType = 0;
    private float mFromY;
    private int mFromYData = 0;
    private int mFromYType = 0;
    private float mPivotX;
    private int mPivotXType = 0;
    private float mPivotXValue = 0.0f;
    private float mPivotY;
    private int mPivotYType = 0;
    private float mPivotYValue = 0.0f;
    private final Resources mResources;
    private float mToX;
    private int mToXData = 0;
    private int mToXType = 0;
    private float mToY;
    private int mToYData = 0;
    private int mToYType = 0;

    public ScaleAnimation(float f, float f2, float f3, float f4) {
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = f5;
        this.mPivotYValue = f6;
        this.initializePivotPoint();
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, int n, float f5, int n2, float f6) {
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXValue = f5;
        this.mPivotXType = n;
        this.mPivotYValue = f6;
        this.mPivotYType = n2;
        this.initializePivotPoint();
    }

    public ScaleAnimation(Context object, AttributeSet object2) {
        super((Context)object, (AttributeSet)object2);
        this.mResources = ((Context)object).getResources();
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.ScaleAnimation);
        object2 = ((TypedArray)object).peekValue(2);
        this.mFromX = 0.0f;
        if (object2 != null) {
            if (((TypedValue)object2).type == 4) {
                this.mFromX = ((TypedValue)object2).getFloat();
            } else {
                this.mFromXType = ((TypedValue)object2).type;
                this.mFromXData = ((TypedValue)object2).data;
            }
        }
        object2 = ((TypedArray)object).peekValue(3);
        this.mToX = 0.0f;
        if (object2 != null) {
            if (((TypedValue)object2).type == 4) {
                this.mToX = ((TypedValue)object2).getFloat();
            } else {
                this.mToXType = ((TypedValue)object2).type;
                this.mToXData = ((TypedValue)object2).data;
            }
        }
        object2 = ((TypedArray)object).peekValue(4);
        this.mFromY = 0.0f;
        if (object2 != null) {
            if (((TypedValue)object2).type == 4) {
                this.mFromY = ((TypedValue)object2).getFloat();
            } else {
                this.mFromYType = ((TypedValue)object2).type;
                this.mFromYData = ((TypedValue)object2).data;
            }
        }
        object2 = ((TypedArray)object).peekValue(5);
        this.mToY = 0.0f;
        if (object2 != null) {
            if (((TypedValue)object2).type == 4) {
                this.mToY = ((TypedValue)object2).getFloat();
            } else {
                this.mToYType = ((TypedValue)object2).type;
                this.mToYData = ((TypedValue)object2).data;
            }
        }
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(0));
        this.mPivotXType = ((Animation.Description)object2).type;
        this.mPivotXValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(1));
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
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = this.getScaleFactor();
        if (this.mFromX != 1.0f || this.mToX != 1.0f) {
            f2 = this.mFromX;
            f2 += (this.mToX - f2) * f;
        }
        if (this.mFromY != 1.0f || this.mToY != 1.0f) {
            f3 = this.mFromY;
            f3 += (this.mToY - f3) * f;
        }
        if (this.mPivotX == 0.0f && this.mPivotY == 0.0f) {
            transformation.getMatrix().setScale(f2, f3);
        } else {
            transformation.getMatrix().setScale(f2, f3, this.mPivotX * f4, this.mPivotY * f4);
        }
    }

    @Override
    public void initialize(int n, int n2, int n3, int n4) {
        super.initialize(n, n2, n3, n4);
        this.mFromX = this.resolveScale(this.mFromX, this.mFromXType, this.mFromXData, n, n3);
        this.mToX = this.resolveScale(this.mToX, this.mToXType, this.mToXData, n, n3);
        this.mFromY = this.resolveScale(this.mFromY, this.mFromYType, this.mFromYData, n2, n4);
        this.mToY = this.resolveScale(this.mToY, this.mToYType, this.mToYData, n2, n4);
        this.mPivotX = this.resolveSize(this.mPivotXType, this.mPivotXValue, n, n3);
        this.mPivotY = this.resolveSize(this.mPivotYType, this.mPivotYValue, n2, n4);
    }

    float resolveScale(float f, int n, int n2, int n3, int n4) {
        block6 : {
            block5 : {
                block4 : {
                    if (n != 6) break block4;
                    f = TypedValue.complexToFraction(n2, n3, n4);
                    break block5;
                }
                if (n != 5) break block6;
                f = TypedValue.complexToDimension(n2, this.mResources.getDisplayMetrics());
            }
            if (n3 == 0) {
                return 1.0f;
            }
            return f / (float)n3;
        }
        return f;
    }
}

