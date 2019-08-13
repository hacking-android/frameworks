/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodFormatterFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodFormatter;
import android.icu.impl.duration.TimeUnit;
import android.icu.impl.duration.impl.PeriodFormatterData;

class BasicPeriodFormatter
implements PeriodFormatter {
    private BasicPeriodFormatterFactory.Customizations customs;
    private PeriodFormatterData data;
    private BasicPeriodFormatterFactory factory;
    private String localeName;

    BasicPeriodFormatter(BasicPeriodFormatterFactory basicPeriodFormatterFactory, String string, PeriodFormatterData periodFormatterData, BasicPeriodFormatterFactory.Customizations customizations) {
        this.factory = basicPeriodFormatterFactory;
        this.localeName = string;
        this.data = periodFormatterData;
        this.customs = customizations;
    }

    private String format(int n, boolean bl, int[] arrn) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        Object object = arrn;
        int n9 = 0;
        for (n7 = 0; n7 < ((int[])object).length; ++n7) {
            n4 = n9;
            if (object[n7] > 0) {
                n4 = n9 | 1 << n7;
            }
            n9 = n4;
        }
        n7 = n9;
        if (!this.data.allowZero()) {
            n4 = 0;
            n7 = 1;
            while (n4 < ((int[])object).length) {
                n2 = n9;
                if ((n9 & n7) != 0) {
                    n2 = n9;
                    if (object[n4] == 1) {
                        n2 = n9 & n7;
                    }
                }
                ++n4;
                n7 <<= 1;
                n9 = n2;
            }
            n7 = n9;
            if (n9 == 0) {
                return null;
            }
        }
        n2 = 0;
        n9 = n7;
        n4 = n2;
        if (this.data.useMilliseconds() != 0) {
            n9 = n7;
            n4 = n2;
            if ((1 << TimeUnit.MILLISECOND.ordinal & n7) != 0) {
                n6 = TimeUnit.SECOND.ordinal;
                n5 = TimeUnit.MILLISECOND.ordinal;
                n8 = 1 << n6;
                n3 = 1 << n5;
                n9 = this.data.useMilliseconds();
                if (n9 != 1) {
                    if (n9 != 2) {
                        n9 = n7;
                        n4 = n2;
                    } else {
                        n9 = n7;
                        n4 = n2;
                        if ((n7 & n8) != 0) {
                            object[n6] = object[n6] + (object[n5] - 1) / 1000;
                            n9 = n7 & n3;
                            n4 = 1;
                        }
                    }
                } else {
                    n9 = n7;
                    if ((n7 & n8) == 0) {
                        n9 = n7 | n8;
                        object[n6] = 1;
                    }
                    object[n6] = object[n6] + (object[n5] - 1) / 1000;
                    n9 &= n3;
                    n4 = 1;
                }
            }
        }
        n7 = 0;
        n2 = ((int[])object).length - 1;
        do {
            n5 = n2;
            if (n7 >= ((int[])object).length) break;
            n5 = n2;
            if ((1 << n7 & n9) != 0) break;
            ++n7;
        } while (true);
        while (n5 > n7 && (1 << n5 & n9) == 0) {
            --n5;
        }
        n6 = 1;
        n3 = n7;
        do {
            n2 = n6;
            if (n3 > n5) break;
            if ((1 << n3 & n9) != 0 && object[n3] > 1) {
                n2 = 0;
                break;
            }
            ++n3;
        } while (true);
        StringBuffer stringBuffer = new StringBuffer();
        n3 = this.customs.displayLimit && n2 == 0 ? n : 0;
        if (this.customs.displayDirection && n2 == 0) {
            n = bl ? 2 : 1;
            n6 = n;
        } else {
            n6 = 0;
        }
        bl = this.data.appendPrefix(n3, n6, stringBuffer);
        boolean bl2 = n7 != n5;
        boolean bl3 = true;
        boolean bl4 = this.customs.separatorVariant != 0;
        n = n7;
        int n10 = 0;
        int n11 = n7;
        n8 = n3;
        n3 = n10;
        int n12 = n4;
        n10 = n9;
        while (n11 <= n5) {
            if (n3 != 0) {
                this.data.appendSkippedUnit(stringBuffer);
                bl3 = true;
                n9 = 0;
            } else {
                n9 = n3;
            }
            while (++n < n5 && (1 << n & n10) == 0) {
                n9 = 1;
            }
            object = TimeUnit.units[n11];
            n3 = arrn[n11];
            n4 = this.customs.countVariant;
            if (n11 == n5) {
                if (n12 != 0) {
                    n4 = 5;
                }
            } else {
                n4 = 0;
            }
            boolean bl5 = n11 == n5;
            n3 = n9 | this.data.appendUnit((TimeUnit)object, n3 - 1, n4, this.customs.unitVariant, bl4, bl, bl2, bl5, bl3, stringBuffer);
            boolean bl6 = false;
            if (this.customs.separatorVariant != 0 && n <= n5) {
                bl = n11 == n7;
                bl3 = n == n5;
                bl5 = this.customs.separatorVariant == 2;
                bl = this.data.appendUnitSeparator((TimeUnit)object, bl5, bl, bl3, stringBuffer);
            } else {
                bl = false;
            }
            n9 = n;
            bl3 = bl6;
            n11 = n;
            n = n9;
        }
        this.data.appendSuffix(n8, n6, stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public String format(Period period) {
        if (period.isSet()) {
            return this.format(period.timeLimit, period.inFuture, period.counts);
        }
        throw new IllegalArgumentException("period is not set");
    }

    @Override
    public PeriodFormatter withLocale(String string) {
        if (!this.localeName.equals(string)) {
            PeriodFormatterData periodFormatterData = this.factory.getData(string);
            return new BasicPeriodFormatter(this.factory, string, periodFormatterData, this.customs);
        }
        return this;
    }
}

