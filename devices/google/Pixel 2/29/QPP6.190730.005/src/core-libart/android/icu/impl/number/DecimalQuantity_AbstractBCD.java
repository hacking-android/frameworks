/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.Utility;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.RoundingUtils;
import android.icu.text.PluralRules;
import android.icu.text.UFieldPosition;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.FieldPosition;

public abstract class DecimalQuantity_AbstractBCD
implements DecimalQuantity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final double[] DOUBLE_MULTIPLIERS = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21};
    protected static final int INFINITY_FLAG = 2;
    static final byte[] INT64_BCD = new byte[]{9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8};
    protected static final int NAN_FLAG = 4;
    protected static final int NEGATIVE_FLAG = 1;
    private static final int SECTION_LOWER_EDGE = -1;
    private static final int SECTION_UPPER_EDGE = -2;
    @Deprecated
    public boolean explicitExactDouble = false;
    protected byte flags;
    protected boolean isApproximate;
    protected int lOptPos = Integer.MAX_VALUE;
    protected int lReqPos = 0;
    protected int origDelta;
    protected double origDouble;
    protected int precision;
    protected int rOptPos = Integer.MIN_VALUE;
    protected int rReqPos = 0;
    protected int scale;

    private void _setToBigDecimal(BigDecimal bigDecimal) {
        int n = bigDecimal.scale();
        this._setToBigInteger(bigDecimal.scaleByPowerOfTen(n).toBigInteger());
        this.scale -= n;
    }

    private void _setToBigInteger(BigInteger bigInteger) {
        if (bigInteger.bitLength() < 32) {
            this.readIntToBcd(bigInteger.intValue());
        } else if (bigInteger.bitLength() < 64) {
            this.readLongToBcd(bigInteger.longValue());
        } else {
            this.readBigIntegerToBcd(bigInteger);
        }
    }

    private void _setToDoubleFast(double d) {
        this.isApproximate = true;
        this.origDouble = d;
        this.origDelta = 0;
        int n = (int)((9218868437227405312L & Double.doubleToLongBits(d)) >> 52) - 1023;
        if (n <= 52 && (double)((long)d) == d) {
            this._setToLong((long)d);
            return;
        }
        if ((n = (int)((double)(52 - n) / 3.32192809489)) >= 0) {
            int n2;
            for (n2 = n; n2 >= 22; n2 -= 22) {
                d *= 1.0E22;
            }
            d *= DOUBLE_MULTIPLIERS[n2];
        } else {
            int n3;
            for (n3 = n; n3 <= -22; n3 += 22) {
                d /= 1.0E22;
            }
            d /= DOUBLE_MULTIPLIERS[-n3];
        }
        long l = Math.round(d);
        if (l != 0L) {
            this._setToLong(l);
            this.scale -= n;
        }
    }

    private void _setToInt(int n) {
        if (n == Integer.MIN_VALUE) {
            this.readLongToBcd(-((long)n));
        } else {
            this.readIntToBcd(n);
        }
    }

    private void _setToLong(long l) {
        if (l == Long.MIN_VALUE) {
            this.readBigIntegerToBcd(BigInteger.valueOf(l).negate());
        } else if (l <= Integer.MAX_VALUE) {
            this.readIntToBcd((int)l);
        } else {
            this.readLongToBcd(l);
        }
    }

    private void convertToAccurateDouble() {
        double d = this.origDouble;
        int n = this.origDelta;
        this.setBcdToZero();
        String string = Double.toString(d);
        if (string.indexOf(69) != -1) {
            int n2 = string.indexOf(69);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.charAt(0));
            stringBuilder.append(string.substring(2, n2));
            this._setToLong(Long.parseLong(stringBuilder.toString()));
            this.scale += Integer.parseInt(string.substring(n2 + 1)) - (n2 - 1) + 1;
        } else if (string.charAt(0) == '0') {
            this._setToLong(Long.parseLong(string.substring(2)));
            this.scale += 2 - string.length();
        } else if (string.charAt(string.length() - 1) == '0') {
            this._setToLong(Long.parseLong(string.substring(0, string.length() - 2)));
        } else {
            int n3 = string.indexOf(46);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(0, n3));
            stringBuilder.append(string.substring(n3 + 1));
            this._setToLong(Long.parseLong(stringBuilder.toString()));
            this.scale += n3 - string.length() + 1;
        }
        this.scale += n;
        this.compact();
        this.explicitExactDouble = true;
    }

    private int fractionCount() {
        return -this.getLowerDisplayMagnitude();
    }

    private int fractionCountWithoutTrailingZeros() {
        return Math.max(-this.scale, 0);
    }

    private static int safeSubtract(int n, int n2) {
        int n3 = n - n2;
        if (n2 < 0 && n3 < n) {
            return Integer.MAX_VALUE;
        }
        if (n2 > 0 && n3 > n) {
            return Integer.MIN_VALUE;
        }
        return n3;
    }

    @Override
    public void adjustMagnitude(int n) {
        if (this.precision != 0) {
            this.scale = Utility.addExact(this.scale, n);
            this.origDelta = Utility.addExact(this.origDelta, n);
        }
    }

    @Deprecated
    public void appendDigit(byte by, int n, boolean bl) {
        if (by == 0) {
            if (bl && this.precision != 0) {
                this.scale += n + 1;
            }
            return;
        }
        int n2 = this.scale;
        int n3 = n;
        if (n2 > 0) {
            n3 = n += n2;
            if (bl) {
                this.scale = 0;
                n3 = n;
            }
        }
        this.shiftLeft(n3 + 1);
        this.setDigitPos(0, by);
        if (bl) {
            this.scale += n3 + 1;
        }
    }

    protected abstract BigDecimal bcdToBigDecimal();

    public DecimalQuantity_AbstractBCD clear() {
        this.lOptPos = Integer.MAX_VALUE;
        this.lReqPos = 0;
        this.rReqPos = 0;
        this.rOptPos = Integer.MIN_VALUE;
        this.flags = (byte)(false ? 1 : 0);
        this.setBcdToZero();
        return this;
    }

    protected abstract void compact();

    protected abstract void copyBcdFrom(DecimalQuantity var1);

    @Override
    public void copyFrom(DecimalQuantity decimalQuantity) {
        this.copyBcdFrom(decimalQuantity);
        decimalQuantity = (DecimalQuantity_AbstractBCD)decimalQuantity;
        this.lOptPos = ((DecimalQuantity_AbstractBCD)decimalQuantity).lOptPos;
        this.lReqPos = ((DecimalQuantity_AbstractBCD)decimalQuantity).lReqPos;
        this.rReqPos = ((DecimalQuantity_AbstractBCD)decimalQuantity).rReqPos;
        this.rOptPos = ((DecimalQuantity_AbstractBCD)decimalQuantity).rOptPos;
        this.scale = ((DecimalQuantity_AbstractBCD)decimalQuantity).scale;
        this.precision = ((DecimalQuantity_AbstractBCD)decimalQuantity).precision;
        this.flags = ((DecimalQuantity_AbstractBCD)decimalQuantity).flags;
        this.origDouble = ((DecimalQuantity_AbstractBCD)decimalQuantity).origDouble;
        this.origDelta = ((DecimalQuantity_AbstractBCD)decimalQuantity).origDelta;
        this.isApproximate = ((DecimalQuantity_AbstractBCD)decimalQuantity).isApproximate;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof DecimalQuantity_AbstractBCD)) {
            return false;
        }
        object = (DecimalQuantity_AbstractBCD)object;
        int n = this.scale == ((DecimalQuantity_AbstractBCD)object).scale && this.precision == ((DecimalQuantity_AbstractBCD)object).precision && this.flags == ((DecimalQuantity_AbstractBCD)object).flags && this.lOptPos == ((DecimalQuantity_AbstractBCD)object).lOptPos && this.lReqPos == ((DecimalQuantity_AbstractBCD)object).lReqPos && this.rReqPos == ((DecimalQuantity_AbstractBCD)object).rReqPos && this.rOptPos == ((DecimalQuantity_AbstractBCD)object).rOptPos && this.isApproximate == ((DecimalQuantity_AbstractBCD)object).isApproximate ? 1 : 0;
        if (n == 0) {
            return false;
        }
        if (this.precision == 0) {
            return true;
        }
        if (this.isApproximate) {
            if (this.origDouble != ((DecimalQuantity_AbstractBCD)object).origDouble || this.origDelta != ((DecimalQuantity_AbstractBCD)object).origDelta) {
                bl = false;
            }
            return bl;
        }
        for (n = this.getUpperDisplayMagnitude(); n >= this.getLowerDisplayMagnitude(); --n) {
            if (this.getDigit(n) == ((DecimalQuantity_AbstractBCD)object).getDigit(n)) continue;
            return false;
        }
        return true;
    }

    public boolean fitsInLong() {
        if (this.isZero()) {
            return true;
        }
        if (this.scale < 0) {
            return false;
        }
        int n = this.getMagnitude();
        if (n < 18) {
            return true;
        }
        if (n > 18) {
            return false;
        }
        for (n = 0; n < this.precision; ++n) {
            byte[] arrby;
            byte by = this.getDigit(18 - n);
            if (by < (arrby = INT64_BCD)[n]) {
                return true;
            }
            if (by <= arrby[n]) continue;
            return false;
        }
        return this.isNegative();
    }

    @Override
    public byte getDigit(int n) {
        return this.getDigitPos(n - this.scale);
    }

    protected abstract byte getDigitPos(int var1);

    @Override
    public int getLowerDisplayMagnitude() {
        int n;
        block1 : {
            int n2;
            block0 : {
                n2 = this.rReqPos;
                n = this.scale;
                if (n2 >= n) break block0;
                n = n2;
                break block1;
            }
            n2 = this.rOptPos;
            if (n2 <= n) break block1;
            n = n2;
        }
        return n;
    }

    @Override
    public int getMagnitude() throws ArithmeticException {
        int n = this.precision;
        if (n != 0) {
            return this.scale + n - 1;
        }
        throw new ArithmeticException("Magnitude is not well-defined for zero");
    }

    @Override
    public double getPluralOperand(PluralRules.Operand operand) {
        int n = 1.$SwitchMap$android$icu$text$PluralRules$Operand[operand.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return Math.abs(this.toDouble());
                        }
                        return this.fractionCountWithoutTrailingZeros();
                    }
                    return this.fractionCount();
                }
                return this.toFractionLong(false);
            }
            return this.toFractionLong(true);
        }
        long l = this.isNegative() ? -this.toLong(true) : this.toLong(true);
        return l;
    }

    @Override
    public long getPositionFingerprint() {
        return 0L ^ (long)this.lOptPos ^ (long)(this.lReqPos << 16) ^ (long)this.rReqPos << 32 ^ (long)this.rOptPos << 48;
    }

    @Override
    public StandardPlural getStandardPlural(PluralRules pluralRules) {
        if (pluralRules == null) {
            return StandardPlural.OTHER;
        }
        return StandardPlural.orOtherFromString(pluralRules.select(this));
    }

    @Override
    public int getUpperDisplayMagnitude() {
        int n;
        block1 : {
            int n2;
            block0 : {
                n2 = this.lReqPos;
                n = this.scale + this.precision;
                if (n2 <= n) break block0;
                n = n2;
                break block1;
            }
            n2 = this.lOptPos;
            if (n2 >= n) break block1;
            n = n2;
        }
        return n - 1;
    }

    @Override
    public boolean isInfinite() {
        boolean bl = (this.flags & 2) != 0;
        return bl;
    }

    @Override
    public boolean isNaN() {
        boolean bl = (this.flags & 4) != 0;
        return bl;
    }

    @Override
    public boolean isNegative() {
        byte by = this.flags;
        boolean bl = true;
        if ((by & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isZero() {
        boolean bl = this.precision == 0;
        return bl;
    }

    @Override
    public void multiplyBy(BigDecimal bigDecimal) {
        if (!(this.isInfinite() || this.isZero() || this.isNaN())) {
            this.setToBigDecimal(this.toBigDecimal().multiply(bigDecimal));
            return;
        }
    }

    @Override
    public void negate() {
        this.flags = (byte)(this.flags ^ 1);
    }

    @Override
    public void populateUFieldPosition(FieldPosition fieldPosition) {
        if (fieldPosition instanceof UFieldPosition) {
            ((UFieldPosition)fieldPosition).setFractionDigits((int)this.getPluralOperand(PluralRules.Operand.v), (long)this.getPluralOperand(PluralRules.Operand.f));
        }
    }

    protected abstract void readBigIntegerToBcd(BigInteger var1);

    protected abstract void readIntToBcd(int var1);

    protected abstract void readLongToBcd(long var1);

    @Override
    public void roundToIncrement(BigDecimal bigDecimal, MathContext mathContext) {
        bigDecimal = this.toBigDecimal().divide(bigDecimal, 0, mathContext.getRoundingMode()).multiply(bigDecimal).round(mathContext);
        if (bigDecimal.signum() == 0) {
            this.setBcdToZero();
        } else {
            this.setToBigDecimal(bigDecimal);
        }
    }

    @Override
    public void roundToInfinity() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
    }

    @Override
    public void roundToMagnitude(int n, MathContext mathContext) {
        block38 : {
            block34 : {
                byte by;
                boolean bl;
                int n2;
                int n3;
                block30 : {
                    int n4;
                    block31 : {
                        int n5;
                        block35 : {
                            block37 : {
                                block36 : {
                                    block33 : {
                                        block32 : {
                                            n3 = DecimalQuantity_AbstractBCD.safeSubtract(n, this.scale);
                                            n4 = mathContext.getPrecision();
                                            if (n == Integer.MAX_VALUE) break block32;
                                            n2 = n3;
                                            if (n4 <= 0) break block33;
                                            n2 = n3;
                                            if (this.precision - n3 <= n4) break block33;
                                        }
                                        n2 = this.precision - n4;
                                    }
                                    if (n2 <= 0 && !this.isApproximate || this.precision == 0) break block34;
                                    n5 = this.getDigitPos(DecimalQuantity_AbstractBCD.safeSubtract(n2, 1));
                                    by = this.getDigitPos(n2);
                                    n4 = 2;
                                    if (this.isApproximate) break block35;
                                    if (n5 >= 5) break block36;
                                    n3 = 1;
                                    break block30;
                                }
                                if (n5 <= 5) break block37;
                                n3 = 3;
                                break block30;
                            }
                            n5 = DecimalQuantity_AbstractBCD.safeSubtract(n2, 2);
                            do {
                                n3 = n4;
                                if (n5 < 0) break block30;
                                if (this.getDigitPos(n5) != 0) {
                                    n3 = 3;
                                    break block30;
                                }
                                --n5;
                            } while (true);
                        }
                        n3 = DecimalQuantity_AbstractBCD.safeSubtract(n2, 2);
                        int n6 = Math.max(0, this.precision - 14);
                        if (n5 == 0) {
                            n5 = -1;
                            n4 = n3;
                            do {
                                n3 = n5;
                                if (n4 < n6) break block31;
                                if (this.getDigitPos(n4) != 0) {
                                    n3 = 1;
                                    break block31;
                                }
                                --n4;
                            } while (true);
                        }
                        if (n5 == 4) {
                            n5 = n3;
                            do {
                                n3 = n4;
                                if (n5 < n6) break block31;
                                if (this.getDigitPos(n5) != 9) {
                                    n3 = 1;
                                    break block31;
                                }
                                --n5;
                            } while (true);
                        }
                        if (n5 == 5) {
                            n5 = n3;
                            do {
                                n3 = n4;
                                if (n5 < n6) break block31;
                                if (this.getDigitPos(n5) != 0) {
                                    n3 = 3;
                                    break block31;
                                }
                                --n5;
                            } while (true);
                        }
                        if (n5 == 9) {
                            n5 = -2;
                            n4 = n3;
                            do {
                                n3 = n5;
                                if (n4 < n6) break block31;
                                if (this.getDigitPos(n4) != 9) {
                                    n3 = 3;
                                    break block31;
                                }
                                --n4;
                            } while (true);
                        }
                        n3 = n5 < 5 ? 1 : 3;
                    }
                    bl = RoundingUtils.roundsAtMidpoint(mathContext.getRoundingMode().ordinal());
                    if (DecimalQuantity_AbstractBCD.safeSubtract(n2, 1) < this.precision - 14 || bl && n3 == 2 || !bl && n3 < 0) break block38;
                    this.isApproximate = false;
                    this.origDouble = 0.0;
                    this.origDelta = 0;
                    if (n2 <= 0) {
                        return;
                    }
                    n4 = n3;
                    if (n3 == -1) {
                        n4 = 1;
                    }
                    n3 = n4;
                    if (n4 == -2) {
                        n3 = 3;
                    }
                }
                bl = by % 2 == 0;
                bl = RoundingUtils.getRoundingDirection(bl, this.isNegative(), n3, mathContext.getRoundingMode().ordinal(), this);
                if (n2 >= this.precision) {
                    this.setBcdToZero();
                    this.scale = n;
                } else {
                    this.shiftRight(n2);
                }
                if (!bl) {
                    if (by == 9) {
                        n = 0;
                        while (this.getDigitPos(n) == 9) {
                            ++n;
                        }
                        this.shiftRight(n);
                    }
                    n = this.getDigitPos(0);
                    this.setDigitPos(0, (byte)(n + 1));
                    ++this.precision;
                }
                this.compact();
            }
            return;
        }
        this.convertToAccurateDouble();
        this.roundToMagnitude(n, mathContext);
    }

    protected abstract void setBcdToZero();

    protected abstract void setDigitPos(int var1, byte var2);

    @Override
    public void setFractionLength(int n, int n2) {
        this.rReqPos = -n;
        this.rOptPos = -n2;
    }

    @Override
    public void setIntegerLength(int n, int n2) {
        int n3 = n;
        if (n < this.lReqPos) {
            n3 = this.lReqPos;
        }
        this.lOptPos = n2;
        this.lReqPos = n3;
    }

    @Override
    public void setToBigDecimal(BigDecimal bigDecimal) {
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
        BigDecimal bigDecimal2 = bigDecimal;
        if (bigDecimal.signum() == -1) {
            this.flags = (byte)(this.flags | 1);
            bigDecimal2 = bigDecimal.negate();
        }
        if (bigDecimal2.signum() != 0) {
            this._setToBigDecimal(bigDecimal2);
            this.compact();
        }
    }

    public void setToBigInteger(BigInteger bigInteger) {
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
        BigInteger bigInteger2 = bigInteger;
        if (bigInteger.signum() == -1) {
            this.flags = (byte)(this.flags | 1);
            bigInteger2 = bigInteger.negate();
        }
        if (bigInteger2.signum() != 0) {
            this._setToBigInteger(bigInteger2);
            this.compact();
        }
    }

    public void setToDouble(double d) {
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
        double d2 = d;
        if (Double.compare(d, 0.0) < 0) {
            this.flags = (byte)(this.flags | 1);
            d2 = -d;
        }
        if (Double.isNaN(d2)) {
            this.flags = (byte)(this.flags | 4);
        } else if (Double.isInfinite(d2)) {
            this.flags = (byte)(this.flags | 2);
        } else if (d2 != 0.0) {
            this._setToDoubleFast(d2);
            this.compact();
        }
    }

    public void setToInt(int n) {
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
        int n2 = n;
        if (n < 0) {
            this.flags = (byte)(this.flags | 1);
            n2 = -n;
        }
        if (n2 != 0) {
            this._setToInt(n2);
            this.compact();
        }
    }

    public void setToLong(long l) {
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
        long l2 = l;
        if (l < 0L) {
            this.flags = (byte)(this.flags | 1);
            l2 = -l;
        }
        if (l2 != 0L) {
            this._setToLong(l2);
            this.compact();
        }
    }

    protected abstract void shiftLeft(int var1);

    protected abstract void shiftRight(int var1);

    @Override
    public int signum() {
        int n = this.isNegative() ? -1 : (this.isZero() ? 0 : 1);
        return n;
    }

    @Override
    public BigDecimal toBigDecimal() {
        if (this.isApproximate) {
            this.convertToAccurateDouble();
        }
        return this.bcdToBigDecimal();
    }

    @Override
    public double toDouble() {
        double d;
        if (this.isNaN()) {
            return Double.NaN;
        }
        if (this.isInfinite()) {
            double d2 = this.isNegative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            return d2;
        }
        long l = 0L;
        int n = this.precision;
        int n2 = n - Math.min(n, 17);
        for (n = this.precision - 1; n >= n2; --n) {
            l = 10L * l + (long)this.getDigitPos(n);
        }
        double d3 = l;
        n = this.scale + n2;
        if (n >= 0) {
            do {
                d = d3;
                n2 = n;
                if (n < 22) break;
                if (Double.isInfinite(d3 *= 1.0E22)) {
                    n2 = 0;
                    d = d3;
                    break;
                }
                n -= 22;
            } while (true);
            d3 = d * DOUBLE_MULTIPLIERS[n2];
        } else {
            do {
                d = d3;
                n2 = n;
                if (n > -22) break;
                if ((d3 /= 1.0E22) == 0.0) {
                    n2 = 0;
                    d = d3;
                    break;
                }
                n += 22;
            } while (true);
            d3 = d / DOUBLE_MULTIPLIERS[-n2];
        }
        d = d3;
        if (this.isNegative()) {
            d = -d3;
        }
        return d;
    }

    public long toFractionLong(boolean bl) {
        long l = 0L;
        int n = -1;
        int n2 = Math.max(this.scale, this.rOptPos);
        long l2 = l;
        int n3 = n;
        int n4 = n2;
        if (bl) {
            n4 = Math.min(n2, this.rReqPos);
            n3 = n;
            l2 = l;
        }
        while (n3 >= n4 && (double)l2 <= 1.0E17) {
            l2 = 10L * l2 + (long)this.getDigitPos(n3 - this.scale);
            --n3;
        }
        l = l2;
        if (!bl) {
            do {
                l = l2;
                if (l2 <= 0L) break;
                l = l2;
                if (l2 % 10L != 0L) break;
                l2 /= 10L;
            } while (true);
        }
        return l;
    }

    public long toLong(boolean bl) {
        int n;
        long l = 0L;
        int n2 = n = Math.min(this.scale + this.precision, this.lOptPos) - 1;
        if (bl) {
            n2 = Math.min(n, 17);
        }
        while (n2 >= 0) {
            l = 10L * l + (long)this.getDigitPos(n2 - this.scale);
            --n2;
        }
        long l2 = l;
        if (this.isNegative()) {
            l2 = -l;
        }
        return l2;
    }

    @Override
    public String toPlainString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.isNegative()) {
            stringBuilder.append('-');
        }
        if (this.precision == 0 || this.getMagnitude() < 0) {
            stringBuilder.append('0');
        }
        for (int i = this.getUpperDisplayMagnitude(); i >= this.getLowerDisplayMagnitude(); --i) {
            stringBuilder.append((char)(this.getDigit(i) + 48));
            if (i != 0) continue;
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }

    public String toScientificString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.toScientificString(stringBuilder);
        return stringBuilder.toString();
    }

    public void toScientificString(StringBuilder stringBuilder) {
        int n;
        if (this.isNegative()) {
            stringBuilder.append('-');
        }
        if ((n = this.precision) == 0) {
            stringBuilder.append("0E+0");
            return;
        }
        int n2 = Math.min(n + this.scale, this.lOptPos);
        n = this.scale;
        n2 = n2 - n - 1;
        int n3 = Math.max(n, this.rOptPos) - this.scale;
        stringBuilder.append((char)(this.getDigitPos(n2) + 48));
        if (n >= n3) {
            stringBuilder.append('.');
            for (n = n2 - 1; n >= n3; --n) {
                stringBuilder.append((char)(this.getDigitPos(n) + 48));
            }
        }
        stringBuilder.append('E');
        n = this.scale + n2;
        if (n == Integer.MIN_VALUE) {
            stringBuilder.append("-2147483648");
            return;
        }
        if (n < 0) {
            n *= -1;
            stringBuilder.append('-');
        } else {
            stringBuilder.append('+');
        }
        if (n == 0) {
            stringBuilder.append('0');
        }
        n3 = stringBuilder.length();
        while (n > 0) {
            n2 = n / 10;
            stringBuilder.insert(n3, (char)(n % 10 + 48));
            n = n2;
        }
    }

    public void truncate() {
        int n = this.scale;
        if (n < 0) {
            this.shiftRight(-n);
            this.scale = 0;
            this.compact();
        }
    }

}

