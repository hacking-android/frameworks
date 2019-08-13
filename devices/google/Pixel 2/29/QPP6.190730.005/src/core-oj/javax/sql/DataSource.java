/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Wrapper;
import javax.sql.CommonDataSource;

public interface DataSource
extends CommonDataSource,
Wrapper {
    public Connection getConnection() throws SQLException;

    public Connection getConnection(String var1, String var2) throws SQLException;
}

