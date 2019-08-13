/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.PatternStringParser;
import android.icu.number.NumberFormatter;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;

public class Grouper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Grouper GROUPER_AUTO;
    private static final Grouper GROUPER_INDIC;
    private static final Grouper GROUPER_INDIC_MIN2;
    private static final Grouper GROUPER_MIN2;
    private static final Grouper GROUPER_NEVER;
    private static final Grouper GROUPER_ON_ALIGNED;
    private static final Grouper GROUPER_WESTERN;
    private static final Grouper GROUPER_WESTERN_MIN2;
    private final short grouping1;
    private final short grouping2;
    private final short minGrouping;

    static {
        GROUPER_NEVER = new Grouper(-1, -1, -2);
        GROUPER_MIN2 = new Grouper(-2, -2, -3);
        GROUPER_AUTO = new Grouper(-2, -2, -2);
        GROUPER_ON_ALIGNED = new Grouper(-4, -4, 1);
        GROUPER_WESTERN = new Grouper(3, 3, 1);
        GROUPER_INDIC = new Grouper(3, 2, 1);
        GROUPER_WESTERN_MIN2 = new Grouper(3, 3, 2);
        GROUPER_INDIC_MIN2 = new Grouper(3, 2, 2);
    }

    private Grouper(short s, short s2, short s3) {
        this.grouping1 = s;
        this.grouping2 = s2;
        this.minGrouping = s3;
    }

    public static Grouper forProperties(DecimalFormatProperties decimalFormatProperties) {
        if (!decimalFormatProperties.getGroupingUsed()) {
            return GROUPER_NEVER;
        }
        short s = (short)decimalFormatProperties.getGroupingSize();
        short s2 = (short)decimalFormatProperties.getSecondaryGroupingSize();
        short s3 = (short)decimalFormatProperties.getMinimumGroupingDigits();
        short s4 = s <= 0 && s2 > 0 ? (s = s2) : s;
        short s5 = s2 > 0 ? s2 : (s2 = s4);
        return Grouper.getInstance(s4, s5, s3);
    }

    public static Grouper forStrategy(NumberFormatter.GroupingStrategy groupingStrategy) {
        int n = 1.$SwitchMap$android$icu$number$NumberFormatter$GroupingStrategy[groupingStrategy.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n == 5) {
                            return GROUPER_WESTERN;
                        }
                        throw new AssertionError();
                    }
                    return GROUPER_ON_ALIGNED;
                }
                return GROUPER_AUTO;
            }
            return GROUPER_MIN2;
        }
        return GROUPER_NEVER;
    }

    public static Grouper getInstance(short s, short s2, short s3) {
        if (s == -1) {
            return GROUPER_NEVER;
        }
        if (s == 3 && s2 == 3 && s3 == 1) {
            return GROUPER_WESTERN;
        }
        if (s == 3 && s2 == 2 && s3 == 1) {
            return GROUPER_INDIC;
        }
        if (s == 3 && s2 == 3 && s3 == 2) {
            return GROUPER_WESTERN_MIN2;
        }
        if (s == 3 && s2 == 2 && s3 == 2) {
            return GROUPER_INDIC_MIN2;
        }
        return new Grouper(s, s2, s3);
    }

    private static short getMinGroupingForLocale(ULocale uLocale) {
        return Short.valueOf(((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale)).getStringWithFallback("NumberElements/minimumGroupingDigits"));
    }

    public short getPrimary() {
        return this.grouping1;
    }

    public short getSecondary() {
        return this.grouping2;
    }

    public boolean groupAtPosition(int n, DecimalQuantity decimalQuantity) {
        short s = this.grouping1;
        if (s != -1 && s != 0) {
            boolean bl = true;
            if ((n -= s) < 0 || n % this.grouping2 != 0 || decimalQuantity.getUpperDisplayMagnitude() - this.grouping1 + 1 < this.minGrouping) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public Grouper withLocaleData(ULocale uLocale, PatternStringParser.ParsedPatternInfo parsedPatternInfo) {
        short s = this.grouping1;
        if (s != -2 && s != -4) {
            return this;
        }
        s = (short)(parsedPatternInfo.positive.groupingSizes & 65535L);
        short s2 = (short)(parsedPatternInfo.positive.groupingSizes >>> 16 & 65535L);
        short s3 = (short)(parsedPatternInfo.positive.groupingSizes >>> 32 & 65535L);
        short s4 = s;
        if (s2 == -1) {
            s = this.grouping1 == -4 ? (short)3 : -1;
            s4 = s;
        }
        short s5 = s2;
        if (s3 == -1) {
            s5 = s = s4;
        }
        short s6 = (s = (short)this.minGrouping) == -2 ? (s = (short)Grouper.getMinGroupingForLocale(uLocale)) : (s == -3 ? (s = (short)((short)Math.max(2, Grouper.getMinGroupingForLocale(uLocale)))) : (s = (short)this.minGrouping));
        return Grouper.getInstance(s4, s5, s6);
    }

}

