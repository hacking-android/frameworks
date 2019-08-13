/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.AdapterHelper;
import com.android.internal.widget.ChildHelper;
import com.android.internal.widget.DefaultItemAnimator;
import com.android.internal.widget.GapWorker;
import com.android.internal.widget.NestedScrollingChild;
import com.android.internal.widget.RecyclerViewAccessibilityDelegate;
import com.android.internal.widget.ScrollingView;
import com.android.internal.widget.ViewInfoStore;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
extends ViewGroup
implements ScrollingView,
NestedScrollingChild {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    private static final boolean ALLOW_THREAD_GAP_WORK;
    private static final int[] CLIP_TO_PADDING_ATTR;
    static final boolean DEBUG = false;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS;
    public static final long NO_ID = -1L;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator;
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private OnItemTouchListener mActiveOnItemTouchListener;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout = false;
    private int mDispatchScrollCounter = 0;
    private int mEatRequestLayout = 0;
    private int mEatenAccessibilityChangeFlags;
    @VisibleForTesting
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    boolean mIsAttached;
    ItemAnimator mItemAnimator = new DefaultItemAnimator();
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    @VisibleForTesting
    LayoutManager mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter = 0;
    boolean mLayoutRequestEaten;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
    @VisibleForTesting
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout = true;
    final Recycler mRecycler = new Recycler();
    RecyclerListener mRecyclerListener;
    private EdgeEffect mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor = Float.MIN_VALUE;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId = -1;
    private int mScrollState = 0;
    final State mState;
    final Rect mTempRect = new Rect();
    private final Rect mTempRect2 = new Rect();
    final RectF mTempRectF = new RectF();
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable = new Runnable(){

        @Override
        public void run() {
            if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
                if (!RecyclerView.this.mIsAttached) {
                    RecyclerView.this.requestLayout();
                    return;
                }
                if (RecyclerView.this.mLayoutFrozen) {
                    RecyclerView.this.mLayoutRequestEaten = true;
                    return;
                }
                RecyclerView.this.consumePendingUpdateOperations();
                return;
            }
        }
    };
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger = new ViewFlinger();
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore = new ViewInfoStore();

    static {
        NESTED_SCROLLING_ATTRS = new int[]{16843830};
        CLIP_TO_PADDING_ATTR = new int[]{16842987};
        boolean bl = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = bl;
        bl = Build.VERSION.SDK_INT >= 23;
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bl;
        bl = Build.VERSION.SDK_INT >= 16;
        POST_UPDATES_ON_ANIMATION = bl;
        bl = Build.VERSION.SDK_INT >= 21;
        ALLOW_THREAD_GAP_WORK = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        FORCE_ABS_FOCUS_SEARCH_DIRECTION = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        IGNORE_DETACHED_FOCUSED_CHILD = bl;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
        sQuinticInterpolator = new Interpolator(){

            @Override
            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context object, AttributeSet attributeSet, int n) {
        super((Context)object, attributeSet, n);
        Object object2 = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mPrefetchRegistry = object2;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList<ViewHolder>();
        this.mItemAnimatorRunner = new Runnable(){

            @Override
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback(){

            @Override
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processDisappeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processPersistent(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        RecyclerView.this.postAnimationRunner();
                    }
                } else if (RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    RecyclerView.this.postAnimationRunner();
                }
            }

            @Override
            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        if (attributeSet != null) {
            object2 = ((Context)object).obtainStyledAttributes(attributeSet, CLIP_TO_PADDING_ATTR, n, 0);
            this.mClipToPadding = ((TypedArray)object2).getBoolean(0, true);
            ((TypedArray)object2).recycle();
        } else {
            this.mClipToPadding = true;
        }
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        object2 = ViewConfiguration.get((Context)object);
        this.mTouchSlop = ((ViewConfiguration)object2).getScaledTouchSlop();
        this.mMinFlingVelocity = ((ViewConfiguration)object2).getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = ((ViewConfiguration)object2).getScaledMaximumFlingVelocity();
        boolean bl = this.getOverScrollMode() == 2;
        this.setWillNotDraw(bl);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.initAdapterManager();
        this.initChildrenHelper();
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
        this.mAccessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        boolean bl2 = true;
        bl = true;
        if (attributeSet != null) {
            TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, 0);
            object2 = typedArray.getString(2);
            if (typedArray.getInt(1, -1) == -1) {
                this.setDescendantFocusability(262144);
            }
            typedArray.recycle();
            this.createLayoutManager((Context)object, (String)object2, attributeSet, n, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                object = ((Context)object).obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, n, 0);
                bl = ((TypedArray)object).getBoolean(0, true);
                ((TypedArray)object).recycle();
            }
        } else {
            this.setDescendantFocusability(262144);
            bl = bl2;
        }
        this.setNestedScrollingEnabled(bl);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean bl = view.getParent() == this;
        this.mRecycler.unscrapView(this.getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
        } else if (!bl) {
            this.mChildHelper.addView(view, true);
        } else {
            this.mChildHelper.hide(view);
        }
    }

    private void animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean bl, boolean bl2) {
        viewHolder.setIsRecyclable(false);
        if (bl) {
            this.addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (bl2) {
                this.addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            this.addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    private void cancelTouch() {
        this.resetTouch();
        this.setScrollState(0);
    }

    static void clearNestedRecyclerViewIfNotNested(ViewHolder viewHolder) {
        if (viewHolder.mNestedRecyclerView != null) {
            Object object = (View)viewHolder.mNestedRecyclerView.get();
            while (object != null) {
                if (object == viewHolder.itemView) {
                    return;
                }
                if ((object = ((View)object).getParent()) instanceof View) {
                    object = (View)object;
                    continue;
                }
                object = null;
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private void createLayoutManager(Context arrobject, String object, AttributeSet attributeSet, int n, int n2) {
        block11 : {
            if (object == null || (object = object.trim()).length() == 0) break block11;
            string2 = this.getFullClassName((Context)arrobject, (String)object);
            object = this.isInEditMode() != false ? this.getClass().getClassLoader() : arrobject.getClassLoader();
            class_ = object.loadClass(string2).asSubclass(LayoutManager.class);
            stringBuilder = null;
            object = class_.getConstructor(RecyclerView.LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
            arrobject = new Object[]{arrobject, attributeSet, n, n2};
            ** GOTO lbl28
            {
                catch (NoSuchMethodException noSuchMethodException) {
                    try {
                        try {
                            object = class_.getConstructor(new Class[0]);
                            arrobject = stringBuilder;
                        }
                        catch (NoSuchMethodException noSuchMethodException) {
                            noSuchMethodException.initCause(noSuchMethodException);
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(attributeSet.getPositionDescription());
                            stringBuilder.append(": Error creating LayoutManager ");
                            stringBuilder.append(string2);
                            illegalStateException = new IllegalStateException(stringBuilder.toString(), noSuchMethodException);
                            throw illegalStateException;
                        }
lbl28: // 2 sources:
                        object.setAccessible(true);
                        this.setLayoutManager(object.newInstance((Object[])arrobject));
                    }
                    catch (ClassCastException classCastException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Class is not a LayoutManager ");
                        object.append(string2);
                        throw new IllegalStateException(object.toString(), classCastException);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Cannot access non-public constructor ");
                        object.append(string2);
                        throw new IllegalStateException(object.toString(), illegalAccessException);
                    }
                    catch (InstantiationException instantiationException) {
                        arrobject = new StringBuilder();
                        arrobject.append(attributeSet.getPositionDescription());
                        arrobject.append(": Could not instantiate the LayoutManager: ");
                        arrobject.append(string2);
                        throw new IllegalStateException(arrobject.toString(), instantiationException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        arrobject = new StringBuilder();
                        arrobject.append(attributeSet.getPositionDescription());
                        arrobject.append(": Could not instantiate the LayoutManager: ");
                        arrobject.append(string2);
                        throw new IllegalStateException(arrobject.toString(), invocationTargetException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        object = new StringBuilder();
                        object.append(attributeSet.getPositionDescription());
                        object.append(": Unable to find LayoutManager ");
                        object.append(string2);
                        throw new IllegalStateException(object.toString(), classNotFoundException);
                    }
                }
            }
        }
    }

    private boolean didChildRangeChange(int n, int n2) {
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] arrn = this.mMinMaxLayoutPositions;
        boolean bl = false;
        if (arrn[0] != n || arrn[1] != n2) {
            bl = true;
        }
        return bl;
    }

    private void dispatchContentChangedIfNecessary() {
        int n = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (n != 0 && this.isAccessibilityEnabled()) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
            accessibilityEvent.setEventType(2048);
            accessibilityEvent.setContentChangeTypes(n);
            this.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    private void dispatchLayoutStep1() {
        ItemAnimator.ItemHolderInfo itemHolderInfo;
        int n;
        int n2;
        Object object = this.mState;
        boolean bl = true;
        ((State)object).assertLayoutStep(1);
        this.mState.mIsMeasuring = false;
        this.eatRequestLayout();
        this.mViewInfoStore.clear();
        this.onEnterLayoutOrScroll();
        this.processAdapterUpdatesAndSetAnimationFlags();
        this.saveFocusInfo();
        object = this.mState;
        if (!((State)object).mRunSimpleAnimations || !this.mItemsChanged) {
            bl = false;
        }
        ((State)object).mTrackOldChangeHolders = bl;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        object = this.mState;
        ((State)object).mInPreLayout = ((State)object).mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            n = this.mChildHelper.getChildCount();
            for (n2 = 0; n2 < n; ++n2) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
                if (((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid() && !this.mAdapter.hasStableIds()) continue;
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object), ((ViewHolder)object).getUnmodifiedPayloads());
                this.mViewInfoStore.addToPreLayout((ViewHolder)object, itemHolderInfo);
                if (!this.mState.mTrackOldChangeHolders || !((ViewHolder)object).isUpdated() || ((ViewHolder)object).isRemoved() || ((ViewHolder)object).shouldIgnore() || ((ViewHolder)object).isInvalid()) continue;
                long l = this.getChangedHolderKey((ViewHolder)object);
                this.mViewInfoStore.addToOldChangeHolders(l, (ViewHolder)object);
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            this.saveOldPositions();
            bl = this.mState.mStructureChanged;
            object = this.mState;
            ((State)object).mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, (State)object);
            this.mState.mStructureChanged = bl;
            for (n2 = 0; n2 < this.mChildHelper.getChildCount(); ++n2) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
                if (((ViewHolder)object).shouldIgnore() || this.mViewInfoStore.isInPreLayout((ViewHolder)object)) continue;
                int n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                bl = ((ViewHolder)object).hasAnyOfTheFlags(8192);
                n = n3;
                if (!bl) {
                    n = n3 | 4096;
                }
                itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, n, ((ViewHolder)object).getUnmodifiedPayloads());
                if (bl) {
                    this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, itemHolderInfo);
                    continue;
                }
                this.mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)object, itemHolderInfo);
            }
            this.clearOldPositions();
        } else {
            this.clearOldPositions();
        }
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        State state = this.mState;
        state.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        state.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, state);
        state = this.mState;
        state.mStructureChanged = false;
        this.mPendingSavedState = null;
        boolean bl = state.mRunSimpleAnimations && this.mItemAnimator != null;
        state.mRunSimpleAnimations = bl;
        this.mState.mLayoutStep = 4;
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        this.eatRequestLayout();
        this.onEnterLayoutOrScroll();
        Object object = this.mState;
        object.mLayoutStep = 1;
        if (object.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (viewHolder.shouldIgnore()) continue;
                long l = this.getChangedHolderKey(viewHolder);
                ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
                object = this.mViewInfoStore.getFromOldChangeHolders(l);
                if (object != null && !object.shouldIgnore()) {
                    boolean bl = this.mViewInfoStore.isDisappearing((ViewHolder)object);
                    boolean bl2 = this.mViewInfoStore.isDisappearing(viewHolder);
                    if (bl && object == viewHolder) {
                        this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                        continue;
                    }
                    ItemAnimator.ItemHolderInfo itemHolderInfo2 = this.mViewInfoStore.popFromPreLayout((ViewHolder)object);
                    this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                    itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
                    if (itemHolderInfo2 == null) {
                        this.handleMissingPreInfoForChangeError(l, viewHolder, (ViewHolder)object);
                        continue;
                    }
                    this.animateChange((ViewHolder)object, viewHolder, itemHolderInfo2, itemHolderInfo, bl, bl2);
                    continue;
                }
                this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        object = this.mState;
        object.mPreviousLayoutItemCount = object.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        object = this.mState;
        object.mRunSimpleAnimations = false;
        object.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            object = this.mLayout;
            object.mPrefetchMaxCountObserved = 0;
            object.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        this.onExitLayoutOrScroll();
        this.resumeRequestLayout(false);
        this.mViewInfoStore.clear();
        object = this.mMinMaxLayoutPositions;
        if (this.didChildRangeChange(object[0], object[1])) {
            this.dispatchOnScrolled(0, 0);
        }
        this.recoverFocusFromState();
        this.resetFocusInfo();
    }

    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        OnItemTouchListener onItemTouchListener = this.mActiveOnItemTouchListener;
        if (onItemTouchListener != null) {
            if (n == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                onItemTouchListener.onTouchEvent(this, motionEvent);
                if (n == 3 || n == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
        }
        if (n != 0) {
            int n2 = this.mOnItemTouchListeners.size();
            for (n = 0; n < n2; ++n) {
                onItemTouchListener = this.mOnItemTouchListeners.get(n);
                if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) continue;
                this.mActiveOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n == 3 || n == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int n2 = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n2; ++i) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (!onItemTouchListener.onInterceptTouchEvent(this, motionEvent) || n == 3) continue;
            this.mActiveOnItemTouchListener = onItemTouchListener;
            return true;
        }
        return false;
    }

    private void findMinMaxChildLayoutPositions(int[] arrn) {
        int n = this.mChildHelper.getChildCount();
        if (n == 0) {
            arrn[0] = -1;
            arrn[1] = -1;
            return;
        }
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            int n4;
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (viewHolder.shouldIgnore()) {
                n4 = n3;
            } else {
                int n5 = viewHolder.getLayoutPosition();
                int n6 = n2;
                if (n5 < n2) {
                    n6 = n5;
                }
                n2 = n6;
                n4 = n3;
                if (n5 > n3) {
                    n4 = n5;
                    n2 = n6;
                }
            }
            n3 = n4;
        }
        arrn[0] = n2;
        arrn[1] = n3;
    }

    static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView)view;
        }
        ViewGroup viewGroup = (ViewGroup)view;
        int n = viewGroup.getChildCount();
        for (int i = 0; i < n; ++i) {
            view = RecyclerView.findNestedRecyclerView(viewGroup.getChildAt(i));
            if (view == null) continue;
            return view;
        }
        return null;
    }

    private View findNextViewToFocus() {
        ViewHolder viewHolder;
        int n = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int n2 = this.mState.getItemCount();
        for (int i = n; i < n2 && (viewHolder = this.findViewHolderForAdapterPosition(i)) != null; ++i) {
            if (!viewHolder.itemView.hasFocusable()) continue;
            return viewHolder.itemView;
        }
        for (n = Math.min((int)n2, (int)n) - 1; n >= 0; --n) {
            viewHolder = this.findViewHolderForAdapterPosition(n);
            if (viewHolder == null) {
                return null;
            }
            if (!viewHolder.itemView.hasFocusable()) continue;
            return viewHolder.itemView;
        }
        return null;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).mViewHolder;
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set(view.getLeft() - rect2.left - layoutParams.leftMargin, view.getTop() - rect2.top - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    private int getDeepestFocusedViewWithId(View view) {
        int n = view.getId();
        while (!view.isFocused() && view instanceof ViewGroup && view.hasFocus()) {
            if ((view = ((ViewGroup)view).getFocusedChild()).getId() == -1) continue;
            n = view.getId();
        }
        return n;
    }

    private String getFullClassName(Context object, String string2) {
        if (string2.charAt(0) == '.') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Context)object).getPackageName());
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        if (string2.contains(".")) {
            return string2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(RecyclerView.class.getPackage().getName());
        ((StringBuilder)object).append('.');
        ((StringBuilder)object).append(string2);
        return ((StringBuilder)object).toString();
    }

    private float getScrollFactor() {
        if (this.mScrollFactor == Float.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            if (this.getContext().getTheme().resolveAttribute(16842829, typedValue, true)) {
                this.mScrollFactor = typedValue.getDimension(this.getContext().getResources().getDisplayMetrics());
            } else {
                return 0.0f;
            }
        }
        return this.mScrollFactor;
    }

    private void handleMissingPreInfoForChangeError(long l, ViewHolder viewHolder, ViewHolder object) {
        Object object2;
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            object2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (object2 == viewHolder || this.getChangedHolderKey((ViewHolder)object2) != l) continue;
            object = this.mAdapter;
            if (object != null && ((Adapter)object).hasStableIds()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(" \n View Holder 2:");
                ((StringBuilder)object).append(viewHolder);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" \n View Holder 2:");
            ((StringBuilder)object).append(viewHolder);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(" cannot be found but it is necessary for ");
        ((StringBuilder)object2).append(viewHolder);
        Log.e(TAG, ((StringBuilder)object2).toString());
    }

    private boolean hasUpdatedView() {
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore() || !viewHolder.isUpdated()) continue;
            return true;
        }
        return false;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback(){

            @Override
            public void addView(View view, int n) {
                RecyclerView.this.addView(view, n);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override
            public void attachViewToParent(View object, int n, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                if (viewHolder != null) {
                    if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Called attach on a child which is not detached: ");
                        ((StringBuilder)object).append(viewHolder);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    viewHolder.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent((View)object, n, layoutParams);
            }

            @Override
            public void detachViewFromParent(int n) {
                Object object = this.getChildAt(n);
                if (object != null && (object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    if (((ViewHolder)object).isTmpDetached() && !((ViewHolder)object).shouldIgnore()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("called detach on an already detached child ");
                        stringBuilder.append(object);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    ((ViewHolder)object).addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(n);
            }

            @Override
            public View getChildAt(int n) {
                return RecyclerView.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override
            public void onEnteredHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    ((ViewHolder)object).onEnteredHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void onLeftHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    ((ViewHolder)object).onLeftHiddenState(RecyclerView.this);
                }
            }

            @Override
            public void removeAllViews() {
                int n = this.getChildCount();
                for (int i = 0; i < n; ++i) {
                    RecyclerView.this.dispatchChildDetached(this.getChildAt(i));
                }
                RecyclerView.this.removeAllViews();
            }

            @Override
            public void removeViewAt(int n) {
                View view = RecyclerView.this.getChildAt(n);
                if (view != null) {
                    RecyclerView.this.dispatchChildDetached(view);
                }
                RecyclerView.this.removeViewAt(n);
            }
        });
    }

    private boolean isPreferredNextFocus(View view, View view2, int n) {
        boolean bl = false;
        if (view2 != null && view2 != this) {
            if (view == null) {
                return true;
            }
            if (n != 2 && n != 1) {
                return this.isPreferredNextFocusAbsolute(view, view2, n);
            }
            int n2 = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
            if (n == 2) {
                bl = true;
            }
            if (this.isPreferredNextFocusAbsolute(view, view2, n2 = bl ^ n2 ? 66 : 17)) {
                return true;
            }
            if (n == 2) {
                return this.isPreferredNextFocusAbsolute(view, view2, 130);
            }
            return this.isPreferredNextFocusAbsolute(view, view2, 33);
        }
        return false;
    }

    private boolean isPreferredNextFocusAbsolute(View object, View view, int n) {
        this.mTempRect.set(0, 0, ((View)object).getWidth(), ((View)object).getHeight());
        this.mTempRect2.set(0, 0, view.getWidth(), view.getHeight());
        this.offsetDescendantRectToMyCoords((View)object, this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect2);
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        if (this.mTempRect.top >= this.mTempRect2.top && this.mTempRect.bottom > this.mTempRect2.top || this.mTempRect.bottom >= this.mTempRect2.bottom) {
                            bl4 = false;
                        }
                        return bl4;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("direction must be absolute. received:");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                bl4 = (this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right ? bl : false;
                return bl4;
            }
            bl4 = (this.mTempRect.bottom > this.mTempRect2.bottom || this.mTempRect.top >= this.mTempRect2.bottom) && this.mTempRect.top > this.mTempRect2.top ? bl2 : false;
            return bl4;
        }
        bl4 = (this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left ? bl3 : false;
        return bl4;
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mScrollPointerId) {
            int n2;
            n = n == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(n);
            this.mLastTouchX = n2 = (int)(motionEvent.getX(n) + 0.5f);
            this.mInitialTouchX = n2;
            this.mLastTouchY = n = (int)(motionEvent.getY(n) + 0.5f);
            this.mInitialTouchY = n;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        boolean bl = this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
        return bl;
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            this.mLayout.onItemsChanged(this);
        }
        if (this.predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean bl = this.mItemsAddedOrRemoved;
        boolean bl2 = false;
        boolean bl3 = bl || this.mItemsChanged;
        State state = this.mState;
        bl = !(!this.mFirstLayoutComplete || this.mItemAnimator == null || !this.mDataSetHasChangedAfterLayout && !bl3 && !this.mLayout.mRequestedSimpleAnimations || this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds());
        state.mRunSimpleAnimations = bl;
        state = this.mState;
        bl = state.mRunSimpleAnimations && bl3 && !this.mDataSetHasChangedAfterLayout && this.predictiveItemAnimationsEnabled() ? true : bl2;
        state.mRunPredictiveAnimations = bl;
    }

    private void pullGlows(float f, float f2, float f3, float f4) {
        boolean bl = false;
        if (f2 < 0.0f) {
            this.ensureLeftGlow();
            this.mLeftGlow.onPull(-f2 / (float)this.getWidth(), 1.0f - f3 / (float)this.getHeight());
            bl = true;
        } else if (f2 > 0.0f) {
            this.ensureRightGlow();
            this.mRightGlow.onPull(f2 / (float)this.getWidth(), f3 / (float)this.getHeight());
            bl = true;
        }
        if (f4 < 0.0f) {
            this.ensureTopGlow();
            this.mTopGlow.onPull(-f4 / (float)this.getHeight(), f / (float)this.getWidth());
            bl = true;
        } else if (f4 > 0.0f) {
            this.ensureBottomGlow();
            this.mBottomGlow.onPull(f4 / (float)this.getHeight(), 1.0f - f / (float)this.getWidth());
            bl = true;
        }
        if (bl || f2 != 0.0f || f4 != 0.0f) {
            this.postInvalidateOnAnimation();
        }
    }

    private void recoverFocusFromState() {
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && this.hasFocus() && this.getDescendantFocusability() != 393216 && (this.getDescendantFocusability() != 131072 || !this.isFocused())) {
            Object object;
            Object object2;
            if (!this.isFocused()) {
                object = this.getFocusedChild();
                if (IGNORE_DETACHED_FOCUSED_CHILD && (((View)object).getParent() == null || !((View)object).hasFocus())) {
                    if (this.mChildHelper.getChildCount() == 0) {
                        this.requestFocus();
                        return;
                    }
                } else if (!this.mChildHelper.isHidden((View)object)) {
                    return;
                }
            }
            object = object2 = null;
            if (this.mState.mFocusedItemId != -1L) {
                object = object2;
                if (this.mAdapter.hasStableIds()) {
                    object = this.findViewHolderForItemId(this.mState.mFocusedItemId);
                }
            }
            object2 = null;
            if (object != null && !this.mChildHelper.isHidden(((ViewHolder)object).itemView) && ((ViewHolder)object).itemView.hasFocusable()) {
                object = ((ViewHolder)object).itemView;
            } else {
                object = object2;
                if (this.mChildHelper.getChildCount() > 0) {
                    object = this.findNextViewToFocus();
                }
            }
            if (object != null) {
                object2 = object;
                if ((long)this.mState.mFocusedSubChildId != -1L) {
                    Object t = ((View)object).findViewById(this.mState.mFocusedSubChildId);
                    object2 = object;
                    if (t != null) {
                        object2 = object;
                        if (((View)t).isFocusable()) {
                            object2 = t;
                        }
                    }
                }
                ((View)object2).requestFocus();
            }
            return;
        }
    }

    private void releaseGlows() {
        boolean bl = false;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl = true;
        }
        if ((edgeEffect = this.mTopGlow) != null) {
            edgeEffect.onRelease();
            bl = true;
        }
        if ((edgeEffect = this.mRightGlow) != null) {
            edgeEffect.onRelease();
            bl = true;
        }
        if ((edgeEffect = this.mBottomGlow) != null) {
            edgeEffect.onRelease();
            bl = true;
        }
        if (bl) {
            this.postInvalidateOnAnimation();
        }
    }

    private void resetFocusInfo() {
        State state = this.mState;
        state.mFocusedItemId = -1L;
        state.mFocusedItemPosition = -1;
        state.mFocusedSubChildId = -1;
    }

    private void resetTouch() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        this.stopNestedScroll();
        this.releaseGlows();
    }

    private void saveFocusInfo() {
        Object object;
        Object object2 = object = null;
        if (this.mPreserveFocusAfterLayout) {
            object2 = object;
            if (this.hasFocus()) {
                object2 = object;
                if (this.mAdapter != null) {
                    object2 = this.getFocusedChild();
                }
            }
        }
        if ((object2 = object2 == null ? null : this.findContainingViewHolder((View)object2)) == null) {
            this.resetFocusInfo();
        } else {
            object = this.mState;
            long l = this.mAdapter.hasStableIds() ? ((ViewHolder)object2).getItemId() : -1L;
            ((State)object).mFocusedItemId = l;
            object = this.mState;
            int n = this.mDataSetHasChangedAfterLayout ? -1 : (((ViewHolder)object2).isRemoved() ? ((ViewHolder)object2).mOldPosition : ((ViewHolder)object2).getAdapterPosition());
            ((State)object).mFocusedItemPosition = n;
            this.mState.mFocusedSubChildId = this.getDeepestFocusedViewWithId(((ViewHolder)object2).itemView);
        }
    }

    private void setAdapterInternal(Adapter object, boolean bl, boolean bl2) {
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!bl || bl2) {
            this.removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        adapter = this.mAdapter;
        this.mAdapter = object;
        if (object != null) {
            ((Adapter)object).registerAdapterDataObserver(this.mObserver);
            ((Adapter)object).onAttachedToRecyclerView(this);
        }
        if ((object = this.mLayout) != null) {
            ((LayoutManager)object).onAdapterChanged(adapter, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter, this.mAdapter, bl);
        this.mState.mStructureChanged = true;
        this.markKnownViewsInvalid();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.stopSmoothScroller();
        }
    }

    void absorbGlows(int n, int n2) {
        if (n < 0) {
            this.ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-n);
        } else if (n > 0) {
            this.ensureRightGlow();
            this.mRightGlow.onAbsorb(n);
        }
        if (n2 < 0) {
            this.ensureTopGlow();
            this.mTopGlow.onAbsorb(-n2);
        } else if (n2 > 0) {
            this.ensureBottomGlow();
            this.mBottomGlow.onAbsorb(n2);
        }
        if (n != 0 || n2 != 0) {
            this.postInvalidateOnAnimation();
        }
    }

    @Override
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, arrayList, n, n2)) {
            super.addFocusables(arrayList, n, n2);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration, -1);
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int n) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(n, itemDecoration);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList<OnScrollListener>();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    void animateAppearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void animateDisappearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        this.addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            this.postAnimationRunner();
        }
    }

    void assertInLayoutOrScroll(String string2) {
        if (!this.isComputingLayout()) {
            if (string2 == null) {
                throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException(string2);
        }
    }

    void assertNotInLayoutOrScroll(String string2) {
        if (this.isComputingLayout()) {
            if (string2 == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
            }
            throw new IllegalStateException(string2);
        }
        if (this.mDispatchScrollCounter > 0) {
            Log.w(TAG, "Cannot call this method in a scroll callback. Scroll callbacks might be run during a measure & layout pass where you cannot change the RecyclerView data. Any method call that might change the structure of the RecyclerView or the adapter contents should be postponed to the next frame.", new IllegalStateException(""));
        }
    }

    boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        ItemAnimator itemAnimator = this.mItemAnimator;
        boolean bl = itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
        return bl;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean bl = layoutParams instanceof LayoutParams && this.mLayout.checkLayoutParams((LayoutParams)layoutParams);
        return bl;
    }

    void clearOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.clearOldPosition();
        }
        this.mRecycler.clearOldPositions();
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void clearOnScrollListeners() {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.clear();
        }
    }

    @Override
    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return n;
    }

    @Override
    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return n;
    }

    @Override
    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollHorizontally()) {
            n = this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return n;
    }

    @Override
    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (layoutManager.canScrollVertically()) {
            n = this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return n;
    }

    void considerReleasingGlowsOnScroll(int n, int n2) {
        int n3 = 0;
        EdgeEffect edgeEffect = this.mLeftGlow;
        int n4 = n3;
        if (edgeEffect != null) {
            n4 = n3;
            if (!edgeEffect.isFinished()) {
                n4 = n3;
                if (n > 0) {
                    this.mLeftGlow.onRelease();
                    n4 = 1;
                }
            }
        }
        edgeEffect = this.mRightGlow;
        n3 = n4;
        if (edgeEffect != null) {
            n3 = n4;
            if (!edgeEffect.isFinished()) {
                n3 = n4;
                if (n < 0) {
                    this.mRightGlow.onRelease();
                    n3 = 1;
                }
            }
        }
        edgeEffect = this.mTopGlow;
        n = n3;
        if (edgeEffect != null) {
            n = n3;
            if (!edgeEffect.isFinished()) {
                n = n3;
                if (n2 > 0) {
                    this.mTopGlow.onRelease();
                    n = 1;
                }
            }
        }
        edgeEffect = this.mBottomGlow;
        n3 = n;
        if (edgeEffect != null) {
            n3 = n;
            if (!edgeEffect.isFinished()) {
                n3 = n;
                if (n2 < 0) {
                    this.mBottomGlow.onRelease();
                    n3 = 1;
                }
            }
        }
        if (n3 != 0) {
            this.postInvalidateOnAnimation();
        }
    }

    void consumePendingUpdateOperations() {
        if (this.mFirstLayoutComplete && !this.mDataSetHasChangedAfterLayout) {
            if (!this.mAdapterHelper.hasPendingUpdates()) {
                return;
            }
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                Trace.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                this.eatRequestLayout();
                this.onEnterLayoutOrScroll();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutRequestEaten) {
                    if (this.hasUpdatedView()) {
                        this.dispatchLayout();
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                    }
                }
                this.resumeRequestLayout(true);
                this.onExitLayoutOrScroll();
                Trace.endSection();
            } else if (this.mAdapterHelper.hasPendingUpdates()) {
                Trace.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                this.dispatchLayout();
                Trace.endSection();
            }
            return;
        }
        Trace.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
        this.dispatchLayout();
        Trace.endSection();
    }

    void defaultOnMeasure(int n, int n2) {
        n = LayoutManager.chooseSize(n, this.getPaddingLeft() + this.getPaddingRight(), this.getMinimumWidth());
        this.setMeasuredDimension(n, LayoutManager.chooseSize(n2, this.getPaddingTop() + this.getPaddingBottom(), this.getMinimumHeight()));
    }

    void dispatchChildAttached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildAttachedToWindow(view);
        Object object = this.mAdapter;
        if (object != null && viewHolder != null) {
            ((Adapter)object).onViewAttachedToWindow(viewHolder);
        }
        if ((object = this.mOnChildAttachStateListeners) != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewAttachedToWindow(view);
            }
        }
    }

    void dispatchChildDetached(View view) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        this.onChildDetachedFromWindow(view);
        Object object = this.mAdapter;
        if (object != null && viewHolder != null) {
            ((Adapter)object).onViewDetachedFromWindow(viewHolder);
        }
        if ((object = this.mOnChildAttachStateListeners) != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                this.mOnChildAttachStateListeners.get(i).onChildViewDetachedFromWindow(view);
            }
        }
    }

    void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e(TAG, "No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e(TAG, "No layout manager attached; skipping layout");
            return;
        }
        State state = this.mState;
        state.mIsMeasuring = false;
        if (state.mLayoutStep == 1) {
            this.dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == this.getWidth() && this.mLayout.getHeight() == this.getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        }
        this.dispatchLayoutStep3();
    }

    void dispatchOnScrollStateChanged(int n) {
        Object object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).onScrollStateChanged(n);
        }
        this.onScrollStateChanged(n);
        object = this.mScrollListener;
        if (object != null) {
            ((OnScrollListener)object).onScrollStateChanged(this, n);
        }
        if ((object = this.mScrollListeners) != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                this.mScrollListeners.get(i).onScrollStateChanged(this, n);
            }
        }
    }

    void dispatchOnScrolled(int n, int n2) {
        ++this.mDispatchScrollCounter;
        int n3 = this.getScrollX();
        int n4 = this.getScrollY();
        this.onScrollChanged(n3, n4, n3, n4);
        this.onScrolled(n, n2);
        Object object = this.mScrollListener;
        if (object != null) {
            ((OnScrollListener)object).onScrolled(this, n, n2);
        }
        if ((object = this.mScrollListeners) != null) {
            for (n4 = object.size() - 1; n4 >= 0; --n4) {
                this.mScrollListeners.get(n4).onScrolled(this, n, n2);
            }
        }
        --this.mDispatchScrollCounter;
    }

    void dispatchPendingImportantForAccessibilityChanges() {
        for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; --i) {
            int n;
            ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(i);
            if (viewHolder.itemView.getParent() != this || viewHolder.shouldIgnore() || (n = viewHolder.mPendingAccessibilityState) == -1) continue;
            viewHolder.itemView.setImportantForAccessibility(n);
            viewHolder.mPendingAccessibilityState = -1;
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    @Override
    public void draw(Canvas canvas) {
        int n;
        int n2;
        super.draw(canvas);
        int n3 = this.mItemDecorations.size();
        for (n = 0; n < n3; ++n) {
            this.mItemDecorations.get(n).onDrawOver(canvas, this, this.mState);
        }
        n = 0;
        EdgeEffect edgeEffect = this.mLeftGlow;
        int n4 = 1;
        n3 = n;
        if (edgeEffect != null) {
            n3 = n;
            if (!edgeEffect.isFinished()) {
                n3 = canvas.save();
                n = this.mClipToPadding ? this.getPaddingBottom() : 0;
                canvas.rotate(270.0f);
                canvas.translate(-this.getHeight() + n, 0.0f);
                edgeEffect = this.mLeftGlow;
                n = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
                canvas.restoreToCount(n3);
                n3 = n;
            }
        }
        edgeEffect = this.mTopGlow;
        n = n3;
        if (edgeEffect != null) {
            n = n3;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                if (this.mClipToPadding) {
                    canvas.translate(this.getPaddingLeft(), this.getPaddingTop());
                }
                n = (edgeEffect = this.mTopGlow) != null && edgeEffect.draw(canvas) ? 1 : 0;
                n = n3 | n;
                canvas.restoreToCount(n2);
            }
        }
        edgeEffect = this.mRightGlow;
        n3 = n;
        if (edgeEffect != null) {
            n3 = n;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                int n5 = this.getWidth();
                n3 = this.mClipToPadding ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate(-n3, -n5);
                edgeEffect = this.mRightGlow;
                n3 = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
                n3 = n | n3;
                canvas.restoreToCount(n2);
            }
        }
        edgeEffect = this.mBottomGlow;
        n = n3;
        if (edgeEffect != null) {
            n = n3;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                canvas.rotate(180.0f);
                if (this.mClipToPadding) {
                    canvas.translate(-this.getWidth() + this.getPaddingRight(), -this.getHeight() + this.getPaddingBottom());
                } else {
                    canvas.translate(-this.getWidth(), -this.getHeight());
                }
                edgeEffect = this.mBottomGlow;
                n = edgeEffect != null && edgeEffect.draw(canvas) ? n4 : 0;
                n = n3 | n;
                canvas.restoreToCount(n2);
            }
        }
        n3 = n;
        if (n == 0) {
            n3 = n;
            if (this.mItemAnimator != null) {
                n3 = n;
                if (this.mItemDecorations.size() > 0) {
                    n3 = n;
                    if (this.mItemAnimator.isRunning()) {
                        n3 = 1;
                    }
                }
            }
        }
        if (n3 != 0) {
            this.postInvalidateOnAnimation();
        }
    }

    @Override
    public boolean drawChild(Canvas canvas, View view, long l) {
        return super.drawChild(canvas, view, l);
    }

    void eatRequestLayout() {
        ++this.mEatRequestLayout;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mBottomGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
        } else {
            this.mBottomGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
        }
    }

    void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mLeftGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
        } else {
            this.mLeftGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
        }
    }

    void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mRightGlow.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
        } else {
            this.mRightGlow.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
        }
    }

    void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = new EdgeEffect(this.getContext());
        if (this.mClipToPadding) {
            this.mTopGlow.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
        } else {
            this.mTopGlow.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
        }
    }

    public View findChildViewUnder(float f, float f2) {
        for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
            View view = this.mChildHelper.getChildAt(i);
            float f3 = view.getTranslationX();
            float f4 = view.getTranslationY();
            if (!(f >= (float)view.getLeft() + f3) || !(f <= (float)view.getRight() + f3) || !(f2 >= (float)view.getTop() + f4) || !(f2 <= (float)view.getBottom() + f4)) continue;
            return view;
        }
        return null;
    }

    public View findContainingItemView(View view) {
        ViewParent viewParent = view.getParent();
        while (viewParent != null && viewParent != this && viewParent instanceof View) {
            view = (View)((Object)viewParent);
            viewParent = view.getParent();
        }
        if (viewParent != this) {
            view = null;
        }
        return view;
    }

    public ViewHolder findContainingViewHolder(View object) {
        object = (object = this.findContainingItemView((View)object)) == null ? null : this.getChildViewHolder((View)object);
        return object;
    }

    public ViewHolder findViewHolderForAdapterPosition(int n) {
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        for (int i = 0; i < n2; ++i) {
            ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            ViewHolder viewHolder3 = viewHolder;
            if (viewHolder2 != null) {
                viewHolder3 = viewHolder;
                if (!viewHolder2.isRemoved()) {
                    viewHolder3 = viewHolder;
                    if (this.getAdapterPositionFor(viewHolder2) == n) {
                        if (this.mChildHelper.isHidden(viewHolder2.itemView)) {
                            viewHolder3 = viewHolder2;
                        } else {
                            return viewHolder2;
                        }
                    }
                }
            }
            viewHolder = viewHolder3;
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long l) {
        Object object = this.mAdapter;
        if (object != null && ((Adapter)object).hasStableIds()) {
            int n = this.mChildHelper.getUnfilteredChildCount();
            Adapter adapter = null;
            for (int i = 0; i < n; ++i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
                object = adapter;
                if (viewHolder != null) {
                    object = adapter;
                    if (!viewHolder.isRemoved()) {
                        object = adapter;
                        if (viewHolder.getItemId() == l) {
                            if (this.mChildHelper.isHidden(viewHolder.itemView)) {
                                object = viewHolder;
                            } else {
                                return viewHolder;
                            }
                        }
                    }
                }
                adapter = object;
            }
            return adapter;
        }
        return null;
    }

    public ViewHolder findViewHolderForLayoutPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    ViewHolder findViewHolderForPosition(int var1_1, boolean var2_2) {
        var3_3 = this.mChildHelper.getUnfilteredChildCount();
        var4_4 = null;
        var5_5 = 0;
        while (var5_5 < var3_3) {
            block3 : {
                block4 : {
                    var6_6 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(var5_5));
                    var7_7 = var4_4;
                    if (var6_6 == null) break block3;
                    var7_7 = var4_4;
                    if (var6_6.isRemoved()) break block3;
                    if (!var2_2) break block4;
                    if (var6_6.mPosition == var1_1) ** GOTO lbl-1000
                    var7_7 = var4_4;
                    break block3;
                }
                if (var6_6.getLayoutPosition() != var1_1) {
                    var7_7 = var4_4;
                } else lbl-1000: // 2 sources:
                {
                    if (this.mChildHelper.isHidden(var6_6.itemView) == false) return var6_6;
                    var7_7 = var6_6;
                }
            }
            ++var5_5;
            var4_4 = var7_7;
        }
        return var4_4;
    }

    public boolean fling(int n, int n2) {
        boolean bl;
        boolean bl2;
        int n3;
        Object object;
        block15 : {
            block14 : {
                block13 : {
                    block12 : {
                        object = this.mLayout;
                        if (object == null) {
                            Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            return false;
                        }
                        if (this.mLayoutFrozen) {
                            return false;
                        }
                        bl = ((LayoutManager)object).canScrollHorizontally();
                        bl2 = this.mLayout.canScrollVertically();
                        if (!bl) break block12;
                        n3 = n;
                        if (Math.abs(n) >= this.mMinFlingVelocity) break block13;
                    }
                    n3 = 0;
                }
                if (!bl2) break block14;
                n = n2;
                if (Math.abs(n2) >= this.mMinFlingVelocity) break block15;
            }
            n = 0;
        }
        if (n3 == 0 && n == 0) {
            return false;
        }
        if (!this.dispatchNestedPreFling(n3, n)) {
            bl2 = bl || bl2;
            this.dispatchNestedFling(n3, n, bl2);
            object = this.mOnFlingListener;
            if (object != null && ((OnFlingListener)object).onFling(n3, n)) {
                return true;
            }
            if (bl2) {
                n2 = this.mMaxFlingVelocity;
                n2 = Math.max(-n2, Math.min(n3, n2));
                n3 = this.mMaxFlingVelocity;
                n = Math.max(-n3, Math.min(n, n3));
                this.mViewFlinger.fling(n2, n);
                return true;
            }
        }
        return false;
    }

    @Override
    public View focusSearch(View view, int n) {
        int n2;
        Object object = this.mLayout.onInterceptFocusSearch(view, n);
        if (object != null) {
            return object;
        }
        object = this.mAdapter;
        int n3 = 1;
        int n4 = object != null && this.mLayout != null && !this.isComputingLayout() && !this.mLayoutFrozen ? 1 : 0;
        object = FocusFinder.getInstance();
        if (n4 != 0 && (n == 2 || n == 1)) {
            int n5;
            int n6 = 0;
            n4 = n;
            if (this.mLayout.canScrollVertically()) {
                n2 = n == 2 ? 130 : 33;
                n4 = ((FocusFinder)object).findNextFocus(this, view, n2) == null ? 1 : 0;
                n6 = n5 = n4;
                n4 = n;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    n4 = n2;
                    n6 = n5;
                }
            }
            n5 = n6;
            n2 = n4;
            if (n6 == 0) {
                n5 = n6;
                n2 = n4;
                if (this.mLayout.canScrollHorizontally()) {
                    n = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
                    n2 = n4 == 2 ? 1 : 0;
                    n = (n2 ^ n) != 0 ? 66 : 17;
                    n2 = ((FocusFinder)object).findNextFocus(this, view, n) == null ? n3 : 0;
                    n5 = n6 = n2;
                    n2 = n4;
                    if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                        n2 = n;
                        n5 = n6;
                    }
                }
            }
            if (n5 != 0) {
                this.consumePendingUpdateOperations();
                if (this.findContainingItemView(view) == null) {
                    return null;
                }
                this.eatRequestLayout();
                this.mLayout.onFocusSearchFailed(view, n2, this.mRecycler, this.mState);
                this.resumeRequestLayout(false);
            }
            object = ((FocusFinder)object).findNextFocus(this, view, n2);
        } else {
            View view2 = ((FocusFinder)object).findNextFocus(this, view, n);
            object = view2;
            n2 = n;
            if (view2 == null) {
                object = view2;
                n2 = n;
                if (n4 != 0) {
                    this.consumePendingUpdateOperations();
                    if (this.findContainingItemView(view) == null) {
                        return null;
                    }
                    this.eatRequestLayout();
                    object = this.mLayout.onFocusSearchFailed(view, n, this.mRecycler, this.mState);
                    this.resumeRequestLayout(false);
                    n2 = n;
                }
            }
        }
        if (!this.isPreferredNextFocus(view, (View)object, n2)) {
            object = super.focusSearch(view, n2);
        }
        return object;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(this.getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager");
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    int getAdapterPositionFor(ViewHolder viewHolder) {
        if (!viewHolder.hasAnyOfTheFlags(524) && viewHolder.isBound()) {
            return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
        }
        return -1;
    }

    @Override
    public int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.getBaseline();
        }
        return super.getBaseline();
    }

    long getChangedHolderKey(ViewHolder viewHolder) {
        long l = this.mAdapter.hasStableIds() ? viewHolder.getItemId() : (long)viewHolder.mPosition;
        return l;
    }

    public int getChildAdapterPosition(View object) {
        int n = (object = RecyclerView.getChildViewHolderInt((View)object)) != null ? ((ViewHolder)object).getAdapterPosition() : -1;
        return n;
    }

    @Override
    protected int getChildDrawingOrder(int n, int n2) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
        if (childDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(n, n2);
        }
        return childDrawingOrderCallback.onGetChildDrawingOrder(n, n2);
    }

    public long getChildItemId(View object) {
        Adapter adapter = this.mAdapter;
        long l = -1L;
        if (adapter != null && adapter.hasStableIds()) {
            if ((object = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                l = ((ViewHolder)object).getItemId();
            }
            return l;
        }
        return -1L;
    }

    public int getChildLayoutPosition(View object) {
        int n = (object = RecyclerView.getChildViewHolderInt((View)object)) != null ? ((ViewHolder)object).getLayoutPosition() : -1;
        return n;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return this.getChildAdapterPosition(view);
    }

    public ViewHolder getChildViewHolder(View view) {
        Object object = view.getParent();
        if (object != null && object != this) {
            object = new StringBuilder();
            ((StringBuilder)object).append("View ");
            ((StringBuilder)object).append(view);
            ((StringBuilder)object).append(" is not a direct child of ");
            ((StringBuilder)object).append(this);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return RecyclerView.getChildViewHolderInt(view);
    }

    @Override
    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0L;
    }

    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    public boolean hasPendingAdapterUpdates() {
        boolean bl = !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
        return bl;
    }

    void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback(){

            void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                int n = updateOp.cmd;
                if (n != 1) {
                    if (n != 2) {
                        if (n != 4) {
                            if (n == 8) {
                                RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
                            }
                        } else {
                            RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                        }
                    } else {
                        RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                    }
                } else {
                    RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                }
            }

            @Override
            public ViewHolder findViewHolder(int n) {
                ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n, true);
                if (viewHolder == null) {
                    return null;
                }
                if (RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) {
                    return null;
                }
                return viewHolder;
            }

            @Override
            public void markViewHoldersUpdated(int n, int n2, Object object) {
                RecyclerView.this.viewRangeUpdate(n, n2, object);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override
            public void offsetPositionsForAdd(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForInsert(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForMove(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForMove(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, true);
                Object object = RecyclerView.this;
                ((RecyclerView)object).mItemsAddedOrRemoved = true;
                object = ((RecyclerView)object).mState;
                ((State)object).mDeletedInvisibleItemCountSincePreviousLayout += n2;
            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }
        });
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    boolean isAccessibilityEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        boolean bl = accessibilityManager != null && accessibilityManager.isEnabled();
        return bl;
    }

    public boolean isAnimating() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        boolean bl = itemAnimator != null && itemAnimator.isRunning();
        return bl;
    }

    @Override
    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isComputingLayout() {
        boolean bl = this.mLayoutOrScrollCounter > 0;
        return bl;
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    void jumpToPositionForSmoothScroller(int n) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return;
        }
        layoutManager.scrollToPosition(n);
        this.awakenScrollBars();
    }

    void markItemDecorInsetsDirty() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ((LayoutParams)this.mChildHelper.getUnfilteredChildAt((int)i).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    void markKnownViewsInvalid() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(6);
        }
        this.markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void offsetChildrenHorizontal(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetLeftAndRight(n);
        }
    }

    public void offsetChildrenVertical(int n) {
        int n2 = this.mChildHelper.getChildCount();
        for (int i = 0; i < n2; ++i) {
            this.mChildHelper.getChildAt(i).offsetTopAndBottom(n);
        }
    }

    void offsetPositionRecordsForInsert(int n, int n2) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n) continue;
            viewHolder.offsetPosition(n2, false);
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForInsert(n, n2);
        this.requestLayout();
    }

    void offsetPositionRecordsForMove(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = this.mChildHelper.getUnfilteredChildCount();
        if (n < n2) {
            n5 = n;
            n3 = n2;
            n4 = -1;
        } else {
            n5 = n2;
            n3 = n;
            n4 = 1;
        }
        for (int i = 0; i < n6; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.mPosition < n5 || viewHolder.mPosition > n3) continue;
            if (viewHolder.mPosition == n) {
                viewHolder.offsetPosition(n2 - n, false);
            } else {
                viewHolder.offsetPosition(n4, false);
            }
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForMove(n, n2);
        this.requestLayout();
    }

    void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            if (viewHolder.mPosition >= n + n2) {
                viewHolder.offsetPosition(-n2, bl);
                this.mState.mStructureChanged = true;
                continue;
            }
            if (viewHolder.mPosition < n) continue;
            viewHolder.flagRemovedAndOffsetPosition(n - 1, -n2, bl);
            this.mState.mStructureChanged = true;
        }
        this.mRecycler.offsetPositionRecordsForRemove(n, n2, bl);
        this.requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean bl = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || this.isLayoutRequested()) {
            bl = false;
        }
        this.mFirstLayoutComplete = bl;
        Object object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = GapWorker.sGapWorker.get();
            if (this.mGapWorker == null) {
                float f;
                this.mGapWorker = new GapWorker();
                object = this.getDisplay();
                float f2 = f = 60.0f;
                if (!this.isInEditMode()) {
                    f2 = f;
                    if (object != null) {
                        float f3 = ((Display)object).getRefreshRate();
                        f2 = f;
                        if (f3 >= 30.0f) {
                            f2 = f3;
                        }
                    }
                }
                this.mGapWorker.mFrameIntervalNs = (long)(1.0E9f / f2);
                GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object object = this.mItemAnimator;
        if (object != null) {
            ((ItemAnimator)object).endAnimations();
        }
        this.stopScroll();
        this.mIsAttached = false;
        object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        this.removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.mItemDecorations.size();
        for (int i = 0; i < n; ++i) {
            this.mItemDecorations.get(i).onDraw(canvas, this, this.mState);
        }
    }

    void onEnterLayoutOrScroll() {
        ++this.mLayoutOrScrollCounter;
    }

    void onExitLayoutOrScroll() {
        --this.mLayoutOrScrollCounter;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            this.dispatchContentChangedIfNecessary();
            this.dispatchPendingImportantForAccessibilityChanges();
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mLayout == null) {
            return false;
        }
        if (this.mLayoutFrozen) {
            return false;
        }
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            float f = this.mLayout.canScrollVertically() ? -motionEvent.getAxisValue(9) : 0.0f;
            float f2 = this.mLayout.canScrollHorizontally() ? motionEvent.getAxisValue(10) : 0.0f;
            if (f != 0.0f || f2 != 0.0f) {
                float f3 = this.getScrollFactor();
                this.scrollByInternal((int)(f2 * f3), (int)(f * f3), motionEvent);
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arrn) {
        boolean bl = this.mLayoutFrozen;
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.dispatchOnItemTouchIntercept((MotionEvent)arrn)) {
            this.cancelTouch();
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        bl = layoutManager.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)arrn);
        int n = arrn.getActionMasked();
        int n2 = arrn.getActionIndex();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5) {
                            if (n == 6) {
                                this.onPointerUp((MotionEvent)arrn);
                            }
                        } else {
                            this.mScrollPointerId = arrn.getPointerId(n2);
                            this.mLastTouchX = n = (int)(arrn.getX(n2) + 0.5f);
                            this.mInitialTouchX = n;
                            this.mLastTouchY = n2 = (int)(arrn.getY(n2) + 0.5f);
                            this.mInitialTouchY = n2;
                        }
                    } else {
                        this.cancelTouch();
                    }
                } else {
                    n = arrn.findPointerIndex(this.mScrollPointerId);
                    if (n < 0) {
                        arrn = new StringBuilder();
                        arrn.append("Error processing scroll; pointer index for id ");
                        arrn.append(this.mScrollPointerId);
                        arrn.append(" not found. Did any MotionEvents get skipped?");
                        Log.e(TAG, arrn.toString());
                        return false;
                    }
                    n2 = (int)(arrn.getX(n) + 0.5f);
                    n = (int)(arrn.getY(n) + 0.5f);
                    if (this.mScrollState != 1) {
                        int n3;
                        int n4 = n2 - this.mInitialTouchX;
                        int n5 = n - this.mInitialTouchY;
                        n2 = n = 0;
                        if (bl) {
                            int n6 = Math.abs(n4);
                            n3 = this.mTouchSlop;
                            n2 = n;
                            if (n6 > n3) {
                                n = this.mInitialTouchX;
                                n2 = n4 < 0 ? -1 : 1;
                                this.mLastTouchX = n + n3 * n2;
                                n2 = 1;
                            }
                        }
                        n = n2;
                        if (bl3) {
                            n4 = Math.abs(n5);
                            n3 = this.mTouchSlop;
                            n = n2;
                            if (n4 > n3) {
                                n = this.mInitialTouchY;
                                n2 = n5 < 0 ? -1 : 1;
                                this.mLastTouchY = n + n3 * n2;
                                n = 1;
                            }
                        }
                        if (n != 0) {
                            this.setScrollState(1);
                        }
                    }
                }
            } else {
                this.mVelocityTracker.clear();
                this.stopNestedScroll();
            }
        } else {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = arrn.getPointerId(0);
            this.mLastTouchX = n2 = (int)(arrn.getX() + 0.5f);
            this.mInitialTouchX = n2;
            this.mLastTouchY = n2 = (int)(arrn.getY() + 0.5f);
            this.mInitialTouchY = n2;
            if (this.mScrollState == 2) {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                this.setScrollState(1);
            }
            arrn = this.mNestedOffsets;
            arrn[1] = 0;
            arrn[0] = 0;
            n2 = 0;
            if (bl) {
                n2 = false | true;
            }
            n = n2;
            if (bl3) {
                n = n2 | 2;
            }
            this.startNestedScroll(n);
        }
        if (this.mScrollState == 1) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Trace.beginSection(TRACE_ON_LAYOUT_TAG);
        this.dispatchLayout();
        Trace.endSection();
        this.mFirstLayoutComplete = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void onMeasure(int n, int n2) {
        Object object = this.mLayout;
        if (object == null) {
            this.defaultOnMeasure(n, n2);
            return;
        }
        boolean bl = ((LayoutManager)object).mAutoMeasure;
        boolean bl2 = false;
        if (bl) {
            int n3 = View.MeasureSpec.getMode(n);
            int n4 = View.MeasureSpec.getMode(n2);
            boolean bl3 = bl2;
            if (n3 == 1073741824) {
                bl3 = bl2;
                if (n4 == 1073741824) {
                    bl3 = true;
                }
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            if (bl3 || this.mAdapter == null) return;
            if (this.mState.mLayoutStep == 1) {
                this.dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(n, n2);
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            if (!this.mLayout.shouldMeasureTwice()) return;
            this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824));
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            return;
        } else {
            if (this.mHasFixedSize) {
                this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
                return;
            }
            if (this.mAdapterUpdateDuringMeasure) {
                this.eatRequestLayout();
                this.onEnterLayoutOrScroll();
                this.processAdapterUpdatesAndSetAnimationFlags();
                this.onExitLayoutOrScroll();
                if (this.mState.mRunPredictiveAnimations) {
                    this.mState.mInPreLayout = true;
                } else {
                    this.mAdapterHelper.consumeUpdatesInOnePass();
                    this.mState.mInPreLayout = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                this.resumeRequestLayout(false);
            }
            this.mState.mItemCount = (object = this.mAdapter) != null ? ((Adapter)object).getItemCount() : 0;
            this.eatRequestLayout();
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            this.resumeRequestLayout(false);
            this.mState.mInPreLayout = false;
        }
    }

    @Override
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        if (this.isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(n, rect);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.mPendingSavedState = (SavedState)parcelable;
        super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null) {
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.mPendingSavedState;
        if (object != null) {
            savedState.copyFrom((SavedState)object);
        } else {
            object = this.mLayout;
            savedState.mLayoutState = object != null ? ((LayoutManager)object).onSaveInstanceState() : null;
        }
        return savedState;
    }

    public void onScrollStateChanged(int n) {
    }

    public void onScrolled(int n, int n2) {
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3 || n2 != n4) {
            this.invalidateGlows();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arrn) {
        boolean bl = this.mLayoutFrozen;
        int n = 0;
        if (!bl && !this.mIgnoreMotionEventTillDown) {
            int[] arrn2;
            if (this.dispatchOnItemTouch((MotionEvent)arrn)) {
                this.cancelTouch();
                return true;
            }
            Object object = this.mLayout;
            if (object == null) {
                return false;
            }
            bl = ((LayoutManager)object).canScrollHorizontally();
            boolean bl2 = this.mLayout.canScrollVertically();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            object = MotionEvent.obtain((MotionEvent)arrn);
            int n2 = arrn.getActionMasked();
            int n3 = arrn.getActionIndex();
            if (n2 == 0) {
                arrn2 = this.mNestedOffsets;
                arrn2[1] = 0;
                arrn2[0] = 0;
            }
            arrn2 = this.mNestedOffsets;
            ((MotionEvent)object).offsetLocation(arrn2[0], arrn2[1]);
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 != 5) {
                                if (n2 != 6) {
                                    n3 = 0;
                                } else {
                                    this.onPointerUp((MotionEvent)arrn);
                                    n3 = 0;
                                }
                            } else {
                                this.mScrollPointerId = arrn.getPointerId(n3);
                                this.mLastTouchX = n2 = (int)(arrn.getX(n3) + 0.5f);
                                this.mInitialTouchX = n2;
                                this.mLastTouchY = n3 = (int)(arrn.getY(n3) + 0.5f);
                                this.mInitialTouchY = n3;
                                n3 = 0;
                            }
                        } else {
                            this.cancelTouch();
                            n3 = 0;
                        }
                    } else {
                        int n4;
                        n3 = arrn.findPointerIndex(this.mScrollPointerId);
                        if (n3 < 0) {
                            arrn = new StringBuilder();
                            arrn.append("Error processing scroll; pointer index for id ");
                            arrn.append(this.mScrollPointerId);
                            arrn.append(" not found. Did any MotionEvents get skipped?");
                            Log.e(TAG, arrn.toString());
                            return false;
                        }
                        int n5 = (int)(arrn.getX(n3) + 0.5f);
                        int n6 = (int)(arrn.getY(n3) + 0.5f);
                        n2 = this.mLastTouchX - n5;
                        n3 = this.mLastTouchY - n6;
                        if (this.dispatchNestedPreScroll(n2, n3, this.mScrollConsumed, this.mScrollOffset)) {
                            arrn = this.mScrollConsumed;
                            n2 -= arrn[0];
                            n3 -= arrn[1];
                            arrn = this.mScrollOffset;
                            ((MotionEvent)object).offsetLocation(arrn[0], arrn[1]);
                            arrn2 = this.mNestedOffsets;
                            n4 = arrn2[0];
                            arrn = this.mScrollOffset;
                            arrn2[0] = n4 + arrn[0];
                            arrn2[1] = arrn2[1] + arrn[1];
                        }
                        int n7 = 0;
                        n4 = n2;
                        int n8 = n3;
                        if (this.mScrollState != 1) {
                            int n9;
                            int n10;
                            int n11;
                            n8 = n9 = 0;
                            n4 = n2;
                            if (bl) {
                                n11 = Math.abs(n2);
                                n10 = this.mTouchSlop;
                                n8 = n9;
                                n4 = n2;
                                if (n11 > n10) {
                                    n4 = n2 > 0 ? n2 - n10 : n2 + n10;
                                    n8 = 1;
                                }
                            }
                            n9 = n8;
                            n2 = n3;
                            if (bl2) {
                                n10 = Math.abs(n3);
                                n11 = this.mTouchSlop;
                                n9 = n8;
                                n2 = n3;
                                if (n10 > n11) {
                                    n2 = n3 > 0 ? n3 - n11 : n3 + n11;
                                    n9 = 1;
                                }
                            }
                            if (n9 != 0) {
                                this.setScrollState(1);
                                n8 = n2;
                            } else {
                                n8 = n2;
                            }
                        }
                        if (this.mScrollState == 1) {
                            arrn = this.mScrollOffset;
                            this.mLastTouchX = n5 - arrn[0];
                            this.mLastTouchY = n6 - arrn[1];
                            n3 = bl ? n4 : 0;
                            n2 = n;
                            if (bl2) {
                                n2 = n8;
                            }
                            if (this.scrollByInternal(n3, n2, (MotionEvent)object)) {
                                this.getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            if (this.mGapWorker != null && (n4 != 0 || n8 != 0)) {
                                this.mGapWorker.postFromTraversal(this, n4, n8);
                            }
                        }
                        n3 = n7;
                    }
                } else {
                    this.mVelocityTracker.addMovement((MotionEvent)object);
                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
                    float f = bl ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                    float f2 = bl2 ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                    if (f == 0.0f && f2 == 0.0f || !this.fling((int)f, (int)f2)) {
                        this.setScrollState(0);
                    }
                    this.resetTouch();
                    n3 = 1;
                }
            } else {
                int n12 = 0;
                this.mScrollPointerId = arrn.getPointerId(0);
                this.mLastTouchX = n3 = (int)(arrn.getX() + 0.5f);
                this.mInitialTouchX = n3;
                this.mLastTouchY = n3 = (int)(arrn.getY() + 0.5f);
                this.mInitialTouchY = n3;
                n3 = 0;
                if (bl) {
                    n3 = false | true;
                }
                n2 = n3;
                if (bl2) {
                    n2 = n3 | 2;
                }
                this.startNestedScroll(n2);
                n3 = n12;
            }
            if (n3 == 0) {
                this.mVelocityTracker.addMovement((MotionEvent)object);
            }
            ((MotionEvent)object).recycle();
            return true;
        }
        return false;
    }

    void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            this.postOnAnimation(this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            long l = this.getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l, viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    void removeAndRecycleViews() {
        Object object = this.mItemAnimator;
        if (object != null) {
            ((ItemAnimator)object).endAnimations();
        }
        if ((object = this.mLayout) != null) {
            ((LayoutManager)object).removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    boolean removeAnimatingView(View object) {
        this.eatRequestLayout();
        boolean bl = this.mChildHelper.removeViewIfHidden((View)object);
        if (bl) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            this.mRecycler.unscrapView((ViewHolder)object);
            this.mRecycler.recycleViewHolderInternal((ViewHolder)object);
        }
        this.resumeRequestLayout(bl ^ true);
        return bl;
    }

    @Override
    protected void removeDetachedView(View object, boolean bl) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
        if (viewHolder != null) {
            if (viewHolder.isTmpDetached()) {
                viewHolder.clearTmpDetachFlag();
            } else if (!viewHolder.shouldIgnore()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                ((StringBuilder)object).append(viewHolder);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        this.dispatchChildDetached((View)object);
        super.removeDetachedView((View)object, bl);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            boolean bl = this.getOverScrollMode() == 2;
            this.setWillNotDraw(bl);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list == null) {
            return;
        }
        list.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list != null) {
            list.remove(onScrollListener);
        }
    }

    void repositionShadowingViews() {
        int n = this.mChildHelper.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.mChildHelper.getChildAt(i);
            Object object = this.getChildViewHolder(view);
            if (object == null || ((ViewHolder)object).mShadowingHolder == null) continue;
            object = object.mShadowingHolder.itemView;
            int n2 = view.getLeft();
            int n3 = view.getTop();
            if (n2 == ((View)object).getLeft() && n3 == ((View)object).getTop()) continue;
            ((View)object).layout(n2, n3, ((View)object).getWidth() + n2, ((View)object).getHeight() + n3);
        }
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.mTempRect.set(0, 0, view2.getWidth(), view2.getHeight());
            Object object = view2.getLayoutParams();
            if (object instanceof LayoutParams) {
                object = (LayoutParams)object;
                if (!((LayoutParams)object).mInsetsDirty) {
                    object = ((LayoutParams)object).mDecorInsets;
                    Rect rect = this.mTempRect;
                    rect.left -= ((Rect)object).left;
                    rect = this.mTempRect;
                    rect.right += ((Rect)object).right;
                    rect = this.mTempRect;
                    rect.top -= ((Rect)object).top;
                    rect = this.mTempRect;
                    rect.bottom += ((Rect)object).bottom;
                }
            }
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.offsetRectIntoDescendantCoords(view, this.mTempRect);
            this.requestChildRectangleOnScreen(view, this.mTempRect, this.mFirstLayoutComplete ^ true);
        }
        super.requestChildFocus(view, view2);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, bl);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean bl) {
        int n = this.mOnItemTouchListeners.size();
        for (int i = 0; i < n; ++i) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(bl);
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    @Override
    public void requestLayout() {
        if (this.mEatRequestLayout == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
        } else {
            this.mLayoutRequestEaten = true;
        }
    }

    void resumeRequestLayout(boolean bl) {
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!bl) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (bl && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                this.dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        --this.mEatRequestLayout;
    }

    void saveOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.saveOldPosition();
        }
    }

    @Override
    public void scrollBy(int n, int n2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        boolean bl = layoutManager.canScrollHorizontally();
        boolean bl2 = this.mLayout.canScrollVertically();
        if (bl || bl2) {
            int n3 = 0;
            if (!bl) {
                n = 0;
            }
            if (bl2) {
                n3 = n2;
            }
            this.scrollByInternal(n, n3, null);
        }
    }

    boolean scrollByInternal(int n, int n2, MotionEvent arrn) {
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        this.consumePendingUpdateOperations();
        int[] arrn2 = this.mAdapter;
        boolean bl = false;
        if (arrn2 != null) {
            this.eatRequestLayout();
            this.onEnterLayoutOrScroll();
            Trace.beginSection(TRACE_SCROLL_TAG);
            n3 = n4;
            n7 = n8;
            if (n != 0) {
                n7 = this.mLayout.scrollHorizontallyBy(n, this.mRecycler, this.mState);
                n3 = n - n7;
            }
            n5 = n6;
            n9 = n10;
            if (n2 != 0) {
                n9 = this.mLayout.scrollVerticallyBy(n2, this.mRecycler, this.mState);
                n5 = n2 - n9;
            }
            Trace.endSection();
            this.repositionShadowingViews();
            this.onExitLayoutOrScroll();
            this.resumeRequestLayout(false);
        }
        if (!this.mItemDecorations.isEmpty()) {
            this.invalidate();
        }
        if (this.dispatchNestedScroll(n7, n9, n3, n5, this.mScrollOffset)) {
            n = this.mLastTouchX;
            arrn2 = this.mScrollOffset;
            this.mLastTouchX = n - arrn2[0];
            this.mLastTouchY -= arrn2[1];
            if (arrn != null) {
                arrn.offsetLocation(arrn2[0], arrn2[1]);
            }
            arrn2 = this.mNestedOffsets;
            n = arrn2[0];
            arrn = this.mScrollOffset;
            arrn2[0] = n + arrn[0];
            arrn2[1] = arrn2[1] + arrn[1];
        } else if (this.getOverScrollMode() != 2) {
            if (arrn != null) {
                this.pullGlows(arrn.getX(), n3, arrn.getY(), n5);
            }
            this.considerReleasingGlowsOnScroll(n, n2);
        }
        if (n7 != 0 || n9 != 0) {
            this.dispatchOnScrolled(n7, n9);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        if (n7 != 0 || n9 != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void scrollTo(int n, int n2) {
        Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        this.stopScroll();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        layoutManager.scrollToPosition(n);
        this.awakenScrollBars();
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        this.setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, false, true);
        this.requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        boolean bl = this.mChildDrawingOrderCallback != null;
        this.setChildrenDrawingOrderEnabled(bl);
    }

    @VisibleForTesting
    boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int n) {
        if (this.isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = n;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        viewHolder.itemView.setImportantForAccessibility(n);
        return true;
    }

    @Override
    public void setClipToPadding(boolean bl) {
        if (bl != this.mClipToPadding) {
            this.invalidateGlows();
        }
        this.mClipToPadding = bl;
        super.setClipToPadding(bl);
        if (this.mFirstLayoutComplete) {
            this.requestLayout();
        }
    }

    void setDataSetChangedAfterLayout() {
        if (this.mDataSetHasChangedAfterLayout) {
            return;
        }
        this.mDataSetHasChangedAfterLayout = true;
        int n = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n; ++i) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (viewHolder == null || viewHolder.shouldIgnore()) continue;
            viewHolder.addFlags(512);
        }
        this.mRecycler.setAdapterPositionsAsUnknown();
        this.markKnownViewsInvalid();
    }

    public void setHasFixedSize(boolean bl) {
        this.mHasFixedSize = bl;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.mItemAnimator;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        if ((itemAnimator = (this.mItemAnimator = itemAnimator)) != null) {
            itemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    public void setItemViewCacheSize(int n) {
        this.mRecycler.setViewCacheSize(n);
    }

    public void setLayoutFrozen(boolean bl) {
        if (bl != this.mLayoutFrozen) {
            this.assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (!bl) {
                this.mLayoutFrozen = false;
                if (this.mLayoutRequestEaten && this.mLayout != null && this.mAdapter != null) {
                    this.requestLayout();
                }
                this.mLayoutRequestEaten = false;
            } else {
                long l = SystemClock.uptimeMillis();
                this.onTouchEvent(MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0));
                this.mLayoutFrozen = true;
                this.mIgnoreMotionEventTillDown = true;
                this.stopScroll();
            }
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        Object object;
        if (layoutManager == this.mLayout) {
            return;
        }
        this.stopScroll();
        if (this.mLayout != null) {
            object = this.mItemAnimator;
            if (object != null) {
                ((ItemAnimator)object).endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            this.mRecycler.clear();
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            this.mRecycler.clear();
        }
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView == null) {
                this.mLayout.setRecyclerView(this);
                if (this.mIsAttached) {
                    this.mLayout.dispatchAttachedToWindow(this);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("LayoutManager ");
                ((StringBuilder)object).append(layoutManager);
                ((StringBuilder)object).append(" is already attached to a RecyclerView: ");
                ((StringBuilder)object).append(layoutManager.mRecyclerView);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        this.mRecycler.updateViewCacheSize();
        this.requestLayout();
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean bl) {
        this.mPreserveFocusAfterLayout = bl;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    void setScrollState(int n) {
        if (n == this.mScrollState) {
            return;
        }
        this.mScrollState = n;
        if (n != 2) {
            this.stopScrollersInternal();
        }
        this.dispatchOnScrollStateChanged(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setScrollingTouchSlop(int n) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.getContext());
        if (n != 0) {
            if (n == 1) {
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
            stringBuilder.append(n);
            stringBuilder.append("; using default value");
            Log.w(TAG, stringBuilder.toString());
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (this.isComputingLayout()) {
            int n = 0;
            if (accessibilityEvent != null) {
                n = accessibilityEvent.getContentChangeTypes();
            }
            int n2 = n;
            if (n == 0) {
                n2 = 0;
            }
            this.mEatenAccessibilityChangeFlags |= n2;
            return true;
        }
        return false;
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, null);
    }

    public void smoothScrollBy(int n, int n2, Interpolator interpolator2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        if (!layoutManager.canScrollHorizontally()) {
            n = 0;
        }
        if (!this.mLayout.canScrollVertically()) {
            n2 = 0;
        }
        if (n != 0 || n2 != 0) {
            this.mViewFlinger.smoothScrollBy(n, n2, interpolator2);
        }
    }

    public void smoothScrollToPosition(int n) {
        if (this.mLayoutFrozen) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        layoutManager.smoothScrollToPosition(this, this.mState, n);
    }

    public void stopScroll() {
        this.setScrollState(0);
        this.stopScrollersInternal();
    }

    public void swapAdapter(Adapter adapter, boolean bl) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, true, bl);
        this.setDataSetChangedAfterLayout();
        this.requestLayout();
    }

    void viewRangeUpdate(int n, int n2, Object object) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < n3; ++i) {
            View view = this.mChildHelper.getUnfilteredChildAt(i);
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder == null || viewHolder.shouldIgnore() || viewHolder.mPosition < n || viewHolder.mPosition >= n + n2) continue;
            viewHolder.addFlags(2);
            viewHolder.addChangePayload(object);
            ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.viewRangeUpdate(n, n2);
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public final void bindViewHolder(VH object, int n) {
            ((ViewHolder)object).mPosition = n;
            if (this.hasStableIds()) {
                ((ViewHolder)object).mItemId = this.getItemId(n);
            }
            ((ViewHolder)object).setFlags(1, 519);
            Trace.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            this.onBindViewHolder(object, n, ((ViewHolder)object).getUnmodifiedPayloads());
            ((ViewHolder)object).clearPayload();
            object = ((ViewHolder)object).itemView.getLayoutParams();
            if (object instanceof LayoutParams) {
                ((LayoutParams)object).mInsetsDirty = true;
            }
            Trace.endSection();
        }

        public final VH createViewHolder(ViewGroup viewGroup, int n) {
            Trace.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            viewGroup = this.onCreateViewHolder(viewGroup, n);
            ((ViewHolder)viewGroup).mItemViewType = n;
            Trace.endSection();
            return (VH)viewGroup;
        }

        public abstract int getItemCount();

        public long getItemId(int n) {
            return -1L;
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int n) {
            this.mObservable.notifyItemRangeChanged(n, 1);
        }

        public final void notifyItemChanged(int n, Object object) {
            this.mObservable.notifyItemRangeChanged(n, 1, object);
        }

        public final void notifyItemInserted(int n) {
            this.mObservable.notifyItemRangeInserted(n, 1);
        }

        public final void notifyItemMoved(int n, int n2) {
            this.mObservable.notifyItemMoved(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2) {
            this.mObservable.notifyItemRangeChanged(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2, Object object) {
            this.mObservable.notifyItemRangeChanged(n, n2, object);
        }

        public final void notifyItemRangeInserted(int n, int n2) {
            this.mObservable.notifyItemRangeInserted(n, n2);
        }

        public final void notifyItemRangeRemoved(int n, int n2) {
            this.mObservable.notifyItemRangeRemoved(n, n2);
        }

        public final void notifyItemRemoved(int n) {
            this.mObservable.notifyItemRangeRemoved(n, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH var1, int var2);

        public void onBindViewHolder(VH VH, int n, List<Object> list) {
            this.onBindViewHolder(VH, n);
        }

        public abstract VH onCreateViewHolder(ViewGroup var1, int var2);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH VH) {
            return false;
        }

        public void onViewAttachedToWindow(VH VH) {
        }

        public void onViewDetachedFromWindow(VH VH) {
        }

        public void onViewRecycled(VH VH) {
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public void setHasStableIds(boolean bl) {
            if (!this.hasObservers()) {
                this.mHasStableIds = bl;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver(adapterDataObserver);
        }
    }

    static class AdapterDataObservable
    extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return this.mObservers.isEmpty() ^ true;
        }

        public void notifyChanged() {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onChanged();
            }
        }

        public void notifyItemMoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(n, n2, 1);
            }
        }

        public void notifyItemRangeChanged(int n, int n2) {
            this.notifyItemRangeChanged(n, n2, null);
        }

        public void notifyItemRangeChanged(int n, int n2, Object object) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(n, n2, object);
            }
        }

        public void notifyItemRangeInserted(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(n, n2);
            }
        }

        public void notifyItemRangeRemoved(int n, int n2) {
            for (int i = this.mObservers.size() - 1; i >= 0; --i) {
                ((AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(n, n2);
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int n, int n2) {
        }

        public void onItemRangeChanged(int n, int n2, Object object) {
            this.onItemRangeChanged(n, n2);
        }

        public void onItemRangeInserted(int n, int n2) {
        }

        public void onItemRangeMoved(int n, int n2, int n3) {
        }

        public void onItemRangeRemoved(int n, int n2) {
        }
    }

    public static interface ChildDrawingOrderCallback {
        public int onGetChildDrawingOrder(int var1, int var2);
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120L;
        private long mChangeDuration = 250L;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250L;
        private long mRemoveDuration = 120L;

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int n = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            int n2 = n;
            if ((n & 4) == 0) {
                int n3 = viewHolder.getOldPosition();
                int n4 = viewHolder.getAdapterPosition();
                n2 = n;
                if (n3 != -1) {
                    n2 = n;
                    if (n4 != -1) {
                        n2 = n;
                        if (n3 != n4) {
                            n2 = n | 2048;
                        }
                    }
                }
            }
            return n2;
        }

        public abstract boolean animateAppearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animateChange(ViewHolder var1, ViewHolder var2, ItemHolderInfo var3, ItemHolderInfo var4);

        public abstract boolean animateDisappearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animatePersistence(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
            return this.canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            this.onAnimationFinished(viewHolder);
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener != null) {
                itemAnimatorListener.onAnimationFinished(viewHolder);
            }
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            this.onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int n = this.mFinishedListeners.size();
            for (int i = 0; i < n; ++i) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public abstract void endAnimation(ViewHolder var1);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean bl = this.isRunning();
            if (itemAnimatorFinishedListener != null) {
                if (!bl) {
                    itemAnimatorFinishedListener.onAnimationsFinished();
                } else {
                    this.mFinishedListeners.add(itemAnimatorFinishedListener);
                }
            }
            return bl;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        public ItemHolderInfo recordPostLayoutInformation(State state, ViewHolder viewHolder) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public ItemHolderInfo recordPreLayoutInformation(State state, ViewHolder viewHolder, int n, List<Object> list) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long l) {
            this.mAddDuration = l;
        }

        public void setChangeDuration(long l) {
            this.mChangeDuration = l;
        }

        void setListener(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public void setMoveDuration(long l) {
            this.mMoveDuration = l;
        }

        public void setRemoveDuration(long l) {
            this.mRemoveDuration = l;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface AdapterChanges {
        }

        public static interface ItemAnimatorFinishedListener {
            public void onAnimationsFinished();
        }

        static interface ItemAnimatorListener {
            public void onAnimationFinished(ViewHolder var1);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return this.setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder object, int n) {
                object = ((ViewHolder)object).itemView;
                this.left = ((View)object).getLeft();
                this.top = ((View)object).getTop();
                this.right = ((View)object).getRight();
                this.bottom = ((View)object).getBottom();
                return this;
            }
        }

    }

    private class ItemAnimatorRestoreListener
    implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int n, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            this.getItemOffsets(rect, ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        SmoothScroller mSmoothScroller;
        private int mWidth;
        private int mWidthMode;

        /*
         * Enabled aggressive block sorting
         */
        private void addViewInt(View view, int n, boolean bl) {
            Object object = RecyclerView.getChildViewHolderInt(view);
            if (!bl && !((ViewHolder)object).isRemoved()) {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout((ViewHolder)object);
            } else {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout((ViewHolder)object);
            }
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!((ViewHolder)object).wasReturnedFromScrap() && !((ViewHolder)object).isScrap()) {
                if (view.getParent() == this.mRecyclerView) {
                    int n2 = this.mChildHelper.indexOfChild(view);
                    int n3 = n;
                    if (n == -1) {
                        n3 = this.mChildHelper.getChildCount();
                    }
                    if (n2 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                        ((StringBuilder)object).append(this.mRecyclerView.indexOfChild(view));
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                    if (n2 != n3) {
                        this.mRecyclerView.mLayout.moveView(n2, n3);
                    }
                } else {
                    this.mChildHelper.addView(view, n, false);
                    layoutParams.mInsetsDirty = true;
                    SmoothScroller smoothScroller = this.mSmoothScroller;
                    if (smoothScroller != null && smoothScroller.isRunning()) {
                        this.mSmoothScroller.onChildAttachedToWindow(view);
                    }
                }
            } else {
                if (((ViewHolder)object).isScrap()) {
                    ((ViewHolder)object).unScrap();
                } else {
                    ((ViewHolder)object).clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, n, view.getLayoutParams(), false);
            }
            if (layoutParams.mPendingInvalidate) {
                ((ViewHolder)object).itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public static int chooseSize(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode(n);
            n = View.MeasureSpec.getSize(n);
            if (n4 != Integer.MIN_VALUE) {
                if (n4 != 1073741824) {
                    return Math.max(n2, n3);
                }
                return n;
            }
            return Math.min(n, Math.max(n2, n3));
        }

        private void detachViewInternal(int n, View view) {
            this.mChildHelper.detachViewFromParent(n);
        }

        public static int getChildMeasureSpec(int n, int n2, int n3, int n4, boolean bl) {
            block8 : {
                int n5;
                int n6;
                int n7;
                block6 : {
                    block9 : {
                        block10 : {
                            block11 : {
                                block7 : {
                                    n5 = Math.max(0, n - n3);
                                    n7 = 0;
                                    n3 = 0;
                                    n6 = 0;
                                    n = 0;
                                    if (!bl) break block6;
                                    if (n4 < 0) break block7;
                                    n3 = n4;
                                    n = 1073741824;
                                    break block8;
                                }
                                if (n4 != -1) break block9;
                                if (n2 == Integer.MIN_VALUE) break block10;
                                if (n2 == 0) break block11;
                                if (n2 == 1073741824) break block10;
                                break block8;
                            }
                            n3 = 0;
                            n = 0;
                            break block8;
                        }
                        n3 = n5;
                        n = n2;
                        break block8;
                    }
                    n3 = n7;
                    n = n6;
                    if (n4 == -2) {
                        n3 = 0;
                        n = 0;
                    }
                    break block8;
                }
                if (n4 >= 0) {
                    n3 = n4;
                    n = 1073741824;
                } else if (n4 == -1) {
                    n3 = n5;
                    n = n2;
                } else {
                    n3 = n7;
                    n = n6;
                    if (n4 == -2) {
                        n3 = n5;
                        n = n2 != Integer.MIN_VALUE && n2 != 1073741824 ? 0 : Integer.MIN_VALUE;
                    }
                }
            }
            return View.MeasureSpec.makeMeasureSpec(n3, n);
        }

        @Deprecated
        public static int getChildMeasureSpec(int n, int n2, int n3, boolean bl) {
            int n4 = Math.max(0, n - n2);
            n = 0;
            n2 = 0;
            if (bl) {
                if (n3 >= 0) {
                    n = n3;
                    n2 = 1073741824;
                } else {
                    n = 0;
                    n2 = 0;
                }
            } else if (n3 >= 0) {
                n = n3;
                n2 = 1073741824;
            } else if (n3 == -1) {
                n = n4;
                n2 = 1073741824;
            } else if (n3 == -2) {
                n = n4;
                n2 = Integer.MIN_VALUE;
            }
            return View.MeasureSpec.makeMeasureSpec(n, n2);
        }

        public static Properties getProperties(Context object, AttributeSet attributeSet, int n, int n2) {
            Properties properties = new Properties();
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, n2);
            properties.orientation = ((TypedArray)object).getInt(0, 1);
            properties.spanCount = ((TypedArray)object).getInt(4, 1);
            properties.reverseLayout = ((TypedArray)object).getBoolean(3, false);
            properties.stackFromEnd = ((TypedArray)object).getBoolean(5, false);
            ((TypedArray)object).recycle();
            return properties;
        }

        private static boolean isMeasurementUpToDate(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode(n2);
            n2 = View.MeasureSpec.getSize(n2);
            boolean bl = false;
            boolean bl2 = false;
            if (n3 > 0 && n != n3) {
                return false;
            }
            if (n4 != Integer.MIN_VALUE) {
                if (n4 != 0) {
                    if (n4 != 1073741824) {
                        return false;
                    }
                    if (n2 == n) {
                        bl2 = true;
                    }
                    return bl2;
                }
                return true;
            }
            bl2 = bl;
            if (n2 >= n) {
                bl2 = true;
            }
            return bl2;
        }

        private void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        private void scrapOrRecycleView(Recycler recycler, int n, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) {
                return;
            }
            if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                this.removeViewAt(n);
                recycler.recycleViewHolderInternal(viewHolder);
            } else {
                this.detachViewAt(n);
                recycler.scrapView(view);
                this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
            }
        }

        public void addDisappearingView(View view) {
            this.addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int n) {
            this.addViewInt(view, n, true);
        }

        public void addView(View view) {
            this.addView(view, -1);
        }

        public void addView(View view, int n) {
            this.addViewInt(view, n, false);
        }

        public void assertInLayoutOrScroll(String string2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertInLayoutOrScroll(string2);
            }
        }

        public void assertNotInLayoutOrScroll(String string2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(string2);
            }
        }

        public void attachView(View view) {
            this.attachView(view, -1);
        }

        public void attachView(View view, int n) {
            this.attachView(view, n, (LayoutParams)view.getLayoutParams());
        }

        public void attachView(View view, int n, LayoutParams layoutParams) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            this.mChildHelper.attachViewToParent(view, n, layoutParams, viewHolder.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(recyclerView.getItemDecorInsetsForChild(view));
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            boolean bl = layoutParams != null;
            return bl;
        }

        public void collectAdjacentPrefetchPositions(int n, int n2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int n, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                this.scrapOrRecycleView(recycler, i, this.getChildAt(i));
            }
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            this.scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
        }

        public void detachAndScrapViewAt(int n, Recycler recycler) {
            this.scrapOrRecycleView(recycler, n, this.getChildAt(n));
        }

        public void detachView(View view) {
            int n = this.mChildHelper.indexOfChild(view);
            if (n >= 0) {
                this.detachViewInternal(n, view);
            }
        }

        public void detachViewAt(int n) {
            this.detachViewInternal(n, this.getChildAt(n));
        }

        void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            this.onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            this.onDetachedFromWindow(recyclerView, recycler);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
            }
        }

        public View findContainingItemView(View view) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return null;
            }
            if ((view = recyclerView.findContainingItemView(view)) == null) {
                return null;
            }
            if (this.mChildHelper.isHidden(view)) {
                return null;
            }
            return view;
        }

        public View findViewByPosition(int n) {
            int n2 = this.getChildCount();
            for (int i = 0; i < n2; ++i) {
                View view = this.getChildAt(i);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder == null || viewHolder.getLayoutPosition() != n || viewHolder.shouldIgnore() || !this.mRecyclerView.mState.isPreLayout() && viewHolder.isRemoved()) continue;
                return view;
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
        }

        public View getChildAt(int n) {
            Object object = this.mChildHelper;
            object = object != null ? ((ChildHelper)object).getChildAt(n) : null;
            return object;
        }

        public int getChildCount() {
            ChildHelper childHelper = this.mChildHelper;
            int n = childHelper != null ? childHelper.getChildCount() : 0;
            return n;
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.mRecyclerView;
            boolean bl = recyclerView != null && recyclerView.mClipToPadding;
            return bl;
        }

        public int getColumnCountForAccessibility(Recycler object, State state) {
            object = this.mRecyclerView;
            int n = 1;
            if (object != null && ((RecyclerView)object).mAdapter != null) {
                if (this.canScrollHorizontally()) {
                    n = this.mRecyclerView.mAdapter.getItemCount();
                }
                return n;
            }
            return 1;
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + this.getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - this.getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + this.getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - this.getTopDecorationHeight(view);
        }

        public View getFocusedChild() {
            View view = this.mRecyclerView;
            if (view == null) {
                return null;
            }
            if ((view = view.getFocusedChild()) != null && !this.mChildHelper.isHidden(view)) {
                return view;
            }
            return null;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getItemCount() {
            Object object = this.mRecyclerView;
            object = object != null ? ((RecyclerView)object).getAdapter() : null;
            int n = object != null ? ((Adapter)object).getItemCount() : 0;
            return n;
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return this.mRecyclerView.getLayoutDirection();
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.left;
        }

        public int getMinimumHeight() {
            return this.mRecyclerView.getMinimumHeight();
        }

        public int getMinimumWidth() {
            return this.mRecyclerView.getMinimumWidth();
        }

        public int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingBottom() : 0;
            return n;
        }

        public int getPaddingEnd() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingEnd() : 0;
            return n;
        }

        public int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingLeft() : 0;
            return n;
        }

        public int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingRight() : 0;
            return n;
        }

        public int getPaddingStart() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingStart() : 0;
            return n;
        }

        public int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            int n = recyclerView != null ? recyclerView.getPaddingTop() : 0;
            return n;
        }

        public int getPosition(View view) {
            return ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.right;
        }

        public int getRowCountForAccessibility(Recycler object, State state) {
            object = this.mRecyclerView;
            int n = 1;
            if (object != null && ((RecyclerView)object).mAdapter != null) {
                if (this.canScrollVertically()) {
                    n = this.mRecyclerView.mAdapter.getItemCount();
                }
                return n;
            }
            return 1;
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.top;
        }

        public void getTransformedBoundingBox(View view, boolean bl, Rect rect) {
            Matrix matrix;
            Parcelable parcelable;
            if (bl) {
                parcelable = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
                rect.set(-((Rect)parcelable).left, -((Rect)parcelable).top, view.getWidth() + ((Rect)parcelable).right, view.getHeight() + ((Rect)parcelable).bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.mRecyclerView != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                parcelable = this.mRecyclerView.mTempRectF;
                ((RectF)parcelable).set(rect);
                matrix.mapRect((RectF)parcelable);
                rect.set((int)Math.floor(((RectF)parcelable).left), (int)Math.floor(((RectF)parcelable).top), (int)Math.ceil(((RectF)parcelable).right), (int)Math.ceil(((RectF)parcelable).bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewGroup.LayoutParams layoutParams = this.getChildAt(i).getLayoutParams();
                if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
                return true;
            }
            return false;
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            boolean bl = recyclerView != null && recyclerView.hasFocus();
            return bl;
        }

        public void ignoreView(View object) {
            RecyclerView recyclerView;
            ViewParent viewParent = ((View)object).getParent();
            if (viewParent == (recyclerView = this.mRecyclerView) && recyclerView.indexOfChild((View)object) != -1) {
                object = RecyclerView.getChildViewHolderInt((View)object);
                ((ViewHolder)object).addFlags(128);
                this.mRecyclerView.mViewInfoStore.removeViewHolder((ViewHolder)object);
                return;
            }
            throw new IllegalArgumentException("View should be fully attached to be ignored");
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.mRecyclerView;
            boolean bl = recyclerView != null && recyclerView.isFocused();
            return bl;
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            boolean bl = smoothScroller != null && smoothScroller.isRunning();
            return bl;
        }

        public void layoutDecorated(View view, int n, int n2, int n3, int n4) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            view.layout(rect.left + n, rect.top + n2, n3 - rect.right, n4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int n, int n2, int n3, int n4) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(rect.left + n + layoutParams.leftMargin, rect.top + n2 + layoutParams.topMargin, n3 - rect.right - layoutParams.rightMargin, n4 - rect.bottom - layoutParams.bottomMargin);
        }

        public void measureChild(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n, n2);
            }
        }

        public void measureChildWithMargins(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
                view.measure(n, n2);
            }
        }

        public void moveView(int n, int n2) {
            Object object = this.getChildAt(n);
            if (object != null) {
                this.detachViewAt(n);
                this.attachView((View)object, n2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot move a child from non-existing index:");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void offsetChildrenHorizontal(int n) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.offsetChildrenHorizontal(n);
            }
        }

        public void offsetChildrenVertical(int n) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.offsetChildrenVertical(n);
            }
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n, int n2) {
            return false;
        }

        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.onDetachedFromWindow(recyclerView);
        }

        public View onFocusSearchFailed(View view, int n, Recycler recycler, State state) {
            return null;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(Recycler object, State state, AccessibilityEvent accessibilityEvent) {
            object = this.mRecyclerView;
            if (object != null && accessibilityEvent != null) {
                boolean bl = true;
                if (!(((View)object).canScrollVertically(1) || this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1) || this.mRecyclerView.canScrollHorizontally(1))) {
                    bl = false;
                }
                accessibilityEvent.setScrollable(bl);
                if (this.mRecyclerView.mAdapter != null) {
                    accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
                }
                return;
            }
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfo);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfo accessibilityNodeInfo) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfo.addAction(8192);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfo.addAction(4096);
                accessibilityNodeInfo.setScrollable(true);
            }
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
        }

        void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView)) {
                this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfo);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            boolean bl = this.canScrollVertically();
            int n = 0;
            int n2 = bl ? this.getPosition(view) : 0;
            if (this.canScrollHorizontally()) {
                n = this.getPosition(view);
            }
            accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(n2, 1, n, 1, false, false));
        }

        public View onInterceptFocusSearch(View view, int n) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
            this.onItemsUpdated(recyclerView, n, n2);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(Recycler recycler, State state, int n, int n2) {
            this.mRecyclerView.defaultOnMeasure(n, n2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            boolean bl = this.isSmoothScrolling() || recyclerView.isComputingLayout();
            return bl;
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return this.onRequestChildFocus(recyclerView, view, view2);
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int n) {
        }

        boolean performAccessibilityAction(int n, Bundle bundle) {
            return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n, bundle);
        }

        public boolean performAccessibilityAction(Recycler object, State state, int n, Bundle bundle) {
            object = this.mRecyclerView;
            if (object == null) {
                return false;
            }
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            if (n != 4096) {
                if (n != 8192) {
                    n = n3;
                } else {
                    if (((View)object).canScrollVertically(-1)) {
                        n4 = -(this.getHeight() - this.getPaddingTop() - this.getPaddingBottom());
                    }
                    n = n4;
                    if (this.mRecyclerView.canScrollHorizontally(-1)) {
                        n5 = -(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
                        n = n4;
                    }
                }
            } else {
                n4 = n2;
                if (((View)object).canScrollVertically(1)) {
                    n4 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                }
                n = n4;
                if (this.mRecyclerView.canScrollHorizontally(1)) {
                    n5 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                    n = n4;
                }
            }
            if (n == 0 && n5 == 0) {
                return false;
            }
            this.mRecyclerView.smoothScrollBy(n5, n);
            return true;
        }

        boolean performAccessibilityActionForItem(View view, int n, Bundle bundle) {
            return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n, bundle);
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int n, Bundle bundle) {
            return false;
        }

        public void postOnAnimation(Runnable runnable) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.postOnAnimation(runnable);
            }
        }

        public void removeAllViews() {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                this.mChildHelper.removeViewAt(i);
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                if (RecyclerView.getChildViewHolderInt(this.getChildAt(i)).shouldIgnore()) continue;
                this.removeAndRecycleViewAt(i, recycler);
            }
        }

        void removeAndRecycleScrapInt(Recycler recycler) {
            int n = recycler.getScrapCount();
            for (int i = n - 1; i >= 0; --i) {
                View view = recycler.getScrapViewAt(i);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder.shouldIgnore()) continue;
                viewHolder.setIsRecyclable(false);
                if (viewHolder.isTmpDetached()) {
                    this.mRecyclerView.removeDetachedView(view, false);
                }
                if (this.mRecyclerView.mItemAnimator != null) {
                    this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
                }
                viewHolder.setIsRecyclable(true);
                recycler.quickRecycleScrapView(view);
            }
            recycler.clearScrap();
            if (n > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            this.removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int n, Recycler recycler) {
            View view = this.getChildAt(n);
            this.removeViewAt(n);
            recycler.recycleView(view);
        }

        public boolean removeCallbacks(Runnable runnable) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int n) {
            if (this.getChildAt(n) != null) {
                this.mChildHelper.removeViewAt(n);
            }
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingTop();
            int n3 = this.getWidth() - this.getPaddingRight();
            int n4 = this.getHeight();
            int n5 = this.getPaddingBottom();
            int n6 = view.getLeft() + rect.left - view.getScrollX();
            int n7 = view.getTop() + rect.top - view.getScrollY();
            int n8 = rect.width() + n6;
            int n9 = rect.height();
            int n10 = Math.min(0, n6 - n);
            int n11 = Math.min(0, n7 - n2);
            int n12 = Math.max(0, n8 - n3);
            n9 = Math.max(0, n9 + n7 - (n4 - n5));
            if (this.getLayoutDirection() == 1) {
                n10 = n12 != 0 ? n12 : Math.max(n10, n8 - n3);
            } else if (n10 == 0) {
                n10 = Math.min(n6 - n, n12);
            }
            if (n11 == 0) {
                n11 = Math.min(n7 - n2, n9);
            }
            if (n10 == 0 && n11 == 0) {
                return false;
            }
            if (bl) {
                recyclerView.scrollBy(n10, n11);
            } else {
                recyclerView.smoothScrollBy(n10, n11);
            }
            return true;
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int n) {
        }

        public int scrollVerticallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void setAutoMeasureEnabled(boolean bl) {
            this.mAutoMeasure = bl;
        }

        void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        public final void setItemPrefetchEnabled(boolean bl) {
            if (bl != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = bl;
                this.mPrefetchMaxCountObserved = 0;
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        void setMeasureSpecs(int n, int n2) {
            this.mWidth = View.MeasureSpec.getSize(n);
            this.mWidthMode = View.MeasureSpec.getMode(n);
            if (this.mWidthMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize(n2);
            this.mHeightMode = View.MeasureSpec.getMode(n2);
            if (this.mHeightMode == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        public void setMeasuredDimension(int n, int n2) {
            this.mRecyclerView.setMeasuredDimension(n, n2);
        }

        public void setMeasuredDimension(Rect rect, int n, int n2) {
            int n3 = rect.width();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = rect.height();
            int n7 = this.getPaddingTop();
            int n8 = this.getPaddingBottom();
            this.setMeasuredDimension(LayoutManager.chooseSize(n, n3 + n4 + n5, this.getMinimumWidth()), LayoutManager.chooseSize(n2, n6 + n7 + n8, this.getMinimumHeight()));
        }

        void setMeasuredDimensionFromChildren(int n, int n2) {
            int n3 = this.getChildCount();
            if (n3 == 0) {
                this.mRecyclerView.defaultOnMeasure(n, n2);
                return;
            }
            int n4 = Integer.MAX_VALUE;
            int n5 = Integer.MAX_VALUE;
            int n6 = Integer.MIN_VALUE;
            int n7 = Integer.MIN_VALUE;
            for (int i = 0; i < n3; ++i) {
                View view = this.getChildAt(i);
                Rect rect = this.mRecyclerView.mTempRect;
                this.getDecoratedBoundsWithMargins(view, rect);
                int n8 = n4;
                if (rect.left < n4) {
                    n8 = rect.left;
                }
                int n9 = n6;
                if (rect.right > n6) {
                    n9 = rect.right;
                }
                n6 = n5;
                if (rect.top < n5) {
                    n6 = rect.top;
                }
                int n10 = n7;
                if (rect.bottom > n7) {
                    n10 = rect.bottom;
                }
                n4 = n8;
                n5 = n6;
                n6 = n9;
                n7 = n10;
            }
            this.mRecyclerView.mTempRect.set(n4, n5, n6, n7);
            this.setMeasuredDimension(this.mRecyclerView.mTempRect, n, n2);
        }

        public void setMeasurementCacheEnabled(boolean bl) {
            this.mMeasurementCacheEnabled = bl;
        }

        void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        boolean shouldMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            boolean bl = view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !LayoutManager.isMeasurementUpToDate(view.getWidth(), n, layoutParams.width) || !LayoutManager.isMeasurementUpToDate(view.getHeight(), n2, layoutParams.height);
            return bl;
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean shouldReMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            boolean bl = !(this.mMeasurementCacheEnabled && LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n, layoutParams.width) && LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n2, layoutParams.height));
            return bl;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int n) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            if (smoothScroller2 != null && smoothScroller != smoothScroller2 && smoothScroller2.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public void stopIgnoringView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).stopIgnoring();
            ((ViewHolder)object).resetInternal();
            ((ViewHolder)object).addFlags(4);
        }

        void stopSmoothScroller() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            if (smoothScroller != null) {
                smoothScroller.stop();
            }
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public static interface LayoutPrefetchRegistry {
            public void addPosition(int var1, int var2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        @Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }
    }

    public static interface OnChildAttachStateChangeListener {
        public void onChildViewAttachedToWindow(View var1);

        public void onChildViewDetachedFromWindow(View var1);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int var1, int var2);
    }

    public static interface OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

        public void onRequestDisallowInterceptTouchEvent(boolean var1);

        public void onTouchEvent(RecyclerView var1, MotionEvent var2);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        }

        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        }
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        SparseArray<ScrapData> mScrap = new SparseArray();

        private ScrapData getScrapDataForType(int n) {
            ScrapData scrapData;
            ScrapData scrapData2 = scrapData = this.mScrap.get(n);
            if (scrapData == null) {
                scrapData2 = new ScrapData();
                this.mScrap.put(n, scrapData2);
            }
            return scrapData2;
        }

        void attach(Adapter adapter) {
            ++this.mAttachCount;
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); ++i) {
                this.mScrap.valueAt((int)i).mScrapHeap.clear();
            }
        }

        void detach() {
            --this.mAttachCount;
        }

        void factorInBindTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mBindRunningAverageNs = this.runningAverage(scrapData.mBindRunningAverageNs, l);
        }

        void factorInCreateTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mCreateRunningAverageNs = this.runningAverage(scrapData.mCreateRunningAverageNs, l);
        }

        public ViewHolder getRecycledView(int n) {
            Object object = this.mScrap.get(n);
            if (object != null && !((ScrapData)object).mScrapHeap.isEmpty()) {
                object = ((ScrapData)object).mScrapHeap;
                return (ViewHolder)((ArrayList)object).remove(((ArrayList)object).size() - 1);
            }
            return null;
        }

        public int getRecycledViewCount(int n) {
            return this.getScrapDataForType((int)n).mScrapHeap.size();
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            if (adapter != null) {
                this.detach();
            }
            if (!bl && this.mAttachCount == 0) {
                this.clear();
            }
            if (adapter2 != null) {
                this.attach(adapter2);
            }
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int n = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = this.getScrapDataForType((int)n).mScrapHeap;
            if (this.mScrap.get((int)n).mMaxScrap <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        long runningAverage(long l, long l2) {
            if (l == 0L) {
                return l2;
            }
            return l / 4L * 3L + l2 / 4L;
        }

        public void setMaxRecycledViews(int n, int n2) {
            ArrayList<ViewHolder> arrayList = this.getScrapDataForType(n);
            ((ScrapData)arrayList).mMaxScrap = n2;
            arrayList = ((ScrapData)arrayList).mScrapHeap;
            if (arrayList != null) {
                while (arrayList.size() > n2) {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }

        int size() {
            int n = 0;
            for (int i = 0; i < this.mScrap.size(); ++i) {
                ArrayList<ViewHolder> arrayList = this.mScrap.valueAt((int)i).mScrapHeap;
                int n2 = n;
                if (arrayList != null) {
                    n2 = n + arrayList.size();
                }
                n = n2;
            }
            return n;
        }

        boolean willBindInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mBindRunningAverageNs;
            boolean bl = l3 == 0L || l + l3 < l2;
            return bl;
        }

        boolean willCreateInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mCreateRunningAverageNs;
            boolean bl = l3 == 0L || l + l3 < l2;
            return bl;
        }

        static class ScrapData {
            long mBindRunningAverageNs = 0L;
            long mCreateRunningAverageNs = 0L;
            int mMaxScrap = 5;
            @UnsupportedAppUsage
            ArrayList<ViewHolder> mScrapHeap = new ArrayList();

            ScrapData() {
            }
        }

    }

    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax = 2;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax = 2;

        private void attachAccessibilityDelegate(View view) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
                if (view.getAccessibilityDelegate() == null) {
                    view.setAccessibilityDelegate(RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl) {
            int n;
            for (n = viewGroup.getChildCount() - 1; n >= 0; --n) {
                View view = viewGroup.getChildAt(n);
                if (!(view instanceof ViewGroup)) continue;
                this.invalidateDisplayListInt((ViewGroup)view, true);
            }
            if (!bl) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
            } else {
                n = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(n);
            }
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ViewGroup) {
                this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
            }
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder viewHolder, int n, int n2, long l) {
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            int n3 = viewHolder.getItemViewType();
            long l2 = RecyclerView.this.getNanoTime();
            if (l != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(n3, l2, l)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n);
            l = RecyclerView.this.getNanoTime();
            this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), l - l2);
            this.attachAccessibilityDelegate(viewHolder.itemView);
            if (RecyclerView.this.mState.isPreLayout()) {
                viewHolder.mPreLayoutPosition = n2;
            }
            return true;
        }

        void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean bl) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            viewHolder.itemView.setAccessibilityDelegate(null);
            if (bl) {
                this.dispatchViewRecycled(viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            this.getRecycledViewPool().putRecycledView(viewHolder);
        }

        public void bindViewToPosition(View object, int n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
            if (viewHolder != null) {
                int n2 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
                if (n2 >= 0 && n2 < RecyclerView.this.mAdapter.getItemCount()) {
                    this.tryBindViewHolderByDeadline(viewHolder, n2, n, Long.MAX_VALUE);
                    object = viewHolder.itemView.getLayoutParams();
                    if (object == null) {
                        object = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                        viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                    } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
                        object = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object);
                        viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                    } else {
                        object = (LayoutParams)object;
                    }
                    boolean bl = true;
                    ((LayoutParams)object).mInsetsDirty = true;
                    ((LayoutParams)object).mViewHolder = viewHolder;
                    if (viewHolder.itemView.getParent() != null) {
                        bl = false;
                    }
                    ((LayoutParams)object).mPendingInvalidate = bl;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Inconsistency detected. Invalid item position ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("(offset:");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(").state:");
                ((StringBuilder)object).append(RecyclerView.this.mState.getItemCount());
                throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
        }

        public void clear() {
            this.mAttachedScrap.clear();
            this.recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int n;
            int n2 = this.mCachedViews.size();
            for (n = 0; n < n2; ++n) {
                this.mCachedViews.get(n).clearOldPosition();
            }
            n2 = this.mAttachedScrap.size();
            for (n = 0; n < n2; ++n) {
                this.mAttachedScrap.get(n).clearOldPosition();
            }
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList != null) {
                n2 = arrayList.size();
                for (n = 0; n < n2; ++n) {
                    this.mChangedScrap.get(n).clearOldPosition();
                }
            }
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        public int convertPreLayoutPositionToPostLayout(int n) {
            if (n >= 0 && n < RecyclerView.this.mState.getItemCount()) {
                if (!RecyclerView.this.mState.isPreLayout()) {
                    return n;
                }
                return RecyclerView.this.mAdapterHelper.findPositionOffset(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid position ");
            stringBuilder.append(n);
            stringBuilder.append(". State item count is ");
            stringBuilder.append(RecyclerView.this.mState.getItemCount());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void dispatchViewRecycled(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            }
        }

        ViewHolder getChangedScrapViewForPosition(int n) {
            int n2;
            Object object = this.mChangedScrap;
            if (object != null && (n2 = ((ArrayList)object).size()) != 0) {
                for (int i = 0; i < n2; ++i) {
                    object = this.mChangedScrap.get(i);
                    if (((ViewHolder)object).wasReturnedFromScrap() || ((ViewHolder)object).getLayoutPosition() != n) continue;
                    ((ViewHolder)object).addFlags(32);
                    return object;
                }
                if (RecyclerView.this.mAdapter.hasStableIds() && (n = RecyclerView.this.mAdapterHelper.findPositionOffset(n)) > 0 && n < RecyclerView.this.mAdapter.getItemCount()) {
                    long l = RecyclerView.this.mAdapter.getItemId(n);
                    for (n = 0; n < n2; ++n) {
                        object = this.mChangedScrap.get(n);
                        if (((ViewHolder)object).wasReturnedFromScrap() || ((ViewHolder)object).getItemId() != l) continue;
                        ((ViewHolder)object).addFlags(32);
                        return object;
                    }
                }
                return null;
            }
            return null;
        }

        RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        ViewHolder getScrapOrCachedViewForId(long l, int n, boolean bl) {
            int n2;
            ViewHolder viewHolder;
            for (n2 = this.mAttachedScrap.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.getItemId() != l || viewHolder.wasReturnedFromScrap()) continue;
                if (n == viewHolder.getItemViewType()) {
                    viewHolder.addFlags(32);
                    if (viewHolder.isRemoved() && !RecyclerView.this.mState.isPreLayout()) {
                        viewHolder.setFlags(2, 14);
                    }
                    return viewHolder;
                }
                if (bl) continue;
                this.mAttachedScrap.remove(n2);
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                this.quickRecycleScrapView(viewHolder.itemView);
            }
            for (n2 = this.mCachedViews.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.getItemId() != l) continue;
                if (n == viewHolder.getItemViewType()) {
                    if (!bl) {
                        this.mCachedViews.remove(n2);
                    }
                    return viewHolder;
                }
                if (bl) continue;
                this.recycleCachedViewAt(n2);
                return null;
            }
            return null;
        }

        ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int n, boolean bl) {
            ViewHolder viewHolder;
            Object object;
            int n2;
            int n3 = this.mAttachedScrap.size();
            for (n2 = 0; n2 < n3; ++n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (!bl && (object = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n)) != null) {
                viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                RecyclerView.this.mChildHelper.unhide((View)object);
                n = RecyclerView.this.mChildHelper.indexOfChild((View)object);
                if (n != -1) {
                    RecyclerView.this.mChildHelper.detachViewFromParent(n);
                    this.scrapView((View)object);
                    viewHolder.addFlags(8224);
                    return viewHolder;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("layout index should not be -1 after unhiding a view:");
                ((StringBuilder)object).append(viewHolder);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            n3 = this.mCachedViews.size();
            for (n2 = 0; n2 < n3; ++n2) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.isInvalid() || viewHolder.getLayoutPosition() != n) continue;
                if (!bl) {
                    this.mCachedViews.remove(n2);
                }
                return viewHolder;
            }
            return null;
        }

        View getScrapViewAt(int n) {
            return this.mAttachedScrap.get((int)n).itemView;
        }

        public View getViewForPosition(int n) {
            return this.getViewForPosition(n, false);
        }

        View getViewForPosition(int n, boolean bl) {
            return this.tryGetViewHolderForPositionByDeadline((int)n, (boolean)bl, (long)Long.MAX_VALUE).itemView;
        }

        void markItemDecorInsetsDirty() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                LayoutParams layoutParams = (LayoutParams)this.mCachedViews.get((int)i).itemView.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.mInsetsDirty = true;
            }
        }

        void markKnownViewsInvalid() {
            if (RecyclerView.this.mAdapter != null && RecyclerView.this.mAdapter.hasStableIds()) {
                int n = this.mCachedViews.size();
                for (int i = 0; i < n; ++i) {
                    ViewHolder viewHolder = this.mCachedViews.get(i);
                    if (viewHolder == null) continue;
                    viewHolder.addFlags(6);
                    viewHolder.addChangePayload(null);
                }
            } else {
                this.recycleAndClearCachedViews();
            }
        }

        void offsetPositionRecordsForInsert(int n, int n2) {
            int n3 = this.mCachedViews.size();
            for (int i = 0; i < n3; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || viewHolder.mPosition < n) continue;
                viewHolder.offsetPosition(n2, true);
            }
        }

        void offsetPositionRecordsForMove(int n, int n2) {
            int n3;
            int n4;
            int n5;
            if (n < n2) {
                n4 = n;
                n5 = n2;
                n3 = -1;
            } else {
                n4 = n2;
                n5 = n;
                n3 = 1;
            }
            int n6 = this.mCachedViews.size();
            for (int i = 0; i < n6; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || viewHolder.mPosition < n4 || viewHolder.mPosition > n5) continue;
                if (viewHolder.mPosition == n) {
                    viewHolder.offsetPosition(n2 - n, false);
                    continue;
                }
                viewHolder.offsetPosition(n3, false);
            }
        }

        void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null) continue;
                if (viewHolder.mPosition >= n + n2) {
                    viewHolder.offsetPosition(-n2, bl);
                    continue;
                }
                if (viewHolder.mPosition < n) continue;
                viewHolder.addFlags(8);
                this.recycleCachedViewAt(i);
            }
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            this.clear();
            this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl);
        }

        void quickRecycleScrapView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).mScrapContainer = null;
            ((ViewHolder)object).mInChangeScrap = false;
            ((ViewHolder)object).clearReturnedFromScrapFlag();
            this.recycleViewHolderInternal((ViewHolder)object);
        }

        void recycleAndClearCachedViews() {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                this.recycleCachedViewAt(i);
            }
            this.mCachedViews.clear();
            if (ALLOW_THREAD_GAP_WORK) {
                RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        void recycleCachedViewAt(int n) {
            this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n), true);
            this.mCachedViews.remove(n);
        }

        public void recycleView(View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolder.isScrap()) {
                viewHolder.unScrap();
            } else if (viewHolder.wasReturnedFromScrap()) {
                viewHolder.clearReturnedFromScrapFlag();
            }
            this.recycleViewHolderInternal(viewHolder);
        }

        void recycleViewHolderInternal(ViewHolder viewHolder) {
            boolean bl;
            block14 : {
                block15 : {
                    block16 : {
                        int n;
                        int n2;
                        block18 : {
                            int n3;
                            int n4;
                            int n5;
                            block17 : {
                                boolean bl2 = viewHolder.isScrap();
                                bl = false;
                                if (bl2 || viewHolder.itemView.getParent() != null) break block14;
                                if (viewHolder.isTmpDetached()) break block15;
                                if (viewHolder.shouldIgnore()) break block16;
                                bl = viewHolder.doesTransientStatePreventRecycling();
                                n5 = RecyclerView.this.mAdapter != null && bl && RecyclerView.this.mAdapter.onFailedToRecycleView(viewHolder) ? 1 : 0;
                                n2 = 0;
                                n4 = 0;
                                n3 = 0;
                                if (n5 != 0) break block17;
                                n = n3;
                                if (!viewHolder.isRecyclable()) break block18;
                            }
                            n5 = n4;
                            if (this.mViewCacheMax > 0) {
                                n5 = n4;
                                if (!viewHolder.hasAnyOfTheFlags(526)) {
                                    n5 = n2 = this.mCachedViews.size();
                                    if (n2 >= this.mViewCacheMax) {
                                        n5 = n2;
                                        if (n2 > 0) {
                                            this.recycleCachedViewAt(0);
                                            n5 = n2 - 1;
                                        }
                                    }
                                    n = n2 = n5;
                                    if (ALLOW_THREAD_GAP_WORK) {
                                        n = n2;
                                        if (n5 > 0) {
                                            n = n2;
                                            if (!RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(viewHolder.mPosition)) {
                                                --n5;
                                                while (n5 >= 0 && RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(n2 = this.mCachedViews.get((int)n5).mPosition)) {
                                                    --n5;
                                                }
                                                n = n5 + 1;
                                            }
                                        }
                                    }
                                    this.mCachedViews.add(n, viewHolder);
                                    n5 = 1;
                                }
                            }
                            n2 = n5;
                            n = n3;
                            if (n5 == 0) {
                                this.addViewHolderToRecycledViewPool(viewHolder, true);
                                n = 1;
                                n2 = n5;
                            }
                        }
                        RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
                        if (n2 == 0 && n == 0 && bl) {
                            viewHolder.mOwnerRecyclerView = null;
                        }
                        return;
                    }
                    throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                stringBuilder.append(viewHolder);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
            stringBuilder.append(viewHolder.isScrap());
            stringBuilder.append(" isAttached:");
            if (viewHolder.itemView.getParent() != null) {
                bl = true;
            }
            stringBuilder.append(bl);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        void recycleViewInternal(View view) {
            this.recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(view));
        }

        void scrapView(View object) {
            if (!((ViewHolder)(object = RecyclerView.getChildViewHolderInt((View)object))).hasAnyOfTheFlags(12) && ((ViewHolder)object).isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder((ViewHolder)object)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList();
                }
                ((ViewHolder)object).setScrapContainer(this, true);
                this.mChangedScrap.add((ViewHolder)object);
            } else {
                if (((ViewHolder)object).isInvalid() && !((ViewHolder)object).isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
                    throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                }
                ((ViewHolder)object).setScrapContainer(this, false);
                this.mAttachedScrap.add((ViewHolder)object);
            }
        }

        void setAdapterPositionsAsUnknown() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null) continue;
                viewHolder.addFlags(512);
            }
        }

        void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            RecycledViewPool recycledViewPool2 = this.mRecyclerPool;
            if (recycledViewPool2 != null) {
                recycledViewPool2.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
            }
        }

        void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        public void setViewCacheSize(int n) {
            this.mRequestedCacheMax = n;
            this.updateViewCacheSize();
        }

        ViewHolder tryGetViewHolderForPositionByDeadline(int n, boolean bl, long l) {
            if (n >= 0 && n < RecyclerView.this.mState.getItemCount()) {
                int n2;
                int n3 = 0;
                Object object = null;
                boolean bl2 = RecyclerView.this.mState.isPreLayout();
                boolean bl3 = true;
                if (bl2) {
                    object = this.getChangedScrapViewForPosition(n);
                    n2 = object != null ? 1 : 0;
                    n3 = n2;
                }
                n2 = n3;
                Object object2 = object;
                if (object == null) {
                    object = this.getScrapOrHiddenOrCachedHolderForPosition(n, bl);
                    n2 = n3;
                    object2 = object;
                    if (object != null) {
                        if (!this.validateViewHolderForOffsetPosition((ViewHolder)object)) {
                            if (!bl) {
                                ((ViewHolder)object).addFlags(4);
                                if (((ViewHolder)object).isScrap()) {
                                    RecyclerView.this.removeDetachedView(((ViewHolder)object).itemView, false);
                                    ((ViewHolder)object).unScrap();
                                } else if (((ViewHolder)object).wasReturnedFromScrap()) {
                                    ((ViewHolder)object).clearReturnedFromScrapFlag();
                                }
                                this.recycleViewHolderInternal((ViewHolder)object);
                            }
                            object2 = null;
                            n2 = n3;
                        } else {
                            n2 = 1;
                            object2 = object;
                        }
                    }
                }
                if (object2 == null) {
                    int n4 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
                    if (n4 >= 0 && n4 < RecyclerView.this.mAdapter.getItemCount()) {
                        int n5 = RecyclerView.this.mAdapter.getItemViewType(n4);
                        n3 = n2;
                        object = object2;
                        if (RecyclerView.this.mAdapter.hasStableIds()) {
                            object2 = this.getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(n4), n5, bl);
                            n3 = n2;
                            object = object2;
                            if (object2 != null) {
                                ((ViewHolder)object2).mPosition = n4;
                                n3 = 1;
                                object = object2;
                            }
                        }
                        object2 = object;
                        if (object == null) {
                            Object object3 = this.mViewCacheExtension;
                            object2 = object;
                            if (object3 != null) {
                                object3 = ((ViewCacheExtension)object3).getViewForPositionAndType(this, n, n5);
                                object2 = object;
                                if (object3 != null) {
                                    object2 = RecyclerView.this.getChildViewHolder((View)object3);
                                    if (object2 != null) {
                                        if (((ViewHolder)object2).shouldIgnore()) {
                                            throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                        }
                                    } else {
                                        throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                    }
                                }
                            }
                        }
                        object = object2;
                        if (object2 == null) {
                            object = object2 = this.getRecycledViewPool().getRecycledView(n5);
                            if (object2 != null) {
                                ((ViewHolder)object2).resetInternal();
                                object = object2;
                                if (FORCE_INVALIDATE_DISPLAY_LIST) {
                                    this.invalidateDisplayListInt((ViewHolder)object2);
                                    object = object2;
                                }
                            }
                        }
                        if (object == null) {
                            long l2 = RecyclerView.this.getNanoTime();
                            if (l != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(n5, l2, l)) {
                                return null;
                            }
                            object2 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, n5);
                            if (ALLOW_THREAD_GAP_WORK && (object = RecyclerView.findNestedRecyclerView(((ViewHolder)object2).itemView)) != null) {
                                ((ViewHolder)object2).mNestedRecyclerView = new WeakReference<Object>(object);
                            }
                            long l3 = RecyclerView.this.getNanoTime();
                            this.mRecyclerPool.factorInCreateTime(n5, l3 - l2);
                            n2 = n3;
                        } else {
                            n2 = n3;
                            object2 = object;
                        }
                    } else {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Inconsistency detected. Invalid item position ");
                        ((StringBuilder)object2).append(n);
                        ((StringBuilder)object2).append("(offset:");
                        ((StringBuilder)object2).append(n4);
                        ((StringBuilder)object2).append(").state:");
                        ((StringBuilder)object2).append(RecyclerView.this.mState.getItemCount());
                        throw new IndexOutOfBoundsException(((StringBuilder)object2).toString());
                    }
                }
                if (n2 != 0 && !RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object2).hasAnyOfTheFlags(8192)) {
                    ((ViewHolder)object2).setFlags(0, 8192);
                    if (RecyclerView.this.mState.mRunSimpleAnimations) {
                        n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object2);
                        object = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (ViewHolder)object2, n3 | 4096, ((ViewHolder)object2).getUnmodifiedPayloads());
                        RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object2, (ItemAnimator.ItemHolderInfo)object);
                    }
                }
                bl = false;
                if (RecyclerView.this.mState.isPreLayout() && ((ViewHolder)object2).isBound()) {
                    ((ViewHolder)object2).mPreLayoutPosition = n;
                } else if (!((ViewHolder)object2).isBound() || ((ViewHolder)object2).needsUpdate() || ((ViewHolder)object2).isInvalid()) {
                    bl = this.tryBindViewHolderByDeadline((ViewHolder)object2, RecyclerView.this.mAdapterHelper.findPositionOffset(n), n, l);
                }
                object = ((ViewHolder)object2).itemView.getLayoutParams();
                if (object == null) {
                    object = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                    ((ViewHolder)object2).itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
                    object = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object);
                    ((ViewHolder)object2).itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else {
                    object = (LayoutParams)object;
                }
                ((LayoutParams)object).mViewHolder = object2;
                bl = n2 != 0 && bl ? bl3 : false;
                ((LayoutParams)object).mPendingInvalidate = bl;
                return object2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid item position ");
            stringBuilder.append(n);
            stringBuilder.append("(");
            stringBuilder.append(n);
            stringBuilder.append("). Item count:");
            stringBuilder.append(RecyclerView.this.mState.getItemCount());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        void updateViewCacheSize() {
            int n = RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0;
            this.mViewCacheMax = this.mRequestedCacheMax + n;
            for (n = this.mCachedViews.size() - 1; n >= 0 && this.mCachedViews.size() > this.mViewCacheMax; --n) {
                this.recycleCachedViewAt(n);
            }
        }

        boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition >= 0 && viewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()) {
                boolean bl = RecyclerView.this.mState.isPreLayout();
                boolean bl2 = false;
                if (!bl && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                    return false;
                }
                if (RecyclerView.this.mAdapter.hasStableIds()) {
                    if (viewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) {
                        bl2 = true;
                    }
                    return bl2;
                }
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
            stringBuilder.append(viewHolder);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void viewRangeUpdate(int n, int n2) {
            for (int i = this.mCachedViews.size() - 1; i >= 0; --i) {
                int n3;
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null || (n3 = viewHolder.getLayoutPosition()) < n || n3 >= n + n2) continue;
                viewHolder.addFlags(2);
                this.recycleCachedViewAt(i);
            }
        }
    }

    public static interface RecyclerListener {
        public void onViewRecycled(ViewHolder var1);
    }

    private class RecyclerViewDataObserver
    extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        @Override
        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            RecyclerView.this.mState.mStructureChanged = true;
            RecyclerView.this.setDataSetChangedAfterLayout();
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override
        public void onItemRangeChanged(int n, int n2, Object object) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(n, n2, object)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeInserted(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeMoved(int n, int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(n, n2, n3)) {
                this.triggerUpdateProcessor();
            }
        }

        @Override
        public void onItemRangeRemoved(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n, n2)) {
                this.triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() {
            if (POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.postOnAnimation(recyclerView.mUpdateChildViewsRunnable);
            } else {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mAdapterUpdateDuringMeasure = true;
                recyclerView.requestLayout();
            }
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        Parcelable mLayoutState;

        SavedState(Parcel parcel) {
            super(parcel);
            this.mLayoutState = parcel.readParcelable(LayoutManager.class.getClassLoader());
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        void copyFrom(SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

    }

    public static class SimpleOnItemTouchListener
    implements OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean bl) {
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private int mTargetPosition = -1;
        private View mTargetView;

        private void onAnimation(int n, int n2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
                this.stop();
            }
            this.mPendingInitialRun = false;
            View view = this.mTargetView;
            if (view != null) {
                if (this.getChildPosition(view) == this.mTargetPosition) {
                    this.onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    this.stop();
                } else {
                    Log.e(RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                this.onSeekTargetStep(n, n2, recyclerView.mState, this.mRecyclingAction);
                boolean bl = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (bl) {
                    if (this.mRunning) {
                        this.mPendingInitialRun = true;
                        recyclerView.mViewFlinger.postOnAnimation();
                    } else {
                        this.stop();
                    }
                }
            }
        }

        public View findViewByPosition(int n) {
            return this.mRecyclerView.mLayout.findViewByPosition(n);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        @Deprecated
        public void instantScrollToPosition(int n) {
            this.mRecyclerView.scrollToPosition(n);
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        protected void normalize(PointF pointF) {
            double d = Math.sqrt(pointF.x * pointF.x + pointF.y * pointF.y);
            pointF.x = (float)((double)pointF.x / d);
            pointF.y = (float)((double)pointF.y / d);
        }

        protected void onChildAttachedToWindow(View view) {
            if (this.getChildPosition(view) == this.getTargetPosition()) {
                this.mTargetView = view;
            }
        }

        protected abstract void onSeekTargetStep(int var1, int var2, State var3, Action var4);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(View var1, State var2, Action var3);

        public void setTargetPosition(int n) {
            this.mTargetPosition = n;
        }

        void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition != -1) {
                this.mRecyclerView.mState.mTargetPosition = this.mTargetPosition;
                this.mRunning = true;
                this.mPendingInitialRun = true;
                this.mTargetView = this.findViewByPosition(this.getTargetPosition());
                this.onStart();
                this.mRecyclerView.mViewFlinger.postOnAnimation();
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        protected final void stop() {
            if (!this.mRunning) {
                return;
            }
            this.onStop();
            this.mRecyclerView.mState.mTargetPosition = -1;
            this.mTargetView = null;
            this.mTargetPosition = -1;
            this.mPendingInitialRun = false;
            this.mRunning = false;
            this.mLayoutManager.onSmoothScrollerStopped(this);
            this.mLayoutManager = null;
            this.mRecyclerView = null;
        }

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean mChanged = false;
            private int mConsecutiveUpdates = 0;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition = -1;

            public Action(int n, int n2) {
                this(n, n2, Integer.MIN_VALUE, null);
            }

            public Action(int n, int n2, int n3) {
                this(n, n2, n3, null);
            }

            public Action(int n, int n2, int n3, Interpolator interpolator2) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator2;
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration >= 1) {
                    return;
                }
                throw new IllegalStateException("Scroll duration must be a positive number");
            }

            public int getDuration() {
                return this.mDuration;
            }

            public int getDx() {
                return this.mDx;
            }

            public int getDy() {
                return this.mDy;
            }

            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            boolean hasJumpTarget() {
                boolean bl = this.mJumpToPosition >= 0;
                return bl;
            }

            public void jumpTo(int n) {
                this.mJumpToPosition = n;
            }

            void runIfNecessary(RecyclerView recyclerView) {
                if (this.mJumpToPosition >= 0) {
                    int n = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(n);
                    this.mChanged = false;
                    return;
                }
                if (this.mChanged) {
                    this.validate();
                    if (this.mInterpolator == null) {
                        if (this.mDuration == Integer.MIN_VALUE) {
                            recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                        } else {
                            recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                        }
                    } else {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                    }
                    ++this.mConsecutiveUpdates;
                    if (this.mConsecutiveUpdates > 10) {
                        Log.e(RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.mChanged = false;
                } else {
                    this.mConsecutiveUpdates = 0;
                }
            }

            public void setDuration(int n) {
                this.mChanged = true;
                this.mDuration = n;
            }

            public void setDx(int n) {
                this.mChanged = true;
                this.mDx = n;
            }

            public void setDy(int n) {
                this.mChanged = true;
                this.mDy = n;
            }

            public void setInterpolator(Interpolator interpolator2) {
                this.mChanged = true;
                this.mInterpolator = interpolator2;
            }

            public void update(int n, int n2, int n3, Interpolator interpolator2) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator2;
                this.mChanged = true;
            }
        }

        public static interface ScrollVectorProvider {
            public PointF computeScrollVectorForPosition(int var1);
        }

    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        private int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        void assertLayoutStep(int n) {
            if ((this.mLayoutStep & n) != 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Layout state should be one of ");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(" but it is ");
            stringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
            throw new IllegalStateException(stringBuilder.toString());
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public <T> T get(int n) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return null;
            }
            return (T)sparseArray.get(n);
        }

        public int getItemCount() {
            int n = this.mInPreLayout ? this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout : this.mItemCount;
            return n;
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            boolean bl = this.mTargetPosition != -1;
            return bl;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        void prepareForNestedPrefetch(Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mStructureChanged = false;
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public void put(int n, Object object) {
            if (this.mData == null) {
                this.mData = new SparseArray();
            }
            this.mData.put(n, object);
        }

        public void remove(int n) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return;
            }
            sparseArray.remove(n);
        }

        State reset() {
            this.mTargetPosition = -1;
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray != null) {
                sparseArray.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("State{mTargetPosition=");
            stringBuilder.append(this.mTargetPosition);
            stringBuilder.append(", mData=");
            stringBuilder.append(this.mData);
            stringBuilder.append(", mItemCount=");
            stringBuilder.append(this.mItemCount);
            stringBuilder.append(", mPreviousLayoutItemCount=");
            stringBuilder.append(this.mPreviousLayoutItemCount);
            stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            stringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
            stringBuilder.append(", mStructureChanged=");
            stringBuilder.append(this.mStructureChanged);
            stringBuilder.append(", mInPreLayout=");
            stringBuilder.append(this.mInPreLayout);
            stringBuilder.append(", mRunSimpleAnimations=");
            stringBuilder.append(this.mRunSimpleAnimations);
            stringBuilder.append(", mRunPredictiveAnimations=");
            stringBuilder.append(this.mRunPredictiveAnimations);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface LayoutState {
        }

    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler var1, int var2, int var3);
    }

    class ViewFlinger
    implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        private boolean mReSchedulePostAnimationCallback = false;
        private OverScroller mScroller;

        ViewFlinger() {
            this.mScroller = new OverScroller(RecyclerView.this.getContext(), sQuinticInterpolator);
        }

        private int computeScrollDuration(int n, int n2, int n3, int n4) {
            int n5;
            int n6 = Math.abs(n);
            boolean bl = n6 > (n5 = Math.abs(n2));
            n3 = (int)Math.sqrt(n3 * n3 + n4 * n4);
            n2 = (int)Math.sqrt(n * n + n2 * n2);
            RecyclerView recyclerView = RecyclerView.this;
            n = bl ? recyclerView.getWidth() : recyclerView.getHeight();
            n4 = n / 2;
            float f = Math.min(1.0f, (float)n2 * 1.0f / (float)n);
            float f2 = n4;
            float f3 = n4;
            f = this.distanceInfluenceForSnapDuration(f);
            if (n3 > 0) {
                n = Math.round(Math.abs((f2 + f3 * f) / (float)n3) * 1000.0f) * 4;
            } else {
                n2 = bl ? n6 : n5;
                n = (int)(((float)n2 / (float)n + 1.0f) * 300.0f);
            }
            return Math.min(n, 2000);
        }

        private void disableRunOnAnimationRequests() {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private float distanceInfluenceForSnapDuration(float f) {
            return (float)Math.sin((float)((double)(f - 0.5f) * 0.4712389167638204));
        }

        private void enableRunOnAnimationRequests() {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                this.postOnAnimation();
            }
        }

        public void fling(int n, int n2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, n, n2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.postOnAnimation();
        }

        void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                RecyclerView.this.removeCallbacks(this);
                RecyclerView.this.postOnAnimation(this);
            }
        }

        @Override
        public void run() {
            SmoothScroller smoothScroller;
            block32 : {
                int n;
                OverScroller overScroller;
                int n2;
                int n3;
                block34 : {
                    int n4;
                    int n5;
                    int n6;
                    block33 : {
                        if (RecyclerView.this.mLayout == null) {
                            this.stop();
                            return;
                        }
                        this.disableRunOnAnimationRequests();
                        RecyclerView.this.consumePendingUpdateOperations();
                        overScroller = this.mScroller;
                        smoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
                        if (!overScroller.computeScrollOffset()) break block32;
                        int n7 = overScroller.getCurrX();
                        int n8 = overScroller.getCurrY();
                        n = n7 - this.mLastFlingX;
                        n2 = n8 - this.mLastFlingY;
                        int n9 = 0;
                        n4 = 0;
                        int n10 = 0;
                        n3 = 0;
                        this.mLastFlingX = n7;
                        this.mLastFlingY = n8;
                        int n11 = 0;
                        n5 = 0;
                        int n12 = 0;
                        n6 = 0;
                        if (RecyclerView.this.mAdapter != null) {
                            RecyclerView.this.eatRequestLayout();
                            RecyclerView.this.onEnterLayoutOrScroll();
                            Trace.beginSection(RecyclerView.TRACE_SCROLL_TAG);
                            if (n != 0) {
                                n4 = RecyclerView.this.mLayout.scrollHorizontallyBy(n, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                                n5 = n - n4;
                            }
                            if (n2 != 0) {
                                n3 = RecyclerView.this.mLayout.scrollVerticallyBy(n2, RecyclerView.this.mRecycler, RecyclerView.this.mState);
                                n6 = n2 - n3;
                            }
                            Trace.endSection();
                            RecyclerView.this.repositionShadowingViews();
                            RecyclerView.this.onExitLayoutOrScroll();
                            RecyclerView.this.resumeRequestLayout(false);
                            n9 = n4;
                            n10 = n3;
                            n11 = n5;
                            n12 = n6;
                            if (smoothScroller != null) {
                                n9 = n4;
                                n10 = n3;
                                n11 = n5;
                                n12 = n6;
                                if (!smoothScroller.isPendingInitialRun()) {
                                    n9 = n4;
                                    n10 = n3;
                                    n11 = n5;
                                    n12 = n6;
                                    if (smoothScroller.isRunning()) {
                                        n9 = RecyclerView.this.mState.getItemCount();
                                        if (n9 == 0) {
                                            smoothScroller.stop();
                                            n9 = n4;
                                            n10 = n3;
                                            n11 = n5;
                                            n12 = n6;
                                        } else if (smoothScroller.getTargetPosition() >= n9) {
                                            smoothScroller.setTargetPosition(n9 - 1);
                                            smoothScroller.onAnimation(n - n5, n2 - n6);
                                            n9 = n4;
                                            n10 = n3;
                                            n11 = n5;
                                            n12 = n6;
                                        } else {
                                            smoothScroller.onAnimation(n - n5, n2 - n6);
                                            n12 = n6;
                                            n11 = n5;
                                            n10 = n3;
                                            n9 = n4;
                                        }
                                    }
                                }
                            }
                        }
                        if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                            RecyclerView.this.invalidate();
                        }
                        if (RecyclerView.this.getOverScrollMode() != 2) {
                            RecyclerView.this.considerReleasingGlowsOnScroll(n, n2);
                        }
                        if (n11 != 0 || n12 != 0) {
                            n5 = (int)overScroller.getCurrVelocity();
                            n3 = 0;
                            if (n11 != n7) {
                                n4 = n11 < 0 ? -n5 : (n11 > 0 ? n5 : 0);
                                n3 = n4;
                            }
                            n4 = 0;
                            if (n12 != n8) {
                                n4 = n12 < 0 ? -n5 : (n12 > 0 ? n5 : 0);
                            }
                            if (RecyclerView.this.getOverScrollMode() != 2) {
                                RecyclerView.this.absorbGlows(n3, n4);
                            }
                            if (!(n3 == 0 && n11 != n7 && overScroller.getFinalX() != 0 || n4 == 0 && n12 != n8 && overScroller.getFinalY() != 0)) {
                                overScroller.abortAnimation();
                            }
                        }
                        if (n9 != 0 || n10 != 0) {
                            RecyclerView.this.dispatchOnScrolled(n9, n10);
                        }
                        if (!RecyclerView.this.awakenScrollBars()) {
                            RecyclerView.this.invalidate();
                        }
                        n6 = 1;
                        n4 = n2 != 0 && RecyclerView.this.mLayout.canScrollVertically() && n10 == n2 ? 1 : 0;
                        n5 = n != 0 && RecyclerView.this.mLayout.canScrollHorizontally() && n9 == n ? 1 : 0;
                        if (n != 0) break block33;
                        n3 = n6;
                        if (n2 == 0) break block34;
                    }
                    n3 = n6;
                    if (n5 == 0) {
                        n3 = n4 != 0 ? n6 : 0;
                    }
                }
                if (!overScroller.isFinished() && n3 != 0) {
                    this.postOnAnimation();
                    if (RecyclerView.this.mGapWorker != null) {
                        RecyclerView.this.mGapWorker.postFromTraversal(RecyclerView.this, n, n2);
                    }
                } else {
                    RecyclerView.this.setScrollState(0);
                    if (ALLOW_THREAD_GAP_WORK) {
                        RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    }
                }
            }
            if (smoothScroller != null) {
                if (smoothScroller.isPendingInitialRun()) {
                    smoothScroller.onAnimation(0, 0);
                }
                if (!this.mReSchedulePostAnimationCallback) {
                    smoothScroller.stop();
                }
            }
            this.enableRunOnAnimationRequests();
        }

        public void smoothScrollBy(int n, int n2) {
            this.smoothScrollBy(n, n2, 0, 0);
        }

        public void smoothScrollBy(int n, int n2, int n3) {
            this.smoothScrollBy(n, n2, n3, sQuinticInterpolator);
        }

        public void smoothScrollBy(int n, int n2, int n3, int n4) {
            this.smoothScrollBy(n, n2, this.computeScrollDuration(n, n2, n3, n4));
        }

        public void smoothScrollBy(int n, int n2, int n3, Interpolator interpolator2) {
            if (this.mInterpolator != interpolator2) {
                this.mInterpolator = interpolator2;
                this.mScroller = new OverScroller(RecyclerView.this.getContext(), interpolator2);
            }
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, n, n2, n3);
            this.postOnAnimation();
        }

        public void smoothScrollBy(int n, int n2, Interpolator interpolator2) {
            int n3 = this.computeScrollDuration(n, n2, 0, 0);
            if (interpolator2 == null) {
                interpolator2 = sQuinticInterpolator;
            }
            this.smoothScrollBy(n, n2, n3, interpolator2);
        }

        public void stop() {
            RecyclerView.this.removeCallbacks(this);
            this.mScroller.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        private int mFlags;
        private boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1L;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        @VisibleForTesting
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        private Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view != null) {
                this.itemView = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList<Object>();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        private boolean doesTransientStatePreventRecycling() {
            boolean bl = (this.mFlags & 16) == 0 && this.itemView.hasTransientState();
            return bl;
        }

        private void onEnteredHiddenState(RecyclerView recyclerView) {
            this.mWasImportantForAccessibilityBeforeHidden = this.itemView.getImportantForAccessibility();
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        private void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        private boolean shouldBeKeptAsChild() {
            boolean bl = (this.mFlags & 16) != 0;
            return bl;
        }

        void addChangePayload(Object object) {
            if (object == null) {
                this.addFlags(1024);
            } else if ((1024 & this.mFlags) == 0) {
                this.createPayloadsIfNeeded();
                this.mPayloads.add(object);
            }
        }

        void addFlags(int n) {
            this.mFlags |= n;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        void flagRemovedAndOffsetPosition(int n, int n2, boolean bl) {
            this.addFlags(8);
            this.offsetPosition(n2, bl);
            this.mPosition = n;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.getAdapterPositionFor(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            int n;
            int n2 = n = this.mPreLayoutPosition;
            if (n == -1) {
                n2 = this.mPosition;
            }
            return n2;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            int n;
            int n2 = n = this.mPreLayoutPosition;
            if (n == -1) {
                n2 = this.mPosition;
            }
            return n2;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) == 0) {
                List<Object> list = this.mPayloads;
                if (list != null && list.size() != 0) {
                    return this.mUnmodifiedPayloads;
                }
                return FULLUPDATE_PAYLOADS;
            }
            return FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int n) {
            boolean bl = (this.mFlags & n) != 0;
            return bl;
        }

        boolean isAdapterPositionUnknown() {
            boolean bl = (this.mFlags & 512) != 0 || this.isInvalid();
            return bl;
        }

        boolean isBound() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        boolean isInvalid() {
            boolean bl = (this.mFlags & 4) != 0;
            return bl;
        }

        public final boolean isRecyclable() {
            boolean bl = (this.mFlags & 16) == 0 && !this.itemView.hasTransientState();
            return bl;
        }

        boolean isRemoved() {
            boolean bl = (this.mFlags & 8) != 0;
            return bl;
        }

        boolean isScrap() {
            boolean bl = this.mScrapContainer != null;
            return bl;
        }

        boolean isTmpDetached() {
            boolean bl = (this.mFlags & 256) != 0;
            return bl;
        }

        boolean isUpdated() {
            boolean bl = (this.mFlags & 2) != 0;
            return bl;
        }

        boolean needsUpdate() {
            boolean bl = (this.mFlags & 2) != 0;
            return bl;
        }

        void offsetPosition(int n, boolean bl) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (bl) {
                this.mPreLayoutPosition += n;
            }
            this.mPosition += n;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            this.clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        void setFlags(int n, int n2) {
            this.mFlags = this.mFlags & n2 | n & n2;
        }

        public final void setIsRecyclable(boolean bl) {
            int n = this.mIsRecyclableCount;
            n = bl ? --n : ++n;
            n = this.mIsRecyclableCount = n;
            if (n < 0) {
                this.mIsRecyclableCount = 0;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                stringBuilder.append(this);
                Log.e("View", stringBuilder.toString());
            } else if (!bl && n == 1) {
                this.mFlags |= 16;
            } else if (bl && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        void setScrapContainer(Recycler recycler, boolean bl) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = bl;
        }

        boolean shouldIgnore() {
            boolean bl = (this.mFlags & 128) != 0;
            return bl;
        }

        void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("ViewHolder{");
            ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
            ((StringBuilder)charSequence).append(" position=");
            ((StringBuilder)charSequence).append(this.mPosition);
            ((StringBuilder)charSequence).append(" id=");
            ((StringBuilder)charSequence).append(this.mItemId);
            ((StringBuilder)charSequence).append(", oldPos=");
            ((StringBuilder)charSequence).append(this.mOldPosition);
            ((StringBuilder)charSequence).append(", pLpos:");
            ((StringBuilder)charSequence).append(this.mPreLayoutPosition);
            StringBuilder stringBuilder = new StringBuilder(((StringBuilder)charSequence).toString());
            if (this.isScrap()) {
                stringBuilder.append(" scrap ");
                charSequence = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder.append((String)charSequence);
            }
            if (this.isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!this.isBound()) {
                stringBuilder.append(" unbound");
            }
            if (this.needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (this.isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (this.shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (this.isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.isRecyclable()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" not recyclable(");
                ((StringBuilder)charSequence).append(this.mIsRecyclableCount);
                ((StringBuilder)charSequence).append(")");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            if (this.isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            boolean bl = (this.mFlags & 32) != 0;
            return bl;
        }
    }

}

