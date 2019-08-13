/*
 * Decompiled with CFR 0.145.
 */
package android.icu.math;

import android.icu.lang.UCharacter;
import android.icu.math.MathContext;
import java.io.Serializable;
import java.math.BigInteger;

public class BigDecimal
extends Number
implements Serializable,
Comparable<BigDecimal> {
    private static final int MaxArg = 999999999;
    private static final int MaxExp = 999999999;
    private static final int MinArg = -999999999;
    private static final int MinExp = -999999999;
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
    public static final BigDecimal ZERO;
    private static byte[] bytecar;
    private static byte[] bytedig;
    private static final byte isneg = -1;
    private static final byte ispos = 1;
    private static final byte iszero = 0;
    private static final MathContext plainMC;
    private static final long serialVersionUID = 8245355804974198832L;
    private int exp;
    private byte form;
    private byte ind;
    private byte[] mant;

    static {
        ZERO = new BigDecimal(0L);
        ONE = new BigDecimal(1L);
        TEN = new BigDecimal(10);
        plainMC = new MathContext(0, 0);
        bytecar = new byte[190];
        bytedig = BigDecimal.diginit();
    }

    private BigDecimal() {
        this.form = (byte)(false ? 1 : 0);
    }

    public BigDecimal(double d) {
        this(new java.math.BigDecimal(d).toString());
    }

    public BigDecimal(int n) {
        this.form = (byte)(false ? 1 : 0);
        if (n <= 9 && n >= -9) {
            if (n == 0) {
                this.mant = BigDecimal.ZERO.mant;
                this.ind = (byte)(false ? 1 : 0);
            } else if (n == 1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = (byte)(true ? 1 : 0);
            } else if (n == -1) {
                this.mant = BigDecimal.ONE.mant;
                this.ind = (byte)-1;
            } else {
                this.mant = new byte[1];
                if (n > 0) {
                    this.mant[0] = (byte)n;
                    this.ind = (byte)(true ? 1 : 0);
                } else {
                    this.mant[0] = (byte)(-n);
                    this.ind = (byte)-1;
                }
            }
            return;
        }
        if (n > 0) {
            this.ind = (byte)(true ? 1 : 0);
            n = -n;
        } else {
            this.ind = (byte)-1;
        }
        int n2 = n;
        int n3 = 9;
        do {
            if ((n2 /= 10) == 0) {
                this.mant = new byte[10 - n3];
                n3 = 10 - n3 - 1;
                do {
                    this.mant[n3] = -((byte)(n % 10));
                    if ((n /= 10) == 0) {
                        return;
                    }
                    --n3;
                } while (true);
            }
            --n3;
        } while (true);
    }

    public BigDecimal(long l) {
        this.form = (byte)(false ? 1 : 0);
        if (l > 0L) {
            this.ind = (byte)(true ? 1 : 0);
            l = -l;
        } else {
            this.ind = l == 0L ? (byte)(false ? 1 : 0) : (byte)-1;
        }
        long l2 = l;
        int n = 18;
        do {
            if ((l2 /= 10L) == 0L) {
                this.mant = new byte[19 - n];
                n = 19 - n - 1;
                do {
                    this.mant[n] = -((byte)(l % 10L));
                    if ((l /= 10L) == 0L) {
                        return;
                    }
                    --n;
                } while (true);
            }
            --n;
        } while (true);
    }

    public BigDecimal(String string) {
        this(string.toCharArray(), 0, string.length());
    }

    public BigDecimal(java.math.BigDecimal bigDecimal) {
        this(bigDecimal.toString());
    }

    public BigDecimal(BigInteger bigInteger) {
        this(bigInteger.toString(10));
    }

    public BigDecimal(BigInteger serializable, int n) {
        this(((BigInteger)serializable).toString(10));
        if (n >= 0) {
            this.exp = -n;
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Negative scale: ");
        ((StringBuilder)serializable).append(n);
        throw new NumberFormatException(((StringBuilder)serializable).toString());
    }

    public BigDecimal(char[] arrc) {
        this(arrc, 0, arrc.length);
    }

    public BigDecimal(char[] arrc, int n, int n2) {
        int n3;
        int n4;
        int n5;
        byte[] arrby;
        int n6;
        int n7;
        int n8;
        boolean bl;
        block51 : {
            int n9;
            this.form = (byte)(false ? 1 : 0);
            n7 = 0;
            if (n2 <= 0) {
                this.bad(arrc);
            }
            this.ind = (byte)(true ? 1 : 0);
            if (arrc[n] == '-') {
                if (--n2 == 0) {
                    this.bad(arrc);
                }
                this.ind = (byte)-1;
                ++n;
            } else if (arrc[n] == '+') {
                if (--n2 == 0) {
                    this.bad(arrc);
                }
                ++n;
            }
            bl = false;
            int n10 = 0;
            n5 = 0;
            n3 = -1;
            n6 = -1;
            int n11 = n2;
            n8 = n;
            do {
                n4 = n7;
                n7 = n10;
                if (n11 <= 0) break block51;
                n9 = arrc[n8];
                if (n9 >= 48 && n9 <= 57) {
                    ++n5;
                    n4 = n8;
                } else if (n9 == 46) {
                    if (n3 >= 0) {
                        this.bad(arrc);
                    }
                    n3 = n8 - n;
                    n4 = n6;
                } else {
                    if (n9 == 101 || n9 == 69) break;
                    if (!UCharacter.isDigit(n9)) {
                        this.bad(arrc);
                    }
                    ++n5;
                    bl = true;
                    n4 = n8;
                }
                --n11;
                ++n8;
                n7 = n9;
                n6 = n4;
            } while (true);
            if (n8 - n > n2 - 2) {
                this.bad(arrc);
            }
            if (arrc[n8 + 1] == '-') {
                n8 += 2;
                n4 = 1;
            } else if (arrc[n8 + 1] == '+') {
                n8 += 2;
                n4 = 0;
            } else {
                ++n8;
                n4 = 0;
            }
            n11 = n2 - (n8 - n);
            n2 = n11 == 0 ? 1 : 0;
            n7 = n11 > 9 ? 1 : 0;
            if ((n2 | n7) != 0) {
                this.bad(arrc);
            }
            n7 = n11;
            n2 = n8;
            n8 = n7;
            while (n8 > 0) {
                n7 = arrc[n2];
                if (n7 < 48) {
                    this.bad(arrc);
                }
                if (n7 > 57) {
                    if (!UCharacter.isDigit(n7)) {
                        this.bad(arrc);
                    }
                    n7 = n11 = UCharacter.digit(n7, 10);
                    if (n11 < 0) {
                        this.bad(arrc);
                        n7 = n11;
                    }
                } else {
                    n7 -= 48;
                }
                this.exp = this.exp * 10 + n7;
                --n8;
                ++n2;
            }
            if (n4 != 0) {
                this.exp = -this.exp;
            }
            n7 = 1;
            n4 = n9;
        }
        if (n5 == 0) {
            this.bad(arrc);
        }
        if (n3 >= 0) {
            this.exp = this.exp + n3 - n5;
        }
        n8 = n;
        n2 = n;
        n = n5;
        n5 = n8;
        do {
            n8 = n4;
            if (n5 > n6 - 1) break;
            n4 = arrc[n5];
            if (n4 == 48) {
                ++n2;
                n8 = n3 - 1;
                --n;
            } else if (n4 == 46) {
                ++n2;
                n8 = n3 - 1;
            } else {
                if (n4 <= 57) {
                    n8 = n4;
                    break;
                }
                if (UCharacter.digit(n4, 10) != 0) {
                    n8 = n4;
                    break;
                }
                ++n2;
                n8 = n3 - 1;
                --n;
            }
            ++n5;
            n3 = n8;
        } while (true);
        this.mant = new byte[n];
        if (bl) {
            n4 = 0;
            n5 = n;
            n = n4;
            while (n5 > 0) {
                n4 = n2;
                if (n == n3) {
                    n4 = n2 + 1;
                }
                if ((n2 = arrc[n4]) <= 57) {
                    this.mant[n] = (byte)(n2 - 48);
                } else {
                    if ((n2 = UCharacter.digit(n2, 10)) < 0) {
                        this.bad(arrc);
                    }
                    this.mant[n] = (byte)n2;
                }
                n2 = n4 + 1;
                --n5;
                ++n;
            }
        } else {
            n8 = 0;
            while (n > 0) {
                n5 = n2;
                if (n8 == n3) {
                    n5 = n2 + 1;
                }
                this.mant[n8] = (byte)(arrc[n5] - 48);
                n2 = n5 + 1;
                --n;
                ++n8;
            }
        }
        if ((arrby = this.mant)[0] == 0) {
            this.ind = (byte)(false ? 1 : 0);
            if (this.exp > 0) {
                this.exp = 0;
            }
            if (n7 != 0) {
                this.mant = BigDecimal.ZERO.mant;
                this.exp = 0;
            }
        } else if (n7 != 0) {
            n2 = 1;
            this.form = (byte)(true ? 1 : 0);
            n8 = this.exp + arrby.length - 1;
            n = n8 < -999999999 ? 1 : 0;
            if (n8 <= 999999999) {
                n2 = 0;
            }
            if ((n | n2) != 0) {
                this.bad(arrc);
            }
        }
    }

    private static final boolean allzero(byte[] arrby, int n) {
        int n2 = n;
        if (n < 0) {
            n2 = 0;
        }
        n = arrby.length;
        while (n2 <= n - 1) {
            if (arrby[n2] != 0) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    private void bad(char[] arrc) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a number: ");
        stringBuilder.append(String.valueOf(arrc));
        throw new NumberFormatException(stringBuilder.toString());
    }

    private void badarg(String string, int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad argument ");
        stringBuilder.append(n);
        stringBuilder.append(" to ");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static final byte[] byteaddsub(byte[] arrby, int n, byte[] arrby2, int n2, int n3, boolean bl) {
        byte[] arrby3;
        int n4;
        int n5 = arrby.length;
        int n6 = arrby2.length;
        int n7 = n - 1;
        int n8 = n = (n4 = n2 - 1);
        if (n < n7) {
            n8 = n7;
        }
        byte[] arrby4 = arrby3 = null;
        if (bl) {
            arrby4 = arrby3;
            if (n8 + 1 == n5) {
                arrby4 = arrby;
            }
        }
        arrby3 = arrby4;
        if (arrby4 == null) {
            arrby3 = new byte[n8 + 1];
        }
        boolean bl2 = false;
        if (n3 == 1) {
            bl2 = true;
        } else if (n3 == -1) {
            bl2 = true;
        }
        n = 0;
        n2 = n8;
        int n9 = n7;
        for (n7 = n2; n7 >= 0; --n7) {
            int n10 = n9;
            n2 = n;
            if (n9 >= 0) {
                n2 = n;
                if (n9 < n5) {
                    n2 = n + arrby[n9];
                }
                n10 = n9 - 1;
            }
            int n11 = n4;
            n = n2;
            if (n4 >= 0) {
                n = n2;
                if (n4 < n6) {
                    n = bl2 ? (n3 > 0 ? n2 + arrby2[n4] : n2 - arrby2[n4]) : n2 + arrby2[n4] * n3;
                }
                n11 = n4 - 1;
            }
            if (n < 10 && n >= 0) {
                arrby3[n7] = (byte)n;
                n = 0;
            } else {
                arrby3[n7] = bytedig[n += 90];
                n = bytecar[n];
            }
            n9 = n10;
            n4 = n11;
        }
        if (n == 0) {
            return arrby3;
        }
        arrby4 = null;
        arrby2 = arrby4;
        if (bl) {
            arrby2 = arrby4;
            if (n8 + 2 == arrby.length) {
                arrby2 = arrby;
            }
        }
        if (arrby2 == null) {
            arrby2 = new byte[n8 + 2];
        }
        arrby2[0] = (byte)n;
        if (n8 < 10) {
            n2 = n8 + 1;
            n = 0;
            while (n2 > 0) {
                arrby2[n + 1] = arrby3[n];
                --n2;
                ++n;
            }
        } else {
            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)1, (int)(n8 + 1));
        }
        return arrby2;
    }

    private void checkdigits(BigDecimal serializable, int n) {
        if (n == 0) {
            return;
        }
        Object object = this.mant;
        if (((byte[])object).length > n && !BigDecimal.allzero((byte[])object, n)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Too many digits: ");
            ((StringBuilder)serializable).append(this.toString());
            throw new ArithmeticException(((StringBuilder)serializable).toString());
        }
        if (serializable == null) {
            return;
        }
        object = ((BigDecimal)serializable).mant;
        if (((byte[])object).length > n && !BigDecimal.allzero((byte[])object, n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Too many digits: ");
            ((StringBuilder)object).append(((BigDecimal)serializable).toString());
            throw new ArithmeticException(((StringBuilder)object).toString());
        }
    }

    private static final BigDecimal clone(BigDecimal bigDecimal) {
        BigDecimal bigDecimal2 = new BigDecimal();
        bigDecimal2.ind = bigDecimal.ind;
        bigDecimal2.exp = bigDecimal.exp;
        bigDecimal2.form = bigDecimal.form;
        bigDecimal2.mant = bigDecimal.mant;
        return bigDecimal2;
    }

    private static final byte[] diginit() {
        byte[] arrby = new byte[190];
        for (int i = 0; i <= 189; ++i) {
            int n = i - 90;
            if (n >= 0) {
                arrby[i] = (byte)(n % 10);
                BigDecimal.bytecar[i] = (byte)(n / 10);
                continue;
            }
            arrby[i] = (byte)((n += 100) % 10);
            BigDecimal.bytecar[i] = (byte)(n / 10 - 10);
        }
        return arrby;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private BigDecimal dodivide(char var1_1, BigDecimal var2_2, MathContext var3_3, int var4_4) {
        var5_5 = var1_1;
        var6_6 = var4_4;
        var7_7 = false;
        var8_8 = false;
        var9_9 = false;
        var10_10 = false;
        var11_11 = false;
        var12_12 = false;
        if (var3_3.lostDigits) {
            this.checkdigits((BigDecimal)var2_2, var3_3.digits);
        }
        var13_13 /* !! */  = this;
        if (var2_2.ind == 0) throw new ArithmeticException("Divide by 0");
        if (var13_13 /* !! */ .ind == 0) {
            if (var3_3.form != 0) {
                return BigDecimal.ZERO;
            }
            if (var6_6 != -1) return var13_13 /* !! */ .setScale(var6_6);
            return var13_13 /* !! */ ;
        }
        var4_4 = var3_3.digits;
        if (var4_4 > 0) {
            var14_14 = var13_13 /* !! */ ;
            if (var13_13 /* !! */ .mant.length > var4_4) {
                var14_15 = BigDecimal.clone((BigDecimal)var13_13 /* !! */ ).round(var3_3);
            }
            if (var2_2.mant.length > var4_4) {
                var13_13 /* !! */  = BigDecimal.clone((BigDecimal)var2_2).round(var3_3);
                var2_2 = var14_16;
                var15_27 = var4_4;
            } else {
                var13_13 /* !! */  = var2_2;
                var2_2 = var14_16;
                var15_27 = var4_4;
            }
        } else {
            var4_4 = var6_6;
            if (var6_6 == -1) {
                var4_4 = var13_13 /* !! */ .scale();
            }
            var15_27 = var13_13 /* !! */ .mant.length;
            var16_28 = var13_13 /* !! */ .exp;
            var6_6 = var15_27;
            if (var4_4 != -var16_28) {
                var6_6 = var15_27 + var4_4 + var16_28;
            }
            if ((var6_6 = var6_6 - (var2_2.mant.length - 1) - var2_2.exp) < (var14_17 = var13_13 /* !! */ .mant).length) {
                var6_6 = var14_17.length;
            }
            if (var6_6 < (var14_18 = var2_2.mant).length) {
                var15_27 = var14_18.length;
                var14_19 = var2_2;
                var2_2 = var13_13 /* !! */ ;
                var6_6 = var4_4;
                var13_13 /* !! */  = var14_19;
            } else {
                var14_20 = var2_2;
                var2_2 = var13_13 /* !! */ ;
                var15_27 = var6_6;
                var13_13 /* !! */  = var14_20;
                var6_6 = var4_4;
            }
        }
        var4_4 = var2_2.exp - var13_13 /* !! */ .exp + var2_2.mant.length - var13_13 /* !! */ .mant.length;
        if (var4_4 < 0 && var5_5 != 68) {
            if (var5_5 != 73) return BigDecimal.clone((BigDecimal)var2_2).finish(var3_3, false);
            return BigDecimal.ZERO;
        }
        var17_29 = new BigDecimal();
        var17_29.ind = (byte)(var2_2.ind * var13_13 /* !! */ .ind);
        var17_29.exp = var4_4;
        var17_29.mant = new byte[var15_27 + 1];
        var16_28 = var15_27 + var15_27 + 1;
        var14_22 = BigDecimal.extend(var2_2.mant, var16_28);
        var4_4 = var16_28;
        var18_30 = var13_13 /* !! */ .mant;
        var19_31 = var18_30[0] * 10 + 1;
        if (var18_30.length > 1) {
            var19_31 += var18_30[1];
        }
        var20_32 = 0;
        var21_33 = var16_28;
        block0 : do {
            var16_28 = 0;
            do {
                block49 : {
                    block48 : {
                        block42 : {
                            block47 : {
                                block46 : {
                                    block41 : {
                                        block44 : {
                                            block43 : {
                                                if (var4_4 >= var21_33) break block43;
                                                var22_34 = var11_11;
                                                var11_11 = var12_12;
                                                ** GOTO lbl96
                                            }
                                            if (var4_4 == var21_33) break block44;
                                            var22_34 = var11_11;
                                            var11_11 = var12_12;
                                            var5_5 = var14_23[0] * 10;
                                            if (var4_4 > 1) {
                                                var5_5 += var14_23[1];
                                                var12_12 = var22_34;
                                            } else {
                                                var12_12 = var22_34;
                                            }
                                            break block42;
                                        }
                                        var24_36 = 0;
                                        for (var23_35 = var4_4; var23_35 > 0; --var23_35, ++var24_36) {
                                            block45 : {
                                                var22_34 = var11_11;
                                                var25_37 = var24_36 < var18_30.length ? var18_30[var24_36] : 0;
                                                var11_11 = var12_12;
                                                if (var14_23[var24_36] >= var25_37) break block45;
lbl96: // 2 sources:
                                                var23_35 = var20_32 != 0 ? 1 : 0;
                                                var25_37 = var16_28 != 0 ? 1 : 0;
                                                var24_36 = var20_32;
                                                if ((var23_35 | var25_37) != 0) {
                                                    var17_29.mant[var20_32] = (byte)var16_28;
                                                    var24_36 = var20_32 + 1;
                                                    if (var24_36 == var15_27 + 1) {
                                                        var1_1 = var24_36;
                                                        break block41;
                                                    }
                                                    if (var14_23[0] == false) {
                                                        var1_1 = var24_36;
                                                        break block41;
                                                    }
                                                }
                                                if (var6_6 >= 0 && -var17_29.exp > var6_6 || var5_5 != 68 && var17_29.exp <= 0) {
                                                    var1_1 = var24_36;
                                                    break block41;
                                                }
                                                --var17_29.exp;
                                                --var21_33;
                                                var12_12 = var11_11;
                                                var11_11 = var22_34;
                                                var20_32 = var24_36;
                                                continue block0;
                                            }
                                            if (var14_23[var24_36] > var25_37) {
                                                var5_5 = var14_23[0];
                                                var12_12 = var22_34;
                                                break block42;
                                            }
                                            var12_12 = var11_11;
                                            var11_11 = var22_34;
                                        }
                                        var17_29.mant[var20_32] = (byte)(++var16_28);
                                        var14_23[0] = (byte)(false ? 1 : 0);
                                        var1_1 = var20_32 + 1;
                                        var11_11 = var12_12;
                                    }
                                    var24_36 = var1_1;
                                    if (var1_1 == 0) {
                                        var24_36 = 1;
                                    }
                                    if (((var1_1 = var5_5 == 73 ? 1 : 0) | (var19_31 = var5_5 == 82 ? 1 : 0)) == 0) break block46;
                                    var1_1 = var17_29.exp;
                                    if (var24_36 + var1_1 > var15_27) throw new ArithmeticException("Integer overflow");
                                    if (var5_5 != 82) break block47;
                                    if (var17_29.mant[0] == 0) {
                                        return BigDecimal.clone((BigDecimal)var2_2).finish(var3_3, false);
                                    }
                                    if (var14_23[0] == false) {
                                        return BigDecimal.ZERO;
                                    }
                                    var17_29.ind = var2_2.ind;
                                    var6_6 = var15_27 + var15_27 + 1 - var2_2.mant.length;
                                    var17_29.exp = var1_1 - var6_6 + var2_2.exp;
                                    break block48;
                                }
                                if (var14_23[0] != false && (var1_1 = (var2_2 = var17_29.mant)[var24_36 - 1]) % 5 == 0) {
                                    var2_2[var24_36 - 1] = (byte)(var1_1 + 1);
                                }
                            }
                            if (var6_6 >= 0) {
                                var2_2 = var17_29.mant;
                                if (var24_36 != var2_2.length) {
                                    var17_29.exp -= var2_2.length - var24_36;
                                }
                                var17_29.round(var17_29.mant.length - (-var17_29.exp - var6_6), var3_3.roundingMode);
                                if (var17_29.exp == -var6_6) return var17_29.finish(var3_3, true);
                                var2_2 = var17_29.mant;
                                var17_29.mant = BigDecimal.extend(var2_2, var2_2.length + 1);
                                --var17_29.exp;
                                return var17_29.finish(var3_3, true);
                            }
                            var2_2 = var17_29.mant;
                            if (var24_36 == var2_2.length) {
                                var17_29.round(var3_3);
                                return var17_29.finish(var3_3, true);
                            }
                            if (var2_2[0] == 0) {
                                return BigDecimal.ZERO;
                            }
                            var13_13 /* !! */  = new byte[var24_36];
                            System.arraycopy((byte[])var2_2, (int)0, (byte[])var13_13 /* !! */ , (int)0, (int)var24_36);
                            var17_29.mant = var13_13 /* !! */ ;
                            return var17_29.finish(var3_3, true);
                        }
                        var5_5 = var24_36 = var5_5 * 10 / var19_31;
                        if (var24_36 == 0) {
                            var5_5 = 1;
                        }
                        var23_35 = var16_28 + var5_5;
                        var14_26 = BigDecimal.byteaddsub((byte[])var14_23, var4_4, var18_30, var21_33, -var5_5, true);
                        if (var14_26[0] != 0) {
                            var5_5 = var1_1;
                            var22_34 = var12_12;
                            var12_12 = var11_11;
                            var16_28 = var23_35;
                            var11_11 = var22_34;
                            continue;
                        }
                        break block49;
                    }
                    for (var1_1 = var4_4 - 1; var1_1 >= 1 && ((var15_27 = var17_29.exp < var2_2.exp ? 1 : 0) & (var5_5 = var17_29.exp < var13_13 /* !! */ .exp ? 1 : 0)) != 0 && var14_23[var1_1] == false; --var4_4, ++var17_29.exp, --var1_1) {
                    }
                    if (var4_4 < ((void)var14_23).length) {
                        var2_2 = new byte[var4_4];
                        System.arraycopy((byte[])var14_23, (int)0, (byte[])var2_2, (int)0, (int)var4_4);
                        var14_24 = var2_2;
                    }
                    var17_29.mant = var14_25;
                    return var17_29.finish(var3_3, false);
                }
                var24_36 = 0;
                var5_5 = var4_4;
                do {
                    var16_28 = var5_5;
                    if (var24_36 > var4_4 - 2 || var14_26[var24_36] != 0) break;
                    var5_5 = var16_28 - 1;
                    ++var24_36;
                } while (true);
                if (var24_36 != 0) {
                    System.arraycopy((byte[])var14_26, (int)var24_36, (byte[])var14_26, (int)0, (int)var16_28);
                }
                var5_5 = var1_1;
                var22_34 = var12_12;
                var12_12 = var11_11;
                var4_4 = var16_28;
                var16_28 = var23_35;
                var11_11 = var22_34;
            } while (true);
            break;
        } while (true);
    }

    private static final byte[] extend(byte[] arrby, int n) {
        if (arrby.length == n) {
            return arrby;
        }
        byte[] arrby2 = new byte[n];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby.length);
        return arrby2;
    }

    private BigDecimal finish(MathContext serializable, boolean bl) {
        block26 : {
            block24 : {
                int n;
                block25 : {
                    block23 : {
                        byte[] arrby;
                        int n2;
                        byte[] arrby2;
                        if (((MathContext)serializable).digits != 0 && this.mant.length > ((MathContext)serializable).digits) {
                            this.round((MathContext)serializable);
                        }
                        int n3 = 1;
                        if (bl && ((MathContext)serializable).form != 0) {
                            n2 = this.mant.length;
                            for (n = n2 - 1; n >= 1 && this.mant[n] == 0; --n) {
                                --n2;
                                ++this.exp;
                            }
                            arrby2 = this.mant;
                            if (n2 < arrby2.length) {
                                arrby = new byte[n2];
                                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)n2);
                                this.mant = arrby;
                            }
                        }
                        this.form = (byte)(false ? 1 : 0);
                        n2 = this.mant.length;
                        n = 0;
                        while (n2 > 0) {
                            block20 : {
                                block21 : {
                                    block22 : {
                                        int n4;
                                        arrby2 = this.mant;
                                        if (arrby2[n] == 0) break block20;
                                        if (n > 0) {
                                            arrby = new byte[arrby2.length - n];
                                            System.arraycopy((byte[])arrby2, (int)n, (byte[])arrby, (int)0, (int)(arrby2.length - n));
                                            this.mant = arrby;
                                        }
                                        if ((n = this.exp + this.mant.length) > 0) {
                                            if (n > ((MathContext)serializable).digits && ((MathContext)serializable).digits != 0) {
                                                this.form = (byte)((MathContext)serializable).form;
                                            }
                                            if (n - 1 <= 999999999) {
                                                return this;
                                            }
                                        } else if (n < -5) {
                                            this.form = (byte)((MathContext)serializable).form;
                                        }
                                        if (((n2 = n4 > 999999999 ? n3 : 0) | (n = (n4 = n - 1) < -999999999 ? 1 : 0)) == 0) break block21;
                                        n = n4;
                                        if (this.form != 2) break block22;
                                        n = n2 = n4 % 3;
                                        if (n2 < 0) {
                                            n = n2 + 3;
                                        }
                                        n = n2 = n4 - n;
                                        if (n2 < -999999999) break block22;
                                        n = n2;
                                        if (n2 <= 999999999) break block21;
                                    }
                                    serializable = new StringBuilder();
                                    ((StringBuilder)serializable).append("Exponent Overflow: ");
                                    ((StringBuilder)serializable).append(n);
                                    throw new ArithmeticException(((StringBuilder)serializable).toString());
                                }
                                return this;
                            }
                            --n2;
                            ++n;
                        }
                        this.ind = (byte)(false ? 1 : 0);
                        if (((MathContext)serializable).form == 0) break block23;
                        this.exp = 0;
                        break block24;
                    }
                    n = this.exp;
                    if (n <= 0) break block25;
                    this.exp = 0;
                    break block24;
                }
                if (n < -999999999) break block26;
            }
            this.mant = BigDecimal.ZERO.mant;
            return this;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Exponent Overflow: ");
        ((StringBuilder)serializable).append(this.exp);
        throw new ArithmeticException(((StringBuilder)serializable).toString());
    }

    private int intcheck(int n, int n2) {
        int n3 = this.intValueExact();
        int n4 = 1;
        if (((n2 = n3 > n2 ? n4 : 0) | (n = n3 < n ? 1 : 0)) == 0) {
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conversion overflow: ");
        stringBuilder.append(n3);
        throw new ArithmeticException(stringBuilder.toString());
    }

    private char[] layout() {
        Object object = this.mant;
        char[] arrc = new char[((byte[])object).length];
        int n = ((byte[])object).length;
        int n2 = 0;
        while (n > 0) {
            arrc[n2] = (char)(this.mant[n2] + 48);
            --n;
            ++n2;
        }
        if (this.form != 0) {
            object = new StringBuilder(arrc.length + 15);
            if (this.ind == -1) {
                ((StringBuilder)object).append('-');
            }
            n2 = this.exp + arrc.length - 1;
            if (this.form == 1) {
                ((StringBuilder)object).append(arrc[0]);
                n = n2;
                if (arrc.length > 1) {
                    ((StringBuilder)object).append('.');
                    ((StringBuilder)object).append(arrc, 1, arrc.length - 1);
                    n = n2;
                }
            } else {
                int n3;
                n = n3 = n2 % 3;
                if (n3 < 0) {
                    n = n3 + 3;
                }
                n2 -= n;
                if (++n >= arrc.length) {
                    ((StringBuilder)object).append(arrc, 0, arrc.length);
                    n -= arrc.length;
                    while (n > 0) {
                        ((StringBuilder)object).append('0');
                        --n;
                    }
                    n = n2;
                } else {
                    ((StringBuilder)object).append(arrc, 0, n);
                    ((StringBuilder)object).append('.');
                    ((StringBuilder)object).append(arrc, n, arrc.length - n);
                    n = n2;
                }
            }
            if (n != 0) {
                int n4;
                if (n < 0) {
                    n = -n;
                    n4 = n2 = 45;
                } else {
                    n4 = n2 = 43;
                }
                ((StringBuilder)object).append('E');
                ((StringBuilder)object).append((char)n4);
                ((StringBuilder)object).append(n);
            }
            arrc = new char[((StringBuilder)object).length()];
            n = ((StringBuilder)object).length();
            if (n != 0) {
                ((StringBuilder)object).getChars(0, n, arrc, 0);
            }
            return arrc;
        }
        if (this.exp == 0) {
            if (this.ind >= 0) {
                return arrc;
            }
            object = new char[arrc.length + 1];
            object[0] = (char)45;
            System.arraycopy(arrc, 0, object, 1, arrc.length);
            return object;
        }
        n = this.ind == -1 ? 1 : 0;
        n2 = this.exp;
        int n5 = arrc.length + n2;
        if (n5 < 1) {
            object = new char[n + 2 - n2];
            if (n != 0) {
                object[0] = (char)45;
            }
            object[n] = (char)48;
            object[n + 1] = (char)46;
            int n6 = -n5;
            n2 = n + 2;
            while (n6 > 0) {
                object[n2] = (char)48;
                --n6;
                ++n2;
            }
            System.arraycopy(arrc, 0, object, n + 2 - n5, arrc.length);
            return object;
        }
        if (n5 > arrc.length) {
            object = new char[n + n5];
            if (n != 0) {
                object[0] = (char)45;
            }
            System.arraycopy(arrc, 0, object, n, arrc.length);
            n2 = n5 - arrc.length;
            n = arrc.length + n;
            while (n2 > 0) {
                object[n] = (char)48;
                --n2;
                ++n;
            }
            return object;
        }
        object = new char[n + 1 + arrc.length];
        if (n != 0) {
            object[0] = (char)45;
        }
        System.arraycopy(arrc, 0, object, n, n5);
        object[n + n5] = (char)46;
        System.arraycopy(arrc, n5, object, n + n5 + 1, arrc.length - n5);
        return object;
    }

    private BigDecimal round(int n, int n2) {
        Object object;
        block39 : {
            boolean bl;
            block31 : {
                int n3;
                int n4;
                block38 : {
                    block37 : {
                        block36 : {
                            block35 : {
                                byte by;
                                block34 : {
                                    block32 : {
                                        block33 : {
                                            block30 : {
                                                n4 = this.mant.length - n;
                                                if (n4 <= 0) {
                                                    return this;
                                                }
                                                this.exp += n4;
                                                n4 = this.ind;
                                                object = this.mant;
                                                if (n > 0) {
                                                    this.mant = new byte[n];
                                                    System.arraycopy((byte[])object, (int)0, (byte[])this.mant, (int)0, (int)n);
                                                    bl = true;
                                                    by = object[n];
                                                } else {
                                                    this.mant = BigDecimal.ZERO.mant;
                                                    this.ind = (byte)(false ? 1 : 0);
                                                    bl = false;
                                                    by = n == 0 ? object[0] : (byte)0;
                                                }
                                                n3 = 0;
                                                if (n2 != 4) break block30;
                                                n2 = n3;
                                                if (by >= 5) {
                                                    n2 = n4;
                                                }
                                                break block31;
                                            }
                                            if (n2 != 7) break block32;
                                            if (!BigDecimal.allzero((byte[])object, n)) break block33;
                                            n2 = n3;
                                            break block31;
                                        }
                                        throw new ArithmeticException("Rounding necessary");
                                    }
                                    if (n2 != 5) break block34;
                                    if (by > 5) {
                                        n2 = n4;
                                    } else {
                                        n2 = n3;
                                        if (by == 5) {
                                            n2 = n3;
                                            if (!BigDecimal.allzero((byte[])object, n + 1)) {
                                                n2 = n4;
                                            }
                                        }
                                    }
                                    break block31;
                                }
                                if (n2 != 6) break block35;
                                if (by > 5) {
                                    n2 = n4;
                                } else {
                                    n2 = n3;
                                    if (by == 5) {
                                        if (!BigDecimal.allzero((byte[])object, n + 1)) {
                                            n2 = n4;
                                        } else {
                                            object = this.mant;
                                            n2 = n3;
                                            if (object[((Object)object).length - 1] % 2 != false) {
                                                n2 = n4;
                                            }
                                        }
                                    }
                                }
                                break block31;
                            }
                            if (n2 != 1) break block36;
                            n2 = n3;
                            break block31;
                        }
                        if (n2 != 0) break block37;
                        n2 = n3;
                        if (!BigDecimal.allzero((byte[])object, n)) {
                            n2 = n4;
                        }
                        break block31;
                    }
                    if (n2 != 2) break block38;
                    n2 = n3;
                    if (n4 > 0) {
                        n2 = n3;
                        if (!BigDecimal.allzero((byte[])object, n)) {
                            n2 = n4;
                        }
                    }
                    break block31;
                }
                if (n2 != 3) break block39;
                n2 = n3;
                if (n4 < 0) {
                    n2 = n3;
                    if (!BigDecimal.allzero((byte[])object, n)) {
                        n2 = n4;
                    }
                }
            }
            if (n2 != 0) {
                n = this.ind;
                if (n == 0) {
                    this.mant = BigDecimal.ONE.mant;
                    this.ind = (byte)n2;
                } else {
                    n = n == -1 ? -n2 : n2;
                    object = this.mant;
                    byte[] arrby = BigDecimal.byteaddsub((byte[])object, ((Object)object).length, BigDecimal.ONE.mant, 1, n, bl);
                    n = arrby.length;
                    object = this.mant;
                    if (n > ((Object)object).length) {
                        ++this.exp;
                        System.arraycopy((byte[])arrby, (int)0, (byte[])object, (int)0, (int)((Object)object).length);
                    } else {
                        this.mant = arrby;
                    }
                }
            }
            if (this.exp <= 999999999) {
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Exponent Overflow: ");
            ((StringBuilder)object).append(this.exp);
            throw new ArithmeticException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad round value: ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private BigDecimal round(MathContext mathContext) {
        return this.round(mathContext.digits, mathContext.roundingMode);
    }

    public static BigDecimal valueOf(double d) {
        return new BigDecimal(new Double(d).toString());
    }

    public static BigDecimal valueOf(long l) {
        return BigDecimal.valueOf(l, 0);
    }

    public static BigDecimal valueOf(long l, int n) {
        Serializable serializable = l == 0L ? ZERO : (l == 1L ? ONE : (l == 10L ? TEN : new BigDecimal(l)));
        if (n == 0) {
            return serializable;
        }
        if (n >= 0) {
            serializable = BigDecimal.clone((BigDecimal)serializable);
            ((BigDecimal)serializable).exp = -n;
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Negative scale: ");
        ((StringBuilder)serializable).append(n);
        throw new NumberFormatException(((StringBuilder)serializable).toString());
    }

    public BigDecimal abs() {
        return this.abs(plainMC);
    }

    public BigDecimal abs(MathContext mathContext) {
        if (this.ind == -1) {
            return this.negate(mathContext);
        }
        return this.plus(mathContext);
    }

    public BigDecimal add(BigDecimal bigDecimal) {
        return this.add(bigDecimal, plainMC);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public BigDecimal add(BigDecimal var1_1, MathContext var2_2) {
        block28 : {
            block26 : {
                block27 : {
                    block25 : {
                        var3_3 = var1_1;
                        if (var2_2.lostDigits) {
                            this.checkdigits((BigDecimal)var3_3, var2_2.digits);
                        }
                        var4_4 = this;
                        if (var4_4.ind == 0 && var2_2.form != 0) {
                            return var1_1.plus(var2_2);
                        }
                        if (var3_3.ind == 0 && var2_2.form != 0) {
                            return var4_4.plus(var2_2);
                        }
                        var5_5 = var2_2.digits;
                        var6_6 = var3_3;
                        var7_10 = var4_4;
                        if (var5_5 > 0) {
                            var8_11 = var4_4;
                            if (var4_4.mant.length > var5_5) {
                                var8_11 = BigDecimal.clone((BigDecimal)var4_4).round(var2_2);
                            }
                            var6_7 = var3_3;
                            var7_10 = var8_11;
                            if (var3_3.mant.length > var5_5) {
                                var6_8 = BigDecimal.clone((BigDecimal)var1_1).round(var2_2);
                                var7_10 = var8_11;
                            }
                        }
                        var3_3 = new BigDecimal();
                        var1_1 = var7_10.mant;
                        var9_12 = var7_10.mant.length;
                        var8_11 = var6_9.mant;
                        var10_13 = var6_9.mant.length;
                        var11_14 = var7_10.exp;
                        var12_15 = var6_9.exp;
                        if (var11_14 == var12_15) {
                            var3_3.exp = var11_14;
                            var12_15 = var9_12;
                            var5_5 = var10_13;
                        } else if (var11_14 > var12_15) {
                            if ((var12_15 = var9_12 + var11_14 - var12_15) >= var10_13 + var5_5 + 1 && var5_5 > 0) {
                                var3_3.mant = var1_1;
                                var3_3.exp = var11_14;
                                var3_3.ind = var7_10.ind;
                                if (var9_12 >= var5_5) return BigDecimal.super.finish(var2_2, false);
                                var3_3.mant = BigDecimal.extend(var7_10.mant, var5_5);
                                var3_3.exp -= var5_5 - var9_12;
                                return BigDecimal.super.finish(var2_2, false);
                            }
                            var3_3.exp = var6_9.exp;
                            var13_16 = var10_13;
                            var11_14 = var12_15;
                            if (var12_15 > var5_5 + 1) {
                                var13_16 = var10_13;
                                var11_14 = var12_15;
                                if (var5_5 > 0) {
                                    var12_15 = var12_15 - var5_5 - 1;
                                    var13_16 = var10_13 - var12_15;
                                    var3_3.exp += var12_15;
                                    var11_14 = var5_5 + 1;
                                }
                            }
                            var12_15 = var9_12;
                            var5_5 = var13_16;
                            if (var11_14 > var9_12) {
                                var12_15 = var11_14;
                                var5_5 = var13_16;
                            }
                        } else {
                            var13_16 = var10_13 + var12_15 - var11_14;
                            if (var13_16 >= var9_12 + var5_5 + 1 && var5_5 > 0) {
                                var3_3.mant = var8_11;
                                var3_3.exp = var12_15;
                                var3_3.ind = var6_9.ind;
                                if (var10_13 >= var5_5) return BigDecimal.super.finish(var2_2, false);
                                var3_3.mant = BigDecimal.extend(var6_9.mant, var5_5);
                                var3_3.exp -= var5_5 - var10_13;
                                return BigDecimal.super.finish(var2_2, false);
                            }
                            var3_3.exp = var7_10.exp;
                            if (var13_16 > var5_5 + 1 && var5_5 > 0) {
                                var12_15 = var13_16 - var5_5 - 1;
                                var11_14 = var9_12 - var12_15;
                                var3_3.exp += var12_15;
                                var13_16 = var5_5 + 1;
                            } else {
                                var11_14 = var9_12;
                            }
                            var12_15 = var11_14;
                            var5_5 = var10_13;
                            if (var13_16 > var10_13) {
                                var5_5 = var13_16;
                                var12_15 = var11_14;
                            }
                        }
                        var11_14 = var7_10.ind;
                        var3_3.ind = var11_14 == 0 ? (byte)(true ? 1 : 0) : (byte)var11_14;
                        var11_14 = var7_10.ind == -1 ? 1 : 0;
                        var13_16 = var6_9.ind == -1 ? 1 : 0;
                        if (var11_14 != var13_16) break block25;
                        var11_14 = 1;
                        var4_4 = var1_1;
                        var13_16 = var12_15;
                        var7_10 = var8_11;
                        var10_13 = var5_5;
                        ** GOTO lbl149
                    }
                    if (var6_9.ind == 0) break block26;
                    var11_14 = var12_15 < var5_5 ? 1 : 0;
                    if ((var11_14 | (var13_16 = var7_10.ind == 0 ? 1 : 0)) == 0) break block27;
                    var3_3.ind = -var3_3.ind;
                    var11_14 = -1;
                    var4_4 = var8_11;
                    var13_16 = var5_5;
                    var7_10 = var1_1;
                    var10_13 = var12_15;
                    ** GOTO lbl149
                }
                if (var12_15 <= var5_5) break block28;
            }
            var11_14 = -1;
            var4_4 = var1_1;
            var13_16 = var12_15;
            var7_10 = var8_11;
            var10_13 = var5_5;
            ** GOTO lbl149
        }
        var10_13 = 0;
        var11_14 = 0;
        var13_16 = var1_1.length - 1;
        var14_17 = -1;
        var15_18 = var8_11.length - 1;
        do {
            block33 : {
                block32 : {
                    block30 : {
                        block31 : {
                            block29 : {
                                if (var10_13 > var13_16) break block29;
                                var9_12 = var1_1[var10_13];
                                break block30;
                            }
                            if (var11_14 <= var15_18) break block31;
                            var4_4 = var1_1;
                            var13_16 = var12_15;
                            var7_10 = var8_11;
                            var10_13 = var5_5;
                            var11_14 = var14_17;
                            if (var2_2.form != 0) {
                                return BigDecimal.ZERO;
                            }
                            break block32;
                        }
                        var9_12 = 0;
                    }
                    var16_19 = var11_14 <= var15_18 ? var8_11[var11_14] : 0;
                    if (var9_12 == var16_19) break block33;
                    var4_4 = var1_1;
                    var13_16 = var12_15;
                    var7_10 = var8_11;
                    var10_13 = var5_5;
                    var11_14 = var14_17;
                    if (var9_12 < var16_19) {
                        var3_3.ind = -var3_3.ind;
                        var11_14 = var14_17;
                        var10_13 = var12_15;
                        var7_10 = var1_1;
                        var13_16 = var5_5;
                        var4_4 = var8_11;
                    }
                }
                var3_3.mant = BigDecimal.byteaddsub(var4_4, var13_16, var7_10, var10_13, var11_14, false);
                return BigDecimal.super.finish(var2_2, false);
            }
            ++var10_13;
            ++var11_14;
        } while (true);
    }

    public byte byteValueExact() {
        int n = this.intValueExact();
        boolean bl = true;
        boolean bl2 = n > 127;
        if (n >= -128) {
            bl = false;
        }
        if (!(bl | bl2)) {
            return (byte)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conversion overflow: ");
        stringBuilder.append(this.toString());
        throw new ArithmeticException(stringBuilder.toString());
    }

    @Override
    public int compareTo(BigDecimal bigDecimal) {
        return this.compareTo(bigDecimal, plainMC);
    }

    public int compareTo(BigDecimal bigDecimal, MathContext arrby) {
        if (arrby.lostDigits) {
            this.checkdigits(bigDecimal, arrby.digits);
        }
        int n = this.ind;
        int n2 = bigDecimal.ind;
        int n3 = 1;
        n = n == n2 ? 1 : 0;
        n2 = this.exp == bigDecimal.exp ? 1 : 0;
        if ((n & n2) != 0) {
            int n4 = this.mant.length;
            byte[] arrby2 = bigDecimal.mant;
            if (n4 < arrby2.length) {
                return -this.ind;
            }
            if (n4 > arrby2.length) {
                return this.ind;
            }
            n = n4 <= arrby.digits ? 1 : 0;
            if ((n | (n2 = arrby.digits == 0 ? n3 : 0)) != 0) {
                n2 = n4;
                n = 0;
                while (n2 > 0) {
                    arrby = this.mant;
                    n3 = arrby[n];
                    arrby2 = bigDecimal.mant;
                    if (n3 < arrby2[n]) {
                        return -this.ind;
                    }
                    if (arrby[n] > arrby2[n]) {
                        return this.ind;
                    }
                    --n2;
                    ++n;
                }
                return 0;
            }
        } else {
            n2 = this.ind;
            n = bigDecimal.ind;
            if (n2 < n) {
                return -1;
            }
            if (n2 > n) {
                return 1;
            }
        }
        bigDecimal = BigDecimal.clone(bigDecimal);
        bigDecimal.ind = -bigDecimal.ind;
        return this.add((BigDecimal)bigDecimal, (MathContext)arrby).ind;
    }

    public BigDecimal divide(BigDecimal bigDecimal) {
        return this.dodivide('D', bigDecimal, plainMC, -1);
    }

    public BigDecimal divide(BigDecimal bigDecimal, int n) {
        return this.dodivide('D', bigDecimal, new MathContext(0, 0, false, n), -1);
    }

    public BigDecimal divide(BigDecimal serializable, int n, int n2) {
        if (n >= 0) {
            return this.dodivide('D', (BigDecimal)serializable, new MathContext(0, 0, false, n2), n);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Negative scale: ");
        ((StringBuilder)serializable).append(n);
        throw new ArithmeticException(((StringBuilder)serializable).toString());
    }

    public BigDecimal divide(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('D', bigDecimal, mathContext, -1);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal) {
        return this.dodivide('I', bigDecimal, plainMC, 0);
    }

    public BigDecimal divideInteger(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('I', bigDecimal, mathContext, 0);
    }

    @Override
    public double doubleValue() {
        return Double.valueOf(this.toString());
    }

    public boolean equals(Object arrc) {
        int n;
        boolean bl;
        if (arrc == null) {
            return false;
        }
        if (!(arrc instanceof BigDecimal)) {
            return false;
        }
        char[] arrc2 = arrc;
        if (this.ind != arrc2.ind) {
            return false;
        }
        int n2 = this.mant.length == arrc2.mant.length ? 1 : 0;
        if (n2 & (n = this.exp == arrc2.exp ? 1 : 0) & (bl = this.form == arrc2.form)) {
            n2 = this.mant.length;
            n = 0;
            while (n2 > 0) {
                if (this.mant[n] != arrc2.mant[n]) {
                    return false;
                }
                --n2;
                ++n;
            }
        } else {
            arrc = this.layout();
            if (arrc.length != (arrc2 = arrc2.layout()).length) {
                return false;
            }
            n = arrc.length;
            n2 = 0;
            while (n > 0) {
                if (arrc[n2] != arrc2[n2]) {
                    return false;
                }
                --n;
                ++n2;
            }
        }
        return true;
    }

    @Override
    public float floatValue() {
        return Float.valueOf(this.toString()).floatValue();
    }

    public String format(int n, int n2) {
        return this.format(n, n2, -1, -1, 1, 4);
    }

    public String format(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        Object object;
        int n8 = n < -1 ? 1 : 0;
        if (n8 | (n7 = n == 0 ? 1 : 0)) {
            this.badarg("format", 1, String.valueOf(n));
        }
        if (n2 < -1) {
            this.badarg("format", 2, String.valueOf(n2));
        }
        if ((n8 = n3 < -1 ? 1 : 0) | (n7 = n3 == 0 ? 1 : 0)) {
            this.badarg("format", 3, String.valueOf(n3));
        }
        if (n4 < -1) {
            this.badarg("format", 4, String.valueOf(n3));
        }
        if (n5 != 1 && n5 != 2) {
            if (n5 == -1) {
                n5 = 1;
            } else {
                this.badarg("format", 5, String.valueOf(n5));
            }
        }
        if (n6 != 4) {
            if (n6 == -1) {
                n6 = 4;
            } else {
                try {
                    new MathContext(9, 1, false, n6);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    this.badarg("format", 6, String.valueOf(n6));
                }
            }
        }
        char[] arrc = BigDecimal.clone(this);
        arrc.form = n4 == -1 ? (byte)(false ? 1 : 0) : (arrc.ind == 0 ? (byte)(false ? 1 : 0) : ((n8 = arrc.exp + arrc.mant.length) > n4 ? (byte)((byte)n5) : (n8 < -5 ? (byte)((byte)n5) : (byte)(false ? 1 : 0))));
        if (n2 >= 0) {
            n4 = n5;
            do {
                if ((n5 = (int)arrc.form) == 0) {
                    n5 = -arrc.exp;
                } else if (n5 == 1) {
                    n5 = arrc.mant.length - 1;
                } else {
                    n5 = n8 = (arrc.exp + arrc.mant.length - 1) % 3;
                    if (n8 < 0) {
                        n5 = n8 + 3;
                    }
                    n5 = ++n5 >= ((char[])(object = arrc.mant)).length ? 0 : ((char[])object).length - n5;
                }
                if (n5 == n2) break;
                if (n5 < n2) {
                    object = arrc.mant;
                    arrc.mant = BigDecimal.extend((byte[])object, ((char[])object).length + n2 - n5);
                    arrc.exp -= n2 - n5;
                    if (arrc.exp < -999999999) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Exponent Overflow: ");
                        ((StringBuilder)object).append(arrc.exp);
                        throw new ArithmeticException(((StringBuilder)object).toString());
                    }
                    break;
                }
                object = arrc.mant;
                if ((n5 -= n2) > ((char[])object).length) {
                    arrc.mant = BigDecimal.ZERO.mant;
                    arrc.ind = (byte)(false ? 1 : 0);
                    arrc.exp = 0;
                    continue;
                }
                n7 = ((char[])object).length;
                n8 = arrc.exp;
                BigDecimal.super.round(n7 - n5, n6);
                if (arrc.exp - n8 == n5) break;
            } while (true);
        }
        object = arrc.layout();
        if (n > 0) {
            n4 = ((char[])object).length;
            n2 = 0;
            while (n4 > 0 && object[n2] != '.' && object[n2] != 'E') {
                --n4;
                ++n2;
            }
            if (n2 > n) {
                this.badarg("format", 1, String.valueOf(n));
            }
            arrc = object;
            if (n2 < n) {
                arrc = new char[((char[])object).length + n - n2];
                n2 = n - n2;
                n = 0;
                while (n2 > 0) {
                    arrc[n] = (char)32;
                    --n2;
                    ++n;
                }
                System.arraycopy(object, 0, arrc, n, ((char[])object).length);
            }
        } else {
            arrc = object;
        }
        if (n3 > 0) {
            n2 = arrc.length - 1;
            n = arrc.length - 1;
            while (n2 > 0 && arrc[n] != 'E') {
                --n2;
                --n;
            }
            if (n == 0) {
                object = new char[arrc.length + n3 + 2];
                System.arraycopy(arrc, 0, object, 0, arrc.length);
                n2 = n3 + 2;
                n = arrc.length;
                while (n2 > 0) {
                    object[n] = (char)32;
                    --n2;
                    ++n;
                }
            } else {
                n4 = arrc.length - n - 2;
                if (n4 > n3) {
                    this.badarg("format", 3, String.valueOf(n3));
                }
                object = arrc;
                if (n4 < n3) {
                    object = new char[arrc.length + n3 - n4];
                    System.arraycopy(arrc, 0, object, 0, n + 2);
                    n3 -= n4;
                    n2 = n + 2;
                    while (n3 > 0) {
                        object[n2] = (char)48;
                        --n3;
                        ++n2;
                    }
                    System.arraycopy(arrc, n + 2, object, n2, n4);
                }
            }
        } else {
            object = arrc;
        }
        return new String((char[])object);
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int intValue() {
        return this.toBigInteger().intValue();
    }

    public int intValueExact() {
        Object object;
        block12 : {
            int n;
            int n2;
            int n3;
            block11 : {
                block9 : {
                    block10 : {
                        if (this.ind == 0) {
                            return 0;
                        }
                        object = this.mant;
                        n2 = ((byte[])object).length - 1;
                        n3 = this.exp;
                        if (n3 >= 0) break block9;
                        if (!BigDecimal.allzero((byte[])object, (n2 += n3) + 1)) break block10;
                        if (n2 < 0) {
                            return 0;
                        }
                        n = 0;
                        break block11;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Decimal part non-zero: ");
                    ((StringBuilder)object).append(this.toString());
                    throw new ArithmeticException(((StringBuilder)object).toString());
                }
                if (n3 + n2 > 9) break block12;
                n = this.exp;
            }
            n3 = 0;
            for (int i = 0; i <= n2 + n; ++i) {
                int n4;
                n3 = n4 = n3 * 10;
                if (i > n2) continue;
                n3 = n4 + this.mant[i];
            }
            if (n2 + n == 9 && (n2 = n3 / 1000000000) != (object = this.mant)[0]) {
                if (n3 == Integer.MIN_VALUE && this.ind == -1 && object[0] == 2) {
                    return n3;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Conversion overflow: ");
                ((StringBuilder)object).append(this.toString());
                throw new ArithmeticException(((StringBuilder)object).toString());
            }
            if (this.ind == 1) {
                return n3;
            }
            return -n3;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Conversion overflow: ");
        ((StringBuilder)object).append(this.toString());
        throw new ArithmeticException(((StringBuilder)object).toString());
    }

    @Override
    public long longValue() {
        return this.toBigInteger().longValue();
    }

    public long longValueExact() {
        Object object;
        block12 : {
            long l;
            int n;
            int n2;
            int n3;
            block11 : {
                block9 : {
                    block10 : {
                        if (this.ind == 0) {
                            return 0L;
                        }
                        object = this.mant;
                        n2 = ((byte[])object).length - 1;
                        n3 = this.exp;
                        if (n3 >= 0) break block9;
                        n = n2 + n3;
                        if (!BigDecimal.allzero(this.mant, n2 = n < 0 ? 0 : n + 1)) break block10;
                        if (n < 0) {
                            return 0L;
                        }
                        n3 = 0;
                        n2 = n;
                        break block11;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Decimal part non-zero: ");
                    ((StringBuilder)object).append(this.toString());
                    throw new ArithmeticException(((StringBuilder)object).toString());
                }
                if (n3 + ((byte[])object).length > 18) break block12;
                n3 = this.exp;
            }
            long l2 = 0L;
            for (n = 0; n <= n2 + n3; ++n) {
                l2 = l = l2 * 10L;
                if (n > n2) continue;
                l2 = l + (long)this.mant[n];
            }
            if (n2 + n3 == 18 && (l = l2 / 1000000000000000000L) != (long)(object = this.mant)[0]) {
                if (l2 == Long.MIN_VALUE && this.ind == -1 && object[0] == 9) {
                    return l2;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Conversion overflow: ");
                ((StringBuilder)object).append(this.toString());
                throw new ArithmeticException(((StringBuilder)object).toString());
            }
            if (this.ind == 1) {
                return l2;
            }
            return -l2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Conversion overflow: ");
        ((StringBuilder)object).append(this.toString());
        throw new ArithmeticException(((StringBuilder)object).toString());
    }

    public BigDecimal max(BigDecimal bigDecimal) {
        return this.max(bigDecimal, plainMC);
    }

    public BigDecimal max(BigDecimal bigDecimal, MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) >= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal min(BigDecimal bigDecimal) {
        return this.min(bigDecimal, plainMC);
    }

    public BigDecimal min(BigDecimal bigDecimal, MathContext mathContext) {
        if (this.compareTo(bigDecimal, mathContext) <= 0) {
            return this.plus(mathContext);
        }
        return bigDecimal.plus(mathContext);
    }

    public BigDecimal movePointLeft(int n) {
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.exp -= n;
        return bigDecimal.finish(plainMC, false);
    }

    public BigDecimal movePointRight(int n) {
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.exp += n;
        return bigDecimal.finish(plainMC, false);
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return this.multiply(bigDecimal, plainMC);
    }

    public BigDecimal multiply(BigDecimal arrby, MathContext mathContext) {
        int n;
        byte[] arrby2;
        byte[] arrby3;
        byte[] arrby4;
        byte[] arrby5 = arrby;
        if (mathContext.lostDigits) {
            this.checkdigits((BigDecimal)arrby5, mathContext.digits);
        }
        Object object = this;
        int n2 = 0;
        int n3 = 0;
        int n4 = mathContext.digits;
        if (n4 > 0) {
            arrby2 = object;
            if (((BigDecimal)object).mant.length > n4) {
                arrby2 = BigDecimal.clone((BigDecimal)object).round(mathContext);
            }
            arrby4 = arrby5;
            arrby3 = arrby2;
            n = n2;
            if (arrby5.mant.length > n4) {
                arrby4 = BigDecimal.clone((BigDecimal)arrby).round(mathContext);
                arrby3 = arrby2;
                n = n2;
            }
        } else {
            n = ((BigDecimal)object).exp;
            if (n > 0) {
                n3 = 0 + n;
            }
            n2 = arrby5.exp;
            arrby4 = arrby5;
            arrby3 = object;
            n = n3;
            if (n2 > 0) {
                n = n3 + n2;
                arrby3 = object;
                arrby4 = arrby5;
            }
        }
        if (arrby3.mant.length < arrby4.mant.length) {
            arrby = arrby3.mant;
            arrby2 = arrby4.mant;
        } else {
            arrby = arrby4.mant;
            arrby2 = arrby3.mant;
        }
        int n5 = arrby.length + arrby2.length - 1;
        boolean bl = false;
        n3 = arrby[0] * arrby2[0] > 9 ? n5 + 1 : n5;
        object = new BigDecimal();
        arrby5 = new byte[n3];
        n4 = arrby.length;
        n2 = 0;
        while (n4 > 0) {
            byte by = arrby[n2];
            if (by != 0) {
                arrby5 = BigDecimal.byteaddsub(arrby5, arrby5.length, arrby2, n5, by, true);
            }
            --n5;
            --n4;
            ++n2;
        }
        ((BigDecimal)object).ind = (byte)(arrby3.ind * arrby4.ind);
        ((BigDecimal)object).exp = arrby3.exp + arrby4.exp - n;
        ((BigDecimal)object).mant = n == 0 ? arrby5 : BigDecimal.extend(arrby5, arrby5.length + n);
        return BigDecimal.super.finish(mathContext, bl);
    }

    public BigDecimal negate() {
        return this.negate(plainMC);
    }

    public BigDecimal negate(MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        BigDecimal bigDecimal = BigDecimal.clone(this);
        bigDecimal.ind = -bigDecimal.ind;
        return bigDecimal.finish(mathContext, false);
    }

    public BigDecimal plus() {
        return this.plus(plainMC);
    }

    public BigDecimal plus(MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(null, mathContext.digits);
        }
        if (mathContext.form == 0 && this.form == 0) {
            if (this.mant.length <= mathContext.digits) {
                return this;
            }
            if (mathContext.digits == 0) {
                return this;
            }
        }
        return BigDecimal.clone(this).finish(mathContext, false);
    }

    public BigDecimal pow(BigDecimal bigDecimal) {
        return this.pow(bigDecimal, plainMC);
    }

    public BigDecimal pow(BigDecimal bigDecimal, MathContext serializable) {
        block15 : {
            BigDecimal bigDecimal2;
            BigDecimal bigDecimal3;
            int n;
            int n2;
            BigDecimal bigDecimal4;
            block14 : {
                block12 : {
                    block13 : {
                        if (((MathContext)serializable).lostDigits) {
                            this.checkdigits(bigDecimal, ((MathContext)serializable).digits);
                        }
                        n2 = bigDecimal.intcheck(-999999999, 999999999);
                        bigDecimal4 = this;
                        n = ((MathContext)serializable).digits;
                        if (n != 0) break block12;
                        if (bigDecimal.ind == -1) break block13;
                        n = 0;
                        bigDecimal3 = bigDecimal4;
                        break block14;
                    }
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Negative power: ");
                    ((StringBuilder)serializable).append(bigDecimal.toString());
                    throw new ArithmeticException(((StringBuilder)serializable).toString());
                }
                if (bigDecimal.mant.length + bigDecimal.exp > n) break block15;
                bigDecimal2 = bigDecimal4;
                if (bigDecimal4.mant.length > n) {
                    bigDecimal2 = BigDecimal.clone(bigDecimal4).round((MathContext)serializable);
                }
                n = n + (bigDecimal.mant.length + bigDecimal.exp) + 1;
                bigDecimal3 = bigDecimal2;
            }
            MathContext mathContext = new MathContext(n, ((MathContext)serializable).form, false, ((MathContext)serializable).roundingMode);
            bigDecimal4 = ONE;
            if (n2 == 0) {
                return bigDecimal4;
            }
            n = n2;
            if (n2 < 0) {
                n = -n2;
            }
            boolean bl = false;
            int n3 = 1;
            n2 = n;
            n = n3;
            do {
                n2 += n2;
                bigDecimal2 = bigDecimal4;
                if (n2 < 0) {
                    bl = true;
                    bigDecimal2 = bigDecimal4.multiply(bigDecimal3, mathContext);
                }
                if (n == 31) {
                    bigDecimal4 = bigDecimal2;
                    if (bigDecimal.ind < 0) {
                        bigDecimal4 = ONE.divide(bigDecimal2, mathContext);
                    }
                    return bigDecimal4.finish((MathContext)serializable, true);
                }
                if (bl) {
                    bigDecimal2 = bigDecimal2.multiply(bigDecimal2, mathContext);
                }
                ++n;
                bigDecimal4 = bigDecimal2;
            } while (true);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Too many digits: ");
        ((StringBuilder)serializable).append(bigDecimal.toString());
        throw new ArithmeticException(((StringBuilder)serializable).toString());
    }

    public BigDecimal remainder(BigDecimal bigDecimal) {
        return this.dodivide('R', bigDecimal, plainMC, -1);
    }

    public BigDecimal remainder(BigDecimal bigDecimal, MathContext mathContext) {
        return this.dodivide('R', bigDecimal, mathContext, -1);
    }

    public int scale() {
        int n = this.exp;
        if (n >= 0) {
            return 0;
        }
        return -n;
    }

    public BigDecimal setScale(int n) {
        return this.setScale(n, 7);
    }

    public BigDecimal setScale(int n, int n2) {
        Serializable serializable;
        block7 : {
            block6 : {
                int n3;
                block5 : {
                    n3 = this.scale();
                    if (n3 == n && this.form == 0) {
                        return this;
                    }
                    serializable = BigDecimal.clone(this);
                    if (n3 > n) break block5;
                    n2 = n3 == 0 ? ((BigDecimal)serializable).exp + n : n - n3;
                    byte[] arrby = ((BigDecimal)serializable).mant;
                    ((BigDecimal)serializable).mant = BigDecimal.extend(arrby, arrby.length + n2);
                    ((BigDecimal)serializable).exp = -n;
                    break block6;
                }
                if (n < 0) break block7;
                BigDecimal bigDecimal = ((BigDecimal)serializable).round(((BigDecimal)serializable).mant.length - (n3 - n), n2);
                serializable = bigDecimal;
                if (bigDecimal.exp != -n) {
                    serializable = bigDecimal.mant;
                    bigDecimal.mant = BigDecimal.extend((byte[])serializable, ((Serializable)serializable).length + 1);
                    --bigDecimal.exp;
                    serializable = bigDecimal;
                }
            }
            ((BigDecimal)serializable).form = (byte)(false ? 1 : 0);
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Negative scale: ");
        ((StringBuilder)serializable).append(n);
        throw new ArithmeticException(((StringBuilder)serializable).toString());
    }

    public short shortValueExact() {
        int n = this.intValueExact();
        boolean bl = true;
        boolean bl2 = n > 32767;
        if (n >= -32768) {
            bl = false;
        }
        if (!(bl | bl2)) {
            return (short)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conversion overflow: ");
        stringBuilder.append(this.toString());
        throw new ArithmeticException(stringBuilder.toString());
    }

    public int signum() {
        return this.ind;
    }

    public BigDecimal subtract(BigDecimal bigDecimal) {
        return this.subtract(bigDecimal, plainMC);
    }

    public BigDecimal subtract(BigDecimal bigDecimal, MathContext mathContext) {
        if (mathContext.lostDigits) {
            this.checkdigits(bigDecimal, mathContext.digits);
        }
        bigDecimal = BigDecimal.clone(bigDecimal);
        bigDecimal.ind = -bigDecimal.ind;
        return this.add(bigDecimal, mathContext);
    }

    public java.math.BigDecimal toBigDecimal() {
        return new java.math.BigDecimal(this.unscaledValue(), this.scale());
    }

    public BigInteger toBigInteger() {
        BigDecimal bigDecimal;
        int n = this.exp;
        int n2 = 1;
        n = n >= 0 ? 1 : 0;
        if (this.form != 0) {
            n2 = 0;
        }
        if ((n & n2) != 0) {
            bigDecimal = this;
        } else {
            n = this.exp;
            if (n >= 0) {
                bigDecimal = BigDecimal.clone(this);
                bigDecimal.form = (byte)(false ? 1 : 0);
            } else if (-n >= this.mant.length) {
                bigDecimal = ZERO;
            } else {
                bigDecimal = BigDecimal.clone(this);
                byte[] arrby = bigDecimal.mant;
                n = arrby.length + bigDecimal.exp;
                byte[] arrby2 = new byte[n];
                System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)n);
                bigDecimal.mant = arrby2;
                bigDecimal.form = (byte)(false ? 1 : 0);
                bigDecimal.exp = 0;
            }
        }
        return new BigInteger(new String(bigDecimal.layout()));
    }

    public BigInteger toBigIntegerExact() {
        Object object;
        int n = this.exp;
        if (n < 0 && !BigDecimal.allzero((byte[])(object = this.mant), ((byte[])object).length + n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Decimal part non-zero: ");
            ((StringBuilder)object).append(this.toString());
            throw new ArithmeticException(((StringBuilder)object).toString());
        }
        return this.toBigInteger();
    }

    public char[] toCharArray() {
        return this.layout();
    }

    public String toString() {
        return new String(this.layout());
    }

    public BigInteger unscaledValue() {
        BigDecimal bigDecimal;
        if (this.exp >= 0) {
            bigDecimal = this;
        } else {
            bigDecimal = BigDecimal.clone(this);
            bigDecimal.exp = 0;
        }
        return bigDecimal.toBigInteger();
    }
}

