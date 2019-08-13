/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class InvalidClassException
extends ObjectStreamException {
    private static final long serialVersionUID = -4333316296251054416L;
    public String classname;

    public InvalidClassException(String string) {
        super(string);
    }

    public InvalidClassException(String string, String string2) {
        super(string2);
        this.classname = string;
    }

    @Override
    public String getMessage() {
        if (this.classname == null) {
            return super.getMessage();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.classname);
        stringBuilder.append("; ");
        stringBuilder.append(super.getMessage());
        return stringBuilder.toString();
    }
}

