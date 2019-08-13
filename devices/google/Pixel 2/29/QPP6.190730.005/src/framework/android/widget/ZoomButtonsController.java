/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ZoomControls;

@Deprecated
public class ZoomButtonsController
implements View.OnTouchListener {
    private static final int MSG_DISMISS_ZOOM_CONTROLS = 3;
    private static final int MSG_POST_CONFIGURATION_CHANGED = 2;
    private static final int MSG_POST_SET_VISIBLE = 4;
    private static final String TAG = "ZoomButtonsController";
    private static final int ZOOM_CONTROLS_TIMEOUT = (int)ViewConfiguration.getZoomControlsTimeout();
    private static final int ZOOM_CONTROLS_TOUCH_PADDING = 20;
    private boolean mAutoDismissControls = true;
    private OnZoomListener mCallback;
    private final IntentFilter mConfigurationChangedFilter = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
    private final BroadcastReceiver mConfigurationChangedReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!ZoomButtonsController.this.mIsVisible) {
                return;
            }
            ZoomButtonsController.this.mHandler.removeMessages(2);
            ZoomButtonsController.this.mHandler.sendEmptyMessage(2);
        }
    };
    private final FrameLayout mContainer;
    private WindowManager.LayoutParams mContainerLayoutParams;
    private final int[] mContainerRawLocation = new int[2];
    private final Context mContext;
    private ZoomControls mControls;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        if (ZoomButtonsController.this.mOwnerView.getWindowToken() == null) {
                            Log.e(ZoomButtonsController.TAG, "Cannot make the zoom controller visible if the owner view is not attached to a window.");
                        } else {
                            ZoomButtonsController.this.setVisible(true);
                        }
                    }
                } else {
                    ZoomButtonsController.this.setVisible(false);
                }
            } else {
                ZoomButtonsController.this.onPostConfigurationChanged();
            }
        }
    };
    private boolean mIsVisible;
    private final View mOwnerView;
    private final int[] mOwnerViewRawLocation = new int[2];
    private Runnable mPostedVisibleInitializer;
    private boolean mReleaseTouchListenerOnUp;
    private final int[] mTempIntArray = new int[2];
    private final Rect mTempRect = new Rect();
    private int mTouchPaddingScaledSq;
    private View mTouchTargetView;
    private final int[] mTouchTargetWindowLocation = new int[2];
    private final WindowManager mWindowManager;

    public ZoomButtonsController(View view) {
        this.mContext = view.getContext();
        this.mWindowManager = (WindowManager)this.mContext.getSystemService("window");
        this.mOwnerView = view;
        int n = this.mTouchPaddingScaledSq = (int)(this.mContext.getResources().getDisplayMetrics().density * 20.0f);
        this.mTouchPaddingScaledSq = n * n;
        this.mContainer = this.createContainer();
    }

    private FrameLayout createContainer() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2);
        layoutParams.gravity = 8388659;
        layoutParams.flags = 131608;
        layoutParams.height = -2;
        layoutParams.width = -1;
        layoutParams.type = 1000;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 16974607;
        this.mContainerLayoutParams = layoutParams;
        Container container = new Container(this.mContext);
        container.setLayoutParams(layoutParams);
        container.setMeasureAllChildren(true);
        ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(17367343, (ViewGroup)container);
        this.mControls = (ZoomControls)container.findViewById(16909575);
        this.mControls.setOnZoomInClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ZoomButtonsController.this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
                if (ZoomButtonsController.this.mCallback != null) {
                    ZoomButtonsController.this.mCallback.onZoom(true);
                }
            }
        });
        this.mControls.setOnZoomOutClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ZoomButtonsController.this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
                if (ZoomButtonsController.this.mCallback != null) {
                    ZoomButtonsController.this.mCallback.onZoom(false);
                }
            }
        });
        return container;
    }

    private void dismissControlsDelayed(int n) {
        if (this.mAutoDismissControls) {
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessageDelayed(3, n);
        }
    }

    private View findViewForTouch(int n, int n2) {
        int[] arrn = this.mContainerRawLocation;
        int n3 = n - arrn[0];
        int n4 = n2 - arrn[1];
        Rect rect = this.mTempRect;
        arrn = null;
        n2 = Integer.MAX_VALUE;
        for (n = this.mContainer.getChildCount() - 1; n >= 0; --n) {
            int n5;
            Object object;
            View view = this.mContainer.getChildAt(n);
            if (view.getVisibility() != 0) {
                object = arrn;
                n5 = n2;
            } else {
                view.getHitRect(rect);
                if (rect.contains(n3, n4)) {
                    return view;
                }
                n5 = n3 >= rect.left && n3 <= rect.right ? 0 : Math.min(Math.abs(rect.left - n3), Math.abs(n3 - rect.right));
                int n6 = n4 >= rect.top && n4 <= rect.bottom ? 0 : Math.min(Math.abs(rect.top - n4), Math.abs(n4 - rect.bottom));
                n6 = n5 * n5 + n6 * n6;
                object = arrn;
                n5 = n2;
                if (n6 < this.mTouchPaddingScaledSq) {
                    object = arrn;
                    n5 = n2;
                    if (n6 < n2) {
                        object = view;
                        n5 = n6;
                    }
                }
            }
            arrn = object;
            n2 = n5;
        }
        return arrn;
    }

    private boolean isInterestingKey(int n) {
        if (n != 4 && n != 66) {
            switch (n) {
                default: {
                    return false;
                }
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
            }
        }
        return true;
    }

    private boolean onContainerKey(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        if (this.isInterestingKey(n)) {
            if (n == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    Object object = this.mOwnerView;
                    if (object != null && (object = ((View)object).getKeyDispatcherState()) != null) {
                        ((KeyEvent.DispatcherState)object).startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.setVisible(false);
                    return true;
                }
            } else {
                this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
            }
            return false;
        }
        ViewRootImpl viewRootImpl = this.mOwnerView.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchInputEvent(keyEvent);
        }
        return true;
    }

    private void onPostConfigurationChanged() {
        this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        this.refreshPositioningVariables();
    }

    private void refreshPositioningVariables() {
        if (this.mOwnerView.getWindowToken() == null) {
            return;
        }
        int n = this.mOwnerView.getHeight();
        int n2 = this.mOwnerView.getWidth();
        this.mOwnerView.getLocationOnScreen(this.mOwnerViewRawLocation);
        Object object = this.mContainerRawLocation;
        int[] arrn = this.mOwnerViewRawLocation;
        object[0] = arrn[0];
        object[1] = arrn[1] + (n -= this.mContainer.getHeight());
        arrn = this.mTempIntArray;
        this.mOwnerView.getLocationInWindow(arrn);
        object = this.mContainerLayoutParams;
        object.x = arrn[0];
        object.width = n2;
        object.y = arrn[1] + n;
        if (this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mContainer, (ViewGroup.LayoutParams)object);
        }
    }

    private void setTouchTargetView(View view) {
        this.mTouchTargetView = view;
        if (view != null) {
            view.getLocationInWindow(this.mTouchTargetWindowLocation);
        }
    }

    public ViewGroup getContainer() {
        return this.mContainer;
    }

    public View getZoomControls() {
        return this.mControls;
    }

    public boolean isAutoDismissed() {
        return this.mAutoDismissControls;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    @Override
    public boolean onTouch(View view, MotionEvent arrn) {
        int n = arrn.getAction();
        if (arrn.getPointerCount() > 1) {
            return false;
        }
        if (this.mReleaseTouchListenerOnUp) {
            if (n == 1 || n == 3) {
                this.mOwnerView.setOnTouchListener(null);
                this.setTouchTargetView(null);
                this.mReleaseTouchListenerOnUp = false;
            }
            return true;
        }
        this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        view = this.mTouchTargetView;
        if (n != 0) {
            if (n == 1 || n == 3) {
                this.setTouchTargetView(null);
            }
        } else {
            view = this.findViewForTouch((int)arrn.getRawX(), (int)arrn.getRawY());
            this.setTouchTargetView(view);
        }
        if (view != null) {
            int[] arrn2 = this.mContainerRawLocation;
            n = arrn2[0];
            Object object = this.mTouchTargetWindowLocation;
            int n2 = object[0];
            int n3 = arrn2[1];
            int n4 = object[1];
            object = MotionEvent.obtain((MotionEvent)arrn);
            arrn = this.mOwnerViewRawLocation;
            ((MotionEvent)object).offsetLocation(arrn[0] - (n + n2), arrn[1] - (n3 + n4));
            float f = ((MotionEvent)object).getX();
            float f2 = ((MotionEvent)object).getY();
            if (f < 0.0f && f > -20.0f) {
                ((MotionEvent)object).offsetLocation(-f, 0.0f);
            }
            if (f2 < 0.0f && f2 > -20.0f) {
                ((MotionEvent)object).offsetLocation(0.0f, -f2);
            }
            boolean bl = view.dispatchTouchEvent((MotionEvent)object);
            ((MotionEvent)object).recycle();
            return bl;
        }
        return false;
    }

    public void setAutoDismissed(boolean bl) {
        if (this.mAutoDismissControls == bl) {
            return;
        }
        this.mAutoDismissControls = bl;
    }

    public void setFocusable(boolean bl) {
        int n = this.mContainerLayoutParams.flags;
        if (bl) {
            WindowManager.LayoutParams layoutParams = this.mContainerLayoutParams;
            layoutParams.flags &= -9;
        } else {
            WindowManager.LayoutParams layoutParams = this.mContainerLayoutParams;
            layoutParams.flags |= 8;
        }
        if (this.mContainerLayoutParams.flags != n && this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mContainer, this.mContainerLayoutParams);
        }
    }

    public void setOnZoomListener(OnZoomListener onZoomListener) {
        this.mCallback = onZoomListener;
    }

    public void setVisible(boolean bl) {
        if (bl) {
            if (this.mOwnerView.getWindowToken() == null) {
                if (!this.mHandler.hasMessages(4)) {
                    this.mHandler.sendEmptyMessage(4);
                }
                return;
            }
            this.dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        }
        if (this.mIsVisible == bl) {
            return;
        }
        this.mIsVisible = bl;
        if (bl) {
            if (this.mContainerLayoutParams.token == null) {
                this.mContainerLayoutParams.token = this.mOwnerView.getWindowToken();
            }
            this.mWindowManager.addView(this.mContainer, this.mContainerLayoutParams);
            if (this.mPostedVisibleInitializer == null) {
                this.mPostedVisibleInitializer = new Runnable(){

                    @Override
                    public void run() {
                        ZoomButtonsController.this.refreshPositioningVariables();
                        if (ZoomButtonsController.this.mCallback != null) {
                            ZoomButtonsController.this.mCallback.onVisibilityChanged(true);
                        }
                    }
                };
            }
            this.mHandler.post(this.mPostedVisibleInitializer);
            this.mContext.registerReceiver(this.mConfigurationChangedReceiver, this.mConfigurationChangedFilter);
            this.mOwnerView.setOnTouchListener(this);
            this.mReleaseTouchListenerOnUp = false;
        } else {
            if (this.mTouchTargetView != null) {
                this.mReleaseTouchListenerOnUp = true;
            } else {
                this.mOwnerView.setOnTouchListener(null);
            }
            this.mContext.unregisterReceiver(this.mConfigurationChangedReceiver);
            this.mWindowManager.removeViewImmediate(this.mContainer);
            this.mHandler.removeCallbacks(this.mPostedVisibleInitializer);
            OnZoomListener onZoomListener = this.mCallback;
            if (onZoomListener != null) {
                onZoomListener.onVisibilityChanged(false);
            }
        }
    }

    public void setZoomInEnabled(boolean bl) {
        this.mControls.setIsZoomInEnabled(bl);
    }

    public void setZoomOutEnabled(boolean bl) {
        this.mControls.setIsZoomOutEnabled(bl);
    }

    public void setZoomSpeed(long l) {
        this.mControls.setZoomSpeed(l);
    }

    private class Container
    extends FrameLayout {
        public Container(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            boolean bl = ZoomButtonsController.this.onContainerKey(keyEvent) ? true : super.dispatchKeyEvent(keyEvent);
            return bl;
        }
    }

    public static interface OnZoomListener {
        public void onVisibilityChanged(boolean var1);

        public void onZoom(boolean var1);
    }

}

