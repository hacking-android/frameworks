/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.time.DateTimeException;

public class DateTimeParseException
extends DateTimeException {
    private static final long serialVersionUID = 4304633501674722597L;
    private final int errorIndex;
    private final String parsedString;

    public DateTimeParseException(String string, CharSequence charSequence, int n) {
        super(string);
        this.parsedString = charSequence.toString();
        this.errorIndex = n;
    }

    public DateTimeParseException(String string, CharSequence charSequence, int n, Throwable throwable) {
        super(string, throwable);
        this.parsedString = charSequence.toString();
        this.errorIndex = n;
    }

    public int getErrorIndex() {
        return this.errorIndex;
    }

    public String getParsedString() {
        return this.parsedString;
    }
}

