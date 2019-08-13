/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.R;

@Deprecated
public class PreferenceFrameLayout
extends FrameLayout {
    private static final int DEFAULT_BORDER_BOTTOM = 0;
    private static final int DEFAULT_BORDER_LEFT = 0;
    private static final int DEFAULT_BORDER_RIGHT = 0;
    private static final int DEFAULT_BORDER_TOP = 0;
    private final int mBorderBottom;
    private final int mBorderLeft;
    private final int mBorderRight;
    private final int mBorderTop;
    private boolean mPaddingApplied;

    public PreferenceFrameLayout(Context context) {
        this(context, null);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17957041);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public PreferenceFrameLayout(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.PreferenceFrameLayout, n, n2);
        float f = context.getResources().getDisplayMetrics().density;
        n2 = (int)(f * 0.0f + 0.5f);
        n = (int)(f * 0.0f + 0.5f);
        int n3 = (int)(f * 0.0f + 0.5f);
        int n4 = (int)(0.0f * f + 0.5f);
        this.mBorderTop = ((TypedArray)object).getDimensionPixelSize(3, n2);
        this.mBorderBottom = ((TypedArray)object).getDimensionPixelSize(0, n);
        this.mBorderLeft = ((TypedArray)object).getDimensionPixelSize(1, n3);
        this.mBorderRight = ((TypedArray)object).getDimensionPixelSize(2, n4);
        ((TypedArray)object).recycle();
    }

    @Override
    public void addView(View view) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = this.getPaddingTop();
        int n6 = this.getPaddingBottom();
        int n7 = this.getPaddingLeft();
        int n8 = this.getPaddingRight();
        LayoutParams layoutParams = view.getLayoutParams() instanceof LayoutParams ? (LayoutParams)view.getLayoutParams() : null;
        if (layoutParams != null && layoutParams.removeBorders) {
            n3 = n5;
            n2 = n6;
            n = n7;
            n4 = n8;
            if (this.mPaddingApplied) {
                n3 = n5 - this.mBorderTop;
                n2 = n6 - this.mBorderBottom;
                n = n7 - this.mBorderLeft;
                n4 = n8 - this.mBorderRight;
                this.mPaddingApplied = false;
            }
        } else {
            n3 = n5;
            n2 = n6;
            n = n7;
            n4 = n8;
            if (!this.mPaddingApplied) {
                n3 = n5 + this.mBorderTop;
                n2 = n6 + this.mBorderBottom;
                n = n7 + this.mBorderLeft;
                n4 = n8 + this.mBorderRight;
                this.mPaddingApplied = true;
            }
        }
        n5 = this.getPaddingTop();
        n8 = this.getPaddingBottom();
        n6 = this.getPaddingLeft();
        n7 = this.getPaddingRight();
        if (n5 != n3 || n8 != n2 || n6 != n || n7 != n4) {
            this.setPadding(n, n3, n4, n2);
        }
        super.addView(view);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public boolean removeBorders = false;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.PreferenceFrameLayout_Layout);
            this.removeBorders = ((TypedArray)object).getBoolean(0, false);
            ((TypedArray)object).recycle();
        }
    }

}

