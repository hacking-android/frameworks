/*
 * Decompiled with CFR 0.145.
 */
package java.security;

public class PrivilegedActionException
extends Exception {
    private static final long serialVersionUID = 4724086851538908602L;
    private Exception exception;

    public PrivilegedActionException(Exception exception) {
        super((Throwable)null);
        this.exception = exception;
    }

    @Override
    public Throwable getCause() {
        return this.exception;
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    public String toString() {
        String string;
        block0 : {
            string = this.getClass().getName();
            if (this.exception == null) break block0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(this.exception.toString());
            string = stringBuilder.toString();
        }
        return string;
    }
}

