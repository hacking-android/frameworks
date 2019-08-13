/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class IllegalFormatConversionException
extends IllegalFormatException {
    private static final long serialVersionUID = 17000126L;
    private Class<?> arg;
    private char c;

    public IllegalFormatConversionException(char c, Class<?> class_) {
        if (class_ != null) {
            this.c = c;
            this.arg = class_;
            return;
        }
        throw new NullPointerException();
    }

    public Class<?> getArgumentClass() {
        return this.arg;
    }

    public char getConversion() {
        return this.c;
    }

    @Override
    public String getMessage() {
        return String.format("%c != %s", Character.valueOf(this.c), this.arg.getName());
    }
}

