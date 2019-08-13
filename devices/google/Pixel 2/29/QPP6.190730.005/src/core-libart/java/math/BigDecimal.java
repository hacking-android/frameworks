/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInt;
import java.math.BigInteger;
import java.math.Conversion;
import java.math.MathContext;
import java.math.Multiplication;
import java.math.RoundingMode;
import java.util.Arrays;
import libcore.math.MathUtils;

public class BigDecimal
extends Number
implements Comparable<BigDecimal>,
Serializable {
    private static final BigDecimal[] BI_SCALED_BY_ZERO;
    private static final int BI_SCALED_BY_ZERO_LENGTH = 11;
    private static final char[] CH_ZEROS;
    private static final BigInteger[] FIVE_POW;
    private static final double LOG10_2 = 0.3010299956639812;
    private static final long[] LONG_FIVE_POW;
    private static final int[] LONG_FIVE_POW_BIT_LENGTH;
    private static final int[] LONG_POWERS_OF_TEN_BIT_LENGTH;
    public static final BigDecimal ONE;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    public static final BigDecimal TEN;
    private static final BigInteger[] TEN_POW;
    public static final BigDecimal ZERO;
    private static final BigDecimal[] ZERO_SCALED_BY;
    private static final long serialVersionUID = 6108874887143696463L;
    private transient int bitLength;
    private transient int hashCode;
    private BigInteger intVal;
    private transient int precision;
    private int scale;
    private transient long smallValue;
    private transient String toStringImage;

    static {
        int[] arrn;
        int n;
        LONG_FIVE_POW = new long[]{1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L, 7450580596923828125L};
        LONG_FIVE_POW_BIT_LENGTH = new int[LONG_FIVE_POW.length];
        LONG_POWERS_OF_TEN_BIT_LENGTH = new int[MathUtils.LONG_POWERS_OF_TEN.length];
        BI_SCALED_BY_ZERO = new BigDecimal[11];
        ZERO_SCALED_BY = new BigDecimal[11];
        CH_ZEROS = new char[100];
        Arrays.fill(CH_ZEROS, '0');
        for (n = 0; n < ZERO_SCALED_BY.length; ++n) {
            BigDecimal.BI_SCALED_BY_ZERO[n] = new BigDecimal(n, 0);
            BigDecimal.ZERO_SCALED_BY[n] = new BigDecimal(0, n);
        }
        for (n = 0; n < (arrn = LONG_FIVE_POW_BIT_LENGTH).length; ++n) {
            arrn[n] = BigDecimal.bitLength(LONG_FIVE_POW[n]);
        }
        for (n = 0; n < (arrn = LONG_POWERS_OF_TEN_BIT_LENGTH).length; ++n) {
            arrn[n] = BigDecimal.bitLength(MathUtils.LONG_POWERS_OF_TEN[n]);
        }
        TEN_POW = Multiplication.bigTenPows;
        FIVE_POW = Multiplication.bigFivePows;
        ZERO = new BigDecimal(0, 0);
        ONE = new BigDecimal(1, 0);
        TEN = new BigDecimal(10, 0);
    }

    public BigDecimal(double d) {
        this.toStringImage = null;
        this.hashCode = 0;
        this.precision = 0;
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            long l = Double.doubleToLongBits(d);
            this.scale = 1075 - (int)(l >> 52 & 2047L);
            long l2 = this.scale == 1075 ? (l & 0xFFFFFFFFFFFFFL) << 1 : l & 0xFFFFFFFFFFFFFL | 0x10000000000000L;
            if (l2 == 0L) {
                this.scale = 0;
                this.precision = 1;
            }
            int n = this.scale;
            long l3 = l2;
            if (n > 0) {
                n = Math.min(n, Long.numberOfTrailingZeros(l2));
                l3 = l2 >>> n;
                this.scale -= n;
            }
            l2 = l3;
            if (l >> 63 != 0L) {
                l2 = -l3;
            }
            int n2 = BigDecimal.bitLength(l2);
            n = this.scale;
            if (n < 0) {
                n = n2 == 0 ? 0 : n2 - n;
                this.bitLength = n;
                if (this.bitLength < 64) {
                    this.smallValue = l2 << -this.scale;
                } else {
                    BigInt bigInt = new BigInt();
                    bigInt.putLongInt(l2);
                    bigInt.shift(-this.scale);
                    this.intVal = new BigInteger(bigInt);
                }
                this.scale = 0;
            } else if (n > 0) {
                long[] arrl = LONG_FIVE_POW;
                if (n < arrl.length && LONG_FIVE_POW_BIT_LENGTH[n] + n2 < 64) {
                    this.smallValue = arrl[n] * l2;
                    this.bitLength = BigDecimal.bitLength(this.smallValue);
                } else {
                    this.setUnscaledValue(Multiplication.multiplyByFivePow(BigInteger.valueOf(l2), this.scale));
                }
            } else {
                this.smallValue = l2;
                this.bitLength = n2;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Infinity or NaN: ");
        stringBuilder.append(d);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public BigDecimal(double d, MathContext mathContext) {
        this(d);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(int n) {
        this(n, 0);
    }

    private BigDecimal(int n, int n2) {
        this.toStringImage = null;
        this.hashCode = 0;
        this.precision = 0;
        this.smallValue = n;
        this.scale = n2;
        this.bitLength = BigDecimal.bitLength(n);
    }

    public BigDecimal(int n, MathContext mathContext) {
        this(n, 0);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(long l) {
        this(l, 0);
    }

    private BigDecimal(long l, int n) {
        this.toStringImage = null;
        this.hashCode = 0;
        this.precision = 0;
        this.smallValue = l;
        this.scale = n;
        this.bitLength = BigDecimal.bitLength(l);
    }

    public BigDecimal(long l, MathContext mathContext) {
        this(l);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(String string) {
        this(string.toCharArray(), 0, string.length());
    }

    public BigDecimal(String string, MathContext mathContext) {
        this(string.toCharArray(), 0, string.length());
        this.inplaceRound(mathContext);
    }

    public BigDecimal(BigInteger bigInteger) {
        this(bigInteger, 0);
    }

    public BigDecimal(BigInteger bigInteger, int n) {
        this.toStringImage = null;
        this.hashCode = 0;
        this.precision = 0;
        if (bigInteger != null) {
            this.scale = n;
            this.setUnscaledValue(bigInteger);
            return;
        }
        throw new NullPointerException("unscaledVal == null");
    }

    public BigDecimal(BigInteger bigInteger, int n, MathContext mathContext) {
        this(bigInteger, n);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(BigInteger bigInteger, MathContext mathContext) {
        this(bigInteger);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(char[] arrc) {
        this(arrc, 0, arrc.length);
    }

    public BigDecimal(char[] object, int n, int n2) {
        int n3 = n;
        this.toStringImage = null;
        this.hashCode = 0;
        this.precision = 0;
        int n4 = n2 - 1 + n3;
        if (object != null) {
            if (n4 < ((char[])object).length && n3 >= 0 && n2 > 0 && n4 >= 0) {
                int n5;
                StringBuilder stringBuilder = new StringBuilder(n2);
                int n6 = n3;
                int n7 = n;
                if (n3 <= n4) {
                    n6 = n3;
                    n7 = n;
                    if (object[n3] == '+') {
                        n6 = n3 + 1;
                        n7 = n + 1;
                    }
                }
                n = 0;
                n2 = 0;
                while (n6 <= n4 && object[n6] != '.' && object[n6] != 'e' && object[n6] != 'E') {
                    n5 = n;
                    n3 = n2;
                    if (n2 == 0) {
                        if (object[n6] == '0') {
                            n5 = n + 1;
                            n3 = n2;
                        } else {
                            n3 = 1;
                            n5 = n;
                        }
                    }
                    ++n6;
                    n = n5;
                    n2 = n3;
                }
                stringBuilder.append((char[])object, n7, n6 - n7);
                n5 = 0 + (n6 - n7);
                if (n6 <= n4 && object[n6] == '.') {
                    int n8 = n6 + 1;
                    n6 = n;
                    for (n = n8; n <= n4 && object[n] != 'e' && object[n] != 'E'; ++n) {
                        n3 = n6;
                        n7 = n2;
                        if (n2 == 0) {
                            if (object[n] == '0') {
                                n3 = n6 + 1;
                                n7 = n2;
                            } else {
                                n7 = 1;
                                n3 = n6;
                            }
                        }
                        n6 = n3;
                        n2 = n7;
                    }
                    n6 = this.scale = n - n8;
                    n2 = n5 + n6;
                    stringBuilder.append((char[])object, n8, n6);
                    n6 = n;
                    n = n2;
                } else {
                    this.scale = 0;
                    n = n5;
                }
                if (n6 <= n4 && (object[n6] == 'e' || object[n6] == 'E')) {
                    n2 = n6 + 1;
                    if (n2 <= n4 && object[n2] == '+' && (n6 = n2 + 1) <= n4 && object[n6] != '-') {
                        ++n2;
                    }
                    object = String.valueOf(object, n2, n4 + 1 - n2);
                    long l = (long)this.scale - (long)Integer.parseInt((String)object);
                    this.scale = (int)l;
                    if (l != (long)this.scale) {
                        throw new NumberFormatException("Scale out of range");
                    }
                }
                if (n < 19) {
                    this.smallValue = Long.parseLong(stringBuilder.toString());
                    this.bitLength = BigDecimal.bitLength(this.smallValue);
                } else {
                    this.setUnscaledValue(new BigInteger(stringBuilder.toString()));
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad offset/length: offset=");
            stringBuilder.append(n3);
            stringBuilder.append(" len=");
            stringBuilder.append(n2);
            stringBuilder.append(" in.length=");
            stringBuilder.append(((char[])object).length);
            throw new NumberFormatException(stringBuilder.toString());
        }
        throw new NullPointerException("in == null");
    }

    public BigDecimal(char[] arrc, int n, int n2, MathContext mathContext) {
        this(arrc, n, n2);
        this.inplaceRound(mathContext);
    }

    public BigDecimal(char[] arrc, MathContext mathContext) {
        this(arrc, 0, arrc.length);
        this.inplaceRound(mathContext);
    }

    private static BigDecimal addAndMult10(BigDecimal bigDecimal, BigDecimal object, int n) {
        if (n < MathUtils.LONG_POWERS_OF_TEN.length && Math.max(bigDecimal.bitLength, ((BigDecimal)object).bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[n]) + 1 < 64) {
            return BigDecimal.valueOf(bigDecimal.smallValue + ((BigDecimal)object).smallValue * MathUtils.LONG_POWERS_OF_TEN[n], bigDecimal.scale);
        }
        object = Multiplication.multiplyByTenPow(((BigDecimal)object).getUnscaledValue(), n).getBigInt();
        ((BigInt)object).add(bigDecimal.getUnscaledValue().getBigInt());
        return new BigDecimal(new BigInteger((BigInt)object), bigDecimal.scale);
    }

    private int approxPrecision() {
        int n = this.precision;
        if (n <= 0) {
            n = (int)((double)(this.bitLength - 1) * 0.3010299956639812) + 1;
        }
        return n;
    }

    private static int bitLength(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = n;
        }
        return 32 - Integer.numberOfLeadingZeros(n2);
    }

    private static int bitLength(long l) {
        long l2 = l;
        if (l < 0L) {
            l2 = l;
        }
        return 64 - Long.numberOfLeadingZeros(l2);
    }

    private static int compareAbsoluteValues(long l, long l2) {
        int n = (l = Math.abs(l) - 1L) > (l2 = Math.abs(l2) - 1L) ? 1 : (l < l2 ? -1 : 0);
        return n;
    }

    private static int compareForRounding(long l, long l2) {
        long l3 = l2 / 2L;
        if (l != l3 && l != -l3) {
            return BigDecimal.compareAbsoluteValues(l, l3);
        }
        return -((int)l2 & 1);
    }

    private int decimalDigitsInLong(long l) {
        if (l == Long.MIN_VALUE) {
            return 19;
        }
        int n = Arrays.binarySearch(MathUtils.LONG_POWERS_OF_TEN, Math.abs(l));
        n = n < 0 ? -n - 1 : ++n;
        return n;
    }

    private static BigDecimal divideBigIntegers(BigInteger bigInteger, BigInteger bigInteger2, int n, RoundingMode roundingMode) {
        Object object = bigInteger.divideAndRemainder(bigInteger2);
        BigInteger bigInteger3 = object[0];
        if (((BigInteger)(object = object[1])).signum() == 0) {
            return new BigDecimal(bigInteger3, n);
        }
        int n2 = bigInteger.signum() * bigInteger2.signum();
        if (bigInteger2.bitLength() < 63) {
            int n3 = BigDecimal.compareForRounding(((BigInteger)object).longValue(), bigInteger2.longValue());
            n2 = BigDecimal.roundingBehavior((int)bigInteger3.testBit(0), (n3 + 5) * n2, roundingMode);
        } else {
            int n4 = ((BigInteger)object).abs().shiftLeftOneBit().compareTo(bigInteger2.abs());
            n2 = BigDecimal.roundingBehavior((int)bigInteger3.testBit(0), (n4 + 5) * n2, roundingMode);
        }
        if (n2 != 0) {
            if (bigInteger3.bitLength() < 63) {
                return BigDecimal.valueOf(bigInteger3.longValue() + (long)n2, n);
            }
            return new BigDecimal(bigInteger3.add(BigInteger.valueOf(n2)), n);
        }
        return new BigDecimal(bigInteger3, n);
    }

    private static BigDecimal dividePrimitiveLongs(long l, long l2, int n, RoundingMode roundingMode) {
        long l3 = l / l2;
        long l4 = l % l2;
        int n2 = Long.signum(l);
        int n3 = Long.signum(l2);
        l = l3;
        if (l4 != 0L) {
            int n4 = BigDecimal.compareForRounding(l4, l2);
            l = l3 + (long)BigDecimal.roundingBehavior((int)l3 & 1, (n4 + 5) * (n2 * n3), roundingMode);
        }
        return BigDecimal.valueOf(l, n);
    }

    private BigInteger getUnscaledValue() {
        if (this.intVal == null) {
            this.intVal = BigInteger.valueOf(this.smallValue);
        }
        return this.intVal;
    }

    private void inplaceRound(MathContext mathContext) {
        int n = mathContext.getPrecision();
        if (this.approxPrecision() >= n && n != 0) {
            long l;
            int n2 = this.precision() - n;
            if (n2 <= 0) {
                return;
            }
            if (this.bitLength < 64) {
                this.smallRound(mathContext, n2);
                return;
            }
            BigInteger bigInteger = Multiplication.powerOf10(n2);
            BigInteger[] arrbigInteger = this.getUnscaledValue().divideAndRemainder(bigInteger);
            long l2 = l = (long)this.scale - (long)n2;
            if (arrbigInteger[1].signum() != 0) {
                n2 = arrbigInteger[1].abs().shiftLeftOneBit().compareTo(bigInteger);
                n2 = BigDecimal.roundingBehavior((int)arrbigInteger[0].testBit(0), arrbigInteger[1].signum() * (n2 + 5), mathContext.getRoundingMode());
                if (n2 != 0) {
                    arrbigInteger[0] = arrbigInteger[0].add(BigInteger.valueOf(n2));
                }
                l2 = l;
                if (new BigDecimal(arrbigInteger[0]).precision() > n) {
                    arrbigInteger[0] = arrbigInteger[0].divide(BigInteger.TEN);
                    l2 = l - 1L;
                }
            }
            this.scale = BigDecimal.safeLongToInt(l2);
            this.precision = n;
            this.setUnscaledValue(arrbigInteger[0]);
            return;
        }
    }

    private boolean isZero() {
        boolean bl = this.bitLength == 0 && this.smallValue != -1L;
        return bl;
    }

    private BigDecimal movePoint(long l) {
        if (this.isZero()) {
            return BigDecimal.zeroScaledBy(Math.max(l, 0L));
        }
        if (l >= 0L) {
            if (this.bitLength < 64) {
                return BigDecimal.valueOf(this.smallValue, BigDecimal.safeLongToInt(l));
            }
            return new BigDecimal(this.getUnscaledValue(), BigDecimal.safeLongToInt(l));
        }
        if (-l < (long)MathUtils.LONG_POWERS_OF_TEN.length && this.bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[(int)(-l)] < 64) {
            return BigDecimal.valueOf(this.smallValue * MathUtils.LONG_POWERS_OF_TEN[(int)(-l)], 0);
        }
        return new BigDecimal(Multiplication.multiplyByTenPow(this.getUnscaledValue(), BigDecimal.safeLongToInt(-l)), 0);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.bitLength = this.intVal.bitLength();
        if (this.bitLength < 64) {
            this.smallValue = this.intVal.longValue();
        }
    }

    private static int roundingBehavior(int n, int n2, RoundingMode roundingMode) {
        int n3 = 0;
        switch (roundingMode) {
            default: {
                break;
            }
            case HALF_EVEN: {
                if (Math.abs(n2) + n <= 5) break;
                n3 = Integer.signum(n2);
                break;
            }
            case HALF_DOWN: {
                if (Math.abs(n2) <= 5) break;
                n3 = Integer.signum(n2);
                break;
            }
            case HALF_UP: {
                if (Math.abs(n2) < 5) break;
                n3 = Integer.signum(n2);
                break;
            }
            case FLOOR: {
                n3 = Math.min(Integer.signum(n2), 0);
                break;
            }
            case CEILING: {
                n3 = Math.max(Integer.signum(n2), 0);
                break;
            }
            case DOWN: {
                break;
            }
            case UP: {
                n3 = Integer.signum(n2);
                break;
            }
            case UNNECESSARY: {
                if (n2 == 0) break;
                throw new ArithmeticException("Rounding necessary");
            }
        }
        return n3;
    }

    private static int safeLongToInt(long l) {
        if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
            return (int)l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Out of int range: ");
        stringBuilder.append(l);
        throw new ArithmeticException(stringBuilder.toString());
    }

    private void setUnscaledValue(BigInteger bigInteger) {
        this.intVal = bigInteger;
        this.bitLength = bigInteger.bitLength();
        if (this.bitLength < 64) {
            this.smallValue = bigInteger.longValue();
        }
    }

    private void smallRound(MathContext mathContext, int n) {
        long l = MathUtils.LONG_POWERS_OF_TEN[n];
        long l2 = (long)this.scale - (long)n;
        long l3 = this.smallValue;
        long l4 = l3 / l;
        if ((l3 %= l) != 0L) {
            n = BigDecimal.compareForRounding(l3, l);
            l3 = l4 + (long)BigDecimal.roundingBehavior((int)l4 & 1, Long.signum(l3) * (n + 5), mathContext.getRoundingMode());
            l = l2;
            l4 = l3;
            if (Math.log10(Math.abs(l3)) >= (double)mathContext.getPrecision()) {
                l4 = l3 / 10L;
                l = l2 - 1L;
            }
        } else {
            l = l2;
        }
        this.scale = BigDecimal.safeLongToInt(l);
        this.precision = mathContext.getPrecision();
        this.smallValue = l4;
        this.bitLength = BigDecimal.bitLength(l4);
        this.intVal = null;
    }

    private long valueExact(int n) {
        BigInteger bigInteger = this.toBigIntegerExact();
        if (bigInteger.bitLength() < n) {
            return bigInteger.longValue();
        }
        throw new ArithmeticException("Rounding necessary");
    }

    public static BigDecimal valueOf(double d) {
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            return new BigDecimal(Double.toString(d));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Infinity or NaN: ");
        stringBuilder.append(d);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static BigDecimal valueOf(long l) {
        if (l >= 0L && l < 11L) {
            return BI_SCALED_BY_ZERO[(int)l];
        }
        return new BigDecimal(l, 0);
    }

    public static BigDecimal valueOf(long l, int n) {
        BigDecimal[] arrbigDecimal;
        if (n == 0) {
            return BigDecimal.valueOf(l);
        }
        if (l == 0L && n >= 0 && n < (arrbigDecimal = ZERO_SCALED_BY).length) {
            return arrbigDecimal[n];
        }
        return new BigDecimal(l, n);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.getUnscaledValue();
        objectOutputStream.defaultWriteObject();
    }

    private static BigDecimal zeroScaledBy(long l) {
        if (l == (long)((int)l)) {
            return BigDecimal.valueOf(0L, (int)l);
        }
        if (l >= 0L) {
            return new BigDecimal(0, Integer.MAX_VALUE);
        }
        return new BigDecimal(0, Integer.MIN_VALUE);
    }

    public BigDecimal abs() {
        BigDecimal bigDecimal = this.signum() < 0 ? this.negate() : this;
        return bigDecimal;
    }

    public BigDecimal abs(MathContext mathContext) {
        BigDecimal bigDecimal = this.signum() < 0 ? this.negate() : new BigDecimal(this.getUnscaledValue(), this.scale);
        bigDecimal.inplaceRound(mathContext);
        return bigDecimal;
    }

    public BigDecimal add(BigDecimal bigDecimal) {
        int n = this.scale - bigDecimal.scale;
        if (this.isZero()) {
            if (n <= 0) {
                return bigDecimal;
            }
            if (bigDecimal.isZero()) {
                return this;
            }
        } else if (bigDecimal.isZero() && n >= 0) {
            return this;
        }
        if (n == 0) {
            if (Math.max(this.bitLength, bigDecimal.bitLength) + 1 < 64) {
                return BigDecimal.valueOf(this.smallValue + bigDecimal.smallValue, this.scale);
            }
            return new BigDecimal(this.getUnscaledValue().add(bigDecimal.getUnscaledValue()), this.scale);
        }
        if (n > 0) {
            return BigDecimal.addAndMult10(this, bigDecimal, n);
        }
        return BigDecimal.addAndMult10(bigDecimal, this, -n);
    }

    public BigDecimal add(BigDecimal number, MathContext mathContext) {
        block4 : {
            block7 : {
                BigDecimal bigDecimal;
                BigDecimal bigDecimal2;
                block6 : {
                    long l;
                    block5 : {
                        l = (long)this.scale - (long)number.scale;
                        if (number.isZero() || this.isZero() || mathContext.getPrecision() == 0) break block4;
                        if ((long)this.approxPrecision() >= l - 1L) break block5;
                        bigDecimal = number;
                        bigDecimal2 = this;
                        break block6;
                    }
                    if ((long)number.approxPrecision() >= -l - 1L) break block7;
                    bigDecimal = this;
                    bigDecimal2 = number;
                }
                if (mathContext.getPrecision() >= bigDecimal.approxPrecision()) {
                    return this.add((BigDecimal)number).round(mathContext);
                }
                int n = bigDecimal.signum();
                number = n == bigDecimal2.signum() ? Multiplication.multiplyByPositiveInt(bigDecimal.getUnscaledValue(), 10).add(BigInteger.valueOf(n)) : Multiplication.multiplyByPositiveInt(bigDecimal.getUnscaledValue().subtract(BigInteger.valueOf(n)), 10).add(BigInteger.valueOf(n * 9));
                return new BigDecimal((BigInteger)number, bigDecimal.scale + 1).round(mathContext);
            }
            return this.add((BigDecimal)number).round(mathContext);
        }
        return this.add((BigDecimal)number).round(mathContext);
    }

    public byte byteValueExact() {
        return (byte)this.valueExact(8);
    }

    @Override
    public int compareTo(BigDecimal number) {
        int n = this.signum();
        int n2 = number.signum();
        int n3 = -1;
        if (n == n2) {
            BigInteger bigInteger;
            if (this.scale == number.scale && this.bitLength < 64 && number.bitLength < 64) {
                long l = this.smallValue;
                long l2 = number.smallValue;
                if (l >= l2) {
                    n3 = l > l2 ? 1 : 0;
                }
                return n3;
            }
            long l = (long)this.scale - (long)number.scale;
            n3 = this.approxPrecision() - number.approxPrecision();
            if ((long)n3 > l + 1L) {
                return n;
            }
            if ((long)n3 < l - 1L) {
                return -n;
            }
            BigInteger bigInteger2 = this.getUnscaledValue();
            BigInteger bigInteger3 = number.getUnscaledValue();
            if (l < 0L) {
                bigInteger = bigInteger2.multiply(Multiplication.powerOf10(-l));
                number = bigInteger3;
            } else {
                bigInteger = bigInteger2;
                number = bigInteger3;
                if (l > 0L) {
                    number = bigInteger3.multiply(Multiplication.powerOf10(l));
                    bigInteger = bigInteger2;
                }
            }
            return bigInteger.compareTo((BigInteger)number);
        }
        if (n < n2) {
            return -1;
        }
        return 1;
    }

    public BigDecimal divide(BigDecimal number) {
        BigInteger bigInteger = this.getUnscaledValue();
        Object object = ((BigDecimal)number).getUnscaledValue();
        long l = (long)this.scale - (long)((BigDecimal)number).scale;
        int n = 0;
        int n2 = 1;
        int n3 = FIVE_POW.length;
        if (!((BigDecimal)number).isZero()) {
            if (bigInteger.signum() == 0) {
                return BigDecimal.zeroScaledBy(l);
            }
            number = bigInteger.gcd((BigInteger)object);
            bigInteger = bigInteger.divide((BigInteger)number);
            number = ((BigInteger)object).divide((BigInteger)number);
            int n4 = ((BigInteger)number).getLowestSetBit();
            number = ((BigInteger)number).shiftRight(n4);
            do {
                if ((object = ((BigInteger)number).divideAndRemainder(FIVE_POW[n2]))[1].signum() == 0) {
                    n += n2;
                    int n5 = n2;
                    if (n2 < n3 - 1) {
                        n5 = n2 + 1;
                    }
                    number = object[0];
                    n2 = n5;
                    continue;
                }
                if (n2 == 1) {
                    if (((BigInteger)number).abs().equals(BigInteger.ONE)) {
                        object = bigInteger;
                        if (((BigInteger)number).signum() < 0) {
                            object = bigInteger.negate();
                        }
                        n2 = BigDecimal.safeLongToInt((long)Math.max(n4, n) + l);
                        number = (n = n4 - n) > 0 ? Multiplication.multiplyByFivePow((BigInteger)object, n) : ((BigInteger)object).shiftLeft(-n);
                        return new BigDecimal((BigInteger)number, n2);
                    }
                    throw new ArithmeticException("Non-terminating decimal expansion; no exact representable decimal result");
                }
                n2 = 1;
            } while (true);
        }
        throw new ArithmeticException("Division by zero");
    }

    public BigDecimal divide(BigDecimal bigDecimal, int n) {
        return this.divide(bigDecimal, this.scale, RoundingMode.valueOf(n));
    }

    public BigDecimal divide(BigDecimal bigDecimal, int n, int n2) {
        return this.divide(bigDecimal, n, RoundingMode.valueOf(n2));
    }

    public BigDecimal divide(BigDecimal serializable, int n, RoundingMode roundingMode) {
        if (roundingMode != null) {
            if (!((BigDecimal)serializable).isZero()) {
                long l = (long)this.scale - (long)((BigDecimal)serializable).scale - (long)n;
                if (BigDecimal.bitLength(l) <= 32) {
                    BigInteger bigInteger;
                    if (this.bitLength < 64 && ((BigDecimal)serializable).bitLength < 64) {
                        if (l == 0L) {
                            if (this.smallValue != Long.MIN_VALUE || ((BigDecimal)serializable).smallValue != -1L) {
                                return BigDecimal.dividePrimitiveLongs(this.smallValue, ((BigDecimal)serializable).smallValue, n, roundingMode);
                            }
                        } else if (l > 0L) {
                            if (l < (long)MathUtils.LONG_POWERS_OF_TEN.length && ((BigDecimal)serializable).bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[(int)l] < 64) {
                                return BigDecimal.dividePrimitiveLongs(this.smallValue, ((BigDecimal)serializable).smallValue * MathUtils.LONG_POWERS_OF_TEN[(int)l], n, roundingMode);
                            }
                        } else if (-l < (long)MathUtils.LONG_POWERS_OF_TEN.length && this.bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[(int)(-l)] < 64) {
                            return BigDecimal.dividePrimitiveLongs(this.smallValue * MathUtils.LONG_POWERS_OF_TEN[(int)(-l)], ((BigDecimal)serializable).smallValue, n, roundingMode);
                        }
                    }
                    BigInteger bigInteger2 = this.getUnscaledValue();
                    BigInteger bigInteger3 = BigDecimal.super.getUnscaledValue();
                    if (l > 0L) {
                        serializable = Multiplication.multiplyByTenPow(bigInteger3, (int)l);
                        bigInteger = bigInteger2;
                    } else {
                        bigInteger = bigInteger2;
                        serializable = bigInteger3;
                        if (l < 0L) {
                            bigInteger = Multiplication.multiplyByTenPow(bigInteger2, (int)(-l));
                            serializable = bigInteger3;
                        }
                    }
                    return BigDecimal.divideBigIntegers(bigInteger, (BigInteger)serializable, n, roundingMode);
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unable to perform divisor / dividend scaling: the difference in scale is too big (");
                ((StringBuilder)serializable).append(l);
                ((StringBuilder)serializable).append(")");
                throw new ArithmeticException(((StringBuilder)serializable).toString());
            }
            throw new ArithmeticException("Division by zero");
        }
        throw new NullPointerException("roundingMode == null");
    }

    public BigDecimal divide(BigDecimal object, MathContext mathContext) {
        long l;
        long l2 = (long)mathContext.getPrecision() + 2L + (long)((BigDecimal)object).approxPrecision() - (long)this.approxPrecision();
        long l3 = l = (long)this.scale - (long)((BigDecimal)object).scale;
        int n = 1;
        int n2 = TEN_POW.length;
        Object object2 = new BigInteger[]{this.getUnscaledValue()};
        if (mathContext.getPrecision() != 0 && !this.isZero() && !((BigDecimal)object).isZero()) {
            long l4 = l3;
            if (l2 > 0L) {
                object2[0] = this.getUnscaledValue().multiply(Multiplication.powerOf10(l2));
                l4 = l3 + l2;
            }
            BigInteger[] arrbigInteger = object2[0].divideAndRemainder(((BigDecimal)object).getUnscaledValue());
            object2 = arrbigInteger[0];
            if (arrbigInteger[1].signum() != 0) {
                n = arrbigInteger[1].shiftLeftOneBit().compareTo(BigDecimal.super.getUnscaledValue());
                object2 = ((BigInteger)object2).multiply(BigInteger.TEN).add(BigInteger.valueOf(arrbigInteger[0].signum() * (n + 5)));
                l3 = l4 + 1L;
            } else {
                object = object2;
                do {
                    l3 = l4;
                    object2 = object;
                    if (((BigInteger)object).testBit(0)) break;
                    object2 = ((BigInteger)object).divideAndRemainder(TEN_POW[n]);
                    if (((BigInteger)object2[1]).signum() == 0 && l4 - (long)n >= l) {
                        l4 -= (long)n;
                        int n3 = n;
                        if (n < n2 - 1) {
                            n3 = n + 1;
                        }
                        object = object2[0];
                        n = n3;
                        continue;
                    }
                    if (n == 1) {
                        l3 = l4;
                        object2 = object;
                        break;
                    }
                    n = 1;
                } while (true);
            }
            return new BigDecimal((BigInteger)object2, BigDecimal.safeLongToInt(l3), mathContext);
        }
        return this.divide((BigDecimal)object);
    }

    public BigDecimal divide(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return this.divide(bigDecimal, this.scale, roundingMode);
    }

    public BigDecimal[] divideAndRemainder(BigDecimal bigDecimal) {
        BigDecimal[] arrbigDecimal;
        arrbigDecimal = new BigDecimal[]{this.divideToIntegralValue(bigDecimal), this.subtract(arrbigDecimal[0].multiply(bigDecimal))};
        return arrbigDecimal;
    }

    public BigDecimal[] divideAndRemainder(BigDecimal bigDecimal, MathContext mathContext) {
        BigDecimal[] arrbigDecimal;
        arrbigDecimal = new BigDecimal[]{this.divideToIntegralValue(bigDecimal, mathContext), this.subtract(arrbigDecimal[0].multiply(bigDecimal))};
        return arrbigDecimal;
    }

    public BigDecimal divideToIntegralValue(BigDecimal number) {
        long l = (long)this.scale - (long)((BigDecimal)number).scale;
        long l2 = 0L;
        int n = 1;
        int n2 = TEN_POW.length;
        if (!((BigDecimal)number).isZero()) {
            if ((long)((BigDecimal)number).approxPrecision() + l <= (long)this.approxPrecision() + 1L && !this.isZero()) {
                if (l == 0L) {
                    number = this.getUnscaledValue().divide(BigDecimal.super.getUnscaledValue());
                    l2 = l;
                } else if (l > 0L) {
                    BigInteger bigInteger = Multiplication.powerOf10(l);
                    number = this.getUnscaledValue().divide(BigDecimal.super.getUnscaledValue().multiply(bigInteger)).multiply(bigInteger);
                    l2 = l;
                } else {
                    BigInteger[] arrbigInteger = Multiplication.powerOf10(-l);
                    number = this.getUnscaledValue().multiply((BigInteger)arrbigInteger).divide(BigDecimal.super.getUnscaledValue());
                    while (!((BigInteger)number).testBit(0)) {
                        int n3;
                        arrbigInteger = ((BigInteger)number).divideAndRemainder(TEN_POW[n]);
                        if (arrbigInteger[1].signum() == 0 && l2 - (long)n >= l) {
                            l2 -= (long)n;
                            n3 = n;
                            if (n < n2 - 1) {
                                n3 = n + 1;
                            }
                            number = arrbigInteger[0];
                        } else {
                            if (n == 1) break;
                            n3 = 1;
                        }
                        n = n3;
                    }
                }
            } else {
                number = BigInteger.ZERO;
                l2 = l;
            }
            number = ((BigInteger)number).signum() == 0 ? BigDecimal.zeroScaledBy(l2) : new BigDecimal((BigInteger)number, BigDecimal.safeLongToInt(l2));
            return number;
        }
        throw new ArithmeticException("Division by zero");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public BigDecimal divideToIntegralValue(BigDecimal var1_1, MathContext var2_2) {
        block11 : {
            var3_3 = var2_2.getPrecision();
            var4_4 = this.precision() - var1_1.precision();
            var5_5 = BigDecimal.TEN_POW.length;
            var8_7 = var6_6 = (long)this.scale - (long)var1_1.scale;
            var10_8 = (long)var4_4 - var6_6 + 1L;
            var12_9 /* !! */  = new BigInteger[2];
            if (var3_3 == 0) return this.divideToIntegralValue((BigDecimal)var1_1);
            if (this.isZero() != false) return this.divideToIntegralValue((BigDecimal)var1_1);
            if (BigDecimal.super.isZero()) {
                return this.divideToIntegralValue((BigDecimal)var1_1);
            }
            var2_2 = "Division impossible";
            if (var10_8 > 0L) break block11;
            var12_9 /* !! */ [0] = BigInteger.ZERO;
            ** GOTO lbl18
        }
        if (var6_6 == 0L) {
            var12_9 /* !! */ [0] = this.getUnscaledValue().divide(BigDecimal.super.getUnscaledValue());
lbl18: // 2 sources:
            var1_1 = var12_9 /* !! */ ;
        } else if (var6_6 > 0L) {
            var12_9 /* !! */ [0] = this.getUnscaledValue().divide(BigDecimal.super.getUnscaledValue().multiply(Multiplication.powerOf10(var6_6)));
            var8_7 = Math.min(var6_6, Math.max((long)var3_3 - var10_8 + 1L, 0L));
            var12_9 /* !! */ [0] = var12_9 /* !! */ [0].multiply(Multiplication.powerOf10(var8_7));
            var1_1 = var12_9 /* !! */ ;
        } else {
            var10_8 = Math.min(-var6_6, Math.max((long)var3_3 - (long)var4_4, 0L));
            var12_9 /* !! */  = this.getUnscaledValue().multiply(Multiplication.powerOf10(var10_8)).divideAndRemainder(BigDecimal.super.getUnscaledValue());
            var10_8 = var8_7 + var10_8;
            var13_10 = -var10_8;
            if (var12_9 /* !! */ [1].signum() != 0 && var13_10 > 0L) {
                var8_7 = var15_11 = (long)new BigDecimal(var12_9 /* !! */ [1]).precision() + var13_10 - (long)var1_1.precision();
                if (var15_11 == 0L) {
                    var12_9 /* !! */ [1] = var12_9 /* !! */ [1].multiply(Multiplication.powerOf10(var13_10)).divide(BigDecimal.super.getUnscaledValue());
                    var8_7 = Math.abs(var12_9 /* !! */ [1].signum());
                }
                if (var8_7 > 0L) throw new ArithmeticException("Division impossible");
            }
            var8_7 = var10_8;
            var1_1 = var12_9 /* !! */ ;
        }
        if (var1_1[0].signum() == 0) {
            return BigDecimal.zeroScaledBy(var6_6);
        }
        var12_9 /* !! */  = var1_1[0];
        var17_12 = new BigDecimal((BigInteger)var1_1[0]);
        var10_8 = var17_12.precision();
        var4_4 = 1;
        var1_1 = var2_2;
        var2_2 = var12_9 /* !! */ ;
        while (!var2_2.testBit(0)) {
            var12_9 /* !! */  = var2_2.divideAndRemainder(BigDecimal.TEN_POW[var4_4]);
            if (var12_9 /* !! */ [1].signum() == 0 && (var10_8 - (long)var4_4 >= (long)var3_3 || var8_7 - (long)var4_4 >= var6_6)) {
                var10_8 -= (long)var4_4;
                var8_7 -= (long)var4_4;
                var18_13 = var4_4;
                if (var4_4 < var5_5 - 1) {
                    var18_13 = var4_4 + 1;
                }
                var2_2 = var12_9 /* !! */ [0];
                var4_4 = var18_13;
                continue;
            }
            if (var4_4 == 1) break;
            var4_4 = 1;
        }
        if (var10_8 > (long)var3_3) throw new ArithmeticException((String)var1_1);
        var17_12.scale = BigDecimal.safeLongToInt(var8_7);
        var17_12.setUnscaledValue((BigInteger)var2_2);
        return var17_12;
    }

    @Override
    public double doubleValue() {
        int n;
        block15 : {
            long l;
            int n2;
            block19 : {
                block21 : {
                    long l2;
                    block20 : {
                        int n3;
                        int n4;
                        long l3;
                        int n5;
                        block18 : {
                            long l4;
                            Object object;
                            block16 : {
                                long l5;
                                block17 : {
                                    n = this.signum();
                                    n4 = 1076;
                                    l = (long)this.bitLength - (long)((double)this.scale / 0.3010299956639812);
                                    if (l < -1074L || n == 0) break block15;
                                    if (l > 1025L) {
                                        return (double)n * Double.POSITIVE_INFINITY;
                                    }
                                    BigInteger bigInteger = this.getUnscaledValue().abs();
                                    n2 = this.scale;
                                    if (n2 <= 0) {
                                        object = bigInteger.multiply(Multiplication.powerOf10(-n2));
                                    } else {
                                        BigInteger bigInteger2 = Multiplication.powerOf10(n2);
                                        n2 = 100 - (int)l;
                                        object = bigInteger;
                                        if (n2 > 0) {
                                            object = bigInteger.shiftLeft(n2);
                                            n4 = 1076 - n2;
                                        }
                                        object = object.divideAndRemainder(bigInteger2);
                                        n2 = object[1].shiftLeftOneBit().compareTo(bigInteger2);
                                        object = object[0].shiftLeft(2).add(BigInteger.valueOf((n2 + 3) * n2 / 2 + 1));
                                        n4 -= 2;
                                    }
                                    n5 = object.getLowestSetBit();
                                    n3 = object.bitLength() - 54;
                                    if (n3 <= 0) break block16;
                                    l3 = l5 = object.shiftRight(n3).longValue();
                                    if ((l5 & 1L) == 1L && n5 < n3) break block17;
                                    l = l5;
                                    l2 = l3;
                                    if ((l5 & 3L) != 3L) break block18;
                                }
                                l = l5 + 2L;
                                l2 = l3;
                                break block18;
                            }
                            l3 = l4 = object.longValue() << -n3;
                            l = l4;
                            l2 = l3;
                            if ((l4 & 3L) == 3L) {
                                l = l4 + 2L;
                                l2 = l3;
                            }
                        }
                        if ((l & 0x40000000000000L) == 0L) {
                            l >>= 1;
                            n4 += n3;
                        } else {
                            l >>= 2;
                            n4 += n3 + 1;
                        }
                        if (n4 > 2046) {
                            return (double)n * Double.POSITIVE_INFINITY;
                        }
                        n2 = n4;
                        if (n4 > 0) break block19;
                        if (n4 < -53) {
                            return (double)n * 0.0;
                        }
                        l3 = l2 >> 1;
                        l2 = l3 >> -n4;
                        if ((l2 & 3L) == 3L) break block20;
                        l = l2;
                        if ((l2 & 1L) != 1L) break block21;
                        l = l2;
                        if ((l3 & -1L >>> n4 + 63) == 0L) break block21;
                        l = l2;
                        if (n5 >= n3) break block21;
                    }
                    l = l2 + 1L;
                }
                n2 = 0;
                l >>= 1;
            }
            return Double.longBitsToDouble((long)n & Long.MIN_VALUE | (long)n2 << 52 | 0xFFFFFFFFFFFFFL & l);
        }
        return (double)n * 0.0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof BigDecimal) {
            int n;
            int n2;
            object = (BigDecimal)object;
            if (((BigDecimal)object).scale != this.scale || (n2 = ((BigDecimal)object).bitLength) != (n = this.bitLength) || !(n < 64 ? ((BigDecimal)object).smallValue == this.smallValue : ((BigDecimal)object).intVal.equals(this.intVal))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public float floatValue() {
        float f = this.signum();
        long l = (long)this.bitLength - (long)((double)this.scale / 0.3010299956639812);
        f = l >= -149L && f != 0.0f ? (l > 129L ? (f *= Float.POSITIVE_INFINITY) : (float)this.doubleValue()) : (f *= 0.0f);
        return f;
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        if (this.bitLength < 64) {
            long l = this.smallValue;
            this.hashCode = (int)(l & -1L);
            this.hashCode = this.hashCode * 33 + (int)(l >> 32 & -1L);
            this.hashCode = this.hashCode * 17 + this.scale;
            return this.hashCode;
        }
        this.hashCode = this.intVal.hashCode() * 17 + this.scale;
        return this.hashCode;
    }

    @Override
    public int intValue() {
        int n = this.scale;
        n = n > -32 && n <= this.approxPrecision() ? this.toBigInteger().intValue() : 0;
        return n;
    }

    public int intValueExact() {
        return (int)this.valueExact(32);
    }

    @Override
    public long longValue() {
        int n = this.scale;
        long l = n > -64 && n <= this.approxPrecision() ? this.toBigInteger().longValue() : 0L;
        return l;
    }

    public long longValueExact() {
        return this.valueExact(64);
    }

    public BigDecimal max(BigDecimal bigDecimal) {
        block0 : {
            if (this.compareTo(bigDecimal) < 0) break block0;
            bigDecimal = this;
        }
        return bigDecimal;
    }

    public BigDecimal min(BigDecimal bigDecimal) {
        block0 : {
            if (this.compareTo(bigDecimal) > 0) break block0;
            bigDecimal = this;
        }
        return bigDecimal;
    }

    public BigDecimal movePointLeft(int n) {
        return this.movePoint((long)this.scale + (long)n);
    }

    public BigDecimal movePointRight(int n) {
        return this.movePoint((long)this.scale - (long)n);
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        long l = (long)this.scale + (long)bigDecimal.scale;
        if (!this.isZero() && !bigDecimal.isZero()) {
            long l2;
            long l3;
            boolean bl;
            if (this.bitLength + bigDecimal.bitLength < 64 && !(bl = (l3 = bigDecimal.smallValue * (l2 = this.smallValue)) == Long.MIN_VALUE && Math.signum(l2) * Math.signum(bigDecimal.smallValue) > 0.0f)) {
                return BigDecimal.valueOf(l3, BigDecimal.safeLongToInt(l));
            }
            return new BigDecimal(this.getUnscaledValue().multiply(bigDecimal.getUnscaledValue()), BigDecimal.safeLongToInt(l));
        }
        return BigDecimal.zeroScaledBy(l);
    }

    public BigDecimal multiply(BigDecimal bigDecimal, MathContext mathContext) {
        bigDecimal = this.multiply(bigDecimal);
        bigDecimal.inplaceRound(mathContext);
        return bigDecimal;
    }

    public BigDecimal negate() {
        int n = this.bitLength;
        if (n >= 63 && (n != 63 || this.smallValue == Long.MIN_VALUE)) {
            return new BigDecimal(this.getUnscaledValue().negate(), this.scale);
        }
        return BigDecimal.valueOf(-this.smallValue, this.scale);
    }

    public BigDecimal negate(MathContext mathContext) {
        BigDecimal bigDecimal = this.negate();
        bigDecimal.inplaceRound(mathContext);
        return bigDecimal;
    }

    public BigDecimal plus() {
        return this;
    }

    public BigDecimal plus(MathContext mathContext) {
        return this.round(mathContext);
    }

    public BigDecimal pow(int n) {
        if (n == 0) {
            return ONE;
        }
        if (n >= 0 && n <= 999999999) {
            long l = (long)this.scale * (long)n;
            BigDecimal bigDecimal = this.isZero() ? BigDecimal.zeroScaledBy(l) : new BigDecimal(this.getUnscaledValue().pow(n), BigDecimal.safeLongToInt(l));
            return bigDecimal;
        }
        throw new ArithmeticException("Invalid operation");
    }

    public BigDecimal pow(int n, MathContext mathContext) {
        int n2 = Math.abs(n);
        int n3 = mathContext.getPrecision();
        int n4 = (int)Math.log10(n2) + 1;
        MathContext mathContext2 = mathContext;
        if (!(n == 0 || this.isZero() && n > 0)) {
            if (!(n2 > 999999999 || n3 == 0 && n < 0 || n3 > 0 && n4 > n3)) {
                BigDecimal bigDecimal;
                if (n3 > 0) {
                    mathContext2 = new MathContext(n3 + n4 + 1, mathContext.getRoundingMode());
                }
                BigDecimal bigDecimal2 = this.round(mathContext2);
                for (n4 = Integer.highestOneBit((int)n2) >> 1; n4 > 0; n4 >>= 1) {
                    bigDecimal2 = bigDecimal = bigDecimal2.multiply(bigDecimal2, mathContext2);
                    if ((n2 & n4) != n4) continue;
                    bigDecimal2 = bigDecimal.multiply(this, mathContext2);
                }
                bigDecimal = bigDecimal2;
                if (n < 0) {
                    bigDecimal = ONE.divide(bigDecimal2, mathContext2);
                }
                bigDecimal.inplaceRound(mathContext);
                return bigDecimal;
            }
            throw new ArithmeticException("Invalid operation");
        }
        return this.pow(n);
    }

    public int precision() {
        int n = this.precision;
        if (n != 0) {
            return n;
        }
        n = this.bitLength;
        if (n == 0) {
            this.precision = 1;
        } else if (n < 64) {
            this.precision = this.decimalDigitsInLong(this.smallValue);
        } else {
            int n2;
            n = n2 = (int)((double)(n - 1) * 0.3010299956639812) + 1;
            if (this.getUnscaledValue().divide(Multiplication.powerOf10(n2)).signum() != 0) {
                n = n2 + 1;
            }
            this.precision = n;
        }
        return this.precision;
    }

    public BigDecimal remainder(BigDecimal bigDecimal) {
        return this.divideAndRemainder(bigDecimal)[1];
    }

    public BigDecimal remainder(BigDecimal bigDecimal, MathContext mathContext) {
        return this.divideAndRemainder(bigDecimal, mathContext)[1];
    }

    public BigDecimal round(MathContext mathContext) {
        BigDecimal bigDecimal = new BigDecimal(this.getUnscaledValue(), this.scale);
        bigDecimal.inplaceRound(mathContext);
        return bigDecimal;
    }

    public int scale() {
        return this.scale;
    }

    public BigDecimal scaleByPowerOfTen(int n) {
        long l = (long)this.scale - (long)n;
        if (this.bitLength < 64) {
            long l2 = this.smallValue;
            if (l2 == 0L) {
                return BigDecimal.zeroScaledBy(l);
            }
            return BigDecimal.valueOf(l2, BigDecimal.safeLongToInt(l));
        }
        return new BigDecimal(this.getUnscaledValue(), BigDecimal.safeLongToInt(l));
    }

    public BigDecimal setScale(int n) {
        return this.setScale(n, RoundingMode.UNNECESSARY);
    }

    public BigDecimal setScale(int n, int n2) {
        return this.setScale(n, RoundingMode.valueOf(n2));
    }

    public BigDecimal setScale(int n, RoundingMode roundingMode) {
        if (roundingMode != null) {
            long l = (long)n - (long)this.scale;
            if (l == 0L) {
                return this;
            }
            if (l > 0L) {
                if (l < (long)MathUtils.LONG_POWERS_OF_TEN.length && this.bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[(int)l] < 64) {
                    return BigDecimal.valueOf(this.smallValue * MathUtils.LONG_POWERS_OF_TEN[(int)l], n);
                }
                return new BigDecimal(Multiplication.multiplyByTenPow(this.getUnscaledValue(), (int)l), n);
            }
            if (this.bitLength < 64 && -l < (long)MathUtils.LONG_POWERS_OF_TEN.length) {
                return BigDecimal.dividePrimitiveLongs(this.smallValue, MathUtils.LONG_POWERS_OF_TEN[(int)(-l)], n, roundingMode);
            }
            return BigDecimal.divideBigIntegers(this.getUnscaledValue(), Multiplication.powerOf10(-l), n, roundingMode);
        }
        throw new NullPointerException("roundingMode == null");
    }

    public short shortValueExact() {
        return (short)this.valueExact(16);
    }

    public int signum() {
        if (this.bitLength < 64) {
            return Long.signum(this.smallValue);
        }
        return this.getUnscaledValue().signum();
    }

    public BigDecimal stripTrailingZeros() {
        int n = 1;
        int n2 = TEN_POW.length;
        long l = this.scale;
        if (this.isZero()) {
            return new BigDecimal(BigInteger.ZERO, 0);
        }
        BigInteger bigInteger = this.getUnscaledValue();
        while (!bigInteger.testBit(0)) {
            BigInteger[] arrbigInteger = bigInteger.divideAndRemainder(TEN_POW[n]);
            if (arrbigInteger[1].signum() == 0) {
                l -= (long)n;
                int n3 = n;
                if (n < n2 - 1) {
                    n3 = n + 1;
                }
                bigInteger = arrbigInteger[0];
                n = n3;
                continue;
            }
            if (n == 1) break;
            n = 1;
        }
        return new BigDecimal(bigInteger, BigDecimal.safeLongToInt(l));
    }

    public BigDecimal subtract(BigDecimal bigDecimal) {
        int n = this.scale - bigDecimal.scale;
        if (this.isZero()) {
            if (n <= 0) {
                return bigDecimal.negate();
            }
            if (bigDecimal.isZero()) {
                return this;
            }
        } else if (bigDecimal.isZero() && n >= 0) {
            return this;
        }
        if (n == 0) {
            if (Math.max(this.bitLength, bigDecimal.bitLength) + 1 < 64) {
                return BigDecimal.valueOf(this.smallValue - bigDecimal.smallValue, this.scale);
            }
            return new BigDecimal(this.getUnscaledValue().subtract(bigDecimal.getUnscaledValue()), this.scale);
        }
        if (n > 0) {
            if (n < MathUtils.LONG_POWERS_OF_TEN.length && Math.max(this.bitLength, bigDecimal.bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[n]) + 1 < 64) {
                return BigDecimal.valueOf(this.smallValue - bigDecimal.smallValue * MathUtils.LONG_POWERS_OF_TEN[n], this.scale);
            }
            return new BigDecimal(this.getUnscaledValue().subtract(Multiplication.multiplyByTenPow(bigDecimal.getUnscaledValue(), n)), this.scale);
        }
        if ((n = -n) < MathUtils.LONG_POWERS_OF_TEN.length && Math.max(this.bitLength + LONG_POWERS_OF_TEN_BIT_LENGTH[n], bigDecimal.bitLength) + 1 < 64) {
            return BigDecimal.valueOf(this.smallValue * MathUtils.LONG_POWERS_OF_TEN[n] - bigDecimal.smallValue, bigDecimal.scale);
        }
        return new BigDecimal(Multiplication.multiplyByTenPow(this.getUnscaledValue(), n).subtract(bigDecimal.getUnscaledValue()), bigDecimal.scale);
    }

    public BigDecimal subtract(BigDecimal number, MathContext mathContext) {
        long l = number.scale;
        long l2 = this.scale;
        if (!number.isZero() && !this.isZero() && mathContext.getPrecision() != 0) {
            if ((long)number.approxPrecision() < l - l2 - 1L && mathContext.getPrecision() < this.approxPrecision()) {
                int n = this.signum();
                number = n != number.signum() ? Multiplication.multiplyByPositiveInt(this.getUnscaledValue(), 10).add(BigInteger.valueOf(n)) : Multiplication.multiplyByPositiveInt(this.getUnscaledValue().subtract(BigInteger.valueOf(n)), 10).add(BigInteger.valueOf(n * 9));
                return new BigDecimal((BigInteger)number, this.scale + 1).round(mathContext);
            }
            return this.subtract((BigDecimal)number).round(mathContext);
        }
        return this.subtract((BigDecimal)number).round(mathContext);
    }

    public BigInteger toBigInteger() {
        if (this.scale != 0 && !this.isZero()) {
            if (this.scale < 0) {
                return this.getUnscaledValue().multiply(Multiplication.powerOf10(-((long)this.scale)));
            }
            return this.getUnscaledValue().divide(Multiplication.powerOf10(this.scale));
        }
        return this.getUnscaledValue();
    }

    public BigInteger toBigIntegerExact() {
        if (this.scale != 0 && !this.isZero()) {
            int n = this.scale;
            if (n < 0) {
                return this.getUnscaledValue().multiply(Multiplication.powerOf10(-((long)this.scale)));
            }
            if (n <= this.approxPrecision() && this.scale <= this.getUnscaledValue().getLowestSetBit()) {
                BigInteger[] arrbigInteger = this.getUnscaledValue().divideAndRemainder(Multiplication.powerOf10(this.scale));
                if (arrbigInteger[1].signum() == 0) {
                    return arrbigInteger[0];
                }
                throw new ArithmeticException("Rounding necessary");
            }
            throw new ArithmeticException("Rounding necessary");
        }
        return this.getUnscaledValue();
    }

    public String toEngineeringString() {
        CharSequence charSequence = this.getUnscaledValue().toString();
        if (this.scale == 0) {
            return charSequence;
        }
        int n = this.getUnscaledValue().signum() < 0 ? 2 : 1;
        int n2 = ((String)charSequence).length();
        long l = -((long)this.scale) + (long)n2 - (long)n;
        charSequence = new StringBuilder((String)charSequence);
        int n3 = this.scale;
        if (n3 > 0 && l >= -6L) {
            if (l >= 0L) {
                ((StringBuilder)charSequence).insert(n2 - n3, '.');
            } else {
                ((StringBuilder)charSequence).insert(n - 1, "0.");
                ((StringBuilder)charSequence).insert(n + 1, CH_ZEROS, 0, -((int)l) - 1);
            }
        } else {
            int n4 = n2 - n;
            int n5 = (int)(l % 3L);
            int n6 = n;
            n3 = n2;
            long l2 = l;
            if (n5 != 0) {
                if (this.getUnscaledValue().signum() == 0) {
                    n3 = n5 < 0 ? -n5 : 3 - n5;
                    l += (long)n3;
                    n5 = n3;
                } else {
                    n3 = n5 < 0 ? n5 + 3 : n5;
                    l -= (long)n3;
                    n += n3;
                    n5 = n3;
                }
                n6 = n;
                n3 = n2;
                l2 = l;
                if (n4 < 3) {
                    n5 -= n4;
                    do {
                        n6 = n;
                        n3 = ++n2;
                        l2 = l;
                        if (n5 <= 0) break;
                        ((StringBuilder)charSequence).insert(n2, '0');
                        --n5;
                    } while (true);
                }
            }
            n = n3;
            if (n3 - n6 >= 1) {
                ((StringBuilder)charSequence).insert(n6, '.');
                n = n3 + 1;
            }
            if (l2 != 0L) {
                ((StringBuilder)charSequence).insert(n, 'E');
                n3 = n;
                if (l2 > 0L) {
                    n3 = n + 1;
                    ((StringBuilder)charSequence).insert(n3, '+');
                }
                ((StringBuilder)charSequence).insert(n3 + 1, Long.toString(l2));
            }
        }
        return ((StringBuilder)charSequence).toString();
    }

    public String toPlainString() {
        char[] arrc = this.getUnscaledValue().toString();
        if (!(this.scale == 0 || this.isZero() && this.scale < 0)) {
            int n = this.signum() < 0 ? 1 : 0;
            int n2 = this.scale;
            StringBuilder stringBuilder = new StringBuilder(arrc.length() + 1 + Math.abs(this.scale));
            if (n == 1) {
                stringBuilder.append('-');
            }
            if (this.scale > 0) {
                if ((n2 -= arrc.length() - n) >= 0) {
                    char[] arrc2;
                    stringBuilder.append("0.");
                    while (n2 > (arrc2 = CH_ZEROS).length) {
                        stringBuilder.append(arrc2);
                        n2 -= CH_ZEROS.length;
                    }
                    stringBuilder.append(arrc2, 0, n2);
                    stringBuilder.append(arrc.substring(n));
                } else {
                    n2 = n - n2;
                    stringBuilder.append(arrc.substring(n, n2));
                    stringBuilder.append('.');
                    stringBuilder.append(arrc.substring(n2));
                }
            } else {
                stringBuilder.append(arrc.substring(n));
                for (n = n2; n < -(arrc = CH_ZEROS).length; n += BigDecimal.CH_ZEROS.length) {
                    stringBuilder.append(arrc);
                }
                stringBuilder.append(arrc, 0, -n);
            }
            return stringBuilder.toString();
        }
        return arrc;
    }

    public String toString() {
        String string = this.toStringImage;
        if (string != null) {
            return string;
        }
        if (this.bitLength < 32) {
            this.toStringImage = Conversion.toDecimalScaledString(this.smallValue, this.scale);
            return this.toStringImage;
        }
        string = this.getUnscaledValue().toString();
        if (this.scale == 0) {
            return string;
        }
        int n = this.getUnscaledValue().signum() < 0 ? 2 : 1;
        int n2 = string.length();
        long l = -((long)this.scale) + (long)n2 - (long)n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        int n3 = this.scale;
        if (n3 > 0 && l >= -6L) {
            if (l >= 0L) {
                stringBuilder.insert(n2 - n3, '.');
            } else {
                stringBuilder.insert(n - 1, "0.");
                stringBuilder.insert(n + 1, CH_ZEROS, 0, -((int)l) - 1);
            }
        } else {
            n3 = n2;
            if (n2 - n >= 1) {
                stringBuilder.insert(n, '.');
                n3 = n2 + 1;
            }
            stringBuilder.insert(n3, 'E');
            n = n3;
            if (l > 0L) {
                n = n3 + 1;
                stringBuilder.insert(n, '+');
            }
            stringBuilder.insert(n + 1, Long.toString(l));
        }
        this.toStringImage = stringBuilder.toString();
        return this.toStringImage;
    }

    public BigDecimal ulp() {
        return BigDecimal.valueOf(1L, this.scale);
    }

    public BigInteger unscaledValue() {
        return this.getUnscaledValue();
    }

}

