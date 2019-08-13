/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.range.RangeMacroProps;
import android.icu.number.FormattedNumberRange;
import android.icu.number.NumberRangeFormatterImpl;
import android.icu.number.NumberRangeFormatterSettings;

public class LocalizedNumberRangeFormatter
extends NumberRangeFormatterSettings<LocalizedNumberRangeFormatter> {
    private volatile NumberRangeFormatterImpl fImpl;

    LocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        super(numberRangeFormatterSettings, n, object);
    }

    @Override
    LocalizedNumberRangeFormatter create(int n, Object object) {
        return new LocalizedNumberRangeFormatter(this, n, object);
    }

    FormattedNumberRange formatImpl(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, boolean bl) {
        if (this.fImpl == null) {
            this.fImpl = new NumberRangeFormatterImpl(this.resolve());
        }
        return this.fImpl.format(decimalQuantity, decimalQuantity2, bl);
    }

    public FormattedNumberRange formatRange(double d, double d2) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD2 = new DecimalQuantity_DualStorageBCD(d2);
        boolean bl = d == d2;
        return this.formatImpl(decimalQuantity_DualStorageBCD, decimalQuantity_DualStorageBCD2, bl);
    }

    public FormattedNumberRange formatRange(int n, int n2) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(n);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD2 = new DecimalQuantity_DualStorageBCD(n2);
        boolean bl = n == n2;
        return this.formatImpl(decimalQuantity_DualStorageBCD, decimalQuantity_DualStorageBCD2, bl);
    }

    public FormattedNumberRange formatRange(Number number, Number number2) {
        if (number != null && number2 != null) {
            return this.formatImpl(new DecimalQuantity_DualStorageBCD(number), new DecimalQuantity_DualStorageBCD(number2), number.equals(number2));
        }
        throw new IllegalArgumentException("Cannot format null values in range");
    }
}

