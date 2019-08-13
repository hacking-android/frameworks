/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

public class CompletionException
extends RuntimeException {
    private static final long serialVersionUID = 7830266012832686185L;

    protected CompletionException() {
    }

    protected CompletionException(String string) {
        super(string);
    }

    public CompletionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CompletionException(Throwable throwable) {
        super(throwable);
    }
}

