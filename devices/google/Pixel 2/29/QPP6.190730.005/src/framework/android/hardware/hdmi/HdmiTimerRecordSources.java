/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiRecordSources;
import android.util.Log;

@SystemApi
public class HdmiTimerRecordSources {
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PHYSICAL_ADDRESS = 5;
    private static final int EXTERNAL_SOURCE_SPECIFIER_EXTERNAL_PLUG = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_FRIDAY = 32;
    private static final int RECORDING_SEQUENCE_REPEAT_MASK = 127;
    public static final int RECORDING_SEQUENCE_REPEAT_MONDAY = 2;
    public static final int RECORDING_SEQUENCE_REPEAT_ONCE_ONLY = 0;
    public static final int RECORDING_SEQUENCE_REPEAT_SATUREDAY = 64;
    public static final int RECORDING_SEQUENCE_REPEAT_SUNDAY = 1;
    public static final int RECORDING_SEQUENCE_REPEAT_THURSDAY = 16;
    public static final int RECORDING_SEQUENCE_REPEAT_TUESDAY = 4;
    public static final int RECORDING_SEQUENCE_REPEAT_WEDNESDAY = 8;
    private static final String TAG = "HdmiTimerRecordingSources";

    private HdmiTimerRecordSources() {
    }

    private static void checkDurationValue(int n, int n2) {
        if (n >= 0 && n <= 99) {
            if (n2 >= 0 && n2 <= 59) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("minute should be in rage of [0, 59]:");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hour should be in rage of [0, 99]:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkTimeValue(int n, int n2) {
        if (n >= 0 && n <= 23) {
            if (n2 >= 0 && n2 <= 59) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Minute should be in rage of [0, 59]:");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hour should be in rage of [0, 23]:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @SystemApi
    public static boolean checkTimerRecordSource(int n, byte[] arrby) {
        int n2 = arrby.length - 7;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return false;
                }
                n = arrby[7];
                if (n == 4) {
                    if (2 != n2) {
                        bl4 = false;
                    }
                    return bl4;
                }
                if (n == 5) {
                    bl4 = 3 == n2 ? bl : false;
                    return bl4;
                }
                return false;
            }
            bl4 = 4 == n2 ? bl2 : false;
            return bl4;
        }
        bl4 = 7 == n2 ? bl3 : false;
        return bl4;
    }

    private static void checkTimerRecordSourceInputs(TimerInfo timerInfo, HdmiRecordSources.RecordSource recordSource) {
        if (timerInfo != null) {
            if (recordSource != null) {
                return;
            }
            Log.w(TAG, "source should not be null.");
            throw new IllegalArgumentException("source should not be null.");
        }
        Log.w(TAG, "TimerInfo should not be null.");
        throw new IllegalArgumentException("TimerInfo should not be null.");
    }

    public static Duration durationOf(int n, int n2) {
        HdmiTimerRecordSources.checkDurationValue(n, n2);
        return new Duration(n, n2);
    }

    public static TimerRecordSource ofAnalogueSource(TimerInfo timerInfo, HdmiRecordSources.AnalogueServiceSource analogueServiceSource) {
        HdmiTimerRecordSources.checkTimerRecordSourceInputs(timerInfo, analogueServiceSource);
        return new TimerRecordSource(timerInfo, analogueServiceSource);
    }

    public static TimerRecordSource ofDigitalSource(TimerInfo timerInfo, HdmiRecordSources.DigitalServiceSource digitalServiceSource) {
        HdmiTimerRecordSources.checkTimerRecordSourceInputs(timerInfo, digitalServiceSource);
        return new TimerRecordSource(timerInfo, digitalServiceSource);
    }

    public static TimerRecordSource ofExternalPhysicalAddress(TimerInfo timerInfo, HdmiRecordSources.ExternalPhysicalAddress externalPhysicalAddress) {
        HdmiTimerRecordSources.checkTimerRecordSourceInputs(timerInfo, externalPhysicalAddress);
        return new TimerRecordSource(timerInfo, new ExternalSourceDecorator(externalPhysicalAddress, 5));
    }

    public static TimerRecordSource ofExternalPlug(TimerInfo timerInfo, HdmiRecordSources.ExternalPlugData externalPlugData) {
        HdmiTimerRecordSources.checkTimerRecordSourceInputs(timerInfo, externalPlugData);
        return new TimerRecordSource(timerInfo, new ExternalSourceDecorator(externalPlugData, 4));
    }

    public static Time timeOf(int n, int n2) {
        HdmiTimerRecordSources.checkTimeValue(n, n2);
        return new Time(n, n2);
    }

    public static TimerInfo timerInfoOf(int n, int n2, Time object, Duration duration, int n3) {
        if (n >= 0 && n <= 31) {
            if (n2 >= 1 && n2 <= 12) {
                HdmiTimerRecordSources.checkTimeValue(((Time)object).mHour, ((Time)object).mMinute);
                HdmiTimerRecordSources.checkDurationValue(duration.mHour, duration.mMinute);
                if (n3 != 0 && (n3 & -128) != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid reecording sequence value:");
                    ((StringBuilder)object).append(n3);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return new TimerInfo(n, n2, (Time)object, duration, n3);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Month of year should be in range of [1, 12]:");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Day of month should be in range of [0, 31]:");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @SystemApi
    public static final class Duration
    extends TimeUnit {
        private Duration(int n, int n2) {
            super(n, n2);
        }
    }

    private static class ExternalSourceDecorator
    extends HdmiRecordSources.RecordSource {
        private final int mExternalSourceSpecifier;
        private final HdmiRecordSources.RecordSource mRecordSource;

        private ExternalSourceDecorator(HdmiRecordSources.RecordSource recordSource, int n) {
            super(recordSource.mSourceType, recordSource.getDataSize(false) + 1);
            this.mRecordSource = recordSource;
            this.mExternalSourceSpecifier = n;
        }

        @Override
        int extraParamToByteArray(byte[] arrby, int n) {
            arrby[n] = (byte)this.mExternalSourceSpecifier;
            this.mRecordSource.toByteArray(false, arrby, n + 1);
            return this.getDataSize(false);
        }
    }

    @SystemApi
    public static final class Time
    extends TimeUnit {
        private Time(int n, int n2) {
            super(n, n2);
        }
    }

    static class TimeUnit {
        final int mHour;
        final int mMinute;

        TimeUnit(int n, int n2) {
            this.mHour = n;
            this.mMinute = n2;
        }

        static byte toBcdByte(int n) {
            return (byte)(n / 10 % 10 << 4 | n % 10);
        }

        int toByteArray(byte[] arrby, int n) {
            arrby[n] = TimeUnit.toBcdByte(this.mHour);
            arrby[n + 1] = TimeUnit.toBcdByte(this.mMinute);
            return 2;
        }
    }

    @SystemApi
    public static final class TimerInfo {
        private static final int BASIC_INFO_SIZE = 7;
        private static final int DAY_OF_MONTH_SIZE = 1;
        private static final int DURATION_SIZE = 2;
        private static final int MONTH_OF_YEAR_SIZE = 1;
        private static final int RECORDING_SEQUENCE_SIZE = 1;
        private static final int START_TIME_SIZE = 2;
        private final int mDayOfMonth;
        private final Duration mDuration;
        private final int mMonthOfYear;
        private final int mRecordingSequence;
        private final Time mStartTime;

        private TimerInfo(int n, int n2, Time time, Duration duration, int n3) {
            this.mDayOfMonth = n;
            this.mMonthOfYear = n2;
            this.mStartTime = time;
            this.mDuration = duration;
            this.mRecordingSequence = n3;
        }

        int getDataSize() {
            return 7;
        }

        int toByteArray(byte[] arrby, int n) {
            arrby[n] = (byte)this.mDayOfMonth;
            arrby[++n] = (byte)this.mMonthOfYear;
            ++n;
            n += this.mStartTime.toByteArray(arrby, n);
            arrby[n + this.mDuration.toByteArray((byte[])arrby, (int)n)] = (byte)this.mRecordingSequence;
            return this.getDataSize();
        }
    }

    @SystemApi
    public static final class TimerRecordSource {
        private final HdmiRecordSources.RecordSource mRecordSource;
        private final TimerInfo mTimerInfo;

        private TimerRecordSource(TimerInfo timerInfo, HdmiRecordSources.RecordSource recordSource) {
            this.mTimerInfo = timerInfo;
            this.mRecordSource = recordSource;
        }

        int getDataSize() {
            return this.mTimerInfo.getDataSize() + this.mRecordSource.getDataSize(false);
        }

        int toByteArray(byte[] arrby, int n) {
            int n2 = this.mTimerInfo.toByteArray(arrby, n);
            this.mRecordSource.toByteArray(false, arrby, n + n2);
            return this.getDataSize();
        }
    }

}

