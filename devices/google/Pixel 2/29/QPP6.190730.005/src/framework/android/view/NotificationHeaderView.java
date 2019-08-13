/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.widget.CachingIconView;
import java.util.ArrayList;

@RemoteViews.RemoteView
public class NotificationHeaderView
extends ViewGroup {
    public static final int NO_COLOR = 1;
    private boolean mAcceptAllTouches;
    private View mAppName;
    private View mAppOps;
    private View.OnClickListener mAppOpsListener;
    private View mAudiblyAlertedIcon;
    private Drawable mBackground;
    private View mCameraIcon;
    private final int mChildMinWidth;
    private final int mContentEndMargin;
    private boolean mEntireHeaderClickable;
    private ImageView mExpandButton;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandOnlyOnButton;
    private boolean mExpanded;
    private final int mGravity;
    private View mHeaderText;
    private int mHeaderTextMarginEnd;
    private CachingIconView mIcon;
    private int mIconColor;
    private View mMicIcon;
    private int mOriginalNotificationColor;
    private View mOverlayIcon;
    private View mProfileBadge;
    ViewOutlineProvider mProvider = new ViewOutlineProvider(){

        @Override
        public void getOutline(View view, Outline outline) {
            if (NotificationHeaderView.this.mBackground != null) {
                outline.setRect(0, 0, NotificationHeaderView.this.getWidth(), NotificationHeaderView.this.getHeight());
                outline.setAlpha(1.0f);
            }
        }
    };
    private View mSecondaryHeaderText;
    private boolean mShowExpandButtonAtEnd;
    private boolean mShowWorkBadgeAtEnd;
    private int mTotalWidth;
    private HeaderTouchListener mTouchListener = new HeaderTouchListener();

    public NotificationHeaderView(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public NotificationHeaderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationHeaderView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public NotificationHeaderView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        Resources resources = this.getResources();
        this.mChildMinWidth = resources.getDimensionPixelSize(17105326);
        this.mContentEndMargin = resources.getDimensionPixelSize(17105307);
        this.mEntireHeaderClickable = resources.getBoolean(17891489);
        object = ((Context)object).obtainStyledAttributes(attributeSet, new int[]{16842927}, n, n2);
        this.mGravity = ((TypedArray)object).getInt(0, 0);
        ((TypedArray)object).recycle();
    }

    private View getFirstChildNotGone() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            return view;
        }
        return this;
    }

    private int shrinkViewForOverflow(int n, int n2, View view, int n3) {
        int n4 = view.getMeasuredWidth();
        int n5 = n2;
        if (n2 > 0) {
            n5 = n2;
            if (view.getVisibility() != 8) {
                n5 = n2;
                if (n4 > n3) {
                    n3 = Math.max(n3, n4 - n2);
                    view.measure(View.MeasureSpec.makeMeasureSpec(n3, Integer.MIN_VALUE), n);
                    n5 = n2 - (n4 - n3);
                }
            }
        }
        return n5;
    }

    private void updateExpandButton() {
        int n;
        int n2;
        if (this.mExpanded) {
            n2 = 17302346;
            n = 17039913;
        } else {
            n2 = 17302406;
            n = 17039912;
        }
        this.mExpandButton.setImageDrawable(this.getContext().getDrawable(n2));
        this.mExpandButton.setColorFilter(this.mOriginalNotificationColor);
        this.mExpandButton.setContentDescription(this.mContext.getText(n));
    }

    private void updateTouchListener() {
        if (this.mExpandClickListener == null && this.mAppOpsListener == null) {
            this.setOnTouchListener(null);
            return;
        }
        this.setOnTouchListener(this.mTouchListener);
        this.mTouchListener.bindTouchRects();
    }

    @Override
    protected void drawableStateChanged() {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null && drawable2.isStateful()) {
            this.mBackground.setState(this.getDrawableState());
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(this.getContext(), attributeSet);
    }

    public ImageView getExpandButton() {
        return this.mExpandButton;
    }

    public int getHeaderTextMarginEnd() {
        return this.mHeaderTextMarginEnd;
    }

    public CachingIconView getIcon() {
        return this.mIcon;
    }

    public int getOriginalIconColor() {
        return this.mIconColor;
    }

    public int getOriginalNotificationColor() {
        return this.mOriginalNotificationColor;
    }

    public View getWorkProfileIcon() {
        return this.mProfileBadge;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isInTouchRect(float f, float f2) {
        if (this.mExpandClickListener == null) {
            return false;
        }
        return this.mTouchListener.isInside(f, f2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, this.getWidth(), this.getHeight());
            this.mBackground.draw(canvas);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAppName = this.findViewById(16908731);
        this.mHeaderText = this.findViewById(16908977);
        this.mSecondaryHeaderText = this.findViewById(16908979);
        this.mExpandButton = (ImageView)this.findViewById(16908898);
        this.mIcon = (CachingIconView)this.findViewById(16908294);
        this.mProfileBadge = this.findViewById(16909257);
        this.mCameraIcon = this.findViewById(16908791);
        this.mMicIcon = this.findViewById(16909111);
        this.mOverlayIcon = this.findViewById(16909207);
        this.mAppOps = this.findViewById(16908732);
        this.mAudiblyAlertedIcon = this.findViewById(16908712);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n4 = this.getPaddingStart();
        n3 = this.getMeasuredWidth();
        n2 = (this.mGravity & 1) != 0 ? 1 : 0;
        n = n4;
        if (n2 != 0) {
            n = n4 + (this.getMeasuredWidth() / 2 - this.mTotalWidth / 2);
        }
        int n5 = this.getChildCount();
        int n6 = this.getMeasuredHeight();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        n2 = n3;
        for (int i = 0; i < n5; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            int n9 = view.getMeasuredHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            int n10 = (int)((float)this.getPaddingTop() + (float)(n6 - n7 - n8 - n9) / 2.0f);
            if (!(view == this.mExpandButton && this.mShowExpandButtonAtEnd || view == this.mProfileBadge || view == this.mAppOps)) {
                n3 = n + marginLayoutParams.getMarginStart();
                n4 = n = view.getMeasuredWidth() + n3;
                n += marginLayoutParams.getMarginEnd();
            } else {
                n4 = n2 == this.getMeasuredWidth() ? n2 - this.mContentEndMargin : n2 - marginLayoutParams.getMarginEnd();
                n3 = n4 - view.getMeasuredWidth();
                n2 = n3 - marginLayoutParams.getMarginStart();
            }
            int n11 = n3;
            int n12 = n4;
            if (this.getLayoutDirection() == 1) {
                n11 = this.getWidth() - n4;
                n12 = this.getWidth() - n3;
            }
            view.layout(n11, n10, n12, n10 + n9);
        }
        this.updateTouchListener();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.getSize(n2);
        int n5 = View.MeasureSpec.makeMeasureSpec(n3, Integer.MIN_VALUE);
        int n6 = View.MeasureSpec.makeMeasureSpec(n4, Integer.MIN_VALUE);
        n2 = this.getPaddingStart();
        int n7 = this.getPaddingEnd();
        for (n = 0; n < this.getChildCount(); ++n) {
            View view = this.getChildAt(n);
            if (view.getVisibility() == 8) continue;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            view.measure(NotificationHeaderView.getChildMeasureSpec(n5, marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width), NotificationHeaderView.getChildMeasureSpec(n6, marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
            if (!(view == this.mExpandButton && this.mShowExpandButtonAtEnd || view == this.mProfileBadge || view == this.mAppOps)) {
                n2 += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + view.getMeasuredWidth();
                continue;
            }
            n7 += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + view.getMeasuredWidth();
        }
        n = Math.max(this.mHeaderTextMarginEnd, n7);
        if (n2 > n3 - n) {
            this.shrinkViewForOverflow(n6, this.shrinkViewForOverflow(n6, this.shrinkViewForOverflow(n6, n2 - n3 + n, this.mAppName, this.mChildMinWidth), this.mHeaderText, 0), this.mSecondaryHeaderText, 0);
        }
        this.mTotalWidth = Math.min(n2 + this.getPaddingEnd(), n3);
        this.setMeasuredDimension(n3, n4);
    }

    @RemotableViewMethod
    public void setAcceptAllTouches(boolean bl) {
        bl = this.mEntireHeaderClickable || bl;
        this.mAcceptAllTouches = bl;
    }

    public void setAppOpsOnClickListener(View.OnClickListener onClickListener) {
        this.mAppOpsListener = onClickListener;
        this.mAppOps.setOnClickListener(this.mAppOpsListener);
        this.mCameraIcon.setOnClickListener(this.mAppOpsListener);
        this.mMicIcon.setOnClickListener(this.mAppOpsListener);
        this.mOverlayIcon.setOnClickListener(this.mAppOpsListener);
        this.updateTouchListener();
    }

    @RemotableViewMethod
    public void setExpandOnlyOnButton(boolean bl) {
        this.mExpandOnlyOnButton = bl;
    }

    @RemotableViewMethod
    public void setExpanded(boolean bl) {
        this.mExpanded = bl;
        this.updateExpandButton();
    }

    public void setHeaderBackgroundDrawable(Drawable drawable2) {
        if (drawable2 != null) {
            this.setWillNotDraw(false);
            this.mBackground = drawable2;
            this.mBackground.setCallback(this);
            this.setOutlineProvider(this.mProvider);
        } else {
            this.setWillNotDraw(true);
            this.mBackground = null;
            this.setOutlineProvider(null);
        }
        this.invalidate();
    }

    @RemotableViewMethod
    public void setHeaderTextMarginEnd(int n) {
        if (this.mHeaderTextMarginEnd != n) {
            this.mHeaderTextMarginEnd = n;
            this.requestLayout();
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
        this.mExpandButton.setOnClickListener(this.mExpandClickListener);
        this.updateTouchListener();
    }

    @RemotableViewMethod
    public void setOriginalIconColor(int n) {
        this.mIconColor = n;
    }

    @RemotableViewMethod
    public void setOriginalNotificationColor(int n) {
        this.mOriginalNotificationColor = n;
    }

    public void setRecentlyAudiblyAlerted(boolean bl) {
        View view = this.mAudiblyAlertedIcon;
        int n = bl ? 0 : 8;
        view.setVisibility(n);
    }

    public void setShowExpandButtonAtEnd(boolean bl) {
        if (bl != this.mShowExpandButtonAtEnd) {
            this.setClipToPadding(bl ^ true);
            this.mShowExpandButtonAtEnd = bl;
        }
    }

    public void setShowWorkBadgeAtEnd(boolean bl) {
        if (bl != this.mShowWorkBadgeAtEnd) {
            this.setClipToPadding(bl ^ true);
            this.mShowWorkBadgeAtEnd = bl;
        }
    }

    public void showAppOpsIcons(ArraySet<Integer> arraySet) {
        View view = this.mOverlayIcon;
        if (view != null && this.mCameraIcon != null && this.mMicIcon != null && arraySet != null) {
            boolean bl = arraySet.contains(24);
            int n = 0;
            int n2 = bl ? 0 : 8;
            view.setVisibility(n2);
            view = this.mCameraIcon;
            n2 = arraySet.contains(26) ? 0 : 8;
            view.setVisibility(n2);
            view = this.mMicIcon;
            n2 = arraySet.contains(27) ? n : 8;
            view.setVisibility(n2);
            return;
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = super.verifyDrawable(drawable2) || drawable2 == this.mBackground;
        return bl;
    }

    public class HeaderTouchListener
    implements View.OnTouchListener {
        private Rect mAppOpsRect;
        private float mDownX;
        private float mDownY;
        private Rect mExpandButtonRect;
        private final ArrayList<Rect> mTouchRects = new ArrayList();
        private int mTouchSlop;
        private boolean mTrackGesture;

        private Rect addRectAroundView(View object) {
            object = this.getRectAroundView((View)object);
            this.mTouchRects.add((Rect)object);
            return object;
        }

        private void addWidthRect() {
            Rect rect = new Rect();
            rect.top = 0;
            rect.bottom = (int)(NotificationHeaderView.this.getResources().getDisplayMetrics().density * 32.0f);
            rect.left = 0;
            rect.right = NotificationHeaderView.this.getWidth();
            this.mTouchRects.add(rect);
        }

        private Rect getRectAroundView(View view) {
            float f = NotificationHeaderView.this.getResources().getDisplayMetrics().density * 48.0f;
            float f2 = Math.max(f, (float)view.getWidth());
            f = Math.max(f, (float)view.getHeight());
            Rect rect = new Rect();
            if (view.getVisibility() == 8) {
                view = NotificationHeaderView.this.getFirstChildNotGone();
                rect.left = (int)((float)view.getLeft() - f2 / 2.0f);
            } else {
                rect.left = (int)((float)(view.getLeft() + view.getRight()) / 2.0f - f2 / 2.0f);
            }
            rect.top = (int)((float)(view.getTop() + view.getBottom()) / 2.0f - f / 2.0f);
            rect.bottom = (int)((float)rect.top + f);
            rect.right = (int)((float)rect.left + f2);
            return rect;
        }

        private boolean isInside(float f, float f2) {
            if (NotificationHeaderView.this.mAcceptAllTouches) {
                return true;
            }
            if (NotificationHeaderView.this.mExpandOnlyOnButton) {
                return this.mExpandButtonRect.contains((int)f, (int)f2);
            }
            for (int i = 0; i < this.mTouchRects.size(); ++i) {
                if (!this.mTouchRects.get(i).contains((int)f, (int)f2)) continue;
                return true;
            }
            return false;
        }

        public void bindTouchRects() {
            this.mTouchRects.clear();
            this.addRectAroundView(NotificationHeaderView.this.mIcon);
            this.mExpandButtonRect = this.addRectAroundView(NotificationHeaderView.this.mExpandButton);
            this.mAppOpsRect = this.addRectAroundView(NotificationHeaderView.this.mAppOps);
            this.addWidthRect();
            this.mTouchSlop = ViewConfiguration.get(NotificationHeaderView.this.getContext()).getScaledTouchSlop();
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            int n = motionEvent.getActionMasked() & 255;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2 && this.mTrackGesture && (Math.abs(this.mDownX - f) > (float)this.mTouchSlop || Math.abs(this.mDownY - f2) > (float)this.mTouchSlop)) {
                        this.mTrackGesture = false;
                    }
                } else if (this.mTrackGesture) {
                    if (NotificationHeaderView.this.mAppOps.isVisibleToUser() && (this.mAppOpsRect.contains((int)f, (int)f2) || this.mAppOpsRect.contains((int)this.mDownX, (int)this.mDownY))) {
                        NotificationHeaderView.this.mAppOps.performClick();
                        return true;
                    }
                    NotificationHeaderView.this.mExpandButton.performClick();
                }
            } else {
                this.mTrackGesture = false;
                if (this.isInside(f, f2)) {
                    this.mDownX = f;
                    this.mDownY = f2;
                    this.mTrackGesture = true;
                    return true;
                }
            }
            return this.mTrackGesture;
        }
    }

}

