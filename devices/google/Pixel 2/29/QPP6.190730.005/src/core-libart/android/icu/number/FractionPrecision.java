/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.Precision;

public abstract class FractionPrecision
extends Precision {
    FractionPrecision() {
    }

    public Precision withMaxDigits(int n) {
        if (n >= 1 && n <= 999) {
            return FractionPrecision.constructFractionSignificant(this, -1, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public Precision withMinDigits(int n) {
        if (n >= 1 && n <= 999) {
            return FractionPrecision.constructFractionSignificant(this, n, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }
}

