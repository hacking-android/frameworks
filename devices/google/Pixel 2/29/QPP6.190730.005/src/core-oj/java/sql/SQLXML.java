/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

public interface SQLXML {
    public void free() throws SQLException;

    public InputStream getBinaryStream() throws SQLException;

    public Reader getCharacterStream() throws SQLException;

    public <T extends Source> T getSource(Class<T> var1) throws SQLException;

    public String getString() throws SQLException;

    public OutputStream setBinaryStream() throws SQLException;

    public Writer setCharacterStream() throws SQLException;

    public <T extends Result> T setResult(Class<T> var1) throws SQLException;

    public void setString(String var1) throws SQLException;
}

