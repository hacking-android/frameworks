/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.util.Map;

public interface Struct {
    public Object[] getAttributes() throws SQLException;

    public Object[] getAttributes(Map<String, Class<?>> var1) throws SQLException;

    public String getSQLTypeName() throws SQLException;
}

