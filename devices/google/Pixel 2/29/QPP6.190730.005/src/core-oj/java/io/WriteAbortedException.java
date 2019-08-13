/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.ObjectStreamException;

public class WriteAbortedException
extends ObjectStreamException {
    private static final long serialVersionUID = -3326426625597282442L;
    public Exception detail;

    public WriteAbortedException(String string, Exception exception) {
        super(string);
        this.initCause(null);
        this.detail = exception;
    }

    @Override
    public Throwable getCause() {
        return this.detail;
    }

    @Override
    public String getMessage() {
        if (this.detail == null) {
            return super.getMessage();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.getMessage());
        stringBuilder.append("; ");
        stringBuilder.append(this.detail.toString());
        return stringBuilder.toString();
    }
}

