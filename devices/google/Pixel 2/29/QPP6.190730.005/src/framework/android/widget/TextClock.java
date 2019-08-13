/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import libcore.icu.LocaleData;

@RemoteViews.RemoteView
public class TextClock
extends TextView {
    @Deprecated
    public static final CharSequence DEFAULT_FORMAT_12_HOUR = "h:mm a";
    @Deprecated
    public static final CharSequence DEFAULT_FORMAT_24_HOUR = "H:mm";
    private CharSequence mDescFormat;
    private CharSequence mDescFormat12;
    private CharSequence mDescFormat24;
    @ViewDebug.ExportedProperty
    private CharSequence mFormat;
    private CharSequence mFormat12;
    private CharSequence mFormat24;
    private ContentObserver mFormatChangeObserver;
    @ViewDebug.ExportedProperty
    private boolean mHasSeconds;
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            if (TextClock.this.mStopTicking) {
                return;
            }
            if (TextClock.this.mTimeZone == null && "android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction())) {
                object = intent.getStringExtra("time-zone");
                TextClock.this.createTime((String)object);
            } else if (!TextClock.this.mShouldRunTicker && ("android.intent.action.TIME_TICK".equals(intent.getAction()) || "android.intent.action.TIME_SET".equals(intent.getAction()))) {
                return;
            }
            TextClock.this.onTimeChanged();
        }
    };
    private boolean mRegistered;
    private boolean mShouldRunTicker;
    private boolean mShowCurrentUserTime;
    private boolean mStopTicking;
    private final Runnable mTicker = new Runnable(){

        @Override
        public void run() {
            if (TextClock.this.mStopTicking) {
                return;
            }
            TextClock.this.onTimeChanged();
            long l = SystemClock.uptimeMillis();
            TextClock.this.getHandler().postAtTime(TextClock.this.mTicker, 1000L - l % 1000L + l);
        }
    };
    private Calendar mTime;
    private String mTimeZone;

    public TextClock(Context context) {
        super(context);
        this.init();
    }

    public TextClock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextClock(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TextClock(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TextClock, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TextClock, attributeSet, typedArray, n, n2);
        try {
            this.mFormat12 = typedArray.getText(0);
            this.mFormat24 = typedArray.getText(1);
            this.mTimeZone = typedArray.getString(2);
            this.init();
            return;
        }
        finally {
            typedArray.recycle();
        }
    }

    private static CharSequence abc(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        block0 : {
            if (charSequence != null) break block0;
            charSequence = charSequence2 == null ? charSequence3 : charSequence2;
        }
        return charSequence;
    }

    private void chooseFormat() {
        boolean bl = this.is24HourModeEnabled();
        LocaleData localeData = LocaleData.get((Locale)this.getContext().getResources().getConfiguration().locale);
        if (bl) {
            this.mFormat = TextClock.abc(this.mFormat24, this.mFormat12, localeData.timeFormat_Hm);
            this.mDescFormat = TextClock.abc(this.mDescFormat24, this.mDescFormat12, this.mFormat);
        } else {
            this.mFormat = TextClock.abc(this.mFormat12, this.mFormat24, localeData.timeFormat_hm);
            this.mDescFormat = TextClock.abc(this.mDescFormat12, this.mDescFormat24, this.mFormat);
        }
        bl = this.mHasSeconds;
        this.mHasSeconds = DateFormat.hasSeconds(this.mFormat);
        if (this.mShouldRunTicker && bl != this.mHasSeconds) {
            if (bl) {
                this.getHandler().removeCallbacks(this.mTicker);
            } else {
                this.mTicker.run();
            }
        }
    }

    private void createTime(String string2) {
        this.mTime = string2 != null ? Calendar.getInstance(TimeZone.getTimeZone(string2)) : Calendar.getInstance();
    }

    private void init() {
        if (this.mFormat12 == null || this.mFormat24 == null) {
            LocaleData localeData = LocaleData.get((Locale)this.getContext().getResources().getConfiguration().locale);
            if (this.mFormat12 == null) {
                this.mFormat12 = localeData.timeFormat_hm;
            }
            if (this.mFormat24 == null) {
                this.mFormat24 = localeData.timeFormat_Hm;
            }
        }
        this.createTime(this.mTimeZone);
        this.chooseFormat();
    }

    @UnsupportedAppUsage
    private void onTimeChanged() {
        this.mTime.setTimeInMillis(System.currentTimeMillis());
        this.setText(DateFormat.format(this.mFormat, this.mTime));
        this.setContentDescription(DateFormat.format(this.mDescFormat, this.mTime));
    }

    private void registerObserver() {
        if (this.mRegistered) {
            if (this.mFormatChangeObserver == null) {
                this.mFormatChangeObserver = new FormatChangeObserver(this.getHandler());
            }
            ContentResolver contentResolver = this.getContext().getContentResolver();
            Uri uri = Settings.System.getUriFor("time_12_24");
            if (this.mShowCurrentUserTime) {
                contentResolver.registerContentObserver(uri, true, this.mFormatChangeObserver, -1);
            } else {
                contentResolver.registerContentObserver(uri, true, this.mFormatChangeObserver, UserHandle.myUserId());
            }
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.getContext().registerReceiverAsUser(this.mIntentReceiver, Process.myUserHandle(), intentFilter, null, this.getHandler());
    }

    private void unregisterObserver() {
        if (this.mFormatChangeObserver != null) {
            this.getContext().getContentResolver().unregisterContentObserver(this.mFormatChangeObserver);
        }
    }

    private void unregisterReceiver() {
        this.getContext().unregisterReceiver(this.mIntentReceiver);
    }

    public void disableClockTick() {
        this.mStopTicking = true;
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        CharSequence charSequence = this.getFormat12Hour();
        Object var3_3 = null;
        charSequence = charSequence == null ? null : charSequence.toString();
        viewHierarchyEncoder.addProperty("format12Hour", (String)charSequence);
        charSequence = this.getFormat24Hour();
        charSequence = charSequence == null ? null : charSequence.toString();
        viewHierarchyEncoder.addProperty("format24Hour", (String)charSequence);
        charSequence = this.mFormat;
        charSequence = charSequence == null ? var3_3 : charSequence.toString();
        viewHierarchyEncoder.addProperty("format", (String)charSequence);
        viewHierarchyEncoder.addProperty("hasSeconds", this.mHasSeconds);
    }

    @UnsupportedAppUsage
    public CharSequence getFormat() {
        return this.mFormat;
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat12Hour() {
        return this.mFormat12;
    }

    @ViewDebug.ExportedProperty
    public CharSequence getFormat24Hour() {
        return this.mFormat24;
    }

    public String getTimeZone() {
        return this.mTimeZone;
    }

    public boolean is24HourModeEnabled() {
        if (this.mShowCurrentUserTime) {
            return DateFormat.is24HourFormat(this.getContext(), ActivityManager.getCurrentUser());
        }
        return DateFormat.is24HourFormat(this.getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mRegistered) {
            this.mRegistered = true;
            this.registerReceiver();
            this.registerObserver();
            this.createTime(this.mTimeZone);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mRegistered) {
            this.unregisterReceiver();
            this.unregisterObserver();
            this.mRegistered = false;
        }
    }

    @Override
    public void onVisibilityAggregated(boolean bl) {
        super.onVisibilityAggregated(bl);
        if (!this.mShouldRunTicker && bl) {
            this.mShouldRunTicker = true;
            if (this.mHasSeconds) {
                this.mTicker.run();
            } else {
                this.onTimeChanged();
            }
        } else if (this.mShouldRunTicker && !bl) {
            this.mShouldRunTicker = false;
            this.getHandler().removeCallbacks(this.mTicker);
        }
    }

    public void refresh() {
        this.onTimeChanged();
        this.invalidate();
    }

    public void setContentDescriptionFormat12Hour(CharSequence charSequence) {
        this.mDescFormat12 = charSequence;
        this.chooseFormat();
        this.onTimeChanged();
    }

    public void setContentDescriptionFormat24Hour(CharSequence charSequence) {
        this.mDescFormat24 = charSequence;
        this.chooseFormat();
        this.onTimeChanged();
    }

    @RemotableViewMethod
    public void setFormat12Hour(CharSequence charSequence) {
        this.mFormat12 = charSequence;
        this.chooseFormat();
        this.onTimeChanged();
    }

    @RemotableViewMethod
    public void setFormat24Hour(CharSequence charSequence) {
        this.mFormat24 = charSequence;
        this.chooseFormat();
        this.onTimeChanged();
    }

    public void setShowCurrentUserTime(boolean bl) {
        this.mShowCurrentUserTime = bl;
        this.chooseFormat();
        this.onTimeChanged();
        this.unregisterObserver();
        this.registerObserver();
    }

    @RemotableViewMethod
    public void setTimeZone(String string2) {
        this.mTimeZone = string2;
        this.createTime(string2);
        this.onTimeChanged();
    }

    private class FormatChangeObserver
    extends ContentObserver {
        public FormatChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean bl) {
            TextClock.this.chooseFormat();
            TextClock.this.onTimeChanged();
        }

        @Override
        public void onChange(boolean bl, Uri uri) {
            TextClock.this.chooseFormat();
            TextClock.this.onTimeChanged();
        }
    }

}

