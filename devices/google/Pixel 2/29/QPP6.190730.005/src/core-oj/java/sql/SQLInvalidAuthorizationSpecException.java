/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLNonTransientException;

public class SQLInvalidAuthorizationSpecException
extends SQLNonTransientException {
    private static final long serialVersionUID = -64105250450891498L;

    public SQLInvalidAuthorizationSpecException() {
    }

    public SQLInvalidAuthorizationSpecException(String string) {
        super(string);
    }

    public SQLInvalidAuthorizationSpecException(String string, String string2) {
        super(string, string2);
    }

    public SQLInvalidAuthorizationSpecException(String string, String string2, int n) {
        super(string, string2, n);
    }

    public SQLInvalidAuthorizationSpecException(String string, String string2, int n, Throwable throwable) {
        super(string, string2, n, throwable);
    }

    public SQLInvalidAuthorizationSpecException(String string, String string2, Throwable throwable) {
        super(string, string2, throwable);
    }

    public SQLInvalidAuthorizationSpecException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SQLInvalidAuthorizationSpecException(Throwable throwable) {
        super(throwable);
    }
}

