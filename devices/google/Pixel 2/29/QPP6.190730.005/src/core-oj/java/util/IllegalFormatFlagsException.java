/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.IllegalFormatException;

public class IllegalFormatFlagsException
extends IllegalFormatException {
    private static final long serialVersionUID = 790824L;
    private String flags;

    public IllegalFormatFlagsException(String string) {
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
        stringBuilder.append("Flags = '");
        stringBuilder.append(this.flags);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}

