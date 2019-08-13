/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CalendarViewLegacyDelegate;
import android.widget.CalendarViewMaterialDelegate;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarView
extends FrameLayout {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
    private static final String LOG_TAG = "CalendarView";
    private static final int MODE_HOLO = 0;
    private static final int MODE_MATERIAL = 1;
    @UnsupportedAppUsage
    private final CalendarViewDelegate mDelegate;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843613);
    }

    public CalendarView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CalendarView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.CalendarView, attributeSet, typedArray, n, n2);
        int n3 = typedArray.getInt(13, 0);
        typedArray.recycle();
        if (n3 != 0) {
            if (n3 != 1) throw new IllegalArgumentException("invalid calendarViewMode attribute");
            this.mDelegate = new CalendarViewMaterialDelegate(this, context, attributeSet, n, n2);
            return;
        } else {
            this.mDelegate = new CalendarViewLegacyDelegate(this, context, attributeSet, n, n2);
        }
    }

    public static boolean parseDate(String string2, Calendar calendar) {
        if (string2 != null && !string2.isEmpty()) {
            try {
                calendar.setTime(DATE_FORMATTER.parse(string2));
                return true;
            }
            catch (ParseException parseException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Date: ");
                stringBuilder.append(string2);
                stringBuilder.append(" not in format: ");
                stringBuilder.append(DATE_FORMAT);
                Log.w(LOG_TAG, stringBuilder.toString());
                return false;
            }
        }
        return false;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CalendarView.class.getName();
    }

    public boolean getBoundsForDate(long l, Rect rect) {
        return this.mDelegate.getBoundsForDate(l, rect);
    }

    public long getDate() {
        return this.mDelegate.getDate();
    }

    public int getDateTextAppearance() {
        return this.mDelegate.getDateTextAppearance();
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    @Deprecated
    public int getFocusedMonthDateColor() {
        return this.mDelegate.getFocusedMonthDateColor();
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate();
    }

    @Deprecated
    public Drawable getSelectedDateVerticalBar() {
        return this.mDelegate.getSelectedDateVerticalBar();
    }

    @Deprecated
    public int getSelectedWeekBackgroundColor() {
        return this.mDelegate.getSelectedWeekBackgroundColor();
    }

    @Deprecated
    public boolean getShowWeekNumber() {
        return this.mDelegate.getShowWeekNumber();
    }

    @Deprecated
    public int getShownWeekCount() {
        return this.mDelegate.getShownWeekCount();
    }

    @Deprecated
    public int getUnfocusedMonthDateColor() {
        return this.mDelegate.getUnfocusedMonthDateColor();
    }

    public int getWeekDayTextAppearance() {
        return this.mDelegate.getWeekDayTextAppearance();
    }

    @Deprecated
    public int getWeekNumberColor() {
        return this.mDelegate.getWeekNumberColor();
    }

    @Deprecated
    public int getWeekSeparatorLineColor() {
        return this.mDelegate.getWeekSeparatorLineColor();
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    public void setDate(long l) {
        this.mDelegate.setDate(l);
    }

    public void setDate(long l, boolean bl, boolean bl2) {
        this.mDelegate.setDate(l, bl, bl2);
    }

    public void setDateTextAppearance(int n) {
        this.mDelegate.setDateTextAppearance(n);
    }

    public void setFirstDayOfWeek(int n) {
        this.mDelegate.setFirstDayOfWeek(n);
    }

    @Deprecated
    public void setFocusedMonthDateColor(int n) {
        this.mDelegate.setFocusedMonthDateColor(n);
    }

    public void setMaxDate(long l) {
        this.mDelegate.setMaxDate(l);
    }

    public void setMinDate(long l) {
        this.mDelegate.setMinDate(l);
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.mDelegate.setOnDateChangeListener(onDateChangeListener);
    }

    @Deprecated
    public void setSelectedDateVerticalBar(int n) {
        this.mDelegate.setSelectedDateVerticalBar(n);
    }

    @Deprecated
    public void setSelectedDateVerticalBar(Drawable drawable2) {
        this.mDelegate.setSelectedDateVerticalBar(drawable2);
    }

    @Deprecated
    public void setSelectedWeekBackgroundColor(int n) {
        this.mDelegate.setSelectedWeekBackgroundColor(n);
    }

    @Deprecated
    public void setShowWeekNumber(boolean bl) {
        this.mDelegate.setShowWeekNumber(bl);
    }

    @Deprecated
    public void setShownWeekCount(int n) {
        this.mDelegate.setShownWeekCount(n);
    }

    @Deprecated
    public void setUnfocusedMonthDateColor(int n) {
        this.mDelegate.setUnfocusedMonthDateColor(n);
    }

    public void setWeekDayTextAppearance(int n) {
        this.mDelegate.setWeekDayTextAppearance(n);
    }

    @Deprecated
    public void setWeekNumberColor(int n) {
        this.mDelegate.setWeekNumberColor(n);
    }

    @Deprecated
    public void setWeekSeparatorLineColor(int n) {
        this.mDelegate.setWeekSeparatorLineColor(n);
    }

    static abstract class AbstractCalendarViewDelegate
    implements CalendarViewDelegate {
        protected static final String DEFAULT_MAX_DATE = "01/01/2100";
        protected static final String DEFAULT_MIN_DATE = "01/01/1900";
        protected Context mContext;
        protected Locale mCurrentLocale;
        protected CalendarView mDelegator;

        AbstractCalendarViewDelegate(CalendarView calendarView, Context context) {
            this.mDelegator = calendarView;
            this.mContext = context;
            this.setCurrentLocale(Locale.getDefault());
        }

        @Override
        public int getFocusedMonthDateColor() {
            return 0;
        }

        @Override
        public Drawable getSelectedDateVerticalBar() {
            return null;
        }

        @Override
        public int getSelectedWeekBackgroundColor() {
            return 0;
        }

        @Override
        public boolean getShowWeekNumber() {
            return false;
        }

        @Override
        public int getShownWeekCount() {
            return 0;
        }

        @Override
        public int getUnfocusedMonthDateColor() {
            return 0;
        }

        @Override
        public int getWeekNumberColor() {
            return 0;
        }

        @Override
        public int getWeekSeparatorLineColor() {
            return 0;
        }

        @Override
        public void onConfigurationChanged(Configuration configuration) {
        }

        protected void setCurrentLocale(Locale locale) {
            if (locale.equals(this.mCurrentLocale)) {
                return;
            }
            this.mCurrentLocale = locale;
        }

        @Override
        public void setFocusedMonthDateColor(int n) {
        }

        @Override
        public void setSelectedDateVerticalBar(int n) {
        }

        @Override
        public void setSelectedDateVerticalBar(Drawable drawable2) {
        }

        @Override
        public void setSelectedWeekBackgroundColor(int n) {
        }

        @Override
        public void setShowWeekNumber(boolean bl) {
        }

        @Override
        public void setShownWeekCount(int n) {
        }

        @Override
        public void setUnfocusedMonthDateColor(int n) {
        }

        @Override
        public void setWeekNumberColor(int n) {
        }

        @Override
        public void setWeekSeparatorLineColor(int n) {
        }
    }

    private static interface CalendarViewDelegate {
        public boolean getBoundsForDate(long var1, Rect var3);

        public long getDate();

        public int getDateTextAppearance();

        public int getFirstDayOfWeek();

        public int getFocusedMonthDateColor();

        public long getMaxDate();

        public long getMinDate();

        public Drawable getSelectedDateVerticalBar();

        public int getSelectedWeekBackgroundColor();

        public boolean getShowWeekNumber();

        public int getShownWeekCount();

        public int getUnfocusedMonthDateColor();

        public int getWeekDayTextAppearance();

        public int getWeekNumberColor();

        public int getWeekSeparatorLineColor();

        public void onConfigurationChanged(Configuration var1);

        public void setDate(long var1);

        public void setDate(long var1, boolean var3, boolean var4);

        public void setDateTextAppearance(int var1);

        public void setFirstDayOfWeek(int var1);

        public void setFocusedMonthDateColor(int var1);

        public void setMaxDate(long var1);

        public void setMinDate(long var1);

        public void setOnDateChangeListener(OnDateChangeListener var1);

        public void setSelectedDateVerticalBar(int var1);

        public void setSelectedDateVerticalBar(Drawable var1);

        public void setSelectedWeekBackgroundColor(int var1);

        public void setShowWeekNumber(boolean var1);

        public void setShownWeekCount(int var1);

        public void setUnfocusedMonthDateColor(int var1);

        public void setWeekDayTextAppearance(int var1);

        public void setWeekNumberColor(int var1);

        public void setWeekSeparatorLineColor(int var1);
    }

    public static interface OnDateChangeListener {
        public void onSelectedDayChange(CalendarView var1, int var2, int var3, int var4);
    }

}

