/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public interface Blob {
    public void free() throws SQLException;

    public InputStream getBinaryStream() throws SQLException;

    public InputStream getBinaryStream(long var1, long var3) throws SQLException;

    public byte[] getBytes(long var1, int var3) throws SQLException;

    public long length() throws SQLException;

    public long position(Blob var1, long var2) throws SQLException;

    public long position(byte[] var1, long var2) throws SQLException;

    public OutputStream setBinaryStream(long var1) throws SQLException;

    public int setBytes(long var1, byte[] var3) throws SQLException;

    public int setBytes(long var1, byte[] var3, int var4, int var5) throws SQLException;

    public void truncate(long var1) throws SQLException;
}

