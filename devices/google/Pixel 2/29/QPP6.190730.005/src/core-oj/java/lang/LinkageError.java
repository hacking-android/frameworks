/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class LinkageError
extends Error {
    private static final long serialVersionUID = 3579600108157160122L;

    public LinkageError() {
    }

    public LinkageError(String string) {
        super(string);
    }

    public LinkageError(String string, Throwable throwable) {
        super(string, throwable);
    }
}

