/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.TimeZone
 *  android.text.TextUtils
 *  libcore.timezone.CountryTimeZones
 *  libcore.timezone.CountryTimeZones$OffsetResult
 *  libcore.timezone.TimeZoneFinder
 */
package com.android.internal.telephony;

import android.text.TextUtils;
import com.android.internal.telephony.NitzData;
import java.util.Date;
import java.util.TimeZone;
import libcore.timezone.CountryTimeZones;
import libcore.timezone.TimeZoneFinder;

public class TimeZoneLookupHelper {
    private static final int MS_PER_HOUR = 3600000;
    private CountryTimeZones mLastCountryTimeZones;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CountryTimeZones getCountryTimeZones(String string) {
        synchronized (this) {
            if (this.mLastCountryTimeZones != null && this.mLastCountryTimeZones.isForCountryCode(string)) {
                return this.mLastCountryTimeZones;
            }
            string = TimeZoneFinder.getInstance().lookupCountryTimeZones(string);
            if (string == null) return string;
            this.mLastCountryTimeZones = string;
            return string;
        }
    }

    static TimeZone guessZoneByNitzStatic(NitzData object) {
        object = (object = TimeZoneLookupHelper.lookupByNitzStatic((NitzData)object)) != null ? TimeZone.getTimeZone(((OffsetResult)object).zoneId) : null;
        return object;
    }

    private static OffsetResult lookupByInstantOffsetDst(long l, int n, boolean bl) {
        int n2;
        boolean bl2;
        int n3 = n2 = n;
        if (bl) {
            n3 = n2 - 3600000;
        }
        String[] arrstring = TimeZone.getAvailableIDs(n3);
        TimeZone timeZone = null;
        Date date = new Date(l);
        boolean bl3 = true;
        n2 = arrstring.length;
        n3 = 0;
        do {
            bl2 = bl3;
            if (n3 >= n2) break;
            TimeZone timeZone2 = TimeZone.getTimeZone(arrstring[n3]);
            TimeZone timeZone3 = timeZone;
            if (timeZone2.getOffset(l) == n) {
                timeZone3 = timeZone;
                if (timeZone2.inDaylightTime(date) == bl) {
                    if (timeZone == null) {
                        timeZone3 = timeZone2;
                    } else {
                        bl2 = false;
                        break;
                    }
                }
            }
            ++n3;
            timeZone = timeZone3;
        } while (true);
        if (timeZone == null) {
            return null;
        }
        return new OffsetResult(timeZone.getID(), bl2);
    }

    private static OffsetResult lookupByNitzStatic(NitzData object) {
        int n = ((NitzData)object).getLocalOffsetMillis();
        boolean bl = ((NitzData)object).isDst();
        long l = ((NitzData)object).getCurrentTimeInMillis();
        OffsetResult offsetResult = TimeZoneLookupHelper.lookupByInstantOffsetDst(l, n, bl);
        object = offsetResult;
        if (offsetResult == null) {
            object = TimeZoneLookupHelper.lookupByInstantOffsetDst(l, n, bl ^ true);
        }
        return object;
    }

    public boolean countryUsesUtc(String string, long l) {
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        string = this.getCountryTimeZones(string);
        bl = bl2;
        if (string != null) {
            bl = bl2;
            if (string.hasUtcZone(l)) {
                bl = true;
            }
        }
        return bl;
    }

    public CountryResult lookupByCountry(String string, long l) {
        if ((string = this.getCountryTimeZones(string)) == null) {
            return null;
        }
        if (string.getDefaultTimeZoneId() == null) {
            return null;
        }
        return new CountryResult(string.getDefaultTimeZoneId(), string.isDefaultOkForCountryTimeZoneDetection(l), l);
    }

    public OffsetResult lookupByNitz(NitzData nitzData) {
        return TimeZoneLookupHelper.lookupByNitzStatic(nitzData);
    }

    public OffsetResult lookupByNitzCountry(NitzData nitzData, String string) {
        if ((string = this.getCountryTimeZones(string)) == null) {
            return null;
        }
        android.icu.util.TimeZone timeZone = android.icu.util.TimeZone.getDefault();
        if ((nitzData = string.lookupByOffsetWithBias(nitzData.getLocalOffsetMillis(), nitzData.isDst(), nitzData.getCurrentTimeInMillis(), timeZone)) == null) {
            return null;
        }
        return new OffsetResult(((CountryTimeZones.OffsetResult)nitzData).mTimeZone.getID(), ((CountryTimeZones.OffsetResult)nitzData).mOneMatch);
    }

    public static final class CountryResult {
        public final boolean allZonesHaveSameOffset;
        public final long whenMillis;
        public final String zoneId;

        public CountryResult(String string, boolean bl, long l) {
            this.zoneId = string;
            this.allZonesHaveSameOffset = bl;
            this.whenMillis = l;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (CountryResult)object;
                if (this.allZonesHaveSameOffset != ((CountryResult)object).allZonesHaveSameOffset) {
                    return false;
                }
                if (this.whenMillis != ((CountryResult)object).whenMillis) {
                    return false;
                }
                return this.zoneId.equals(((CountryResult)object).zoneId);
            }
            return false;
        }

        public int hashCode() {
            int n = this.zoneId.hashCode();
            int n2 = this.allZonesHaveSameOffset;
            long l = this.whenMillis;
            return (n * 31 + n2) * 31 + (int)(l ^ l >>> 32);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CountryResult{zoneId='");
            stringBuilder.append(this.zoneId);
            stringBuilder.append('\'');
            stringBuilder.append(", allZonesHaveSameOffset=");
            stringBuilder.append(this.allZonesHaveSameOffset);
            stringBuilder.append(", whenMillis=");
            stringBuilder.append(this.whenMillis);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static final class OffsetResult {
        public final boolean isOnlyMatch;
        public final String zoneId;

        public OffsetResult(String string, boolean bl) {
            this.zoneId = string;
            this.isOnlyMatch = bl;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (OffsetResult)object;
                if (this.isOnlyMatch != ((OffsetResult)object).isOnlyMatch) {
                    return false;
                }
                return this.zoneId.equals(((OffsetResult)object).zoneId);
            }
            return false;
        }

        public int hashCode() {
            return this.zoneId.hashCode() * 31 + this.isOnlyMatch;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Result{zoneId='");
            stringBuilder.append(this.zoneId);
            stringBuilder.append('\'');
            stringBuilder.append(", isOnlyMatch=");
            stringBuilder.append(this.isOnlyMatch);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

