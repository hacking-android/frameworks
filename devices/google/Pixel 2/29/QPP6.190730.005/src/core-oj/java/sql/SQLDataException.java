/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLDataException
extends SQLNonTransientException {
    private static final long serialVersionUID = -6889123282670549800L;

    public SQLDataException() {
    }

    public SQLDataException(String string) {
        super(string);
    }

    public SQLDataException(String string, String string2) {
        super(string, string2);
    }

    public SQLDataException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLDataException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLDataException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLDataException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLDataException(Throwable throwable) {
        super(throwable);
    }
}

