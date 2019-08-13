/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListConnector;
import android.widget.ExpandableListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.android.internal.R;

class FastScroller {
    private static Property<View, Integer> BOTTOM;
    private static final int DURATION_CROSS_FADE = 50;
    private static final int DURATION_FADE_IN = 150;
    private static final int DURATION_FADE_OUT = 300;
    private static final int DURATION_RESIZE = 100;
    private static final long FADE_TIMEOUT = 1500L;
    private static Property<View, Integer> LEFT;
    private static final int MIN_PAGES = 4;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_LEFT = 0;
    private static final int PREVIEW_RIGHT = 1;
    private static Property<View, Integer> RIGHT;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final long TAP_TIMEOUT;
    private static final int THUMB_POSITION_INSIDE = 1;
    private static final int THUMB_POSITION_MIDPOINT = 0;
    private static Property<View, Integer> TOP;
    private boolean mAlwaysShow;
    @UnsupportedAppUsage
    private final Rect mContainerRect = new Rect();
    private int mCurrentSection = -1;
    private AnimatorSet mDecorAnimation;
    private final Runnable mDeferHide = new Runnable(){

        @Override
        public void run() {
            FastScroller.this.setState(0);
        }
    };
    private boolean mEnabled;
    private int mFirstVisibleItem;
    @UnsupportedAppUsage
    private int mHeaderCount;
    private float mInitialTouchY;
    private boolean mLayoutFromRight;
    private final AbsListView mList;
    private Adapter mListAdapter;
    @UnsupportedAppUsage
    private boolean mLongList;
    private boolean mMatchDragPosition;
    @UnsupportedAppUsage
    private final int mMinimumTouchTarget;
    private int mOldChildCount;
    private int mOldItemCount;
    private final ViewGroupOverlay mOverlay;
    private int mOverlayPosition;
    private long mPendingDrag = -1L;
    private AnimatorSet mPreviewAnimation;
    private final View mPreviewImage;
    private int mPreviewMinHeight;
    private int mPreviewMinWidth;
    private int mPreviewPadding;
    private final int[] mPreviewResId = new int[2];
    private final TextView mPrimaryText;
    private int mScaledTouchSlop;
    private int mScrollBarStyle;
    private boolean mScrollCompleted;
    private int mScrollbarPosition = -1;
    private final TextView mSecondaryText;
    private SectionIndexer mSectionIndexer;
    private Object[] mSections;
    private boolean mShowingPreview;
    private boolean mShowingPrimary;
    private int mState;
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(Animator object) {
            object = FastScroller.this;
            ((FastScroller)object).mShowingPrimary = ((FastScroller)object).mShowingPrimary ^ true;
        }
    };
    private final Rect mTempBounds = new Rect();
    private final Rect mTempMargins = new Rect();
    private int mTextAppearance;
    private ColorStateList mTextColor;
    private float mTextSize;
    @UnsupportedAppUsage
    private Drawable mThumbDrawable;
    @UnsupportedAppUsage
    private final ImageView mThumbImage;
    private int mThumbMinHeight;
    private int mThumbMinWidth;
    private float mThumbOffset;
    private int mThumbPosition;
    private float mThumbRange;
    @UnsupportedAppUsage
    private Drawable mTrackDrawable;
    @UnsupportedAppUsage
    private final ImageView mTrackImage;
    private boolean mUpdatingLayout;
    private int mWidth;

    static {
        TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        LEFT = new IntProperty<View>("left"){

            @Override
            public Integer get(View view) {
                return view.getLeft();
            }

            @Override
            public void setValue(View view, int n) {
                view.setLeft(n);
            }
        };
        TOP = new IntProperty<View>("top"){

            @Override
            public Integer get(View view) {
                return view.getTop();
            }

            @Override
            public void setValue(View view, int n) {
                view.setTop(n);
            }
        };
        RIGHT = new IntProperty<View>("right"){

            @Override
            public Integer get(View view) {
                return view.getRight();
            }

            @Override
            public void setValue(View view, int n) {
                view.setRight(n);
            }
        };
        BOTTOM = new IntProperty<View>("bottom"){

            @Override
            public Integer get(View view) {
                return view.getBottom();
            }

            @Override
            public void setValue(View view, int n) {
                view.setBottom(n);
            }
        };
    }

    @UnsupportedAppUsage
    public FastScroller(AbsListView absListView, int n) {
        this.mList = absListView;
        this.mOldItemCount = absListView.getCount();
        this.mOldChildCount = absListView.getChildCount();
        Object object = absListView.getContext();
        this.mScaledTouchSlop = ViewConfiguration.get((Context)object).getScaledTouchSlop();
        this.mScrollBarStyle = absListView.getScrollBarStyle();
        boolean bl = true;
        this.mScrollCompleted = true;
        this.mState = 1;
        if (object.getApplicationInfo().targetSdkVersion < 11) {
            bl = false;
        }
        this.mMatchDragPosition = bl;
        this.mTrackImage = new ImageView((Context)object);
        this.mTrackImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mThumbImage = new ImageView((Context)object);
        this.mThumbImage.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mPreviewImage = new View((Context)object);
        this.mPreviewImage.setAlpha(0.0f);
        this.mPrimaryText = this.createPreviewTextView((Context)object);
        this.mSecondaryText = this.createPreviewTextView((Context)object);
        this.mMinimumTouchTarget = absListView.getResources().getDimensionPixelSize(17105152);
        this.setStyle(n);
        this.mOverlay = object = absListView.getOverlay();
        ((ViewGroupOverlay)object).add(this.mTrackImage);
        ((ViewGroupOverlay)object).add(this.mThumbImage);
        ((ViewGroupOverlay)object).add(this.mPreviewImage);
        ((ViewGroupOverlay)object).add(this.mPrimaryText);
        ((ViewGroupOverlay)object).add(this.mSecondaryText);
        this.getSectionsFromIndexer();
        this.updateLongList(this.mOldChildCount, this.mOldItemCount);
        this.setScrollbarPosition(absListView.getVerticalScrollbarPosition());
        this.postAutoHide();
    }

    private static Animator animateAlpha(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, f);
    }

    private static Animator animateBounds(View view, Rect rect) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofInt(LEFT, rect.left), PropertyValuesHolder.ofInt(TOP, rect.top), PropertyValuesHolder.ofInt(RIGHT, rect.right), PropertyValuesHolder.ofInt(BOTTOM, rect.bottom));
    }

    private static Animator animateScaleX(View view, float f) {
        return ObjectAnimator.ofFloat(view, View.SCALE_X, f);
    }

    private void applyLayout(View view, Rect rect) {
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        float f = this.mLayoutFromRight ? (float)(rect.right - rect.left) : 0.0f;
        view.setPivotX(f);
    }

    private void beginDrag() {
        AbsListView absListView;
        this.mPendingDrag = -1L;
        this.setState(2);
        if (this.mListAdapter == null && this.mList != null) {
            this.getSectionsFromIndexer();
        }
        if ((absListView = this.mList) != null) {
            absListView.requestDisallowInterceptTouchEvent(true);
            this.mList.reportScrollStateChange(1);
        }
        this.cancelFling();
    }

    private void cancelFling() {
        MotionEvent motionEvent = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private TextView createPreviewTextView(Context object) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        object = new TextView((Context)object);
        ((View)object).setLayoutParams(layoutParams);
        ((TextView)object).setSingleLine(true);
        ((TextView)object).setEllipsize(TextUtils.TruncateAt.MIDDLE);
        ((TextView)object).setGravity(17);
        ((View)object).setAlpha(0.0f);
        ((View)object).setLayoutDirection(this.mList.getLayoutDirection());
        return object;
    }

    private float getPosFromItemCount(int n, int n2, int n3) {
        Object object = this.mSectionIndexer;
        if (object == null || this.mListAdapter == null) {
            this.getSectionsFromIndexer();
        }
        if (n2 != 0 && n3 != 0) {
            Object object2;
            int n4 = object != null && (object2 = this.mSections) != null && ((Object[])object2).length > 0 ? 1 : 0;
            if (n4 != 0 && this.mMatchDragPosition) {
                int n5 = this.mHeaderCount;
                n4 = n - n5;
                if (n4 < 0) {
                    return 0.0f;
                }
                n3 -= n5;
                object2 = this.mList.getChildAt(0);
                float f = object2 != null && ((View)object2).getHeight() != 0 ? (float)(this.mList.getPaddingTop() - ((View)object2).getTop()) / (float)((View)object2).getHeight() : 0.0f;
                int n6 = object.getSectionForPosition(n4);
                n5 = object.getPositionForSection(n6);
                int n7 = this.mSections.length;
                if (n6 < n7 - 1) {
                    n = n6 + 1 < n7 ? object.getPositionForSection(n6 + 1) : n3 - 1;
                    n -= n5;
                } else {
                    n = n3 - n5;
                }
                f = n == 0 ? 0.0f : ((float)n4 + f - (float)n5) / (float)n;
                f = ((float)n6 + f) / (float)n7;
                if (n4 > 0 && n4 + n2 == n3) {
                    object = this.mList.getChildAt(n2 - 1);
                    n3 = this.mList.getPaddingBottom();
                    if (this.mList.getClipToPadding()) {
                        n2 = ((View)object).getHeight();
                        n = this.mList.getHeight() - n3 - ((View)object).getTop();
                    } else {
                        n2 = ((View)object).getHeight();
                        n = this.mList.getHeight() - ((View)object).getTop();
                        n2 += n3;
                    }
                    if (n > 0 && n2 > 0) {
                        f += (1.0f - f) * ((float)n / (float)n2);
                    }
                }
                return f;
            }
            if (n2 == n3) {
                return 0.0f;
            }
            return (float)n / (float)(n3 - n2);
        }
        return 0.0f;
    }

    private float getPosFromMotionEvent(float f) {
        float f2 = this.mThumbRange;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((f - this.mThumbOffset) / f2, 0.0f, 1.0f);
    }

    private void getSectionsFromIndexer() {
        Object object;
        this.mSectionIndexer = null;
        Object object2 = object = this.mList.getAdapter();
        if (object instanceof HeaderViewListAdapter) {
            this.mHeaderCount = ((HeaderViewListAdapter)object).getHeadersCount();
            object2 = ((HeaderViewListAdapter)object).getWrappedAdapter();
        }
        if (object2 instanceof ExpandableListConnector) {
            object = ((ExpandableListConnector)object2).getAdapter();
            if (object instanceof SectionIndexer) {
                this.mSectionIndexer = (SectionIndexer)object;
                this.mListAdapter = object2;
                this.mSections = this.mSectionIndexer.getSections();
            }
        } else if (object2 instanceof SectionIndexer) {
            this.mListAdapter = object2;
            this.mSectionIndexer = (SectionIndexer)object2;
            this.mSections = this.mSectionIndexer.getSections();
        } else {
            this.mListAdapter = object2;
            this.mSections = null;
        }
    }

    private static Animator groupAnimatorOfFloat(Property<View, Float> property, float f, View ... arrview) {
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = arrview.length - 1; i >= 0; --i) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(arrview[i], property, f);
            if (builder == null) {
                builder = animatorSet.play(objectAnimator);
                continue;
            }
            builder.with(objectAnimator);
        }
        return animatorSet;
    }

    private boolean isPointInside(float f, float f2) {
        boolean bl = this.isPointInsideX(f) && (this.mTrackDrawable != null || this.isPointInsideY(f2));
        return bl;
    }

    private boolean isPointInsideX(float f) {
        float f2 = this.mThumbImage.getTranslationX();
        float f3 = this.mThumbImage.getLeft();
        float f4 = this.mThumbImage.getRight();
        f3 = (float)this.mMinimumTouchTarget - (f4 + f2 - (f3 + f2));
        f2 = 0.0f;
        if (f3 > 0.0f) {
            f2 = f3;
        }
        boolean bl = this.mLayoutFromRight;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (!(f >= (float)this.mThumbImage.getLeft() - f2)) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = f <= (float)this.mThumbImage.getRight() + f2 ? bl2 : false;
        return bl3;
    }

    private boolean isPointInsideY(float f) {
        float f2 = this.mThumbImage.getTranslationY();
        float f3 = (float)this.mThumbImage.getTop() + f2;
        float f4 = (float)this.mThumbImage.getBottom() + f2;
        float f5 = (float)this.mMinimumTouchTarget - (f4 - f3);
        f2 = 0.0f;
        if (f5 > 0.0f) {
            f2 = f5 / 2.0f;
        }
        boolean bl = f >= f3 - f2 && f <= f4 + f2;
        return bl;
    }

    private void layoutThumb() {
        Rect rect = this.mTempBounds;
        this.measureViewToSide(this.mThumbImage, null, null, rect);
        this.applyLayout(this.mThumbImage, rect);
    }

    private void layoutTrack() {
        int n;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        Rect rect = this.mContainerRect;
        int n2 = Math.max(0, rect.width());
        int n3 = Math.max(0, rect.height());
        imageView.measure(View.MeasureSpec.makeMeasureSpec(n2, Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(n3, 0));
        if (this.mThumbPosition == 1) {
            n3 = rect.top;
            n2 = rect.bottom;
        } else {
            n = imageView2.getHeight() / 2;
            n3 = rect.top;
            n2 = rect.bottom - n;
            n3 += n;
        }
        int n4 = imageView.getMeasuredWidth();
        n = imageView2.getLeft() + (imageView2.getWidth() - n4) / 2;
        imageView.layout(n, n3, n + n4, n2);
    }

    private void measureFloating(View view, Rect rect, Rect rect2) {
        int n;
        int n2;
        int n3;
        if (rect == null) {
            n3 = 0;
            n = 0;
            n2 = 0;
        } else {
            n3 = rect.left;
            n = rect.top;
            n2 = rect.right;
        }
        rect = this.mContainerRect;
        int n4 = rect.width();
        int n5 = Math.max(0, rect.height());
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, n4 - n3 - n2), Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(n5, 0));
        n3 = rect.height();
        n2 = view.getMeasuredWidth();
        n3 = n3 / 10 + n + rect.top;
        n = view.getMeasuredHeight();
        n4 = (n4 - n2) / 2 + rect.left;
        rect2.set(n4, n3, n4 + n2, n + n3);
    }

    private void measurePreview(View view, Rect rect) {
        Rect rect2 = this.mTempMargins;
        rect2.left = this.mPreviewImage.getPaddingLeft();
        rect2.top = this.mPreviewImage.getPaddingTop();
        rect2.right = this.mPreviewImage.getPaddingRight();
        rect2.bottom = this.mPreviewImage.getPaddingBottom();
        if (this.mOverlayPosition == 0) {
            this.measureFloating(view, rect2, rect);
        } else {
            this.measureViewToSide(view, this.mThumbImage, rect2, rect);
        }
    }

    private void measureViewToSide(View view, View view2, Rect rect, Rect rect2) {
        int n;
        int n2;
        int n3;
        if (rect == null) {
            n = 0;
            n3 = 0;
            n2 = 0;
        } else {
            n = rect.left;
            n3 = rect.top;
            n2 = rect.right;
        }
        rect = this.mContainerRect;
        int n4 = rect.width();
        if (view2 != null) {
            n4 = this.mLayoutFromRight ? view2.getLeft() : (n4 -= view2.getRight());
        }
        int n5 = Math.max(0, rect.height());
        n4 = Math.max(0, n4 - n - n2);
        view.measure(View.MeasureSpec.makeMeasureSpec(n4, Integer.MIN_VALUE), View.MeasureSpec.makeSafeMeasureSpec(n5, 0));
        n5 = Math.min(n4, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            n4 = view2 == null ? rect.right : view2.getLeft();
            n = (n4 -= n2) - n5;
        } else {
            n4 = view2 == null ? rect.left : view2.getRight();
            n = n4 + n;
            n4 = n + n5;
        }
        rect2.set(n, n3, n4, n3 + view.getMeasuredHeight());
    }

    private void onStateDependencyChanged(boolean bl) {
        if (this.isEnabled()) {
            if (this.isAlwaysShowEnabled()) {
                this.setState(1);
            } else if (this.mState == 1) {
                this.postAutoHide();
            } else if (bl) {
                this.setState(1);
                this.postAutoHide();
            }
        } else {
            this.stop();
        }
        this.mList.resolvePadding();
    }

    private void postAutoHide() {
        this.mList.removeCallbacks(this.mDeferHide);
        this.mList.postDelayed(this.mDeferHide, 1500L);
    }

    private void refreshDrawablePressedState() {
        boolean bl = this.mState == 2;
        this.mThumbImage.setPressed(bl);
        this.mTrackImage.setPressed(bl);
    }

    private void scrollTo(float f) {
        int n;
        this.mScrollCompleted = false;
        int n2 = this.mList.getCount();
        Object object = this.mSections;
        int n3 = object == null ? 0 : ((Object[])object).length;
        if (object != null && n3 > 1) {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            block20 : {
                n6 = n4 = MathUtils.constrain((int)((float)n3 * f), 0, n3 - 1);
                int n11 = this.mSectionIndexer.getPositionForSection(n6);
                int n12 = n6;
                n8 = n2;
                n10 = n11;
                int n13 = n6;
                n5 = n6 + 1;
                if (n6 < n3 - 1) {
                    n8 = this.mSectionIndexer.getPositionForSection(n6 + 1);
                }
                n = n12;
                n9 = n10;
                n7 = n13;
                if (n8 == n11) {
                    n9 = n10;
                    n10 = n6;
                    do {
                        n = n12;
                        n7 = n13;
                        if (n10 <= 0) break block20;
                        n = n10 - 1;
                        n6 = this.mSectionIndexer.getPositionForSection(n);
                        if (n6 != n11) {
                            n7 = n;
                            n9 = n6;
                            break block20;
                        }
                        n10 = n;
                        n9 = n6;
                    } while (n != 0);
                    n = 0;
                    n7 = n13;
                    n9 = n6;
                }
            }
            n6 = n5 + 1;
            n10 = n5;
            while (n6 < n3 && this.mSectionIndexer.getPositionForSection(n6) == n8) {
                ++n6;
                ++n10;
            }
            float f2 = (float)n7 / (float)n3;
            float f3 = (float)n10 / (float)n3;
            float f4 = n2 == 0 ? Float.MAX_VALUE : 0.125f / (float)n2;
            if (n7 != n4 || !(f - f2 < f4)) {
                n9 = (int)((float)(n8 - n9) * (f - f2) / (f3 - f2)) + n9;
            }
            n9 = MathUtils.constrain(n9, 0, n2 - 1);
            object = this.mList;
            if (object instanceof ExpandableListView) {
                object = (ExpandableListView)object;
                ((AbsListView)object).setSelectionFromTop(((ExpandableListView)object).getFlatListPosition(ExpandableListView.getPackedPositionForGroup(this.mHeaderCount + n9)), 0);
            } else if (object instanceof ListView) {
                ((ListView)object).setSelectionFromTop(this.mHeaderCount + n9, 0);
            } else {
                ((AdapterView)object).setSelection(this.mHeaderCount + n9);
            }
        } else {
            n = MathUtils.constrain((int)((float)n2 * f), 0, n2 - 1);
            object = this.mList;
            if (object instanceof ExpandableListView) {
                object = (ExpandableListView)object;
                ((AbsListView)object).setSelectionFromTop(((ExpandableListView)object).getFlatListPosition(ExpandableListView.getPackedPositionForGroup(this.mHeaderCount + n)), 0);
            } else if (object instanceof ListView) {
                ((ListView)object).setSelectionFromTop(this.mHeaderCount + n, 0);
            } else {
                ((AdapterView)object).setSelection(this.mHeaderCount + n);
            }
            n = -1;
        }
        if (this.mCurrentSection != n) {
            this.mCurrentSection = n;
            boolean bl = this.transitionPreviewLayout(n);
            if (!this.mShowingPreview && bl) {
                this.transitionToDragging();
            } else if (this.mShowingPreview && !bl) {
                this.transitionToVisible();
            }
        }
    }

    @UnsupportedAppUsage
    private void setState(int n) {
        this.mList.removeCallbacks(this.mDeferHide);
        int n2 = n;
        if (this.mAlwaysShow) {
            n2 = n;
            if (n == 0) {
                n2 = 1;
            }
        }
        if (n2 == this.mState) {
            return;
        }
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    if (this.transitionPreviewLayout(this.mCurrentSection)) {
                        this.transitionToDragging();
                    } else {
                        this.transitionToVisible();
                    }
                }
            } else {
                this.transitionToVisible();
            }
        } else {
            this.transitionToHidden();
        }
        this.mState = n2;
        this.refreshDrawablePressedState();
    }

    private void setThumbPos(float f) {
        f = this.mThumbRange * f + this.mThumbOffset;
        View view = this.mThumbImage;
        view.setTranslationY(f - (float)view.getHeight() / 2.0f);
        view = this.mPreviewImage;
        float f2 = (float)view.getHeight() / 2.0f;
        int n = this.mOverlayPosition;
        if (n != 1) {
            f = n != 2 ? 0.0f : (f -= f2);
        }
        Rect rect = this.mContainerRect;
        n = rect.top;
        int n2 = rect.bottom;
        f = MathUtils.constrain(f, (float)n + f2, (float)n2 - f2) - f2;
        view.setTranslationY(f);
        this.mPrimaryText.setTranslationY(f);
        this.mSecondaryText.setTranslationY(f);
    }

    private void startPendingDrag() {
        this.mPendingDrag = SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private boolean transitionPreviewLayout(int n) {
        Object object = this.mSections;
        TextView textView = null;
        Object object2 = textView;
        if (object != null) {
            object2 = textView;
            if (n >= 0) {
                object2 = textView;
                if (n < ((Object[])object).length) {
                    object = object[n];
                    object2 = textView;
                    if (object != null) {
                        object2 = object.toString();
                    }
                }
            }
        }
        Object object3 = this.mTempBounds;
        View view = this.mPreviewImage;
        if (this.mShowingPrimary) {
            textView = this.mPrimaryText;
            object = this.mSecondaryText;
        } else {
            textView = this.mSecondaryText;
            object = this.mPrimaryText;
        }
        ((TextView)object).setText((CharSequence)object2);
        this.measurePreview((View)object, (Rect)object3);
        this.applyLayout((View)object, (Rect)object3);
        Object object4 = this.mPreviewAnimation;
        if (object4 != null) {
            ((AnimatorSet)object4).cancel();
        }
        object4 = FastScroller.animateAlpha((View)object, 1.0f).setDuration(50L);
        Animator animator2 = FastScroller.animateAlpha(textView, 0.0f).setDuration(50L);
        animator2.addListener(this.mSwitchPrimaryListener);
        ((Rect)object3).left -= view.getPaddingLeft();
        ((Rect)object3).top -= view.getPaddingTop();
        ((Rect)object3).right += view.getPaddingRight();
        ((Rect)object3).bottom += view.getPaddingBottom();
        object3 = FastScroller.animateBounds(view, (Rect)object3);
        ((Animator)object3).setDuration(100L);
        this.mPreviewAnimation = new AnimatorSet();
        object4 = this.mPreviewAnimation.play(animator2).with((Animator)object4);
        ((AnimatorSet.Builder)object4).with((Animator)object3);
        int n2 = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        n = ((View)object).getWidth();
        if (n > n2) {
            ((View)object).setScaleX((float)n2 / (float)n);
            ((AnimatorSet.Builder)object4).with(FastScroller.animateScaleX((View)object, 1.0f).setDuration(100L));
        } else {
            ((View)object).setScaleX(1.0f);
        }
        n2 = textView.getWidth();
        if (n2 > n) {
            ((AnimatorSet.Builder)object4).with(FastScroller.animateScaleX(textView, (float)n / (float)n2).setDuration(100L));
        }
        this.mPreviewAnimation.start();
        return TextUtils.isEmpty((CharSequence)object2) ^ true;
    }

    private void transitionToDragging() {
        Animator animator2 = this.mDecorAnimation;
        if (animator2 != null) {
            animator2.cancel();
        }
        animator2 = FastScroller.groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(150L);
        Animator animator3 = FastScroller.groupAnimatorOfFloat(View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(animator2, animator3);
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    private void transitionToHidden() {
        Animator animator2 = this.mDecorAnimation;
        if (animator2 != null) {
            animator2.cancel();
        }
        animator2 = FastScroller.groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        int n = this.mLayoutFromRight ? this.mThumbImage.getWidth() : -this.mThumbImage.getWidth();
        float f = n;
        Animator animator3 = FastScroller.groupAnimatorOfFloat(View.TRANSLATION_X, f, this.mThumbImage, this.mTrackImage).setDuration(300L);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(animator2, animator3);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToVisible() {
        Animator animator2 = this.mDecorAnimation;
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = FastScroller.groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        Animator animator4 = FastScroller.groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        animator2 = FastScroller.groupAnimatorOfFloat(View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        this.mDecorAnimation = new AnimatorSet();
        this.mDecorAnimation.playTogether(animator3, animator4, animator2);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void updateAppearance() {
        float f;
        int n = 0;
        this.mTrackImage.setImageDrawable(this.mTrackDrawable);
        Object object = this.mTrackDrawable;
        if (object != null) {
            n = Math.max(0, ((Drawable)object).getIntrinsicWidth());
        }
        this.mThumbImage.setImageDrawable(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        object = this.mThumbDrawable;
        int n2 = n;
        if (object != null) {
            n2 = Math.max(n, ((Drawable)object).getIntrinsicWidth());
        }
        this.mWidth = Math.max(n2, this.mThumbMinWidth);
        n = this.mTextAppearance;
        if (n != 0) {
            this.mPrimaryText.setTextAppearance(n);
            this.mSecondaryText.setTextAppearance(this.mTextAppearance);
        }
        if ((object = this.mTextColor) != null) {
            this.mPrimaryText.setTextColor((ColorStateList)object);
            this.mSecondaryText.setTextColor(this.mTextColor);
        }
        if ((f = this.mTextSize) > 0.0f) {
            this.mPrimaryText.setTextSize(0, f);
            this.mSecondaryText.setTextSize(0, this.mTextSize);
        }
        n = this.mPreviewPadding;
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mPrimaryText.setPadding(n, n, n, n);
        this.mSecondaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setPadding(n, n, n, n);
        this.refreshDrawablePressedState();
    }

    private void updateContainerRect() {
        AbsListView absListView = this.mList;
        absListView.resolvePadding();
        Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = 0;
        rect.right = absListView.getWidth();
        rect.bottom = absListView.getHeight();
        int n = this.mScrollBarStyle;
        if (n == 16777216 || n == 0) {
            rect.left += absListView.getPaddingLeft();
            rect.top += absListView.getPaddingTop();
            rect.right -= absListView.getPaddingRight();
            rect.bottom -= absListView.getPaddingBottom();
            if (n == 16777216) {
                n = this.getWidth();
                if (this.mScrollbarPosition == 2) {
                    rect.right += n;
                } else {
                    rect.left -= n;
                }
            }
        }
    }

    private void updateLongList(int n, int n2) {
        boolean bl = n > 0 && n2 / n >= 4;
        if (this.mLongList != bl) {
            this.mLongList = bl;
            this.onStateDependencyChanged(false);
        }
    }

    private void updateOffsetAndRange() {
        float f;
        float f2;
        ImageView imageView = this.mTrackImage;
        ImageView imageView2 = this.mThumbImage;
        if (this.mThumbPosition == 1) {
            f2 = (float)imageView2.getHeight() / 2.0f;
            f = (float)imageView.getTop() + f2;
            f2 = (float)imageView.getBottom() - f2;
        } else {
            f = imageView.getTop();
            f2 = imageView.getBottom();
        }
        this.mThumbOffset = f;
        this.mThumbRange = f2 - f;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    public boolean isEnabled() {
        boolean bl = this.mEnabled && (this.mLongList || this.mAlwaysShow);
        return bl;
    }

    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        int n = motionEvent.getActionMasked();
        if ((n == 9 || n == 7) && this.mState == 0 && this.isPointInside(motionEvent.getX(), motionEvent.getY())) {
            this.setState(1);
            this.postAutoHide();
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        block9 : {
            block6 : {
                block7 : {
                    block8 : {
                        if (!this.isEnabled()) {
                            return false;
                        }
                        int n = motionEvent.getActionMasked();
                        if (n == 0) break block6;
                        if (n == 1) break block7;
                        if (n == 2) break block8;
                        if (n == 3) break block7;
                        break block9;
                    }
                    if (!this.isPointInside(motionEvent.getX(), motionEvent.getY())) {
                        this.cancelPendingDrag();
                    } else {
                        long l = this.mPendingDrag;
                        if (l >= 0L && l <= SystemClock.uptimeMillis()) {
                            this.beginDrag();
                            this.scrollTo(this.getPosFromMotionEvent(this.mInitialTouchY));
                            return this.onTouchEvent(motionEvent);
                        }
                    }
                    break block9;
                }
                this.cancelPendingDrag();
                break block9;
            }
            if (this.isPointInside(motionEvent.getX(), motionEvent.getY())) {
                if (!this.mList.isInScrollingContainer()) {
                    return true;
                }
                this.mInitialTouchY = motionEvent.getY();
                this.startPendingDrag();
            }
        }
        return false;
    }

    public void onItemCountChanged(int n, int n2) {
        if (this.mOldItemCount != n2 || this.mOldChildCount != n) {
            this.mOldItemCount = n2;
            this.mOldChildCount = n;
            boolean bl = n2 - n > 0;
            if (bl && this.mState != 2) {
                this.setThumbPos(this.getPosFromItemCount(this.mList.getFirstVisiblePosition(), n, n2));
            }
            this.updateLongList(n, n2);
        }
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (this.mState != 2 && !this.isPointInside(motionEvent.getX(), motionEvent.getY())) {
            return null;
        }
        return PointerIcon.getSystemIcon(this.mList.getContext(), 1000);
    }

    public void onScroll(int n, int n2, int n3) {
        boolean bl = this.isEnabled();
        boolean bl2 = false;
        if (!bl) {
            this.setState(0);
            return;
        }
        if (n3 - n2 > 0) {
            bl2 = true;
        }
        if (bl2 && this.mState != 2) {
            this.setThumbPos(this.getPosFromItemCount(n, n2, n3));
        }
        this.mScrollCompleted = true;
        if (this.mFirstVisibleItem != n) {
            this.mFirstVisibleItem = n;
            if (this.mState != 2) {
                this.setState(1);
                this.postAutoHide();
            }
        }
    }

    public void onSectionsChanged() {
        this.mListAdapter = null;
    }

    @UnsupportedAppUsage
    public void onSizeChanged(int n, int n2, int n3, int n4) {
        this.updateLayout();
    }

    @UnsupportedAppUsage
    public boolean onTouchEvent(MotionEvent object) {
        if (!this.isEnabled()) {
            return false;
        }
        int n = ((MotionEvent)object).getActionMasked();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.cancelPendingDrag();
                    }
                } else {
                    if (this.mPendingDrag >= 0L && Math.abs(((MotionEvent)object).getY() - this.mInitialTouchY) > (float)this.mScaledTouchSlop) {
                        this.beginDrag();
                    }
                    if (this.mState == 2) {
                        float f = this.getPosFromMotionEvent(((MotionEvent)object).getY());
                        this.setThumbPos(f);
                        if (this.mScrollCompleted) {
                            this.scrollTo(f);
                        }
                        return true;
                    }
                }
            } else {
                if (this.mPendingDrag >= 0L) {
                    this.beginDrag();
                    float f = this.getPosFromMotionEvent(((MotionEvent)object).getY());
                    this.setThumbPos(f);
                    this.scrollTo(f);
                }
                if (this.mState == 2) {
                    object = this.mList;
                    if (object != null) {
                        ((AbsListView)object).requestDisallowInterceptTouchEvent(false);
                        this.mList.reportScrollStateChange(0);
                    }
                    this.setState(1);
                    this.postAutoHide();
                    return true;
                }
            }
        } else if (this.isPointInside(((MotionEvent)object).getX(), ((MotionEvent)object).getY()) && !this.mList.isInScrollingContainer()) {
            this.beginDrag();
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
    }

    public void setAlwaysShow(boolean bl) {
        if (this.mAlwaysShow != bl) {
            this.mAlwaysShow = bl;
            this.onStateDependencyChanged(false);
        }
    }

    public void setEnabled(boolean bl) {
        if (this.mEnabled != bl) {
            this.mEnabled = bl;
            this.onStateDependencyChanged(true);
        }
    }

    public void setScrollBarStyle(int n) {
        if (this.mScrollBarStyle != n) {
            this.mScrollBarStyle = n;
            this.updateLayout();
        }
    }

    public void setScrollbarPosition(int n) {
        boolean bl = true;
        int n2 = n;
        if (n == 0) {
            n = this.mList.isLayoutRtl() ? 1 : 2;
            n2 = n;
        }
        if (this.mScrollbarPosition != n2) {
            this.mScrollbarPosition = n2;
            if (n2 == 1) {
                bl = false;
            }
            this.mLayoutFromRight = bl;
            n = this.mPreviewResId[this.mLayoutFromRight];
            this.mPreviewImage.setBackgroundResource(n);
            n = Math.max(0, this.mPreviewMinWidth - this.mPreviewImage.getPaddingLeft() - this.mPreviewImage.getPaddingRight());
            this.mPrimaryText.setMinimumWidth(n);
            this.mSecondaryText.setMinimumWidth(n);
            n = Math.max(0, this.mPreviewMinHeight - this.mPreviewImage.getPaddingTop() - this.mPreviewImage.getPaddingBottom());
            this.mPrimaryText.setMinimumHeight(n);
            this.mSecondaryText.setMinimumHeight(n);
            this.updateLayout();
        }
    }

    public void setStyle(int n) {
        TypedArray typedArray = this.mList.getContext().obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, n);
        int n2 = typedArray.getIndexCount();
        block16 : for (n = 0; n < n2; ++n) {
            int n3 = typedArray.getIndex(n);
            switch (n3) {
                default: {
                    continue block16;
                }
                case 13: {
                    this.mTrackDrawable = typedArray.getDrawable(n3);
                    continue block16;
                }
                case 12: {
                    this.mThumbMinWidth = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 11: {
                    this.mThumbMinHeight = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 10: {
                    this.mThumbDrawable = typedArray.getDrawable(n3);
                    continue block16;
                }
                case 9: {
                    this.mOverlayPosition = typedArray.getInt(n3, 0);
                    continue block16;
                }
                case 8: {
                    this.mPreviewResId[1] = typedArray.getResourceId(n3, 0);
                    continue block16;
                }
                case 7: {
                    this.mPreviewResId[0] = typedArray.getResourceId(n3, 0);
                    continue block16;
                }
                case 6: {
                    this.mThumbPosition = typedArray.getInt(n3, 0);
                    continue block16;
                }
                case 5: {
                    this.mPreviewMinHeight = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 4: {
                    this.mPreviewMinWidth = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 3: {
                    this.mPreviewPadding = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 2: {
                    this.mTextColor = typedArray.getColorStateList(n3);
                    continue block16;
                }
                case 1: {
                    this.mTextSize = typedArray.getDimensionPixelSize(n3, 0);
                    continue block16;
                }
                case 0: {
                    this.mTextAppearance = typedArray.getResourceId(n3, 0);
                }
            }
        }
        typedArray.recycle();
        this.updateAppearance();
    }

    public void stop() {
        this.setState(0);
    }

    public void updateLayout() {
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        this.updateContainerRect();
        this.layoutThumb();
        this.layoutTrack();
        this.updateOffsetAndRange();
        Rect rect = this.mTempBounds;
        this.measurePreview(this.mPrimaryText, rect);
        this.applyLayout(this.mPrimaryText, rect);
        this.measurePreview(this.mSecondaryText, rect);
        this.applyLayout(this.mSecondaryText, rect);
        if (this.mPreviewImage != null) {
            rect.left -= this.mPreviewImage.getPaddingLeft();
            rect.top -= this.mPreviewImage.getPaddingTop();
            rect.right += this.mPreviewImage.getPaddingRight();
            rect.bottom += this.mPreviewImage.getPaddingBottom();
            this.applyLayout(this.mPreviewImage, rect);
        }
        this.mUpdatingLayout = false;
    }

}

