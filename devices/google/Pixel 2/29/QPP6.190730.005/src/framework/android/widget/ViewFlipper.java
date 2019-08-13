/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.RemoteViews;
import android.widget.ViewAnimator;
import com.android.internal.R;

@RemoteViews.RemoteView
public class ViewFlipper
extends ViewAnimator {
    private static final int DEFAULT_INTERVAL = 3000;
    private static final boolean LOGD = false;
    private static final String TAG = "ViewFlipper";
    private boolean mAutoStart = false;
    private int mFlipInterval = 3000;
    private final Runnable mFlipRunnable = new Runnable(){

        @Override
        public void run() {
            if (ViewFlipper.this.mRunning) {
                ViewFlipper.this.showNext();
                ViewFlipper viewFlipper = ViewFlipper.this;
                viewFlipper.postDelayed(viewFlipper.mFlipRunnable, ViewFlipper.this.mFlipInterval);
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            object = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(object)) {
                ViewFlipper.this.mUserPresent = false;
                ViewFlipper.this.updateRunning();
            } else if ("android.intent.action.USER_PRESENT".equals(object)) {
                ViewFlipper.this.mUserPresent = true;
                ViewFlipper.this.updateRunning(false);
            }
        }
    };
    private boolean mRunning = false;
    private boolean mStarted = false;
    @UnsupportedAppUsage
    private boolean mUserPresent = true;
    private boolean mVisible = false;

    public ViewFlipper(Context context) {
        super(context);
    }

    public ViewFlipper(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ViewFlipper);
        this.mFlipInterval = ((TypedArray)object).getInt(0, 3000);
        this.mAutoStart = ((TypedArray)object).getBoolean(1, false);
        ((TypedArray)object).recycle();
    }

    private void updateRunning() {
        this.updateRunning(true);
    }

    @UnsupportedAppUsage
    private void updateRunning(boolean bl) {
        boolean bl2 = this.mVisible && this.mStarted && this.mUserPresent;
        if (bl2 != this.mRunning) {
            if (bl2) {
                this.showOnly(this.mWhichChild, bl);
                this.postDelayed(this.mFlipRunnable, this.mFlipInterval);
            } else {
                this.removeCallbacks(this.mFlipRunnable);
            }
            this.mRunning = bl2;
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ViewFlipper.class.getName();
    }

    public int getFlipInterval() {
        return this.mFlipInterval;
    }

    public boolean isAutoStart() {
        return this.mAutoStart;
    }

    public boolean isFlipping() {
        return this.mStarted;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.getContext().registerReceiverAsUser(this.mReceiver, Process.myUserHandle(), intentFilter, null, this.getHandler());
        if (this.mAutoStart) {
            this.startFlipping();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        this.getContext().unregisterReceiver(this.mReceiver);
        this.updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        boolean bl = n == 0;
        this.mVisible = bl;
        this.updateRunning(false);
    }

    public void setAutoStart(boolean bl) {
        this.mAutoStart = bl;
    }

    @RemotableViewMethod
    public void setFlipInterval(int n) {
        this.mFlipInterval = n;
    }

    public void startFlipping() {
        this.mStarted = true;
        this.updateRunning();
    }

    public void stopFlipping() {
        this.mStarted = false;
        this.updateRunning();
    }

}

