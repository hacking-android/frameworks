/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;

public interface Clob {
    public void free() throws SQLException;

    public InputStream getAsciiStream() throws SQLException;

    public Reader getCharacterStream() throws SQLException;

    public Reader getCharacterStream(long var1, long var3) throws SQLException;

    public String getSubString(long var1, int var3) throws SQLException;

    public long length() throws SQLException;

    public long position(String var1, long var2) throws SQLException;

    public long position(Clob var1, long var2) throws SQLException;

    public OutputStream setAsciiStream(long var1) throws SQLException;

    public Writer setCharacterStream(long var1) throws SQLException;

    public int setString(long var1, String var3) throws SQLException;

    public int setString(long var1, String var3, int var4, int var5) throws SQLException;

    public void truncate(long var1) throws SQLException;
}

