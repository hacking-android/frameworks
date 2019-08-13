/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLNonTransientConnectionException
extends SQLNonTransientException {
    private static final long serialVersionUID = -5852318857474782892L;

    public SQLNonTransientConnectionException() {
    }

    public SQLNonTransientConnectionException(String string) {
        super(string);
    }

    public SQLNonTransientConnectionException(String string, String string2) {
        super(string, string2);
    }

    public SQLNonTransientConnectionException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLNonTransientConnectionException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLNonTransientConnectionException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLNonTransientConnectionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLNonTransientConnectionException(Throwable throwable) {
        super(throwable);
    }
}

