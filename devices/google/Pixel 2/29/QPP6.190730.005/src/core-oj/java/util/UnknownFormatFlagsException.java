/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class UnknownFormatFlagsException
extends IllegalFormatException {
    private static final long serialVersionUID = 19370506L;
    private String flags;

    public UnknownFormatFlagsException(String string) {
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flags = ");
        stringBuilder.append(this.flags);
        return stringBuilder.toString();
    }
}

