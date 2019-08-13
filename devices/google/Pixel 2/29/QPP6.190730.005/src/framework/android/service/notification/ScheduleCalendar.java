/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.service.notification.ZenModeConfig;
import android.util.ArraySet;
import android.util.Log;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

public class ScheduleCalendar {
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final String TAG = "ScheduleCalendar";
    private final Calendar mCalendar = Calendar.getInstance();
    private final ArraySet<Integer> mDays = new ArraySet();
    private ZenModeConfig.ScheduleInfo mSchedule;

    private long addDays(long l, int n) {
        this.mCalendar.setTimeInMillis(l);
        this.mCalendar.add(5, n);
        return this.mCalendar.getTimeInMillis();
    }

    private int getDayOfWeek(long l) {
        this.mCalendar.setTimeInMillis(l);
        return this.mCalendar.get(7);
    }

    private long getNextTime(long l, int n, int n2) {
        long l2 = this.getTime(l, n, n2);
        l = l2 <= l ? this.addDays(l2, 1) : l2;
        return l;
    }

    private long getTime(long l, int n, int n2) {
        this.mCalendar.setTimeInMillis(l);
        this.mCalendar.set(11, n);
        this.mCalendar.set(12, n2);
        this.mCalendar.set(13, 0);
        this.mCalendar.set(14, 0);
        return this.mCalendar.getTimeInMillis();
    }

    private boolean isInSchedule(int n, long l, long l2, long l3) {
        int n2 = this.getDayOfWeek(l);
        boolean bl = true;
        l2 = this.addDays(l2, n);
        l3 = this.addDays(l3, n);
        if (!this.mDays.contains((n2 - 1 + n % 7 + 7) % 7 + 1) || l < l2 || l >= l3) {
            bl = false;
        }
        return bl;
    }

    private void updateDays() {
        this.mDays.clear();
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo != null && scheduleInfo.days != null) {
            for (int i = 0; i < this.mSchedule.days.length; ++i) {
                this.mDays.add(this.mSchedule.days[i]);
            }
        }
    }

    public boolean exitAtAlarm() {
        return this.mSchedule.exitAtAlarm;
    }

    public long getNextChangeTime(long l) {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo == null) {
            return 0L;
        }
        return Math.min(this.getNextTime(l, scheduleInfo.startHour, this.mSchedule.startMinute), this.getNextTime(l, this.mSchedule.endHour, this.mSchedule.endMinute));
    }

    public boolean isAlarmInSchedule(long l, long l2) {
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        boolean bl = false;
        if (scheduleInfo != null && this.mDays.size() != 0) {
            long l3 = this.getTime(l, this.mSchedule.startHour, this.mSchedule.startMinute);
            long l4 = this.getTime(l, this.mSchedule.endHour, this.mSchedule.endMinute);
            if (l4 <= l3) {
                l4 = this.addDays(l4, 1);
            }
            if (this.isInSchedule(-1, l, l3, l4) && this.isInSchedule(-1, l2, l3, l4) || this.isInSchedule(0, l, l3, l4) && this.isInSchedule(0, l2, l3, l4)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean isInSchedule(long l) {
        if (this.mSchedule != null && this.mDays.size() != 0) {
            long l2 = this.getTime(l, this.mSchedule.startHour, this.mSchedule.startMinute);
            long l3 = this.getTime(l, this.mSchedule.endHour, this.mSchedule.endMinute);
            boolean bl = true;
            if (l3 <= l2) {
                l3 = this.addDays(l3, 1);
            }
            boolean bl2 = bl;
            if (!this.isInSchedule(-1, l, l2, l3)) {
                bl2 = this.isInSchedule(0, l, l2, l3) ? bl : false;
            }
            return bl2;
        }
        return false;
    }

    public void maybeSetNextAlarm(long l, long l2) {
        Object object = this.mSchedule;
        if (object != null && ((ZenModeConfig.ScheduleInfo)object).exitAtAlarm) {
            if (l2 == 0L) {
                this.mSchedule.nextAlarm = 0L;
            }
            if (l2 > l) {
                if (this.mSchedule.nextAlarm != 0L && this.mSchedule.nextAlarm >= l) {
                    object = this.mSchedule;
                    ((ZenModeConfig.ScheduleInfo)object).nextAlarm = Math.min(((ZenModeConfig.ScheduleInfo)object).nextAlarm, l2);
                } else {
                    this.mSchedule.nextAlarm = l2;
                }
            } else if (this.mSchedule.nextAlarm < l) {
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("All alarms are in the past ");
                    ((StringBuilder)object).append(this.mSchedule.nextAlarm);
                    Log.d(TAG, ((StringBuilder)object).toString());
                }
                this.mSchedule.nextAlarm = 0L;
            }
        }
    }

    public void setSchedule(ZenModeConfig.ScheduleInfo scheduleInfo) {
        if (Objects.equals(this.mSchedule, scheduleInfo)) {
            return;
        }
        this.mSchedule = scheduleInfo;
        this.updateDays();
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
    }

    public boolean shouldExitForAlarm(long l) {
        boolean bl;
        block1 : {
            ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
            bl = false;
            if (scheduleInfo == null) {
                return false;
            }
            if (!scheduleInfo.exitAtAlarm || this.mSchedule.nextAlarm == 0L || l < this.mSchedule.nextAlarm || !this.isAlarmInSchedule(this.mSchedule.nextAlarm, l)) break block1;
            bl = true;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScheduleCalendar[mDays=");
        stringBuilder.append(this.mDays);
        stringBuilder.append(", mSchedule=");
        stringBuilder.append(this.mSchedule);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

