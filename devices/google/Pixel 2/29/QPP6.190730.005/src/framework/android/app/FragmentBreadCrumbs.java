/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.BackStackRecord;
import android.app.FragmentManager;
import android.app.FragmentManagerImpl;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
public class FragmentBreadCrumbs
extends ViewGroup
implements FragmentManager.OnBackStackChangedListener {
    private static final int DEFAULT_GRAVITY = 8388627;
    Activity mActivity;
    LinearLayout mContainer;
    private int mGravity;
    LayoutInflater mInflater;
    private int mLayoutResId;
    int mMaxVisible = -1;
    private OnBreadCrumbClickListener mOnBreadCrumbClickListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View object) {
            if (((View)object).getTag() instanceof FragmentManager.BackStackEntry) {
                FragmentManager.BackStackEntry backStackEntry = (FragmentManager.BackStackEntry)((View)object).getTag();
                if (backStackEntry == FragmentBreadCrumbs.this.mParentEntry) {
                    if (FragmentBreadCrumbs.this.mParentClickListener != null) {
                        FragmentBreadCrumbs.this.mParentClickListener.onClick((View)object);
                    }
                } else {
                    OnBreadCrumbClickListener onBreadCrumbClickListener;
                    if (FragmentBreadCrumbs.this.mOnBreadCrumbClickListener != null && (onBreadCrumbClickListener = FragmentBreadCrumbs.this.mOnBreadCrumbClickListener).onBreadCrumbClick((FragmentManager.BackStackEntry)(object = backStackEntry == FragmentBreadCrumbs.this.mTopEntry ? null : backStackEntry), 0)) {
                        return;
                    }
                    if (backStackEntry == FragmentBreadCrumbs.this.mTopEntry) {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack();
                    } else {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack(backStackEntry.getId(), 0);
                    }
                }
            }
        }
    };
    private View.OnClickListener mParentClickListener;
    BackStackRecord mParentEntry;
    private int mTextColor;
    BackStackRecord mTopEntry;

    public FragmentBreadCrumbs(Context context) {
        this(context, null);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17956933);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public FragmentBreadCrumbs(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.FragmentBreadCrumbs, n, n2);
        this.mGravity = ((TypedArray)object).getInt(0, 8388627);
        this.mLayoutResId = ((TypedArray)object).getResourceId(2, 17367154);
        this.mTextColor = ((TypedArray)object).getColor(1, 0);
        ((TypedArray)object).recycle();
    }

    private BackStackRecord createBackStackEntry(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        BackStackRecord backStackRecord = new BackStackRecord((FragmentManagerImpl)this.mActivity.getFragmentManager());
        backStackRecord.setBreadCrumbTitle(charSequence);
        backStackRecord.setBreadCrumbShortTitle(charSequence2);
        return backStackRecord;
    }

    private FragmentManager.BackStackEntry getPreEntry(int n) {
        BackStackRecord backStackRecord = this.mParentEntry;
        if (backStackRecord != null) {
            if (n != 0) {
                backStackRecord = this.mTopEntry;
            }
            return backStackRecord;
        }
        return this.mTopEntry;
    }

    private int getPreEntryCount() {
        BackStackRecord backStackRecord = this.mTopEntry;
        int n = 1;
        int n2 = backStackRecord != null ? 1 : 0;
        if (this.mParentEntry == null) {
            n = 0;
        }
        return n2 + n;
    }

    @Override
    public void onBackStackChanged() {
        this.updateCrumbs();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.getChildCount() == 0) {
            return;
        }
        View view = this.getChildAt(0);
        n4 = this.mPaddingTop;
        int n5 = this.mPaddingTop;
        int n6 = view.getMeasuredHeight();
        int n7 = this.mPaddingBottom;
        n = this.getLayoutDirection();
        if ((n = Gravity.getAbsoluteGravity(this.mGravity & 8388615, n)) != 1) {
            if (n != 5) {
                n2 = this.mPaddingLeft;
                n = view.getMeasuredWidth() + n2;
            } else {
                n = this.mRight - this.mLeft - this.mPaddingRight;
                n2 = n - view.getMeasuredWidth();
            }
        } else {
            n2 = this.mPaddingLeft + (this.mRight - this.mLeft - view.getMeasuredWidth()) / 2;
            n = view.getMeasuredWidth() + n2;
        }
        n3 = n2;
        if (n2 < this.mPaddingLeft) {
            n3 = this.mPaddingLeft;
        }
        n2 = n;
        if (n > this.mRight - this.mLeft - this.mPaddingRight) {
            n2 = this.mRight - this.mLeft - this.mPaddingRight;
        }
        view.layout(n3, n4, n2, n5 + n6 - n7);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.getChildCount();
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            View view = this.getChildAt(n4);
            int n9 = n6;
            int n10 = n7;
            n3 = n8;
            if (view.getVisibility() != 8) {
                this.measureChild(view, n, n2);
                n10 = Math.max(n7, view.getMeasuredWidth());
                n9 = Math.max(n6, view.getMeasuredHeight());
                n3 = FragmentBreadCrumbs.combineMeasuredStates(n8, view.getMeasuredState());
            }
            n6 = n9;
            n7 = n10;
            n8 = n3;
        }
        n3 = this.mPaddingLeft;
        n4 = this.mPaddingRight;
        n6 = Math.max(n6 + (this.mPaddingTop + this.mPaddingBottom), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(FragmentBreadCrumbs.resolveSizeAndState(Math.max(n7 + (n3 + n4), this.getSuggestedMinimumWidth()), n, n8), FragmentBreadCrumbs.resolveSizeAndState(n6, n2, n8 << 16));
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
        this.mInflater = (LayoutInflater)activity.getSystemService("layout_inflater");
        this.mContainer = (LinearLayout)this.mInflater.inflate(17367156, (ViewGroup)this, false);
        this.addView(this.mContainer);
        activity.getFragmentManager().addOnBackStackChangedListener(this);
        this.updateCrumbs();
        this.setLayoutTransition(new LayoutTransition());
    }

    public void setMaxVisible(int n) {
        if (n >= 1) {
            this.mMaxVisible = n;
            return;
        }
        throw new IllegalArgumentException("visibleCrumbs must be greater than zero");
    }

    public void setOnBreadCrumbClickListener(OnBreadCrumbClickListener onBreadCrumbClickListener) {
        this.mOnBreadCrumbClickListener = onBreadCrumbClickListener;
    }

    public void setParentTitle(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) {
        this.mParentEntry = this.createBackStackEntry(charSequence, charSequence2);
        this.mParentClickListener = onClickListener;
        this.updateCrumbs();
    }

    public void setTitle(CharSequence charSequence, CharSequence charSequence2) {
        this.mTopEntry = this.createBackStackEntry(charSequence, charSequence2);
        this.updateCrumbs();
    }

    void updateCrumbs() {
        int n;
        int n2;
        FragmentManager.BackStackEntry backStackEntry;
        Object object = this.mActivity.getFragmentManager();
        int n3 = ((FragmentManager)object).getBackStackEntryCount();
        int n4 = this.getPreEntryCount();
        int n5 = this.mContainer.getChildCount();
        for (n = 0; n < n3 + n4; ++n) {
            backStackEntry = n < n4 ? this.getPreEntry(n) : ((FragmentManager)object).getBackStackEntryAt(n - n4);
            n2 = n5;
            if (n < n5) {
                n2 = n5;
                if (this.mContainer.getChildAt(n).getTag() != backStackEntry) {
                    for (n2 = n; n2 < n5; ++n2) {
                        this.mContainer.removeViewAt(n);
                    }
                    n2 = n;
                }
            }
            if (n >= n2) {
                View view = this.mInflater.inflate(this.mLayoutResId, (ViewGroup)this, false);
                TextView textView = (TextView)view.findViewById(16908310);
                textView.setText(backStackEntry.getBreadCrumbTitle());
                textView.setTag(backStackEntry);
                textView.setTextColor(this.mTextColor);
                if (n == 0) {
                    ((View)view.findViewById(16909065)).setVisibility(8);
                }
                this.mContainer.addView(view);
                textView.setOnClickListener(this.mOnClickListener);
            }
            n5 = n2;
        }
        for (n = this.mContainer.getChildCount(); n > n3 + n4; --n) {
            this.mContainer.removeViewAt(n - 1);
        }
        for (n5 = 0; n5 < n; ++n5) {
            object = this.mContainer.getChildAt(n5);
            backStackEntry = ((View)object).findViewById(16908310);
            boolean bl = n5 < n - 1;
            ((View)((Object)backStackEntry)).setEnabled(bl);
            n2 = this.mMaxVisible;
            if (n2 <= 0) continue;
            n2 = n5 < n - n2 ? 8 : 0;
            ((View)object).setVisibility(n2);
            backStackEntry = ((View)object).findViewById(16909065);
            n2 = n5 > n - this.mMaxVisible && n5 != 0 ? 0 : 8;
            ((View)((Object)backStackEntry)).setVisibility(n2);
        }
    }

    @Deprecated
    public static interface OnBreadCrumbClickListener {
        public boolean onBreadCrumbClick(FragmentManager.BackStackEntry var1, int var2);
    }

}

