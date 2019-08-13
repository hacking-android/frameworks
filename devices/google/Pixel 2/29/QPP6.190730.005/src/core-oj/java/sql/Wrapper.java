/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;

public interface Wrapper {
    public boolean isWrapperFor(Class<?> var1) throws SQLException;

    public <T> T unwrap(Class<T> var1) throws SQLException;
}

