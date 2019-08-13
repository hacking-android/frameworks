/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.util.Map;

public interface Ref {
    public String getBaseTypeName() throws SQLException;

    public Object getObject() throws SQLException;

    public Object getObject(Map<String, Class<?>> var1) throws SQLException;

    public void setObject(Object var1) throws SQLException;
}

