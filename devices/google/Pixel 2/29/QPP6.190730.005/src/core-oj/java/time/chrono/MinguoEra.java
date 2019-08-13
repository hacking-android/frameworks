/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

public enum MinguoEra implements Era
{
    BEFORE_ROC,
    ROC;
    

    public static MinguoEra of(int n) {
        if (n != 0) {
            if (n == 1) {
                return ROC;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid era: ");
            stringBuilder.append(n);
            throw new DateTimeException(stringBuilder.toString());
        }
        return BEFORE_ROC;
    }

    @Override
    public int getValue() {
        return this.ordinal();
    }
}

