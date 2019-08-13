/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.sql.SQLException;
import javax.sql.RowSetInternal;

public interface RowSetReader {
    public void readData(RowSetInternal var1) throws SQLException;
}

