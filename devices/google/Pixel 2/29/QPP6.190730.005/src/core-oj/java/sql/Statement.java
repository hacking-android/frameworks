/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Wrapper;

public interface Statement
extends Wrapper,
AutoCloseable {
    public static final int CLOSE_ALL_RESULTS = 3;
    public static final int CLOSE_CURRENT_RESULT = 1;
    public static final int EXECUTE_FAILED = -3;
    public static final int KEEP_CURRENT_RESULT = 2;
    public static final int NO_GENERATED_KEYS = 2;
    public static final int RETURN_GENERATED_KEYS = 1;
    public static final int SUCCESS_NO_INFO = -2;

    public void addBatch(String var1) throws SQLException;

    public void cancel() throws SQLException;

    public void clearBatch() throws SQLException;

    public void clearWarnings() throws SQLException;

    @Override
    public void close() throws SQLException;

    public boolean execute(String var1) throws SQLException;

    public boolean execute(String var1, int var2) throws SQLException;

    public boolean execute(String var1, int[] var2) throws SQLException;

    public boolean execute(String var1, String[] var2) throws SQLException;

    public int[] executeBatch() throws SQLException;

    public ResultSet executeQuery(String var1) throws SQLException;

    public int executeUpdate(String var1) throws SQLException;

    public int executeUpdate(String var1, int var2) throws SQLException;

    public int executeUpdate(String var1, int[] var2) throws SQLException;

    public int executeUpdate(String var1, String[] var2) throws SQLException;

    public Connection getConnection() throws SQLException;

    public int getFetchDirection() throws SQLException;

    public int getFetchSize() throws SQLException;

    public ResultSet getGeneratedKeys() throws SQLException;

    public int getMaxFieldSize() throws SQLException;

    public int getMaxRows() throws SQLException;

    public boolean getMoreResults() throws SQLException;

    public boolean getMoreResults(int var1) throws SQLException;

    public int getQueryTimeout() throws SQLException;

    public ResultSet getResultSet() throws SQLException;

    public int getResultSetConcurrency() throws SQLException;

    public int getResultSetHoldability() throws SQLException;

    public int getResultSetType() throws SQLException;

    public int getUpdateCount() throws SQLException;

    public SQLWarning getWarnings() throws SQLException;

    public boolean isClosed() throws SQLException;

    public boolean isPoolable() throws SQLException;

    public void setCursorName(String var1) throws SQLException;

    public void setEscapeProcessing(boolean var1) throws SQLException;

    public void setFetchDirection(int var1) throws SQLException;

    public void setFetchSize(int var1) throws SQLException;

    public void setMaxFieldSize(int var1) throws SQLException;

    public void setMaxRows(int var1) throws SQLException;

    public void setPoolable(boolean var1) throws SQLException;

    public void setQueryTimeout(int var1) throws SQLException;
}

