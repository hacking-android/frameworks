/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface Array {
    public void free() throws SQLException;

    public Object getArray() throws SQLException;

    public Object getArray(long var1, int var3) throws SQLException;

    public Object getArray(long var1, int var3, Map<String, Class<?>> var4) throws SQLException;

    public Object getArray(Map<String, Class<?>> var1) throws SQLException;

    public int getBaseType() throws SQLException;

    public String getBaseTypeName() throws SQLException;

    public ResultSet getResultSet() throws SQLException;

    public ResultSet getResultSet(long var1, int var3) throws SQLException;

    public ResultSet getResultSet(long var1, int var3, Map<String, Class<?>> var4) throws SQLException;

    public ResultSet getResultSet(Map<String, Class<?>> var1) throws SQLException;
}

