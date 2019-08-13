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
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

public interface SQLInput {
    public Array readArray() throws SQLException;

    public InputStream readAsciiStream() throws SQLException;

    public BigDecimal readBigDecimal() throws SQLException;

    public InputStream readBinaryStream() throws SQLException;

    public Blob readBlob() throws SQLException;

    public boolean readBoolean() throws SQLException;

    public byte readByte() throws SQLException;

    public byte[] readBytes() throws SQLException;

    public Reader readCharacterStream() throws SQLException;

    public Clob readClob() throws SQLException;

    public Date readDate() throws SQLException;

    public double readDouble() throws SQLException;

    public float readFloat() throws SQLException;

    public int readInt() throws SQLException;

    public long readLong() throws SQLException;

    public NClob readNClob() throws SQLException;

    public String readNString() throws SQLException;

    public Object readObject() throws SQLException;

    public Ref readRef() throws SQLException;

    public RowId readRowId() throws SQLException;

    public SQLXML readSQLXML() throws SQLException;

    public short readShort() throws SQLException;

    public String readString() throws SQLException;

    public Time readTime() throws SQLException;

    public Timestamp readTimestamp() throws SQLException;

    public URL readURL() throws SQLException;

    public boolean wasNull() throws SQLException;
}

