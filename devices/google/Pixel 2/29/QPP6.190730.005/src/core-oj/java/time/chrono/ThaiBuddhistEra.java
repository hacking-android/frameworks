/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

public enum ThaiBuddhistEra implements Era
{
    BEFORE_BE,
    BE;
    

    public static ThaiBuddhistEra of(int n) {
        if (n != 0) {
            if (n == 1) {
                return BE;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid era: ");
            stringBuilder.append(n);
            throw new DateTimeException(stringBuilder.toString());
        }
        return BEFORE_BE;
    }

    @Override
    public int getValue() {
        return this.ordinal();
    }
}

