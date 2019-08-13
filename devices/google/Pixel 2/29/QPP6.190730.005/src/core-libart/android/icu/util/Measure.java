/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.MeasureUnit;

public class Measure {
    private final Number number;
    private final MeasureUnit unit;

    public Measure(Number number, MeasureUnit measureUnit) {
        if (number != null && measureUnit != null) {
            this.number = number;
            this.unit = measureUnit;
            return;
        }
        throw new NullPointerException("Number and MeasureUnit must not be null");
    }

    private static boolean numbersEqual(Number number, Number number2) {
        if (number.equals(number2)) {
            return true;
        }
        return number.doubleValue() == number2.doubleValue();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Measure)) {
            return false;
        }
        object = (Measure)object;
        if (!this.unit.equals(((Measure)object).unit) || !Measure.numbersEqual(this.number, ((Measure)object).number)) {
            bl = false;
        }
        return bl;
    }

    public Number getNumber() {
        return this.number;
    }

    public MeasureUnit getUnit() {
        return this.unit;
    }

    public int hashCode() {
        return Double.valueOf(this.number.doubleValue()).hashCode() * 31 + this.unit.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.number.toString());
        stringBuilder.append(' ');
        stringBuilder.append(this.unit.toString());
        return stringBuilder.toString();
    }
}

