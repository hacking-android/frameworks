/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.R;

public class TranslateAnimation
extends Animation {
    protected float mFromXDelta;
    private int mFromXType = 0;
    @UnsupportedAppUsage
    protected float mFromXValue = 0.0f;
    protected float mFromYDelta;
    private int mFromYType = 0;
    @UnsupportedAppUsage
    protected float mFromYValue = 0.0f;
    protected float mToXDelta;
    private int mToXType = 0;
    @UnsupportedAppUsage
    protected float mToXValue = 0.0f;
    protected float mToYDelta;
    private int mToYType = 0;
    @UnsupportedAppUsage
    protected float mToYValue = 0.0f;

    public TranslateAnimation(float f, float f2, float f3, float f4) {
        this.mFromXValue = f;
        this.mToXValue = f2;
        this.mFromYValue = f3;
        this.mToYValue = f4;
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
    }

    public TranslateAnimation(int n, float f, int n2, float f2, int n3, float f3, int n4, float f4) {
        this.mFromXValue = f;
        this.mToXValue = f2;
        this.mFromYValue = f3;
        this.mToYValue = f4;
        this.mFromXType = n;
        this.mToXType = n2;
        this.mFromYType = n3;
        this.mToYType = n4;
    }

    public TranslateAnimation(Context object, AttributeSet object2) {
        super((Context)object, (AttributeSet)object2);
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.TranslateAnimation);
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(0));
        this.mFromXType = ((Animation.Description)object2).type;
        this.mFromXValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(1));
        this.mToXType = ((Animation.Description)object2).type;
        this.mToXValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(2));
        this.mFromYType = ((Animation.Description)object2).type;
        this.mFromYValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(3));
        this.mToYType = ((Animation.Description)object2).type;
        this.mToYValue = ((Animation.Description)object2).value;
        ((TypedArray)object).recycle();
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        float f2 = this.mFromXDelta;
        float f3 = this.mFromYDelta;
        float f4 = this.mFromXDelta;
        float f5 = this.mToXDelta;
        if (f4 != f5) {
            f2 = f4 + (f5 - f4) * f;
        }
        if ((f5 = this.mFromYDelta) != (f4 = this.mToYDelta)) {
            f3 = f5 + (f4 - f5) * f;
        }
        transformation.getMatrix().setTranslate(f2, f3);
    }

    @Override
    public void initialize(int n, int n2, int n3, int n4) {
        super.initialize(n, n2, n3, n4);
        this.mFromXDelta = this.resolveSize(this.mFromXType, this.mFromXValue, n, n3);
        this.mToXDelta = this.resolveSize(this.mToXType, this.mToXValue, n, n3);
        this.mFromYDelta = this.resolveSize(this.mFromYType, this.mFromYValue, n2, n4);
        this.mToYDelta = this.resolveSize(this.mToYType, this.mToYValue, n2, n4);
    }
}

