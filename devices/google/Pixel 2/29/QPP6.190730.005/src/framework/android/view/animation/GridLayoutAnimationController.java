/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import com.android.internal.R;
import java.util.Random;

public class GridLayoutAnimationController
extends LayoutAnimationController {
    public static final int DIRECTION_BOTTOM_TO_TOP = 2;
    public static final int DIRECTION_HORIZONTAL_MASK = 1;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int DIRECTION_TOP_TO_BOTTOM = 0;
    public static final int DIRECTION_VERTICAL_MASK = 2;
    public static final int PRIORITY_COLUMN = 1;
    public static final int PRIORITY_NONE = 0;
    public static final int PRIORITY_ROW = 2;
    private float mColumnDelay;
    private int mDirection;
    private int mDirectionPriority;
    private float mRowDelay;

    public GridLayoutAnimationController(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.GridLayoutAnimation);
        this.mColumnDelay = Animation.Description.parseValue((TypedValue)object.peekValue((int)0)).value;
        this.mRowDelay = Animation.Description.parseValue((TypedValue)object.peekValue((int)1)).value;
        this.mDirection = ((TypedArray)object).getInt(2, 0);
        this.mDirectionPriority = ((TypedArray)object).getInt(3, 0);
        ((TypedArray)object).recycle();
    }

    public GridLayoutAnimationController(Animation animation) {
        this(animation, 0.5f, 0.5f);
    }

    public GridLayoutAnimationController(Animation animation, float f, float f2) {
        super(animation);
        this.mColumnDelay = f;
        this.mRowDelay = f2;
    }

    private int getTransformedColumnIndex(AnimationParameters animationParameters) {
        int n = this.getOrder();
        if (n != 1) {
            if (n != 2) {
                n = animationParameters.column;
            } else {
                if (this.mRandomizer == null) {
                    this.mRandomizer = new Random();
                }
                n = (int)((float)animationParameters.columnsCount * this.mRandomizer.nextFloat());
            }
        } else {
            n = animationParameters.columnsCount - 1 - animationParameters.column;
        }
        int n2 = n;
        if ((this.mDirection & 1) == 1) {
            n2 = animationParameters.columnsCount - 1 - n;
        }
        return n2;
    }

    private int getTransformedRowIndex(AnimationParameters animationParameters) {
        int n = this.getOrder();
        if (n != 1) {
            if (n != 2) {
                n = animationParameters.row;
            } else {
                if (this.mRandomizer == null) {
                    this.mRandomizer = new Random();
                }
                n = (int)((float)animationParameters.rowsCount * this.mRandomizer.nextFloat());
            }
        } else {
            n = animationParameters.rowsCount - 1 - animationParameters.row;
        }
        int n2 = n;
        if ((this.mDirection & 2) == 2) {
            n2 = animationParameters.rowsCount - 1 - n;
        }
        return n2;
    }

    public float getColumnDelay() {
        return this.mColumnDelay;
    }

    @Override
    protected long getDelayForView(View object) {
        int n;
        object = (AnimationParameters)object.getLayoutParams().layoutAnimationParameters;
        if (object == null) {
            return 0L;
        }
        int n2 = this.getTransformedColumnIndex((AnimationParameters)object);
        int n3 = this.getTransformedRowIndex((AnimationParameters)object);
        int n4 = ((AnimationParameters)object).rowsCount;
        int n5 = ((AnimationParameters)object).columnsCount;
        long l = this.mAnimation.getDuration();
        float f = this.mColumnDelay * (float)l;
        float f2 = this.mRowDelay * (float)l;
        if (this.mInterpolator == null) {
            this.mInterpolator = new LinearInterpolator();
        }
        if ((n = this.mDirectionPriority) != 1) {
            if (n != 2) {
                l = (long)((float)n2 * f + (float)n3 * f2);
                f = (float)n5 * f + (float)n4 * f2;
            } else {
                l = (long)((float)n2 * f + (float)(n3 * n5) * f);
                f = (float)n5 * f + (float)(n4 * n5) * f;
            }
        } else {
            l = (long)((float)n3 * f2 + (float)(n2 * n4) * f2);
            f = (float)n4 * f2 + (float)(n5 * n4) * f2;
        }
        f2 = (float)l / f;
        return (long)(this.mInterpolator.getInterpolation(f2) * f);
    }

    public int getDirection() {
        return this.mDirection;
    }

    public int getDirectionPriority() {
        return this.mDirectionPriority;
    }

    public float getRowDelay() {
        return this.mRowDelay;
    }

    public void setColumnDelay(float f) {
        this.mColumnDelay = f;
    }

    public void setDirection(int n) {
        this.mDirection = n;
    }

    public void setDirectionPriority(int n) {
        this.mDirectionPriority = n;
    }

    public void setRowDelay(float f) {
        this.mRowDelay = f;
    }

    @Override
    public boolean willOverlap() {
        boolean bl = this.mColumnDelay < 1.0f || this.mRowDelay < 1.0f;
        return bl;
    }

    public static class AnimationParameters
    extends LayoutAnimationController.AnimationParameters {
        public int column;
        public int columnsCount;
        public int row;
        public int rowsCount;
    }

}

