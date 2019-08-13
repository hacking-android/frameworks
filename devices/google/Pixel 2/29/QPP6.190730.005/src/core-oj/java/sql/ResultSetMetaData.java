/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.sql.Wrapper;

public interface ResultSetMetaData
extends Wrapper {
    public static final int columnNoNulls = 0;
    public static final int columnNullable = 1;
    public static final int columnNullableUnknown = 2;

    public String getCatalogName(int var1) throws SQLException;

    public String getColumnClassName(int var1) throws SQLException;

    public int getColumnCount() throws SQLException;

    public int getColumnDisplaySize(int var1) throws SQLException;

    public String getColumnLabel(int var1) throws SQLException;

    public String getColumnName(int var1) throws SQLException;

    public int getColumnType(int var1) throws SQLException;

    public String getColumnTypeName(int var1) throws SQLException;

    public int getPrecision(int var1) throws SQLException;

    public int getScale(int var1) throws SQLException;

    public String getSchemaName(int var1) throws SQLException;

    public String getTableName(int var1) throws SQLException;

    public boolean isAutoIncrement(int var1) throws SQLException;

    public boolean isCaseSensitive(int var1) throws SQLException;

    public boolean isCurrency(int var1) throws SQLException;

    public boolean isDefinitelyWritable(int var1) throws SQLException;

    public int isNullable(int var1) throws SQLException;

    public boolean isReadOnly(int var1) throws SQLException;

    public boolean isSearchable(int var1) throws SQLException;

    public boolean isSigned(int var1) throws SQLException;

    public boolean isWritable(int var1) throws SQLException;
}

