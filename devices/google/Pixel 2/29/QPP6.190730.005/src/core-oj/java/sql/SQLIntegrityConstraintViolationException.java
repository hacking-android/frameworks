/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLIntegrityConstraintViolationException
extends SQLNonTransientException {
    private static final long serialVersionUID = 8033405298774849169L;

    public SQLIntegrityConstraintViolationException() {
    }

    public SQLIntegrityConstraintViolationException(String string) {
        super(string);
    }

    public SQLIntegrityConstraintViolationException(String string, String string2) {
        super(string, string2);
    }

    public SQLIntegrityConstraintViolationException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLIntegrityConstraintViolationException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLIntegrityConstraintViolationException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLIntegrityConstraintViolationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLIntegrityConstraintViolationException(Throwable throwable) {
        super(throwable);
    }
}

