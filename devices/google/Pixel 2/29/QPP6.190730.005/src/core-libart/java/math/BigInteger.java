/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInt;
import java.math.BitLevel;
import java.math.Conversion;
import java.math.Logical;
import java.math.Primality;
import java.util.Random;

public class BigInteger
extends Number
implements Comparable<BigInteger>,
Serializable {
    static final BigInteger MINUS_ONE;
    public static final BigInteger ONE;
    static final BigInteger[] SMALL_VALUES;
    public static final BigInteger TEN;
    public static final BigInteger ZERO;
    private static final long serialVersionUID = -8287574255936472291L;
    private transient BigInt bigInt;
    transient int[] digits;
    private transient int firstNonzeroDigit;
    private transient int hashCode;
    private transient boolean javaIsValid;
    private byte[] magnitude;
    private transient boolean nativeIsValid;
    transient int numberLength;
    transient int sign;
    private int signum;

    static {
        ZERO = new BigInteger(0, 0L);
        ONE = new BigInteger(1, 1L);
        TEN = new BigInteger(1, 10L);
        MINUS_ONE = new BigInteger(-1, 1L);
        SMALL_VALUES = new BigInteger[]{ZERO, ONE, new BigInteger(1, 2L), new BigInteger(1, 3L), new BigInteger(1, 4L), new BigInteger(1, 5L), new BigInteger(1, 6L), new BigInteger(1, 7L), new BigInteger(1, 8L), new BigInteger(1, 9L), TEN};
    }

    public BigInteger(int n, int n2, Random object) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        if (n >= 2) {
            if (n < 16) {
                do {
                    int n3;
                    n2 = n3 = ((Random)object).nextInt() & (1 << n) - 1 | 1 << n - 1;
                    if (n <= 2) continue;
                    n2 = n3 | 1;
                } while (!BigInteger.isSmallPrime(n2));
                object = new BigInt();
                ((BigInt)object).putULongInt(n2, false);
                this.setBigInt((BigInt)object);
            } else {
                do {
                    this.setBigInt(BigInt.generatePrimeDefault(n));
                } while (this.bitLength() != n);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bitLength < 2: ");
        ((StringBuilder)object).append(n);
        throw new ArithmeticException(((StringBuilder)object).toString());
    }

    BigInteger(int n, int n2, int[] arrn) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        this.setJavaRepresentation(n, n2, arrn);
    }

    BigInteger(int n, long l) {
        boolean bl = false;
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        BigInt bigInt = new BigInt();
        if (n < 0) {
            bl = true;
        }
        bigInt.putULongInt(l, bl);
        this.setBigInt(bigInt);
    }

    public BigInteger(int n, Random serializable) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        if (n >= 0) {
            if (n == 0) {
                this.setJavaRepresentation(0, 1, new int[]{0});
            } else {
                int n2;
                int n3 = n + 31 >> 5;
                int[] arrn = new int[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    arrn[n2] = ((Random)serializable).nextInt();
                }
                n2 = n3 - 1;
                arrn[n2] = arrn[n2] >>> (-n & 31);
                this.setJavaRepresentation(1, n3, arrn);
            }
            this.javaIsValid = true;
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("numBits < 0: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public BigInteger(int n, byte[] object) {
        boolean bl = false;
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        if (object != null) {
            if (n >= -1 && n <= 1) {
                if (n == 0) {
                    int n2 = ((byte[])object).length;
                    for (int i = 0; i < n2; ++i) {
                        if (object[i] == false) {
                            continue;
                        }
                        throw new NumberFormatException("signum-magnitude mismatch");
                    }
                }
                BigInt bigInt = new BigInt();
                if (n < 0) {
                    bl = true;
                }
                bigInt.putBigEndian((byte[])object, bl);
                this.setBigInt(bigInt);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid signum: ");
            ((StringBuilder)object).append(n);
            throw new NumberFormatException(((StringBuilder)object).toString());
        }
        throw new NullPointerException("magnitude == null");
    }

    public BigInteger(String string) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        BigInt bigInt = new BigInt();
        bigInt.putDecString(string);
        this.setBigInt(bigInt);
    }

    public BigInteger(String charSequence, int n) {
        block2 : {
            block6 : {
                block7 : {
                    block4 : {
                        block5 : {
                            block3 : {
                                this.nativeIsValid = false;
                                this.javaIsValid = false;
                                this.firstNonzeroDigit = -2;
                                this.hashCode = 0;
                                if (charSequence == null) break block2;
                                if (n != 10) break block3;
                                BigInt bigInt = new BigInt();
                                bigInt.putDecString((String)charSequence);
                                this.setBigInt(bigInt);
                                break block4;
                            }
                            if (n != 16) break block5;
                            BigInt bigInt = new BigInt();
                            bigInt.putHexString((String)charSequence);
                            this.setBigInt(bigInt);
                            break block4;
                        }
                        if (n < 2 || n > 36) break block6;
                        if (((String)charSequence).isEmpty()) break block7;
                        BigInteger.parseFromString(this, (String)charSequence, n);
                    }
                    return;
                }
                throw new NumberFormatException("value.isEmpty()");
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid radix: ");
            ((StringBuilder)charSequence).append(n);
            throw new NumberFormatException(((StringBuilder)charSequence).toString());
        }
        throw new NullPointerException("value == null");
    }

    BigInteger(BigInt bigInt) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        if (bigInt != null && bigInt.hasNativeBignum()) {
            this.setBigInt(bigInt);
            return;
        }
        throw new AssertionError();
    }

    public BigInteger(byte[] arrby) {
        this.nativeIsValid = false;
        this.javaIsValid = false;
        this.firstNonzeroDigit = -2;
        this.hashCode = 0;
        if (arrby.length != 0) {
            BigInt bigInt = new BigInt();
            bigInt.putBigEndianTwosComplement(arrby);
            this.setBigInt(bigInt);
            return;
        }
        throw new NumberFormatException("value.length == 0");
    }

    static int inplaceAdd(int[] arrn, int n, int n2) {
        long l = (long)n2 & 0xFFFFFFFFL;
        for (n2 = 0; l != 0L && n2 < n; l >>= 32, ++n2) {
            arrn[n2] = (int)(l += (long)arrn[n2] & 0xFFFFFFFFL);
        }
        return (int)l;
    }

    private static boolean isSmallPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        int n2 = (int)Math.sqrt(n);
        for (int i = 3; i <= n2; i += 2) {
            if (n % i != 0) continue;
            return false;
        }
        return true;
    }

    static int multiplyByInt(int[] arrn, int[] arrn2, int n, int n2) {
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            arrn[i] = (int)(l += ((long)arrn2[i] & 0xFFFFFFFFL) * (0xFFFFFFFFL & (long)n2));
            l >>>= 32;
        }
        return (int)l;
    }

    private static void parseFromString(BigInteger bigInteger, String string, int n) {
        int n2;
        int n3;
        int n4;
        int n5 = string.length();
        if (string.charAt(0) == '-') {
            n2 = -1;
            n4 = 1;
            n3 = n5 - 1;
        } else {
            n2 = 1;
            n4 = 0;
            n3 = n5;
        }
        int n6 = Conversion.digitFitInInt[n];
        int n7 = n3 / n6;
        int n8 = n3 % n6;
        n3 = n7;
        if (n8 != 0) {
            n3 = n7 + 1;
        }
        int[] arrn = new int[n3];
        int n9 = Conversion.bigRadices[n - 2];
        n7 = 0;
        n3 = n8 == 0 ? n6 : n8;
        n3 += n4;
        n8 = n4;
        n4 = n3;
        n3 = n7;
        while (n8 < n5) {
            n8 = Integer.parseInt(string.substring(n8, n4), n);
            arrn[n3] = BigInteger.multiplyByInt(arrn, arrn, n3, n9) + BigInteger.inplaceAdd(arrn, n3, n8);
            n8 = n4;
            n4 = n8 + n6;
            ++n3;
        }
        bigInteger.setJavaRepresentation(n2, n3, arrn);
    }

    public static BigInteger probablePrime(int n, Random random) {
        return new BigInteger(n, 100, random);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        object = new BigInt();
        byte[] arrby = this.magnitude;
        boolean bl = this.signum < 0;
        ((BigInt)object).putBigEndian(arrby, bl);
        this.setBigInt((BigInt)object);
    }

    private void setBigInt(BigInt bigInt) {
        this.bigInt = bigInt;
        this.nativeIsValid = true;
    }

    private void setJavaRepresentation(int n, int n2, int[] arrn) {
        int n3;
        do {
            n3 = n2;
            if (n2 <= 0) break;
            n3 = --n2;
        } while (arrn[n2] == 0);
        if (arrn[n3] == 0) {
            n = 0;
        }
        this.sign = n;
        this.digits = arrn;
        this.numberLength = n3 + 1;
        this.javaIsValid = true;
    }

    private byte[] twosComplement() {
        this.prepareJavaRepresentation();
        if (this.sign == 0) {
            return new byte[]{0};
        }
        int n = this.bitLength();
        int n2 = this.getFirstNonzeroDigit();
        int n3 = (n >> 3) + 1;
        byte[] arrby = new byte[n3];
        int n4 = 0;
        int n5 = 4;
        if (n3 - (this.numberLength << 2) == 1) {
            n = this.sign < 0 ? -1 : 0;
            arrby[0] = (byte)n;
            n = 4;
            n4 = 0 + 1;
        } else {
            n = n3 & 3;
            if (n == 0) {
                n = 4;
            }
        }
        int n6 = n2;
        n3 = n2 = n3 - (n2 << 2);
        int n7 = n5;
        int n8 = n6;
        if (this.sign < 0) {
            int n9;
            n3 = -this.digits[n6];
            int n10 = n6 + 1;
            if (n10 == this.numberLength) {
                n5 = n;
            }
            n7 = 0;
            n6 = n3;
            do {
                n3 = n2--;
                n8 = n5;
                n9 = n10;
                if (n7 >= n5) break;
                arrby[n2] = (byte)n6;
                ++n7;
                n6 >>= 8;
            } while (true);
            while (n3 > n4) {
                n6 = this.digits[n9];
                n5 = n8;
                if (++n9 == this.numberLength) {
                    n5 = n;
                }
                n2 = 0;
                while (n2 < n5) {
                    arrby[--n3] = (byte)n6;
                    ++n2;
                    n6 >>= 8;
                }
                n8 = n5;
            }
        } else {
            while (n3 > n4) {
                n6 = this.digits[n8];
                n5 = n7;
                if (++n8 == this.numberLength) {
                    n5 = n;
                }
                n2 = 0;
                while (n2 < n5) {
                    arrby[--n3] = (byte)n6;
                    ++n2;
                    n6 >>= 8;
                }
                n7 = n5;
            }
        }
        return arrby;
    }

    public static BigInteger valueOf(long l) {
        if (l < 0L) {
            if (l != -1L) {
                return new BigInteger(-1, -l);
            }
            return MINUS_ONE;
        }
        BigInteger[] arrbigInteger = SMALL_VALUES;
        if (l < (long)arrbigInteger.length) {
            return arrbigInteger[(int)l];
        }
        return new BigInteger(1, l);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        BigInt bigInt = this.getBigInt();
        this.signum = bigInt.sign();
        this.magnitude = bigInt.bigEndianMagnitude();
        objectOutputStream.defaultWriteObject();
    }

    public BigInteger abs() {
        BigInt bigInt = this.getBigInt();
        if (bigInt.sign() >= 0) {
            return this;
        }
        bigInt = bigInt.copy();
        bigInt.setSign(1);
        return new BigInteger(bigInt);
    }

    public BigInteger add(BigInteger bigInteger) {
        BigInt bigInt = this.getBigInt();
        BigInt bigInt2 = bigInteger.getBigInt();
        if (bigInt2.sign() == 0) {
            return this;
        }
        if (bigInt.sign() == 0) {
            return bigInteger;
        }
        return new BigInteger(BigInt.addition(bigInt, bigInt2));
    }

    public BigInteger and(BigInteger bigInteger) {
        this.prepareJavaRepresentation();
        bigInteger.prepareJavaRepresentation();
        return Logical.and(this, bigInteger);
    }

    public BigInteger andNot(BigInteger bigInteger) {
        this.prepareJavaRepresentation();
        bigInteger.prepareJavaRepresentation();
        return Logical.andNot(this, bigInteger);
    }

    public int bitCount() {
        this.prepareJavaRepresentation();
        return BitLevel.bitCount(this);
    }

    public int bitLength() {
        if (!this.nativeIsValid && this.javaIsValid) {
            return BitLevel.bitLength(this);
        }
        return this.getBigInt().bitLength();
    }

    public BigInteger clearBit(int n) {
        this.prepareJavaRepresentation();
        if (this.testBit(n)) {
            return BitLevel.flipBit(this, n);
        }
        return this;
    }

    @Override
    public int compareTo(BigInteger bigInteger) {
        return BigInt.cmp(this.getBigInt(), bigInteger.getBigInt());
    }

    BigInteger copy() {
        this.prepareJavaRepresentation();
        int n = this.numberLength;
        int[] arrn = new int[n];
        System.arraycopy(this.digits, 0, arrn, 0, n);
        return new BigInteger(this.sign, this.numberLength, arrn);
    }

    public BigInteger divide(BigInteger bigInteger) {
        BigInt bigInt = new BigInt();
        BigInt.division(this.getBigInt(), bigInteger.getBigInt(), bigInt, null);
        return new BigInteger(bigInt);
    }

    public BigInteger[] divideAndRemainder(BigInteger object) {
        BigInt bigInt = ((BigInteger)object).getBigInt();
        object = new BigInt();
        BigInt bigInt2 = new BigInt();
        BigInt.division(this.getBigInt(), bigInt, (BigInt)object, bigInt2);
        return new BigInteger[]{new BigInteger((BigInt)object), new BigInteger(bigInt2)};
    }

    @Override
    public double doubleValue() {
        return Conversion.bigInteger2Double(this);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof BigInteger) {
            if (this.compareTo((BigInteger)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public BigInteger flipBit(int n) {
        this.prepareJavaRepresentation();
        if (n >= 0) {
            return BitLevel.flipBit(this, n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("n < 0: ");
        stringBuilder.append(n);
        throw new ArithmeticException(stringBuilder.toString());
    }

    @Override
    public float floatValue() {
        return (float)this.doubleValue();
    }

    public BigInteger gcd(BigInteger bigInteger) {
        return new BigInteger(BigInt.gcd(this.getBigInt(), bigInteger.getBigInt()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    BigInt getBigInt() {
        if (this.nativeIsValid) {
            return this.bigInt;
        }
        synchronized (this) {
            if (this.nativeIsValid) {
                return this.bigInt;
            }
            BigInt bigInt = new BigInt();
            int[] arrn = this.digits;
            boolean bl = this.sign < 0;
            bigInt.putLittleEndianInts(arrn, bl);
            this.setBigInt(bigInt);
            return bigInt;
        }
    }

    int getFirstNonzeroDigit() {
        if (this.firstNonzeroDigit == -2) {
            int n;
            if (this.sign == 0) {
                n = -1;
            } else {
                int n2 = 0;
                do {
                    n = ++n2;
                } while (this.digits[n2] == 0);
            }
            this.firstNonzeroDigit = n;
        }
        return this.firstNonzeroDigit;
    }

    public int getLowestSetBit() {
        this.prepareJavaRepresentation();
        if (this.sign == 0) {
            return -1;
        }
        int n = this.getFirstNonzeroDigit();
        return (n << 5) + Integer.numberOfTrailingZeros(this.digits[n]);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.prepareJavaRepresentation();
            int n = 0;
            for (int i = 0; i < this.numberLength; ++i) {
                n = n * 33 + this.digits[i];
            }
            this.hashCode = this.sign * n;
        }
        return this.hashCode;
    }

    @Override
    public int intValue() {
        if (this.nativeIsValid && this.bigInt.twosCompFitsIntoBytes(4)) {
            return (int)this.bigInt.longInt();
        }
        this.prepareJavaRepresentation();
        return this.sign * this.digits[0];
    }

    public boolean isProbablePrime(int n) {
        if (n <= 0) {
            return true;
        }
        return this.getBigInt().isPrime(n);
    }

    @Override
    public long longValue() {
        long l;
        if (this.nativeIsValid && this.bigInt.twosCompFitsIntoBytes(8)) {
            return this.bigInt.longInt();
        }
        this.prepareJavaRepresentation();
        if (this.numberLength > 1) {
            int[] arrn = this.digits;
            l = arrn[1];
            l = (long)arrn[0] & 0xFFFFFFFFL | l << 32;
        } else {
            l = (long)this.digits[0] & 0xFFFFFFFFL;
        }
        return (long)this.sign * l;
    }

    public BigInteger max(BigInteger bigInteger) {
        block0 : {
            if (this.compareTo(bigInteger) != 1) break block0;
            bigInteger = this;
        }
        return bigInteger;
    }

    public BigInteger min(BigInteger bigInteger) {
        block0 : {
            if (this.compareTo(bigInteger) != -1) break block0;
            bigInteger = this;
        }
        return bigInteger;
    }

    public BigInteger mod(BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return new BigInteger(BigInt.modulus(this.getBigInt(), bigInteger.getBigInt()));
        }
        throw new ArithmeticException("m.signum() <= 0");
    }

    public BigInteger modInverse(BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return new BigInteger(BigInt.modInverse(this.getBigInt(), bigInteger.getBigInt()));
        }
        throw new ArithmeticException("modulus not positive");
    }

    public BigInteger modPow(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() > 0) {
            int n = bigInteger.signum();
            if (n == 0) {
                return ONE.mod(bigInteger2);
            }
            BigInteger bigInteger3 = n < 0 ? this.modInverse(bigInteger2) : this;
            return new BigInteger(BigInt.modExp(bigInteger3.getBigInt(), bigInteger.getBigInt(), bigInteger2.getBigInt()));
        }
        throw new ArithmeticException("modulus.signum() <= 0");
    }

    public BigInteger multiply(BigInteger bigInteger) {
        return new BigInteger(BigInt.product(this.getBigInt(), bigInteger.getBigInt()));
    }

    public BigInteger negate() {
        BigInt bigInt = this.getBigInt();
        int n = bigInt.sign();
        if (n == 0) {
            return this;
        }
        bigInt = bigInt.copy();
        bigInt.setSign(-n);
        return new BigInteger(bigInt);
    }

    public BigInteger nextProbablePrime() {
        if (this.sign >= 0) {
            return Primality.nextProbablePrime(this);
        }
        throw new ArithmeticException("sign < 0");
    }

    public BigInteger not() {
        this.prepareJavaRepresentation();
        return Logical.not(this);
    }

    public BigInteger or(BigInteger bigInteger) {
        this.prepareJavaRepresentation();
        bigInteger.prepareJavaRepresentation();
        return Logical.or(this, bigInteger);
    }

    public BigInteger pow(int n) {
        if (n >= 0) {
            return new BigInteger(BigInt.exp(this.getBigInt(), n));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exp < 0: ");
        stringBuilder.append(n);
        throw new ArithmeticException(stringBuilder.toString());
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void prepareJavaRepresentation() {
        if (this.javaIsValid) {
            return;
        }
        synchronized (this) {
            if (this.javaIsValid) {
                return;
            }
            int n = this.bigInt.sign();
            int[] arrn = n != 0 ? this.bigInt.littleEndianIntsMagnitude() : new int[]{0};
            this.setJavaRepresentation(n, arrn.length, arrn);
            return;
        }
    }

    public BigInteger remainder(BigInteger bigInteger) {
        BigInt bigInt = new BigInt();
        BigInt.division(this.getBigInt(), bigInteger.getBigInt(), null, bigInt);
        return new BigInteger(bigInt);
    }

    public BigInteger setBit(int n) {
        this.prepareJavaRepresentation();
        if (!this.testBit(n)) {
            return BitLevel.flipBit(this, n);
        }
        return this;
    }

    public BigInteger shiftLeft(int n) {
        if (n == 0) {
            return this;
        }
        int n2 = this.signum();
        if (n2 == 0) {
            return this;
        }
        if (n2 <= 0 && n < 0) {
            return BitLevel.shiftRight(this, -n);
        }
        return new BigInteger(BigInt.shift(this.getBigInt(), n));
    }

    BigInteger shiftLeftOneBit() {
        BigInteger bigInteger = this.signum() == 0 ? this : BitLevel.shiftLeftOneBit(this);
        return bigInteger;
    }

    public BigInteger shiftRight(int n) {
        return this.shiftLeft(-n);
    }

    public int signum() {
        if (this.javaIsValid) {
            return this.sign;
        }
        return this.getBigInt().sign();
    }

    public BigInteger subtract(BigInteger object) {
        BigInt bigInt = this.getBigInt();
        if (((BigInt)(object = ((BigInteger)object).getBigInt())).sign() == 0) {
            return this;
        }
        return new BigInteger(BigInt.subtraction(bigInt, (BigInt)object));
    }

    public boolean testBit(int n) {
        if (n >= 0) {
            int n2;
            int n3 = this.signum();
            if (n3 > 0 && this.nativeIsValid && !this.javaIsValid) {
                return this.getBigInt().isBitSet(n);
            }
            this.prepareJavaRepresentation();
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            if (n == 0) {
                if ((this.digits[0] & 1) == 0) {
                    bl3 = false;
                }
                return bl3;
            }
            int n4 = n >> 5;
            if (n4 >= this.numberLength) {
                bl3 = n3 < 0 ? bl : false;
                return bl3;
            }
            int n5 = n2 = this.digits[n4];
            if (n3 < 0) {
                n5 = this.getFirstNonzeroDigit();
                if (n4 < n5) {
                    return false;
                }
                n5 = n5 == n4 ? -n2 : n2;
            }
            bl3 = (n5 & 1 << (n & 31)) != 0 ? bl2 : false;
            return bl3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("n < 0: ");
        stringBuilder.append(n);
        throw new ArithmeticException(stringBuilder.toString());
    }

    public byte[] toByteArray() {
        return this.twosComplement();
    }

    public String toString() {
        return this.getBigInt().decString();
    }

    public String toString(int n) {
        if (n == 10) {
            return this.getBigInt().decString();
        }
        this.prepareJavaRepresentation();
        return Conversion.bigInteger2String(this, n);
    }

    public BigInteger xor(BigInteger bigInteger) {
        this.prepareJavaRepresentation();
        bigInteger.prepareJavaRepresentation();
        return Logical.xor(this, bigInteger);
    }
}

