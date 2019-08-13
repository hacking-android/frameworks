/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class OptionalDataException
extends ObjectStreamException {
    private static final long serialVersionUID = -8011121865681257820L;
    public boolean eof;
    public int length;

    OptionalDataException(int n) {
        this.eof = false;
        this.length = n;
    }

    OptionalDataException(boolean bl) {
        this.length = 0;
        this.eof = bl;
    }
}

