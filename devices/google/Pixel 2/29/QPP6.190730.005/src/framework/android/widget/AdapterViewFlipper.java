/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

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
import android.widget.Adapter;
import android.widget.AdapterViewAnimator;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
public class AdapterViewFlipper
extends AdapterViewAnimator {
    private static final int DEFAULT_INTERVAL = 10000;
    private static final boolean LOGD = false;
    private static final String TAG = "ViewFlipper";
    private boolean mAdvancedByHost = false;
    private boolean mAutoStart = false;
    private int mFlipInterval = 10000;
    private final Runnable mFlipRunnable = new Runnable(){

        @Override
        public void run() {
            if (AdapterViewFlipper.this.mRunning) {
                AdapterViewFlipper.this.showNext();
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            object = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(object)) {
                AdapterViewFlipper.this.mUserPresent = false;
                AdapterViewFlipper.this.updateRunning();
            } else if ("android.intent.action.USER_PRESENT".equals(object)) {
                AdapterViewFlipper.this.mUserPresent = true;
                AdapterViewFlipper.this.updateRunning(false);
            }
        }
    };
    private boolean mRunning = false;
    private boolean mStarted = false;
    private boolean mUserPresent = true;
    private boolean mVisible = false;

    public AdapterViewFlipper(Context context) {
        super(context);
    }

    public AdapterViewFlipper(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterViewFlipper(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AdapterViewFlipper(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AdapterViewFlipper, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.AdapterViewFlipper, attributeSet, typedArray, n, n2);
        this.mFlipInterval = typedArray.getInt(0, 10000);
        this.mAutoStart = typedArray.getBoolean(1, false);
        this.mLoopViews = true;
        typedArray.recycle();
    }

    private void updateRunning() {
        this.updateRunning(true);
    }

    private void updateRunning(boolean bl) {
        boolean bl2 = !this.mAdvancedByHost && this.mVisible && this.mStarted && this.mUserPresent && this.mAdapter != null;
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
    public void fyiWillBeAdvancedByHostKThx() {
        this.mAdvancedByHost = true;
        this.updateRunning(false);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AdapterViewFlipper.class.getName();
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

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.updateRunning();
    }

    public void setAutoStart(boolean bl) {
        this.mAutoStart = bl;
    }

    public void setFlipInterval(int n) {
        this.mFlipInterval = n;
    }

    @RemotableViewMethod
    @Override
    public void showNext() {
        if (this.mRunning) {
            this.removeCallbacks(this.mFlipRunnable);
            this.postDelayed(this.mFlipRunnable, this.mFlipInterval);
        }
        super.showNext();
    }

    @RemotableViewMethod
    @Override
    public void showPrevious() {
        if (this.mRunning) {
            this.removeCallbacks(this.mFlipRunnable);
            this.postDelayed(this.mFlipRunnable, this.mFlipInterval);
        }
        super.showPrevious();
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

