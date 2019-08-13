/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;

public interface PooledConnection {
    public void addConnectionEventListener(ConnectionEventListener var1);

    public void addStatementEventListener(StatementEventListener var1);

    public void close() throws SQLException;

    public Connection getConnection() throws SQLException;

    public void removeConnectionEventListener(ConnectionEventListener var1);

    public void removeStatementEventListener(StatementEventListener var1);
}

