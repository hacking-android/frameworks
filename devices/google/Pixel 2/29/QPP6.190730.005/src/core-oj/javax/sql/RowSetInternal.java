/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.RowSetMetaData;

public interface RowSetInternal {
    public Connection getConnection() throws SQLException;

    public ResultSet getOriginal() throws SQLException;

    public ResultSet getOriginalRow() throws SQLException;

    public Object[] getParams() throws SQLException;

    public void setMetaData(RowSetMetaData var1) throws SQLException;
}

