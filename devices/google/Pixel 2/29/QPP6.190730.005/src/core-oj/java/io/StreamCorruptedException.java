/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class StreamCorruptedException
extends ObjectStreamException {
    private static final long serialVersionUID = 8983558202217591746L;

    public StreamCorruptedException() {
    }

    public StreamCorruptedException(String string) {
        super(string);
    }
}

