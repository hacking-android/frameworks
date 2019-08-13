/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.number.NumberRangeFormatter;
import android.icu.text.NumberFormat;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.util.Arrays;

public class FormattedNumberRange {
    final NumberRangeFormatter.RangeIdentityResult identityResult;
    final DecimalQuantity quantity1;
    final DecimalQuantity quantity2;
    final NumberStringBuilder string;

    FormattedNumberRange(NumberStringBuilder numberStringBuilder, DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        this.string = numberStringBuilder;
        this.quantity1 = decimalQuantity;
        this.quantity2 = decimalQuantity2;
        this.identityResult = rangeIdentityResult;
    }

    public <A extends Appendable> A appendTo(A a) {
        try {
            a.append(this.string);
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
        return a;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof FormattedNumberRange)) {
            return false;
        }
        object = (FormattedNumberRange)object;
        if (!(Arrays.equals(this.string.toCharArray(), ((FormattedNumberRange)object).string.toCharArray()) && Arrays.equals(this.string.toFieldArray(), ((FormattedNumberRange)object).string.toFieldArray()) && this.quantity1.toBigDecimal().equals(((FormattedNumberRange)object).quantity1.toBigDecimal()) && this.quantity2.toBigDecimal().equals(((FormattedNumberRange)object).quantity2.toBigDecimal()))) {
            bl = false;
        }
        return bl;
    }

    public BigDecimal getFirstBigDecimal() {
        return this.quantity1.toBigDecimal();
    }

    public NumberRangeFormatter.RangeIdentityResult getIdentityResult() {
        return this.identityResult;
    }

    public BigDecimal getSecondBigDecimal() {
        return this.quantity2.toBigDecimal();
    }

    public int hashCode() {
        return Arrays.hashCode(this.string.toCharArray()) ^ Arrays.hashCode(this.string.toFieldArray()) ^ this.quantity1.toBigDecimal().hashCode() ^ this.quantity2.toBigDecimal().hashCode();
    }

    public boolean nextFieldPosition(FieldPosition fieldPosition) {
        return this.string.nextFieldPosition(fieldPosition);
    }

    public AttributedCharacterIterator toCharacterIterator() {
        return this.string.toCharacterIterator();
    }

    public String toString() {
        return this.string.toString();
    }
}

