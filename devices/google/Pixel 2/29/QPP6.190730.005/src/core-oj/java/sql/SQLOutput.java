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
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;

public interface SQLOutput {
    public void writeArray(Array var1) throws SQLException;

    public void writeAsciiStream(InputStream var1) throws SQLException;

    public void writeBigDecimal(BigDecimal var1) throws SQLException;

    public void writeBinaryStream(InputStream var1) throws SQLException;

    public void writeBlob(Blob var1) throws SQLException;

    public void writeBoolean(boolean var1) throws SQLException;

    public void writeByte(byte var1) throws SQLException;

    public void writeBytes(byte[] var1) throws SQLException;

    public void writeCharacterStream(Reader var1) throws SQLException;

    public void writeClob(Clob var1) throws SQLException;

    public void writeDate(Date var1) throws SQLException;

    public void writeDouble(double var1) throws SQLException;

    public void writeFloat(float var1) throws SQLException;

    public void writeInt(int var1) throws SQLException;

    public void writeLong(long var1) throws SQLException;

    public void writeNClob(NClob var1) throws SQLException;

    public void writeNString(String var1) throws SQLException;

    public void writeObject(SQLData var1) throws SQLException;

    public void writeRef(Ref var1) throws SQLException;

    public void writeRowId(RowId var1) throws SQLException;

    public void writeSQLXML(SQLXML var1) throws SQLException;

    public void writeShort(short var1) throws SQLException;

    public void writeString(String var1) throws SQLException;

    public void writeStruct(Struct var1) throws SQLException;

    public void writeTime(Time var1) throws SQLException;

    public void writeTimestamp(Timestamp var1) throws SQLException;

    public void writeURL(URL var1) throws SQLException;
}

