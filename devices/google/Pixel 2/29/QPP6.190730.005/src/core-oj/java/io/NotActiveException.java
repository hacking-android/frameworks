/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class NotActiveException
extends ObjectStreamException {
    private static final long serialVersionUID = -3893467273049808895L;

    public NotActiveException() {
    }

    public NotActiveException(String string) {
        super(string);
    }
}

