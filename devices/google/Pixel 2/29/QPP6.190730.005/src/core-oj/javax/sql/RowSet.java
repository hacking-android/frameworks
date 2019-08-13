/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import javax.sql.RowSetListener;

public interface RowSet
extends ResultSet {
    public void addRowSetListener(RowSetListener var1);

    public void clearParameters() throws SQLException;

    public void execute() throws SQLException;

    public String getCommand();

    public String getDataSourceName();

    public boolean getEscapeProcessing() throws SQLException;

    public int getMaxFieldSize() throws SQLException;

    public int getMaxRows() throws SQLException;

    public String getPassword();

    public int getQueryTimeout() throws SQLException;

    public int getTransactionIsolation();

    public Map<String, Class<?>> getTypeMap() throws SQLException;

    public String getUrl() throws SQLException;

    public String getUsername();

    public boolean isReadOnly();

    public void removeRowSetListener(RowSetListener var1);

    public void setArray(int var1, Array var2) throws SQLException;

    public void setAsciiStream(int var1, InputStream var2) throws SQLException;

    public void setAsciiStream(int var1, InputStream var2, int var3) throws SQLException;

    public void setAsciiStream(String var1, InputStream var2) throws SQLException;

    public void setAsciiStream(String var1, InputStream var2, int var3) throws SQLException;

    public void setBigDecimal(int var1, BigDecimal var2) throws SQLException;

    public void setBigDecimal(String var1, BigDecimal var2) throws SQLException;

    public void setBinaryStream(int var1, InputStream var2) throws SQLException;

    public void setBinaryStream(int var1, InputStream var2, int var3) throws SQLException;

    public void setBinaryStream(String var1, InputStream var2) throws SQLException;

    public void setBinaryStream(String var1, InputStream var2, int var3) throws SQLException;

    public void setBlob(int var1, InputStream var2) throws SQLException;

    public void setBlob(int var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(int var1, Blob var2) throws SQLException;

    public void setBlob(String var1, InputStream var2) throws SQLException;

    public void setBlob(String var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(String var1, Blob var2) throws SQLException;

    public void setBoolean(int var1, boolean var2) throws SQLException;

    public void setBoolean(String var1, boolean var2) throws SQLException;

    public void setByte(int var1, byte var2) throws SQLException;

    public void setByte(String var1, byte var2) throws SQLException;

    public void setBytes(int var1, byte[] var2) throws SQLException;

    public void setBytes(String var1, byte[] var2) throws SQLException;

    public void setCharacterStream(int var1, Reader var2) throws SQLException;

    public void setCharacterStream(int var1, Reader var2, int var3) throws SQLException;

    public void setCharacterStream(String var1, Reader var2) throws SQLException;

    public void setCharacterStream(String var1, Reader var2, int var3) throws SQLException;

    public void setClob(int var1, Reader var2) throws SQLException;

    public void setClob(int var1, Reader var2, long var3) throws SQLException;

    public void setClob(int var1, Clob var2) throws SQLException;

    public void setClob(String var1, Reader var2) throws SQLException;

    public void setClob(String var1, Reader var2, long var3) throws SQLException;

    public void setClob(String var1, Clob var2) throws SQLException;

    public void setCommand(String var1) throws SQLException;

    public void setConcurrency(int var1) throws SQLException;

    public void setDataSourceName(String var1) throws SQLException;

    public void setDate(int var1, Date var2) throws SQLException;

    public void setDate(int var1, Date var2, Calendar var3) throws SQLException;

    public void setDate(String var1, Date var2) throws SQLException;

    public void setDate(String var1, Date var2, Calendar var3) throws SQLException;

    public void setDouble(int var1, double var2) throws SQLException;

    public void setDouble(String var1, double var2) throws SQLException;

    public void setEscapeProcessing(boolean var1) throws SQLException;

    public void setFloat(int var1, float var2) throws SQLException;

    public void setFloat(String var1, float var2) throws SQLException;

    public void setInt(int var1, int var2) throws SQLException;

    public void setInt(String var1, int var2) throws SQLException;

    public void setLong(int var1, long var2) throws SQLException;

    public void setLong(String var1, long var2) throws SQLException;

    public void setMaxFieldSize(int var1) throws SQLException;

    public void setMaxRows(int var1) throws SQLException;

    public void setNCharacterStream(int var1, Reader var2) throws SQLException;

    public void setNCharacterStream(int var1, Reader var2, long var3) throws SQLException;

    public void setNCharacterStream(String var1, Reader var2) throws SQLException;

    public void setNCharacterStream(String var1, Reader var2, long var3) throws SQLException;

    public void setNClob(int var1, Reader var2) throws SQLException;

    public void setNClob(int var1, Reader var2, long var3) throws SQLException;

    public void setNClob(int var1, NClob var2) throws SQLException;

    public void setNClob(String var1, Reader var2) throws SQLException;

    public void setNClob(String var1, Reader var2, long var3) throws SQLException;

    public void setNClob(String var1, NClob var2) throws SQLException;

    public void setNString(int var1, String var2) throws SQLException;

    public void setNString(String var1, String var2) throws SQLException;

    public void setNull(int var1, int var2) throws SQLException;

    public void setNull(int var1, int var2, String var3) throws SQLException;

    public void setNull(String var1, int var2) throws SQLException;

    public void setNull(String var1, int var2, String var3) throws SQLException;

    public void setObject(int var1, Object var2) throws SQLException;

    public void setObject(int var1, Object var2, int var3) throws SQLException;

    public void setObject(int var1, Object var2, int var3, int var4) throws SQLException;

    public void setObject(String var1, Object var2) throws SQLException;

    public void setObject(String var1, Object var2, int var3) throws SQLException;

    public void setObject(String var1, Object var2, int var3, int var4) throws SQLException;

    public void setPassword(String var1) throws SQLException;

    public void setQueryTimeout(int var1) throws SQLException;

    public void setReadOnly(boolean var1) throws SQLException;

    public void setRef(int var1, Ref var2) throws SQLException;

    public void setRowId(int var1, RowId var2) throws SQLException;

    public void setRowId(String var1, RowId var2) throws SQLException;

    public void setSQLXML(int var1, SQLXML var2) throws SQLException;

    public void setSQLXML(String var1, SQLXML var2) throws SQLException;

    public void setShort(int var1, short var2) throws SQLException;

    public void setShort(String var1, short var2) throws SQLException;

    public void setString(int var1, String var2) throws SQLException;

    public void setString(String var1, String var2) throws SQLException;

    public void setTime(int var1, Time var2) throws SQLException;

    public void setTime(int var1, Time var2, Calendar var3) throws SQLException;

    public void setTime(String var1, Time var2) throws SQLException;

    public void setTime(String var1, Time var2, Calendar var3) throws SQLException;

    public void setTimestamp(int var1, Timestamp var2) throws SQLException;

    public void setTimestamp(int var1, Timestamp var2, Calendar var3) throws SQLException;

    public void setTimestamp(String var1, Timestamp var2) throws SQLException;

    public void setTimestamp(String var1, Timestamp var2, Calendar var3) throws SQLException;

    public void setTransactionIsolation(int var1) throws SQLException;

    public void setType(int var1) throws SQLException;

    public void setTypeMap(Map<String, Class<?>> var1) throws SQLException;

    public void setURL(int var1, URL var2) throws SQLException;

    public void setUrl(String var1) throws SQLException;

    public void setUsername(String var1) throws SQLException;
}

