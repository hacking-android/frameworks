/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Display;
import android.view.DragEvent;
import android.view.FocusFinder;
import android.view.GhostView;
import android.view.InputEvent;
import android.view.InputEventConsistencyVerifier;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroupOverlay;
import android.view.ViewHierarchyEncoder;
import android.view.ViewManager;
import android.view.ViewOutlineProvider;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationListener;
import android.view._$$Lambda$ViewGroup$ViewLocationHolder$AjKvqdj7SGGIzA5qrlZUuu71jl8;
import android.view._$$Lambda$ViewGroup$ViewLocationHolder$QbO7cM0ULKe25a7bfXG3VH6DB0c;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class ViewGroup
extends View
implements ViewParent,
ViewManager {
    private static final int ARRAY_CAPACITY_INCREMENT = 12;
    private static final int ARRAY_INITIAL_CAPACITY = 12;
    private static final int CHILD_LEFT_INDEX = 0;
    private static final int CHILD_TOP_INDEX = 1;
    protected static final int CLIP_TO_PADDING_MASK = 34;
    @UnsupportedAppUsage
    private static final boolean DBG = false;
    private static final int[] DESCENDANT_FOCUSABILITY_FLAGS = new int[]{131072, 262144, 393216};
    private static final int FLAG_ADD_STATES_FROM_CHILDREN = 8192;
    @Deprecated
    private static final int FLAG_ALWAYS_DRAWN_WITH_CACHE = 16384;
    @Deprecated
    private static final int FLAG_ANIMATION_CACHE = 64;
    static final int FLAG_ANIMATION_DONE = 16;
    @Deprecated
    private static final int FLAG_CHILDREN_DRAWN_WITH_CACHE = 32768;
    static final int FLAG_CLEAR_TRANSFORMATION = 256;
    static final int FLAG_CLIP_CHILDREN = 1;
    private static final int FLAG_CLIP_TO_PADDING = 2;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123983692L)
    protected static final int FLAG_DISALLOW_INTERCEPT = 524288;
    static final int FLAG_INVALIDATE_REQUIRED = 4;
    static final int FLAG_IS_TRANSITION_GROUP = 16777216;
    static final int FLAG_IS_TRANSITION_GROUP_SET = 33554432;
    private static final int FLAG_LAYOUT_MODE_WAS_EXPLICITLY_SET = 8388608;
    private static final int FLAG_MASK_FOCUSABILITY = 393216;
    private static final int FLAG_NOTIFY_ANIMATION_LISTENER = 512;
    private static final int FLAG_NOTIFY_CHILDREN_ON_DRAWABLE_STATE_CHANGE = 65536;
    static final int FLAG_OPTIMIZE_INVALIDATE = 128;
    private static final int FLAG_PADDING_NOT_NULL = 32;
    private static final int FLAG_PREVENT_DISPATCH_ATTACHED_TO_WINDOW = 4194304;
    private static final int FLAG_RUN_ANIMATION = 8;
    private static final int FLAG_SHOW_CONTEXT_MENU_WITH_COORDS = 536870912;
    private static final int FLAG_SPLIT_MOTION_EVENTS = 2097152;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_NOT_TYPED = 268435456;
    private static final int FLAG_START_ACTION_MODE_FOR_CHILD_IS_TYPED = 134217728;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769647L)
    protected static final int FLAG_SUPPORT_STATIC_TRANSFORMATIONS = 2048;
    static final int FLAG_TOUCHSCREEN_BLOCKS_FOCUS = 67108864;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769377L)
    protected static final int FLAG_USE_CHILD_DRAWING_ORDER = 1024;
    public static final int FOCUS_AFTER_DESCENDANTS = 262144;
    public static final int FOCUS_BEFORE_DESCENDANTS = 131072;
    public static final int FOCUS_BLOCK_DESCENDANTS = 393216;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static int LAYOUT_MODE_DEFAULT = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
    private static final int LAYOUT_MODE_UNDEFINED = -1;
    @Deprecated
    public static final int PERSISTENT_ALL_CACHES = 3;
    @Deprecated
    public static final int PERSISTENT_ANIMATION_CACHE = 1;
    @Deprecated
    public static final int PERSISTENT_NO_CACHE = 0;
    @Deprecated
    public static final int PERSISTENT_SCROLLING_CACHE = 2;
    private static final ActionMode SENTINEL_ACTION_MODE;
    private static final String TAG = "ViewGroup";
    private static float[] sDebugLines;
    private Animation.AnimationListener mAnimationListener;
    Paint mCachePaint;
    @ViewDebug.ExportedProperty(category="layout")
    private int mChildCountWithTransientState = 0;
    private Transformation mChildTransformation;
    int mChildUnhandledKeyListeners = 0;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private View[] mChildren;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mChildrenCount;
    private HashSet<View> mChildrenInterestedInDrag;
    private View mCurrentDragChild;
    private DragEvent mCurrentDragStartEvent;
    private View mDefaultFocus;
    @UnsupportedAppUsage
    protected ArrayList<View> mDisappearingChildren;
    private HoverTarget mFirstHoverTarget;
    @UnsupportedAppUsage
    private TouchTarget mFirstTouchTarget;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private View mFocused;
    View mFocusedInCluster;
    @ViewDebug.ExportedProperty(flagMapping={@ViewDebug.FlagToString(equals=1, mask=1, name="CLIP_CHILDREN"), @ViewDebug.FlagToString(equals=2, mask=2, name="CLIP_TO_PADDING"), @ViewDebug.FlagToString(equals=32, mask=32, name="PADDING_NOT_NULL")}, formatToHexString=true)
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769411L)
    protected int mGroupFlags;
    private boolean mHoveredSelf;
    RectF mInvalidateRegion;
    Transformation mInvalidationTransformation;
    private boolean mIsInterestedInDrag;
    @ViewDebug.ExportedProperty(category="events")
    private int mLastTouchDownIndex = -1;
    @ViewDebug.ExportedProperty(category="events")
    private long mLastTouchDownTime;
    @ViewDebug.ExportedProperty(category="events")
    private float mLastTouchDownX;
    @ViewDebug.ExportedProperty(category="events")
    private float mLastTouchDownY;
    private LayoutAnimationController mLayoutAnimationController;
    private boolean mLayoutCalledWhileSuppressed = false;
    private int mLayoutMode = -1;
    private LayoutTransition.TransitionListener mLayoutTransitionListener = new LayoutTransition.TransitionListener(){

        @Override
        public void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int n) {
            if (ViewGroup.this.mLayoutCalledWhileSuppressed && !layoutTransition.isChangingLayout()) {
                ViewGroup.this.requestLayout();
                ViewGroup.this.mLayoutCalledWhileSuppressed = false;
            }
            if (n == 3 && ViewGroup.this.mTransitioningViews != null) {
                ViewGroup.this.endViewTransition(view);
            }
        }

        @Override
        public void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int n) {
            if (n == 3) {
                ViewGroup.this.startViewTransition(view);
            }
        }
    };
    private PointF mLocalPoint;
    private int mNestedScrollAxes;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768704L)
    protected OnHierarchyChangeListener mOnHierarchyChangeListener;
    @UnsupportedAppUsage
    protected int mPersistentDrawingCache;
    private ArrayList<View> mPreSortedChildren;
    boolean mSuppressLayout = false;
    private float[] mTempPoint;
    private View mTooltipHoverTarget;
    private boolean mTooltipHoveredSelf;
    private List<Integer> mTransientIndices = null;
    private List<View> mTransientViews = null;
    private LayoutTransition mTransition;
    private ArrayList<View> mTransitioningViews;
    private ArrayList<View> mVisibilityChangingChildren;

    static {
        LAYOUT_MODE_DEFAULT = 0;
        SENTINEL_ACTION_MODE = new ActionMode(){

            @Override
            public void finish() {
            }

            @Override
            public View getCustomView() {
                return null;
            }

            @Override
            public Menu getMenu() {
                return null;
            }

            @Override
            public MenuInflater getMenuInflater() {
                return null;
            }

            @Override
            public CharSequence getSubtitle() {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public void invalidate() {
            }

            @Override
            public void setCustomView(View view) {
            }

            @Override
            public void setSubtitle(int n) {
            }

            @Override
            public void setSubtitle(CharSequence charSequence) {
            }

            @Override
            public void setTitle(int n) {
            }

            @Override
            public void setTitle(CharSequence charSequence) {
            }
        };
    }

    public ViewGroup(Context context) {
        this(context, null);
    }

    public ViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewGroup(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ViewGroup(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.initViewGroup();
        this.initFromAttributes(context, attributeSet, n, n2);
    }

    private void addDisappearingView(View view) {
        ArrayList<View> arrayList;
        ArrayList<View> arrayList2 = arrayList = this.mDisappearingChildren;
        if (arrayList == null) {
            this.mDisappearingChildren = arrayList2 = new ArrayList();
        }
        arrayList2.add(view);
    }

    private void addInArray(View object, int n) {
        int n2;
        block9 : {
            block8 : {
                int n3;
                View[] arrview;
                block7 : {
                    arrview = this.mChildren;
                    n2 = this.mChildrenCount;
                    n3 = arrview.length;
                    if (n != n2) break block7;
                    View[] arrview2 = arrview;
                    if (n3 == n2) {
                        this.mChildren = new View[n3 + 12];
                        System.arraycopy(arrview, 0, this.mChildren, 0, n3);
                        arrview2 = this.mChildren;
                    }
                    n = this.mChildrenCount;
                    this.mChildrenCount = n + 1;
                    arrview2[n] = object;
                    break block8;
                }
                if (n >= n2) break block9;
                if (n3 == n2) {
                    this.mChildren = new View[n3 + 12];
                    System.arraycopy(arrview, 0, this.mChildren, 0, n);
                    System.arraycopy(arrview, n, this.mChildren, n + 1, n2 - n);
                    arrview = this.mChildren;
                } else {
                    System.arraycopy(arrview, n, arrview, n + 1, n2 - n);
                }
                arrview[n] = object;
                ++this.mChildrenCount;
                n3 = this.mLastTouchDownIndex;
                if (n3 >= n) {
                    this.mLastTouchDownIndex = n3 + 1;
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("index=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" count=");
        ((StringBuilder)object).append(n2);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    private TouchTarget addTouchTarget(View object, int n) {
        object = TouchTarget.obtain((View)object, n);
        ((TouchTarget)object).next = this.mFirstTouchTarget;
        this.mFirstTouchTarget = object;
        return object;
    }

    private void addViewInner(View view, int n, LayoutParams list, boolean bl) {
        Object object = this.mTransition;
        if (object != null) {
            ((LayoutTransition)object).cancel(3);
        }
        if (view.getParent() == null) {
            object = this.mTransition;
            if (object != null) {
                ((LayoutTransition)object).addChild(this, view);
            }
            object = list;
            if (!this.checkLayoutParams((LayoutParams)((Object)list))) {
                object = this.generateLayoutParams((LayoutParams)((Object)list));
            }
            if (bl) {
                view.mLayoutParams = object;
            } else {
                view.setLayoutParams((LayoutParams)object);
            }
            int n2 = n;
            if (n < 0) {
                n2 = this.mChildrenCount;
            }
            this.addInArray(view, n2);
            if (bl) {
                view.assignParent(this);
            } else {
                view.mParent = this;
            }
            if (view.hasUnhandledKeyListener()) {
                this.incrementChildUnhandledKeyListeners();
            }
            if (view.hasFocus()) {
                this.requestChildFocus(view, view.findFocus());
            }
            if ((list = this.mAttachInfo) != null && (this.mGroupFlags & 4194304) == 0) {
                bl = ((View.AttachInfo)list).mKeepScreenOn;
                ((View.AttachInfo)list).mKeepScreenOn = false;
                view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
                if (((View.AttachInfo)list).mKeepScreenOn) {
                    this.needGlobalAttributesUpdate(true);
                }
                ((View.AttachInfo)list).mKeepScreenOn = bl;
            }
            if (view.isLayoutDirectionInherited()) {
                view.resetRtlProperties();
            }
            this.dispatchViewAdded(view);
            if ((view.mViewFlags & 4194304) == 4194304) {
                this.mGroupFlags |= 65536;
            }
            if (view.hasTransientState()) {
                this.childHasTransientStateChanged(view, true);
            }
            if (view.getVisibility() != 8) {
                this.notifySubtreeAccessibilityStateChangedIfNeeded();
            }
            if ((list = this.mTransientIndices) != null) {
                int n3 = list.size();
                for (n = 0; n < n3; ++n) {
                    int n4 = this.mTransientIndices.get(n);
                    if (n2 > n4) continue;
                    this.mTransientIndices.set(n, n4 + 1);
                }
            }
            if (this.mCurrentDragStartEvent != null && view.getVisibility() == 0) {
                this.notifyChildOfDragStart(view);
            }
            if (view.hasDefaultFocus()) {
                this.setDefaultFocus(view);
            }
            this.touchAccessibilityNodeProviderIfNeeded(view);
            return;
        }
        throw new IllegalStateException("The specified child already has a parent. You must call removeView() on the child's parent first.");
    }

    private static void applyOpToRegionByBounds(Region region, View view, Region.Op op) {
        int[] arrn = new int[2];
        view.getLocationInWindow(arrn);
        int n = arrn[0];
        int n2 = arrn[1];
        region.op(n, n2, n + view.getWidth(), n2 + view.getHeight(), op);
    }

    private void bindLayoutAnimation(View view) {
        view.setAnimation(this.mLayoutAnimationController.getAnimationForView(view));
    }

    private WindowInsets brokenDispatchApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets windowInsets2 = windowInsets;
        if (!windowInsets.isConsumed()) {
            int n = this.getChildCount();
            int n2 = 0;
            do {
                windowInsets2 = windowInsets;
                if (n2 >= n) break;
                windowInsets = this.getChildAt(n2).dispatchApplyWindowInsets(windowInsets);
                if (windowInsets.isConsumed()) {
                    windowInsets2 = windowInsets;
                    break;
                }
                ++n2;
            } while (true);
        }
        return windowInsets2;
    }

    private void cancelAndClearTouchTargets(MotionEvent object) {
        if (this.mFirstTouchTarget != null) {
            boolean bl = false;
            MotionEvent motionEvent = object;
            if (object == null) {
                long l = SystemClock.uptimeMillis();
                motionEvent = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
                motionEvent.setSource(4098);
                bl = true;
            }
            object = this.mFirstTouchTarget;
            while (object != null) {
                ViewGroup.resetCancelNextUpFlag(((TouchTarget)object).child);
                this.dispatchTransformedTouchEvent(motionEvent, true, ((TouchTarget)object).child, ((TouchTarget)object).pointerIdBits);
                object = ((TouchTarget)object).next;
            }
            this.clearTouchTargets();
            if (bl) {
                motionEvent.recycle();
            }
        }
    }

    private void cancelHoverTarget(View view) {
        HoverTarget hoverTarget = null;
        Object object = this.mFirstHoverTarget;
        while (object != null) {
            HoverTarget hoverTarget2 = ((HoverTarget)object).next;
            if (((HoverTarget)object).child == view) {
                if (hoverTarget == null) {
                    this.mFirstHoverTarget = hoverTarget2;
                } else {
                    hoverTarget.next = hoverTarget2;
                }
                ((HoverTarget)object).recycle();
                long l = SystemClock.uptimeMillis();
                object = MotionEvent.obtain(l, l, 10, 0.0f, 0.0f, 0);
                ((MotionEvent)object).setSource(4098);
                view.dispatchHoverEvent((MotionEvent)object);
                ((MotionEvent)object).recycle();
                return;
            }
            hoverTarget = object;
            object = hoverTarget2;
        }
    }

    @UnsupportedAppUsage
    private void cancelTouchTarget(View view) {
        TouchTarget touchTarget = null;
        Object object = this.mFirstTouchTarget;
        while (object != null) {
            TouchTarget touchTarget2 = ((TouchTarget)object).next;
            if (((TouchTarget)object).child == view) {
                if (touchTarget == null) {
                    this.mFirstTouchTarget = touchTarget2;
                } else {
                    touchTarget.next = touchTarget2;
                }
                ((TouchTarget)object).recycle();
                long l = SystemClock.uptimeMillis();
                object = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
                ((MotionEvent)object).setSource(4098);
                view.dispatchTouchEvent((MotionEvent)object);
                ((MotionEvent)object).recycle();
                return;
            }
            touchTarget = object;
            object = touchTarget2;
        }
    }

    private void clearCachedLayoutMode() {
        if (!this.hasBooleanFlag(8388608)) {
            this.mLayoutMode = -1;
        }
    }

    private void clearTouchTargets() {
        TouchTarget touchTarget = this.mFirstTouchTarget;
        if (touchTarget != null) {
            TouchTarget touchTarget2;
            do {
                touchTarget2 = touchTarget.next;
                touchTarget.recycle();
                touchTarget = touchTarget2;
            } while (touchTarget2 != null);
            this.mFirstTouchTarget = null;
        }
    }

    private PointerIcon dispatchResolvePointerIcon(MotionEvent object, int n, View object2) {
        if (!((View)object2).hasIdentityMatrix()) {
            MotionEvent motionEvent = this.getTransformedMotionEvent((MotionEvent)object, (View)object2);
            object = ((View)object2).onResolvePointerIcon(motionEvent, n);
            motionEvent.recycle();
        } else {
            float f = this.mScrollX - ((View)object2).mLeft;
            float f2 = this.mScrollY - ((View)object2).mTop;
            ((MotionEvent)object).offsetLocation(f, f2);
            object2 = ((View)object2).onResolvePointerIcon((MotionEvent)object, n);
            ((MotionEvent)object).offsetLocation(-f, -f2);
            object = object2;
        }
        return object;
    }

    private boolean dispatchTooltipHoverEvent(MotionEvent motionEvent, View view) {
        boolean bl;
        if (!view.hasIdentityMatrix()) {
            motionEvent = this.getTransformedMotionEvent(motionEvent, view);
            bl = view.dispatchTooltipHoverEvent(motionEvent);
            motionEvent.recycle();
        } else {
            float f = this.mScrollX - view.mLeft;
            float f2 = this.mScrollY - view.mTop;
            motionEvent.offsetLocation(f, f2);
            bl = view.dispatchTooltipHoverEvent(motionEvent);
            motionEvent.offsetLocation(-f, -f2);
        }
        return bl;
    }

    private boolean dispatchTransformedGenericPointerEvent(MotionEvent motionEvent, View view) {
        boolean bl;
        if (!view.hasIdentityMatrix()) {
            motionEvent = this.getTransformedMotionEvent(motionEvent, view);
            bl = view.dispatchGenericMotionEvent(motionEvent);
            motionEvent.recycle();
        } else {
            float f = this.mScrollX - view.mLeft;
            float f2 = this.mScrollY - view.mTop;
            motionEvent.offsetLocation(f, f2);
            bl = view.dispatchGenericMotionEvent(motionEvent);
            motionEvent.offsetLocation(-f, -f2);
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean dispatchTransformedTouchEvent(MotionEvent var1_1, boolean var2_2, View var3_3, int var4_4) {
        block7 : {
            var5_5 = var1_1.getAction();
            if (var2_2 || var5_5 == 3) break block7;
            var5_5 = var1_1.getPointerIdBits();
            var4_4 = var5_5 & var4_4;
            if (var4_4 == 0) {
                return false;
            }
            if (var4_4 != var5_5) ** GOTO lbl19
            if (var3_3 != null && !var3_3.hasIdentityMatrix()) {
                var1_1 = MotionEvent.obtain(var1_1);
            } else {
                if (var3_3 == null) {
                    return super.dispatchTouchEvent(var1_1);
                }
                var6_6 = this.mScrollX - var3_3.mLeft;
                var7_7 = this.mScrollY - var3_3.mTop;
                var1_1.offsetLocation(var6_6, var7_7);
                var2_2 = var3_3.dispatchTouchEvent(var1_1);
                var1_1.offsetLocation(-var6_6, -var7_7);
                return var2_2;
lbl19: // 1 sources:
                var1_1 = var1_1.split(var4_4);
            }
            if (var3_3 == null) {
                var2_2 = super.dispatchTouchEvent(var1_1);
            } else {
                var1_1.offsetLocation(this.mScrollX - var3_3.mLeft, this.mScrollY - var3_3.mTop);
                if (!var3_3.hasIdentityMatrix()) {
                    var1_1.transform(var3_3.getInverseMatrix());
                }
                var2_2 = var3_3.dispatchTouchEvent(var1_1);
            }
            var1_1.recycle();
            return var2_2;
        }
        var1_1.setAction(3);
        var2_2 = var3_3 == null ? super.dispatchTouchEvent(var1_1) : var3_3.dispatchTouchEvent(var1_1);
        var1_1.setAction(var5_5);
        return var2_2;
    }

    private static void drawCorner(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5) {
        ViewGroup.fillRect(canvas, paint, n, n2, n + n3, n2 + ViewGroup.sign(n4) * n5);
        ViewGroup.fillRect(canvas, paint, n, n2, n + ViewGroup.sign(n3) * n5, n2 + n4);
    }

    private static void drawRect(Canvas canvas, Paint paint, int n, int n2, int n3, int n4) {
        if (sDebugLines == null) {
            sDebugLines = new float[16];
        }
        float[] arrf = sDebugLines;
        arrf[0] = n;
        arrf[1] = n2;
        arrf[2] = n3;
        arrf[3] = n2;
        arrf[4] = n3;
        arrf[5] = n2;
        arrf[6] = n3;
        arrf[7] = n4;
        arrf[8] = n3;
        arrf[9] = n4;
        arrf[10] = n;
        arrf[11] = n4;
        arrf[12] = n;
        arrf[13] = n4;
        arrf[14] = n;
        arrf[15] = n2;
        canvas.drawLines(arrf, paint);
    }

    private static void drawRectCorners(Canvas canvas, int n, int n2, int n3, int n4, Paint paint, int n5, int n6) {
        ViewGroup.drawCorner(canvas, paint, n, n2, n5, n5, n6);
        ViewGroup.drawCorner(canvas, paint, n, n4, n5, -n5, n6);
        ViewGroup.drawCorner(canvas, paint, n3, n2, -n5, n5, n6);
        ViewGroup.drawCorner(canvas, paint, n3, n4, -n5, -n5, n6);
    }

    private void exitHoverTargets() {
        if (this.mHoveredSelf || this.mFirstHoverTarget != null) {
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain(l, l, 10, 0.0f, 0.0f, 0);
            motionEvent.setSource(4098);
            this.dispatchHoverEvent(motionEvent);
            motionEvent.recycle();
        }
    }

    private void exitTooltipHoverTargets() {
        if (this.mTooltipHoveredSelf || this.mTooltipHoverTarget != null) {
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain(l, l, 10, 0.0f, 0.0f, 0);
            motionEvent.setSource(4098);
            this.dispatchTooltipHoverEvent(motionEvent);
            motionEvent.recycle();
        }
    }

    private static void fillDifference(Canvas canvas, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Paint paint) {
        n5 = n - n5;
        n7 = n3 + n7;
        ViewGroup.fillRect(canvas, paint, n5, n2 - n6, n7, n2);
        ViewGroup.fillRect(canvas, paint, n5, n2, n, n4);
        ViewGroup.fillRect(canvas, paint, n3, n2, n7, n4);
        ViewGroup.fillRect(canvas, paint, n5, n4, n7, n4 + n8);
    }

    private static void fillRect(Canvas canvas, Paint paint, int n, int n2, int n3, int n4) {
        if (n != n3 && n2 != n4) {
            int n5 = n;
            int n6 = n3;
            if (n > n3) {
                n6 = n;
                n5 = n3;
            }
            n3 = n2;
            n = n4;
            if (n2 > n4) {
                n3 = n4;
                n = n2;
            }
            canvas.drawRect(n5, n3, n6, n, paint);
        }
    }

    private View findChildWithAccessibilityFocus() {
        ViewParent viewParent = this.getViewRootImpl();
        if (viewParent == null) {
            return null;
        }
        View view = viewParent.getAccessibilityFocusedHost();
        if (view == null) {
            return null;
        }
        viewParent = view.getParent();
        while (viewParent instanceof View) {
            if (viewParent == this) {
                return view;
            }
            view = (View)((Object)viewParent);
            viewParent = view.getParent();
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getAndVerifyPreorderedIndex(int n, int n2, boolean bl) {
        if (!bl) return n2;
        if ((n2 = this.getChildDrawingOrder(n, n2)) < n) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getChildDrawingOrder() returned invalid index ");
        stringBuilder.append(n2);
        stringBuilder.append(" (child count is ");
        stringBuilder.append(n);
        stringBuilder.append(")");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static View getAndVerifyPreorderedView(ArrayList<View> object, View[] arrview, int n) {
        if (object != null) {
            if ((object = ((ArrayList)object).get(n)) == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid preorderedList contained null child at index ");
                ((StringBuilder)object).append(n);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
        } else {
            object = arrview[n];
        }
        return object;
    }

    public static int getChildMeasureSpec(int n, int n2, int n3) {
        int n4 = View.MeasureSpec.getMode(n);
        int n5 = View.MeasureSpec.getSize(n);
        int n6 = 0;
        n = 0;
        n5 = Math.max(0, n5 - n2);
        int n7 = 0;
        n2 = 0;
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 0) {
                if (n4 != 1073741824) {
                    n = n7;
                } else if (n3 >= 0) {
                    n = n3;
                    n2 = 1073741824;
                } else if (n3 == -1) {
                    n = n5;
                    n2 = 1073741824;
                } else {
                    n = n7;
                    if (n3 == -2) {
                        n = n5;
                        n2 = Integer.MIN_VALUE;
                    }
                }
            } else if (n3 >= 0) {
                n = n3;
                n2 = 1073741824;
            } else if (n3 == -1) {
                if (!View.sUseZeroUnspecifiedMeasureSpec) {
                    n = n5;
                }
                n2 = 0;
            } else {
                n = n7;
                if (n3 == -2) {
                    n = View.sUseZeroUnspecifiedMeasureSpec ? n6 : n5;
                    n2 = 0;
                }
            }
        } else if (n3 >= 0) {
            n = n3;
            n2 = 1073741824;
        } else if (n3 == -1) {
            n = n5;
            n2 = Integer.MIN_VALUE;
        } else {
            n = n7;
            if (n3 == -2) {
                n = n5;
                n2 = Integer.MIN_VALUE;
            }
        }
        return View.MeasureSpec.makeMeasureSpec(n, n2);
    }

    private ChildListForAutofill getChildrenForAutofill(int n) {
        ChildListForAutofill childListForAutofill = ChildListForAutofill.obtain();
        this.populateChildrenForAutofill(childListForAutofill, n);
        return childListForAutofill;
    }

    private PointF getLocalPoint() {
        if (this.mLocalPoint == null) {
            this.mLocalPoint = new PointF();
        }
        return this.mLocalPoint;
    }

    private float[] getTempPoint() {
        if (this.mTempPoint == null) {
            this.mTempPoint = new float[2];
        }
        return this.mTempPoint;
    }

    private TouchTarget getTouchTarget(View view) {
        TouchTarget touchTarget = this.mFirstTouchTarget;
        while (touchTarget != null) {
            if (touchTarget.child == view) {
                return touchTarget;
            }
            touchTarget = touchTarget.next;
        }
        return null;
    }

    private MotionEvent getTransformedMotionEvent(MotionEvent motionEvent, View view) {
        float f = this.mScrollX - view.mLeft;
        float f2 = this.mScrollY - view.mTop;
        motionEvent = MotionEvent.obtain(motionEvent);
        motionEvent.offsetLocation(f, f2);
        if (!view.hasIdentityMatrix()) {
            motionEvent.transform(view.getInverseMatrix());
        }
        return motionEvent;
    }

    private boolean hasBooleanFlag(int n) {
        boolean bl = (this.mGroupFlags & n) == n;
        return bl;
    }

    private boolean hasChildWithZ() {
        for (int i = 0; i < this.mChildrenCount; ++i) {
            if (this.mChildren[i].getZ() == 0.0f) continue;
            return true;
        }
        return false;
    }

    private void initFromAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ViewGroup, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.ViewGroup, attributeSet, typedArray, n, n2);
        n2 = typedArray.getIndexCount();
        block15 : for (n = 0; n < n2; ++n) {
            int n3 = typedArray.getIndex(n);
            switch (n3) {
                default: {
                    continue block15;
                }
                case 12: {
                    this.setTouchscreenBlocksFocus(typedArray.getBoolean(n3, false));
                    continue block15;
                }
                case 11: {
                    this.setTransitionGroup(typedArray.getBoolean(n3, false));
                    continue block15;
                }
                case 10: {
                    this.setLayoutMode(typedArray.getInt(n3, -1));
                    continue block15;
                }
                case 9: {
                    if (!typedArray.getBoolean(n3, false)) continue block15;
                    this.setLayoutTransition(new LayoutTransition());
                    continue block15;
                }
                case 8: {
                    this.setMotionEventSplittingEnabled(typedArray.getBoolean(n3, false));
                    continue block15;
                }
                case 7: {
                    this.setDescendantFocusability(DESCENDANT_FOCUSABILITY_FLAGS[typedArray.getInt(n3, 0)]);
                    continue block15;
                }
                case 6: {
                    this.setAddStatesFromChildren(typedArray.getBoolean(n3, false));
                    continue block15;
                }
                case 5: {
                    this.setAlwaysDrawnWithCacheEnabled(typedArray.getBoolean(n3, true));
                    continue block15;
                }
                case 4: {
                    this.setPersistentDrawingCache(typedArray.getInt(n3, 2));
                    continue block15;
                }
                case 3: {
                    this.setAnimationCacheEnabled(typedArray.getBoolean(n3, true));
                    continue block15;
                }
                case 2: {
                    n3 = typedArray.getResourceId(n3, -1);
                    if (n3 <= 0) continue block15;
                    this.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this.mContext, n3));
                    continue block15;
                }
                case 1: {
                    this.setClipToPadding(typedArray.getBoolean(n3, true));
                    continue block15;
                }
                case 0: {
                    this.setClipChildren(typedArray.getBoolean(n3, true));
                }
            }
        }
        typedArray.recycle();
    }

    private void initViewGroup() {
        if (!this.debugDraw()) {
            this.setFlags(128, 128);
        }
        this.mGroupFlags |= 1;
        this.mGroupFlags |= 2;
        this.mGroupFlags |= 16;
        this.mGroupFlags |= 64;
        this.mGroupFlags |= 16384;
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 11) {
            this.mGroupFlags |= 2097152;
        }
        this.setDescendantFocusability(131072);
        this.mChildren = new View[12];
        this.mChildrenCount = 0;
        this.mPersistentDrawingCache = 2;
    }

    private WindowInsets newDispatchApplyWindowInsets(WindowInsets windowInsets) {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).dispatchApplyWindowInsets(windowInsets);
        }
        return windowInsets;
    }

    private void notifyAnimationListener() {
        this.mGroupFlags &= -513;
        this.mGroupFlags |= 16;
        if (this.mAnimationListener != null) {
            this.post(new Runnable(){

                @Override
                public void run() {
                    ViewGroup.this.mAnimationListener.onAnimationEnd(ViewGroup.this.mLayoutAnimationController.getAnimation());
                }
            });
        }
        this.invalidate(true);
    }

    private static MotionEvent obtainMotionEventNoHistoryOrSelf(MotionEvent motionEvent) {
        if (motionEvent.getHistorySize() == 0) {
            return motionEvent;
        }
        return MotionEvent.obtainNoHistory(motionEvent);
    }

    private void populateChildrenForAutofill(ArrayList<View> arrayList, int n) {
        int n2 = this.mChildrenCount;
        if (n2 <= 0) {
            return;
        }
        ArrayList<View> arrayList2 = this.buildOrderedChildList();
        boolean bl = arrayList2 == null && this.isChildrenDrawingOrderEnabled();
        for (int i = 0; i < n2; ++i) {
            int n3 = this.getAndVerifyPreorderedIndex(n2, i, bl);
            View view = arrayList2 == null ? this.mChildren[n3] : arrayList2.get(n3);
            if ((n & 1) == 0 && !view.isImportantForAutofill()) {
                if (!(view instanceof ViewGroup)) continue;
                ((ViewGroup)view).populateChildrenForAutofill(arrayList, n);
                continue;
            }
            arrayList.add(view);
        }
    }

    private void recreateChildDisplayList(View view) {
        boolean bl = (view.mPrivateFlags & Integer.MIN_VALUE) != 0;
        view.mRecreateDisplayList = bl;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    private void removeFromArray(int n) {
        block9 : {
            int n2;
            block8 : {
                View[] arrview;
                block7 : {
                    arrview = this.mChildren;
                    ArrayList<View> arrayList = this.mTransitioningViews;
                    if (arrayList == null || !arrayList.contains(arrview[n])) {
                        arrview[n].mParent = null;
                    }
                    if (n != (n2 = this.mChildrenCount) - 1) break block7;
                    this.mChildrenCount = n2 = this.mChildrenCount - 1;
                    arrview[n2] = null;
                    break block8;
                }
                if (n < 0 || n >= n2) break block9;
                System.arraycopy(arrview, n + 1, arrview, n, n2 - n - 1);
                this.mChildrenCount = n2 = this.mChildrenCount - 1;
                arrview[n2] = null;
            }
            n2 = this.mLastTouchDownIndex;
            if (n2 == n) {
                this.mLastTouchDownTime = 0L;
                this.mLastTouchDownIndex = -1;
            } else if (n2 > n) {
                this.mLastTouchDownIndex = n2 - 1;
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private void removeFromArray(int n, int n2) {
        int n3;
        View[] arrview = this.mChildren;
        int n4 = this.mChildrenCount;
        if ((n = Math.max(0, n)) == (n3 = Math.min(n4, n + n2))) {
            return;
        }
        if (n3 == n4) {
            for (n2 = n; n2 < n3; ++n2) {
                arrview[n2].mParent = null;
                arrview[n2] = null;
            }
        } else {
            for (n2 = n; n2 < n3; ++n2) {
                arrview[n2].mParent = null;
            }
            System.arraycopy(arrview, n3, arrview, n, n4 - n3);
            for (n2 = n4 - (n3 - n); n2 < n4; ++n2) {
                arrview[n2] = null;
            }
        }
        this.mChildrenCount -= n3 - n;
    }

    private void removePointersFromTouchTargets(int n) {
        TouchTarget touchTarget = null;
        TouchTarget touchTarget2 = this.mFirstTouchTarget;
        while (touchTarget2 != null) {
            TouchTarget touchTarget3 = touchTarget2.next;
            if ((touchTarget2.pointerIdBits & n) != 0) {
                touchTarget2.pointerIdBits &= n;
                if (touchTarget2.pointerIdBits == 0) {
                    if (touchTarget == null) {
                        this.mFirstTouchTarget = touchTarget3;
                    } else {
                        touchTarget.next = touchTarget3;
                    }
                    touchTarget2.recycle();
                    touchTarget2 = touchTarget3;
                    continue;
                }
            }
            touchTarget = touchTarget2;
            touchTarget2 = touchTarget3;
        }
    }

    private void removeViewInternal(int n, View view) {
        Object object = this.mTransition;
        if (object != null) {
            ((LayoutTransition)object).removeChild(this, view);
        }
        int n2 = 0;
        if (view == this.mFocused) {
            view.unFocus(null);
            n2 = 1;
        }
        if (view == this.mFocusedInCluster) {
            this.clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        this.cancelTouchTarget(view);
        this.cancelHoverTarget(view);
        if (!(view.getAnimation() != null || (object = this.mTransitioningViews) != null && ((ArrayList)object).contains(view))) {
            if (view.mAttachInfo != null) {
                view.dispatchDetachedFromWindow();
            }
        } else {
            this.addDisappearingView(view);
        }
        boolean bl = view.hasTransientState();
        int n3 = 0;
        if (bl) {
            this.childHasTransientStateChanged(view, false);
        }
        this.needGlobalAttributesUpdate(false);
        this.removeFromArray(n);
        if (view.hasUnhandledKeyListener()) {
            this.decrementChildUnhandledKeyListeners();
        }
        if (view == this.mDefaultFocus) {
            this.clearDefaultFocus(view);
        }
        if (n2 != 0) {
            this.clearChildFocus(view);
            if (!this.rootViewRequestFocus()) {
                this.notifyGlobalFocusCleared(this);
            }
        }
        this.dispatchViewRemoved(view);
        if (view.getVisibility() != 8) {
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        n2 = (object = this.mTransientIndices) == null ? n3 : object.size();
        for (n3 = 0; n3 < n2; ++n3) {
            int n4 = this.mTransientIndices.get(n3);
            if (n >= n4) continue;
            this.mTransientIndices.set(n3, n4 - 1);
        }
        if (this.mCurrentDragStartEvent != null) {
            this.mChildrenInterestedInDrag.remove(view);
        }
    }

    private boolean removeViewInternal(View view) {
        int n = this.indexOfChild(view);
        if (n >= 0) {
            this.removeViewInternal(n, view);
            return true;
        }
        return false;
    }

    private void removeViewsInternal(int n, int n2) {
        int n3 = n + n2;
        if (n >= 0 && n2 >= 0 && n3 <= this.mChildrenCount) {
            View view = this.mFocused;
            boolean bl = this.mAttachInfo != null;
            boolean bl2 = false;
            View view2 = null;
            View[] arrview = this.mChildren;
            for (int i = n; i < n3; ++i) {
                View view3 = arrview[i];
                Object object = this.mTransition;
                if (object != null) {
                    ((LayoutTransition)object).removeChild(this, view3);
                }
                if (view3 == view) {
                    view3.unFocus(null);
                    bl2 = true;
                }
                if (view3 == this.mDefaultFocus) {
                    view2 = view3;
                }
                if (view3 == this.mFocusedInCluster) {
                    this.clearFocusedInCluster(view3);
                }
                view3.clearAccessibilityFocus();
                this.cancelTouchTarget(view3);
                this.cancelHoverTarget(view3);
                if (!(view3.getAnimation() != null || (object = this.mTransitioningViews) != null && ((ArrayList)object).contains(view3))) {
                    if (bl) {
                        view3.dispatchDetachedFromWindow();
                    }
                } else {
                    this.addDisappearingView(view3);
                }
                if (view3.hasTransientState()) {
                    this.childHasTransientStateChanged(view3, false);
                }
                this.needGlobalAttributesUpdate(false);
                this.dispatchViewRemoved(view3);
            }
            this.removeFromArray(n, n2);
            if (view2 != null) {
                this.clearDefaultFocus(view2);
            }
            if (bl2) {
                this.clearChildFocus(view);
                if (!this.rootViewRequestFocus()) {
                    this.notifyGlobalFocusCleared(view);
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private static boolean resetCancelNextUpFlag(View view) {
        if ((view.mPrivateFlags & 67108864) != 0) {
            view.mPrivateFlags &= -67108865;
            return true;
        }
        return false;
    }

    private void resetTouchState() {
        this.clearTouchTargets();
        ViewGroup.resetCancelNextUpFlag(this);
        this.mGroupFlags &= -524289;
        this.mNestedScrollAxes = 0;
    }

    private boolean restoreFocusInClusterInternal(int n) {
        if (this.mFocusedInCluster != null && this.getDescendantFocusability() != 393216 && (this.mFocusedInCluster.mViewFlags & 12) == 0 && this.mFocusedInCluster.restoreFocusInCluster(n)) {
            return true;
        }
        return super.restoreFocusInCluster(n);
    }

    private void setBooleanFlag(int n, boolean bl) {
        this.mGroupFlags = bl ? (this.mGroupFlags |= n) : (this.mGroupFlags &= n);
    }

    private void setLayoutMode(int n, boolean bl) {
        this.mLayoutMode = n;
        this.setBooleanFlag(8388608, bl);
    }

    private void setTouchscreenBlocksFocusNoRefocus(boolean bl) {
        this.mGroupFlags = bl ? (this.mGroupFlags |= 67108864) : (this.mGroupFlags &= -67108865);
    }

    private static int sign(int n) {
        n = n >= 0 ? 1 : -1;
        return n;
    }

    private void touchAccessibilityNodeProviderIfNeeded(View view) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            view.getAccessibilityNodeProvider();
        }
    }

    @Override
    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
        int n;
        if (this.getAccessibilityNodeProvider() != null) {
            return;
        }
        ChildListForAccessibility childListForAccessibility = ChildListForAccessibility.obtain(this, true);
        try {
            n = childListForAccessibility.getChildCount();
        }
        catch (Throwable throwable) {
            childListForAccessibility.recycle();
            throw throwable;
        }
        for (int i = 0; i < n; ++i) {
            View view = childListForAccessibility.getChildAt(i);
            if ((view.mViewFlags & 12) != 0) continue;
            if (view.includeForAccessibility()) {
                arrayList.add(view);
                continue;
            }
            view.addChildrenForAccessibility(arrayList);
        }
        childListForAccessibility.recycle();
        return;
    }

    @Override
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3;
        int n4 = arrayList.size();
        int n5 = this.getDescendantFocusability();
        boolean bl = this.shouldBlockFocusForTouchscreen();
        boolean bl2 = this.isFocusableInTouchMode() || !bl;
        if (n5 == 393216) {
            if (bl2) {
                super.addFocusables(arrayList, n, n2);
            }
            return;
        }
        int n6 = n2;
        if (bl) {
            n6 = n2 | 1;
        }
        if (n5 == 131072 && bl2) {
            super.addFocusables(arrayList, n, n6);
        }
        n2 = 0;
        View[] arrview = new View[this.mChildrenCount];
        for (n3 = 0; n3 < this.mChildrenCount; ++n3) {
            View view = this.mChildren[n3];
            int n7 = n2;
            if ((view.mViewFlags & 12) == 0) {
                arrview[n2] = view;
                n7 = n2 + 1;
            }
            n2 = n7;
        }
        FocusFinder.sort(arrview, 0, n2, this, this.isLayoutRtl());
        for (n3 = 0; n3 < n2; ++n3) {
            arrview[n3].addFocusables(arrayList, n, n6);
        }
        if (n5 == 262144 && bl2 && n4 == arrayList.size()) {
            super.addFocusables(arrayList, n, n6);
        }
    }

    @Override
    public void addKeyboardNavigationClusters(Collection<View> collection, int n) {
        int n2;
        int n3 = collection.size();
        if (this.isKeyboardNavigationCluster()) {
            boolean bl = this.getTouchscreenBlocksFocus();
            try {
                this.setTouchscreenBlocksFocusNoRefocus(false);
                super.addKeyboardNavigationClusters(collection, n);
            }
            finally {
                this.setTouchscreenBlocksFocusNoRefocus(bl);
            }
        } else {
            super.addKeyboardNavigationClusters(collection, n);
        }
        if (n3 != collection.size()) {
            return;
        }
        if (this.getDescendantFocusability() == 393216) {
            return;
        }
        n3 = 0;
        View[] arrview = new View[this.mChildrenCount];
        for (n2 = 0; n2 < this.mChildrenCount; ++n2) {
            View view = this.mChildren[n2];
            int n4 = n3;
            if ((view.mViewFlags & 12) == 0) {
                arrview[n3] = view;
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        FocusFinder.sort(arrview, 0, n3, this, this.isLayoutRtl());
        for (n2 = 0; n2 < n3; ++n2) {
            arrview[n2].addKeyboardNavigationClusters(collection, n);
        }
    }

    public boolean addStatesFromChildren() {
        boolean bl = (this.mGroupFlags & 8192) != 0;
        return bl;
    }

    @Override
    public void addTouchables(ArrayList<View> arrayList) {
        super.addTouchables(arrayList);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if ((view.mViewFlags & 12) != 0) continue;
            view.addTouchables(arrayList);
        }
    }

    @UnsupportedAppUsage
    public void addTransientView(View view, int n) {
        if (n >= 0 && view != null) {
            if (view.mParent == null) {
                int n2;
                if (this.mTransientIndices == null) {
                    this.mTransientIndices = new ArrayList<Integer>();
                    this.mTransientViews = new ArrayList<View>();
                }
                if ((n2 = this.mTransientIndices.size()) > 0) {
                    int n3;
                    for (n3 = 0; n3 < n2 && n >= this.mTransientIndices.get(n3); ++n3) {
                    }
                    this.mTransientIndices.add(n3, n);
                    this.mTransientViews.add(n3, view);
                } else {
                    this.mTransientIndices.add(n);
                    this.mTransientViews.add(view);
                }
                view.mParent = this;
                if (this.mAttachInfo != null) {
                    view.dispatchAttachedToWindow(this.mAttachInfo, this.mViewFlags & 12);
                }
                this.invalidate(true);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The specified view already has a parent ");
            stringBuilder.append(view.mParent);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public void addView(View view) {
        this.addView(view, -1);
    }

    public void addView(View view, int n) {
        if (view != null) {
            LayoutParams layoutParams;
            LayoutParams layoutParams2 = layoutParams = view.getLayoutParams();
            if (layoutParams == null && (layoutParams2 = this.generateDefaultLayoutParams()) == null) {
                throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
            }
            this.addView(view, n, layoutParams2);
            return;
        }
        throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }

    public void addView(View view, int n, int n2) {
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.width = n;
        layoutParams.height = n2;
        this.addView(view, -1, layoutParams);
    }

    public void addView(View view, int n, LayoutParams layoutParams) {
        if (view != null) {
            this.requestLayout();
            this.invalidate(true);
            this.addViewInner(view, n, layoutParams, false);
            return;
        }
        throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }

    @Override
    public void addView(View view, LayoutParams layoutParams) {
        this.addView(view, -1, layoutParams);
    }

    protected boolean addViewInLayout(View view, int n, LayoutParams layoutParams) {
        return this.addViewInLayout(view, n, layoutParams, false);
    }

    protected boolean addViewInLayout(View view, int n, LayoutParams layoutParams, boolean bl) {
        if (view != null) {
            view.mParent = null;
            this.addViewInner(view, n, layoutParams, bl);
            view.mPrivateFlags = view.mPrivateFlags & -2097153 | 32;
            return true;
        }
        throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
    }

    protected void attachLayoutAnimationParameters(View object, LayoutParams layoutParams, int n, int n2) {
        LayoutAnimationController.AnimationParameters animationParameters = layoutParams.layoutAnimationParameters;
        object = animationParameters;
        if (animationParameters == null) {
            layoutParams.layoutAnimationParameters = object = new LayoutAnimationController.AnimationParameters();
        }
        ((LayoutAnimationController.AnimationParameters)object).count = n2;
        ((LayoutAnimationController.AnimationParameters)object).index = n;
    }

    protected void attachViewToParent(View view, int n, LayoutParams layoutParams) {
        view.mLayoutParams = layoutParams;
        int n2 = n;
        if (n < 0) {
            n2 = this.mChildrenCount;
        }
        this.addInArray(view, n2);
        view.mParent = this;
        view.mPrivateFlags = view.mPrivateFlags & -2097153 & -32769 | 32 | Integer.MIN_VALUE;
        this.mPrivateFlags |= Integer.MIN_VALUE;
        if (view.hasFocus()) {
            this.requestChildFocus(view, view.findFocus());
        }
        boolean bl = this.isAttachedToWindow() && this.getWindowVisibility() == 0 && this.isShown();
        this.dispatchVisibilityAggregated(bl);
        this.notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @Override
    public void bringChildToFront(View view) {
        int n = this.indexOfChild(view);
        if (n >= 0) {
            this.removeFromArray(n);
            this.addInArray(view, this.mChildrenCount);
            view.mParent = this;
            this.requestLayout();
            this.invalidate();
        }
    }

    ArrayList<View> buildOrderedChildList() {
        int n = this.mChildrenCount;
        if (n > 1 && this.hasChildWithZ()) {
            ArrayList<View> arrayList = this.mPreSortedChildren;
            if (arrayList == null) {
                this.mPreSortedChildren = new ArrayList(n);
            } else {
                arrayList.clear();
                this.mPreSortedChildren.ensureCapacity(n);
            }
            boolean bl = this.isChildrenDrawingOrderEnabled();
            for (int i = 0; i < n; ++i) {
                int n2 = this.getAndVerifyPreorderedIndex(n, i, bl);
                arrayList = this.mChildren[n2];
                float f = ((View)((Object)arrayList)).getZ();
                for (n2 = i; n2 > 0 && this.mPreSortedChildren.get(n2 - 1).getZ() > f; --n2) {
                }
                this.mPreSortedChildren.add(n2, (View)((Object)arrayList));
            }
            return this.mPreSortedChildren;
        }
        return null;
    }

    public ArrayList<View> buildTouchDispatchChildList() {
        return this.buildOrderedChildList();
    }

    protected boolean canAnimate() {
        boolean bl = this.mLayoutAnimationController != null;
        return bl;
    }

    @Override
    public void captureTransitioningViews(List<View> list) {
        if (this.getVisibility() != 0) {
            return;
        }
        if (this.isTransitionGroup()) {
            list.add(this);
        } else {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                this.getChildAt(i).captureTransitioningViews(list);
            }
        }
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        boolean bl = layoutParams != null;
        return bl;
    }

    @Override
    public void childDrawableStateChanged(View view) {
        if ((this.mGroupFlags & 8192) != 0) {
            this.refreshDrawableState();
        }
    }

    @Override
    public void childHasTransientStateChanged(View object, boolean bl) {
        boolean bl2 = this.hasTransientState();
        this.mChildCountWithTransientState = bl ? ++this.mChildCountWithTransientState : --this.mChildCountWithTransientState;
        bl = this.hasTransientState();
        if (this.mParent != null && bl2 != bl) {
            try {
                this.mParent.childHasTransientStateChanged(this, bl);
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mParent.getClass().getSimpleName());
                ((StringBuilder)object).append(" does not fully implement ViewParent");
                Log.e(TAG, ((StringBuilder)object).toString(), abstractMethodError);
            }
        }
    }

    protected void cleanupLayoutState(View view) {
        view.mPrivateFlags &= -4097;
    }

    @Override
    public void clearChildFocus(View view) {
        this.mFocused = null;
        if (this.mParent != null) {
            this.mParent.clearChildFocus(this);
        }
    }

    void clearDefaultFocus(View view) {
        View view2 = this.mDefaultFocus;
        if (view2 != view && view2 != null && view2.isFocusedByDefault()) {
            return;
        }
        this.mDefaultFocus = null;
        for (int i = 0; i < this.mChildrenCount; ++i) {
            view = this.mChildren[i];
            if (view.isFocusedByDefault()) {
                this.mDefaultFocus = view;
                return;
            }
            if (this.mDefaultFocus != null || !view.hasDefaultFocus()) continue;
            this.mDefaultFocus = view;
        }
        if (this.mParent instanceof ViewGroup) {
            ((ViewGroup)this.mParent).clearDefaultFocus(this);
        }
    }

    public void clearDisappearingChildren() {
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                View view = arrayList.get(i);
                if (view.mAttachInfo != null) {
                    view.dispatchDetachedFromWindow();
                }
                view.clearAnimation();
            }
            arrayList.clear();
            this.invalidate();
        }
    }

    @Override
    public void clearFocus() {
        if (this.mFocused == null) {
            super.clearFocus();
        } else {
            View view = this.mFocused;
            this.mFocused = null;
            view.clearFocus();
        }
    }

    void clearFocusedInCluster() {
        ViewParent viewParent;
        View view = this.findKeyboardNavigationCluster();
        ViewParent viewParent2 = this;
        do {
            viewParent2.mFocusedInCluster = null;
            if (viewParent2 == view) break;
            viewParent = viewParent2.getParent();
            viewParent2 = viewParent;
        } while (viewParent instanceof ViewGroup);
    }

    void clearFocusedInCluster(View view) {
        if (this.mFocusedInCluster != view) {
            return;
        }
        this.clearFocusedInCluster();
    }

    @Override
    Insets computeOpticalInsets() {
        if (this.isLayoutModeOptical()) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            for (int i = 0; i < this.mChildrenCount; ++i) {
                Object object = this.getChildAt(i);
                int n5 = n;
                int n6 = n2;
                int n7 = n3;
                int n8 = n4;
                if (((View)object).getVisibility() == 0) {
                    object = ((View)object).getOpticalInsets();
                    n5 = Math.max(n, ((Insets)object).left);
                    n6 = Math.max(n2, ((Insets)object).top);
                    n7 = Math.max(n3, ((Insets)object).right);
                    n8 = Math.max(n4, ((Insets)object).bottom);
                }
                n = n5;
                n2 = n6;
                n3 = n7;
                n4 = n8;
            }
            return Insets.of(n, n2, n3, n4);
        }
        return Insets.NONE;
    }

    @Override
    public Bitmap createSnapshot(ViewDebug.CanvasProvider object, boolean bl) {
        int n;
        Object object2;
        int n2 = this.mChildrenCount;
        Object object3 = null;
        if (bl) {
            object2 = new int[n2];
            n = 0;
            do {
                object3 = object2;
                if (n >= n2) break;
                object3 = this.getChildAt(n);
                object2[n] = ((View)object3).getVisibility();
                if (object2[n] == 0) {
                    ((View)object3).mViewFlags = ((View)object3).mViewFlags & -13 | 4;
                }
                ++n;
            } while (true);
        }
        try {
            object = super.createSnapshot((ViewDebug.CanvasProvider)object, bl);
            return object;
        }
        finally {
            if (bl) {
                for (n = 0; n < n2; ++n) {
                    object2 = this.getChildAt(n);
                    object2.mViewFlags = object2.mViewFlags & -13 | object3[n] & 12;
                }
            }
        }
    }

    @Override
    protected void debug(int n) {
        CharSequence charSequence;
        CharSequence charSequence2;
        super.debug(n);
        if (this.mFocused != null) {
            charSequence = ViewGroup.debugIndent(n);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("mFocused");
            Log.d("View", ((StringBuilder)charSequence2).toString());
            this.mFocused.debug(n + 1);
        }
        if (this.mDefaultFocus != null) {
            charSequence2 = ViewGroup.debugIndent(n);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("mDefaultFocus");
            Log.d("View", ((StringBuilder)charSequence).toString());
            this.mDefaultFocus.debug(n + 1);
        }
        if (this.mFocusedInCluster != null) {
            charSequence = ViewGroup.debugIndent(n);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("mFocusedInCluster");
            Log.d("View", ((StringBuilder)charSequence2).toString());
            this.mFocusedInCluster.debug(n + 1);
        }
        if (this.mChildrenCount != 0) {
            charSequence2 = ViewGroup.debugIndent(n);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("{");
            Log.d("View", ((StringBuilder)charSequence).toString());
        }
        int n2 = this.mChildrenCount;
        for (int i = 0; i < n2; ++i) {
            this.mChildren[i].debug(n + 1);
        }
        if (this.mChildrenCount != 0) {
            charSequence2 = ViewGroup.debugIndent(n);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("}");
            Log.d("View", ((StringBuilder)charSequence).toString());
        }
    }

    void decrementChildUnhandledKeyListeners() {
        --this.mChildUnhandledKeyListeners;
        if (this.mChildUnhandledKeyListeners == 0 && this.mParent instanceof ViewGroup) {
            ((ViewGroup)this.mParent).decrementChildUnhandledKeyListeners();
        }
    }

    @Override
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).destroyHardwareResources();
        }
    }

    protected void detachAllViewsFromParent() {
        int n = this.mChildrenCount;
        if (n <= 0) {
            return;
        }
        View[] arrview = this.mChildren;
        this.mChildrenCount = 0;
        --n;
        while (n >= 0) {
            arrview[n].mParent = null;
            arrview[n] = null;
            --n;
        }
    }

    protected void detachViewFromParent(int n) {
        this.removeFromArray(n);
    }

    protected void detachViewFromParent(View view) {
        this.removeFromArray(this.indexOfChild(view));
    }

    protected void detachViewsFromParent(int n, int n2) {
        this.removeFromArray(n, n2);
    }

    @Override
    public boolean dispatchActivityResult(String string2, int n, int n2, Intent intent) {
        if (super.dispatchActivityResult(string2, n, n2, intent)) {
            return true;
        }
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            if (!this.getChildAt(i).dispatchActivityResult(string2, n, n2, intent)) continue;
            return true;
        }
        return false;
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        windowInsets = super.dispatchApplyWindowInsets(windowInsets);
        if (View.sBrokenInsetsDispatch) {
            return this.brokenDispatchApplyWindowInsets(windowInsets);
        }
        return this.newDispatchApplyWindowInsets(windowInsets);
    }

    @UnsupportedAppUsage
    @Override
    void dispatchAttachedToWindow(View.AttachInfo attachInfo, int n) {
        int n2;
        List<Integer> list;
        this.mGroupFlags |= 4194304;
        super.dispatchAttachedToWindow(attachInfo, n);
        this.mGroupFlags &= -4194305;
        int n3 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (n2 = 0; n2 < n3; ++n2) {
            list = arrview[n2];
            ((View)((Object)list)).dispatchAttachedToWindow(attachInfo, this.combineVisibility(n, ((View)((Object)list)).getVisibility()));
        }
        list = this.mTransientIndices;
        n2 = list == null ? 0 : list.size();
        for (n3 = 0; n3 < n2; ++n3) {
            list = this.mTransientViews.get(n3);
            ((View)((Object)list)).dispatchAttachedToWindow(attachInfo, this.combineVisibility(n, ((View)((Object)list)).getVisibility()));
        }
    }

    @Override
    void dispatchCancelPendingInputEvents() {
        super.dispatchCancelPendingInputEvents();
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchCancelPendingInputEvents();
        }
    }

    @Override
    public boolean dispatchCapturedPointerEvent(MotionEvent motionEvent) {
        View view;
        return (this.mPrivateFlags & 18) == 18 ? super.dispatchCapturedPointerEvent(motionEvent) : (view = this.mFocused) != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchCapturedPointerEvent(motionEvent);
    }

    @Override
    void dispatchCollectViewAttributes(View.AttachInfo attachInfo, int n) {
        if ((n & 12) == 0) {
            super.dispatchCollectViewAttributes(attachInfo, n);
            int n2 = this.mChildrenCount;
            View[] arrview = this.mChildren;
            for (int i = 0; i < n2; ++i) {
                View view = arrview[i];
                view.dispatchCollectViewAttributes(attachInfo, view.mViewFlags & 12 | n);
            }
        }
    }

    @Override
    public void dispatchConfigurationChanged(Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchConfigurationChanged(configuration);
        }
    }

    @UnsupportedAppUsage
    @Override
    void dispatchDetachedFromWindow() {
        int n;
        this.cancelAndClearTouchTargets(null);
        this.exitHoverTargets();
        this.exitTooltipHoverTargets();
        int n2 = 0;
        this.mLayoutCalledWhileSuppressed = false;
        this.mChildrenInterestedInDrag = null;
        this.mIsInterestedInDrag = false;
        View[] arrview = this.mCurrentDragStartEvent;
        if (arrview != null) {
            arrview.recycle();
            this.mCurrentDragStartEvent = null;
        }
        int n3 = this.mChildrenCount;
        arrview = this.mChildren;
        for (n = 0; n < n3; ++n) {
            arrview[n].dispatchDetachedFromWindow();
        }
        this.clearDisappearingChildren();
        n = this.mTransientViews == null ? n2 : this.mTransientIndices.size();
        for (n2 = 0; n2 < n; ++n2) {
            this.mTransientViews.get(n2).dispatchDetachedFromWindow();
        }
        super.dispatchDetachedFromWindow();
    }

    @Override
    public void dispatchDisplayHint(int n) {
        super.dispatchDisplayHint(n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchDisplayHint(n);
        }
    }

    @Override
    boolean dispatchDragEnterExitInPreN(DragEvent dragEvent) {
        View view;
        if (dragEvent.mAction == 6 && (view = this.mCurrentDragChild) != null) {
            view.dispatchDragEnterExitInPreN(dragEvent);
            this.mCurrentDragChild = null;
        }
        boolean bl = this.mIsInterestedInDrag && super.dispatchDragEnterExitInPreN(dragEvent);
        return bl;
    }

    @Override
    public boolean dispatchDragEvent(DragEvent dragEvent) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        float f = dragEvent.mX;
        float f2 = dragEvent.mY;
        ClipData clipData = dragEvent.mClipData;
        PointF pointF = this.getLocalPoint();
        int n = dragEvent.mAction;
        if (n != 1) {
            if (n != 2 && n != 3) {
                if (n != 4) {
                    bl = bl3;
                } else {
                    HashSet<View> hashSet = this.mChildrenInterestedInDrag;
                    if (hashSet != null) {
                        Iterator<View> iterator = hashSet.iterator();
                        bl = bl4;
                        while (iterator.hasNext()) {
                            if (!iterator.next().dispatchDragEvent(dragEvent)) continue;
                            bl = true;
                        }
                        hashSet.clear();
                    }
                    if ((hashSet = this.mCurrentDragStartEvent) != null) {
                        ((DragEvent)((Object)hashSet)).recycle();
                        this.mCurrentDragStartEvent = null;
                    }
                    bl4 = bl;
                    if (this.mIsInterestedInDrag) {
                        if (super.dispatchDragEvent(dragEvent)) {
                            bl = true;
                        }
                        this.mIsInterestedInDrag = false;
                        bl4 = bl;
                    }
                    bl = bl4;
                }
            } else {
                View view;
                View view2 = this.findFrontmostDroppableChildAt(dragEvent.mX, dragEvent.mY, pointF);
                if (view2 != this.mCurrentDragChild) {
                    if (sCascadedDragDrop) {
                        n = dragEvent.mAction;
                        dragEvent.mX = 0.0f;
                        dragEvent.mY = 0.0f;
                        dragEvent.mClipData = null;
                        view = this.mCurrentDragChild;
                        if (view != null) {
                            dragEvent.mAction = 6;
                            view.dispatchDragEnterExitInPreN(dragEvent);
                        }
                        if (view2 != null) {
                            dragEvent.mAction = 5;
                            view2.dispatchDragEnterExitInPreN(dragEvent);
                        }
                        dragEvent.mAction = n;
                        dragEvent.mX = f;
                        dragEvent.mY = f2;
                        dragEvent.mClipData = clipData;
                    }
                    this.mCurrentDragChild = view2;
                }
                view = view2;
                if (view2 == null) {
                    view = view2;
                    if (this.mIsInterestedInDrag) {
                        view = this;
                    }
                }
                bl = bl3;
                if (view != null) {
                    if (view != this) {
                        dragEvent.mX = pointF.x;
                        dragEvent.mY = pointF.y;
                        bl4 = view.dispatchDragEvent(dragEvent);
                        dragEvent.mX = f;
                        dragEvent.mY = f2;
                        bl = bl4;
                        if (this.mIsInterestedInDrag) {
                            bl2 = sCascadedDragDrop ? bl4 : dragEvent.mEventHandlerWasCalled;
                            bl = bl4;
                            if (!bl2) {
                                bl = super.dispatchDragEvent(dragEvent);
                            }
                        }
                    } else {
                        bl = super.dispatchDragEvent(dragEvent);
                    }
                }
            }
        } else {
            this.mCurrentDragChild = null;
            this.mCurrentDragStartEvent = DragEvent.obtain(dragEvent);
            View[] arrview = this.mChildrenInterestedInDrag;
            if (arrview == null) {
                this.mChildrenInterestedInDrag = new HashSet();
            } else {
                arrview.clear();
            }
            int n2 = this.mChildrenCount;
            arrview = this.mChildren;
            bl = bl2;
            for (n = 0; n < n2; ++n) {
                View view = arrview[n];
                view.mPrivateFlags2 &= -4;
                bl4 = bl;
                if (view.getVisibility() == 0) {
                    bl4 = bl;
                    if (this.notifyChildOfDragStart(arrview[n])) {
                        bl4 = true;
                    }
                }
                bl = bl4;
            }
            this.mIsInterestedInDrag = super.dispatchDragEvent(dragEvent);
            if (this.mIsInterestedInDrag) {
                bl = true;
            }
            if (!bl) {
                this.mCurrentDragStartEvent.recycle();
                this.mCurrentDragStartEvent = null;
            }
        }
        return bl;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        ArrayList<View> arrayList;
        int n;
        int n2;
        Object object;
        boolean bl = canvas.isRecordingFor(this.mRenderNode);
        int n3 = this.mChildrenCount;
        Object object2 = this.mChildren;
        int n4 = this.mGroupFlags;
        if ((n4 & 8) != 0 && this.canAnimate()) {
            this.isHardwareAccelerated();
            for (n2 = 0; n2 < n3; ++n2) {
                arrayList = object2[n2];
                if ((((View)arrayList).mViewFlags & 12) != 0) continue;
                this.attachLayoutAnimationParameters((View)((Object)arrayList), ((View)((Object)arrayList)).getLayoutParams(), n2, n3);
                this.bindLayoutAnimation((View)((Object)arrayList));
            }
            object = this.mLayoutAnimationController;
            if (((LayoutAnimationController)object).willOverlap()) {
                this.mGroupFlags |= 128;
            }
            ((LayoutAnimationController)object).start();
            this.mGroupFlags &= -9;
            this.mGroupFlags &= -17;
            arrayList = this.mAnimationListener;
            if (arrayList != null) {
                arrayList.onAnimationStart(((LayoutAnimationController)object).getAnimation());
            }
        }
        int n5 = 0;
        boolean bl2 = false;
        boolean bl3 = (n4 & 34) == 34;
        if (bl3) {
            n5 = canvas.save(2);
            canvas.clipRect(this.mScrollX + this.mPaddingLeft, this.mScrollY + this.mPaddingTop, this.mScrollX + this.mRight - this.mLeft - this.mPaddingRight, this.mScrollY + this.mBottom - this.mTop - this.mPaddingBottom);
        }
        this.mPrivateFlags &= -65;
        this.mGroupFlags &= -5;
        long l = this.getDrawingTime();
        if (bl) {
            canvas.insertReorderBarrier();
        }
        int n6 = (arrayList = this.mTransientIndices) == null ? 0 : arrayList.size();
        int n7 = n6 != 0 ? 0 : -1;
        arrayList = bl ? null : this.buildOrderedChildList();
        if (arrayList == null && this.isChildrenDrawingOrderEnabled()) {
            bl2 = true;
        }
        n2 = 0;
        for (int i = 0; i < n3; ++i) {
            block31 : {
                int n8;
                block30 : {
                    n8 = n2;
                    n2 = n4;
                    while (n7 >= 0 && this.mTransientIndices.get(n7) == i) {
                        block29 : {
                            block28 : {
                                object = this.mTransientViews.get(n7);
                                if ((((View)object).mViewFlags & 12) == 0) break block28;
                                n4 = n8;
                                if (((View)object).getAnimation() == null) break block29;
                            }
                            n4 = n8 | this.drawChild(canvas, (View)object, l);
                        }
                        n = ++n7;
                        if (n7 >= n6) {
                            n = -1;
                        }
                        n7 = n;
                        n8 = n4;
                    }
                    object = ViewGroup.getAndVerifyPreorderedView(arrayList, (View[])object2, this.getAndVerifyPreorderedIndex(n3, i, bl2));
                    if ((((View)object).mViewFlags & 12) == 0) break block30;
                    n = n8;
                    if (((View)object).getAnimation() == null) break block31;
                }
                n = n8 | this.drawChild(canvas, (View)object, l);
            }
            n4 = n2;
            n2 = n;
        }
        n = n2;
        do {
            block33 : {
                block32 : {
                    n2 = n;
                    if (n7 < 0) break;
                    object2 = this.mTransientViews.get(n7);
                    if ((((View)object2).mViewFlags & 12) == 0) break block32;
                    n2 = n;
                    if (((View)object2).getAnimation() == null) break block33;
                }
                n2 = n | this.drawChild(canvas, (View)object2, l);
            }
            if (++n7 >= n6) break;
            n = n2;
        } while (true);
        if (arrayList != null) {
            arrayList.clear();
        }
        n = n2;
        if (this.mDisappearingChildren != null) {
            arrayList = this.mDisappearingChildren;
            n7 = arrayList.size() - 1;
            do {
                n = n2;
                if (n7 < 0) break;
                n2 |= this.drawChild(canvas, arrayList.get(n7), l);
                --n7;
            } while (true);
        }
        if (bl) {
            canvas.insertInorderBarrier();
        }
        if (this.debugDraw()) {
            this.onDebugDraw(canvas);
        }
        if (bl3) {
            canvas.restoreToCount(n5);
        }
        if (((n2 = this.mGroupFlags) & 4) == 4) {
            this.invalidate(true);
        }
        if ((n2 & 16) == 0 && (n2 & 512) == 0 && this.mLayoutAnimationController.isDone() && n == 0) {
            this.mGroupFlags |= 512;
            this.post(new Runnable(){

                @Override
                public void run() {
                    ViewGroup.this.notifyAnimationListener();
                }
            });
        }
    }

    @Override
    public void dispatchDrawableHotspotChanged(float f, float f2) {
        int n = this.mChildrenCount;
        if (n == 0) {
            return;
        }
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            boolean bl = !view.isClickable() && !view.isLongClickable();
            boolean bl2 = (view.mViewFlags & 4194304) != 0;
            if (!bl && !bl2) continue;
            float[] arrf = this.getTempPoint();
            arrf[0] = f;
            arrf[1] = f2;
            this.transformPointToViewLocal(arrf, view);
            view.drawableHotspotChanged(arrf[0], arrf[1]);
        }
    }

    @Override
    public void dispatchFinishTemporaryDetach() {
        super.dispatchFinishTemporaryDetach();
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchFinishTemporaryDetach();
        }
    }

    protected void dispatchFreezeSelfOnly(SparseArray<Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
    }

    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchGenericFocusedEvent(motionEvent);
        }
        View view = this.mFocused;
        if (view != null && (view.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchGenericMotionEvent(motionEvent);
        }
        return false;
    }

    @Override
    protected boolean dispatchGenericPointerEvent(MotionEvent motionEvent) {
        int n = this.mChildrenCount;
        if (n != 0) {
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            ArrayList<View> arrayList = this.buildOrderedChildList();
            boolean bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
            View[] arrview = this.mChildren;
            for (int i = n - 1; i >= 0; --i) {
                View view = ViewGroup.getAndVerifyPreorderedView(arrayList, arrview, this.getAndVerifyPreorderedIndex(n, i, bl));
                if (!view.canReceivePointerEvents() || !this.isTransformedTouchPointInView(f, f2, view, null) || !this.dispatchTransformedGenericPointerEvent(motionEvent, view)) continue;
                if (arrayList != null) {
                    arrayList.clear();
                }
                return true;
            }
            if (arrayList != null) {
                arrayList.clear();
            }
        }
        return super.dispatchGenericPointerEvent(motionEvent);
    }

    @UnsupportedAppUsage
    @Override
    protected void dispatchGetDisplayList() {
        Object object;
        int n;
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (n = 0; n < n2; ++n) {
            object = arrview[n];
            if ((((View)object).mViewFlags & 12) != 0 && ((View)object).getAnimation() == null) continue;
            this.recreateChildDisplayList((View)object);
        }
        n = this.mTransientViews == null ? 0 : this.mTransientIndices.size();
        for (n2 = 0; n2 < n; ++n2) {
            object = this.mTransientViews.get(n2);
            if ((((View)object).mViewFlags & 12) != 0 && ((View)object).getAnimation() == null) continue;
            this.recreateChildDisplayList((View)object);
        }
        if (this.mOverlay != null) {
            this.recreateChildDisplayList(this.mOverlay.getOverlayView());
        }
        if (this.mDisappearingChildren != null) {
            object = this.mDisappearingChildren;
            n2 = ((ArrayList)object).size();
            for (n = 0; n < n2; ++n) {
                this.recreateChildDisplayList((View)((ArrayList)object).get(n));
            }
        }
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean bl;
        int n;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        Object object;
        Object object2;
        Object object3;
        block37 : {
            block33 : {
                Object object4;
                ArrayList<View> arrayList;
                block32 : {
                    n = motionEvent.getAction();
                    bl4 = this.onInterceptHoverEvent(motionEvent);
                    motionEvent.setAction(n);
                    object = motionEvent;
                    object3 = this.mFirstHoverTarget;
                    this.mFirstHoverTarget = null;
                    if (bl4 || n == 10) break block33;
                    float f = motionEvent.getX();
                    float f2 = motionEvent.getY();
                    int n2 = this.mChildrenCount;
                    if (n2 == 0) break block33;
                    arrayList = this.buildOrderedChildList();
                    bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
                    bl3 = bl;
                    View[] arrview = this.mChildren;
                    bl = false;
                    object4 = null;
                    object2 = object;
                    object = object3;
                    block0 : for (int i = n2 - 1; i >= 0; --i) {
                        View view = ViewGroup.getAndVerifyPreorderedView(arrayList, arrview, this.getAndVerifyPreorderedIndex(n2, i, bl3));
                        if (!view.canReceivePointerEvents() || !this.isTransformedTouchPointInView(f, f2, view, null)) continue;
                        object3 = object;
                        Object object5 = null;
                        do {
                            block36 : {
                                boolean bl5;
                                block35 : {
                                    block34 : {
                                        if (object3 != null) break block34;
                                        object3 = HoverTarget.obtain(view);
                                        bl5 = false;
                                        break block35;
                                    }
                                    if (((HoverTarget)object3).child != view) break block36;
                                    if (object5 != null) {
                                        ((HoverTarget)object5).next = ((HoverTarget)object3).next;
                                    } else {
                                        object = ((HoverTarget)object3).next;
                                    }
                                    ((HoverTarget)object3).next = null;
                                    bl5 = true;
                                }
                                if (object4 != null) {
                                    ((HoverTarget)object4).next = object3;
                                } else {
                                    this.mFirstHoverTarget = object3;
                                }
                                if (n == 9) {
                                    if (!bl5) {
                                        bl |= this.dispatchTransformedGenericPointerEvent(motionEvent, view);
                                    }
                                } else if (n == 7) {
                                    if (!bl5) {
                                        object2 = ViewGroup.obtainMotionEventNoHistoryOrSelf((MotionEvent)object2);
                                        ((MotionEvent)object2).setAction(9);
                                        bl2 = this.dispatchTransformedGenericPointerEvent((MotionEvent)object2, view);
                                        ((MotionEvent)object2).setAction(n);
                                        bl = bl | bl2 | this.dispatchTransformedGenericPointerEvent((MotionEvent)object2, view);
                                    } else {
                                        bl |= this.dispatchTransformedGenericPointerEvent(motionEvent, view);
                                    }
                                }
                                if (bl) {
                                    object4 = object2;
                                    object3 = object;
                                    bl3 = bl;
                                    break block32;
                                }
                                object4 = object3;
                                continue block0;
                            }
                            object5 = object3;
                            object3 = ((HoverTarget)object3).next;
                        } while (true);
                    }
                    bl3 = bl;
                    object3 = object;
                    object4 = object2;
                }
                object = object4;
                object2 = object3;
                bl = bl3;
                if (arrayList != null) {
                    arrayList.clear();
                    object = object4;
                    object2 = object3;
                    bl = bl3;
                }
                break block37;
            }
            bl = false;
            object2 = object3;
        }
        while (object2 != null) {
            object3 = ((HoverTarget)object2).child;
            if (n == 10) {
                bl |= this.dispatchTransformedGenericPointerEvent(motionEvent, (View)object3);
            } else {
                if (n == 7) {
                    bl3 = motionEvent.isHoverExitPending();
                    motionEvent.setHoverExitPending(true);
                    this.dispatchTransformedGenericPointerEvent(motionEvent, (View)object3);
                    motionEvent.setHoverExitPending(bl3);
                }
                object = ViewGroup.obtainMotionEventNoHistoryOrSelf((MotionEvent)object);
                ((MotionEvent)object).setAction(10);
                this.dispatchTransformedGenericPointerEvent((MotionEvent)object, (View)object3);
                ((MotionEvent)object).setAction(n);
            }
            object3 = ((HoverTarget)object2).next;
            ((HoverTarget)object2).recycle();
            object2 = object3;
        }
        bl2 = !bl && n != 10 && !motionEvent.isHoverExitPending();
        if (bl2 == (bl3 = this.mHoveredSelf)) {
            object3 = object;
            bl3 = bl;
            if (bl2) {
                bl3 = bl | super.dispatchHoverEvent(motionEvent);
                object3 = object;
            }
        } else {
            object2 = object;
            bl4 = bl;
            if (bl3) {
                if (n == 10) {
                    bl |= super.dispatchHoverEvent(motionEvent);
                } else {
                    if (n == 7) {
                        super.dispatchHoverEvent(motionEvent);
                    }
                    object = ViewGroup.obtainMotionEventNoHistoryOrSelf((MotionEvent)object);
                    ((MotionEvent)object).setAction(10);
                    super.dispatchHoverEvent((MotionEvent)object);
                    ((MotionEvent)object).setAction(n);
                }
                this.mHoveredSelf = false;
                bl4 = bl;
                object2 = object;
            }
            object3 = object2;
            bl3 = bl4;
            if (bl2) {
                if (n == 9) {
                    bl3 = bl4 | super.dispatchHoverEvent(motionEvent);
                    this.mHoveredSelf = true;
                    object3 = object2;
                } else {
                    object3 = object2;
                    bl3 = bl4;
                    if (n == 7) {
                        object3 = ViewGroup.obtainMotionEventNoHistoryOrSelf((MotionEvent)object2);
                        ((MotionEvent)object3).setAction(9);
                        bl = super.dispatchHoverEvent((MotionEvent)object3);
                        ((MotionEvent)object3).setAction(n);
                        bl3 = bl4 | bl | super.dispatchHoverEvent((MotionEvent)object3);
                        this.mHoveredSelf = true;
                    }
                }
            }
        }
        if (object3 != motionEvent) {
            ((MotionEvent)object3).recycle();
        }
        return bl3;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View view;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(keyEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18 ? super.dispatchKeyEvent(keyEvent) : (view = this.mFocused) != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(keyEvent, 1);
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyEventPreIme(keyEvent);
        }
        View view = this.mFocused;
        if (view != null && (view.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchKeyEventPreIme(keyEvent);
        }
        return false;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if ((this.mPrivateFlags & 18) == 18) {
            return super.dispatchKeyShortcutEvent(keyEvent);
        }
        View view = this.mFocused;
        if (view != null && (view.mPrivateFlags & 16) == 16) {
            return this.mFocused.dispatchKeyShortcutEvent(keyEvent);
        }
        return false;
    }

    @Override
    void dispatchMovedToDisplay(Display display, Configuration configuration) {
        super.dispatchMovedToDisplay(display, configuration);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchMovedToDisplay(display, configuration);
        }
    }

    @Override
    public void dispatchPointerCaptureChanged(boolean bl) {
        this.exitHoverTargets();
        super.dispatchPointerCaptureChanged(bl);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchPointerCaptureChanged(bl);
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        int n;
        boolean bl;
        if (this.includeForAccessibility() && (bl = super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent))) {
            return bl;
        }
        ChildListForAccessibility childListForAccessibility = ChildListForAccessibility.obtain(this, true);
        try {
            n = childListForAccessibility.getChildCount();
        }
        catch (Throwable throwable) {
            childListForAccessibility.recycle();
            throw throwable;
        }
        for (int i = 0; i < n; ++i) {
            View view = childListForAccessibility.getChildAt(i);
            if ((view.mViewFlags & 12) != 0 || !(bl = view.dispatchPopulateAccessibilityEvent(accessibilityEvent))) continue;
            childListForAccessibility.recycle();
            return bl;
        }
        childListForAccessibility.recycle();
        return false;
    }

    @Override
    public void dispatchProvideAutofillStructure(ViewStructure object, int n) {
        super.dispatchProvideAutofillStructure((ViewStructure)object, n);
        if (((ViewStructure)object).getChildCount() != 0) {
            return;
        }
        if (!this.isLaidOut()) {
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("dispatchProvideAutofillStructure(): not laid out, ignoring ");
                ((StringBuilder)object).append(this.mChildrenCount);
                ((StringBuilder)object).append(" children of ");
                ((StringBuilder)object).append(this.getAutofillId());
                Log.v("View", ((StringBuilder)object).toString());
            }
            return;
        }
        ChildListForAutofill childListForAutofill = this.getChildrenForAutofill(n);
        int n2 = childListForAutofill.size();
        ((ViewStructure)object).setChildCount(n2);
        for (int i = 0; i < n2; ++i) {
            ((View)childListForAutofill.get(i)).dispatchProvideAutofillStructure(((ViewStructure)object).newChild(i), n);
        }
        childListForAutofill.recycle();
    }

    @Override
    public void dispatchProvideStructure(ViewStructure object) {
        super.dispatchProvideStructure((ViewStructure)object);
        if (!this.isAssistBlocked() && ((ViewStructure)object).getChildCount() == 0) {
            int n = this.mChildrenCount;
            if (n <= 0) {
                return;
            }
            if (!this.isLaidOut()) {
                if (Helper.sVerbose) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("dispatchProvideStructure(): not laid out, ignoring ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" children of ");
                    ((StringBuilder)object).append(this.getAccessibilityViewId());
                    Log.v("View", ((StringBuilder)object).toString());
                }
                return;
            }
            ((ViewStructure)object).setChildCount(n);
            Object object2 = this.buildOrderedChildList();
            boolean bl = object2 == null && this.isChildrenDrawingOrderEnabled();
            for (int i = 0; i < n; ++i) {
                IndexOutOfBoundsException indexOutOfBoundsException2;
                block12 : {
                    int n2;
                    block13 : {
                        try {
                            n2 = this.getAndVerifyPreorderedIndex(n, i, bl);
                        }
                        catch (IndexOutOfBoundsException indexOutOfBoundsException2) {
                            int n3;
                            boolean bl2;
                            int n4 = i;
                            if (this.mContext.getApplicationInfo().targetSdkVersion >= 23) break block12;
                            Serializable serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Bad getChildDrawingOrder while collecting assist @ ");
                            ((StringBuilder)serializable).append(i);
                            ((StringBuilder)serializable).append(" of ");
                            ((StringBuilder)serializable).append(n);
                            Log.w(TAG, ((StringBuilder)serializable).toString(), indexOutOfBoundsException2);
                            bl = bl2 = false;
                            n2 = n4;
                            if (i <= 0) break block13;
                            int[] arrn = new int[n];
                            object2 = new SparseBooleanArray();
                            for (n3 = 0; n3 < i; ++n3) {
                                arrn[n3] = this.getChildDrawingOrder(n, n3);
                                ((SparseBooleanArray)object2).put(arrn[n3], true);
                            }
                            n3 = 0;
                            for (n2 = i; n2 < n; ++n2) {
                                while (((SparseBooleanArray)object2).get(n3, false)) {
                                    ++n3;
                                }
                                arrn[n2] = n3++;
                            }
                            serializable = new ArrayList(n);
                            n3 = 0;
                            do {
                                object2 = serializable;
                                bl = bl2;
                                n2 = n4;
                                if (n3 >= n) break;
                                n2 = arrn[n3];
                                ((ArrayList)serializable).add(this.mChildren[n2]);
                                ++n3;
                            } while (true);
                        }
                    }
                    ViewGroup.getAndVerifyPreorderedView(object2, this.mChildren, n2).dispatchProvideStructure(((ViewStructure)object).newChild(i));
                    continue;
                }
                throw indexOutOfBoundsException2;
            }
            if (object2 != null) {
                ((ArrayList)object2).clear();
            }
            return;
        }
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if ((view.mViewFlags & 536870912) == 536870912) continue;
            view.dispatchRestoreInstanceState(sparseArray);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchSaveInstanceState(sparseArray);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if ((view.mViewFlags & 536870912) == 536870912) continue;
            view.dispatchSaveInstanceState(sparseArray);
        }
    }

    @Override
    void dispatchScreenStateChanged(int n) {
        super.dispatchScreenStateChanged(n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchScreenStateChanged(n);
        }
    }

    @Override
    public void dispatchSetActivated(boolean bl) {
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            arrview[i].setActivated(bl);
        }
    }

    @Override
    protected void dispatchSetPressed(boolean bl) {
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if (bl && (view.isClickable() || view.isLongClickable())) continue;
            view.setPressed(bl);
        }
    }

    @Override
    public void dispatchSetSelected(boolean bl) {
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            arrview[i].setSelected(bl);
        }
    }

    @Override
    public void dispatchStartTemporaryDetach() {
        super.dispatchStartTemporaryDetach();
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchStartTemporaryDetach();
        }
    }

    @Override
    public void dispatchSystemUiVisibilityChanged(int n) {
        super.dispatchSystemUiVisibilityChanged(n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchSystemUiVisibilityChanged(n);
        }
    }

    protected void dispatchThawSelfOnly(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
    }

    @Override
    boolean dispatchTooltipHoverEvent(MotionEvent motionEvent) {
        View view;
        int n = motionEvent.getAction();
        if (n != 7) {
            if (n != 9 && n == 10) {
                View view2 = this.mTooltipHoverTarget;
                if (view2 != null) {
                    view2.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoverTarget = null;
                } else if (this.mTooltipHoveredSelf) {
                    super.dispatchTooltipHoverEvent(motionEvent);
                    this.mTooltipHoveredSelf = false;
                }
            }
            return false;
        }
        Object object = null;
        Object var5_6 = null;
        int n2 = this.mChildrenCount;
        if (n2 != 0) {
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            ArrayList<View> arrayList = this.buildOrderedChildList();
            boolean bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
            object = this.mChildren;
            int n3 = n2 - 1;
            do {
                int n4;
                view = var5_6;
                if (n3 < 0 || (view = ViewGroup.getAndVerifyPreorderedView(arrayList, object, n4 = this.getAndVerifyPreorderedIndex(n2, n3, bl))).canReceivePointerEvents() && this.isTransformedTouchPointInView(f, f2, view, null) && this.dispatchTooltipHoverEvent(motionEvent, view)) break;
                --n3;
            } while (true);
            object = view;
            if (arrayList != null) {
                arrayList.clear();
                object = view;
            }
        }
        if ((view = this.mTooltipHoverTarget) != object) {
            if (view != null) {
                motionEvent.setAction(10);
                this.mTooltipHoverTarget.dispatchTooltipHoverEvent(motionEvent);
                motionEvent.setAction(n);
            }
            this.mTooltipHoverTarget = object;
        }
        if (this.mTooltipHoverTarget != null) {
            if (this.mTooltipHoveredSelf) {
                this.mTooltipHoveredSelf = false;
                motionEvent.setAction(10);
                super.dispatchTooltipHoverEvent(motionEvent);
                motionEvent.setAction(n);
            }
            return true;
        }
        this.mTooltipHoveredSelf = super.dispatchTooltipHoverEvent(motionEvent);
        return this.mTooltipHoveredSelf;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent var1_1) {
        block37 : {
            block42 : {
                block41 : {
                    block34 : {
                        block36 : {
                            block35 : {
                                block40 : {
                                    block38 : {
                                        block39 : {
                                            if (this.mInputEventConsistencyVerifier != null) {
                                                this.mInputEventConsistencyVerifier.onTouchEvent(var1_1, 1);
                                            }
                                            if (var1_1.isTargetAccessibilityFocus() && this.isAccessibilityFocusedViewOrHost()) {
                                                var1_1.setTargetAccessibilityFocus(false);
                                            }
                                            var2_2 = false;
                                            if (!this.onFilterTouchEventForSecurity(var1_1)) break block37;
                                            var3_3 = var1_1.getAction();
                                            var4_4 = var3_3 & 255;
                                            if (var4_4 == 0) {
                                                this.cancelAndClearTouchTargets(var1_1);
                                                this.resetTouchState();
                                            }
                                            if (var4_4 != 0 && this.mFirstTouchTarget == null) {
                                                var5_5 = true;
                                            } else {
                                                var6_6 = (this.mGroupFlags & 524288) != 0 ? 1 : 0;
                                                if (var6_6 == 0) {
                                                    var7_7 = this.onInterceptTouchEvent(var1_1);
                                                    var1_1.setAction(var3_3);
                                                } else {
                                                    var7_7 = false;
                                                }
                                                var5_5 = var7_7;
                                            }
                                            if (var5_5 || this.mFirstTouchTarget != null) {
                                                var1_1.setTargetAccessibilityFocus(false);
                                            }
                                            var8_8 = ViewGroup.resetCancelNextUpFlag(this) || var4_4 == 3;
                                            var9_9 = (this.mGroupFlags & 2097152) != 0;
                                            var10_10 = null;
                                            var11_11 = null;
                                            var6_6 = 0;
                                            if (var8_8 || var5_5) break block38;
                                            var12_12 = var1_1.isTargetAccessibilityFocus() != false ? this.findChildWithAccessibilityFocus() : null;
                                            if (var4_4 != 0 && (!var9_9 || var4_4 != 5) && var4_4 != 7) break block38;
                                            var13_13 = var1_1.getActionIndex();
                                            var14_14 = var9_9 != false ? 1 << var1_1.getPointerId(var13_13) : -1;
                                            this.removePointersFromTouchTargets(var14_14);
                                            var3_3 = this.mChildrenCount;
                                            if (false || var3_3 == 0) break block39;
                                            var15_15 = var1_1.getX(var13_13);
                                            var16_16 = var1_1.getY(var13_13);
                                            var17_17 = this.buildTouchDispatchChildList();
                                            var7_7 = var17_17 == null && this.isChildrenDrawingOrderEnabled() != false;
                                            var18_18 = this.mChildren;
                                            var10_10 = null;
                                            break block40;
                                        }
                                        var6_6 = 0;
                                        break block41;
                                    }
                                    var3_3 = 0;
                                    break block42;
                                }
                                for (var19_19 = var3_3 - 1; var19_19 >= 0; --var19_19) {
                                    var20_20 = this.getAndVerifyPreorderedIndex(var3_3, var19_19, var7_7);
                                    var21_21 = var19_19;
                                    var22_22 = ViewGroup.getAndVerifyPreorderedView(var17_17, var18_18, var20_20);
                                    var11_11 = var12_12;
                                    var19_19 = var21_21;
                                    if (var12_12 == null) ** GOTO lbl64
                                    if (var12_12 != var22_22) {
                                        var11_11 = var12_12;
                                        var19_19 = var21_21;
                                    } else {
                                        var11_11 = null;
                                        var19_19 = var3_3 - 1;
lbl64: // 2 sources:
                                        if (var22_22.canReceivePointerEvents() && this.isTransformedTouchPointInView(var15_15, var16_16, var22_22, null)) {
                                            var10_10 = this.getTouchTarget(var22_22);
                                            if (var10_10 != null) {
                                                var10_10.pointerIdBits |= var14_14;
                                                var11_11 = var10_10;
                                                break block34;
                                            }
                                            ViewGroup.resetCancelNextUpFlag(var22_22);
                                            if (this.dispatchTransformedTouchEvent(var1_1, false, var22_22, var14_14)) {
                                                this.mLastTouchDownTime = var1_1.getDownTime();
                                                if (var17_17 != null) {
                                                    break block35;
                                                }
                                                this.mLastTouchDownIndex = var20_20;
                                                break block36;
                                            }
                                            var1_1.setTargetAccessibilityFocus(false);
                                        } else {
                                            var1_1.setTargetAccessibilityFocus(false);
                                        }
                                    }
                                    var12_12 = var11_11;
                                }
                                var11_11 = var10_10;
                                break block34;
                            }
                            for (var6_6 = 0; var6_6 < var3_3; ++var6_6) {
                                if (var18_18[var20_20] != this.mChildren[var6_6]) continue;
                                this.mLastTouchDownIndex = var6_6;
                                break;
                            }
                        }
                        this.mLastTouchDownX = var1_1.getX();
                        this.mLastTouchDownY = var1_1.getY();
                        var11_11 = this.addTouchTarget(var22_22, var14_14);
                        var6_6 = 1;
                    }
                    if (var17_17 != null) {
                        var17_17.clear();
                    }
                }
                var10_10 = var11_11;
                var3_3 = var6_6;
                if (var11_11 == null) {
                    var10_10 = var11_11;
                    var3_3 = var6_6;
                    if (this.mFirstTouchTarget != null) {
                        var10_10 = this.mFirstTouchTarget;
                        while (var10_10.next != null) {
                            var10_10 = var10_10.next;
                        }
                        var10_10.pointerIdBits |= var14_14;
                        var3_3 = var6_6;
                    }
                }
            }
            var7_7 = false;
            if (this.mFirstTouchTarget == null) {
                var7_7 = this.dispatchTransformedTouchEvent(var1_1, var8_8, null, -1);
            } else {
                var12_12 = null;
                var11_11 = this.mFirstTouchTarget;
                while (var11_11 != null) {
                    var17_17 = var11_11.next;
                    if (var3_3 != 0 && var11_11 == var10_10) {
                        var2_2 = true;
                    } else {
                        var23_23 = ViewGroup.resetCancelNextUpFlag(var11_11.child) || var5_5;
                        if (this.dispatchTransformedTouchEvent(var1_1, var23_23, var11_11.child, var11_11.pointerIdBits)) {
                            var7_7 = true;
                        }
                        var2_2 = var7_7;
                        if (var23_23) {
                            if (var12_12 == null) {
                                this.mFirstTouchTarget = var17_17;
                            } else {
                                var12_12.next = var17_17;
                            }
                            var11_11.recycle();
                            var11_11 = var17_17;
                            continue;
                        }
                    }
                    var12_12 = var11_11;
                    var11_11 = var17_17;
                    var7_7 = var2_2;
                }
            }
            if (!var8_8 && var4_4 != 1 && var4_4 != 7) {
                var2_2 = var7_7;
                if (var9_9) {
                    var2_2 = var7_7;
                    if (var4_4 == 6) {
                        this.removePointersFromTouchTargets(1 << var1_1.getPointerId(var1_1.getActionIndex()));
                        var2_2 = var7_7;
                    }
                }
            } else {
                this.resetTouchState();
                var2_2 = var7_7;
            }
        }
        if (var2_2 != false) return var2_2;
        if (this.mInputEventConsistencyVerifier == null) return var2_2;
        this.mInputEventConsistencyVerifier.onUnhandledEvent(var1_1, 1);
        return var2_2;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        View view;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(motionEvent, 1);
        }
        if ((this.mPrivateFlags & 18) == 18 ? super.dispatchTrackballEvent(motionEvent) : (view = this.mFocused) != null && (view.mPrivateFlags & 16) == 16 && this.mFocused.dispatchTrackballEvent(motionEvent)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 1);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    View dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        if (!this.hasUnhandledKeyListener()) {
            return null;
        }
        Object object = this.buildOrderedChildList();
        if (object != null) {
            try {
            }
            catch (Throwable throwable) {
                ((ArrayList)object).clear();
                throw throwable;
            }
            for (int i = object.size() - 1; i >= 0; --i) {
                View view = ((ArrayList)object).get(i).dispatchUnhandledKeyEvent(keyEvent);
                if (view == null) continue;
                ((ArrayList)object).clear();
                return view;
            }
            ((ArrayList)object).clear();
        } else {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                object = this.getChildAt(i).dispatchUnhandledKeyEvent(keyEvent);
                if (object == null) continue;
                return object;
            }
        }
        if (this.onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    @Override
    public boolean dispatchUnhandledMove(View view, int n) {
        View view2 = this.mFocused;
        boolean bl = view2 != null && view2.dispatchUnhandledMove(view, n);
        return bl;
    }

    @UnsupportedAppUsage
    void dispatchViewAdded(View view) {
        this.onViewAdded(view);
        OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewAdded(this, view);
        }
    }

    @UnsupportedAppUsage
    void dispatchViewRemoved(View view) {
        this.onViewRemoved(view);
        OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        if (onHierarchyChangeListener != null) {
            onHierarchyChangeListener.onChildViewRemoved(this, view);
        }
    }

    @Override
    boolean dispatchVisibilityAggregated(boolean bl) {
        bl = super.dispatchVisibilityAggregated(bl);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            if (arrview[i].getVisibility() != 0) continue;
            arrview[i].dispatchVisibilityAggregated(bl);
        }
        return bl;
    }

    @Override
    protected void dispatchVisibilityChanged(View view, int n) {
        super.dispatchVisibilityChanged(view, n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchVisibilityChanged(view, n);
        }
    }

    @Override
    public void dispatchWindowFocusChanged(boolean bl) {
        super.dispatchWindowFocusChanged(bl);
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].dispatchWindowFocusChanged(bl);
        }
    }

    @Override
    void dispatchWindowInsetsAnimationFinished(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        super.dispatchWindowInsetsAnimationFinished(insetsAnimation);
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).dispatchWindowInsetsAnimationFinished(insetsAnimation);
        }
    }

    @Override
    WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets) {
        windowInsets = super.dispatchWindowInsetsAnimationProgress(windowInsets);
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).dispatchWindowInsetsAnimationProgress(windowInsets);
        }
        return windowInsets;
    }

    @Override
    void dispatchWindowInsetsAnimationStarted(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        super.dispatchWindowInsetsAnimationStarted(insetsAnimation);
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).dispatchWindowInsetsAnimationStarted(insetsAnimation);
        }
    }

    @Override
    public void dispatchWindowSystemUiVisiblityChanged(int n) {
        super.dispatchWindowSystemUiVisiblityChanged(n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchWindowSystemUiVisiblityChanged(n);
        }
    }

    @Override
    public void dispatchWindowVisibilityChanged(int n) {
        super.dispatchWindowVisibilityChanged(n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            arrview[i].dispatchWindowVisibilityChanged(n);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        return view.draw(canvas, this, l);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int n = this.mGroupFlags;
        if ((65536 & n) != 0) {
            if ((n & 8192) == 0) {
                View[] arrview = this.mChildren;
                int n2 = this.mChildrenCount;
                for (n = 0; n < n2; ++n) {
                    View view = arrview[n];
                    if ((view.mViewFlags & 4194304) == 0) continue;
                    view.refreshDrawableState();
                }
            } else {
                throw new IllegalStateException("addStateFromChildren cannot be enabled if a child has duplicateParentState set to true");
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("focus:descendantFocusability", this.getDescendantFocusability());
        viewHierarchyEncoder.addProperty("drawing:clipChildren", this.getClipChildren());
        viewHierarchyEncoder.addProperty("drawing:clipToPadding", this.getClipToPadding());
        viewHierarchyEncoder.addProperty("drawing:childrenDrawingOrderEnabled", this.isChildrenDrawingOrderEnabled());
        viewHierarchyEncoder.addProperty("drawing:persistentDrawingCache", this.getPersistentDrawingCache());
        int n = this.getChildCount();
        viewHierarchyEncoder.addProperty("meta:__childCount__", (short)n);
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("meta:__child__");
            stringBuilder.append(i);
            viewHierarchyEncoder.addPropertyKey(stringBuilder.toString());
            this.getChildAt(i).encode(viewHierarchyEncoder);
        }
    }

    public void endViewTransition(View view) {
        ArrayList<View> arrayList = this.mTransitioningViews;
        if (arrayList != null) {
            arrayList.remove(view);
            arrayList = this.mDisappearingChildren;
            if (arrayList != null && arrayList.contains(view)) {
                arrayList.remove(view);
                arrayList = this.mVisibilityChangingChildren;
                if (arrayList != null && arrayList.contains(view)) {
                    this.mVisibilityChangingChildren.remove(view);
                } else {
                    if (view.mAttachInfo != null) {
                        view.dispatchDetachedFromWindow();
                    }
                    if (view.mParent != null) {
                        view.mParent = null;
                    }
                }
                this.invalidate();
            }
        }
    }

    @Override
    public View findFocus() {
        if (this.isFocused()) {
            return this;
        }
        View view = this.mFocused;
        if (view != null) {
            return view.findFocus();
        }
        return null;
    }

    View findFrontmostDroppableChildAt(float f, float f2, PointF pointF) {
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        --n;
        while (n >= 0) {
            View view = arrview[n];
            if (view.canAcceptDrag() && this.isTransformedTouchPointInView(f, f2, view, pointF)) {
                return view;
            }
            --n;
        }
        return null;
    }

    @Override
    public void findNamedViews(Map<String, View> map) {
        if (this.getVisibility() != 0 && this.mGhostView == null) {
            return;
        }
        super.findNamedViews(map);
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).findNamedViews(map);
        }
    }

    public View findViewByAccessibilityIdTraversal(int n) {
        Object object = super.findViewByAccessibilityIdTraversal(n);
        if (object != null) {
            return object;
        }
        if (this.getAccessibilityNodeProvider() != null) {
            return null;
        }
        int n2 = this.mChildrenCount;
        object = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            Object t = ((View)object[i]).findViewByAccessibilityIdTraversal(n);
            if (t == null) continue;
            return t;
        }
        return null;
    }

    public View findViewByAutofillIdTraversal(int n) {
        Object t = super.findViewByAutofillIdTraversal(n);
        if (t != null) {
            return t;
        }
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            t = arrview[i].findViewByAutofillIdTraversal(n);
            if (t == null) continue;
            return t;
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        if (predicate.test(this)) {
            return (T)this;
        }
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            View view2 = arrview[i];
            if (view2 == view || (view2.mPrivateFlags & 8) != 0 || (view2 = view2.findViewByPredicate(predicate)) == null) continue;
            return (T)view2;
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewTraversal(int n) {
        if (n == this.mID) {
            return (T)this;
        }
        View[] arrview = this.mChildren;
        int n2 = this.mChildrenCount;
        for (int i = 0; i < n2; ++i) {
            View view = arrview[i];
            if ((view.mPrivateFlags & 8) != 0 || (view = view.findViewById(n)) == null) continue;
            return (T)view;
        }
        return null;
    }

    @Override
    protected <T extends View> T findViewWithTagTraversal(Object object) {
        if (object != null && object.equals(this.mTag)) {
            return (T)this;
        }
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if ((view.mPrivateFlags & 8) != 0 || (view = view.findViewWithTag(object)) == null) continue;
            return (T)view;
        }
        return null;
    }

    @Override
    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int n) {
        super.findViewsWithText(arrayList, charSequence, n);
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n2; ++i) {
            View view = arrview[i];
            if ((view.mViewFlags & 12) != 0 || (view.mPrivateFlags & 8) != 0) continue;
            view.findViewsWithText(arrayList, charSequence, n);
        }
    }

    void finishAnimatingView(View view, Animation animation) {
        ArrayList<View> arrayList = this.mDisappearingChildren;
        if (arrayList != null && arrayList.contains(view)) {
            arrayList.remove(view);
            if (view.mAttachInfo != null) {
                view.dispatchDetachedFromWindow();
            }
            view.clearAnimation();
            this.mGroupFlags |= 4;
        }
        if (animation != null && !animation.getFillAfter()) {
            view.clearAnimation();
        }
        if ((view.mPrivateFlags & 65536) == 65536) {
            view.onAnimationEnd();
            view.mPrivateFlags &= -65537;
            this.mGroupFlags |= 4;
        }
    }

    @Override
    public View focusSearch(View view, int n) {
        if (this.isRootNamespace()) {
            return FocusFinder.getInstance().findNextFocus(this, view, n);
        }
        if (this.mParent != null) {
            return this.mParent.focusSearch(view, n);
        }
        return null;
    }

    @Override
    public void focusableViewAvailable(View view) {
        if (!(this.mParent == null || this.getDescendantFocusability() == 393216 || (this.mViewFlags & 12) != 0 || !this.isFocusableInTouchMode() && this.shouldBlockFocusForTouchscreen() || this.isFocused() && this.getDescendantFocusability() != 262144)) {
            this.mParent.focusableViewAvailable(view);
        }
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        boolean bl;
        block14 : {
            block13 : {
                boolean bl2;
                int n;
                boolean bl3;
                block10 : {
                    int n2 = this.mPrivateFlags;
                    bl3 = false;
                    bl2 = (n2 & 512) == 0;
                    if (bl2 && region == null) {
                        return true;
                    }
                    super.gatherTransparentRegion(region);
                    int n3 = this.mChildrenCount;
                    n = 1;
                    n2 = 1;
                    if (n3 <= 0) break block10;
                    ArrayList<View> arrayList = this.buildOrderedChildList();
                    bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
                    View[] arrview = this.mChildren;
                    for (n = 0; n < n3; ++n) {
                        int n4;
                        block12 : {
                            View view;
                            block11 : {
                                view = ViewGroup.getAndVerifyPreorderedView(arrayList, arrview, this.getAndVerifyPreorderedIndex(n3, n, bl));
                                if ((view.mViewFlags & 12) == 0) break block11;
                                n4 = n2;
                                if (view.getAnimation() == null) break block12;
                            }
                            n4 = n2;
                            if (!view.gatherTransparentRegion(region)) {
                                n4 = 0;
                            }
                        }
                        n2 = n4;
                    }
                    n = n2;
                    if (arrayList != null) {
                        arrayList.clear();
                        n = n2;
                    }
                }
                if (bl2) break block13;
                bl = bl3;
                if (n == 0) break block14;
            }
            bl = true;
        }
        return bl;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return layoutParams;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ViewGroup.class.getName();
    }

    public View getChildAt(int n) {
        if (n >= 0 && n < this.mChildrenCount) {
            return this.mChildren[n];
        }
        return null;
    }

    public int getChildCount() {
        return this.mChildrenCount;
    }

    public final int getChildDrawingOrder(int n) {
        return this.getChildDrawingOrder(this.getChildCount(), n);
    }

    protected int getChildDrawingOrder(int n, int n2) {
        return n2;
    }

    protected boolean getChildStaticTransformation(View view, Transformation transformation) {
        return false;
    }

    Transformation getChildTransformation() {
        if (this.mChildTransformation == null) {
            this.mChildTransformation = new Transformation();
        }
        return this.mChildTransformation;
    }

    @Override
    public boolean getChildVisibleRect(View view, Rect rect, Point point) {
        return this.getChildVisibleRect(view, rect, point, false);
    }

    public boolean getChildVisibleRect(View view, Rect rect, Point point, boolean bl) {
        boolean bl2;
        block25 : {
            boolean bl3;
            block24 : {
                RectF rectF;
                block23 : {
                    block22 : {
                        block21 : {
                            int n;
                            int n2;
                            block20 : {
                                block19 : {
                                    block18 : {
                                        rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new RectF();
                                        rectF.set(rect);
                                        if (!view.hasIdentityMatrix()) {
                                            view.getMatrix().mapRect(rectF);
                                        }
                                        n2 = view.mLeft - this.mScrollX;
                                        n = view.mTop - this.mScrollY;
                                        rectF.offset(n2, n);
                                        if (point != null) {
                                            if (!view.hasIdentityMatrix()) {
                                                float[] arrf = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformLocation : new float[2];
                                                arrf[0] = point.x;
                                                arrf[1] = point.y;
                                                view.getMatrix().mapPoints(arrf);
                                                point.x = Math.round(arrf[0]);
                                                point.y = Math.round(arrf[1]);
                                            }
                                            point.x += n2;
                                            point.y += n;
                                        }
                                        n2 = this.mRight - this.mLeft;
                                        n = this.mBottom - this.mTop;
                                        bl2 = true;
                                        if (this.mParent == null) break block18;
                                        bl3 = bl2;
                                        if (!(this.mParent instanceof ViewGroup)) break block19;
                                        bl3 = bl2;
                                        if (!((ViewGroup)this.mParent).getClipChildren()) break block19;
                                    }
                                    bl3 = rectF.intersect(0.0f, 0.0f, n2, n);
                                }
                                if (bl) break block20;
                                bl2 = bl3;
                                if (!bl3) break block21;
                            }
                            bl2 = bl3;
                            if ((this.mGroupFlags & 34) == 34) {
                                bl2 = rectF.intersect(this.mPaddingLeft, this.mPaddingTop, n2 - this.mPaddingRight, n - this.mPaddingBottom);
                            }
                        }
                        if (bl) break block22;
                        bl3 = bl2;
                        if (!bl2) break block23;
                    }
                    bl3 = bl2;
                    if (this.mClipBounds != null) {
                        bl3 = rectF.intersect(this.mClipBounds.left, this.mClipBounds.top, this.mClipBounds.right, this.mClipBounds.bottom);
                    }
                }
                rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
                if (bl) break block24;
                bl2 = bl3;
                if (!bl3) break block25;
            }
            bl2 = bl3;
            if (this.mParent != null) {
                bl2 = this.mParent instanceof ViewGroup ? ((ViewGroup)this.mParent).getChildVisibleRect(this, rect, point, bl) : this.mParent.getChildVisibleRect(this, rect, point);
            }
        }
        return bl2;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean getClipChildren() {
        int n = this.mGroupFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean getClipToPadding() {
        return this.hasBooleanFlag(2);
    }

    View getDeepestFocusedChild() {
        ViewGroup viewGroup = this;
        do {
            View view = null;
            if (viewGroup == null) break;
            if (viewGroup.isFocused()) {
                return viewGroup;
            }
            if (viewGroup instanceof ViewGroup) {
                view = viewGroup.getFocusedChild();
            }
            viewGroup = view;
        } while (true);
        return null;
    }

    @ViewDebug.ExportedProperty(category="focus", mapping={@ViewDebug.IntToString(from=131072, to="FOCUS_BEFORE_DESCENDANTS"), @ViewDebug.IntToString(from=262144, to="FOCUS_AFTER_DESCENDANTS"), @ViewDebug.IntToString(from=393216, to="FOCUS_BLOCK_DESCENDANTS")})
    public int getDescendantFocusability() {
        return this.mGroupFlags & 393216;
    }

    public View getFocusedChild() {
        return this.mFocused;
    }

    public LayoutAnimationController getLayoutAnimation() {
        return this.mLayoutAnimationController;
    }

    public Animation.AnimationListener getLayoutAnimationListener() {
        return this.mAnimationListener;
    }

    public int getLayoutMode() {
        if (this.mLayoutMode == -1) {
            int n = this.mParent instanceof ViewGroup ? ((ViewGroup)this.mParent).getLayoutMode() : LAYOUT_MODE_DEFAULT;
            this.setLayoutMode(n, false);
        }
        return this.mLayoutMode;
    }

    public LayoutTransition getLayoutTransition() {
        return this.mTransition;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxes;
    }

    int getNumChildrenForAccessibility() {
        int n = 0;
        for (int i = 0; i < this.getChildCount(); ++i) {
            int n2;
            View view = this.getChildAt(i);
            if (view.includeForAccessibility()) {
                n2 = n + 1;
            } else {
                n2 = n;
                if (view instanceof ViewGroup) {
                    n2 = n + ((ViewGroup)view).getNumChildrenForAccessibility();
                }
            }
            n = n2;
        }
        return n;
    }

    @Override
    public ViewGroupOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new ViewGroupOverlay(this.mContext, this);
        }
        return (ViewGroupOverlay)this.mOverlay;
    }

    @ViewDebug.ExportedProperty(category="drawing", mapping={@ViewDebug.IntToString(from=0, to="NONE"), @ViewDebug.IntToString(from=1, to="ANIMATION"), @ViewDebug.IntToString(from=2, to="SCROLLING"), @ViewDebug.IntToString(from=3, to="ALL")})
    @Deprecated
    public int getPersistentDrawingCache() {
        return this.mPersistentDrawingCache;
    }

    @Override
    void getScrollIndicatorBounds(Rect rect) {
        super.getScrollIndicatorBounds(rect);
        boolean bl = (this.mGroupFlags & 34) == 34;
        if (bl) {
            rect.left += this.mPaddingLeft;
            rect.right -= this.mPaddingRight;
            rect.top += this.mPaddingTop;
            rect.bottom -= this.mPaddingBottom;
        }
    }

    @ViewDebug.ExportedProperty(category="focus")
    public boolean getTouchscreenBlocksFocus() {
        boolean bl = (this.mGroupFlags & 67108864) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public View getTransientView(int n) {
        List<View> list = this.mTransientViews;
        if (list != null && n < list.size()) {
            return this.mTransientViews.get(n);
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getTransientViewCount() {
        List<Integer> list = this.mTransientIndices;
        int n = list == null ? 0 : list.size();
        return n;
    }

    public int getTransientViewIndex(int n) {
        List<Integer> list;
        if (n >= 0 && (list = this.mTransientIndices) != null && n < list.size()) {
            return this.mTransientIndices.get(n);
        }
        return -1;
    }

    @Override
    void handleFocusGainInternal(int n, Rect rect) {
        View view = this.mFocused;
        if (view != null) {
            view.unFocus(this);
            this.mFocused = null;
            this.mFocusedInCluster = null;
        }
        super.handleFocusGainInternal(n, rect);
    }

    @Override
    boolean hasDefaultFocus() {
        boolean bl = this.mDefaultFocus != null || super.hasDefaultFocus();
        return bl;
    }

    @Override
    public boolean hasFocus() {
        boolean bl = (this.mPrivateFlags & 2) != 0 || this.mFocused != null;
        return bl;
    }

    @Override
    boolean hasFocusable(boolean bl, boolean bl2) {
        if ((this.mViewFlags & 12) != 0) {
            return false;
        }
        if ((bl || this.getFocusable() != 16) && this.isFocusable()) {
            return true;
        }
        if (this.getDescendantFocusability() != 393216) {
            return this.hasFocusableChild(bl2);
        }
        return false;
    }

    boolean hasFocusableChild(boolean bl) {
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            View view = arrview[i];
            if ((!bl || !view.hasExplicitFocusable()) && (bl || !view.hasFocusable())) continue;
            return true;
        }
        return false;
    }

    @Override
    protected boolean hasHoveredChild() {
        boolean bl = this.mFirstHoverTarget != null;
        return bl;
    }

    @Override
    public boolean hasTransientState() {
        boolean bl = this.mChildCountWithTransientState > 0 || super.hasTransientState();
        return bl;
    }

    @Override
    boolean hasUnhandledKeyListener() {
        boolean bl = this.mChildUnhandledKeyListeners > 0 || super.hasUnhandledKeyListener();
        return bl;
    }

    void incrementChildUnhandledKeyListeners() {
        ++this.mChildUnhandledKeyListeners;
        if (this.mChildUnhandledKeyListeners == 1 && this.mParent instanceof ViewGroup) {
            ((ViewGroup)this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    public int indexOfChild(View view) {
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            if (arrview[i] != view) continue;
            return i;
        }
        return -1;
    }

    @Override
    protected void internalSetPadding(int n, int n2, int n3, int n4) {
        super.internalSetPadding(n, n2, n3, n4);
        this.mGroupFlags = (this.mPaddingLeft | this.mPaddingTop | this.mPaddingRight | this.mPaddingBottom) != 0 ? (this.mGroupFlags |= 32) : (this.mGroupFlags &= -33);
    }

    @Deprecated
    @Override
    public final void invalidateChild(View object, Rect rect) {
        block17 : {
            View.AttachInfo attachInfo;
            int[] arrn;
            Object object2;
            boolean bl;
            Object object3;
            block19 : {
                Object object4;
                ViewGroup viewGroup;
                block18 : {
                    attachInfo = this.mAttachInfo;
                    if (attachInfo != null && attachInfo.mHardwareAccelerated) {
                        this.onDescendantInvalidated((View)object, (View)object);
                        return;
                    }
                    viewGroup = this;
                    if (attachInfo == null) break block17;
                    bl = (((View)object).mPrivateFlags & 64) != 0;
                    object2 = ((View)object).getMatrix();
                    if (((View)object).mLayerType != 0) {
                        this.mPrivateFlags |= Integer.MIN_VALUE;
                        this.mPrivateFlags &= -32769;
                    }
                    arrn = attachInfo.mInvalidateChildLocation;
                    arrn[0] = ((View)object).mLeft;
                    arrn[1] = ((View)object).mTop;
                    if (!((Matrix)object2).isIdentity()) break block18;
                    object3 = viewGroup;
                    object4 = object2;
                    if ((this.mGroupFlags & 2048) == 0) break block19;
                }
                object3 = attachInfo.mTmpTransformRect;
                ((RectF)object3).set(rect);
                if ((this.mGroupFlags & 2048) != 0) {
                    Transformation transformation = attachInfo.mTmpTransformation;
                    if (this.getChildStaticTransformation((View)object, transformation)) {
                        object4 = attachInfo.mTmpMatrix;
                        ((Matrix)object4).set(transformation.getMatrix());
                        object = object4;
                        if (!((Matrix)object2).isIdentity()) {
                            ((Matrix)object4).preConcat((Matrix)object2);
                            object = object4;
                        }
                    } else {
                        object = object2;
                    }
                } else {
                    object = object2;
                }
                ((Matrix)object).mapRect((RectF)object3);
                rect.set((int)Math.floor(((RectF)object3).left), (int)Math.floor(((RectF)object3).top), (int)Math.ceil(((RectF)object3).right), (int)Math.ceil(((RectF)object3).bottom));
                object4 = object2;
                object3 = viewGroup;
            }
            do {
                object = null;
                if (object3 instanceof View) {
                    object = (View)object3;
                }
                if (bl) {
                    if (object != null) {
                        ((View)object).mPrivateFlags |= 64;
                    } else if (object3 instanceof ViewRootImpl) {
                        ((ViewRootImpl)object3).mIsAnimating = true;
                    }
                }
                if (object != null && (((View)object).mPrivateFlags & 2097152) != 2097152) {
                    ((View)object).mPrivateFlags = ((View)object).mPrivateFlags & -2097153 | 2097152;
                }
                object3 = object3.invalidateChildInParent(arrn, rect);
                if (object == null || ((Matrix)(object = ((View)object).getMatrix())).isIdentity()) continue;
                object2 = attachInfo.mTmpTransformRect;
                ((RectF)object2).set(rect);
                ((Matrix)object).mapRect((RectF)object2);
                rect.set((int)Math.floor(((RectF)object2).left), (int)Math.floor(((RectF)object2).top), (int)Math.ceil(((RectF)object2).right), (int)Math.ceil(((RectF)object2).bottom));
            } while (object3 != null);
        }
    }

    @Deprecated
    @Override
    public ViewParent invalidateChildInParent(int[] arrn, Rect rect) {
        if ((this.mPrivateFlags & 32800) != 0) {
            int n = this.mGroupFlags;
            if ((n & 144) != 128) {
                rect.offset(arrn[0] - this.mScrollX, arrn[1] - this.mScrollY);
                if ((this.mGroupFlags & 1) == 0) {
                    rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                }
                n = this.mLeft;
                int n2 = this.mTop;
                if ((this.mGroupFlags & 1) == 1 && !rect.intersect(0, 0, this.mRight - n, this.mBottom - n2)) {
                    rect.setEmpty();
                }
                arrn[0] = n;
                arrn[1] = n2;
            } else {
                if ((n & 1) == 1) {
                    rect.set(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                } else {
                    rect.union(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
                }
                arrn[0] = this.mLeft;
                arrn[1] = this.mTop;
                this.mPrivateFlags &= -33;
            }
            this.mPrivateFlags &= -32769;
            if (this.mLayerType != 0) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
            }
            return this.mParent;
        }
        return null;
    }

    @Override
    void invalidateInheritedLayoutMode(int n) {
        int n2 = this.mLayoutMode;
        if (n2 != -1 && n2 != n && !this.hasBooleanFlag(8388608)) {
            this.setLayoutMode(-1, false);
            int n3 = this.getChildCount();
            for (n2 = 0; n2 < n3; ++n2) {
                this.getChildAt(n2).invalidateInheritedLayoutMode(n);
            }
            return;
        }
    }

    @Deprecated
    public boolean isAlwaysDrawnWithCacheEnabled() {
        boolean bl = (this.mGroupFlags & 16384) == 16384;
        return bl;
    }

    @Deprecated
    public boolean isAnimationCacheEnabled() {
        boolean bl = (this.mGroupFlags & 64) == 64;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    protected boolean isChildrenDrawingOrderEnabled() {
        boolean bl = (this.mGroupFlags & 1024) == 1024;
        return bl;
    }

    @Deprecated
    protected boolean isChildrenDrawnWithCacheEnabled() {
        boolean bl = (this.mGroupFlags & 32768) == 32768;
        return bl;
    }

    boolean isLayoutModeOptical() {
        int n = this.mLayoutMode;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isLayoutSuppressed() {
        return this.mSuppressLayout;
    }

    public boolean isMotionEventSplittingEnabled() {
        boolean bl = (this.mGroupFlags & 2097152) == 2097152;
        return bl;
    }

    public final boolean isShowingContextMenuWithCoords() {
        boolean bl = (this.mGroupFlags & 536870912) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    protected boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        float[] arrf = this.getTempPoint();
        arrf[0] = f;
        arrf[1] = f2;
        this.transformPointToViewLocal(arrf, view);
        boolean bl = view.pointInView(arrf[0], arrf[1]);
        if (bl && pointF != null) {
            pointF.set(arrf[0], arrf[1]);
        }
        return bl;
    }

    public boolean isTransitionGroup() {
        boolean bl;
        block6 : {
            block5 : {
                int n = this.mGroupFlags;
                boolean bl2 = false;
                bl = false;
                if ((33554432 & n) != 0) {
                    if ((n & 16777216) != 0) {
                        bl = true;
                    }
                    return bl;
                }
                ViewOutlineProvider viewOutlineProvider = this.getOutlineProvider();
                if (this.getBackground() != null || this.getTransitionName() != null) break block5;
                bl = bl2;
                if (viewOutlineProvider == null) break block6;
                bl = bl2;
                if (viewOutlineProvider == ViewOutlineProvider.BACKGROUND) break block6;
            }
            bl = true;
        }
        return bl;
    }

    boolean isViewTransitioning(View view) {
        ArrayList<View> arrayList = this.mTransitioningViews;
        boolean bl = arrayList != null && arrayList.contains(view);
        return bl;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            arrview[i].jumpDrawablesToCurrentState();
        }
    }

    @Override
    public final void layout(int n, int n2, int n3, int n4) {
        LayoutTransition layoutTransition;
        if (!(this.mSuppressLayout || (layoutTransition = this.mTransition) != null && layoutTransition.isChangingLayout())) {
            layoutTransition = this.mTransition;
            if (layoutTransition != null) {
                layoutTransition.layoutChange(this);
            }
            super.layout(n, n2, n3, n4);
        } else {
            this.mLayoutCalledWhileSuppressed = true;
        }
    }

    @UnsupportedAppUsage
    @Override
    public void makeOptionalFitsSystemWindows() {
        super.makeOptionalFitsSystemWindows();
        int n = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n; ++i) {
            arrview[i].makeOptionalFitsSystemWindows();
        }
    }

    protected void measureChild(View view, int n, int n2) {
        LayoutParams layoutParams = view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight, layoutParams.width), ViewGroup.getChildMeasureSpec(n2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams)view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(n3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4, marginLayoutParams.height));
    }

    protected void measureChildren(int n, int n2) {
        int n3 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n3; ++i) {
            View view = arrview[i];
            if ((view.mViewFlags & 12) == 8) continue;
            this.measureChild(view, n, n2);
        }
    }

    boolean notifyChildOfDragStart(View view) {
        float f = this.mCurrentDragStartEvent.mX;
        float f2 = this.mCurrentDragStartEvent.mY;
        float[] arrf = this.getTempPoint();
        arrf[0] = f;
        arrf[1] = f2;
        this.transformPointToViewLocal(arrf, view);
        DragEvent dragEvent = this.mCurrentDragStartEvent;
        dragEvent.mX = arrf[0];
        dragEvent.mY = arrf[1];
        boolean bl = view.dispatchDragEvent(dragEvent);
        dragEvent = this.mCurrentDragStartEvent;
        dragEvent.mX = f;
        dragEvent.mY = f2;
        dragEvent.mEventHandlerWasCalled = false;
        if (bl) {
            this.mChildrenInterestedInDrag.add(view);
            if (!view.canAcceptDrag()) {
                view.mPrivateFlags2 |= 1;
                view.refreshDrawableState();
            }
        }
        return bl;
    }

    @Override
    public void notifySubtreeAccessibilityStateChanged(View view, View object, int n) {
        if (this.getAccessibilityLiveRegion() != 0) {
            this.notifyViewAccessibilityStateChangedIfNeeded(1);
        } else if (this.mParent != null) {
            try {
                this.mParent.notifySubtreeAccessibilityStateChanged(this, (View)object, n);
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mParent.getClass().getSimpleName());
                ((StringBuilder)object).append(" does not fully implement ViewParent");
                Log.e("View", ((StringBuilder)object).toString(), abstractMethodError);
            }
        }
    }

    @Override
    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null) {
            ViewParent viewParent;
            if (this.getImportantForAccessibility() != 4 && !this.isImportantForAccessibility() && this.getChildCount() > 0 && (viewParent = this.getParentForAccessibility()) instanceof View) {
                ((View)((Object)viewParent)).notifySubtreeAccessibilityStateChangedIfNeeded();
                return;
            }
            super.notifySubtreeAccessibilityStateChangedIfNeeded();
            return;
        }
    }

    @UnsupportedAppUsage
    public void offsetChildrenTopAndBottom(int n) {
        int n2 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        boolean bl = false;
        for (int i = 0; i < n2; ++i) {
            View view = arrview[i];
            view.mTop += n;
            view.mBottom += n;
            if (view.mRenderNode == null) continue;
            bl = true;
            view.mRenderNode.offsetTopAndBottom(n);
        }
        if (bl) {
            this.invalidateViewProperty(false, false);
        }
        this.notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    public final void offsetDescendantRectToMyCoords(View view, Rect rect) {
        this.offsetRectBetweenParentAndChild(view, rect, true, false);
    }

    void offsetRectBetweenParentAndChild(View object, Rect rect, boolean bl, boolean bl2) {
        if (object == this) {
            return;
        }
        Object object2 = ((View)object).mParent;
        View view = object;
        object = object2;
        while (object != null && object instanceof View && object != this) {
            if (bl) {
                rect.offset(view.mLeft - view.mScrollX, view.mTop - view.mScrollY);
                if (bl2) {
                    view = (View)object;
                    if (!rect.intersect(0, 0, view.mRight - view.mLeft, view.mBottom - view.mTop)) {
                        rect.setEmpty();
                    }
                }
            } else {
                if (bl2) {
                    object2 = (View)object;
                    if (!rect.intersect(0, 0, ((View)object2).mRight - ((View)object2).mLeft, ((View)object2).mBottom - ((View)object2).mTop)) {
                        rect.setEmpty();
                    }
                }
                rect.offset(view.mScrollX - view.mLeft, view.mScrollY - view.mTop);
            }
            view = (View)object;
            object = view.mParent;
        }
        if (object == this) {
            if (bl) {
                rect.offset(view.mLeft - view.mScrollX, view.mTop - view.mScrollY);
            } else {
                rect.offset(view.mScrollX - view.mLeft, view.mScrollY - view.mTop);
            }
            return;
        }
        throw new IllegalArgumentException("parameter must be a descendant of this view");
    }

    public final void offsetRectIntoDescendantCoords(View view, Rect rect) {
        this.offsetRectBetweenParentAndChild(view, rect, false, false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.clearCachedLayoutMode();
    }

    @UnsupportedAppUsage
    protected void onChildVisibilityChanged(View view, int n, int n2) {
        Object object = this.mTransition;
        if (object != null) {
            if (n2 == 0) {
                ((LayoutTransition)object).showChild(this, view, n);
            } else {
                ((LayoutTransition)object).hideChild(this, view, n2);
                object = this.mTransitioningViews;
                if (object != null && ((ArrayList)object).contains(view)) {
                    if (this.mVisibilityChangingChildren == null) {
                        this.mVisibilityChangingChildren = new ArrayList();
                    }
                    this.mVisibilityChangingChildren.add(view);
                    this.addDisappearingView(view);
                }
            }
        }
        if (n2 == 0 && this.mCurrentDragStartEvent != null && !this.mChildrenInterestedInDrag.contains(view)) {
            this.notifyChildOfDragStart(view);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn;
        if ((this.mGroupFlags & 8192) == 0) {
            return super.onCreateDrawableState(n);
        }
        int n2 = 0;
        int n3 = this.getChildCount();
        for (int i = 0; i < n3; ++i) {
            arrn = this.getChildAt(i).getDrawableState();
            int n4 = n2;
            if (arrn != null) {
                n4 = n2 + arrn.length;
            }
            n2 = n4;
        }
        int[] arrn2 = super.onCreateDrawableState(n + n2);
        for (n = 0; n < n3; ++n) {
            int[] arrn3 = this.getChildAt(n).getDrawableState();
            arrn = arrn2;
            if (arrn3 != null) {
                arrn = ViewGroup.mergeDrawableStates(arrn2, arrn3);
            }
            arrn2 = arrn;
        }
        return arrn2;
    }

    protected void onDebugDraw(Canvas canvas) {
        int n;
        int n2;
        int n3;
        Object object;
        Paint paint = ViewGroup.getDebugPaint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.STROKE);
        for (n2 = 0; n2 < this.getChildCount(); ++n2) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() == 8) continue;
            object = view.getOpticalInsets();
            n3 = view.getLeft();
            n = ((Insets)object).left;
            int n4 = view.getTop();
            ViewGroup.drawRect(canvas, paint, n + n3, ((Insets)object).top + n4, view.getRight() - ((Insets)object).right - 1, view.getBottom() - ((Insets)object).bottom - 1);
        }
        paint.setColor(Color.argb(63, 255, 0, 255));
        paint.setStyle(Paint.Style.FILL);
        this.onDebugDrawMargins(canvas, paint);
        paint.setColor(DEBUG_CORNERS_COLOR);
        paint.setStyle(Paint.Style.FILL);
        n = this.dipsToPixels(8);
        n3 = this.dipsToPixels(1);
        for (n2 = 0; n2 < this.getChildCount(); ++n2) {
            object = this.getChildAt(n2);
            if (((View)object).getVisibility() == 8) continue;
            ViewGroup.drawRectCorners(canvas, ((View)object).getLeft(), ((View)object).getTop(), ((View)object).getRight(), ((View)object).getBottom(), paint, n, n3);
        }
    }

    protected void onDebugDrawMargins(Canvas canvas, Paint paint) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            view.getLayoutParams().onDebugDraw(view, canvas, paint);
        }
    }

    @Override
    public void onDescendantInvalidated(View view, View view2) {
        this.mPrivateFlags |= view2.mPrivateFlags & 64;
        if ((view2.mPrivateFlags & -2097153) != 0) {
            this.mPrivateFlags = this.mPrivateFlags & -2097153 | 2097152;
            this.mPrivateFlags &= -32769;
        }
        if (this.mLayerType == 1) {
            this.mPrivateFlags |= -2145386496;
            view2 = this;
        }
        if (this.mParent != null) {
            this.mParent.onDescendantInvalidated(this, view2);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.clearCachedLayoutMode();
    }

    @UnsupportedAppUsage
    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.getAccessibilityNodeProvider() != null) {
            return;
        }
        if (this.mAttachInfo != null) {
            ArrayList<View> arrayList = this.mAttachInfo.mTempArrayList;
            arrayList.clear();
            this.addChildrenForAccessibility(arrayList);
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                accessibilityNodeInfo.addChildUnchecked(arrayList.get(i));
            }
            arrayList.clear();
        }
    }

    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            int n = motionEvent.getAction();
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            if ((n == 7 || n == 9) && this.isOnScrollbar(f, f2)) {
                return true;
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return motionEvent.isFromSource(8194) && motionEvent.getAction() == 0 && motionEvent.isButtonPressed(1) && this.isOnScrollbarThumb(motionEvent.getX(), motionEvent.getY());
    }

    @Override
    protected abstract void onLayout(boolean var1, int var2, int var3, int var4, int var5);

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        return this.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return this.dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View view, int n, Bundle bundle) {
        return false;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.dispatchNestedPreScroll(n, n2, arrn, null);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.dispatchNestedScroll(n, n2, n3, n4, null);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.mNestedScrollAxes = n;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3;
        int n4 = this.mChildrenCount;
        if ((n & 2) != 0) {
            n3 = 0;
            n2 = 1;
        } else {
            n3 = n4 - 1;
            n2 = -1;
            n4 = -1;
        }
        View[] arrview = this.mChildren;
        while (n3 != n4) {
            View view = arrview[n3];
            if ((view.mViewFlags & 12) == 0 && view.requestFocus(n, rect)) {
                return true;
            }
            n3 += n2;
        }
        return false;
    }

    public boolean onRequestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.onRequestSendAccessibilityEvent(this, view, accessibilityEvent);
        }
        return this.onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
    }

    public boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        return true;
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        float f;
        float f2 = motionEvent.getX(n);
        if (!this.isOnScrollbarThumb(f2, f = motionEvent.getY(n)) && !this.isDraggingScrollBar()) {
            int n2 = this.mChildrenCount;
            if (n2 != 0) {
                ArrayList<View> arrayList = this.buildOrderedChildList();
                boolean bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
                View[] arrview = this.mChildren;
                for (int i = n2 - 1; i >= 0; --i) {
                    Object object = ViewGroup.getAndVerifyPreorderedView(arrayList, arrview, this.getAndVerifyPreorderedIndex(n2, i, bl));
                    if (!((View)object).canReceivePointerEvents() || !this.isTransformedTouchPointInView(f2, f, (View)object, null) || (object = this.dispatchResolvePointerIcon(motionEvent, n, (View)object)) == null) continue;
                    if (arrayList != null) {
                        arrayList.clear();
                    }
                    return object;
                }
                if (arrayList != null) {
                    arrayList.clear();
                }
            }
            return super.onResolvePointerIcon(motionEvent, n);
        }
        return PointerIcon.getSystemIcon(this.mContext, 1000);
    }

    protected void onSetLayoutParams(View view, LayoutParams layoutParams) {
        this.requestLayout();
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return false;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.stopNestedScroll();
        this.mNestedScrollAxes = 0;
    }

    public void onViewAdded(View view) {
    }

    public void onViewRemoved(View view) {
    }

    @Override
    protected boolean pointInHoveredChild(MotionEvent motionEvent) {
        if (this.mFirstHoverTarget != null) {
            return this.isTransformedTouchPointInView(motionEvent.getX(), motionEvent.getY(), this.mFirstHoverTarget.child, null);
        }
        return false;
    }

    @Override
    public void recomputeViewAttributes(View object) {
        if (this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes && (object = this.mParent) != null) {
            object.recomputeViewAttributes(this);
        }
    }

    public void removeAllViews() {
        this.removeAllViewsInLayout();
        this.requestLayout();
        this.invalidate(true);
    }

    public void removeAllViewsInLayout() {
        int n = this.mChildrenCount;
        if (n <= 0) {
            return;
        }
        Object object = this.mChildren;
        this.mChildrenCount = 0;
        View view = this.mFocused;
        boolean bl = this.mAttachInfo != null;
        boolean bl2 = false;
        this.needGlobalAttributesUpdate(false);
        --n;
        while (n >= 0) {
            View view2 = object[n];
            Object object2 = this.mTransition;
            if (object2 != null) {
                ((LayoutTransition)object2).removeChild(this, view2);
            }
            if (view2 == view) {
                view2.unFocus(null);
                bl2 = true;
            }
            view2.clearAccessibilityFocus();
            this.cancelTouchTarget(view2);
            this.cancelHoverTarget(view2);
            if (!(view2.getAnimation() != null || (object2 = this.mTransitioningViews) != null && ((ArrayList)object2).contains(view2))) {
                if (bl) {
                    view2.dispatchDetachedFromWindow();
                }
            } else {
                this.addDisappearingView(view2);
            }
            if (view2.hasTransientState()) {
                this.childHasTransientStateChanged(view2, false);
            }
            this.dispatchViewRemoved(view2);
            view2.mParent = null;
            object[n] = null;
            --n;
        }
        object = this.mDefaultFocus;
        if (object != null) {
            this.clearDefaultFocus((View)object);
        }
        if ((object = this.mFocusedInCluster) != null) {
            this.clearFocusedInCluster((View)object);
        }
        if (bl2) {
            this.clearChildFocus(view);
            if (!this.rootViewRequestFocus()) {
                this.notifyGlobalFocusCleared(view);
            }
        }
    }

    protected void removeDetachedView(View view, boolean bl) {
        Object object = this.mTransition;
        if (object != null) {
            ((LayoutTransition)object).removeChild(this, view);
        }
        if (view == this.mFocused) {
            view.clearFocus();
        }
        if (view == this.mDefaultFocus) {
            this.clearDefaultFocus(view);
        }
        if (view == this.mFocusedInCluster) {
            this.clearFocusedInCluster(view);
        }
        view.clearAccessibilityFocus();
        this.cancelTouchTarget(view);
        this.cancelHoverTarget(view);
        if (bl && view.getAnimation() != null || (object = this.mTransitioningViews) != null && ((ArrayList)object).contains(view)) {
            this.addDisappearingView(view);
        } else if (view.mAttachInfo != null) {
            view.dispatchDetachedFromWindow();
        }
        if (view.hasTransientState()) {
            this.childHasTransientStateChanged(view, false);
        }
        this.dispatchViewRemoved(view);
    }

    @UnsupportedAppUsage
    public void removeTransientView(View view) {
        List<View> list = this.mTransientViews;
        if (list == null) {
            return;
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            if (view != this.mTransientViews.get(i)) continue;
            this.mTransientViews.remove(i);
            this.mTransientIndices.remove(i);
            view.mParent = null;
            if (view.mAttachInfo != null) {
                view.dispatchDetachedFromWindow();
            }
            this.invalidate(true);
            return;
        }
    }

    @Override
    public void removeView(View view) {
        if (this.removeViewInternal(view)) {
            this.requestLayout();
            this.invalidate(true);
        }
    }

    public void removeViewAt(int n) {
        this.removeViewInternal(n, this.getChildAt(n));
        this.requestLayout();
        this.invalidate(true);
    }

    public void removeViewInLayout(View view) {
        this.removeViewInternal(view);
    }

    public void removeViews(int n, int n2) {
        this.removeViewsInternal(n, n2);
        this.requestLayout();
        this.invalidate(true);
    }

    public void removeViewsInLayout(int n, int n2) {
        this.removeViewsInternal(n, n2);
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        if (this.getDescendantFocusability() == 393216) {
            return;
        }
        super.unFocus(view2);
        View view3 = this.mFocused;
        if (view3 != view) {
            if (view3 != null) {
                view3.unFocus(view2);
            }
            this.mFocused = view;
        }
        if (this.mParent != null) {
            this.mParent.requestChildFocus(this, view2);
        }
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return false;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean bl) {
        boolean bl2 = (this.mGroupFlags & 524288) != 0;
        if (bl == bl2) {
            return;
        }
        this.mGroupFlags = bl ? (this.mGroupFlags |= 524288) : (this.mGroupFlags &= -524289);
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean requestFocus(int n, Rect object) {
        boolean bl;
        int n2 = this.getDescendantFocusability();
        if (n2 != 131072) {
            if (n2 != 262144) {
                if (n2 != 393216) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("descendant focusability must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS but is ");
                    ((StringBuilder)object).append(n2);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                bl = super.requestFocus(n, (Rect)object);
            } else {
                bl = this.onRequestFocusInDescendants(n, (Rect)object);
                if (!bl) {
                    bl = super.requestFocus(n, (Rect)object);
                }
            }
        } else {
            bl = super.requestFocus(n, (Rect)object);
            if (!bl) {
                bl = this.onRequestFocusInDescendants(n, (Rect)object);
            }
        }
        if (bl && !this.isLayoutValid() && (this.mPrivateFlags & 1) == 0) {
            this.mPrivateFlags |= 1;
        }
        return bl;
    }

    @Override
    public boolean requestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        ViewParent viewParent = this.mParent;
        if (viewParent == null) {
            return false;
        }
        if (!this.onRequestSendAccessibilityEvent(view, accessibilityEvent)) {
            return false;
        }
        return viewParent.requestSendAccessibilityEvent(this, accessibilityEvent);
    }

    public void requestTransitionStart(LayoutTransition layoutTransition) {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestTransitionStart(layoutTransition);
        }
    }

    @Override
    public void requestTransparentRegion(View view) {
        if (view != null) {
            view.mPrivateFlags |= 512;
            if (this.mParent != null) {
                this.mParent.requestTransparentRegion(this);
            }
        }
    }

    @Override
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isLayoutDirectionInherited()) continue;
            view.resetResolvedDrawables();
        }
    }

    @Override
    public void resetResolvedLayoutDirection() {
        super.resetResolvedLayoutDirection();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isLayoutDirectionInherited()) continue;
            view.resetResolvedLayoutDirection();
        }
    }

    @Override
    public void resetResolvedPadding() {
        super.resetResolvedPadding();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isLayoutDirectionInherited()) continue;
            view.resetResolvedPadding();
        }
    }

    @Override
    public void resetResolvedTextAlignment() {
        super.resetResolvedTextAlignment();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isTextAlignmentInherited()) continue;
            view.resetResolvedTextAlignment();
        }
    }

    @Override
    public void resetResolvedTextDirection() {
        super.resetResolvedTextDirection();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isTextDirectionInherited()) continue;
            view.resetResolvedTextDirection();
        }
    }

    @Override
    void resetSubtreeAccessibilityStateChanged() {
        super.resetSubtreeAccessibilityStateChanged();
        View[] arrview = this.mChildren;
        int n = this.mChildrenCount;
        for (int i = 0; i < n; ++i) {
            arrview[i].resetSubtreeAccessibilityStateChanged();
        }
    }

    @Override
    protected void resolveDrawables() {
        super.resolveDrawables();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isLayoutDirectionInherited() || view.areDrawablesResolved()) continue;
            view.resolveDrawables();
        }
    }

    @Override
    public boolean resolveLayoutDirection() {
        boolean bl = super.resolveLayoutDirection();
        if (bl) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                if (!view.isLayoutDirectionInherited()) continue;
                view.resolveLayoutDirection();
            }
        }
        return bl;
    }

    @Override
    public void resolveLayoutParams() {
        super.resolveLayoutParams();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.getChildAt(i).resolveLayoutParams();
        }
    }

    @UnsupportedAppUsage
    @Override
    public void resolvePadding() {
        super.resolvePadding();
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!view.isLayoutDirectionInherited() || view.isPaddingResolved()) continue;
            view.resolvePadding();
        }
    }

    @Override
    public boolean resolveRtlPropertiesIfNeeded() {
        boolean bl = super.resolveRtlPropertiesIfNeeded();
        if (bl) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                if (!view.isLayoutDirectionInherited()) continue;
                view.resolveRtlPropertiesIfNeeded();
            }
        }
        return bl;
    }

    @Override
    public boolean resolveTextAlignment() {
        boolean bl = super.resolveTextAlignment();
        if (bl) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                if (!view.isTextAlignmentInherited()) continue;
                view.resolveTextAlignment();
            }
        }
        return bl;
    }

    @Override
    public boolean resolveTextDirection() {
        boolean bl = super.resolveTextDirection();
        if (bl) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                if (!view.isTextDirectionInherited()) continue;
                view.resolveTextDirection();
            }
        }
        return bl;
    }

    @Override
    public boolean restoreDefaultFocus() {
        if (this.mDefaultFocus != null && this.getDescendantFocusability() != 393216 && (this.mDefaultFocus.mViewFlags & 12) == 0 && this.mDefaultFocus.restoreDefaultFocus()) {
            return true;
        }
        return super.restoreDefaultFocus();
    }

    @Override
    public boolean restoreFocusInCluster(int n) {
        if (this.isKeyboardNavigationCluster()) {
            boolean bl = this.getTouchscreenBlocksFocus();
            try {
                this.setTouchscreenBlocksFocusNoRefocus(false);
                boolean bl2 = this.restoreFocusInClusterInternal(n);
                return bl2;
            }
            finally {
                this.setTouchscreenBlocksFocusNoRefocus(bl);
            }
        }
        return this.restoreFocusInClusterInternal(n);
    }

    @Override
    public boolean restoreFocusNotInCluster() {
        if (this.mFocusedInCluster != null) {
            return this.restoreFocusInCluster(130);
        }
        if (!this.isKeyboardNavigationCluster() && (this.mViewFlags & 12) == 0) {
            int n = this.getDescendantFocusability();
            if (n == 393216) {
                return super.requestFocus(130, null);
            }
            if (n == 131072 && super.requestFocus(130, null)) {
                return true;
            }
            for (int i = 0; i < this.mChildrenCount; ++i) {
                View view = this.mChildren[i];
                if (view.isKeyboardNavigationCluster() || !view.restoreFocusNotInCluster()) continue;
                return true;
            }
            if (n == 262144 && !this.hasFocusableChild(false)) {
                return super.requestFocus(130, null);
            }
            return false;
        }
        return false;
    }

    public void scheduleLayoutAnimation() {
        this.mGroupFlags |= 8;
    }

    public void setAddStatesFromChildren(boolean bl) {
        this.mGroupFlags = bl ? (this.mGroupFlags |= 8192) : (this.mGroupFlags &= -8193);
        this.refreshDrawableState();
    }

    @Deprecated
    public void setAlwaysDrawnWithCacheEnabled(boolean bl) {
        this.setBooleanFlag(16384, bl);
    }

    @Deprecated
    public void setAnimationCacheEnabled(boolean bl) {
        this.setBooleanFlag(64, bl);
    }

    @Deprecated
    protected void setChildrenDrawingCacheEnabled(boolean bl) {
        if (bl || (this.mPersistentDrawingCache & 3) != 3) {
            View[] arrview = this.mChildren;
            int n = this.mChildrenCount;
            for (int i = 0; i < n; ++i) {
                arrview[i].setDrawingCacheEnabled(bl);
            }
        }
    }

    protected void setChildrenDrawingOrderEnabled(boolean bl) {
        this.setBooleanFlag(1024, bl);
    }

    @Deprecated
    protected void setChildrenDrawnWithCacheEnabled(boolean bl) {
        this.setBooleanFlag(32768, bl);
    }

    public void setClipChildren(boolean bl) {
        boolean bl2 = (this.mGroupFlags & 1) == 1;
        if (bl != bl2) {
            this.setBooleanFlag(1, bl);
            for (int i = 0; i < this.mChildrenCount; ++i) {
                View view = this.getChildAt(i);
                if (view.mRenderNode == null) continue;
                view.mRenderNode.setClipToBounds(bl);
            }
            this.invalidate(true);
        }
    }

    public void setClipToPadding(boolean bl) {
        if (this.hasBooleanFlag(2) != bl) {
            this.setBooleanFlag(2, bl);
            this.invalidate(true);
        }
    }

    void setDefaultFocus(View view) {
        View view2 = this.mDefaultFocus;
        if (view2 != null && view2.isFocusedByDefault()) {
            return;
        }
        this.mDefaultFocus = view;
        if (this.mParent instanceof ViewGroup) {
            ((ViewGroup)this.mParent).setDefaultFocus(this);
        }
    }

    public void setDescendantFocusability(int n) {
        if (n != 131072 && n != 262144 && n != 393216) {
            throw new IllegalArgumentException("must be one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS");
        }
        this.mGroupFlags &= -393217;
        this.mGroupFlags |= 393216 & n;
    }

    public void setLayoutAnimation(LayoutAnimationController layoutAnimationController) {
        this.mLayoutAnimationController = layoutAnimationController;
        if (this.mLayoutAnimationController != null) {
            this.mGroupFlags |= 8;
        }
    }

    public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public void setLayoutMode(int n) {
        if (this.mLayoutMode != n) {
            this.invalidateInheritedLayoutMode(n);
            boolean bl = n != -1;
            this.setLayoutMode(n, bl);
            this.requestLayout();
        }
    }

    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (this.mTransition != null) {
            LayoutTransition layoutTransition2 = this.mTransition;
            layoutTransition2.cancel();
            layoutTransition2.removeTransitionListener(this.mLayoutTransitionListener);
        }
        if ((layoutTransition = (this.mTransition = layoutTransition)) != null) {
            layoutTransition.addTransitionListener(this.mLayoutTransitionListener);
        }
    }

    public void setMotionEventSplittingEnabled(boolean bl) {
        this.mGroupFlags = bl ? (this.mGroupFlags |= 2097152) : (this.mGroupFlags &= -2097153);
    }

    public void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Deprecated
    public void setPersistentDrawingCache(int n) {
        this.mPersistentDrawingCache = n & 3;
    }

    protected void setStaticTransformationsEnabled(boolean bl) {
        this.setBooleanFlag(2048, bl);
    }

    public void setTouchscreenBlocksFocus(boolean bl) {
        if (bl) {
            View view;
            this.mGroupFlags |= 67108864;
            if (this.hasFocus() && !this.isKeyboardNavigationCluster() && !this.getDeepestFocusedChild().isFocusableInTouchMode() && (view = this.focusSearch(2)) != null) {
                view.requestFocus();
            }
        } else {
            this.mGroupFlags &= -67108865;
        }
    }

    public void setTransitionGroup(boolean bl) {
        this.mGroupFlags |= 33554432;
        this.mGroupFlags = bl ? (this.mGroupFlags |= 16777216) : (this.mGroupFlags &= -16777217);
    }

    boolean shouldBlockFocusForTouchscreen() {
        boolean bl = this.getTouchscreenBlocksFocus() && this.mContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen") && (!this.isKeyboardNavigationCluster() || !this.hasFocus() && this.findKeyboardNavigationCluster() == this);
        return bl;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override
    public boolean showContextMenuForChild(View view) {
        boolean bl = this.isShowingContextMenuWithCoords();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        bl = bl2;
        if (this.mParent != null) {
            bl = bl2;
            if (this.mParent.showContextMenuForChild(view)) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean showContextMenuForChild(View view, float f, float f2) {
        boolean bl;
        block3 : {
            try {
                this.mGroupFlags |= 536870912;
                boolean bl2 = this.showContextMenuForChild(view);
                bl = true;
                if (!bl2) break block3;
            }
            catch (Throwable throwable) {
                this.mGroupFlags = -536870913 & this.mGroupFlags;
                throw throwable;
            }
            this.mGroupFlags = -536870913 & this.mGroupFlags;
            return true;
        }
        this.mGroupFlags = -536870913 & this.mGroupFlags;
        if (this.mParent == null || !this.mParent.showContextMenuForChild(view, f, f2)) {
            bl = false;
        }
        return bl;
    }

    @Override
    public ActionMode startActionModeForChild(View object, ActionMode.Callback callback) {
        int n = this.mGroupFlags;
        if ((134217728 & n) == 0) {
            try {
                this.mGroupFlags = n | 268435456;
                object = this.startActionModeForChild((View)object, callback, 0);
                return object;
            }
            finally {
                this.mGroupFlags = -268435457 & this.mGroupFlags;
            }
        }
        return SENTINEL_ACTION_MODE;
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n) {
        ActionMode actionMode;
        int n2 = this.mGroupFlags;
        if ((268435456 & n2) == 0 && n == 0) {
            try {
                this.mGroupFlags = n2 | 134217728;
                actionMode = this.startActionModeForChild(view, callback);
                if (actionMode != SENTINEL_ACTION_MODE) {
                    return actionMode;
                }
            }
            finally {
                this.mGroupFlags = -134217729 & this.mGroupFlags;
            }
        }
        if (this.mParent != null) {
            try {
                actionMode = this.mParent.startActionModeForChild(view, callback, n);
                return actionMode;
            }
            catch (AbstractMethodError abstractMethodError) {
                return this.mParent.startActionModeForChild(view, callback);
            }
        }
        return null;
    }

    public void startLayoutAnimation() {
        if (this.mLayoutAnimationController != null) {
            this.mGroupFlags |= 8;
            this.requestLayout();
        }
    }

    public void startViewTransition(View view) {
        if (view.mParent == this) {
            if (this.mTransitioningViews == null) {
                this.mTransitioningViews = new ArrayList<E>();
            }
            this.mTransitioningViews.add(view);
        }
    }

    @Override
    public void subtractObscuredTouchableRegion(Region region, View object) {
        View view;
        int n = this.mChildrenCount;
        ArrayList<View> arrayList = this.buildTouchDispatchChildList();
        boolean bl = arrayList == null && this.isChildrenDrawingOrderEnabled();
        View[] arrview = this.mChildren;
        for (int i = n - 1; i >= 0 && (view = ViewGroup.getAndVerifyPreorderedView(arrayList, arrview, this.getAndVerifyPreorderedIndex(n, i, bl))) != object; --i) {
            if (!view.canReceivePointerEvents()) continue;
            ViewGroup.applyOpToRegionByBounds(region, view, Region.Op.DIFFERENCE);
        }
        ViewGroup.applyOpToRegionByBounds(region, this, Region.Op.INTERSECT);
        object = this.getParent();
        if (object != null) {
            object.subtractObscuredTouchableRegion(region, this);
        }
    }

    public void suppressLayout(boolean bl) {
        this.mSuppressLayout = bl;
        if (!bl && this.mLayoutCalledWhileSuppressed) {
            this.requestLayout();
            this.mLayoutCalledWhileSuppressed = false;
        }
    }

    @UnsupportedAppUsage
    public void transformPointToViewLocal(float[] arrf, View view) {
        arrf[0] = arrf[0] + (float)(this.mScrollX - view.mLeft);
        arrf[1] = arrf[1] + (float)(this.mScrollY - view.mTop);
        if (!view.hasIdentityMatrix()) {
            view.getInverseMatrix().mapPoints(arrf);
        }
    }

    @Override
    void unFocus(View view) {
        View view2 = this.mFocused;
        if (view2 == null) {
            super.unFocus(view);
        } else {
            view2.unFocus(view);
            this.mFocused = null;
        }
    }

    @Override
    boolean updateLocalSystemUiVisibility(int n, int n2) {
        boolean bl = super.updateLocalSystemUiVisibility(n, n2);
        int n3 = this.mChildrenCount;
        View[] arrview = this.mChildren;
        for (int i = 0; i < n3; ++i) {
            bl |= arrview[i].updateLocalSystemUiVisibility(n, n2);
        }
        return bl;
    }

    @Override
    public void updateViewLayout(View object, LayoutParams layoutParams) {
        if (this.checkLayoutParams(layoutParams)) {
            if (((View)object).mParent == this) {
                ((View)object).setLayoutParams(layoutParams);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Given view not a child of ");
            ((StringBuilder)object).append(this);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid LayoutParams supplied to ");
        ((StringBuilder)object).append(this);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static class ChildListForAccessibility {
        private static final int MAX_POOL_SIZE = 32;
        private static final Pools.SynchronizedPool<ChildListForAccessibility> sPool = new Pools.SynchronizedPool(32);
        private final ArrayList<View> mChildren = new ArrayList();
        private final ArrayList<ViewLocationHolder> mHolders = new ArrayList();

        ChildListForAccessibility() {
        }

        private void clear() {
            this.mChildren.clear();
        }

        private void init(ViewGroup object, boolean bl) {
            int n;
            ArrayList<View> arrayList = this.mChildren;
            int n2 = ((ViewGroup)object).getChildCount();
            for (n = 0; n < n2; ++n) {
                arrayList.add(((ViewGroup)object).getChildAt(n));
            }
            if (bl) {
                ArrayList<ViewLocationHolder> arrayList2 = this.mHolders;
                for (n = 0; n < n2; ++n) {
                    arrayList2.add(ViewLocationHolder.obtain((ViewGroup)object, arrayList.get(n)));
                }
                this.sort(arrayList2);
                for (n = 0; n < n2; ++n) {
                    object = arrayList2.get(n);
                    arrayList.set(n, ((ViewLocationHolder)object).mView);
                    ((ViewLocationHolder)object).recycle();
                }
                arrayList2.clear();
            }
        }

        public static ChildListForAccessibility obtain(ViewGroup viewGroup, boolean bl) {
            ChildListForAccessibility childListForAccessibility;
            ChildListForAccessibility childListForAccessibility2 = childListForAccessibility = sPool.acquire();
            if (childListForAccessibility == null) {
                childListForAccessibility2 = new ChildListForAccessibility();
            }
            childListForAccessibility2.init(viewGroup, bl);
            return childListForAccessibility2;
        }

        private void sort(ArrayList<ViewLocationHolder> arrayList) {
            try {
                ViewLocationHolder.setComparisonStrategy(1);
                Collections.sort(arrayList);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ViewLocationHolder.setComparisonStrategy(2);
                Collections.sort(arrayList);
            }
        }

        public View getChildAt(int n) {
            return this.mChildren.get(n);
        }

        public int getChildCount() {
            return this.mChildren.size();
        }

        public void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

    private static class ChildListForAutofill
    extends ArrayList<View> {
        private static final int MAX_POOL_SIZE = 32;
        private static final Pools.SimplePool<ChildListForAutofill> sPool = new Pools.SimplePool(32);

        private ChildListForAutofill() {
        }

        public static ChildListForAutofill obtain() {
            ChildListForAutofill childListForAutofill;
            ChildListForAutofill childListForAutofill2 = childListForAutofill = sPool.acquire();
            if (childListForAutofill == null) {
                childListForAutofill2 = new ChildListForAutofill();
            }
            return childListForAutofill2;
        }

        public void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

    private static final class HoverTarget {
        private static final int MAX_RECYCLED = 32;
        private static HoverTarget sRecycleBin;
        private static final Object sRecycleLock;
        private static int sRecycledCount;
        public View child;
        public HoverTarget next;

        static {
            sRecycleLock = new Object[0];
        }

        private HoverTarget() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static HoverTarget obtain(View view) {
            HoverTarget hoverTarget;
            if (view == null) {
                throw new IllegalArgumentException("child must be non-null");
            }
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycleBin == null) {
                    hoverTarget = new HoverTarget();
                } else {
                    hoverTarget = sRecycleBin;
                    sRecycleBin = hoverTarget.next;
                    --sRecycledCount;
                    hoverTarget.next = null;
                }
            }
            hoverTarget.child = view;
            return hoverTarget;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recycle() {
            if (this.child == null) {
                throw new IllegalStateException("already recycled once");
            }
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycledCount < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    ++sRecycledCount;
                } else {
                    this.next = null;
                }
                this.child = null;
                return;
            }
        }
    }

    public static class LayoutParams {
        @Deprecated
        public static final int FILL_PARENT = -1;
        public static final int MATCH_PARENT = -1;
        public static final int WRAP_CONTENT = -2;
        @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=-1, to="MATCH_PARENT"), @ViewDebug.IntToString(from=-2, to="WRAP_CONTENT")})
        public int height;
        public LayoutAnimationController.AnimationParameters layoutAnimationParameters;
        @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=-1, to="MATCH_PARENT"), @ViewDebug.IntToString(from=-2, to="WRAP_CONTENT")})
        public int width;

        @UnsupportedAppUsage
        LayoutParams() {
        }

        public LayoutParams(int n, int n2) {
            this.width = n;
            this.height = n2;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ViewGroup_Layout);
            this.setBaseAttributes((TypedArray)object, 0, 1);
            ((TypedArray)object).recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            this.width = layoutParams.width;
            this.height = layoutParams.height;
        }

        protected static String sizeToString(int n) {
            if (n == -2) {
                return "wrap-content";
            }
            if (n == -1) {
                return "match-parent";
            }
            return String.valueOf(n);
        }

        public String debug(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("ViewGroup.LayoutParams={ width=");
            stringBuilder.append(LayoutParams.sizeToString(this.width));
            stringBuilder.append(", height=");
            stringBuilder.append(LayoutParams.sizeToString(this.height));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        void encode(ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.beginObject(this);
            this.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.endObject();
        }

        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.addProperty("width", this.width);
            viewHierarchyEncoder.addProperty("height", this.height);
        }

        public void onDebugDraw(View view, Canvas canvas, Paint paint) {
        }

        public void resolveLayoutDirection(int n) {
        }

        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = typedArray.getLayoutDimension(n, "layout_width");
            this.height = typedArray.getLayoutDimension(n2, "layout_height");
        }
    }

    public static class MarginLayoutParams
    extends LayoutParams {
        public static final int DEFAULT_MARGIN_RELATIVE = Integer.MIN_VALUE;
        private static final int DEFAULT_MARGIN_RESOLVED = 0;
        private static final int LAYOUT_DIRECTION_MASK = 3;
        private static final int LEFT_MARGIN_UNDEFINED_MASK = 4;
        private static final int NEED_RESOLUTION_MASK = 32;
        private static final int RIGHT_MARGIN_UNDEFINED_MASK = 8;
        private static final int RTL_COMPATIBILITY_MODE_MASK = 16;
        private static final int UNDEFINED_MARGIN = Integer.MIN_VALUE;
        @ViewDebug.ExportedProperty(category="layout")
        public int bottomMargin;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        private int endMargin = Integer.MIN_VALUE;
        @ViewDebug.ExportedProperty(category="layout")
        public int leftMargin;
        @ViewDebug.ExportedProperty(category="layout", flagMapping={@ViewDebug.FlagToString(equals=3, mask=3, name="LAYOUT_DIRECTION"), @ViewDebug.FlagToString(equals=4, mask=4, name="LEFT_MARGIN_UNDEFINED_MASK"), @ViewDebug.FlagToString(equals=8, mask=8, name="RIGHT_MARGIN_UNDEFINED_MASK"), @ViewDebug.FlagToString(equals=16, mask=16, name="RTL_COMPATIBILITY_MODE_MASK"), @ViewDebug.FlagToString(equals=32, mask=32, name="NEED_RESOLUTION_MASK")}, formatToHexString=true)
        byte mMarginFlags;
        @ViewDebug.ExportedProperty(category="layout")
        public int rightMargin;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        private int startMargin = Integer.MIN_VALUE;
        @ViewDebug.ExportedProperty(category="layout")
        public int topMargin;

        public MarginLayoutParams(int n, int n2) {
            super(n, n2);
            this.mMarginFlags = (byte)(this.mMarginFlags | 4);
            this.mMarginFlags = (byte)(this.mMarginFlags | 8);
            this.mMarginFlags = (byte)(this.mMarginFlags & -33);
            this.mMarginFlags = (byte)(this.mMarginFlags & -17);
        }

        public MarginLayoutParams(Context context, AttributeSet object) {
            object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.ViewGroup_MarginLayout);
            this.setBaseAttributes((TypedArray)object, 0, 1);
            int n = ((TypedArray)object).getDimensionPixelSize(2, -1);
            if (n >= 0) {
                this.leftMargin = n;
                this.topMargin = n;
                this.rightMargin = n;
                this.bottomMargin = n;
            } else {
                int n2 = ((TypedArray)object).getDimensionPixelSize(9, -1);
                n = ((TypedArray)object).getDimensionPixelSize(10, -1);
                if (n2 >= 0) {
                    this.leftMargin = n2;
                    this.rightMargin = n2;
                } else {
                    this.leftMargin = ((TypedArray)object).getDimensionPixelSize(3, Integer.MIN_VALUE);
                    if (this.leftMargin == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte)(this.mMarginFlags | 4);
                        this.leftMargin = 0;
                    }
                    this.rightMargin = ((TypedArray)object).getDimensionPixelSize(5, Integer.MIN_VALUE);
                    if (this.rightMargin == Integer.MIN_VALUE) {
                        this.mMarginFlags = (byte)(this.mMarginFlags | 8);
                        this.rightMargin = 0;
                    }
                }
                this.startMargin = ((TypedArray)object).getDimensionPixelSize(7, Integer.MIN_VALUE);
                this.endMargin = ((TypedArray)object).getDimensionPixelSize(8, Integer.MIN_VALUE);
                if (n >= 0) {
                    this.topMargin = n;
                    this.bottomMargin = n;
                } else {
                    this.topMargin = ((TypedArray)object).getDimensionPixelSize(4, 0);
                    this.bottomMargin = ((TypedArray)object).getDimensionPixelSize(6, 0);
                }
                if (this.isMarginRelative()) {
                    this.mMarginFlags = (byte)(this.mMarginFlags | 32);
                }
            }
            boolean bl = context.getApplicationInfo().hasRtlSupport();
            if (context.getApplicationInfo().targetSdkVersion < 17 || !bl) {
                this.mMarginFlags = (byte)(this.mMarginFlags | 16);
            }
            this.mMarginFlags = (byte)(0 | this.mMarginFlags);
            ((TypedArray)object).recycle();
        }

        public MarginLayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mMarginFlags = (byte)(this.mMarginFlags | 4);
            this.mMarginFlags = (byte)(this.mMarginFlags | 8);
            this.mMarginFlags = (byte)(this.mMarginFlags & -33);
            this.mMarginFlags = (byte)(this.mMarginFlags & -17);
        }

        public MarginLayoutParams(MarginLayoutParams marginLayoutParams) {
            this.width = marginLayoutParams.width;
            this.height = marginLayoutParams.height;
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        private void doResolveMargins() {
            int n = this.mMarginFlags;
            if ((n & 16) == 16) {
                if ((n & 4) == 4 && (n = this.startMargin) > Integer.MIN_VALUE) {
                    this.leftMargin = n;
                }
                if ((this.mMarginFlags & 8) == 8 && (n = this.endMargin) > Integer.MIN_VALUE) {
                    this.rightMargin = n;
                }
            } else if ((n & 3) != 1) {
                n = this.startMargin;
                if (n <= Integer.MIN_VALUE) {
                    n = 0;
                }
                this.leftMargin = n;
                n = this.endMargin;
                if (n <= Integer.MIN_VALUE) {
                    n = 0;
                }
                this.rightMargin = n;
            } else {
                n = this.endMargin;
                if (n <= Integer.MIN_VALUE) {
                    n = 0;
                }
                this.leftMargin = n;
                n = this.startMargin;
                if (n <= Integer.MIN_VALUE) {
                    n = 0;
                }
                this.rightMargin = n;
            }
            this.mMarginFlags = (byte)(this.mMarginFlags & -33);
        }

        public final void copyMarginsFrom(MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
            this.startMargin = marginLayoutParams.startMargin;
            this.endMargin = marginLayoutParams.endMargin;
            this.mMarginFlags = marginLayoutParams.mMarginFlags;
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("leftMargin", this.leftMargin);
            viewHierarchyEncoder.addProperty("topMargin", this.topMargin);
            viewHierarchyEncoder.addProperty("rightMargin", this.rightMargin);
            viewHierarchyEncoder.addProperty("bottomMargin", this.bottomMargin);
            viewHierarchyEncoder.addProperty("startMargin", this.startMargin);
            viewHierarchyEncoder.addProperty("endMargin", this.endMargin);
        }

        public int getLayoutDirection() {
            return this.mMarginFlags & 3;
        }

        public int getMarginEnd() {
            int n = this.endMargin;
            if (n != Integer.MIN_VALUE) {
                return n;
            }
            if ((this.mMarginFlags & 32) == 32) {
                this.doResolveMargins();
            }
            if ((this.mMarginFlags & 3) != 1) {
                return this.rightMargin;
            }
            return this.leftMargin;
        }

        public int getMarginStart() {
            int n = this.startMargin;
            if (n != Integer.MIN_VALUE) {
                return n;
            }
            if ((this.mMarginFlags & 32) == 32) {
                this.doResolveMargins();
            }
            if ((this.mMarginFlags & 3) != 1) {
                return this.leftMargin;
            }
            return this.rightMargin;
        }

        public boolean isLayoutRtl() {
            byte by = this.mMarginFlags;
            boolean bl = true;
            if ((by & 3) != 1) {
                bl = false;
            }
            return bl;
        }

        public boolean isMarginRelative() {
            boolean bl = this.startMargin != Integer.MIN_VALUE || this.endMargin != Integer.MIN_VALUE;
            return bl;
        }

        @Override
        public void onDebugDraw(View view, Canvas canvas, Paint paint) {
            Insets insets = View.isLayoutModeOptical(view.mParent) ? view.getOpticalInsets() : Insets.NONE;
            ViewGroup.fillDifference(canvas, view.getLeft() + insets.left, view.getTop() + insets.top, view.getRight() - insets.right, view.getBottom() - insets.bottom, this.leftMargin, this.topMargin, this.rightMargin, this.bottomMargin, paint);
        }

        @Override
        public void resolveLayoutDirection(int n) {
            this.setLayoutDirection(n);
            if (this.isMarginRelative() && (this.mMarginFlags & 32) == 32) {
                this.doResolveMargins();
                return;
            }
        }

        public void setLayoutDirection(int n) {
            if (n != 0 && n != 1) {
                return;
            }
            byte by = this.mMarginFlags;
            if (n != (by & 3)) {
                this.mMarginFlags = (byte)(by & -4);
                this.mMarginFlags = (byte)(this.mMarginFlags | n & 3);
                this.mMarginFlags = this.isMarginRelative() ? (byte)((byte)(this.mMarginFlags | 32)) : (byte)((byte)(this.mMarginFlags & -33));
            }
        }

        public void setMarginEnd(int n) {
            this.endMargin = n;
            this.mMarginFlags = (byte)(this.mMarginFlags | 32);
        }

        public void setMarginStart(int n) {
            this.startMargin = n;
            this.mMarginFlags = (byte)(this.mMarginFlags | 32);
        }

        public void setMargins(int n, int n2, int n3, int n4) {
            this.leftMargin = n;
            this.topMargin = n2;
            this.rightMargin = n3;
            this.bottomMargin = n4;
            this.mMarginFlags = (byte)(this.mMarginFlags & -5);
            this.mMarginFlags = (byte)(this.mMarginFlags & -9);
            this.mMarginFlags = this.isMarginRelative() ? (byte)((byte)(this.mMarginFlags | 32)) : (byte)((byte)(this.mMarginFlags & -33));
        }

        @UnsupportedAppUsage
        public void setMarginsRelative(int n, int n2, int n3, int n4) {
            this.startMargin = n;
            this.topMargin = n2;
            this.endMargin = n3;
            this.bottomMargin = n4;
            this.mMarginFlags = (byte)(this.mMarginFlags | 32);
        }
    }

    public static interface OnHierarchyChangeListener {
        public void onChildViewAdded(View var1, View var2);

        public void onChildViewRemoved(View var1, View var2);
    }

    private static final class TouchTarget {
        public static final int ALL_POINTER_IDS = -1;
        private static final int MAX_RECYCLED = 32;
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock;
        private static int sRecycledCount;
        @UnsupportedAppUsage
        public View child;
        public TouchTarget next;
        public int pointerIdBits;

        static {
            sRecycleLock = new Object[0];
        }

        @UnsupportedAppUsage
        private TouchTarget() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TouchTarget obtain(View view, int n) {
            TouchTarget touchTarget;
            if (view == null) {
                throw new IllegalArgumentException("child must be non-null");
            }
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycleBin == null) {
                    touchTarget = new TouchTarget();
                } else {
                    touchTarget = sRecycleBin;
                    sRecycleBin = touchTarget.next;
                    --sRecycledCount;
                    touchTarget.next = null;
                }
            }
            touchTarget.child = view;
            touchTarget.pointerIdBits = n;
            return touchTarget;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recycle() {
            if (this.child == null) {
                throw new IllegalStateException("already recycled once");
            }
            Object object = sRecycleLock;
            synchronized (object) {
                if (sRecycledCount < 32) {
                    this.next = sRecycleBin;
                    sRecycleBin = this;
                    ++sRecycledCount;
                } else {
                    this.next = null;
                }
                this.child = null;
                return;
            }
        }
    }

    static class ViewLocationHolder
    implements Comparable<ViewLocationHolder> {
        public static final int COMPARISON_STRATEGY_LOCATION = 2;
        public static final int COMPARISON_STRATEGY_STRIPE = 1;
        private static final int MAX_POOL_SIZE = 32;
        private static int sComparisonStrategy;
        private static final Pools.SynchronizedPool<ViewLocationHolder> sPool;
        private int mLayoutDirection;
        private final Rect mLocation = new Rect();
        private ViewGroup mRoot;
        public View mView;

        static {
            sPool = new Pools.SynchronizedPool(32);
            sComparisonStrategy = 1;
        }

        ViewLocationHolder() {
        }

        private void clear() {
            this.mView = null;
            this.mRoot = null;
            this.mLocation.set(0, 0, 0, 0);
        }

        private static int compareBoundsOfTree(ViewLocationHolder viewLocationHolder, ViewLocationHolder viewLocationHolder2) {
            int n;
            if (sComparisonStrategy == 1) {
                if (viewLocationHolder.mLocation.bottom - viewLocationHolder2.mLocation.top <= 0) {
                    return -1;
                }
                if (viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.bottom >= 0) {
                    return 1;
                }
            }
            if (viewLocationHolder.mLayoutDirection == 0) {
                n = viewLocationHolder.mLocation.left - viewLocationHolder2.mLocation.left;
                if (n != 0) {
                    return n;
                }
            } else {
                n = viewLocationHolder.mLocation.right - viewLocationHolder2.mLocation.right;
                if (n != 0) {
                    return -n;
                }
            }
            if ((n = viewLocationHolder.mLocation.top - viewLocationHolder2.mLocation.top) != 0) {
                return n;
            }
            n = viewLocationHolder.mLocation.height() - viewLocationHolder2.mLocation.height();
            if (n != 0) {
                return -n;
            }
            n = viewLocationHolder.mLocation.width() - viewLocationHolder2.mLocation.width();
            if (n != 0) {
                return -n;
            }
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            Rect rect3 = new Rect();
            viewLocationHolder.mView.getBoundsOnScreen(rect, true);
            viewLocationHolder2.mView.getBoundsOnScreen(rect2, true);
            rect = viewLocationHolder.mView.findViewByPredicateTraversal(new _$$Lambda$ViewGroup$ViewLocationHolder$QbO7cM0ULKe25a7bfXG3VH6DB0c(rect3, rect), null);
            viewLocationHolder2 = viewLocationHolder2.mView.findViewByPredicateTraversal(new _$$Lambda$ViewGroup$ViewLocationHolder$AjKvqdj7SGGIzA5qrlZUuu71jl8(rect3, rect2), null);
            if (rect != null && viewLocationHolder2 != null) {
                return ViewLocationHolder.compareBoundsOfTree(ViewLocationHolder.obtain(viewLocationHolder.mRoot, (View)((Object)rect)), ViewLocationHolder.obtain(viewLocationHolder.mRoot, (View)((Object)viewLocationHolder2)));
            }
            if (rect != null) {
                return 1;
            }
            if (viewLocationHolder2 != null) {
                return -1;
            }
            return 0;
        }

        private void init(ViewGroup viewGroup, View view) {
            Rect rect = this.mLocation;
            view.getDrawingRect(rect);
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            this.mView = view;
            this.mRoot = viewGroup;
            this.mLayoutDirection = viewGroup.getLayoutDirection();
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$0(Rect rect, Rect rect2, View view) {
            view.getBoundsOnScreen(rect, true);
            return true ^ rect.equals(rect2);
        }

        static /* synthetic */ boolean lambda$compareBoundsOfTree$1(Rect rect, Rect rect2, View view) {
            view.getBoundsOnScreen(rect, true);
            return true ^ rect.equals(rect2);
        }

        public static ViewLocationHolder obtain(ViewGroup viewGroup, View view) {
            ViewLocationHolder viewLocationHolder;
            ViewLocationHolder viewLocationHolder2 = viewLocationHolder = sPool.acquire();
            if (viewLocationHolder == null) {
                viewLocationHolder2 = new ViewLocationHolder();
            }
            viewLocationHolder2.init(viewGroup, view);
            return viewLocationHolder2;
        }

        public static void setComparisonStrategy(int n) {
            sComparisonStrategy = n;
        }

        @Override
        public int compareTo(ViewLocationHolder viewLocationHolder) {
            if (viewLocationHolder == null) {
                return 1;
            }
            int n = ViewLocationHolder.compareBoundsOfTree(this, viewLocationHolder);
            if (n != 0) {
                return n;
            }
            return this.mView.getAccessibilityViewId() - viewLocationHolder.mView.getAccessibilityViewId();
        }

        public void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

}

