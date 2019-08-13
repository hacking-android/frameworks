/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;

public class SQLNonTransientException
extends SQLException {
    private static final long serialVersionUID = -9104382843534716547L;

    public SQLNonTransientException() {
    }

    public SQLNonTransientException(String string) {
        super(string);
    }

    public SQLNonTransientException(String string, String string2) {
        super(string, string2);
    }

    public SQLNonTransientException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLNonTransientException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLNonTransientException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLNonTransientException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLNonTransientException(Throwable throwable) {
        super(throwable);
    }
}

