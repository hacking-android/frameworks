/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;

public class SQLTransientException
extends SQLException {
    private static final long serialVersionUID = -9042733978262274539L;

    public SQLTransientException() {
    }

    public SQLTransientException(String string) {
        super(string);
    }

    public SQLTransientException(String string, String string2) {
        super(string, string2);
    }

    public SQLTransientException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLTransientException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLTransientException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLTransientException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLTransientException(Throwable throwable) {
        super(throwable);
    }
}

