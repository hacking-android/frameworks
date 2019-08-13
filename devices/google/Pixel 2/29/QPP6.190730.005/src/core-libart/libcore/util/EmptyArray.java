/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class EmptyArray {
    public static final boolean[] BOOLEAN = new boolean[0];
    @UnsupportedAppUsage
    public static final byte[] BYTE = new byte[0];
    public static final char[] CHAR = new char[0];
    public static final Class<?>[] CLASS;
    public static final double[] DOUBLE;
    public static final float[] FLOAT;
    @UnsupportedAppUsage
    public static final int[] INT;
    @UnsupportedAppUsage
    public static final long[] LONG;
    @UnsupportedAppUsage
    public static final Object[] OBJECT;
    public static final StackTraceElement[] STACK_TRACE_ELEMENT;
    public static final String[] STRING;
    public static final Throwable[] THROWABLE;
    public static final Type[] TYPE;
    public static final TypeVariable[] TYPE_VARIABLE;

    static {
        DOUBLE = new double[0];
        FLOAT = new float[0];
        INT = new int[0];
        LONG = new long[0];
        CLASS = new Class[0];
        OBJECT = new Object[0];
        STRING = new String[0];
        THROWABLE = new Throwable[0];
        STACK_TRACE_ELEMENT = new StackTraceElement[0];
        TYPE = new Type[0];
        TYPE_VARIABLE = new TypeVariable[0];
    }

    private EmptyArray() {
    }
}

