/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.util.Arrays;
import sun.misc.FloatingDecimal;

public class FormattedFloatingDecimal {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadLocal<Object> threadLocalCharBuffer = new ThreadLocal<Object>(){

        @Override
        protected Object initialValue() {
            return new char[20];
        }
    };
    private int decExponentRounded;
    private char[] exponent;
    private char[] mantissa;

    private FormattedFloatingDecimal(int n, Form form, FloatingDecimal.BinaryToASCIIConverter binaryToASCIIConverter) {
        if (binaryToASCIIConverter.isExceptional()) {
            this.mantissa = binaryToASCIIConverter.toJavaFormatString().toCharArray();
            this.exponent = null;
            return;
        }
        char[] arrc = FormattedFloatingDecimal.getBuffer();
        int n2 = binaryToASCIIConverter.getDigits(arrc);
        int n3 = binaryToASCIIConverter.getDecimalExponent();
        boolean bl = binaryToASCIIConverter.isNegative();
        int n4 = 2.$SwitchMap$sun$misc$FormattedFloatingDecimal$Form[form.ordinal()];
        if (n4 != 1) {
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 == 4) {
                        if ((n3 = FormattedFloatingDecimal.applyPrecision(n3, arrc, n2, n)) - 1 >= -4 && n3 - 1 < n) {
                            this.fillDecimal(n - n3, arrc, n2, n3, bl);
                        } else {
                            this.fillScientific(n - 1, arrc, n2, n3, bl);
                        }
                        this.decExponentRounded = n3;
                    }
                } else {
                    n3 = FormattedFloatingDecimal.applyPrecision(n3, arrc, n2, n + 1);
                    this.fillScientific(n, arrc, n2, n3, bl);
                    this.decExponentRounded = n3;
                }
            } else {
                n3 = FormattedFloatingDecimal.applyPrecision(n3, arrc, n2, n3 + n);
                this.fillDecimal(n, arrc, n2, n3, bl);
                this.decExponentRounded = n3;
            }
        } else {
            this.decExponentRounded = n3;
            this.fillCompatible(n, arrc, n2, n3, bl);
        }
    }

    private static int applyPrecision(int n, char[] arrc, int n2, int n3) {
        if (n3 < n2 && n3 >= 0) {
            if (n3 == 0) {
                if (arrc[0] >= '5') {
                    arrc[0] = (char)49;
                    Arrays.fill(arrc, 1, n2, '0');
                    return n + 1;
                }
                Arrays.fill(arrc, 0, n2, '0');
                return n;
            }
            if (arrc[n3] >= '5') {
                int n4 = n3 - 1;
                n3 = arrc[n4];
                int n5 = n4;
                int n6 = n3;
                if (n3 == 57) {
                    while (n3 == 57 && n4 > 0) {
                        n3 = arrc[--n4];
                    }
                    n5 = n4;
                    n6 = n3;
                    if (n3 == 57) {
                        arrc[0] = (char)49;
                        Arrays.fill(arrc, 1, n2, '0');
                        return n + 1;
                    }
                }
                arrc[n5] = (char)(n6 + 1);
                Arrays.fill(arrc, n5 + 1, n2, '0');
            } else {
                Arrays.fill(arrc, n3, n2, '0');
            }
            return n;
        }
        return n;
    }

    private static char[] create(boolean bl, int n) {
        if (bl) {
            char[] arrc = new char[n + 1];
            arrc[0] = (char)45;
            return arrc;
        }
        return new char[n];
    }

    private void fillCompatible(int n, char[] arrc, int n2, int n3, boolean bl) {
        boolean bl2 = false;
        if (n3 > 0 && n3 < 8) {
            if (n2 < n3) {
                n = n3 - n2;
                this.mantissa = FormattedFloatingDecimal.create(bl, n2 + n + 2);
                System.arraycopy((Object)arrc, 0, (Object)this.mantissa, (int)bl, n2);
                Arrays.fill(this.mantissa, bl + n2, bl + n2 + n, '0');
                arrc = this.mantissa;
                arrc[bl + n2 + n] = (char)46;
                arrc[bl + n2 + n + 1] = (char)48;
            } else if (n3 < n2) {
                n = Math.min(n2 - n3, n);
                this.mantissa = FormattedFloatingDecimal.create(bl, n3 + 1 + n);
                System.arraycopy((Object)arrc, 0, (Object)this.mantissa, (int)bl, n3);
                char[] arrc2 = this.mantissa;
                arrc2[bl + n3] = (char)46;
                System.arraycopy((Object)arrc, n3, (Object)arrc2, bl + n3 + 1, n);
            } else {
                this.mantissa = FormattedFloatingDecimal.create(bl, n2 + 2);
                System.arraycopy((Object)arrc, 0, (Object)this.mantissa, (int)bl, n2);
                arrc = this.mantissa;
                arrc[bl + n2] = (char)46;
                arrc[bl + n2 + 1] = (char)48;
            }
        } else if (n3 <= 0 && n3 > -3) {
            int n4 = Math.max(0, Math.min(-n3, n));
            n = Math.max(0, Math.min(n2, n + n3));
            if (n4 > 0) {
                char[] arrc3 = this.mantissa = FormattedFloatingDecimal.create(bl, n4 + 2 + n);
                arrc3[bl] = (char)48;
                arrc3[bl + 1] = (char)46;
                Arrays.fill(arrc3, bl + 2, bl + 2 + n4, '0');
                if (n > 0) {
                    System.arraycopy((Object)arrc, 0, (Object)this.mantissa, bl + 2 + n4, n);
                }
            } else if (n > 0) {
                char[] arrc4 = this.mantissa = FormattedFloatingDecimal.create(bl, n4 + 2 + n);
                arrc4[bl] = (char)48;
                arrc4[bl + 1] = (char)46;
                System.arraycopy((Object)arrc, 0, (Object)arrc4, bl + 2, n);
            } else {
                this.mantissa = FormattedFloatingDecimal.create(bl, 1);
                this.mantissa[bl] = (char)48;
            }
        } else {
            if (n2 > 1) {
                char[] arrc5 = this.mantissa = FormattedFloatingDecimal.create(bl, n2 + 1);
                arrc5[bl] = arrc[0];
                arrc5[bl + 1] = (char)46;
                System.arraycopy((Object)arrc, 1, (Object)arrc5, bl + 2, n2 - 1);
            } else {
                char[] arrc6 = this.mantissa = FormattedFloatingDecimal.create(bl, 3);
                arrc6[bl] = arrc[0];
                arrc6[bl + 1] = (char)46;
                arrc6[bl + 2] = (char)48;
            }
            if (n3 <= 0) {
                bl2 = true;
            }
            if (bl2) {
                n2 = -n3 + 1;
                n = 1;
            } else {
                n2 = n3 - 1;
                n = 0;
            }
            if (n2 <= 9) {
                this.exponent = FormattedFloatingDecimal.create(bl2, 1);
                this.exponent[n] = (char)(n2 + 48);
            } else if (n2 <= 99) {
                this.exponent = FormattedFloatingDecimal.create(bl2, 2);
                arrc = this.exponent;
                arrc[n] = (char)(n2 / 10 + 48);
                arrc[n + 1] = (char)(n2 % 10 + 48);
            } else {
                this.exponent = FormattedFloatingDecimal.create(bl2, 3);
                arrc = this.exponent;
                arrc[n] = (char)(n2 / 100 + 48);
                arrc[n + 1] = (char)((n2 %= 100) / 10 + 48);
                arrc[n + 2] = (char)(n2 % 10 + 48);
            }
        }
    }

    private void fillDecimal(int n, char[] arrc, int n2, int n3, boolean bl) {
        if (n3 > 0) {
            if (n2 < n3) {
                this.mantissa = FormattedFloatingDecimal.create(bl, n3);
                System.arraycopy((Object)arrc, 0, (Object)this.mantissa, (int)bl, n2);
                Arrays.fill(this.mantissa, bl + n2, bl + n3, '0');
            } else {
                n = (n2 = Math.min(n2 - n3, n)) > 0 ? n2 + 1 : 0;
                this.mantissa = FormattedFloatingDecimal.create(bl, n + n3);
                System.arraycopy((Object)arrc, 0, (Object)this.mantissa, (int)bl, n3);
                if (n2 > 0) {
                    char[] arrc2 = this.mantissa;
                    arrc2[bl + n3] = (char)46;
                    System.arraycopy((Object)arrc, n3, (Object)arrc2, bl + n3 + 1, n2);
                }
            }
        } else if (n3 <= 0) {
            int n4 = Math.max(0, Math.min(-n3, n));
            n = Math.max(0, Math.min(n2, n + n3));
            if (n4 > 0) {
                char[] arrc3 = this.mantissa = FormattedFloatingDecimal.create(bl, n4 + 2 + n);
                arrc3[bl] = (char)48;
                arrc3[bl + 1] = (char)46;
                Arrays.fill(arrc3, bl + 2, bl + 2 + n4, '0');
                if (n > 0) {
                    System.arraycopy((Object)arrc, 0, (Object)this.mantissa, bl + 2 + n4, n);
                }
            } else if (n > 0) {
                char[] arrc4 = this.mantissa = FormattedFloatingDecimal.create(bl, n4 + 2 + n);
                arrc4[bl] = (char)48;
                arrc4[bl + 1] = (char)46;
                System.arraycopy((Object)arrc, 0, (Object)arrc4, bl + 2, n);
            } else {
                this.mantissa = FormattedFloatingDecimal.create(bl, 1);
                this.mantissa[bl] = (char)48;
            }
        }
    }

    private void fillScientific(int n, char[] arrc, int n2, int n3, boolean bl) {
        char c;
        if ((n = Math.max(0, Math.min(n2 - 1, n))) > 0) {
            char[] arrc2 = this.mantissa = FormattedFloatingDecimal.create(bl, n + 2);
            arrc2[bl] = arrc[0];
            arrc2[bl + 1] = (char)46;
            System.arraycopy((Object)arrc, 1, (Object)arrc2, bl + 2, n);
        } else {
            this.mantissa = FormattedFloatingDecimal.create(bl, 1);
            this.mantissa[bl] = arrc[0];
        }
        if (n3 <= 0) {
            n2 = 45;
            n = -n3 + 1;
            c = n2;
        } else {
            n2 = 43;
            n = n3 - 1;
            c = n2;
        }
        if (n <= 9) {
            this.exponent = new char[]{c, '0', (char)(n + 48)};
        } else if (n <= 99) {
            this.exponent = new char[]{c, (char)(n / 10 + 48), (char)(n % 10 + 48)};
        } else {
            char c2 = (char)(n / 100 + 48);
            this.exponent = new char[]{c, c2, (char)((n %= 100) / 10 + 48), (char)(n % 10 + 48)};
        }
    }

    private static char[] getBuffer() {
        return (char[])threadLocalCharBuffer.get();
    }

    public static FormattedFloatingDecimal valueOf(double d, int n, Form form) {
        boolean bl = form == Form.COMPATIBLE;
        return new FormattedFloatingDecimal(n, form, FloatingDecimal.getBinaryToASCIIConverter(d, bl));
    }

    public char[] getExponent() {
        return this.exponent;
    }

    public int getExponentRounded() {
        return this.decExponentRounded - 1;
    }

    public char[] getMantissa() {
        return this.mantissa;
    }

    public static enum Form {
        SCIENTIFIC,
        COMPATIBLE,
        DECIMAL_FLOAT,
        GENERAL;
        
    }

}

