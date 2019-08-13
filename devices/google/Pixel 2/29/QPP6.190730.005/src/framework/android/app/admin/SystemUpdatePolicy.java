/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.admin.-$
 *  android.app.admin.-$$Lambda
 *  android.app.admin.-$$Lambda$SystemUpdatePolicy
 *  android.app.admin.-$$Lambda$SystemUpdatePolicy$cfrSWvZcAu30PIPvKA2LGQbmTew
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app.admin;

import android.annotation.SystemApi;
import android.app.admin.-$;
import android.app.admin.FreezePeriod;
import android.app.admin._$$Lambda$SystemUpdatePolicy$cfrSWvZcAu30PIPvKA2LGQbmTew;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class SystemUpdatePolicy
implements Parcelable {
    public static final Parcelable.Creator<SystemUpdatePolicy> CREATOR = new Parcelable.Creator<SystemUpdatePolicy>(){

        @Override
        public SystemUpdatePolicy createFromParcel(Parcel parcel) {
            SystemUpdatePolicy systemUpdatePolicy = new SystemUpdatePolicy();
            systemUpdatePolicy.mPolicyType = parcel.readInt();
            systemUpdatePolicy.mMaintenanceWindowStart = parcel.readInt();
            systemUpdatePolicy.mMaintenanceWindowEnd = parcel.readInt();
            int n = parcel.readInt();
            systemUpdatePolicy.mFreezePeriods.ensureCapacity(n);
            for (int i = 0; i < n; ++i) {
                MonthDay monthDay = MonthDay.of(parcel.readInt(), parcel.readInt());
                MonthDay monthDay2 = MonthDay.of(parcel.readInt(), parcel.readInt());
                systemUpdatePolicy.mFreezePeriods.add(new FreezePeriod(monthDay, monthDay2));
            }
            return systemUpdatePolicy;
        }

        public SystemUpdatePolicy[] newArray(int n) {
            return new SystemUpdatePolicy[n];
        }
    };
    static final int FREEZE_PERIOD_MAX_LENGTH = 90;
    static final int FREEZE_PERIOD_MIN_SEPARATION = 60;
    private static final String KEY_FREEZE_END = "end";
    private static final String KEY_FREEZE_START = "start";
    private static final String KEY_FREEZE_TAG = "freeze";
    private static final String KEY_INSTALL_WINDOW_END = "install_window_end";
    private static final String KEY_INSTALL_WINDOW_START = "install_window_start";
    private static final String KEY_POLICY_TYPE = "policy_type";
    private static final String TAG = "SystemUpdatePolicy";
    public static final int TYPE_INSTALL_AUTOMATIC = 1;
    public static final int TYPE_INSTALL_WINDOWED = 2;
    @SystemApi
    public static final int TYPE_PAUSE = 4;
    public static final int TYPE_POSTPONE = 3;
    private static final int TYPE_UNKNOWN = -1;
    private static final int WINDOW_BOUNDARY = 1440;
    private final ArrayList<FreezePeriod> mFreezePeriods = new ArrayList();
    private int mMaintenanceWindowEnd;
    private int mMaintenanceWindowStart;
    private int mPolicyType = -1;

    private SystemUpdatePolicy() {
    }

    public static SystemUpdatePolicy createAutomaticInstallPolicy() {
        SystemUpdatePolicy systemUpdatePolicy = new SystemUpdatePolicy();
        systemUpdatePolicy.mPolicyType = 1;
        return systemUpdatePolicy;
    }

    public static SystemUpdatePolicy createPostponeInstallPolicy() {
        SystemUpdatePolicy systemUpdatePolicy = new SystemUpdatePolicy();
        systemUpdatePolicy.mPolicyType = 3;
        return systemUpdatePolicy;
    }

    public static SystemUpdatePolicy createWindowedInstallPolicy(int n, int n2) {
        if (n >= 0 && n < 1440 && n2 >= 0 && n2 < 1440) {
            SystemUpdatePolicy systemUpdatePolicy = new SystemUpdatePolicy();
            systemUpdatePolicy.mPolicyType = 2;
            systemUpdatePolicy.mMaintenanceWindowStart = n;
            systemUpdatePolicy.mMaintenanceWindowEnd = n2;
            return systemUpdatePolicy;
        }
        throw new IllegalArgumentException("startTime and endTime must be inside [0, 1440)");
    }

    private static long dateToMillis(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private InstallationOption getInstallationOptionRegardlessFreezeAt(long l) {
        int n = this.mPolicyType;
        if (n != 1 && n != 3) {
            if (n == 2) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(l);
                long l2 = TimeUnit.HOURS.toMillis(calendar.get(11)) + TimeUnit.MINUTES.toMillis(calendar.get(12)) + TimeUnit.SECONDS.toMillis(calendar.get(13)) + (long)calendar.get(14);
                long l3 = TimeUnit.MINUTES.toMillis(this.mMaintenanceWindowStart);
                l = TimeUnit.MINUTES.toMillis(this.mMaintenanceWindowEnd);
                long l4 = TimeUnit.DAYS.toMillis(1L);
                if (l3 <= l2 && l2 <= l || l3 > l && (l3 <= l2 || l2 <= l)) {
                    return new InstallationOption(1, (l - l2 + l4) % l4);
                }
                return new InstallationOption(4, (l3 - l2 + l4) % l4);
            }
            throw new RuntimeException("Unknown policy type");
        }
        return new InstallationOption(this.mPolicyType, Long.MAX_VALUE);
    }

    static /* synthetic */ String lambda$toString$0(FreezePeriod freezePeriod) {
        return freezePeriod.toString();
    }

    private static LocalDate millisToDate(long l) {
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SystemUpdatePolicy restoreFromXml(XmlPullParser xmlPullParser) {
        try {
            SystemUpdatePolicy systemUpdatePolicy = new SystemUpdatePolicy();
            Object object = xmlPullParser.getAttributeValue(null, KEY_POLICY_TYPE);
            if (object == null) return null;
            {
                int n;
                systemUpdatePolicy.mPolicyType = Integer.parseInt((String)object);
                object = xmlPullParser.getAttributeValue(null, KEY_INSTALL_WINDOW_START);
                if (object != null) {
                    systemUpdatePolicy.mMaintenanceWindowStart = Integer.parseInt((String)object);
                }
                if ((object = xmlPullParser.getAttributeValue(null, KEY_INSTALL_WINDOW_END)) != null) {
                    systemUpdatePolicy.mMaintenanceWindowEnd = Integer.parseInt((String)object);
                }
                int n2 = xmlPullParser.getDepth();
                while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
                    if (n == 3 || n == 4 || !xmlPullParser.getName().equals(KEY_FREEZE_TAG)) continue;
                    ArrayList<FreezePeriod> arrayList = systemUpdatePolicy.mFreezePeriods;
                    object = new FreezePeriod(MonthDay.parse(xmlPullParser.getAttributeValue(null, KEY_FREEZE_START)), MonthDay.parse(xmlPullParser.getAttributeValue(null, KEY_FREEZE_END)));
                    arrayList.add((FreezePeriod)object);
                }
                return systemUpdatePolicy;
            }
        }
        catch (IOException | NumberFormatException | XmlPullParserException throwable) {
            Log.w(TAG, "Load xml failed", throwable);
        }
        return null;
    }

    private static LocalDate roundUpLeapDay(LocalDate localDate) {
        if (localDate.isLeapYear() && localDate.getMonthValue() == 2 && localDate.getDayOfMonth() == 28) {
            return localDate.plusDays(1L);
        }
        return localDate;
    }

    private long timeUntilNextFreezePeriod(long l) {
        List<FreezePeriod> list;
        Object object;
        Object object2;
        LocalDate localDate;
        block2 : {
            list = FreezePeriod.canonicalizePeriods(this.mFreezePeriods);
            localDate = SystemUpdatePolicy.millisToDate(l);
            object2 = null;
            Iterator<FreezePeriod> iterator = list.iterator();
            do {
                object = object2;
                if (!iterator.hasNext()) break block2;
                object = iterator.next();
                if (!((FreezePeriod)object).after(localDate)) continue;
                object = (LocalDate)object.toCurrentOrFutureRealDates((LocalDate)localDate).first;
                break block2;
            } while (!((FreezePeriod)object).contains(localDate));
            throw new IllegalArgumentException("Given date is inside a freeze period");
        }
        object2 = object;
        if (object == null) {
            object2 = (LocalDate)list.get((int)0).toCurrentOrFutureRealDates((LocalDate)localDate).first;
        }
        return SystemUpdatePolicy.dateToMillis((LocalDate)object2) - l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Pair<LocalDate, LocalDate> getCurrentFreezePeriod(LocalDate localDate) {
        for (FreezePeriod freezePeriod : this.mFreezePeriods) {
            if (!freezePeriod.contains(localDate)) continue;
            return freezePeriod.toCurrentOrFutureRealDates(localDate);
        }
        return null;
    }

    public List<FreezePeriod> getFreezePeriods() {
        return Collections.unmodifiableList(this.mFreezePeriods);
    }

    public int getInstallWindowEnd() {
        if (this.mPolicyType == 2) {
            return this.mMaintenanceWindowEnd;
        }
        return -1;
    }

    public int getInstallWindowStart() {
        if (this.mPolicyType == 2) {
            return this.mMaintenanceWindowStart;
        }
        return -1;
    }

    @SystemApi
    public InstallationOption getInstallationOptionAt(long l) {
        Pair<LocalDate, LocalDate> pair = this.getCurrentFreezePeriod(SystemUpdatePolicy.millisToDate(l));
        if (pair != null) {
            return new InstallationOption(4, SystemUpdatePolicy.dateToMillis(SystemUpdatePolicy.roundUpLeapDay((LocalDate)pair.second).plusDays(1L)) - l);
        }
        pair = this.getInstallationOptionRegardlessFreezeAt(l);
        if (this.mFreezePeriods.size() > 0) {
            ((InstallationOption)((Object)pair)).limitEffectiveTime(this.timeUntilNextFreezePeriod(l));
        }
        return pair;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public boolean isValid() {
        try {
            this.validateType();
            this.validateFreezePeriods();
            return true;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.attribute(null, KEY_POLICY_TYPE, Integer.toString(this.mPolicyType));
        xmlSerializer.attribute(null, KEY_INSTALL_WINDOW_START, Integer.toString(this.mMaintenanceWindowStart));
        xmlSerializer.attribute(null, KEY_INSTALL_WINDOW_END, Integer.toString(this.mMaintenanceWindowEnd));
        for (int i = 0; i < this.mFreezePeriods.size(); ++i) {
            FreezePeriod freezePeriod = this.mFreezePeriods.get(i);
            xmlSerializer.startTag(null, KEY_FREEZE_TAG);
            xmlSerializer.attribute(null, KEY_FREEZE_START, freezePeriod.getStart().toString());
            xmlSerializer.attribute(null, KEY_FREEZE_END, freezePeriod.getEnd().toString());
            xmlSerializer.endTag(null, KEY_FREEZE_TAG);
        }
    }

    public SystemUpdatePolicy setFreezePeriods(List<FreezePeriod> list) {
        FreezePeriod.validatePeriods(list);
        this.mFreezePeriods.clear();
        this.mFreezePeriods.addAll(list);
        return this;
    }

    public String toString() {
        return String.format("SystemUpdatePolicy (type: %d, windowStart: %d, windowEnd: %d, freezes: [%s])", this.mPolicyType, this.mMaintenanceWindowStart, this.mMaintenanceWindowEnd, this.mFreezePeriods.stream().map(_$$Lambda$SystemUpdatePolicy$cfrSWvZcAu30PIPvKA2LGQbmTew.INSTANCE).collect(Collectors.joining(",")));
    }

    public void validateAgainstPreviousFreezePeriod(LocalDate localDate, LocalDate localDate2, LocalDate localDate3) {
        FreezePeriod.validateAgainstPreviousFreezePeriod(this.mFreezePeriods, localDate, localDate2, localDate3);
    }

    public void validateFreezePeriods() {
        FreezePeriod.validatePeriods(this.mFreezePeriods);
    }

    public void validateType() {
        int n = this.mPolicyType;
        if (n != 1 && n != 3) {
            if (n == 2) {
                n = this.mMaintenanceWindowStart;
                if (n >= 0 && n < 1440 && (n = this.mMaintenanceWindowEnd) >= 0 && n < 1440) {
                    return;
                }
                throw new IllegalArgumentException("Invalid maintenance window");
            }
            throw new IllegalArgumentException("Invalid system update policy type.");
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeInt(this.mMaintenanceWindowStart);
        parcel.writeInt(this.mMaintenanceWindowEnd);
        int n2 = this.mFreezePeriods.size();
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            FreezePeriod freezePeriod = this.mFreezePeriods.get(n);
            parcel.writeInt(freezePeriod.getStart().getMonthValue());
            parcel.writeInt(freezePeriod.getStart().getDayOfMonth());
            parcel.writeInt(freezePeriod.getEnd().getMonthValue());
            parcel.writeInt(freezePeriod.getEnd().getDayOfMonth());
        }
    }

    @SystemApi
    public static class InstallationOption {
        private long mEffectiveTime;
        private final int mType;

        InstallationOption(int n, long l) {
            this.mType = n;
            this.mEffectiveTime = l;
        }

        public long getEffectiveTime() {
            return this.mEffectiveTime;
        }

        public int getType() {
            return this.mType;
        }

        protected void limitEffectiveTime(long l) {
            this.mEffectiveTime = Long.min(this.mEffectiveTime, l);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface InstallationOptionType {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface SystemUpdatePolicyType {
    }

    public static final class ValidationFailedException
    extends IllegalArgumentException
    implements Parcelable {
        public static final Parcelable.Creator<ValidationFailedException> CREATOR = new Parcelable.Creator<ValidationFailedException>(){

            @Override
            public ValidationFailedException createFromParcel(Parcel parcel) {
                return new ValidationFailedException(parcel.readInt(), parcel.readString());
            }

            public ValidationFailedException[] newArray(int n) {
                return new ValidationFailedException[n];
            }
        };
        public static final int ERROR_COMBINED_FREEZE_PERIOD_TOO_CLOSE = 6;
        public static final int ERROR_COMBINED_FREEZE_PERIOD_TOO_LONG = 5;
        public static final int ERROR_DUPLICATE_OR_OVERLAP = 2;
        public static final int ERROR_NEW_FREEZE_PERIOD_TOO_CLOSE = 4;
        public static final int ERROR_NEW_FREEZE_PERIOD_TOO_LONG = 3;
        public static final int ERROR_NONE = 0;
        public static final int ERROR_UNKNOWN = 1;
        private final int mErrorCode;

        private ValidationFailedException(int n, String string2) {
            super(string2);
            this.mErrorCode = n;
        }

        public static ValidationFailedException combinedPeriodTooClose(String string2) {
            return new ValidationFailedException(6, string2);
        }

        public static ValidationFailedException combinedPeriodTooLong(String string2) {
            return new ValidationFailedException(5, string2);
        }

        public static ValidationFailedException duplicateOrOverlapPeriods() {
            return new ValidationFailedException(2, "Found duplicate or overlapping periods");
        }

        public static ValidationFailedException freezePeriodTooClose(String string2) {
            return new ValidationFailedException(4, string2);
        }

        public static ValidationFailedException freezePeriodTooLong(String string2) {
            return new ValidationFailedException(3, string2);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mErrorCode);
            parcel.writeString(this.getMessage());
        }

        @Retention(value=RetentionPolicy.SOURCE)
        static @interface ValidationFailureType {
        }

    }

}

