/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.FDBigInteger;

public class FloatingDecimal {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final ASCIIToBinaryConverter A2BC_NEGATIVE_INFINITY;
    static final ASCIIToBinaryConverter A2BC_NEGATIVE_ZERO;
    static final ASCIIToBinaryConverter A2BC_NOT_A_NUMBER;
    static final ASCIIToBinaryConverter A2BC_POSITIVE_INFINITY;
    static final ASCIIToBinaryConverter A2BC_POSITIVE_ZERO;
    private static final BinaryToASCIIConverter B2AC_NEGATIVE_INFINITY;
    private static final BinaryToASCIIConverter B2AC_NEGATIVE_ZERO;
    private static final BinaryToASCIIConverter B2AC_NOT_A_NUMBER;
    private static final BinaryToASCIIConverter B2AC_POSITIVE_INFINITY;
    private static final BinaryToASCIIConverter B2AC_POSITIVE_ZERO;
    static final int BIG_DECIMAL_EXPONENT = 324;
    static final long EXP_ONE = 4607182418800017408L;
    static final int EXP_SHIFT = 52;
    static final long FRACT_HOB = 0x10000000000000L;
    private static final int INFINITY_LENGTH;
    private static final String INFINITY_REP = "Infinity";
    static final int INT_DECIMAL_DIGITS = 9;
    static final int MAX_DECIMAL_DIGITS = 15;
    static final int MAX_DECIMAL_EXPONENT = 308;
    static final int MAX_NDIGITS = 1100;
    static final int MAX_SMALL_BIN_EXP = 62;
    static final int MIN_DECIMAL_EXPONENT = -324;
    static final int MIN_SMALL_BIN_EXP = -21;
    private static final int NAN_LENGTH;
    private static final String NAN_REP = "NaN";
    static final int SINGLE_EXP_SHIFT = 23;
    static final int SINGLE_FRACT_HOB = 8388608;
    static final int SINGLE_MAX_DECIMAL_DIGITS = 7;
    static final int SINGLE_MAX_DECIMAL_EXPONENT = 38;
    static final int SINGLE_MAX_NDIGITS = 200;
    static final int SINGLE_MIN_DECIMAL_EXPONENT = -45;
    private static final ThreadLocal<BinaryToASCIIBuffer> threadLocalBinaryToASCIIBuffer;

