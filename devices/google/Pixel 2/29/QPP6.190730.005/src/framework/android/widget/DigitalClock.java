/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.Calendar;

@Deprecated
public class DigitalClock
extends TextView {
    Calendar mCalendar;
    String mFormat;
    private FormatChangeObserver mFormatChangeObserver;
    private Handler mHandler;
    private Runnable mTicker;
    private boolean mTickerStopped = false;

    public DigitalClock(Context context) {
        super(context);
        this.initClock();
    }

    public DigitalClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initClock();
    }

    private void initClock() {
        if (this.mCalendar == null) {
            this.mCalendar = Calendar.getInstance();
        }
    }

    private void setFormat() {
        this.mFormat = DateFormat.getTimeFormatString(this.getContext());
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return DigitalClock.class.getName();
    }

    @Override
    protected void onAttachedToWindow() {
        this.mTickerStopped = false;
        super.onAttachedToWindow();
        this.mFormatChangeObserver = new FormatChangeObserver();
        this.getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.mFormatChangeObserver);
        this.setFormat();
        this.mHandler = new Handler();
        this.mTicker = new Runnable(){

            @Override
            public void run() {
                if (DigitalClock.this.mTickerStopped) {
                    return;
                }
                DigitalClock.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
                DigitalClock digitalClock = DigitalClock.this;
                digitalClock.setText(DateFormat.format((CharSequence)digitalClock.mFormat, DigitalClock.this.mCalendar));
                DigitalClock.this.invalidate();
                long l = SystemClock.uptimeMillis();
                DigitalClock.this.mHandler.postAtTime(DigitalClock.this.mTicker, 1000L - l % 1000L + l);
            }
        };
        this.mTicker.run();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTickerStopped = true;
        this.getContext().getContentResolver().unregisterContentObserver(this.mFormatChangeObserver);
    }

    private class FormatChangeObserver
    extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean bl) {
            DigitalClock.this.setFormat();
        }
    }

}

