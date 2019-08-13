/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLTransientException;

public class SQLTransactionRollbackException
extends SQLTransientException {
    private static final long serialVersionUID = 5246680841170837229L;

    public SQLTransactionRollbackException() {
    }

    public SQLTransactionRollbackException(String string) {
        super(string);
    }

    public SQLTransactionRollbackException(String string, String string2) {
        super(string, string2);
    }

    public SQLTransactionRollbackException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLTransactionRollbackException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLTransactionRollbackException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLTransactionRollbackException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLTransactionRollbackException(Throwable throwable) {
        super(throwable);
    }
}

