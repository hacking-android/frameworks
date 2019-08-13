/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface RowSetMetaData
extends ResultSetMetaData {
    public void setAutoIncrement(int var1, boolean var2) throws SQLException;

    public void setCaseSensitive(int var1, boolean var2) throws SQLException;

    public void setCatalogName(int var1, String var2) throws SQLException;

    public void setColumnCount(int var1) throws SQLException;

    public void setColumnDisplaySize(int var1, int var2) throws SQLException;

    public void setColumnLabel(int var1, String var2) throws SQLException;

    public void setColumnName(int var1, String var2) throws SQLException;

    public void setColumnType(int var1, int var2) throws SQLException;

    public void setColumnTypeName(int var1, String var2) throws SQLException;

    public void setCurrency(int var1, boolean var2) throws SQLException;

    public void setNullable(int var1, int var2) throws SQLException;

    public void setPrecision(int var1, int var2) throws SQLException;

    public void setScale(int var1, int var2) throws SQLException;

    public void setSchemaName(int var1, String var2) throws SQLException;

    public void setSearchable(int var1, boolean var2) throws SQLException;

    public void setSigned(int var1, boolean var2) throws SQLException;

    public void setTableName(int var1, String var2) throws SQLException;
}

