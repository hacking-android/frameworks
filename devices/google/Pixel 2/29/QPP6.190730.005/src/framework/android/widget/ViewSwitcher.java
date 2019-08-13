/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

public class ViewSwitcher
extends ViewAnimator {
    ViewFactory mFactory;

    public ViewSwitcher(Context context) {
        super(context);
    }

    public ViewSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private View obtainView() {
        FrameLayout.LayoutParams layoutParams;
        View view = this.mFactory.makeView();
        FrameLayout.LayoutParams layoutParams2 = layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        }
        this.addView(view, layoutParams2);
        return view;
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() < 2) {
            super.addView(view, n, layoutParams);
            return;
        }
        throw new IllegalStateException("Can't add more than 2 views to a ViewSwitcher");
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ViewSwitcher.class.getName();
    }

    public View getNextView() {
        int n = this.mWhichChild == 0 ? 1 : 0;
        return this.getChildAt(n);
    }

    public void reset() {
        this.mFirstTime = true;
        View view = this.getChildAt(0);
        if (view != null) {
            view.setVisibility(8);
        }
        if ((view = this.getChildAt(1)) != null) {
            view.setVisibility(8);
        }
    }

    public void setFactory(ViewFactory viewFactory) {
        this.mFactory = viewFactory;
        this.obtainView();
        this.obtainView();
    }

    public static interface ViewFactory {
        public View makeView();
    }

}

