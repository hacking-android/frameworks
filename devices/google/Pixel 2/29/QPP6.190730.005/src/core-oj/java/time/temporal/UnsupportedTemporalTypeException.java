/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.DateTimeException;

public class UnsupportedTemporalTypeException
extends DateTimeException {
    private static final long serialVersionUID = -6158898438688206006L;

    public UnsupportedTemporalTypeException(String string) {
        super(string);
    }

    public UnsupportedTemporalTypeException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

