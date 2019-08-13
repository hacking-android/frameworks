/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.R;
import com.android.internal.widget.ScrollingTabContainerView;

public class ActionBarContainer
extends FrameLayout {
    private View mActionBarView;
    private View mActionContextView;
    private Drawable mBackground;
    private int mHeight;
    private boolean mIsSplit;
    private boolean mIsStacked;
    private boolean mIsTransitioning;
    private Drawable mSplitBackground;
    private Drawable mStackedBackground;
    private View mTabContainer;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        this.setBackground(new ActionBarBackgroundDrawable());
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
        this.mBackground = ((TypedArray)object).getDrawable(2);
        this.mStackedBackground = ((TypedArray)object).getDrawable(18);
        this.mHeight = ((TypedArray)object).getDimensionPixelSize(4, -1);
        int n = this.getId();
        boolean bl = true;
        if (n == 16909391) {
            this.mIsSplit = true;
            this.mSplitBackground = ((TypedArray)object).getDrawable(19);
        }
        ((TypedArray)object).recycle();
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                bl = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            bl = false;
        }
        this.setWillNotDraw(bl);
    }

    private int getMeasuredHeightWithMargins(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    private static boolean isCollapsed(View view) {
        boolean bl = view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
        return bl;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Drawable drawable2 = this.mBackground;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        drawable2 = this.mStackedBackground;
        bl = bl2;
        if (drawable2 != null) {
            bl = bl2;
            if (drawable2.isStateful()) {
                bl = bl2 | drawable2.setState(arrn);
            }
        }
        drawable2 = this.mSplitBackground;
        bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = bl | drawable2.setState(arrn);
            }
        }
        if (bl2) {
            this.invalidate();
        }
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        if ((drawable2 = this.mStackedBackground) != null) {
            drawable2.jumpToCurrentState();
        }
        if ((drawable2 = this.mSplitBackground) != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = this.findViewById(16908682);
        this.mActionContextView = this.findViewById(16908687);
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl = this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
        return bl;
    }

    @Override
    public void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Object object;
        super.onLayout(bl, n, n2, n3, n4);
        Object object2 = this.mTabContainer;
        bl = object2 != null && ((View)object2).getVisibility() != 8;
        if (object2 != null && ((View)object2).getVisibility() != 8) {
            n2 = this.getMeasuredHeight();
            object = (FrameLayout.LayoutParams)((View)object2).getLayoutParams();
            ((View)object2).layout(n, n2 - ((View)object2).getMeasuredHeight() - ((FrameLayout.LayoutParams)object).bottomMargin, n3, n2 - ((FrameLayout.LayoutParams)object).bottomMargin);
        }
        n = 0;
        n2 = 0;
        if (this.mIsSplit) {
            object2 = this.mSplitBackground;
            if (object2 != null) {
                ((Drawable)object2).setBounds(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
                n = 1;
            }
        } else {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else {
                    object = this.mActionContextView;
                    if (object != null && ((View)object).getVisibility() == 0) {
                        this.mBackground.setBounds(this.mActionContextView.getLeft(), this.mActionContextView.getTop(), this.mActionContextView.getRight(), this.mActionContextView.getBottom());
                    } else {
                        this.mBackground.setBounds(0, 0, 0, 0);
                    }
                }
                n2 = 1;
            }
            this.mIsStacked = bl;
            n = n2;
            if (bl) {
                object = this.mStackedBackground;
                n = n2;
                if (object != null) {
                    ((Drawable)object).setBounds(((View)object2).getLeft(), ((View)object2).getTop(), ((View)object2).getRight(), ((View)object2).getBottom());
                    n = 1;
                }
            }
        }
        if (n != 0) {
            this.invalidate();
        }
    }

    @Override
    public void onMeasure(int n, int n2) {
        int n3;
        int n4 = n2;
        if (this.mActionBarView == null) {
            n4 = n2;
            if (View.MeasureSpec.getMode(n2) == Integer.MIN_VALUE) {
                n3 = this.mHeight;
                n4 = n2;
                if (n3 >= 0) {
                    n4 = View.MeasureSpec.makeMeasureSpec(Math.min(n3, View.MeasureSpec.getSize(n2)), Integer.MIN_VALUE);
                }
            }
        }
        super.onMeasure(n, n4);
        if (this.mActionBarView == null) {
            return;
        }
        View view = this.mTabContainer;
        if (view != null && view.getVisibility() != 8) {
            n = 0;
            int n5 = this.getChildCount();
            for (n2 = 0; n2 < n5; ++n2) {
                view = this.getChildAt(n2);
                if (view == this.mTabContainer) continue;
                n3 = ActionBarContainer.isCollapsed(view) ? 0 : this.getMeasuredHeightWithMargins(view);
                n = Math.max(n, n3);
            }
            n2 = View.MeasureSpec.getMode(n4) == Integer.MIN_VALUE ? View.MeasureSpec.getSize(n4) : Integer.MAX_VALUE;
            this.setMeasuredDimension(this.getMeasuredWidth(), Math.min(this.getMeasuredHeightWithMargins(this.mTabContainer) + n, n2));
        }
    }

    @Override
    public void onResolveDrawables(int n) {
        super.onResolveDrawables(n);
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
        if ((drawable2 = this.mStackedBackground) != null) {
            drawable2.setLayoutDirection(n);
        }
        if ((drawable2 = this.mSplitBackground) != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable object) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            this.unscheduleDrawable(this.mBackground);
        }
        this.mBackground = object;
        if (object != null) {
            ((Drawable)object).setCallback(this);
            object = this.mActionBarView;
            if (object != null) {
                this.mBackground.setBounds(((View)object).getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        boolean bl = this.mIsSplit;
        boolean bl2 = true;
        if (bl) {
            if (this.mSplitBackground != null) {
                bl2 = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            bl2 = false;
        }
        this.setWillNotDraw(bl2);
        this.invalidate();
    }

    public void setSplitBackground(Drawable drawable2) {
        boolean bl;
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            this.unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = drawable2;
        boolean bl2 = false;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            if (this.mIsSplit && (drawable2 = this.mSplitBackground) != null) {
                drawable2.setBounds(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
            }
        }
        if (this.mIsSplit) {
            bl = bl2;
            if (this.mSplitBackground == null) {
                bl = true;
            }
        } else {
            bl = bl2;
            if (this.mBackground == null) {
                bl = bl2;
                if (this.mStackedBackground == null) {
                    bl = true;
                }
            }
        }
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setStackedBackground(Drawable drawable2) {
        Drawable drawable3 = this.mStackedBackground;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            this.unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            if (this.mIsStacked && (drawable2 = this.mStackedBackground) != null) {
                drawable2.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        boolean bl = this.mIsSplit;
        boolean bl2 = true;
        if (bl) {
            if (this.mSplitBackground != null) {
                bl2 = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            bl2 = false;
        }
        this.setWillNotDraw(bl2);
        this.invalidate();
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        Object object = this.mTabContainer;
        if (object != null) {
            this.removeView((View)object);
        }
        this.mTabContainer = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            this.addView(scrollingTabContainerView);
            object = scrollingTabContainerView.getLayoutParams();
            ((ViewGroup.LayoutParams)object).width = -1;
            ((ViewGroup.LayoutParams)object).height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean bl) {
        this.mIsTransitioning = bl;
        int n = bl ? 393216 : 262144;
        this.setDescendantFocusability(n);
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setVisible(bl, false);
        }
        if ((drawable2 = this.mStackedBackground) != null) {
            drawable2.setVisible(bl, false);
        }
        if ((drawable2 = this.mSplitBackground) != null) {
            drawable2.setVisible(bl, false);
        }
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n) {
        if (n != 0) {
            return super.startActionModeForChild(view, callback, n);
        }
        return null;
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = drawable2 == this.mBackground && !this.mIsSplit || drawable2 == this.mStackedBackground && this.mIsStacked || drawable2 == this.mSplitBackground && this.mIsSplit || super.verifyDrawable(drawable2);
        return bl;
    }

    private class ActionBarBackgroundDrawable
    extends Drawable {
        private ActionBarBackgroundDrawable() {
        }

        @Override
        public void draw(Canvas canvas) {
            if (ActionBarContainer.this.mIsSplit) {
                if (ActionBarContainer.this.mSplitBackground != null) {
                    ActionBarContainer.this.mSplitBackground.draw(canvas);
                }
            } else {
                if (ActionBarContainer.this.mBackground != null) {
                    ActionBarContainer.this.mBackground.draw(canvas);
                }
                if (ActionBarContainer.this.mStackedBackground != null && ActionBarContainer.this.mIsStacked) {
                    ActionBarContainer.this.mStackedBackground.draw(canvas);
                }
            }
        }

        @Override
        public int getOpacity() {
            if (ActionBarContainer.this.mIsSplit) {
                if (ActionBarContainer.this.mSplitBackground != null && ActionBarContainer.this.mSplitBackground.getOpacity() == -1) {
                    return -1;
                }
            } else {
                if (ActionBarContainer.this.mIsStacked && (ActionBarContainer.this.mStackedBackground == null || ActionBarContainer.this.mStackedBackground.getOpacity() != -1)) {
                    return 0;
                }
                if (!ActionBarContainer.isCollapsed(ActionBarContainer.this.mActionBarView) && ActionBarContainer.this.mBackground != null && ActionBarContainer.this.mBackground.getOpacity() == -1) {
                    return -1;
                }
            }
            return 0;
        }

        @Override
        public void getOutline(Outline outline) {
            if (ActionBarContainer.this.mIsSplit) {
                if (ActionBarContainer.this.mSplitBackground != null) {
                    ActionBarContainer.this.mSplitBackground.getOutline(outline);
                }
            } else if (ActionBarContainer.this.mBackground != null) {
                ActionBarContainer.this.mBackground.getOutline(outline);
            }
        }

        @Override
        public void setAlpha(int n) {
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

}

