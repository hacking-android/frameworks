/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.FixedUnitBuilder;
import android.icu.impl.duration.MultiUnitBuilder;
import android.icu.impl.duration.OneOrTwoUnitBuilder;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderFactory;
import android.icu.impl.duration.SingleUnitBuilder;
import android.icu.impl.duration.TimeUnit;
import android.icu.impl.duration.impl.PeriodFormatterData;
import android.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.TimeZone;

class BasicPeriodBuilderFactory
implements PeriodBuilderFactory {
    private static final short allBits = 255;
    private PeriodFormatterDataService ds;
    private Settings settings;

    BasicPeriodBuilderFactory(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
        this.settings = new Settings();
    }

    static long approximateDurationOf(TimeUnit timeUnit) {
        return TimeUnit.approxDurations[timeUnit.ordinal];
    }

    private Settings getSettings() {
        if (this.settings.effectiveSet() == 0) {
            return null;
        }
        return this.settings.setInUse();
    }

    @Override
    public PeriodBuilder getFixedUnitBuilder(TimeUnit timeUnit) {
        return FixedUnitBuilder.get(timeUnit, this.getSettings());
    }

    @Override
    public PeriodBuilder getMultiUnitBuilder(int n) {
        return MultiUnitBuilder.get(n, this.getSettings());
    }

    @Override
    public PeriodBuilder getOneOrTwoUnitBuilder() {
        return OneOrTwoUnitBuilder.get(this.getSettings());
    }

    @Override
    public PeriodBuilder getSingleUnitBuilder() {
        return SingleUnitBuilder.get(this.getSettings());
    }

    @Override
    public PeriodBuilderFactory setAllowMilliseconds(boolean bl) {
        this.settings = this.settings.setAllowMilliseconds(bl);
        return this;
    }

    @Override
    public PeriodBuilderFactory setAllowZero(boolean bl) {
        this.settings = this.settings.setAllowZero(bl);
        return this;
    }

    @Override
    public PeriodBuilderFactory setAvailableUnitRange(TimeUnit timeUnit, TimeUnit timeUnit2) {
        int n = 0;
        for (int i = timeUnit2.ordinal; i <= timeUnit.ordinal; ++i) {
            n |= 1 << i;
        }
        if (n != 0) {
            this.settings = this.settings.setUnits(n);
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("range ");
        stringBuilder.append(timeUnit);
        stringBuilder.append(" to ");
        stringBuilder.append(timeUnit2);
        stringBuilder.append(" is empty");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public PeriodBuilderFactory setLocale(String string) {
        this.settings = this.settings.setLocale(string);
        return this;
    }

    @Override
    public PeriodBuilderFactory setMaxLimit(float f) {
        this.settings = this.settings.setMaxLimit(f);
        return this;
    }

    @Override
    public PeriodBuilderFactory setMinLimit(float f) {
        this.settings = this.settings.setMinLimit(f);
        return this;
    }

    @Override
    public PeriodBuilderFactory setTimeZone(TimeZone timeZone) {
        return this;
    }

    @Override
    public PeriodBuilderFactory setUnitIsAvailable(TimeUnit timeUnit, boolean bl) {
        int n = this.settings.uset;
        n = bl ? (n |= 1 << timeUnit.ordinal) : (n &= 1 << timeUnit.ordinal);
        this.settings = this.settings.setUnits(n);
        return this;
    }

    @Override
    public PeriodBuilderFactory setWeeksAloneOnly(boolean bl) {
        this.settings = this.settings.setWeeksAloneOnly(bl);
        return this;
    }

    class Settings {
        boolean allowMillis = true;
        boolean allowZero = true;
        boolean inUse;
        int maxLimit;
        TimeUnit maxUnit = TimeUnit.YEAR;
        int minLimit;
        TimeUnit minUnit = TimeUnit.MILLISECOND;
        short uset = (short)255;
        boolean weeksAloneOnly;

        Settings() {
        }

        public Settings copy() {
            Settings settings = new Settings();
            settings.inUse = this.inUse;
            settings.uset = this.uset;
            settings.maxUnit = this.maxUnit;
            settings.minUnit = this.minUnit;
            settings.maxLimit = this.maxLimit;
            settings.minLimit = this.minLimit;
            settings.allowZero = this.allowZero;
            settings.weeksAloneOnly = this.weeksAloneOnly;
            settings.allowMillis = this.allowMillis;
            return settings;
        }

        Period createLimited(long l, boolean bl) {
            TimeUnit timeUnit;
            long l2;
            long l3;
            TimeUnit timeUnit2;
            int n;
            if (this.maxLimit > 0 && l * 1000L > (long)(n = this.maxLimit) * (l3 = BasicPeriodBuilderFactory.approximateDurationOf(this.maxUnit))) {
                return Period.moreThan((float)n / 1000.0f, this.maxUnit).inPast(bl);
            }
            if (this.minLimit > 0 && 1000L * l < (l3 = (timeUnit2 = this.effectiveMinUnit()) == (timeUnit = this.minUnit) ? (long)this.minLimit : Math.max(1000L, BasicPeriodBuilderFactory.approximateDurationOf(timeUnit) * (long)this.minLimit / l2)) * (l2 = BasicPeriodBuilderFactory.approximateDurationOf(timeUnit2))) {
                return Period.lessThan((float)l3 / 1000.0f, timeUnit2).inPast(bl);
            }
            return null;
        }

        TimeUnit effectiveMinUnit() {
            if (!this.allowMillis && this.minUnit == TimeUnit.MILLISECOND) {
                int n;
                int n2 = TimeUnit.units.length - 1;
                while ((n = n2 - 1) >= 0) {
                    n2 = n;
                    if ((this.uset & 1 << n) == 0) continue;
                    return TimeUnit.units[n];
                }
                return TimeUnit.SECOND;
            }
            return this.minUnit;
        }

        short effectiveSet() {
            if (this.allowMillis) {
                return this.uset;
            }
            return (short)(this.uset & 1 << TimeUnit.MILLISECOND.ordinal);
        }

        Settings setAllowMilliseconds(boolean bl) {
            if (this.allowMillis == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.allowMillis = bl;
            return settings;
        }

        Settings setAllowZero(boolean bl) {
            if (this.allowZero == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.allowZero = bl;
            return settings;
        }

        Settings setInUse() {
            this.inUse = true;
            return this;
        }

        Settings setLocale(String object) {
            PeriodFormatterData periodFormatterData = BasicPeriodBuilderFactory.this.ds.get((String)object);
            object = this.setAllowZero(periodFormatterData.allowZero()).setWeeksAloneOnly(periodFormatterData.weeksAloneOnly());
            int n = periodFormatterData.useMilliseconds();
            boolean bl = true;
            if (n == 1) {
                bl = false;
            }
            return ((Settings)object).setAllowMilliseconds(bl);
        }

        Settings setMaxLimit(float f) {
            int n = f <= 0.0f ? 0 : (int)(1000.0f * f);
            if (f == (float)n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.maxLimit = n;
            return settings;
        }

        Settings setMinLimit(float f) {
            int n = f <= 0.0f ? 0 : (int)(1000.0f * f);
            if (f == (float)n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.minLimit = n;
            return settings;
        }

        Settings setUnits(int n) {
            if (this.uset == n) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.uset = (short)n;
            if ((n & 255) == 255) {
                settings.uset = (short)255;
                settings.maxUnit = TimeUnit.YEAR;
                settings.minUnit = TimeUnit.MILLISECOND;
            } else {
                int n2 = -1;
                for (int i = 0; i < TimeUnit.units.length; ++i) {
                    int n3 = n2;
                    if ((1 << i & n) != 0) {
                        if (n2 == -1) {
                            settings.maxUnit = TimeUnit.units[i];
                        }
                        n3 = i;
                    }
                    n2 = n3;
                }
                if (n2 == -1) {
                    settings.maxUnit = null;
                    settings.minUnit = null;
                } else {
                    settings.minUnit = TimeUnit.units[n2];
                }
            }
            return settings;
        }

        Settings setWeeksAloneOnly(boolean bl) {
            if (this.weeksAloneOnly == bl) {
                return this;
            }
            Settings settings = this.inUse ? this.copy() : this;
            settings.weeksAloneOnly = bl;
            return settings;
        }
    }

}

