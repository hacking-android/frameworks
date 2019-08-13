/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import java.math.BigDecimal;
import java.util.Comparator;

public class ParsedNumber {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Comparator<ParsedNumber> COMPARATOR = new Comparator<ParsedNumber>(){

        @Override
        public int compare(ParsedNumber parsedNumber, ParsedNumber parsedNumber2) {
            return parsedNumber.charEnd - parsedNumber2.charEnd;
        }
    };
    public static final int FLAG_FAIL = 256;
    public static final int FLAG_HAS_DECIMAL_SEPARATOR = 32;
    public static final int FLAG_HAS_EXPONENT = 8;
    public static final int FLAG_INFINITY = 128;
    public static final int FLAG_NAN = 64;
    public static final int FLAG_NEGATIVE = 1;
    public static final int FLAG_PERCENT = 2;
    public static final int FLAG_PERMILLE = 4;
    public int charEnd;
    public String currencyCode;
    public int flags;
    public String prefix;
    public DecimalQuantity_DualStorageBCD quantity;
    public String suffix;

    public ParsedNumber() {
        this.clear();
    }

    public void clear() {
        this.quantity = null;
        this.charEnd = 0;
        this.flags = 0;
        this.prefix = null;
        this.suffix = null;
        this.currencyCode = null;
    }

    public void copyFrom(ParsedNumber parsedNumber) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = parsedNumber.quantity;
        decimalQuantity_DualStorageBCD = decimalQuantity_DualStorageBCD == null ? null : (DecimalQuantity_DualStorageBCD)decimalQuantity_DualStorageBCD.createCopy();
        this.quantity = decimalQuantity_DualStorageBCD;
        this.charEnd = parsedNumber.charEnd;
        this.flags = parsedNumber.flags;
        this.prefix = parsedNumber.prefix;
        this.suffix = parsedNumber.suffix;
        this.currencyCode = parsedNumber.currencyCode;
    }

    public Number getNumber() {
        return this.getNumber(0);
    }

    public Number getNumber(int n) {
        boolean bl = (this.flags & 64) != 0;
        boolean bl2 = (this.flags & 128) != 0;
        boolean bl3 = (n & 4096) != 0;
        n = (n & 16) != 0 ? 1 : 0;
        if (bl) {
            return Double.NaN;
        }
        if (bl2) {
            if ((1 & this.flags) != 0) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (this.quantity.isZero() && this.quantity.isNegative() && n == 0) {
            return 0.0;
        }
        if (this.quantity.fitsInLong() && !bl3) {
            return this.quantity.toLong(false);
        }
        return this.quantity.toBigDecimal();
    }

    boolean isBetterThan(ParsedNumber parsedNumber) {
        boolean bl = COMPARATOR.compare(this, parsedNumber) > 0;
        return bl;
    }

    public void postProcess() {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = this.quantity;
        if (decimalQuantity_DualStorageBCD != null && (this.flags & 1) != 0) {
            decimalQuantity_DualStorageBCD.negate();
        }
    }

    public boolean seenNumber() {
        int n;
        boolean bl = this.quantity != null || ((n = this.flags) & 64) != 0 || (n & 128) != 0;
        return bl;
    }

    public void setCharsConsumed(StringSegment stringSegment) {
        this.charEnd = stringSegment.getOffset();
    }

    public boolean success() {
        boolean bl = this.charEnd > 0 && (this.flags & 256) == 0;
        return bl;
    }

}

