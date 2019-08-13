/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.SQLException;
import javax.sql.CommonDataSource;
import javax.sql.PooledConnection;

public interface ConnectionPoolDataSource
extends CommonDataSource {
    public PooledConnection getPooledConnection() throws SQLException;

    public PooledConnection getPooledConnection(String var1, String var2) throws SQLException;
}

