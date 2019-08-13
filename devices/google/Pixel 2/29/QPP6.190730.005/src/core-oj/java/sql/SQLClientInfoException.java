/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.ClientInfoStatus;
import java.sql.SQLException;
import java.util.Map;

public class SQLClientInfoException
extends SQLException {
    private static final long serialVersionUID = -4319604256824655880L;
    private Map<String, ClientInfoStatus> failedProperties;

    public SQLClientInfoException() {
        this.failedProperties = null;
    }

    public SQLClientInfoException(String string, String string2, int n, Map<String, ClientInfoStatus> map) {
        super(string, string2, n);
        this.failedProperties = map;
    }

    public SQLClientInfoException(String string, String string2, int n, Map<String, ClientInfoStatus> map, Throwable throwable) {
        super(string, string2, n);
        this.initCause(throwable);
        this.failedProperties = map;
    }

    public SQLClientInfoException(String string, String string2, Map<String, ClientInfoStatus> map) {
        super(string, string2);
        this.failedProperties = map;
    }

    public SQLClientInfoException(String string, String string2, Map<String, ClientInfoStatus> map, Throwable throwable) {
        super(string, string2);
        this.initCause(throwable);
        this.failedProperties = map;
    }

    public SQLClientInfoException(String string, Map<String, ClientInfoStatus> map) {
        super(string);
        this.failedProperties = map;
    }

    public SQLClientInfoException(String string, Map<String, ClientInfoStatus> map, Throwable throwable) {
        super(string);
        this.initCause(throwable);
        this.failedProperties = map;
    }

    public SQLClientInfoException(Map<String, ClientInfoStatus> map) {
        this.failedProperties = map;
    }

    public SQLClientInfoException(Map<String, ClientInfoStatus> map, Throwable throwable) {
        String string = throwable != null ? throwable.toString() : null;
        super(string);
        this.initCause(throwable);
        this.failedProperties = map;
    }

    public Map<String, ClientInfoStatus> getFailedProperties() {
        return this.failedProperties;
    }
}

