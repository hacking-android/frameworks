/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.GeneralSecurityException;

public class UnrecoverableEntryException
extends GeneralSecurityException {
    private static final long serialVersionUID = -4527142945246286535L;

    public UnrecoverableEntryException() {
    }

    public UnrecoverableEntryException(String string) {
        super(string);
    }
}

