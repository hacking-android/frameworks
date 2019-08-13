/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.SQLException;
import java.util.EventObject;
import javax.sql.PooledConnection;

public class ConnectionEvent
extends EventObject {
    static final long serialVersionUID = -4843217645290030002L;
    private SQLException ex = null;

    public ConnectionEvent(PooledConnection pooledConnection) {
        super(pooledConnection);
    }

    public ConnectionEvent(PooledConnection pooledConnection, SQLException sQLException) {
        super(pooledConnection);
        this.ex = sQLException;
    }

    public SQLException getSQLException() {
        return this.ex;
    }
}

