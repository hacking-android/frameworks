/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

public class ExecutionException
extends Exception {
    private static final long serialVersionUID = 7830266012832686185L;

    protected ExecutionException() {
    }

    protected ExecutionException(String string) {
        super(string);
    }

    public ExecutionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ExecutionException(Throwable throwable) {
        super(throwable);
    }
}

