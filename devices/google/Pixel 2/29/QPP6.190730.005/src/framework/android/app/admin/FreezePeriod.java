/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.SystemUpdatePolicy;
import android.util.Log;
import android.util.Pair;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FreezePeriod {
    static final int DAYS_IN_YEAR = 365;
    private static final int DUMMY_YEAR = 2001;
    private static final String TAG = "FreezePeriod";
    private final MonthDay mEnd;
    private final int mEndDay;
    private final MonthDay mStart;
    private final int mStartDay;

    private FreezePeriod(int n, int n2) {
        this.mStartDay = n;
        this.mStart = FreezePeriod.dayOfYearToMonthDay(n);
        this.mEndDay = n2;
        this.mEnd = FreezePeriod.dayOfYearToMonthDay(n2);
    }

    public FreezePeriod(MonthDay monthDay, MonthDay monthDay2) {
        this.mStart = monthDay;
        this.mStartDay = this.mStart.atYear(2001).getDayOfYear();
        this.mEnd = monthDay2;
        this.mEndDay = this.mEnd.atYear(2001).getDayOfYear();
    }

    static List<FreezePeriod> canonicalizePeriods(List<FreezePeriod> arrayList2) {
        int n;
        int n2;
        boolean[] arrbl = new boolean[365];
        for (FreezePeriod freezePeriod : arrayList2) {
            for (n = freezePeriod.mStartDay; n <= freezePeriod.getEffectiveEndDay(); ++n) {
                arrbl[(n - 1) % 365] = true;
            }
        }
        ArrayList<FreezePeriod> arrayList = new ArrayList<FreezePeriod>();
        n = 0;
        while ((n2 = n) < 365) {
            if (!arrbl[n2]) {
                n = n2 + 1;
                continue;
            }
            for (n = n2; n < 365 && arrbl[n]; ++n) {
            }
            arrayList.add(new FreezePeriod(n2 + 1, n));
        }
        n = arrayList.size() - 1;
        if (n > 0 && ((FreezePeriod)arrayList.get((int)n)).mEndDay == 365 && ((FreezePeriod)arrayList.get((int)0)).mStartDay == 1) {
            arrayList.set(n, new FreezePeriod(((FreezePeriod)arrayList.get((int)n)).mStartDay, ((FreezePeriod)arrayList.get((int)0)).mEndDay));
            arrayList.remove(0);
        }
        return arrayList;
    }

    private static int dayOfYearDisregardLeapYear(LocalDate localDate) {
        return localDate.withYear(2001).getDayOfYear();
    }

    private static MonthDay dayOfYearToMonthDay(int n) {
        LocalDate localDate = LocalDate.ofYearDay(2001, n);
        return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
    }

    public static int distanceWithoutLeapYear(LocalDate localDate, LocalDate localDate2) {
        return FreezePeriod.dayOfYearDisregardLeapYear(localDate) - FreezePeriod.dayOfYearDisregardLeapYear(localDate2) + (localDate.getYear() - localDate2.getYear()) * 365;
    }

    static void validateAgainstPreviousFreezePeriod(List<FreezePeriod> object, LocalDate serializable, LocalDate localDate, LocalDate object2) {
        block8 : {
            block9 : {
                long l;
                block12 : {
                    block11 : {
                        block10 : {
                            Pair<LocalDate, LocalDate> pair;
                            if (object.size() == 0 || serializable == null || localDate == null) break block8;
                            if (((LocalDate)serializable).isAfter((ChronoLocalDate)object2) || localDate.isAfter((ChronoLocalDate)object2)) {
                                pair = new StringBuilder();
                                ((StringBuilder)((Object)pair)).append("Previous period (");
                                ((StringBuilder)((Object)pair)).append(serializable);
                                ((StringBuilder)((Object)pair)).append(",");
                                ((StringBuilder)((Object)pair)).append(localDate);
                                ((StringBuilder)((Object)pair)).append(") is after current date ");
                                ((StringBuilder)((Object)pair)).append(object2);
                                Log.w(TAG, ((StringBuilder)((Object)pair)).toString());
                            }
                            object = FreezePeriod.canonicalizePeriods(object);
                            pair = object.get(0);
                            Iterator<FreezePeriod> iterator = object.iterator();
                            do {
                                object = pair;
                            } while (iterator.hasNext() && !((FreezePeriod)(object = iterator.next())).contains((LocalDate)object2) && ((FreezePeriod)object).mStartDay <= FreezePeriod.dayOfYearDisregardLeapYear((LocalDate)object2));
                            pair = ((FreezePeriod)object).toCurrentOrFutureRealDates((LocalDate)object2);
                            object = pair;
                            if (((LocalDate)object2).isAfter((ChronoLocalDate)pair.first)) {
                                object = new Pair<LocalDate, LocalDate>((LocalDate)object2, (LocalDate)pair.second);
                            }
                            if (((LocalDate)((Pair)object).first).isAfter((ChronoLocalDate)((Pair)object).second)) break block9;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Prev: ");
                            ((StringBuilder)object2).append(serializable);
                            ((StringBuilder)object2).append(",");
                            ((StringBuilder)object2).append(localDate);
                            ((StringBuilder)object2).append("; cur: ");
                            ((StringBuilder)object2).append(((Pair)object).first);
                            ((StringBuilder)object2).append(",");
                            ((StringBuilder)object2).append(((Pair)object).second);
                            object2 = ((StringBuilder)object2).toString();
                            l = FreezePeriod.distanceWithoutLeapYear((LocalDate)((Pair)object).first, localDate) - 1;
                            if (l <= 0L) break block10;
                            if (l < 60L) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Previous freeze period too close to new period: ");
                                ((StringBuilder)object).append(l);
                                ((StringBuilder)object).append(", ");
                                ((StringBuilder)object).append((String)object2);
                                throw SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooClose(((StringBuilder)object).toString());
                            }
                            break block11;
                        }
                        l = FreezePeriod.distanceWithoutLeapYear((LocalDate)((Pair)object).second, (LocalDate)serializable) + 1;
                        if (l > 90L) break block12;
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Combined freeze period exceeds maximum days: ");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append((String)object2);
                throw SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooLong(((StringBuilder)object).toString());
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Current freeze dates inverted: ");
            ((StringBuilder)serializable).append(((Pair)object).first);
            ((StringBuilder)serializable).append("-");
            ((StringBuilder)serializable).append(((Pair)object).second);
            throw new IllegalStateException(((StringBuilder)serializable).toString());
        }
    }

    static void validatePeriods(List<FreezePeriod> object) {
        List<FreezePeriod> list = FreezePeriod.canonicalizePeriods(object);
        if (list.size() == object.size()) {
            for (int i = 0; i < list.size(); ++i) {
                FreezePeriod freezePeriod = list.get(i);
                if (freezePeriod.getLength() <= 90) {
                    int n;
                    object = i > 0 ? list.get(i - 1) : list.get(list.size() - 1);
                    if (object == freezePeriod || (n = i == 0 && !((FreezePeriod)object).isWrapped() ? freezePeriod.mStartDay + (365 - ((FreezePeriod)object).mEndDay) - 1 : freezePeriod.mStartDay - ((FreezePeriod)object).mEndDay - 1) >= 60) continue;
                    list = new StringBuilder();
                    ((StringBuilder)((Object)list)).append("Freeze periods ");
                    ((StringBuilder)((Object)list)).append(object);
                    ((StringBuilder)((Object)list)).append(" and ");
                    ((StringBuilder)((Object)list)).append(freezePeriod);
                    ((StringBuilder)((Object)list)).append(" are too close together: ");
                    ((StringBuilder)((Object)list)).append(n);
                    ((StringBuilder)((Object)list)).append(" days apart");
                    throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooClose(((StringBuilder)((Object)list)).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Freeze period ");
                ((StringBuilder)object).append(freezePeriod);
                ((StringBuilder)object).append(" is too long: ");
                ((StringBuilder)object).append(freezePeriod.getLength());
                ((StringBuilder)object).append(" days");
                throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooLong(((StringBuilder)object).toString());
            }
            return;
        }
        throw SystemUpdatePolicy.ValidationFailedException.duplicateOrOverlapPeriods();
    }

    boolean after(LocalDate localDate) {
        boolean bl = this.mStartDay > FreezePeriod.dayOfYearDisregardLeapYear(localDate);
        return bl;
    }

    boolean contains(LocalDate localDate) {
        int n = FreezePeriod.dayOfYearDisregardLeapYear(localDate);
        boolean bl = this.isWrapped();
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            bl2 = bl3;
            if (this.mStartDay <= n) {
                bl2 = bl3;
                if (n <= this.mEndDay) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        if (this.mStartDay <= n || n <= this.mEndDay) {
            bl2 = true;
        }
        return bl2;
    }

    int getEffectiveEndDay() {
        if (!this.isWrapped()) {
            return this.mEndDay;
        }
        return this.mEndDay + 365;
    }

    public MonthDay getEnd() {
        return this.mEnd;
    }

    int getLength() {
        return this.getEffectiveEndDay() - this.mStartDay + 1;
    }

    public MonthDay getStart() {
        return this.mStart;
    }

    boolean isWrapped() {
        boolean bl = this.mEndDay < this.mStartDay;
        return bl;
    }

    Pair<LocalDate, LocalDate> toCurrentOrFutureRealDates(LocalDate localDate) {
        int n;
        int n2 = FreezePeriod.dayOfYearDisregardLeapYear(localDate);
        if (this.contains(localDate)) {
            if (this.mStartDay <= n2) {
                n2 = 0;
                n = this.isWrapped();
            } else {
                n2 = -1;
                n = 0;
            }
        } else if (this.mStartDay > n2) {
            n2 = 0;
            n = this.isWrapped();
        } else {
            n2 = 1;
            n = 1;
        }
        return new Pair<LocalDate, LocalDate>(LocalDate.ofYearDay(2001, this.mStartDay).withYear(localDate.getYear() + n2), LocalDate.ofYearDay(2001, this.mEndDay).withYear(localDate.getYear() + n));
    }

    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalDate.ofYearDay(2001, this.mStartDay).format(dateTimeFormatter));
        stringBuilder.append(" - ");
        stringBuilder.append(LocalDate.ofYearDay(2001, this.mEndDay).format(dateTimeFormatter));
        return stringBuilder.toString();
    }
}

