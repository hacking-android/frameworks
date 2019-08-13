/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget._$$Lambda$DateTimeView$ReceiverInfo$AVLnX7U5lTcE9jLnlKKNAT1GUeI;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RemoteViews.RemoteView
public class DateTimeView
extends TextView {
    private static final int SHOW_MONTH_DAY_YEAR = 1;
    private static final int SHOW_TIME = 0;
    private static final ThreadLocal<ReceiverInfo> sReceiverInfo = new ThreadLocal();
    int mLastDisplay = -1;
    java.text.DateFormat mLastFormat;
    private String mNowText;
    private boolean mShowRelativeTime;
    Date mTime;
    long mTimeMillis;
    private long mUpdateTimeMillis;

    public DateTimeView(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public DateTimeView(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.DateTimeView, 0, 0);
        int n = ((TypedArray)object).getIndexCount();
        for (int i = 0; i < n; ++i) {
            if (((TypedArray)object).getIndex(i) != 0) continue;
            this.setShowRelativeTime(((TypedArray)object).getBoolean(i, false));
        }
        ((TypedArray)object).recycle();
    }

    private long computeNextMidnight(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.add(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    private static int dayDistance(TimeZone timeZone, long l, long l2) {
        return Time.getJulianDay(l2, timeZone.getOffset(l2) / 1000) - Time.getJulianDay(l, timeZone.getOffset(l) / 1000);
    }

    private java.text.DateFormat getTimeFormat() {
        return DateFormat.getTimeFormat(this.getContext());
    }

    public static void setReceiverHandler(Handler handler) {
        ReceiverInfo receiverInfo;
        ReceiverInfo receiverInfo2 = receiverInfo = sReceiverInfo.get();
        if (receiverInfo == null) {
            receiverInfo2 = new ReceiverInfo();
            sReceiverInfo.set(receiverInfo2);
        }
        receiverInfo2.setHandler(handler);
    }

    private void updateNowText() {
        if (!this.mShowRelativeTime) {
            return;
        }
        this.mNowText = this.getContext().getResources().getString(17040519);
    }

    private void updateRelativeTime() {
        Object object;
        int n;
        long l = System.currentTimeMillis();
        long l2 = Math.abs(l - this.mTimeMillis);
        boolean bl = l >= this.mTimeMillis;
        if (l2 < 60000L) {
            this.setText(this.mNowText);
            this.mUpdateTimeMillis = this.mTimeMillis + 60000L + 1L;
            return;
        }
        if (l2 < 3600000L) {
            int n2 = (int)(l2 / 60000L);
            object = this.getContext().getResources();
            n = bl ? 18153484 : 18153485;
            object = String.format(((Resources)object).getQuantityString(n, n2), n2);
            l = 60000L;
            n = n2;
        } else if (l2 < 86400000L) {
            int n3 = (int)(l2 / 3600000L);
            object = this.getContext().getResources();
            n = bl ? 18153480 : 18153481;
            object = String.format(((Resources)object).getQuantityString(n, n3), n3);
            l = 3600000L;
            n = n3;
        } else if (l2 < 31449600000L) {
            TimeZone timeZone = TimeZone.getDefault();
            int n4 = Math.max(Math.abs(DateTimeView.dayDistance(timeZone, this.mTimeMillis, l)), 1);
            object = this.getContext().getResources();
            n = bl ? 18153476 : 18153477;
            object = String.format(((Resources)object).getQuantityString(n, n4), n4);
            if (!bl && n4 == 1) {
                l = 86400000L;
            } else {
                this.mUpdateTimeMillis = this.computeNextMidnight(timeZone);
                l = -1L;
            }
            n = n4;
        } else {
            int n5 = (int)(l2 / 31449600000L);
            object = this.getContext().getResources();
            n = bl ? 18153488 : 18153489;
            object = String.format(((Resources)object).getQuantityString(n, n5), n5);
            l = 31449600000L;
            n = n5;
        }
        if (l != -1L) {
            this.mUpdateTimeMillis = bl ? this.mTimeMillis + (long)(n + 1) * l + 1L : this.mTimeMillis - (long)n * l + 1L;
        }
        this.setText((CharSequence)object);
    }

    void clearFormatAndUpdate() {
        this.mLastFormat = null;
        this.update();
    }

    public boolean isShowRelativeTime() {
        return this.mShowRelativeTime;
    }

    @Override
    protected void onAttachedToWindow() {
        ReceiverInfo receiverInfo;
        super.onAttachedToWindow();
        ReceiverInfo receiverInfo2 = receiverInfo = sReceiverInfo.get();
        if (receiverInfo == null) {
            receiverInfo2 = new ReceiverInfo();
            sReceiverInfo.set(receiverInfo2);
        }
        receiverInfo2.addView(this);
        if (this.mShowRelativeTime) {
            this.update();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.updateNowText();
        this.update();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ReceiverInfo receiverInfo = sReceiverInfo.get();
        if (receiverInfo != null) {
            receiverInfo.removeView(this);
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mShowRelativeTime) {
            Object object;
            long l = System.currentTimeMillis();
            long l2 = Math.abs(l - this.mTimeMillis);
            int n = l >= this.mTimeMillis ? 1 : 0;
            if (l2 < 60000L) {
                object = this.mNowText;
            } else if (l2 < 3600000L) {
                int n2 = (int)(l2 / 60000L);
                object = this.getContext().getResources();
                n = n != 0 ? 18153482 : 18153483;
                object = String.format(((Resources)object).getQuantityString(n, n2), n2);
            } else if (l2 < 86400000L) {
                int n3 = (int)(l2 / 3600000L);
                object = this.getContext().getResources();
                n = n != 0 ? 18153478 : 18153479;
                object = String.format(((Resources)object).getQuantityString(n, n3), n3);
            } else if (l2 < 31449600000L) {
                int n4 = Math.max(Math.abs(DateTimeView.dayDistance(TimeZone.getDefault(), this.mTimeMillis, l)), 1);
                object = this.getContext().getResources();
                n = n != 0 ? 18153474 : 18153475;
                object = String.format(((Resources)object).getQuantityString(n, n4), n4);
            } else {
                int n5 = (int)(l2 / 31449600000L);
                object = this.getContext().getResources();
                n = n != 0 ? 18153486 : 18153487;
                object = String.format(((Resources)object).getQuantityString(n, n5), n5);
            }
            accessibilityNodeInfo.setText((CharSequence)object);
        }
    }

    @RemotableViewMethod
    public void setShowRelativeTime(boolean bl) {
        this.mShowRelativeTime = bl;
        this.updateNowText();
        this.update();
    }

    @RemotableViewMethod
    @UnsupportedAppUsage
    public void setTime(long l) {
        Time time = new Time();
        time.set(l);
        this.mTimeMillis = time.toMillis(false);
        this.mTime = new Date(time.year - 1900, time.month, time.monthDay, time.hour, time.minute, 0);
        this.update();
    }

    @RemotableViewMethod
    @Override
    public void setVisibility(int n) {
        boolean bl = n != 8 && this.getVisibility() == 8;
        super.setVisibility(n);
        if (bl) {
            this.update();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @UnsupportedAppUsage
    void update() {
        if (this.mTime == null) return;
        if (this.getVisibility() == 8) {
            return;
        }
        if (this.mShowRelativeTime) {
            this.updateRelativeTime();
            return;
        }
        Object object = this.mTime;
        object = new Time();
        ((Time)object).set(this.mTimeMillis);
        ((Time)object).second = 0;
        ((Time)object).hour -= 12;
        long l = ((Time)object).toMillis(false);
        ((Time)object).hour += 12;
        long l2 = ((Time)object).toMillis(false);
        ((Time)object).hour = 0;
        ((Time)object).minute = 0;
        long l3 = ((Time)object).toMillis(false);
        ++((Time)object).monthDay;
        long l4 = ((Time)object).toMillis(false);
        ((Time)object).set(System.currentTimeMillis());
        ((Time)object).second = 0;
        long l5 = ((Time)object).normalize(false);
        int n = l5 >= l3 && l5 < l4 || l5 >= l && l5 < l2 ? 0 : 1;
        if (n == this.mLastDisplay && this.mLastFormat != null) {
            object = this.mLastFormat;
        } else {
            if (n != 0) {
                if (n != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unknown display value: ");
                    ((StringBuilder)object).append(n);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                object = java.text.DateFormat.getDateInstance(3);
            } else {
                object = this.getTimeFormat();
            }
            this.mLastFormat = object;
        }
        this.setText(((java.text.DateFormat)object).format(this.mTime));
        if (n == 0) {
            if (l2 <= l4) {
                l2 = l4;
            }
            this.mUpdateTimeMillis = l2;
            return;
        }
        if (this.mTimeMillis < l5) {
            this.mUpdateTimeMillis = 0L;
            return;
        }
        l2 = l < l3 ? l : l3;
        this.mUpdateTimeMillis = l2;
    }

    private static class ReceiverInfo {
        private final ArrayList<DateTimeView> mAttachedViews = new ArrayList();
        private Handler mHandler = new Handler();
        private final ContentObserver mObserver = new ContentObserver(new Handler()){

            @Override
            public void onChange(boolean bl) {
                ReceiverInfo.this.updateAll();
            }
        };
        private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.TIME_TICK".equals(intent.getAction()) && System.currentTimeMillis() < ReceiverInfo.this.getSoonestUpdateTime()) {
                    return;
                }
                ReceiverInfo.this.updateAll();
            }
        };

        private ReceiverInfo() {
        }

        static final Context getApplicationContextIfAvailable(Context context) {
            if ((context = context.getApplicationContext()) == null) {
                context = ActivityThread.currentApplication().getApplicationContext();
            }
            return context;
        }

        static /* synthetic */ void lambda$updateAll$0(DateTimeView dateTimeView) {
            dateTimeView.clearFormatAndUpdate();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addView(DateTimeView dateTimeView) {
            ArrayList<DateTimeView> arrayList = this.mAttachedViews;
            synchronized (arrayList) {
                boolean bl = this.mAttachedViews.isEmpty();
                this.mAttachedViews.add(dateTimeView);
                if (bl) {
                    this.register(ReceiverInfo.getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        long getSoonestUpdateTime() {
            long l = Long.MAX_VALUE;
            ArrayList<DateTimeView> arrayList = this.mAttachedViews;
            synchronized (arrayList) {
                int n = this.mAttachedViews.size();
                int n2 = 0;
                while (n2 < n) {
                    long l2 = this.mAttachedViews.get(n2).mUpdateTimeMillis;
                    long l3 = l;
                    if (l2 < l) {
                        l3 = l2;
                    }
                    ++n2;
                    l = l3;
                }
                return l;
            }
        }

        void register(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            context.registerReceiver(this.mReceiver, intentFilter, null, this.mHandler);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void removeView(DateTimeView dateTimeView) {
            ArrayList<DateTimeView> arrayList = this.mAttachedViews;
            synchronized (arrayList) {
                if (this.mAttachedViews.remove(dateTimeView) && this.mAttachedViews.isEmpty()) {
                    this.unregister(ReceiverInfo.getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setHandler(Handler handler) {
            this.mHandler = handler;
            ArrayList<DateTimeView> arrayList = this.mAttachedViews;
            synchronized (arrayList) {
                if (!this.mAttachedViews.isEmpty()) {
                    this.unregister(this.mAttachedViews.get(0).getContext());
                    this.register(this.mAttachedViews.get(0).getContext());
                }
                return;
            }
        }

        void unregister(Context context) {
            context.unregisterReceiver(this.mReceiver);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void updateAll() {
            ArrayList<DateTimeView> arrayList = this.mAttachedViews;
            synchronized (arrayList) {
                int n = this.mAttachedViews.size();
                int n2 = 0;
                while (n2 < n) {
                    DateTimeView dateTimeView = this.mAttachedViews.get(n2);
                    _$$Lambda$DateTimeView$ReceiverInfo$AVLnX7U5lTcE9jLnlKKNAT1GUeI _$$Lambda$DateTimeView$ReceiverInfo$AVLnX7U5lTcE9jLnlKKNAT1GUeI = new _$$Lambda$DateTimeView$ReceiverInfo$AVLnX7U5lTcE9jLnlKKNAT1GUeI(dateTimeView);
                    dateTimeView.post(_$$Lambda$DateTimeView$ReceiverInfo$AVLnX7U5lTcE9jLnlKKNAT1GUeI);
                    ++n2;
                }
                return;
            }
        }

    }

}

