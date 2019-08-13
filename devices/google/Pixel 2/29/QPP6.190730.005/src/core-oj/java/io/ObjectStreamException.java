/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;

public abstract class ObjectStreamException
extends IOException {
    private static final long serialVersionUID = 7260898174833392607L;

    protected ObjectStreamException() {
    }

    protected ObjectStreamException(String string) {
        super(string);
    }
}

