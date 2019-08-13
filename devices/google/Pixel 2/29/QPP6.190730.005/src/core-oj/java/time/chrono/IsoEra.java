/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;

public enum IsoEra implements Era
{
    BCE,
    CE;
    

    public static IsoEra of(int n) {
        if (n != 0) {
            if (n == 1) {
                return CE;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid era: ");
            stringBuilder.append(n);
            throw new DateTimeException(stringBuilder.toString());
        }
        return BCE;
    }

    @Override
    public int getValue() {
        return this.ordinal();
    }
}

