/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class DuplicateFormatFlagsException
extends IllegalFormatException {
    private static final long serialVersionUID = 18890531L;
    private String flags;

    public DuplicateFormatFlagsException(String string) {
        if (string != null) {
            this.flags = string;
            return;
        }
        throw new NullPointerException();
    }

    public String getFlags() {
        return this.flags;
    }

    @Override
    public String getMessage() {
        return String.format("Flags = '%s'", this.flags);
    }
}

