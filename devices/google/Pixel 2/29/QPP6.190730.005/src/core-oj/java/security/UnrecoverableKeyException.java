/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.UnrecoverableEntryException;

public class UnrecoverableKeyException
extends UnrecoverableEntryException {
    private static final long serialVersionUID = 7275063078190151277L;

    public UnrecoverableKeyException() {
    }

    public UnrecoverableKeyException(String string) {
        super(string);
    }
}

