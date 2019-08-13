/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.view.menu.IconMenuItemView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;
import java.util.ArrayList;

public final class IconMenuView
extends ViewGroup
implements MenuBuilder.ItemInvoker,
MenuView,
Runnable {
    private static final int ITEM_CAPTION_CYCLE_DELAY = 1000;
    private int mAnimations;
    private boolean mHasStaleChildren;
    private Drawable mHorizontalDivider;
    private int mHorizontalDividerHeight;
    private ArrayList<Rect> mHorizontalDividerRects;
    @UnsupportedAppUsage
    private Drawable mItemBackground;
    private boolean mLastChildrenCaptionMode;
    private int[] mLayout;
    private int mLayoutNumRows;
    @UnsupportedAppUsage
    private int mMaxItems;
    private int mMaxItemsPerRow;
    private int mMaxRows;
    @UnsupportedAppUsage
    private MenuBuilder mMenu;
    private boolean mMenuBeingLongpressed = false;
    private Drawable mMoreIcon;
    private int mNumActualItemsShown;
    private int mRowHeight;
    private Drawable mVerticalDivider;
    private ArrayList<Rect> mVerticalDividerRects;
    private int mVerticalDividerWidth;

    public IconMenuView(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.IconMenuView, 0, 0);
        this.mRowHeight = typedArray.getDimensionPixelSize(0, 64);
        this.mMaxRows = typedArray.getInt(1, 2);
        this.mMaxItems = typedArray.getInt(4, 6);
        this.mMaxItemsPerRow = typedArray.getInt(2, 3);
        this.mMoreIcon = typedArray.getDrawable(3);
        typedArray.recycle();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.MenuView, 0, 0);
        this.mItemBackground = ((TypedArray)object).getDrawable(5);
        this.mHorizontalDivider = ((TypedArray)object).getDrawable(2);
        this.mHorizontalDividerRects = new ArrayList();
        this.mVerticalDivider = ((TypedArray)object).getDrawable(3);
        this.mVerticalDividerRects = new ArrayList();
        this.mAnimations = ((TypedArray)object).getResourceId(0, 0);
        ((TypedArray)object).recycle();
        object = this.mHorizontalDivider;
        if (object != null) {
            this.mHorizontalDividerHeight = ((Drawable)object).getIntrinsicHeight();
            if (this.mHorizontalDividerHeight == -1) {
                this.mHorizontalDividerHeight = 1;
            }
        }
        if ((object = this.mVerticalDivider) != null) {
            this.mVerticalDividerWidth = ((Drawable)object).getIntrinsicWidth();
            if (this.mVerticalDividerWidth == -1) {
                this.mVerticalDividerWidth = 1;
            }
        }
        this.mLayout = new int[this.mMaxRows];
        this.setWillNotDraw(false);
        this.setFocusableInTouchMode(true);
        this.setDescendantFocusability(262144);
    }

    private void calculateItemFittingMetadata(int n) {
        int n2 = this.mMaxItemsPerRow;
        int n3 = this.getChildCount();
        block0 : for (int i = 0; i < n3; ++i) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
            layoutParams.maxNumItemsOnRow = 1;
            for (int j = n2; j > 0; --j) {
                if (layoutParams.desiredWidth >= n / j) continue;
                layoutParams.maxNumItemsOnRow = j;
                continue block0;
            }
        }
    }

    private boolean doItemsFit() {
        int n = 0;
        int[] arrn = this.mLayout;
        int n2 = this.mLayoutNumRows;
        for (int i = 0; i < n2; ++i) {
            int n3;
            int n4 = arrn[i];
            if (n4 == 1) {
                n3 = n + 1;
            } else {
                int n5 = n4;
                do {
                    n3 = n++;
                    if (n5 <= 0) break;
                    if (((LayoutParams)this.getChildAt((int)n).getLayoutParams()).maxNumItemsOnRow < n4) {
                        return false;
                    }
                    --n5;
                } while (true);
            }
            n = n3;
        }
        return true;
    }

    private void layoutItems(int n) {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            this.mLayoutNumRows = 0;
            return;
        }
        for (n = java.lang.Math.min((int)((int)java.lang.Math.ceil((double)((double)((float)n2 / (float)this.mMaxItemsPerRow)))), (int)this.mMaxRows); n <= this.mMaxRows; ++n) {
            this.layoutItemsUsingGravity(n, n2);
            if (n >= n2 || this.doItemsFit()) break;
        }
    }

    private void layoutItemsUsingGravity(int n, int n2) {
        int n3 = n2 / n;
        int[] arrn = this.mLayout;
        for (int i = 0; i < n; ++i) {
            arrn[i] = n3;
            if (i < n - n2 % n) continue;
            arrn[i] = arrn[i] + 1;
        }
        this.mLayoutNumRows = n;
    }

    private void positionChildren(int n, int n2) {
        if (this.mHorizontalDivider != null) {
            this.mHorizontalDividerRects.clear();
        }
        if (this.mVerticalDivider != null) {
            this.mVerticalDividerRects.clear();
        }
        int n3 = this.mLayoutNumRows;
        int[] arrn = this.mLayout;
        int n4 = 0;
        Object object = null;
        float f = 0.0f;
        float f2 = (float)(n2 - this.mHorizontalDividerHeight * (n3 - 1)) / (float)n3;
        n2 = n3;
        for (int i = 0; i < n2; ++i) {
            float f3 = 0.0f;
            float f4 = (float)(n - this.mVerticalDividerWidth * (arrn[i] - 1)) / (float)arrn[i];
            for (int j = 0; j < arrn[i]; ++j) {
                object = this.getChildAt(n4);
                ((View)object).measure(View.MeasureSpec.makeMeasureSpec((int)f4, 1073741824), View.MeasureSpec.makeMeasureSpec((int)f2, 1073741824));
                object = (LayoutParams)((View)object).getLayoutParams();
                ((LayoutParams)object).left = (int)f3;
                ((LayoutParams)object).right = (int)(f3 + f4);
                ((LayoutParams)object).top = (int)f;
                ((LayoutParams)object).bottom = (int)(f + f2);
                f3 += f4;
                ++n4;
                if (this.mVerticalDivider != null) {
                    this.mVerticalDividerRects.add(new Rect((int)f3, (int)f, (int)((float)this.mVerticalDividerWidth + f3), (int)(f + f2)));
                }
                f3 += (float)this.mVerticalDividerWidth;
            }
            if (object != null) {
                ((LayoutParams)object).right = n;
            }
            f = f3 = f + f2;
            if (this.mHorizontalDivider == null) continue;
            f = f3;
            if (i >= n3 - 1) continue;
            this.mHorizontalDividerRects.add(new Rect(0, (int)f3, n, (int)((float)this.mHorizontalDividerHeight + f3)));
            f = f3 + (float)this.mHorizontalDividerHeight;
        }
    }

    private void setChildrenCaptionMode(boolean bl) {
        this.mLastChildrenCaptionMode = bl;
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            ((IconMenuItemView)this.getChildAt(i)).setCaptionMode(bl);
        }
    }

    private void setCycleShortcutCaptionMode(boolean bl) {
        if (!bl) {
            this.removeCallbacks(this);
            this.setChildrenCaptionMode(false);
            this.mMenuBeingLongpressed = false;
        } else {
            this.setChildrenCaptionMode(true);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @UnsupportedAppUsage
    IconMenuItemView createMoreItemView() {
        Context context = this.getContext();
        IconMenuItemView iconMenuItemView = (IconMenuItemView)LayoutInflater.from(context).inflate(17367163, null);
        iconMenuItemView.initialize(context.getResources().getText(17040450), this.mMoreIcon);
        iconMenuItemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                IconMenuView.this.mMenu.changeMenuMode();
            }
        });
        return iconMenuItemView;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 82) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                this.removeCallbacks(this);
                this.postDelayed(this, ViewConfiguration.getLongPressTimeout());
            } else if (keyEvent.getAction() == 1) {
                if (this.mMenuBeingLongpressed) {
                    this.setCycleShortcutCaptionMode(false);
                    return true;
                }
                this.removeCallbacks(this);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    Drawable getItemBackgroundDrawable() {
        return this.mItemBackground.getConstantState().newDrawable(this.getContext().getResources());
    }

    public int[] getLayout() {
        return this.mLayout;
    }

    public int getLayoutNumRows() {
        return this.mLayoutNumRows;
    }

    int getMaxItems() {
        return this.mMaxItems;
    }

    @UnsupportedAppUsage
    int getNumActualItemsShown() {
        return this.mNumActualItemsShown;
    }

    @Override
    public int getWindowAnimations() {
        return this.mAnimations;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    void markStaleChildren() {
        if (!this.mHasStaleChildren) {
            this.mHasStaleChildren = true;
            this.requestLayout();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.requestFocus();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.setCycleShortcutCaptionMode(false);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Object object;
        int n;
        Object object2 = this.mHorizontalDivider;
        if (object2 != null) {
            object = this.mHorizontalDividerRects;
            for (n = object.size() - 1; n >= 0; --n) {
                ((Drawable)object2).setBounds((Rect)((ArrayList)object).get(n));
                ((Drawable)object2).draw(canvas);
            }
        }
        if ((object = this.mVerticalDivider) != null) {
            object2 = this.mVerticalDividerRects;
            for (n = object2.size() - 1; n >= 0; --n) {
                ((Drawable)object).setBounds((Rect)((ArrayList)object2).get(n));
                ((Drawable)object).draw(canvas);
            }
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        for (n = this.getChildCount() - 1; n >= 0; --n) {
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            view.layout(layoutParams.left, layoutParams.top, layoutParams.right, layoutParams.bottom);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = IconMenuView.resolveSize(Integer.MAX_VALUE, n);
        this.calculateItemFittingMetadata(n3);
        this.layoutItems(n3);
        n = this.mLayoutNumRows;
        int n4 = this.mRowHeight;
        int n5 = this.mHorizontalDividerHeight;
        this.setMeasuredDimension(n3, IconMenuView.resolveSize((n4 + n5) * n - n5, n2));
        if (n > 0) {
            this.positionChildren(this.getMeasuredWidth(), this.getMeasuredHeight());
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)object;
        super.onRestoreInstanceState(((AbsSavedState)object).getSuperState());
        if (((SavedState)object).focusedPosition >= this.getChildCount()) {
            return;
        }
        object = this.getChildAt(((SavedState)object).focusedPosition);
        if (object != null) {
            ((View)object).requestFocus();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        View view = this.getFocusedChild();
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            if (this.getChildAt(i) != view) continue;
            return new SavedState(parcelable, i);
        }
        return new SavedState(parcelable, -1);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        if (!bl) {
            this.setCycleShortcutCaptionMode(false);
        }
        super.onWindowFocusChanged(bl);
    }

    @Override
    public void run() {
        if (this.mMenuBeingLongpressed) {
            this.setChildrenCaptionMode(this.mLastChildrenCaptionMode ^ true);
        } else {
            this.mMenuBeingLongpressed = true;
            this.setCycleShortcutCaptionMode(true);
        }
        this.postDelayed(this, 1000L);
    }

    void setNumActualItemsShown(int n) {
        this.mNumActualItemsShown = n;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        int bottom;
        int desiredWidth;
        int left;
        int maxNumItemsOnRow;
        int right;
        int top;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    private static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int focusedPosition;

        @UnsupportedAppUsage
        private SavedState(Parcel parcel) {
            super(parcel);
            this.focusedPosition = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int n) {
            super(parcelable);
            this.focusedPosition = n;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.focusedPosition);
        }

    }

}

