/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package android.webkit;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Calendar;
import java.util.Locale;
import libcore.icu.LocaleData;

public class DateSorter {
    public static final int DAY_COUNT = 5;
    private static final String LOGTAG = "webkit";
    private static final int NUM_DAYS_AGO = 7;
    private long[] mBins = new long[4];
    private String[] mLabels = new String[5];

    public DateSorter(Context context) {
        Resources resources = context.getResources();
        Object object = Calendar.getInstance();
        this.beginningOfDay((Calendar)object);
        this.mBins[0] = ((Calendar)object).getTimeInMillis();
        ((Calendar)object).add(6, -1);
        this.mBins[1] = ((Calendar)object).getTimeInMillis();
        ((Calendar)object).add(6, -6);
        this.mBins[2] = ((Calendar)object).getTimeInMillis();
        ((Calendar)object).add(6, 7);
        ((Calendar)object).add(2, -1);
        this.mBins[3] = ((Calendar)object).getTimeInMillis();
        Locale locale = resources.getConfiguration().locale;
        object = locale;
        if (locale == null) {
            object = Locale.getDefault();
        }
        object = LocaleData.get((Locale)object);
        this.mLabels[0] = ((LocaleData)object).today;
        this.mLabels[1] = ((LocaleData)object).yesterday;
        object = resources.getQuantityString(18153492, 7);
        this.mLabels[2] = String.format((String)object, 7);
        this.mLabels[3] = context.getString(17040227);
        this.mLabels[4] = context.getString(17040525);
    }

    private void beginningOfDay(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }

    public long getBoundary(int n) {
        int n2;
        block5 : {
            block4 : {
                if (n < 0) break block4;
                n2 = n;
                if (n <= 4) break block5;
            }
            n2 = 0;
        }
        if (n2 == 4) {
            return Long.MIN_VALUE;
        }
        return this.mBins[n2];
    }

    public int getIndex(long l) {
        for (int i = 0; i < 4; ++i) {
            if (l <= this.mBins[i]) continue;
            return i;
        }
        return 4;
    }

    public String getLabel(int n) {
        if (n >= 0 && n < 5) {
            return this.mLabels[n];
        }
        return "";
    }
}

