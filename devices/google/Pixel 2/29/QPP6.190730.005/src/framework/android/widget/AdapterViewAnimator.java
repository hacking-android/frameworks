/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Advanceable;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class AdapterViewAnimator
extends AdapterView<Adapter>
implements RemoteViewsAdapter.RemoteAdapterConnectionCallback,
Advanceable {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final String TAG = "RemoteViewAnimator";
    static final int TOUCH_MODE_DOWN_IN_CURRENT_VIEW = 1;
    static final int TOUCH_MODE_HANDLED = 2;
    static final int TOUCH_MODE_NONE = 0;
    int mActiveOffset = 0;
    Adapter mAdapter;
    boolean mAnimateFirstTime = true;
    int mCurrentWindowEnd = -1;
    int mCurrentWindowStart = 0;
    int mCurrentWindowStartUnbounded = 0;
    AdapterView<Adapter> mDataSetObserver;
    boolean mDeferNotifyDataSetChanged = false;
    boolean mFirstTime = true;
    ObjectAnimator mInAnimation;
    boolean mLoopViews = true;
    int mMaxNumActiveViews = 1;
    ObjectAnimator mOutAnimation;
    private Runnable mPendingCheckForTap;
    ArrayList<Integer> mPreviousViews;
    int mReferenceChildHeight = -1;
    int mReferenceChildWidth = -1;
    RemoteViewsAdapter mRemoteViewsAdapter;
    private int mRestoreWhichChild = -1;
    private int mTouchMode = 0;
    HashMap<Integer, ViewAndMetaData> mViewsMap = new HashMap();
    int mWhichChild = 0;

    public AdapterViewAnimator(Context context) {
        this(context, null);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AdapterViewAnimator(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AdapterViewAnimator, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.AdapterViewAnimator, attributeSet, typedArray, n, n2);
        n = typedArray.getResourceId(0, 0);
        if (n > 0) {
            this.setInAnimation(context, n);
        } else {
            this.setInAnimation(this.getDefaultInAnimation());
        }
        n = typedArray.getResourceId(1, 0);
        if (n > 0) {
            this.setOutAnimation(context, n);
        } else {
            this.setOutAnimation(this.getDefaultOutAnimation());
        }
        this.setAnimateFirstView(typedArray.getBoolean(2, true));
        this.mLoopViews = typedArray.getBoolean(3, false);
        typedArray.recycle();
        this.initViewAnimator();
    }

    private void addChild(View view) {
        this.addViewInLayout(view, -1, this.createOrReuseLayoutParams(view));
        if (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) {
            int n = View.MeasureSpec.makeMeasureSpec(0, 0);
            view.measure(n, n);
            this.mReferenceChildWidth = view.getMeasuredWidth();
            this.mReferenceChildHeight = view.getMeasuredHeight();
        }
    }

    private ViewAndMetaData getMetaDataForChild(View view) {
        for (ViewAndMetaData viewAndMetaData : this.mViewsMap.values()) {
            if (viewAndMetaData.view != view) continue;
            return viewAndMetaData;
        }
        return null;
    }

    private void initViewAnimator() {
        this.mPreviousViews = new ArrayList();
    }

    private void measureChildren() {
        int n = this.getChildCount();
        int n2 = this.getMeasuredWidth();
        int n3 = this.mPaddingLeft;
        int n4 = this.mPaddingRight;
        int n5 = this.getMeasuredHeight();
        int n6 = this.mPaddingTop;
        int n7 = this.mPaddingBottom;
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).measure(View.MeasureSpec.makeMeasureSpec(n2 - n3 - n4, 1073741824), View.MeasureSpec.makeMeasureSpec(n5 - n6 - n7, 1073741824));
        }
    }

    private void setDisplayedChild(int n, boolean bl) {
        if (this.mAdapter != null) {
            this.mWhichChild = n;
            int n2 = this.getWindowSize();
            int n3 = 0;
            if (n >= n2) {
                n = this.mLoopViews ? 0 : this.getWindowSize() - 1;
                this.mWhichChild = n;
            } else if (n < 0) {
                n = this.mLoopViews ? this.getWindowSize() - 1 : 0;
                this.mWhichChild = n;
            }
            n = n3;
            if (this.getFocusedChild() != null) {
                n = 1;
            }
            this.showOnly(this.mWhichChild, bl);
            if (n != 0) {
                this.requestFocus(2);
            }
        }
    }

    @Override
    public void advance() {
        this.showNext();
    }

    void applyTransformForChildAtIndex(View view, int n) {
    }

    void cancelHandleClick() {
        View view = this.getCurrentView();
        if (view != null) {
            this.hideTapFeedback(view);
        }
        this.mTouchMode = 0;
    }

    void checkForAndHandleDataChanged() {
        if (this.mDataChanged) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    AdapterViewAnimator.this.handleDataChanged();
                    if (AdapterViewAnimator.this.mWhichChild >= AdapterViewAnimator.this.getWindowSize()) {
                        AdapterViewAnimator adapterViewAnimator = AdapterViewAnimator.this;
                        adapterViewAnimator.mWhichChild = 0;
                        adapterViewAnimator.showOnly(adapterViewAnimator.mWhichChild, false);
                    } else if (AdapterViewAnimator.this.mOldItemCount != AdapterViewAnimator.this.getCount()) {
                        AdapterViewAnimator adapterViewAnimator = AdapterViewAnimator.this;
                        adapterViewAnimator.showOnly(adapterViewAnimator.mWhichChild, false);
                    }
                    AdapterViewAnimator.this.refreshChildren();
                    AdapterViewAnimator.this.requestLayout();
                }
            });
        }
        this.mDataChanged = false;
    }

    void configureViewAnimator(int n, int n2) {
        this.mMaxNumActiveViews = n;
        this.mActiveOffset = n2;
        this.mPreviousViews.clear();
        this.mViewsMap.clear();
        this.removeAllViewsInLayout();
        this.mCurrentWindowStart = 0;
        this.mCurrentWindowEnd = -1;
    }

    ViewGroup.LayoutParams createOrReuseLayoutParams(View object) {
        if ((object = ((View)object).getLayoutParams()) != null) {
            return object;
        }
        return new ViewGroup.LayoutParams(0, 0);
    }

    @Override
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override
    public void fyiWillBeAdvancedByHostKThx() {
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AdapterViewAnimator.class.getName();
    }

    @Override
    public Adapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    public int getBaseline() {
        int n = this.getCurrentView() != null ? this.getCurrentView().getBaseline() : super.getBaseline();
        return n;
    }

    public View getCurrentView() {
        return this.getViewAtRelativeIndex(this.mActiveOffset);
    }

    ObjectAnimator getDefaultInAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1.0f);
        objectAnimator.setDuration(200L);
        return objectAnimator;
    }

    ObjectAnimator getDefaultOutAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.0f);
        objectAnimator.setDuration(200L);
        return objectAnimator;
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    FrameLayout getFrameForChild() {
        return new FrameLayout(this.mContext);
    }

    public ObjectAnimator getInAnimation() {
        return this.mInAnimation;
    }

    int getNumActiveViews() {
        if (this.mAdapter != null) {
            return Math.min(this.getCount() + 1, this.mMaxNumActiveViews);
        }
        return this.mMaxNumActiveViews;
    }

    public ObjectAnimator getOutAnimation() {
        return this.mOutAnimation;
    }

    @Override
    public View getSelectedView() {
        return this.getViewAtRelativeIndex(this.mActiveOffset);
    }

    View getViewAtRelativeIndex(int n) {
        if (n >= 0 && n <= this.getNumActiveViews() - 1 && this.mAdapter != null && this.mViewsMap.get(n = this.modulo(this.mCurrentWindowStartUnbounded + n, this.getWindowSize())) != null) {
            return this.mViewsMap.get((Object)Integer.valueOf((int)n)).view;
        }
        return null;
    }

    int getWindowSize() {
        if (this.mAdapter != null) {
            int n = this.getCount();
            if (n <= this.getNumActiveViews() && this.mLoopViews) {
                return this.mMaxNumActiveViews * n;
            }
            return n;
        }
        return 0;
    }

    void hideTapFeedback(View view) {
        view.setPressed(false);
    }

    int modulo(int n, int n2) {
        if (n2 > 0) {
            return (n % n2 + n2) % n2;
        }
        return 0;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.checkForAndHandleDataChanged();
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            n3 = this.mPaddingLeft;
            int n5 = view.getMeasuredWidth();
            n4 = this.mPaddingTop;
            int n6 = view.getMeasuredHeight();
            view.layout(this.mPaddingLeft, this.mPaddingTop, n3 + n5, n4 + n6);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.getSize(n2);
        int n5 = View.MeasureSpec.getMode(n);
        int n6 = View.MeasureSpec.getMode(n2);
        n = this.mReferenceChildWidth;
        n2 = 0;
        int n7 = n != -1 && this.mReferenceChildHeight != -1 ? 1 : 0;
        if (n6 == 0) {
            n = n7 != 0 ? this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom : 0;
        } else {
            n = n4;
            if (n6 == Integer.MIN_VALUE) {
                n = n4;
                if (n7 != 0 && (n = this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom) > n4) {
                    n = n4 | 16777216;
                }
            }
        }
        if (n5 == 0) {
            if (n7 != 0) {
                n7 = this.mReferenceChildWidth;
                n2 = this.mPaddingLeft;
                n2 = this.mPaddingRight + (n7 + n2);
            }
        } else {
            n2 = n3;
            if (n6 == Integer.MIN_VALUE) {
                n2 = n3;
                if (n7 != 0 && (n2 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight) > n3) {
                    n2 = n3 | 16777216;
                }
            }
        }
        this.setMeasuredDimension(n2, n);
        this.measureChildren();
    }

    @Override
    public boolean onRemoteAdapterConnected() {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != this.mAdapter) {
            int n;
            this.setAdapter(remoteViewsAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteViewsAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
            }
            if ((n = this.mRestoreWhichChild) > -1) {
                this.setDisplayedChild(n, false);
                this.mRestoreWhichChild = -1;
            }
            return false;
        }
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.superNotifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public void onRemoteAdapterDisconnected() {
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.mWhichChild = ((SavedState)parcelable).whichChild;
        if (this.mRemoteViewsAdapter != null && this.mAdapter == null) {
            this.mRestoreWhichChild = this.mWhichChild;
        } else {
            this.setDisplayedChild(this.mWhichChild, false);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.saveRemoteViewsCache();
        }
        return new SavedState(parcelable, this.mWhichChild);
    }

    @Override
    public boolean onTouchEvent(MotionEvent object) {
        boolean bl;
        int n = ((MotionEvent)object).getAction();
        boolean bl2 = false;
        boolean bl3 = false;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        bl = n != 6 ? bl2 : bl2;
                    } else {
                        object = this.getCurrentView();
                        if (object != null) {
                            this.hideTapFeedback((View)object);
                        }
                        this.mTouchMode = 0;
                        bl = bl2;
                    }
                } else {
                    bl = bl2;
                }
            } else {
                bl = bl3;
                if (this.mTouchMode == 1) {
                    final View view = this.getCurrentView();
                    final ViewAndMetaData viewAndMetaData = this.getMetaDataForChild(view);
                    bl = bl3;
                    if (view != null) {
                        bl = bl3;
                        if (this.isTransformedTouchPointInView(((MotionEvent)object).getX(), ((MotionEvent)object).getY(), view, null)) {
                            object = this.getHandler();
                            if (object != null) {
                                ((Handler)object).removeCallbacks(this.mPendingCheckForTap);
                            }
                            this.showTapFeedback(view);
                            this.postDelayed(new Runnable(){

                                @Override
                                public void run() {
                                    AdapterViewAnimator.this.hideTapFeedback(view);
                                    AdapterViewAnimator.this.post(new Runnable(){

                                        @Override
                                        public void run() {
                                            if (viewAndMetaData != null) {
                                                AdapterViewAnimator.this.performItemClick(view, viewAndMetaData.adapterPosition, viewAndMetaData.itemId);
                                            } else {
                                                AdapterViewAnimator.this.performItemClick(view, 0, 0L);
                                            }
                                        }
                                    });
                                }

                            }, ViewConfiguration.getPressedStateDuration());
                            bl = true;
                        }
                    }
                }
                this.mTouchMode = 0;
            }
        } else {
            View view = this.getCurrentView();
            bl = bl2;
            if (view != null) {
                bl = bl2;
                if (this.isTransformedTouchPointInView(((MotionEvent)object).getX(), ((MotionEvent)object).getY(), view, null)) {
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new CheckForTap();
                    }
                    this.mTouchMode = 1;
                    this.postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    bl = bl2;
                }
            }
        }
        return bl;
    }

    void refreshChildren() {
        if (this.mAdapter == null) {
            return;
        }
        for (int i = this.mCurrentWindowStart; i <= this.mCurrentWindowEnd; ++i) {
            int n = this.modulo(i, this.getWindowSize());
            int n2 = this.getCount();
            View view = this.mAdapter.getView(this.modulo(i, n2), null, this);
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
            if (!this.mViewsMap.containsKey(n)) continue;
            FrameLayout frameLayout = (FrameLayout)this.mViewsMap.get((Object)Integer.valueOf((int)n)).view;
            frameLayout.removeAllViewsInLayout();
            frameLayout.addView(view);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        AdapterView<Adapter> adapterView;
        Adapter adapter2 = this.mAdapter;
        if (adapter2 != null && (adapterView = this.mDataSetObserver) != null) {
            adapter2.unregisterDataSetObserver((DataSetObserver)((Object)adapterView));
        }
        this.mAdapter = adapter;
        this.checkFocus();
        if (this.mAdapter != null) {
            this.mDataSetObserver = new AdapterView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
            this.mItemCount = this.mAdapter.getCount();
        }
        this.setFocusable(true);
        this.mWhichChild = 0;
        this.showOnly(this.mWhichChild, false);
    }

    public void setAnimateFirstView(boolean bl) {
        this.mAnimateFirstTime = bl;
    }

    @RemotableViewMethod
    public void setDisplayedChild(int n) {
        this.setDisplayedChild(n, true);
    }

    public void setInAnimation(ObjectAnimator objectAnimator) {
        this.mInAnimation = objectAnimator;
    }

    public void setInAnimation(Context context, int n) {
        this.setInAnimation((ObjectAnimator)AnimatorInflater.loadAnimator(context, n));
    }

    public void setOutAnimation(ObjectAnimator objectAnimator) {
        this.mOutAnimation = objectAnimator;
    }

    public void setOutAnimation(Context context, int n) {
        this.setOutAnimation((ObjectAnimator)AnimatorInflater.loadAnimator(context, n));
    }

    @RemotableViewMethod(asyncImpl="setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(Intent intent) {
        this.setRemoteViewsAdapter(intent, false);
    }

    @Override
    public void setRemoteViewsAdapter(Intent intent, boolean bl) {
        if (this.mRemoteViewsAdapter != null && new Intent.FilterComparison(intent).equals(new Intent.FilterComparison(this.mRemoteViewsAdapter.getRemoteViewsServiceIntent()))) {
            return;
        }
        this.mDeferNotifyDataSetChanged = false;
        this.mRemoteViewsAdapter = new RemoteViewsAdapter(this.getContext(), intent, this, bl);
        if (this.mRemoteViewsAdapter.isDataReady()) {
            this.setAdapter(this.mRemoteViewsAdapter);
        }
    }

    public Runnable setRemoteViewsAdapterAsync(Intent intent) {
        return new RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    public void setRemoteViewsOnClickHandler(RemoteViews.OnClickHandler onClickHandler) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteViewsAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setRemoteViewsOnClickHandler(onClickHandler);
        }
    }

    @Override
    public void setSelection(int n) {
        this.setDisplayedChild(n);
    }

    public void showNext() {
        this.setDisplayedChild(this.mWhichChild + 1);
    }

    void showOnly(int n, boolean bl) {
        int n2;
        int n3;
        View view;
        int n4;
        int n5;
        Object object;
        int n6;
        if (this.mAdapter == null) {
            return;
        }
        int n7 = this.getCount();
        if (n7 == 0) {
            return;
        }
        for (n6 = 0; n6 < this.mPreviousViews.size(); ++n6) {
            object = this.mViewsMap.get((Object)this.mPreviousViews.get((int)n6)).view;
            this.mViewsMap.remove(this.mPreviousViews.get(n6));
            ((View)object).clearAnimation();
            if (object instanceof ViewGroup) {
                ((ViewGroup)object).removeAllViewsInLayout();
            }
            this.applyTransformForChildAtIndex((View)object, -1);
            this.removeViewInLayout((View)object);
        }
        this.mPreviousViews.clear();
        int n8 = n - this.mActiveOffset;
        int n9 = this.getNumActiveViews() + n8 - 1;
        n = Math.max(0, n8);
        n6 = Math.min(n7 - 1, n9);
        if (this.mLoopViews) {
            n = n8;
            n6 = n9;
        }
        int n10 = (n3 = this.modulo(n, this.getWindowSize())) > (n5 = this.modulo(n6, this.getWindowSize())) ? 1 : 0;
        for (Integer n11 : this.mViewsMap.keySet()) {
            n4 = 0;
            if (n10 == 0 && (n11 < n3 || n11 > n5)) {
                n2 = 1;
            } else {
                n2 = n4;
                if (n10 != 0) {
                    n2 = n4;
                    if (n11 > n5) {
                        n2 = n4;
                        if (n11 < n3) {
                            n2 = 1;
                        }
                    }
                }
            }
            if (n2 == 0) continue;
            view = this.mViewsMap.get((Object)n11).view;
            n2 = this.mViewsMap.get((Object)n11).relativeIndex;
            this.mPreviousViews.add(n11);
            this.transformViewForTransition(n2, -1, view, bl);
        }
        if (n != this.mCurrentWindowStart || n6 != this.mCurrentWindowEnd || n8 != this.mCurrentWindowStartUnbounded) {
            n10 = n7;
            n2 = n3;
            n7 = n6;
            n6 = n5;
            for (n4 = n; n4 <= n7; ++n4) {
                int n12 = this.modulo(n4, this.getWindowSize());
                n5 = this.mViewsMap.containsKey(n12) ? this.mViewsMap.get((Object)Integer.valueOf((int)n12)).relativeIndex : -1;
                int n13 = n4 - n8;
                n3 = this.mViewsMap.containsKey(n12) && !this.mPreviousViews.contains(n12) ? 1 : 0;
                if (n3 != 0) {
                    object = this.mViewsMap.get((Object)Integer.valueOf((int)n12)).view;
                    this.mViewsMap.get((Object)Integer.valueOf((int)n12)).relativeIndex = n13;
                    this.applyTransformForChildAtIndex((View)object, n13);
                    this.transformViewForTransition(n5, n13, (View)object, bl);
                } else {
                    n5 = this.modulo(n4, n10);
                    object = this.mAdapter.getView(n5, null, this);
                    long l = this.mAdapter.getItemId(n5);
                    view = this.getFrameForChild();
                    if (object != null) {
                        ((ViewGroup)view).addView((View)object);
                    }
                    this.mViewsMap.put(n12, new ViewAndMetaData(view, n13, n5, l));
                    this.addChild(view);
                    this.applyTransformForChildAtIndex(view, n13);
                    this.transformViewForTransition(-1, n13, view, bl);
                }
                this.mViewsMap.get((Object)Integer.valueOf((int)n12)).view.bringToFront();
            }
            this.mCurrentWindowStart = n;
            this.mCurrentWindowEnd = n7;
            this.mCurrentWindowStartUnbounded = n8;
            if (this.mRemoteViewsAdapter != null) {
                n = this.modulo(this.mCurrentWindowStart, n10);
                n6 = this.modulo(this.mCurrentWindowEnd, n10);
                this.mRemoteViewsAdapter.setVisibleRangeHint(n, n6);
            }
        }
        this.requestLayout();
        this.invalidate();
    }

    public void showPrevious() {
        this.setDisplayedChild(this.mWhichChild - 1);
    }

    void showTapFeedback(View view) {
        view.setPressed(true);
    }

    void transformViewForTransition(int n, int n2, View view, boolean bl) {
        if (n == -1) {
            this.mInAnimation.setTarget(view);
            this.mInAnimation.start();
        } else if (n2 == -1) {
            this.mOutAnimation.setTarget(view);
            this.mOutAnimation.start();
        }
    }

    final class CheckForTap
    implements Runnable {
        CheckForTap() {
        }

        @Override
        public void run() {
            if (AdapterViewAnimator.this.mTouchMode == 1) {
                View view = AdapterViewAnimator.this.getCurrentView();
                AdapterViewAnimator.this.showTapFeedback(view);
            }
        }
    }

    static class SavedState
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
        int whichChild;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.whichChild = parcel.readInt();
        }

        SavedState(Parcelable parcelable, int n) {
            super(parcelable);
            this.whichChild = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AdapterViewAnimator.SavedState{ whichChild = ");
            stringBuilder.append(this.whichChild);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.whichChild);
        }

    }

    class ViewAndMetaData {
        int adapterPosition;
        long itemId;
        int relativeIndex;
        View view;

        ViewAndMetaData(View view, int n, int n2, long l) {
            this.view = view;
            this.relativeIndex = n;
            this.adapterPosition = n2;
            this.itemId = l;
        }
    }

}

