/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.android.internal.util.Preconditions;
import com.android.internal.view._$$Lambda$FloatingActionMode$LU5MpPuKYDtwlFAuYhXYfzgLNLE;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.FloatingToolbar;
import java.util.Arrays;

public final class FloatingActionMode
extends ActionMode {
    private static final int MAX_HIDE_DURATION = 3000;
    private static final int MOVING_HIDE_DELAY = 50;
    private final int mBottomAllowance;
    private final ActionMode.Callback2 mCallback;
    private final Rect mContentRect;
    private final Rect mContentRectOnScreen;
    private final Context mContext;
    private final Point mDisplaySize;
    private FloatingToolbar mFloatingToolbar;
    private FloatingToolbarVisibilityHelper mFloatingToolbarVisibilityHelper;
    private final Runnable mHideOff = new Runnable(){

        @Override
        public void run() {
            if (FloatingActionMode.this.isViewStillActive()) {
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setHideRequested(false);
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };
    private final MenuBuilder mMenu;
    private final Runnable mMovingOff = new Runnable(){

        @Override
        public void run() {
            if (FloatingActionMode.this.isViewStillActive()) {
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setMoving(false);
                FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };
    private final View mOriginatingView;
    private final Rect mPreviousContentRectOnScreen;
    private final int[] mPreviousViewPositionOnScreen;
    private final Rect mPreviousViewRectOnScreen;
    private final int[] mRootViewPositionOnScreen;
    private final Rect mScreenRect;
    private final int[] mViewPositionOnScreen;
    private final Rect mViewRectOnScreen;

    public FloatingActionMode(Context context, ActionMode.Callback2 callback2, View view, FloatingToolbar floatingToolbar) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mCallback = Preconditions.checkNotNull(callback2);
        this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
        this.setType(1);
        this.mMenu.setCallback(new MenuBuilder.Callback(){

            @Override
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return FloatingActionMode.this.mCallback.onActionItemClicked(FloatingActionMode.this, menuItem);
            }

            @Override
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        });
        this.mContentRect = new Rect();
        this.mContentRectOnScreen = new Rect();
        this.mPreviousContentRectOnScreen = new Rect();
        this.mViewPositionOnScreen = new int[2];
        this.mPreviousViewPositionOnScreen = new int[2];
        this.mRootViewPositionOnScreen = new int[2];
        this.mViewRectOnScreen = new Rect();
        this.mPreviousViewRectOnScreen = new Rect();
        this.mScreenRect = new Rect();
        this.mOriginatingView = Preconditions.checkNotNull(view);
        this.mOriginatingView.getLocationOnScreen(this.mViewPositionOnScreen);
        this.mBottomAllowance = context.getResources().getDimensionPixelSize(17105081);
        this.mDisplaySize = new Point();
        this.setFloatingToolbar(Preconditions.checkNotNull(floatingToolbar));
    }

    private static boolean intersectsClosed(Rect rect, Rect rect2) {
        boolean bl = rect.left <= rect2.right && rect2.left <= rect.right && rect.top <= rect2.bottom && rect2.top <= rect.bottom;
        return bl;
    }

    private boolean isContentRectWithinBounds() {
        boolean bl;
        block0 : {
            this.mContext.getSystemService(WindowManager.class).getDefaultDisplay().getRealSize(this.mDisplaySize);
            Rect rect = this.mScreenRect;
            int n = this.mDisplaySize.x;
            int n2 = this.mDisplaySize.y;
            bl = false;
            rect.set(0, 0, n, n2);
            if (!FloatingActionMode.intersectsClosed(this.mContentRectOnScreen, this.mScreenRect) || !FloatingActionMode.intersectsClosed(this.mContentRectOnScreen, this.mViewRectOnScreen)) break block0;
            bl = true;
        }
        return bl;
    }

    private boolean isViewStillActive() {
        boolean bl = this.mOriginatingView.getWindowVisibility() == 0 && this.mOriginatingView.isShown();
        return bl;
    }

    private void repositionToolbar() {
        this.mContentRectOnScreen.set(this.mContentRect);
        Object object = this.mOriginatingView.getParent();
        if (object instanceof ViewGroup) {
            ((ViewGroup)object).getChildVisibleRect(this.mOriginatingView, this.mContentRectOnScreen, null, true);
            object = this.mContentRectOnScreen;
            int[] arrn = this.mRootViewPositionOnScreen;
            ((Rect)object).offset(arrn[0], arrn[1]);
        } else {
            Rect rect = this.mContentRectOnScreen;
            object = this.mViewPositionOnScreen;
            rect.offset(object[0], object[1]);
        }
        if (this.isContentRectWithinBounds()) {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(false);
            object = this.mContentRectOnScreen;
            ((Rect)object).set(Math.max(((Rect)object).left, this.mViewRectOnScreen.left), Math.max(this.mContentRectOnScreen.top, this.mViewRectOnScreen.top), Math.min(this.mContentRectOnScreen.right, this.mViewRectOnScreen.right), Math.min(this.mContentRectOnScreen.bottom, this.mViewRectOnScreen.bottom + this.mBottomAllowance));
            if (!this.mContentRectOnScreen.equals(this.mPreviousContentRectOnScreen)) {
                this.mOriginatingView.removeCallbacks(this.mMovingOff);
                this.mFloatingToolbarVisibilityHelper.setMoving(true);
                this.mOriginatingView.postDelayed(this.mMovingOff, 50L);
                this.mFloatingToolbar.setContentRect(this.mContentRectOnScreen);
                this.mFloatingToolbar.updateLayout();
            }
        } else {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(true);
            this.mContentRectOnScreen.setEmpty();
        }
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
        this.mPreviousContentRectOnScreen.set(this.mContentRectOnScreen);
    }

    private void reset() {
        this.mFloatingToolbar.dismiss();
        this.mFloatingToolbarVisibilityHelper.deactivate();
        this.mOriginatingView.removeCallbacks(this.mMovingOff);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
    }

    private void setFloatingToolbar(FloatingToolbar floatingToolbar) {
        this.mFloatingToolbar = floatingToolbar.setMenu(this.mMenu).setOnMenuItemClickListener(new _$$Lambda$FloatingActionMode$LU5MpPuKYDtwlFAuYhXYfzgLNLE(this));
        this.mFloatingToolbarVisibilityHelper = new FloatingToolbarVisibilityHelper(this.mFloatingToolbar);
        this.mFloatingToolbarVisibilityHelper.activate();
    }

    @Override
    public void finish() {
        this.reset();
        this.mCallback.onDestroyActionMode(this);
    }

    @Override
    public View getCustomView() {
        return null;
    }

    @Override
    public Menu getMenu() {
        return this.mMenu;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return new MenuInflater(this.mContext);
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
    public void hide(long l) {
        long l2 = l;
        if (l == -1L) {
            l2 = ViewConfiguration.getDefaultActionModeHideDuration();
        }
        l = Math.min(3000L, l2);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
        if (l <= 0L) {
            this.mHideOff.run();
        } else {
            this.mFloatingToolbarVisibilityHelper.setHideRequested(true);
            this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            this.mOriginatingView.postDelayed(this.mHideOff, l);
        }
    }

    @Override
    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
        this.invalidateContentRect();
    }

    @Override
    public void invalidateContentRect() {
        this.mCallback.onGetContentRect(this, this.mOriginatingView, this.mContentRect);
        this.repositionToolbar();
    }

    public /* synthetic */ boolean lambda$setFloatingToolbar$0$FloatingActionMode(MenuItem menuItem) {
        return this.mMenu.performItemAction(menuItem, 0);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        this.mFloatingToolbarVisibilityHelper.setWindowFocused(bl);
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
    }

    @Override
    public void setCustomView(View view) {
    }

    public void setOutsideTouchable(boolean bl, PopupWindow.OnDismissListener onDismissListener) {
        this.mFloatingToolbar.setOutsideTouchable(bl, onDismissListener);
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

    public void updateViewLocationInWindow() {
        this.mOriginatingView.getLocationOnScreen(this.mViewPositionOnScreen);
        this.mOriginatingView.getRootView().getLocationOnScreen(this.mRootViewPositionOnScreen);
        this.mOriginatingView.getGlobalVisibleRect(this.mViewRectOnScreen);
        int[] arrn = this.mViewRectOnScreen;
        int[] arrn2 = this.mRootViewPositionOnScreen;
        arrn.offset(arrn2[0], arrn2[1]);
        if (!Arrays.equals(this.mViewPositionOnScreen, this.mPreviousViewPositionOnScreen) || !this.mViewRectOnScreen.equals(this.mPreviousViewRectOnScreen)) {
            this.repositionToolbar();
            arrn2 = this.mPreviousViewPositionOnScreen;
            arrn = this.mViewPositionOnScreen;
            arrn2[0] = arrn[0];
            arrn2[1] = arrn[1];
            this.mPreviousViewRectOnScreen.set(this.mViewRectOnScreen);
        }
    }

    private static final class FloatingToolbarVisibilityHelper {
        private static final long MIN_SHOW_DURATION_FOR_MOVE_HIDE = 500L;
        private boolean mActive;
        private boolean mHideRequested;
        private long mLastShowTime;
        private boolean mMoving;
        private boolean mOutOfBounds;
        private final FloatingToolbar mToolbar;
        private boolean mWindowFocused = true;

        public FloatingToolbarVisibilityHelper(FloatingToolbar floatingToolbar) {
            this.mToolbar = Preconditions.checkNotNull(floatingToolbar);
        }

        public void activate() {
            this.mHideRequested = false;
            this.mMoving = false;
            this.mOutOfBounds = false;
            this.mWindowFocused = true;
            this.mActive = true;
        }

        public void deactivate() {
            this.mActive = false;
            this.mToolbar.dismiss();
        }

        public void setHideRequested(boolean bl) {
            this.mHideRequested = bl;
        }

        public void setMoving(boolean bl) {
            boolean bl2 = System.currentTimeMillis() - this.mLastShowTime > 500L;
            if (!bl || bl2) {
                this.mMoving = bl;
            }
        }

        public void setOutOfBounds(boolean bl) {
            this.mOutOfBounds = bl;
        }

        public void setWindowFocused(boolean bl) {
            this.mWindowFocused = bl;
        }

        public void updateToolbarVisibility() {
            if (!this.mActive) {
                return;
            }
            if (!this.mHideRequested && !this.mMoving && !this.mOutOfBounds && this.mWindowFocused) {
                this.mToolbar.show();
                this.mLastShowTime = System.currentTimeMillis();
            } else {
                this.mToolbar.hide();
            }
        }
    }

}

