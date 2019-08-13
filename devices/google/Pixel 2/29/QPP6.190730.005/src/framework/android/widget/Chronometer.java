/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.MeasureFormat
 *  android.icu.text.MeasureFormat$FormatWidth
 *  android.icu.util.Measure
 *  android.icu.util.MeasureUnit
 *  android.icu.util.TimeUnit
 */
package android.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Locale;

@RemoteViews.RemoteView
public class Chronometer
extends TextView {
    private static final int HOUR_IN_SEC = 3600;
    private static final int MIN_IN_SEC = 60;
    private static final String TAG = "Chronometer";
    private long mBase;
    private boolean mCountDown;
    private String mFormat;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private Object[] mFormatterArgs = new Object[1];
    private Locale mFormatterLocale;
    private boolean mLogged;
    private long mNow;
    private OnChronometerTickListener mOnChronometerTickListener;
    private StringBuilder mRecycle = new StringBuilder(8);
    private boolean mRunning;
    private boolean mStarted;
    private final Runnable mTickRunnable = new Runnable(){

        @Override
        public void run() {
            if (Chronometer.this.mRunning) {
                Chronometer.this.updateText(SystemClock.elapsedRealtime());
                Chronometer.this.dispatchChronometerTick();
                Chronometer chronometer = Chronometer.this;
                chronometer.postDelayed(chronometer.mTickRunnable, 1000L);
            }
        }
    };
    private boolean mVisible;

    public Chronometer(Context context) {
        this(context, null, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Chronometer, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.Chronometer, attributeSet, typedArray, n, n2);
        this.setFormat(typedArray.getString(0));
        this.setCountDown(typedArray.getBoolean(1, false));
        typedArray.recycle();
        this.init();
    }

    private static String formatDuration(long l) {
        int n;
        int n2 = n = (int)(l / 1000L);
        if (n < 0) {
            n2 = -n;
        }
        int n3 = 0;
        int n4 = 0;
        n = n2;
        if (n2 >= 3600) {
            n3 = n2 / 3600;
            n = n2 - n3 * 3600;
        }
        n2 = n;
        if (n >= 60) {
            n4 = n / 60;
            n2 = n - n4 * 60;
        }
        ArrayList<Measure> arrayList = new ArrayList<Measure>();
        if (n3 > 0) {
            arrayList.add(new Measure((Number)n3, (MeasureUnit)MeasureUnit.HOUR));
        }
        if (n4 > 0) {
            arrayList.add(new Measure((Number)n4, (MeasureUnit)MeasureUnit.MINUTE));
        }
        arrayList.add(new Measure((Number)n2, (MeasureUnit)MeasureUnit.SECOND));
        return MeasureFormat.getInstance((Locale)Locale.getDefault(), (MeasureFormat.FormatWidth)MeasureFormat.FormatWidth.WIDE).formatMeasures(arrayList.toArray((T[])new Measure[arrayList.size()]));
    }

    private void init() {
        this.mBase = SystemClock.elapsedRealtime();
        this.updateText(this.mBase);
    }

    private void updateRunning() {
        boolean bl = this.mVisible && this.mStarted && this.isShown();
        if (bl != this.mRunning) {
            if (bl) {
                this.updateText(SystemClock.elapsedRealtime());
                this.dispatchChronometerTick();
                this.postDelayed(this.mTickRunnable, 1000L);
            } else {
                this.removeCallbacks(this.mTickRunnable);
            }
            this.mRunning = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateText(long l) {
        synchronized (this) {
            Object object;
            block9 : {
                this.mNow = l;
                l = this.mCountDown ? this.mBase - l : (l -= this.mBase);
                long l2 = l / 1000L;
                boolean bl = false;
                l = l2;
                if (l2 < 0L) {
                    l = -l2;
                    bl = true;
                }
                object = DateUtils.formatElapsedTime(this.mRecycle, l);
                String string2 = object;
                if (bl) {
                    string2 = this.getResources().getString(17040454, object);
                }
                object = string2;
                if (this.mFormat != null) {
                    Locale locale = Locale.getDefault();
                    if (this.mFormatter == null || !locale.equals(this.mFormatterLocale)) {
                        this.mFormatterLocale = locale;
                        this.mFormatter = object = new Formatter(this.mFormatBuilder, locale);
                    }
                    this.mFormatBuilder.setLength(0);
                    this.mFormatterArgs[0] = string2;
                    try {
                        this.mFormatter.format(this.mFormat, this.mFormatterArgs);
                        object = this.mFormatBuilder.toString();
                    }
                    catch (IllegalFormatException illegalFormatException) {
                        object = string2;
                        if (this.mLogged) break block9;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Illegal format string: ");
                        ((StringBuilder)object).append(this.mFormat);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        this.mLogged = true;
                        object = string2;
                    }
                }
            }
            this.setText((CharSequence)object);
            return;
        }
    }

    void dispatchChronometerTick() {
        OnChronometerTickListener onChronometerTickListener = this.mOnChronometerTickListener;
        if (onChronometerTickListener != null) {
            onChronometerTickListener.onChronometerTick(this);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return Chronometer.class.getName();
    }

    public long getBase() {
        return this.mBase;
    }

    @Override
    public CharSequence getContentDescription() {
        return Chronometer.formatDuration(this.mNow - this.mBase);
    }

    public String getFormat() {
        return this.mFormat;
    }

    public OnChronometerTickListener getOnChronometerTickListener() {
        return this.mOnChronometerTickListener;
    }

    public boolean isCountDown() {
        return this.mCountDown;
    }

    public boolean isTheFinalCountDown() {
        try {
            Context context = this.getContext();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/9jK-NcRmVcw"));
            context.startActivity(intent.addCategory("android.intent.category.BROWSABLE").addFlags(528384));
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        this.updateRunning();
    }

    @Override
    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        this.updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        boolean bl = n == 0;
        this.mVisible = bl;
        this.updateRunning();
    }

    @RemotableViewMethod
    public void setBase(long l) {
        this.mBase = l;
        this.dispatchChronometerTick();
        this.updateText(SystemClock.elapsedRealtime());
    }

    @RemotableViewMethod
    public void setCountDown(boolean bl) {
        this.mCountDown = bl;
        this.updateText(SystemClock.elapsedRealtime());
    }

    @RemotableViewMethod
    public void setFormat(String string2) {
        this.mFormat = string2;
        if (string2 != null && this.mFormatBuilder == null) {
            this.mFormatBuilder = new StringBuilder(string2.length() * 2);
        }
    }

    public void setOnChronometerTickListener(OnChronometerTickListener onChronometerTickListener) {
        this.mOnChronometerTickListener = onChronometerTickListener;
    }

    @RemotableViewMethod
    public void setStarted(boolean bl) {
        this.mStarted = bl;
        this.updateRunning();
    }

    public void start() {
        this.mStarted = true;
        this.updateRunning();
    }

    public void stop() {
        this.mStarted = false;
        this.updateRunning();
    }

    public static interface OnChronometerTickListener {
        public void onChronometerTick(Chronometer var1);
    }

}

