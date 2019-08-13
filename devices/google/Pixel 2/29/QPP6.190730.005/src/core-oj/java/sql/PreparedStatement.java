/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public interface PreparedStatement
extends Statement {
    public void addBatch() throws SQLException;

    public void clearParameters() throws SQLException;

    public boolean execute() throws SQLException;

    public ResultSet executeQuery() throws SQLException;

    public int executeUpdate() throws SQLException;

    public ResultSetMetaData getMetaData() throws SQLException;

    public ParameterMetaData getParameterMetaData() throws SQLException;

    public void setArray(int var1, Array var2) throws SQLException;

    public void setAsciiStream(int var1, InputStream var2) throws SQLException;

    public void setAsciiStream(int var1, InputStream var2, int var3) throws SQLException;

    public void setAsciiStream(int var1, InputStream var2, long var3) throws SQLException;

    public void setBigDecimal(int var1, BigDecimal var2) throws SQLException;

    public void setBinaryStream(int var1, InputStream var2) throws SQLException;

    public void setBinaryStream(int var1, InputStream var2, int var3) throws SQLException;

    public void setBinaryStream(int var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(int var1, InputStream var2) throws SQLException;

    public void setBlob(int var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(int var1, Blob var2) throws SQLException;

    public void setBoolean(int var1, boolean var2) throws SQLException;

    public void setByte(int var1, byte var2) throws SQLException;

    public void setBytes(int var1, byte[] var2) throws SQLException;

    public void setCharacterStream(int var1, Reader var2) throws SQLException;

    public void setCharacterStream(int var1, Reader var2, int var3) throws SQLException;

    public void setCharacterStream(int var1, Reader var2, long var3) throws SQLException;

    public void setClob(int var1, Reader var2) throws SQLException;

    public void setClob(int var1, Reader var2, long var3) throws SQLException;

    public void setClob(int var1, Clob var2) throws SQLException;

    public void setDate(int var1, Date var2) throws SQLException;

    public void setDate(int var1, Date var2, Calendar var3) throws SQLException;

    public void setDouble(int var1, double var2) throws SQLException;

    public void setFloat(int var1, float var2) throws SQLException;

    public void setInt(int var1, int var2) throws SQLException;

    public void setLong(int var1, long var2) throws SQLException;

    public void setNCharacterStream(int var1, Reader var2) throws SQLException;

    public void setNCharacterStream(int var1, Reader var2, long var3) throws SQLException;

    public void setNClob(int var1, Reader var2) throws SQLException;

    public void setNClob(int var1, Reader var2, long var3) throws SQLException;

    public void setNClob(int var1, NClob var2) throws SQLException;

    public void setNString(int var1, String var2) throws SQLException;

    public void setNull(int var1, int var2) throws SQLException;

    public void setNull(int var1, int var2, String var3) throws SQLException;

    public void setObject(int var1, Object var2) throws SQLException;

    public void setObject(int var1, Object var2, int var3) throws SQLException;

    public void setObject(int var1, Object var2, int var3, int var4) throws SQLException;

    public void setRef(int var1, Ref var2) throws SQLException;

    public void setRowId(int var1, RowId var2) throws SQLException;

    public void setSQLXML(int var1, SQLXML var2) throws SQLException;

    public void setShort(int var1, short var2) throws SQLException;

    public void setString(int var1, String var2) throws SQLException;

    public void setTime(int var1, Time var2) throws SQLException;

    public void setTime(int var1, Time var2, Calendar var3) throws SQLException;

    public void setTimestamp(int var1, Timestamp var2) throws SQLException;

    public void setTimestamp(int var1, Timestamp var2, Calendar var3) throws SQLException;

    public void setURL(int var1, URL var2) throws SQLException;

    @Deprecated
    public void setUnicodeStream(int var1, InputStream var2, int var3) throws SQLException;
}

