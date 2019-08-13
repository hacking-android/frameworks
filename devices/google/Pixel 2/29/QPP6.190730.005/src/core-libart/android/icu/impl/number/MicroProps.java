/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.Padder;
import android.icu.number.IntegerWidth;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.DecimalFormatSymbols;

public class MicroProps
implements Cloneable,
MicroPropsGenerator {
    public NumberFormatter.DecimalSeparatorDisplay decimal;
    private volatile boolean exhausted;
    public Grouper grouping;
    private final boolean immutable;
    public IntegerWidth integerWidth;
    public Modifier modInner;
    public Modifier modMiddle;
    public Modifier modOuter;
    public Padder padding;
    public Precision rounder;
    public NumberFormatter.SignDisplay sign;
    public DecimalFormatSymbols symbols;
    public boolean useCurrency;

    public MicroProps(boolean bl) {
        this.immutable = bl;
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        if (this.immutable) {
            return (MicroProps)this.clone();
        }
        if (!this.exhausted) {
            this.exhausted = true;
            return this;
        }
        throw new AssertionError((Object)"Cannot re-use a mutable MicroProps in the quantity chain");
    }
}

