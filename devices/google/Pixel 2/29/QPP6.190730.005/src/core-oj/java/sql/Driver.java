/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public interface Driver {
    public boolean acceptsURL(String var1) throws SQLException;

    public Connection connect(String var1, Properties var2) throws SQLException;

    public int getMajorVersion();

    public int getMinorVersion();

    public DriverPropertyInfo[] getPropertyInfo(String var1, Properties var2) throws SQLException;

    public boolean jdbcCompliant();
}

