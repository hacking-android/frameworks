/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.LocalizedNumberFormatterAsFormat;
import android.icu.impl.number.MacroProps;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.number.FormattedNumber;
import android.icu.number.NumberFormatterImpl;
import android.icu.number.NumberFormatterSettings;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.text.Format;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class LocalizedNumberFormatter
extends NumberFormatterSettings<LocalizedNumberFormatter> {
    static final AtomicLongFieldUpdater<LocalizedNumberFormatter> callCount = AtomicLongFieldUpdater.newUpdater(LocalizedNumberFormatter.class, "callCountInternal");
    volatile long callCountInternal;
    volatile NumberFormatterImpl compiled;
    volatile LocalizedNumberFormatter savedWithUnit;

    LocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        super(numberFormatterSettings, n, object);
    }

    private boolean computeCompiled() {
        MacroProps macroProps = this.resolve();
        if (callCount.incrementAndGet(this) == macroProps.threshold.longValue()) {
            this.compiled = new NumberFormatterImpl(macroProps);
            return true;
        }
        return this.compiled != null;
    }

    @Override
    LocalizedNumberFormatter create(int n, Object object) {
        return new LocalizedNumberFormatter(this, n, object);
    }

    public FormattedNumber format(double d) {
        return this.format(new DecimalQuantity_DualStorageBCD(d));
    }

    public FormattedNumber format(long l) {
        return this.format(new DecimalQuantity_DualStorageBCD(l));
    }

    @Deprecated
    public FormattedNumber format(DecimalQuantity decimalQuantity) {
        NumberStringBuilder numberStringBuilder = new NumberStringBuilder();
        if (this.computeCompiled()) {
            this.compiled.format(decimalQuantity, numberStringBuilder);
        } else {
            NumberFormatterImpl.formatStatic(this.resolve(), decimalQuantity, numberStringBuilder);
        }
        return new FormattedNumber(numberStringBuilder, decimalQuantity);
    }

    public FormattedNumber format(Measure object) {
        Number number;
        block5 : {
            MeasureUnit measureUnit;
            block4 : {
                measureUnit = ((Measure)object).getUnit();
                number = ((Measure)object).getNumber();
                if (Objects.equals(this.resolve().unit, measureUnit)) {
                    return this.format(number);
                }
                LocalizedNumberFormatter localizedNumberFormatter = this.savedWithUnit;
                if (localizedNumberFormatter == null) break block4;
                object = localizedNumberFormatter;
                if (Objects.equals(localizedNumberFormatter.resolve().unit, measureUnit)) break block5;
            }
            this.savedWithUnit = object = new LocalizedNumberFormatter(this, 3, measureUnit);
        }
        return ((LocalizedNumberFormatter)object).format(number);
    }

    public FormattedNumber format(Number number) {
        return this.format(new DecimalQuantity_DualStorageBCD(number));
    }

    @Deprecated
    public String getAffixImpl(boolean bl, boolean bl2) {
        NumberStringBuilder numberStringBuilder = new NumberStringBuilder();
        int n = bl2 ? -1 : 1;
        byte by = (byte)n;
        StandardPlural standardPlural = StandardPlural.OTHER;
        n = this.computeCompiled() ? this.compiled.getPrefixSuffix(by, standardPlural, numberStringBuilder) : NumberFormatterImpl.getPrefixSuffixStatic(this.resolve(), by, standardPlural, numberStringBuilder);
        if (bl) {
            return numberStringBuilder.subSequence(0, n).toString();
        }
        return numberStringBuilder.subSequence(n, numberStringBuilder.length()).toString();
    }

    public Format toFormat() {
        return new LocalizedNumberFormatterAsFormat(this, this.resolve().loc);
    }
}

