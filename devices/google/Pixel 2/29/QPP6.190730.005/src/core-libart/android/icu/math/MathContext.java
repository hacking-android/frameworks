/*
 * Decompiled with CFR 0.145.
 */
package android.icu.math;

import java.io.Serializable;

public final class MathContext
implements Serializable {
    public static final MathContext DEFAULT;
    private static final int DEFAULT_DIGITS = 9;
    private static final int DEFAULT_FORM = 1;
    private static final boolean DEFAULT_LOSTDIGITS = false;
    private static final int DEFAULT_ROUNDINGMODE = 4;
    public static final int ENGINEERING = 2;
    private static final int MAX_DIGITS = 999999999;
    private static final int MIN_DIGITS = 0;
    public static final int PLAIN = 0;
    private static final int[] ROUNDS;
    private static final String[] ROUNDWORDS;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_UNNECESSARY = 7;
    public static final int ROUND_UP = 0;
    public static final int SCIENTIFIC = 1;
    private static final long serialVersionUID = 7163376998892515376L;
    int digits;
    int form;
    boolean lostDigits;
    int roundingMode;

    static {
        ROUNDS = new int[]{4, 7, 2, 1, 3, 5, 6, 0};
        ROUNDWORDS = new String[]{"ROUND_HALF_UP", "ROUND_UNNECESSARY", "ROUND_CEILING", "ROUND_DOWN", "ROUND_FLOOR", "ROUND_HALF_DOWN", "ROUND_HALF_EVEN", "ROUND_UP"};
        DEFAULT = new MathContext(9, 1, false, 4);
    }

    public MathContext(int n) {
        this(n, 1, false, 4);
    }

    public MathContext(int n, int n2) {
        this(n, n2, false, 4);
    }

    public MathContext(int n, int n2, boolean bl) {
        this(n, n2, bl, 4);
    }

    public MathContext(int n, int n2, boolean bl, int n3) {
        if (n != 9) {
            if (n >= 0) {
                if (n > 999999999) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Digits too large: ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Digits too small: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        if (n2 == 1 || n2 == 2 || n2 == 0) {
            if (MathContext.isValidRound(n3)) {
                this.digits = n;
                this.form = n2;
                this.lostDigits = bl;
                this.roundingMode = n3;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad roundingMode value: ");
            stringBuilder.append(n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad form value: ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static boolean isValidRound(int n) {
        int n2 = ROUNDS.length;
        int n3 = 0;
        while (n2 > 0) {
            if (n == ROUNDS[n3]) {
                return true;
            }
            --n2;
            ++n3;
        }
        return false;
    }

    public int getDigits() {
        return this.digits;
    }

    public int getForm() {
        return this.form;
    }

    public boolean getLostDigits() {
        return this.lostDigits;
    }

    public int getRoundingMode() {
        return this.roundingMode;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = null;
        int n = this.form;
        String string = n == 1 ? "SCIENTIFIC" : (n == 2 ? "ENGINEERING" : "PLAIN");
        int n2 = ROUNDS.length;
        n = 0;
        do {
            charSequence = stringBuilder;
            if (n2 <= 0) break;
            if (this.roundingMode == ROUNDS[n]) {
                charSequence = ROUNDWORDS[n];
                break;
            }
            --n2;
            ++n;
        } while (true);
        stringBuilder = new StringBuilder();
        stringBuilder.append("digits=");
        stringBuilder.append(this.digits);
        stringBuilder.append(" form=");
        stringBuilder.append(string);
        stringBuilder.append(" lostDigits=");
        string = this.lostDigits ? "1" : "0";
        stringBuilder.append(string);
        stringBuilder.append(" roundingMode=");
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }
}