    static {
        INFINITY_LENGTH = INFINITY_REP.length();
        NAN_LENGTH = NAN_REP.length();
        B2AC_POSITIVE_INFINITY = new ExceptionalBinaryToASCIIBuffer(INFINITY_REP, false);
        B2AC_NEGATIVE_INFINITY = new ExceptionalBinaryToASCIIBuffer("-Infinity", true);
        B2AC_NOT_A_NUMBER = new ExceptionalBinaryToASCIIBuffer(NAN_REP, false);
        B2AC_POSITIVE_ZERO = new BinaryToASCIIBuffer(false, new char[]{'0'});
        B2AC_NEGATIVE_ZERO = new BinaryToASCIIBuffer(true, new char[]{'0'});
        threadLocalBinaryToASCIIBuffer = new ThreadLocal<BinaryToASCIIBuffer>(){

            @Override
            protected BinaryToASCIIBuffer initialValue() {
                return new BinaryToASCIIBuffer();
            }
        };
        A2BC_POSITIVE_INFINITY = new PreparedASCIIToBinaryBuffer(Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        A2BC_NEGATIVE_INFINITY = new PreparedASCIIToBinaryBuffer(Double.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        A2BC_NOT_A_NUMBER = new PreparedASCIIToBinaryBuffer(Double.NaN, Float.NaN);
        A2BC_POSITIVE_ZERO = new PreparedASCIIToBinaryBuffer(0.0, 0.0f);
        A2BC_NEGATIVE_ZERO = new PreparedASCIIToBinaryBuffer(0.0, 0.0f);
    }

    public static void appendTo(double d, Appendable appendable) {
        FloatingDecimal.getBinaryToASCIIConverter(d).appendTo(appendable);
    }

    public static void appendTo(float f, Appendable appendable) {
        FloatingDecimal.getBinaryToASCIIConverter(f).appendTo(appendable);
    }

    private static BinaryToASCIIBuffer getBinaryToASCIIBuffer() {
        return threadLocalBinaryToASCIIBuffer.get();
    }

    public static BinaryToASCIIConverter getBinaryToASCIIConverter(double d) {
        return FloatingDecimal.getBinaryToASCIIConverter(d, true);
    }

    static BinaryToASCIIConverter getBinaryToASCIIConverter(double d, boolean bl) {
        int n;
        long l = Double.doubleToRawLongBits(d);
        boolean bl2 = (Long.MIN_VALUE & l) != 0L;
        long l2 = 0xFFFFFFFFFFFFFL & l;
        int n2 = (int)((9218868437227405312L & l) >> 52);
        if (n2 == 2047) {
            if (l2 == 0L) {
                BinaryToASCIIConverter binaryToASCIIConverter = bl2 ? B2AC_NEGATIVE_INFINITY : B2AC_POSITIVE_INFINITY;
                return binaryToASCIIConverter;
            }
            return B2AC_NOT_A_NUMBER;
        }
        if (n2 == 0) {
            if (l2 == 0L) {
                BinaryToASCIIConverter binaryToASCIIConverter = bl2 ? B2AC_NEGATIVE_ZERO : B2AC_POSITIVE_ZERO;
                return binaryToASCIIConverter;
            }
            n = Long.numberOfLeadingZeros(l2);
            n2 = n - 11;
            l = l2 << n2;
            n2 = 1 - n2;
            n = 64 - n;
        } else {
            l = l2 | 0x10000000000000L;
            n = 53;
        }
        BinaryToASCIIBuffer binaryToASCIIBuffer = FloatingDecimal.getBinaryToASCIIBuffer();
        binaryToASCIIBuffer.setSign(bl2);
        binaryToASCIIBuffer.dtoa(n2 - 1023, l, n, bl);
        return binaryToASCIIBuffer;
    }

    private static BinaryToASCIIConverter getBinaryToASCIIConverter(float f) {
        int n = Float.floatToRawIntBits(f);
        boolean bl = (Integer.MIN_VALUE & n) != 0;
        int n2 = 8388607 & n;
        int n3 = (2139095040 & n) >> 23;
        if (n3 == 255) {
            if ((long)n2 == 0L) {
                BinaryToASCIIConverter binaryToASCIIConverter = bl ? B2AC_NEGATIVE_INFINITY : B2AC_POSITIVE_INFINITY;
                return binaryToASCIIConverter;
            }
            return B2AC_NOT_A_NUMBER;
        }
        if (n3 == 0) {
            if (n2 == 0) {
                BinaryToASCIIConverter binaryToASCIIConverter = bl ? B2AC_NEGATIVE_ZERO : B2AC_POSITIVE_ZERO;
                return binaryToASCIIConverter;
            }
            n = Integer.numberOfLeadingZeros(n2);
            n3 = n - 8;
            n2 <<= n3;
            n3 = 1 - n3;
            n = 32 - n;
        } else {
            n2 |= 8388608;
            n = 24;
        }
        BinaryToASCIIBuffer binaryToASCIIBuffer = FloatingDecimal.getBinaryToASCIIBuffer();
        binaryToASCIIBuffer.setSign(bl);
        binaryToASCIIBuffer.dtoa(n3 - 127, (long)n2 << 29, n, true);
        return binaryToASCIIBuffer;
    }

    static int getHexDigit(String string, int n) {
        int n2 = Character.digit(string.charAt(n), 16);
        if (n2 > -1 && n2 < 16) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected failure of digit conversion of ");
        stringBuilder.append(string.charAt(n));
        throw new AssertionError((Object)stringBuilder.toString());
    }

    public static double parseDouble(String string) throws NumberFormatException {
        return FloatingDecimal.readJavaFormatString(string).doubleValue();
    }

    public static float parseFloat(String string) throws NumberFormatException {
        return FloatingDecimal.readJavaFormatString(string).floatValue();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static ASCIIToBinaryConverter parseHexString(String object) {
        Object object2;
        block41 : {
            boolean bl;
            float f;
            long l;
            block50 : {
                long l2;
                block49 : {
                    int n;
                    int n2;
                    long l3;
                    int n3;
                    block44 : {
                        int n4;
                        block48 : {
                            int n5;
                            block47 : {
                                int n6;
                                block42 : {
                                    block46 : {
                                        block45 : {
                                            block43 : {
                                                CharSequence charSequence;
                                                object2 = HexFloatPattern.VALUE.matcher((CharSequence)object);
                                                if (!((Matcher)object2).matches()) break block41;
                                                object = ((Matcher)object2).group(1);
                                                bl = object != null && ((String)object).equals("-");
                                                n3 = 0;
                                                object = ((Matcher)object2).group(4);
                                                if (object != null) {
                                                    object = FloatingDecimal.stripLeadingZeros((String)object);
                                                    n2 = ((String)object).length();
                                                } else {
                                                    String string = FloatingDecimal.stripLeadingZeros(((Matcher)object2).group(6));
                                                    n2 = string.length();
                                                    object = ((Matcher)object2).group(7);
                                                    n3 = ((String)object).length();
                                                    charSequence = new StringBuilder();
                                                    ((StringBuilder)charSequence).append(string);
                                                    ((StringBuilder)charSequence).append((String)object);
                                                    object = ((StringBuilder)charSequence).toString();
                                                }
                                                charSequence = FloatingDecimal.stripLeadingZeros((String)object);
                                                n5 = ((String)charSequence).length();
                                                n3 = n2 >= 1 ? (n2 - 1) * 4 : (n3 - n5 + 1) * -4;
                                                if (n5 == 0) {
                                                    if (!bl) return A2BC_POSITIVE_ZERO;
                                                    return A2BC_NEGATIVE_ZERO;
                                                }
                                                object = ((Matcher)object2).group(8);
                                                n2 = object != null && !((String)object).equals("+") ? 0 : 1;
                                                try {
                                                    n = Integer.parseInt(((Matcher)object2).group(9));
                                                }
                                                catch (NumberFormatException numberFormatException) {
                                                    if (bl) {
                                                        if (n2 == 0) return A2BC_NEGATIVE_ZERO;
                                                        return A2BC_NEGATIVE_INFINITY;
                                                    }
                                                    if (n2 == 0) return A2BC_POSITIVE_ZERO;
                                                    return A2BC_POSITIVE_INFINITY;
                                                }
                                                l = n;
                                                l2 = n2 != 0 ? 1L : -1L;
                                                l = l2 * l + (long)n3;
                                                n6 = 0;
                                                n4 = 0;
                                                n2 = 0;
                                                l2 = FloatingDecimal.getHexDigit((String)charSequence, 0);
                                                if (l2 == 1L) {
                                                    l2 = 0L | l2 << 52;
                                                    n3 = 48;
                                                } else if (l2 <= 3L) {
                                                    l2 = 0L | l2 << 51;
                                                    n3 = 47;
                                                    ++l;
                                                } else if (l2 <= 7L) {
                                                    l2 = 0L | l2 << 50;
                                                    n3 = 46;
                                                    l += 2L;
                                                } else {
                                                    if (l2 > 15L) throw new AssertionError((Object)"Result from digit conversion too large!");
                                                    l2 = 0L | l2 << 49;
                                                    n3 = 45;
                                                    l += 3L;
                                                }
                                                object = object2;
                                                for (n = 1; n < n5 && n3 >= 0; n3 -= 4, ++n) {
                                                    l2 |= (long)FloatingDecimal.getHexDigit((String)charSequence, n) << n3;
                                                }
                                                if (n < n5) {
                                                    l3 = FloatingDecimal.getHexDigit((String)charSequence, n);
                                                    if (n3 != -4) {
                                                        if (n3 != -3) {
                                                            if (n3 != -2) {
                                                                if (n3 != -1) throw new AssertionError((Object)"Unexpected shift distance remainder.");
                                                                n3 = (l3 & 1L) != 0L ? 1 : 0;
                                                                l2 |= (l3 & 14L) >> 1;
                                                            } else {
                                                                n3 = (l3 & 2L) != 0L ? 1 : 0;
                                                                n2 = (l3 & 1L) != 0L ? 1 : 0;
                                                                l2 |= (l3 & 12L) >> 2;
                                                            }
                                                        } else {
                                                            n3 = (l3 & 4L) != 0L ? 1 : 0;
                                                            n2 = (l3 & 3L) != 0L ? 1 : 0;
                                                            l2 |= (l3 & 8L) >> 3;
                                                        }
                                                    } else {
                                                        n3 = (l3 & 8L) != 0L ? 1 : 0;
                                                        n2 = (l3 & 7L) != 0L ? 1 : 0;
                                                    }
                                                    n4 = n + 1;
                                                    n6 = n2;
                                                    do {
                                                        n = n3;
                                                        n2 = n6;
                                                        l3 = l2;
                                                        if (n4 < n5) {
                                                            n = n3;
                                                            n2 = n6;
                                                            l3 = l2;
                                                            if (n6 == 0) {
                                                                l3 = FloatingDecimal.getHexDigit((String)charSequence, n4);
                                                                n2 = n6 == 0 && l3 == 0L ? 0 : 1;
                                                                ++n4;
                                                                n6 = n2;
                                                                continue;
                                                            }
                                                        }
                                                        break;
                                                    } while (true);
                                                } else {
                                                    l3 = l2;
                                                    n2 = n4;
                                                    n = n6;
                                                }
                                                n3 = bl ? Integer.MIN_VALUE : 0;
                                                if (l < -126L) break block42;
                                                if (l <= 127L) break block43;
                                                n3 |= 2139095040;
                                                break block44;
                                            }
                                            n4 = (l3 & (1L << 28) - 1L) == 0L && n == 0 && n2 == 0 ? 0 : 1;
                                            n5 = (int)(l3 >>> 28);
                                            if ((n5 & 3) != 1) break block45;
                                            n6 = n5;
                                            if (n4 == 0) break block46;
                                        }
                                        n6 = n5 + 1;
                                    }
                                    n3 |= ((int)l + 126 << 23) + (n6 >> 1);
                                    break block44;
                                }
                                if (l < -150L) break block44;
                                n4 = (int)(-98L - l);
                                n6 = (l3 & (1L << n4) - 1L) == 0L && n == 0 && n2 == 0 ? 0 : 1;
                                n5 = (int)(l3 >>> n4);
                                if ((n5 & 3) != 1) break block47;
                                n4 = n5;
                                if (n6 == 0) break block48;
                            }
                            n4 = n5 + 1;
                        }
                        n3 |= n4 >> 1;
                    }
                    f = Float.intBitsToFloat(n3);
                    if (l > 1023L) {
                        if (!bl) return A2BC_POSITIVE_INFINITY;
                        return A2BC_NEGATIVE_INFINITY;
                    }
                    if (l <= 1023L && l >= -1022L) {
                        l2 = 1023L + l << 52 & 9218868437227405312L | l3 & 0xFFFFFFFFFFFFFL;
                        n3 = n;
                    } else {
                        if (l < -1075L) {
                            if (!bl) return A2BC_POSITIVE_ZERO;
                            return A2BC_NEGATIVE_ZERO;
                        }
                        n2 = n2 == 0 && n == 0 ? 0 : 1;
                        n = 53 - ((int)l + 1074 + 1);
                        n3 = (l3 & 1L << n - 1) != 0L ? 1 : 0;
                        if (n > 1) {
                            n2 = n2 == 0 && (l3 & -1L << n - 1) == 0L ? 0 : 1;
                        }
                        l2 = l3 >> n & 0xFFFFFFFFFFFFFL | 0L;
                    }
                    n = (l2 & 1L) == 0L ? 1 : 0;
                    if (n != 0 && n3 != 0 && n2 != 0) break block49;
                    l = l2;
                    if (n != 0) break block50;
                    l = l2;
                    if (n3 == 0) break block50;
                }
                l = l2 + 1L;
            }
            double d = bl ? Double.longBitsToDouble(l | Long.MIN_VALUE) : Double.longBitsToDouble(l);
            return new PreparedASCIIToBinaryBuffer(d, f);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("For input string: \"");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("\"");
        throw new NumberFormatException(((StringBuilder)object2).toString());
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static ASCIIToBinaryConverter readJavaFormatString(String object) throws NumberFormatException {
        Object object2;
        block50 : {
            block47 : {
                int n2;
                boolean bl;
                int n;
                int n3;
                block64 : {
                    int n4;
                    int n5;
                    block55 : {
                        int n8;
                        int n6;
                        int n9;
                        int n7;
                        int n11;
                        int n10;
                        block56 : {
                            int n12;
                            int n13;
                            block62 : {
                                block63 : {
                                    block60 : {
                                        block61 : {
                                            block53 : {
                                                block52 : {
                                                    block49 : {
                                                        block48 : {
                                                            boolean bl2 = false;
                                                            bl = false;
                                                            n6 = 0;
                                                            object2 = ((String)object).trim();
                                                            object = object2;
                                                            n5 = ((String)object).length();
                                                            if (n5 == 0) break block47;
                                                            n2 = 0;
                                                            n4 = ((String)object).charAt(0);
                                                            if (n4 != 43) {
                                                                if (n4 != 45) {
                                                                    bl = bl2;
                                                                    break block48;
                                                                }
                                                                bl = true;
                                                            }
                                                            n2 = 0 + 1;
                                                            n6 = 1;
                                                        }
                                                        n4 = ((String)object).charAt(n2);
                                                        if (n4 != 78) break block49;
                                                        if (n5 - n2 == NAN_LENGTH && ((String)object).indexOf(NAN_REP, n2) == n2) {
                                                            return A2BC_NOT_A_NUMBER;
                                                        }
                                                        break block50;
                                                    }
                                                    if (n4 == 73) {
                                                        block51 : {
                                                            if (n5 - n2 != INFINITY_LENGTH || ((String)object).indexOf(INFINITY_REP, n2) != n2) break block50;
                                                            if (!bl) break block51;
                                                            object2 = A2BC_NEGATIVE_INFINITY;
                                                            return object2;
                                                        }
                                                        object2 = A2BC_POSITIVE_INFINITY;
                                                        return object2;
                                                    }
                                                    if (n4 == 48 && n5 > n2 + 1) {
                                                        n4 = ((String)object).charAt(n2 + 1);
                                                        if (n4 != 120 && n4 != 88) break block52;
                                                        return FloatingDecimal.parseHexString((String)object);
                                                    }
                                                }
                                                object2 = new char[n5];
                                                n12 = 0;
                                                n11 = 0;
                                                n7 = 0;
                                                n9 = 0;
                                                n3 = 0;
                                                do {
                                                    n4 = ++n2;
                                                    n = n12;
                                                    n8 = n11;
                                                    n13 = n7;
                                                    n10 = n3;
                                                    if (n2 >= n5) break block53;
                                                    char c = ((String)object).charAt(n2);
                                                    if (c == '0') {
                                                        ++n9;
                                                        continue;
                                                    }
                                                    n4 = n2;
                                                    n = n12;
                                                    n8 = n11;
                                                    n13 = n7;
                                                    n10 = n3;
                                                    if (c != '.') break block53;
                                                    if (n11 != 0) break;
                                                    n4 = n7 = n2;
                                                    if (n6 != 0) {
                                                        n4 = n7 - 1;
                                                    }
                                                    n11 = 1;
                                                    n7 = n4;
                                                    continue;
                                                    break;
                                                } while (true);
                                                object2 = new NumberFormatException("multiple points");
                                                throw object2;
                                            }
                                            while (n4 < n5) {
                                                block59 : {
                                                    block57 : {
                                                        block58 : {
                                                            block54 : {
                                                                n2 = ((String)object).charAt(n4);
                                                                if (n2 < 49 || n2 > 57) break block54;
                                                                object2[n] = (char)n2;
                                                                n7 = n + 1;
                                                                n2 = 0;
                                                                break block57;
                                                            }
                                                            if (n2 != 48) break block58;
                                                            object2[n] = (char)n2;
                                                            n2 = n10 + 1;
                                                            n7 = n + 1;
                                                            break block57;
                                                        }
                                                        if (n2 != 46) break;
                                                        if (n8 != 0) break block59;
                                                        n2 = n7 = n4;
                                                        if (n6 != 0) {
                                                            n2 = n7 - 1;
                                                        }
                                                        n8 = 1;
                                                        n13 = n2;
                                                        n2 = n10;
                                                        n7 = n;
                                                    }
                                                    ++n4;
                                                    n = n7;
                                                    n10 = n2;
                                                    continue;
                                                }
                                                object2 = new NumberFormatException("multiple points");
                                                throw object2;
                                            }
                                            if ((n = (n3 = n - n10) == 0 ? 1 : 0) != 0 && n9 == 0) break block50;
                                            n2 = n8 != 0 ? n13 - n9 : n3 + n10;
                                            if (n4 >= n5) break block55;
                                            n11 = ((String)object).charAt(n4);
                                            if (n11 != 101 && n11 != 69) break block55;
                                            n8 = 0;
                                            n13 = 214748364;
                                            n7 = 0;
                                            n6 = n4 + 1;
                                            n4 = ((String)object).charAt(n6);
                                            n9 = 1;
                                            if (n4 == 43) break block60;
                                            if (n4 == 45) break block61;
                                            n4 = n6;
                                            break block62;
                                        }
                                        n4 = -1;
                                        break block63;
                                    }
                                    n4 = n9;
                                }
                                n9 = n4;
                                n4 = ++n6;
                            }
                            n6 = n4;
                            while (n6 < n5) {
                                if (n8 >= n13) {
                                    n7 = 1;
                                }
                                n12 = n6 + '\u0001';
                                n11 = ((String)object).charAt(n6);
                                if (n11 >= 48 && n11 <= 57) {
                                    n8 = n8 * 10 + (n11 - 48);
                                    n6 = n12;
                                    continue;
                                }
                                n6 = n11;
                                n11 = n12 - 1;
                                n13 = n6;
                                break block56;
                            }
                            n13 = n11;
                            n11 = n6;
                        }
                        n6 = n3 + 324 + n10;
                        n2 = n7 == 0 && n8 <= n6 ? (n2 += n9 * n8) : n9 * n6;
                        if (n11 == n4) break block50;
                        n4 = n11;
                    }
                    if (n4 >= n5) break block64;
                    if (n4 != n5 - 1) break block50;
                    try {
                        if (((String)object).charAt(n4) != 'f' && ((String)object).charAt(n4) != 'F' && ((String)object).charAt(n4) != 'd' && ((String)object).charAt(n4) != 'D') break block50;
                    }
                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
                }
                if (n != 0) {
                    if (bl) {
                        object2 = A2BC_NEGATIVE_ZERO;
                        return object2;
                    }
                    object2 = A2BC_POSITIVE_ZERO;
                    return object2;
                }
                return new ASCIIToBinaryBuffer(bl, n2, (char[])object2, n3);
                break block50;
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
                break block50;
            }
            try {
                object2 = new NumberFormatException("empty String");
                throw object2;
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
            break block50;
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                // empty catch block
            }
        }
        object2 = new StringBuilder();
        object2.append("For input string: \"");
        object2.append((String)object);
        object2.append("\"");
        throw new NumberFormatException(object2.toString());
    }

    static String stripLeadingZeros(String string) {
        if (!string.isEmpty() && string.charAt(0) == '0') {
            for (int i = 1; i < string.length(); ++i) {
                if (string.charAt(i) == '0') continue;
                return string.substring(i);
            }
            return "";
        }
        return string;
    }

    public static String toJavaFormatString(double d) {
        return FloatingDecimal.getBinaryToASCIIConverter(d).toJavaFormatString();
    }

    public static String toJavaFormatString(float f) {
        return FloatingDecimal.getBinaryToASCIIConverter(f).toJavaFormatString();
    }

    static class ASCIIToBinaryBuffer
    implements ASCIIToBinaryConverter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final double[] BIG_10_POW;
        private static final int MAX_SMALL_TEN;
        private static final int SINGLE_MAX_SMALL_TEN;
        private static final float[] SINGLE_SMALL_10_POW;
        private static final double[] SMALL_10_POW;
        private static final double[] TINY_10_POW;
        int decExponent;
        char[] digits;
        boolean isNegative;
        int nDigits;

        static {
            SMALL_10_POW = new double[]{1.0, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, 1.0E7, 1.0E8, 1.0E9, 1.0E10, 1.0E11, 1.0E12, 1.0E13, 1.0E14, 1.0E15, 1.0E16, 1.0E17, 1.0E18, 1.0E19, 1.0E20, 1.0E21, 1.0E22};
            SINGLE_SMALL_10_POW = new float[]{1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f};
            BIG_10_POW = new double[]{1.0E16, 1.0E32, 1.0E64, 1.0E128, 1.0E256};
            TINY_10_POW = new double[]{1.0E-16, 1.0E-32, 1.0E-64, 1.0E-128, 1.0E-256};
            MAX_SMALL_TEN = SMALL_10_POW.length - 1;
            SINGLE_MAX_SMALL_TEN = SINGLE_SMALL_10_POW.length - 1;
        }

        ASCIIToBinaryBuffer(boolean bl, int n, char[] arrc, int n2) {
            this.isNegative = bl;
            this.decExponent = n;
            this.digits = arrc;
            this.nDigits = n2;
        }

        @Override
        public double doubleValue() {
            Object object;
            double d;
            int n;
            int n2;
            int n3;
            double d2;
            double d3;
            int n4 = Math.min(this.nDigits, 16);
            int n5 = this.digits[0] - 48;
            int n6 = Math.min(n4, 9);
            for (n2 = 1; n2 < n6; ++n2) {
                n5 = n5 * 10 + this.digits[n2] - 48;
            }
            long l = n5;
            for (n2 = n6; n2 < n4; ++n2) {
                l = 10L * l + (long)(this.digits[n2] - 48);
            }
            double d4 = l;
            n2 = this.decExponent - n4;
            if (this.nDigits <= 15) {
                if (n2 != 0 && d4 != 0.0) {
                    if (n2 >= 0) {
                        n3 = MAX_SMALL_TEN;
                        if (n2 <= n3) {
                            d4 = SMALL_10_POW[n2] * d4;
                            if (this.isNegative) {
                                d4 = -d4;
                            }
                            return d4;
                        }
                        n = 15 - n4;
                        if (n2 <= n3 + n) {
                            double[] arrd = SMALL_10_POW;
                            double d5 = arrd[n];
                            d4 = arrd[n2 - n] * (d4 * d5);
                            if (this.isNegative) {
                                d4 = -d4;
                            }
                            return d4;
                        }
                    } else if (n2 >= -MAX_SMALL_TEN) {
                        d4 /= SMALL_10_POW[-n2];
                        if (this.isNegative) {
                            d4 = -d4;
                        }
                        return d4;
                    }
                } else {
                    if (this.isNegative) {
                        d4 = -d4;
                    }
                    return d4;
                }
            }
            if (n2 > 0) {
                n3 = this.decExponent;
                d = Double.NEGATIVE_INFINITY;
                if (n3 > 309) {
                    if (!this.isNegative) {
                        d = Double.POSITIVE_INFINITY;
                    }
                    return d;
                }
                d3 = d4;
                if ((n2 & 15) != 0) {
                    d3 = d4 * SMALL_10_POW[n2 & 15];
                }
                n2 = n3 = n2 >> 4;
                if (n3 != 0) {
                    n3 = 0;
                    while (n2 > 1) {
                        d4 = d3;
                        if ((n2 & 1) != 0) {
                            d4 = d3 * BIG_10_POW[n3];
                        }
                        ++n3;
                        n2 >>= 1;
                        d3 = d4;
                    }
                    d4 = d2 = BIG_10_POW[n3] * d3;
                    if (Double.isInfinite(d2)) {
                        if (Double.isInfinite(d3 / 2.0 * BIG_10_POW[n3])) {
                            if (!this.isNegative) {
                                d = Double.POSITIVE_INFINITY;
                            }
                            return d;
                        }
                        d4 = Double.MAX_VALUE;
                    }
                } else {
                    d4 = d3;
                }
            } else if (n2 < 0) {
                n3 = -n2;
                n2 = this.decExponent;
                d = 0.0;
                if (n2 < -325) {
                    if (!this.isNegative) {
                        d = 0.0;
                    }
                    return d;
                }
                d3 = d4;
                if ((n3 & 15) != 0) {
                    d3 = d4 / SMALL_10_POW[n3 & 15];
                }
                if (n3 != 0) {
                    n3 = 0;
                    for (n2 = n3 >>= 4; n2 > 1; n2 >>= 1) {
                        d4 = d3;
                        if ((n2 & 1) != 0) {
                            d4 = d3 * TINY_10_POW[n3];
                        }
                        ++n3;
                        d3 = d4;
                    }
                    object = TINY_10_POW;
                    d4 = d2 = object[n3] * d3;
                    if (d2 == 0.0) {
                        if (2.0 * d3 * object[n3] == 0.0) {
                            if (!this.isNegative) {
                                d = 0.0;
                            }
                            return d;
                        }
                        d4 = Double.MIN_VALUE;
                    }
                } else {
                    d4 = d3;
                }
            }
            if (this.nDigits > 1100) {
                this.nDigits = 1101;
                this.digits[1100] = (char)49;
            }
            object = new FDBigInteger(l, this.digits, n4, this.nDigits);
            n = this.decExponent - this.nDigits;
            long l2 = Double.doubleToRawLongBits(d4);
            int n7 = Math.max(0, -n);
            n2 = Math.max(0, n);
            object = ((FDBigInteger)object).multByPow52(n2, 0);
            ((FDBigInteger)object).makeImmutable();
            FDBigInteger fDBigInteger = null;
            n3 = 0;
            long l3 = l;
            int n8 = n5;
            do {
                n5 = (int)(l2 >>> 52);
                l = l2 & 0xFFFFFFFFFFFFFL;
                if (n5 > 0) {
                    l |= 0x10000000000000L;
                } else {
                    n5 = Long.numberOfLeadingZeros(l) - 11;
                    l <<= n5;
                    n5 = 1 - n5;
                }
                int n9 = n5 - 1023;
                int n10 = Long.numberOfTrailingZeros(l);
                int n11 = n9 - 52 + n10;
                n5 = n7;
                int n12 = n2;
                if (n11 >= 0) {
                    n5 += n11;
                } else {
                    n12 -= n11;
                }
                n9 = n9 <= -1023 ? n9 + n10 + 1023 : n10 + 1;
                int n13 = n5 + n9;
                n12 += n9;
                n9 = Math.min(n13, Math.min(n12, n5));
                n5 -= n9;
                FDBigInteger fDBigInteger2 = FDBigInteger.valueOfMulPow52(l >>> n10, n7, n13 - n9);
                if (fDBigInteger == null || n3 != (n12 -= n9)) {
                    fDBigInteger = ((FDBigInteger)object).leftShift(n12);
                    n3 = n12;
                }
                Object object2 = object;
                n12 = fDBigInteger2.cmp(fDBigInteger);
                if (n12 > 0) {
                    n12 = 1;
                    object = fDBigInteger2.leftInplaceSub(fDBigInteger);
                    if (53 - n10 == 1 && n11 > -1022 && --n5 < 0) {
                        object = ((FDBigInteger)object).leftShift(1);
                        n5 = 0;
                    }
                } else {
                    l = l2;
                    if (n12 >= 0) break;
                    n12 = 0;
                    object = fDBigInteger.rightInplaceSub(fDBigInteger2);
                }
                n5 = ((FDBigInteger)object).cmpPow52(n7, n5);
                if (n5 < 0) {
                    l = l2;
                    break;
                }
                long l4 = 1L;
                if (n5 == 0) {
                    l = l2;
                    if ((l2 & 1L) == 0L) break;
                    if (n12 != 0) {
                        l4 = -1L;
                    }
                    l = l2 + l4;
                    break;
                }
                if (n12 != 0) {
                    l4 = -1L;
                }
                l = l2 += l4;
                if (l2 == 0L) break;
                if (l2 == 9218868437227405312L) {
                    l = l2;
                    break;
                }
                object = object2;
            } while (true);
            l2 = l;
            if (this.isNegative) {
                l2 = l | Long.MIN_VALUE;
            }
            return Double.longBitsToDouble(l2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public float floatValue() {
            var1_1 = Math.min(this.nDigits, 8);
            var2_2 = this.digits[0] - 48;
            for (var3_3 = 1; var3_3 < var1_1; ++var3_3) {
                var2_2 = var2_2 * 10 + this.digits[var3_3] - 48;
            }
            var4_4 = var2_2;
            var5_5 = this.decExponent;
            var3_3 = var5_5 - var1_1;
            var6_6 = this.nDigits;
            var7_7 = 0.0f;
            if (var6_6 > 7) ** GOTO lbl33
            if (var3_3 != 0 && var4_4 != 0.0f) {
                if (var3_3 >= 0) {
                    var5_5 = ASCIIToBinaryBuffer.SINGLE_MAX_SMALL_TEN;
                    if (var3_3 <= var5_5) {
                        var7_7 = var4_4 * ASCIIToBinaryBuffer.SINGLE_SMALL_10_POW[var3_3];
                        if (this.isNegative == false) return var7_7;
                        return -var7_7;
                    }
                    var6_6 = 7 - var1_1;
                    if (var3_3 <= var5_5 + var6_6) {
                        var8_8 = ASCIIToBinaryBuffer.SINGLE_SMALL_10_POW;
                        var7_7 = var4_4 * var8_8[var6_6] * var8_8[var3_3 - var6_6];
                        if (this.isNegative == false) return var7_7;
                        return -var7_7;
                    }
                } else if (var3_3 >= -ASCIIToBinaryBuffer.SINGLE_MAX_SMALL_TEN) {
                    var7_7 = var4_4 / ASCIIToBinaryBuffer.SINGLE_SMALL_10_POW[-var3_3];
                    if (this.isNegative == false) return var7_7;
                    return -var7_7;
                }
            } else {
                if (this.isNegative == false) return var4_4;
                return -var4_4;
lbl33: // 1 sources:
                if (var5_5 >= var6_6 && var6_6 + var5_5 <= 15) {
                    var9_10 = var2_2;
                    var2_2 = var1_1;
                    do {
                        if (var2_2 >= (var3_3 = this.nDigits)) {
                            var11_11 = var9_10;
                            var2_2 = this.decExponent;
                            var7_7 = (float)(var11_11 * ASCIIToBinaryBuffer.SMALL_10_POW[var2_2 - var3_3]);
                            if (this.isNegative == false) return var7_7;
                            return -var7_7;
                        }
                        var9_10 = 10L * var9_10 + (long)(this.digits[var2_2] - 48);
                        ++var2_2;
                    } while (true);
                }
            }
            var13_13 = var4_4;
            if (var3_3 > 0) {
                if (this.decExponent > 39) {
                    if (this.isNegative == false) return Float.POSITIVE_INFINITY;
                    return Float.NEGATIVE_INFINITY;
                }
                var11_12 = var13_13;
                if ((var3_3 & 15) != 0) {
                    var11_12 = var13_13 * ASCIIToBinaryBuffer.SMALL_10_POW[var3_3 & 15];
                }
                var3_3 = var5_5 = var3_3 >> 4;
                if (var5_5 != 0) {
                    var5_5 = 0;
                    while (var3_3 > 0) {
                        var13_13 = var11_12;
                        if ((var3_3 & 1) != 0) {
                            var13_13 = var11_12 * ASCIIToBinaryBuffer.BIG_10_POW[var5_5];
                        }
                        ++var5_5;
                        var3_3 >>= 1;
                        var11_12 = var13_13;
                    }
                }
            } else if (var3_3 < 0) {
                var3_3 = -var3_3;
                if (this.decExponent < -46) {
                    if (this.isNegative == false) return var7_7;
                    return 0.0f;
                }
                var11_12 = var13_13;
                if ((var3_3 & 15) != 0) {
                    var11_12 = var13_13 / ASCIIToBinaryBuffer.SMALL_10_POW[var3_3 & 15];
                }
                if ((var3_3 >>= 4) != 0) {
                    var5_5 = 0;
                    while (var3_3 > 0) {
                        var13_13 = var11_12;
                        if ((var3_3 & 1) != 0) {
                            var13_13 = var11_12 * ASCIIToBinaryBuffer.TINY_10_POW[var5_5];
                        }
                        ++var5_5;
                        var3_3 >>= 1;
                        var11_12 = var13_13;
                    }
                }
            } else {
                var11_12 = var13_13;
            }
            var7_7 = Math.max(Float.MIN_VALUE, Math.min(Float.MAX_VALUE, (float)var11_12));
            if (this.nDigits > 200) {
                this.nDigits = 201;
                this.digits[200] = (char)49;
            }
            var8_9 = new FDBigInteger(var2_2, this.digits, var1_1, this.nDigits);
            var15_14 = this.decExponent - this.nDigits;
            var6_6 = Float.floatToRawIntBits(var7_7);
            var16_15 = Math.max(0, -var15_14);
            var5_5 = Math.max(0, var15_14);
            var17_16 = var8_9.multByPow52(var5_5, 0);
            var17_16.makeImmutable();
            var18_17 = null;
            var3_3 = 0;
            var19_18 = var2_2;
            do {
                block47 : {
                    block46 : {
                        var20_19 = var6_6 >>> 23;
                        var2_2 = 8388607 & var6_6;
                        if (var20_19 > 0) {
                            var2_2 |= 8388608;
                        } else {
                            var20_19 = Integer.numberOfLeadingZeros(var2_2) - 8;
                            var2_2 <<= var20_19;
                            var20_19 = 1 - var20_19;
                        }
                        var21_20 = var20_19 - 127;
                        var22_21 = Integer.numberOfTrailingZeros(var2_2);
                        var23_22 = var21_20 - 23 + var22_21;
                        var20_19 = var16_15;
                        var24_23 = var5_5;
                        if (var23_22 >= 0) {
                            var20_19 += var23_22;
                        } else {
                            var24_23 -= var23_22;
                        }
                        var21_20 = var21_20 <= -127 ? var21_20 + var22_21 + 127 : var22_21 + 1;
                        var25_24 = var20_19 + var21_20;
                        var26_25 = Math.min(var25_24, Math.min(var24_23 += var21_20, var20_19));
                        var21_20 = var24_23 - var26_25;
                        var24_23 = var20_19 - var26_25;
                        var8_9 = FDBigInteger.valueOfMulPow52(var2_2 >>> var22_21, var16_15, var25_24 - var26_25);
                        if (var18_17 == null) break block46;
                        var20_19 = var3_3;
                        if (var3_3 == var21_20) break block47;
                    }
                    var18_17 = var17_16.leftShift(var21_20);
                    var20_19 = var21_20;
                }
                if ((var2_2 = var8_9.cmp(var18_17)) > 0) {
                    var8_9 = var8_9.leftInplaceSub(var18_17);
                    if (24 - var22_21 == 1 && var23_22 > -126) {
                        var3_3 = var24_23 - 1;
                        if (var3_3 < 0) {
                            var8_9 = var8_9.leftShift(1);
                            var2_2 = 1;
                            var3_3 = 0;
                        } else {
                            var2_2 = 1;
                        }
                    } else {
                        var2_2 = 1;
                        var3_3 = var24_23;
                    }
                } else {
                    var3_3 = var6_6;
                    if (var2_2 >= 0) break;
                    var8_9 = var18_17.rightInplaceSub(var8_9);
                    var2_2 = 0;
                    var3_3 = var24_23;
                }
                var21_20 = 1;
                var24_23 = 1;
                var3_3 = var8_9.cmpPow52(var16_15, var3_3);
                if (var3_3 < 0) {
                    var3_3 = var6_6;
                    break;
                }
                if (var3_3 == 0) {
                    var3_3 = var6_6;
                    if ((var6_6 & 1) == 0) break;
                    var3_3 = var24_23;
                    if (var2_2 != 0) {
                        var3_3 = -1;
                    }
                    var3_3 = var6_6 + var3_3;
                    break;
                }
                var3_3 = var21_20;
                if (var2_2 != 0) {
                    var3_3 = -1;
                }
                var6_6 += var3_3;
                var3_3 = var6_6;
                if (var6_6 == 0) break;
                if (var6_6 == 2139095040) {
                    var3_3 = var6_6;
                    break;
                }
                var3_3 = var20_19;
            } while (true);
            var2_2 = var3_3;
            if (this.isNegative == false) return Float.intBitsToFloat(var2_2);
            var2_2 = var3_3 | Integer.MIN_VALUE;
            return Float.intBitsToFloat(var2_2);
        }
    }

    static interface ASCIIToBinaryConverter {
        public double doubleValue();

        public float floatValue();
    }

    static class BinaryToASCIIBuffer
    implements BinaryToASCIIConverter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int[] N_5_BITS;
        private static int[] insignificantDigitsNumber;
        private final char[] buffer = new char[26];
        private int decExponent;
        private boolean decimalDigitsRoundedUp = false;
        private final char[] digits;
        private boolean exactDecimalConversion = false;
        private int firstDigitIndex;
        private boolean isNegative;
        private int nDigits;

        static {
            insignificantDigitsNumber = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19};
            N_5_BITS = new int[]{0, 3, 5, 7, 10, 12, 14, 17, 19, 21, 24, 26, 28, 31, 33, 35, 38, 40, 42, 45, 47, 49, 52, 54, 56, 59, 61};
        }

        BinaryToASCIIBuffer() {
            this.digits = new char[20];
        }

        BinaryToASCIIBuffer(boolean bl, char[] arrc) {
            this.isNegative = bl;
            this.decExponent = 0;
            this.digits = arrc;
            this.firstDigitIndex = 0;
            this.nDigits = arrc.length;
        }

        private void developLongDigits(int n, long l, int n2) {
            int n3;
            int n4 = n;
            long l2 = l;
            if (n2 != 0) {
                long l3 = FDBigInteger.LONG_5_POW[n2] << n2;
                long l4 = l / l3;
                n4 = n += n2;
                l2 = l4;
                if (l % l3 >= l3 >> 1) {
                    l2 = l4 + 1L;
                    n4 = n;
                }
            }
            int n5 = this.digits.length - 1;
            if (l2 <= Integer.MAX_VALUE) {
                int n6;
                int n7;
                int n8;
                n = (int)l2;
                n2 = n % 10;
                n3 = n4;
                n4 = n /= 10;
                do {
                    n7 = n4;
                    n = n5;
                    n8 = n2;
                    n6 = n3++;
                    if (n2 != 0) break;
                    n2 = n4 % 10;
                    n4 /= 10;
                } while (true);
                while (n7 != 0) {
                    this.digits[n] = (char)(n8 + 48);
                    ++n6;
                    n8 = n7 % 10;
                    n7 /= 10;
                    --n;
                }
                this.digits[n] = (char)(n8 + 48);
                n3 = n6;
            } else {
                int n9;
                n2 = (int)(l2 % 10L);
                l = l2 / 10L;
                do {
                    n9 = n2;
                    n = n5;
                    n3 = n4++;
                    l2 = l;
                    if (n2 != 0) break;
                    n2 = (int)(l % 10L);
                    l /= 10L;
                } while (true);
                while (l2 != 0L) {
                    this.digits[n] = (char)(n9 + 48);
                    ++n3;
                    n9 = (int)(l2 % 10L);
                    l2 /= 10L;
                    --n;
                }
                this.digits[n] = (char)(n9 + 48);
            }
            this.decExponent = n3 + 1;
            this.firstDigitIndex = n;
            this.nDigits = this.digits.length - n;
        }

        private void dtoa(int n, long l, int n2, boolean bl) {
            int n3;
            int n4;
            int n5;
            block36 : {
                int n6;
                int n7;
                int n8;
                Object object;
                block32 : {
                    block33 : {
                        int n9;
                        int n10;
                        int n11;
                        block35 : {
                            int n12;
                            int n13;
                            int n14;
                            int n15;
                            block34 : {
                                n4 = Long.numberOfTrailingZeros(l);
                                n13 = 53 - n4;
                                this.decimalDigitsRoundedUp = false;
                                this.exactDecimalConversion = false;
                                n3 = Math.max(0, n13 - n - 1);
                                if (n <= 62 && n >= -21 && n3 < FDBigInteger.LONG_5_POW.length && N_5_BITS[n3] + n13 < 64 && n3 == 0) {
                                    n2 = n > n2 ? BinaryToASCIIBuffer.insignificantDigitsForPow2(n - n2 - 1) : 0;
                                    l = n >= 52 ? (l <<= n - 52) : (l >>>= 52 - n);
                                    this.developLongDigits(0, l, n2);
                                    return;
                                }
                                n5 = BinaryToASCIIBuffer.estimateDecExp(l, n);
                                n7 = Math.max(0, -n5);
                                n = n7 + n3 + n;
                                n8 = Math.max(0, n5);
                                n3 = n8 + n3;
                                l >>>= n4;
                                n6 = n - (n13 - 1);
                                n4 = Math.min(n6, n3);
                                n6 -= n4;
                                n11 = n3 - n4;
                                n = n2 = n - n2 - n4;
                                if (n13 == 1) {
                                    n = n2 - 1;
                                }
                                n3 = n6;
                                n4 = n11;
                                n2 = n;
                                if (n < 0) {
                                    n3 = n6 - n;
                                    n4 = n11 - n;
                                    n2 = 0;
                                }
                                n = n7 < ((int[])(object = N_5_BITS)).length ? object[n7] : n7 * 3;
                                n6 = n13 + n3 + n;
                                object = N_5_BITS;
                                n = n8 + 1 < ((int[])object).length ? object[n8 + 1] : (n8 + 1) * 3;
                                n = n4 + 1 + n;
                                if (n6 >= 64 || n >= 64) break block32;
                                if (n6 >= 32 || n >= 32) break block33;
                                n = (int)l * FDBigInteger.SMALL_5_POW[n7] << n3;
                                n10 = FDBigInteger.SMALL_5_POW[n8] << n4;
                                n4 = FDBigInteger.SMALL_5_POW[n7];
                                n9 = n10 * 10;
                                n12 = n / n10;
                                n14 = n % n10 * 10;
                                n15 = (n4 << n2) * 10;
                                n2 = n14 < n15 ? 1 : 0;
                                n = n14 + n15 > n9 ? 1 : 0;
                                if (n12 == 0 && n == 0) {
                                    --n5;
                                    n3 = 0;
                                } else {
                                    object = this.digits;
                                    n3 = 0 + 1;
                                    object[0] = (char)(n12 + 48);
                                }
                                if (!bl || n5 < -3) break block34;
                                n6 = n13;
                                n11 = n14;
                                n8 = n12;
                                n7 = n15;
                                n4 = n3;
                                if (n5 < 8) break block35;
                            }
                            n2 = 0;
                            n = 0;
                            n4 = n3;
                            n7 = n15;
                            n8 = n12;
                            n11 = n14;
                            n6 = n13;
                        }
                        while (n2 == 0 && n == 0) {
                            n8 = n11 / n10;
                            n11 = n11 % n10 * 10;
                            if ((long)(n7 *= 10) > 0L) {
                                n = n11 < n7 ? 1 : 0;
                                n2 = n11 + n7 > n9 ? 1 : 0;
                                n3 = n;
                                n = n2;
                                n2 = n3;
                            } else {
                                n2 = 1;
                                n = 1;
                            }
                            this.digits[n4] = (char)(n8 + 48);
                            ++n4;
                        }
                        l = (n11 << 1) - n9;
                        bl = n11 == 0;
                        this.exactDecimalConversion = bl;
                        n3 = n4;
                        n4 = n2;
                        n2 = n;
                        break block36;
                    }
                    l = FDBigInteger.LONG_5_POW[n7] * l << n3;
                    long l2 = FDBigInteger.LONG_5_POW[n8] << n4;
                    long l3 = FDBigInteger.LONG_5_POW[n7];
                    long l4 = l2 * 10L;
                    n4 = (int)(l / l2);
                    n2 = (l = l % l2 * 10L) < (l3 = (l3 << n2) * 10L) ? 1 : 0;
                    n3 = l + l3 > l4 ? 1 : 0;
                    if (n4 == 0 && n3 == 0) {
                        n4 = n5 - 1;
                        n = 0;
                    } else {
                        object = this.digits;
                        n = 0 + 1;
                        object[0] = (char)(n4 + 48);
                        n4 = n5;
                    }
                    if (bl && n4 >= -3 && n4 < 8) {
                        n5 = n2;
                        n2 = n3;
                    } else {
                        n5 = 0;
                        n2 = 0;
                    }
                    while (n5 == 0 && n2 == 0) {
                        n6 = (int)(l / l2);
                        l = l % l2 * 10L;
                        if ((l3 *= 10L) > 0L) {
                            n2 = l < l3 ? 1 : 0;
                            n5 = l + l3 > l4 ? 1 : 0;
                            n3 = n5;
                            n5 = n2;
                            n2 = n3;
                        } else {
                            n5 = 1;
                            n2 = 1;
                        }
                        this.digits[n] = (char)(n6 + 48);
                        ++n;
                    }
                    l3 = (l << 1) - l4;
                    bl = l == 0L;
                    this.exactDecimalConversion = bl;
                    n6 = n5;
                    n3 = n;
                    l = l3;
                    n5 = n4;
                    n4 = n6;
                    break block36;
                }
                object = FDBigInteger.valueOfPow52(n8, n4);
                n = ((FDBigInteger)object).getNormalizationBias();
                FDBigInteger fDBigInteger = ((FDBigInteger)object).leftShift(n);
                FDBigInteger fDBigInteger2 = FDBigInteger.valueOfMulPow52(l, n7, n3 + n);
                object = FDBigInteger.valueOfPow52(n7 + 1, n2 + n + 1);
                FDBigInteger fDBigInteger3 = FDBigInteger.valueOfPow52(n8 + 1, n4 + n + 1);
                n3 = fDBigInteger2.quoRemIteration(fDBigInteger);
                n = fDBigInteger2.cmp((FDBigInteger)object) < 0 ? 1 : 0;
                n2 = fDBigInteger3.addAndCmp(fDBigInteger2, (FDBigInteger)object) <= 0 ? 1 : 0;
                if (n3 == 0 && n2 == 0) {
                    --n5;
                    n4 = 0;
                } else {
                    char[] arrc = this.digits;
                    n4 = 0 + 1;
                    arrc[0] = (char)(n3 + 48);
                }
                if (!bl || n5 < -3 || n5 >= 8) {
                    n = 0;
                    n2 = 0;
                }
                while (n == 0 && n2 == 0) {
                    n3 = fDBigInteger2.quoRemIteration(fDBigInteger);
                    n = fDBigInteger2.cmp((FDBigInteger)(object = ((FDBigInteger)object).multBy10())) < 0 ? 1 : 0;
                    n2 = n;
                    n = fDBigInteger3.addAndCmp(fDBigInteger2, (FDBigInteger)object) <= 0 ? 1 : 0;
                    n6 = n;
                    this.digits[n4] = (char)(n3 + 48);
                    ++n4;
                    n = n2;
                    n2 = n6;
                }
                if (n2 != 0 && n != 0) {
                    object = fDBigInteger2.leftShift(1);
                    l = ((FDBigInteger)object).cmp(fDBigInteger3);
                } else {
                    l = 0L;
                    object = fDBigInteger2;
                }
                bl = ((FDBigInteger)object).cmp(FDBigInteger.ZERO) == 0;
                this.exactDecimalConversion = bl;
                n3 = n4;
                n4 = n;
            }
            this.decExponent = n5 + 1;
            this.firstDigitIndex = 0;
            this.nDigits = n3;
            if (n2 != 0) {
                if (n4 != 0) {
                    if (l == 0L) {
                        if ((this.digits[this.firstDigitIndex + this.nDigits - 1] & '\u0001') != 0) {
                            this.roundup();
                        }
                    } else if (l > 0L) {
                        this.roundup();
                    }
                } else {
                    this.roundup();
                }
            }
        }

        static int estimateDecExp(long l, int n) {
            double d = (Double.longBitsToDouble(l & 0xFFFFFFFFFFFFFL | 4607182418800017408L) - 1.5) * 0.289529654 + 0.176091259 + (double)n * 0.301029995663981;
            l = Double.doubleToRawLongBits(d);
            int n2 = (int)((9218868437227405312L & l) >> 52) - 1023;
            int n3 = 0;
            n = (Long.MIN_VALUE & l) != 0L ? 1 : 0;
            if (n2 >= 0 && n2 < 52) {
                n3 = (int)((0xFFFFFFFFFFFFFL & l | 0x10000000000000L) >> 52 - n2);
                n = n != 0 ? ((0xFFFFFFFFFFFFFL >> n2 & l) == 0L ? -n3 : -n3 - 1) : n3;
                return n;
            }
            if (n2 < 0) {
                if ((Long.MAX_VALUE & l) != 0L && n != 0) {
                    n3 = -1;
                }
                return n3;
            }
            return (int)d;
        }

        private int getChars(char[] arrc) {
            int n;
            int n2 = 0;
            if (this.isNegative) {
                arrc[0] = (char)45;
                n2 = 1;
            }
            if ((n = this.decExponent) > 0 && n < 8) {
                n = Math.min(this.nDigits, n);
                System.arraycopy((Object)this.digits, this.firstDigitIndex, (Object)arrc, n2, n);
                n2 += n;
                int n3 = this.decExponent;
                if (n < n3) {
                    n = n3 - n;
                    Arrays.fill(arrc, n2, n2 + n, '0');
                    n2 += n;
                    n = n2 + 1;
                    arrc[n2] = (char)46;
                    n2 = n + 1;
                    arrc[n] = (char)48;
                } else {
                    n3 = n2 + 1;
                    arrc[n2] = (char)46;
                    n2 = this.nDigits;
                    if (n < n2) {
                        System.arraycopy((Object)this.digits, this.firstDigitIndex + n, (Object)arrc, n3, n2 -= n);
                        n2 += n3;
                    } else {
                        n2 = n3 + 1;
                        arrc[n3] = (char)48;
                    }
                }
            } else {
                int n4 = this.decExponent;
                if (n4 <= 0 && n4 > -3) {
                    int n5 = n2 + 1;
                    arrc[n2] = (char)48;
                    n = n5 + 1;
                    arrc[n5] = (char)46;
                    n2 = n;
                    if (n4 != 0) {
                        Arrays.fill(arrc, n, n - n4, '0');
                        n2 = n - this.decExponent;
                    }
                    System.arraycopy((Object)this.digits, this.firstDigitIndex, (Object)arrc, n2, this.nDigits);
                    n2 += this.nDigits;
                } else {
                    n4 = n2 + 1;
                    char[] arrc2 = this.digits;
                    n = this.firstDigitIndex;
                    arrc[n2] = arrc2[n];
                    n2 = n4 + 1;
                    arrc[n4] = (char)46;
                    n4 = this.nDigits;
                    if (n4 > 1) {
                        System.arraycopy((Object)arrc2, n + 1, (Object)arrc, n2, n4 - 1);
                        n2 += this.nDigits - 1;
                    } else {
                        arrc[n2] = (char)48;
                        ++n2;
                    }
                    n = n2 + 1;
                    arrc[n2] = (char)69;
                    n2 = this.decExponent;
                    if (n2 <= 0) {
                        arrc[n] = (char)45;
                        n2 = -n2 + 1;
                        n4 = n + 1;
                        n = n2;
                        n2 = n4;
                    } else {
                        n4 = n2 - 1;
                        n2 = n;
                        n = n4;
                    }
                    if (n <= 9) {
                        arrc[n2] = (char)(n + 48);
                        ++n2;
                    } else if (n <= 99) {
                        n4 = n2 + 1;
                        arrc[n2] = (char)(n / 10 + 48);
                        arrc[n4] = (char)(n % 10 + 48);
                        n2 = n4 + 1;
                    } else {
                        n4 = n2 + 1;
                        arrc[n2] = (char)(n / 100 + 48);
                        n2 = n % 100;
                        n = n4 + 1;
                        arrc[n4] = (char)(n2 / 10 + 48);
                        arrc[n] = (char)(n2 % 10 + 48);
                        n2 = n + 1;
                    }
                }
            }
            return n2;
        }

        private static int insignificantDigits(int n) {
            int n2 = 0;
            while ((long)n >= 10L) {
                n = (int)((long)n / 10L);
                ++n2;
            }
            return n2;
        }

        private static int insignificantDigitsForPow2(int n) {
            int[] arrn;
            if (n > 1 && n < (arrn = insignificantDigitsNumber).length) {
                return arrn[n];
            }
            return 0;
        }

        private void roundup() {
            int n = this.firstDigitIndex + this.nDigits - 1;
            char c = this.digits[n];
            int n2 = n;
            char c2 = c;
            if (c == '9') {
                while (c == '9' && n > this.firstDigitIndex) {
                    char[] arrc = this.digits;
                    arrc[n] = (char)48;
                    c = arrc[--n];
                }
                n2 = n;
                c2 = c;
                if (c == '9') {
                    ++this.decExponent;
                    this.digits[this.firstDigitIndex] = (char)49;
                    return;
                }
            }
            this.digits[n2] = (char)(c2 + '\u0001');
            this.decimalDigitsRoundedUp = true;
        }

        private void setSign(boolean bl) {
            this.isNegative = bl;
        }

        @Override
        public void appendTo(Appendable appendable) {
            block1 : {
                int n;
                block0 : {
                    n = this.getChars(this.buffer);
                    if (!(appendable instanceof StringBuilder)) break block0;
                    ((StringBuilder)appendable).append(this.buffer, 0, n);
                    break block1;
                }
                if (!(appendable instanceof StringBuffer)) break block1;
                ((StringBuffer)appendable).append(this.buffer, 0, n);
            }
        }

        @Override
        public boolean decimalDigitsExact() {
            return this.exactDecimalConversion;
        }

        @Override
        public boolean digitsRoundedUp() {
            return this.decimalDigitsRoundedUp;
        }

        @Override
        public int getDecimalExponent() {
            return this.decExponent;
        }

        @Override
        public int getDigits(char[] arrc) {
            System.arraycopy((Object)this.digits, this.firstDigitIndex, (Object)arrc, 0, this.nDigits);
            return this.nDigits;
        }

        @Override
        public boolean isExceptional() {
            return false;
        }

        @Override
        public boolean isNegative() {
            return this.isNegative;
        }

        @Override
        public String toJavaFormatString() {
            int n = this.getChars(this.buffer);
            return new String(this.buffer, 0, n);
        }
    }

    public static interface BinaryToASCIIConverter {
        public void appendTo(Appendable var1);

        public boolean decimalDigitsExact();

        public boolean digitsRoundedUp();

        public int getDecimalExponent();

        public int getDigits(char[] var1);

        public boolean isExceptional();

        public boolean isNegative();

        public String toJavaFormatString();
    }

    private static class ExceptionalBinaryToASCIIBuffer
    implements BinaryToASCIIConverter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String image;
        private boolean isNegative;

        public ExceptionalBinaryToASCIIBuffer(String string, boolean bl) {
            this.image = string;
            this.isNegative = bl;
        }

        @Override
        public void appendTo(Appendable appendable) {
            block1 : {
                block0 : {
                    if (!(appendable instanceof StringBuilder)) break block0;
                    ((StringBuilder)appendable).append(this.image);
                    break block1;
                }
                if (!(appendable instanceof StringBuffer)) break block1;
                ((StringBuffer)appendable).append(this.image);
            }
        }

        @Override
        public boolean decimalDigitsExact() {
            throw new IllegalArgumentException("Exceptional value is not exact");
        }

        @Override
        public boolean digitsRoundedUp() {
            throw new IllegalArgumentException("Exceptional value is not rounded");
        }

        @Override
        public int getDecimalExponent() {
            throw new IllegalArgumentException("Exceptional value does not have an exponent");
        }

        @Override
        public int getDigits(char[] arrc) {
            throw new IllegalArgumentException("Exceptional value does not have digits");
        }

        @Override
        public boolean isExceptional() {
            return true;
        }

        @Override
        public boolean isNegative() {
            return this.isNegative;
        }

        @Override
        public String toJavaFormatString() {
            return this.image;
        }
    }

    private static class HexFloatPattern {
        private static final Pattern VALUE = Pattern.compile("([-+])?0[xX](((\\p{XDigit}+)\\.?)|((\\p{XDigit}*)\\.(\\p{XDigit}+)))[pP]([-+])?(\\p{Digit}+)[fFdD]?");

        private HexFloatPattern() {
        }
    }

    static class PreparedASCIIToBinaryBuffer
    implements ASCIIToBinaryConverter {
        private final double doubleVal;
        private final float floatVal;

        public PreparedASCIIToBinaryBuffer(double d, float f) {
            this.doubleVal = d;
            this.floatVal = f;
        }

        @Override
        public double doubleValue() {
            return this.doubleVal;
        }

        @Override
        public float floatValue() {
            return this.floatVal;
        }
    }

}

