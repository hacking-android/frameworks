/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

final class TransactionAbortError
extends InternalError {
    private TransactionAbortError() {
    }

    private TransactionAbortError(String string) {
        super(string);
    }

    private TransactionAbortError(String string, Throwable throwable) {
        super(string);
        this.initCause(throwable);
    }

    private TransactionAbortError(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        this(string, throwable);
    }
}

