/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLFeatureNotSupportedException
extends SQLNonTransientException {
    private static final long serialVersionUID = -1026510870282316051L;

    public SQLFeatureNotSupportedException() {
    }

    public SQLFeatureNotSupportedException(String string) {
        super(string);
    }

    public SQLFeatureNotSupportedException(String string, String string2) {
        super(string, string2);
    }

    public SQLFeatureNotSupportedException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLFeatureNotSupportedException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLFeatureNotSupportedException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLFeatureNotSupportedException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLFeatureNotSupportedException(Throwable throwable) {
        super(throwable);
    }
}

