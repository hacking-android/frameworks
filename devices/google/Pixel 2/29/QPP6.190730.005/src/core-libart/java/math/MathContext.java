/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.math.RoundingMode;

public final class MathContext
implements Serializable {
    public static final MathContext DECIMAL128 = new MathContext(34, RoundingMode.HALF_EVEN);
    public static final MathContext DECIMAL32 = new MathContext(7, RoundingMode.HALF_EVEN);
    public static final MathContext DECIMAL64 = new MathContext(16, RoundingMode.HALF_EVEN);
    public static final MathContext UNLIMITED = new MathContext(0, RoundingMode.HALF_UP);
    private static final long serialVersionUID = 5579720004786848255L;
    private final int precision;
    private final RoundingMode roundingMode;

    public MathContext(int n) {
        this(n, RoundingMode.HALF_UP);
    }

    public MathContext(int n, RoundingMode roundingMode) {
        this.precision = n;
        this.roundingMode = roundingMode;
        this.checkValid();
    }

    public MathContext(String string) {
        int n;
        int n2 = "precision=".length();
        int n3 = "roundingMode=".length();
        if (string.startsWith("precision=") && (n = string.indexOf(32, n2)) != -1) {
            String string2 = string.substring(n2, n);
            try {
                this.precision = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                throw this.invalidMathContext("Bad precision", string);
            }
            if (string.regionMatches(++n, "roundingMode=", 0, n3)) {
                this.roundingMode = RoundingMode.valueOf(string.substring(n + n3));
                this.checkValid();
                return;
            }
            throw this.invalidMathContext("Missing rounding mode", string);
        }
        throw this.invalidMathContext("Missing precision", string);
    }

    private void checkValid() {
        if (this.precision >= 0) {
            if (this.roundingMode != null) {
                return;
            }
            throw new NullPointerException("roundingMode == null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative precision: ");
        stringBuilder.append(this.precision);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private IllegalArgumentException invalidMathContext(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        try {
            this.checkValid();
            return;
        }
        catch (Exception exception) {
            throw new StreamCorruptedException(exception.getMessage());
        }
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof MathContext && ((MathContext)object).getPrecision() == this.precision && ((MathContext)object).getRoundingMode() == this.roundingMode;
        return bl;
    }

    public int getPrecision() {
        return this.precision;
    }

    public RoundingMode getRoundingMode() {
        return this.roundingMode;
    }

    public int hashCode() {
        return this.precision << 3 | this.roundingMode.ordinal();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("precision=");
        stringBuilder.append(this.precision);
        stringBuilder.append(" roundingMode=");
        stringBuilder.append((Object)this.roundingMode);
        return stringBuilder.toString();
    }
}

