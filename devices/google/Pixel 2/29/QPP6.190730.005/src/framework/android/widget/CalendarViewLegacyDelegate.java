/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 *  android.icu.util.TimeZone
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.Date;
import java.util.Locale;
import libcore.icu.LocaleData;

class CalendarViewLegacyDelegate
extends CalendarView.AbstractCalendarViewDelegate {
    private static final int ADJUSTMENT_SCROLL_DURATION = 500;
    private static final int DAYS_PER_WEEK = 7;
    private static final int DEFAULT_DATE_TEXT_SIZE = 14;
    private static final int DEFAULT_SHOWN_WEEK_COUNT = 6;
    private static final boolean DEFAULT_SHOW_WEEK_NUMBER = true;
    private static final int DEFAULT_WEEK_DAY_TEXT_APPEARANCE_RES_ID = -1;
    private static final int GOTO_SCROLL_DURATION = 1000;
    private static final long MILLIS_IN_DAY = 86400000L;
    private static final long MILLIS_IN_WEEK = 604800000L;
    private static final int SCROLL_CHANGE_DELAY = 40;
    private static final int SCROLL_HYST_WEEKS = 2;
    private static final int UNSCALED_BOTTOM_BUFFER = 20;
    private static final int UNSCALED_LIST_SCROLL_TOP_OFFSET = 2;
    private static final int UNSCALED_SELECTED_DATE_VERTICAL_BAR_WIDTH = 6;
    private static final int UNSCALED_WEEK_MIN_VISIBLE_HEIGHT = 12;
    private static final int UNSCALED_WEEK_SEPARATOR_LINE_WIDTH = 1;
    private WeeksAdapter mAdapter;
    private int mBottomBuffer = 20;
    private int mCurrentMonthDisplayed = -1;
    private int mCurrentScrollState = 0;
    private int mDateTextAppearanceResId;
    private int mDateTextSize;
    private ViewGroup mDayNamesHeader;
    private String[] mDayNamesLong;
    private String[] mDayNamesShort;
    private int mDaysPerWeek = 7;
    private Calendar mFirstDayOfMonth;
    private int mFirstDayOfWeek;
    private int mFocusedMonthDateColor;
    private float mFriction = 0.05f;
    private boolean mIsScrollingUp = false;
    private int mListScrollTopOffset = 2;
    private ListView mListView;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private TextView mMonthName;
    private CalendarView.OnDateChangeListener mOnDateChangeListener;
    private long mPreviousScrollPosition;
    private int mPreviousScrollState = 0;
    private ScrollStateRunnable mScrollStateChangedRunnable = new ScrollStateRunnable();
    private Drawable mSelectedDateVerticalBar;
    private final int mSelectedDateVerticalBarWidth;
    private int mSelectedWeekBackgroundColor;
    private boolean mShowWeekNumber;
    private int mShownWeekCount;
    private Calendar mTempDate;
    private int mUnfocusedMonthDateColor;
    private float mVelocityScale = 0.333f;
    private int mWeekDayTextAppearanceResId;
    private int mWeekMinVisibleHeight = 12;
    private int mWeekNumberColor;
    private int mWeekSeparatorLineColor;
    private final int mWeekSeparatorLineWidth;

    CalendarViewLegacyDelegate(CalendarView object, Context context, AttributeSet attributeSet, int n, int n2) {
        super((CalendarView)object, context);
        object = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarView, n, n2);
        this.mShowWeekNumber = ((TypedArray)object).getBoolean(1, true);
        this.mFirstDayOfWeek = ((TypedArray)object).getInt(0, LocaleData.get((Locale)Locale.getDefault()).firstDayOfWeek);
        if (!CalendarView.parseDate(((TypedArray)object).getString(2), this.mMinDate)) {
            CalendarView.parseDate("01/01/1900", this.mMinDate);
        }
        if (!CalendarView.parseDate(((TypedArray)object).getString(3), this.mMaxDate)) {
            CalendarView.parseDate("01/01/2100", this.mMaxDate);
        }
        if (!this.mMaxDate.before((Object)this.mMinDate)) {
            this.mShownWeekCount = ((TypedArray)object).getInt(4, 6);
            this.mSelectedWeekBackgroundColor = ((TypedArray)object).getColor(5, 0);
            this.mFocusedMonthDateColor = ((TypedArray)object).getColor(6, 0);
            this.mUnfocusedMonthDateColor = ((TypedArray)object).getColor(7, 0);
            this.mWeekSeparatorLineColor = ((TypedArray)object).getColor(9, 0);
            this.mWeekNumberColor = ((TypedArray)object).getColor(8, 0);
            this.mSelectedDateVerticalBar = ((TypedArray)object).getDrawable(10);
            this.mDateTextAppearanceResId = ((TypedArray)object).getResourceId(12, 16973894);
            this.updateDateTextSize();
            this.mWeekDayTextAppearanceResId = ((TypedArray)object).getResourceId(11, -1);
            ((TypedArray)object).recycle();
            object = this.mDelegator.getResources().getDisplayMetrics();
            this.mWeekMinVisibleHeight = (int)TypedValue.applyDimension(1, 12.0f, (DisplayMetrics)object);
            this.mListScrollTopOffset = (int)TypedValue.applyDimension(1, 2.0f, (DisplayMetrics)object);
            this.mBottomBuffer = (int)TypedValue.applyDimension(1, 20.0f, (DisplayMetrics)object);
            this.mSelectedDateVerticalBarWidth = (int)TypedValue.applyDimension(1, 6.0f, (DisplayMetrics)object);
            this.mWeekSeparatorLineWidth = (int)TypedValue.applyDimension(1, 1.0f, (DisplayMetrics)object);
            object = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(17367106, null, false);
            this.mDelegator.addView((View)object);
            this.mListView = (ListView)this.mDelegator.findViewById(16908298);
            this.mDayNamesHeader = (ViewGroup)((View)object).findViewById(16908866);
            this.mMonthName = (TextView)((View)object).findViewById(16909128);
            this.setUpHeader();
            this.setUpListView();
            this.setUpAdapter();
            this.mTempDate.setTimeInMillis(System.currentTimeMillis());
            if (this.mTempDate.before((Object)this.mMinDate)) {
                this.goTo(this.mMinDate, false, true, true);
            } else if (this.mMaxDate.before((Object)this.mTempDate)) {
                this.goTo(this.mMaxDate, false, true, true);
            } else {
                this.goTo(this.mTempDate, false, true, true);
            }
            this.mDelegator.invalidate();
            return;
        }
        throw new IllegalArgumentException("Max date cannot be before min date.");
    }

    private static Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance((Locale)locale);
        }
        long l = calendar.getTimeInMillis();
        calendar = Calendar.getInstance((Locale)locale);
        calendar.setTimeInMillis(l);
        return calendar;
    }

    private int getWeeksSinceMinDate(Calendar calendar) {
        if (!calendar.before((Object)this.mMinDate)) {
            return (int)((calendar.getTimeInMillis() + (long)calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) - (this.mMinDate.getTimeInMillis() + (long)this.mMinDate.getTimeZone().getOffset(this.mMinDate.getTimeInMillis())) + (long)(this.mMinDate.get(7) - this.mFirstDayOfWeek) * 86400000L) / 604800000L);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromDate: ");
        stringBuilder.append(this.mMinDate.getTime());
        stringBuilder.append(" does not precede toDate: ");
        stringBuilder.append(calendar.getTime());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void goTo(Calendar calendar, boolean bl, boolean bl2, boolean bl3) {
        if (!calendar.before((Object)this.mMinDate) && !calendar.after((Object)this.mMaxDate)) {
            int n;
            int n2 = this.mListView.getFirstVisiblePosition();
            View view = this.mListView.getChildAt(0);
            int n3 = n2;
            if (view != null) {
                n3 = n2;
                if (view.getTop() < 0) {
                    n3 = n2 + 1;
                }
            }
            n2 = n = this.mShownWeekCount + n3 - 1;
            if (view != null) {
                n2 = n;
                if (view.getTop() > this.mBottomBuffer) {
                    n2 = n - 1;
                }
            }
            if (bl2) {
                this.mAdapter.setSelectedDay(calendar);
            }
            if ((n = this.getWeeksSinceMinDate(calendar)) >= n3 && n <= n2 && !bl3) {
                if (bl2) {
                    this.setMonthDisplayed(calendar);
                }
            } else {
                this.mFirstDayOfMonth.setTimeInMillis(calendar.getTimeInMillis());
                this.mFirstDayOfMonth.set(5, 1);
                this.setMonthDisplayed(this.mFirstDayOfMonth);
                n3 = this.mFirstDayOfMonth.before((Object)this.mMinDate) ? 0 : this.getWeeksSinceMinDate(this.mFirstDayOfMonth);
                this.mPreviousScrollState = 2;
                if (bl) {
                    this.mListView.smoothScrollToPositionFromTop(n3, this.mListScrollTopOffset, 1000);
                } else {
                    this.mListView.setSelectionFromTop(n3, this.mListScrollTopOffset);
                    this.onScrollStateChanged(this.mListView, 0);
                }
            }
            return;
        }
        throw new IllegalArgumentException("timeInMillis must be between the values of getMinDate() and getMaxDate()");
    }

    private void invalidateAllWeekViews() {
        int n = this.mListView.getChildCount();
        for (int i = 0; i < n; ++i) {
            this.mListView.getChildAt(i).invalidate();
        }
    }

    private static boolean isSameDate(Calendar calendar, Calendar calendar2) {
        int n = calendar.get(6);
        int n2 = calendar2.get(6);
        boolean bl = true;
        if (n != n2 || calendar.get(1) != calendar2.get(1)) {
            bl = false;
        }
        return bl;
    }

    private void onScroll(AbsListView absListView, int n, int n2, int n3) {
        block14 : {
            WeekView weekView;
            long l;
            block13 : {
                long l2;
                block12 : {
                    n = 0;
                    weekView = (WeekView)absListView.getChildAt(0);
                    if (weekView == null) {
                        return;
                    }
                    l = absListView.getFirstVisiblePosition() * weekView.getHeight() - weekView.getBottom();
                    if (l >= (l2 = this.mPreviousScrollPosition)) break block12;
                    this.mIsScrollingUp = true;
                    break block13;
                }
                if (l <= l2) break block14;
                this.mIsScrollingUp = false;
            }
            if (weekView.getBottom() < this.mWeekMinVisibleHeight) {
                n = 1;
            }
            if (this.mIsScrollingUp) {
                weekView = (WeekView)absListView.getChildAt(n + 2);
            } else if (n != 0) {
                weekView = (WeekView)absListView.getChildAt(n);
            }
            if (weekView != null) {
                n = this.mIsScrollingUp ? weekView.getMonthOfFirstWeekDay() : weekView.getMonthOfLastWeekDay();
                n = this.mCurrentMonthDisplayed == 11 && n == 0 ? 1 : (this.mCurrentMonthDisplayed == 0 && n == 11 ? -1 : (n -= this.mCurrentMonthDisplayed));
                if (!this.mIsScrollingUp && n > 0 || this.mIsScrollingUp && n < 0) {
                    absListView = weekView.getFirstDay();
                    if (this.mIsScrollingUp) {
                        absListView.add(5, -7);
                    } else {
                        absListView.add(5, 7);
                    }
                    this.setMonthDisplayed((Calendar)absListView);
                }
            }
            this.mPreviousScrollPosition = l;
            this.mPreviousScrollState = this.mCurrentScrollState;
            return;
        }
    }

    private void onScrollStateChanged(AbsListView absListView, int n) {
        this.mScrollStateChangedRunnable.doScrollStateChange(absListView, n);
    }

    private void setMonthDisplayed(Calendar object) {
        this.mCurrentMonthDisplayed = object.get(2);
        this.mAdapter.setFocusMonth(this.mCurrentMonthDisplayed);
        long l = object.getTimeInMillis();
        object = DateUtils.formatDateRange(this.mContext, l, l, 52);
        this.mMonthName.setText((CharSequence)object);
        this.mMonthName.invalidate();
    }

    private void setUpAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new WeeksAdapter(this.mContext);
            this.mAdapter.registerDataSetObserver(new DataSetObserver(){

                @Override
                public void onChanged() {
                    if (CalendarViewLegacyDelegate.this.mOnDateChangeListener != null) {
                        Calendar calendar = CalendarViewLegacyDelegate.this.mAdapter.getSelectedDay();
                        CalendarViewLegacyDelegate.this.mOnDateChangeListener.onSelectedDayChange(CalendarViewLegacyDelegate.this.mDelegator, calendar.get(1), calendar.get(2), calendar.get(5));
                    }
                }
            });
            this.mListView.setAdapter(this.mAdapter);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void setUpHeader() {
        int n;
        int n2;
        int n3 = this.mDaysPerWeek;
        this.mDayNamesShort = new String[n3];
        this.mDayNamesLong = new String[n3];
        int n4 = this.mFirstDayOfWeek;
        for (n = this.mFirstDayOfWeek; n < n4 + n3; ++n) {
            n2 = n > 7 ? n - 7 : n;
            this.mDayNamesShort[n - this.mFirstDayOfWeek] = DateUtils.getDayOfWeekString(n2, 50);
            this.mDayNamesLong[n - this.mFirstDayOfWeek] = DateUtils.getDayOfWeekString(n2, 10);
        }
        TextView textView = (TextView)this.mDayNamesHeader.getChildAt(0);
        if (this.mShowWeekNumber) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        n2 = this.mDayNamesHeader.getChildCount();
        for (n = 1; n < n2; ++n) {
            textView = (TextView)this.mDayNamesHeader.getChildAt(n);
            n3 = this.mWeekDayTextAppearanceResId;
            if (n3 > -1) {
                textView.setTextAppearance(n3);
            }
            if (n < this.mDaysPerWeek + 1) {
                textView.setText(this.mDayNamesShort[n - 1]);
                textView.setContentDescription(this.mDayNamesLong[n - 1]);
                textView.setVisibility(0);
                continue;
            }
            textView.setVisibility(8);
        }
        this.mDayNamesHeader.invalidate();
    }

    private void setUpListView() {
        this.mListView.setDivider(null);
        this.mListView.setItemsCanFocus(true);
        this.mListView.setVerticalScrollBarEnabled(false);
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                CalendarViewLegacyDelegate.this.onScroll(absListView, n, n2, n3);
            }

            @Override
            public void onScrollStateChanged(AbsListView absListView, int n) {
                CalendarViewLegacyDelegate.this.onScrollStateChanged(absListView, n);
            }
        });
        this.mListView.setFriction(this.mFriction);
        this.mListView.setVelocityScale(this.mVelocityScale);
    }

    private void updateDateTextSize() {
        TypedArray typedArray = this.mDelegator.getContext().obtainStyledAttributes(this.mDateTextAppearanceResId, R.styleable.TextAppearance);
        this.mDateTextSize = typedArray.getDimensionPixelSize(0, 14);
        typedArray.recycle();
    }

    @Override
    public boolean getBoundsForDate(long l, Rect rect) {
        int[] arrn = Calendar.getInstance();
        arrn.setTimeInMillis(l);
        int n = this.mListView.getCount();
        for (int i = 0; i < n; ++i) {
            WeekView weekView = (WeekView)this.mListView.getChildAt(i);
            if (!weekView.getBoundsForDate((Calendar)arrn, rect)) continue;
            int[] arrn2 = new int[2];
            arrn = new int[2];
            weekView.getLocationOnScreen(arrn2);
            this.mDelegator.getLocationOnScreen(arrn);
            i = arrn2[1] - arrn[1];
            rect.top += i;
            rect.bottom += i;
            return true;
        }
        return false;
    }

    @Override
    public long getDate() {
        return this.mAdapter.mSelectedDate.getTimeInMillis();
    }

    @Override
    public int getDateTextAppearance() {
        return this.mDateTextAppearanceResId;
    }

    @Override
    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    @Override
    public int getFocusedMonthDateColor() {
        return this.mFocusedMonthDateColor;
    }

    @Override
    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    @Override
    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    @Override
    public Drawable getSelectedDateVerticalBar() {
        return this.mSelectedDateVerticalBar;
    }

    @Override
    public int getSelectedWeekBackgroundColor() {
        return this.mSelectedWeekBackgroundColor;
    }

    @Override
    public boolean getShowWeekNumber() {
        return this.mShowWeekNumber;
    }

    @Override
    public int getShownWeekCount() {
        return this.mShownWeekCount;
    }

    @Override
    public int getUnfocusedMonthDateColor() {
        return this.mUnfocusedMonthDateColor;
    }

    @Override
    public int getWeekDayTextAppearance() {
        return this.mWeekDayTextAppearanceResId;
    }

    @Override
    public int getWeekNumberColor() {
        return this.mWeekNumberColor;
    }

    @Override
    public int getWeekSeparatorLineColor() {
        return this.mWeekSeparatorLineColor;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setCurrentLocale(configuration.locale);
    }

    @Override
    protected void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = CalendarViewLegacyDelegate.getCalendarForLocale(this.mTempDate, locale);
        this.mFirstDayOfMonth = CalendarViewLegacyDelegate.getCalendarForLocale(this.mFirstDayOfMonth, locale);
        this.mMinDate = CalendarViewLegacyDelegate.getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = CalendarViewLegacyDelegate.getCalendarForLocale(this.mMaxDate, locale);
    }

    @Override
    public void setDate(long l) {
        this.setDate(l, false, false);
    }

    @Override
    public void setDate(long l, boolean bl, boolean bl2) {
        this.mTempDate.setTimeInMillis(l);
        if (CalendarViewLegacyDelegate.isSameDate(this.mTempDate, this.mAdapter.mSelectedDate)) {
            return;
        }
        this.goTo(this.mTempDate, bl, true, bl2);
    }

    @Override
    public void setDateTextAppearance(int n) {
        if (this.mDateTextAppearanceResId != n) {
            this.mDateTextAppearanceResId = n;
            this.updateDateTextSize();
            this.invalidateAllWeekViews();
        }
    }

    @Override
    public void setFirstDayOfWeek(int n) {
        if (this.mFirstDayOfWeek == n) {
            return;
        }
        this.mFirstDayOfWeek = n;
        this.mAdapter.init();
        this.mAdapter.notifyDataSetChanged();
        this.setUpHeader();
    }

    @Override
    public void setFocusedMonthDateColor(int n) {
        if (this.mFocusedMonthDateColor != n) {
            this.mFocusedMonthDateColor = n;
            int n2 = this.mListView.getChildCount();
            for (n = 0; n < n2; ++n) {
                WeekView weekView = (WeekView)this.mListView.getChildAt(n);
                if (!weekView.mHasFocusedDay) continue;
                weekView.invalidate();
            }
        }
    }

    @Override
    public void setMaxDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (CalendarViewLegacyDelegate.isSameDate(this.mTempDate, this.mMaxDate)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(l);
        this.mAdapter.init();
        Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.after((Object)this.mMaxDate)) {
            this.setDate(this.mMaxDate.getTimeInMillis());
        } else {
            this.goTo(calendar, false, true, false);
        }
    }

    @Override
    public void setMinDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (CalendarViewLegacyDelegate.isSameDate(this.mTempDate, this.mMinDate)) {
            return;
        }
        this.mMinDate.setTimeInMillis(l);
        Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.before((Object)this.mMinDate)) {
            this.mAdapter.setSelectedDay(this.mMinDate);
        }
        this.mAdapter.init();
        if (calendar.before((Object)this.mMinDate)) {
            this.setDate(this.mTempDate.getTimeInMillis());
        } else {
            this.goTo(calendar, false, true, false);
        }
    }

    @Override
    public void setOnDateChangeListener(CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override
    public void setSelectedDateVerticalBar(int n) {
        this.setSelectedDateVerticalBar(this.mDelegator.getContext().getDrawable(n));
    }

    @Override
    public void setSelectedDateVerticalBar(Drawable object) {
        if (this.mSelectedDateVerticalBar != object) {
            this.mSelectedDateVerticalBar = object;
            int n = this.mListView.getChildCount();
            for (int i = 0; i < n; ++i) {
                object = (WeekView)this.mListView.getChildAt(i);
                if (!((WeekView)object).mHasSelectedDay) continue;
                ((View)object).invalidate();
            }
        }
    }

    @Override
    public void setSelectedWeekBackgroundColor(int n) {
        if (this.mSelectedWeekBackgroundColor != n) {
            this.mSelectedWeekBackgroundColor = n;
            int n2 = this.mListView.getChildCount();
            for (n = 0; n < n2; ++n) {
                WeekView weekView = (WeekView)this.mListView.getChildAt(n);
                if (!weekView.mHasSelectedDay) continue;
                weekView.invalidate();
            }
        }
    }

    @Override
    public void setShowWeekNumber(boolean bl) {
        if (this.mShowWeekNumber == bl) {
            return;
        }
        this.mShowWeekNumber = bl;
        this.mAdapter.notifyDataSetChanged();
        this.setUpHeader();
    }

    @Override
    public void setShownWeekCount(int n) {
        if (this.mShownWeekCount != n) {
            this.mShownWeekCount = n;
            this.mDelegator.invalidate();
        }
    }

    @Override
    public void setUnfocusedMonthDateColor(int n) {
        if (this.mUnfocusedMonthDateColor != n) {
            this.mUnfocusedMonthDateColor = n;
            int n2 = this.mListView.getChildCount();
            for (n = 0; n < n2; ++n) {
                WeekView weekView = (WeekView)this.mListView.getChildAt(n);
                if (!weekView.mHasUnfocusedDay) continue;
                weekView.invalidate();
            }
        }
    }

    @Override
    public void setWeekDayTextAppearance(int n) {
        if (this.mWeekDayTextAppearanceResId != n) {
            this.mWeekDayTextAppearanceResId = n;
            this.setUpHeader();
        }
    }

    @Override
    public void setWeekNumberColor(int n) {
        if (this.mWeekNumberColor != n) {
            this.mWeekNumberColor = n;
            if (this.mShowWeekNumber) {
                this.invalidateAllWeekViews();
            }
        }
    }

    @Override
    public void setWeekSeparatorLineColor(int n) {
        if (this.mWeekSeparatorLineColor != n) {
            this.mWeekSeparatorLineColor = n;
            this.invalidateAllWeekViews();
        }
    }

    private class ScrollStateRunnable
    implements Runnable {
        private int mNewState;
        private AbsListView mView;

        private ScrollStateRunnable() {
        }

        public void doScrollStateChange(AbsListView absListView, int n) {
            this.mView = absListView;
            this.mNewState = n;
            CalendarViewLegacyDelegate.this.mDelegator.removeCallbacks(this);
            CalendarViewLegacyDelegate.this.mDelegator.postDelayed(this, 40L);
        }

        @Override
        public void run() {
            CalendarViewLegacyDelegate.this.mCurrentScrollState = this.mNewState;
            if (this.mNewState == 0 && CalendarViewLegacyDelegate.this.mPreviousScrollState != 0) {
                View view = this.mView.getChildAt(0);
                if (view == null) {
                    return;
                }
                int n = view.getBottom() - CalendarViewLegacyDelegate.this.mListScrollTopOffset;
                if (n > CalendarViewLegacyDelegate.this.mListScrollTopOffset) {
                    if (CalendarViewLegacyDelegate.this.mIsScrollingUp) {
                        this.mView.smoothScrollBy(n - view.getHeight(), 500);
                    } else {
                        this.mView.smoothScrollBy(n, 500);
                    }
                }
            }
            CalendarViewLegacyDelegate.this.mPreviousScrollState = this.mNewState;
        }
    }

    private class WeekView
    extends View {
        private String[] mDayNumbers;
        private final Paint mDrawPaint;
        private Calendar mFirstDay;
        private boolean[] mFocusDay;
        private boolean mHasFocusedDay;
        private boolean mHasSelectedDay;
        private boolean mHasUnfocusedDay;
        private int mHeight;
        private int mLastWeekDayMonth;
        private final Paint mMonthNumDrawPaint;
        private int mMonthOfFirstWeekDay;
        private int mNumCells;
        private int mSelectedDay;
        private int mSelectedLeft;
        private int mSelectedRight;
        private final Rect mTempRect;
        private int mWeek;
        private int mWidth;

        public WeekView(Context context) {
            super(context);
            this.mTempRect = new Rect();
            this.mDrawPaint = new Paint();
            this.mMonthNumDrawPaint = new Paint();
            this.mMonthOfFirstWeekDay = -1;
            this.mLastWeekDayMonth = -1;
            this.mWeek = -1;
            this.mHasSelectedDay = false;
            this.mSelectedDay = -1;
            this.mSelectedLeft = -1;
            this.mSelectedRight = -1;
            this.initializePaints();
        }

        private void drawBackground(Canvas canvas) {
            Rect rect;
            if (!this.mHasSelectedDay) {
                return;
            }
            this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mSelectedWeekBackgroundColor);
            this.mTempRect.top = CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
            this.mTempRect.bottom = this.mHeight;
            boolean bl = this.isLayoutRtl();
            int n = 0;
            if (bl) {
                rect = this.mTempRect;
                rect.left = 0;
                rect.right = this.mSelectedLeft - 2;
            } else {
                rect = this.mTempRect;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    n = this.mWidth / this.mNumCells;
                }
                rect.left = n;
                this.mTempRect.right = this.mSelectedLeft - 2;
            }
            canvas.drawRect(this.mTempRect, this.mDrawPaint);
            if (bl) {
                rect = this.mTempRect;
                rect.left = this.mSelectedRight + 3;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    n = this.mWidth;
                    n -= n / this.mNumCells;
                } else {
                    n = this.mWidth;
                }
                rect.right = n;
            } else {
                rect = this.mTempRect;
                rect.left = this.mSelectedRight + 3;
                rect.right = this.mWidth;
            }
            canvas.drawRect(this.mTempRect, this.mDrawPaint);
        }

        private void drawSelectedDateVerticalBars(Canvas canvas) {
            if (!this.mHasSelectedDay) {
                return;
            }
            CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedLeft - CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2, CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedLeft + CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2, this.mHeight);
            CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
            CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedRight - CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2, CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedRight + CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2, this.mHeight);
            CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
        }

        private void drawWeekNumbersAndDates(Canvas canvas) {
            int n;
            float f = this.mDrawPaint.getTextSize();
            int n2 = (int)(((float)this.mHeight + f) / 2.0f) - CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
            int n3 = this.mNumCells;
            int n4 = n3 * 2;
            this.mDrawPaint.setTextAlign(Paint.Align.CENTER);
            this.mDrawPaint.setTextSize(CalendarViewLegacyDelegate.this.mDateTextSize);
            int n5 = 0;
            if (this.isLayoutRtl()) {
                for (n = 0; n < n3 - 1; ++n) {
                    Paint paint = this.mMonthNumDrawPaint;
                    n5 = this.mFocusDay[n] ? CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor;
                    paint.setColor(n5);
                    n5 = (n * 2 + 1) * this.mWidth / n4;
                    canvas.drawText(this.mDayNumbers[n3 - 1 - n], n5, n2, this.mMonthNumDrawPaint);
                }
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekNumberColor);
                    n = this.mWidth;
                    n5 = n / n4;
                    canvas.drawText(this.mDayNumbers[0], n - n5, n2, this.mDrawPaint);
                }
            } else {
                n = n5;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekNumberColor);
                    n = this.mWidth / n4;
                    canvas.drawText(this.mDayNumbers[0], n, n2, this.mDrawPaint);
                    n = 0 + 1;
                }
                while (n < n3) {
                    Paint paint = this.mMonthNumDrawPaint;
                    n5 = this.mFocusDay[n] ? CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor;
                    paint.setColor(n5);
                    n5 = (n * 2 + 1) * this.mWidth / n4;
                    canvas.drawText(this.mDayNumbers[n], n5, n2, this.mMonthNumDrawPaint);
                    ++n;
                }
            }
        }

        private void drawWeekSeparators(Canvas canvas) {
            float f;
            int n;
            float f2;
            int n2 = n = CalendarViewLegacyDelegate.this.mListView.getFirstVisiblePosition();
            if (CalendarViewLegacyDelegate.this.mListView.getChildAt(0).getTop() < 0) {
                n2 = n + 1;
            }
            if (n2 == this.mWeek) {
                return;
            }
            this.mDrawPaint.setColor(CalendarViewLegacyDelegate.this.mWeekSeparatorLineColor);
            this.mDrawPaint.setStrokeWidth(CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth);
            if (this.isLayoutRtl()) {
                f2 = 0.0f;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    n2 = this.mWidth;
                    n2 -= n2 / this.mNumCells;
                } else {
                    n2 = this.mWidth;
                }
                f = n2;
            } else {
                f2 = CalendarViewLegacyDelegate.this.mShowWeekNumber ? (float)(this.mWidth / this.mNumCells) : 0.0f;
                f = this.mWidth;
            }
            canvas.drawLine(f2, 0.0f, f, 0.0f, this.mDrawPaint);
        }

        private void initializePaints() {
            this.mDrawPaint.setFakeBoldText(false);
            this.mDrawPaint.setAntiAlias(true);
            this.mDrawPaint.setStyle(Paint.Style.FILL);
            this.mMonthNumDrawPaint.setFakeBoldText(true);
            this.mMonthNumDrawPaint.setAntiAlias(true);
            this.mMonthNumDrawPaint.setStyle(Paint.Style.FILL);
            this.mMonthNumDrawPaint.setTextAlign(Paint.Align.CENTER);
            this.mMonthNumDrawPaint.setTextSize(CalendarViewLegacyDelegate.this.mDateTextSize);
        }

        private void updateSelectionPositions() {
            if (this.mHasSelectedDay) {
                int n;
                boolean bl = this.isLayoutRtl();
                int n2 = n = this.mSelectedDay - CalendarViewLegacyDelegate.this.mFirstDayOfWeek;
                if (n < 0) {
                    n2 = n + 7;
                }
                n = n2;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    n = n2;
                    if (!bl) {
                        n = n2 + 1;
                    }
                }
                this.mSelectedLeft = bl ? (CalendarViewLegacyDelegate.this.mDaysPerWeek - 1 - n) * this.mWidth / this.mNumCells : this.mWidth * n / this.mNumCells;
                this.mSelectedRight = this.mSelectedLeft + this.mWidth / this.mNumCells;
            }
        }

        public boolean getBoundsForDate(Calendar calendar, Rect rect) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(this.mFirstDay.getTime());
            for (int i = 0; i < CalendarViewLegacyDelegate.this.mDaysPerWeek; ++i) {
                if (calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5)) {
                    int n = this.mWidth / this.mNumCells;
                    if (this.isLayoutRtl()) {
                        i = CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mNumCells - i - 2 : this.mNumCells - i - 1;
                        rect.left = i * n;
                    } else {
                        if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                            ++i;
                        }
                        rect.left = i * n;
                    }
                    rect.top = 0;
                    rect.right = rect.left + n;
                    rect.bottom = this.getHeight();
                    return true;
                }
                calendar2.add(5, 1);
            }
            return false;
        }

        public boolean getDayFromLocation(float f, Calendar calendar) {
            int n;
            int n2;
            int n3;
            boolean bl = this.isLayoutRtl();
            if (bl) {
                n3 = 0;
                if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    n = this.mWidth;
                    n -= n / this.mNumCells;
                } else {
                    n = this.mWidth;
                }
                n2 = n;
            } else {
                n = CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0;
                n2 = this.mWidth;
                n3 = n;
            }
            if (!(f < (float)n3) && !(f > (float)n2)) {
                n = n2 = (int)((f - (float)n3) * (float)CalendarViewLegacyDelegate.this.mDaysPerWeek / (float)(n2 - n3));
                if (bl) {
                    n = CalendarViewLegacyDelegate.this.mDaysPerWeek - 1 - n2;
                }
                calendar.setTimeInMillis(this.mFirstDay.getTimeInMillis());
                calendar.add(5, n);
                return true;
            }
            calendar.clear();
            return false;
        }

        public Calendar getFirstDay() {
            return this.mFirstDay;
        }

        public int getMonthOfFirstWeekDay() {
            return this.mMonthOfFirstWeekDay;
        }

        public int getMonthOfLastWeekDay() {
            return this.mLastWeekDayMonth;
        }

        public void init(int n, int n2, int n3) {
            this.mSelectedDay = n2;
            boolean bl = this.mSelectedDay != -1;
            this.mHasSelectedDay = bl;
            n2 = CalendarViewLegacyDelegate.this.mShowWeekNumber ? CalendarViewLegacyDelegate.this.mDaysPerWeek + 1 : CalendarViewLegacyDelegate.this.mDaysPerWeek;
            this.mNumCells = n2;
            this.mWeek = n;
            CalendarViewLegacyDelegate.this.mTempDate.setTimeInMillis(CalendarViewLegacyDelegate.this.mMinDate.getTimeInMillis());
            CalendarViewLegacyDelegate.this.mTempDate.add(3, this.mWeek);
            CalendarViewLegacyDelegate.this.mTempDate.setFirstDayOfWeek(CalendarViewLegacyDelegate.this.mFirstDayOfWeek);
            n = this.mNumCells;
            this.mDayNumbers = new String[n];
            this.mFocusDay = new boolean[n];
            n = 0;
            if (CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                this.mDayNumbers[0] = String.format(Locale.getDefault(), "%d", CalendarViewLegacyDelegate.this.mTempDate.get(3));
                n = 0 + 1;
            }
            int n4 = CalendarViewLegacyDelegate.this.mFirstDayOfWeek;
            n2 = CalendarViewLegacyDelegate.this.mTempDate.get(7);
            CalendarViewLegacyDelegate.this.mTempDate.add(5, n4 - n2);
            this.mFirstDay = (Calendar)CalendarViewLegacyDelegate.this.mTempDate.clone();
            this.mMonthOfFirstWeekDay = CalendarViewLegacyDelegate.this.mTempDate.get(2);
            this.mHasUnfocusedDay = true;
            while (n < this.mNumCells) {
                bl = CalendarViewLegacyDelegate.this.mTempDate.get(2) == n3;
                this.mFocusDay[n] = bl;
                this.mHasFocusedDay |= bl;
                int n5 = this.mHasUnfocusedDay;
                n2 = !bl ? 1 : 0;
                this.mHasUnfocusedDay = n5 & n2;
                this.mDayNumbers[n] = !CalendarViewLegacyDelegate.this.mTempDate.before((Object)CalendarViewLegacyDelegate.this.mMinDate) && !CalendarViewLegacyDelegate.this.mTempDate.after((Object)CalendarViewLegacyDelegate.this.mMaxDate) ? String.format(Locale.getDefault(), "%d", CalendarViewLegacyDelegate.this.mTempDate.get(5)) : "";
                CalendarViewLegacyDelegate.this.mTempDate.add(5, 1);
                ++n;
            }
            if (CalendarViewLegacyDelegate.this.mTempDate.get(5) == 1) {
                CalendarViewLegacyDelegate.this.mTempDate.add(5, -1);
            }
            this.mLastWeekDayMonth = CalendarViewLegacyDelegate.this.mTempDate.get(2);
            this.updateSelectionPositions();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            this.drawBackground(canvas);
            this.drawWeekNumbersAndDates(canvas);
            this.drawWeekSeparators(canvas);
            this.drawSelectedDateVerticalBars(canvas);
        }

        @Override
        protected void onMeasure(int n, int n2) {
            this.mHeight = (CalendarViewLegacyDelegate.this.mListView.getHeight() - CalendarViewLegacyDelegate.this.mListView.getPaddingTop() - CalendarViewLegacyDelegate.this.mListView.getPaddingBottom()) / CalendarViewLegacyDelegate.this.mShownWeekCount;
            this.setMeasuredDimension(View.MeasureSpec.getSize(n), this.mHeight);
        }

        @Override
        protected void onSizeChanged(int n, int n2, int n3, int n4) {
            this.mWidth = n;
            this.updateSelectionPositions();
        }
    }

    private class WeeksAdapter
    extends BaseAdapter
    implements View.OnTouchListener {
        private int mFocusedMonth;
        private GestureDetector mGestureDetector;
        private final Calendar mSelectedDate = Calendar.getInstance();
        private int mSelectedWeek;
        private int mTotalWeekCount;

        public WeeksAdapter(Context context) {
            CalendarViewLegacyDelegate.this.mContext = context;
            this.mGestureDetector = new GestureDetector(CalendarViewLegacyDelegate.this.mContext, new CalendarGestureListener());
            this.init();
        }

        private void init() {
            this.mSelectedWeek = CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            CalendarViewLegacyDelegate calendarViewLegacyDelegate = CalendarViewLegacyDelegate.this;
            this.mTotalWeekCount = calendarViewLegacyDelegate.getWeeksSinceMinDate(calendarViewLegacyDelegate.mMaxDate);
            if (CalendarViewLegacyDelegate.this.mMinDate.get(7) != CalendarViewLegacyDelegate.this.mFirstDayOfWeek || CalendarViewLegacyDelegate.this.mMaxDate.get(7) != CalendarViewLegacyDelegate.this.mFirstDayOfWeek) {
                ++this.mTotalWeekCount;
            }
            this.notifyDataSetChanged();
        }

        private void onDateTapped(Calendar calendar) {
            this.setSelectedDay(calendar);
            CalendarViewLegacyDelegate.this.setMonthDisplayed(calendar);
        }

        @Override
        public int getCount() {
            return this.mTotalWeekCount;
        }

        @Override
        public Object getItem(int n) {
            return null;
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        public Calendar getSelectedDay() {
            return this.mSelectedDate;
        }

        @Override
        public View getView(int n, View object, ViewGroup viewGroup) {
            if (object != null) {
                object = (WeekView)object;
            } else {
                object = CalendarViewLegacyDelegate.this;
                object = (CalendarViewLegacyDelegate)object.new WeekView(((CalendarViewLegacyDelegate)object).mContext);
                ((View)object).setLayoutParams(new AbsListView.LayoutParams(-2, -2));
                ((View)object).setClickable(true);
                ((View)object).setOnTouchListener(this);
            }
            int n2 = this.mSelectedWeek == n ? this.mSelectedDate.get(7) : -1;
            ((WeekView)object).init(n, n2, this.mFocusedMonth);
            return object;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (CalendarViewLegacyDelegate.this.mListView.isEnabled() && this.mGestureDetector.onTouchEvent(motionEvent)) {
                if (!((WeekView)view).getDayFromLocation(motionEvent.getX(), CalendarViewLegacyDelegate.this.mTempDate)) {
                    return true;
                }
                if (!CalendarViewLegacyDelegate.this.mTempDate.before((Object)CalendarViewLegacyDelegate.this.mMinDate) && !CalendarViewLegacyDelegate.this.mTempDate.after((Object)CalendarViewLegacyDelegate.this.mMaxDate)) {
                    this.onDateTapped(CalendarViewLegacyDelegate.this.mTempDate);
                    return true;
                }
                return true;
            }
            return false;
        }

        public void setFocusMonth(int n) {
            if (this.mFocusedMonth == n) {
                return;
            }
            this.mFocusedMonth = n;
            this.notifyDataSetChanged();
        }

        public void setSelectedDay(Calendar calendar) {
            if (calendar.get(6) == this.mSelectedDate.get(6) && calendar.get(1) == this.mSelectedDate.get(1)) {
                return;
            }
            this.mSelectedDate.setTimeInMillis(calendar.getTimeInMillis());
            this.mSelectedWeek = CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            this.mFocusedMonth = this.mSelectedDate.get(2);
            this.notifyDataSetChanged();
        }

        class CalendarGestureListener
        extends GestureDetector.SimpleOnGestureListener {
            CalendarGestureListener() {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        }

    }

}

