/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLSyntaxErrorException
extends SQLNonTransientException {
    private static final long serialVersionUID = -1843832610477496053L;

    public SQLSyntaxErrorException() {
    }

    public SQLSyntaxErrorException(String string) {
        super(string);
    }

    public SQLSyntaxErrorException(String string, String string2) {
        super(string, string2);
    }

    public SQLSyntaxErrorException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLSyntaxErrorException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLSyntaxErrorException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLSyntaxErrorException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLSyntaxErrorException(Throwable throwable) {
        super(throwable);
    }
}

