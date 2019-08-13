/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.transition.Transition;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.autofill.Helper;
import android.view.autofill.IAutofillWindowPresenter;
import android.view.autofill._$$Lambda$AutofillPopupWindow$DnLs9aVkSgQ89oSTe4P9EweBBks;
import android.widget.PopupWindow;

public class AutofillPopupWindow
extends PopupWindow {
    private static final String TAG = "AutofillPopupWindow";
    private boolean mFullScreen;
    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            AutofillPopupWindow.this.dismiss();
        }
    };
    private WindowManager.LayoutParams mWindowLayoutParams;
    private final WindowPresenter mWindowPresenter;

    public AutofillPopupWindow(IAutofillWindowPresenter iAutofillWindowPresenter) {
        this.mWindowPresenter = new WindowPresenter(iAutofillWindowPresenter);
        this.setTouchModal(false);
        this.setOutsideTouchable(true);
        this.setInputMethodMode(2);
        this.setFocusable(true);
    }

    static /* synthetic */ void lambda$update$0(int[] arrn, View view, int n, int n2, int n3, int n4) {
        arrn[0] = arrn[0] - (n - n3);
        arrn[1] = arrn[1] - (n2 - n4);
    }

    @Override
    protected void attachToAnchor(View view, int n, int n2, int n3) {
        super.attachToAnchor(view, n, n2, n3);
        view.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
    }

    @Override
    protected void detachFromAnchor() {
        View view = this.getAnchor();
        if (view != null) {
            view.removeOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        }
        super.detachFromAnchor();
    }

    @Override
    public void dismiss() {
        if (this.isShowing() && !this.isTransitioningToDismiss()) {
            this.setShowing(false);
            this.setTransitioningToDismiss(true);
            this.mWindowPresenter.hide(this.getTransitionEpicenter());
            this.detachFromAnchor();
            if (this.getOnDismissListener() != null) {
                this.getOnDismissListener().onDismiss();
            }
            return;
        }
    }

    @Override
    protected boolean findDropDownPosition(View view, WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (this.mFullScreen) {
            layoutParams.x = n;
            layoutParams.y = n2;
            layoutParams.width = n3;
            layoutParams.height = n4;
            layoutParams.gravity = n5;
            return false;
        }
        return super.findDropDownPosition(view, layoutParams, n, n2, n3, n4, n5, bl);
    }

    @Override
    public int getAnimationStyle() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public Drawable getBackground() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public View getContentView() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    protected WindowManager.LayoutParams getDecorViewLayoutParams() {
        return this.mWindowLayoutParams;
    }

    @Override
    public float getElevation() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public Transition getEnterTransition() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public Transition getExitTransition() {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    protected boolean hasContentView() {
        return true;
    }

    @Override
    protected boolean hasDecorView() {
        return true;
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void setContentView(View view) {
        if (view == null) {
            return;
        }
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void setElevation(float f) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void setEnterTransition(Transition transition2) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void setExitTransition(Transition transition2) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        throw new IllegalStateException("You can't call this!");
    }

    @Override
    public void showAsDropDown(View view, int n, int n2, int n3) {
        Object object;
        if (Helper.sVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("showAsDropDown(): anchor=");
            ((StringBuilder)object).append(view);
            ((StringBuilder)object).append(", xoff=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", yoff=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(", isShowing(): ");
            ((StringBuilder)object).append(this.isShowing());
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        if (this.isShowing()) {
            return;
        }
        this.setShowing(true);
        this.setDropDown(true);
        this.attachToAnchor(view, n, n2, n3);
        this.mWindowLayoutParams = object = this.createPopupLayoutParams(view.getWindowToken());
        this.updateAboveAnchor(this.findDropDownPosition(view, (WindowManager.LayoutParams)object, n, n2, ((WindowManager.LayoutParams)object).width, ((WindowManager.LayoutParams)object).height, n3, this.getAllowScrollingAnchorParent()));
        ((WindowManager.LayoutParams)object).accessibilityIdOfAnchor = view.getAccessibilityViewId();
        ((WindowManager.LayoutParams)object).packageName = view.getContext().getPackageName();
        this.mWindowPresenter.show((WindowManager.LayoutParams)object, this.getTransitionEpicenter(), this.isLayoutInsetDecor(), view.getLayoutDirection());
    }

    public void update(final View view, int n, int n2, int n3, int n4, Rect parcelable) {
        boolean bl = n3 == -1;
        this.mFullScreen = bl;
        int n5 = this.mFullScreen ? 2008 : 1005;
        this.setWindowLayoutType(n5);
        if (this.mFullScreen) {
            n5 = 0;
            n = 0;
            parcelable = new Point();
            view.getContext().getDisplay().getSize((Point)parcelable);
            n3 = ((Point)parcelable).x;
            if (n4 != -1) {
                n = ((Point)parcelable).y - n4;
            }
            n2 = n;
        } else if (parcelable != null) {
            final int[] arrn = new int[]{parcelable.left, parcelable.top};
            View view2 = new View(view.getContext()){

                @Override
                public void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.addOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override
                public int getAccessibilityViewId() {
                    return view.getAccessibilityViewId();
                }

                @Override
                public IBinder getApplicationWindowToken() {
                    return view.getApplicationWindowToken();
                }

                @Override
                public int getLayoutDirection() {
                    return view.getLayoutDirection();
                }

                @Override
                public void getLocationOnScreen(int[] arrn3) {
                    int[] arrn2 = arrn;
                    arrn3[0] = arrn2[0];
                    arrn3[1] = arrn2[1];
                }

                @Override
                public View getRootView() {
                    return view.getRootView();
                }

                @Override
                public ViewTreeObserver getViewTreeObserver() {
                    return view.getViewTreeObserver();
                }

                @Override
                public void getWindowDisplayFrame(Rect rect) {
                    view.getWindowDisplayFrame(rect);
                }

                @Override
                public IBinder getWindowToken() {
                    return view.getWindowToken();
                }

                @Override
                public boolean isAttachedToWindow() {
                    return view.isAttachedToWindow();
                }

                @Override
                public void removeOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
                    view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
                }

                @Override
                public boolean requestRectangleOnScreen(Rect rect, boolean bl) {
                    return view.requestRectangleOnScreen(rect, bl);
                }
            };
            view2.setLeftTopRightBottom(parcelable.left, parcelable.top, parcelable.right, parcelable.bottom);
            view2.setScrollX(view.getScrollX());
            view2.setScrollY(view.getScrollY());
            view.setOnScrollChangeListener(new _$$Lambda$AutofillPopupWindow$DnLs9aVkSgQ89oSTe4P9EweBBks(arrn));
            view2.setWillNotDraw(true);
            view = view2;
            n5 = n;
        } else {
            n5 = n;
        }
        if (!this.mFullScreen) {
            this.setAnimationStyle(-1);
        } else if (n4 == -1) {
            this.setAnimationStyle(0);
        } else {
            this.setAnimationStyle(16974609);
        }
        if (!this.isShowing()) {
            this.setWidth(n3);
            this.setHeight(n4);
            this.showAsDropDown(view, n5, n2);
        } else {
            this.update(view, n5, n2, n3, n4);
        }
    }

    @Override
    protected void update(View view, WindowManager.LayoutParams layoutParams) {
        int n = view != null ? view.getLayoutDirection() : 3;
        this.mWindowPresenter.show(layoutParams, this.getTransitionEpicenter(), this.isLayoutInsetDecor(), n);
    }

    private class WindowPresenter {
        final IAutofillWindowPresenter mPresenter;

        WindowPresenter(IAutofillWindowPresenter iAutofillWindowPresenter) {
            this.mPresenter = iAutofillWindowPresenter;
        }

        void hide(Rect rect) {
            try {
                this.mPresenter.hide(rect);
            }
            catch (RemoteException remoteException) {
                Log.w(AutofillPopupWindow.TAG, "Error hiding fill window", remoteException);
                remoteException.rethrowFromSystemServer();
            }
        }

        void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean bl, int n) {
            try {
                this.mPresenter.show(layoutParams, rect, bl, n);
            }
            catch (RemoteException remoteException) {
                Log.w(AutofillPopupWindow.TAG, "Error showing fill window", remoteException);
                remoteException.rethrowFromSystemServer();
            }
        }
    }

}

