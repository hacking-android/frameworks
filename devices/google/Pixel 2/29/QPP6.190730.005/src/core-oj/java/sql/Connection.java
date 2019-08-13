/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Wrapper;
import java.util.Map;
import java.util.Properties;

public interface Connection
extends Wrapper,
AutoCloseable {
    public static final int TRANSACTION_NONE = 0;
    public static final int TRANSACTION_READ_COMMITTED = 2;
    public static final int TRANSACTION_READ_UNCOMMITTED = 1;
    public static final int TRANSACTION_REPEATABLE_READ = 4;
    public static final int TRANSACTION_SERIALIZABLE = 8;

    public void clearWarnings() throws SQLException;

    @Override
    public void close() throws SQLException;

    public void commit() throws SQLException;

    public Array createArrayOf(String var1, Object[] var2) throws SQLException;

    public Blob createBlob() throws SQLException;

    public Clob createClob() throws SQLException;

    public NClob createNClob() throws SQLException;

    public SQLXML createSQLXML() throws SQLException;

    public Statement createStatement() throws SQLException;

    public Statement createStatement(int var1, int var2) throws SQLException;

    public Statement createStatement(int var1, int var2, int var3) throws SQLException;

    public Struct createStruct(String var1, Object[] var2) throws SQLException;

    public boolean getAutoCommit() throws SQLException;

    public String getCatalog() throws SQLException;

    public String getClientInfo(String var1) throws SQLException;

    public Properties getClientInfo() throws SQLException;

    public int getHoldability() throws SQLException;

    public DatabaseMetaData getMetaData() throws SQLException;

    public int getTransactionIsolation() throws SQLException;

    public Map<String, Class<?>> getTypeMap() throws SQLException;

    public SQLWarning getWarnings() throws SQLException;

    public boolean isClosed() throws SQLException;

    public boolean isReadOnly() throws SQLException;

    public boolean isValid(int var1) throws SQLException;

    public String nativeSQL(String var1) throws SQLException;

    public CallableStatement prepareCall(String var1) throws SQLException;

    public CallableStatement prepareCall(String var1, int var2, int var3) throws SQLException;

    public CallableStatement prepareCall(String var1, int var2, int var3, int var4) throws SQLException;

    public PreparedStatement prepareStatement(String var1) throws SQLException;

    public PreparedStatement prepareStatement(String var1, int var2) throws SQLException;

    public PreparedStatement prepareStatement(String var1, int var2, int var3) throws SQLException;

    public PreparedStatement prepareStatement(String var1, int var2, int var3, int var4) throws SQLException;

    public PreparedStatement prepareStatement(String var1, int[] var2) throws SQLException;

    public PreparedStatement prepareStatement(String var1, String[] var2) throws SQLException;

    public void releaseSavepoint(Savepoint var1) throws SQLException;

    public void rollback() throws SQLException;

    public void rollback(Savepoint var1) throws SQLException;

    public void setAutoCommit(boolean var1) throws SQLException;

    public void setCatalog(String var1) throws SQLException;

    public void setClientInfo(String var1, String var2) throws SQLClientInfoException;

    public void setClientInfo(Properties var1) throws SQLClientInfoException;

    public void setHoldability(int var1) throws SQLException;

    public void setReadOnly(boolean var1) throws SQLException;

    public Savepoint setSavepoint() throws SQLException;

    public Savepoint setSavepoint(String var1) throws SQLException;

    public void setTransactionIsolation(int var1) throws SQLException;

    public void setTypeMap(Map<String, Class<?>> var1) throws SQLException;
}

