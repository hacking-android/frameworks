/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public interface CommonDataSource {
    public PrintWriter getLogWriter() throws SQLException;

    public int getLoginTimeout() throws SQLException;

    public Logger getParentLogger() throws SQLFeatureNotSupportedException;

    public void setLogWriter(PrintWriter var1) throws SQLException;

    public void setLoginTimeout(int var1) throws SQLException;
}

