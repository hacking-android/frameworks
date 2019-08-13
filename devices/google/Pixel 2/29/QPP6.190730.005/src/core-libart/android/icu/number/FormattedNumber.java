/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.util.Arrays;

public class FormattedNumber {
    final DecimalQuantity fq;
    final NumberStringBuilder nsb;

    FormattedNumber(NumberStringBuilder numberStringBuilder, DecimalQuantity decimalQuantity) {
        this.nsb = numberStringBuilder;
        this.fq = decimalQuantity;
    }

    public <A extends Appendable> A appendTo(A a) {
        try {
            a.append(this.nsb);
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
        if (!(object instanceof FormattedNumber)) {
            return false;
        }
        object = (FormattedNumber)object;
        if (!(Arrays.equals(this.nsb.toCharArray(), ((FormattedNumber)object).nsb.toCharArray()) && Arrays.equals(this.nsb.toFieldArray(), ((FormattedNumber)object).nsb.toFieldArray()) && this.fq.toBigDecimal().equals(((FormattedNumber)object).fq.toBigDecimal()))) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public AttributedCharacterIterator getFieldIterator() {
        return this.nsb.toCharacterIterator();
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal() {
        return this.fq;
    }

    public int hashCode() {
        return Arrays.hashCode(this.nsb.toCharArray()) ^ Arrays.hashCode(this.nsb.toFieldArray()) ^ this.fq.toBigDecimal().hashCode();
    }

    public boolean nextFieldPosition(FieldPosition fieldPosition) {
        this.fq.populateUFieldPosition(fieldPosition);
        return this.nsb.nextFieldPosition(fieldPosition);
    }

    @Deprecated
    public void populateFieldPosition(FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        this.nextFieldPosition(fieldPosition);
    }

    public BigDecimal toBigDecimal() {
        return this.fq.toBigDecimal();
    }

    public AttributedCharacterIterator toCharacterIterator() {
        return this.nsb.toCharacterIterator();
    }

    public String toString() {
        return this.nsb.toString();
    }
}

