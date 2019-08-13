/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class NotSerializableException
extends ObjectStreamException {
    private static final long serialVersionUID = 2906642554793891381L;

    public NotSerializableException() {
    }

    public NotSerializableException(String string) {
        super(string);
    }
}

