/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.EventObject;
import javax.sql.PooledConnection;

public class StatementEvent
extends EventObject {
    private SQLException exception;
    private PreparedStatement statement;

    public StatementEvent(PooledConnection pooledConnection, PreparedStatement preparedStatement) {
        super(pooledConnection);
        this.statement = preparedStatement;
        this.exception = null;
    }

    public StatementEvent(PooledConnection pooledConnection, PreparedStatement preparedStatement, SQLException sQLException) {
        super(pooledConnection);
        this.statement = preparedStatement;
        this.exception = sQLException;
    }

    public SQLException getSQLException() {
        return this.exception;
    }

    public PreparedStatement getStatement() {
        return this.statement;
    }
}

