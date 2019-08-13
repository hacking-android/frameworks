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
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Calendar;
import java.util.Map;

public interface ResultSet
extends Wrapper,
AutoCloseable {
    public static final int CLOSE_CURSORS_AT_COMMIT = 2;
    public static final int CONCUR_READ_ONLY = 1007;
    public static final int CONCUR_UPDATABLE = 1008;
    public static final int FETCH_FORWARD = 1000;
    public static final int FETCH_REVERSE = 1001;
    public static final int FETCH_UNKNOWN = 1002;
    public static final int HOLD_CURSORS_OVER_COMMIT = 1;
    public static final int TYPE_FORWARD_ONLY = 1003;
    public static final int TYPE_SCROLL_INSENSITIVE = 1004;
    public static final int TYPE_SCROLL_SENSITIVE = 1005;

    public boolean absolute(int var1) throws SQLException;

    public void afterLast() throws SQLException;

    public void beforeFirst() throws SQLException;

    public void cancelRowUpdates() throws SQLException;

    public void clearWarnings() throws SQLException;

    @Override
    public void close() throws SQLException;

    public void deleteRow() throws SQLException;

    public int findColumn(String var1) throws SQLException;

    public boolean first() throws SQLException;

    public Array getArray(int var1) throws SQLException;

    public Array getArray(String var1) throws SQLException;

    public InputStream getAsciiStream(int var1) throws SQLException;

    public InputStream getAsciiStream(String var1) throws SQLException;

    public BigDecimal getBigDecimal(int var1) throws SQLException;

    @Deprecated
    public BigDecimal getBigDecimal(int var1, int var2) throws SQLException;

    public BigDecimal getBigDecimal(String var1) throws SQLException;

    @Deprecated
    public BigDecimal getBigDecimal(String var1, int var2) throws SQLException;

    public InputStream getBinaryStream(int var1) throws SQLException;

    public InputStream getBinaryStream(String var1) throws SQLException;

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

    public int getConcurrency() throws SQLException;

    public String getCursorName() throws SQLException;

    public Date getDate(int var1) throws SQLException;

    public Date getDate(int var1, Calendar var2) throws SQLException;

    public Date getDate(String var1) throws SQLException;

    public Date getDate(String var1, Calendar var2) throws SQLException;

    public double getDouble(int var1) throws SQLException;

    public double getDouble(String var1) throws SQLException;

    public int getFetchDirection() throws SQLException;

    public int getFetchSize() throws SQLException;

    public float getFloat(int var1) throws SQLException;

    public float getFloat(String var1) throws SQLException;

    public int getHoldability() throws SQLException;

    public int getInt(int var1) throws SQLException;

    public int getInt(String var1) throws SQLException;

    public long getLong(int var1) throws SQLException;

    public long getLong(String var1) throws SQLException;

    public ResultSetMetaData getMetaData() throws SQLException;

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

    public int getRow() throws SQLException;

    public RowId getRowId(int var1) throws SQLException;

    public RowId getRowId(String var1) throws SQLException;

    public SQLXML getSQLXML(int var1) throws SQLException;

    public SQLXML getSQLXML(String var1) throws SQLException;

    public short getShort(int var1) throws SQLException;

    public short getShort(String var1) throws SQLException;

    public Statement getStatement() throws SQLException;

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

    public int getType() throws SQLException;

    public URL getURL(int var1) throws SQLException;

    public URL getURL(String var1) throws SQLException;

    @Deprecated
    public InputStream getUnicodeStream(int var1) throws SQLException;

    @Deprecated
    public InputStream getUnicodeStream(String var1) throws SQLException;

    public SQLWarning getWarnings() throws SQLException;

    public void insertRow() throws SQLException;

    public boolean isAfterLast() throws SQLException;

    public boolean isBeforeFirst() throws SQLException;

    public boolean isClosed() throws SQLException;

    public boolean isFirst() throws SQLException;

    public boolean isLast() throws SQLException;

    public boolean last() throws SQLException;

    public void moveToCurrentRow() throws SQLException;

    public void moveToInsertRow() throws SQLException;

    public boolean next() throws SQLException;

    public boolean previous() throws SQLException;

    public void refreshRow() throws SQLException;

    public boolean relative(int var1) throws SQLException;

    public boolean rowDeleted() throws SQLException;

    public boolean rowInserted() throws SQLException;

    public boolean rowUpdated() throws SQLException;

    public void setFetchDirection(int var1) throws SQLException;

    public void setFetchSize(int var1) throws SQLException;

    public void updateArray(int var1, Array var2) throws SQLException;

    public void updateArray(String var1, Array var2) throws SQLException;

    public void updateAsciiStream(int var1, InputStream var2) throws SQLException;

    public void updateAsciiStream(int var1, InputStream var2, int var3) throws SQLException;

    public void updateAsciiStream(int var1, InputStream var2, long var3) throws SQLException;

    public void updateAsciiStream(String var1, InputStream var2) throws SQLException;

    public void updateAsciiStream(String var1, InputStream var2, int var3) throws SQLException;

    public void updateAsciiStream(String var1, InputStream var2, long var3) throws SQLException;

    public void updateBigDecimal(int var1, BigDecimal var2) throws SQLException;

    public void updateBigDecimal(String var1, BigDecimal var2) throws SQLException;

    public void updateBinaryStream(int var1, InputStream var2) throws SQLException;

    public void updateBinaryStream(int var1, InputStream var2, int var3) throws SQLException;

    public void updateBinaryStream(int var1, InputStream var2, long var3) throws SQLException;

    public void updateBinaryStream(String var1, InputStream var2) throws SQLException;

    public void updateBinaryStream(String var1, InputStream var2, int var3) throws SQLException;

    public void updateBinaryStream(String var1, InputStream var2, long var3) throws SQLException;

    public void updateBlob(int var1, InputStream var2) throws SQLException;

    public void updateBlob(int var1, InputStream var2, long var3) throws SQLException;

    public void updateBlob(int var1, Blob var2) throws SQLException;

    public void updateBlob(String var1, InputStream var2) throws SQLException;

    public void updateBlob(String var1, InputStream var2, long var3) throws SQLException;

    public void updateBlob(String var1, Blob var2) throws SQLException;

    public void updateBoolean(int var1, boolean var2) throws SQLException;

    public void updateBoolean(String var1, boolean var2) throws SQLException;

    public void updateByte(int var1, byte var2) throws SQLException;

    public void updateByte(String var1, byte var2) throws SQLException;

    public void updateBytes(int var1, byte[] var2) throws SQLException;

    public void updateBytes(String var1, byte[] var2) throws SQLException;

    public void updateCharacterStream(int var1, Reader var2) throws SQLException;

    public void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException;

    public void updateCharacterStream(int var1, Reader var2, long var3) throws SQLException;

    public void updateCharacterStream(String var1, Reader var2) throws SQLException;

    public void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException;

    public void updateCharacterStream(String var1, Reader var2, long var3) throws SQLException;

    public void updateClob(int var1, Reader var2) throws SQLException;

    public void updateClob(int var1, Reader var2, long var3) throws SQLException;

    public void updateClob(int var1, Clob var2) throws SQLException;

    public void updateClob(String var1, Reader var2) throws SQLException;

    public void updateClob(String var1, Reader var2, long var3) throws SQLException;

    public void updateClob(String var1, Clob var2) throws SQLException;

    public void updateDate(int var1, Date var2) throws SQLException;

    public void updateDate(String var1, Date var2) throws SQLException;

    public void updateDouble(int var1, double var2) throws SQLException;

    public void updateDouble(String var1, double var2) throws SQLException;

    public void updateFloat(int var1, float var2) throws SQLException;

    public void updateFloat(String var1, float var2) throws SQLException;

    public void updateInt(int var1, int var2) throws SQLException;

    public void updateInt(String var1, int var2) throws SQLException;

    public void updateLong(int var1, long var2) throws SQLException;

    public void updateLong(String var1, long var2) throws SQLException;

    public void updateNCharacterStream(int var1, Reader var2) throws SQLException;

    public void updateNCharacterStream(int var1, Reader var2, long var3) throws SQLException;

    public void updateNCharacterStream(String var1, Reader var2) throws SQLException;

    public void updateNCharacterStream(String var1, Reader var2, long var3) throws SQLException;

    public void updateNClob(int var1, Reader var2) throws SQLException;

    public void updateNClob(int var1, Reader var2, long var3) throws SQLException;

    public void updateNClob(int var1, NClob var2) throws SQLException;

    public void updateNClob(String var1, Reader var2) throws SQLException;

    public void updateNClob(String var1, Reader var2, long var3) throws SQLException;

    public void updateNClob(String var1, NClob var2) throws SQLException;

    public void updateNString(int var1, String var2) throws SQLException;

    public void updateNString(String var1, String var2) throws SQLException;

    public void updateNull(int var1) throws SQLException;

    public void updateNull(String var1) throws SQLException;

    public void updateObject(int var1, Object var2) throws SQLException;

    public void updateObject(int var1, Object var2, int var3) throws SQLException;

    public void updateObject(String var1, Object var2) throws SQLException;

    public void updateObject(String var1, Object var2, int var3) throws SQLException;

    public void updateRef(int var1, Ref var2) throws SQLException;

    public void updateRef(String var1, Ref var2) throws SQLException;

    public void updateRow() throws SQLException;

    public void updateRowId(int var1, RowId var2) throws SQLException;

    public void updateRowId(String var1, RowId var2) throws SQLException;

    public void updateSQLXML(int var1, SQLXML var2) throws SQLException;

    public void updateSQLXML(String var1, SQLXML var2) throws SQLException;

    public void updateShort(int var1, short var2) throws SQLException;

    public void updateShort(String var1, short var2) throws SQLException;

    public void updateString(int var1, String var2) throws SQLException;

    public void updateString(String var1, String var2) throws SQLException;

    public void updateTime(int var1, Time var2) throws SQLException;

    public void updateTime(String var1, Time var2) throws SQLException;

    public void updateTimestamp(int var1, Timestamp var2) throws SQLException;

    public void updateTimestamp(String var1, Timestamp var2) throws SQLException;

    public boolean wasNull() throws SQLException;
}

