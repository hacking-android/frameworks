/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.internal.view.ActionBarPolicy;

public class ScrollingTabContainerView
extends HorizontalScrollView
implements AdapterView.OnItemClickListener {
    private static final int FADE_DURATION = 200;
    private static final String TAG = "ScrollingTabContainerView";
    private static final TimeInterpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    private LinearLayout mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected Animator mVisibilityAnim;

    @UnsupportedAppUsage
    public ScrollingTabContainerView(Context object) {
        super((Context)object);
        this.setHorizontalScrollBarEnabled(false);
        object = ActionBarPolicy.get((Context)object);
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
        this.mTabLayout = this.createTabLayout();
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    private Spinner createSpinner() {
        Spinner spinner = new Spinner(this.getContext(), null, 16843479);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        spinner.setOnItemClickListenerInt(this);
        return spinner;
    }

    private LinearLayout createTabLayout() {
        LinearLayout linearLayout = new LinearLayout(this.getContext(), null, 16843508);
        linearLayout.setMeasureWithLargestChildEnabled(true);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        return linearLayout;
    }

    private TabView createTabView(Context object, ActionBar.Tab tab, boolean bl) {
        object = new TabView((Context)object, tab, bl);
        if (bl) {
            ((View)object).setBackgroundDrawable(null);
            ((View)object).setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
        } else {
            ((View)object).setFocusable(true);
            if (this.mTabClickListener == null) {
                this.mTabClickListener = new TabClickListener();
            }
            ((View)object).setOnClickListener(this.mTabClickListener);
        }
        return object;
    }

    private boolean isCollapsed() {
        Spinner spinner = this.mTabSpinner;
        boolean bl = spinner != null && spinner.getParent() == this;
        return bl;
    }

    private void performCollapse() {
        Object object;
        if (this.isCollapsed()) {
            return;
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = this.createSpinner();
        }
        this.removeView(this.mTabLayout);
        this.addView((View)this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
            object = new TabAdapter(this.mContext);
            ((TabAdapter)object).setDropDownViewContext(this.mTabSpinner.getPopupContext());
            this.mTabSpinner.setAdapter((SpinnerAdapter)object);
        }
        if ((object = this.mTabSelector) != null) {
            this.removeCallbacks((Runnable)object);
            this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!this.isCollapsed()) {
            return false;
        }
        this.removeView(this.mTabSpinner);
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        this.setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    @UnsupportedAppUsage
    public void addTab(ActionBar.Tab object, int n, boolean bl) {
        object = this.createTabView(this.mContext, (ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)object, n, new LinearLayout.LayoutParams(0, -1, 1.0f));
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (bl) {
            ((TabView)object).setSelected(true);
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    @UnsupportedAppUsage
    public void addTab(ActionBar.Tab object, boolean bl) {
        TabView tabView = this.createTabView(this.mContext, (ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)tabView, new LinearLayout.LayoutParams(0, -1, 1.0f));
        object = this.mTabSpinner;
        if (object != null) {
            ((TabAdapter)((AbsSpinner)object).getAdapter()).notifyDataSetChanged();
        }
        if (bl) {
            tabView.setSelected(true);
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    @UnsupportedAppUsage
    public void animateToTab(int n) {
        final View view = this.mTabLayout.getChildAt(n);
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
        this.mTabSelector = new Runnable(){

            @Override
            public void run() {
                int n = view.getLeft();
                int n2 = (ScrollingTabContainerView.this.getWidth() - view.getWidth()) / 2;
                ScrollingTabContainerView.this.smoothScrollTo(n - n2, 0);
                ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        this.post(this.mTabSelector);
    }

    @UnsupportedAppUsage
    public void animateToVisibility(int n) {
        Animator animator2 = this.mVisibilityAnim;
        if (animator2 != null) {
            animator2.cancel();
        }
        if (n == 0) {
            if (this.getVisibility() != 0) {
                this.setAlpha(0.0f);
            }
            animator2 = ObjectAnimator.ofFloat((Object)this, "alpha", 1.0f);
            ((ObjectAnimator)animator2).setDuration(200L);
            ((ValueAnimator)animator2).setInterpolator(sAlphaInterpolator);
            animator2.addListener(this.mVisAnimListener.withFinalVisibility(n));
            ((ObjectAnimator)animator2).start();
        } else {
            animator2 = ObjectAnimator.ofFloat((Object)this, "alpha", 0.0f);
            ((ObjectAnimator)animator2).setDuration(200L);
            ((ValueAnimator)animator2).setInterpolator(sAlphaInterpolator);
            animator2.addListener(this.mVisAnimListener.withFinalVisibility(n));
            ((ObjectAnimator)animator2).start();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            this.post(runnable);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = ActionBarPolicy.get(this.getContext());
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        ((TabView)view).getTab().select();
    }

    @Override
    public void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode(n);
        n2 = 1;
        boolean bl = n3 == 1073741824;
        this.setFillViewport(bl);
        int n4 = this.mTabLayout.getChildCount();
        if (n4 > 1 && (n3 == 1073741824 || n3 == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = n4 > 2 ? (int)((float)View.MeasureSpec.getSize(n) * 0.4f) : View.MeasureSpec.getSize(n) / 2;
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        n3 = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
        if (bl || !this.mAllowCollapse) {
            n2 = 0;
        }
        if (n2 != 0) {
            this.mTabLayout.measure(0, n3);
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(n)) {
                this.performCollapse();
            } else {
                this.performExpand();
            }
        } else {
            this.performExpand();
        }
        n2 = this.getMeasuredWidth();
        super.onMeasure(n, n3);
        n = this.getMeasuredWidth();
        if (bl && n2 != n) {
            this.setTabSelected(this.mSelectedTabIndex);
        }
    }

    @UnsupportedAppUsage
    public void removeAllTabs() {
        this.mTabLayout.removeAllViews();
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    @UnsupportedAppUsage
    public void removeTabAt(int n) {
        this.mTabLayout.removeViewAt(n);
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    @UnsupportedAppUsage
    public void setAllowCollapse(boolean bl) {
        this.mAllowCollapse = bl;
    }

    public void setContentHeight(int n) {
        this.mContentHeight = n;
        this.requestLayout();
    }

    @UnsupportedAppUsage
    public void setTabSelected(int n) {
        View view;
        this.mSelectedTabIndex = n;
        int n2 = this.mTabLayout.getChildCount();
        for (int i = 0; i < n2; ++i) {
            view = this.mTabLayout.getChildAt(i);
            boolean bl = i == n;
            view.setSelected(bl);
            if (!bl) continue;
            this.animateToTab(n);
        }
        view = this.mTabSpinner;
        if (view != null && n >= 0) {
            ((AbsSpinner)view).setSelection(n);
        }
    }

    @UnsupportedAppUsage
    public void updateTab(int n) {
        ((TabView)this.mTabLayout.getChildAt(n)).update();
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    private class TabAdapter
    extends BaseAdapter {
        private Context mDropDownContext;

        public TabAdapter(Context context) {
            this.setDropDownViewContext(context);
        }

        @Override
        public int getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        @Override
        public View getDropDownView(int n, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ScrollingTabContainerView.this.createTabView(this.mDropDownContext, (ActionBar.Tab)this.getItem(n), true);
            } else {
                ((TabView)view).bindTab((ActionBar.Tab)this.getItem(n));
            }
            return view;
        }

        @Override
        public Object getItem(int n) {
            return ((TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(n)).getTab();
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ScrollingTabContainerView.this;
                view = ((ScrollingTabContainerView)view).createTabView(((ScrollingTabContainerView)view).mContext, (ActionBar.Tab)this.getItem(n), true);
            } else {
                ((TabView)view).bindTab((ActionBar.Tab)this.getItem(n));
            }
            return view;
        }

        public void setDropDownViewContext(Context context) {
            this.mDropDownContext = context;
        }
    }

    private class TabClickListener
    implements View.OnClickListener {
        private TabClickListener() {
        }

        @Override
        public void onClick(View view) {
            ((TabView)view).getTab().select();
            int n = ScrollingTabContainerView.this.mTabLayout.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view2 = ScrollingTabContainerView.this.mTabLayout.getChildAt(i);
                boolean bl = view2 == view;
                view2.setSelected(bl);
            }
        }
    }

    private class TabView
    extends LinearLayout {
        private View mCustomView;
        private ImageView mIconView;
        private ActionBar.Tab mTab;
        private TextView mTextView;

        public TabView(Context context, ActionBar.Tab tab, boolean bl) {
            super(context, null, 16843507);
            this.mTab = tab;
            if (bl) {
                this.setGravity(8388627);
            }
            this.update();
        }

        public void bindTab(ActionBar.Tab tab) {
            this.mTab = tab;
            this.update();
        }

        @Override
        public CharSequence getAccessibilityClassName() {
            return ActionBar.Tab.class.getName();
        }

        public ActionBar.Tab getTab() {
            return this.mTab;
        }

        @Override
        public void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            if (ScrollingTabContainerView.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), n2);
            }
        }

        @Override
        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (bl2 && bl) {
                this.sendAccessibilityEvent(4);
            }
        }

        public void update() {
            ActionBar.Tab tab = this.mTab;
            Object object = tab.getCustomView();
            Object object2 = null;
            if (object != null) {
                object2 = ((View)object).getParent();
                if (object2 != this) {
                    if (object2 != null) {
                        ((ViewGroup)object2).removeView((View)object);
                    }
                    this.addView((View)object);
                }
                this.mCustomView = object;
                object2 = this.mTextView;
                if (object2 != null) {
                    ((View)object2).setVisibility(8);
                }
                if ((object2 = this.mIconView) != null) {
                    ((ImageView)object2).setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
            } else {
                Object object3;
                View view;
                object = this.mCustomView;
                if (object != null) {
                    this.removeView((View)object);
                    this.mCustomView = null;
                }
                Drawable drawable2 = tab.getIcon();
                object = tab.getText();
                if (drawable2 != null) {
                    if (this.mIconView == null) {
                        view = new ImageView(this.getContext());
                        object3 = new LinearLayout.LayoutParams(-2, -2);
                        ((LinearLayout.LayoutParams)object3).gravity = 16;
                        view.setLayoutParams((ViewGroup.LayoutParams)object3);
                        this.addView(view, 0);
                        this.mIconView = view;
                    }
                    this.mIconView.setImageDrawable(drawable2);
                    this.mIconView.setVisibility(0);
                } else {
                    object3 = this.mIconView;
                    if (object3 != null) {
                        ((ImageView)object3).setVisibility(8);
                        this.mIconView.setImageDrawable(null);
                    }
                }
                boolean bl = TextUtils.isEmpty((CharSequence)object) ^ true;
                if (bl) {
                    if (this.mTextView == null) {
                        view = new TextView(this.getContext(), null, 16843509);
                        ((TextView)view).setEllipsize(TextUtils.TruncateAt.END);
                        object3 = new LinearLayout.LayoutParams(-2, -2);
                        ((LinearLayout.LayoutParams)object3).gravity = 16;
                        view.setLayoutParams((ViewGroup.LayoutParams)object3);
                        this.addView(view);
                        this.mTextView = view;
                    }
                    this.mTextView.setText((CharSequence)object);
                    this.mTextView.setVisibility(0);
                } else {
                    object = this.mTextView;
                    if (object != null) {
                        ((View)object).setVisibility(8);
                        this.mTextView.setText(null);
                    }
                }
                object = this.mIconView;
                if (object != null) {
                    ((View)object).setContentDescription(tab.getContentDescription());
                }
                if (!bl) {
                    object2 = tab.getContentDescription();
                }
                this.setTooltipText((CharSequence)object2);
            }
        }
    }

    protected class VisibilityAnimListener
    implements Animator.AnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        @Override
        public void onAnimationEnd(Animator object) {
            if (this.mCanceled) {
                return;
            }
            object = ScrollingTabContainerView.this;
            ((ScrollingTabContainerView)object).mVisibilityAnim = null;
            ((View)object).setVisibility(this.mFinalVisibility);
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            ScrollingTabContainerView.this.setVisibility(0);
            ScrollingTabContainerView.this.mVisibilityAnim = animator2;
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(int n) {
            this.mFinalVisibility = n;
            return this;
        }
    }

}

