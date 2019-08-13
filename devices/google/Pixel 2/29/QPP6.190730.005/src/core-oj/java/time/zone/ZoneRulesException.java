/*
 * Decompiled with CFR 0.145.
 */
package java.time.zone;

import java.time.DateTimeException;

public class ZoneRulesException
extends DateTimeException {
    private static final long serialVersionUID = -1632418723876261839L;

    public ZoneRulesException(String string) {
        super(string);
    }

    public ZoneRulesException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

