/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.TimeZone;

@RemoteViews.RemoteView
@Deprecated
public class AnalogClock
extends View {
    private boolean mAttached;
    private Time mCalendar;
    private boolean mChanged;
    @UnsupportedAppUsage
    private Drawable mDial;
    private int mDialHeight;
    private int mDialWidth;
    private float mHour;
    @UnsupportedAppUsage
    private Drawable mHourHand;
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            if (intent.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
                object = intent.getStringExtra("time-zone");
                AnalogClock.this.mCalendar = new Time(TimeZone.getTimeZone((String)object).getID());
            }
            AnalogClock.this.onTimeChanged();
            AnalogClock.this.invalidate();
        }
    };
    @UnsupportedAppUsage
    private Drawable mMinuteHand;
    private float mMinutes;

    public AnalogClock(Context context) {
        this(context, null);
    }

    public AnalogClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnalogClock(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AnalogClock(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        context.getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AnalogClock, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.AnalogClock, attributeSet, typedArray, n, n2);
        this.mDial = typedArray.getDrawable(0);
        if (this.mDial == null) {
            this.mDial = context.getDrawable(17302112);
        }
        this.mHourHand = typedArray.getDrawable(1);
        if (this.mHourHand == null) {
            this.mHourHand = context.getDrawable(17302113);
        }
        this.mMinuteHand = typedArray.getDrawable(2);
        if (this.mMinuteHand == null) {
            this.mMinuteHand = context.getDrawable(17302114);
        }
        this.mCalendar = new Time();
        this.mDialWidth = this.mDial.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
    }

    private void onTimeChanged() {
        this.mCalendar.setToNow();
        int n = this.mCalendar.hour;
        int n2 = this.mCalendar.minute;
        int n3 = this.mCalendar.second;
        this.mMinutes = (float)n2 + (float)n3 / 60.0f;
        this.mHour = (float)n + this.mMinutes / 60.0f;
        this.mChanged = true;
        this.updateContentDescription(this.mCalendar);
    }

    private void updateContentDescription(Time time) {
        this.setContentDescription(DateUtils.formatDateTime(this.mContext, time.toMillis(false), 129));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.getContext().registerReceiverAsUser(this.mIntentReceiver, Process.myUserHandle(), intentFilter, null, this.getHandler());
        }
        this.mCalendar = new Time();
        this.onTimeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.getContext().unregisterReceiver(this.mIntentReceiver);
            this.mAttached = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean bl = this.mChanged;
        if (bl) {
            this.mChanged = false;
        }
        int n = this.mRight - this.mLeft;
        int n2 = this.mBottom - this.mTop;
        int n3 = n / 2;
        int n4 = n2 / 2;
        Drawable drawable2 = this.mDial;
        int n5 = drawable2.getIntrinsicWidth();
        int n6 = drawable2.getIntrinsicHeight();
        boolean bl2 = false;
        if (n < n5 || n2 < n6) {
            bl2 = true;
            float f = Math.min((float)n / (float)n5, (float)n2 / (float)n6);
            canvas.save();
            canvas.scale(f, f, n3, n4);
        }
        if (bl) {
            drawable2.setBounds(n3 - n5 / 2, n4 - n6 / 2, n5 / 2 + n3, n6 / 2 + n4);
        }
        drawable2.draw(canvas);
        canvas.save();
        canvas.rotate(this.mHour / 12.0f * 360.0f, n3, n4);
        drawable2 = this.mHourHand;
        if (bl) {
            n5 = drawable2.getIntrinsicWidth();
            n6 = drawable2.getIntrinsicHeight();
            drawable2.setBounds(n3 - n5 / 2, n4 - n6 / 2, n5 / 2 + n3, n4 + n6 / 2);
        }
        drawable2.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate(this.mMinutes / 60.0f * 360.0f, n3, n4);
        drawable2 = this.mMinuteHand;
        if (bl) {
            n6 = drawable2.getIntrinsicWidth();
            n5 = drawable2.getIntrinsicHeight();
            drawable2.setBounds(n3 - n6 / 2, n4 - n5 / 2, n6 / 2 + n3, n4 + n5 / 2);
        }
        drawable2.draw(canvas);
        canvas.restore();
        if (bl2) {
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode(n);
        int n4 = View.MeasureSpec.getSize(n);
        int n5 = View.MeasureSpec.getMode(n2);
        int n6 = View.MeasureSpec.getSize(n2);
        float f = 1.0f;
        float f2 = 1.0f;
        float f3 = f;
        if (n3 != 0) {
            n3 = this.mDialWidth;
            f3 = f;
            if (n4 < n3) {
                f3 = (float)n4 / (float)n3;
            }
        }
        f = f2;
        if (n5 != 0) {
            n5 = this.mDialHeight;
            f = f2;
            if (n6 < n5) {
                f = (float)n6 / (float)n5;
            }
        }
        f3 = Math.min(f3, f);
        this.setMeasuredDimension(AnalogClock.resolveSizeAndState((int)((float)this.mDialWidth * f3), n, 0), AnalogClock.resolveSizeAndState((int)((float)this.mDialHeight * f3), n2, 0));
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mChanged = true;
    }

}

