/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class TextProgressBar
extends RelativeLayout
implements Chronometer.OnChronometerTickListener {
    static final int CHRONOMETER_ID = 16908308;
    static final int PROGRESSBAR_ID = 16908301;
    public static final String TAG = "TextProgressBar";
    Chronometer mChronometer = null;
    boolean mChronometerFollow = false;
    int mChronometerGravity = 0;
    int mDuration = -1;
    long mDurationBase = -1L;
    ProgressBar mProgressBar = null;

    public TextProgressBar(Context context) {
        super(context);
    }

    public TextProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TextProgressBar(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public TextProgressBar(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
        n = view.getId();
        if (n == 16908308 && view instanceof Chronometer) {
            this.mChronometer = (Chronometer)view;
            this.mChronometer.setOnChronometerTickListener(this);
            boolean bl = layoutParams.width == -2;
            this.mChronometerFollow = bl;
            this.mChronometerGravity = this.mChronometer.getGravity() & 8388615;
        } else if (n == 16908301 && view instanceof ProgressBar) {
            this.mProgressBar = (ProgressBar)view;
        }
    }

    @Override
    public void onChronometerTick(Chronometer object) {
        if (this.mProgressBar != null) {
            long l = SystemClock.elapsedRealtime();
            if (l >= this.mDurationBase) {
                this.mChronometer.stop();
            }
            int n = (int)(this.mDurationBase - l);
            this.mProgressBar.setProgress(this.mDuration - n);
            if (this.mChronometerFollow) {
                object = (RelativeLayout.LayoutParams)this.mProgressBar.getLayoutParams();
                int n2 = this.mProgressBar.getWidth() - (((RelativeLayout.LayoutParams)object).leftMargin + ((RelativeLayout.LayoutParams)object).rightMargin);
                int n3 = this.mProgressBar.getProgress() * n2 / this.mProgressBar.getMax();
                int n4 = ((RelativeLayout.LayoutParams)object).leftMargin;
                n = 0;
                int n5 = this.mChronometer.getWidth();
                int n6 = this.mChronometerGravity;
                if (n6 == 8388613) {
                    n = -n5;
                } else if (n6 == 1) {
                    n = -(n5 / 2);
                }
                n4 = n3 + n4 + n;
                n2 = n2 - ((RelativeLayout.LayoutParams)object).rightMargin - n5;
                if (n4 < ((RelativeLayout.LayoutParams)object).leftMargin) {
                    n = ((RelativeLayout.LayoutParams)object).leftMargin;
                } else {
                    n = n4;
                    if (n4 > n2) {
                        n = n2;
                    }
                }
                ((RelativeLayout.LayoutParams)this.mChronometer.getLayoutParams()).leftMargin = n;
                this.mChronometer.requestLayout();
            }
            return;
        }
        throw new RuntimeException("Expecting child ProgressBar with id 'android.R.id.progress'");
    }

    @RemotableViewMethod
    public void setDurationBase(long l) {
        Chronometer chronometer;
        this.mDurationBase = l;
        if (this.mProgressBar != null && (chronometer = this.mChronometer) != null) {
            this.mDuration = (int)(l - chronometer.getBase());
            if (this.mDuration <= 0) {
                this.mDuration = 1;
            }
            this.mProgressBar.setMax(this.mDuration);
            return;
        }
        throw new RuntimeException("Expecting child ProgressBar with id 'android.R.id.progress' and Chronometer id 'android.R.id.text1'");
    }
}

