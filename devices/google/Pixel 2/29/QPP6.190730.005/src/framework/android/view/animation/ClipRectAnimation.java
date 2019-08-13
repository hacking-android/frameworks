/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.R;

public class ClipRectAnimation
extends Animation {
    private int mFromBottomType = 0;
    private float mFromBottomValue;
    private int mFromLeftType = 0;
    private float mFromLeftValue;
    protected final Rect mFromRect = new Rect();
    private int mFromRightType = 0;
    private float mFromRightValue;
    private int mFromTopType = 0;
    private float mFromTopValue;
    private int mToBottomType = 0;
    private float mToBottomValue;
    private int mToLeftType = 0;
    private float mToLeftValue;
    protected final Rect mToRect = new Rect();
    private int mToRightType = 0;
    private float mToRightValue;
    private int mToTopType = 0;
    private float mToTopValue;

    public ClipRectAnimation(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this(new Rect(n, n2, n3, n4), new Rect(n5, n6, n7, n8));
    }

    public ClipRectAnimation(Context object, AttributeSet object2) {
        super((Context)object, (AttributeSet)object2);
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.ClipRectAnimation);
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(1));
        this.mFromLeftType = ((Animation.Description)object2).type;
        this.mFromLeftValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(3));
        this.mFromTopType = ((Animation.Description)object2).type;
        this.mFromTopValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(2));
        this.mFromRightType = ((Animation.Description)object2).type;
        this.mFromRightValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(0));
        this.mFromBottomType = ((Animation.Description)object2).type;
        this.mFromBottomValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(5));
        this.mToLeftType = ((Animation.Description)object2).type;
        this.mToLeftValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(7));
        this.mToTopType = ((Animation.Description)object2).type;
        this.mToTopValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(6));
        this.mToRightType = ((Animation.Description)object2).type;
        this.mToRightValue = ((Animation.Description)object2).value;
        object2 = Animation.Description.parseValue(((TypedArray)object).peekValue(4));
        this.mToBottomType = ((Animation.Description)object2).type;
        this.mToBottomValue = ((Animation.Description)object2).value;
        ((TypedArray)object).recycle();
    }

    public ClipRectAnimation(Rect rect, Rect rect2) {
        if (rect != null && rect2 != null) {
            this.mFromLeftValue = rect.left;
            this.mFromTopValue = rect.top;
            this.mFromRightValue = rect.right;
            this.mFromBottomValue = rect.bottom;
            this.mToLeftValue = rect2.left;
            this.mToTopValue = rect2.top;
            this.mToRightValue = rect2.right;
            this.mToBottomValue = rect2.bottom;
            return;
        }
        throw new RuntimeException("Expected non-null animation clip rects");
    }

    @Override
    protected void applyTransformation(float f, Transformation transformation) {
        transformation.setClipRect(this.mFromRect.left + (int)((float)(this.mToRect.left - this.mFromRect.left) * f), this.mFromRect.top + (int)((float)(this.mToRect.top - this.mFromRect.top) * f), this.mFromRect.right + (int)((float)(this.mToRect.right - this.mFromRect.right) * f), this.mFromRect.bottom + (int)((float)(this.mToRect.bottom - this.mFromRect.bottom) * f));
    }

    @Override
    public void initialize(int n, int n2, int n3, int n4) {
        super.initialize(n, n2, n3, n4);
        this.mFromRect.set((int)this.resolveSize(this.mFromLeftType, this.mFromLeftValue, n, n3), (int)this.resolveSize(this.mFromTopType, this.mFromTopValue, n2, n4), (int)this.resolveSize(this.mFromRightType, this.mFromRightValue, n, n3), (int)this.resolveSize(this.mFromBottomType, this.mFromBottomValue, n2, n4));
        this.mToRect.set((int)this.resolveSize(this.mToLeftType, this.mToLeftValue, n, n3), (int)this.resolveSize(this.mToTopType, this.mToTopValue, n2, n4), (int)this.resolveSize(this.mToRightType, this.mToRightValue, n, n3), (int)this.resolveSize(this.mToBottomType, this.mToBottomValue, n2, n4));
    }

    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }
}

