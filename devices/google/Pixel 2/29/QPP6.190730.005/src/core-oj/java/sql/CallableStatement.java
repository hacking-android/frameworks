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
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public interface CallableStatement
extends PreparedStatement {
    public Array getArray(int var1) throws SQLException;

    public Array getArray(String var1) throws SQLException;

    public BigDecimal getBigDecimal(int var1) throws SQLException;

    @Deprecated
    public BigDecimal getBigDecimal(int var1, int var2) throws SQLException;

    public BigDecimal getBigDecimal(String var1) throws SQLException;

    public Blob getBlob(int var1) throws SQLException;

    public Blob getBlob(String var1) throws SQLException;

    public boolean getBoolean(int var1) throws SQLException;

    public boolean getBoolean(String var1) throws SQLException;

    public byte getByte(int var1) throws SQLException;

    public byte getByte(String var1) throws SQLException;

    public byte[] getBytes(int var1) throws SQLException;

    public byte[] getBytes(String var1) throws SQLException;

    public Reader getCharacterStream(int var1) throws SQLException;

    public Reader getCharacterStream(String var1) throws SQLException;

    public Clob getClob(int var1) throws SQLException;

    public Clob getClob(String var1) throws SQLException;

    public Date getDate(int var1) throws SQLException;

    public Date getDate(int var1, Calendar var2) throws SQLException;

    public Date getDate(String var1) throws SQLException;

    public Date getDate(String var1, Calendar var2) throws SQLException;

    public double getDouble(int var1) throws SQLException;

    public double getDouble(String var1) throws SQLException;

    public float getFloat(int var1) throws SQLException;

    public float getFloat(String var1) throws SQLException;

    public int getInt(int var1) throws SQLException;

    public int getInt(String var1) throws SQLException;

    public long getLong(int var1) throws SQLException;

    public long getLong(String var1) throws SQLException;

    public Reader getNCharacterStream(int var1) throws SQLException;

    public Reader getNCharacterStream(String var1) throws SQLException;

    public NClob getNClob(int var1) throws SQLException;

    public NClob getNClob(String var1) throws SQLException;

    public String getNString(int var1) throws SQLException;

    public String getNString(String var1) throws SQLException;

    public Object getObject(int var1) throws SQLException;

    public Object getObject(int var1, Map<String, Class<?>> var2) throws SQLException;

    public Object getObject(String var1) throws SQLException;

    public Object getObject(String var1, Map<String, Class<?>> var2) throws SQLException;

    public Ref getRef(int var1) throws SQLException;

    public Ref getRef(String var1) throws SQLException;

    public RowId getRowId(int var1) throws SQLException;

    public RowId getRowId(String var1) throws SQLException;

    public SQLXML getSQLXML(int var1) throws SQLException;

    public SQLXML getSQLXML(String var1) throws SQLException;

    public short getShort(int var1) throws SQLException;

    public short getShort(String var1) throws SQLException;

    public String getString(int var1) throws SQLException;

    public String getString(String var1) throws SQLException;

    public Time getTime(int var1) throws SQLException;

    public Time getTime(int var1, Calendar var2) throws SQLException;

    public Time getTime(String var1) throws SQLException;

    public Time getTime(String var1, Calendar var2) throws SQLException;

    public Timestamp getTimestamp(int var1) throws SQLException;

    public Timestamp getTimestamp(int var1, Calendar var2) throws SQLException;

    public Timestamp getTimestamp(String var1) throws SQLException;

    public Timestamp getTimestamp(String var1, Calendar var2) throws SQLException;

    public URL getURL(int var1) throws SQLException;

    public URL getURL(String var1) throws SQLException;

    public void registerOutParameter(int var1, int var2) throws SQLException;

    public void registerOutParameter(int var1, int var2, int var3) throws SQLException;

    public void registerOutParameter(int var1, int var2, String var3) throws SQLException;

    public void registerOutParameter(String var1, int var2) throws SQLException;

    public void registerOutParameter(String var1, int var2, int var3) throws SQLException;

    public void registerOutParameter(String var1, int var2, String var3) throws SQLException;

    public void setAsciiStream(String var1, InputStream var2) throws SQLException;

    public void setAsciiStream(String var1, InputStream var2, int var3) throws SQLException;

    public void setAsciiStream(String var1, InputStream var2, long var3) throws SQLException;

    public void setBigDecimal(String var1, BigDecimal var2) throws SQLException;

    public void setBinaryStream(String var1, InputStream var2) throws SQLException;

    public void setBinaryStream(String var1, InputStream var2, int var3) throws SQLException;

    public void setBinaryStream(String var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(String var1, InputStream var2) throws SQLException;

    public void setBlob(String var1, InputStream var2, long var3) throws SQLException;

    public void setBlob(String var1, Blob var2) throws SQLException;

    public void setBoolean(String var1, boolean var2) throws SQLException;

    public void setByte(String var1, byte var2) throws SQLException;

    public void setBytes(String var1, byte[] var2) throws SQLException;

    public void setCharacterStream(String var1, Reader var2) throws SQLException;

    public void setCharacterStream(String var1, Reader var2, int var3) throws SQLException;

    public void setCharacterStream(String var1, Reader var2, long var3) throws SQLException;

    public void setClob(String var1, Reader var2) throws SQLException;

    public void setClob(String var1, Reader var2, long var3) throws SQLException;

    public void setClob(String var1, Clob var2) throws SQLException;

    public void setDate(String var1, Date var2) throws SQLException;

    public void setDate(String var1, Date var2, Calendar var3) throws SQLException;

    public void setDouble(String var1, double var2) throws SQLException;

    public void setFloat(String var1, float var2) throws SQLException;

    public void setInt(String var1, int var2) throws SQLException;

    public void setLong(String var1, long var2) throws SQLException;

    public void setNCharacterStream(String var1, Reader var2) throws SQLException;

    public void setNCharacterStream(String var1, Reader var2, long var3) throws SQLException;

    public void setNClob(String var1, Reader var2) throws SQLException;

    public void setNClob(String var1, Reader var2, long var3) throws SQLException;

    public void setNClob(String var1, NClob var2) throws SQLException;

    public void setNString(String var1, String var2) throws SQLException;

    public void setNull(String var1, int var2) throws SQLException;

    public void setNull(String var1, int var2, String var3) throws SQLException;

    public void setObject(String var1, Object var2) throws SQLException;

    public void setObject(String var1, Object var2, int var3) throws SQLException;

    public void setObject(String var1, Object var2, int var3, int var4) throws SQLException;

    public void setRowId(String var1, RowId var2) throws SQLException;

    public void setSQLXML(String var1, SQLXML var2) throws SQLException;

    public void setShort(String var1, short var2) throws SQLException;

    public void setString(String var1, String var2) throws SQLException;

    public void setTime(String var1, Time var2) throws SQLException;

    public void setTime(String var1, Time var2, Calendar var3) throws SQLException;

    public void setTimestamp(String var1, Timestamp var2) throws SQLException;

    public void setTimestamp(String var1, Timestamp var2, Calendar var3) throws SQLException;

    public void setURL(String var1, URL var2) throws SQLException;

    public boolean wasNull() throws SQLException;
}

