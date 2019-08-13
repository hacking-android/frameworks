/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 */
package com.android.internal.telephony;

import android.telephony.Rlog;
import com.android.internal.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class NitzData {
    private static final String LOG_TAG = "SST";
    private static final int MAX_NITZ_YEAR = 2037;
    private static final int MS_PER_HOUR = 3600000;
    private static final int MS_PER_QUARTER_HOUR = 900000;
    private final long mCurrentTimeMillis;
    private final Integer mDstOffset;
    private final TimeZone mEmulatorHostTimeZone;
    private final String mOriginalString;
    private final int mZoneOffset;

    private NitzData(String string, int n, Integer n2, long l, TimeZone timeZone) {
        if (string != null) {
            this.mOriginalString = string;
            this.mZoneOffset = n;
            this.mDstOffset = n2;
            this.mCurrentTimeMillis = l;
            this.mEmulatorHostTimeZone = timeZone;
            return;
        }
        throw new NullPointerException("originalString==null");
    }

    public static NitzData createForTests(int n, Integer n2, long l, TimeZone timeZone) {
        return new NitzData("Test data", n, n2, l, timeZone);
    }

    public static NitzData parse(String string) {
        int n;
        Calendar calendar;
        int n2;
        Object object;
        Object object2;
        block13 : {
            block12 : {
                int n3;
                block11 : {
                    block10 : {
                        try {
                            calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                            calendar.clear();
                            n2 = 0;
                        }
                        catch (RuntimeException runtimeException) {
                            object = new StringBuilder();
                            object.append("NITZ: Parsing NITZ time ");
                            object.append(string);
                            object.append(" ex=");
                            object.append(runtimeException);
                            Rlog.e((String)LOG_TAG, (String)object.toString());
                            return null;
                        }
                        calendar.set(16, 0);
                        object = string.split("[/:,+-]");
                        n = Integer.parseInt(object[0]) + 2000;
                        if (n <= 2037) break block10;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("NITZ year: ");
                        stringBuilder.append(n);
                        stringBuilder.append(" exceeds limit, skip NITZ time update");
                        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                        return null;
                    }
                    n3 = 1;
                    calendar.set(1, n);
                    calendar.set(2, Integer.parseInt(object[1]) - 1);
                    calendar.set(5, Integer.parseInt(object[2]));
                    calendar.set(10, Integer.parseInt(object[3]));
                    calendar.set(12, Integer.parseInt(object[4]));
                    calendar.set(13, Integer.parseInt(object[5]));
                    if (string.indexOf(45) != -1) break block11;
                    n2 = 1;
                }
                n = Integer.parseInt(object[6]);
                n2 = n2 != 0 ? n3 : -1;
                object2 = ((String[])object).length >= 8 ? Integer.valueOf(Integer.parseInt(object[7])) : null;
                if (object2 == null) break block12;
                object2 = (Integer)object2 * 3600000;
                break block13;
            }
            object2 = null;
        }
        object = ((String[])object).length >= 9 ? TimeZone.getTimeZone(object[8].replace('!', '/')) : null;
        object2 = new NitzData(string, n2 * n * 900000, (Integer)object2, calendar.getTimeInMillis(), (TimeZone)object);
        return object2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (NitzData)object;
            if (this.mZoneOffset != ((NitzData)object).mZoneOffset) {
                return false;
            }
            if (this.mCurrentTimeMillis != ((NitzData)object).mCurrentTimeMillis) {
                return false;
            }
            if (!this.mOriginalString.equals(((NitzData)object).mOriginalString)) {
                return false;
            }
            Serializable serializable = this.mDstOffset;
            if (serializable != null ? !((Integer)serializable).equals(((NitzData)object).mDstOffset) : ((NitzData)object).mDstOffset != null) {
                return false;
            }
            serializable = this.mEmulatorHostTimeZone;
            if (serializable != null) {
                bl = serializable.equals(((NitzData)object).mEmulatorHostTimeZone);
            } else if (((NitzData)object).mEmulatorHostTimeZone != null) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getCurrentTimeInMillis() {
        return this.mCurrentTimeMillis;
    }

    public Integer getDstAdjustmentMillis() {
        return this.mDstOffset;
    }

    public TimeZone getEmulatorHostTimeZone() {
        return this.mEmulatorHostTimeZone;
    }

    public int getLocalOffsetMillis() {
        return this.mZoneOffset;
    }

    public int hashCode() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        block0 : {
            n2 = this.mOriginalString.hashCode();
            n5 = this.mZoneOffset;
            Serializable serializable = this.mDstOffset;
            n4 = 0;
            n3 = serializable != null ? ((Integer)serializable).hashCode() : 0;
            long l = this.mCurrentTimeMillis;
            n = (int)(l ^ l >>> 32);
            serializable = this.mEmulatorHostTimeZone;
            if (serializable == null) break block0;
            n4 = serializable.hashCode();
        }
        return (((n2 * 31 + n5) * 31 + n3) * 31 + n) * 31 + n4;
    }

    public boolean isDst() {
        Integer n = this.mDstOffset;
        boolean bl = n != null && n != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NitzData{mOriginalString=");
        stringBuilder.append(this.mOriginalString);
        stringBuilder.append(", mZoneOffset=");
        stringBuilder.append(this.mZoneOffset);
        stringBuilder.append(", mDstOffset=");
        stringBuilder.append(this.mDstOffset);
        stringBuilder.append(", mCurrentTimeMillis=");
        stringBuilder.append(this.mCurrentTimeMillis);
        stringBuilder.append(", mEmulatorHostTimeZone=");
        stringBuilder.append(this.mEmulatorHostTimeZone);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

