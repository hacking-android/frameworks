/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.text.PluralRules;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.FieldPosition;

public interface DecimalQuantity
extends PluralRules.IFixedDecimal {
    public void adjustMagnitude(int var1);

    public void copyFrom(DecimalQuantity var1);

    public DecimalQuantity createCopy();

    public byte getDigit(int var1);

    public int getLowerDisplayMagnitude();

    public int getMagnitude() throws ArithmeticException;

    public long getPositionFingerprint();

    public StandardPlural getStandardPlural(PluralRules var1);

    public int getUpperDisplayMagnitude();

    @Override
    public boolean isInfinite();

    @Override
    public boolean isNaN();

    public boolean isNegative();

    public boolean isZero();

    public int maxRepresentableDigits();

    public void multiplyBy(BigDecimal var1);

    public void negate();

    public void populateUFieldPosition(FieldPosition var1);

    public void roundToIncrement(BigDecimal var1, MathContext var2);

    public void roundToInfinity();

    public void roundToMagnitude(int var1, MathContext var2);

    public void setFractionLength(int var1, int var2);

    public void setIntegerLength(int var1, int var2);

    public void setToBigDecimal(BigDecimal var1);

    public int signum();

    public BigDecimal toBigDecimal();

    public double toDouble();

    public String toPlainString();
}

