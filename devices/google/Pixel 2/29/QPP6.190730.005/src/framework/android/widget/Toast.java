/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Toast {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    static final String TAG = "Toast";
    static final boolean localLOGV = false;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static INotificationManager sService;
    final Context mContext;
    @UnsupportedAppUsage
    int mDuration;
    View mNextView;
    @UnsupportedAppUsage(maxTargetSdk=28)
    final TN mTN;

    public Toast(Context context) {
        this(context, null);
    }

    public Toast(Context context, Looper looper) {
        this.mContext = context;
        this.mTN = new TN(context.getPackageName(), looper);
        this.mTN.mY = context.getResources().getDimensionPixelSize(17105478);
        this.mTN.mGravity = context.getResources().getInteger(17694899);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private static INotificationManager getService() {
        INotificationManager iNotificationManager = sService;
        if (iNotificationManager != null) {
            return iNotificationManager;
        }
        sService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        return sService;
    }

    public static Toast makeText(Context context, int n, int n2) throws Resources.NotFoundException {
        return Toast.makeText(context, context.getResources().getText(n), n2);
    }

    public static Toast makeText(Context object, Looper object2, CharSequence charSequence, int n) {
        object2 = new Toast((Context)object, (Looper)object2);
        object = ((LayoutInflater)((Context)object).getSystemService("layout_inflater")).inflate(17367327, null);
        ((TextView)((View)object).findViewById(16908299)).setText(charSequence);
        ((Toast)object2).mNextView = object;
        ((Toast)object2).mDuration = n;
        return object2;
    }

    public static Toast makeText(Context context, CharSequence charSequence, int n) {
        return Toast.makeText(context, null, charSequence, n);
    }

    public void cancel() {
        this.mTN.cancel();
    }

    public int getDuration() {
        return this.mDuration;
    }

    public int getGravity() {
        return this.mTN.mGravity;
    }

    public float getHorizontalMargin() {
        return this.mTN.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        return this.mTN.mVerticalMargin;
    }

    public View getView() {
        return this.mNextView;
    }

    @UnsupportedAppUsage
    public WindowManager.LayoutParams getWindowParams() {
        return this.mTN.mParams;
    }

    public int getXOffset() {
        return this.mTN.mX;
    }

    public int getYOffset() {
        return this.mTN.mY;
    }

    public void setDuration(int n) {
        this.mDuration = n;
        this.mTN.mDuration = n;
    }

    public void setGravity(int n, int n2, int n3) {
        TN tN = this.mTN;
        tN.mGravity = n;
        tN.mX = n2;
        tN.mY = n3;
    }

    public void setMargin(float f, float f2) {
        TN tN = this.mTN;
        tN.mHorizontalMargin = f;
        tN.mVerticalMargin = f2;
    }

    public void setText(int n) {
        this.setText(this.mContext.getText(n));
    }

    public void setText(CharSequence charSequence) {
        View view = this.mNextView;
        if (view != null) {
            if ((view = (TextView)view.findViewById(16908299)) != null) {
                ((TextView)view).setText(charSequence);
                return;
            }
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }

    public void setView(View view) {
        this.mNextView = view;
    }

    public void show() {
        if (this.mNextView != null) {
            INotificationManager iNotificationManager = Toast.getService();
            String string2 = this.mContext.getOpPackageName();
            TN tN = this.mTN;
            tN.mNextView = this.mNextView;
            int n = this.mContext.getDisplayId();
            try {
                iNotificationManager.enqueueToast(string2, tN, this.mDuration, n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new RuntimeException("setView must have been called");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Duration {
    }

    private static class TN
    extends ITransientNotification.Stub {
        private static final int CANCEL = 2;
        private static final int HIDE = 1;
        static final long LONG_DURATION_TIMEOUT = 7000L;
        static final long SHORT_DURATION_TIMEOUT = 4000L;
        private static final int SHOW = 0;
        int mDuration;
        @UnsupportedAppUsage(maxTargetSdk=28)
        int mGravity;
        final Handler mHandler;
        float mHorizontalMargin;
        @UnsupportedAppUsage(maxTargetSdk=28)
        View mNextView;
        String mPackageName;
        @UnsupportedAppUsage(maxTargetSdk=28)
        private final WindowManager.LayoutParams mParams;
        float mVerticalMargin;
        @UnsupportedAppUsage(maxTargetSdk=28)
        View mView;
        WindowManager mWM;
        int mX;
        @UnsupportedAppUsage(maxTargetSdk=28)
        int mY;

        TN(String object, Looper looper) {
            WindowManager.LayoutParams layoutParams = this.mParams = new WindowManager.LayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -2;
            layoutParams.format = -3;
            layoutParams.windowAnimations = 16973828;
            layoutParams.type = 2005;
            layoutParams.setTitle(Toast.TAG);
            layoutParams.flags = 152;
            this.mPackageName = object;
            object = looper;
            if (looper == null && (object = Looper.myLooper()) == null) {
                throw new RuntimeException("Can't toast on a thread that has not called Looper.prepare()");
            }
            this.mHandler = new Handler((Looper)object, null){

                @Override
                public void handleMessage(Message object) {
                    int n = ((Message)object).what;
                    if (n != 0) {
                        if (n != 1) {
                            if (n == 2) {
                                TN.this.handleHide();
                                TN.this.mNextView = null;
                                try {
                                    Toast.getService().cancelToast(TN.this.mPackageName, TN.this);
                                }
                                catch (RemoteException remoteException) {}
                            }
                        } else {
                            TN.this.handleHide();
                            TN.this.mNextView = null;
                        }
                    } else {
                        object = (IBinder)((Message)object).obj;
                        TN.this.handleShow((IBinder)object);
                    }
                }
            };
        }

        private void trySendAccessibilityEvent() {
            AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mView.getContext());
            if (!accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(64);
            accessibilityEvent.setClassName(this.getClass().getName());
            accessibilityEvent.setPackageName(this.mView.getContext().getPackageName());
            this.mView.dispatchPopulateAccessibilityEvent(accessibilityEvent);
            accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        }

        public void cancel() {
            this.mHandler.obtainMessage(2).sendToTarget();
        }

        @UnsupportedAppUsage
        public void handleHide() {
            View view = this.mView;
            if (view != null) {
                if (view.getParent() != null) {
                    this.mWM.removeViewImmediate(this.mView);
                }
                try {
                    Toast.getService().finishToken(this.mPackageName, this);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                this.mView = null;
            }
        }

        public void handleShow(IBinder iBinder) {
            if (!this.mHandler.hasMessages(2) && !this.mHandler.hasMessages(1)) {
                if (this.mView != this.mNextView) {
                    this.handleHide();
                    this.mView = this.mNextView;
                    Context context = this.mView.getContext().getApplicationContext();
                    String string2 = this.mView.getContext().getOpPackageName();
                    Object object = context;
                    if (context == null) {
                        object = this.mView.getContext();
                    }
                    this.mWM = (WindowManager)((Context)object).getSystemService("window");
                    object = this.mView.getContext().getResources().getConfiguration();
                    int n = Gravity.getAbsoluteGravity(this.mGravity, ((Configuration)object).getLayoutDirection());
                    object = this.mParams;
                    ((WindowManager.LayoutParams)object).gravity = n;
                    if ((n & 7) == 7) {
                        ((WindowManager.LayoutParams)object).horizontalWeight = 1.0f;
                    }
                    if ((n & 112) == 112) {
                        this.mParams.verticalWeight = 1.0f;
                    }
                    object = this.mParams;
                    ((WindowManager.LayoutParams)object).x = this.mX;
                    ((WindowManager.LayoutParams)object).y = this.mY;
                    ((WindowManager.LayoutParams)object).verticalMargin = this.mVerticalMargin;
                    ((WindowManager.LayoutParams)object).horizontalMargin = this.mHorizontalMargin;
                    ((WindowManager.LayoutParams)object).packageName = string2;
                    long l = this.mDuration == 1 ? 7000L : 4000L;
                    ((WindowManager.LayoutParams)object).hideTimeoutMilliseconds = l;
                    this.mParams.token = iBinder;
                    if (this.mView.getParent() != null) {
                        this.mWM.removeView(this.mView);
                    }
                    try {
                        this.mWM.addView(this.mView, this.mParams);
                        this.trySendAccessibilityEvent();
                    }
                    catch (WindowManager.BadTokenException badTokenException) {
                        // empty catch block
                    }
                }
                return;
            }
        }

        @Override
        public void hide() {
            this.mHandler.obtainMessage(1).sendToTarget();
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        @Override
        public void show(IBinder iBinder) {
            this.mHandler.obtainMessage(0, iBinder).sendToTarget();
        }

    }

}

