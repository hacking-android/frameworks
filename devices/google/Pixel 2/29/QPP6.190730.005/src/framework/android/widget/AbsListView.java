/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.Trace;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.StateSet;
import android.view.AbsSavedState;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.FastScroller;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.OverScroller;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbsListView
extends AdapterView<ListAdapter>
implements TextWatcher,
ViewTreeObserver.OnGlobalLayoutListener,
Filter.FilterListener,
ViewTreeObserver.OnTouchModeChangeListener,
RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    public static final int CHOICE_MODE_MULTIPLE = 2;
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final int INVALID_POINTER = -1;
    static final int LAYOUT_FORCE_BOTTOM = 3;
    static final int LAYOUT_FORCE_TOP = 1;
    static final int LAYOUT_MOVE_SELECTION = 6;
    static final int LAYOUT_NORMAL = 0;
    static final int LAYOUT_SET_SELECTION = 2;
    static final int LAYOUT_SPECIFIC = 4;
    static final int LAYOUT_SYNC = 5;
    static final int OVERSCROLL_LIMIT_DIVISOR = 3;
    private static final boolean PROFILE_FLINGING = false;
    private static final boolean PROFILE_SCROLLING = false;
    private static final String TAG = "AbsListView";
    static final int TOUCH_MODE_DONE_WAITING = 2;
    static final int TOUCH_MODE_DOWN = 0;
    static final int TOUCH_MODE_FLING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    static final int TOUCH_MODE_OVERFLING = 6;
    static final int TOUCH_MODE_OVERSCROLL = 5;
    static final int TOUCH_MODE_REST = -1;
    static final int TOUCH_MODE_SCROLL = 3;
    static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;
    public static final int TRANSCRIPT_MODE_DISABLED = 0;
    public static final int TRANSCRIPT_MODE_NORMAL = 1;
    static final Interpolator sLinearInterpolator = new LinearInterpolator();
    private ListItemAccessibilityDelegate mAccessibilityDelegate;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mActivePointerId = -1;
    @UnsupportedAppUsage
    ListAdapter mAdapter;
    boolean mAdapterHasStableIds;
    private int mCacheColorHint;
    boolean mCachingActive;
    boolean mCachingStarted;
    SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    int mCheckedItemCount;
    @UnsupportedAppUsage
    ActionMode mChoiceActionMode;
    int mChoiceMode = 0;
    private Runnable mClearScrollingCache;
    @UnsupportedAppUsage
    private ContextMenu.ContextMenuInfo mContextMenuInfo = null;
    @UnsupportedAppUsage
    AdapterDataSetObserver mDataSetObserver;
    private InputConnection mDefInputConnection;
    private boolean mDeferNotifyDataSetChanged = false;
    private float mDensityScale;
    private int mDirection = 0;
    boolean mDrawSelectorOnTop = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768444L)
    private EdgeEffect mEdgeGlowBottom = new EdgeEffect(this.mContext);
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769408L)
    private EdgeEffect mEdgeGlowTop = new EdgeEffect(this.mContext);
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768941L)
    private FastScroller mFastScroll;
    boolean mFastScrollAlwaysVisible;
    boolean mFastScrollEnabled;
    private int mFastScrollStyle;
    private boolean mFiltered;
    private int mFirstPositionDistanceGuess;
    private boolean mFlingProfilingStarted = false;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private FlingRunnable mFlingRunnable;
    private StrictMode.Span mFlingStrictSpan = null;
    private boolean mForceTranscriptScroll;
    private boolean mGlobalLayoutListenerAddedFilter;
    private boolean mHasPerformedLongPress;
    @UnsupportedAppUsage
    private boolean mIsChildViewEnabled;
    private boolean mIsDetaching;
    final boolean[] mIsScrap = new boolean[1];
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastHandledItemCount;
    private int mLastPositionDistanceGuess;
    private int mLastScrollState = 0;
    private int mLastTouchMode = -1;
    int mLastY;
    @UnsupportedAppUsage
    int mLayoutMode = 0;
    Rect mListPadding = new Rect();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124051740L)
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    int mMotionCorrection;
    @UnsupportedAppUsage
    int mMotionPosition;
    int mMotionViewNewTop;
    int mMotionViewOriginalTop;
    int mMotionX;
    @UnsupportedAppUsage
    int mMotionY;
    MultiChoiceModeWrapper mMultiChoiceModeCallback;
    private int mNestedYOffset = 0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769353L)
    private OnScrollListener mOnScrollListener;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769379L)
    int mOverflingDistance;
    @UnsupportedAppUsage
    int mOverscrollDistance;
    int mOverscrollMax;
    private final Thread mOwnerThread;
    private CheckForKeyLongPress mPendingCheckForKeyLongPress;
    @UnsupportedAppUsage
    private CheckForLongPress mPendingCheckForLongPress;
    @UnsupportedAppUsage
    private CheckForTap mPendingCheckForTap;
    private SavedState mPendingSync;
    private PerformClick mPerformClick;
    @UnsupportedAppUsage
    PopupWindow mPopup;
    private boolean mPopupHidden;
    Runnable mPositionScrollAfterLayout;
    @UnsupportedAppUsage
    AbsPositionScroller mPositionScroller;
    private InputConnectionWrapper mPublicInputConnection;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769398L)
    final RecycleBin mRecycler = new RecycleBin();
    private RemoteViewsAdapter mRemoteAdapter;
    int mResurrectToPosition = -1;
    private final int[] mScrollConsumed = new int[2];
    View mScrollDown;
    private final int[] mScrollOffset = new int[2];
    private boolean mScrollProfilingStarted = false;
    private StrictMode.Span mScrollStrictSpan = null;
    View mScrollUp;
    boolean mScrollingCacheEnabled;
    int mSelectedTop = 0;
    @UnsupportedAppUsage
    int mSelectionBottomPadding = 0;
    int mSelectionLeftPadding = 0;
    int mSelectionRightPadding = 0;
    @UnsupportedAppUsage
    int mSelectionTopPadding = 0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    Drawable mSelector;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mSelectorPosition = -1;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Rect mSelectorRect = new Rect();
    private int[] mSelectorState;
    private boolean mSmoothScrollbarEnabled = true;
    boolean mStackFromBottom;
    EditText mTextFilter;
    private boolean mTextFilterEnabled;
    private final float[] mTmpPoint = new float[2];
    private Rect mTouchFrame;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769413L)
    int mTouchMode = -1;
    private Runnable mTouchModeReset;
    @UnsupportedAppUsage
    private int mTouchSlop;
    private int mTranscriptMode;
    private float mVelocityScale = 1.0f;
    @UnsupportedAppUsage
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;
    int mWidthMeasureSpec = 0;

    public AbsListView(Context object) {
        super((Context)object);
        this.initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        this.setVerticalScrollBarEnabled(true);
        object = ((Context)object).obtainStyledAttributes(R.styleable.View);
        this.initializeScrollbarsInternal((TypedArray)object);
        ((TypedArray)object).recycle();
    }

    public AbsListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842858);
    }

    public AbsListView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AbsListView(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        this.initAbsListView();
        this.mOwnerThread = Thread.currentThread();
        TypedArray typedArray = context.obtainStyledAttributes((AttributeSet)object, R.styleable.AbsListView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.AbsListView, (AttributeSet)object, typedArray, n, n2);
        object = typedArray.getDrawable(0);
        if (object != null) {
            this.setSelector((Drawable)object);
        }
        this.mDrawSelectorOnTop = typedArray.getBoolean(1, false);
        this.setStackFromBottom(typedArray.getBoolean(2, false));
        this.setScrollingCacheEnabled(typedArray.getBoolean(3, true));
        this.setTextFilterEnabled(typedArray.getBoolean(4, false));
        this.setTranscriptMode(typedArray.getInt(5, 0));
        this.setCacheColorHint(typedArray.getColor(6, 0));
        this.setSmoothScrollbarEnabled(typedArray.getBoolean(9, true));
        this.setChoiceMode(typedArray.getInt(7, 0));
        this.setFastScrollEnabled(typedArray.getBoolean(8, false));
        this.setFastScrollStyle(typedArray.getResourceId(11, 0));
        this.setFastScrollAlwaysVisible(typedArray.getBoolean(10, false));
        typedArray.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            this.setRevealOnFocusHint(false);
        }
    }

    private boolean acceptFilter() {
        boolean bl = this.mTextFilterEnabled && this.getAdapter() instanceof Filterable && ((Filterable)this.getAdapter()).getFilter() != null;
        return bl;
    }

    private void addAccessibilityActionIfEnabled(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl, AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        if (bl) {
            accessibilityNodeInfo.addAction(accessibilityAction);
        }
    }

    @UnsupportedAppUsage
    private boolean canScrollDown() {
        int n = this.getChildCount();
        int n2 = this.mFirstPosition;
        int n3 = this.mItemCount;
        boolean bl = false;
        boolean bl2 = n2 + n < n3;
        boolean bl3 = bl2;
        if (!bl2) {
            bl3 = bl2;
            if (n > 0) {
                bl2 = bl;
                if (this.getChildAt(n - 1).getBottom() > this.mBottom - this.mListPadding.bottom) {
                    bl2 = true;
                }
                bl3 = bl2;
            }
        }
        return bl3;
    }

    @UnsupportedAppUsage
    private boolean canScrollUp() {
        int n = this.mFirstPosition;
        boolean bl = true;
        boolean bl2 = n > 0;
        boolean bl3 = bl2;
        if (!bl2) {
            bl3 = bl2;
            if (this.getChildCount() > 0) {
                bl2 = this.getChildAt(0).getTop() < this.mListPadding.top ? bl : false;
                bl3 = bl2;
            }
        }
        return bl3;
    }

    private void clearScrollingCache() {
        if (!this.isHardwareAccelerated()) {
            if (this.mClearScrollingCache == null) {
                this.mClearScrollingCache = new Runnable(){

                    @Override
                    public void run() {
                        if (AbsListView.this.mCachingStarted) {
                            AbsListView absListView = AbsListView.this;
                            absListView.mCachingActive = false;
                            absListView.mCachingStarted = false;
                            absListView.setChildrenDrawnWithCacheEnabled(false);
                            if ((AbsListView.this.mPersistentDrawingCache & 2) == 0) {
                                AbsListView.this.setChildrenDrawingCacheEnabled(false);
                            }
                            if (!AbsListView.this.isAlwaysDrawnWithCacheEnabled()) {
                                AbsListView.this.invalidate();
                            }
                        }
                    }
                };
            }
            this.post(this.mClearScrollingCache);
        }
    }

    private boolean contentFits() {
        int n = this.getChildCount();
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        if (n != this.mItemCount) {
            return false;
        }
        if (this.getChildAt(0).getTop() < this.mListPadding.top || this.getChildAt(n - 1).getBottom() > this.getHeight() - this.mListPadding.bottom) {
            bl = false;
        }
        return bl;
    }

    private void createScrollingCache() {
        if (this.mScrollingCacheEnabled && !this.mCachingStarted && !this.isHardwareAccelerated()) {
            this.setChildrenDrawnWithCacheEnabled(true);
            this.setChildrenDrawingCacheEnabled(true);
            this.mCachingActive = true;
            this.mCachingStarted = true;
        }
    }

    private void createTextFilter(boolean bl) {
        if (this.mPopup == null) {
            PopupWindow popupWindow = new PopupWindow(this.getContext());
            popupWindow.setFocusable(false);
            popupWindow.setTouchable(false);
            popupWindow.setInputMethodMode(2);
            popupWindow.setContentView(this.getTextFilterInput());
            popupWindow.setWidth(-2);
            popupWindow.setHeight(-2);
            popupWindow.setBackgroundDrawable(null);
            this.mPopup = popupWindow;
            this.getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = true;
        }
        if (bl) {
            this.mPopup.setAnimationStyle(16974601);
        } else {
            this.mPopup.setAnimationStyle(16974602);
        }
    }

    private void dismissPopup() {
        PopupWindow popupWindow = this.mPopup;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    private void drawSelector(Canvas canvas) {
        if (this.shouldDrawSelector()) {
            Drawable drawable2 = this.mSelector;
            drawable2.setBounds(this.mSelectorRect);
            drawable2.draw(canvas);
        }
    }

    private void finishGlows() {
        if (this.shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.finish();
            this.mEdgeGlowBottom.finish();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int getDistance(Rect rect, Rect rect2, int n) {
        int n2;
        int n3;
        int n4;
        if (n != 1 && n != 2) {
            if (n != 17) {
                if (n != 33) {
                    if (n != 66) {
                        if (n != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
                        n = rect.left + rect.width() / 2;
                        n3 = rect.bottom;
                        n4 = rect2.left + rect2.width() / 2;
                        n2 = rect2.top;
                    } else {
                        n = rect.right;
                        n3 = rect.top + rect.height() / 2;
                        n4 = rect2.left;
                        n2 = rect2.top + rect2.height() / 2;
                    }
                } else {
                    n = rect.left + rect.width() / 2;
                    n3 = rect.top;
                    n4 = rect2.left + rect2.width() / 2;
                    n2 = rect2.bottom;
                }
            } else {
                n = rect.left;
                n3 = rect.top + rect.height() / 2;
                n4 = rect2.right;
                n2 = rect2.top + rect2.height() / 2;
            }
        } else {
            n = rect.right + rect.width() / 2;
            n3 = rect.top + rect.height() / 2;
            n4 = rect2.left + rect2.width() / 2;
            n2 = rect2.top + rect2.height() / 2;
        }
        n = n4 - n;
        return (n2 -= n3) * n2 + n * n;
    }

    private int[] getDrawableStateForSelector() {
        int n;
        if (this.mIsChildViewEnabled) {
            return super.getDrawableState();
        }
        int n2 = ENABLED_STATE_SET[0];
        int[] arrn = this.onCreateDrawableState(1);
        int n3 = -1;
        int n4 = arrn.length - 1;
        do {
            n = n3;
            if (n4 < 0) break;
            if (arrn[n4] == n2) {
                n = n4;
                break;
            }
            --n4;
        } while (true);
        if (n >= 0) {
            System.arraycopy(arrn, n + 1, arrn, n, arrn.length - n - 1);
        }
        return arrn;
    }

    private EditText getTextFilterInput() {
        if (this.mTextFilter == null) {
            this.mTextFilter = (EditText)LayoutInflater.from(this.getContext()).inflate(17367329, null);
            this.mTextFilter.setRawInputType(177);
            this.mTextFilter.setImeOptions(268435456);
            this.mTextFilter.addTextChangedListener(this);
        }
        return this.mTextFilter;
    }

    private void initAbsListView() {
        this.setClickable(true);
        this.setFocusableInTouchMode(true);
        this.setWillNotDraw(false);
        this.setAlwaysDrawnWithCacheEnabled(false);
        this.setScrollingCacheEnabled(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mDensityScale = this.getContext().getResources().getDisplayMetrics().density;
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void invalidateBottomGlow() {
        int n;
        int n2;
        if (!this.shouldDisplayEdgeEffects()) {
            return;
        }
        boolean bl = this.getClipToPadding();
        int n3 = n = this.getHeight();
        if (bl) {
            n3 = n - this.mPaddingBottom;
        }
        n = bl ? this.mPaddingLeft : 0;
        int n4 = n2 = this.getWidth();
        if (bl) {
            n4 = n2 - this.mPaddingRight;
        }
        this.invalidate(n, n3 - this.mEdgeGlowBottom.getMaxHeight(), n4, n3);
    }

    private void invalidateTopGlow() {
        int n;
        if (!this.shouldDisplayEdgeEffects()) {
            return;
        }
        boolean bl = this.getClipToPadding();
        int n2 = 0;
        int n3 = bl ? this.mPaddingTop : 0;
        if (bl) {
            n2 = this.mPaddingLeft;
        }
        int n4 = n = this.getWidth();
        if (bl) {
            n4 = n - this.mPaddingRight;
        }
        this.invalidate(n2, n3, n4, this.mEdgeGlowTop.getMaxHeight() + n3);
    }

    private boolean isItemClickable(View view) {
        return view.hasExplicitFocusable() ^ true;
    }

    private boolean isOwnerThread() {
        boolean bl = this.mOwnerThread == Thread.currentThread();
        return bl;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mMotionX = (int)motionEvent.getX(n);
            this.mMotionY = (int)motionEvent.getY(n);
            this.mMotionCorrection = 0;
            this.mActivePointerId = motionEvent.getPointerId(n);
        }
    }

    private void onTouchCancel() {
        int n = this.mTouchMode;
        if (n != 5) {
            if (n != 6) {
                this.mTouchMode = -1;
                this.setPressed(false);
                View view = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
                if (view != null) {
                    view.setPressed(false);
                }
                this.clearScrollingCache();
                this.removeCallbacks(this.mPendingCheckForLongPress);
                this.recycleVelocityTracker();
            }
        } else {
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            this.mFlingRunnable.startSpringback();
        }
        if (this.shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        this.mActivePointerId = -1;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        this.mHasPerformedLongPress = false;
        this.mActivePointerId = motionEvent.getPointerId(0);
        this.hideSelector();
        if (this.mTouchMode == 6) {
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
            this.mTouchMode = 5;
            this.mMotionX = (int)motionEvent.getX();
            this.mLastY = this.mMotionY = (int)motionEvent.getY();
            this.mMotionCorrection = 0;
            this.mDirection = 0;
        } else {
            int n;
            int n2 = (int)motionEvent.getX();
            int n3 = (int)motionEvent.getY();
            int n4 = n = this.pointToPosition(n2, n3);
            if (!this.mDataChanged) {
                if (this.mTouchMode == 4) {
                    this.createScrollingCache();
                    this.mTouchMode = 3;
                    this.mMotionCorrection = 0;
                    n4 = this.findMotionRow(n3);
                    this.mFlingRunnable.flywheelTouch();
                } else {
                    n4 = n;
                    if (n >= 0) {
                        n4 = n;
                        if (((ListAdapter)this.getAdapter()).isEnabled(n)) {
                            this.mTouchMode = 0;
                            if (this.mPendingCheckForTap == null) {
                                this.mPendingCheckForTap = new CheckForTap();
                            }
                            this.mPendingCheckForTap.x = motionEvent.getX();
                            this.mPendingCheckForTap.y = motionEvent.getY();
                            this.postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                            n4 = n;
                        }
                    }
                }
            }
            if (n4 >= 0) {
                this.mMotionViewOriginalTop = this.getChildAt(n4 - this.mFirstPosition).getTop();
            }
            this.mMotionX = n2;
            this.mMotionY = n3;
            this.mMotionPosition = n4;
            this.mLastY = Integer.MIN_VALUE;
        }
        if (this.mTouchMode == 0 && this.mMotionPosition != -1 && this.performButtonActionOnTouchDown(motionEvent)) {
            this.removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void onTouchMove(MotionEvent object, MotionEvent object2) {
        int n;
        if (this.mHasPerformedLongPress) {
            return;
        }
        int n2 = n = object.findPointerIndex(this.mActivePointerId);
        if (n == -1) {
            n2 = 0;
            this.mActivePointerId = object.getPointerId(0);
        }
        if (this.mDataChanged) {
            this.layoutChildren();
        }
        int n3 = (int)object.getY(n2);
        n = this.mTouchMode;
        if (n != 0 && n != 1 && n != 2) {
            if (n == 3 || n == 5) {
                this.scrollIfNeeded((int)object.getX(n2), n3, (MotionEvent)object2);
            }
        } else if (!this.startScrollIfNeeded((int)object.getX(n2), n3, (MotionEvent)object2)) {
            object2 = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
            float f = object.getX(n2);
            if (!this.pointInView(f, n3, this.mTouchSlop)) {
                this.setPressed(false);
                if (object2 != null) {
                    ((View)object2).setPressed(false);
                }
                object = this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress;
                this.removeCallbacks((Runnable)object);
                this.mTouchMode = 2;
                this.updateSelectorState();
            } else if (object2 != null) {
                object = this.mTmpPoint;
                object[0] = f;
                object[1] = n3;
                this.transformPointToViewLocal((float[])object, (View)object2);
                ((View)object2).drawableHotspotChanged(object[0], object[1]);
            }
        }
    }

    private void onTouchUp(MotionEvent object) {
        int n = this.mTouchMode;
        if (n != 0 && n != 1 && n != 2) {
            if (n != 3) {
                if (n == 5) {
                    if (this.mFlingRunnable == null) {
                        this.mFlingRunnable = new FlingRunnable();
                    }
                    object = this.mVelocityTracker;
                    ((VelocityTracker)object).computeCurrentVelocity(1000, this.mMaximumVelocity);
                    n = (int)((VelocityTracker)object).getYVelocity(this.mActivePointerId);
                    this.reportScrollStateChange(2);
                    if (Math.abs(n) > this.mMinimumVelocity) {
                        this.mFlingRunnable.startOverfling(-n);
                    } else {
                        this.mFlingRunnable.startSpringback();
                    }
                }
            } else {
                int n2 = this.getChildCount();
                if (n2 > 0) {
                    int n3 = this.getChildAt(0).getTop();
                    int n4 = this.getChildAt(n2 - 1).getBottom();
                    int n5 = this.mListPadding.top;
                    int n6 = this.getHeight() - this.mListPadding.bottom;
                    if (this.mFirstPosition == 0 && n3 >= n5 && this.mFirstPosition + n2 < this.mItemCount && n4 <= this.getHeight() - n6) {
                        this.mTouchMode = -1;
                        this.reportScrollStateChange(0);
                    } else {
                        object = this.mVelocityTracker;
                        ((VelocityTracker)object).computeCurrentVelocity(1000, this.mMaximumVelocity);
                        int n7 = (int)(((VelocityTracker)object).getYVelocity(this.mActivePointerId) * this.mVelocityScale);
                        n = Math.abs(n7) > this.mMinimumVelocity ? 1 : 0;
                        if (!(n == 0 || this.mFirstPosition == 0 && n3 == n5 - this.mOverscrollDistance || this.mFirstPosition + n2 == this.mItemCount && n4 == this.mOverscrollDistance + n6)) {
                            if (!this.dispatchNestedPreFling(0.0f, -n7)) {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new FlingRunnable();
                                }
                                this.reportScrollStateChange(2);
                                this.mFlingRunnable.start(-n7);
                                this.dispatchNestedFling(0.0f, -n7, true);
                            } else {
                                this.mTouchMode = -1;
                                this.reportScrollStateChange(0);
                            }
                        } else {
                            this.mTouchMode = -1;
                            this.reportScrollStateChange(0);
                            object = this.mFlingRunnable;
                            if (object != null) {
                                ((FlingRunnable)object).endFling();
                            }
                            if ((object = this.mPositionScroller) != null) {
                                ((AbsPositionScroller)object).stop();
                            }
                            if (n != 0 && !this.dispatchNestedPreFling(0.0f, -n7)) {
                                this.dispatchNestedFling(0.0f, -n7, false);
                            }
                        }
                    }
                } else {
                    this.mTouchMode = -1;
                    this.reportScrollStateChange(0);
                }
            }
        } else {
            int n8 = this.mMotionPosition;
            final View view = this.getChildAt(n8 - this.mFirstPosition);
            if (view != null) {
                float f;
                if (this.mTouchMode != 0) {
                    view.setPressed(false);
                }
                if ((n = (f = ((MotionEvent)object).getX()) > (float)this.mListPadding.left && f < (float)(this.getWidth() - this.mListPadding.right) ? 1 : 0) != 0 && !view.hasExplicitFocusable()) {
                    if (this.mPerformClick == null) {
                        this.mPerformClick = new PerformClick();
                    }
                    final PerformClick performClick = this.mPerformClick;
                    performClick.mClickMotionPosition = n8;
                    performClick.rememberWindowAttachCount();
                    this.mResurrectToPosition = n8;
                    n = this.mTouchMode;
                    if (n != 0 && n != 1) {
                        if (!this.mDataChanged && this.mAdapter.isEnabled(n8)) {
                            performClick.run();
                        }
                    } else {
                        Object object2 = this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress;
                        this.removeCallbacks((Runnable)object2);
                        this.mLayoutMode = 0;
                        if (!this.mDataChanged && this.mAdapter.isEnabled(n8)) {
                            this.mTouchMode = 1;
                            this.setSelectedPositionInt(this.mMotionPosition);
                            this.layoutChildren();
                            view.setPressed(true);
                            this.positionSelector(this.mMotionPosition, view);
                            this.setPressed(true);
                            object2 = this.mSelector;
                            if (object2 != null) {
                                if ((object2 = ((Drawable)object2).getCurrent()) != null && object2 instanceof TransitionDrawable) {
                                    ((TransitionDrawable)object2).resetTransition();
                                }
                                this.mSelector.setHotspot(f, ((MotionEvent)object).getY());
                            }
                            if ((object = this.mTouchModeReset) != null) {
                                this.removeCallbacks((Runnable)object);
                            }
                            this.mTouchModeReset = new Runnable(){

                                @Override
                                public void run() {
                                    AbsListView.this.mTouchModeReset = null;
                                    AbsListView.this.mTouchMode = -1;
                                    view.setPressed(false);
                                    AbsListView.this.setPressed(false);
                                    if (!AbsListView.this.mDataChanged && !AbsListView.this.mIsDetaching && AbsListView.this.isAttachedToWindow()) {
                                        performClick.run();
                                    }
                                }
                            };
                            this.postDelayed(this.mTouchModeReset, ViewConfiguration.getPressedStateDuration());
                        } else {
                            this.mTouchMode = -1;
                            this.updateSelectorState();
                        }
                        return;
                    }
                }
            }
            this.mTouchMode = -1;
            this.updateSelectorState();
        }
        this.setPressed(false);
        if (this.shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        this.invalidate();
        this.removeCallbacks(this.mPendingCheckForLongPress);
        this.recycleVelocityTracker();
        this.mActivePointerId = -1;
        object = this.mScrollStrictSpan;
        if (object != null) {
            ((StrictMode.Span)object).finish();
            this.mScrollStrictSpan = null;
        }
    }

    private boolean performStylusButtonPressAction(MotionEvent object) {
        if (this.mChoiceMode == 3 && this.mChoiceActionMode == null && (object = this.getChildAt(this.mMotionPosition - this.mFirstPosition)) != null && this.performLongPress((View)object, this.mMotionPosition, this.mAdapter.getItemId(this.mMotionPosition))) {
            this.mTouchMode = -1;
            this.setPressed(false);
            ((View)object).setPressed(false);
            return true;
        }
        return false;
    }

    private void positionPopup() {
        int n = this.getResources().getDisplayMetrics().heightPixels;
        int[] arrn = new int[2];
        this.getLocationOnScreen(arrn);
        n = n - arrn[1] - this.getHeight() + (int)(this.mDensityScale * 20.0f);
        if (!this.mPopup.isShowing()) {
            this.mPopup.showAtLocation(this, 81, arrn[0], n);
        } else {
            this.mPopup.update(arrn[0], n, -1, -1);
        }
    }

    @UnsupportedAppUsage
    private void positionSelector(int n, View object, boolean bl, float f, float f2) {
        boolean bl2 = n != this.mSelectorPosition;
        if (n != -1) {
            this.mSelectorPosition = n;
        }
        Rect rect = this.mSelectorRect;
        rect.set(((View)object).getLeft(), ((View)object).getTop(), ((View)object).getRight(), ((View)object).getBottom());
        if (object instanceof SelectionBoundsAdjuster) {
            ((SelectionBoundsAdjuster)object).adjustListItemSelectionBounds(rect);
        }
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        boolean bl3 = ((View)object).isEnabled();
        if (this.mIsChildViewEnabled != bl3) {
            this.mIsChildViewEnabled = bl3;
        }
        if ((object = this.mSelector) != null) {
            if (bl2) {
                ((Drawable)object).setVisible(false, false);
                ((Drawable)object).setState(StateSet.NOTHING);
            }
            ((Drawable)object).setBounds(rect);
            if (bl2) {
                if (this.getVisibility() == 0) {
                    ((Drawable)object).setVisible(true, false);
                }
                this.updateSelectorState();
            }
            if (bl) {
                ((Drawable)object).setHotspot(f, f2);
            }
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void scrollIfNeeded(int n, int n2, MotionEvent object) {
        block29 : {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            block28 : {
                block36 : {
                    block30 : {
                        block33 : {
                            block31 : {
                                boolean bl;
                                block34 : {
                                    block35 : {
                                        block32 : {
                                            Object object2;
                                            n5 = n7 = n2 - this.mMotionY;
                                            if (this.mLastY == Integer.MIN_VALUE) {
                                                n5 = n7 - this.mMotionCorrection;
                                            }
                                            n7 = (n7 = this.mLastY) != Integer.MIN_VALUE ? (n7 -= n2) : -n5;
                                            if (this.dispatchNestedPreScroll(0, n7, this.mScrollConsumed, this.mScrollOffset)) {
                                                int[] arrn = this.mScrollConsumed;
                                                n6 = arrn[1];
                                                object2 = this.mScrollOffset;
                                                n3 = -object2[1];
                                                n7 = arrn[1];
                                                if (object != null) {
                                                    ((MotionEvent)object).offsetLocation(0.0f, object2[1]);
                                                    this.mNestedYOffset += this.mScrollOffset[1];
                                                }
                                                n5 += n6;
                                            } else {
                                                n3 = 0;
                                                n7 = 0;
                                            }
                                            n6 = this.mLastY;
                                            n7 = n6 != Integer.MIN_VALUE ? n2 - n6 + n7 : n5;
                                            n8 = 0;
                                            n4 = 0;
                                            n6 = this.mTouchMode;
                                            if (n6 != 3) break block28;
                                            if (this.mScrollStrictSpan == null) {
                                                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("AbsListView-scroll");
                                            }
                                            if (n2 == this.mLastY) break block29;
                                            if ((this.mGroupFlags & 524288) == 0 && Math.abs(n5) > this.mTouchSlop && (object2 = this.getParent()) != null) {
                                                object2.requestDisallowInterceptTouchEvent(true);
                                            }
                                            n6 = (n6 = this.mMotionPosition) >= 0 ? (n6 -= this.mFirstPosition) : this.getChildCount() / 2;
                                            object2 = this.getChildAt(n6);
                                            int n9 = object2 != null ? ((View)object2).getTop() : 0;
                                            bl = n7 != 0 ? this.trackMotionScroll(n5, n7) : false;
                                            object2 = this.getChildAt(n6);
                                            if (object2 == null) break block30;
                                            n5 = ((View)object2).getTop();
                                            if (!bl) break block31;
                                            n6 = -n7 - (n5 - n9);
                                            if (!this.dispatchNestedScroll(0, n6 - n7, 0, n6, this.mScrollOffset)) break block32;
                                            object2 = this.mScrollOffset;
                                            n5 = false - object2[1];
                                            if (object != null) {
                                                ((MotionEvent)object).offsetLocation(0.0f, (float)object2[1]);
                                                this.mNestedYOffset += this.mScrollOffset[1];
                                            }
                                            break block33;
                                        }
                                        bl = this.overScrollBy(0, n6, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
                                        if (bl && (object = this.mVelocityTracker) != null) {
                                            ((VelocityTracker)object).clear();
                                        }
                                        if ((n5 = this.getOverScrollMode()) == 0) break block34;
                                        if (n5 != 1) break block35;
                                        if (!this.contentFits()) break block34;
                                        n5 = n4;
                                        break block33;
                                    }
                                    n5 = n4;
                                    break block33;
                                }
                                if (!bl) {
                                    this.mDirection = 0;
                                    this.mTouchMode = 5;
                                }
                                if (n7 > 0) {
                                    this.mEdgeGlowTop.onPull((float)(-n6) / (float)this.getHeight(), (float)n / (float)this.getWidth());
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease();
                                    }
                                    this.invalidateTopGlow();
                                    n5 = n4;
                                } else {
                                    n5 = n4;
                                    if (n7 < 0) {
                                        this.mEdgeGlowBottom.onPull((float)n6 / (float)this.getHeight(), 1.0f - (float)n / (float)this.getWidth());
                                        if (!this.mEdgeGlowTop.isFinished()) {
                                            this.mEdgeGlowTop.onRelease();
                                        }
                                        this.invalidateBottomGlow();
                                        n5 = n4;
                                    }
                                }
                                break block33;
                            }
                            n5 = n4;
                        }
                        this.mMotionY = n2 + n5 + n3;
                        break block36;
                    }
                    n5 = n8;
                }
                this.mLastY = n2 + n5 + n3;
                break block29;
            }
            if (n6 == 5 && n2 != this.mLastY) {
                n4 = this.mScrollY;
                n8 = n4 - n7;
                n6 = n2 > this.mLastY ? 1 : -1;
                if (this.mDirection == 0) {
                    this.mDirection = n6;
                }
                int n10 = -n7;
                if (n8 < 0 && n4 >= 0 || n8 > 0 && n4 <= 0) {
                    n10 = n4 = -n4;
                    n7 += n4;
                } else {
                    n7 = 0;
                }
                if (n10 != 0) {
                    this.overScrollBy(0, n10, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
                    n4 = this.getOverScrollMode();
                    if (n4 == 0 || n4 == 1 && !this.contentFits()) {
                        if (n5 > 0) {
                            this.mEdgeGlowTop.onPull((float)n10 / (float)this.getHeight(), (float)n / (float)this.getWidth());
                            if (!this.mEdgeGlowBottom.isFinished()) {
                                this.mEdgeGlowBottom.onRelease();
                            }
                            this.invalidateTopGlow();
                        } else if (n5 < 0) {
                            this.mEdgeGlowBottom.onPull((float)n10 / (float)this.getHeight(), 1.0f - (float)n / (float)this.getWidth());
                            if (!this.mEdgeGlowTop.isFinished()) {
                                this.mEdgeGlowTop.onRelease();
                            }
                            this.invalidateBottomGlow();
                        }
                    }
                }
                if (n7 != 0) {
                    if (this.mScrollY != 0) {
                        this.mScrollY = 0;
                        this.invalidateParentIfNeeded();
                    }
                    this.trackMotionScroll(n7, n7);
                    this.mTouchMode = 3;
                    n5 = this.findClosestMotionRow(n2);
                    n = 0;
                    this.mMotionCorrection = 0;
                    object = this.getChildAt(n5 - this.mFirstPosition);
                    if (object != null) {
                        n = ((View)object).getTop();
                    }
                    this.mMotionViewOriginalTop = n;
                    this.mMotionY = n2 + n3;
                    this.mMotionPosition = n5;
                }
                this.mLastY = n2 + 0 + n3;
                this.mDirection = n6;
            }
        }
    }

    private void setFastScrollerAlwaysVisibleUiThread(boolean bl) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setAlwaysShow(bl);
        }
    }

    private void setFastScrollerEnabledUiThread(boolean bl) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setEnabled(bl);
        } else if (bl) {
            this.mFastScroll = new FastScroller(this, this.mFastScrollStyle);
            this.mFastScroll.setEnabled(true);
        }
        this.resolvePadding();
        fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.updateLayout();
        }
    }

    private void setItemViewLayoutParams(View view, int n) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        LayoutParams layoutParams2 = layoutParams == null ? (LayoutParams)this.generateDefaultLayoutParams() : (!this.checkLayoutParams(layoutParams) ? (LayoutParams)this.generateLayoutParams(layoutParams) : (LayoutParams)layoutParams);
        if (this.mAdapterHasStableIds) {
            layoutParams2.itemId = this.mAdapter.getItemId(n);
        }
        layoutParams2.viewType = this.mAdapter.getItemViewType(n);
        layoutParams2.isEnabled = this.mAdapter.isEnabled(n);
        if (layoutParams2 != layoutParams) {
            view.setLayoutParams(layoutParams2);
        }
    }

    private boolean shouldDisplayEdgeEffects() {
        boolean bl = this.getOverScrollMode() != 2;
        return bl;
    }

    private boolean showContextMenuForChildInternal(View view, float f, float f2, boolean bl) {
        int n = this.getPositionForView(view);
        if (n < 0) {
            return false;
        }
        long l = this.mAdapter.getItemId(n);
        boolean bl2 = false;
        if (this.mOnItemLongClickListener != null) {
            bl2 = this.mOnItemLongClickListener.onItemLongClick(this, view, n, l);
        }
        boolean bl3 = bl2;
        if (!bl2) {
            this.mContextMenuInfo = this.createContextMenuInfo(this.getChildAt(n - this.mFirstPosition), n, l);
            bl3 = bl ? super.showContextMenuForChild(view, f, f2) : super.showContextMenuForChild(view);
        }
        return bl3;
    }

    private boolean showContextMenuInternal(float f, float f2, boolean bl) {
        int n = this.pointToPosition((int)f, (int)f2);
        if (n != -1) {
            long l = this.mAdapter.getItemId(n);
            View view = this.getChildAt(n - this.mFirstPosition);
            if (view != null) {
                this.mContextMenuInfo = this.createContextMenuInfo(view, n, l);
                if (bl) {
                    return super.showContextMenuForChild(this, f, f2);
                }
                return super.showContextMenuForChild(this);
            }
        }
        if (bl) {
            return super.showContextMenu(f, f2);
        }
        return super.showContextMenu();
    }

    private void showPopup() {
        if (this.getWindowVisibility() == 0) {
            this.createTextFilter(true);
            this.positionPopup();
            this.checkFocus();
        }
    }

    private boolean startScrollIfNeeded(int n, int n2, MotionEvent motionEvent) {
        int n3 = n2 - this.mMotionY;
        int n4 = Math.abs(n3);
        int n5 = this.mScrollY != 0 ? 1 : 0;
        if ((n5 != 0 || n4 > this.mTouchSlop) && (this.getNestedScrollAxes() & 2) == 0) {
            this.createScrollingCache();
            if (n5 != 0) {
                this.mTouchMode = 5;
                this.mMotionCorrection = 0;
            } else {
                this.mTouchMode = 3;
                n5 = this.mTouchSlop;
                if (n3 <= 0) {
                    n5 = -n5;
                }
                this.mMotionCorrection = n5;
            }
            this.removeCallbacks(this.mPendingCheckForLongPress);
            this.setPressed(false);
            Object object = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
            if (object != null) {
                ((View)object).setPressed(false);
            }
            this.reportScrollStateChange(1);
            object = this.getParent();
            if (object != null) {
                object.requestDisallowInterceptTouchEvent(true);
            }
            this.scrollIfNeeded(n, n2, motionEvent);
            return true;
        }
        return false;
    }

    private void updateOnScreenCheckedViews() {
        int n = this.mFirstPosition;
        int n2 = this.getChildCount();
        boolean bl = this.getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            int n3 = n + i;
            if (view instanceof Checkable) {
                ((Checkable)((Object)view)).setChecked(this.mCheckStates.get(n3));
                continue;
            }
            if (!bl) continue;
            view.setActivated(this.mCheckStates.get(n3));
        }
    }

    private void useDefaultSelector() {
        this.setSelector(this.getContext().getDrawable(17301602));
    }

    @Override
    public void addTouchables(ArrayList<View> arrayList) {
        int n = this.getChildCount();
        int n2 = this.mFirstPosition;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (listAdapter.isEnabled(n2 + i)) {
                arrayList.add(view);
            }
            view.addTouchables(arrayList);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public boolean canScrollList(int n) {
        boolean bl;
        block7 : {
            block6 : {
                int n2 = this.getChildCount();
                boolean bl2 = false;
                bl = false;
                if (n2 == 0) {
                    return false;
                }
                int n3 = this.mFirstPosition;
                Rect rect = this.mListPadding;
                if (n > 0) {
                    n = this.getChildAt(n2 - 1).getBottom();
                    if (n3 + n2 < this.mItemCount || n > this.getHeight() - rect.bottom) {
                        bl = true;
                    }
                    return bl;
                }
                n = this.getChildAt(0).getTop();
                if (n3 > 0) break block6;
                bl = bl2;
                if (n >= rect.top) break block7;
            }
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean checkInputConnectionProxy(View view) {
        boolean bl = view == this.mTextFilter;
        return bl;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void clearChoices() {
        Cloneable cloneable = this.mCheckStates;
        if (cloneable != null) {
            ((SparseBooleanArray)cloneable).clear();
        }
        if ((cloneable = this.mCheckedIdStates) != null) {
            ((LongSparseArray)cloneable).clear();
        }
        this.mCheckedItemCount = 0;
    }

    public void clearTextFilter() {
        if (this.mFiltered) {
            this.getTextFilterInput().setText("");
            this.mFiltered = false;
            PopupWindow popupWindow = this.mPopup;
            if (popupWindow != null && popupWindow.isShowing()) {
                this.dismissPopup();
            }
        }
    }

    @Override
    protected int computeVerticalScrollExtent() {
        int n = this.getChildCount();
        if (n > 0) {
            if (this.mSmoothScrollbarEnabled) {
                int n2 = n * 100;
                View view = this.getChildAt(0);
                int n3 = view.getTop();
                int n4 = view.getHeight();
                int n5 = n2;
                if (n4 > 0) {
                    n5 = n2 + n3 * 100 / n4;
                }
                view = this.getChildAt(n - 1);
                n3 = view.getBottom();
                n = view.getHeight();
                n2 = n5;
                if (n > 0) {
                    n2 = n5 - (n3 - this.getHeight()) * 100 / n;
                }
                return n2;
            }
            return 1;
        }
        return 0;
    }

    @Override
    protected int computeVerticalScrollOffset() {
        int n = this.mFirstPosition;
        int n2 = this.getChildCount();
        if (n >= 0 && n2 > 0) {
            if (this.mSmoothScrollbarEnabled) {
                View view = this.getChildAt(0);
                int n3 = view.getTop();
                int n4 = view.getHeight();
                if (n4 > 0) {
                    return Math.max(n * 100 - n3 * 100 / n4 + (int)((float)this.mScrollY / (float)this.getHeight() * (float)this.mItemCount * 100.0f), 0);
                }
            } else {
                int n5 = this.mItemCount;
                int n6 = n == 0 ? 0 : (n + n2 == n5 ? n5 : n2 / 2 + n);
                return (int)((float)n + (float)n2 * ((float)n6 / (float)n5));
            }
        }
        return 0;
    }

    @Override
    protected int computeVerticalScrollRange() {
        int n;
        if (this.mSmoothScrollbarEnabled) {
            int n2;
            n = n2 = Math.max(this.mItemCount * 100, 0);
            if (this.mScrollY != 0) {
                n = n2 + Math.abs((int)((float)this.mScrollY / (float)this.getHeight() * (float)this.mItemCount * 100.0f));
            }
        } else {
            n = this.mItemCount;
        }
        return n;
    }

    void confirmCheckedPositionsById() {
        Object object;
        this.mCheckStates.clear();
        boolean bl = false;
        for (int i = 0; i < this.mCheckedIdStates.size(); ++i) {
            int n;
            long l = this.mCheckedIdStates.keyAt(i);
            if (l != this.mAdapter.getItemId(n = this.mCheckedIdStates.valueAt(i).intValue())) {
                int n2;
                block4 : {
                    int n3 = Math.min(n + 20, this.mItemCount);
                    for (n2 = Math.max((int)0, (int)(n - 20)); n2 < n3; ++n2) {
                        if (l != this.mAdapter.getItemId(n2)) continue;
                        this.mCheckStates.put(n2, true);
                        this.mCheckedIdStates.setValueAt(i, n2);
                        n2 = 1;
                        break block4;
                    }
                    n2 = 0;
                }
                if (n2 != 0) continue;
                this.mCheckedIdStates.delete(l);
                --i;
                --this.mCheckedItemCount;
                bl = true;
                ActionMode actionMode = this.mChoiceActionMode;
                if (actionMode == null || (object = this.mMultiChoiceModeCallback) == null) continue;
                ((MultiChoiceModeWrapper)object).onItemCheckedStateChanged(actionMode, n, l, false);
                continue;
            }
            this.mCheckStates.put(n, true);
        }
        if (bl && (object = this.mChoiceActionMode) != null) {
            ((ActionMode)object).invalidate();
        }
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(View view, int n, long l) {
        return new AdapterView.AdapterContextMenuInfo(view, n, l);
    }

    AbsPositionScroller createPositionScroller() {
        return new PositionScroller();
    }

    @Override
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        boolean bl;
        int n = 0;
        boolean bl2 = (this.mGroupFlags & 34) == 34;
        if (bl2) {
            n = canvas.save();
            int n2 = this.mScrollX;
            int n3 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + n2, this.mPaddingTop + n3, this.mRight + n2 - this.mLeft - this.mPaddingRight, this.mBottom + n3 - this.mTop - this.mPaddingBottom);
            this.mGroupFlags &= -35;
        }
        if (!(bl = this.mDrawSelectorOnTop)) {
            this.drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (bl) {
            this.drawSelector(canvas);
        }
        if (bl2) {
            canvas.restoreToCount(n);
            this.mGroupFlags = 34 | this.mGroupFlags;
        }
    }

    @Override
    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    @Override
    protected void dispatchSetPressed(boolean bl) {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.shouldDisplayEdgeEffects()) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6 = this.mScrollY;
            boolean bl = this.getClipToPadding();
            if (bl) {
                n5 = this.getWidth() - this.mPaddingLeft - this.mPaddingRight;
                n2 = this.getHeight() - this.mPaddingTop - this.mPaddingBottom;
                n4 = this.mPaddingLeft;
                n3 = this.mPaddingTop;
            } else {
                n5 = this.getWidth();
                n2 = this.getHeight();
                n4 = 0;
                n3 = 0;
            }
            this.mEdgeGlowTop.setSize(n5, n2);
            this.mEdgeGlowBottom.setSize(n5, n2);
            boolean bl2 = this.mEdgeGlowTop.isFinished();
            int n7 = 0;
            if (!bl2) {
                int n8 = canvas.save();
                canvas.clipRect(n4, n3, n4 + n5, this.mEdgeGlowTop.getMaxHeight() + n3);
                n = Math.min(0, this.mFirstPositionDistanceGuess + n6);
                canvas.translate(n4, n + n3);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    this.invalidateTopGlow();
                }
                canvas.restoreToCount(n8);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                n = canvas.save();
                canvas.clipRect(n4, n3 + n2 - this.mEdgeGlowBottom.getMaxHeight(), n4 + n5, n3 + n2);
                n2 = -n5;
                n6 = Math.max(this.getHeight(), this.mLastPositionDistanceGuess + n6);
                n3 = n7;
                if (bl) {
                    n3 = this.mPaddingBottom;
                }
                canvas.translate(n2 + n4, n6 - n3);
                canvas.rotate(180.0f, n5, 0.0f);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    this.invalidateBottomGlow();
                }
                canvas.restoreToCount(n);
            }
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.updateSelectorState();
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("drawing:cacheColorHint", this.getCacheColorHint());
        viewHierarchyEncoder.addProperty("list:fastScrollEnabled", this.isFastScrollEnabled());
        viewHierarchyEncoder.addProperty("list:scrollingCacheEnabled", this.isScrollingCacheEnabled());
        viewHierarchyEncoder.addProperty("list:smoothScrollbarEnabled", this.isSmoothScrollbarEnabled());
        viewHierarchyEncoder.addProperty("list:stackFromBottom", this.isStackFromBottom());
        viewHierarchyEncoder.addProperty("list:textFilterEnabled", this.isTextFilterEnabled());
        View view = this.getSelectedView();
        if (view != null) {
            viewHierarchyEncoder.addPropertyKey("selectedView");
            view.encode(viewHierarchyEncoder);
        }
    }

    abstract void fillGap(boolean var1);

    int findClosestMotionRow(int n) {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return -1;
        }
        if ((n = this.findMotionRow(n)) == -1) {
            n = this.mFirstPosition + n2 - 1;
        }
        return n;
    }

    @UnsupportedAppUsage
    abstract int findMotionRow(int var1);

    public void fling(int n) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.reportScrollStateChange(2);
        this.mFlingRunnable.start(n);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AbsListView.class.getName();
    }

    View getAccessibilityFocusedChild(View object) {
        ViewParent viewParent = ((View)object).getParent();
        View view = object;
        for (object = viewParent; object instanceof View && object != this; object = object.getParent()) {
            view = (View)object;
        }
        if (!(object instanceof View)) {
            return null;
        }
        return view;
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        float f;
        block2 : {
            int n = this.getChildCount();
            f = super.getBottomFadingEdgeStrength();
            if (n == 0) {
                return f;
            }
            if (this.mFirstPosition + n - 1 < this.mItemCount - 1) {
                return 1.0f;
            }
            int n2 = this.getChildAt(n - 1).getBottom();
            n = this.getHeight();
            float f2 = this.getVerticalFadingEdgeLength();
            if (n2 <= n - this.mPaddingBottom) break block2;
            f = (float)(n2 - n + this.mPaddingBottom) / f2;
        }
        return f;
    }

    @Override
    protected int getBottomPaddingOffset() {
        int n = (this.mGroupFlags & 34) == 34 ? 0 : this.mPaddingBottom;
        return n;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode != 0 && this.mCheckedIdStates != null && this.mAdapter != null) {
            LongSparseArray<Integer> longSparseArray = this.mCheckedIdStates;
            int n = longSparseArray.size();
            long[] arrl = new long[n];
            for (int i = 0; i < n; ++i) {
                arrl[i] = longSparseArray.keyAt(i);
            }
            return arrl;
        }
        return new long[0];
    }

    public int getCheckedItemPosition() {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode == 1 && (sparseBooleanArray = this.mCheckStates) != null && sparseBooleanArray.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    @Override
    public void getFocusedRect(Rect rect) {
        View view = this.getSelectedView();
        if (view != null && view.getParent() == this) {
            view.getFocusedRect(rect);
            this.offsetDescendantRectToMyCoords(view, rect);
        } else {
            super.getFocusedRect(rect);
        }
    }

    int getFooterViewsCount() {
        return 0;
    }

    int getHeaderViewsCount() {
        return 0;
    }

    int getHeightForPosition(int n) {
        int n2 = this.getFirstVisiblePosition();
        int n3 = this.getChildCount();
        if ((n2 = n - n2) >= 0 && n2 < n3) {
            return this.getChildAt(n2).getHeight();
        }
        View view = this.obtainView(n, this.mIsScrap);
        view.measure(this.mWidthMeasureSpec, 0);
        n3 = view.getMeasuredHeight();
        this.mRecycler.addScrapView(view, n);
        return n3;
    }

    @Override
    protected int getLeftPaddingOffset() {
        int n = (this.mGroupFlags & 34) == 34 ? 0 : -this.mPaddingLeft;
        return n;
    }

    public int getListPaddingBottom() {
        return this.mListPadding.bottom;
    }

    public int getListPaddingLeft() {
        return this.mListPadding.left;
    }

    public int getListPaddingRight() {
        return this.mListPadding.right;
    }

    public int getListPaddingTop() {
        return this.mListPadding.top;
    }

    @Override
    protected int getRightPaddingOffset() {
        int n = (this.mGroupFlags & 34) == 34 ? 0 : this.mPaddingRight;
        return n;
    }

    @ViewDebug.ExportedProperty
    @Override
    public View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    int getSelectionModeForAccessibility() {
        int n = this.getChoiceMode();
        if (n != 0) {
            if (n != 1) {
                if (n != 2 && n != 3) {
                    return 0;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    @Override
    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    public CharSequence getTextFilter() {
        EditText editText;
        if (this.mTextFilterEnabled && (editText = this.mTextFilter) != null) {
            return editText.getText();
        }
        return null;
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        float f;
        block2 : {
            int n = this.getChildCount();
            f = super.getTopFadingEdgeStrength();
            if (n == 0) {
                return f;
            }
            if (this.mFirstPosition > 0) {
                return 1.0f;
            }
            n = this.getChildAt(0).getTop();
            float f2 = this.getVerticalFadingEdgeLength();
            if (n >= this.mPaddingTop) break block2;
            f = (float)(-(n - this.mPaddingTop)) / f2;
        }
        return f;
    }

    @Override
    protected int getTopPaddingOffset() {
        int n = (this.mGroupFlags & 34) == 34 ? 0 : -this.mPaddingTop;
        return n;
    }

    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    @Override
    public int getVerticalScrollbarWidth() {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.isEnabled()) {
            return Math.max(super.getVerticalScrollbarWidth(), this.mFastScroll.getWidth());
        }
        return super.getVerticalScrollbarWidth();
    }

    void handleBoundsChange() {
        if (this.mInLayout) {
            return;
        }
        int n = this.getChildCount();
        if (n > 0) {
            this.mDataChanged = true;
            this.rememberSyncState();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null && layoutParams.width >= 1 && layoutParams.height >= 1) continue;
                view.forceLayout();
            }
        }
    }

    @Override
    protected void handleDataChanged() {
        int n;
        Object object;
        int n2 = this.mItemCount;
        int n3 = this.mLastHandledItemCount;
        this.mLastHandledItemCount = this.mItemCount;
        if (this.mChoiceMode != 0 && (object = this.mAdapter) != null && object.hasStableIds()) {
            this.confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        int n4 = 3;
        if (n2 > 0) {
            int n5;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                n = this.mTranscriptMode;
                if (n == 2) {
                    this.mLayoutMode = 3;
                    return;
                }
                if (n == 1) {
                    if (this.mForceTranscriptScroll) {
                        this.mForceTranscriptScroll = false;
                        this.mLayoutMode = 3;
                        return;
                    }
                    int n6 = this.getChildCount();
                    n5 = this.getHeight() - this.getPaddingBottom();
                    object = this.getChildAt(n6 - 1);
                    n = object != null ? ((View)object).getBottom() : n5;
                    if (this.mFirstPosition + n6 >= n3 && n <= n5) {
                        this.mLayoutMode = 3;
                        return;
                    }
                    this.awakenScrollBars();
                }
                if ((n = this.mSyncMode) != 0) {
                    if (n == 1) {
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), n2 - 1);
                        return;
                    }
                } else {
                    if (this.isInTouchMode()) {
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), n2 - 1);
                        return;
                    }
                    n = this.findSyncPosition();
                    if (n >= 0 && this.lookForSelectablePosition(n, true) == n) {
                        this.mSyncPosition = n;
                        this.mLayoutMode = this.mSyncHeight == (long)this.getHeight() ? 5 : 2;
                        this.setNextSelectedPositionInt(n);
                        return;
                    }
                }
            }
            if (!this.isInTouchMode()) {
                n = n5 = this.getSelectedItemPosition();
                if (n5 >= n2) {
                    n = n2 - 1;
                }
                n5 = n;
                if (n < 0) {
                    n5 = 0;
                }
                if ((n = this.lookForSelectablePosition(n5, true)) >= 0) {
                    this.setNextSelectedPositionInt(n);
                    return;
                }
                n = this.lookForSelectablePosition(n5, false);
                if (n >= 0) {
                    this.setNextSelectedPositionInt(n);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        n = this.mStackFromBottom ? n4 : 1;
        this.mLayoutMode = n;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        this.checkSelectionChanged();
    }

    @Override
    protected boolean handleScrollBarDragging(MotionEvent motionEvent) {
        return false;
    }

    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            this.setSelectedPositionInt(-1);
            this.setNextSelectedPositionInt(-1);
            this.mSelectedTop = 0;
        }
    }

    @Override
    protected void internalSetPadding(int n, int n2, int n3, int n4) {
        super.internalSetPadding(n, n2, n3, n4);
        if (this.isLayoutRequested()) {
            this.handleBoundsChange();
        }
    }

    public void invalidateViews() {
        this.mDataChanged = true;
        this.rememberSyncState();
        this.requestLayout();
        this.invalidate();
    }

    @UnsupportedAppUsage
    void invokeOnItemScrollListener() {
        Object object = this.mFastScroll;
        if (object != null) {
            ((FastScroller)object).onScroll(this.mFirstPosition, this.getChildCount(), this.mItemCount);
        }
        if ((object = this.mOnScrollListener) != null) {
            object.onScroll(this, this.mFirstPosition, this.getChildCount(), this.mItemCount);
        }
        this.onScrollChanged(0, 0, 0, 0);
    }

    public boolean isDrawSelectorOnTop() {
        return this.mDrawSelectorOnTop;
    }

    public boolean isFastScrollAlwaysVisible() {
        FastScroller fastScroller = this.mFastScroll;
        boolean bl = true;
        boolean bl2 = true;
        if (fastScroller == null) {
            if (!this.mFastScrollEnabled || !this.mFastScrollAlwaysVisible) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = fastScroller.isEnabled() && this.mFastScroll.isAlwaysShowEnabled() ? bl : false;
        return bl2;
    }

    @ViewDebug.ExportedProperty
    public boolean isFastScrollEnabled() {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller == null) {
            return this.mFastScrollEnabled;
        }
        return fastScroller.isEnabled();
    }

    @Override
    protected boolean isInFilterMode() {
        return this.mFiltered;
    }

    public boolean isItemChecked(int n) {
        SparseBooleanArray sparseBooleanArray;
        if (this.mChoiceMode != 0 && (sparseBooleanArray = this.mCheckStates) != null) {
            return sparseBooleanArray.get(n);
        }
        return false;
    }

    @Override
    protected boolean isPaddingOffsetRequired() {
        boolean bl = (this.mGroupFlags & 34) != 34;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    @ViewDebug.ExportedProperty
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    @ViewDebug.ExportedProperty
    public boolean isStackFromBottom() {
        return this.mStackFromBottom;
    }

    @ViewDebug.ExportedProperty
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @UnsupportedAppUsage
    @Override
    protected boolean isVerticalScrollBarHidden() {
        return this.isFastScrollEnabled();
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mSelector;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    void keyPressed() {
        if (this.isEnabled() && this.isClickable()) {
            Drawable drawable2 = this.mSelector;
            Object object = this.mSelectorRect;
            if (drawable2 != null && (this.isFocused() || this.touchModeDrawsInPressedState()) && !((Rect)object).isEmpty()) {
                object = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (object != null) {
                    if (((View)object).hasExplicitFocusable()) {
                        return;
                    }
                    ((View)object).setPressed(true);
                }
                this.setPressed(true);
                boolean bl = this.isLongClickable();
                drawable2 = drawable2.getCurrent();
                if (drawable2 != null && drawable2 instanceof TransitionDrawable) {
                    if (bl) {
                        ((TransitionDrawable)drawable2).startTransition(ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((TransitionDrawable)drawable2).resetTransition();
                    }
                }
                if (bl && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress();
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    this.postDelayed(this.mPendingCheckForKeyLongPress, ViewConfiguration.getLongPressTimeout());
                }
            }
            return;
        }
    }

    protected void layoutChildren() {
    }

    View obtainView(int n, boolean[] arrbl) {
        int n2;
        Trace.traceBegin(8L, "obtainView");
        arrbl[0] = false;
        View view = this.mRecycler.getTransientStateView(n);
        if (view != null) {
            View view2;
            if (((LayoutParams)view.getLayoutParams()).viewType == this.mAdapter.getItemViewType(n) && (view2 = this.mAdapter.getView(n, view, this)) != view) {
                this.setItemViewLayoutParams(view2, n);
                this.mRecycler.addScrapView(view2, n);
            }
            arrbl[0] = true;
            view.dispatchFinishTemporaryDetach();
            return view;
        }
        view = this.mRecycler.getScrapView(n);
        View view3 = this.mAdapter.getView(n, view, this);
        if (view != null) {
            if (view3 != view) {
                this.mRecycler.addScrapView(view, n);
            } else if (view3.isTemporarilyDetached()) {
                arrbl[0] = true;
                view3.dispatchFinishTemporaryDetach();
            }
        }
        if ((n2 = this.mCacheColorHint) != 0) {
            view3.setDrawingCacheBackgroundColor(n2);
        }
        if (view3.getImportantForAccessibility() == 0) {
            view3.setImportantForAccessibility(1);
        }
        this.setItemViewLayoutParams(view3, n);
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAccessibilityDelegate == null) {
                this.mAccessibilityDelegate = new ListItemAccessibilityDelegate();
            }
            if (view3.getAccessibilityDelegate() == null) {
                view3.setAccessibilityDelegate(this.mAccessibilityDelegate);
            }
        }
        Trace.traceEnd(8L);
        return view3;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        viewTreeObserver.addOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null && !this.mGlobalLayoutListenerAddedFilter) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver((DataSetObserver)((Object)this.mDataSetObserver));
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
    }

    @Override
    public void onCancelPendingInputEvents() {
        super.onCancelPendingInputEvents();
        Runnable runnable = this.mPerformClick;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
        if ((runnable = this.mPendingCheckForTap) != null) {
            this.removeCallbacks(runnable);
        }
        if ((runnable = this.mPendingCheckForLongPress) != null) {
            this.removeCallbacks(runnable);
        }
        if ((runnable = this.mPendingCheckForKeyLongPress) != null) {
            this.removeCallbacks(runnable);
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (this.isTextFilterEnabled()) {
            if (this.mPublicInputConnection == null) {
                this.mDefInputConnection = new BaseInputConnection(this, false);
                this.mPublicInputConnection = new InputConnectionWrapper(editorInfo);
            }
            editorInfo.inputType = 177;
            editorInfo.imeOptions = 6;
            return this.mPublicInputConnection;
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        ListAdapter listAdapter;
        super.onDetachedFromWindow();
        this.mIsDetaching = true;
        this.dismissPopup();
        this.mRecycler.clear();
        Object object = this.getViewTreeObserver();
        ((ViewTreeObserver)object).removeOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null) {
            ((ViewTreeObserver)object).removeOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = false;
        }
        if ((listAdapter = this.mAdapter) != null && (object = this.mDataSetObserver) != null) {
            listAdapter.unregisterDataSetObserver((DataSetObserver)object);
            this.mDataSetObserver = null;
        }
        if ((object = this.mScrollStrictSpan) != null) {
            ((StrictMode.Span)object).finish();
            this.mScrollStrictSpan = null;
        }
        if ((object = this.mFlingStrictSpan) != null) {
            ((StrictMode.Span)object).finish();
            this.mFlingStrictSpan = null;
        }
        if ((object = this.mFlingRunnable) != null) {
            this.removeCallbacks((Runnable)object);
        }
        if ((object = this.mPositionScroller) != null) {
            ((AbsPositionScroller)object).stop();
        }
        if ((object = this.mClearScrollingCache) != null) {
            this.removeCallbacks((Runnable)object);
        }
        if ((object = this.mPerformClick) != null) {
            this.removeCallbacks((Runnable)object);
        }
        if ((object = this.mTouchModeReset) != null) {
            this.removeCallbacks((Runnable)object);
            this.mTouchModeReset.run();
        }
        this.mIsDetaching = false;
    }

    @Override
    protected void onDisplayHint(int n) {
        PopupWindow popupWindow;
        super.onDisplayHint(n);
        if (n != 0) {
            PopupWindow popupWindow2;
            if (n == 4 && (popupWindow2 = this.mPopup) != null && popupWindow2.isShowing()) {
                this.dismissPopup();
            }
        } else if (this.mFiltered && (popupWindow = this.mPopup) != null && !popupWindow.isShowing()) {
            this.showPopup();
        }
        boolean bl = n == 4;
        this.mPopupHidden = bl;
    }

    @Override
    public void onFilterComplete(int n) {
        if (this.mSelectedPosition < 0 && n > 0) {
            this.mResurrectToPosition = -1;
            this.resurrectSelection();
        }
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (bl && this.mSelectedPosition < 0 && !this.isInTouchMode()) {
            if (!this.isAttachedToWindow() && this.mAdapter != null) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            this.resurrectSelection();
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (n != 8) {
            if (n == 11 && motionEvent.isFromSource(2) && ((n = motionEvent.getActionButton()) == 32 || n == 2) && ((n = this.mTouchMode) == 0 || n == 1) && this.performStylusButtonPressAction(motionEvent)) {
                this.removeCallbacks(this.mPendingCheckForLongPress);
                this.removeCallbacks(this.mPendingCheckForTap);
            }
        } else {
            float f = motionEvent.isFromSource(2) ? motionEvent.getAxisValue(9) : (motionEvent.isFromSource(4194304) ? motionEvent.getAxisValue(26) : 0.0f);
            n = Math.round(this.mVerticalScrollFactor * f);
            if (n != 0 && !this.trackMotionScroll(n, n)) {
                return true;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override
    public void onGlobalLayout() {
        if (this.isShown()) {
            PopupWindow popupWindow;
            if (this.mFiltered && (popupWindow = this.mPopup) != null && !popupWindow.isShowing() && !this.mPopupHidden) {
                this.showPopup();
            }
        } else {
            PopupWindow popupWindow = this.mPopup;
            if (popupWindow != null && popupWindow.isShowing()) {
                this.dismissPopup();
            }
        }
    }

    public void onInitializeAccessibilityNodeInfoForItem(View view, int n, AccessibilityNodeInfo accessibilityNodeInfo) {
        if (n == -1) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        boolean bl = layoutParams instanceof LayoutParams ? ((LayoutParams)layoutParams).isEnabled && this.isEnabled() : false;
        accessibilityNodeInfo.setEnabled(bl);
        if (n == this.getSelectedItemPosition()) {
            accessibilityNodeInfo.setSelected(true);
            this.addAccessibilityActionIfEnabled(accessibilityNodeInfo, bl, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_SELECTION);
        } else {
            this.addAccessibilityActionIfEnabled(accessibilityNodeInfo, bl, AccessibilityNodeInfo.AccessibilityAction.ACTION_SELECT);
        }
        if (this.isItemClickable(view)) {
            this.addAccessibilityActionIfEnabled(accessibilityNodeInfo, bl, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            accessibilityNodeInfo.setClickable(true);
        }
        if (this.isLongClickable()) {
            this.addAccessibilityActionIfEnabled(accessibilityNodeInfo, bl, AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            accessibilityNodeInfo.setLongClickable(true);
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.isEnabled()) {
            if (this.canScrollUp()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.canScrollDown()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.setScrollable(true);
            }
        }
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        accessibilityNodeInfo.setClickable(false);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null && fastScroller.onInterceptHoverEvent(motionEvent)) {
            return true;
        }
        return super.onInterceptHoverEvent(motionEvent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        Object object = this.mPositionScroller;
        if (object != null) {
            ((AbsPositionScroller)object).stop();
        }
        if (this.mIsDetaching || !this.isAttachedToWindow()) return false;
        object = this.mFastScroll;
        if (object != null && ((FastScroller)object).onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        if (n == 0) {
            n = this.mTouchMode;
            if (n != 6 && n != 5) {
                int n2 = (int)motionEvent.getX();
                int n3 = (int)motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                int n4 = this.findMotionRow(n3);
                if (n != 4 && n4 >= 0) {
                    this.mMotionViewOriginalTop = this.getChildAt(n4 - this.mFirstPosition).getTop();
                    this.mMotionX = n2;
                    this.mMotionY = n3;
                    this.mMotionPosition = n4;
                    this.mTouchMode = 0;
                    this.clearScrollingCache();
                }
                this.mLastY = Integer.MIN_VALUE;
                this.initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mNestedYOffset = 0;
                this.startNestedScroll(2);
                if (n != 4) return false;
                return true;
            }
        } else {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 6) return false;
                        this.onSecondaryPointerUp(motionEvent);
                        return false;
                    }
                } else {
                    int n5;
                    if (this.mTouchMode != 0) return false;
                    n = n5 = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (n5 == -1) {
                        n = 0;
                        this.mActivePointerId = motionEvent.getPointerId(0);
                    }
                    n5 = (int)motionEvent.getY(n);
                    this.initVelocityTrackerIfNotExists();
                    this.mVelocityTracker.addMovement(motionEvent);
                    if (!this.startScrollIfNeeded((int)motionEvent.getX(n), n5, null)) return false;
                    return true;
                }
            }
            this.mTouchMode = -1;
            this.mActivePointerId = -1;
            this.recycleVelocityTracker();
            this.reportScrollStateChange(0);
            this.stopNestedScroll();
            return false;
        }
        this.mMotionCorrection = 0;
        return true;
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent object) {
        if (KeyEvent.isConfirmKey(n)) {
            if (!this.isEnabled()) {
                return true;
            }
            if (this.isClickable() && this.isPressed() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount()) {
                object = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (object != null) {
                    this.performItemClick((View)object, this.mSelectedPosition, this.mSelectedRowId);
                    ((View)object).setPressed(false);
                }
                this.setPressed(false);
                return true;
            }
        }
        return super.onKeyUp(n, (KeyEvent)object);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mInLayout = true;
        n3 = this.getChildCount();
        if (bl) {
            for (n = 0; n < n3; ++n) {
                this.getChildAt(n).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        this.layoutChildren();
        this.mOverscrollMax = (n4 - n2) / 3;
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.onItemCountChanged(this.getChildCount(), this.mItemCount);
        }
        this.mInLayout = false;
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.mSelector == null) {
            this.useDefaultSelector();
        }
        Object object = this.mListPadding;
        ((Rect)object).left = this.mSelectionLeftPadding + this.mPaddingLeft;
        ((Rect)object).top = this.mSelectionTopPadding + this.mPaddingTop;
        ((Rect)object).right = this.mSelectionRightPadding + this.mPaddingRight;
        ((Rect)object).bottom = this.mSelectionBottomPadding + this.mPaddingBottom;
        n = this.mTranscriptMode;
        boolean bl = true;
        if (n == 1) {
            int n3 = this.getChildCount();
            n2 = this.getHeight() - this.getPaddingBottom();
            object = this.getChildAt(n3 - 1);
            n = object != null ? ((View)object).getBottom() : n2;
            if (this.mFirstPosition + n3 < this.mLastHandledItemCount || n > n2) {
                bl = false;
            }
            this.mForceTranscriptScroll = bl;
        }
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        int n = this.getChildCount();
        if (!bl && n > 0 && this.canScrollList((int)f2) && Math.abs(f2) > (float)this.mMinimumVelocity) {
            this.reportScrollStateChange(2);
            if (this.mFlingRunnable == null) {
                this.mFlingRunnable = new FlingRunnable();
            }
            if (!this.dispatchNestedPreFling(0.0f, f2)) {
                this.mFlingRunnable.start((int)f2);
            }
            return true;
        }
        return this.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        view = this.getChildAt(this.getChildCount() / 2);
        n = view != null ? view.getTop() : 0;
        if (view == null || this.trackMotionScroll(-n4, -n4)) {
            if (view != null) {
                n2 = view.getTop() - n;
                n = n4 - n2;
            } else {
                n = n4;
                n2 = 0;
            }
            this.dispatchNestedScroll(0, n2, 0, n, null);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        super.onNestedScrollAccepted(view, view2, n);
        this.startNestedScroll(2);
    }

    @Override
    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        if (this.mScrollY != n2) {
            this.onScrollChanged(this.mScrollX, n2, this.mScrollX, this.mScrollY);
            this.mScrollY = n2;
            this.invalidateParentIfNeeded();
            this.awakenScrollBars();
        }
    }

    @Override
    public boolean onRemoteAdapterConnected() {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != this.mAdapter) {
            this.setAdapter(remoteViewsAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
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
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        Object object = this.mFastScroll;
        if (object != null && (object = ((FastScroller)object).onResolvePointerIcon(motionEvent, n)) != null) {
            return object;
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)object;
        super.onRestoreInstanceState(((AbsSavedState)object).getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = ((SavedState)object).height;
        if (((SavedState)object).selectedId >= 0L) {
            this.mNeedSync = true;
            this.mPendingSync = object;
            this.mSyncRowId = ((SavedState)object).selectedId;
            this.mSyncPosition = ((SavedState)object).position;
            this.mSpecificTop = ((SavedState)object).viewTop;
            this.mSyncMode = 0;
        } else if (((SavedState)object).firstId >= 0L) {
            this.setSelectedPositionInt(-1);
            this.setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = object;
            this.mSyncRowId = ((SavedState)object).firstId;
            this.mSyncPosition = ((SavedState)object).position;
            this.mSpecificTop = ((SavedState)object).viewTop;
            this.mSyncMode = 1;
        }
        this.setFilterText(((SavedState)object).filter);
        if (((SavedState)object).checkState != null) {
            this.mCheckStates = ((SavedState)object).checkState;
        }
        if (((SavedState)object).checkIdState != null) {
            this.mCheckedIdStates = ((SavedState)object).checkIdState;
        }
        this.mCheckedItemCount = ((SavedState)object).checkedItemCount;
        if (((SavedState)object).inActionMode && this.mChoiceMode == 3 && (object = this.mMultiChoiceModeCallback) != null) {
            this.mChoiceActionMode = this.startActionMode((ActionMode.Callback)object);
        }
        this.requestLayout();
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollbarPosition(this.getVerticalScrollbarPosition());
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        long l;
        int n;
        this.dismissPopup();
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.mPendingSync;
        if (object != null) {
            savedState.selectedId = ((SavedState)object).selectedId;
            savedState.firstId = this.mPendingSync.firstId;
            savedState.viewTop = this.mPendingSync.viewTop;
            savedState.position = this.mPendingSync.position;
            savedState.height = this.mPendingSync.height;
            savedState.filter = this.mPendingSync.filter;
            savedState.inActionMode = this.mPendingSync.inActionMode;
            savedState.checkedItemCount = this.mPendingSync.checkedItemCount;
            savedState.checkState = this.mPendingSync.checkState;
            savedState.checkIdState = this.mPendingSync.checkIdState;
            return savedState;
        }
        int n2 = this.getChildCount();
        boolean bl = true;
        n2 = n2 > 0 && this.mItemCount > 0 ? 1 : 0;
        savedState.selectedId = l = this.getSelectedItemId();
        savedState.height = this.getHeight();
        if (l >= 0L) {
            savedState.viewTop = this.mSelectedTop;
            savedState.position = this.getSelectedItemPosition();
            savedState.firstId = -1L;
        } else if (n2 != 0 && this.mFirstPosition > 0) {
            savedState.viewTop = this.getChildAt(0).getTop();
            n2 = n = this.mFirstPosition;
            if (n >= this.mItemCount) {
                n2 = this.mItemCount - 1;
            }
            savedState.position = n2;
            savedState.firstId = this.mAdapter.getItemId(n2);
        } else {
            savedState.viewTop = 0;
            savedState.firstId = -1L;
            savedState.position = 0;
        }
        savedState.filter = null;
        if (this.mFiltered && (object = this.mTextFilter) != null && (object = ((EditText)object).getText()) != null) {
            savedState.filter = object.toString();
        }
        if (this.mChoiceMode != 3 || this.mChoiceActionMode == null) {
            bl = false;
        }
        savedState.inActionMode = bl;
        object = this.mCheckStates;
        if (object != null) {
            savedState.checkState = ((SparseBooleanArray)object).clone();
        }
        if (this.mCheckedIdStates != null) {
            object = new LongSparseArray<E>();
            n = this.mCheckedIdStates.size();
            for (n2 = 0; n2 < n; ++n2) {
                ((LongSparseArray)object).put(this.mCheckedIdStates.keyAt(n2), this.mCheckedIdStates.valueAt(n2));
            }
            savedState.checkIdState = object;
        }
        savedState.checkedItemCount = this.mCheckedItemCount;
        object = this.mRemoteAdapter;
        if (object != null) {
            ((RemoteViewsAdapter)object).saveRemoteViewsCache();
        }
        return savedState;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this.handleBoundsChange();
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.onSizeChanged(n, n2, n3, n4);
        }
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        boolean bl = (n & 2) != 0;
        return bl;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        if (this.isTextFilterEnabled()) {
            this.createTextFilter(true);
            n = charSequence.length();
            boolean bl = this.mPopup.isShowing();
            if (!bl && n > 0) {
                this.showPopup();
                this.mFiltered = true;
            } else if (bl && n == 0) {
                this.dismissPopup();
                this.mFiltered = false;
            }
            Object object = this.mAdapter;
            if (object instanceof Filterable) {
                if ((object = ((Filterable)object).getFilter()) != null) {
                    ((Filter)object).filter(charSequence, this);
                } else {
                    throw new IllegalStateException("You cannot call onTextChanged with a non filterable adapter");
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent object) {
        boolean bl = this.isEnabled();
        boolean bl2 = false;
        if (!bl) {
            if (this.isClickable() || this.isLongClickable()) {
                bl2 = true;
            }
            return bl2;
        }
        Object object2 = this.mPositionScroller;
        if (object2 != null) {
            ((AbsPositionScroller)object2).stop();
        }
        if (!this.mIsDetaching && this.isAttachedToWindow()) {
            this.startNestedScroll(2);
            object2 = this.mFastScroll;
            if (object2 != null && ((FastScroller)object2).onTouchEvent((MotionEvent)object)) {
                return true;
            }
            this.initVelocityTrackerIfNotExists();
            object2 = MotionEvent.obtain((MotionEvent)object);
            int n = ((MotionEvent)object).getActionMasked();
            if (n == 0) {
                this.mNestedYOffset = 0;
            }
            ((MotionEvent)object2).offsetLocation(0.0f, this.mNestedYOffset);
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 5) {
                                if (n == 6) {
                                    this.onSecondaryPointerUp((MotionEvent)object);
                                    int n2 = this.mMotionX;
                                    n = this.mMotionY;
                                    n2 = this.pointToPosition(n2, n);
                                    if (n2 >= 0) {
                                        this.mMotionViewOriginalTop = this.getChildAt(n2 - this.mFirstPosition).getTop();
                                        this.mMotionPosition = n2;
                                    }
                                    this.mLastY = n;
                                }
                            } else {
                                int n3 = ((MotionEvent)object).getActionIndex();
                                int n4 = ((MotionEvent)object).getPointerId(n3);
                                n = (int)((MotionEvent)object).getX(n3);
                                n3 = (int)((MotionEvent)object).getY(n3);
                                this.mMotionCorrection = 0;
                                this.mActivePointerId = n4;
                                this.mMotionX = n;
                                this.mMotionY = n3;
                                if ((n = this.pointToPosition(n, n3)) >= 0) {
                                    this.mMotionViewOriginalTop = this.getChildAt(n - this.mFirstPosition).getTop();
                                    this.mMotionPosition = n;
                                }
                                this.mLastY = n3;
                            }
                        } else {
                            this.onTouchCancel();
                        }
                    } else {
                        this.onTouchMove((MotionEvent)object, (MotionEvent)object2);
                    }
                } else {
                    this.onTouchUp((MotionEvent)object);
                }
            } else {
                this.onTouchDown((MotionEvent)object);
            }
            object = this.mVelocityTracker;
            if (object != null) {
                ((VelocityTracker)object).addMovement((MotionEvent)object2);
            }
            ((MotionEvent)object2).recycle();
            return true;
        }
        return false;
    }

    @Override
    public void onTouchModeChanged(boolean bl) {
        if (bl) {
            this.hideSelector();
            if (this.getHeight() > 0 && this.getChildCount() > 0) {
                this.layoutChildren();
            }
            this.updateSelectorState();
        } else {
            int n = this.mTouchMode;
            if (n == 5 || n == 6) {
                Object object = this.mFlingRunnable;
                if (object != null) {
                    ((FlingRunnable)object).endFling();
                }
                if ((object = this.mPositionScroller) != null) {
                    ((AbsPositionScroller)object).stop();
                }
                if (this.mScrollY != 0) {
                    this.mScrollY = 0;
                    this.invalidateParentCaches();
                    this.finishGlows();
                    this.invalidate();
                }
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        int n = this.isInTouchMode() ^ true;
        if (!bl) {
            this.setChildrenDrawingCacheEnabled(false);
            Object object = this.mFlingRunnable;
            if (object != null) {
                this.removeCallbacks((Runnable)object);
                this.mFlingRunnable.mSuppressIdleStateChangeCall = false;
                this.mFlingRunnable.endFling();
                object = this.mPositionScroller;
                if (object != null) {
                    ((AbsPositionScroller)object).stop();
                }
                if (this.mScrollY != 0) {
                    this.mScrollY = 0;
                    this.invalidateParentCaches();
                    this.finishGlows();
                    this.invalidate();
                }
            }
            this.dismissPopup();
            if (n == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else {
            int n2;
            if (this.mFiltered && !this.mPopupHidden) {
                this.showPopup();
            }
            if (n != (n2 = this.mLastTouchMode) && n2 != -1) {
                if (n == 1) {
                    this.resurrectSelection();
                } else {
                    this.hideSelector();
                    this.mLayoutMode = 0;
                    this.layoutChildren();
                }
            }
        }
        this.mLastTouchMode = n;
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n != 4096) {
            if (n != 8192 && n != 16908344) {
                if (n != 16908346) {
                    return false;
                }
            } else {
                if (this.isEnabled() && this.canScrollUp()) {
                    this.smoothScrollBy(-(this.getHeight() - this.mListPadding.top - this.mListPadding.bottom), 200);
                    return true;
                }
                return false;
            }
        }
        if (this.isEnabled() && this.canScrollDown()) {
            this.smoothScrollBy(this.getHeight() - this.mListPadding.top - this.mListPadding.bottom, 200);
            return true;
        }
        return false;
    }

    @Override
    public boolean performItemClick(View view, int n, long l) {
        boolean bl = false;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = true;
        int n2 = this.mChoiceMode;
        if (n2 != 0) {
            boolean bl6 = false;
            if (n2 != 2 && (n2 != 3 || this.mChoiceActionMode == null)) {
                bl4 = bl5;
                bl5 = bl6;
                if (this.mChoiceMode == 1) {
                    if (this.mCheckStates.get(n, false) ^ true) {
                        this.mCheckStates.clear();
                        this.mCheckStates.put(n, true);
                        if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                            this.mCheckedIdStates.clear();
                            this.mCheckedIdStates.put(this.mAdapter.getItemId(n), n);
                        }
                        this.mCheckedItemCount = 1;
                    } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                        this.mCheckedItemCount = 0;
                    }
                    bl5 = true;
                    bl4 = bl3;
                }
            } else {
                bl = this.mCheckStates.get(n, false) ^ true;
                this.mCheckStates.put(n, bl);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (bl) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(n), n);
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(n));
                    }
                }
                this.mCheckedItemCount = bl ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
                ActionMode actionMode = this.mChoiceActionMode;
                bl4 = bl2;
                if (actionMode != null) {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(actionMode, n, l, bl);
                    bl4 = false;
                }
                bl5 = true;
            }
            if (bl5) {
                this.updateOnScreenCheckedViews();
            }
            bl = true;
        }
        boolean bl7 = bl;
        if (bl4) {
            bl7 = bl | super.performItemClick(view, n, l);
        }
        return bl7;
    }

    @UnsupportedAppUsage
    boolean performLongPress(View view, int n, long l) {
        return this.performLongPress(view, n, l, -1.0f, -1.0f);
    }

    @UnsupportedAppUsage
    boolean performLongPress(View object, int n, long l, float f, float f2) {
        if (this.mChoiceMode == 3) {
            if (this.mChoiceActionMode == null) {
                this.mChoiceActionMode = object = this.startActionMode(this.mMultiChoiceModeCallback);
                if (object != null) {
                    this.setItemChecked(n, true);
                    this.performHapticFeedback(0);
                }
            }
            return true;
        }
        boolean bl = false;
        if (this.mOnItemLongClickListener != null) {
            bl = this.mOnItemLongClickListener.onItemLongClick(this, (View)object, n, l);
        }
        boolean bl2 = bl;
        if (!bl) {
            this.mContextMenuInfo = this.createContextMenuInfo((View)object, n, l);
            bl2 = f != -1.0f && f2 != -1.0f ? super.showContextMenuForChild(this, f, f2) : super.showContextMenuForChild(this);
        }
        if (bl2) {
            this.performHapticFeedback(0);
        }
        return bl2;
    }

    public int pointToPosition(int n, int n2) {
        Object object = this.mTouchFrame;
        Rect rect = object;
        if (object == null) {
            rect = this.mTouchFrame = new Rect();
        }
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            object = this.getChildAt(i);
            if (((View)object).getVisibility() != 0) continue;
            ((View)object).getHitRect(rect);
            if (!rect.contains(n, n2)) continue;
            return this.mFirstPosition + i;
        }
        return -1;
    }

    public long pointToRowId(int n, int n2) {
        if ((n = this.pointToPosition(n, n2)) >= 0) {
            return this.mAdapter.getItemId(n);
        }
        return Long.MIN_VALUE;
    }

    void positionSelector(int n, View view) {
        this.positionSelector(n, view, false, -1.0f, -1.0f);
    }

    void positionSelectorLikeFocus(int n, View view) {
        if (this.mSelector != null && this.mSelectorPosition != n && n != -1) {
            Rect rect = this.mSelectorRect;
            this.positionSelector(n, view, true, rect.exactCenterX(), rect.exactCenterY());
        } else {
            this.positionSelector(n, view);
        }
    }

    void positionSelectorLikeTouch(int n, View view, float f, float f2) {
        this.positionSelector(n, view, true, f, f2);
    }

    public void reclaimViews(List<View> list) {
        int n = this.getChildCount();
        RecyclerListener recyclerListener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams == null || !this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) continue;
            list.add(view);
            view.setAccessibilityDelegate(null);
            if (recyclerListener == null) continue;
            recyclerListener.onMovedToScrapHeap(view);
        }
        this.mRecycler.reclaimScrapViews(list);
        this.removeAllViewsInLayout();
    }

    int reconcileSelectedPosition() {
        int n;
        int n2 = n = this.mSelectedPosition;
        if (n < 0) {
            n2 = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, n2), this.mItemCount - 1);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769710L)
    void reportScrollStateChange(int n) {
        OnScrollListener onScrollListener;
        if (n != this.mLastScrollState && (onScrollListener = this.mOnScrollListener) != null) {
            this.mLastScrollState = n;
            onScrollListener.onScrollStateChanged(this, n);
        }
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    @Override
    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    void requestLayoutIfNecessary() {
        if (this.getChildCount() > 0) {
            this.resetList();
            this.requestLayout();
            this.invalidate();
        }
    }

    void resetList() {
        this.removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mPositionScrollAfterLayout = null;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.setSelectedPositionInt(-1);
        this.setNextSelectedPositionInt(-1);
        this.mSelectedTop = 0;
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        this.invalidate();
    }

    boolean resurrectSelection() {
        int n;
        int n2;
        Object object;
        int n3;
        boolean bl;
        block21 : {
            int n4;
            int n5;
            int n6;
            int n7;
            block23 : {
                block22 : {
                    n7 = this.getChildCount();
                    if (n7 <= 0) {
                        return false;
                    }
                    n4 = 0;
                    n = 0;
                    n6 = this.mListPadding.top;
                    n5 = this.mBottom - this.mTop - this.mListPadding.bottom;
                    n2 = this.mFirstPosition;
                    n3 = this.mResurrectToPosition;
                    bl = true;
                    if (n3 < n2 || n3 >= n2 + n7) break block22;
                    object = this.getChildAt(n3 - this.mFirstPosition);
                    n = ((View)object).getTop();
                    n4 = ((View)object).getBottom();
                    if (n < n6) {
                        n = n6 + this.getVerticalFadingEdgeLength();
                    } else if (n4 > n5) {
                        n = n5 - ((View)object).getMeasuredHeight() - this.getVerticalFadingEdgeLength();
                    }
                    break block21;
                }
                if (n3 >= n2) break block23;
                n4 = n2;
                int n8 = 0;
                do {
                    block24 : {
                        int n9;
                        block25 : {
                            n5 = n;
                            n3 = n4;
                            if (n8 >= n7) break;
                            n3 = this.getChildAt(n8).getTop();
                            n5 = n6;
                            if (n8 != 0) break block24;
                            n9 = n3;
                            if (n2 > 0) break block25;
                            n = n9;
                            n5 = n6;
                            if (n3 >= n6) break block24;
                        }
                        n5 = n6 + this.getVerticalFadingEdgeLength();
                        n = n9;
                    }
                    if (n3 >= n5) {
                        n = n2 + n8;
                        n5 = n3;
                        n3 = n;
                        break;
                    }
                    ++n8;
                    n6 = n5;
                } while (true);
                n = n5;
                break block21;
            }
            int n10 = this.mItemCount;
            bl = false;
            n = n4;
            for (n3 = n7 - 1; n3 >= 0; --n3) {
                int n11;
                int n12;
                block26 : {
                    block27 : {
                        object = this.getChildAt(n3);
                        n4 = ((View)object).getTop();
                        n11 = ((View)object).getBottom();
                        n12 = n5;
                        if (n3 != n7 - 1) break block26;
                        n6 = n4;
                        if (n2 + n7 < n10) break block27;
                        n = n6;
                        n12 = n5;
                        if (n11 <= n5) break block26;
                    }
                    n12 = n5 - this.getVerticalFadingEdgeLength();
                    n = n6;
                }
                if (n11 <= n12) {
                    n = n4;
                    n3 = n2 + n3;
                    break block21;
                }
                n5 = n12;
            }
            n3 = n2 + n7 - 1;
        }
        this.mResurrectToPosition = -1;
        this.removeCallbacks(this.mFlingRunnable);
        object = this.mPositionScroller;
        if (object != null) {
            ((AbsPositionScroller)object).stop();
        }
        this.mTouchMode = -1;
        this.clearScrollingCache();
        this.mSpecificTop = n;
        n = this.lookForSelectablePosition(n3, bl);
        if (n >= n2 && n <= this.getLastVisiblePosition()) {
            this.mLayoutMode = 4;
            this.updateSelectorState();
            this.setSelectionInt(n);
            this.invokeOnItemScrollListener();
        } else {
            n = -1;
        }
        this.reportScrollStateChange(0);
        bl = n >= 0;
        return bl;
    }

    @UnsupportedAppUsage
    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition < 0 && this.resurrectSelection()) {
            this.updateSelectorState();
            return true;
        }
        return false;
    }

    public void scrollListBy(int n) {
        this.trackMotionScroll(-n, -n);
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            int n = this.getFirstVisiblePosition();
            int n2 = this.getLastVisiblePosition();
            if (this.mLastAccessibilityScrollEventFromIndex == n && this.mLastAccessibilityScrollEventToIndex == n2) {
                return;
            }
            this.mLastAccessibilityScrollEventFromIndex = n;
            this.mLastAccessibilityScrollEventToIndex = n2;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean sendToTextFilter(int var1_1, int var2_2, KeyEvent var3_3) {
        if (!this.acceptFilter()) {
            return false;
        }
        var4_4 = false;
        var5_5 = false;
        var6_6 = false;
        var7_7 = true;
        if (var1_1 != 4) {
            if (var1_1 != 62) {
                if (var1_1 != 66) {
                    switch (var1_1) {
                        default: {
                            var4_4 = var7_7;
                            ** break;
                        }
                        case 19: 
                        case 20: 
                        case 21: 
                        case 22: 
                        case 23: 
                    }
                }
                var4_4 = false;
                ** break;
lbl17: // 2 sources:
            } else {
                var4_4 = this.mFiltered;
            }
        } else {
            var7_7 = var4_4;
            if (this.mFiltered) {
                var8_8 = this.mPopup;
                var7_7 = var4_4;
                if (var8_8 != null) {
                    var7_7 = var4_4;
                    if (var8_8.isShowing()) {
                        if (var3_3.getAction() == 0 && var3_3.getRepeatCount() == 0) {
                            var8_8 = this.getKeyDispatcherState();
                            if (var8_8 != null) {
                                var8_8.startTracking(var3_3, this);
                            }
                            var7_7 = true;
                        } else {
                            var7_7 = var6_6;
                            if (var3_3.getAction() == 1) {
                                var7_7 = var4_4;
                                if (var3_3.isTracking()) {
                                    var7_7 = var4_4;
                                    if (!var3_3.isCanceled()) {
                                        var7_7 = true;
                                        this.mTextFilter.setText("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            var4_4 = false;
            var5_5 = var7_7;
        }
        var7_7 = var5_5;
        if (var4_4 == false) return var7_7;
        this.createTextFilter(true);
        var9_9 = var3_3;
        var8_8 = var9_9;
        if (var9_9.getRepeatCount() > 0) {
            var8_8 = KeyEvent.changeTimeRepeat(var3_3, var3_3.getEventTime(), 0);
        }
        if ((var10_10 = var3_3.getAction()) == 0) {
            return this.mTextFilter.onKeyDown(var1_1, (KeyEvent)var8_8);
        }
        if (var10_10 == 1) {
            return this.mTextFilter.onKeyUp(var1_1, (KeyEvent)var8_8);
        }
        if (var10_10 == 2) return this.mTextFilter.onKeyMultiple(var1_1, var2_2, var3_3);
        return var5_5;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter != null) {
            this.mAdapterHasStableIds = this.mAdapter.hasStableIds();
            if (this.mChoiceMode != 0 && this.mAdapterHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray<E>();
            }
        }
        this.clearChoices();
    }

    public void setBottomEdgeEffectColor(int n) {
        this.mEdgeGlowBottom.setColor(n);
        this.invalidateBottomGlow();
    }

    public void setCacheColorHint(int n) {
        if (n != this.mCacheColorHint) {
            this.mCacheColorHint = n;
            int n2 = this.getChildCount();
            for (int i = 0; i < n2; ++i) {
                this.getChildAt(i).setDrawingCacheBackgroundColor(n);
            }
            this.mRecycler.setCacheColorHint(n);
        }
    }

    public void setChoiceMode(int n) {
        this.mChoiceMode = n;
        Object object = this.mChoiceActionMode;
        if (object != null) {
            ((ActionMode)object).finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray(0);
            }
            if (this.mCheckedIdStates == null && (object = this.mAdapter) != null && object.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray<E>(0);
            }
            if (this.mChoiceMode == 3) {
                this.clearChoices();
                this.setLongClickable(true);
            }
        }
    }

    public void setDrawSelectorOnTop(boolean bl) {
        this.mDrawSelectorOnTop = bl;
    }

    public void setEdgeEffectColor(int n) {
        this.setTopEdgeEffectColor(n);
        this.setBottomEdgeEffectColor(n);
    }

    public void setFastScrollAlwaysVisible(final boolean bl) {
        if (this.mFastScrollAlwaysVisible != bl) {
            if (bl && !this.mFastScrollEnabled) {
                this.setFastScrollEnabled(true);
            }
            this.mFastScrollAlwaysVisible = bl;
            if (this.isOwnerThread()) {
                this.setFastScrollerAlwaysVisibleUiThread(bl);
            } else {
                this.post(new Runnable(){

                    @Override
                    public void run() {
                        AbsListView.this.setFastScrollerAlwaysVisibleUiThread(bl);
                    }
                });
            }
        }
    }

    public void setFastScrollEnabled(final boolean bl) {
        if (this.mFastScrollEnabled != bl) {
            this.mFastScrollEnabled = bl;
            if (this.isOwnerThread()) {
                this.setFastScrollerEnabledUiThread(bl);
            } else {
                this.post(new Runnable(){

                    @Override
                    public void run() {
                        AbsListView.this.setFastScrollerEnabledUiThread(bl);
                    }
                });
            }
        }
    }

    public void setFastScrollStyle(int n) {
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller == null) {
            this.mFastScrollStyle = n;
        } else {
            fastScroller.setStyle(n);
        }
    }

    public void setFilterText(String string2) {
        if (this.mTextFilterEnabled && !TextUtils.isEmpty(string2)) {
            this.createTextFilter(false);
            this.mTextFilter.setText(string2);
            this.mTextFilter.setSelection(string2.length());
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter instanceof Filterable) {
                if (this.mPopup == null) {
                    ((Filterable)((Object)listAdapter)).getFilter().filter(string2);
                }
                this.mFiltered = true;
                this.mDataSetObserver.clearSavedState();
            }
        }
    }

    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        if (bl) {
            PopupWindow popupWindow;
            n = this.getWindowVisibility() == 0 ? 1 : 0;
            if (this.mFiltered && n != 0 && (popupWindow = this.mPopup) != null && popupWindow.isShowing()) {
                this.positionPopup();
            }
        }
        return bl;
    }

    public void setFriction(float f) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(f);
    }

    public void setItemChecked(int n, boolean bl) {
        block18 : {
            int n2;
            block16 : {
                block19 : {
                    block17 : {
                        n2 = this.mChoiceMode;
                        if (n2 == 0) {
                            return;
                        }
                        if (bl && n2 == 3 && this.mChoiceActionMode == null) {
                            MultiChoiceModeWrapper multiChoiceModeWrapper = this.mMultiChoiceModeCallback;
                            if (multiChoiceModeWrapper != null && multiChoiceModeWrapper.hasWrappedCallback()) {
                                this.mChoiceActionMode = this.startActionMode(this.mMultiChoiceModeCallback);
                            } else {
                                throw new IllegalStateException("AbsListView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
                            }
                        }
                        int n3 = this.mChoiceMode;
                        n2 = 0;
                        if (n3 == 2 || n3 == 3) break block16;
                        n3 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds() ? 1 : 0;
                        n2 = this.isItemChecked(n) != bl ? 1 : 0;
                        if (bl || this.isItemChecked(n)) {
                            this.mCheckStates.clear();
                            if (n3 != 0) {
                                this.mCheckedIdStates.clear();
                            }
                        }
                        if (!bl) break block17;
                        this.mCheckStates.put(n, true);
                        if (n3 != 0) {
                            this.mCheckedIdStates.put(this.mAdapter.getItemId(n), n);
                        }
                        this.mCheckedItemCount = 1;
                        n = n2;
                        break block18;
                    }
                    if (this.mCheckStates.size() == 0) break block19;
                    n = n2;
                    if (this.mCheckStates.valueAt(0)) break block18;
                }
                this.mCheckedItemCount = 0;
                n = n2;
                break block18;
            }
            boolean bl2 = this.mCheckStates.get(n);
            this.mCheckStates.put(n, bl);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (bl) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(n), n);
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(n));
                }
            }
            if (bl2 != bl) {
                n2 = 1;
            }
            if (n2 != 0) {
                this.mCheckedItemCount = bl ? ++this.mCheckedItemCount : --this.mCheckedItemCount;
            }
            if (this.mChoiceActionMode != null) {
                long l = this.mAdapter.getItemId(n);
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, n, l, bl);
            }
            n = n2;
        }
        if (!this.mInLayout && !this.mBlockLayoutRequests && n != 0) {
            this.mDataChanged = true;
            this.rememberSyncState();
            this.requestLayout();
        }
    }

    public void setMultiChoiceModeListener(MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(multiChoiceModeListener);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        this.invokeOnItemScrollListener();
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecycler.mRecyclerListener = recyclerListener;
    }

    public void setRemoteViewsAdapter(Intent intent) {
        this.setRemoteViewsAdapter(intent, false);
    }

    @Override
    public void setRemoteViewsAdapter(Intent intent, boolean bl) {
        if (this.mRemoteAdapter != null && new Intent.FilterComparison(intent).equals(new Intent.FilterComparison(this.mRemoteAdapter.getRemoteViewsServiceIntent()))) {
            return;
        }
        this.mDeferNotifyDataSetChanged = false;
        this.mRemoteAdapter = new RemoteViewsAdapter(this.getContext(), intent, this, bl);
        if (this.mRemoteAdapter.isDataReady()) {
            this.setAdapter(this.mRemoteAdapter);
        }
    }

    public Runnable setRemoteViewsAdapterAsync(Intent intent) {
        return new RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    public void setRemoteViewsOnClickHandler(RemoteViews.OnClickHandler onClickHandler) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setRemoteViewsOnClickHandler(onClickHandler);
        }
    }

    @Override
    public void setScrollBarStyle(int n) {
        super.setScrollBarStyle(n);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollBarStyle(n);
        }
    }

    public void setScrollIndicators(View view, View view2) {
        this.mScrollUp = view;
        this.mScrollDown = view2;
    }

    public void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled && !bl) {
            this.clearScrollingCache();
        }
        this.mScrollingCacheEnabled = bl;
    }

    public void setSelectionFromTop(int n, int n2) {
        if (this.mAdapter == null) {
            return;
        }
        if (!this.isInTouchMode()) {
            int n3;
            n = n3 = this.lookForSelectablePosition(n, true);
            if (n3 >= 0) {
                this.setNextSelectedPositionInt(n3);
                n = n3;
            }
        } else {
            this.mResurrectToPosition = n;
        }
        if (n >= 0) {
            AbsPositionScroller absPositionScroller;
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mListPadding.top + n2;
            if (this.mNeedSync) {
                this.mSyncPosition = n;
                this.mSyncRowId = this.mAdapter.getItemId(n);
            }
            if ((absPositionScroller = this.mPositionScroller) != null) {
                absPositionScroller.stop();
            }
            this.requestLayout();
        }
    }

    abstract void setSelectionInt(int var1);

    public void setSelector(int n) {
        this.setSelector(this.getContext().getDrawable(n));
    }

    public void setSelector(Drawable drawable2) {
        Object object = this.mSelector;
        if (object != null) {
            ((Drawable)object).setCallback(null);
            this.unscheduleDrawable(this.mSelector);
        }
        this.mSelector = drawable2;
        object = new Rect();
        drawable2.getPadding((Rect)object);
        this.mSelectionLeftPadding = ((Rect)object).left;
        this.mSelectionTopPadding = ((Rect)object).top;
        this.mSelectionRightPadding = ((Rect)object).right;
        this.mSelectionBottomPadding = ((Rect)object).bottom;
        drawable2.setCallback(this);
        this.updateSelectorState();
    }

    public void setSmoothScrollbarEnabled(boolean bl) {
        this.mSmoothScrollbarEnabled = bl;
    }

    public void setStackFromBottom(boolean bl) {
        if (this.mStackFromBottom != bl) {
            this.mStackFromBottom = bl;
            this.requestLayoutIfNecessary();
        }
    }

    public void setTextFilterEnabled(boolean bl) {
        this.mTextFilterEnabled = bl;
    }

    public void setTopEdgeEffectColor(int n) {
        this.mEdgeGlowTop.setColor(n);
        this.invalidateTopGlow();
    }

    public void setTranscriptMode(int n) {
        this.mTranscriptMode = n;
    }

    public void setVelocityScale(float f) {
        this.mVelocityScale = f;
    }

    @Override
    public void setVerticalScrollbarPosition(int n) {
        super.setVerticalScrollbarPosition(n);
        FastScroller fastScroller = this.mFastScroll;
        if (fastScroller != null) {
            fastScroller.setScrollbarPosition(n);
        }
    }

    void setVisibleRangeHint(int n, int n2) {
        RemoteViewsAdapter remoteViewsAdapter = this.mRemoteAdapter;
        if (remoteViewsAdapter != null) {
            remoteViewsAdapter.setVisibleRangeHint(n, n2);
        }
    }

    public final boolean shouldDrawSelector() {
        return this.mSelectorRect.isEmpty() ^ true;
    }

    boolean shouldShowSelector() {
        boolean bl = this.isFocused() && !this.isInTouchMode() || this.touchModeDrawsInPressedState() && this.isPressed();
        return bl;
    }

    @Override
    public boolean showContextMenu() {
        return this.showContextMenuInternal(0.0f, 0.0f, false);
    }

    @Override
    public boolean showContextMenu(float f, float f2) {
        return this.showContextMenuInternal(f, f2, true);
    }

    @Override
    public boolean showContextMenuForChild(View view) {
        if (this.isShowingContextMenuWithCoords()) {
            return false;
        }
        return this.showContextMenuForChildInternal(view, 0.0f, 0.0f, false);
    }

    @Override
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return this.showContextMenuForChildInternal(view, f, f2, true);
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, false, false);
    }

    @UnsupportedAppUsage
    void smoothScrollBy(int n, int n2, boolean bl, boolean bl2) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        int n3 = this.mFirstPosition;
        int n4 = this.getChildCount();
        int n5 = this.getPaddingTop();
        int n6 = this.getHeight();
        int n7 = this.getPaddingBottom();
        if (!(n == 0 || this.mItemCount == 0 || n4 == 0 || n3 == 0 && this.getChildAt(0).getTop() == n5 && n < 0 || n3 + n4 == this.mItemCount && this.getChildAt(n4 - 1).getBottom() == n6 - n7 && n > 0)) {
            this.reportScrollStateChange(2);
            this.mFlingRunnable.startScroll(n, n2, bl, bl2);
        } else {
            this.mFlingRunnable.endFling();
            AbsPositionScroller absPositionScroller = this.mPositionScroller;
            if (absPositionScroller != null) {
                absPositionScroller.stop();
            }
        }
    }

    void smoothScrollByOffset(int n) {
        View view;
        int n2 = -1;
        if (n < 0) {
            n2 = this.getFirstVisiblePosition();
        } else if (n > 0) {
            n2 = this.getLastVisiblePosition();
        }
        if (n2 > -1 && (view = this.getChildAt(n2 - this.getFirstVisiblePosition())) != null) {
            Rect rect = new Rect();
            int n3 = n2;
            if (view.getGlobalVisibleRect(rect)) {
                int n4 = view.getWidth();
                n3 = view.getHeight();
                float f = (float)(rect.width() * rect.height()) / (float)(n4 * n3);
                if (n < 0 && f < 0.75f) {
                    n3 = n2 + 1;
                } else {
                    n3 = n2;
                    if (n > 0) {
                        n3 = n2;
                        if (f < 0.75f) {
                            n3 = n2 - 1;
                        }
                    }
                }
            }
            this.smoothScrollToPosition(Math.max(0, Math.min(this.getCount(), n3 + n)));
        }
    }

    public void smoothScrollToPosition(int n) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = this.createPositionScroller();
        }
        this.mPositionScroller.start(n);
    }

    public void smoothScrollToPosition(int n, int n2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = this.createPositionScroller();
        }
        this.mPositionScroller.start(n, n2);
    }

    public void smoothScrollToPositionFromTop(int n, int n2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = this.createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(n, n2);
    }

    public void smoothScrollToPositionFromTop(int n, int n2, int n3) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = this.createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(n, n2, n3);
    }

    boolean touchModeDrawsInPressedState() {
        int n = this.mTouchMode;
        return n == 1 || n == 2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124051739L)
    boolean trackMotionScroll(int n, int n2) {
        int n3 = this.getChildCount();
        if (n3 == 0) {
            return true;
        }
        int n4 = this.getChildAt(0).getTop();
        int n5 = this.getChildAt(n3 - 1).getBottom();
        Object object = this.mListPadding;
        int n6 = 0;
        int n7 = 0;
        if ((this.mGroupFlags & 34) == 34) {
            n6 = ((Rect)object).top;
            n7 = ((Rect)object).bottom;
        }
        int n8 = this.getHeight() - n7;
        n7 = this.getHeight() - this.mPaddingBottom - this.mPaddingTop;
        int n9 = n < 0 ? Math.max(-(n7 - 1), n) : Math.min(n7 - 1, n);
        int n10 = n2 < 0 ? Math.max(-(n7 - 1), n2) : Math.min(n7 - 1, n2);
        int n11 = this.mFirstPosition;
        this.mFirstPositionDistanceGuess = n11 == 0 ? n4 - ((Rect)object).top : (this.mFirstPositionDistanceGuess += n10);
        this.mLastPositionDistanceGuess = n11 + n3 == this.mItemCount ? ((Rect)object).bottom + n5 : (this.mLastPositionDistanceGuess += n10);
        n = n11 == 0 && n4 >= ((Rect)object).top && n10 >= 0 ? 1 : 0;
        n2 = n11 + n3 == this.mItemCount && n5 <= this.getHeight() - ((Rect)object).bottom && n10 <= 0 ? 1 : 0;
        if (n == 0 && n2 == 0) {
            boolean bl = n10 < 0;
            boolean bl2 = this.isInTouchMode();
            if (bl2) {
                this.hideSelector();
            }
            int n12 = this.getHeaderViewsCount();
            int n13 = this.mItemCount - this.getFooterViewsCount();
            n7 = 0;
            n2 = 0;
            n = 0;
            if (bl) {
                n2 = n7 = -n10;
                if ((this.mGroupFlags & 34) == 34) {
                    n2 = n7 + ((Rect)object).top;
                }
                n7 = n8;
                for (int i = 0; i < n3 && ((View)(object = this.getChildAt(i))).getBottom() < n2; ++i) {
                    ++n;
                    int n14 = n11 + i;
                    if (n14 < n12 || n14 >= n13) continue;
                    ((View)object).clearAccessibilityFocus();
                    this.mRecycler.addScrapView((View)object, n14);
                }
                n7 = 0;
                n2 = n;
                n = n7;
            } else {
                int n15 = n = this.getHeight() - n10;
                if ((this.mGroupFlags & 34) == 34) {
                    n15 = n - ((Rect)object).bottom;
                }
                int n16 = n3 - 1;
                n = n2;
                for (n2 = n16; n2 >= 0 && ((View)(object = this.getChildAt(n2))).getTop() > n15; --n2) {
                    n7 = n2;
                    ++n;
                    n16 = n11 + n2;
                    if (n16 < n12 || n16 >= n13) continue;
                    ((View)object).clearAccessibilityFocus();
                    this.mRecycler.addScrapView((View)object, n16);
                }
                n2 = n;
                n = n7;
            }
            this.mMotionViewNewTop = this.mMotionViewOriginalTop + n9;
            this.mBlockLayoutRequests = true;
            if (n2 > 0) {
                this.detachViewsFromParent(n, n2);
                this.mRecycler.removeSkippedScrap();
            }
            if (!this.awakenScrollBars()) {
                this.invalidate();
            }
            this.offsetChildrenTopAndBottom(n10);
            if (bl) {
                this.mFirstPosition += n2;
            }
            if (n6 - n4 < (n = Math.abs(n10)) || n5 - n8 < n) {
                this.fillGap(bl);
            }
            this.mRecycler.fullyDetachScrapViews();
            n2 = 0;
            n = 0;
            if (!bl2 && this.mSelectedPosition != -1) {
                n2 = this.mSelectedPosition - this.mFirstPosition;
                if (n2 >= 0 && n2 < this.getChildCount()) {
                    this.positionSelector(this.mSelectedPosition, this.getChildAt(n2));
                    n = 1;
                }
            } else {
                n7 = this.mSelectorPosition;
                if (n7 != -1) {
                    n = n2;
                    if ((n7 -= this.mFirstPosition) >= 0) {
                        n = n2;
                        if (n7 < this.getChildCount()) {
                            this.positionSelector(this.mSelectorPosition, this.getChildAt(n7));
                            n = 1;
                        }
                    }
                }
            }
            if (n == 0) {
                this.mSelectorRect.setEmpty();
            }
            this.mBlockLayoutRequests = false;
            this.invokeOnItemScrollListener();
            return false;
        }
        boolean bl = false;
        if (n10 != 0) {
            bl = true;
        }
        return bl;
    }

    void updateScrollIndicators() {
        int n;
        View view = this.mScrollUp;
        int n2 = 0;
        if (view != null) {
            n = this.canScrollUp() ? 0 : 4;
            view.setVisibility(n);
        }
        if ((view = this.mScrollDown) != null) {
            n = this.canScrollDown() ? n2 : 4;
            view.setVisibility(n);
        }
    }

    @UnsupportedAppUsage
    void updateSelectorState() {
        Drawable drawable2 = this.mSelector;
        if (drawable2 != null && drawable2.isStateful()) {
            if (this.shouldShowSelector()) {
                if (drawable2.setState(this.getDrawableStateForSelector())) {
                    this.invalidateDrawable(drawable2);
                }
            } else {
                drawable2.setState(StateSet.NOTHING);
            }
        }
    }

    @Override
    public boolean verifyDrawable(Drawable drawable2) {
        boolean bl = this.mSelector == drawable2 || super.verifyDrawable(drawable2);
        return bl;
    }

    static abstract class AbsPositionScroller {
        AbsPositionScroller() {
        }

        public abstract void start(int var1);

        public abstract void start(int var1, int var2);

        public abstract void startWithOffset(int var1, int var2);

        public abstract void startWithOffset(int var1, int var2, int var3);

        public abstract void stop();
    }

    class AdapterDataSetObserver
    extends AdapterView<ListAdapter> {
        AdapterDataSetObserver() {
        }

        public void onChanged() {
            AdapterView.AdapterDataSetObserver.super.onChanged();
            if (AbsListView.this.mFastScroll != null) {
                AbsListView.this.mFastScroll.onSectionsChanged();
            }
        }

        public void onInvalidated() {
            AdapterView.AdapterDataSetObserver.super.onInvalidated();
            if (AbsListView.this.mFastScroll != null) {
                AbsListView.this.mFastScroll.onSectionsChanged();
            }
        }
    }

    private class CheckForKeyLongPress
    extends WindowRunnnable
    implements Runnable {
        private CheckForKeyLongPress() {
        }

        @Override
        public void run() {
            if (AbsListView.this.isPressed() && AbsListView.this.mSelectedPosition >= 0) {
                int n = AbsListView.this.mSelectedPosition;
                int n2 = AbsListView.this.mFirstPosition;
                View view = AbsListView.this.getChildAt(n - n2);
                if (!AbsListView.this.mDataChanged) {
                    boolean bl = false;
                    if (this.sameWindow()) {
                        AbsListView absListView = AbsListView.this;
                        bl = absListView.performLongPress(view, absListView.mSelectedPosition, AbsListView.this.mSelectedRowId);
                    }
                    if (bl) {
                        AbsListView.this.setPressed(false);
                        view.setPressed(false);
                    }
                } else {
                    AbsListView.this.setPressed(false);
                    if (view != null) {
                        view.setPressed(false);
                    }
                }
            }
        }
    }

    private class CheckForLongPress
    extends WindowRunnnable
    implements Runnable {
        private static final int INVALID_COORD = -1;
        private float mX;
        private float mY;

        private CheckForLongPress() {
            this.mX = -1.0f;
            this.mY = -1.0f;
        }

        private void setCoords(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        @Override
        public void run() {
            AbsListView absListView = AbsListView.this;
            int n = AbsListView.this.mMotionPosition;
            View view = absListView.getChildAt(n - absListView.mFirstPosition);
            if (view != null) {
                boolean bl;
                n = AbsListView.this.mMotionPosition;
                long l = AbsListView.this.mAdapter.getItemId(AbsListView.this.mMotionPosition);
                boolean bl2 = bl = false;
                if (this.sameWindow()) {
                    bl2 = bl;
                    if (!AbsListView.this.mDataChanged) {
                        float f;
                        float f2 = this.mX;
                        bl2 = f2 != -1.0f && (f = this.mY) != -1.0f ? AbsListView.this.performLongPress(view, n, l, f2, f) : AbsListView.this.performLongPress(view, n, l);
                    }
                }
                if (bl2) {
                    AbsListView.this.mHasPerformedLongPress = true;
                    absListView = AbsListView.this;
                    absListView.mTouchMode = -1;
                    absListView.setPressed(false);
                    view.setPressed(false);
                } else {
                    AbsListView.this.mTouchMode = 2;
                }
            }
        }
    }

    private final class CheckForTap
    implements Runnable {
        float x;
        float y;

        private CheckForTap() {
        }

        @Override
        public void run() {
            if (AbsListView.this.mTouchMode == 0) {
                Object object = AbsListView.this;
                ((AbsListView)object).mTouchMode = 1;
                if ((object = ((ViewGroup)object).getChildAt(((AbsListView)object).mMotionPosition - AbsListView.this.mFirstPosition)) != null && !((View)object).hasExplicitFocusable()) {
                    Object object2 = AbsListView.this;
                    ((AbsListView)object2).mLayoutMode = 0;
                    if (!((AbsListView)object2).mDataChanged) {
                        object2 = AbsListView.this.mTmpPoint;
                        object2[0] = this.x;
                        object2[1] = this.y;
                        AbsListView.this.transformPointToViewLocal((float[])object2, (View)object);
                        ((View)object).drawableHotspotChanged(object2[0], object2[1]);
                        ((View)object).setPressed(true);
                        AbsListView.this.setPressed(true);
                        AbsListView.this.layoutChildren();
                        object2 = AbsListView.this;
                        ((AbsListView)object2).positionSelector(((AbsListView)object2).mMotionPosition, (View)object);
                        AbsListView.this.refreshDrawableState();
                        int n = ViewConfiguration.getLongPressTimeout();
                        boolean bl = AbsListView.this.isLongClickable();
                        if (AbsListView.this.mSelector != null) {
                            object = AbsListView.this.mSelector.getCurrent();
                            if (object != null && object instanceof TransitionDrawable) {
                                if (bl) {
                                    ((TransitionDrawable)object).startTransition(n);
                                } else {
                                    ((TransitionDrawable)object).resetTransition();
                                }
                            }
                            AbsListView.this.mSelector.setHotspot(this.x, this.y);
                        }
                        if (bl) {
                            if (AbsListView.this.mPendingCheckForLongPress == null) {
                                object = AbsListView.this;
                                ((AbsListView)object).mPendingCheckForLongPress = (AbsListView)object.new CheckForLongPress();
                            }
                            AbsListView.this.mPendingCheckForLongPress.setCoords(this.x, this.y);
                            AbsListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                            object = AbsListView.this;
                            ((View)object).postDelayed(((AbsListView)object).mPendingCheckForLongPress, n);
                        } else {
                            AbsListView.this.mTouchMode = 2;
                        }
                    } else {
                        AbsListView.this.mTouchMode = 2;
                    }
                }
            }
        }
    }

    private class FlingRunnable
    implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final Runnable mCheckFlywheel = new Runnable(){

            @Override
            public void run() {
                int n = AbsListView.this.mActivePointerId;
                VelocityTracker velocityTracker = AbsListView.this.mVelocityTracker;
                OverScroller overScroller = FlingRunnable.this.mScroller;
                if (velocityTracker != null && n != -1) {
                    velocityTracker.computeCurrentVelocity(1000, AbsListView.this.mMaximumVelocity);
                    float f = -velocityTracker.getYVelocity(n);
                    if (Math.abs(f) >= (float)AbsListView.this.mMinimumVelocity && overScroller.isScrollingInDirection(0.0f, f)) {
                        AbsListView.this.postDelayed(this, 40L);
                    } else {
                        FlingRunnable.this.endFling();
                        AbsListView.this.mTouchMode = 3;
                        AbsListView.this.reportScrollStateChange(1);
                    }
                    return;
                }
            }
        };
        private int mLastFlingY;
        @UnsupportedAppUsage
        private final OverScroller mScroller;
        private boolean mSuppressIdleStateChangeCall;

        FlingRunnable() {
            this.mScroller = new OverScroller(AbsListView.this.getContext());
        }

        void edgeReached(int n) {
            this.mScroller.notifyVerticalEdgeReached(AbsListView.this.mScrollY, 0, AbsListView.this.mOverflingDistance);
            int n2 = AbsListView.this.getOverScrollMode();
            if (n2 != 0 && (n2 != 1 || AbsListView.this.contentFits())) {
                AbsListView absListView = AbsListView.this;
                absListView.mTouchMode = -1;
                if (absListView.mPositionScroller != null) {
                    AbsListView.this.mPositionScroller.stop();
                }
            } else {
                AbsListView.this.mTouchMode = 6;
                n2 = (int)this.mScroller.getCurrVelocity();
                if (n > 0) {
                    AbsListView.this.mEdgeGlowTop.onAbsorb(n2);
                } else {
                    AbsListView.this.mEdgeGlowBottom.onAbsorb(n2);
                }
            }
            AbsListView.this.invalidate();
            AbsListView.this.postOnAnimation(this);
        }

        @UnsupportedAppUsage(maxTargetSdk=28)
        void endFling() {
            AbsListView absListView = AbsListView.this;
            absListView.mTouchMode = -1;
            absListView.removeCallbacks(this);
            AbsListView.this.removeCallbacks(this.mCheckFlywheel);
            if (!this.mSuppressIdleStateChangeCall) {
                AbsListView.this.reportScrollStateChange(0);
            }
            AbsListView.this.clearScrollingCache();
            this.mScroller.abortAnimation();
            if (AbsListView.this.mFlingStrictSpan != null) {
                AbsListView.this.mFlingStrictSpan.finish();
                AbsListView.this.mFlingStrictSpan = null;
            }
        }

        void flywheelTouch() {
            AbsListView.this.postDelayed(this.mCheckFlywheel, 40L);
        }

        @Override
        public void run() {
            block30 : {
                block29 : {
                    int n;
                    int n2;
                    int n3;
                    block28 : {
                        block27 : {
                            n3 = AbsListView.this.mTouchMode;
                            n = 0;
                            n2 = 0;
                            if (n3 == 3) break block27;
                            if (n3 == 4) break block28;
                            if (n3 != 6) {
                                this.endFling();
                                return;
                            }
                            OverScroller overScroller = this.mScroller;
                            if (overScroller.computeScrollOffset()) {
                                int n4 = AbsListView.this.mScrollY;
                                AbsListView absListView = AbsListView.this;
                                n = overScroller.getCurrY();
                                if (absListView.overScrollBy(0, n - n4, 0, n4, 0, 0, 0, absListView.mOverflingDistance, false)) {
                                    int n5 = n4 <= 0 && n > 0 ? 1 : 0;
                                    n3 = n2;
                                    if (n4 >= 0) {
                                        n3 = n2;
                                        if (n < 0) {
                                            n3 = 1;
                                        }
                                    }
                                    if (n5 == 0 && n3 == 0) {
                                        this.startSpringback();
                                    } else {
                                        n5 = n2 = (int)overScroller.getCurrVelocity();
                                        if (n3 != 0) {
                                            n5 = -n2;
                                        }
                                        overScroller.abortAnimation();
                                        this.start(n5);
                                    }
                                } else {
                                    AbsListView.this.invalidate();
                                    AbsListView.this.postOnAnimation(this);
                                }
                            } else {
                                this.endFling();
                            }
                            break block29;
                        }
                        if (this.mScroller.isFinished()) {
                            return;
                        }
                    }
                    if (AbsListView.this.mDataChanged) {
                        AbsListView.this.layoutChildren();
                    }
                    if (AbsListView.this.mItemCount == 0 || AbsListView.this.getChildCount() == 0) break block30;
                    Object object = this.mScroller;
                    boolean bl = ((OverScroller)object).computeScrollOffset();
                    int n6 = ((OverScroller)object).getCurrY();
                    int n7 = this.mLastFlingY - n6;
                    if (n7 > 0) {
                        object = AbsListView.this;
                        ((AbsListView)object).mMotionPosition = ((AbsListView)object).mFirstPosition;
                        object = AbsListView.this.getChildAt(0);
                        AbsListView.this.mMotionViewOriginalTop = ((View)object).getTop();
                        n3 = Math.min(AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom - AbsListView.this.mPaddingTop - 1, n7);
                    } else {
                        n3 = AbsListView.this.getChildCount() - 1;
                        object = AbsListView.this;
                        ((AbsListView)object).mMotionPosition = ((AbsListView)object).mFirstPosition + n3;
                        object = AbsListView.this.getChildAt(n3);
                        AbsListView.this.mMotionViewOriginalTop = ((View)object).getTop();
                        n3 = Math.max(-(AbsListView.this.getHeight() - AbsListView.this.mPaddingBottom - AbsListView.this.mPaddingTop - 1), n7);
                    }
                    object = AbsListView.this;
                    object = ((ViewGroup)object).getChildAt(((AbsListView)object).mMotionPosition - AbsListView.this.mFirstPosition);
                    n7 = 0;
                    if (object != null) {
                        n7 = ((View)object).getTop();
                    }
                    boolean bl2 = AbsListView.this.trackMotionScroll(n3, n3);
                    n2 = n;
                    if (bl2) {
                        n2 = n;
                        if (n3 != 0) {
                            n2 = 1;
                        }
                    }
                    if (n2 != 0) {
                        if (object != null) {
                            n7 = -(n3 - (((View)object).getTop() - n7));
                            object = AbsListView.this;
                            ((AbsListView)object).overScrollBy(0, n7, 0, ((AbsListView)object).mScrollY, 0, 0, 0, AbsListView.this.mOverflingDistance, false);
                        }
                        if (bl) {
                            this.edgeReached(n3);
                        }
                    } else if (bl && n2 == 0) {
                        if (bl2) {
                            AbsListView.this.invalidate();
                        }
                        this.mLastFlingY = n6;
                        AbsListView.this.postOnAnimation(this);
                    } else {
                        this.endFling();
                    }
                }
                return;
            }
            this.endFling();
        }

        @UnsupportedAppUsage(maxTargetSdk=28)
        void start(int n) {
            int n2 = n < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = n2;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, n2, 0, n, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            AbsListView absListView = AbsListView.this;
            absListView.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = false;
            absListView.postOnAnimation(this);
            if (AbsListView.this.mFlingStrictSpan == null) {
                AbsListView.this.mFlingStrictSpan = StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void startOverfling(int n) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, AbsListView.this.mScrollY, 0, n, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, AbsListView.this.getHeight());
            AbsListView absListView = AbsListView.this;
            absListView.mTouchMode = 6;
            this.mSuppressIdleStateChangeCall = false;
            absListView.invalidate();
            AbsListView.this.postOnAnimation(this);
        }

        void startScroll(int n, int n2, boolean bl, boolean bl2) {
            int n3 = n < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = n3;
            OverScroller overScroller = this.mScroller;
            Object object = bl ? sLinearInterpolator : null;
            overScroller.setInterpolator((Interpolator)object);
            this.mScroller.startScroll(0, n3, 0, n, n2);
            object = AbsListView.this;
            ((AbsListView)object).mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = bl2;
            ((View)object).postOnAnimation(this);
        }

        void startSpringback() {
            this.mSuppressIdleStateChangeCall = false;
            if (this.mScroller.springBack(0, AbsListView.this.mScrollY, 0, 0, 0, 0)) {
                AbsListView absListView = AbsListView.this;
                absListView.mTouchMode = 6;
                absListView.invalidate();
                AbsListView.this.postOnAnimation(this);
            } else {
                AbsListView absListView = AbsListView.this;
                absListView.mTouchMode = -1;
                absListView.reportScrollStateChange(0);
            }
        }

    }

    private class InputConnectionWrapper
    implements InputConnection {
        private final EditorInfo mOutAttrs;
        private InputConnection mTarget;

        public InputConnectionWrapper(EditorInfo editorInfo) {
            this.mOutAttrs = editorInfo;
        }

        private InputConnection getTarget() {
            if (this.mTarget == null) {
                this.mTarget = AbsListView.this.getTextFilterInput().onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override
        public boolean beginBatchEdit() {
            return this.getTarget().beginBatchEdit();
        }

        @Override
        public boolean clearMetaKeyStates(int n) {
            return this.getTarget().clearMetaKeyStates(n);
        }

        @Override
        public void closeConnection() {
            this.getTarget().closeConnection();
        }

        @Override
        public boolean commitCompletion(CompletionInfo completionInfo) {
            return this.getTarget().commitCompletion(completionInfo);
        }

        @Override
        public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
            return this.getTarget().commitContent(inputContentInfo, n, bundle);
        }

        @Override
        public boolean commitCorrection(CorrectionInfo correctionInfo) {
            return this.getTarget().commitCorrection(correctionInfo);
        }

        @Override
        public boolean commitText(CharSequence charSequence, int n) {
            return this.getTarget().commitText(charSequence, n);
        }

        @Override
        public boolean deleteSurroundingText(int n, int n2) {
            return this.getTarget().deleteSurroundingText(n, n2);
        }

        @Override
        public boolean deleteSurroundingTextInCodePoints(int n, int n2) {
            return this.getTarget().deleteSurroundingTextInCodePoints(n, n2);
        }

        @Override
        public boolean endBatchEdit() {
            return this.getTarget().endBatchEdit();
        }

        @Override
        public boolean finishComposingText() {
            InputConnection inputConnection = this.mTarget;
            boolean bl = inputConnection == null || inputConnection.finishComposingText();
            return bl;
        }

        @Override
        public int getCursorCapsMode(int n) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return 16384;
            }
            return inputConnection.getCursorCapsMode(n);
        }

        @Override
        public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int n) {
            return this.getTarget().getExtractedText(extractedTextRequest, n);
        }

        @Override
        public Handler getHandler() {
            return this.getTarget().getHandler();
        }

        @Override
        public CharSequence getSelectedText(int n) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return "";
            }
            return inputConnection.getSelectedText(n);
        }

        @Override
        public CharSequence getTextAfterCursor(int n, int n2) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return "";
            }
            return inputConnection.getTextAfterCursor(n, n2);
        }

        @Override
        public CharSequence getTextBeforeCursor(int n, int n2) {
            InputConnection inputConnection = this.mTarget;
            if (inputConnection == null) {
                return "";
            }
            return inputConnection.getTextBeforeCursor(n, n2);
        }

        @Override
        public boolean performContextMenuAction(int n) {
            return this.getTarget().performContextMenuAction(n);
        }

        @Override
        public boolean performEditorAction(int n) {
            if (n == 6) {
                InputMethodManager inputMethodManager = AbsListView.this.getContext().getSystemService(InputMethodManager.class);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(AbsListView.this.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean performPrivateCommand(String string2, Bundle bundle) {
            return this.getTarget().performPrivateCommand(string2, bundle);
        }

        @Override
        public boolean reportFullscreenMode(boolean bl) {
            return AbsListView.this.mDefInputConnection.reportFullscreenMode(bl);
        }

        @Override
        public boolean requestCursorUpdates(int n) {
            return this.getTarget().requestCursorUpdates(n);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent keyEvent) {
            return AbsListView.this.mDefInputConnection.sendKeyEvent(keyEvent);
        }

        @Override
        public boolean setComposingRegion(int n, int n2) {
            return this.getTarget().setComposingRegion(n, n2);
        }

        @Override
        public boolean setComposingText(CharSequence charSequence, int n) {
            return this.getTarget().setComposingText(charSequence, n);
        }

        @Override
        public boolean setSelection(int n, int n2) {
            return this.getTarget().setSelection(n, n2);
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        @ViewDebug.ExportedProperty(category="list")
        boolean forceAdd;
        boolean isEnabled;
        long itemId = -1L;
        @ViewDebug.ExportedProperty(category="list")
        boolean recycledHeaderFooter;
        @UnsupportedAppUsage
        int scrappedFromPosition;
        @ViewDebug.ExportedProperty(category="list", mapping={@ViewDebug.IntToString(from=-1, to="ITEM_VIEW_TYPE_IGNORE"), @ViewDebug.IntToString(from=-2, to="ITEM_VIEW_TYPE_HEADER_OR_FOOTER")})
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        int viewType;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.viewType = n3;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("list:viewType", this.viewType);
            viewHierarchyEncoder.addProperty("list:recycledHeaderFooter", this.recycledHeaderFooter);
            viewHierarchyEncoder.addProperty("list:forceAdd", this.forceAdd);
            viewHierarchyEncoder.addProperty("list:isEnabled", this.isEnabled);
        }
    }

    class ListItemAccessibilityDelegate
    extends View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            int n = AbsListView.this.getPositionForView(view);
            AbsListView.this.onInitializeAccessibilityNodeInfoForItem(view, n, accessibilityNodeInfo);
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle object) {
            if (super.performAccessibilityAction(view, n, (Bundle)object)) {
                return true;
            }
            int n2 = AbsListView.this.getPositionForView(view);
            if (n2 != -1 && AbsListView.this.mAdapter != null) {
                if (n2 >= AbsListView.this.mAdapter.getCount()) {
                    return false;
                }
                object = view.getLayoutParams();
                boolean bl = object instanceof LayoutParams ? ((LayoutParams)object).isEnabled : false;
                if (AbsListView.this.isEnabled() && bl) {
                    if (n != 4) {
                        if (n != 8) {
                            if (n != 16) {
                                if (n != 32) {
                                    return false;
                                }
                                if (AbsListView.this.isLongClickable()) {
                                    long l = AbsListView.this.getItemIdAtPosition(n2);
                                    return AbsListView.this.performLongPress(view, n2, l);
                                }
                                return false;
                            }
                            if (AbsListView.this.isItemClickable(view)) {
                                long l = AbsListView.this.getItemIdAtPosition(n2);
                                return AbsListView.this.performItemClick(view, n2, l);
                            }
                            return false;
                        }
                        if (AbsListView.this.getSelectedItemPosition() == n2) {
                            AbsListView.this.setSelection(-1);
                            return true;
                        }
                        return false;
                    }
                    if (AbsListView.this.getSelectedItemPosition() != n2) {
                        AbsListView.this.setSelection(n2);
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
    }

    public static interface MultiChoiceModeListener
    extends ActionMode.Callback {
        public void onItemCheckedStateChanged(ActionMode var1, int var2, long var3, boolean var5);
    }

    class MultiChoiceModeWrapper
    implements MultiChoiceModeListener {
        private MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {
        }

        public boolean hasWrappedCallback() {
            boolean bl = this.mWrapped != null;
            return bl;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            if (this.mWrapped.onCreateActionMode(actionMode, menu2)) {
                AbsListView.this.setLongClickable(false);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode object) {
            this.mWrapped.onDestroyActionMode((ActionMode)object);
            object = AbsListView.this;
            ((AbsListView)object).mChoiceActionMode = null;
            ((AbsListView)object).clearChoices();
            object = AbsListView.this;
            ((AbsListView)object).mDataChanged = true;
            ((AdapterView)object).rememberSyncState();
            AbsListView.this.requestLayout();
            AbsListView.this.setLongClickable(true);
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int n, long l, boolean bl) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, n, l, bl);
            if (AbsListView.this.getCheckedItemCount() == 0) {
                actionMode.finish();
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu2);
        }

        public void setWrapped(MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }
    }

    public static interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        public void onScroll(AbsListView var1, int var2, int var3, int var4);

        public void onScrollStateChanged(AbsListView var1, int var2);
    }

    private class PerformClick
    extends WindowRunnnable
    implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
        }

        @Override
        public void run() {
            if (AbsListView.this.mDataChanged) {
                return;
            }
            ListAdapter listAdapter = AbsListView.this.mAdapter;
            int n = this.mClickMotionPosition;
            if (listAdapter != null && AbsListView.this.mItemCount > 0 && n != -1 && n < listAdapter.getCount() && this.sameWindow() && listAdapter.isEnabled(n)) {
                View view = AbsListView.this;
                if ((view = view.getChildAt(n - view.mFirstPosition)) != null) {
                    AbsListView.this.performItemClick(view, n, listAdapter.getItemId(n));
                }
            }
        }
    }

    class PositionScroller
    extends AbsPositionScroller
    implements Runnable {
        private static final int MOVE_DOWN_BOUND = 3;
        private static final int MOVE_DOWN_POS = 1;
        private static final int MOVE_OFFSET = 5;
        private static final int MOVE_UP_BOUND = 4;
        private static final int MOVE_UP_POS = 2;
        private static final int SCROLL_DURATION = 200;
        private int mBoundPos;
        private final int mExtraScroll;
        private int mLastSeenPos;
        private int mMode;
        private int mOffsetFromTop;
        private int mScrollDuration;
        private int mTargetPos;

        PositionScroller() {
            this.mExtraScroll = ViewConfiguration.get(AbsListView.this.mContext).getScaledFadingEdgeLength();
        }

        private void scrollToVisible(int n, int n2, int n3) {
            int n4;
            Object object;
            int n5;
            int n6;
            int n7;
            int n8;
            block16 : {
                block15 : {
                    n8 = n2;
                    n5 = AbsListView.this.mFirstPosition;
                    n7 = n5 + AbsListView.this.getChildCount() - 1;
                    n6 = AbsListView.this.mListPadding.top;
                    n4 = AbsListView.this.getHeight() - AbsListView.this.mListPadding.bottom;
                    if (n < n5 || n > n7) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("scrollToVisible called with targetPos ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" not visible [");
                        ((StringBuilder)object).append(n5);
                        ((StringBuilder)object).append(", ");
                        ((StringBuilder)object).append(n7);
                        ((StringBuilder)object).append("]");
                        Log.w(AbsListView.TAG, ((StringBuilder)object).toString());
                    }
                    if (n8 < n5) break block15;
                    n2 = n8;
                    if (n8 <= n7) break block16;
                }
                n2 = -1;
            }
            object = AbsListView.this.getChildAt(n - n5);
            n8 = ((View)object).getTop();
            n7 = ((View)object).getBottom();
            n = 0;
            if (n7 > n4) {
                n = n7 - n4;
            }
            if (n8 < n6) {
                n = n8 - n6;
            }
            if (n == 0) {
                return;
            }
            if (n2 >= 0) {
                object = AbsListView.this.getChildAt(n2 - n5);
                n8 = ((View)object).getTop();
                n2 = ((View)object).getBottom();
                n5 = Math.abs(n);
                if (n < 0 && n2 + n5 > n4) {
                    n2 = Math.max(0, n2 - n4);
                } else {
                    n2 = n;
                    if (n > 0) {
                        n2 = n;
                        if (n8 - n5 < n6) {
                            n2 = Math.min(0, n8 - n6);
                        }
                    }
                }
            } else {
                n2 = n;
            }
            AbsListView.this.smoothScrollBy(n2, n3);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            int n = AbsListView.this.getHeight();
            int n2 = AbsListView.this.mFirstPosition;
            int n3 = this.mMode;
            boolean bl = false;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 != 5) {
                                return;
                            }
                            if (this.mLastSeenPos == n2) {
                                AbsListView.this.postOnAnimation(this);
                                return;
                            }
                            this.mLastSeenPos = n2;
                            int n4 = AbsListView.this.getChildCount();
                            if (n4 <= 0) {
                                return;
                            }
                            n3 = this.mTargetPos;
                            int n5 = n2 + n4 - 1;
                            View view = AbsListView.this.getChildAt(0);
                            int n6 = view.getHeight();
                            View view2 = AbsListView.this.getChildAt(n4 - 1);
                            n = view2.getHeight();
                            float f = (float)n6 == 0.0f ? 1.0f : (float)(view.getTop() + n6) / (float)n6;
                            float f2 = (float)n == 0.0f ? 1.0f : (float)(AbsListView.this.getHeight() + n - view2.getBottom()) / (float)n;
                            float f3 = 0.0f;
                            if (n3 < n2) {
                                f = (float)(n2 - n3) + (1.0f - f) + 1.0f;
                            } else {
                                f = f3;
                                if (n3 > n5) {
                                    f = (float)(n3 - n5) + (1.0f - f2);
                                }
                            }
                            f = Math.min(Math.abs(f / (float)n4), 1.0f);
                            if (n3 < n2) {
                                n2 = (int)((float)(-AbsListView.this.getHeight()) * f);
                                n3 = (int)((float)this.mScrollDuration * f);
                                AbsListView.this.smoothScrollBy(n2, n3, true, true);
                                AbsListView.this.postOnAnimation(this);
                                return;
                            }
                            if (n3 > n5) {
                                n2 = (int)((float)AbsListView.this.getHeight() * f);
                                n3 = (int)((float)this.mScrollDuration * f);
                                AbsListView.this.smoothScrollBy(n2, n3, true, true);
                                AbsListView.this.postOnAnimation(this);
                                return;
                            }
                            n3 = AbsListView.this.getChildAt(n3 - n2).getTop() - this.mOffsetFromTop;
                            n2 = (int)((float)this.mScrollDuration * ((float)Math.abs(n3) / (float)AbsListView.this.getHeight()));
                            AbsListView.this.smoothScrollBy(n3, n2, true, false);
                            return;
                        }
                        n3 = AbsListView.this.getChildCount() - 2;
                        if (n3 < 0) {
                            return;
                        }
                        int n7 = n2 + n3;
                        if (n7 == this.mLastSeenPos) {
                            AbsListView.this.postOnAnimation(this);
                            return;
                        }
                        View view = AbsListView.this.getChildAt(n3);
                        n2 = view.getHeight();
                        n3 = view.getTop();
                        int n8 = Math.max(AbsListView.this.mListPadding.top, this.mExtraScroll);
                        this.mLastSeenPos = n7;
                        if (n7 > this.mBoundPos) {
                            AbsListView.this.smoothScrollBy(-(n - n3 - n8), this.mScrollDuration, true, true);
                            AbsListView.this.postOnAnimation(this);
                            return;
                        }
                        if ((n -= n8) > (n3 += n2)) {
                            AbsListView.this.smoothScrollBy(-(n - n3), this.mScrollDuration, true, false);
                            return;
                        }
                        AbsListView.this.reportScrollStateChange(0);
                        return;
                    }
                    n3 = AbsListView.this.getChildCount();
                    if (n2 != this.mBoundPos && n3 > 1 && n2 + n3 < AbsListView.this.mItemCount) {
                        int n9 = n2 + 1;
                        if (n9 == this.mLastSeenPos) {
                            AbsListView.this.postOnAnimation(this);
                            return;
                        }
                        View view = AbsListView.this.getChildAt(1);
                        n3 = view.getHeight();
                        n2 = view.getTop();
                        n = Math.max(AbsListView.this.mListPadding.bottom, this.mExtraScroll);
                        if (n9 < this.mBoundPos) {
                            AbsListView.this.smoothScrollBy(Math.max(0, n3 + n2 - n), this.mScrollDuration, true, true);
                            this.mLastSeenPos = n9;
                            AbsListView.this.postOnAnimation(this);
                            return;
                        }
                        if (n2 > n) {
                            AbsListView.this.smoothScrollBy(n2 - n, this.mScrollDuration, true, false);
                            return;
                        }
                        AbsListView.this.reportScrollStateChange(0);
                        return;
                    }
                    AbsListView.this.reportScrollStateChange(0);
                    return;
                }
                if (n2 == this.mLastSeenPos) {
                    AbsListView.this.postOnAnimation(this);
                    return;
                }
                View view = AbsListView.this;
                bl = false;
                if ((view = ((ViewGroup)view).getChildAt(0)) == null) {
                    return;
                }
                n = view.getTop();
                n3 = n2 > 0 ? Math.max(this.mExtraScroll, AbsListView.this.mListPadding.top) : AbsListView.this.mListPadding.top;
                view = AbsListView.this;
                int n10 = this.mScrollDuration;
                if (n2 > this.mTargetPos) {
                    bl = true;
                }
                ((AbsListView)view).smoothScrollBy(n - n3, n10, true, bl);
                this.mLastSeenPos = n2;
                if (n2 <= this.mTargetPos) return;
                AbsListView.this.postOnAnimation(this);
                return;
            }
            n3 = AbsListView.this.getChildCount() - 1;
            n2 += n3;
            if (n3 < 0) {
                return;
            }
            if (n2 == this.mLastSeenPos) {
                AbsListView.this.postOnAnimation(this);
                return;
            }
            View view = AbsListView.this.getChildAt(n3);
            int n11 = view.getHeight();
            int n12 = view.getTop();
            n3 = n2 < AbsListView.this.mItemCount - 1 ? Math.max(AbsListView.this.mListPadding.bottom, this.mExtraScroll) : AbsListView.this.mListPadding.bottom;
            view = AbsListView.this;
            int n13 = this.mScrollDuration;
            if (n2 < this.mTargetPos) {
                bl = true;
            }
            ((AbsListView)view).smoothScrollBy(n11 - (n - n12) + n3, n13, true, bl);
            this.mLastSeenPos = n2;
            if (n2 >= this.mTargetPos) return;
            AbsListView.this.postOnAnimation(this);
        }

        @Override
        public void start(final int n) {
            int n2;
            block7 : {
                block6 : {
                    int n3;
                    block5 : {
                        this.stop();
                        if (AbsListView.this.mDataChanged) {
                            AbsListView.this.mPositionScrollAfterLayout = new Runnable(){

                                @Override
                                public void run() {
                                    PositionScroller.this.start(n);
                                }
                            };
                            return;
                        }
                        n2 = AbsListView.this.getChildCount();
                        if (n2 == 0) {
                            return;
                        }
                        int n4 = AbsListView.this.mFirstPosition;
                        n3 = n4 + n2 - 1;
                        n2 = Math.max(0, Math.min(AbsListView.this.getCount() - 1, n));
                        if (n2 >= n4) break block5;
                        n = n4 - n2 + 1;
                        this.mMode = 2;
                        break block6;
                    }
                    if (n2 <= n3) break block7;
                    n = n2 - n3 + 1;
                    this.mMode = 1;
                }
                this.mScrollDuration = n > 0 ? 200 / n : 200;
                this.mTargetPos = n2;
                this.mBoundPos = -1;
                this.mLastSeenPos = -1;
                AbsListView.this.postOnAnimation(this);
                return;
            }
            this.scrollToVisible(n2, -1, 200);
        }

        @Override
        public void start(final int n, final int n2) {
            int n3;
            block14 : {
                block13 : {
                    int n4;
                    int n5;
                    block12 : {
                        this.stop();
                        if (n2 == -1) {
                            this.start(n);
                            return;
                        }
                        if (AbsListView.this.mDataChanged) {
                            AbsListView.this.mPositionScrollAfterLayout = new Runnable(){

                                @Override
                                public void run() {
                                    PositionScroller.this.start(n, n2);
                                }
                            };
                            return;
                        }
                        n3 = AbsListView.this.getChildCount();
                        if (n3 == 0) {
                            return;
                        }
                        n5 = AbsListView.this.mFirstPosition;
                        n4 = n5 + n3 - 1;
                        n3 = Math.max(0, Math.min(AbsListView.this.getCount() - 1, n));
                        if (n3 >= n5) break block12;
                        if ((n4 -= n2) < 1) {
                            return;
                        }
                        n = n5 - n3 + 1;
                        n5 = n4 - 1;
                        if (n5 < n) {
                            n = n5;
                            this.mMode = 4;
                        } else {
                            this.mMode = 2;
                        }
                        break block13;
                    }
                    if (n3 <= n4) break block14;
                    if ((n5 = n2 - n5) < 1) {
                        return;
                    }
                    n = n3 - n4 + 1;
                    if (--n5 < n) {
                        this.mMode = 3;
                        n = n5;
                    } else {
                        this.mMode = 1;
                    }
                }
                this.mScrollDuration = n > 0 ? 200 / n : 200;
                this.mTargetPos = n3;
                this.mBoundPos = n2;
                this.mLastSeenPos = -1;
                AbsListView.this.postOnAnimation(this);
                return;
            }
            this.scrollToVisible(n3, n2, 200);
        }

        @Override
        public void startWithOffset(int n, int n2) {
            this.startWithOffset(n, n2, 200);
        }

        @Override
        public void startWithOffset(final int n, final int n2, final int n3) {
            int n4;
            block8 : {
                int n5;
                block7 : {
                    int n6;
                    block6 : {
                        this.stop();
                        if (AbsListView.this.mDataChanged) {
                            AbsListView.this.mPositionScrollAfterLayout = new Runnable(){

                                @Override
                                public void run() {
                                    PositionScroller.this.startWithOffset(n, n2, n3);
                                }
                            };
                            return;
                        }
                        n5 = AbsListView.this.getChildCount();
                        if (n5 == 0) {
                            return;
                        }
                        this.mTargetPos = Math.max(0, Math.min(AbsListView.this.getCount() - 1, n));
                        this.mOffsetFromTop = n2 += AbsListView.this.getPaddingTop();
                        this.mBoundPos = -1;
                        this.mLastSeenPos = -1;
                        this.mMode = 5;
                        n = AbsListView.this.mFirstPosition;
                        n6 = n + n5 - 1;
                        n4 = this.mTargetPos;
                        if (n4 >= n) break block6;
                        n -= n4;
                        break block7;
                    }
                    if (n4 <= n6) break block8;
                    n = n4 - n6;
                }
                float f = (float)n / (float)n5;
                if (!(f < 1.0f)) {
                    n3 = (int)((float)n3 / f);
                }
                this.mScrollDuration = n3;
                this.mLastSeenPos = -1;
                AbsListView.this.postOnAnimation(this);
                return;
            }
            n = AbsListView.this.getChildAt(n4 - n).getTop();
            AbsListView.this.smoothScrollBy(n - n2, n3, true, false);
        }

        @Override
        public void stop() {
            AbsListView.this.removeCallbacks(this);
        }

    }

    class RecycleBin {
        private View[] mActiveViews = new View[0];
        private ArrayList<View> mCurrentScrap;
        private int mFirstActivePosition;
        @UnsupportedAppUsage
        private RecyclerListener mRecyclerListener;
        private ArrayList<View>[] mScrapViews;
        private ArrayList<View> mSkippedScrap;
        private SparseArray<View> mTransientStateViews;
        private LongSparseArray<View> mTransientStateViewsById;
        private int mViewTypeCount;

        RecycleBin() {
        }

        private void clearScrap(ArrayList<View> arrayList) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.removeDetachedView(arrayList.remove(n - 1 - i), false);
            }
        }

        private void clearScrapForRebind(View view) {
            view.clearAccessibilityFocus();
            view.setAccessibilityDelegate(null);
        }

        private ArrayList<View> getSkippedScrap() {
            if (this.mSkippedScrap == null) {
                this.mSkippedScrap = new ArrayList();
            }
            return this.mSkippedScrap;
        }

        private void pruneScrapViews() {
            SparseArray<View> sparseArray;
            int n;
            int n2;
            int n3 = this.mActiveViews.length;
            int n4 = this.mViewTypeCount;
            Object object = this.mScrapViews;
            for (n2 = 0; n2 < n4; ++n2) {
                sparseArray = object[n2];
                n = ((ArrayList)((Object)sparseArray)).size();
                while (n > n3) {
                    ((ArrayList)((Object)sparseArray)).remove(--n);
                }
            }
            sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                n2 = 0;
                while (n2 < sparseArray.size()) {
                    object = (View)sparseArray.valueAt(n2);
                    n = n2;
                    if (!((View)object).hasTransientState()) {
                        this.removeDetachedView((View)object, false);
                        sparseArray.removeAt(n2);
                        n = n2 - 1;
                    }
                    n2 = n + 1;
                }
            }
            if ((object = this.mTransientStateViewsById) != null) {
                n2 = 0;
                while (n2 < ((LongSparseArray)object).size()) {
                    sparseArray = (View)((LongSparseArray)object).valueAt(n2);
                    n = n2;
                    if (!((View)((Object)sparseArray)).hasTransientState()) {
                        this.removeDetachedView((View)((Object)sparseArray), false);
                        ((LongSparseArray)object).removeAt(n2);
                        n = n2 - 1;
                    }
                    n2 = n + 1;
                }
            }
        }

        private void removeDetachedView(View view, boolean bl) {
            view.setAccessibilityDelegate(null);
            AbsListView.this.removeDetachedView(view, bl);
        }

        private View retrieveFromScrap(ArrayList<View> object, int n) {
            int n2 = ((ArrayList)object).size();
            if (n2 > 0) {
                for (int i = n2 - 1; i >= 0; --i) {
                    Object object2 = ((ArrayList)object).get(i);
                    object2 = (LayoutParams)((View)object2).getLayoutParams();
                    if (AbsListView.this.mAdapterHasStableIds) {
                        if (AbsListView.this.mAdapter.getItemId(n) != ((LayoutParams)object2).itemId) continue;
                        return (View)((ArrayList)object).remove(i);
                    }
                    if (((LayoutParams)object2).scrappedFromPosition != n) continue;
                    object = (View)((ArrayList)object).remove(i);
                    this.clearScrapForRebind((View)object);
                    return object;
                }
                object = ((ArrayList)object).remove(n2 - 1);
                this.clearScrapForRebind((View)object);
                return object;
            }
            return null;
        }

        void addScrapView(View view, int n) {
            Object object = (LayoutParams)view.getLayoutParams();
            if (object == null) {
                return;
            }
            ((LayoutParams)object).scrappedFromPosition = n;
            int n2 = ((LayoutParams)object).viewType;
            if (!this.shouldRecycleViewType(n2)) {
                if (n2 != -2) {
                    this.getSkippedScrap().add(view);
                }
                return;
            }
            view.dispatchStartTemporaryDetach();
            AbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
            if (view.hasTransientState()) {
                if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds) {
                    if (this.mTransientStateViewsById == null) {
                        this.mTransientStateViewsById = new LongSparseArray();
                    }
                    this.mTransientStateViewsById.put(((LayoutParams)object).itemId, view);
                } else if (!AbsListView.this.mDataChanged) {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new SparseArray();
                    }
                    this.mTransientStateViews.put(n, view);
                } else {
                    this.clearScrapForRebind(view);
                    this.getSkippedScrap().add(view);
                }
            } else {
                this.clearScrapForRebind(view);
                if (this.mViewTypeCount == 1) {
                    this.mCurrentScrap.add(view);
                } else {
                    this.mScrapViews[n2].add(view);
                }
                object = this.mRecyclerListener;
                if (object != null) {
                    object.onMovedToScrapHeap(view);
                }
            }
        }

        @UnsupportedAppUsage
        void clear() {
            if (this.mViewTypeCount == 1) {
                this.clearScrap(this.mCurrentScrap);
            } else {
                int n = this.mViewTypeCount;
                for (int i = 0; i < n; ++i) {
                    this.clearScrap(this.mScrapViews[i]);
                }
            }
            this.clearTransientStateViews();
        }

        void clearTransientStateViews() {
            int n;
            int n2;
            Cloneable cloneable = this.mTransientStateViews;
            if (cloneable != null) {
                n = ((SparseArray)cloneable).size();
                for (n2 = 0; n2 < n; ++n2) {
                    this.removeDetachedView((View)((SparseArray)cloneable).valueAt(n2), false);
                }
                ((SparseArray)cloneable).clear();
            }
            if ((cloneable = this.mTransientStateViewsById) != null) {
                n = ((LongSparseArray)cloneable).size();
                for (n2 = 0; n2 < n; ++n2) {
                    this.removeDetachedView((View)((LongSparseArray)cloneable).valueAt(n2), false);
                }
                ((LongSparseArray)cloneable).clear();
            }
        }

        void fillActiveViews(int n, int n2) {
            if (this.mActiveViews.length < n) {
                this.mActiveViews = new View[n];
            }
            this.mFirstActivePosition = n2;
            View[] arrview = this.mActiveViews;
            for (int i = 0; i < n; ++i) {
                View view = AbsListView.this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams == null || layoutParams.viewType == -2) continue;
                arrview[i] = view;
                layoutParams.scrappedFromPosition = n2 + i;
            }
        }

        void fullyDetachScrapViews() {
            int n = this.mViewTypeCount;
            ArrayList<View>[] arrarrayList = this.mScrapViews;
            for (int i = 0; i < n; ++i) {
                ArrayList<View> arrayList = arrarrayList[i];
                for (int j = arrayList.size() - 1; j >= 0; --j) {
                    View view = arrayList.get(j);
                    if (!view.isTemporarilyDetached()) continue;
                    this.removeDetachedView(view, false);
                }
            }
        }

        View getActiveView(int n) {
            View[] arrview = this.mActiveViews;
            if ((n -= this.mFirstActivePosition) >= 0 && n < arrview.length) {
                View view = arrview[n];
                arrview[n] = null;
                return view;
            }
            return null;
        }

        View getScrapView(int n) {
            int n2 = AbsListView.this.mAdapter.getItemViewType(n);
            if (n2 < 0) {
                return null;
            }
            if (this.mViewTypeCount == 1) {
                return this.retrieveFromScrap(this.mCurrentScrap, n);
            }
            ArrayList<View>[] arrarrayList = this.mScrapViews;
            if (n2 < arrarrayList.length) {
                return this.retrieveFromScrap(arrarrayList[n2], n);
            }
            return null;
        }

        View getTransientStateView(int n) {
            if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds && this.mTransientStateViewsById != null) {
                long l = AbsListView.this.mAdapter.getItemId(n);
                View view = this.mTransientStateViewsById.get(l);
                this.mTransientStateViewsById.remove(l);
                return view;
            }
            SparseArray<View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null && (n = sparseArray.indexOfKey(n)) >= 0) {
                sparseArray = this.mTransientStateViews.valueAt(n);
                this.mTransientStateViews.removeAt(n);
                return sparseArray;
            }
            return null;
        }

        public void markChildrenDirty() {
            int n;
            int n2;
            Cloneable cloneable;
            if (this.mViewTypeCount == 1) {
                cloneable = this.mCurrentScrap;
                n = ((ArrayList)cloneable).size();
                for (n2 = 0; n2 < n; ++n2) {
                    ((View)((ArrayList)cloneable).get(n2)).forceLayout();
                }
            } else {
                int n3 = this.mViewTypeCount;
                for (n2 = 0; n2 < n3; ++n2) {
                    cloneable = this.mScrapViews[n2];
                    int n4 = ((ArrayList)cloneable).size();
                    for (n = 0; n < n4; ++n) {
                        ((View)((ArrayList)cloneable).get(n)).forceLayout();
                    }
                }
            }
            if ((cloneable = this.mTransientStateViews) != null) {
                n = ((SparseArray)cloneable).size();
                for (n2 = 0; n2 < n; ++n2) {
                    this.mTransientStateViews.valueAt(n2).forceLayout();
                }
            }
            if ((cloneable = this.mTransientStateViewsById) != null) {
                n = ((LongSparseArray)cloneable).size();
                for (n2 = 0; n2 < n; ++n2) {
                    this.mTransientStateViewsById.valueAt(n2).forceLayout();
                }
            }
        }

        void reclaimScrapViews(List<View> list) {
            if (this.mViewTypeCount == 1) {
                list.addAll(this.mCurrentScrap);
            } else {
                int n = this.mViewTypeCount;
                ArrayList<View>[] arrarrayList = this.mScrapViews;
                for (int i = 0; i < n; ++i) {
                    list.addAll(arrarrayList[i]);
                }
            }
        }

        void removeSkippedScrap() {
            ArrayList<View> arrayList = this.mSkippedScrap;
            if (arrayList == null) {
                return;
            }
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.removeDetachedView(this.mSkippedScrap.get(i), false);
            }
            this.mSkippedScrap.clear();
        }

        void scrapActiveViews() {
            View[] arrview = this.mActiveViews;
            ArrayList<View> arrayList = this.mRecyclerListener;
            boolean bl = true;
            boolean bl2 = arrayList != null;
            if (this.mViewTypeCount <= 1) {
                bl = false;
            }
            arrayList = this.mCurrentScrap;
            for (int i = arrview.length - 1; i >= 0; --i) {
                View view = arrview[i];
                ArrayList<View> arrayList2 = arrayList;
                if (view != null) {
                    arrayList2 = (LayoutParams)view.getLayoutParams();
                    int n = ((LayoutParams)arrayList2).viewType;
                    arrview[i] = null;
                    if (view.hasTransientState()) {
                        view.dispatchStartTemporaryDetach();
                        if (AbsListView.this.mAdapter != null && AbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new LongSparseArray();
                            }
                            long l = AbsListView.this.mAdapter.getItemId(this.mFirstActivePosition + i);
                            this.mTransientStateViewsById.put(l, view);
                            arrayList2 = arrayList;
                        } else if (!AbsListView.this.mDataChanged) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArray();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + i, view);
                            arrayList2 = arrayList;
                        } else {
                            arrayList2 = arrayList;
                            if (n != -2) {
                                this.removeDetachedView(view, false);
                                arrayList2 = arrayList;
                            }
                        }
                    } else if (!this.shouldRecycleViewType(n)) {
                        arrayList2 = arrayList;
                        if (n != -2) {
                            this.removeDetachedView(view, false);
                            arrayList2 = arrayList;
                        }
                    } else {
                        if (bl) {
                            arrayList = this.mScrapViews[n];
                        }
                        ((LayoutParams)arrayList2).scrappedFromPosition = this.mFirstActivePosition + i;
                        this.removeDetachedView(view, false);
                        arrayList.add(view);
                        arrayList2 = arrayList;
                        if (bl2) {
                            this.mRecyclerListener.onMovedToScrapHeap(view);
                            arrayList2 = arrayList;
                        }
                    }
                }
                arrayList = arrayList2;
            }
            this.pruneScrapViews();
        }

        void setCacheColorHint(int n) {
            int n2;
            int n3;
            if (this.mViewTypeCount == 1) {
                ArrayList<View> object2 = this.mCurrentScrap;
                n2 = object2.size();
                for (n3 = 0; n3 < n2; ++n3) {
                    object2.get(n3).setDrawingCacheBackgroundColor(n);
                }
            } else {
                int n4 = this.mViewTypeCount;
                for (n3 = 0; n3 < n4; ++n3) {
                    ArrayList<View> arrayList = this.mScrapViews[n3];
                    int n5 = arrayList.size();
                    for (n2 = 0; n2 < n5; ++n2) {
                        arrayList.get(n2).setDrawingCacheBackgroundColor(n);
                    }
                }
            }
            for (View view : this.mActiveViews) {
                if (view == null) continue;
                view.setDrawingCacheBackgroundColor(n);
            }
        }

        public void setViewTypeCount(int n) {
            if (n >= 1) {
                ArrayList[] arrarrayList = new ArrayList[n];
                for (int i = 0; i < n; ++i) {
                    arrarrayList[i] = new ArrayList();
                }
                this.mViewTypeCount = n;
                this.mCurrentScrap = arrarrayList[0];
                this.mScrapViews = arrarrayList;
                return;
            }
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }

        public boolean shouldRecycleViewType(int n) {
            boolean bl = n >= 0;
            return bl;
        }
    }

    public static interface RecyclerListener {
        public void onMovedToScrapHeap(View var1);
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
        LongSparseArray<Integer> checkIdState;
        SparseBooleanArray checkState;
        int checkedItemCount;
        String filter;
        @UnsupportedAppUsage
        long firstId;
        int height;
        boolean inActionMode;
        int position;
        long selectedId;
        @UnsupportedAppUsage
        int viewTop;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewTop = parcel.readInt();
            this.position = parcel.readInt();
            this.height = parcel.readInt();
            this.filter = parcel.readString();
            boolean bl = parcel.readByte() != 0;
            this.inActionMode = bl;
            this.checkedItemCount = parcel.readInt();
            this.checkState = parcel.readSparseBooleanArray();
            int n = parcel.readInt();
            if (n > 0) {
                this.checkIdState = new LongSparseArray();
                for (int i = 0; i < n; ++i) {
                    long l = parcel.readLong();
                    int n2 = parcel.readInt();
                    this.checkIdState.put(l, n2);
                }
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AbsListView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" selectedId=");
            stringBuilder.append(this.selectedId);
            stringBuilder.append(" firstId=");
            stringBuilder.append(this.firstId);
            stringBuilder.append(" viewTop=");
            stringBuilder.append(this.viewTop);
            stringBuilder.append(" position=");
            stringBuilder.append(this.position);
            stringBuilder.append(" height=");
            stringBuilder.append(this.height);
            stringBuilder.append(" filter=");
            stringBuilder.append(this.filter);
            stringBuilder.append(" checkState=");
            stringBuilder.append(this.checkState);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeLong(this.selectedId);
            parcel.writeLong(this.firstId);
            parcel.writeInt(this.viewTop);
            parcel.writeInt(this.position);
            parcel.writeInt(this.height);
            parcel.writeString(this.filter);
            parcel.writeByte((byte)(this.inActionMode ? 1 : 0));
            parcel.writeInt(this.checkedItemCount);
            parcel.writeSparseBooleanArray(this.checkState);
            LongSparseArray<Integer> longSparseArray = this.checkIdState;
            n = longSparseArray != null ? longSparseArray.size() : 0;
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeLong(this.checkIdState.keyAt(i));
                parcel.writeInt(this.checkIdState.valueAt(i));
            }
        }

    }

    public static interface SelectionBoundsAdjuster {
        public void adjustListItemSelectionBounds(Rect var1);
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = AbsListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            boolean bl = AbsListView.this.getWindowAttachCount() == this.mOriginalAttachCount;
            return bl;
        }
    }

}

