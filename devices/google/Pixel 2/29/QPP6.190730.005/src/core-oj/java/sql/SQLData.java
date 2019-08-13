/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public interface SQLData {
    public String getSQLTypeName() throws SQLException;

    public void readSQL(SQLInput var1, String var2) throws SQLException;

    public void writeSQL(SQLOutput var1) throws SQLException;
}

