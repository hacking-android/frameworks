/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerImpl;

public class Presentation
extends Dialog {
    private static final int MSG_CANCEL = 1;
    private static final String TAG = "Presentation";
    private final Display mDisplay;
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener(){

        @Override
        public void onDisplayAdded(int n) {
        }

        @Override
        public void onDisplayChanged(int n) {
            if (n == Presentation.this.mDisplay.getDisplayId()) {
                Presentation.this.handleDisplayChanged();
            }
        }

        @Override
        public void onDisplayRemoved(int n) {
            if (n == Presentation.this.mDisplay.getDisplayId()) {
                Presentation.this.handleDisplayRemoved();
            }
        }
    };
    private final DisplayManager mDisplayManager;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1) {
                Presentation.this.cancel();
            }
        }
    };
    private final IBinder mToken = new Binder();

    public Presentation(Context context, Display display) {
        this(context, display, 0);
    }

    public Presentation(Context object, Display object2, int n) {
        super(Presentation.createPresentationContext((Context)object, (Display)object2, n), n, false);
        this.mDisplay = object2;
        this.mDisplayManager = (DisplayManager)this.getContext().getSystemService("display");
        object = this.getWindow();
        object2 = ((Window)object).getAttributes();
        ((WindowManager.LayoutParams)object2).token = this.mToken;
        ((Window)object).setAttributes((WindowManager.LayoutParams)object2);
        ((Window)object).setGravity(119);
        ((Window)object).setType(2037);
        this.setCanceledOnTouchOutside(false);
    }

    @UnsupportedAppUsage
    private static Context createPresentationContext(Context object, Display object2, int n) {
        if (object != null) {
            if (object2 != null) {
                object2 = ((Context)object).createDisplayContext((Display)object2);
                int n2 = n;
                if (n == 0) {
                    TypedValue typedValue = new TypedValue();
                    ((Context)object2).getTheme().resolveAttribute(16843712, typedValue, true);
                    n2 = typedValue.resourceId;
                }
                object = (WindowManagerImpl)((Context)object).getSystemService("window");
                return new ContextThemeWrapper((Context)object2, n2, ((WindowManagerImpl)object).createPresentationWindowManager((Context)object2)){
                    final /* synthetic */ WindowManagerImpl val$displayWindowManager;
                    {
                        this.val$displayWindowManager = windowManagerImpl;
                        super(context, n);
                    }

                    @Override
                    public Object getSystemService(String string2) {
                        if ("window".equals(string2)) {
                            return this.val$displayWindowManager;
                        }
                        return super.getSystemService(string2);
                    }
                };
            }
            throw new IllegalArgumentException("display must not be null");
        }
        throw new IllegalArgumentException("outerContext must not be null");
    }

    private void handleDisplayChanged() {
        this.onDisplayChanged();
        if (!this.isConfigurationStillValid()) {
            Log.i(TAG, "Presentation is being dismissed because the display metrics have changed since it was created.");
            this.cancel();
        }
    }

    private void handleDisplayRemoved() {
        this.onDisplayRemoved();
        this.cancel();
    }

    private boolean isConfigurationStillValid() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplay.getMetrics(displayMetrics);
        return displayMetrics.equalsPhysical(this.getResources().getDisplayMetrics());
    }

    public Display getDisplay() {
        return this.mDisplay;
    }

    public Resources getResources() {
        return this.getContext().getResources();
    }

    public void onDisplayChanged() {
    }

    public void onDisplayRemoved() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
        if (!this.isConfigurationStillValid()) {
            Log.i(TAG, "Presentation is being dismissed because the display metrics have changed since it was created.");
            this.mHandler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void onStop() {
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        super.onStop();
    }

    @Override
    public void show() {
        super.show();
    }

}

