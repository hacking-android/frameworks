/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;

public interface Savepoint {
    public int getSavepointId() throws SQLException;

    public String getSavepointName() throws SQLException;
}

