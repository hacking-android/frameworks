/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DisplayContext
 *  android.icu.text.SimpleDateFormat
 *  android.icu.util.Calendar
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.MathUtils;
import android.util.StateSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import libcore.icu.LocaleData;

class SimpleMonthView
extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String MONTH_YEAR_FORMAT = "MMMMy";
    private static final int SELECTED_HIGHLIGHT_ALPHA = 176;
    private int mActivatedDay = -1;
    private final Calendar mCalendar;
    private int mCellWidth;
    private final NumberFormat mDayFormatter;
    private int mDayHeight;
    private final Paint mDayHighlightPaint = new Paint();
    private final Paint mDayHighlightSelectorPaint = new Paint();
    private int mDayOfWeekHeight;
    private final String[] mDayOfWeekLabels = new String[7];
    private final TextPaint mDayOfWeekPaint = new TextPaint();
    private int mDayOfWeekStart;
    private final TextPaint mDayPaint = new TextPaint();
    private final Paint mDaySelectorPaint = new Paint();
    private int mDaySelectorRadius;
    private ColorStateList mDayTextColor;
    private int mDaysInMonth;
    private final int mDesiredCellWidth;
    private final int mDesiredDayHeight;
    private final int mDesiredDayOfWeekHeight;
    private final int mDesiredDaySelectorRadius;
    private final int mDesiredMonthHeight;
    private int mEnabledDayEnd = 31;
    private int mEnabledDayStart = 1;
    private int mHighlightedDay = -1;
    private boolean mIsTouchHighlighted = false;
    private final Locale mLocale;
    private int mMonth;
    private int mMonthHeight;
    private final TextPaint mMonthPaint = new TextPaint();
    private String mMonthYearLabel;
    private OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private int mPreviouslyHighlightedDay = -1;
    private int mToday = -1;
    private final MonthViewTouchHelper mTouchHelper;
    private int mWeekStart = 1;
    private int mYear;

    public SimpleMonthView(Context context) {
        this(context, null);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public SimpleMonthView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SimpleMonthView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).getResources();
        this.mDesiredMonthHeight = ((Resources)object).getDimensionPixelSize(17105093);
        this.mDesiredDayOfWeekHeight = ((Resources)object).getDimensionPixelSize(17105088);
        this.mDesiredDayHeight = ((Resources)object).getDimensionPixelSize(17105087);
        this.mDesiredCellWidth = ((Resources)object).getDimensionPixelSize(17105092);
        this.mDesiredDaySelectorRadius = ((Resources)object).getDimensionPixelSize(17105090);
        this.mTouchHelper = new MonthViewTouchHelper(this);
        this.setAccessibilityDelegate(this.mTouchHelper);
        this.setImportantForAccessibility(1);
        this.mLocale = object.getConfiguration().locale;
        this.mCalendar = Calendar.getInstance((Locale)this.mLocale);
        this.mDayFormatter = NumberFormat.getIntegerInstance(this.mLocale);
        this.updateMonthYearLabel();
        this.updateDayOfWeekLabels();
        this.initPaints((Resources)object);
    }

    private ColorStateList applyTextAppearance(Paint paint, int n) {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(null, R.styleable.TextAppearance, 0, n);
        Object object = typedArray.getString(12);
        if (object != null) {
            paint.setTypeface(Typeface.create((String)object, 0));
        }
        paint.setTextSize(typedArray.getDimensionPixelSize(0, (int)paint.getTextSize()));
        object = typedArray.getColorStateList(3);
        if (object != null) {
            paint.setColor(((ColorStateList)object).getColorForState(ENABLED_STATE_SET, 0));
        }
        typedArray.recycle();
        return object;
    }

    private void drawDays(Canvas canvas) {
        TextPaint textPaint = this.mDayPaint;
        int n = this.mMonthHeight + this.mDayOfWeekHeight;
        int n2 = this.mDayHeight;
        int n3 = this.mCellWidth;
        float f = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int n4 = n2 / 2 + n;
        int n5 = this.findDayOffset();
        for (int i = 1; i <= this.mDaysInMonth; ++i) {
            float f2;
            Object object;
            float f3;
            int n6 = n3 * n5 + n3 / 2;
            if (this.isLayoutRtl()) {
                n6 = this.mPaddedWidth - n6;
            }
            int n7 = 0;
            boolean bl = this.isDayEnabled(i);
            if (bl) {
                n7 = 0 | 8;
            }
            int n8 = this.mActivatedDay;
            boolean bl2 = true;
            n8 = n8 == i ? 1 : 0;
            boolean bl3 = this.mHighlightedDay == i;
            if (n8 != 0) {
                object = bl3 ? this.mDayHighlightSelectorPaint : this.mDaySelectorPaint;
                f2 = n6;
                f3 = n4;
                n7 |= 32;
                canvas.drawCircle(f2, f3, this.mDaySelectorRadius, (Paint)object);
            } else if (bl3) {
                n7 |= 16;
                if (bl) {
                    float f4 = n6;
                    f2 = n4;
                    f3 = this.mDaySelectorRadius;
                    canvas.drawCircle(f4, f2, f3, this.mDayHighlightPaint);
                }
            }
            bl3 = this.mToday == i ? bl2 : false;
            if (bl3 && n8 == 0) {
                n7 = this.mDaySelectorPaint.getColor();
            } else {
                object = StateSet.get(n7);
                n7 = this.mDayTextColor.getColorForState((int[])object, 0);
            }
            textPaint.setColor(n7);
            canvas.drawText(this.mDayFormatter.format(i), n6, (float)n4 - f, textPaint);
            n6 = n5 + 1;
            n5 = n4;
            n7 = n6;
            if (n6 == 7) {
                n7 = 0;
                n5 = n4 + n2;
            }
            n4 = n5;
            n5 = n7;
        }
    }

    private void drawDaysOfWeek(Canvas canvas) {
        TextPaint textPaint = this.mDayOfWeekPaint;
        int n = this.mMonthHeight;
        int n2 = this.mDayOfWeekHeight;
        int n3 = this.mCellWidth;
        float f = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int n4 = n2 / 2;
        for (n2 = 0; n2 < 7; ++n2) {
            int n5 = n3 * n2 + n3 / 2;
            if (this.isLayoutRtl()) {
                n5 = this.mPaddedWidth - n5;
            }
            canvas.drawText(this.mDayOfWeekLabels[n2], n5, (float)(n4 + n) - f, textPaint);
        }
    }

    private void drawMonth(Canvas canvas) {
        float f = (float)this.mPaddedWidth / 2.0f;
        float f2 = this.mMonthPaint.ascent();
        float f3 = this.mMonthPaint.descent();
        f2 = ((float)this.mMonthHeight - (f2 + f3)) / 2.0f;
        canvas.drawText(this.mMonthYearLabel, f, f2, this.mMonthPaint);
    }

    private void ensureFocusedDay() {
        if (this.mHighlightedDay != -1) {
            return;
        }
        int n = this.mPreviouslyHighlightedDay;
        if (n != -1) {
            this.mHighlightedDay = n;
            return;
        }
        n = this.mActivatedDay;
        if (n != -1) {
            this.mHighlightedDay = n;
            return;
        }
        this.mHighlightedDay = 1;
    }

    private int findClosestColumn(Rect rect) {
        int n;
        block2 : {
            if (rect == null) {
                return 3;
            }
            if (this.mCellWidth == 0) {
                return 0;
            }
            n = MathUtils.constrain((rect.centerX() - this.mPaddingLeft) / this.mCellWidth, 0, 6);
            if (!this.isLayoutRtl()) break block2;
            n = 7 - n - 1;
        }
        return n;
    }

    private int findClosestRow(Rect object) {
        if (object == null) {
            return 3;
        }
        if (this.mDayHeight == 0) {
            return 0;
        }
        int n = ((Rect)object).centerY();
        object = this.mDayPaint;
        int n2 = this.mMonthHeight;
        int n3 = this.mDayOfWeekHeight;
        int n4 = this.mDayHeight;
        float f = (((Paint)object).ascent() + ((Paint)object).descent()) / 2.0f;
        int n5 = n4 / 2;
        n = Math.round((float)((int)((float)n - ((float)(n5 + (n2 + n3)) - f))) / (float)n4);
        n3 = this.findDayOffset() + this.mDaysInMonth;
        n2 = n3 / 7;
        n3 = n3 % 7 == 0 ? 1 : 0;
        return MathUtils.constrain(n, 0, n2 - n3);
    }

    private int findDayOffset() {
        int n = this.mDayOfWeekStart;
        int n2 = this.mWeekStart;
        int n3 = n - n2;
        if (n < n2) {
            return n3 + 7;
        }
        return n3;
    }

    private int getDayAtLocation(int n, int n2) {
        if ((n -= this.getPaddingLeft()) >= 0 && n < this.mPaddedWidth) {
            int n3 = this.mMonthHeight + this.mDayOfWeekHeight;
            if ((n2 -= this.getPaddingTop()) >= n3 && n2 < this.mPaddedHeight) {
                if (this.isLayoutRtl()) {
                    n = this.mPaddedWidth - n;
                }
                if (!this.isValidDayOfMonth(n = (n2 - n3) / this.mDayHeight * 7 + n * 7 / this.mPaddedWidth + 1 - this.findDayOffset())) {
                    return -1;
                }
                return n;
            }
            return -1;
        }
        return -1;
    }

    private static int getDaysInMonth(int n, int n2) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Invalid Month");
            }
            case 3: 
            case 5: 
            case 8: 
            case 10: {
                return 30;
            }
            case 1: {
                n = n2 % 4 == 0 ? 29 : 28;
                return n;
            }
            case 0: 
            case 2: 
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
        }
        return 31;
    }

    private void initPaints(Resources resources) {
        String string2 = resources.getString(17039826);
        String string3 = resources.getString(17039816);
        String string4 = resources.getString(17039817);
        int n = resources.getDimensionPixelSize(17105094);
        int n2 = resources.getDimensionPixelSize(17105089);
        int n3 = resources.getDimensionPixelSize(17105091);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize(n);
        this.mMonthPaint.setTypeface(Typeface.create(string2, 0));
        this.mMonthPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthPaint.setStyle(Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize(n2);
        this.mDayOfWeekPaint.setTypeface(Typeface.create(string3, 0));
        this.mDayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightSelectorPaint.setAntiAlias(true);
        this.mDayHighlightSelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize(n3);
        this.mDayPaint.setTypeface(Typeface.create(string4, 0));
        this.mDayPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPaint.setStyle(Paint.Style.FILL);
    }

    private boolean isDayEnabled(int n) {
        boolean bl = n >= this.mEnabledDayStart && n <= this.mEnabledDayEnd;
        return bl;
    }

    private boolean isFirstDayOfWeek(int n) {
        int n2 = this.findDayOffset();
        boolean bl = true;
        if ((n2 + n - 1) % 7 != 0) {
            bl = false;
        }
        return bl;
    }

    private boolean isLastDayOfWeek(int n) {
        boolean bl = (this.findDayOffset() + n) % 7 == 0;
        return bl;
    }

    private boolean isValidDayOfMonth(int n) {
        boolean bl = true;
        if (n < 1 || n > this.mDaysInMonth) {
            bl = false;
        }
        return bl;
    }

    private static boolean isValidDayOfWeek(int n) {
        boolean bl = true;
        if (n < 1 || n > 7) {
            bl = false;
        }
        return bl;
    }

    private static boolean isValidMonth(int n) {
        boolean bl = n >= 0 && n <= 11;
        return bl;
    }

    private boolean moveOneDay(boolean bl) {
        this.ensureFocusedDay();
        boolean bl2 = false;
        if (bl) {
            bl = bl2;
            if (!this.isLastDayOfWeek(this.mHighlightedDay)) {
                int n = this.mHighlightedDay;
                bl = bl2;
                if (n < this.mDaysInMonth) {
                    this.mHighlightedDay = n + 1;
                    bl = true;
                }
            }
        } else {
            bl = bl2;
            if (!this.isFirstDayOfWeek(this.mHighlightedDay)) {
                int n = this.mHighlightedDay;
                bl = bl2;
                if (n > 1) {
                    this.mHighlightedDay = n - 1;
                    bl = true;
                }
            }
        }
        return bl;
    }

    private boolean onDayClicked(int n) {
        if (this.isValidDayOfMonth(n) && this.isDayEnabled(n)) {
            if (this.mOnDayClickListener != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(this.mYear, this.mMonth, n);
                this.mOnDayClickListener.onDayClick(this, calendar);
            }
            this.mTouchHelper.sendEventForVirtualView(n, 1);
            return true;
        }
        return false;
    }

    private boolean sameDay(int n, Calendar calendar) {
        int n2 = this.mYear;
        boolean bl = true;
        if (n2 != calendar.get(1) || this.mMonth != calendar.get(2) || n != calendar.get(5)) {
            bl = false;
        }
        return bl;
    }

    private void updateDayOfWeekLabels() {
        String[] arrstring = LocaleData.get((Locale)this.mLocale).tinyWeekdayNames;
        for (int i = 0; i < 7; ++i) {
            this.mDayOfWeekLabels[i] = arrstring[(this.mWeekStart + i - 1) % 7 + 1];
        }
    }

    private void updateMonthYearLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern(this.mLocale, MONTH_YEAR_FORMAT), this.mLocale);
        simpleDateFormat.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        this.mMonthYearLabel = simpleDateFormat.format(this.mCalendar.getTime());
    }

    @Override
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean bl = this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
        return bl;
    }

    public boolean getBoundsForDay(int n, Rect rect) {
        if (!this.isValidDayOfMonth(n)) {
            return false;
        }
        int n2 = n - 1 + this.findDayOffset();
        n = n2 % 7;
        int n3 = this.mCellWidth;
        n = this.isLayoutRtl() ? this.getWidth() - this.getPaddingRight() - (n + 1) * n3 : this.getPaddingLeft() + n * n3;
        int n4 = n2 / 7;
        n2 = this.mDayHeight;
        int n5 = this.mMonthHeight;
        int n6 = this.mDayOfWeekHeight;
        n6 = this.getPaddingTop() + (n5 + n6) + n4 * n2;
        rect.set(n, n6, n + n3, n6 + n2);
        return true;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    @Override
    public void getFocusedRect(Rect rect) {
        int n = this.mHighlightedDay;
        if (n > 0) {
            this.getBoundsForDay(n, rect);
        } else {
            super.getFocusedRect(rect);
        }
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public String getMonthYearLabel() {
        return this.mMonthYearLabel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int n = this.getPaddingLeft();
        int n2 = this.getPaddingTop();
        canvas.translate(n, n2);
        this.drawMonth(canvas);
        this.drawDaysOfWeek(canvas);
        this.drawDays(canvas);
        canvas.translate(-n, -n2);
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        if (bl) {
            int n2 = this.findDayOffset();
            int n3 = 1;
            if (n != 17) {
                if (n != 33) {
                    if (n != 66) {
                        if (n == 130) {
                            n3 = this.findClosestColumn(rect) - n2 + 1;
                            if (n3 < 1) {
                                n3 += 7;
                            }
                            this.mHighlightedDay = n3;
                        }
                    } else {
                        int n4 = this.findClosestRow(rect);
                        if (n4 != 0) {
                            n3 = 1 + (n4 * 7 - n2);
                        }
                        this.mHighlightedDay = n3;
                    }
                } else {
                    n3 = this.findClosestColumn(rect);
                    int n5 = this.mDaysInMonth;
                    if ((n3 = n3 - n2 + (n2 + n5) / 7 * 7 + 1) > n5) {
                        n3 -= 7;
                    }
                    this.mHighlightedDay = n3;
                }
            } else {
                n3 = this.findClosestRow(rect);
                this.mHighlightedDay = Math.min(this.mDaysInMonth, (n3 + 1) * 7 - n2);
            }
            this.ensureFocusedDay();
            this.invalidate();
        }
        super.onFocusChanged(bl, n, rect);
    }

    @Override
    protected void onFocusLost() {
        if (!this.mIsTouchHighlighted) {
            this.mPreviouslyHighlightedDay = this.mHighlightedDay;
            this.mHighlightedDay = -1;
            this.invalidate();
        }
        super.onFocusLost();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onKeyDown(int var1_1, KeyEvent var2_2) {
        block21 : {
            block20 : {
                var3_3 = false;
                var4_4 = var2_2.getKeyCode();
                if (var4_4 == 61) break block20;
                if (var4_4 == 66) ** GOTO lbl-1000
                switch (var4_4) {
                    default: {
                        var5_5 = var3_3;
                        break block21;
                    }
                    case 22: {
                        var5_5 = var3_3;
                        if (var2_2.hasNoModifiers()) {
                            var5_5 = this.moveOneDay(this.isLayoutRtl() ^ true);
                        }
                        break block21;
                    }
                    case 21: {
                        var5_5 = var3_3;
                        if (var2_2.hasNoModifiers()) {
                            var5_5 = this.moveOneDay(this.isLayoutRtl());
                        }
                        break block21;
                    }
                    case 20: {
                        var5_5 = var3_3;
                        if (var2_2.hasNoModifiers()) {
                            this.ensureFocusedDay();
                            var4_4 = this.mHighlightedDay;
                            var5_5 = var3_3;
                            if (var4_4 <= this.mDaysInMonth - 7) {
                                this.mHighlightedDay = var4_4 + 7;
                                var5_5 = true;
                            }
                        }
                        break block21;
                    }
                    case 19: {
                        var5_5 = var3_3;
                        if (var2_2.hasNoModifiers()) {
                            this.ensureFocusedDay();
                            var4_4 = this.mHighlightedDay;
                            var5_5 = var3_3;
                            if (var4_4 > 7) {
                                this.mHighlightedDay = var4_4 - 7;
                                var5_5 = true;
                            }
                        }
                        break block21;
                    }
                    case 23: lbl-1000: // 2 sources:
                    {
                        var4_4 = this.mHighlightedDay;
                        var5_5 = var3_3;
                        if (var4_4 != -1) {
                            this.onDayClicked(var4_4);
                            return true;
                        }
                        break block21;
                    }
                }
            }
            var4_4 = 0;
            if (var2_2.hasNoModifiers()) {
                var4_4 = 2;
            } else if (var2_2.hasModifiers(1)) {
                var4_4 = 1;
            }
            var5_5 = var3_3;
            if (var4_4 != 0) {
                var6_6 = this.getParent();
                var7_7 = this;
                while ((var8_10 = var7_8.focusSearch(var4_4)) != null && var8_10 != this) {
                    var7_9 = var8_10;
                    if (var8_10.getParent() == var6_6) continue;
                }
                var5_5 = var3_3;
                if (var8_10 != null) {
                    var8_10.requestFocus();
                    return true;
                }
            }
        }
        if (var5_5 == false) return super.onKeyDown(var1_1, var2_2);
        this.invalidate();
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (!bl) {
            return;
        }
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingTop();
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingBottom();
        n = n3 - n - n7 - n5;
        n2 = n4 - n2 - n8 - n6;
        if (n != this.mPaddedWidth && n2 != this.mPaddedHeight) {
            this.mPaddedWidth = n;
            this.mPaddedHeight = n2;
            n = this.getMeasuredHeight();
            float f = (float)n2 / (float)(n - n6 - n8);
            n2 = (int)((float)this.mDesiredMonthHeight * f);
            n = this.mPaddedWidth / 7;
            this.mMonthHeight = n2;
            this.mDayOfWeekHeight = (int)((float)this.mDesiredDayOfWeekHeight * f);
            this.mDayHeight = (int)((float)this.mDesiredDayHeight * f);
            this.mCellWidth = n;
            n3 = Math.min(n5, n7);
            n2 = this.mDayHeight / 2;
            this.mDaySelectorRadius = Math.min(this.mDesiredDaySelectorRadius, Math.min((n /= 2) + n3, n2 + n8));
            this.mTouchHelper.invalidateRoot();
            return;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = this.mDesiredDayHeight;
        int n4 = this.mDesiredDayOfWeekHeight;
        int n5 = this.mDesiredMonthHeight;
        int n6 = this.getPaddingTop();
        int n7 = this.getPaddingBottom();
        this.setMeasuredDimension(SimpleMonthView.resolveSize(this.mDesiredCellWidth * 7 + this.getPaddingStart() + this.getPaddingEnd(), n), SimpleMonthView.resolveSize(n3 * 6 + n4 + n5 + n6 + n7, n2));
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (!this.isEnabled()) {
            return null;
        }
        if (this.getDayAtLocation((int)(motionEvent.getX() + 0.5f), (int)(motionEvent.getY() + 0.5f)) >= 0) {
            return PointerIcon.getSystemIcon(this.getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        block5 : {
            int n;
            int n2;
            int n3;
            block2 : {
                block4 : {
                    block3 : {
                        n = (int)(motionEvent.getX() + 0.5f);
                        n2 = (int)(motionEvent.getY() + 0.5f);
                        n3 = motionEvent.getAction();
                        if (n3 == 0) break block2;
                        if (n3 == 1) break block3;
                        if (n3 == 2) break block2;
                        if (n3 == 3) break block4;
                        break block5;
                    }
                    this.onDayClicked(this.getDayAtLocation(n, n2));
                }
                this.mHighlightedDay = -1;
                this.mIsTouchHighlighted = false;
                this.invalidate();
                break block5;
            }
            n = this.getDayAtLocation(n, n2);
            this.mIsTouchHighlighted = true;
            if (this.mHighlightedDay != n) {
                this.mHighlightedDay = n;
                this.mPreviouslyHighlightedDay = n;
                this.invalidate();
            }
            if (n3 == 0 && n < 0) {
                return false;
            }
        }
        return true;
    }

    void setDayHighlightColor(ColorStateList colorStateList) {
        int n = colorStateList.getColorForState(StateSet.get(24), 0);
        this.mDayHighlightPaint.setColor(n);
        this.invalidate();
    }

    public void setDayOfWeekTextAppearance(int n) {
        this.applyTextAppearance(this.mDayOfWeekPaint, n);
        this.invalidate();
    }

    void setDayOfWeekTextColor(ColorStateList colorStateList) {
        int n = colorStateList.getColorForState(ENABLED_STATE_SET, 0);
        this.mDayOfWeekPaint.setColor(n);
        this.invalidate();
    }

    void setDaySelectorColor(ColorStateList colorStateList) {
        int n = colorStateList.getColorForState(StateSet.get(40), 0);
        this.mDaySelectorPaint.setColor(n);
        this.mDayHighlightSelectorPaint.setColor(n);
        this.mDayHighlightSelectorPaint.setAlpha(176);
        this.invalidate();
    }

    public void setDayTextAppearance(int n) {
        ColorStateList colorStateList = this.applyTextAppearance(this.mDayPaint, n);
        if (colorStateList != null) {
            this.mDayTextColor = colorStateList;
        }
        this.invalidate();
    }

    void setDayTextColor(ColorStateList colorStateList) {
        this.mDayTextColor = colorStateList;
        this.invalidate();
    }

    public void setFirstDayOfWeek(int n) {
        this.mWeekStart = SimpleMonthView.isValidDayOfWeek(n) ? n : this.mCalendar.getFirstDayOfWeek();
        this.updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        this.invalidate();
    }

    void setMonthParams(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mActivatedDay = n;
        if (SimpleMonthView.isValidMonth(n2)) {
            this.mMonth = n2;
        }
        this.mYear = n3;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        this.mWeekStart = SimpleMonthView.isValidDayOfWeek(n4) ? n4 : this.mCalendar.getFirstDayOfWeek();
        Calendar calendar = Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = SimpleMonthView.getDaysInMonth(this.mMonth, this.mYear);
        for (n = 0; n < (n2 = this.mDaysInMonth); ++n) {
            n2 = n + 1;
            if (!this.sameDay(n2, calendar)) continue;
            this.mToday = n2;
        }
        this.mEnabledDayStart = MathUtils.constrain(n5, 1, n2);
        this.mEnabledDayEnd = MathUtils.constrain(n6, this.mEnabledDayStart, this.mDaysInMonth);
        this.updateMonthYearLabel();
        this.updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        this.invalidate();
    }

    public void setMonthTextAppearance(int n) {
        this.applyTextAppearance(this.mMonthPaint, n);
        this.invalidate();
    }

    void setMonthTextColor(ColorStateList colorStateList) {
        int n = colorStateList.getColorForState(ENABLED_STATE_SET, 0);
        this.mMonthPaint.setColor(n);
        this.invalidate();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    public void setSelectedDay(int n) {
        this.mActivatedDay = n;
        this.mTouchHelper.invalidateRoot();
        this.invalidate();
    }

    private class MonthViewTouchHelper
    extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar;
        private final Rect mTempRect;

        public MonthViewTouchHelper(View view) {
            super(view);
            this.mTempRect = new Rect();
            this.mTempCalendar = Calendar.getInstance();
        }

        private CharSequence getDayDescription(int n) {
            if (SimpleMonthView.this.isValidDayOfMonth(n)) {
                this.mTempCalendar.set(SimpleMonthView.this.mYear, SimpleMonthView.this.mMonth, n);
                return DateFormat.format((CharSequence)DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
            }
            return "";
        }

        private CharSequence getDayText(int n) {
            if (SimpleMonthView.this.isValidDayOfMonth(n)) {
                return SimpleMonthView.this.mDayFormatter.format(n);
            }
            return null;
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            int n = SimpleMonthView.this.getDayAtLocation((int)(f + 0.5f), (int)(0.5f + f2));
            if (n != -1) {
                return n;
            }
            return Integer.MIN_VALUE;
        }

        @Override
        protected void getVisibleVirtualViews(IntArray intArray) {
            for (int i = 1; i <= SimpleMonthView.this.mDaysInMonth; ++i) {
                intArray.add(i);
            }
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            if (n2 != 16) {
                return false;
            }
            return SimpleMonthView.this.onDayClicked(n);
        }

        @Override
        protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(this.getDayDescription(n));
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfo accessibilityNodeInfo) {
            if (!SimpleMonthView.this.getBoundsForDay(n, this.mTempRect)) {
                this.mTempRect.setEmpty();
                accessibilityNodeInfo.setContentDescription("");
                accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
                accessibilityNodeInfo.setVisibleToUser(false);
                return;
            }
            accessibilityNodeInfo.setText(this.getDayText(n));
            accessibilityNodeInfo.setContentDescription(this.getDayDescription(n));
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            boolean bl = SimpleMonthView.this.isDayEnabled(n);
            if (bl) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            }
            accessibilityNodeInfo.setEnabled(bl);
            if (n == SimpleMonthView.this.mActivatedDay) {
                accessibilityNodeInfo.setChecked(true);
            }
        }
    }

    public static interface OnDayClickListener {
        public void onDayClick(SimpleMonthView var1, Calendar var2);
    }

}

