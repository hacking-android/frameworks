/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLTransientException;

public class SQLTransientConnectionException
extends SQLTransientException {
    private static final long serialVersionUID = -2520155553543391200L;

    public SQLTransientConnectionException() {
    }

    public SQLTransientConnectionException(String string) {
        super(string);
    }

    public SQLTransientConnectionException(String string, String string2) {
        super(string, string2);
    }

    public SQLTransientConnectionException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLTransientConnectionException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLTransientConnectionException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLTransientConnectionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLTransientConnectionException(Throwable throwable) {
        super(throwable);
    }
}

