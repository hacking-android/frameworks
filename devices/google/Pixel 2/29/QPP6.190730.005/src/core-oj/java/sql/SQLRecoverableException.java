/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;

public class SQLRecoverableException
extends SQLException {
    private static final long serialVersionUID = -4144386502923131579L;

    public SQLRecoverableException() {
    }

    public SQLRecoverableException(String string) {
        super(string);
    }

    public SQLRecoverableException(String string, String string2) {
        super(string, string2);
    }

    public SQLRecoverableException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLRecoverableException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLRecoverableException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLRecoverableException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLRecoverableException(Throwable throwable) {
        super(throwable);
    }
}

