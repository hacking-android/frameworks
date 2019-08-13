/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public final class Half
extends Number
implements Comparable<Half> {
    public static final short EPSILON = 5120;
    private static final int FP16_COMBINED = 32767;
    private static final int FP16_EXPONENT_BIAS = 15;
    private static final int FP16_EXPONENT_MASK = 31;
    private static final int FP16_EXPONENT_MAX = 31744;
    private static final int FP16_EXPONENT_SHIFT = 10;
    private static final int FP16_SIGNIFICAND_MASK = 1023;
    private static final int FP16_SIGN_MASK = 32768;
    private static final int FP16_SIGN_SHIFT = 15;
    private static final float FP32_DENORMAL_FLOAT = Float.intBitsToFloat(1056964608);
    private static final int FP32_DENORMAL_MAGIC = 1056964608;
    private static final int FP32_EXPONENT_BIAS = 127;
    private static final int FP32_EXPONENT_MASK = 255;
    private static final int FP32_EXPONENT_SHIFT = 23;
    private static final int FP32_QNAN_MASK = 4194304;
    private static final int FP32_SIGNIFICAND_MASK = 8388607;
    private static final int FP32_SIGN_SHIFT = 31;
    public static final short LOWEST_VALUE = -1025;
    public static final int MAX_EXPONENT = 15;
    public static final short MAX_VALUE = 31743;
    public static final int MIN_EXPONENT = -14;
    public static final short MIN_NORMAL = 1024;
    public static final short MIN_VALUE = 1;
    public static final short NEGATIVE_INFINITY = -1024;
    public static final short NEGATIVE_ZERO = -32768;
    public static final short NaN = 32256;
    public static final short POSITIVE_INFINITY = 31744;
    public static final short POSITIVE_ZERO = 0;
    public static final int SIZE = 16;
    private final short mValue;

    public Half(double d) {
        this.mValue = Half.toHalf((float)d);
    }

    public Half(float f) {
        this.mValue = Half.toHalf(f);
    }

    public Half(String string2) throws NumberFormatException {
        this.mValue = Half.toHalf(Float.parseFloat(string2));
    }

    public Half(short s) {
        this.mValue = s;
    }

    public static short abs(short s) {
        return (short)(s & 32767);
    }

    public static short ceil(short s) {
        int n = 65535 & s;
        int n2 = n & 32767;
        int n3 = n;
        s = 1;
        if (n2 < 15360) {
            if (n2 == 0) {
                s = 0;
            }
            s = (short)(n3 & 32768 | 15360 & -(s & n >> 15));
        } else {
            s = (short)n3;
            if (n2 < 25600) {
                s = (short)((1 << 25 - (n2 >> 10)) - 1);
                s = (short)(n3 + (s & (n >> 15) - 1) & s);
            }
        }
        return s;
    }

    public static int compare(short s, short s2) {
        boolean bl = Half.less(s, s2);
        int n = -1;
        if (bl) {
            return -1;
        }
        if (Half.greater(s, s2)) {
            return 1;
        }
        int n2 = 32256;
        int n3 = (s & 32767) > 31744 ? 32256 : (int)s;
        if ((s2 & 32767) <= 31744) {
            n2 = s2;
        }
        n3 = n3 == n2 ? 0 : (n3 < n2 ? n : 1);
        return n3;
    }

    public static short copySign(short s, short s2) {
        return (short)(32768 & s2 | s & 32767);
    }

    public static boolean equals(short s, short s2) {
        boolean bl = false;
        if ((s & 32767) > 31744) {
            return false;
        }
        if ((s2 & 32767) > 31744) {
            return false;
        }
        if (s == s2 || ((s | s2) & 32767) == 0) {
            bl = true;
        }
        return bl;
    }

    public static short floor(short s) {
        int n = 65535;
        int n2 = s & 65535;
        int n3 = n2 & 32767;
        int n4 = n2;
        if (n3 < 15360) {
            s = n2 > 32768 ? (short)n : (short)0;
            s = (short)(n4 & 32768 | s & 15360);
        } else {
            s = (short)n4;
            if (n3 < 25600) {
                s = (short)((1 << 25 - (n3 >> 10)) - 1);
                s = (short)(n4 + (-(n2 >> 15) & s) & s);
            }
        }
        return s;
    }

    public static int getExponent(short s) {
        return (s >>> 10 & 31) - 15;
    }

    public static int getSign(short s) {
        s = (32768 & s) == 0 ? (short)1 : (short)-1;
        return s;
    }

    public static int getSignificand(short s) {
        return s & 1023;
    }

    public static boolean greater(short s, short s2) {
        boolean bl = false;
        if ((s & 32767) > 31744) {
            return false;
        }
        if ((s2 & 32767) > 31744) {
            return false;
        }
        if ((s = (s & 32768) != 0 ? (short)(32768 - (s & 65535)) : (short)(s & 65535)) > (s2 = (s2 & 32768) != 0 ? (short)(32768 - (65535 & s2)) : (short)(s2 & 65535))) {
            bl = true;
        }
        return bl;
    }

    public static boolean greaterEquals(short s, short s2) {
        boolean bl = false;
        if ((s & 32767) > 31744) {
            return false;
        }
        if ((s2 & 32767) > 31744) {
            return false;
        }
        if ((s = (s & 32768) != 0 ? (short)(32768 - (s & 65535)) : (short)(s & 65535)) >= (s2 = (s2 & 32768) != 0 ? (short)(32768 - (65535 & s2)) : (short)(s2 & 65535))) {
            bl = true;
        }
        return bl;
    }

    public static int halfToIntBits(short s) {
        s = (s & 32767) > 31744 ? (short)32256 : (short)(65535 & s);
        return s;
    }

    public static int halfToRawIntBits(short s) {
        return 65535 & s;
    }

    public static short halfToShortBits(short s) {
        short s2 = (s & 32767) > 31744 ? (s = (short)32256) : s;
        return s2;
    }

    public static int hashCode(short s) {
        return Half.halfToIntBits(s);
    }

    public static short intBitsToHalf(int n) {
        return (short)(65535 & n);
    }

    public static boolean isInfinite(short s) {
        boolean bl = (s & 32767) == 31744;
        return bl;
    }

    public static boolean isNaN(short s) {
        boolean bl = (s & 32767) > 31744;
        return bl;
    }

    public static boolean isNormalized(short s) {
        boolean bl = (s & 31744) != 0 && (s & 31744) != 31744;
        return bl;
    }

    public static boolean less(short s, short s2) {
        boolean bl = false;
        if ((s & 32767) > 31744) {
            return false;
        }
        if ((s2 & 32767) > 31744) {
            return false;
        }
        if ((s = (s & 32768) != 0 ? (short)(32768 - (s & 65535)) : (short)(s & 65535)) < (s2 = (s2 & 32768) != 0 ? (short)(32768 - (65535 & s2)) : (short)(s2 & 65535))) {
            bl = true;
        }
        return bl;
    }

    public static boolean lessEquals(short s, short s2) {
        boolean bl = false;
        if ((s & 32767) > 31744) {
            return false;
        }
        if ((s2 & 32767) > 31744) {
            return false;
        }
        if ((s = (s & 32768) != 0 ? (short)(32768 - (s & 65535)) : (short)(s & 65535)) <= (s2 = (s2 & 32768) != 0 ? (short)(32768 - (65535 & s2)) : (short)(s2 & 65535))) {
            bl = true;
        }
        return bl;
    }

    public static short max(short s, short s2) {
        if ((s & 32767) > 31744) {
            return 32256;
        }
        if ((s2 & 32767) > 31744) {
            return 32256;
        }
        if ((s & 32767) == 0 && (s2 & 32767) == 0) {
            short s3 = (s & 32768) != 0 ? s2 : s;
            return s3;
        }
        int n = (s & 32768) != 0 ? 32768 - (s & 65535) : s & 65535;
        int n2 = (s2 & 32768) != 0 ? 32768 - (65535 & s2) : s2 & 65535;
        short s4 = n > n2 ? s : s2;
        return s4;
    }

    public static short min(short s, short s2) {
        if ((s & 32767) > 31744) {
            return 32256;
        }
        if ((s2 & 32767) > 31744) {
            return 32256;
        }
        if ((s & 32767) == 0 && (s2 & 32767) == 0) {
            short s3 = (s & 32768) != 0 ? s : s2;
            return s3;
        }
        int n = (s & 32768) != 0 ? 32768 - (s & 65535) : s & 65535;
        int n2 = (s2 & 32768) != 0 ? 32768 - (65535 & s2) : s2 & 65535;
        short s4 = n < n2 ? s : s2;
        return s4;
    }

    public static short parseHalf(String string2) throws NumberFormatException {
        return Half.toHalf(Float.parseFloat(string2));
    }

    public static short round(short s) {
        int n = 65535;
        int n2 = s & 65535;
        int n3 = n2 & 32767;
        if (n3 < 15360) {
            s = n3 >= 14336 ? (short)n : (short)0;
            s = (short)(n2 & 32768 | s & 15360);
        } else {
            s = (short)n2;
            if (n3 < 25600) {
                s = (short)(25 - (n3 >> 10));
                s = (short)(n2 + (1 << s - 1) & (1 << s) - 1);
            }
        }
        return s;
    }

    public static float toFloat(short s) {
        s = (short)(65535 & s);
        int n = 32768 & s;
        int n2 = s >>> 10 & 31;
        int n3 = s & 1023;
        s = 0;
        int n4 = 0;
        if (n2 == 0) {
            if (n3 != 0) {
                float f = Float.intBitsToFloat(1056964608 + n3) - FP32_DENORMAL_FLOAT;
                if (n != 0) {
                    f = -f;
                }
                return f;
            }
        } else {
            n3 <<= 13;
            if (n2 == 31) {
                n2 = 255;
                s = (short)n2;
                n4 = n3;
                if (n3 != 0) {
                    n4 = n3 | 4194304;
                    s = (short)n2;
                }
            } else {
                s = (short)(n2 - 15 + 127);
                n4 = n3;
            }
        }
        return Float.intBitsToFloat(n << 16 | s << 23 | n4);
    }

    public static short toHalf(float f) {
        int n = Float.floatToRawIntBits(f);
        int n2 = n >>> 31;
        int n3 = n >>> 23 & 255;
        int n4 = 8388607 & n;
        n = 0;
        int n5 = 0;
        if (n3 == 255) {
            n5 = 31;
            n3 = n4 != 0 ? 512 : 0;
            n = n3;
            n3 = n5;
        } else if ((n3 = n3 - 127 + 15) >= 31) {
            n3 = 49;
            n = n5;
        } else if (n3 <= 0) {
            if (n3 < -10) {
                n3 = n;
                n = n5;
            } else {
                n3 = n5 = (8388608 | n4) >> 1 - n3;
                if ((n5 & 4096) != 0) {
                    n3 = n5 + 8192;
                }
                n5 = n3 >> 13;
                n3 = n;
                n = n5;
            }
        } else {
            n5 = n3;
            int n6 = n4 >> 13;
            n3 = n5;
            n = n6;
            if ((n4 & 4096) != 0) {
                return (short)(n2 << 15 | (n5 << 10 | n6) + 1);
            }
        }
        return (short)(n2 << 15 | n3 << 10 | n);
    }

    public static String toHexString(short s) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 65535 & s;
        int n2 = n >>> 15;
        s = (short)(n >>> 10 & 31);
        n &= 1023;
        if (s == 31) {
            if (n == 0) {
                if (n2 != 0) {
                    stringBuilder.append('-');
                }
                stringBuilder.append("Infinity");
            } else {
                stringBuilder.append("NaN");
            }
        } else {
            if (n2 == 1) {
                stringBuilder.append('-');
            }
            if (s == 0) {
                if (n == 0) {
                    stringBuilder.append("0x0.0p0");
                } else {
                    stringBuilder.append("0x0.");
                    stringBuilder.append(Integer.toHexString(n).replaceFirst("0{2,}$", ""));
                    stringBuilder.append("p-14");
                }
            } else {
                stringBuilder.append("0x1.");
                stringBuilder.append(Integer.toHexString(n).replaceFirst("0{2,}$", ""));
                stringBuilder.append('p');
                stringBuilder.append(Integer.toString(s - 15));
            }
        }
        return stringBuilder.toString();
    }

    public static String toString(short s) {
        return Float.toString(Half.toFloat(s));
    }

    public static short trunc(short s) {
        int n = 65535 & s;
        int n2 = n & 32767;
        if (n2 < 15360) {
            s = (short)(n & 32768);
        } else {
            s = (short)n;
            if (n2 < 25600) {
                s = (short)(n & (1 << 25 - (n2 >> 10)) - 1);
            }
        }
        return s;
    }

    public static Half valueOf(float f) {
        return new Half(f);
    }

    public static Half valueOf(String string2) {
        return new Half(string2);
    }

    public static Half valueOf(short s) {
        return new Half(s);
    }

    @Override
    public byte byteValue() {
        return (byte)Half.toFloat(this.mValue);
    }

    @Override
    public int compareTo(Half half) {
        return Half.compare(this.mValue, half.mValue);
    }

    @Override
    public double doubleValue() {
        return Half.toFloat(this.mValue);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Half && Half.halfToIntBits(((Half)object).mValue) == Half.halfToIntBits(this.mValue);
        return bl;
    }

    @Override
    public float floatValue() {
        return Half.toFloat(this.mValue);
    }

    public short halfValue() {
        return this.mValue;
    }

    public int hashCode() {
        return Half.hashCode(this.mValue);
    }

    @Override
    public int intValue() {
        return (int)Half.toFloat(this.mValue);
    }

    public boolean isNaN() {
        return Half.isNaN(this.mValue);
    }

    @Override
    public long longValue() {
        return (long)Half.toFloat(this.mValue);
    }

    @Override
    public short shortValue() {
        return (short)Half.toFloat(this.mValue);
    }

    public String toString() {
        return Half.toString(this.mValue);
    }
}

