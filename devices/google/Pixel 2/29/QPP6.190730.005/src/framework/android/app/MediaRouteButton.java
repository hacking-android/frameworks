/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.MediaRouter;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.R;
import com.android.internal.app.MediaRouteDialogPresenter;

public class MediaRouteButton
extends View {
    private static final int[] ACTIVATED_STATE_SET;
    private static final int[] CHECKED_STATE_SET;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private View.OnClickListener mExtendedSettingsClickListener;
    private boolean mIsConnecting;
    private int mMinHeight;
    private int mMinWidth;
    private boolean mRemoteActive;
    private Drawable mRemoteIndicator;
    private int mRouteTypes;
    private final MediaRouter mRouter;

    static {
        CHECKED_STATE_SET = new int[]{16842912};
        ACTIVATED_STATE_SET = new int[]{16843518};
    }

    public MediaRouteButton(Context context) {
        this(context, null);
    }

    public MediaRouteButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843693);
    }

    public MediaRouteButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MediaRouteButton(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        this.mRouter = (MediaRouter)((Context)object).getSystemService("media_router");
        this.mCallback = new MediaRouterCallback();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.MediaRouteButton, n, n2);
        this.setRemoteIndicatorDrawable(((TypedArray)object).getDrawable(3));
        this.mMinWidth = ((TypedArray)object).getDimensionPixelSize(0, 0);
        this.mMinHeight = ((TypedArray)object).getDimensionPixelSize(1, 0);
        n = ((TypedArray)object).getInteger(2, 1);
        ((TypedArray)object).recycle();
        this.setClickable(true);
        this.setRouteTypes(n);
    }

    private Activity getActivity() {
        Context context = this.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        throw new IllegalStateException("The MediaRouteButton's Context is not an Activity.");
    }

    private void refreshRoute() {
        Object object = this.mRouter.getSelectedRoute();
        boolean bl = ((MediaRouter.RouteInfo)object).isDefault();
        boolean bl2 = false;
        bl = !bl && ((MediaRouter.RouteInfo)object).matchesTypes(this.mRouteTypes);
        boolean bl3 = bl2;
        if (bl) {
            bl3 = bl2;
            if (((MediaRouter.RouteInfo)object).isConnecting()) {
                bl3 = true;
            }
        }
        boolean bl4 = false;
        if (this.mRemoteActive != bl) {
            this.mRemoteActive = bl;
            bl4 = true;
        }
        if (this.mIsConnecting != bl3) {
            this.mIsConnecting = bl3;
            bl4 = true;
        }
        if (bl4) {
            this.refreshDrawableState();
        }
        if (this.mAttachedToWindow) {
            this.setEnabled(this.mRouter.isRouteAvailable(this.mRouteTypes, 1));
        }
        if ((object = this.mRemoteIndicator) != null && ((Drawable)object).getCurrent() instanceof AnimationDrawable) {
            object = (AnimationDrawable)this.mRemoteIndicator.getCurrent();
            if (this.mAttachedToWindow) {
                if ((bl4 || bl3) && !((AnimationDrawable)object).isRunning()) {
                    ((AnimationDrawable)object).start();
                }
            } else if (bl && !bl3) {
                if (((AnimationDrawable)object).isRunning()) {
                    ((AnimationDrawable)object).stop();
                }
                ((DrawableContainer)object).selectDrawable(((AnimationDrawable)object).getNumberOfFrames() - 1);
            }
        }
    }

    private void setRemoteIndicatorDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mRemoteIndicator;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            this.unscheduleDrawable(this.mRemoteIndicator);
        }
        this.mRemoteIndicator = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            drawable2.setState(this.getDrawableState());
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
        }
        this.refreshDrawableState();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    public int getRouteTypes() {
        return this.mRouteTypes;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        int n = this.mRouteTypes;
        if (n != 0) {
            this.mRouter.addCallback(n, this.mCallback, 8);
        }
        this.refreshRoute();
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        if (this.mIsConnecting) {
            MediaRouteButton.mergeDrawableStates(arrn, CHECKED_STATE_SET);
        } else if (this.mRemoteActive) {
            MediaRouteButton.mergeDrawableStates(arrn, ACTIVATED_STATE_SET);
        }
        return arrn;
    }

    @Override
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        if (this.mRouteTypes != 0) {
            this.mRouter.removeCallback(this.mCallback);
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRemoteIndicator == null) {
            return;
        }
        int n = this.getPaddingLeft();
        int n2 = this.getWidth();
        int n3 = this.getPaddingRight();
        int n4 = this.getPaddingTop();
        int n5 = this.getHeight();
        int n6 = this.getPaddingBottom();
        int n7 = this.mRemoteIndicator.getIntrinsicWidth();
        int n8 = this.mRemoteIndicator.getIntrinsicHeight();
        n2 = (n2 - n3 - n - n7) / 2 + n;
        n4 = (n5 - n6 - n4 - n8) / 2 + n4;
        this.mRemoteIndicator.setBounds(n2, n4, n2 + n7, n4 + n8);
        this.mRemoteIndicator.draw(canvas);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.getSize(n2);
        int n5 = View.MeasureSpec.getMode(n);
        int n6 = View.MeasureSpec.getMode(n2);
        int n7 = this.mMinWidth;
        Drawable drawable2 = this.mRemoteIndicator;
        n2 = 0;
        n = drawable2 != null ? drawable2.getIntrinsicWidth() + this.getPaddingLeft() + this.getPaddingRight() : 0;
        n7 = Math.max(n7, n);
        int n8 = this.mMinHeight;
        drawable2 = this.mRemoteIndicator;
        n = drawable2 != null ? drawable2.getIntrinsicHeight() + this.getPaddingTop() + this.getPaddingBottom() : n2;
        n2 = Math.max(n8, n);
        n = n5 != Integer.MIN_VALUE ? (n5 != 1073741824 ? n7 : n3) : Math.min(n3, n7);
        if (n6 != Integer.MIN_VALUE) {
            if (n6 == 1073741824) {
                n2 = n4;
            }
        } else {
            n2 = Math.min(n4, n2);
        }
        this.setMeasuredDimension(n, n2);
    }

    @Override
    public boolean performClick() {
        boolean bl = super.performClick();
        boolean bl2 = false;
        if (!bl) {
            this.playSoundEffect(0);
        }
        if (this.showDialogInternal() || bl) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public void setContentDescription(CharSequence charSequence) {
        super.setContentDescription(charSequence);
        this.setTooltipText(charSequence);
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        this.mExtendedSettingsClickListener = onClickListener;
    }

    public void setRouteTypes(int n) {
        int n2 = this.mRouteTypes;
        if (n2 != n) {
            if (this.mAttachedToWindow && n2 != 0) {
                this.mRouter.removeCallback(this.mCallback);
            }
            this.mRouteTypes = n;
            if (this.mAttachedToWindow && n != 0) {
                this.mRouter.addCallback(n, this.mCallback, 8);
            }
            this.refreshRoute();
        }
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
        }
    }

    public void showDialog() {
        this.showDialogInternal();
    }

    boolean showDialogInternal() {
        boolean bl = this.mAttachedToWindow;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (MediaRouteDialogPresenter.showDialogFragment(this.getActivity(), this.mRouteTypes, this.mExtendedSettingsClickListener) != null) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = super.verifyDrawable(drawable2) || drawable2 == this.mRemoteIndicator;
        return bl;
    }

    private final class MediaRouterCallback
    extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteGrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int n) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteSelected(MediaRouter mediaRouter, int n, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteUngrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override
        public void onRouteUnselected(MediaRouter mediaRouter, int n, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }
    }

}

