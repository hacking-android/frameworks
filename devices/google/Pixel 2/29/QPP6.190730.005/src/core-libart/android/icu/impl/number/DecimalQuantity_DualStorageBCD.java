/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_AbstractBCD;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class DecimalQuantity_DualStorageBCD
extends DecimalQuantity_AbstractBCD {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] bcdBytes;
    private long bcdLong;
    private boolean usingBytes;

    public DecimalQuantity_DualStorageBCD() {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setBcdToZero();
        this.flags = (byte)(false ? 1 : 0);
    }

    public DecimalQuantity_DualStorageBCD(double d) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToDouble(d);
    }

    public DecimalQuantity_DualStorageBCD(int n) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToInt(n);
    }

    public DecimalQuantity_DualStorageBCD(long l) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToLong(l);
    }

    public DecimalQuantity_DualStorageBCD(DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.copyFrom(decimalQuantity_DualStorageBCD);
    }

    public DecimalQuantity_DualStorageBCD(Number number) {
        block9 : {
            block3 : {
                block8 : {
                    block7 : {
                        block6 : {
                            block5 : {
                                block4 : {
                                    block2 : {
                                        this.bcdLong = 0L;
                                        this.usingBytes = false;
                                        if (!(number instanceof Long)) break block2;
                                        this.setToLong(number.longValue());
                                        break block3;
                                    }
                                    if (!(number instanceof Integer)) break block4;
                                    this.setToInt(number.intValue());
                                    break block3;
                                }
                                if (!(number instanceof Float)) break block5;
                                this.setToDouble(number.doubleValue());
                                break block3;
                            }
                            if (!(number instanceof Double)) break block6;
                            this.setToDouble(number.doubleValue());
                            break block3;
                        }
                        if (!(number instanceof BigInteger)) break block7;
                        this.setToBigInteger((BigInteger)number);
                        break block3;
                    }
                    if (!(number instanceof BigDecimal)) break block8;
                    this.setToBigDecimal((BigDecimal)number);
                    break block3;
                }
                if (!(number instanceof android.icu.math.BigDecimal)) break block9;
                this.setToBigDecimal(((android.icu.math.BigDecimal)number).toBigDecimal());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number is of an unsupported type: ");
        stringBuilder.append(number.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DecimalQuantity_DualStorageBCD(BigDecimal bigDecimal) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToBigDecimal(bigDecimal);
    }

    public DecimalQuantity_DualStorageBCD(BigInteger bigInteger) {
        this.bcdLong = 0L;
        this.usingBytes = false;
        this.setToBigInteger(bigInteger);
    }

    private void ensureCapacity() {
        this.ensureCapacity(40);
    }

    private void ensureCapacity(int n) {
        if (n == 0) {
            return;
        }
        int n2 = this.usingBytes ? this.bcdBytes.length : 0;
        if (!this.usingBytes) {
            this.bcdBytes = new byte[n];
        } else if (n2 < n) {
            byte[] arrby = new byte[n * 2];
            System.arraycopy((byte[])this.bcdBytes, (int)0, (byte[])arrby, (int)0, (int)n2);
            this.bcdBytes = arrby;
        }
        this.usingBytes = true;
    }

    private void switchStorage() {
        if (this.usingBytes) {
            this.bcdLong = 0L;
            for (int i = this.precision - 1; i >= 0; --i) {
                this.bcdLong <<= 4;
                this.bcdLong |= (long)this.bcdBytes[i];
            }
            this.bcdBytes = null;
            this.usingBytes = false;
        } else {
            this.ensureCapacity();
            for (int i = 0; i < this.precision; ++i) {
                byte[] arrby = this.bcdBytes;
                long l = this.bcdLong;
                arrby[i] = (byte)(15L & l);
                this.bcdLong = l >>> 4;
            }
        }
    }

    private String toNumberString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.usingBytes) {
            if (this.precision == 0) {
                stringBuilder.append('0');
            }
            for (int i = this.precision - 1; i >= 0; --i) {
                stringBuilder.append(this.bcdBytes[i]);
            }
        } else {
            stringBuilder.append(Long.toHexString(this.bcdLong));
        }
        stringBuilder.append("E");
        stringBuilder.append(this.scale);
        return stringBuilder.toString();
    }

    @Override
    protected BigDecimal bcdToBigDecimal() {
        if (this.usingBytes) {
            BigDecimal bigDecimal;
            BigDecimal bigDecimal2 = bigDecimal = new BigDecimal(this.toNumberString());
            if (this.isNegative()) {
                bigDecimal2 = bigDecimal.negate();
            }
            return bigDecimal2;
        }
        long l = 0L;
        for (int i = this.precision - 1; i >= 0; --i) {
            l = 10L * l + (long)this.getDigitPos(i);
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(l);
        bigDecimal = (long)(bigDecimal.scale() + this.scale) <= Integer.MIN_VALUE ? BigDecimal.ZERO : bigDecimal.scaleByPowerOfTen(this.scale);
        BigDecimal bigDecimal3 = bigDecimal;
        if (this.isNegative()) {
            bigDecimal3 = bigDecimal.negate();
        }
        return bigDecimal3;
    }

    @Deprecated
    public String checkHealth() {
        if (this.usingBytes) {
            int n;
            if (this.bcdLong != 0L) {
                return "Value in bcdLong but we are in byte mode";
            }
            if (this.precision == 0) {
                return "Zero precision but we are in byte mode";
            }
            if (this.precision > this.bcdBytes.length) {
                return "Precision exceeds length of byte array";
            }
            if (this.getDigitPos(this.precision - 1) == 0) {
                return "Most significant digit is zero in byte mode";
            }
            if (this.getDigitPos(0) == 0) {
                return "Least significant digit is zero in long mode";
            }
            for (n = 0; n < this.precision; ++n) {
                if (this.getDigitPos(n) >= 10) {
                    return "Digit exceeding 10 in byte array";
                }
                if (this.getDigitPos(n) >= 0) continue;
                return "Digit below 0 in byte array";
            }
            for (n = this.precision; n < this.bcdBytes.length; ++n) {
                if (this.getDigitPos(n) == 0) continue;
                return "Nonzero digits outside of range in byte array";
            }
        } else {
            int n;
            if (this.bcdBytes != null) {
                byte[] arrby;
                for (n = 0; n < (arrby = this.bcdBytes).length; ++n) {
                    if (arrby[n] == 0) continue;
                    return "Nonzero digits in byte array but we are in long mode";
                }
            }
            if (this.precision == 0 && this.bcdLong != 0L) {
                return "Value in bcdLong even though precision is zero";
            }
            if (this.precision > 16) {
                return "Precision exceeds length of long";
            }
            if (this.precision != 0 && this.getDigitPos(this.precision - 1) == 0) {
                return "Most significant digit is zero in long mode";
            }
            if (this.precision != 0 && this.getDigitPos(0) == 0) {
                return "Least significant digit is zero in long mode";
            }
            for (n = 0; n < this.precision; ++n) {
                if (this.getDigitPos(n) >= 10) {
                    return "Digit exceeding 10 in long";
                }
                if (this.getDigitPos(n) >= 0) continue;
                return "Digit below 0 in long (?!)";
            }
            for (n = this.precision; n < 16; ++n) {
                if (this.getDigitPos(n) == 0) continue;
                return "Nonzero digits outside of range in long";
            }
        }
        return null;
    }

    @Override
    protected void compact() {
        if (this.usingBytes) {
            int n;
            for (n = 0; n < this.precision && this.bcdBytes[n] == 0; ++n) {
            }
            if (n == this.precision) {
                this.setBcdToZero();
                return;
            }
            this.shiftRight(n);
            for (n = this.precision - 1; n >= 0 && this.bcdBytes[n] == 0; --n) {
            }
            this.precision = n + 1;
            if (this.precision <= 16) {
                this.switchStorage();
            }
        } else {
            long l = this.bcdLong;
            if (l == 0L) {
                this.setBcdToZero();
                return;
            }
            int n = Long.numberOfTrailingZeros(l) / 4;
            this.bcdLong >>>= n * 4;
            this.scale += n;
            this.precision = 16 - Long.numberOfLeadingZeros(this.bcdLong) / 4;
        }
    }

    @Override
    protected void copyBcdFrom(DecimalQuantity decimalQuantity) {
        decimalQuantity = (DecimalQuantity_DualStorageBCD)decimalQuantity;
        this.setBcdToZero();
        if (((DecimalQuantity_DualStorageBCD)decimalQuantity).usingBytes) {
            this.ensureCapacity(((DecimalQuantity_DualStorageBCD)decimalQuantity).precision);
            System.arraycopy((byte[])((DecimalQuantity_DualStorageBCD)decimalQuantity).bcdBytes, (int)0, (byte[])this.bcdBytes, (int)0, (int)((DecimalQuantity_DualStorageBCD)decimalQuantity).precision);
        } else {
            this.bcdLong = ((DecimalQuantity_DualStorageBCD)decimalQuantity).bcdLong;
        }
    }

    @Override
    public DecimalQuantity createCopy() {
        return new DecimalQuantity_DualStorageBCD(this);
    }

    @Override
    protected byte getDigitPos(int n) {
        if (this.usingBytes) {
            if (n >= 0 && n < this.precision) {
                return this.bcdBytes[n];
            }
            return 0;
        }
        if (n >= 0 && n < 16) {
            return (byte)(this.bcdLong >>> n * 4 & 15L);
        }
        return 0;
    }

    @Deprecated
    public boolean isUsingBytes() {
        return this.usingBytes;
    }

    @Override
    public int maxRepresentableDigits() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void readBigIntegerToBcd(BigInteger object) {
        this.ensureCapacity();
        int n = 0;
        while (object.signum() != 0) {
            object = object.divideAndRemainder(BigInteger.TEN);
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = object[1].byteValue();
            object = object[0];
            ++n;
        }
        this.scale = 0;
        this.precision = n;
    }

    @Override
    protected void readIntToBcd(int n) {
        long l = 0L;
        int n2 = 16;
        int n3 = n;
        n = n2;
        while (n3 != 0) {
            l = (l >>> 4) + ((long)n3 % 10L << 60);
            n3 /= 10;
            --n;
        }
        this.bcdLong = l >>> n * 4;
        this.scale = 0;
        this.precision = 16 - n;
    }

    @Override
    protected void readLongToBcd(long l) {
        if (l >= 10000000000000000L) {
            this.ensureCapacity();
            int n = 0;
            while (l != 0L) {
                this.bcdBytes[n] = (byte)(l % 10L);
                l /= 10L;
                ++n;
            }
            this.scale = 0;
            this.precision = n;
        } else {
            long l2 = 0L;
            int n = 16;
            while (l != 0L) {
                l2 = (l2 >>> 4) + (l % 10L << 60);
                l /= 10L;
                --n;
            }
            this.bcdLong = l2 >>> n * 4;
            this.scale = 0;
            this.precision = 16 - n;
        }
    }

    @Override
    protected void setBcdToZero() {
        if (this.usingBytes) {
            this.bcdBytes = null;
            this.usingBytes = false;
        }
        this.bcdLong = 0L;
        this.scale = 0;
        this.precision = 0;
        this.isApproximate = false;
        this.origDouble = 0.0;
        this.origDelta = 0;
    }

    @Override
    protected void setDigitPos(int n, byte by) {
        if (this.usingBytes) {
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = by;
        } else if (n >= 16) {
            this.switchStorage();
            this.ensureCapacity(n + 1);
            this.bcdBytes[n] = by;
        } else {
            this.bcdLong = this.bcdLong & 15L << (n *= 4) | (long)by << n;
        }
    }

    @Override
    protected void shiftLeft(int n) {
        if (!this.usingBytes && this.precision + n > 16) {
            this.switchStorage();
        }
        if (this.usingBytes) {
            this.ensureCapacity(this.precision + n);
            int n2 = this.precision + n - 1;
            do {
                if (n2 < n) break;
                byte[] arrby = this.bcdBytes;
                arrby[n2] = arrby[n2 - n];
            } while (true);
            for (int i = --n2; i >= 0; --i) {
                this.bcdBytes[i] = (byte)(false ? 1 : 0);
            }
        } else {
            this.bcdLong <<= n * 4;
        }
        this.scale -= n;
        this.precision += n;
    }

    @Override
    protected void shiftRight(int n) {
        if (this.usingBytes) {
            int n2 = 0;
            do {
                if (n2 >= this.precision - n) break;
                byte[] arrby = this.bcdBytes;
                arrby[n2] = arrby[n2 + n];
            } while (true);
            for (int i = ++n2; i < this.precision; ++i) {
                this.bcdBytes[i] = (byte)(false ? 1 : 0);
            }
        } else {
            this.bcdLong >>>= n * 4;
        }
        this.scale += n;
        this.precision -= n;
    }

    public String toString() {
        String string = this.lOptPos > 1000 ? "999" : String.valueOf(this.lOptPos);
        int n = this.lReqPos;
        int n2 = this.rReqPos;
        String string2 = this.rOptPos < -1000 ? "-999" : String.valueOf(this.rOptPos);
        String string3 = this.usingBytes ? "bytes" : "long";
        String string4 = this.isNegative() ? "-" : "";
        return String.format("<DecimalQuantity %s:%d:%d:%s %s %s%s>", string, n, n2, string2, string3, string4, this.toNumberString());
    }
}

