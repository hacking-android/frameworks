/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.sql.Wrapper;

public interface ParameterMetaData
extends Wrapper {
    public static final int parameterModeIn = 1;
    public static final int parameterModeInOut = 2;
    public static final int parameterModeOut = 4;
    public static final int parameterModeUnknown = 0;
    public static final int parameterNoNulls = 0;
    public static final int parameterNullable = 1;
    public static final int parameterNullableUnknown = 2;

    public String getParameterClassName(int var1) throws SQLException;

    public int getParameterCount() throws SQLException;

    public int getParameterMode(int var1) throws SQLException;

    public int getParameterType(int var1) throws SQLException;

    public String getParameterTypeName(int var1) throws SQLException;

    public int getPrecision(int var1) throws SQLException;

    public int getScale(int var1) throws SQLException;

    public int isNullable(int var1) throws SQLException;

    public boolean isSigned(int var1) throws SQLException;
}

