/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLTransientException;

public class SQLTimeoutException
extends SQLTransientException {
    private static final long serialVersionUID = -4487171280562520262L;

    public SQLTimeoutException() {
    }

    public SQLTimeoutException(String string) {
        super(string);
    }

    public SQLTimeoutException(String string, String string2) {
        super(string, string2);
    }

    public SQLTimeoutException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLTimeoutException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLTimeoutException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLTimeoutException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLTimeoutException(Throwable throwable) {
        super(throwable);
    }
}

